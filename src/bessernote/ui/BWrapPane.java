/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import bessernote.deprecated.Undoable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.reactfx.EventStream;
import undo.BChange;
import undo.BUndoManager;
import undo.PlacementChange;

/**
 *
 * @author avarga
 */
public class BWrapPane extends Pane implements ChildSpecifier {
    
    public Pane placeHolder;
    public List<Node> clickable;
    public List<Node> others;
    
    protected double padding;
    protected double prefMinWidth;
    protected double prefMinHeight;
    
//    ChangeListener oListener;
    ChangeListener bListener;
    
    BUndoManager undoManager;
        
    public BWrapPane(BUndoManager undoManager) {
        super();
        
        clickable = new ArrayList<>();
        others = new ArrayList<>();
                               
        bListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                fixOutline();
            }
        };
              
        placeHolder = new Pane();
        placeHolder.boundsInParentProperty().addListener(bListener);
        getChildren().add(placeHolder);
        
        final BWrapPane this2 = this;
        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Node> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                             //permutate
                        }
                    } else if (c.wasUpdated()) {
                             //update item
                    } else {
                        for (Node remItem : c.getRemoved()) {
                            if (!others.contains(remItem) && clickable.contains(remItem)) {
                                clickable.remove(remItem);
                                remItem.boundsInParentProperty().removeListener(bListener);
                                remItem.visibleProperty().removeListener(bListener);
                            }
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            if (!others.contains(addItem)) {
                                clickable.add(addItem);
                                addItem.boundsInParentProperty().addListener(bListener);
                                addItem.visibleProperty().addListener(bListener);
                            }
                        }
                        //fixOutline();
                    }
                }
                if (clickable.size() > 0) {
                    if (this2.getChildren().contains(placeHolder)) {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                this2.removePlaceholder();
                            }                 
                        });
                    }
                    //placeHolder.setVisible(false);
                }
                if (getChildren().size() == 0) {
                    ((Pane)this2.getParent()).getChildren().remove(this2);
                } else {
                    fixOutline();
                }
                //fixOutline();
            }
        });
        
        fixOutline();
        
        this.widthProperty().addListener(bListener);
        this.heightProperty().addListener(bListener);
        this.layoutXProperty().addListener(bListener);
        this.layoutYProperty().addListener(bListener);
        
        this.undoManager = undoManager;
//        undoManager.trackMyPlacementChanges(this);
    }
    
    // I hate life
    public void fixOutline() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                new Thread(new Runnable() {
                    @Override public void run() {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                double minX = Double.MAX_VALUE;
                                double maxX = -Double.MAX_VALUE;
                                double minY = Double.MAX_VALUE;
                                double maxY = -Double.MAX_VALUE;
                                for (Node child : getChildren()) {
                                    if (child.isVisible() && !others.contains(child)) {
                                        minX = Math.min(minX, child.getBoundsInParent().getMinX());
                                        maxX = Math.max(maxX, child.getBoundsInParent().getMaxX());
                                        minY = Math.min(minY, child.getBoundsInParent().getMinY());
                                        maxY = Math.max(maxY, child.getBoundsInParent().getMaxY());
                                    }
                                }
                                
                                setLayoutX(getLayoutX()+minX-padding);
                                setPrefWidth(maxX-minX+padding*2);
                                setLayoutY(getLayoutY()+minY-padding);
                                setPrefHeight(maxY-minY+padding*2);
                                
                                for (Node child : getChildren()) {
                                    if (!others.contains(child)) {
                                        try {   // might be bound
                                            child.setLayoutX(child.getLayoutX()-minX+padding);
                                            child.setLayoutY(child.getLayoutY()-minY+padding);
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
    
    public void setPadding(double d) {
        placeHolder.setLayoutX(placeHolder.getLayoutX()+d-padding);
        placeHolder.setLayoutY(placeHolder.getLayoutY()+d-padding);
        padding = d;
        placeHolder.setPrefSize(prefMinWidth-2*padding, prefMinHeight-2*padding);
        fixOutline();
    }
    
    public void setPrefMinSize(double w, double h) {
        prefMinWidth = w;
        prefMinHeight = h;
        placeHolder.setPrefSize(prefMinWidth-2*padding, prefMinHeight-2*padding);
        fixOutline();
    }
    
    public void setMaxPosition(double x, double y) {
        placeHolder.setLayoutX(placeHolder.getLayoutX()+x-getLayoutX());
        placeHolder.setLayoutY(placeHolder.getLayoutY()+y-getLayoutY());
    }
    
    public double padding() {
        return padding;
    }
    
    @Override
    public List<Node> specifyChildren() {
        return clickable;
    }

    @Override
    public Node specifySelf() {
        return this;
    }
    
    public void setPlaceholder(Pane p){
        if (getChildren().contains(placeHolder)) {
            getChildren().remove(placeHolder);
        }
        placeHolder = p;
        getChildren().add(placeHolder);
    }
    
    public void removePlaceholder() {
        if (getChildren().contains(placeHolder)) {
            getChildren().remove(placeHolder);
        }
    }
    
    public void addOther(Node n) {
        others.add(n);
        getChildren().add(n);
    }
    
}



