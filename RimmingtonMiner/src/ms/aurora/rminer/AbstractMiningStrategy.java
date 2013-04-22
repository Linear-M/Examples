package ms.aurora.rminer;

import ms.aurora.api.methods.Players;
import ms.aurora.api.wrappers.RSPlayer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Rick
 */
public abstract class AbstractMiningStrategy {
    private Timer timer = new Timer();
    private boolean initialized;

    public abstract int getMinimumLevel();

    public abstract void prepare();

    public abstract boolean canExecute();

    public abstract void execute();

    public void registerTask(TimerTask task) {
        timer.schedule(task, (long)100, (long)100);
    }

    public void cancelTasks() {
        timer.cancel();
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
        prepare();
        initialized = true;
    }

    /**
     * Shorter function for obtaining the local player.
     *
     * @return local player
     */
    protected static RSPlayer getLocal() {
        return Players.getLocal();
    }

}
