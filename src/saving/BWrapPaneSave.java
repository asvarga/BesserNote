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
    
    public BWrapPaneSave(BWrapPane wrapPane){
        //Save this
        xPos = wrapPane.getLayoutX();
        yPos = wrapPane.getLayoutY();
        xDim = wrapPane.getPrefWidth();
        yDim = wrapPane.getPrefHeight();
        padding = wrapPane.padding();
        //Save children, if not empty.
        if(! wrapPane.getChildren().isEmpty()){
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
                else if (node instanceof Pane){
                    saveObj = new PaneSave((Pane)node);
                }
                children.add(saveObj);
            }
        }
                
    }

    @Override
    public Parent create() {
       BWrapPane returnMe = new BWrapPane();
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);
       //returnMe.setStyle("-fx-background-color:" + color);
       if(children != null){
            for(Savable child: children){
                returnMe.getChildren().add(child.create());
            }
       }
       return returnMe;
    }

    @Override
    public List<Savable> getChildren() {
        return this.getChildren();
    }
    
}
