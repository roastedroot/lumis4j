# Lumis4J

> **⚠️ WORK IN PROGRESS** - This project is currently under active development.

**Syntax Highlighter powered by Tree-sitter and Neovim themes - Java Edition**

**70+ languages. 120+ themes. Pure Java execution via WebAssembly.**

[![Maven Central](https://img.shields.io/maven-central/v/io.roastedroot/lumis4j)](https://central.sonatype.com/artifact/io.roastedroot/lumis4j)

---

## Features

* **70+ Tree-sitter languages** - Fast and accurate syntax parsing
* **120+ Neovim themes** - Updated and curated themes from the Neovim community
* **Multiple outputs** - HTML (inline/linked), Terminal (ANSI)
* **Language auto-detection** - File extension and shebang support
* **Pure Java execution** - Tree-sitter runs in Java via WebAssembly (WASM) using [Chicory](https://github.com/dylibso/chicory)
* **Zero config** - Works out of the box
* **No native dependencies** - Everything runs in the JVM

## Quick Start

### Maven

```xml
<dependency>
    <groupId>io.roastedroot</groupId>
    <artifactId>lumis4j</artifactId>
    <version>latest</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.roastedroot:lumis4j:0.1.0'
```

### Basic Usage

```java
import io.roastedroot.lumis4j.core.Lumis;
import io.roastedroot.lumis4j.core.Lang;
import io.roastedroot.lumis4j.core.Theme;

var lumis = Lumis.builder()
    .withLang(Lang.JAVASCRIPT)
    .withTheme(Theme.DRACULA)
    .build();

var result = lumis.highlight("console.log('Hello, World!');");
System.out.println(result.string());

lumis.close();
```

### HTML Inline Formatter

```java
import io.roastedroot.lumis4j.core.Lumis;
import io.roastedroot.lumis4j.core.Lang;
import io.roastedroot.lumis4j.core.Theme;
import io.roastedroot.lumis4j.core.Formatter;

var htmlLumis = Lumis.builder()
    .withLang(Lang.JAVA)
    .withTheme(Theme.CATPPUCCIN_FRAPPE)
    .withFormatter(Formatter.HTML_INLINE)
    .build();

var htmlResult = htmlLumis.highlight("public class Hello { }");
System.out.println(htmlResult.string());

htmlLumis.close();
```

### HTML Linked Formatter

```java
import io.roastedroot.lumis4j.core.Lumis;
import io.roastedroot.lumis4j.core.Lang;
import io.roastedroot.lumis4j.core.Formatter;

var linkedLumis = Lumis.builder()
    .withLang(Lang.RUST)
    .withFormatter(Formatter.HTML_LINKED)
    .build();

var linkedResult = linkedLumis.highlight("fn main() {}");
System.out.println(linkedResult.string());

linkedLumis.close();
```

## How It Works

Lumis4J brings the power of [lumis](https://github.com/leandrocp/lumis) to the Java ecosystem by:

1. **Compiling lumis to WebAssembly** - The Rust-based lumis library is compiled to WASM
2. **Running WASM in Java** - Using [Chicory](https://github.com/dylibso/chicory), a pure Java WebAssembly runtime
3. **No native dependencies** - Everything runs entirely within the JVM, no JNI or native libraries required

This approach gives you:
- ✅ Cross-platform compatibility (works anywhere Java runs)
- ✅ No native library management
- ✅ Fast execution (WASM is optimized and JIT-compiled)
- ✅ Memory safety (WASM's sandboxed execution model)

## Supported Languages

70+ languages including:
- JavaScript, TypeScript, TSX
- Java, Kotlin, Scala
- Rust, C, C++, C#
- Python, Ruby, Go
- HTML, CSS, JSON, YAML
- And many more...

See the [`Lang`](core/src/main/java/io/roastedroot/lumis4j/core/Lang.java) enum for the complete list.

## Supported Themes

120+ themes from popular Neovim colorschemes:
- GitHub Dark/Light
- Dracula
- Catppuccin (Mocha, Macchiato, Frappe, Latte)
- OneDark, OneLight
- Nord
- Gruvbox
- And many more...

See the [`Theme`](core/src/main/java/io/roastedroot/lumis4j/core/Theme.java) enum for the complete list.

## Formatters

Three output formats are supported:

| Formatter | Output | Use Case |
|-----------|--------|----------|
| `TERMINAL` | ANSI escape codes | CLI tools, terminal output, logs |
| `HTML_INLINE` | HTML with inline styles | Standalone HTML, email, no external CSS |
| `HTML_LINKED` | HTML with CSS classes | Multiple code blocks, custom styling |

## Building from Source

```bash
# Build the WASM module
cd wasm-build
make local

# Build the Java library
cd ..
mvn clean install
```

## Acknowledgements

This project is a Java wrapper of [lumis](https://github.com/leandrocp/lumis) by [leandrocp](https://github.com/leandrocp).

Special thanks to:
- **[lumis](https://github.com/leandrocp/lumis)** - The original Rust implementation that powers this library
- **[Chicory](https://github.com/dylibso/chicory)** - Pure Java WebAssembly runtime that makes this possible
- **Tree-sitter** - The parsing library that provides fast and accurate syntax highlighting
- **Neovim theme authors** - For the beautiful color schemes

## Contributing

Contributions welcome! Feel free to open issues or PRs for bugs, features, or improvements.
