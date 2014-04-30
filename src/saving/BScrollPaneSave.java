/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BScrollPane;
import bessernote.ui.BWrapPane;
import java.io.Serializable;
import javafx.scene.Node;

/**
 *
 * @author ddliu
 * BScrollPaneSave is the model for a ScrollPane in the saved state.
 */
public class BScrollPaneSave implements Savable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private BWrapPaneSave content;
    private String color;
    
    
    public BScrollPaneSave(BScrollPane scrollPane){
        //Save this
        xPos = scrollPane.getLayoutX();
        yPos = scrollPane.getLayoutY();
        xDim = scrollPane.getPrefWidth();
        yDim = scrollPane.getPrefHeight();
        color = scrollPane.getStyle().substring(scrollPane.getStyle().indexOf("#"), scrollPane.getStyle().length());
                
        //Save child
        if(scrollPane.getContent() != null)
            content = new BWrapPaneSave((BWrapPane)scrollPane.getContent());       
    }
    
}
