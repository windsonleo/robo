package com.tecsoluction.robo.controller;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.view.FxmlView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class SplashController implements Initializable{


	 	
	    @FXML
	    private AnchorPane anchor;

	  
//	    @FXML
//	    private Label trace;

	   
	    @FXML
	    private ImageView imgvfd;
	    
	    @FXML
	    private Image imgfd;
	    
	    
	    @FXML
	    private Pane panefd;
	    
	    @FXML
	    private Circle circlefd;
	    
	    
	    @FXML
	    private StackPane stackroot;
	    
	    
	    private Timeline timeline;

	  
	    
	    @Lazy
	    @Autowired
	    private StageManager stageManager;
	
	    
		
	    public SplashController() {
			// TODO Auto-generated constructor stub
		}
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
	}	
	
	
	
	
	

     
     public void Inicializar (){
    	 
    	 
  
//         circlefd.setFill(new ImagePattern(imgfd));
//         circlefd.setEffect(new DropShadow(+25d, 0d, +2d, Color.AZURE));
         
         imgvfd.setVisible(true);
         
         
         InitAnimacao();
    	
    	 
     }
     
     
     
     void InitAnimacao (){
    	 
    	 timeline = new Timeline(new KeyFrame(Duration.millis(23), new EventHandler<ActionEvent>() {
				
				
			    @Override
			    public void handle(ActionEvent event) {
			    	


			    	PreencherFoda(panefd.getHeight() + 1.6);
			    	


			        
			    }



		        	 
		         }));
			
		 timeline.setCycleCount(221);	         
		 timeline.setAutoReverse(false);
		 timeline.setDelay(Duration.millis(500));
		 timeline.play();
		

		
	}
     

     
     private void PreencherFoda( double y) {
    	 
        	 panefd.setPrefHeight(y);
    	 
    	 
     }
     
     
     
     
     
 	@FXML
 	public void fechar(){
 		
 		
 		new Service<Integer>() {
 	        @Override
 	        public void start() {
 	            super.start();
 	            

 	        
 	        
 	        }

 	        @Override
 	        protected Task<Integer> createTask() {
 	            return new Task<Integer>() {
 	                @Override
 	                protected Integer call() throws Exception {         	  
 	                       
 	                	updateMessage("Fechando Janela Filtro .");
 	                	
 	                	
 	                	Thread.sleep(5000);
 	                	
 	                //	updateMessage("Fechar.");
 	                       
 	                        return 1;
 	                }
 	            };
 	        }

 	        @Override
 	        protected void succeeded() {

 	        	
 	        	
 	        	main();
 	        	
 	    	    Stage stage = (Stage) getPanefd().getScene().getWindow();
 	    	    stage.close();
 	    	    
 	    	  

 			 
 	        }


 	        
 	        
 	        
 	    }.start();
 		
 		
 		

 		
 		
 	}





	protected void main() {

		
		
		stageManager.switchScene(FxmlView.MAIN);
		
		
	}
    	 
     }
     
 
    

	
    

