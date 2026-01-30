package io.roastedroot.lumis4j.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class LumisTest {

    private byte[] readCode(String fixture) throws Exception {
        return LumisTest.class.getResourceAsStream("/fixtures/" + fixture + "/code").readAllBytes();
    }

    private byte[] readResult(String fixture) throws Exception {
        return LumisTest.class
                .getResourceAsStream("/fixtures/" + fixture + "/output")
                .readAllBytes();
    }

    // Poor man Sanity check we are hitting the cache
    private long MAX_EXEC_TIME_MS = 500;

    private LumisResult highlight(Lumis lumis, byte[] code) {
        // TODO: switch to nanoTime for guarantees
        var before = System.currentTimeMillis();
        var result = lumis.highlight(code);
        var duration = System.currentTimeMillis() - before;

        System.out.println("elapsed: " + duration);
        assertTrue(duration < MAX_EXEC_TIME_MS, "Highlighting took " + duration + " ms");
        return result;
    }

    // use this function only to easily store tests results, always manually the result!
    private void writeOutput(String fixture, LumisResult result) throws Exception {
        Files.write(Path.of("src/test/resources/fixtures/" + fixture + "/output"), result.bytes());
    }

    @Test
    public void basicFunctionality() throws Exception {
        // Arrange
        var lumis = Lumis.builder().build();
        var code = readCode("base");
        var expectedResult = readResult("base");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        assertArrayEquals(expectedResult, result.bytes());
        // System.out.println(result.string());
    }

    @Test
    public void changingThemeFunctionality() throws Exception {
        // Arrange
        var lumis = Lumis.builder().withTheme(Theme.BAMBOO_LIGHT).build();
        var code = readCode("bamboo");
        var expectedResult = readResult("bamboo");

        // Act
        var result = highlight(lumis, code);

        // Assert
        assertTrue(result.success());
        assertArrayEquals(expectedResult, result.bytes());
        // System.out.println(result.string());
    }
}
