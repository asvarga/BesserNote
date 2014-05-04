/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BFlashCard;
import bessernote.ui.BImage;
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
import undo.BUndoManager;

/**
 *
 * @author ddliu
 * A BWrapPane in the saved state..
 */
public class BWrapPaneSave implements Saveable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private String color;
    private List<Saveable> children = new ArrayList<>();
    
    public BWrapPaneSave(BWrapPane wrapPane){
        //Save this
        xPos = wrapPane.getLayoutX();
        yPos = wrapPane.getLayoutY();
        xDim = wrapPane.getPrefWidth();
        yDim = wrapPane.getPrefHeight();
        padding = wrapPane.padding();
        if(wrapPane.getStyle().contains("#")){
            color = wrapPane.getStyle().substring(wrapPane.getStyle().indexOf("#"));
            //System.out.println(color.toString());
        }
        else{
            color = "#ffffff";
        }
        //Save children, if not empty.
        if(! wrapPane.getChildren().isEmpty()){
            for(Node node: wrapPane.getChildren()){
                Saveable saveObj = null;
                if(node instanceof BTabPane){
                    saveObj = new BTabPaneSave((BTabPane)node);
                }
                else if(node instanceof BTextArea){
                    saveObj = new BTextAreaSave((BTextArea)node);
                }
                else if(node instanceof BScrollPane){
                    saveObj = new BScrollPaneSave((BScrollPane)node);
                }
                else if (node instanceof BImage){
                    saveObj = new BImageSave((BImage)node);
                }
                 else if (node instanceof BFlashCard){
                    saveObj = new BFlashCardSave((BFlashCard)node);
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
    public Parent create(BUndoManager undoManager) {
       BWrapPane returnMe = new BWrapPane(undoManager);
       //returnMe.setPlaceholder((Pane)children.get(0).create(undoManager));
       returnMe.setPadding(padding);
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);
       returnMe.setStyle("-fx-background-color:" + color);
       //returnMe.setPadding(padding);
       //returnMe.setStyle("-fx-background-color:" + color);
       if(children.size() > 0){
//           for(int i = 1; i <children.size(); i ++){
//               returnMe.getChildren().add(children.get(i).create(undoManager));
//           }
            for(Saveable child: children){
                Node myChild = child.create(undoManager);
                myChild.setStyle("-fx-background-color: " + color);
                returnMe.getChildren().add(myChild);
            }
       }
       return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return this.getChildren();
    }
    
}
