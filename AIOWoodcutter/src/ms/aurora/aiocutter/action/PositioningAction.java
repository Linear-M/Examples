package ms.aurora.aiocutter.action;

import ms.aurora.api.methods.Walking;
import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Action;
import ms.aurora.api.wrappers.RSTile;

import static ms.aurora.aiocutter.AIOCutter.configuration;
import static ms.aurora.api.methods.Players.getLocal;

/**
 * @author _override
 */
public class PositioningAction extends Action {
    @Override
    public boolean activate() {
        return !configuration.getSkillArea().contains(getLocal().getLocation()) && !Inventory.isFull();
    }

    @Override
    public int execute() {
        Walking.walkTo(new RSTile(3285, 3435));
        return 600;
    }
}
