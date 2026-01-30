package io.roastedroot.lumis4j.core;

import java.nio.charset.StandardCharsets;

public final class LumisResult {
    private final boolean success;
    private final byte[] stdout;
    private final byte[] stderr;

    private final byte[] result;

    LumisResult(boolean success, byte[] stdout, byte[] stderr, byte[] result) {
        this.success = success;
        this.stdout = stdout;
        this.stderr = stderr;
        this.result = result;
    }

    public byte[] stdout() {
        return stdout;
    }

    public byte[] stderr() {
        return stderr;
    }

    public byte[] result() {
        return result;
    }

    public boolean success() {
        return success;
    }

    public boolean failure() {
        return !success;
    }

    public void printStdout() {
        System.out.println(new String(stdout, StandardCharsets.UTF_8));
    }

    public void printStderr() {
        System.err.println(new String(stderr, StandardCharsets.UTF_8));
    }
}
