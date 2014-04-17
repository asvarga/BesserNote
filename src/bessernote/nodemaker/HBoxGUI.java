/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BIntegerField;
import bessernote.ui.BNumberField;
import java.util.HashSet;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class HBoxGUI extends BaseGUI {
    
    PlacementGUIRegion placement;
    BIntegerField integer;
    ColorPicker cp;
    
    public HBoxGUI(Node top, double spacing) {
        super(top, spacing);
        
        Text t = new Text("--- Row GUI ---");        
        placement = new PlacementGUIRegion(top, spacing);
        
        Text t2 = new Text("Parts:");
        integer = new BIntegerField("2");
        
        Text t3 = new Text("Border Color:");
        cp = new ColorPicker();
        
        //Text t2 = new Text("Width Control:");
        
        getChildren().addAll(t, placement, t2, integer, t3, cp);
    }

    @Override
    public Node getNode() {
        return new HBox();
    }
    
    @Override
    public void editNode(Node n) {
        int num = integer.getInt();
        for (int i=0; i<num; i++) {
            Pane p = new Pane();
            p.setStyle("-fx-border-color: #"+cp.getValue().toString().substring(2)+";"
                    + "-fx-stroke-style: centered;");
            HBox.setHgrow(p, Priority.ALWAYS);
            ((Pane) n).getChildren().add(p);
        } 
        placement.editNode(n);
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
