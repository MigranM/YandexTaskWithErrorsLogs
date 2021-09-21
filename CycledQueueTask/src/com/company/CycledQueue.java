package com.company;
import java.time.Duration;
import java.util.Iterator;
import java.util.Objects;

public class CycledQueue implements Iterable<LogData> {
    private LogData oldest;
    private LogData newest;
    private int count;
    public int GetCount(){return count;}
    private final int maxErrors;
    private final long TimeInterval;
    private ILogger logger;

    public CycledQueue(long interval, int maxErrors, ILogger logger){
        count = 0;
        this.logger = logger;
        this.maxErrors = maxErrors;
        oldest = null;
        newest = null;
        TimeInterval = interval;
    }
    public void ChangeLogger(ILogger newLogger){
        logger = newLogger;
    }
    private void Add(LogData log){
        switch (count) {
            case 0 -> {
                oldest = log;
                newest = log;
                count++;
            }
            case 1 -> {
                oldest.next = log;
                log.prev = oldest;
                newest = log;
                count++;
            }
            default -> {
                newest.next = log;
                log.prev = newest;
                newest = log;
                count++;
            }
        }


        if(CountErrors()>=maxErrors) {
            logger.Log(log.toString());
        }


    }
    private int CountErrors(){
        int errorsCount=0;
        for(var log : this) {
            if(Objects.equals(log.logType, "ERROR")) errorsCount++;
        }
        return errorsCount;
    }

    public void Enqeue(LogData log)
    {

        if(count==0) {
            Add(log);
        }
        else{
            long interval = Duration.between(oldest.logTime,log.logTime).getSeconds();
            if(interval<TimeInterval){
                Add(log);
            }
            else {
                Remove();
                Enqeue(log);
            }
    }


    }
    private void Remove()
    {
        if(count>1) {
            oldest = oldest.next;
            oldest.prev = null;
            count--;
        }
        else {
            oldest = null;
            newest = null;
            count--;
        }

    }


    @Override
    public Iterator<LogData> iterator() {
        return new CycledQueueEnumerator(oldest);
    }
}
class CycledQueueEnumerator implements  Iterator<LogData>
{
    LogData current;

    public CycledQueueEnumerator(LogData oldestLog){
        current = oldestLog;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public LogData next() {
        LogData tmp = current;
        current = current.next;
        return  tmp;
    }
}
