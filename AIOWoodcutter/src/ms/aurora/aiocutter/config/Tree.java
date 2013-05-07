package ms.aurora.aiocutter.config;

/**
 * @author _override
 */
public enum Tree {
    TREE(1511, 1276, 1278),
    WILLOW(1519, 5553, 5552, 5551, 1308);

    private int[] ids;
    private int logs;

    Tree(int logs, int... ids) {
        this.logs = logs;
        this.ids = ids;
    }

    public int logs() {
        return logs;
    }

    public int[] ids() {
        return ids;
    }
}
