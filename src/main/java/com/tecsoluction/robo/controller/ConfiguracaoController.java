package com.tecsoluction.robo.controller;


import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.tecsoluction.robo.conf.StageManager;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class ConfiguracaoController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(ConfiguracaoController.class);

	 	private static double xOffset = 0;
	    private static double yOffset = 0;
	
	    @FXML
	    private VBox vbox;
	    
	    @Lazy
	    @Autowired
	    private StageManager stageManager;

	
	    @FXML
	    private JFXButton btlogout;

	
	    @FXML
	    private AnchorPane anchor;
	    	    
	    @FXML
	    public ProgressIndicator progressind;
	    

	    @FXML
	    private JFXButton btsalvar;
	    
	    @FXML
	    private JFXButton btfechar;


	    @FXML
	    private JFXTextField jtxtfarquivonome;
	    
	    
	    @FXML
	    private JFXTextField jtxtfmodelonome;
	    
	    
	    
	    
	    private String nomeArquivo;
	    
	    private String nomeModelo;

//	    @FXML
//	    private JFXTextField jtxtprotocolo;
	    

	
//	    
//	    @FXML
//	    private ImageView imgvserver4;
//	    
//	    @FXML
//	    private Image imgserver5;
	    
	    
	    @FXML
	    private  Label trace;
	    
	   
	    
		
	    public ConfiguracaoController() {
			// TODO Auto-generated constructor stub
		}
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
		
		 
		
		
	}	
	
	
	
	
	

     
     void Inicializar (){
    	 
    	 
    
    	
    	 
     }
     
 
    
    
 	@FXML
 	public void salvarnome(){
 		
 		trace.textProperty().unbind();
 		trace.setText("Clicado em Salvar Nome");
 		
 		nomeArquivo = jtxtfarquivonome.getText();
 		
 		nomeModelo =jtxtfmodelonome.getText();
 		
 		
 		
// 		stageManager.
 		
 		
 	}
 	
 	
 	@FXML
	private void mousepress(MouseEvent event){
					
		
  
		anchor.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            xOffset = stageManager.getPrimaryStage().getX() - event.getScreenX();
            yOffset = stageManager.getPrimaryStage().getY() - event.getScreenY();
        }
    });
		
		
	}
	
	
	@FXML
	private void mousedrag(MouseEvent event){
		
		anchor.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	stageManager.getPrimaryStage().setX(event.getScreenX() + xOffset);
            	stageManager.getPrimaryStage().setY(event.getScreenY() + yOffset);
            }
        });
		
		
	}
	
	
	
	@FXML
	public void fechar(){
		
		
		new Service<Integer>() {
	        @Override
	        public void start() {
	            super.start();
	            
	            progressind.setVisible(true);
	          
	            
        		progressind.progressProperty().unbind();
        		progressind.progressProperty().setValue(progressProperty().getValue().doubleValue());

           		trace.textProperty().unbind();
                

//        		
        		trace.textProperty().bind(this.messageProperty());
	        
	        
	        }

	        @Override
	        protected Task<Integer> createTask() {
	            return new Task<Integer>() {
	                @Override
	                protected Integer call() throws Exception {         	  
	                       
	                	updateMessage("Fechando Janela Configuracao .");
	                	
	                	
	                	Thread.sleep(2000);
	                	
	                //	updateMessage("Fechar.");
	                       
	                        return 1;
	                }
	            };
	        }

	        @Override
	        protected void succeeded() {

	        	
	        	
	        	progressind.setVisible(false);
	        	
	    	    Stage stage = (Stage) getBtfechar().getScene().getWindow();
	    	    stage.close();

			 
	        }
	        
	        
	        
	    }.start();
		
		
		

		
		
	}
 	

}


	
    

