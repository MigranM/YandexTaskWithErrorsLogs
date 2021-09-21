package com.company;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
		Random R = new Random();

		String[] types = new String[]{"INFO","ERROR","WARNING"};
		String[] data = new String[]{"some accident","oopsy","OK","404","Hello World"};


		ArrayList<LogData> logs = new ArrayList<>();
		for(int i=0; i < 25;i++){
			int minute = R.nextInt(5);
			int sec = R.nextInt(60);
			LocalDateTime logTime = LocalDateTime.of(2021, 9, 16, 14, 20+minute, sec);
			logs.add(new LogData(
					data[R.nextInt(data.length)],
					types[R.nextInt(types.length)],
					logTime
					));
		}
		logs.sort((x,y)->x.logTime.compareTo(y.logTime));
		ConsoleLogger logger = new ConsoleLogger();
		CycledQueue MyQueue = new CycledQueue(30,3,logger);
		for (var a: logs
			 ) {
			MyQueue.Enqeue(a);
		}

    }
}

