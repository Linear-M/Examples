package ms.aurora.rminer.impl;

import ms.aurora.api.methods.Objects;
import ms.aurora.api.methods.Players;
import ms.aurora.api.methods.Walking;
import ms.aurora.api.methods.tabs.Bank;
import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.wrappers.RSModel;
import ms.aurora.api.wrappers.RSObject;
import ms.aurora.api.wrappers.RSPlayer;
import ms.aurora.api.wrappers.RSTile;
import ms.aurora.event.listeners.PaintListener;
import ms.aurora.rminer.AbstractMiningStrategy;

import java.awt.*;

import static ms.aurora.api.methods.Calculations.distance;
import static ms.aurora.api.methods.filters.ObjectFilters.ID;
import static ms.aurora.api.util.Utilities.sleepNoException;
import static ms.aurora.rminer.RimmingtonMiner.*;

/**
 * Mining strategy for copper, at the Rimmington mine.
 *
 * @author Rick
 */
public class RimmingtonCopper extends AbstractMiningStrategy implements PaintListener {
    private static final RSTile BANK_LOCATION = new RSTile(3013, 3356);
    private static final RSTile MINING_SITE = new RSTile(2978, 3246);

    private static final RSTile[] BANK_PATH = new RSTile[]{
            new RSTile(2977, 3240), new RSTile(2977, 3249), new RSTile(2975, 3262),
            new RSTile(2980, 3272), new RSTile(2990, 3281), new RSTile(2994, 3294),
            new RSTile(3004, 3300), new RSTile(3005, 3310), new RSTile(3007, 3318),
            new RSTile(3006, 3329), new RSTile(3006, 3341), new RSTile(3006, 3351),
            new RSTile(3012, 3355),
    };

    private static final int[] ROCK_ID = {9710, 9709, 9708};
    private static final int[] ORE_ID = {436};
    private volatile RSObject selectedObject = null;

    @Override
    public int getMinimumLevel() {
        return 1;
    }

    @Override
    public void prepare() {

    }

    @Override
    public boolean canExecute() {
        RSPlayer local = Players.getLocal();
        return local.getAnimation() == -1 && !local.isMoving();
    }

    @Override
    public void execute() {
        selectedObject = Objects.get(ID(ROCK_ID));

        if (Inventory.isFull() && !banking) {
            state = State.DROPPING;
            Inventory.dropAll(ORE_ID);
        } else if (Inventory.isFull() && banking) {
            doBanking();
        } else if (selectedObject != null) {
            selectedObject.applyAction("Mine");
            selectedObject = null;
            state = State.MINING;
            sleepNoException(400, 500);
        } else if (distance(getLocal().getLocation(), MINING_SITE) > 14) {
            state = State.WALKING;
            Walking.traverse(BANK_PATH, Walking.BACKWARDS);
        }
    }

    /**
     * Handles the banking of ores.
     */
    private void doBanking() {
        if (distance(getLocal().getLocation(), BANK_LOCATION) > 4) {
            state = State.WALKING;
            Walking.traverse(BANK_PATH, Walking.FORWARDS);
            System.out.println("Walking to bank...");
        } else if (!Bank.isOpen()) {
            state = State.BANKING;
            Bank.open();
        } else {
            state = State.BANKING;
            Inventory.InventoryItem[] items = Inventory.getAll(ORE_ID);
            for (Inventory.InventoryItem item : items) {
                if (item.applyAction("Store All")) {
                    sleepNoException(300, 400);
                    break;
                }
            }
            Walking.traverse(BANK_PATH, Walking.BACKWARDS);
        }
    }

    @Override
    public String toString() {
        return "Copper mining at Rimmington mine.";
    }

    @Override
    public void onRepaint(Graphics graphics) {
        try {
            if (selectedObject != null) {
                RSModel model = selectedObject.getModel();
                graphics.setColor(Color.CYAN);
                graphics.drawPolygon(model.getHull());
            }
        } catch (Exception e) {

        }
    }
}
