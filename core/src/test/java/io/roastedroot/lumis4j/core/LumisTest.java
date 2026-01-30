package io.roastedroot.lumis4j.core;

import org.junit.jupiter.api.Test;

public class LumisTest {

    @Test
    public void basicFunctionality() {
        // Arrange
        var lumis = Lumis.builder().build();
        var jsCode = "function greet(name) {\n" + "    console.log(`Hello ${name}!`);\n" + "}";

        // Act
        var result = lumis.highlight(jsCode);

        // Assert
        System.out.println(result);
    }
}
