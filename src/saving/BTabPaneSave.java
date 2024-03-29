/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BEditableTab;
import bessernote.ui.BTabPane;
import bessernote.ui.BWrapPane;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 * A BTabPane in the saved state. This includes the tabs, since the tabpane itself has a weird structure.
 */
public class BTabPaneSave implements Saveable{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private List<BEditableTabSave> tabs = new ArrayList<>();
    
    public BTabPaneSave (BTabPane tabPane){
        //Save this
        xPos = tabPane.getLayoutX();
        yPos = tabPane.getLayoutY();
        xDim = tabPane.getPrefWidth();
        yDim = tabPane.getPrefHeight();
        //Save tabs
        for(Tab tab: tabPane.getTabs()){
            tabs.add(new BEditableTabSave((BEditableTab)tab));
        }
    }

    @Override
    public Parent create(BUndoManager undoManager) {
        BTabPane returnMe = new BTabPane(undoManager);
        returnMe.getTabs().remove(0);
        //returnMe.getSelectionModel().clearAndSelect(0);
        returnMe.setLayoutX(xPos);
        returnMe.setLayoutY(yPos);
        returnMe.setPrefHeight(yDim);
        returnMe.setPrefWidth(xDim);
        /*
        for(BEditableTabSave tabSave: tabs){
            BEditableTab thisTab = tabSave.create(undoManager);
            returnMe.getTabs().add(thisTab);
            returnMe.getSelectionModel().select(thisTab);
        }
        */
        for(int i=0; i < tabs.size(); i++){
            BEditableTab thisTab = tabs.get(i).create(undoManager);
            returnMe.getTabs().add(thisTab);
            returnMe.getSelectionModel().select(thisTab);
        }
        return returnMe;
    }

    @Override
    public List<Saveable> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
