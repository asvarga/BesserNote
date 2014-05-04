/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUI;
import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BFlashCard;
import bessernote.ui.BNumberField;
import bessernote.ui.BWrapPane;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class FlashCardGUI2 extends BaseGUI {
    
    PlacementGUI placement;
    ColorPicker cp;
    ColorPicker cp2;
    BNumberField padding;

    public FlashCardGUI2(Node top, double spacing) {
        super(top, spacing);

        Text t = new Text("--- FlashCard GUI ---");
        
        // for now:
        //placement = new PlacementGUIRegion(top, spacing);
        placement = new PlacementGUI(top, spacing, true);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();
        
        Text t3 = new Text("Front Color:");
        cp2 = new ColorPicker();
        cp2.setValue(Color.GRAY);
        
        HBox h = new HBox();
        h.getChildren().add(new Text("Padding:"));
        padding = new BNumberField("20");
        h.getChildren().add(padding);

        getChildren().addAll(t, placement, t2, cp, t3, cp2, padding);
    }

    @Override
    public Node getNode(BUndoManager undoManager) {
        return new BFlashCard(undoManager);
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
        BFlashCard p = (BFlashCard) n;
        double pad = padding.getNum();
        p.setPrefMinSize(p.getPrefWidth(), p.getPrefHeight());
        p.setPadding(pad);
        p.setStyle("-fx-background-color: #"+color);

        p.placeHolder.setStyle("-fx-background-color: #"+cp2.getValue().toString().substring(2, cp2.getValue().toString().length()-2));
        
        
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
