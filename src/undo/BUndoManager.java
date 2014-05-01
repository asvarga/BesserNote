/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import java.util.function.Consumer;
import javax.swing.undo.UndoManager;
import org.fxmisc.undo.UndoManagerFactory;
import org.reactfx.EventStream;
import static org.reactfx.EventStreams.merge;
import static org.reactfx.EventStreams.never;
import org.reactfx.Subscription;

/**
 *
 * @author avarga
 */
public class BUndoManager {
//    private final UndoManager undoManager;
//    private EventStream<BChange> changes;
//    
//    public BUndoManager() {
//        changes = never();
//        undoManager = UndoManagerFactory.fixedSizeHistoryUndoManager(
//                changes, 
//                c -> c.redo(), 
//                c -> c.undo(), 
//                (c1, c2) -> c1.mergeWith(c2), 
//                25);    // number of changes saved
//    }
//    
//    public void trackProperty(EventStream<BChange> stream) {
//        if (changes == null) {
//            changes = stream;
//        } else {
//            changes = merge(changes, stream);
//        }
//    }
}
