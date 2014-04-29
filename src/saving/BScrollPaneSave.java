/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BScrollPane;
import java.io.Serializable;
import javafx.scene.Node;

/**
 *
 * @author ddliu
 * BScrollPaneSave is the model for a ScrollPane in the saved state.
 */
public class BScrollPaneSave {
    
    private double xPos, yPos;
    private double xDim, yDim;
    private Node content;
    
    
    public BScrollPaneSave(BScrollPane scrollPane){
        
    }

    
}
