/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
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
    
    PlacementGUIRegion placement;
    ColorPicker cp;

    public PaneGUI(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- Pane GUI ---");

        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }

    @Override
    public Node getNode() {
        return new Pane();
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
        n.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
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
