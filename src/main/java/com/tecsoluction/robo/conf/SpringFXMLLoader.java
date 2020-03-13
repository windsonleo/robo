package com.tecsoluction.robo.conf;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

//import com.cemeer.mala.mala.view.Constantes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Will load the FXML hierarchy as specified in the load method and register
 * Spring as the FXML Controller Factory. Allows Spring and Java FX to coexist
 * once the Spring Application context has been bootstrapped.
 */
@Component
public class SpringFXMLLoader {
	
    private final ResourceBundle resourceBundle;
    private final ApplicationContext context;

    @Autowired
    public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        this.context = context;
    }

    public Parent load(String fxmlPath) throws IOException {      
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean); //Spring now FXML Controller Factory
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
    
//    public static Parent loadstatic(String fxmlPath) throws IOException {    
//    	
//    	  ResourceBundle resourceBundle2 = null;
//    	  ApplicationContext context2 = null ;
//    	
//        FXMLLoader loader = new FXMLLoader();
//        loader.setControllerFactory(context2::getBean); //Spring now FXML Controller Factory
//        loader.setResources(resourceBundle2);
//        loader.setLocation(Constantes.CATEGORIA_VIEW.getClass().getResource(fxmlPath));
//        return loader.load();
//    }


}
