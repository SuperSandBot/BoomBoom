package application;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BackGroundExacutor {

    public final static ScheduledExecutorService Scheduler = Executors.newSingleThreadScheduledExecutor(); 
    public final static Random randomnum = new Random();
}
