/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
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
    
    PlacementGUI placement;
    
    boolean equalWidth;

    public HBoxGUI(double spacing) {
        super(spacing);
        
        Text t = new Text("--- HBox GUI ---");        
        placement = new PlacementGUI(spacing);
        
        //Text t2 = new Text("Width Control:");
        
        getChildren().addAll(t, placement);
    }

    @Override
    public Node getNode() {
        HBox hbox = new HBox();
        hbox.setPrefWidth(300);
        hbox.setPrefHeight(300);
        hbox.setStyle("-fx-background-color: red;");
        
        Pane p1 = new Pane();
        p1.setStyle("-fx-background-color: green;");
        HBox.setHgrow(p1, Priority.ALWAYS);
        Pane p2 = new Pane();
        p2.setStyle("-fx-background-color: blue;");
        HBox.setHgrow(p2, Priority.ALWAYS);
        hbox.getChildren().addAll(p1, p2);
        
        //HBox hbox = new HBox();
        
        
        return hbox;
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
    }
    
    public void setPos(double x, double y) {
        placement.setPos(x, y);
    }
    
}
