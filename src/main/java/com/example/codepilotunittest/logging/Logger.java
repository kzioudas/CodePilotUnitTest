package com.example.codepilotunittest.logging;

import java.util.logging.Level;
import java.util.logging.Logger as JavaLogger;

public class Logger {
    // Use the Java built-in Logger
    private static final JavaLogger LOGGER = JavaLogger.getLogger(Logger.class.getName());

    // Method to log generic information
    public static void info(String message) {
        LOGGER.log(Level.INFO, message);
    }

    // Method to log errors
    public static void error(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    // Method to log debug information
    public static void debug(String message) {
        LOGGER.log(Level.FINE, message);
    }

    // Example usage methods that might correspond to your use cases
    public void logMethodProvided(String method) {
        info("Method provided: " + method);
    }

    public void logDirectivesProvided(java.util.List<String> directives) {
        info("Directives provided: " + directives.toString());
    }

    public void logTestCasesGenerated(com.example.codepilotunittest.core.TestCases testCases) {
        info("Test cases generated: " + testCases.toString());
    }

    public void logJUnitTestsGenerated(java.util.List<com.example.codepilotunittest.core.JUnitTest> jUnitTests) {
        info("JUnit tests generated: " + jUnitTests.toString());
    }
}
