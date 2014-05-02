/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BFlashCard;
import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
import bessernote.ui.BWrapPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * A FlashCard in the saved state.
 */
public class BFlashCardSave implements Savable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private List<Savable> children = new ArrayList<>();
    
    public BFlashCardSave(BFlashCard flashCard){
        //Save this
        xPos = flashCard.getLayoutX();
        yPos = flashCard.getLayoutY();
        xDim = flashCard.getPrefWidth();
        yDim = flashCard.getPrefHeight();
        padding = flashCard.padding();
        //Save children, if there's more than front, which we don't care about.
        //System.out.println(flashCard.getChildren());
        if(flashCard.getChildren().size() > 0){
            for(int i = 0; i < flashCard.getChildren().size(); i++){
                Node node = flashCard.getChildren().get(i);
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
    public Parent create() {
       BFlashCard returnMe = new BFlashCard();
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);
       //returnMe.setStyle("-fx-background-color:" + color);
       if(children.size() > 0){
           for(int i = 0; i < children.size(); i ++){
               returnMe.getChildren().add(children.get(i).create());
           }
//            for(Savable child: children){
//                returnMe.getChildren().add(child.create());
//            }
       }
       return returnMe;
    }

    @Override
    public List<Savable> getChildren() {
        return this.getChildren();
    }
}
