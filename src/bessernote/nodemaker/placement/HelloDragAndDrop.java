/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote.nodemaker.placement;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class HelloDragAndDrop extends Application {

    double startX = -1;
    double startY = -1;

    @Override
    public void start(Stage stage) {

        stage.setTitle("Hello Drag And Drop");

        Group root = new Group();
        Scene scene = new Scene(root, 400, 200);
        scene.setFill(Color.LIGHTGREEN);
 
        final Text source = new Text(50, 100, "DRAG ME");
        source.setScaleX(2.0);
        source.setScaleY(2.0);
        DraggingUtil.enableResizeDrag(source);
        /*
        source.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {         
                    out("mouse pressed");
//                    startX = event.getSceneX();
//                    startY = event.getSceneY();
                    startX = event.getX();
                    startY = event.getY();
                    
                    out("startX: %f\n startY: %f",startX,startY);
                    out("currX: %f\n currY: %f",source.getX(),source.getY());
                }
            }
        });
        source.addEventFilter(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            if (startX >= 0 && startY >= 0) {
                                double deltaX =  e.getSceneX() - startX;
                                double deltaY = e.getSceneY() - startY;
//                                out("deltaX: %f\ndeltaY: %f",deltaX,deltaY);
//                                out("currX: %f\ncurrY: %f",e.getX(),e.getY());
//                                source.setTranslateX(deltaX);
//                                source.setTranslateY(deltaY);
//                                source.setLayoutX(source.getX( ) + deltaX);
//                                source.setLayoutY(source.getY() + deltaY);
                                source.relocate(source.getX() + deltaX,source.getY() +  deltaY);
                            }
                        }
                    }
                }
        );
        
        
        */
        
        
        
        /*
        source.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                startX = event.getSceneX();
                startY = event.getSceneY();
                
                out("release\t startX: %f startY: %f", startX,startY);     
            }
            
        });*/
//        
//        source.addEventFilter(MouseEvent.MOUSE_RELEASED,new EventHandler<MouseEvent>(){
//
//            @Override
//            public void handle(MouseEvent event) {
//                startX = -1;
//                startY = -1;
//            }
//            
//        });

//        final Text target = new Text(250, 100, "DROP HERE");
//        target.setScaleX(2.0);
//        target.setScaleY(2.0);
//
//        source.setOnDragDetected(new EventHandler <MouseEvent>() {
//            public void handle(MouseEvent event) {
//                /* drag was detected, start drag-and-drop gesture*/
//                System.out.println("onDragDetected");
//                
//                /* allow any transfer mode */
//                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
//                
//                /* put a string on dragboard */
//                ClipboardContent content = new ClipboardContent();
//                content.putString(source.getText());
//                db.setContent(content);
//                
//                event.consume();
//            }
//        });
//
//        target.setOnDragOver(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* data is dragged over the target */
//                System.out.println("onDragOver");
//                
//                /* accept it only if it is  not dragged from the same node 
//                 * and if it has a string data */
//                if (event.getGestureSource() != target &&
//                        event.getDragboard().hasString()) {
//                    /* allow for both copying and moving, whatever user chooses */
//                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//                }
//                
//                event.consume();
//            }
//        });
//
//        target.setOnDragEntered(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* the drag-and-drop gesture entered the target */
//                System.out.println("onDragEntered");
//                /* show to the user that it is an actual gesture target */
//                if (event.getGestureSource() != target &&
//                        event.getDragboard().hasString()) {
//                    target.setFill(Color.GREEN);
//                }
//                
//                event.consume();
//            }
//        });
//
//        target.setOnDragExited(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* mouse moved away, remove the graphical cues */
//                target.setFill(Color.BLACK);
//                
//                event.consume();
//            }
//        });
//        
//        target.setOnDragDropped(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* data dropped */
//                System.out.println("onDragDropped");
//                /* if there is a string data on dragboard, read it and use it */
//                Dragboard db = event.getDragboard();
//                boolean success = false;
//                if (db.hasString()) {
//                    target.setText(db.getString());
//                    success = true;
//                }
//                /* let the source know whether the string was successfully 
//                 * transferred and used */
//                event.setDropCompleted(success);
//                
//                event.consume();
//            }
//        });
//
//        source.setOnDragDone(new EventHandler <DragEvent>() {
//            public void handle(DragEvent event) {
//                /* the drag-and-drop gesture ended */
//                System.out.println("onDragDone");
//                /* if the data was successfully moved, clear it */
//                if (event.getTransferMode() == TransferMode.MOVE) {
//                    source.setText("");
//                }
//                
//                event.consume();
//            }
//        });
//
        root.getChildren().add(source);
//        root.getChildren().add(target);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void out(String s, Object... o) {
        System.out.println(String.format(s, o));
    }

    public void out(String s) {
        System.out.println(s);
    }
}
