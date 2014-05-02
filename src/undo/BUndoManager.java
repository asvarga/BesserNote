/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import org.fxmisc.undo.UndoManager;
import org.fxmisc.undo.UndoManagerFactory;
import org.reactfx.EventSource;
import org.reactfx.EventStream;
import static org.reactfx.EventStreams.merge;
import static org.reactfx.EventStreams.never;
import org.reactfx.Subscription;

/**
 *
 * @author avarga
 */
public class BUndoManager {
    private final UndoManager undoManager;
    private EventSource source;
    private EventStream<BChange> changes;
    
    public BUndoManager() {
        source = new EventSource();
        changes = merge(source);
        
        undoManager = UndoManagerFactory.fixedSizeHistoryUndoManager(
                changes, 
                c -> c.redo(), 
                c -> c.undo(), 
                (c1, c2) -> c1.mergeWith(c2), 
                25);    // number of changes saved
    }
    
    public void addChange(BChange c) {
        source.push(c);
    }
    
    public void trackStream(EventStream<BChange> stream) {
        // example stream:
        // changesOf(circle.centerYProperty()).map(c -> new CenterYChange(c));
        changes = merge(changes, stream);
    }
    
//    public ArrayList<EventStream<BChange>> getPlacementChangeStreams(Node n) {
//        ArrayList<EventStream<BChange>> streams = new ArrayList<EventStream<BChange>>();
//        streams.add(changesOf(n.centerYProperty()).map(c -> new CenterYChange(c)))
//    }
    
    public void trackMyPlacementChanges(Region r) {
        r.layoutXProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal) {
                addChange(new PlacementChange(
                        r,
                        PlacementChange.X,
                        (double) oldVal,
                        (double) newVal,
                        true
                ));
            }
        });
        r.layoutYProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal) {
                addChange(new PlacementChange(
                        r,
                        PlacementChange.Y,
                        (double) oldVal,
                        (double) newVal,
                        true
                ));
            }
        });
        r.prefWidthProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal) {
                addChange(new PlacementChange(
                        r,
                        PlacementChange.WIDTH,
                        (double) oldVal,
                        (double) newVal,
                        true
                ));
            }
        });
        r.prefHeightProperty().addListener(new ChangeListener(){
            @Override public void changed(ObservableValue o, Object oldVal, Object newVal) {
                addChange(new PlacementChange(
                        r,
                        PlacementChange.HEIGHT,
                        (double) oldVal,
                        (double) newVal,
                        true
                ));
            }
        });
    }
    
    public void undo() {
        undoManager.undo();
    }
    
    public void redo() {
        undoManager.redo();
    }
}
