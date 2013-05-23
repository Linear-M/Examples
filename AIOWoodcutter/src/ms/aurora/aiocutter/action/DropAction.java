package ms.aurora.aiocutter.action;

import ms.aurora.api.methods.tabs.Inventory;
import ms.aurora.api.script.Action;

import static ms.aurora.aiocutter.AIOCutter.banking;
import static ms.aurora.aiocutter.AIOCutter.configuration;
import static ms.aurora.api.methods.filters.WidgetItemFilters.ID;
import static ms.aurora.api.methods.tabs.Inventory.dropAllByColumn;

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
        dropAllByColumn(ID(configuration.getSelectedTree().logs()));
        return 200;
    }

}
