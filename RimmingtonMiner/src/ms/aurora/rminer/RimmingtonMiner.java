package ms.aurora.rminer;

import ms.aurora.api.script.Script;
import ms.aurora.api.script.ScriptManifest;
import ms.aurora.event.listeners.PaintListener;
import ms.aurora.rminer.gui.RMinerStage;
import ms.aurora.rminer.impl.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ms.aurora.api.util.Utilities.random;


/**
 * @author Rick
 */
@ScriptManifest(name = "RimmingtonMiner", author = "Rick", version = 1.0)
public class RimmingtonMiner extends Script implements PaintListener {
    public static volatile State state = State.INITIALIZING;
    public static AbstractMiningStrategy strategy;
    public static volatile boolean banking;

    @Override
    public void onStart() {
        RMinerStage stage = new RMinerStage();
        stage.show();
    }

    @Override
    public int tick() {
        if (state == State.INITIALIZING) {
            return 1000;
        } else if (state == State.CANCEL) {
            return -1;
        } else if (strategy != null && strategy.canExecute()) {
            if(strategy.isInitialized()) {
            strategy.execute();
            } else {
                strategy.initialize();
            }
        }
        return random(400, 1000);
    }

    @Override
    public void onRepaint(Graphics graphics) {
        graphics.setColor(Color.CYAN);
        graphics.drawString("Current state: " + state.name(), 10, 30);
        if (strategy != null) {
            graphics.drawString("Current strategy: " + strategy.getClass().getSimpleName(), 10, 50);

            if(strategy instanceof PaintListener) {
                ((PaintListener)strategy).onRepaint(graphics);
            }
        }
    }

    public static List<AbstractMiningStrategy> getAvailableStrategies() {
        List<AbstractMiningStrategy> strategies = new ArrayList<AbstractMiningStrategy>();
        strategies.add(new RimmingtonTin());
        strategies.add(new RimmingtonCopper());
        strategies.add(new RimmingtonClay());
        strategies.add(new RimmingtonIron());
        strategies.add(new RimmingtonGold());
        return strategies;
    }

    public static enum State {
        INITIALIZING, CANCEL, IDLE, MINING, WALKING, BANKING, DROPPING
    }
}
