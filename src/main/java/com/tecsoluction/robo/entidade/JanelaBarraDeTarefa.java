package com.tecsoluction.robo.entidade;


import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JSeparator;

import com.tecsoluction.robo.controller.MainController;
import com.tecsoluction.robo.view.FxmlView;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import dorkbox.systemTray.Tray;
import javafx.application.Platform;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JanelaBarraDeTarefa {
	
	
	private  SystemTray tray;
	
	private  Tray trayIcon;
	
	private  java.awt.Image image;
	
	private Image imageIcon;
	
	private Menu menu;
	
	private MenuItem menuitemExibir;
	
	private MenuItem menuitemSair;
	
	private MainController maincontrol;
	
	
	
	
	public JanelaBarraDeTarefa() {
		// TODO Auto-generated constructor stub
		
		Init();
	}
	
	
	public JanelaBarraDeTarefa(MainController mainc) {
		// TODO Auto-generated constructor stub
		
		this.maincontrol = mainc;
		
		Init();
	}
	
	
	
	
	private void Init(){
		
		
		  
		 try {
			image =ImageIO.read(getClass().getResourceAsStream("/images/fodaa.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 Platform.setImplicitExit(false);
		
		 	tray = SystemTray.get();
		 
		if (tray == null) {
			throw new RuntimeException("Unable to load SystemTray!");
		}

		tray.setImage(image);

		tray.getMenu().add(new MenuItem("Exibir", a -> {
			//maincontrol.getStageManager().getPrimaryStage().show();
		
			Platform.runLater(() -> {
				maincontrol.getStageManager().getPrimaryStage().show();
				maincontrol.getStageManager().getPrimaryStage().requestFocus();
				tray.getMenu().setEnabled(true);
			});
		
		
		
		}));

		tray.getMenu().add(new JSeparator());

		tray.getMenu().add(new MenuItem("Fechar", a -> {
			System.exit(0);
		})).setShortcut('q'); // case does not matter
		
		
		
		tray.setTooltip("Executando em BackGround");
		
		
	}
	

		

}
