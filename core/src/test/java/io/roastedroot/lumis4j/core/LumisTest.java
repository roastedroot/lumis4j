package io.roastedroot.lumis4j.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

/*
To update the results in approvals run with:
`APPROVAL_TESTS_USE_REPORTER=AutoApproveReporter`
*/
public class LumisTest {

    private byte[] readCode(String fixture) throws Exception {
        return LumisTest.class.getResourceAsStream("/fixtures/" + fixture + "/code").readAllBytes();
    }

    // Poor man Sanity check: this should fail if we are not hitting the cache
    private long MAX_EXEC_TIME_MS = 200;

    private LumisResult highlight(Lumis lumis, byte[] code) {
        var before = System.nanoTime();
        var result = lumis.highlight(code);
        var duration = (System.nanoTime() - before) / 1_000_000;

        System.out.println("elapsed: " + duration);
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
    //
    //    @Test
    //    public void allDefaults() throws Exception {
    //        // Arrange
    //        var lumis = Lumis.builder().build();
    //        var code = readCode("default");
    //        var expectedResult = readResult("default");
    //
    //        // Act
    //        var result = highlight(lumis, code);
    //
    //        // Assert
    //        assertTrue(result.success());
    //        assertArrayEquals(expectedResult, result.bytes());
    //    }

    //    @Test
    //    public void unavailableLang() throws Exception {
    //        // Arrange
    //        var lumis = Lumis.builder().withLang("csv").build();
    //        var code = readCode("csv");
    //        // var expectedResult = readResult("rust");
    //
    //        // Act
    //        var result = highlight(lumis, code);
    //
    //        // Assert
    //        assertTrue(result.success());
    //        System.out.println(result.string());
    //        // assertArrayEquals(expectedResult, result.bytes());
    //    }
}
