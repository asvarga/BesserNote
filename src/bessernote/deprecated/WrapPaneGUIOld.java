/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.deprecated;

import bessernote.nodemaker.BaseGUI;
import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BNumberField;
import bessernote.ui.BWrapPane;
import java.awt.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author avarga
 */
public class WrapPaneGUIOld extends BaseGUI {
    
    PlacementGUIRegion placement;
    ColorPicker cp;
    ColorPicker cp2;
    BNumberField padding;

    public WrapPaneGUIOld(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- WrapPane GUI ---");

        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();
        
        Text t3 = new Text("Placeholder Color:");
        cp2 = new ColorPicker();
        
        HBox h = new HBox();
        h.getChildren().add(new Text("Padding:"));
        padding = new BNumberField("20");
        h.getChildren().add(padding);

        getChildren().addAll(t, placement, t2, cp, t3, cp2, padding);
    }

    @Override
    public Node getNode() {
        return new BWrapPaneOld();
    }
    
    @Override
    public void editNode(Node n) {
        //placement.editNode(n);
        BWrapPaneOld p = (BWrapPaneOld) n;
        //p.placeHolder.setPrefSize(p.getPrefWidth(), p.getPrefHeight());
        p.placeHolder.setPrefSize(padding.getNum()*10, padding.getNum()*10); 
        p.outline.setStyle("-fx-background-color: #"+cp.getValue().toString().substring(2) +";");
        p.placeHolder.setStyle("-fx-background-color: #"+cp2.getValue().toString().substring(2) +";");
        p.setStyle("-fx-background-color: #FFFF00;");
        p.setPadding(padding.getNum());
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
