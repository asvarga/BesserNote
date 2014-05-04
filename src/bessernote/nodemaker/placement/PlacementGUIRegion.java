/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker.placement;

import bessernote.nodemaker.BaseGUI;
import bessernote.ui.BNumberField;
import java.awt.geom.Point2D;
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
public class PlacementGUIRegion extends BaseGUI {
    
    PlacementGUI1D xGUI;
    PlacementGUI1D yGUI;
    
    public PlacementGUIRegion(Node top, double spacing) {
        
        super(top, spacing);
        
        Text t = new Text("--- Placement GUI ---");
        
        xGUI = new PlacementGUI1D(top, spacing, true);
        yGUI = new PlacementGUI1D(top, spacing, false);
        
        getChildren().addAll(t, xGUI, yGUI);
    }
    
    @Override
    public void editNode(Node n) {
        xGUI.editNode(n);
        yGUI.editNode(n);
    }
    
    @Override
    public void setPos(double x, double y) {
        xGUI.setPos(x, y);
        yGUI.setPos(x, y);
    }
    
    @Override
    public void setSize(double x, double y) {
        xGUI.setSize(x, y);
        yGUI.setSize(x, y);
    }
    
    public Point2D.Double getSize(){
        return new Point2D.Double(xGUI.getSize(), yGUI.getSize());
    }
    
    public Point2D.Double getCoord(){
        return new Point2D.Double(xGUI.getCoord(), yGUI.getCoord());
    }
}
