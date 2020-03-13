package com.tecsoluction.robo.conf;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;

import com.tecsoluction.robo.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * Manages switching Scenes on the Primary Stage
 */

@Getter
@Setter
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;
    private  Image imageIcon;
    
    private  boolean isconfigurado= false;
    
    private  boolean isconfiguradoserver= false;
    
    private File arquivoLocalizado;
    
    private File arquivoConf;
    
    private File arquivoFilter;
    
    private List<File> arquivos;

    
    
    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
//        this.userStage = new Usuario();
        this.imageIcon = new Image(getClass().getResourceAsStream("/images/fodaa.png"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getIcons().add(imageIcon);
      
        
    }

    public void switchScene(final FxmlView view) {
    	
    	
    
    	Parent viewRootNodeHierarchy  = loadViewNodeHierarchy(view.getFxmlFile());
      
        show(viewRootNodeHierarchy, view.getTitle());
        
       // progressoBarra();
    }
    
    public void show(final Parent rootnode, String title) {
        
    	
    	Scene scene = prepareScene(rootnode);
    	
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
//        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setMaximized(false);
      //  primaryStage.setFullScreen(true);
       // progressoBarra();
   
        
        try {
           
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }
    
    private Scene prepareScene(Parent rootnode){
      
    	
    	Scene scene = primaryStage.getScene();

     


        if (scene == null) {
        	
            scene = new Scene(rootnode);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(null);
            scene.getRoot().setStyle("-fx-background-color: transparent;");
            
        }else {
        	
       	 scene.setFill(null);
         scene.getRoot().setStyle("-fx-background-color: transparent;");
        	
        	
        }
        
        scene.setRoot(rootnode);
        
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    public Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
    

    
    public Stage getPrimaryStage() {
		return primaryStage;
	}
    
//    void progressoBarra(){
// 	   
//  	  // jprogress = new ProgressBar();
////  	   ProgressIndicator progressIndicator = new ProgressIndicator();
////  	  vbox.getChildren().addAll(jprogress,progressIndicator);
//  	   
//  	 //  MainController lc = new MainController();
//  	   
//  	   wd = new WorkIndicatorDialog<String>(viewRootNodeHierarchy.getScene().getWindow(), "Loading Project Files...");
//  	   
//  	    wd.addTaskEndNotification(result -> {
//  	       System.out.println(result);
//  	       wd=null; // don't keep the object, cleanup
//  	    });
//  	 
//  	    wd.exec("123", inputParam -> {
//  	       // Thinks to do...
//  	       // NO ACCESS TO UI ELEMENTS!
//  	       for (int i = 0; i < 5; i++) {
//  	           System.out.println("Loading data... '123' =->"+inputParam);
//  	           try {
//  	              Thread.sleep(1000);
//  	           }
//  	           catch (InterruptedException e) {
//  	              e.printStackTrace();
//  	           }
//  	       }
//  	       return new Integer(1);
//  	    });
//  	   
//  	   
//  	   
//     }
    
  
    public void Borrar() {

    	GaussianBlur blur = new GaussianBlur(10);
    	getPrimaryStage().getScene().getRoot().setEffect(blur);
    	getPrimaryStage().getScene().getRoot().setOpacity(0.7);
    	
    	
    	
    }
    
    public void DesBorrar() {

//    	GaussianBlur blur = new GaussianBlur(55);
    	getPrimaryStage().getScene().getRoot().setEffect(null);
    	getPrimaryStage().getScene().getRoot().setOpacity(1);
    	
    	
    	
    }
    
    
}
