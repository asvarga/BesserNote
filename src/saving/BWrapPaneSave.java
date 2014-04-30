/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import bessernote.ui.BWrapPane;
import java.io.Serializable;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author ddliu
 * A BWrapPane in the saved state..
 */
public class BWrapPaneSave{
    
    private double xPos, yPos;
    private double xDim, yDim;
    private double padding;
    private List<Node> children;
    
    public BWrapPaneSave(BWrapPane wrapPane){
        xPos = wrapPane.getLayoutX();
        yPos = wrapPane.getLayoutY();
        xDim = wrapPane.getWidth();
        yDim = wrapPane.getHeight();
        children = wrapPane.getChildren();
    }
    
}
