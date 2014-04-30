/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author ddliu
 * RootSave is the object that contains the state of the entire GUI. 
 * The root is sheet.
 * 
 */
public class RootSave {
       
    private double xDim, yDim;
    private List<Node> children;
    
    public RootSave(Pane root){
        xDim = root.getWidth();
        yDim = root.getHeight();
        children = root.getChildren();
    }
    /*
    create() takes the RootSave object and draws it into a scene graph.
    */
    public void create(){
        
    }
    
}
