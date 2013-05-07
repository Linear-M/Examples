package ms.aurora.rminer.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import ms.aurora.rminer.AbstractMiningStrategy;
import ms.aurora.rminer.RimmingtonMiner;

import java.io.IOException;

import static ms.aurora.rminer.RimmingtonMiner.State.CANCEL;
import static ms.aurora.rminer.RimmingtonMiner.State.IDLE;

/**
 * @author Rick
 */
public class RMinerUI extends AnchorPane {

    @FXML
    private ComboBox<AbstractMiningStrategy> cmbStrategies;

    public RMinerUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RMinerUI.fxml"));

        //fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            this.getChildren().add((Parent) fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void onOK(ActionEvent evt) {
        RimmingtonMiner.strategy = cmbStrategies.getSelectionModel().getSelectedItem();
        RimmingtonMiner.banking = false;
        RimmingtonMiner.state = IDLE;
        getScene().getWindow().hide();
    }

    @FXML
    public void onCancel(ActionEvent evt) {
        RimmingtonMiner.state = CANCEL;
        getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
        cmbStrategies.getItems().clear();
        for(AbstractMiningStrategy strategy : RimmingtonMiner.getAvailableStrategies()) {
            cmbStrategies.getItems().add(strategy);
        }
    }

}
