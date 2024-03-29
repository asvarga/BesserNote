/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import undo.BUndoManager;

/**
 *
 * @author ddliu
 * A FlashCard in the saved state.
 */
public class BFlashCardSave implements Saveable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private String color;
    private List<Saveable> children = new ArrayList<>();
    
    public BFlashCardSave(BFlashCard flashCard){
        //Save this
        xPos = flashCard.getLayoutX();
        yPos = flashCard.getLayoutY();
        xDim = flashCard.getPrefWidth();
        yDim = flashCard.getPrefHeight();
        padding = flashCard.padding();
        if(flashCard.getStyle().contains("#")){
            color = flashCard.getStyle().substring(flashCard.getStyle().indexOf("#"));
            //System.out.println(color.toString());
        }
        else{
            color = "#ffffff";
        }
        //Save children, if there's more than front, which we don't care about.
        //System.out.println(flashCard.getChildren());
        if(flashCard.getChildren().size() > 0){
            for(int i = 0; i < flashCard.getChildren().size(); i++){
                Node node = flashCard.getChildren().get(i);
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
       BFlashCard returnMe = new BFlashCard(undoManager);
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);
       returnMe.setPrefMinSize(returnMe.getPrefWidth(), returnMe.getPrefHeight());
       returnMe.setPadding(padding);       
       //returnMe.getChildren().add(children.get(0).create(undoManager));

       returnMe.setStyle("-fx-background-color:" + color);       
       //returnMe.setStyle("-fx-background-color:" + color);
       if(children.size() > 0){
           for(int i = 0; i < children.size(); i++){
               if(i == 0){
                   //returnMe.setPlaceholder((Pane) (children.get(i).create(undoManager)));
                   returnMe.getChildren().add(children.get(i).create(undoManager));
               }
               else if (i == 1){
                   returnMe.setupP2((Pane)(children.get(i).create(undoManager)));
                   //returnMe.getChildren().add(children.get(i).create(undoManager));
                   //System.out.println(returnMe.getChildren());
               }
               else 
                   returnMe.getChildren().add(children.get(i).create(undoManager));
               }
//            for(Savable child: children){
//                returnMe.getChildren().add(child.create());
//            }
       }
       //System.out.println(returnMe.getChildren());
       return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        return this.getChildren();
    }
}
