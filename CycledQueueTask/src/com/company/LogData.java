package com.company;

import java.time.LocalDateTime;

public class LogData {
    String data;
    String logType;
    LogData prev;
    LogData next;
    LocalDateTime logTime;

    @Override
    public String toString(){
        return logType+ " " + data + " " + logTime;
    }



    public LogData(String data, String type, LocalDateTime time){
        prev = null;
        next =null;
        this.data = data;
        logType = type;
        logTime = time;
    }

}


