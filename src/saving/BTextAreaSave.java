/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BTextArea;
import java.io.Serializable;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author ddliu
 * A BTextArea in the saved state.
 */
public class BTextAreaSave{

    private double xPos, yPos;
    private double xDim, yDim;
    private String content;
    private List<Node> children; 
    
    BTextAreaSave(BTextArea textArea) {
        
    }
    
}
