/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
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

/**
 *
 * @author avarga
 */
public class BWrapPane extends Pane {
    
    public Pane placeHolder;
    private double padding;
    
//    ChangeListener oListener;
    ChangeListener bListener;
    
    public BWrapPane() {
        super();
                               
        bListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                fixOutline();
            }
        };
                   
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
                            remItem.boundsInParentProperty().removeListener(bListener);
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            addItem.boundsInParentProperty().addListener(bListener);
                        }
                        //fixOutline();
                    }
                }
                if (getChildren().size() == 0) {
                    ((Pane)this2.getParent()).getChildren().remove(this2);
                }
                fixOutline();
            }
        });
        
//        oListener = new ChangeListener() {
//            @Override
//            public void changed(ObservableValue ov, Object t, Object t1) {
//                fixChildren();
//            }
//        };
        
//        this.layoutXProperty().addListener(oListener);
//        this.layoutYProperty().addListener(oListener);
        
        placeHolder = new Pane();
        placeHolder.setStyle("-fx-background-color: #FF00FF;");
        getChildren().add(placeHolder);

        fixOutline();
        
    }
    
    // I hate life
    private void fixOutline() {
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
                                    minX = Math.min(minX, child.getBoundsInParent().getMinX());
                                    maxX = Math.max(maxX, child.getBoundsInParent().getMaxX());
                                    minY = Math.min(minY, child.getBoundsInParent().getMinY());
                                    maxY = Math.max(maxY, child.getBoundsInParent().getMaxY());
                                }
                                
                                setLayoutX(getLayoutX()+minX-padding);
                                setPrefWidth(maxX-minX+padding*2);
                                setLayoutY(getLayoutY()+minY-padding);
                                setPrefHeight(maxY-minY+padding*2);
                                
                                for (Node child : getChildren()) {
                                    child.setLayoutX(child.getLayoutX()-minX+padding);
                                    child.setLayoutY(child.getLayoutY()-minY+padding);
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
    
    public void setPadding(double d) {
        padding = d;
        fixOutline();
    }
    
//    @Override
//    public ObservableList<Node> getChildren() {
//        return null;
//    }
    
}



