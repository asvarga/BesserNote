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
public class PaneGUI extends BaseGUI {
    
    PlacementGUI placement;
    ColorPicker cp;

    public PaneGUI(double spacing) {
        super(spacing);

        Text t = new Text("--- Pane GUI ---");

        placement = new PlacementGUI(spacing, true);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }

    @Override
    public Node getNode() {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
        return pane;
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
    }
    
    @Override
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
    
    @Override
    public void setSize(double x, double y) {
        placement.setSize(x, y);
    }
}
