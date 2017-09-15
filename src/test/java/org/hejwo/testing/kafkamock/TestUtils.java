package org.hejwo.testing.kafkamock;

import java.io.IOException;
import java.net.Socket;

public class TestUtils {

    public static boolean isPortAvailable(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

}
