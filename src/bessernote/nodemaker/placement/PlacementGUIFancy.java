/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker.placement;

import bessernote.nodemaker.BaseGUI;
import bessernote.ui.NumberField;
import java.text.ParseException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class PlacementGUIFancy extends BaseGUI {
    
    PlacementGUI1D xGUI;
    PlacementGUI1D yGUI;
    
    public PlacementGUIFancy(double spacing) {
        
        super(spacing);
        
        Text t = new Text("--- Placement GUI ---");
        
        xGUI = new PlacementGUI1D(spacing, true);
        yGUI = new PlacementGUI1D(spacing, false);
        
        getChildren().addAll(t, xGUI, yGUI);
    }
    
    public void editNode(Node n) {
        xGUI.editNode(n);
        yGUI.editNode(n);
    }
    
    public void setPos(double x, double y) {
        xGUI.setPos(x, y);
        yGUI.setPos(x, y);
    }
    
}
