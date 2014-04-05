/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class PlacementGUI extends BaseGUI {
    
    public PlacementGUI(double spacing) {
        
        super(spacing); 
        
        Text t = new Text("--- Placement GUI ---\nAdd shit here.");
        
        getChildren().addAll(t);
        
    }
    
}
