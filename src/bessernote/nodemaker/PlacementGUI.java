/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.ui.NumberField;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class PlacementGUI extends BaseGUI {
    
    NumberField xNum;
    NumberField yNum;
    
    public PlacementGUI(double spacing) {
        
        super(spacing);
        
        Text t = new Text("--- Placement GUI ---");
        
        HBox h = new HBox();
        h.getChildren().add(new Text("X: "));
        xNum = new NumberField("0");
        h.getChildren().add(xNum);
        
        HBox h2 = new HBox();
        h2.getChildren().add(new Text("Y: "));
        yNum = new NumberField("0");
        h2.getChildren().add(yNum);
        
        getChildren().addAll(t, h, h2);
    }
    
    public void editNode(Node n) {
        try {
            n.setLayoutX(Double.parseDouble(xNum.getText()));
        } catch (Exception e) { 
        }
        try {
            n.setLayoutY(Double.parseDouble(yNum.getText()));
        } catch (Exception e) { 
        }
    }
    
}
