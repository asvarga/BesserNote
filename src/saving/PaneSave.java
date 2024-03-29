package saving;


import bessernote.ui.BDeck;
import bessernote.ui.BFlashCard;
import bessernote.ui.BImage;
import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
import bessernote.ui.BWrapPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import saving.BScrollPaneSave;
import saving.BTabPaneSave;
import saving.BTextAreaSave;
import saving.BWrapPaneSave;
import saving.Saveable;
import undo.BUndoManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ddliu
 */
public class PaneSave implements Saveable{
        
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private List<Saveable> children = new ArrayList<>();
    private String color;
    
    public PaneSave(Pane pane){
        //Save this
        xPos = pane.getLayoutX();
        yPos = pane.getLayoutY();
        xDim = pane.getPrefWidth();
        yDim = pane.getPrefHeight();
        if(pane.getStyle().contains("#")){
            color = pane.getStyle().substring(pane.getStyle().indexOf("#"));
            //System.out.println(color.toString());
        }
        else{
            color = "#ffffff";
        }

        //Save children, if not empty.
        if(! pane.getChildren().isEmpty()){
            for(Node node: pane.getChildren()){
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
                else if(node instanceof Path){
                    saveObj = new DoodleSave((Path)node);
                }   
                else if(node instanceof Ellipse){
                    saveObj = new EllipseSave((Ellipse)node);
                }                
                else if(node instanceof BDeck){
                    saveObj = new BDeckSave((BDeck)node);
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
       Pane returnMe = new Pane();
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);

       returnMe.setStyle("-fx-background-color:" + color);
       if (children != null){
            for(Saveable child: children){
                if(child != null)
                    returnMe.getChildren().add(child.create(undoManager));
            }
       }
       return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return this.getChildren();
    }
    
    
}
