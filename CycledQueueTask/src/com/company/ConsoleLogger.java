package com.company;

public class ConsoleLogger implements ILogger {


    @Override
    public void Log(String logData) {
        System.out.println(logData);
    }
}
