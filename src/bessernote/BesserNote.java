/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bessernote;

import bessernote.nodemaker.DockingMenu;
import bessernote.nodemaker.NodeGUI;
import bessernote.nodemaker.placement.DraggingUtil;
import bessernote.ui.BFlashCard;
import bessernote.ui.BImage;
import bessernote.ui.BScrollPane;
import bessernote.ui.BTabPane;
import bessernote.ui.BTextArea;
import bessernote.ui.BWrapPane;
import com.sun.javafx.runtime.VersionInfo;
import com.thoughtworks.xstream.XStream;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import saving.BEditableTabSave;
import saving.BFlashCardSave;
import saving.BImageSave;
import saving.BScrollPaneSave;
import saving.BTabPaneSave;
import saving.BTextAreaSave;
import saving.BWrapPaneSave;
import saving.DoodleSave;
import saving.Loader;
import saving.PaneSave;
import saving.RootSave;
import saving.Saveable;
import saving.Saver;
import saving.lineToSave;
import saving.moveToSave;
import undo.AddChange;
import undo.BUndoManager;
import undo.DeleteChange;

/**
 *
 * @author avarga
 */
public class BesserNote extends Application {

    private Stage stage;
    private Scene scene;
    private BorderPane root;
    private MenuBar menuBar;
    private StackPane stackPane;
    public Pane sheet;
    private Pane above;
    
    private DashedBox dragBox;
    
    public Map<Node, DashedBox> selectBoxes;
    private Node superSelected;
    private int superIndex;
    //private DashedBox superBox;
    public List<Node> superClicked;
    
    private Map<Node, Double> dragOffsetX;
    private Map<Node, Double> dragOffsetY;
    private HashSet<Parent> toResize;
    private boolean dragging = false;
    private boolean resizing = false;
    
    private Popup popup;
    private NodeGUI nodeGUI;
    private Window primaryStage;
    private Desktop desktop = Desktop.getDesktop();
    private FileChooser fileChooser = new FileChooser();
    //private GraphicsContext gc;
    
    private DrawCanvas drawCanvas;
    
    private DockingMenu dockingMenu;
    
    public double startOutlineX;
    public double startOutlineY;
    public Parent target;
    
    //For copying and pasting.
    private Saveable copied;
    private XStream xstream = new XStream();

    
    ///Drawing Canvases
    //private DrawCanvas drawCanvas = new DrawCanvas(this, 2000, 2000);
    //private Canvas circleCanvas = new Canvas();
    private Color c = Color.WHITE;
    
    private BUndoManager undoManager;
    
    //

    @Override
    public void start(final Stage stage) throws IOException {
        this.stage = stage;
        System.out.println("JavaFX Version: "+VersionInfo.getRuntimeVersion());// VersionInfo.getRuntimeVersion())‌​;
        
        root = new BorderPane();        
        scene = new Scene(root, 640, 480, Color.BLACK);
        
        stackPane = new StackPane();
        root.setCenter(stackPane);

        sheet = new Pane();        
        sheet.setStyle("-fx-background-color: #000000");
        stackPane.getChildren().add(sheet);
        target = sheet;
        
        above = new Pane();
        above.setMouseTransparent(true);    // doesn't block clicks
        stackPane.getChildren().add(above);
                
        dragBox = new DashedBox(new String[]{"red", "yellow", "green"}, 10, 3);
        dragBox.setVisible(false);
        above.getChildren().add(dragBox);
        

        ///Add aliases ///
        xstream.alias("scrollPane", BScrollPaneSave.class);
        xstream.alias("tab", BEditableTabSave.class);
        xstream.alias("tabPane", BTabPaneSave.class);
        xstream.alias("flashcard", BFlashCardSave.class);
        xstream.alias("wrapPane", BWrapPaneSave.class);
        xstream.alias("textarea", BTextAreaSave.class);
        xstream.alias("root", RootSave.class);
        xstream.alias("pane", PaneSave.class);
        xstream.alias("image", BImageSave.class);
        xstream.alias("drawing", DoodleSave.class);
        xstream.alias("lineto", lineToSave.class);
        xstream.alias("moveto", moveToSave.class);
        
        //// SELECTION ////
        
        selectBoxes = new HashMap<>();


        undoManager = new BUndoManager();

        
        addSheetListeners();

        //// NODE MAKER ////


        popup = new Popup();
        nodeGUI = new NodeGUI(5);
        popup.getContent().addAll(nodeGUI);
        popup.setAutoFix(false);
        popup.setHideOnEscape(true);
        
        dockingMenu = new DockingMenu(nodeGUI, this);
        root.setLeft(dockingMenu);
        
        popup.addEventFilter(KeyEvent.KEY_PRESSED, 
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent e) {
                    if (e.getCode().equals(KeyCode.ENTER)) {
                        createNode();
                        dragBox.setVisible(false);
                    }
                };
            }
        );
        
        nodeGUI.createButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                createNode();
                dragBox.setVisible(false);
            }
        });
        
        nodeGUI.cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                popup.hide();
                dragBox.setVisible(false);
            }
        });
        
        
        
        //// PAINTING MENU ////
        
        
        


        //// MENU BAR ////

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BSSR files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        menuBar = new MenuBar();
        
        //Initialize Image filechooser
        

        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New...");
        menuItemNew.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(final ActionEvent e){
                Application app2 = new BesserNote();
                Stage anotherStage = new Stage();
                try {
                    app2.start(anotherStage);
                } catch (Exception ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        MenuItem menuItemOpen = new MenuItem("Open...");
        menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                        try {
                            openFile(file);
                        } catch (IOException ex) {
                            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        });
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        MenuItem menuItemSave = new MenuItem("Save");
        menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t){
                try {
                    saveFile();
                } catch (IOException ex) {
                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menuFile.getItems().addAll(
                menuItemNew,
                menuItemOpen,
                menuItemSave,
                new SeparatorMenuItem(),
                menuItemExit);

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        
        MenuItem menuItemUndo = new MenuItem("Undo");
        menuItemUndo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                undoManager.undo();
            }
        });
        menuEdit.getItems().addAll(menuItemUndo);
        
        MenuItem menuItemRedo = new MenuItem("Redo");
        menuItemRedo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                undoManager.redo();
            }
        });
        menuEdit.getItems().addAll(menuItemRedo);
        
        MenuItem menuItemAdd = new MenuItem("Add");
        menuItemAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                popup.show(stage);
            }
        });
        menuEdit.getItems().addAll(menuItemAdd);
//        
//        MenuItem menuItemDraw = new MenuItem("Draw");
//        menuItemDraw.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent t){
//                Popup drawingMenu = new Popup();
//                try {
//                    drawingMenu.getContent().addAll(new DrawingMenu(BesserNote.this));
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                drawingMenu.show(stage, scene.getX(), scene.getY());
//            }
//        });
//        menuEdit.getItems().addAll(menuItemDraw);

        // --- Menu Help
        Menu menuHelp = new Menu("Help");
        MenuItem menuItemHelp = new MenuItem("Help");
        menuItemHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t){
                Popup helpMenu = new Popup();
                VBox helpStuff = new VBox();
                helpStuff.setStyle("-fx-background-color: #ffffff");
                helpStuff.setPrefSize(500, 800);
                Text tutorial1 = new Text("   --- Welcome to BesserNote! ---     (Press esc to quit this brief tutorial.) \n");
                Text tutorial2 = new Text("  The basic idea of BesserNote is to give note takers as much flexibility as possible.\n");
                Text tutorial3 = new Text("  The most basic levels of organization are panes and wrap panes.\n");
                Text tutorial4 = new Text("  A wrap pane adjusts its dimensions to objects that move and resize in it. A pane is static.\n");
                Text tutorial5 = new Text("  More complicated organizational tools are scrollpanes, which have virtually unlimited space because of scrolling in two dimensions.");
                Text tutorial6 = new Text("  A tab pane can create multiple tabs, which help divide topics.\n");
                Text tutorial7 = new Text("  A flashcard has two sides. The left side is the `front` while the right side is the `back`. Click on the front and mouse over it to");
                Text tutorial8 = new Text("  make the back disappear. Flashcards! \n \n Images can also be easily inserted.");
                
                
                Text tutorial9 = new Text("  The best part is that all of these elements are customizable and nestable. You can change color, size, position, and children. \n");
                Text tutorial10 = new Text("  Literally everything can contain anything else. You could have 100 tab panes nested inside of each other.\n");
                
                Text tutorial11 = new Text("  Click on one of the icons on the left, right click, and drag out a shape to start inserting things.\n \n");
                
                Text tutorial12 = new Text("           -------- ADVANCED FEATURES ------- \n");
                Text tutorial13 = new Text("  We have invented a feature called the superclick. If you have multiple things on top of each other, simply press tab to cycle through them. \n");
                Text tutorial14 = new Text("  Undo and redo changes are Cmmd+Z and Cmmd+Y \n");
                Text tutorial15 = new Text("  Draw by going to edit and then draw. Select the pencil icon to draw lines. \n");
                Text tutorial16 = new Text("  Open old bessernote projects by clicking open, and save by clicking save. Easy! \n");
                
                Text tutorial18 = new Text("  We want BesserNote to be easy and intuitive to use. Hopefully you find that it is. \n (Press esc to quit)");
                
                
                Text tutorial17 = new Text("        --------- Keyboard Shortcuts ------- \n CMD + P = Pane \n CMD + W = WrapPane \n CMD + I = TextArea \n CMD + L = ScrollPane \n CMD + T = TabPane \n CMD + F = FlashCard \n CMD + M = Image \n \n" );
                
                
                helpStuff.getChildren().addAll(tutorial1, tutorial2, tutorial3, tutorial4, tutorial5, tutorial6, tutorial7, tutorial8, tutorial9, tutorial10, tutorial11);
                helpStuff.getChildren().addAll(tutorial12, tutorial13, tutorial14, tutorial15, tutorial16, tutorial17, tutorial18);
                helpMenu.getContent().addAll(helpStuff);
                helpMenu.show(sheet, scene.getWidth()/3, scene.getHeight()/3);
                
                
                Popup labelMenu = new Popup();
                Text text1 = new Text("Pane \n (CMD + P) \n \n \n WrapPane \n (CMD + W) \n \n \n \n Text \n (CMD + I) \n \n \n \n ScrollPane \n (CMD + L) \n \n \n  \n TabPane \n  (CMD + T) \n \n \n \n FlashCard \n (CMD + F) \n \n \n Image \n (CMD + M)");
                text1.setFill(Color.RED);
                labelMenu.getContent().addAll(text1);
                labelMenu.show(sheet, 2, 260);
                
            }
        });
        menuHelp.getItems().addAll(menuItemHelp);
    

        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);
        root.setTop(menuBar);

        //// Playing with drawing. ////

        drawCanvas = new DrawCanvas(this);//, sheet.getPrefWidth(), sheet.getPrefHeight());
        stage.widthProperty().addListener(new ChangeListener() {
            @Override public void changed(ObservableValue ov, Object t, Object t1) {
                drawCanvas.setWidth(1);
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        drawCanvas.setWidth(sheet.getWidth());
                        showAllSelections();
                    }                 
                });
            }
        });
        stage.heightProperty().addListener(new ChangeListener() {
            @Override public void changed(ObservableValue ov, Object t, Object t1) {
                drawCanvas.setHeight(1);
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        drawCanvas.setHeight(sheet.getHeight());
                        showAllSelections();
                    }                 
                });
            }
        });
//        drawCanvas.widthProperty().bind(sheet.widthProperty());
//        drawCanvas.heightProperty().bind(sheet.heightProperty());
        stackPane.getChildren().add(drawCanvas);
        drawCanvas.toBack();
//        drawOn();

//        Rectangle r = new Rectangle();
//        r.setX(50);
//        r.setY(50);
//        r.setWidth(200);
//        r.setHeight(100);
//        r.setArcWidth(20);
//        r.setArcHeight(20);
//        r.setFill(Color.WHITE);

        
        stage.setMaximized(true);
        
        stage.setTitle("BesserNote"); 
        stage.setScene(scene); 
        stage.show(); 
        //System.out.println(sheet.getWidth());
    }
    
//    public void begin(final Stage stage) {
//        
//    }
    
    public void addSheetListeners(){
        
        ///Key listeners

        scene.addEventFilter(KeyEvent.KEY_PRESSED,
            new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode() == KeyCode.TAB) {
                        
                        if (superClicked.size() > 0) {
                            if (event.isShiftDown()) {
                                selectPrev();
                            } else {
                                selectNext();
                            }
                        }
                        event.consume();
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        dockingMenu.setDrawOff();                        
                        cancelSuperClick();
                        unselectAll();
                        dragBox.setVisible(false);
                    }
                    else if (event.getCode() == KeyCode.BACK_SPACE && event.isShortcutDown()){
                        for(Map.Entry<Node, DashedBox> entry: selectBoxes.entrySet()){
                            Node deleteMe = entry.getKey();
                            if (deleteMe != sheet) {
                                Pane parent = (Pane) deleteMe.getParent();
                                parent.getChildren().remove(deleteMe);
                                undoManager.addChange(new DeleteChange(deleteMe, parent));
                            }
                        }
                        
                        unselectAll();
                    }
                    //Copy
                    else if (event.getCode() == KeyCode.C && event.isShortcutDown()){
                        copy();
                    }
                    
                    //Paste
                    else if (event.getCode() == KeyCode.V && event.isShortcutDown()){
                        paste();
                    }
                    else if (event.getCode() == KeyCode.Z && event.isShortcutDown()){
                        undoManager.undo();
                    }
                    else if (event.getCode() == KeyCode.Y && event.isShortcutDown()){
                        undoManager.redo();
                    }
                    // Listeners to change the insertion mode.
                    else if(event.getCode() == KeyCode.D && event.isShortcutDown()){
                        dockingMenu.setDrawMode();
                    }
                    else if (event.getCode() == KeyCode.K && event.isShortcutDown()){
                        dockingMenu.setDeckMode();
                    }
                    else if(event.getCode() == KeyCode.P && event.isShortcutDown()){
                        dockingMenu.setPaneMode();
                    }
                    else if (event.getCode() == KeyCode.W && event.isShortcutDown()){
                        dockingMenu.setWrapPaneMode();
                    }
                    else if (event.getCode() == KeyCode.L && event.isShortcutDown()){
                        dockingMenu.setCircleMode();
                    }
                    else if (event.getCode() == KeyCode.T && event.isShortcutDown()){
                        dockingMenu.setTabPaneMode();
                    }
                    else if (event.getCode() == KeyCode.F && event.isShortcutDown()){
                        dockingMenu.setFlashCardMode();
                    }
                    else if (event.getCode() == KeyCode.L && event.isShortcutDown()){
                        dockingMenu.setScrollPaneMode();
                    }
                    else if (event.getCode() == KeyCode.I && event.isShortcutDown()){
                        dockingMenu.setTextAreaMode();
                    }
                    else if (event.getCode() == KeyCode.M && event.isShortcutDown()){
                        dockingMenu.setImageMode();
                    }
                    else if (event.getCode() == KeyCode.S && event.isShortcutDown()){
                        try {
                            saveFile();
                        } catch (IOException ex) {
                            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if (event.getCode() == KeyCode.O && event.isShortcutDown()){
                        File file = fileChooser.showOpenDialog(stage);
                        try {
                            openFile(file);
                        } catch (IOException ex) {
                            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                }
            }
        );
        
        sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        sheet.requestFocus();
                        if (!e.isAltDown()) {
                            if (!e.isShiftDown()) {
                                unselectAll();
                            }
                            cancelSuperClick();
                            superClicked = superClick(e.getX(), e.getY());
                            if (superClicked.size() > 0) {
                                flipSelection(0);
                            }
                        }
                    }
                    
                };
            }
        );
                
        //// DRAG ////
        
        sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) { // && e.isAltDown()
                        if (superSelected != null) {
                            dragOffsetX = new HashMap<>();
                            dragOffsetY = new HashMap<>();
                            toResize = new HashSet<Parent>();
                            for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                                Node n = entry.getKey();
//                                checks if the bottom right corner was picked and adds toResize if selected
                                if( DraggingUtil.toScaleArea(n, e.getX(), e.getY()) && n instanceof Parent){
                                    toResize.add((Parent)n);
                                }
                                
                                
                                Point2D local = sheetToLocal(
                                    n.getParent(), e.getX(), e.getY());
                                dragOffsetX.put(n, local.getX()-n.getLayoutX());
                                dragOffsetY.put(n, local.getY()-n.getLayoutY());
                            }
                            dragging = true;
                            if(!toResize.isEmpty()){
                                //out("RESIZING BITCHES");
                                resizing = true;
                            }
                            else{
                                //out("NOT RESIZING");
                            }
                        }
                    }
                };
            }
        );
        
        sheet.addEventFilter(MouseEvent.MOUSE_DRAGGED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY && dragging) {
                        if (superSelected != null) {
                            if(!resizing){
                                
                            
                            for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                                Node n = entry.getKey();
                                Point2D local = sheetToLocal(
                                    n.getParent(), e.getX(), e.getY());
                                try {   // can't set if bound
                                    double offset = dragOffsetX.get(n);
                                    n.setLayoutX(local.getX()-offset);
                                } catch (Exception ex) {}
                                try {   // can't set if bound
                                    double offset = dragOffsetY.get(n);
                                    n.setLayoutY(local.getY()-offset);
                                } catch (Exception ex) {}
                                showSelection(n);
                            }
                            }
                            else{//resizing
                                for(Parent n: toResize){
//                                    double offsetX = dragOffsetX.get(n);
//                                    double offsetY = dragOffsetY.get(n);
                                    double offsetX = e.getX() - n.localToScene(Point2D.ZERO).getX();
                                    double offsetY = e.getY() - n.localToScene(Point2D.ZERO).getY();
                                    
//                                    double currWidth = n.getLayoutBounds().getWidth();
//                                    double currHeight = n.getLayoutBounds().getHeight();
//                                    double currWidth = n.getBoundsInParent().getWidth();
//                                    double currHeight = n.getBoundsInParent().getHeight();
////                                    double currWidth = n.getBoundsInLocal().getWidth();
//                                    double currHeight = n.getBoundsInLocal().getHeight();
//                                    double offsetToCurrRatioX = offsetX / currWidth;
//                                    double offsetToCurrRatioY = offsetY / currHeight;
//                                    out("offsetX: %f",offsetX);
//                                    out("offsetY: %f",offsetY);
//                                    out("currWidth: %f",currWidth);
//                                    out("currHeight: %f",currHeight);
//                                    out("ratioX: %f",offsetToCurrRatioX);
//                                    out("ratioY: %f\n\n",offsetToCurrRatioY);
////                                    out("is resizable %b",n.isResizable());
//                                    KEEP TRACK OF THE ORIGINAL WIDTH NITWIT
//                                    n.setScaleX(n.getScaleX() * offsetToCurrRatioX);
//                                    n.setScaleY(n.getScaleY() * offsetToCurrRatioY);
//                                    DoubleProperty width = new SimpleDoubleProperty(0);
//                                    width.setValue(offsetX);
//                                    width.getValue();
//                                    DoubleProperty height = new SimpleDoubleProperty(offsetY);
//                                    height.setValue(offsetY);
//                                    height.getValue();
                                 if(n instanceof BImage){
                                     BImage r = (BImage) n;
                                     try { r.setPrefHeight(offsetY); } catch (Exception ex) {}
                                     try { r.setPrefWidth(offsetX); } catch (Exception ex) {}
                                     r.autosize();
                                     try { r.resizeImage(offsetX, offsetY); } catch (Exception ex) {}
                                 }
                                 else if(n instanceof Region){
                                     Region r = (Region) n;
                                     try { r.setPrefHeight(offsetY); } catch (Exception ex) {}
                                     try { r.setPrefWidth(offsetX); } catch (Exception ex) {}
                                     r.autosize();
                                 }
                                 else if(n instanceof Group){
                                     Group g = (Group) n;
                                     try { g.prefWidth(offsetX); } catch (Exception ex) {}
                                     try { g.prefHeight(offsetY); } catch (Exception ex) {}
                                     g.autoSizeChildrenProperty();
                                     g.autosize();
                                     
                                 }
//                                 else if(n instanceof Control){
//                                     Control c = (Control) c;
//                                     
//                                 }
                                    
//                                    n.resize(offsetX, offsetY);
                                }
                            }
                        }
                    }
                };
            }
        );
        
        sheet.addEventFilter(MouseEvent.MOUSE_RELEASED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        dragging = false;
                        resizing = false;
                    }
                };
            }
        );

        //// RIGHT CLICK ////
                
        sheet.addEventFilter(MouseEvent.MOUSE_PRESSED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        startOutlineX = e.getX();
                        startOutlineY = e.getY();
                        
                        boolean clickedSelected = false;
                        
                        if (e.isShortcutDown()) {
                            for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
                                Node n = entry.getKey();
                                Point2D local = sheetToLocal(n, e.getX(), e.getY());
                                if (n instanceof Parent &&
                                        local.getX() >= 0 && 
                                        local.getY() >= 0 &&
                                        local.getX() <= n.getBoundsInLocal().getWidth() &&
                                        local.getY() <= n.getBoundsInLocal().getHeight()) {
                                    clickedSelected = true;
                                    target = (Parent) n;
                                    break;
                                }
                            }
                        }
                        
                        if (clickedSelected) {
                            cancelSuperClick();
                            unselectAll();
                            superClicked = new ArrayList<>();
                            superClicked.add(target);
                            flipSelection(0);
                        } else {
                            cancelSuperClick();
                            unselectAll();
                            superClicked = superClick(e.getX(), e.getY());
                            if (superClicked.size() > 0) {
                                Node sc0 = superClicked.get(0);
                                if (sc0 instanceof ChildSpecifier) {
                                    sc0 = ((ChildSpecifier) sc0).specifySelf();
                                }
                                if (sc0 instanceof Pane) {
                                    flipSelection(0);
                                    target = (Parent) superClicked.get(0);
                                } else {
                                    unselectAll();
                                    target = sheet;
                                }
                            } else {
                                unselectAll();
                                target = sheet;
                            }
                        }
                    }
                    
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                };
            }
        );
        sheet.addEventFilter(MouseEvent.MOUSE_DRAGGED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        double dx = startOutlineX-e.getX();
                        double dy = startOutlineY-e.getY();
                        if (dx*dx+dy*dy >= 25) {
                            dragBox.setLayoutX(Math.min(startOutlineX, e.getX()));
                            dragBox.setLayoutY(Math.min(startOutlineY, e.getY()));
                            dragBox.setPrefSize(Math.abs(dx), Math.abs(dy));
                            dragBox.setVisible(true);
                        } else {
                            dragBox.setVisible(false);
                        }
                    }
                    
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                };
            }
        );
        sheet.addEventFilter(MouseEvent.MOUSE_RELEASED, 
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        double dx = startOutlineX-e.getX();
                        double dy = startOutlineY-e.getY();
                        Node target2 = target;
                        if (target2 instanceof ChildSpecifier) {
                            target2 = ((ChildSpecifier) target2).specifySelf();
                        }
                        if (dx*dx+dy*dy >= 50) {
                            Point2D local = sheetToLocal(target2,
                                    Math.min(startOutlineX, e.getX()), 
                                    Math.min(startOutlineY, e.getY()));
                            Point2D local2 = sheetToLocal(target2,
                                    Math.max(startOutlineX, e.getX()), 
                                    Math.max(startOutlineY, e.getY()));
                            nodeGUI.setPos(local.getX(), local.getY());
                            nodeGUI.setSize(local2.getX()-local.getX(), 
                                    local2.getY()-local.getY());
                        } else {
                            Point2D local = sheetToLocal(target2,startOutlineX, startOutlineY);
                            nodeGUI.setPos(local.getX(), local.getY());
                        }
                        createNode();
                        dragBox.setVisible(false);
                        //popup.show(stage);
                    }
                    
                    // doesnt work:
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                };
            }
        );  
        
        sheet.addEventFilter(ScrollEvent.SCROLL, 
            new EventHandler<ScrollEvent>() {
                @Override public void handle(ScrollEvent t) {
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                }
            }
        );
    }

        
//    publienode void changeRoot(Pane newRoot){
//        stackPane.getChildren().remove(sheet);
//        sheet = newRoot;
//        stackPane.getChildren().add(sheet);
//        target = sheet;
//        addSheetListeners();
//        sheet.toFront();
//
//        //superClicked.clear();
//    }
    
    public Point2D sheetToLocal(Node n, double sheetX, double sheetY) {
        Point2D pointInScene = sheet.localToScene(sheetX, sheetY);
        return n.sceneToLocal(pointInScene);
    }
    
    public void flipSelection(int i) {
//        System.out.println(i);
        superIndex = i;
        superSelected = superClicked.get(superIndex);
        //System.out.println(superSelected);
        
        if (selectBoxes.containsKey(superSelected)) {
            DashedBox dashed = selectBoxes.get(superSelected);
            above.getChildren().remove(dashed);
            selectBoxes.remove(superSelected);
        } else {
            DashedBox dashed = new DashedBox(new String[]{"black", "gray", "white"}, 10, 3);
            above.getChildren().add(dashed);
            selectBoxes.put(superSelected, dashed);
            showSelection(superSelected);
        }
    }
    
    public void showSelection(Node n) {
        DashedBox dashed = selectBoxes.get(n);
        Bounds bounds = n.localToScene(n.getBoundsInLocal());
        bounds = sheet.sceneToLocal(bounds);
        dashed.setPrefSize(bounds.getWidth(), bounds.getHeight());
        dashed.setLayoutX(bounds.getMinX());
        dashed.setLayoutY(bounds.getMinY());
    }
    
    public void showAllSelections() {
        for (Map.Entry<Node, DashedBox> mapEntry : selectBoxes.entrySet()) {
           showSelection(mapEntry.getKey());
        }
    }
    
    public void selectPrev() {
        flipSelection(superIndex);
        flipSelection((superClicked.size()+superIndex-1) % superClicked.size());
    }
    
    public void selectNext() {
        flipSelection(superIndex);
        flipSelection((superIndex+1) % superClicked.size());
    }
    
    public void cancelSuperClick() {
        superClicked = null;
        superIndex = -1;
        superSelected = null;
    }
    
    public void unselectAll() {
        for (Map.Entry mapEntry : selectBoxes.entrySet()) {
            above.getChildren().remove(mapEntry.getValue());
        }
        selectBoxes = new HashMap<>();
    }
    
    public List<Node> superClick(double x, double y) {
        List<Node> ret = new ArrayList<Node>();
        superClickHelper(x, y, sheet, ret);
        return ret;
    }
    
    private void superClickHelper(double x, double y, Node n, List<Node> list) {
        // check if inside  
        Node n2 = n;
        double x2 = x;
        double y2 = y;
        if (n2 instanceof ChildSpecifier) {
            n2 = ((ChildSpecifier) n2).specifySelf();
            Point2D p2 = n2.sceneToLocal(n.localToScene(x, y));
            x2 = p2.getX();
            y2 = p2.getY();
        }
        if (n.isVisible() &&
                x >= 0 &&
                y >= 0 &&
                x <= n.getBoundsInLocal().getWidth() &&
                y <= n.getBoundsInLocal().getHeight()) {
            list.add(0, n);
            if (n instanceof Parent) {
                Parent p = (Parent) n;
                List<Node> children;
                if (p instanceof ChildSpecifier) {
                    children = ((ChildSpecifier) p).specifyChildren();
                } else {
                    children = p.getChildrenUnmodifiable();
                }
                for (Node child : children) {
                    Point2D local = child.parentToLocal(x2, y2);  
                    superClickHelper(local.getX(), local.getY(), child, list);
                }
            }
        }
    }
    
    /*
    public void replaceSheet(Pane newSheet) {
        sheet = newSheet;
        stackPane.getChildren().clear();
        stackPane.getChildren().add(sheet);
        sheet.toFront();
    }
    */
    
    public void replaceSheet(Pane newRoot){
        stackPane.getChildren().remove(sheet);
        selectBoxes.remove(sheet);
        superSelected = null;
        sheet = newRoot;
        addSheetListeners();
        stackPane.getChildren().add(sheet);
        above.toFront();
        target = sheet;
        //superClicked.clear();
    }
    
    private void openFile(File file) throws IOException {
        Loader load = new Loader(file);
        //changeRoot(load.getSheet());

        load.loadNew(undoManager);
        //System.out.println(load.getSheet(undoManager).getChildren());

//        load.loadNew();
//        for (Node child: load.getSheet().getChildren()){
//            if(child instanceof Pane){
//                Pane printMe = (Pane) child;
//                //System.out.print(printMe.getPrefWidth() + " " + printMe.getPrefHeight() + " " + child.getLayoutX() + " " + child.getLayoutY());
//            }
//            //System.out.println(" " + child);
//        }
        //System.out.println(load.getSheet().getChildren());
        
        Application app2 = new BesserNote();
        Stage anotherStage = new Stage();
        try {
            app2.start(anotherStage);
            ((BesserNote) app2).replaceSheet(load.getSheet(undoManager));
        } catch (Exception ex) {
            Logger.getLogger(BesserNote.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //changeRoot(load.getSheet());
    }
    
    private void saveFile() throws IOException{
              //Show save file dialog
              File file = fileChooser.showSaveDialog(primaryStage);
              if(file != null){
                  Saver save = new Saver(file);
                  save.save(sheet);
              }
          }
      
        
    public void createNode() {
        /*
        Node myNode = null; //dockingMenu.createNode();
        System.out.println(myNode);
        if (myNode != null){
            nodeGUI.editNode(myNode);
            sheet.getChildren().add(myNode);
        }
        */

        for (Map.Entry<Node, DashedBox> entry : selectBoxes.entrySet()) {
            Node n = entry.getKey();
            if (n instanceof ChildSpecifier) {
                n = ((ChildSpecifier) n).specifySelf();
            }
            if (n instanceof Pane) {
                Node newNode = nodeGUI.getNode(undoManager);

        //        DraggingUtil.enableResizeDrag(newNode);
                if (newNode != null) {
                    ((Pane) n).getChildren().add(newNode);
                    nodeGUI.editNode(newNode);
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            showAllSelections();
                        }                 
                    });
                    try{
                        undoManager.trackMyPlacementChanges((Region) newNode);
                        undoManager.addChange(new AddChange(newNode, (Pane) n));
                    } catch(Exception f){
                        
                    }
                }
                        
            }
        }
        popup.hide();              
    }
      
    public static void main(String[] args) {
        launch(args);
    }
    
    public Node getCurrentFocus(){
        return this.scene.getFocusOwner();
    }
//    
//    
    public void drawOn(){  
        drawCanvas.setVisible(true);
        drawCanvas.addListeners();
        drawCanvas.toFront();
    }
    
    public void drawOff(){
       drawCanvas.removeListeners();
       drawCanvas.toBack();
       drawCanvas.setVisible(false);
    }
//    
//    public void undoDrawing(){
//        drawCanvas.undoDrawing();
//    }
//    
    public void addDoodle(Path path){        
        MoveTo initialMove = (MoveTo) path.getElements().get(0);        
        double initX = initialMove.getX();
        double initY = initialMove.getY();
        //Transform to new coordinates
        double newInitX = initX - target.getLayoutX();
        double newInitY = initY - target.getLayoutY(); 
        //New Path
        Path addPath = new Path();
        addPath.setStroke(c);
        addPath.setStrokeWidth(7);
        addPath.setSmooth(true);
        addPath.setLayoutX(newInitX);
        addPath.setLayoutY(newInitY);
        MoveTo addInit = new MoveTo();
        addInit.setX(initX - target.getLayoutX() - newInitX);
        addInit.setY(initY - target.getLayoutY() - newInitY);
        addPath.getElements().add(addInit);
        //Transform Path
        for(int i = 1; i < path.getElements().size(); i++){
            LineTo line = (LineTo) path.getElements().get(i);
            addPath.getElements().add(new LineTo((line.getX() - target.getLayoutX() - newInitX), (line.getY()  - target.getLayoutY() - newInitY)));
        }
//        System.out.println(path.getLayoutX() + " " + path.getLayoutY() + " |" + path.toString());
//        System.out.println(addPath.getLayoutX() + " " + addPath.getLayoutY() + " |" + addPath.toString()); 
        ((Pane)target).getChildren().add(addPath);
    }
   
    public void strokeColor(Color c){
        this.c = c;
        drawCanvas.changeColor(c);
    }
    
    
    
    ///Copy + Paste///
    private void copy(){
        Saveable saveObj = null;
        if(superSelected instanceof BTabPane){
            saveObj = new BTabPaneSave((BTabPane)superSelected);
        }
        else if(superSelected instanceof BTextArea){
            saveObj = new BTextAreaSave((BTextArea)superSelected);
        }
        else if(superSelected instanceof BScrollPane){
            saveObj = new BScrollPaneSave((BScrollPane)superSelected);
        }
        else if(superSelected instanceof Path){
            saveObj = new DoodleSave((Path)superSelected);
        }    
        else if (superSelected instanceof BImage){
            saveObj = new BImageSave((BImage)superSelected);
        }
        else if (superSelected instanceof BFlashCard){
            saveObj = new BFlashCardSave((BFlashCard)superSelected);
        }
        else if (superSelected instanceof BWrapPane){
            saveObj = new BWrapPaneSave((BWrapPane)superSelected);
        }
        else if (superSelected instanceof Pane){
            saveObj = new PaneSave((Pane)superSelected);
        }
        copied = saveObj;
        //System.out.println(copied);
    }
    
    public void paste(){
        Node insertMe = copied.create(undoManager);
        Point p = MouseInfo.getPointerInfo().getLocation();
        insertMe.setLayoutX(p.getX() - 75);
        insertMe.setLayoutY(p.getY() - 75);
        ((Pane)target).getChildren().add(insertMe);
    }
    
}
