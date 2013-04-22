package ms.aurora.rminer;

import ms.aurora.api.methods.Players;
import ms.aurora.api.wrappers.RSPlayer;

/**
 * @author Rick
 */
public abstract class AbstractMiningStrategy {
    private boolean initialized;

    public abstract void prepare();

    public abstract boolean canExecute();

    public abstract void execute();

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
