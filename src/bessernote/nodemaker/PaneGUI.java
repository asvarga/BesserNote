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
public class PaneGUI extends BaseGUI {
    
    public PaneGUI(double spacing) {
        super(spacing);
        
        Text t = new Text("--- Pane GUI --- blahblahblahblahblah");
        
        PlacementGUI placement = new PlacementGUI(spacing);
        
        getChildren().addAll(t, placement);
    }
    
}
