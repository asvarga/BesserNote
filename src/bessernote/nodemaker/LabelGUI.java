/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class LabelGUI extends BaseGUI {
    
    public LabelGUI(double spacing) {
        super(spacing);
        
        Text t = new Text("--- Label GUI --- blahblahblah");
        
        PlacementGUI placement = new PlacementGUI(spacing);
        
        getChildren().addAll(t, placement);
    }
    
}
