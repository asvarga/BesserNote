/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package undo;

import bessernote.nodemaker.placement.EasyBind;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.reactfx.Change;

/**
 *
 * @author avarga
 */
public class PlacementChange implements BChange {
    
    static final int MODE_X = 0;
    static final int MODE_Y = 1;
    static final int PAD_X = 2;
    static final int PAD_Y = 3;
    static final int X = 4;
    static final int Y = 5;
    static final int WIDTH = 6;
    static final int HEIGHT = 7;
    
    
    protected final Region node;
    
    protected String oldModeX;
    protected String oldModeY;
    protected double oldPadX = Double.NaN;
    protected double oldPadY = Double.NaN;
    protected double oldX = Double.NaN;
    protected double oldY = Double.NaN;
    protected double oldWidth = Double.NaN;
    protected double oldHeight = Double.NaN;
    protected String newModeX;
    protected String newModeY;
    protected double newPadX = Double.NaN;
    protected double newPadY = Double.NaN;
    protected double newX = Double.NaN;
    protected double newY = Double.NaN;
    protected double newWidth = Double.NaN;
    protected double newHeight = Double.NaN;

    protected PlacementChange(
            Region node,
            String oldModeX, String oldModeY, double oldPadX, double oldPadY,
            double oldX, double oldY, double oldWidth, double oldHeight,
            String newModeX, String newModeY, double newPadX, double newPadY,
            double newX, double newY, double newWidth, double newHeight) {
        this.node = node;
        this.oldModeX = oldModeX;
        this.oldModeY = oldModeY;
        this.oldPadX = oldPadX;
        this.oldPadY = oldPadY;
        this.oldX = oldX;
        this.oldY = oldY;
        this.oldWidth = oldWidth;
        this.oldHeight = oldHeight;
        this.newModeX = newModeX;
        this.newModeY = newModeY;
        this.newPadX = newPadX;
        this.newPadY = newPadY;
        this.newX = newX;
        this.newY = newY;
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }
    
    protected PlacementChange(Region node, int property, Change<String> change) {
        this.node = node;
        switch (property) {
            case MODE_X:
                oldModeX = change.getOldValue();
                newModeX = change.getNewValue();
                break;
            case MODE_Y:
                oldModeY = change.getOldValue();
                newModeY = change.getNewValue();
                break;
        }
    }
    
    // somehow this doesn't work without the pointles bool
    protected PlacementChange(Region node, int property, Change<Double> change, Boolean b) {
        this.node = node;
        switch (property) {
            case PAD_X:
                oldPadX = change.getOldValue();
                newPadX = change.getNewValue();
                break;
            case PAD_Y:
                oldPadY = change.getOldValue();
                newPadY = change.getNewValue();
                break;
            case X:
                oldX = change.getOldValue();
                newX = change.getNewValue();
                break;    
            case Y:
                oldY = change.getOldValue();
                newY = change.getNewValue();
                break;
            case WIDTH:
                oldWidth = change.getOldValue();
                newWidth = change.getNewValue();
                break;
            case HEIGHT:
                oldHeight = change.getOldValue();
                newHeight = change.getNewValue();
                break;
        }
    }

    @Override
    public void redo() {
        if (newX != Double.NaN) { node.setLayoutX(newX); }
        if (newY != Double.NaN) { node.setLayoutX(newY); }
        if (newModeX.equals("Manual")) {
            if (newWidth != Double.NaN) { node.setPrefWidth(newWidth); }
        } else {
            if (newPadX != Double.NaN) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*newPadX), 0));
            }
        }
        if (newModeY.equals("Manual")) {
            if (newHeight != Double.NaN) { node.setPrefHeight(newHeight); }
        } else {
            if (newPadY != Double.NaN) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*newPadY), 0));
            }
        }
        
    };
    
    @Override
    public void undo() {
        if (oldX != Double.NaN) { node.setLayoutX(oldX); }
        if (oldY != Double.NaN) { node.setLayoutX(oldY); }
        if (oldModeX.equals("Manual")) {
            if (oldWidth != Double.NaN) { node.setPrefWidth(oldWidth); }
        } else {
            if (oldPadX != Double.NaN) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*oldPadX), 0));
            }
        }
        if (oldModeY.equals("Manual")) {
            if (oldHeight != Double.NaN) { node.setPrefHeight(oldHeight); }
        } else {
            if (oldPadY != Double.NaN) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*oldPadY), 0));
            }
        }
        
    };

    @Override
    public Optional<BChange> mergeWith(BChange other) {
        if (other instanceof PlacementChange) {
            PlacementChange pc = (PlacementChange) other;
            if (node == pc.node) {
                // This might be wrong:
                return Optional.of((BChange) new PlacementChange(node,
                    oldModeX, oldModeY, oldPadX, oldPadY,
                    oldX, oldY, oldWidth, oldHeight,
                    pc.newModeX, pc.newModeY, pc.newPadX, pc.newPadY,
                    pc.newX, pc.newY, pc.newWidth, pc.newHeight));
            }
        }
        return Optional.empty();
    }

}
