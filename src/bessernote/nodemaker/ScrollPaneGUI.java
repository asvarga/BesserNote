/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BNumberField;
import bessernote.ui.BScrollPane;
import java.awt.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class ScrollPaneGUI extends BaseGUI {
    
    PlacementGUIRegion placement;
    ColorPicker cp;
    BNumberField padding;

    public ScrollPaneGUI(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- Pane GUI ---");

        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();
        
        HBox h = new HBox();
        h.getChildren().add(new Text("Padding:"));
        padding = new BNumberField("20");
        h.getChildren().add(padding);

        getChildren().addAll(t, placement, t2, cp, h);
    }

    @Override
    public Node getNode() {
        return new BScrollPane();
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
        ((BScrollPane) n).getContent().setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2, cp.getValue().toString().length()-2));
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
