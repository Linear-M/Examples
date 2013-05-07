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
 * Mining strategy for gold, at the Rimmington mine.
 *
 * @author Rick
 */
public class RimmingtonGold extends AbstractMiningStrategy implements PaintListener {
    private static final RSTile BANK_LOCATION = new RSTile(3013, 3356);
    private static final RSTile MINING_SITE = new RSTile(2976, 3235);
    private static final int[] ROCK_ID = {9720, 9722};
    private static final int[] ORE_ID = {444};
    private volatile RSObject selectedObject = null;

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
            Walking.walkTo(MINING_SITE);
        }
    }

    /**
     * Handles the banking of ores.
     */
    private void doBanking() {
        if (distance(getLocal().getLocation(), BANK_LOCATION) > 4) {
            state = State.WALKING;
            Walking.walkTo(BANK_LOCATION);
            System.out.println("Walking to bank...");
        } else if (!Bank.isOpen()) {
            state = State.BANKING;
            Bank.open();
        } else {
            state = State.BANKING;
            Bank.depositAllExcept(PICKAXES);
            Walking.walkTo(MINING_SITE);
        }
    }


    @Override
    public String toString() {
        return "Gold mining at Rimmington mine.";
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
