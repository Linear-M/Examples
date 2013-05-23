package ms.aurora.aiocutter.config.impl;

import ms.aurora.aiocutter.config.Configuration;
import ms.aurora.aiocutter.config.Tree;
import ms.aurora.api.wrappers.RSArea;
import ms.aurora.api.wrappers.RSTile;

/**
 * A set of constants specifically for the eastern area of varrock.
 */
public class EastVarrockConfiguration implements Configuration {
    private static final RSArea BANK_AREA = new RSArea(new RSTile(3254, 3422), new RSTile(3251, 3420));
    private static final RSArea TREE_AREA = new RSArea(new RSTile(3270, 3428), new RSTile(3290, 3483));
    private static final Tree[] TREES = { Tree.TREE };


    private Tree selectedTree = Tree.TREE;

    @Override
    public Tree[] getObjectIDs() {
        return TREES;
    }

    @Override
    public RSArea getBankArea() {
        return BANK_AREA;
    }

    @Override
    public RSArea getSkillArea() {
        return TREE_AREA;
    }

    @Override
    public Tree getSelectedTree() {
        return selectedTree;
    }

    @Override
    public void setSelectedTree(Tree tree) {
        selectedTree = tree;
    }

}
