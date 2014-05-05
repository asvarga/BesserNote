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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Path;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class BDeckSave implements Saveable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private List<Saveable> children = new ArrayList<>();
    private List<Saveable> cards = new ArrayList<>();
    private String color;
    
    public BDeckSave(BDeck bDeck){
        xPos = bDeck.getLayoutX();
        yPos = bDeck.getLayoutY();
        xDim = bDeck.getPrefWidth();
        yDim = bDeck.getPrefHeight();
        color = bDeck.getStyle().substring(bDeck.getStyle().indexOf("#"));  
        //Save children, if not empty.
        if(! bDeck.getChildren().isEmpty()){
            for(Node node: bDeck.getChildren()){
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
        
        if(! bDeck.cards.isEmpty()){
            for(Node node: bDeck.cards){
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
                cards.add(saveObj);
            }
        }          
    }
    
    
    
    @Override
    public Node create(BUndoManager undoManager) {
       BDeck returnMe = new BDeck(undoManager);
       returnMe.setLayoutX(xPos);
       returnMe.setLayoutY(yPos);
       returnMe.setPrefHeight(yDim);
       returnMe.setPrefWidth(xDim);

       returnMe.setStyle("-fx-background-color:" + color);
       if (children != null){
            for(Saveable child: children){
                returnMe.getChildren().add(child.create(undoManager));
            }
       }
  
       if (cards != null){
           for (Saveable card: cards){
               if (card != null)
                    returnMe.cards.add(card.create(undoManager));
           }
       }
       
       return returnMe;  
    }

    @Override
    public List<Saveable> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
}
