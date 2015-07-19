package com.horan.eugene.imagestesting.Adapters;

public class LogItem {
    private static LogImages logs;

    public static void setLog(LogImages log) {
        logs = log;
    }

    public static LogImages getLog() {
        return logs;
    }
}

