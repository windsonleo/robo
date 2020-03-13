package com.tecsoluction.robo.entidade;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Animacao {
	
	private Timeline timeline;
	
	private FadeTransition fadein;
	
	private FadeTransition fadeout;
	
	private TranslateTransition translate;
	
	private ScaleTransition scala;
	
	private RotateTransition rotate;
	
	private ParallelTransition  paraleloTrans;
	
	private Path path;
	
	private SequentialTransition sequencetrans;	
	
	
	private boolean autoreverse = false;
	
	private int ciclos = 0;
	
	
	private int minutos = 0;
	
	
	private int segundos = 0;
	
	
	private int duracao = 0;
	
	
	private double fromv=0.0;
	
	private double tov=0.0;
	
	

	
	public Animacao() {

	
	}
	
	
	
	public void init (){
		
		
		
		
	}
	
	
	
	public void ExecutarFadeIn(Node node){
		
		fadein = new FadeTransition(Duration.millis(300), node);
		fadein.setFromValue(0.0);
		fadein.setToValue(1.0);
		fadein.setCycleCount(4);
		fadein.play();
		
		
	}
	
	
	
	public void ExecutarFadeOut(Node node){
		
		fadeout = new FadeTransition(Duration.millis(300), node);
		fadeout.setFromValue(1.0);
		fadeout.setToValue(0.0);
		fadeout.setCycleCount(4);
		fadeout.play();
		
		
	}
	
	
	public void ExecutarRotate(Node node){
		
		rotate = new RotateTransition(Duration.seconds(2000), node);
	//	trans.setFromAngle(0.0);
		rotate.setToAngle(360.0);
		rotate.setAxis(new Point3D(0.0,360.0, 0.0));
		// Let the animation run forever
		rotate.setCycleCount(4);
		// Reverse direction on alternating cycles
		rotate.setAutoReverse(true);
		// Play the Animation
		rotate.play();
		
		
	}
	
	
	public void ExecutarScala(Node node){
		
  		 scala = new ScaleTransition(Duration.seconds(1000), node);
  		scala.setByX(1.5);
  		scala.setByY(1.5);
  		scala.setFromY(0.50);
  		scala.setToY(1.0);
//  		 Let the animation run forever
  		scala.setCycleCount(Animation.INDEFINITE);
//  		 Reverse direction on alternating cycles
  		scala.setAutoReverse(true);
//  		 Play the Animation
  		scala.play();	
		
		
	}
	
	
	
	public void ExecutarTranslate(Node node){
		
 		 translate = new TranslateTransition(Duration.seconds(1000), node);
 		translate.setByX(1.5);
 		translate.setByY(1.5);
 		translate.setFromY(0.50);
 		translate.setToY(1.0);
// 		 Let the animation run forever
 		translate.setCycleCount(Animation.INDEFINITE);
// 		 Reverse direction on alternating cycles
 		translate.setAutoReverse(true);
// 		 Play the Animation
 		translate.play();	
		
		
	}
	
	
	public void ExecutarParallelTransition(List<Animation> anime){
		
		
		   paraleloTrans = new ParallelTransition( );
		   paraleloTrans.getChildren().addAll(anime);
		   paraleloTrans.setCycleCount(4);
		   paraleloTrans.setAutoReverse(false);
		   paraleloTrans.play();

		
		
	}
	
	
	public void ExecutarSequentialTransition(List<Animation> animes,Node node){
		
		
		 sequencetrans = new SequentialTransition();
		 sequencetrans.setNode(node);
		 sequencetrans.getChildren().addAll(animes);
		 sequencetrans.play(); 

		
		
	}
	
	
	
	
	
	


	
	

}
