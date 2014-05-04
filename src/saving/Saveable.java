/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saving;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.Parent;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public interface Saveable {
    
    public Node create(BUndoManager undoManager);
    public List<Saveable> getChildren();
    
    
}
