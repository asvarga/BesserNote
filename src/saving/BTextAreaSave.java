/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
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
 * A BTextArea in the saved state.
 */
public class BTextAreaSave implements Saveable{

    private double xPos, yPos;
    private double xDim, yDim;
    private String text;
    //private List<Savable> children = new ArrayList<>(); 
    private String color;
    
    public BTextAreaSave(BTextArea textArea) {
        //Save this
        xPos = textArea.getLayoutX();
        yPos = textArea.getLayoutY();
        xDim = textArea.getPrefWidth();
        yDim = textArea.getPrefHeight();
        text = textArea.getText();
        //System.out.println(textArea.getStyle());
        color= textArea.getStyle().substring(textArea.getStyle().indexOf("#"), textArea.getStyle().length());
        /*
        //Save children
        for(Node node: textArea.getChildrenUnmodifiable()){
            Savable saveObj = null;
            if(node instanceof BTabPane){
                saveObj = new BTabPaneSave((BTabPane)node);
            }
            else if(node instanceof BTextArea){
                saveObj = new BTextAreaSave((BTextArea)node);
            }
            else if(node instanceof BScrollPane){
                saveObj = new BScrollPaneSave((BScrollPane)node);
            }
            else if (node instanceof BWrapPane){
                saveObj = new BWrapPaneSave((BWrapPane)node);
            }
            children.add(saveObj);
        }
                */
    }

    @Override
    public Parent create(BUndoManager undoManager) {
        BTextArea returnMe = new BTextArea();
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.setPrefHeight(yDim);
        returnMe.setPrefWidth(xDim);
        if(text != null)
             returnMe.setText(text);
        //returnMe.setStyle("-fx-background-color: " + color);
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return new ArrayList<Saveable>();
    }
    
}
