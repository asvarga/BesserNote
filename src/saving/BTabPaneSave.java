/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BTabPane;
import java.io.Serializable;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * A BTabPane in the saved state. This includes the tabs, since the tabpane itself has a weird structure.
 */
public class BTabPaneSave{
    
    class BEditableTabSave{
        private Pane content;
        
        BEditableTabSave(Pane content){
            
        }
        
    }
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private List<Tab> tabs;
    
    public BTabPaneSave (BTabPane tabPane){
        
    }
    
}
