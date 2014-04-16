/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.ui.BTabPane;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author ddliu
 */
public class BTabPaneGUI extends BaseGUI{
    
    PlacementGUI placement;
    ColorPicker cp;
    
    public BTabPaneGUI(double spacing){
        super(spacing);
        
        Text t = new Text("--- TabPane GUI ---");
        
        placement = new PlacementGUI(spacing, true);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }
    
    @Override
    public Node getNode() {
        BTabPane pane = new BTabPane();
        pane.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
        return pane;
    }
    
    @Override
    public void editNode(Node n) {
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
