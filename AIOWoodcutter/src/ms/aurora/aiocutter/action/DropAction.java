package ms.aurora.aiocutter.action;

import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Action;

import static ms.aurora.aiocutter.AIOCutter.banking;
import static ms.aurora.aiocutter.AIOCutter.configuration;

/**
 * @author _override
 */
public class DropAction extends Action {

    @Override
    public boolean activate() {
        return !banking && Inventory.isFull();
    }

    @Override
    public int execute() {
        Inventory.dropAllByColumn(configuration.getSelectedTree().logs());
        return 200;
    }

}
