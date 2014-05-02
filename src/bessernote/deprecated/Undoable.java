/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.deprecated;

import java.util.ArrayList;
import org.reactfx.EventStream;
import undo.BChange;
import undo.BUndoManager;

/**
 *
 * @author avarga
 */
public interface Undoable {
    
    public void getChangeStreams(BUndoManager manager);
    
}
