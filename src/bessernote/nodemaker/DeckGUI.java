/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker;

import bessernote.nodemaker.placement.PlacementGUIRegion;
import bessernote.ui.BDeck;
import bessernote.ui.BNumberField;
import bessernote.ui.BWrapPane;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public class DeckGUI extends BaseGUI {
    
    PlacementGUIRegion placement;
    ColorPicker cp;
    BNumberField padding;

    public DeckGUI(Node top, double spacing) {
        super(top);
        
        Text t = new Text("--- Deck GUI ---");

        placement = new PlacementGUIRegion(top, spacing);

        Text t2 = new Text("Background Color:");
        cp = new ColorPicker();
        
        HBox h = new HBox();
        h.getChildren().add(new Text("Padding:"));
        padding = new BNumberField("20");
        h.getChildren().add(padding);

        getChildren().addAll(t, placement, t2, cp, padding);
    }
    
    @Override
    public Node getNode(BUndoManager undoManager) {
        return new BDeck(undoManager);
    }
    
    @Override
    public void editNode(Node n) {
        placement.editNode(n);
        BDeck p = (BDeck) n;
        double pad = padding.getNum();
        p.setPrefMinSize(p.getPrefWidth(), p.getPrefHeight());
        p.setPadding(pad);
        p.setStyle("-fx-background-color: #"+ color);    
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
