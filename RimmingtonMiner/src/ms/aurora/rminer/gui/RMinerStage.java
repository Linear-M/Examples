package ms.aurora.rminer.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Rick
 */
public class RMinerStage extends Stage {

    public RMinerStage() {
        setTitle("Select a strategy");
        setMinWidth(500);
        setMinHeight(200);
        setResizable(false);
        setScene(new Scene(new RMinerUI()));
        centerOnScreen();
        show();
    }

}
