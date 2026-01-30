package io.roastedroot.lumis4j.core;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.dylibso.chicory.annotations.WasmModuleInterface;
import com.dylibso.chicory.runtime.ImportValues;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasi.WasiOptions;
import com.dylibso.chicory.wasi.WasiPreview1;

@WasmModuleInterface(WasmResource.absoluteFile)
public final class Lumis implements AutoCloseable {

    private static final Theme DEFAULT_THEME = Theme.GITHUB_DARK;
    private static final Lang DEFAULT_LANG = Lang.PLAINTEXT;

    private final Theme theme;
    private final Lang lang;

    // wasm fields
    private final Instance instance;
    private final WasiPreview1 wasi;
    private final Lumis_ModuleExports exports;

    private Lumis(Theme theme, Lang lang) {
        this.theme = theme;
        this.lang = lang;

        var wasiOpts = WasiOptions.builder().inheritSystem().build();
        this.wasi = WasiPreview1.builder().withOptions(wasiOpts).build();
        var imports = ImportValues.builder().addFunction(wasi.toHostFunctions()).build();
        this.instance =
                Instance.builder(LumisModule.load())
                        .withImportValues(imports)
                        .withMachineFactory(LumisModule::create)
                        .build();
        this.exports = new Lumis_ModuleExports(this.instance);
    }

    public static Builder builder() {
        return new Builder();
    }

    public LumisResult highlight(String code) {
        return highlight(
                code.getBytes(UTF_8),
                theme.getValue().getBytes(UTF_8),
                lang.getValue().getBytes(UTF_8));
    }

    public LumisResult highlight(byte[] code) {
        return highlight(code, theme.getValue().getBytes(UTF_8), lang.getValue().getBytes(UTF_8));
    }

    public LumisResult highlight(byte[] code, byte[] theme, byte[] lang) {
        try {
            var codePtr = exports.wasmMalloc(code.length);
            exports.memory().write(codePtr, code);

            var themePtr = exports.wasmMalloc(theme.length);
            exports.memory().write(themePtr, theme);

            var langPtr = exports.wasmMalloc(lang.length);
            exports.memory().write(langPtr, lang);

            var resultPtr =
                    exports.highlight(
                            codePtr, code.length,
                            themePtr, theme.length,
                            langPtr, lang.length);

            var result = unpackResult(resultPtr);
            return new LumisResult(result);
        } catch (RuntimeException e) {
            return new LumisResult(e);
        }
    }

    private byte[] unpackResult(long packed) {
        var addr = (int) ((packed >>> 32) & 0xFFFFFFFFL);
        var len = (int) (packed & 0xFFFFFFFFL);
        var result = instance.memory().readBytes(addr, len);
        exports.wasmFree(addr);
        return result;
    }

    @Override
    public void close() {
        if (wasi != null) {
            wasi.close();
        }
    }

    public static final class Builder {
        private Theme theme = DEFAULT_THEME;
        private Lang lang = DEFAULT_LANG;

        private Builder() {}

        public Builder withTheme(String theme) {
            return withTheme(Theme.fromString(theme));
        }

        public Builder withTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder withLang(String lang) {
            return withLang(Lang.fromString(lang));
        }

        public Builder withLang(Lang lang) {
            this.lang = lang;
            return this;
        }

        public Lumis build() {
            return new Lumis(theme, lang);
        }
    }
}
