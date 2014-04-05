/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

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
    
    ColorPicker cp;
    
    public LabelGUI(double spacing) {
        super(spacing);
        
        Text t = new Text("--- Label GUI ---");
        
        PlacementGUI placement = new PlacementGUI(spacing);
        
        Text t2 = new Text("Text Color:");
        cp = new ColorPicker();
        
        getChildren().addAll(t, placement, t2, cp);
    }
    
    
    @Override
    public Node getNode() {
        Label label = new Label("LABEL!!!");
        label.setTextFill(cp.getValue());
        label.setFont(new Font("Arial", 30));
        return label;
    }
    
}
