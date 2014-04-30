/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.ui.BLabel;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class LabelGUI extends BaseGUI {
    
    PlacementGUI placement;
    ColorPicker cp;

    public LabelGUI(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- Label GUI ---");        
        placement = new PlacementGUI(top, spacing);
        
        Text t2 = new Text("Text Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }

    @Override
    public Node getNode() {
        BLabel label = new BLabel("LABEL!!!");
        
        return label;
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
        ((BLabel) n).setTextFill(cp.getValue());
        ((BLabel) n).setFont(new Font("Arial", 30));
    }
    
    @Override
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
    
}
