/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author avarga
 */
public class DashedBox extends Pane {
    public DashedBox(String color1, String color2, String dashArray, double lineWidth) {
        Rectangle rect2 = new Rectangle();
        rect2.setStyle("-fx-fill: transparent;"
                + "-fx-stroke: "+color2+";"
                + "-fx-stroke-width: "+Double.toString(lineWidth)+";");
        rect2.widthProperty().bind(widthProperty());
        rect2.heightProperty().bind(heightProperty());
        Rectangle rect1 = new Rectangle();
        rect1.setStyle("-fx-stroke-dash-array: "+dashArray+";"
                + "-fx-fill: transparent;"
                + "-fx-stroke: "+color1+";"
                + "-fx-stroke-width: "+Double.toString(lineWidth)+";"
                + "-fx-stroke-line-cap: butt");
        rect1.widthProperty().bind(widthProperty());
        rect1.heightProperty().bind(heightProperty());
        getChildren().add(rect2);
        getChildren().add(rect1);
    }
}
