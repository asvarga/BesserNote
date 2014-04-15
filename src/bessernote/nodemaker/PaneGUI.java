/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

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
        placement.editNode(pane);
        return pane;
    }
}
