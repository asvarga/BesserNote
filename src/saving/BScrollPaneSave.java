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

/**
 *
 * @author ddliu
 * BScrollPaneSave is the model for a ScrollPane in the saved state.
 */
public class BScrollPaneSave implements Savable{
    
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
    public Parent create() {
        BScrollPane returnMe = new BScrollPane();
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.setPrefHeight(xDim);
        returnMe.setPrefWidth(yDim);
        returnMe.setContent(content.create());
        return returnMe;
    }

    @Override
    public List<Savable> getChildren() {
        return new ArrayList<Savable>();
    }
    
}
