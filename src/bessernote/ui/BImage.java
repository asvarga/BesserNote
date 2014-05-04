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
public class BImage extends BWrapPane implements ChildSpecifier{
    
    private FileChooser fileChooser;
    private String path;
    private ImageView imageViewer = new ImageView();
    
    /**
     *
     * @param undoManager
     */
    public BImage(BUndoManager undoManager){
        super(undoManager);
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
        path = file.getPath();
        //Set image
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewer.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(BImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println(this.getPrefWidth());
        //We want a little bit of border
        //System.out.println(((Pane) BImage.this.getChildren().get(0)).getPrefHeight());
        imageViewer.setFitHeight(((Pane) BImage.this.getChildren().get(0)).getPrefHeight() - 5);
        imageViewer.setFitWidth(((Pane) BImage.this.getChildren().get(0)).getPrefWidth() - 5);
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
    
    public void createExistingImage(String path){
        File imagePath = new File(path);
                //Set image
        try {
            BufferedImage bufferedImage = ImageIO.read(imagePath);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageViewer.setImage(image);
        } catch (IOException ex) {
            Logger.getLogger(BImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        //We want a little bit of border
        imageViewer.setFitHeight(BImage.this.getPrefHeight() - 5);
        imageViewer.setFitWidth(BImage.this.getPrefWidth() - 5);
        this.getChildren().add(imageViewer);     
    }
    
    public String returnPath(){
        return path;
    }
    
    public void resizeImage(double x, double y){
        imageViewer.setFitHeight(y);
        imageViewer.setFitWidth(x);       
    }

}
