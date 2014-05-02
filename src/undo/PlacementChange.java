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
public class PlacementChange extends BChange {
    
    public static final int MODE_X = 0;
    public static final int MODE_Y = 1;
    public static final int PAD_X = 2;
    public static final int PAD_Y = 3;
    public static final int X = 4;
    public static final int Y = 5;
    public static final int WIDTH = 6;
    public static final int HEIGHT = 7;
    
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
    
    // somehow this doesn't work without the pointles bool
    protected PlacementChange(Region node, int property, Double oldVal, Double newVal, Boolean b) {
        this.node = node;
        switch (property) {
            case PAD_X:
                oldPadX = oldVal;
                newPadX = newVal;
                break;
            case PAD_Y:
                oldPadY = oldVal;
                newPadY = newVal;
                break;
            case X:
                oldX = oldVal;
                newX = newVal;
                break;    
            case Y:
                oldY = oldVal;
                newY = newVal;
                break;
            case WIDTH:
                oldWidth = oldVal;
                newWidth = newVal;
                break;
            case HEIGHT:
                oldHeight = oldVal;
                newHeight = newVal;
                break;
        }
    }

    @Override
    public void redo() {
        if (!Double.isNaN(newX)) { node.setLayoutX(newX); }
        if (!Double.isNaN(newY)) { node.setLayoutY(newY); }
        if (node.prefWidthProperty().isBound()) {
            if (!Double.isNaN(newPadX)) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*newPadX), 0));
            }
        } else {
            if (!Double.isNaN(newWidth)) { node.setPrefWidth(newWidth); }
        }
        if (node.prefHeightProperty().isBound()) {
            if (!Double.isNaN(newPadY)) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*newPadY), 0));
            }
        } else {
            if (!Double.isNaN(newHeight)) { node.setPrefHeight(newHeight); }
        }
    };
    
    @Override
    public void undo() {
        if (!Double.isNaN(oldX)) { node.setLayoutX(oldX); }
        if (!Double.isNaN(oldY)) { node.setLayoutY(oldY); }
        if (node.prefWidthProperty().isBound()) {
            if (!Double.isNaN(oldPadX)) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefWidthProperty().bind(Bindings.max(parent.widthProperty().subtract(2*oldPadX), 0));
            }
        } else {
            if (!Double.isNaN(oldWidth)) { node.setPrefWidth(oldWidth); }
        }
        if (node.prefHeightProperty().isBound()) {
            if (!Double.isNaN(oldPadY)) {
                Region parent = (Region) node.parentProperty().getValue();
                node.prefHeightProperty().bind(Bindings.max(parent.heightProperty().subtract(2*oldPadY), 0));
            }
        } else {
            if (!Double.isNaN(oldHeight)) { node.setPrefHeight(oldHeight); }
        }
        
    };

    @Override
    public Optional<BChange> mergeWith(BChange other) {
        if (other instanceof PlacementChange) {
            PlacementChange pc = (PlacementChange) other;
            if (node == pc.node) {
                // This might be wrong:
                String omx = (oldModeX == null) ? pc.oldModeX : oldModeX;
                String omy = (oldModeY == null) ? pc.oldModeY : oldModeY;
                Double opx = Double.isNaN(oldPadX) ? pc.oldPadX : oldPadX;
                Double opy = Double.isNaN(oldPadY) ? pc.oldPadY : oldPadY;
                Double ox = Double.isNaN(oldX) ? pc.oldX : oldX;
                Double oy = Double.isNaN(oldY) ? pc.oldY : oldY;
                Double ow = Double.isNaN(oldWidth) ? pc.oldWidth : oldWidth;
                Double oh = Double.isNaN(oldHeight) ? pc.oldHeight : oldHeight;
                
                String nmx = (pc.newModeX == null) ? newModeX : pc.newModeX;
                String nmy = (pc.newModeY == null) ? newModeY : pc.newModeY;
                Double npx = Double.isNaN(pc.newPadX) ? newPadX : pc.newPadX;
                Double npy = Double.isNaN(pc.newPadY) ? newPadY : pc.newPadY;
                Double nx = Double.isNaN(pc.newX) ? newX : pc.newX;
                Double ny = Double.isNaN(pc.newY) ? newY : pc.newY;
                Double nw = Double.isNaN(pc.newWidth) ? newWidth : pc.newWidth;
                Double nh = Double.isNaN(pc.newHeight) ? newHeight : pc.newHeight;
                
                return Optional.of(new PlacementChange(node,
                    omx, omy, opx, opy, ox, oy, ow, oh,
                    nmx, nmy, npx, npy, nx, ny, nw, nh));
            }
        }
        return Optional.empty();
    }

}
