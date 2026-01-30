package io.roastedroot.lumis4j.core;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void basicFunctionality() throws Exception {
        // Arrange
        var lumis = Lumis.builder().build();
        var code = readCode("base");
        var expectedResult = readResult("base");

        // Act
        var result = lumis.highlight(code);

        // Assert
        assertTrue(result.success());
        assertArrayEquals(expectedResult, result.bytes());
        // System.out.println(result.string());
    }
}
