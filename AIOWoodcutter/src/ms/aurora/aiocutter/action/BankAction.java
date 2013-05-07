package ms.aurora.aiocutter.action;

import ms.aurora.api.methods.Walking;
import ms.aurora.api.methods.tabs.Bank;
import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Action;

import static ms.aurora.aiocutter.AIOCutter.banking;
import static ms.aurora.aiocutter.AIOCutter.configuration;
import static ms.aurora.api.methods.Calculations.distance;
import static ms.aurora.api.methods.Players.getLocal;

/**
 * This action handles banking of logs.
 * It only runs when the following conditions are met:
 * - the player is idle
 * - the banking flag in AIOCutter is set
 * - the inventory is full.
 *
 * If the distance exceeds the max interaction distance, it will walk to the nearest tile
 * within the configured banking area. If it is within this distance, it will check whether the bank
 * is open, and if it isn't, it will open it. If it's open, it will deposit all the logs and then close
 * the bank. Continuation of the script then relies on another action to bring us back to the configured
 * interaction area.
 * @author _override
 */
public class BankAction extends Action {
    private static final int MAX_INTERACTION_DISTANCE = 8;

    @Override
    public boolean activate() {
        return banking && Inventory.isFull() && getLocal().isIdle();
    }

    @Override
    public int execute() {
        if(distance(configuration.getBankArea().getNearestTile(), getLocal().getLocation()) >= MAX_INTERACTION_DISTANCE) {
            Walking.walkTo(configuration.getBankArea().getRandomTile());
        } else if(!Bank.isOpen()) {
            Bank.open();
        } else {
            Bank.depositAll(configuration.getSelectedTree().logs());
            Bank.close();
        }
        return 1000;
    }
}
