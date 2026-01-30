package io.roastedroot.lumis4j.core;

import java.util.HashMap;
import java.util.Map;

public enum Lang {
    PLAINTEXT(""),
    //    ANGULAR("angular"),
    //    ASSEMBLY("asm"),
    //    ASTRO("astro"),
    //    BASH("bash"),
    //    C("c"),
    //    CADDY("caddy"),
    //    CLOJURE("clojure"),
    //    COMMENT("comment"),
    //    COMMON_LISP("commonlisp"),
    //    CPP("cpp"),
    //    CMAKE("cmake"),
    //    C_SHARP("csharp"),
    CSV("csv"),
    //    CSS("css"),
    //    DART("dart"),
    //    DIFF("diff"),
    //    DOCKERFILE("dockerfile"),
    //    EEX("eex"),
    //    EJS("ejs"),
    //    ERB("erb"),
    //    ELIXIR("elixir"),
    //    ELM("elm"),
    //    ERLANG("erlang"),
    //    FISH("fish"),
    //    F_SHARP("fsharp"),
    //    GLEAM("gleam"),
    //    GLIMMER("glimmer"),
    //    GO("go"),
    //    GRAPHQL("graphql"),
    //    HASKELL("haskell"),
    //    HCL("hcl"),
    //    HEX("heex"),
    //    HTML("html"),
    //    IEX("iex"),
    JAVA("java"),
    JAVASCRIPT("javascript"),
    //    JSON("json"),
    //    KOTLIN("kotlin"),
    //    LATEX("latex"),
    //    LIQUID("liquid"),
    //    LLVM("llvm"),
    //    LUA("lua"),
    //    OBJ_C("objc"),
    //    OCAML("ocaml"),
    //    OCAML_INTERFACE("ocaml_interface"),
    //    PERL("perl"),
    //    MAKE("make"),
    //    MARKDOWN("markdown"),
    //    MARKDOWN_INLINE("markdown_inline"),
    //    NIX("nix"),
    //    PHP("php"),
    //    POWERSHELL("powershell"),
    //    PROTO_BUF("protobuf"),
    //    PYTHON("python"),
    //    R("r"),
    //    REGEX("regex"),
    //    RUBY("ruby"),
    RUST("rust"),
//    SCALA("scala"),
//    SCSS("scss"),
//    SQL("sql"),
//    SURFACE("surface"),
//    SVELTE("svelte"),
//    SWIFT("swift"),
//    TOML("toml"),
//    TYPESCRIPT("typescript"),
//    TSX("tsx"),
//    TYPST("typst"),
//    VIM("vim"),
//    VUE("vue"),
//    XML("xml"),
//    YAML("yaml"),
//    ZIG("zig")
;

    private final String value;

    Lang(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, Lang> langs;

    static {
        langs = new HashMap<>();
        for (Lang lang : Lang.values()) {
            langs.put(lang.getValue(), lang);
        }
        // Add aliases
        langs.put("text", PLAINTEXT);
        langs.put("plaintext", PLAINTEXT);
        //        langs.put("assembly", ASSEMBLY);
        //        langs.put("c++", CPP);
        //        langs.put("c#", C_SHARP);
        //        langs.put("docker", DOCKERFILE);
        //        langs.put("f#", F_SHARP);
        //        langs.put("ember", GLIMMER);
        //        langs.put("handlebars", GLIMMER);
        //        langs.put("terraform", HCL);
        //        langs.put("jsx", JAVASCRIPT);
        //        langs.put("objective-c", OBJ_C);
        //        langs.put("viml", VIM);
        //        langs.put("vimscript", VIM);
    }

    public static Lang fromString(String lang) {
        if (!langs.containsKey(lang)) {
            throw new IllegalArgumentException("Lang '" + lang + "' not found.");
        }
        return langs.get(lang);
    }
}
