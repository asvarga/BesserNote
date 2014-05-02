/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.ChildSpecifier;
import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BTabPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class TabPaneGUI extends BaseGUI {
    
    PlacementGUIRegion placement;
    ColorPicker cp;
    
    public TabPaneGUI(Node top, double spacing){
        super(top, spacing);
        
        Text t = new Text("--- TabPane GUI ---");
        
        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();

        getChildren().addAll(t, placement, t2, cp);
    }
    
    @Override
    public Node getNode(BUndoManager undoManager) {
        BTabPane pane = new BTabPane(undoManager);
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
