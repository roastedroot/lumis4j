package io.roastedroot.lumis4j.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

/*
To update the results in approvals run with:
`APPROVAL_TESTS_USE_REPORTER=AutoApproveReporter`
*/
public class LumisTest {

    private static Options html() {
        return new Options().forFile().withExtension(".html");
    }

    private byte[] readCode(String fixture) throws Exception {
        return LumisTest.class.getResourceAsStream("/fixtures/" + fixture + "/code").readAllBytes();
    }

    // Poor man Sanity check: this should fail if we are not hitting the cache
    // without cache highlight takes seconds on the first iteration
    private long MAX_EXEC_TIME_MS = 500;

    private LumisResult highlight(Lumis lumis, byte[] code) {
        var before = System.nanoTime();
        var result = lumis.highlight(code);
        var duration = (System.nanoTime() - before) / 1_000_000;

        // System.out.println("elapsed: " + duration);
        assertTrue(duration < MAX_EXEC_TIME_MS, "Highlighting took " + duration + " ms");
        return result;
    }

    @Test
    public void basicUsage() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withLang(Lang.JAVASCRIPT).build();
        var code = readCode("js");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void changeTheme() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withLang(Lang.JAVASCRIPT).withTheme(Theme.BAMBOO_LIGHT).build();
        var code = readCode("bamboo");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void changeLang() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withLang(Lang.RUST).withTheme(Theme.AYU_MIRAGE).build();
        var code = readCode("rust");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void allDefaults() throws Exception {
        // Arrange
        var lumis = Lumis.builder().build();
        var code = readCode("default");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void csv() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withLang("csv").build();
        var code = readCode("csv");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void java() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withLang(Lang.JAVA).withTheme(Theme.AURA_SOFT_DARK).build();
        var code = readCode("java");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string());
    }

    @Test
    public void inlineHtmlFormatter() throws Exception {
        // Arrange
        var lumis =
                Lumis.builder()
                        .withLang(Lang.JAVA)
                        .withTheme(Theme.CATPPUCCIN_FRAPPE)
                        .withFormatter(Formatter.HTML_INLINE)
                        .build();
        var code = readCode("java");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string(), html());
    }

    @Test
    public void linkedHtmlFormatter() throws Exception {
        // Arrange
        var lumis =
                Lumis.builder()
                        .withLang(Lang.RUST)
                        .withTheme(Theme.CATPPUCCIN_FRAPPE)
                        .withFormatter(Formatter.HTML_LINKED)
                        .build();
        var code = readCode("rust");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        Approvals.verify(result.string(), html());
    }

    @Test
    public void readmeExampleJavaScriptDracula() throws Exception {
        // Generate HTML output for README example: JavaScript with Dracula theme
        var lumis =
                Lumis.builder()
                        .withLang(Lang.JAVASCRIPT)
                        .withTheme(Theme.DRACULA)
                        .withFormatter(Formatter.HTML_INLINE)
                        .build();

        var code = "console.log('Hello, World!');";
        var result = lumis.highlight(code);

        assertTrue(result.success());
        Approvals.verify(result.string(), html());
        lumis.close();
    }

    @Test
    public void readmeExampleJavaCatppuccin() throws Exception {
        // Generate HTML output for README example: Java with Catppuccin Frappe theme
        var lumis =
                Lumis.builder()
                        .withLang(Lang.JAVA)
                        .withTheme(Theme.CATPPUCCIN_FRAPPE)
                        .withFormatter(Formatter.HTML_INLINE)
                        .build();

        var code = "public class Hello { }";
        var result = lumis.highlight(code);

        assertTrue(result.success());
        Approvals.verify(result.string(), html());
        lumis.close();
    }
}
