/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bessernote.nodemaker.placement;

import java.util.EventListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Dan
 */
public class DraggingUtil {
//    distance from the corner that will control a special action
    public static double distanceFromCorner = 10;
    /**
     * @return true if the given coordinate pair (x,y) is in the bottom right special action area of node n
     * If the user starts dragging from this area, then the node should be scaled
    */
    public static boolean toScaleArea(Node n, double x, double y){
        if(!n.contains(x, y)){
            return false;
        }
        Bounds b = n.getBoundsInLocal();
        double edgeX = n.getLayoutX() + b.getWidth();
        double edgeY = n.getLayoutY() + b.getHeight();
        return x > edgeX - distanceFromCorner && y > edgeY - distanceFromCorner;
    }
    
     /**
     * @return true if the given coordinate pair (x,y) is in the top left 10 x 10 px area of node n
     * If the user starts dragging from this area, then the node should be scaled
    */
    public static boolean toDragArea(Node n, double x, double y){
        if(!n.contains(x, y)){
            return false;
        }
        Bounds b = n.getBoundsInLocal();
        double edgeX = n.getLayoutX();
        double edgeY = n.getLayoutY();
        return x < edgeX + distanceFromCorner && y < edgeY + distanceFromCorner;
    }
    
    /**
     * adds listeners to the node enabling dragging and zooming
     */
    public static void enableResizeDrag(Node toMod){
        
        
//        EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
//            private double startX = -1;
//            private double startY = -1;
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.getButton() == MouseButton.PRIMARY) {         
//                    out("mouse pressed");
//
//                    startX = event.getX();
//                    startY = event.getY();
//                    
//                    out("startX: %f\n startY: %f",startX,startY);
//                    out("currX: %f\n currY: %f",source.getX(),source.getY());
//                }
//            }
//            
//            public double getStartX(){ return startX;}
//            public double getStartY(){ return startY;}
//            
//        };
        
        PressedHandler pressed = new PressedHandler();
        EventHandler<MouseEvent> dragged =  new EventHandler<MouseEvent>() {
                    private PressedHandler pressed;
                    private Node toMod;
                    @Override
                    public void handle(MouseEvent e) {
                        double startX = pressed.getStartX();
                        double startY = pressed.getStartY();
                        out("startX: %f\nstartY: %f",startX,startY);
                        if (e.getButton() == MouseButton.PRIMARY) {
                            if (startX >= 0 && startY >= 0) {
                                double deltaX =  e.getSceneX() - startX;
                                double deltaY = e.getSceneY() - startY;
                                out("deltaX: %f\ndeltaY: %f",deltaX,deltaY);
                                out("currX: %f\ncurrY: %f",e.getX(),e.getY());

//                                toMod.relocate(toMod.getLayoutX()+ deltaX,toMod.getLayoutY() +  deltaY);
                                toMod.relocate(toMod.getBoundsInLocal().getMinX()+ deltaX,toMod.getBoundsInLocal().getMinY() +  deltaY);
                            }
                        }
                    }
                    
                    public EventHandler<MouseEvent> init(PressedHandler p, Node n){
                        pressed = p;
                        toMod = n;
                        return this;
                    }
        } .init(pressed,toMod);
        toMod.addEventHandler(MouseEvent.MOUSE_PRESSED , pressed);
        toMod.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragged);
        
               
//        
//        
//        dEventFilter(MouseEvent.MOUSE_DRAGGED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        if (e.getButton() == MouseButton.PRIMARY) {
//                                toMod.relocate(toMod.getX() + deltaX,toMod.getY() +  deltaY);
//                                
//                            }
//                        }
//                    }
//                }
//        );
//        toMod.addEventFilter(MouseEvent.MOUSE_DRAGGED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        if (e.getButton() == MouseButton.PRIMARY) {
//                            if (startX >= 0 && startY >= 0) {
//                                double deltaX =  e.getSceneX() - startX;
//                                double deltaY = e.getSceneY() - startY;
////                                out("deltaX: %f\ndeltaY: %f",deltaX,deltaY);
////                                out("currX: %f\ncurrY: %f",e.getX(),e.getY());
////                                source.setTranslateX(deltaX);
////                                source.setTranslateY(deltaY);
////                                source.setLayoutX(source.getX( ) + deltaX);
////                                source.setLayoutY(source.getY() + deltaY);
//                                toMod.relocate(toMod.getX() + deltaX,toMod.getY() +  deltaY);
//                                
//                            }
//                        }
//                    }
//                }
//        );           if (startX >= 0 && startY >= 0) {
//                                double deltaX =  e.getSceneX() - startX;
//                                double deltaY = e.getSceneY() - startY;
////                                out("deltaX: %f\ndeltaY: %f",deltaX,deltaY);
////                                out("currX: %f\ncurrY: %f",e.getX(),e.getY());
////                                source.setTranslateX(deltaX);
////                                source.setTranslateY(deltaY);
////                                source.setLayoutX(source.getX( ) + deltaX);
////                                source.setLayoutY(source.getY() + deltaY);
//                 
    }
    
    public static void out(Object o){
        System.out.println(o);
    }
    
    public static void out(String s, Object... o){
        System.out.println(String.format(s, o));
    }
    
    private static class PressedHandler implements EventHandler<MouseEvent>{
        private double startX = -1;
        private double startY = -1;
        
        
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY) {         
                    out("MOUSE pressed");
                    out("startX: %f\n startY: %f",startX,startY);
                    startX = event.getX();
                    startY = event.getY();
                    
                    out("startX: %f\n startY: %f",startX,startY);
                    out("end of MOUSE pressed");
//                    out("currX: %f\n currY: %f",source.getX(),source.getY());
                }
            }
            
            public double getStartX(){ return startX;}
            public double getStartY(){ return startY;}
            
        }
    
        public static void main(String[] args){
            
        }
    }
    
    

