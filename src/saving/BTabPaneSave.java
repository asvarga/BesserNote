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
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * A BTabPane in the saved state. This includes the tabs, since the tabpane itself has a weird structure.
 */
public class BTabPaneSave implements Savable{
    
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
    
}
