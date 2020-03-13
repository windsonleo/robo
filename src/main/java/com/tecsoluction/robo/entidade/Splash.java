package com.tecsoluction.robo.entidade;

import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.controller.SplashController;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Splash extends Preloader{
	
	
	public SplashController splashcontrol;
	
	

    @Lazy
    @Autowired
    private StageManager stageManager;
	

	@Override
	public void start(Stage arg0) throws Exception {

		
		opensplash();
		
		
	}
	
	
	

	void opensplash() {
		
		System.out.println("abrir splash");
		
try{
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/splash.fxml"));

	        Parent root =loader.load();
	        
	        splashcontrol = (SplashController)loader.getController();
	        
	        splashcontrol.Inicializar();
	        
	        splashcontrol.fechar();


	        
	        
	       
	        

	      
	        
	        javafx.stage.Window win = new Popup() ;
	    	
	    		Stage s1 = new Stage();
	    		s1.initOwner(win);
	    		s1.initModality(Modality.WINDOW_MODAL);
	    		s1.initStyle(StageStyle.TRANSPARENT);
	    		s1.setTitle("Splash");
	  
	            
	            Scene scene = new Scene(root);
	          
	            scene.setFill(null);
	    		 s1.setScene(scene);
	    		// 
	    		 s1.show();
	    		 
	    		 
	    		 
			}catch (Exception e) {

			System.out.println("erro controle de splashcontrol:"+ e);
			
			}
		

//			borrar();
		
		
	
	}
	
	
	
	

}
