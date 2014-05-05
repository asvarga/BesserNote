/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 */
public class BScrollPane extends ScrollPane implements ChildSpecifier {
    
    public BWrapPane p;
    double padding;
        
    public BScrollPane(BUndoManager undoManager) {
        p = new BWrapPane(undoManager);
        this.setContent(p);
        
        this.boundsInParentProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                p.setPrefMinSize(getWidth(), getHeight());
            }
        });
        
        p.setPadding(2000);
        
        BScrollPane this2 = this;
        p.getChildren().addListener(new ListChangeListener<Node>() {
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
                            
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            
                            ChangeListener xChange = new ChangeListener() {
                                @Override public void changed(ObservableValue ov, Object t, Object t1) {
                                    double d1 = (double) t1;
                                    if (d1 >= 2000) {
//                                        ChangeListener this3 = this;
                                        //if (p.clickable.size() == 1) {
                                        this2.setHvalue((d1-this2.getWidth()/4)/(p.getWidth()-this2.getWidth()));
//                                        } else {
//                                            Timer timer = new Timer();       
//                                            timer.schedule(new TimerTask() {
//                                                public void run() {
//                                                    double w = addItem.getBoundsInLocal().getWidth();
//                                                    this2.setHvalue((d1-(this2.getWidth()-w)/2)/(p.getWidth()-this2.getWidth()));
//                                                }
//                                            }, (long) 50);
//                                        }
                                        addItem.layoutXProperty().removeListener(this);
                                    }
                                }
                            };
                            addItem.layoutXProperty().addListener(xChange);

                            ChangeListener yChange = new ChangeListener() {
                                @Override public void changed(ObservableValue ov, Object t, Object t1) {
                                    double d1 = (double) t1;
                                    if (d1 >= 2000) {
                                        //if (p.clickable.size() == 1) {
                                        this2.setVvalue((d1-this2.getHeight()/4)/(p.getHeight()-this2.getHeight()));
//                                        } else {
//                                            Timer timer = new Timer();       
//                                            timer.schedule(new TimerTask() {
//                                                public void run() {
//                                                    double h = addItem.getBoundsInLocal().getHeight();
//                                                    this2.setVvalue((d1-(this2.getHeight()-h)/2)/(p.getHeight()-this2.getHeight()));
//                                                }
//                                            }, (long) 50);
//                                        }
                                        addItem.layoutYProperty().removeListener(this);
                                    }
                                }
                            };
                            addItem.layoutYProperty().addListener(yChange);
                            
//                            ChangeListener wChange = new ChangeListener() {
//                                @Override public void changed(ObservableValue ov, Object t, Object t1) {
//                                    double d1 = (double) t1;
//                                    if (d1 >= 2000) {
//                                        System.out.println(d1);
//                                        System.out.println(p.getWidth());
////                                        this2.setHvalue(d1/p.getWidth());
//                                        addItem.layoutXProperty().removeListener(this);
//                                    }
//                                }
//                            };
//                            addItem.layoutXProperty().addListener(wChange);
//                            
//                            ChangeListener hChange = new ChangeListener() {
//                                @Override public void changed(ObservableValue ov, Object t, Object t1) {
//                                    double d1 = (double) t1;
//                                    if (d1 >= 2000) {
////                                        this2.setHvalue(d1/p.getWidth());
//                                        addItem.layoutXProperty().removeListener(this);
//                                    }
//                                }
//                            };
//                            addItem.layoutXProperty().addListener(hChange);
                        }
                    }
                }
            }
        });
               
        // Delete when empty
        p.parentProperty().addListener(new ChangeListener() {
            @Override public void changed(ObservableValue ov, Object t, Object t1) {
                if (t1 == null) {
                    ((Pane)this2.getParent()).getChildren().remove(this2);
                } 
            }
        });
    }
    
    @Override
    public List<Node> specifyChildren() {
        try {
            return p.specifyChildren();
        } catch (Exception e) {
            System.out.println("ERROR: BScrollPane's content must be of type Parent.");
            return new ArrayList<>();
        }
    }
    
//    @Override
//    public ObservableList<Node> getChildren() {
//        return ((Pane) this.getContent()).getChildren();
//    }
    
    @Override
    public Node specifySelf() {
        return p;
    }
    
    public void setPadding(double d) {
        padding = d;
        p.setPadding(d);
    }

}
