package ms.aurora.aiocutter.action;

import ms.aurora.api.methods.Objects;
import ms.aurora.api.methods.Players;
import ms.aurora.api.methods.Walking;
import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Action;
import ms.aurora.api.wrappers.RSObject;

import static ms.aurora.aiocutter.AIOCutter.configuration;
import static ms.aurora.api.methods.filters.ObjectFilters.ID;

/**
 * The chopping action
 *
 * @author _override
 */
public class ChopAction extends Action {
    private int areaCount = 0;

    @Override
    public boolean activate() {
        return Players.getLocal().isIdle() && !Inventory.isFull();
    }

    @Override
    public int execute() {
        RSObject tree = Objects.get(ID(configuration.getSelectedTree().ids()));
        if (tree != null && configuration.getSkillArea().contains(tree.getLocation())) {
            if (!tree.applyAction("Chop down " + configuration.getSelectedTree().name())) {
                error("Failed to click tree");
            }
        } else {
            info("No tree of type " + configuration.getSelectedTree().name() + " to be found..");
            if (areaCount < 4) {
                areaCount++;
            } else {
                info("Walking to random location.");
                Walking.walkTo(configuration.getSkillArea().getRandomTile());
                areaCount = 0;
            }
        }
        return 1000;
    }
}
