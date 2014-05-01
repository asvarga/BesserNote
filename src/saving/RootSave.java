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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * RootSave is the object that contains the state of the entire GUI. 
 * The root is sheet.
 * 
 */
public class RootSave implements Savable{
       
    private double xDim, yDim;
    private List<Savable> children = new ArrayList<>();
    private String color;
    
    public RootSave(Pane root){
        //Saves this
        xDim = root.getWidth();
        yDim = root.getHeight();
        color= root.getStyle().substring(root.getStyle().indexOf("#"), root.getStyle().length());
        //Save children
        if(! root.getChildren().isEmpty()){
            for(Node node: root.getChildren()){
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
    
    public void printChildren(){
        System.out.println("xdim"+ xDim + "    ydim:" + yDim+ "     Color: "+color);
        for(Savable child: children){
            System.out.println(child);
        }
    }
    
    /*
    create() takes the RootSave object and draws it into a scene graph. Returns only the highest level for the pane. Dimensions and color.
    */
    public Pane create(){
        Pane thisPane = new Pane();
        thisPane.setPrefWidth(xDim);
        thisPane.setPrefHeight(yDim);
        thisPane.setStyle("-fx-background-color: " + color); 
        for(Savable saveChild: children){
            thisPane.getChildren().add(saveChild.create());
        }
        return thisPane;
    }
    
    public List<Savable> getChildren(){
        return children;
    }
    
    public String toString(){
        return xDim + " " + yDim + " " + children + " " + color;
    }
    
    
}
