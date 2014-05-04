/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.ui;

import bessernote.ChildSpecifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import undo.BUndoManager;

/**
 *
 * @author ddliu
 * A BImage has an image inside a pane and a caption that can be edited if you double click on the picture.
 */
public class BImage extends Pane implements ChildSpecifier{
    
    private FileChooser fileChooser;
    private Image image;
    private ImageView imageViewer = new ImageView();
    
    /**
     *
     * @param undoManager
     * @param fileChooser
     */
    public BImage(BUndoManager undoManager){
        fileChooser = new FileChooser();
        imageViewer.setPreserveRatio(true);
        imageViewer.setSmooth(true);
        this.setCache(true);

        //Set extension filter
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image files (JPG, PNG, GIF, TIF)", "*.jpg", "*.png", "*.gif", "*.tif"));//, "*.png". "*.gif", "*.tif"));

                
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
                            remItem.setVisible(true);
                        }
                        for (Node addItem : c.getAddedSubList()) {
                            addItem.setVisible(true);
                        }
                        //fixOutline();
                    }
                }
            }
        });
        

    }
    
    public void createImage(){
         //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        
        //Set image
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewer.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(BImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(this.getPrefWidth());
        imageViewer.setFitHeight(BImage.this.getPrefHeight());
        imageViewer.setFitWidth(BImage.this.getPrefWidth());
        this.getChildren().add(imageViewer);
    }
    
    @Override
    public List<Node> specifyChildren() {
        return this.getChildren();
    }

    @Override
    public Node specifySelf() {
        return this;
    }
    
    
    
    
}
