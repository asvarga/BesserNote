/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import java.awt.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class TabPaneGUI extends BaseGUI {
    
    PlacementGUI placement;
    ColorPicker cp;

    public TabPaneGUI(double spacing) {
        super(spacing);

        Text t = new Text("--- TabPane GUI ---");

        placement = new PlacementGUI(spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }

    @Override
    public Node getNode() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
        pane.setPrefSize(100, 100);
        return pane;
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
    }
    
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
}
