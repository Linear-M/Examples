package ms.aurora.aiocutter.config;

import ms.aurora.api.wrappers.RSArea;

/**
 * @author _override
 */
public interface Configuration {

    public Tree[] getObjectIDs();

    public RSArea getBankArea();

    public RSArea getSkillArea();

    public Tree getSelectedTree();

    public void setSelectedTree(Tree tree);

}
