/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BImage;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class ImageGUI extends BaseGUI{
   
    PlacementGUIRegion placement;
    
    public ImageGUI(Node top, double spacing) {
        super(top, spacing);
        
        Text t = new Text("--- Image GUI ---");

        placement = new PlacementGUIRegion(top, spacing);
        
        getChildren().addAll(t, placement);
    }
    
    @Override
    public Node getNode(BUndoManager undoManager) {
        BImage returnMe =  new BImage(undoManager);
        returnMe.setPrefWidth(placement.getSize().getX());
        returnMe.setPrefHeight(placement.getSize().getY());
        returnMe.setLayoutX(placement.getCoord().getX());
        returnMe.setLayoutY(placement.getCoord().getY());
        returnMe.createImage();
        return returnMe;
    }
    
    @Override
    public void editNode(Node n) {
        //placement.editNode(n);
        
        //n.setStyle("-fx-background-color: #"+color);
//        Pane p = (Pane) n;
//        p.setMinSize(p.getPrefWidth(), p.getPrefHeight());
//        p.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        //n.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2, cp.getValue().toString().length()-2));
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
