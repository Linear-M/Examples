import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ms.aurora.api.wrappers.RSTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * @author rvbiljouw
 */
public class MapperGUI extends AnchorPane {
    private Stage myStage;
    private BufferedImage osrsMap;
    private Point origin;

    private ImageView imageView = new ImageView();
    private ScrollPane scrollPane = new ScrollPane();
    private Button refreshBtn = new Button("Refresh");

    public MapperGUI() {
        setMinWidth(800);
        setMinHeight(600);
        try {
            osrsMap = ImageIO.read(new URL("http://i.imm.io/14fLH.jpeg"));
            origin = new Point(osrsMap.getWidth() / 2, osrsMap.getHeight() / 2);
            imageView.setImage(SwingFXUtils.toFXImage(osrsMap, null));
            scrollPane.setPrefSize(800, 600);
            scrollPane.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setContent(imageView);

            VBox rootBox = new VBox();
            rootBox.getChildren().addAll(refreshBtn, scrollPane);
            getChildren().add(rootBox);
            refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Graphics g = osrsMap.getGraphics();
                    g.setColor(Color.GREEN);
                    for(TestScript.RegionIdentifier i : TestScript.mappedRegions) {
                        RSTile nw = new RSTile(i.baseX, i.baseY);
                        Point pnw = tileToScreen(nw);
                        System.out.println(pnw);
                        g.drawRect(pnw.x, pnw.y, 104, 104);
                    }

                    imageView.setImage(SwingFXUtils.toFXImage(osrsMap, null));
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        myStage = new Stage();
        myStage.setTitle("Mapper GUI");
        myStage.setScene(new Scene(this));
        myStage.centerOnScreen();
        myStage.show();
    }

    public void close() {
        myStage.close();
    }

    private Point tileToScreen(RSTile tile) {
        int x = 128 + (int) ((tile.getX() - 2048) / 0.5);
        int y = osrsMap.getHeight()
                - (int) (128 + (tile.getY() - 2495) / 0.5);
        return new Point(x - origin.x, y - origin.y);
    }

}
