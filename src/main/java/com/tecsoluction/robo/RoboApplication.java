package com.tecsoluction.robo;

import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.controller.MainController;
import com.tecsoluction.robo.view.FxmlView;

import javafx.application.Application;
import javafx.stage.Stage;
import uk.org.lidalia.sysoutslf4j.context.LogLevel;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

@SpringBootApplication
public class RoboApplication extends Application{

	 protected ConfigurableApplicationContext springContext;
	    protected StageManager stageManager;
		final static Logger logger = LoggerFactory.getLogger(RoboApplication.class);

	    

	    
	  @Override
	    public void init() throws Exception {
	        springContext = springBootApplicationContext();
	    }

	    @Override
	    public void start(Stage stage) throws Exception {
	        stageManager = springContext.getBean(StageManager.class, stage);
	        displayInitialScene();
	    }

	    @Override
	    public void stop() throws Exception {
	        springContext.close();
	    }
	    
	    
	    

	    /**
	     * Useful to override this method by sub-classes wishing to change the first
	     * Scene to be displayed on startup. Example: Functional tests on main
	     * window.
	     */
	    protected void displayInitialScene() {
	        stageManager.switchScene(FxmlView.PRELOAD);
	    }

	    
	    private ConfigurableApplicationContext springBootApplicationContext() {
			SysOutOverSLF4J.registerLoggingSystem("com.tecsoluction.robo");
			SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.INFO, LogLevel.ERROR);
			
	    	
	    	SpringApplicationBuilder builder = new SpringApplicationBuilder(RoboApplication.class);
	        builder.headless(false);
	        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
	        return builder.run(args);
	    }

	public static void main(String[] args) {
		
		SysOutOverSLF4J.registerLoggingSystem("com.tecsoluction.robo");
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.INFO, LogLevel.ERROR);
		Application.launch(args);
	}
}
