/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.deprecated;

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
public class BWrapPaneOld extends Pane implements ChildSpecifier {
    
    public Pane outline;
    public Pane placeHolder;
    List<Node> otherChildren;
    private double padding;
    
    ChangeListener oListener;
    ChangeListener bListener;
    
    public BWrapPaneOld() {
        super();
                                
        outline = new Pane();
        outline.setStyle("-fx-background-color: #00FFFF;");
        getChildren().add(outline);
                
        bListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                fixOutline();
            }
        };
                   
        final BWrapPaneOld this2 = this;
        getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                while (c.next()) {
                    if (c.wasPermutated()) {
                        for (int i = c.getFrom(); i < c.getTo(); ++i) {
                             //permutate
                        }
                    } else if (c.wasUpdated()) {
                             //update item
                    } else {
                        for (Node remItem : c.getRemoved()) {
                            otherChildren.remove(remItem);
                            remItem.boundsInParentProperty().removeListener(bListener);
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            otherChildren.add(addItem);
                            addItem.boundsInParentProperty().addListener(bListener);
                        }
                        //fixOutline();
                    }
                }
                if (otherChildren.size() == 0) {
                    ((Pane)this2.getParent()).getChildren().remove(this2);
                }
                fixOutline();
            }
        });
        
        oListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                fixChildren();
            }
        };
        
        outline.layoutXProperty().addListener(oListener);
        outline.layoutYProperty().addListener(oListener);
   
        otherChildren = new ArrayList<Node>();
//        for (int i=0; i<4; i++) {
//            Pane p = new Pane();
//            p.setStyle("-fx-background-color: #00FF00;");
//            getChildren().add(p);
//            p.setLayoutX(Math.random()*300);
//            p.setLayoutY(Math.random()*300);
//            p.setPrefSize(100, 100);
//        }
        
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
                                for (Node child : otherChildren) {
                                    minX = Math.min(minX, child.getBoundsInParent().getMinX());
                                    maxX = Math.max(maxX, child.getBoundsInParent().getMaxX());
                                    minY = Math.min(minY, child.getBoundsInParent().getMinY());
                                    maxY = Math.max(maxY, child.getBoundsInParent().getMaxY());
                                }
                                outline.setLayoutX(minX-padding);
                                outline.setPrefWidth(maxX-minX+padding*2);
                                outline.setLayoutY(minY-padding);
                                outline.setPrefHeight(maxY-minY+padding*2);
                            }
                        });
                    }
                }).start();
            }
        });
    }
    
    private void fixChildren() {
        Platform.runLater(new Runnable() {
            @Override public void run() {
                new Thread(new Runnable() {
                    @Override public void run() {
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                double minDX = Double.MAX_VALUE;
                                double minDY = Double.MAX_VALUE;
                                for (Node child : otherChildren) {
                                    minDX = Math.min(minDX, 
                                            child.getBoundsInParent().getMinX()
                                                    -outline.getLayoutX());
                                    minDY = Math.min(minDY, 
                                            child.getBoundsInParent().getMinY()
                                                    -outline.getLayoutY());
                                }
                                for (Node child : otherChildren) {
                                    child.setLayoutX(child.getLayoutX()-minDX+padding);
                                    child.setLayoutY(child.getLayoutY()-minDY+padding);
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
    
    @Override
    public List<Node> specifyChildren() {
        return otherChildren;
    }
//    
//    @Override
//    public ObservableList<Node> getChildren() {
//        return null;
//    }

    @Override
    public Node specifySelf() {
        return outline;
    }
    
}



// TODO: since outline stands in for this, children are being added to outline