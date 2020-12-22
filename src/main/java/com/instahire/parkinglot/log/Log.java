package com.instahire.parkinglot.log;

public class Log {
    private static Log log;

    private Log() {
    }

    public static Log getInstance() {
        if (log == null) {
            log = new Log();
        }
        return log;
    }

    public void writeMessage(String message) {
        // Can be written in file as well
        System.out.println(message);
    }
}
