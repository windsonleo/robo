package com.tecsoluction.robo.controller;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.CopyTask;
import com.tecsoluction.robo.entidade.CopyTaskArquivos;
import com.tecsoluction.robo.entidade.Filtro;
import com.tecsoluction.robo.entidade.GerenciadorFiltro;
import com.tecsoluction.robo.entidade.ManipuladorArquivo;
import com.tecsoluction.robo.view.FxmlView;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class PreloaderController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(PreloaderController.class);

	 	
		@FXML
	    private VBox vbox;

	    @FXML
	    private AnchorPane anchor;	

	    @FXML
	    private Label lblpergunta;
	 
	    @FXML
	    private JFXButton btcancelar;

	    @FXML
	    private ProgressIndicator progressind;

	    @FXML
	    private Label trace;
	    
	    @Lazy
	    @Autowired
	    private StageManager stageManager;

		private File arquivoFiltro;
		
		private File arquivoConf;
		
		private ManipuladorArquivo manipulador;
		
		private HashMap<Integer, String> lines;
		
		private String nomeArquivo;
		
		private String nomeModelo;
		
		private String usuarioconf;
		
		private String senhaconf;
		
		private String hostconf;
		
		private String protocoloconf;
		
		private String portaconf;
		
		private HashMap<Integer, Filtro> linesfilter;
		
		private List<Filtro> filtros = new ArrayList<Filtro>();
		
		private File arquivoLocalizado;
		
		private  List<File>  arquivos;
		
		private  List<File>  arquivosall;
		
		private File dirfinal;
		
		
		private CopyTaskArquivos copyreg;

		private ArrayList<File> arquivosallfilter;
		
	 	private static double xOffset = 0;
	    private static double yOffset = 0;
	    
	    private boolean first = true;
	    
	    @FXML
	    private SubScene subcena;
	    
	    private RotateTransition	rotate;
	    
	    
	    
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
	    
	    @FXML
	    private AnchorPane anchorr;
	    
	    
		
	    public PreloaderController() {

	    
	    
	    }
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
//		btcancelar.fire();
		
        imgvfd.setVisible(true);
        
        
    
		
		cancelar();
		
	    InitAnimacao();
		
//		 EventHandler<ActionEvent> eventt = new EventHandler<ActionEvent>() { 
//	            public void handle(ActionEvent e) 
//	            { 
//	            	
//	            	if(arquivosall != null && arquivosallfilter!=null){
//	            		
//	            		arquivosall.clear();
//	            		arquivosallfilter.clear();
//	            		arquivos.clear();
//	            		
//	            		
//	            	}else {
//	            		
//
////	            		arquivos.clear();
//	            		
//	            		
//	            	}
//	            	
//	            	
//	            	cancelar();
//	            	
//	           
////	            	VerificarConfFiltroInit();
//	            	
////	            	procurarArquivos();
//	            	
//            		arquivos.addAll(arquivosall);
//            		arquivos.addAll(arquivosallfilter);
//	            	
//	            	
//	            	copyreg = new CopyTaskArquivos(arquivos);
//	            	
//	        		progressind.progressProperty().unbind();
//	        		
//	        		progressind.progressProperty().bind(copyreg.progressProperty());
//	        		
//
//	        		
//	        		trace.textProperty().unbind();
//	        		
//	        		trace.textProperty().bind(copyreg.messageProperty());
//	        		
//
//
//
//	        		copyreg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
//	                         new EventHandler<WorkerStateEvent>() {
//
////	                             private GerenciadorFiltro gerenciafiltro;
//
//								@Override
//	                             public void handle(WorkerStateEvent t) {
//	                                 List<File>copied = copyreg.getValue();
//
//	                                 trace.textProperty().unbind();
//	                                 trace.setText("Localizado: " + copied.size() + " Registros");                                
//	                               
////	                                 trace.textProperty().bind(copyreg.messageProperty());
////	                                 trace.textProperty().unbind();
//	                              
//	                                 progressind.setVisible(false);
//	                                 
//	                                 Main();
//	                                 
//	                                 
//	                                 
//	                                
//								
//								}
//
//
//
//
//
//
//	                         });
//	           
//
//	                 // Start the Task.
//	                 new Thread(copyreg).start();
//	                 
//	            	
//	            } 
//	            
//	  
//	        }; 
//	        
//	        
//	        
//	        btcancelar.setOnAction(eventt);
//	        
	        
	        
//	        btcancelar.fire();
//	        
//	        btcancelar.setDisable(true);
	        
//	        cancelar();
	        
//	        btcancelar.fire();
		
		
	}	
	
	
	
	private void Main() {

		stageManager.switchScene(FxmlView.MAIN);
		
		
		
	}
	

     void Inicializar (){
    	 

    	 
     }
     
     
 void InitAnimacao (){
    	 
    	 timeline = new Timeline(new KeyFrame(Duration.millis(80), new EventHandler<ActionEvent>() {
				
				
			    @Override
			    public void handle(ActionEvent event) {
			    	


			    	PreencherFoda(panefd.getHeight() + 0.1);
			    	


			        
			    }



		        	 
		         }));
			
		 timeline.setCycleCount(100);	         
		 timeline.setAutoReverse(false);
		 timeline.setDelay(Duration.millis(280));
		 timeline.play();
		

		
	}
     

     
     private void PreencherFoda( double y) {
    	 
//    	 if(panefd.getPrefHeight())
    	 
        	 panefd.setPrefHeight(y);
    	 
    	 
     }

     
     @FXML
     void cancelar() {
    	 
//    	 progressind.setVisible(true);
    	 
    	 btcancelar.setVisible(false);
    	 
//    	 VerificarConfFiltroInit();
    	 
    	 
    	 
    	 EventHandler<ActionEvent> eventt = new EventHandler<ActionEvent>() { 
	            public void handle(ActionEvent e) 
	            { 
	            	
	            	
	            	RotacionarCena();
	            	
	            	if(arquivosall != null && arquivosallfilter!=null){
	            		
	            		arquivosall.clear();
	            		arquivosallfilter.clear();
	            		arquivos.clear();
	            		
	            		
	            	}else {
	            		

//	            		arquivos.clear();
	            		
	            		arquivos  = new ArrayList<File>();
	            		arquivosall= new ArrayList<File>();
	            		arquivosallfilter= new ArrayList<File>();
	            		
	            	}
	            	
	            	
	            	Procurar();
	            	
	           
//	            	VerificarConfFiltroInit();
	            	
//	            	procurarArquivos();
	            	
         		arquivos.addAll(arquivosall);
//         		arquivos.addAll(arquivosallfilter);
	            	
	            	
	            	copyreg = new CopyTaskArquivos(arquivos);
	            	
	        		progressind.progressProperty().unbind();
	        		
	        		progressind.progressProperty().bind(copyreg.progressProperty());
	        		

	        		
	        		trace.textProperty().unbind();
	        		
	        		trace.textProperty().bind(copyreg.messageProperty());
	        		



	        		copyreg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
	                         new EventHandler<WorkerStateEvent>() {

//	                             private GerenciadorFiltro gerenciafiltro;

								@Override
	                             public void handle(WorkerStateEvent t) {
	                                 List<File>copied = copyreg.getValue();
	                                 
	                                 EnviarObjetosStage();

	                                 trace.textProperty().unbind();
	                                 trace.setText("Localizado: " + copied.size() + " Registros");                                
	                               
	                                 trace.textProperty().bind(copyreg.messageProperty());
	                                 trace.textProperty().unbind();
	                              
	                                 progressind.setVisible(false);
	                                 
//	                                 if(timeline.getStatus().equals(Status.RUNNING)){
//	                                	 
////	                                	 timeline.setCycleCount(100);
//	                                	 
//	                                	
//	                                	 
//	                                	 Aguardar();
//	                                	 
//	                                 }else  {
//	                                	 
//										PararRotacionarCena();
//										Main();
//	                                	 
//	                                	 
//	                                 }
	                                 

	                                 Aguardar();
	                                 
	                                 trace.textProperty().unbind();
	                                 trace.setText("Aguarde"); 

	                                 
	                                 
	                                 
	                                 
	                                
								
								}








	                         });
	           

	                 // Start the Task.
	                 new Thread(copyreg).start();
	                 
	            	
	            }


	            
	  
	        }; 
	        
	        
	        
	        btcancelar.setOnAction(eventt);
	        btcancelar.fire();
	       
	        
    	 

     } 
     
		private void Procurar() {
			
			progressind.setVisible(true);
				
				
				VerificarConfFiltroInit();	

			

	
			 btcancelar.setDisable(true);
		} 
		
		
		
		public void Aguardar(){
	 		
	 		
	 		new Service<Integer>() {
	 	        @Override
	 	        public void start() {
	 	            super.start();
	 	            
	 	            	//trace.textProperty().unbind();
	 	            	trace.textProperty().bind(this.messageProperty());
	 	        
	 	        }

	 	        @Override
	 	        protected Task<Integer> createTask() {
	 	            return new Task<Integer>() {
	 	                @Override
	 	                protected Integer call() throws Exception {         	  
	 	                       
	 	                	updateMessage("Arquivo Localizado: " + arquivoLocalizado.getName());
	 	                    PararRotacionarCena();
	 	                	
	 	                	Thread.sleep(3000);
	 	                	
	 	                //	updateMessage("Fechar.");
	 	                       
	 	                        return 1;
	 	                }
	 	            };
	 	        }

	 	        @Override
	 	        protected void succeeded() {

	 	        	
                    Main(); 

	 			 
	 	        }
	 	        
	 	        
	 	        
	 	    }.start();
	 		
	 		
	 		

	 		
	 		
	 	}
     
     
     
//     void InitAnimacao (){
//    	 
//    	 timeline = new Timeline(new KeyFrame(Duration.millis(23), new EventHandler<ActionEvent>() {
//				
//				
//			    @Override
//			    public void handle(ActionEvent event) {
//			    	
//
//
//			    	PreencherFoda(panefd.getHeight() + 1.6);
//			    	
//
//
//			        
//			    }
//
//
//
//		        	 
//		         }));
//			
//		 timeline.setCycleCount(221);	         
//		 timeline.setAutoReverse(false);
//		 timeline.setDelay(Duration.millis(500));
//		 timeline.play();
//		
//
//		
//	}
//     

     
//     private void PreencherFoda( double y) {
//    	 
//        	 panefd.setPrefHeight(y);
//    	 
//    	 
//     }
     
     
		private void EnviarObjetosStage() {

			stageManager.setArquivoConf(arquivoConf);
			stageManager.setArquivoFilter(arquivoFiltro);
			stageManager.setArquivoLocalizado(arquivoLocalizado);
			stageManager.setArquivos(arquivos);
			
			
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
 	                       
 	                	updateMessage("Fechando Janela Preload .");
 	                	
 	                	
 	                	Thread.sleep(5000);
 	                	
 	                //	updateMessage("Fechar.");
 	                       
 	                        return 1;
 	                }
 	            };
 	        }

 	        @Override
 	        protected void succeeded() {

 	        	
// 	    	    Stage stage = (Stage) getBtabreMain().getScene().getWindow();
// 	    	    stage.close();

 			 
 	        }
 	        
 	        
 	        
 	    }.start();
 		
 		
 		

 		
 		
 	}
 	
 	

 	    @FXML
 	    void mousedrag(MouseEvent event) {
 	    	
 	    	vbox.setOnMouseDragged(new EventHandler<MouseEvent>() {
 	            @Override
 	            public void handle(MouseEvent event) {
 	            	stageManager.getPrimaryStage().setX(event.getScreenX() + xOffset);
 	            	stageManager.getPrimaryStage().setY(event.getScreenY() + yOffset);
 	            }
 	        });
 	    	
 	    	

 	    }

 	    @FXML
 	    void mousepress(MouseEvent event) {
 	    	
 	    	vbox.setOnMousePressed(new EventHandler<MouseEvent>() {
 		        @Override
 		        public void handle(MouseEvent event) {
 		            xOffset = stageManager.getPrimaryStage().getX() - event.getScreenX();
 		            yOffset = stageManager.getPrimaryStage().getY() - event.getScreenY();
 		        }
 		    });

 	    }
 	    
 	    
 	    
 	   private void VerificarConfFiltroInit() {
 		   

//				progressind.setVisible(true);

 			
 			arquivos = new ArrayList<File>();
 			
 			File arquivoconf = VerificarArquivoDeConfig();
 			
 			File arquivofiltro = VerificarArquivoDeFiltro();
 			
 			
 			if(arquivofiltro != null && arquivoconf != null) {
 				
 				PreencherValoresArquivados(arquivoconf,arquivofiltro);
 				
 				arquivoFiltro = arquivofiltro;
 				arquivoConf = arquivoconf;
 				
 				
 			}else {
 				
 				trace.textProperty().unbind();
 				trace.setText("Nenhum Arquivo de Configuração ou Filtro Encontrado.");	
 				
 				String path = System.getProperty("user.home");
 				String pathfinal = path + "\\" + "Desktop";
 				String patchf = pathfinal + "\\" + "foda";
 				
 				File di = new File(patchf);
 				
 				if(!di.isDirectory()){
 					di.mkdirs();
 					
 				}else {
 					
 					
 					
 				}
 				
 				File fi = new File(di.getPath()  +"\\"+ "inicial.txt");
 				arquivoLocalizado = fi;
 				
 				
 				
 				CriarDiretorio(fi);
 				
 				
 			}
 				
 			
 			
 	   }
 	   
 	   
 		private File VerificarArquivoDeConfig(){
 			
 			
 			arquivosall = new ArrayList<File>();
 			
 			File f = null;

 			String nomearquivoconf = "conf";
 			
// 			String nomearquivofiltro = "filtro.txt";
 			
 			String path = System.getProperty("user.home");
 			String pathfinal = path + "\\" + "Desktop";
 			
// 			System.out.println("path  :" + pathfinal);
 		//	trace.setText("path :" + pathfinal);
 			
 			List<File> arquivosconf = buscarArquivoPorNome(nomearquivoconf, pathfinal);
 			
 			arquivosall.addAll(arquivosconf);
 			
 			f=RetornarArquivoValidoConf(arquivosconf);
 			
// 			if((!f.exists()) && (f.getParent().isEmpty())){
// 				
// 				CriarDiretorio(f);
// 			}
 			
 			return f;
 			
 		}
 		
 		private File VerificarArquivoDeFiltro(){
 			
 			arquivosallfilter = new ArrayList<File>();
 			
 			File f = null;
 			String nomearquivofiltro = "filtro";
 			
 			String path = System.getProperty("user.home");
 			String pathfinal = path + "\\" + "Desktop";
 			
// 			System.out.println("path  :" + pathfinal);
 		//	trace.setText("path :" + pathfinal);
 			
 			List<File> arquivosfiltro = buscarArquivoPorNome(nomearquivofiltro, pathfinal);
 			
 			arquivosallfilter.addAll(arquivosfiltro);
 			
 			f = RetornarArquivoValidoFiltro(arquivosfiltro);
 			
// 			if((!f.exists()) && (f.getParent().isEmpty())){
// 				
// 				CriarDiretorio(f);
// 			}
 			
 			return f;
 			
 		}
 		
 		private File RetornarArquivoValidoFiltro(List<File> arquivos) {
 			
 		   //  trace.setText("Filtrando... ");
 			
 			int count = 0;
 			
 			File fil = null;

 			for(File f : arquivos) {
 				
 				if(f.getName().equals("filtro" + ".txt")){
 					
 					count = count +1;
 					
 					if(count > 1) {
 						
 						ErrorAlert("Existe Mais de Um Arquivo com Esse Nome");
 						return f;
 						
 					}else {
 					
 					fil = f;
 					trace.textProperty().unbind();
 					trace.setText("Arquivo Encontrado :" + fil.getName());
 					
 				//	progressind.setVisible(false);
 					
 					}
 					
 				}
 				
 				
 				
 				
 			}
 			
 			return fil;
 			
 			
 			
 		}
 		
 		public void ErrorAlert(String msg){
 			
 			Alert alert = new Alert(AlertType.ERROR);
 			alert.setTitle("Erro ao Buscar Arquivo.");
 			alert.setHeaderText(null);
 			alert.setContentText("Erro: "+msg);
 			//jtgbligar.setSelected(false);
 			alert.showAndWait();
 		}
 	
 	
 	private File RetornarArquivoValidoConf(List<File> arquivos) {
 		
 		   //  trace.setText("Filtrando... ");
 			
 			int count = 0;
 			
 			File fil = null;

 			for(File f : arquivos) {
 				
 				if(f.getName().equals("conf" + ".txt")){
 					
 					count = count +1;
 					
 					if(count > 1) {
 						
 						ErrorAlert("Existe Mais de Um Arquivo com Esse Nome");
 						return f;
 						
 					}else {
 					
 					fil = f;
 					
// 					trace.textProperty().unbind();
 					trace.setText("Arquivo Encontrado :" + fil.getPath());
 					
 				//	progressind.setVisible(false);
 					
 					}
 					
 				}
 				
 				
 				
 				
 			}
 			
 			return fil;
 			
 			
 			
 		}
 	
 	
 	public ArrayList<File> buscarArquivoPorNome(String palavra, String caminhoInicial){
 	    ArrayList<File> lista = new ArrayList<File>();
 	    

 	    
 	   
 	    try{
 	        File arquivo = new File(caminhoInicial);
 	        lista =  buscar(arquivo, palavra, lista);        
 	    } catch (Exception e) {
 	    	
 	       System.out.println("Caminho Inválido" + caminhoInicial);
 	   //    trace.setText("Caminho Inválido" + caminhoInicial);
 	    }
 	    return lista;
 	}

 	public ArrayList<File> buscar(File arquivo, String palavra, ArrayList<File> lista) {
 	    if (arquivo.isDirectory()) {
 	    	File[] subPastas = arquivo.listFiles();
 	        for (int i = 0; i < subPastas.length; i++) {
 	            lista = buscar(subPastas[i], palavra, lista);
 	            if (arquivo.getName().equalsIgnoreCase(palavra)){
 	            	
 	           
 	            	System.out.println("Busca : " + arquivo);
 	             	lista.add(arquivo);
 	            	
 	            //	trace.setText("Busca : " + arquivo);
 	            	
 	            }
 	            
 	            
 	            else if (arquivo.getName().indexOf(palavra) > -1){
 	            	System.out.println("Busca elseif: for" + arquivo);
 	            	
// 	            	trace.textProperty().unbind();
// 	            	trace.setText("Busca:  " + arquivo);
// 	            	trace.textProperty().bind();
 	            	
 	            	lista.add(arquivo);
 	            }
 	        }
 	    }
 	    else if (arquivo.getName().equalsIgnoreCase(palavra)){
 	    	
 	    	System.out.println("Busca elseif: 2" + arquivo);
 	    	
// 	    	trace.textProperty().unbind();
// 	    	trace.setText("Busca: " + arquivo);
 	    	
 	    	lista.add(arquivo);
 	    }
 	    else if (arquivo.getName().indexOf(palavra) > -1){
 	    	System.out.println("Busca elseif: 3" + arquivo);
 	    	
// 	    	trace.textProperty().unbind();
// 	    	trace.setText("Busca: " + arquivo);
 	    	
 	    	lista.add(arquivo);
 	    }
 	    return lista;
 	}
 	
 	
 	
 	
	private void PreencherValoresArquivados(File c,File f){
		
//		lines= new HashMap<Integer, String>();
//		
//		linesfilter= new HashMap<Integer, Filtro>();
		
		manipulador = new ManipuladorArquivo(c,f);
		
		lines= manipulador.getLines();
		
		System.out.println("line: " + lines.size());
		
		if(lines.isEmpty()){

		manipulador.Escrever(c.getPath(),nomeArquivo);
		manipulador.Escrever(c.getPath(),nomeModelo);
		manipulador.Escrever(c.getPath(),usuarioconf);
		manipulador.Escrever(c.getPath(),senhaconf);
		manipulador.Escrever(c.getPath(),hostconf);
		manipulador.Escrever(c.getPath(),protocoloconf);
		manipulador.Escrever(c.getPath(),portaconf);
		
	//	lines = manipulador.getLines();
		
//		trace.setText(manipulador.Ler(c));
	
		}else {
			
			
			
			nomeArquivo = lines.get(0);
			nomeModelo = lines.get(1);
			usuarioconf= lines.get(2);
			senhaconf= lines.get(3);
			hostconf= lines.get(4); 
			protocoloconf= lines.get(5);
			portaconf= lines.get(6);
			
		}
		
		
		linesfilter= manipulador.getLinesfiter();
		
//		int count = linesfilter.size();
		
		int countt = 0;
		
		System.out.println("lines filter: " + linesfilter.size());
		
		Set<Integer> keyset =null;
		
		if(linesfilter.isEmpty() || !(filtros.isEmpty())){
			
			
			
			 for (int i = 0; i < filtros.size(); i++) {
				 
				 Filtro fil = filtros.get(i);
				 
				 manipulador.EscreverFilter(f.getPath(),fil);
				
			}
			 
			 
		}else {
				
				
				//filtros.clear();
				
				 keyset = linesfilter.keySet();
				
				for(Integer s : keyset) {
					
					
					String[] lin = linesfilter.get(s).getValor().split(",");
					
					Filtro fil = new Filtro();
					fil.setChave(lin[0]);
					fil.setValor(lin[1]);
					fil.setObjeto(lin[2]);
					
					filtros.add(fil);
				
				
				
			}
			
				
				
			
		}
		
		
	
//		}else {
//			
//			 	keyset = linesfilter.keySet();
//				for(Integer s : keyset){
//				
//				
//				String[] lin = linesfilter.get(s).getValor().split(",");
//				Filtro fil = new Filtro();
//				fil.setChave(lin[0]);
//				fil.setValor(lin[1]);
//				fil.setObjeto(lin[2]);
//				
//				filtros.add(fil);
//			
//			
//				}
				
//			for(Integer s : keyset){
//				
//				Filtro fil = linesfilter.get(s);
//				filtros.add(fil);
//				manipulador.EscreverFilter(f.getPath(),filtros.get(countt));
//				countt++;
//				
//			}
//			
//			
//			linesfilter = manipulador.getLinesfiter();
//			trace.textProperty().unbind();
//			trace.setText(manipulador.LerFilter(f));
		
		procurarArquivos();
		arquivoLocalizado =  RetornarArquivoValido(arquivos);
		
		if(arquivoLocalizado.exists()){
		CriarDiretorio(arquivoLocalizado);
		}else {
			
			try {
				arquivoLocalizado.createNewFile();
				CriarDiretorio(arquivoLocalizado);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	//	PreencherAjuda();
			
		}
	
	
	private File RetornarArquivoValido(List<File> arquivos) {
		
		   //  trace.setText("Filtrando... ");
			
			int count = 0;
			
			File fil = null;

			for(File f : arquivos) {
				
				if(f.getName().equals(nomeArquivo + ".xlsx")){
					
					count = count +1;
					
					if(count > 1) {
						
						ErrorAlert("Existe Mais de Um Arquivo com Esse Nome");
						return f;
						
					}else {
					
					fil = f;
					trace.textProperty().unbind();
					trace.setText("Arquivo Encontrado: " + fil.getName());
					
//					progressind.setVisible(false);
					
					}
					
				}
				
				
				
				
			}
			
			return fil;
			
			
			
		}
	
	private void CriarDiretorio(File arquivoLocalizado) {

//		progressind.setVisible(true);
//		
//		progressindligar.setVisible(true);
		
		Date data = new Date();

		
		String datmes = PegarMes(data);
		String datdia = PegarDia(data);
		String datano = PegarAno(data);
		String datfinal = "oficios" +"\\" + datano + "\\" + datmes + "\\"+ datdia +"\\";
		
		
//		File dirfinal = null;
		
		
		
		dirfinal = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), ""),"\\" +datfinal);
		
		 System.out.println(""+dirfinal.getPath() );
		 
		if( dirfinal.mkdirs() ){
		    System.out.println("Diretório criado");
//		    oficiook = true;
		    
		}else{
			
			
		    System.out.println("Diretório não criado");
//		    oficiook = false;
		}
		
		
		if(arquivoConf == null){
			
			String aux = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), ""); 
			aux = aux + "configuracao" + "\\" + "conf.txt" ;
			
			arquivoConf = new File(aux);
			
			System.out.println("aux:" + aux);
			
		}else {
			
//			manipulador = new ManipuladorArquivo(arquivoConf);
//			lines = manipulador.getLines();
//			lines = valoresConfig();
			
			
		}
		if(arquivoFiltro == null){
			
			String aux = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), ""); 
			aux = aux  + "configuracao" + "\\" + "filtro.txt" ;
			
			arquivoFiltro = new File(aux);
			
			System.out.println("aux filtro:" + aux);
			
			
		}else {
			
			
//			manipulador.setFilefiltro(arquivoFiltro);
			
			
		}
		
		manipulador = new ManipuladorArquivo(arquivoConf,arquivoFiltro);
//		
//		lines = manipulador.getLines();
//		lines = valoresConfig();
//		
//		
//		linesfilter = manipulador.getLinesfiter();
//		linesfilter = valoresFilter();
		
		
//		try{
//			dirfinalarq = new File(dirfinal, modelo.getName());
//		    System.out.println("teste file:"+ dirfinalarq.getPath());
//		    
//		    if( dirfinalarq.createNewFile() ){
//		        System.out.println("Arquivo criado");
//		        modelook = true;
//		      //  dirfinalarq.delete();
//		        
//		    }else{
//		        System.out.println("Arquivo não criado");
//		        modelook = false;
//		    }       
//		}catch(IOException ex){
//		    ex.printStackTrace();
//		}	
		
		
	//	CarregarInfo();
		
	}
	
	
	

	private String PegarDia(Date data){

		Calendar c = new GregorianCalendar();
		c.setTime(data);
		String dianome = "";
		int dia = c.get(c.DAY_OF_MONTH);
		
		String day = String.valueOf(dia);
		
		
		switch(dia){
		  case Calendar.SUNDAY: dianome = "Domingo";break;
		  case Calendar.MONDAY: dianome = "Segunda";break;
		  case Calendar.TUESDAY: dianome = "Terça";break;
		  case Calendar.WEDNESDAY: dianome = "Quarta";break;
		  case Calendar.THURSDAY: dianome = "Quinta";break;
		  case Calendar.FRIDAY: dianome = "Sexta";break;
		  case Calendar.SATURDAY: dianome = "Sábado";break;
		}
		
		
		
		return day;
	}


	private String PegarMes(Date data){
		

		
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		String mesnome = "";

		int mes =c.get(c.MONTH);
		
		
		switch(mes){
		  case Calendar.JANUARY: mesnome = "Janeiro";break;
		  case Calendar.FEBRUARY: mesnome = "Fevereiro";break;
		  case Calendar.MARCH: mesnome = "Março";break;
		  case Calendar.APRIL: mesnome = "Abril";break;
		  case Calendar.MAY: mesnome = "Maio";break;
		  case Calendar.JUNE: mesnome = "Junho";break;
		  case Calendar.JULY: mesnome = "Julho";break;
		  
		  case Calendar.AUGUST: mesnome = "Agosto";break;
		  case Calendar.SEPTEMBER: mesnome = "Setembro";break;
		  case Calendar.OCTOBER: mesnome = "Outubro";break;
		  case Calendar.NOVEMBER: mesnome = "Novembro";break;
		  case Calendar.DECEMBER: mesnome = "Dezembro";break;
		}
		
		
		
		return mesnome;
	}

	private String PegarAno(Date data){
		
		
		Calendar c = new GregorianCalendar();
		c.setTime(data);
		String anonome = "";
		int ano =c.get(c.YEAR);
		
		anonome = String.valueOf(ano);
		
		
		return anonome;
	}
	
	public void procurarArquivos() {
		
		
//		progressind.setVisible(true);
//		progressindligar.setVisible(true);
		
		if(nomeArquivo !=null && nomeArquivo!=""){
			
			String path = System.getProperty("user.home");
			String pathfinal = path + "\\" + "Desktop";
			
			System.out.println("path  :" + pathfinal);
		//	trace.setText("path :" + pathfinal);
			
			arquivos = buscarArquivoPorNome(nomeArquivo, pathfinal);
			
			
		}else {
			
			
			ErrorAlert("Preencha o Nome do Arquivo na Configuração!");
			progressind.setVisible(false);
		//	progressindligar.setVisible(false);
			
			
		}
		
		
		
		}
	
	
	
	
public void RotacionarCena(){
		
		if(rotate == null) {
			
			rotate = new RotateTransition(Duration.seconds(10), subcena);
		//	trans.setFromAngle(0.0);
			rotate.setToAngle(360.0);
			rotate.setAxis(new Point3D(3.0,5.0, 7.0));
			// Let the animation run forever
			rotate.setCycleCount(12);
			// Reverse direction on alternating cycles
			rotate.setAutoReverse(true);
			// Play the Animation
			rotate.play();
			
			
			
		}
		
		else {
			
			rotate.playFromStart();
			
			
		}

		
	}
	
	
	public void PararRotacionarCena(){
		
		if(rotate.getStatus().equals(Animation.Status.RUNNING)){
			
			rotate.stop();
			
		} else {
			
		
			
			
		}
	
		
		
	}
    	 
     }
     
 
    

	
    

