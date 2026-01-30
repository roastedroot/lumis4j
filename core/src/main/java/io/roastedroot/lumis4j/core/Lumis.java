package io.roastedroot.lumis4j.core;

import com.dylibso.chicory.annotations.WasmModuleInterface;
import com.dylibso.chicory.runtime.Instance;
import com.dylibso.chicory.wasi.WasiPreview1;

@WasmModuleInterface(WasmResource.absoluteFile)
public final class Lumis implements AutoCloseable {
    private static final Theme DEFAULT_THEME = Theme.GITHUB_DARK;

    private final Theme theme;

    // wasm fields
    private final Instance instance;
    private final WasiPreview1 wasi;

    private Lumis(Theme theme) {
        this.theme = theme;
        this.instance = null;
        this.wasi = null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public LumisResult highlight(String code) {
        return new LumisResult(true, new byte[] {}, new byte[] {}, new byte[] {});
    }

    @Override
    public void close() {
        if (wasi != null) {
            wasi.close();
        }
    }

    public static final class Builder {
        private Theme theme = DEFAULT_THEME;

        private Builder() {}

        public Builder withTheme(String theme) {
            return withTheme(Theme.fromString(theme));
        }

        public Builder withTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Lumis build() {
            return new Lumis(theme);
        }
    }
}
