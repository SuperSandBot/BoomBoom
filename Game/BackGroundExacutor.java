package Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class BackGroundExacutor {

    public final static ScheduledExecutorService Scheduler = Executors.newSingleThreadScheduledExecutor(); 
}
