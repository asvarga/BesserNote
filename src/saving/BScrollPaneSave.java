/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BScrollPane;
import bessernote.ui.BWrapPane;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 * BScrollPaneSave is the model for a ScrollPane in the saved state.
 */
public class BScrollPaneSave implements Saveable {
    
    private double xPos, yPos;
    private double xDim, yDim;
    private BWrapPaneSave content;
    
    
    public BScrollPaneSave(BScrollPane scrollPane){
        //Save this
        xPos = scrollPane.getLayoutX();
        yPos = scrollPane.getLayoutY();
        xDim = scrollPane.getPrefWidth();
        yDim = scrollPane.getPrefHeight();
        //Save child
        if(scrollPane.getContent() != null)
            content = new BWrapPaneSave((BWrapPane)scrollPane.getContent());       
    }

    @Override
    public Parent create(BUndoManager undoManager) {
        BScrollPane returnMe = new BScrollPane(undoManager);
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.setPrefHeight(yDim);
        returnMe.setPrefWidth(xDim);
        returnMe.setContent(content.create(undoManager));
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return new ArrayList<Saveable>();
    }
    
}
