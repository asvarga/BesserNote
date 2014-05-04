/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BImage;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Parent;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class BImageSave implements Saveable{
    
    private double xDim, yDim;
    private double xPos, yPos;
    private String path;
    
    
    public BImageSave(BImage image){
        xDim = image.getPrefWidth();
        yDim = image.getPrefHeight();
        xPos = image.getLayoutX();
        yPos = image.getLayoutY();
        path = image.returnPath();
    }

    @Override
    public Parent create(BUndoManager undoManager) {
        BImage returnMe = new BImage(undoManager);
        returnMe.setPrefWidth(xDim);
        returnMe.setPrefHeight(yDim);
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.createExistingImage(path);
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return new ArrayList<Saveable>();
    }
    
}
