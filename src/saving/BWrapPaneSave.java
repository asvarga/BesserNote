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
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * A BWrapPane in the saved state..
 */
public class BWrapPaneSave implements Savable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private List<Savable> children = new ArrayList<>();
    private String color;
    
    public BWrapPaneSave(BWrapPane wrapPane){
        //Save this
        xPos = wrapPane.getLayoutX();
        yPos = wrapPane.getLayoutY();
        xDim = wrapPane.getPrefWidth();
        yDim = wrapPane.getPrefHeight();
        padding = wrapPane.padding();
        color = wrapPane.getStyle().substring(wrapPane.getStyle().indexOf("#"), wrapPane.getStyle().length());
        //Save children
        for(Node node: wrapPane.getChildren()){
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
                
    }
    
}
