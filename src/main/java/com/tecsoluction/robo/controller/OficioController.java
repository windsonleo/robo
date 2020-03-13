package com.tecsoluction.robo.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.lowagie.text.DocumentException;
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.CopyTask;
import com.tecsoluction.robo.entidade.CopyTaskDet;
import com.tecsoluction.robo.entidade.Detento;
import com.tecsoluction.robo.entidade.Filtro;
import com.tecsoluction.robo.entidade.GerenciadorFiltro;
import com.tecsoluction.robo.entidade.LeitorExcel;
import com.tecsoluction.robo.entidade.Notificacao;
import com.tecsoluction.robo.entidade.Registro;
import com.tecsoluction.robo.entidade.Violacao;
import com.tecsoluction.robo.util.HeaderFooterPageEvent;
import com.tecsoluction.robo.util.WordPoi;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class OficioController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(OficioController.class);

	 	private static double xOffset = 0;
	    private static double yOffset = 0;
	
	    
	    @Lazy
	    @Autowired
	    private StageManager stageManager;

	
	    @FXML
	    private VBox vbox;

	    @FXML
	    private AnchorPane anchor;

	    @FXML
	    private JFXTextField filterField;

	    @FXML
	    private JFXButton btcarregarRegistro;

	    @FXML
	    private Label trace;

//	 
//	    @FXML
//	    private JFXButton btgeraroficio;
	    @FXML
	    private JFXButton  btgerarall;

	    @FXML
	    private ImageView imgvconf;

	    @FXML
	    private JFXButton btfechar;
	    
	  
	    @FXML
	    private JFXButton  btcarregararquivo;
	    
	    @FXML
	    private ImageView imgvfechar;

	    @FXML
	    private ProgressIndicator progressind;
	    
	    @FXML
	    private Label size;

	    @FXML
	    private AnchorPane anchorcena;
	    
	    private File    dirfinal;
	    
	    private String numoficio;
	    
	    private File arquivoLocalizado;

	    private File filesaved ;
	    
  private  ObservableList<Detento> filtros = FXCollections.observableArrayList();
  
  private List<Registro> registrosNotificar = new ArrayList<Registro>();
  
  private List<com.tecsoluction.robo.entidade.Detento> detentoFinal = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
  
  private List<com.tecsoluction.robo.entidade.Detento> detentosucesso = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
  
  private List<com.tecsoluction.robo.entidade.Detento> detentoErro = new ArrayList<com.tecsoluction.robo.entidade.Detento>();  
  
  private List<Registro> registroFiltrados = new ArrayList<Registro>();
	    
	    @FXML
	    private JFXTreeTableView<Detento> tableview ;
	    
//	    private JFXTreeTableColumn<Filtro,Boolean> deletecoluna ;
	    
	    private JFXTreeTableColumn<Detento,String> numerocoluna;
	    
	    private JFXTreeTableColumn<Detento,String> nomecoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> idmonitoradocoluna;
	    
	    private JFXTreeTableColumn<Detento,String> prontuariocoluna;
	    
	    private JFXTreeTableColumn<Detento,String> Estabelecimentocoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> perfilatualcoluna;
	    
	    private JFXTreeTableColumn<Detento,String> emailcoluna;
	    
	    private JFXTreeTableColumn<Detento,String> statusdoc;
	    
	    
//	    private JFXTreeTableColumn<Detento,String> valorcoluna ;
//	  
//	    private JFXTreeTableColumn<Detento,String> objetocoluna;
	    
	    
	    private JFXTreeTableColumn<Detento,String> caracteristicacoluna;
	    
	    private JFXTreeTableColumn<Detento,String> statusenviocoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> processocoluna;
	    
	    
	    private JFXTreeTableColumn<Detento,String> vepcoluna;
	    
	    private JFXTreeTableColumn<Detento,String> erroscoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> varacoluna;
	    
//	    
	    private JFXTreeTableColumn<Detento,String> dataviolacoluna;
	    
	    private JFXTreeTableColumn<Detento,Boolean> buttomcoluna;
	    
	    private JFXTreeTableColumn<Detento,Boolean> buttomExccoluna;
//	    
//	    private JFXTreeTableColumn<Detento,String> valorcoluna ;
//	  
//	    private JFXTreeTableColumn<Detento,String> objetocoluna;
//	    
//	    private JFXTreeTableColumn<Detento,String> chavecoluna;
//	    
//	    private JFXTreeTableColumn<Detento,String> valorcoluna ;
//	  
//	    private JFXTreeTableColumn<Detento,String> objetocoluna;
	    
	    
	    private List<Filtro> filtrosent = new ArrayList<Filtro>();
	    
	    
	    private List<com.tecsoluction.robo.entidade.Detento> detentos = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
	    
	    private List<Registro> registros = new ArrayList<Registro>();
		   
		   private List<com.tecsoluction.robo.entidade.Detento> detentoscarregados = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
		
	   
		   private List<com.tecsoluction.robo.entidade.Detento> detentoserros = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
 
	
		   private List<Registro> registrosSubstituto = new ArrayList<Registro>();
		   
		   private List<Registro> registrosSubstitutosemNoti = new ArrayList<Registro>();
		   
		   private List<Registro> registrosSubstitutosAll = new ArrayList<Registro>();
		   
		   
		   private String nomeModelo;
	    
	    private CopyTaskDet copyDet;
	    
	    private CopyTask copyreg;
	    
	    @FXML
	    private JFXTextField jtxtfarquivo;
	    
		private  String processofinal ;
		
		private String tabhtml;
		
		 @FXML
		private JFXCheckBox jcbfiltro;
		 
		 @FXML
		private JFXCheckBox jcbmostrarerros;
		 @FXML
	    private JFXComboBox<String> jcbvara;
		 
		 
		  private boolean validodet = false;
		  
		  private  GerenciadorFiltro gerenciafiltro;
		  
		  
			 @FXML
				private JFXCheckBox jcbmostrarvalidos;
			 
			 
				
			    @FXML
			    private VBox  vboxfilter;
			    
			    
			    @FXML
			    private JFXTextField jtxtfilter;
			    
			    
			    @FXML
			    private JFXButton btaplicarfilter;
	    
		
	    public OficioController() {
			// TODO Auto-generated constructor stub
		}
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		 EventHandler<ActionEvent> eventt = new EventHandler<ActionEvent>() { 
	            public void handle(ActionEvent e) 
	            { 
	            	
	           
	            	carregararquivo();
	            	
	            	
	            	copyreg = new CopyTask(registros);
	            	
	        		progressind.progressProperty().unbind();
	        		
	        		progressind.progressProperty().bind(copyreg.progressProperty());
	        		

	        		
	        		trace.textProperty().unbind();
	        		
	        		trace.textProperty().bind(copyreg.messageProperty());
	        		
//                    if(jcbfiltro.isSelected()){
//                    	
//                    	 GerenciadorFiltro gerenciafiltro;
//                        
//                    if(!filtros.isEmpty()) {
//                        
//                        gerenciafiltro = new GerenciadorFiltro(filtrosent, registrosNotificar, detentoFinal);
//                        
//                        setRegistros(gerenciafiltro.getRegistros());
//                        
//                        setDetentos(gerenciafiltro.getDetentos());
//                        
//                    	//lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
//                        
//                        filter();
//                        
//                        System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
//                        System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());
//
//                        
//                        System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
//                        System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
// 
//                        System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
//                        System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
//                    
//                        System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
//                        System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
//                        
//                        
//                        }
//                 
//               
//                   
//                    
//                    
//
//                } else {
//               	 
//               	 
//               	 
//               	 
//               	 
//               	 
//                }


	        		copyreg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
	                         new EventHandler<WorkerStateEvent>() {

//	                             private GerenciadorFiltro gerenciafiltro;

								@Override
	                             public void handle(WorkerStateEvent t) {
	                                 List<com.tecsoluction.robo.entidade.Registro> copied = copyreg.getValue();
	                                

	                                
	                                 trace.textProperty().unbind();
	                                 trace.setText("Importados: " + copied.size() + " Registros");
	                                
	                               
//	                                 trace.textProperty().bind(copyreg.messageProperty());
//	                                 trace.textProperty().unbind();
	                              
	                                 if(jcbfiltro.isSelected()){
	                                 
	                                 if(!filtros.isEmpty()) {
	                                     
	                                     gerenciafiltro = new GerenciadorFiltro(filtrosent, registrosNotificar, detentoFinal);
	                                     
	                                     setRegistros(gerenciafiltro.getRegistros());
	                                     
	                                     setDetentos(gerenciafiltro.getDetentos());
	                                     
	                                 	//lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
	                                     
	                                     filter();
	                                     
	                                     System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
	                                     System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());

	                                     
	                                     System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
	                                     System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
	              
	                                     System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
	                                     System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
	                                 
	                                     System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
	                                     System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
	                                     
	                                     
	                                     }
	                              
	                            
	                                
	                                 
	                                 
	          
	                             } else {
	                            	 
	                            	 
	                            	 
	                            	 
	                            	 
	                            	 
	                             }
	                                 
	                                 ValidaDetentoBefore();
	                                	 
	                                	 initColuna();
		                               	 setCellFactoryColuna();
		                               	
		                               	InserirDadosTabela(detentoFinal);
		                              	 
		                               	InitTabela();
		                               	
//		                                QuantificarResultados();
		                                 
//		                                btcarregarRegistro.fire();
		                                 
//		                                 QuantificarResultados();
		                                 
		                                 jcbvara.getItems().setAll(PreencherComboBox());
		                                 
		                                 trace.setText("Convertidos e Filtrados: " + detentoFinal.size() + " Detentos");
									
	                                	 
		                                 vboxfilter.setDisable(false);
	                                 
	                                 
	                                 progressind.setVisible(false);
	                                 
	                                
								
								}




	                         });
	           

	                 // Start the Task.
	                 new Thread(copyreg).start();
	                 
	            	
	            } 
	            
	  
	        }; 
	        
	        
	        
	        btcarregararquivo.setOnAction(eventt);
		
		
		 EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
	            public void handle(ActionEvent e) 
	            { 
	            	
	           
	            	gerarall();
	            	
	            	
	            	copyDet = new CopyTaskDet(detentos);
	            	
	            	
	        		progressind.progressProperty().unbind();
	        		
	        		progressind.progressProperty().bind(copyDet.progressProperty());
	        		

	        		
	        		trace.textProperty().unbind();
	        		
	        		trace.textProperty().bind(copyDet.messageProperty());


	        		copyDet.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
	                         new EventHandler<WorkerStateEvent>() {

	                             @Override
	                             public void handle(WorkerStateEvent t) {
	                                 List<com.tecsoluction.robo.entidade.Detento> copied = copyDet.getValue();
	                                
	                                 
	                                 trace.textProperty().bind(copyDet.messageProperty());
//	                                 trace.textProperty().unbind();

//	                                 QuantificarResultados();
	                                
	                                 trace.textProperty().unbind();
	                                 trace.setText("Gerados: " + detentosucesso.size() + " Arquivos");
	                                
	                               


	                            
	                                 progressind.setVisible(false);
	                                 
	                        		 initColuna();
	                            	 setCellFactoryColuna();
	                                 
	                             	InserirDadosTabela(detentosucesso);
	                             	
	                            	InitTabela();
	                                 
//	                                 btcarregarRegistro.fire();
	                                 
	                                 
	          
	                             }




	                         });
	           

	                 // Start the Task.
	                 new Thread(copyDet).start();
	                 
	            	
	            } 
	            
	  
	        }; 
	        
	        
	        
	        btgerarall.setOnAction(event);
	        
	        
	        
	        
	        jcbvara.valueProperty().addListener((observable, oldValue, newValue) -> {
	    	
	        	
	        	if (oldValue != newValue) {
	        		
	        		
	        		String valor = jcbvara.getSelectionModel().getSelectedItem();
	        		
	        		FiltrarDetentos(valor);
	    		
	    		
	    		}else {
	    			
	    			
	    			
	    			
	    		}
	        	
	        	
	    	});
		
		 
		
		
	}	
	
	
	
	
    private void FiltrarDetentos(String valor) {
    	
    	List<com.tecsoluction.robo.entidade.Detento> dets = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
    	

    	for(com.tecsoluction.robo.entidade.Detento det : detentoFinal){
    		
    		
    		if(det.getVara() != null && det.getVara()!=""){
    			
    			if(det.getVara().toString().equals(valor)){
        			
        			
        			dets.add(det);
        			
        		}else {
        			
        			
        			
        			
        		}
    			
    			
    		}
    		
    		
    		
    		
    	}
    	
		 initColuna();
    	 setCellFactoryColuna();

    	InserirDadosTabela(dets);
    	
    	InitTabela();
    	
//    	InitComponentes();
    	
	}





	@FXML
   private void open() throws IOException {

      FileChooser filechoose = new FileChooser();
  	  filechoose.setTitle("escolha a Planilha");
  	  
  	  filesaved = filechoose.showOpenDialog(jtxtfarquivo.getParent().getScene().getWindow());
  	 
  	  System.out.println("caminho absoluto: " + filesaved.getAbsolutePath());
  	  System.out.println("caminho canonico: " + filesaved.getCanonicalPath());
  	  System.out.println("caminho canonico: " + filesaved.getPath());
  	  
  	  if (filesaved != null) {

  		jtxtfarquivo.setText(filesaved.getName());
  		jtxtfarquivo.labelFloatProperty().setValue(false);

  	  }
  	  
    
    
    }
    
    
    private void carregarexcel() {

		
        if ((filesaved != null) && (filesaved.getAbsolutePath() != null)) {
      		 
        	
        	String extensao = getFileExtension(filesaved.getName());
        	
        	
        	if((extensao.contains("xlsx")) && (extensao!= null)) {
        		
          		 LeitorExcel leitor = new LeitorExcel(filesaved.getAbsolutePath());
           		 
           		 String path = leitor.getSAMPLE_XLSX_FILE_PATH();
           		 
           		 try {

           		//	progressind.setVisible(true);
           			registros = leitor.readRegistersFromExcelFile(path);
           			 
           			 
           			 detentoscarregados = RegistroToDetento(registros);
           			 
           			
           	    	
           	    	filter();
           			
           			jtxtfarquivo.setDisable(true);
//           	    	 btcarregar.setDisable(true);
           			

           			
       			} catch (IOException e) {
       				e.printStackTrace();
       				System.out.println("falha ao ler registro : " + e);
       				jtxtfarquivo.setDisable(false);
//            	 btcarregar.setDisable(false);
//         	    btfilter.setDisable(true);
//         	    btremoveduplicado.setDisable(true);
       			}
        		
        		
        	}else {
        		
//        		ExtesionAlert(extensao);
        		jtxtfarquivo.setDisable(false);
//            	 btcarregar.setDisable(false);
//         	    btfilter.setDisable(true);
//         	    btremoveduplicado.setDisable(true);
        		
        		
        	}
       		 

      		               		 
      	 }else {
      		 
      		trace.setText("Carregue um Arquivo valido..");
      		jtxtfarquivo.setDisable(false);
//        	 btcarregar.setDisable(false);
//        	 
//     	    btfilter.setDisable(true);
//     	    btremoveduplicado.setDisable(true);
      		 
      		 
      	 }
    	

}
    
    
	 private void filter() {

		   
		 
//		   progressind.setVisible(true);
//		   progressindligar.setVisible(true);
		   
		 
		 SubstitutoAll();
		   
		  // trace.setText("Filtrando Violações");
		  registrosNotificar = new ArrayList<Registro>();
//		  registroscancela.clear();
//		  registrosSubstituto.clear();
//		  detentosviolavazia.clear();
//		  registroFiltrados.clear();
		   
		   
		   String cancela = "cancela";
		   
		   String substitui = "substitui";
		   
//			qtdsubstituida = 0;
//			qtdnotcancela  = 0;
		   
		   
		 //  List<Registro> regs = new ArrayList<Registro>();
		   
		   String idnotificacao = new String();
		   
			for (int i = 0; i < getRegistros().size(); i++) {
				
			Registro registro = getRegistros().get(i);
			
			idnotificacao = registro.getIdnotitificacao();
			
			for (int j = 0; j < getRegistros().size(); j++) {
				
				Registro regaux = getRegistros().get(j);
				
				   String idnotificacaoaux = new String();
				   
				   idnotificacaoaux = regaux.getIdnotitificacao();
				
				if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(idnotificacao.toUpperCase())) {
					
					if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(cancela.toUpperCase())){
					
				//	registro.setIsremove(true);
		
					
					if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
						
						
//						registro.setIssubstituto(false);
						registro.setIsremove(true);
						regaux.setIssubstituto(true);
						regaux.setIsremove(false);
						
						
//						if(registrosSubstituto.contains(regaux)){
							
							
							
//						}else {
							
							registrosSubstituto.add(regaux);	
//							qtdsubstituida = qtdsubstituida +1;
//						}
						
//						if(registrosNotificar.contains(regaux)){
//							
//							
//							
//						}else {
//							
//							registrosNotificar.add(regaux);	
////							qtdsubstituida = qtdsubstituida +1;
//						}
						
//						if(registroFiltrados.contains(registro)){
//							
//							
//							
//						}else {
//							
//							registroFiltrados.add(registro);	
//							qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//						}
						
						
						
//						registrosNotificar.add(regaux);
//						registroFiltrados.add(registro);
						
//						qtdregistrosfiltrados = qtdregistrosfiltrados +1;
					
					}else {
						
//						registro.setIsremove(true);
						regaux.setIsremove(true);
//						registro.setIssubstituto(false);
						regaux.setIssubstituto(false);
						registro.setIsremove(true);
						
					
						
						
//						if(registroscancela.contains(regaux)){
							
							
							
//						}else {
							
//							registroscancela.add(regaux);
//							qtdnotcancela  = qtdnotcancela +1;
							
						
						
						
//						if(registroFiltrados.contains(regaux)){
//							
//							
//							
//						}else {
//							
//							registroFiltrados.add(regaux);	
//							qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//						}
//						
//						if(registroFiltrados.contains(registro)){
//							
//							
//							
//						}else {
//							
//							registroFiltrados.add(registro);	
//							qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//						}
						
						
						//registroscancela.add(registro);
						
//						registroFiltrados.add(registro);
//						registroFiltrados.add(regaux);
//						
//						qtdregistrosfiltrados = qtdregistrosfiltrados +2;
						
						
					}
					
					
					
					
					if(registro.isIssubstituto() && registro.isIsremove()){
						
//						registrosduplicidade.add(registro);
						
					}
					
					
					
				}
				
				
				} else {
					
					
					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(idnotificacaoaux.toUpperCase())) {
						
						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())){
						
					//	registro.setIsremove(true);
			
						
						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
							
							
//							registro.setIssubstituto(false);
							regaux.setIsremove(true);
							registro.setIssubstituto(true);
							registro.setIsremove(false);
							
							
//							if(registrosSubstituto.contains(registro)){
								
								
								
//							}else {
								
								registrosSubstituto.add(registro);	
//								qtdsubstituida = qtdsubstituida +1;
//							}
							
//							if(registrosNotificar.contains(registro)){
//								
//								
//								
//							}else {
//								
//								registrosNotificar.add(registro);	
////								qtdsubstituida = qtdsubstituida +1;
//							}
							
//							if(registroFiltrados.contains(registro)){
//								
//								
//								
//							}else {
//								
//								registroFiltrados.add(registro);	
//								qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//							}
							
							
							
//							registrosNotificar.add(registro);
//							registroFiltrados.add(registro);
							
//							qtdregistrosfiltrados = qtdregistrosfiltrados +1;
						
						}else {
							
//							registro.setIsremove(true);
							registro.setIsremove(true);
//							registro.setIssubstituto(false);
							registro.setIssubstituto(false);
							regaux.setIsremove(true);
							
						
							
							
//							if(registroscancela.contains(registro)){
								
								
								
//							}else {
								
//								registroscancela.add(registro);
//								qtdnotcancela  = qtdnotcancela +1;
								
							}
							
							
//							if(registroFiltrados.contains(registro)){
//								
//								
//								
//							}else {
//								
//								registroFiltrados.add(registro);	
//								qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//							}
//							
//							if(registroFiltrados.contains(registro)){
//								
//								
//								
//							}else {
//								
//								registroFiltrados.add(registro);	
//								qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//							}
							
							
							//registroscancela.add(registro);
							
//							registroFiltrados.add(registro);
//							registroFiltrados.add(registro);
//							
//							qtdregistrosfiltrados = qtdregistrosfiltrados +2;
							
							
						
						
						
						
						
						if(registro.isIssubstituto() && registro.isIsremove()){
							
//							registrosduplicidade.add(registro);
							
						}
						
						
						
					}
					
					
					} else {
						
						
						
						
						
						
					}
					
					
					
				}
				
				
				
//					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(idnotificacaoaux.toUpperCase())) {
//					
//					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())){
//					
//						regaux.setIsremove(true);
//		
//					
//					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
//						
//						
////						registro.setIssubstituto(false);
//						registro.setIssubstituto(true);
//						registro.setIsremove(false);
//						
//						qtdsubstituida = qtdsubstituida +1;
//						
//						registrosSubstituto.add(registro);
//					
//					}else {
//						
////						registro.setIsremove(true);
//						registro.setIsremove(true);
////						registro.setIssubstituto(false);
//						registro.setIssubstituto(false);
//						
//						qtdnotcancela  = qtdnotcancela +1;
//						
//						registroscancela.add(registro);
//						//registroscancela.add(registro);
//						
//						
//					}
//					
//					
//					
//					
//					if(regaux.isIssubstituto() && regaux.isIsremove()){
//						
//						registrosduplicidade.add(regaux);
//						
//					}
//				
//					}
//					
//					
//					}else {
//						
//						
//						
//						
//					}
				
			}
			
			
			
			
//			if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())) {
//				
//				//	registro.setIsremove(true);
//		
//					
//					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
//						
//						
////						registro.setIssubstituto(false);
//					//	registro.setIsremove(true);
//						registro.setIssubstituto(true);
//						registro.setIsremove(false);
//						
//						qtdsubstituida = qtdsubstituida +1;
//						
//						registrosSubstituto.add(registro);
//						
//						registrosNotificar.add(registro);
////						registroFiltrados.add(registro);
//						
////						qtdregistrosfiltrados = qtdregistrosfiltrados +1;
//					
//					}else {
//						
////						registro.setIsremove(true);
//						registro.setIsremove(true);
////						registro.setIssubstituto(false);
////						regaux.setIssubstituto(false);
////						registro.setIsremove(true);
//						
//						qtdnotcancela  = qtdnotcancela +1;
//						
//						registroscancela.add(registro);
//						//registroscancela.add(registro);
//						
////						registroFiltrados.add(registro);
////						registroFiltrados.add(regaux);
////						
////						qtdregistrosfiltrados = qtdregistrosfiltrados +2;
//						
//						
//					}
//					
//					
//					
//					
//					if(registro.isIssubstituto() && registro.isIsremove()){
//						
//						registrosduplicidade.add(registro);
//						
//					}
//					
//					
//			}	
			
			
			if((registro.getDescricao() == null || registro.getDescricao()=="" ||
					registro.getDescricao().toUpperCase().contains("SEM VIOLAÇÃO")||registro.getDescricao().toUpperCase().contains("EVENTO RESTAURADO")||registro.getDescricao().toUpperCase().contains("SEM NOTIFICAÇÃO")||registro.getDescricao().toUpperCase().contains("SEM RELATÓRIO")||registro.isIsremove()) && (!registro.isIssubstituto())){					
		
				
//				registroFiltrados.add(registro);
				
//				
//				if(registroFiltrados.contains(registro)){
//					

//					
//				}else {
				if(registro.getDescricao().toUpperCase().contains("SUBSTITUI")){
					
					registro.setIssubstituto(true);
					registrosNotificar.add(registro);
					
				}else {
					
					
					registroFiltrados.add(registro);	
//					qtdregistrosfiltrados = qtdregistrosfiltrados +1;
					
				}
					

//				}
				
				
				
				
				//getRegistros().remove(registro);
				
				//registroFiltrados.add(registro);
				
//				registroFiltrados.add(registro);
				
			}
			
//			if(registro.getNotificacao().contains("[SEM VIOLAÇÃO")&&(registro.getNotificacao().contains("cancela"))){
//				
//				
//				
//				
//			}
			
			else {
				
//				if(registrosNotificar.contains(registro)){
					
					
					
//				}else {
					
					
					registrosNotificar.add(registro);
					
//				}
				
			}
			
//			registrosNotificar.add(registro);
				
			}
			
			
		//	registroFiltrados.addAll(registrosNotificar);
			
			registrosSubstitutosemNoti = SubstituitosSemNotificao(registrosSubstituto);
			
			detentos.clear();
			detentos.addAll(RegistroToDetento(registrosNotificar));
			setRegistros(registrosNotificar);
			
			
			System.out.println("Inicio da Remoção");
//			trace.setText("Inicio da Remoção");
			
			System.out.println("detentos" + detentos.size());
			
			removeduplicado();
			
			
//			 AtualizarQuadroRegistros();
//			 AtualizarQuadro();
	    
	    
	    } 
	 
	 
	 private List<Registro> SubstituitosSemNotificao(List<Registro> registrosSubstituto2) {

			
			registrosSubstitutosemNoti.clear();
			
			for (Registro reg : registrosSubstitutosAll){
				
				if(registrosSubstituto2.contains(reg)){
					
					
					
				}else {
					
					registrosSubstitutosemNoti.add(reg);
					
					
				}
				
				
			}
			
			
			
			
			
			
			return registrosSubstitutosemNoti;
		}



		private void SubstitutoAll(){
			
			registrosSubstitutosAll.clear();
			
			String substitui = new String("substitui");
			
			for (int i = 0; i < getRegistros().size(); i++) {
				
				Registro registro = getRegistros().get(i);
				
				//idnotificacao = registro.getIdnotitificacao();
				
				
				if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
					
					if(registrosSubstitutosAll.contains(registro)){
						
						
						
					}else {
						
						registrosSubstitutosAll.add(registro);
						
					}
					
				
				}else {
					
					
					
				}
				
//				for (int j = 0; j < getRegistros().size(); j++) {
//					
//			
//					Registro regaux = getRegistros().get(i);
//					
//					
//				}
				
				
			}
			
			
		}
		
    
    
    private void removeduplicado() {
		
		List<com.tecsoluction.robo.entidade.Detento> detfinal = RemoverDuplicidade();

		
		detentos.clear();
		detentos.addAll(detfinal);
//		PreencherComboBox();
		
		System.out.println("fim da Remoção");
//		trace.setText("fim da Remoção");
		
		System.out.println("detentos fim" + detentos.size());
		
//		trace.setText("detentos fim" + detentos.size());
		
	//	progressind.setVisible(false);
//		progressindligar.setVisible(false);
//		
//		 AtualizarQuadroRegistros();
//		 AtualizarQuadro();
		 

	 }
    
    
    private List<com.tecsoluction.robo.entidade.Detento> RemoverDuplicidade(){
		

		progressind.setVisible(true);
		
		detentoFinal = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
		
		int count = 0;
		int countv = 0;
		
		
		//percorrer os detentos e comparar se existe
		for (int j = 0; j < detentos.size(); j++) {
			
			com.tecsoluction.robo.entidade.Detento detentoCompara = detentos.get(j);

			List<Violacao> violacoesCompara = detentoCompara.getViolacoes();
			
			if(ExisteDetento(detentoCompara)){
				
				
				violacoesCompara = RecuperarDetentoRepetido(detentoCompara);
				List<Violacao> vios = removeViolacoesInvalidas(violacoesCompara);
				
				count =count+1;
				
				if(vios.isEmpty()){
					
					//detentosremovidos.add(detentoCompara);
					detentoFinal.remove(detentoCompara);
					
					
				}else {
					
					
					detentoCompara.setViolacoes(vios);
					
				}
				
//				detentosrepetidos.add(detentoCompara);
				
				
				System.out.println("entrou duplicidade: " + count);

				
			}else {
				

				detentoCompara.setViolacoes(removeViolacoesInvalidas(violacoesCompara));
				
			}
			
			
			
			
			System.out.println("fim detento" + j);
	
			
			if(detentoCompara.getViolacoes().isEmpty()){
				
				System.out.println("dettento viola vazia" + countv);
				
//				detentosremovidos.add(detentoCompara);
				
//				detentoFinal.remove(detentoCompara);
				
				countv=countv+1;
				
			}else {
				
			//	PreencherVaraComCaracteristica(detentoCompara);
				
				
				
				detentoFinal.add(detentoCompara);
				
//				ValidaDetentoBefore();
				
			}
			
		
			
		}
		
		
		

	System.out.println("qtd detentos : " + detentos.size());
	System.out.println("qtd reg : " + registros.size());
	System.out.println("qtd final det fimal : " + detentoFinal.size());
	
		
		
		
		return detentoFinal;
 
 }
    
    private boolean ExisteDetento(com.tecsoluction.robo.entidade.Detento det) {

		boolean existe = false;
		//trace.setText("Verificando existencia de Detento");
			
		
		
		for (int i = 0; i < getDetentoFinal().size(); i++) {
			
			com.tecsoluction.robo.entidade.Detento detento = getDetentoFinal().get(i);
		
		if(detento.getProntuario().equals(det.getProntuario())){
			
			
			//detentosremovidos.add(det);
			existe = true;
			return existe;
			
		}
		
	
			
			
		}
		
		 
		
		 
		return existe;
	}
 
 
 private List<Violacao> RecuperarDetentoRepetido(com.tecsoluction.robo.entidade.Detento detento){
		
		List<Violacao> repetidos = new ArrayList<Violacao>();
		
		for (int i = 0; i < getDetentoFinal().size(); i++) {
			
			com.tecsoluction.robo.entidade.Detento detentoaux = getDetentoFinal().get(i);
		
		if(detentoaux.getProntuario().equals(detento.getProntuario())){
			
			
			detentoaux.getViolacoes().addAll(detento.getViolacoes());
			repetidos = detentoaux.getViolacoes();
			
			
			getDetentoFinal().remove(detentoaux);
			//esse detentosremovidos.add(detentoaux);
			
		//	detentosrepetidos.add(detentoaux);
			
			
			
		}else {
			
			
			
		}
		

			
			
		}
		
		
		return repetidos;
	}
 
 
 
 private List<Violacao> removeViolacoesInvalidas(List<Violacao> violas) {
		
		List<Violacao> vios = violas;
		
		String procurarpor1 = new String("EVENTO RESTAURADO");
		
		String procurarpor2 = new String("SEM VIOLAÇÃO");
		
		String procurarpor3 = new String("SEM VIOLACAO");
		
		String procurarpor4 = new String("SEM NOTIFICAÇÃO");
		
		String procurarpor5 = new String("SEM NOTIFICACAO");
		String procurarpor6 = new String("JÁ NOTIFICADO");
		String procurarpor7 = new String("JA NOTIFICADO");
		
		String procurarpor8 = new String("CANCELADO");
		
		String procurarpor9 = new String("CANCELADA");
		
		String procurarpor10 = new String("SEM RELATÓRIO");
		
		String procurarpor11 = new String("SEM RELATORIO");
		
		String procurarpor12 = new String("CANCELA");
		
		String procurarpor13 = new String("cancela");
		
		String substitui = new String("substitui");
		
		String substitui22 = new String("RESPOSTA CIODS");
		
		String substitui2 = new String("resposta ciods");
				
		for (int i = 0; i < violas.size(); i++) {
			
			Violacao v = violas.get(i);
			
			
			
			for (int j = 0; j < v.getNotificacoes().size(); j++) {
				
				com.tecsoluction.robo.entidade.Notificacao noti = v.getNotificacoes().get(j);
				
				if ((noti.getDescricao().toLowerCase().contains(procurarpor1.toLowerCase()) || noti.getDescricao().toLowerCase().contains(procurarpor2.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor3.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor4.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor5.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor6.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor7.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor8.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor9.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor10.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor11.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(procurarpor12.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor13.toLowerCase())
				||noti.getDescricao().toLowerCase().contains(substitui22.toLowerCase())||noti.getDescricao().toLowerCase().contains(substitui2.toLowerCase()))
						&& (!noti.getDescricao().toLowerCase().contains(substitui.toLowerCase())) && (!noti.isIssubstituto()) && (noti.isIsremove()))
			
			{
					
					
				System.out.println("entrou no if principal: remove violacao" + noti.getId());
				System.out.println("entrou no if principal: remove violacao is substituto" + noti.issubstituto);
				System.out.println("entrou no if principal: remove violacao viola size" + v.getNotificacoes().size());

				System.out.println("entrou no if principal: remove violacao viola nots" + v.getNotificacoes().toString());
				
				if(v.getNotificacoes().size() > 0){
					
					if((!noti.isIssubstituto()) && (noti.isIsremove()) ){
						
						v.removeNotificacao(noti);
						
					}else {
						
						
						
					}
					
					

					
				}else {
					
					vios.remove(v);
					
					
				}
				
				
//				else {
//					
//					
//					
//					vios.remove(v);
//					
//					System.out.println("entrou no if principal: remove violacao 2" + v.getId());	
//
//					
//				}
				
				
				
				if (v.getNotificacoes().isEmpty() || v.isIsremove()) {
					
					
					System.out.println("entrou no if principal:remove violacao, notificaacao vazia" + v.getId());	
					
					
					vios.remove(v);
					
				}
				


				

			}
				
			}
			

				
				  
				
			}
		
		return vios;
			
		}
    
    
	static String getFileExtension(String filename) {
	    if (filename.contains("."))
	        return filename.substring(filename.lastIndexOf(".") + 1);
	    else
	        return "";
	}

    private List<com.tecsoluction.robo.entidade.Detento> RegistroToDetento(List<Registro> registros2) {
		

		List<com.tecsoluction.robo.entidade.Detento> detentos = new ArrayList<com.tecsoluction.robo.entidade.Detento>();	
		
		
		for (int i = 0; i < registros2.size(); i++) {
			
			Registro reg =	registros2.get(i);		
			com.tecsoluction.robo.entidade.Detento det = new com.tecsoluction.robo.entidade.Detento(reg);
			Violacao viola = new Violacao(reg);
			
			com.tecsoluction.robo.entidade.Notificacao notificacao = new com.tecsoluction.robo.entidade.Notificacao(reg);
			
			viola.setDetento(det);
			notificacao.setViolacao(viola);
			
			viola.addNotificao(notificacao);
			det.addViolacao(viola);

			detentos.add(det);
			
		}
				 
		return detentos;

}
  
    
    void Inicializar (List<Filtro> fils){
   	 
//   	 this.detentos = dets;
   	 this.filtrosent = fils;
   	 
   	 
//   	 initColuna();
//   	 setCellFactoryColuna();
//
//   	 InserirDadosTabela(detentos);
//
//   	 InitTabela();
    	 
     }
     
    @SuppressWarnings("unchecked")
  	private void initColuna(){
      	 
      	 numerocoluna  = new JFXTreeTableColumn<Detento,String>("Notificações");
//      	 numerocoluna.setMinWidth(100);
//      	 numerocoluna.setMaxWidth(100);
      	 numerocoluna.setVisible(true);
      	 numerocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
      		    if(numerocoluna.validateValue(param)) return param.getValue().getValue().notificacao;
      		    else return numerocoluna.getComputedValue(param);
      		});
   		
      	 nomecoluna = new JFXTreeTableColumn<Detento,String>("Nome");
//      	nomecoluna.setMinWidth(150);
//      	nomecoluna.setMaxWidth(250);
   		//valorcoluna.setPrefWidth(100);
      	 nomecoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(nomecoluna.validateValue(param)) return param.getValue().getValue().nome;
  		    else return nomecoluna.getComputedValue(param);
  		});
      	 
      	 
      	 
      	statusdoc = new JFXTreeTableColumn<Detento,String>("Doc");
//      	statusdoc.setMinWidth(50);
//      	statusdoc.setMaxWidth(80);
   		//valorcoluna.setPrefWidth(100);
      	statusdoc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(statusdoc.validateValue(param)) return param.getValue().getValue().statusdoc;
  		    else return statusdoc.getComputedValue(param);
  		});
   		
      	 idmonitoradocoluna = new JFXTreeTableColumn<Detento,String>("Violação");
//      	idmonitoradocoluna.setMinWidth(100);
//      	idmonitoradocoluna.setMaxWidth(150);
      	 idmonitoradocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(idmonitoradocoluna.validateValue(param)) return param.getValue().getValue().violacoes;
  		    else return idmonitoradocoluna.getComputedValue(param);
  		});
      	 
      	 
      	 prontuariocoluna = new JFXTreeTableColumn<Detento,String>("Prontuario");
//      	prontuariocoluna.setMinWidth(50);
//      	prontuariocoluna.setMaxWidth(80);
      	prontuariocoluna.setVisible(false);
  		//valorcoluna.setPrefWidth(100);
      	 prontuariocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(prontuariocoluna.validateValue(param)) return param.getValue().getValue().prontuario;
  		    else return prontuariocoluna.getComputedValue(param);
  		});
      	 
      	 
      	 Estabelecimentocoluna = new JFXTreeTableColumn<Detento,String>("Estabelecimento");
//      	Estabelecimentocoluna.setMinWidth(50);
//      	Estabelecimentocoluna.setMaxWidth(80);
      	Estabelecimentocoluna.setVisible(false);
  		//valorcoluna.setPrefWidth(100);
      	 Estabelecimentocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(Estabelecimentocoluna.validateValue(param)) return param.getValue().getValue().Estabelecimento;
  		    else return Estabelecimentocoluna.getComputedValue(param);
  		});
      	 
      	 
      	 perfilatualcoluna = new JFXTreeTableColumn<Detento,String>("Perfilatual");
//      	perfilatualcoluna.setMinWidth(50);
//      	perfilatualcoluna.setMaxWidth(80);
      	 perfilatualcoluna.setVisible(false);
  		//valorcoluna.setPrefWidth(100);
      	 perfilatualcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(perfilatualcoluna.validateValue(param)) return param.getValue().getValue().perfilatual;
  		    else return perfilatualcoluna.getComputedValue(param);
  		});
      	 
      	 
      	caracteristicacoluna = new JFXTreeTableColumn<Detento,String>("Caracteristica");
//      	caracteristicacoluna.setMinWidth(80);
//      	caracteristicacoluna.setMaxWidth(120);
      	caracteristicacoluna.setVisible(true);
  		//valorcoluna.setPrefWidth(100);
      	caracteristicacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(caracteristicacoluna.validateValue(param)) return param.getValue().getValue().caracteristica;
  		    else return caracteristicacoluna.getComputedValue(param);
  		});
      	 
      	 
      	 statusenviocoluna = new JFXTreeTableColumn<Detento,String>("statusenvio");
//      	statusenviocoluna.setMinWidth(50);
//      	statusenviocoluna.setMaxWidth(80);
  		//valorcoluna.setPrefWidth(100);
      	 statusenviocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(statusenviocoluna.validateValue(param)) return param.getValue().getValue().statusenvio;
  		    else return statusenviocoluna.getComputedValue(param);
  		});
      	 
      	 
      	 emailcoluna = new JFXTreeTableColumn<Detento,String>("email");
//      	emailcoluna.setMinWidth(80);
//      	emailcoluna.setMaxWidth(120);
      	emailcoluna.setVisible(false);
  		//valorcoluna.setPrefWidth(100);
      	 emailcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(emailcoluna.validateValue(param)) return param.getValue().getValue().email;
  		    else return emailcoluna.getComputedValue(param);
  		});
      	 
      	 processocoluna = new JFXTreeTableColumn<Detento,String>("processo");
//      	processocoluna.setMinWidth(80);
//      	processocoluna.setMaxWidth(100);
  		//valorcoluna.setPrefWidth(100);
      	 processocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(processocoluna.validateValue(param)) return param.getValue().getValue().processo;
  		    else return processocoluna.getComputedValue(param);
  		});
      	 
      	 vepcoluna = new JFXTreeTableColumn<Detento,String>("vep");
//      	vepcoluna.setMinWidth(80);
//      	vepcoluna.setMaxWidth(100);
  		//valorcoluna.setPrefWidth(100);
      	 vepcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(vepcoluna.validateValue(param)) return param.getValue().getValue().vep;
  		    else return vepcoluna.getComputedValue(param);
  		});
      	 
      	 erroscoluna = new JFXTreeTableColumn<Detento,String>("erros");
//      	erroscoluna.setMinWidth(250);
//      	erroscoluna.setMaxWidth(350);  	
      	//valorcoluna.setPrefWidth(100);
      	 erroscoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(erroscoluna.validateValue(param)) return param.getValue().getValue().erros;
  		    else return erroscoluna.getComputedValue(param);
  		});
      	 
      	 varacoluna = new JFXTreeTableColumn<Detento,String>("vara");
//      	varacoluna.setMinWidth(80);
//      	varacoluna.setMaxWidth(120); 
  		//valorcoluna.setPrefWidth(100);
      	 varacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(varacoluna.validateValue(param)) return param.getValue().getValue().vara;
  		    else return varacoluna.getComputedValue(param);
  		});
   		
      	 
      	 dataviolacoluna = new JFXTreeTableColumn<Detento,String>("Data Violação");
//      	dataviolacoluna.setMinWidth(50);
//      	dataviolacoluna.setMaxWidth(80); 
      	 dataviolacoluna.setVisible(false);
  		//valorcoluna.setPrefWidth(100);
      	 dataviolacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
  		    if(dataviolacoluna.validateValue(param)) return param.getValue().getValue().dataviola;
  		    else return dataviolacoluna.getComputedValue(param);
  		});
      	 
      	 
      	 buttomcoluna =  new JFXTreeTableColumn<Detento,Boolean>("Gerar");
//      	buttomcoluna.setMinWidth(50);
//      	buttomcoluna.setMaxWidth(50); 

      	 buttomExccoluna =  new JFXTreeTableColumn<Detento,Boolean>("Excluir");
//      	buttomExccoluna.setMinWidth(50);
//      	buttomExccoluna.setMaxWidth(50); 
 
//      	 dataviolacoluna.setPrefWidth(100);
//      	 dataviolacoluna.setVisible(false);
   
       }
       
       
       
       
       @SuppressWarnings({ "unchecked", "unused" })
  	private void InitTabela() {
      	 
      	 
      	 final TreeItem<Detento>  root = new RecursiveTreeItem<Detento>(filtros, RecursiveTreeObject::getChildren);
      	 
      	// tableview = new JFXTreeTableView<Filtro>(root);
      	 
      	 tableview.setShowRoot(false);
         	 tableview.setEditable(true);
         	 tableview.setRoot(root);
//         	 tableview.getChildrenUnmodifiable
         	 tableview.getColumns().setAll(nomecoluna, idmonitoradocoluna,numerocoluna,statusdoc,
         			 prontuariocoluna,Estabelecimentocoluna,perfilatualcoluna,caracteristicacoluna,varacoluna,
         			erroscoluna,vepcoluna,processocoluna,emailcoluna,statusenviocoluna,dataviolacoluna,buttomcoluna,buttomExccoluna);
         	 
       	InitComponentes();
         	 
         //	anchor.getChildren().add(tableview);
          	
      	 
       }
       
       
       
       
       private void InitComponentes() {

      	 
      	 //filterField = new JFXTextField();
      	 filterField.textProperty().addListener((o,oldVal,newVal)->{
      		 tableview.setPredicate(filtro -> filtro.getValue().nome.get().contains(newVal)
//      	                 || filtro.getValue().valor.get().contains(newVal)
//      				 || filtro.getValue().nome.get().contains(newVal)
      				 || filtro.getValue().violacoes.get().contains(newVal)
      				 || filtro.getValue().prontuario.get().contains(newVal)
      				 || filtro.getValue().Estabelecimento.get().contains(newVal)
      				 || filtro.getValue().perfilatual.get().contains(newVal)
      				 || filtro.getValue().notificacao.get().contains(newVal)
//      				 || filtro.getValue().vara.get().contains(newVal)
      				 || filtro.getValue().erros.get().contains(newVal)
      				 || filtro.getValue().statusdoc.get().contains(newVal)
//      				 || filtro.getValue().processo.get().contains(newVal)
      				 || filtro.getValue().email.get().contains(newVal)
      				 || filtro.getValue().statusenvio.get().contains(newVal)
//      				 || filtro.getValue().caracteristica.get().contains(newVal)
      		 		 || filtro.getValue().dataviola.get().contains(newVal));

      	
      	 
      	 
      	 });
      	  
      	// Label size = new Label();
      	 size.textProperty().bind(Bindings.createStringBinding(()->tableview.getCurrentItemsCount()+"",
      			 tableview.currentItemsCountProperty())); 
      	 
      	 
  	}





  	private void setCellFactoryColuna(){
      	 
      	 
  		numerocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
      	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
      	 
  		numerocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
       ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).notificacao
                   .set(t.getNewValue());
   });
    
  		nomecoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
      	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
      	 
  		nomecoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
       ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                   .nome.set(t.getNewValue());
   });
      	 
  		idmonitoradocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
      	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
      	 
  		idmonitoradocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
       ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                   .violacoes.set(t.getNewValue());
   });
      	 
      	
  		
  		prontuariocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		prontuariocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .prontuario.set(t.getNewValue());
  });
  		
  		
  		Estabelecimentocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		Estabelecimentocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .Estabelecimento.set(t.getNewValue());
  });
  		
  		
  		perfilatualcoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		perfilatualcoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .perfilatual.set(t.getNewValue());
  });
  		
  		
  		caracteristicacoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		caracteristicacoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .caracteristica.set(t.getNewValue());
  });
  		
  		
  		varacoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		varacoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .vara.set(t.getNewValue());
  });
  		
  		
  		erroscoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		erroscoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .erros.set(t.getNewValue());
  });
  		
  		
  		vepcoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		vepcoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .vep.set(t.getNewValue());
  });
  		
  		processocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		processocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .processo.set(t.getNewValue());
  });
  		
  		emailcoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		emailcoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .email.set(t.getNewValue());
  });
  		
  		statusenviocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
     	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
     	 
  		statusenviocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
      ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                  .statusenvio.set(t.getNewValue());
  });
  		
//  		artigoscoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
//  	   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
//  	   	 
//  		artigoscoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
//  	    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
//  	                .artigos.set(t.getNewValue());
//  	});
  			
  			
  		dataviolacoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
  		   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
  		   	 
  		dataviolacoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
  		    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
  		                .dataviola.set(t.getNewValue());
  		});
  		
  		
  		statusdoc.setCellFactory((TreeTableColumn<Detento, String> param) -> 
		   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
		   	 
  		statusdoc.setOnEditCommit((CellEditEvent<Detento, String> t)->{
		    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
		                .statusdoc.set(t.getNewValue());
		});
  		
  		
//  				
//  				statusenviocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
//  			   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
//  			   	 
//  					statusenviocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
//  			    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
//  			                .statusenvio.set(t.getNewValue());
//  			});
      	 
      	 
      buttomcoluna.setCellFactory(cellFactory);
      buttomExccoluna.setCellFactory(cellFactorydel);
  	
  	}
       
       
       
     
  	public void InserirDadosTabela(List<com.tecsoluction.robo.entidade.Detento> detentos){
      	 
      	 filtros.clear();
//      	 detentoFinal.clear();
//      	 detentoFinal.addAll(detentos);
    	   
    	   for(int i = 0 ; i< detentos.size(); i++){
       		
      		 com.tecsoluction.robo.entidade.Detento detento = detentos.get(i);
      		 
      		 
      		 if(filtros.contains(detento)){
      			 
      			 
      			 
      		 }else {
      			 
      			 filtros.add(new Detento(detento));
      			 
      		 }
      		
      	
      	 
      	 }
      	 

      	 
       }
       
       
       Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>> cellFactory
       = //
       new Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>>() {
           @Override
           public TreeTableCell<com.tecsoluction.robo.controller.OficioController.Detento, Boolean> call(final TreeTableColumn<Detento, Boolean> param) {
               final TreeTableCell<Detento, Boolean> cell = new TreeTableCell<Detento, Boolean>() {

                 
            	   
            	   Image imgg = new Image(getClass().getResourceAsStream("/images/icons8-arquivo-96.png"),24.0,24.0,true,true);
            	   
            	   final JFXButton btn = new JFXButton();
                   
                   com.tecsoluction.robo.entidade.Detento det = null;

                   @Override
                   public void updateItem(Boolean item, boolean empty) {
                       super.updateItem(item, empty);
                       if (empty) {
                           setGraphic(null);
                           setText(null);
                       } else {
                           btn.setButtonType(JFXButton.ButtonType.RAISED);
                           btn.setOnAction(event -> {

                           
                          	 TreeItem<Detento> empresa = getTreeTableView().getTreeItem(getIndex());
                          	 
//               				Detento value =	empresa.getValue();
//              				det = value.det;
                          	 
                          	 GerarOficioDetento(empresa);
                          	 
                          	 System.out.println(" clicado id:" + empresa);

       	
                           
                           
                           
                           });
                           
                           ImageView ivv = new ImageView();
    				        ivv.setImage(imgg);
    				        ivv.setPreserveRatio(true);
    				        ivv.setSmooth(true);
    				        ivv.setCache(true);
    				        btn.setGraphic(ivv);
                           setGraphic(btn);
                           setText(null);
                           
//           				if(det.getStatusenvio().equals("SUCESSO")||det.getStatusenvio().equals("PENDENTE")){
//          					
//          					btn.setDisable(true);
//          					
//          				}
                       }
                       
                       
                   }

  				private void GerarOficioDetento(TreeItem<Detento> empresa) {

  				Detento value =	empresa.getValue();
//  				com.tecsoluction.robo.entidade.Detento det = value.det;
  				
  				det= value.det;
  				
  				det = ConverterDetento(value);
  				
  				boolean isvalido = ValidarDetento(det);
  				
  				if(isvalido){
  					
  					String retorno = GerarDocumento(det);
  					
//  					det.setStatusenvio("PENDENTE");
  					
  					det.setStatusdoc("SUCESSO");

  					filtros.remove(value);
  					
  					detentoFinal.remove(value.det);
  					
  					detentoFinal.add(det);
  					
  					  					
//  					QuantificarResultados();
  					
//  					 initColuna();
			     	 setCellFactoryColuna();
			     	 

				     InserirDadosTabela(detentoFinal);
			     	 
			     	 
				     InitTabela();
				     	
				     	trace.setText("Sucesso: " + det.getNome());
			     	 
  					
  				}else {
  					
  					
//  					value.det.setStatusdoc("ERROR");
  					
//  					value.det = det;
  					
  					//value.det = det ;
  					
  					detentoFinal.remove(value.det);
  					
  					detentoFinal.add(det);
  					
  					
//  					initColuna();
			     	 setCellFactoryColuna();
			     	InserirDadosTabela(detentoFinal);
			     	InitTabela();
  					
  					
  				}
  				
  				
  				
//  				filtros.remove(value);
  					
//  					detentos.add(det);
  					

  					
  					
  					
  					
			     	
			    	
  					
  					
  				
//  					trace.setText("Qtd Gerado: " + 	detentosucesso.size() + "\n" +
//  					"Erros: " + detentoserros.size());
  					
  					
  					
  					
  				}
               };
               return cell;
           }
       };
       
       
       Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>> cellFactorydel
       = //
       new Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>>() {
           @Override
           public TreeTableCell<com.tecsoluction.robo.controller.OficioController.Detento, Boolean> call(final TreeTableColumn<Detento, Boolean> param) {
               final TreeTableCell<Detento, Boolean> cell = new TreeTableCell<Detento, Boolean>() {

            	   Image imgDel = new Image(getClass().getResourceAsStream("/images/del.png"));
            	   
                   final JFXButton btn = new JFXButton();
                   
                   com.tecsoluction.robo.entidade.Detento det = null;

                   @Override
                   public void updateItem(Boolean item, boolean empty) {
                       super.updateItem(item, empty);
                       if (empty) {
                           setGraphic(null);
                           setText(null);
                       } else {
                           btn.setButtonType(JFXButton.ButtonType.RAISED);
                           btn.setOnAction(event -> {

                           
                          	 TreeItem<Detento> empresa = getTreeTableView().getTreeItem(getIndex());
                          	 
               				Detento value =	empresa.getValue();
              				det = value.det;
                          	 
              				DeleteDetento(empresa);
                          	 
                          	 System.out.println(" clicado id:" + empresa);

       	
                           
                           
                           
                           });
                           
                           ImageView ivv = new ImageView();
   				        ivv.setImage(imgDel);
   				        ivv.setPreserveRatio(true);
   				        ivv.setSmooth(true);
   				        ivv.setCache(true);
   				     btn.setGraphic(ivv);
   				        
                           setGraphic(btn);
                           setText(null);
                           
//           				if(det.getStatusenvio().equals("SUCESSO")||det.getStatusenvio().equals("PENDENTE")){
//          					
//          					btn.setDisable(true);
//          					
//          				}
                       }
                       
                       
                   }

  				private void DeleteDetento(TreeItem<Detento> empresa) {

  				Detento value =	empresa.getValue();
  				det = value.det;
  				
  				System.out.println("valor detento: " + value.nome);
  				System.out.println("valor detento dtet: " + det);
//  				trace.textProperty().unbind();
  				trace.setText("Deletado: " + value.nome);
  				
  				filtros.remove(value);
  				
  				detentoFinal.remove(det);
  				
  			
  				
//  				 initColuna();
  			   	 setCellFactoryColuna();
  			   	
  			   	InserirDadosTabela(detentoFinal);
  			  	 
  			   	InitTabela();
  			   	
//  				InitComponentes();
  					
  					
  					
  					
  				}
               };
               return cell;
           }
       };
       
    
    
// 	@FXML
// 	public void salvaroficio(){
// 		
//
// 		trace.setText("Clicado em Salvar Oficio");
// 		
//// 		nomeArquivo = jtxtfarquivonome.getText();
// 		
// 		
// 	}
 	
 	
 	@FXML
	private void mousepress(MouseEvent event){
					
		
  
		vbox.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            xOffset = stageManager.getPrimaryStage().getX() - event.getScreenX();
            yOffset = stageManager.getPrimaryStage().getY() - event.getScreenY();
        }
    });
		
		
	}
	
	
	@FXML
	private void mousedrag(MouseEvent event){
		
		vbox.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
	                       
	                	updateMessage("Fechando Janela Oficio.");
	                	
	                	
	                	Thread.sleep(1000);
	                	
	                //	updateMessage("Fechar.");
	                       
	                        return 1;
	                }
	            };
	        }

	        @Override
	        protected void succeeded() {

	        	
	        	
//	        	progressind.setVisible(false);
	        	
	    	    Stage stage = (Stage) getBtfechar().getScene().getWindow();
	    	    stage.close();

			 
	        }
	        
	        
	        
	    }.start();
		
		
		

		
		
	}
	
//	@FXML
//	void openpathoficio() {
//		
//			FileChooser filechoose = new FileChooser();
//			filechoose.setTitle("escolha o caminho do Oficio");
//		  
//		   pathoficio = filechoose.showOpenDialog(jtxtpathoficio.getParent().getScene().getWindow());
//		
//		   jtxtpathoficio.setText(pathoficio.getPath());
//		   
//		   
//		   nomearquivo = pathoficio.getName();
//		   
//		   caminhooficio = jtxtpathoficio.getText().replace(nomearquivo, "");
//		   
//		   nomearquivo="";
//		   btsalvarpathoficio.setDisable(false);
//		   
//		   
////		   jtxtpathoficio.setDisable(true);
//
//	}
	

	@FXML
	void CarregarRegistro(ActionEvent event) {
		
		
		
		 initColuna();
    	 setCellFactoryColuna();
    	
    	InserirDadosTabela(detentoFinal);
   	 
    	InitTabela();
		


	}
	
	
	
	class Detento extends RecursiveTreeObject<Detento>{
//		StringProperty chave;
//		StringProperty valor;
//		StringProperty objeto;
		
		com.tecsoluction.robo.entidade.Detento det;
		
		StringProperty notificacao;
		
		StringProperty numero;

		StringProperty nome;
		    
		StringProperty idmonitorado;
		    
		StringProperty prontuario;
		    
		StringProperty Estabelecimento	;
		StringProperty perfilatual;
		StringProperty artigos;
		StringProperty statusenvio;
		
		StringProperty statusdoc;

//		  @JsonIgnore
//		  @OneToMany(fetch=FetchType.EAGER)
//		    private List<Violacao> violacoes = new ArrayList<Violacao>();
		
		StringProperty violacoes;
		   
		StringProperty	email;
		    
		StringProperty	processo;
		    
		StringProperty	vep;
		    
		StringProperty	ult_posicao;
		    
		StringProperty	alarme_posicao;
		    
		StringProperty	mae_pai;
		    
		StringProperty	telefone;
		    
		StringProperty	equipamentos;
		    
//		    private List<Financeiro> financeiros = new ArrayList<Financeiro>();
		
		StringProperty financeiros;
		   
		StringProperty valortotal;
		    
		StringProperty erros ;
		    
		StringProperty vara ;
		
		StringProperty dataviola ;
		
		StringProperty caracteristica ;
		
		
		    
//		     boolean temnotificacaocancel=false;
		
		public Detento(String numero,String nome,String violacoes,String prontuario,String Estabelecimento,String perfilatual,
				String ult_posicao,String vep,String processo,String email,String statusenvio,String artigos
				,String alarme_posicao,String equipamentos,String telefone,String valortotal,String erros
				,String vara,String dataviola, String notif,String caracteristica,String statusdoc)
		
		{
			this.numero = new SimpleStringProperty(numero);
			this.nome  = new SimpleStringProperty(nome);
			this.violacoes  = new SimpleStringProperty(violacoes);
			this.notificacao = new SimpleStringProperty(notif);
//			this.caracteristica = new SimpleStringProperty(caracteristica);
			
			this.prontuario  = new SimpleStringProperty(prontuario);
			
			this.statusdoc = new SimpleStringProperty(statusdoc);
			
			this.Estabelecimento  = new SimpleStringProperty(Estabelecimento);
			this.perfilatual  = new SimpleStringProperty(perfilatual);
			this.artigos  = new SimpleStringProperty(artigos);
			this.statusenvio  = new SimpleStringProperty(statusenvio);
			this.email  = new SimpleStringProperty(email);
			this.processo  = new SimpleStringProperty(processo);
			this.vep  = new SimpleStringProperty(vep);
			this.ult_posicao  = new SimpleStringProperty(ult_posicao);
			this.alarme_posicao  = new SimpleStringProperty(alarme_posicao);
			this.equipamentos  = new SimpleStringProperty(equipamentos);
			
			this.telefone  = new SimpleStringProperty(telefone);
			this.valortotal  = new SimpleStringProperty(valortotal);
			this.erros  = new SimpleStringProperty(erros);
			this.vara  = new SimpleStringProperty(vara);
			this.dataviola  = new SimpleStringProperty(dataviola);
//			this.equipamentos  = new SimpleStringProperty(equipamentos);
			this.caracteristica = new SimpleStringProperty(this.det.getViolacoes().get(0).getCaracteristica());

			
			
			
			
			//this.address = new SimpleStringProperty(address);
			
		}
		
		
		
		public Detento(com.tecsoluction.robo.entidade.Detento dete) {
			
			this.det = dete;
			
//			this.numero = new SimpleStringProperty(dete.getNumero());
			this.nome  = new SimpleStringProperty(dete.getNome());
			this.violacoes  = new SimpleStringProperty(dete.getViolacoes().toString());
			this.dataviola  = new SimpleStringProperty(FormatadorData (dete.getViolacoes().get(0).getDataviolacao()));
			this.notificacao = new SimpleStringProperty(retornarVio(dete.getViolacoes()).toString());
			this.prontuario  = new SimpleStringProperty(dete.getProntuario());
			
			this.Estabelecimento  = new SimpleStringProperty(dete.getEstabelecimento());
			this.perfilatual  = new SimpleStringProperty(dete.getPerfil());
//			this.artigos  = new SimpleStringProperty(dete.getArtigos());
			this.statusenvio  = new SimpleStringProperty(dete.getStatusenvio());
			this.email  = new SimpleStringProperty(dete.getEmail());
			this.processo  = new SimpleStringProperty(dete.getProcesso());
			this.vep  = new SimpleStringProperty(dete.getVep());
			this.statusdoc = new SimpleStringProperty(dete.getStatusdoc());
//			this.ult_posicao  = new SimpleStringProperty(dete.getUlt_posicao());
//			this.alarme_posicao  = new SimpleStringProperty(dete.getAlarme_posicao());
//			this.equipamentos  = new SimpleStringProperty(dete.getEquipamentos());
			
//			this.telefone  = new SimpleStringProperty(dete.getTelefone());
//			this.valortotal  = new SimpleStringProperty(dete.getValortotal());
			this.erros  = new SimpleStringProperty(dete.getErros().toString());
			this.vara  = new SimpleStringProperty(dete.getVara());
//			this.equipamentos  = new SimpleStringProperty(equipamentos);
			
			this.caracteristica = new SimpleStringProperty(dete.getViolacoes().get(0).getCaracteristica());
			
			
			
		}
		
		
	}
	
	
	private boolean ValidarDetento(com.tecsoluction.robo.entidade.Detento det2) {
		
		
//		progressind.setVisible(true);
		
		boolean valido = true;
		
		String nulo = null;
		String branco = "";
		
		
//		PreencherVaraComCaracteristica(det2);
				
				
			 if((det2.getProntuario()==null||(det2.getProntuario().equals(branco)) )){
					
				valido = false;
				det2.addErros("Prontuario");
					
//				Validadet2Informacao("Prontuario det2 Branco ou Nulo");
					
				}
				
			if((det2.getProcesso()==null)&&(det2.getVep()==null) ){
					
				valido = false;
				det2.addErros("Processo/VEP NULOS");
				
//				Validadet2Informacao("Processo det2 Branco ou Nulo");
					
				}
				
			 if((det2.getViolacoes().toString()==null||(det2.getViolacoes().toString().equals(branco)) )){
					
				valido = false;
				det2.addErros("Violações");
				
//				Validadet2Informacao("Violações det2 Branco ou Nulo");
					
				}
			 
			 if((det2.getViolacoes().get(0).getCaracteristica()==null || det2.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
					
					valido = false;
					det2.addErros("CARACTRISTICA");
					
//					Validadet2Informacao("Violações det2 Branco ou Nulo");
						
					}
			 
			 if((det2.getEstabelecimento().toString()==null||(det2.getEstabelecimento().toString().equals(branco)) )){
					
					valido = false;
					det2.addErros("Estabelecimento");
					
//					Validadet2Informacao("Violações det2 Branco ou Nulo");
						
					}
			 
			 
			 if((det2.getVara() == null || det2.getVara().equals(branco)) ){
					
					valido = false;
					det2.addErros("Vara");
					
//					Validadet2Informacao("Violações det2 Branco ou Nulo");
						
					}
				 
				 
//				 if((det2.getVara().toString()==null||(det2.getVara().toString().equals(branco)) )){
//						
//					valido = false;
//					det2.addErros("Vara");
//					
////					Validadet2Informacao("Violações det2 Branco ou Nulo");
//						
//					}
				 
				 
//				 if((det2.getEquipamentos().toString()==null||(det2.getEquipamentos().toString().equals(branco)) )){
//						
//						valido = false;
//						det2.addErros("Equip");
//						
////						Validadet2Informacao("Violações det2 Branco ou Nulo");
//							
//						}
			 
//				if( (det2.getProcesso()!=null) && (!det2.getProcesso().isEmpty()) && (det2.getVep()!=null)&&(!det2.getVep().isEmpty()) ){
//					
//					
//					valido = false;
//					det2.addErros("Processo/VEP PREENCHIDO");
//					
////					Validadet2Informacao("Processo det2 Branco ou Nulo");
//						
//				
//				}
				
				if( (det2.getProcesso()!=null) && (det2.getProcesso().isEmpty()) && (det2.getVep()!=null)&&(det2.getVep().isEmpty()) ){
					
					
					valido = false;
					det2.addErros("Processo/VEP BRANCOS 6");
					
//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
						
				
				}
				
				if( ((det2.getProcesso()!=null) && (!det2.getProcesso().isEmpty())) && ((det2.getVep()!=null)&&(!det2.getVep().isEmpty())) ){
					
					
					valido = false;
					det2.addErros("Processo/VEP PREENCHIDO 60");
					
//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
						
				
				}
		
		
		
		return valido;
		

	}
	
	public List<Notificacao> retornarNotificacoes(Violacao vio){
		
		List<Notificacao> not = new ArrayList<Notificacao>();
		
		for (int i = 0; i < vio.getNotificacoes().size(); i++) {
			
			Notificacao notifi = vio.getNotificacoes().get(i);
			
			not.add(notifi);
			
			
		}
		
		
		
		
		
		return not;
	}
	
	
	public List<Notificacao> retornarVio(List<Violacao> vios){
		
		List<Notificacao> not = new ArrayList<Notificacao>();
		
		for (int i = 0; i < vios.size(); i++) {
			
			Violacao v = vios.get(i);
			
			not.addAll(retornarNotificacoes(v));
			
			
		}
		
		
		return not;
	}
	
//	
//	@FXML
//	private void geraroficio(){
//		
////		Image image = new Image(getClass().getResourceAsStream("/images/settings (1).png"),64.0,64.0,true,true);
////		imgvconf.setImage(image);
////		btconf.setGraphic(imgvconf);
////		btconf.setTextFill(Color.WHITE);
//		
//		System.out.println("Gerar oficio");
//		
//		
//		
//		
//		
//		
//	}
	
	
	void RecebeDiretorio(File fileLocalizado , String nomeModelo, File dirfinal){
		
		this.arquivoLocalizado = fileLocalizado;
		this.nomeModelo = nomeModelo;
		this.dirfinal = dirfinal;
		
	}
	
	
private String GerarDocumento(com.tecsoluction.robo.entidade.Detento det) {
		
//		progressind.setVisible(true);
		
		String path =null;
		String pathauto=null;
		
		String  diretorioaux = System.getProperty("user.dir");
//		 
		 String diretorio = diretorioaux + "/src/main/resources/modelo/";
		 
		 Date data = new Date();
		 
		 String totalviola = String.valueOf(det.getViolacoes().size());
		 
		 String datmes = PegarMes(data);
			String datdia = PegarDia(data);
			String datano = PegarAno(data);
		 
			String datfinal =  datano + "\\" + datmes + "\\"+ datdia +"\\";
		
		 
		 pathauto = (arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "oficiosNaoEnviados" + "\\" + datfinal + "word" +"\\" + det.getNome() + ".doc");
		 
		String diret = (arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "oficiosNaoEnviados" + "\\" + datfinal + "word" + "\\");
		 
		 
		 File dir = new File(diret);
		
		 if (dir.isDirectory()){
			 
			 
			 System.out.println("é diretorio: " + dir.getPath());
			 
		 }else {
			 
			 dir.mkdirs();
			 
			 System.out.println("diretorio criado: " + dir.getPath());
			 
			 
		 }

	    
	    StringBuilder builder = new StringBuilder();
	    
	    String alarme = new String();
	    
	    String varaex = null;
	    
	    String vep = null;
	    
	    String processo = null;
	    
	     processofinal = null;
	    
	    int cont =0;
	    
	    String datanull = null;
	    
//	    String caracteristica = null;
	    
	    
	    
	    for (Violacao vio : det.getViolacoes()) {
	    	
	    	builder.append("\r\n" + vio.getAlarme() + "\r\n");
	    	alarme = alarme +"," + vio.getAlarme();
	    	
//	    	if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){
//	    		
//	    		cont = cont +1;
//	    		
//	    		varaex = vio.getCaracteristica();
//	    		det.setVara(varaex);
//	    		
//	    		
//	    		
//	    	}
	    	
			
			
			if(vio.getDataviolacao() == null) {
				
				
				 datanull = "01/01/2000 00:00";
				
			}else {
				
				datanull = vio.getDataviolacao();
				
				
			}
			
			
			
			
		}
	    
	    cont = 0;
	    
	    vep = det.getVep();
	    		
	    processo = det.getProcesso();
	    
	   
	    if((processo != null && !processo.equals("") ) || (vep != null &&  !vep.equals(""))){
	    	
	    	 if(vep == null || vep.isEmpty() ||  vep.equals("")) {
	    	    	
	    	    	
	    		    processofinal = det.getProcesso();
	    		    	
	    		   
	    		    }
	    		    
	    		    
	    	 if(processo == null || processo.isEmpty() ||  processo.equals("")) {
	    		    	
	    		    	
	    		    processofinal= det.getVep();
	    		    	
	    		    	   
	    		    }
	    	
	    	
	    }
	    
	   
	    
	 
	    
//	    if(varaex == null || varaex ==""){
//	    	
//	    	varaex="vazia";
//	    	
//	    	
//	    }
//	    
//	    System.out.println("varaex:" + varaex);
	    
	   
	//   WordPoi word = new WordPoi(path,pathauto);
//	    word.replaceTag("#OFICIO#","18.789");
//	    word.replaceTag("#VARA#","10º");
//	    word.replaceTag("#DETENTO#",det.getNome());
//	    word.replaceTag("#PRONTUARIO#",det.getProntuario());
//	    word.replaceTag("#DESCRICAO#",det.getViolacoes().get(0).getAlarme());
//	    word.replaceTag("#DATA_HORA_VIOLACAO#",det.getViolacoes().get(0).getDataviolacao());
//	    word.replaceTag("#ALARMES#",builder.toString());
	//  //  word.replaceTag("DESCRICAO", "963-289-594-00");
//	    word.write();
	//    
//	    dados = word.returnBytes();
	    
//	    if(modeloOficio == null) {
//	    	
//	    	ValidaModelo("NENHUM MODELO SELECIONADO");
//	    	trace.setText("NENHUM MODELO SELECIONADO");
//	    	
//	    	
//	    	
//	    }
//	    
//	    
//	    if(caminhoOficio == null) {
//	    	
//	    	ValidaOficio("NENHUM DIRETORIO DO OFICIO FOI CRIADO");
//	    	trace.setText("NENHUM DIRETORIO DO OFICIO FOI CRIADO");
//	    	
//	    	
//	    	
//	    }
	    
	    
//	    System.out.println("teste patth: " + arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + nomeModelo);
	
	    File fi = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + nomeModelo);
	    
	    POIFSFileSystem fs = null;
	    try {
			fs = new POIFSFileSystem(new FileInputStream(fi));
		//	 trace.setText("ok gerar documento");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
//			det.setStatusenvio("ERROR");
//			detentoErro.add(det);
			
		//	 trace.setText("erro gerar documento" + e);
//			 AtualizarQuadro();
		}  
	    
	   // WordPoi word = new WordPoi();
	    HWPFDocument doc = null;
	    
//	    String vara = det.getViolacoes().get(0).getCaracteristica();
//	    
//	    if(vara == null || vara ==""){
//	    	
//	    	vara="vazia";
//	    	
//	    	
//	    }
//	    
//	    System.out.println("vara:" + vara);
	    
//	    System.out.println("modelo:" + modeloOficio.getPath());
//	    
//	    System.out.println("oficio:" + caminhoOficio.getPath());
	    
	    System.out.println("dirfinal:" + dirfinal.getPath());
//	    String nome = det.getNome().toUpperCase().trim();
//	    String prontuario = det.getProntuario().toUpperCase().trim();
//	    String descricao = det.getViolacoes().get(0).getAlarme().toUpperCase().trim();
//	    String data = det.getViolacoes().get(0).getDataviolacao().trim();
	    
	    numoficio = GerarNumeroOficio(data);
	    
	   
	    
	    System.out.println("pathauto gerar 1:" + pathauto);
	    

	    
	    
		// trace.setText("pathauto" + pathauto.toString());
	    
	    boolean isvalido = true;
	   // isvalido = validaDetentoEmail(det);
	    
	    
	    System.out.println("é valido : " + isvalido);
	    
//	    CriaTab(det);
		 
		 if (isvalido){
			 
			// System.out.println("é valido : " + isvalido);
			 
			  Map<String, String> maps =new HashMap<>();
			    maps.put("OFICIO",numoficio);
			    //maps.put("VARA",PegarVara(det));
			   // maps.put("OFICIO","18.252500");
			    maps.put("HOJE",PegarDia(data));
			    maps.put("MES",PegarMes(data));
			    maps.put("ANNO",PegarAno(data));
			 //  maps.put("VARA","15º");
			    
			    maps.put("VARALO",det.getVara());
			    maps.put("DETENTO",det.getNome());
			    maps.put("PROCESSO",processofinal);
			    maps.put("COUNT",totalviola);
			    maps.put("DESCRICAO",det.getViolacoes().get(0).getAlarme());
			    maps.put("QUANDOOCORREU",FormatadorData(datanull));
			    maps.put("MMAIL",FormatadorEmail(det));
//			    maps.put("ALARMES",alarme);
			    
			    try {
					
			    	doc = new HWPFDocument(fs);
//					doc =  WordPoi.replaceText(doc,"${OFICIO}","18.78900");
//					doc =  WordPoi.replaceText(doc,"${VARA}","10º");
//					doc = WordPoi.replaceText(doc,"${DETENTO}",det.getNome());
//					doc = WordPoi.replaceText(doc,"${PRONTUARIO}",det.getProntuario());
//					doc = WordPoi.replaceText(doc,"${DESCRICAO}",det.getViolacoes().get(0).getAlarme());
//					doc = WordPoi.replaceText(doc,"${DATA_HORA_VIOLACAO}",det.getViolacoes().get(0).getDataviolacao());
//					doc = WordPoi.replaceText(doc,"${ALARMES}",builder.toString());
					
			    	WordPoi.replaceTag(doc, maps);
			    	
					WordPoi.saveWord(pathauto,doc);
					

						try {
							ConverterWordPdf2(doc,det);
							det.setStatusdoc("SUCESSO");
						} catch (DocumentException e) {
							// TODO Auto-generated catch block
//							det.setStatusenvio("ERROR");
//							detentoErro.add(det);
							e.printStackTrace();
//							AtualizarQuadro();
						} catch (com.itextpdf.text.DocumentException e) {
							// TODO Auto-generated catch block
							
	//
							
//							det.setStatusenvio("ERROR");
							e.printStackTrace();
//							detentoErro.add(det);
//							AtualizarQuadro();
						}

					
				//	ConverterWordPdf(doc,det,fs);
					
				//	trace.setText("ok replace");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
//					det.setStatusenvio("ERROR");
//					detentoErro.add(det);				
					trace.setText("erro replace" + e);
//					AtualizarQuadro();
				}
			    
			 
			 
			 
			 
		 } else {
			 
			   det.setStatusdoc("ERROR");
			//   AtualizarQuadro();
//				det.addErros("OUTROS");
//			   
//			   detentoErro.add(det);
			   
			 
		//	 ValidaDetentoInformacao("Erro na Validação");
			   
//			    File file = new File(pathauto);
//			    
//			    if(!file.exists()) {
//			    	
//					   det.setStatusenvio("ERROR");
//					   detentoErro.add(det);
//					   AtualizarQuadro();
//					//   carregartabela(detentoFinal);
//			    	
//			    	
//			    }
			 
			 
			 
		//	 return pathauto ;
			 
		//	 detentoErro.add(det);
			 
			 
		 }
		 

	    
	  
	    
	    System.out.println("pathauto gerar 2:" + pathauto);
		
		
		return pathauto;
		
	}

private void ConverterWordPdf2(HWPFDocument document, com.tecsoluction.robo.entidade.Detento det) throws DocumentException, MalformedURLException, IOException, com.itextpdf.text.DocumentException{
	
	
	 System.out.println("Starting the test");  
//   fs = new POIFSFileSystem(new FileInputStream("D:/Resume.doc"));  
	 
	// Image img = new Image(getClass().getResourceAsStream("/images/img1.png"));
	 
	 final Range           range    = document.getRange();
	 
    String filename = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img1.png";
    String filename2 = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img2.png";
    String filename3 = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img3.png";
    
    com.itextpdf.text.Image image =null;
   		 com.itextpdf.text.Image image2 =null;
   		 com.itextpdf.text.Image image3 =null;

		 image = com.itextpdf.text.Image.getInstance(filename);
		image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
		
		image2 = com.itextpdf.text.Image.getInstance(filename2);
		 image2.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
		 
		 image3 = com.itextpdf.text.Image.getInstance(filename3);
		 image3.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);

   // doc.add(image);
    
    
    
	// Image img2 = new Image(getClass().getResourceAsStream("/images/img2.png"));
	 
	 String[] k=null;
	 OutputStream fileForPdf =null;
	 
	
	 
	 WordExtractor we=new WordExtractor(document);
	 k = new String[we.getParagraphText().length];
	 
	 System.out.println("qtd paragrafos :" + we.getParagraphText().length);
	 
	 k = we.getParagraphText();
	 
		Date data = new Date();

		
		String datmes = PegarMes(data);
		String datdia = PegarDia(data);
		String datano = PegarAno(data);
	 
		String datfinal =  datano + "\\" + datmes + "\\"+ datdia +"\\";

		//fileForPdf = new FileOutputStream(new File(dirfinal.getPath()+"/pdf/" + det.getNome() + ".pdf"));
		File fil = new File((arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "oficiosNaoEnviados" + "\\" + datfinal + "pdf" + "\\"));
		fil.mkdirs();
		
		File filarq = new File((arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "oficiosNaoEnviados" + "\\" + datfinal + "pdf" +"\\" + det.getNome() + ".pdf"));
		

		
		fileForPdf = new FileOutputStream(filarq);
		
		we.close();

	 
		com.itextpdf.text.Document documento = new com.itextpdf.text.Document(PageSize.A4, 20, 20, 50, 25);

		com.itextpdf.text.pdf.PdfWriter writer = PdfWriter.getInstance(documento, fileForPdf);
		
		HeaderFooterPageEvent event = new HeaderFooterPageEvent(arquivoLocalizado);	
		writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 559, 788));
//		writer.
//		writer.
		writer.setPageEvent(event);
		
		 Font smallBold = new Font(Font.FontFamily.COURIER, 10,
                 Font.BOLD);
		 
		 Font MDBold = new Font(Font.FontFamily.COURIER, 12,
                 Font.BOLD);
		 
		 Font NORMAL = new Font(Font.FontFamily.COURIER, 12,
                 Font.NORMAL);
		 
//		 BaseColor color = new BaseColor();
		 
		 Font tabheader = new Font(Font.FontFamily.COURIER, 11,
                 Font.BOLD);
		 tabheader.setColor(BaseColor.WHITE);
		 
		 Font tabcontent = new Font(Font.FontFamily.COURIER, 9,
                 Font.NORMAL);
		 tabcontent.setColor(BaseColor.GRAY);
		 Font footer = new Font(Font.FontFamily.TIMES_ROMAN, 4,
                 Font.NORMAL);
		 
		 Font mail = new Font(Font.FontFamily.COURIER, 12,
                 Font.BOLD);
		 mail.setColor(BaseColor.BLUE);
		 
		 Font s = new Font(Font.FontFamily.COURIER, 9,
                 Font.NORMAL);
		 s.setColor(BaseColor.YELLOW);
		 
		 Font c = new Font(Font.FontFamily.COURIER, 9,
                 Font.NORMAL);
		 c.setColor(BaseColor.PINK);
		 
		 Font x = new Font(Font.FontFamily.COURIER, 9,
                 Font.NORMAL);
		 x.setColor(BaseColor.GREEN);
		 
		 int qtd = 0;

	 documento.open();
	 
	 boolean controle = false;
	 
//	 documento.add(image);
	 
	 PdfPTable table = new PdfPTable(7); // 3 columns.
        table.setWidthPercentage(90); //Width 100%
        table.setSpacingBefore(3f); //Space before table
        table.setSpacingAfter(3f); //Space after table
      
 
        //Set Column widths
        float[] columnWidths = {2f, 3f, 3f,3f,3f, 3f, 2f};
        table.setWidths(columnWidths);
        
//        Text t1 = new Text("1");
//        Text t2 = new Text(det.getViolacoes().get(0).getAlarme());
//        Text t3 = new Text(det.getAlarme_posicao());
//        Text t4 = new Text(det.getAlarme_posicao());
//        Text t5 = new Text(det.getAlarme_posicao());
//        Text t6 = new Text(det.getAlarme_posicao());
//        Text t7 = new Text(det.getAlarme_posicao());
        
//        PdfPCell cell11 = new PdfPCell(new Paragraph("111"));
//        cell11.setBorderColor(BaseColor.BLUE);
//        cell11.setPaddingLeft(10);
//        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell1 = new PdfPCell(new Paragraph("QTD",tabheader));
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setBackgroundColor(new BaseColor(28, 110, 164));
        cell1.setPaddingLeft(5);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
    
      //  cell1.addElement(cell11);
        
//        PdfPCell cell22 = new PdfPCell(new Paragraph(det.getViolacoes().get(0).getAlarme()));
//        cell22.setBorderColor(BaseColor.BLUE);
//        cell22.setPaddingLeft(5);
//        cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell22.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell2 = new PdfPCell(new Paragraph("ALARME",tabheader));
        cell2.setBorderColor(BaseColor.WHITE);
        cell2.setBackgroundColor(new BaseColor(28, 110, 164));
        cell2.setPaddingLeft(5);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
       // cell2.addElement(cell22);
        
        PdfPCell cell77 = new PdfPCell(new Paragraph("DURACAO ALARME",tabheader));
        cell77.setBorderColor(BaseColor.WHITE);
        cell77.setBackgroundColor(new BaseColor(28, 110, 164));
        cell77.setPaddingLeft(5);
        cell77.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell77.setVerticalAlignment(Element.ALIGN_MIDDLE);
 
        PdfPCell cell3 = new PdfPCell(new Paragraph("PROCESSO",tabheader));
        cell3.setBorderColor(BaseColor.WHITE);
        cell3.setBackgroundColor(new BaseColor(28, 110, 164));
        cell3.setPaddingLeft(5);
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell4 = new PdfPCell(new Paragraph("VEP",tabheader));
        cell4.setBorderColor(BaseColor.WHITE);
        cell4.setBackgroundColor(new BaseColor(28, 110, 164));
        cell4.setPaddingLeft(5);
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell5 = new PdfPCell(new Paragraph("INICIO",tabheader));
        cell5.setBorderColor(BaseColor.WHITE);
        cell5.setBackgroundColor(new BaseColor(28, 110, 164));  
        cell5.setPaddingLeft(5);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        PdfPCell cell6 = new PdfPCell(new Paragraph("FIM",tabheader));
        cell6.setBorderColor(BaseColor.WHITE);
        cell6.setBackgroundColor(new BaseColor(28, 110, 164));
        cell6.setPaddingLeft(5);
        cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
//        PdfPCell cell7 = new PdfPCell(new Paragraph("DURACAO",tabheader));
//        cell7.setBorderColor(BaseColor.WHITE);
//        cell7.setBackgroundColor(new BaseColor(28, 110, 164));
//        cell7.setPaddingLeft(5);
//        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell7.
 
        //To avoid having the cell border and the content overlap, if you are having thick cell borders
        //cell1.setUserBorderPadding(true);
        //cell2.setUserBorderPadding(true);
        //cell3.setUserBorderPadding(true);
 
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell77);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
//        table.addCell(cell7);
//        table.addCell(cell11);
//        table.addCell(cell22);
        
        
//        table.addCell(cell2);
//        table.addCell(cell3);
        
//        Document doc = new Document();
//        doc.add(table);
 
      
	 
   	 for (int i = 0; i < k.length; i++) {
		 
			
			
		 
		 
			
//		 if(i == 0) {
//			 
//			 
//			  com.itextpdf.text.Paragraph p1 = new com.itextpdf.text.Paragraph(k[i],smallBold);
//			  p1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			  
//			  com.itextpdf.text.Paragraph p8 = new com.itextpdf.text.Paragraph(k[i + 1],smallBold);
//			  p8.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			
//			  com.itextpdf.text.Paragraph p9 = new com.itextpdf.text.Paragraph(k[i + 2],smallBold);
//			  p9.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//			  i = i + 2;
//			 documento.add(p1);
//			 documento.add(p8);
//			 documento.add(p9);
//			 
//			 controle =true;
//			 
//		 }
		 
//		 if(i == 22) {
//			 
//			  com.itextpdf.text.Paragraph p18 = new com.itextpdf.text.Paragraph();
//
//			  p18.add(new com.itextpdf.text.Paragraph(k[i],smallBold));
//			  p18.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//
//			  
//			 documento.add(p18);
//			 
//			 controle =true;
//			 
//		 }
		 
		 if (i == 0 ) {
		//	 k[i] = k[i].replaceAll("\\cM?\r?\n", "");
			 
			 com.itextpdf.text.Paragraph p151 = new com.itextpdf.text.Paragraph("",MDBold);
			  p151.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			  
//			  
				 com.itextpdf.text.Paragraph p153 = new com.itextpdf.text.Paragraph("",MDBold);
				  p153.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			  
				 com.itextpdf.text.Paragraph p152 = new com.itextpdf.text.Paragraph(k[i],MDBold);
				  p152.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
				//  p152.setPaddingTop(-18f);
//				  p152.
			  
			 // documento.add(image2);
			 documento.add( p151);
				  documento.add(Chunk.NEWLINE);
				  documento.add(Chunk.NEWLINE);
//				  documento.add(Chunk.NEWLINE);
//				  documento.add(Chunk.NEWLINE);
//				  documento.add(Chunk.NEWLINE);
//				  documento.add(Chunk.NEWLINE);
			 documento.add( p153);
			  documento.add(Chunk.NEWLINE);
//			  documento.add(Chunk.NEWLINE);
			 documento.add( p152);
			  documento.add(Chunk.NEWLINE);
//			  documento.add(Chunk.NEWLINE);
//			 documento.
			 i=i+1;
			 controle =true;
			
			 
		 }
		 
		 
		 if (i == 1 ) {
		//	 k[i] = k[i].replaceAll("\\cM?\r?\n", "");
			 
			 com.itextpdf.text.Paragraph p11 = new com.itextpdf.text.Paragraph(k[i],MDBold);
			  p11.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
//			  p11.setPaddingTop(-3f);
			 // documento.add(image2);
			 documento.add( p11);
			 documento.add(Chunk.NEWLINE);
//			 i=i+1;
			 controle =true;
			
			 
		 }
		 
		 
		 if (i == 2 ) {
		//	 k[i] = k[i].replaceAll("\\cM?\r?\n", "");
			 
			 int indexini = k[i+1].indexOf(" Direito da : ");
//			 int indexfim = k[i].indexOf("gov.br");
			 String es = k[i+1].substring(0, indexini + 14);
			 String var = k[i+1].substring(indexini +14);
			 
			 com.itextpdf.text.Paragraph p110 = new com.itextpdf.text.Paragraph(k[i],NORMAL);
			  p110.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
			  
				 com.itextpdf.text.Paragraph p1100 = new com.itextpdf.text.Paragraph(es,NORMAL);
				  p1100.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
				  p1100.add(new com.itextpdf.text.Phrase(var,MDBold));
				  
			 // documento.add(image2);
			 documento.add( p110);
			 documento.add( p1100);
			 documento.add(Chunk.NEWLINE);
			 
			 controle =true;
			 
			 i=i+2;
			
			 
		 }
//		 if (i == 10 ) {
		 
		 
		 if(k[i].contains("eletronicamente: ")){
			 
			 int indexini = k[i].indexOf("eletronicamente: ");
			 int indexfim = k[i].indexOf(", que ");
			 String ee = k[i].substring(indexini +17, indexfim);
			 String pp = k[i].substring(0, indexini +17);
			 String ss = k[i].substring(indexfim +6);
			 
			 com.itextpdf.text.Paragraph p1122 = new com.itextpdf.text.Paragraph(pp,NORMAL);
			 p1122.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
			 p1122.add(new com.itextpdf.text.Phrase(ee,MDBold));
			 p1122.add(new com.itextpdf.text.Phrase(ss,NORMAL));
			 
			 documento.add( p1122);
			 controle =true; 
			 
//			 com.itextpdf.text.Paragraph p1123 = new com.itextpdf.text.Paragraph(ee,MDBold);
//			 p1123.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
//			 documento.add( p1123);
//			 controle =true; 
			 
			 
//			 com.itextpdf.text.Paragraph p1124 = new com.itextpdf.text.Paragraph(ee,NORMAL);
//			 p1124.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
//			 documento.add( p1124);
//			 controle =true; 
			 
			 
		 }
		 
//		 if(k[i].contains(" dia citado : ")){
//			 
//			 int indexini = k[i].indexOf(" dia citado : ");
////			 int indexfim = k[i].indexOf(", que ");
//			 String ees = k[i].substring(indexini +1);
//			 String pps = k[i].substring(0, indexini);
////			 String ss = k[i].substring(indexfim);
//			 
//			 com.itextpdf.text.Paragraph p11220 = new com.itextpdf.text.Paragraph(pps,NORMAL);
//			 p11220.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
//			 p11220.add(new com.itextpdf.text.Phrase(ees,MDBold));
////			 p11220.add(new com.itextpdf.text.Phrase(ss,NORMAL));
//			 
//			 documento.add(p11220);
//			 controle =true; 
//			 
////			 com.itextpdf.text.Paragraph p1123 = new com.itextpdf.text.Paragraph(ee,MDBold);
////			 p1123.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
////			 documento.add( p1123);
////			 controle =true; 
//			 
//			 
////			 com.itextpdf.text.Paragraph p1124 = new com.itextpdf.text.Paragraph(ee,NORMAL);
////			 p1124.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
////			 documento.add( p1124);
////			 controle =true; 
//			 
//			 
//		 }
		 
		
			 
			 
			 if(k[i].contains("@seres.pe.gov.br")){
				 
				 
				 int indexini = k[i].indexOf("cemer.");
				 int indexfim = k[i].indexOf("gov.br");
				 String e = k[i].substring(indexini, indexfim + 6);
				 String p = k[i].substring(0, indexini);
				 
				 
				 com.itextpdf.text.Paragraph p112 = new com.itextpdf.text.Paragraph(p,NORMAL);
				 p112.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
				 p112.add(new com.itextpdf.text.Phrase(e,mail));
				 documento.add( p112);
				 controle =true; 
				 
//				 com.itextpdf.text.Paragraph p111 = new com.itextpdf.text.Paragraph(e,mail);
//				 p111.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
//				 documento.add( p111);
//				 controle =true;
//				 documento.add(Chunk.NEWLINE);
				 
//				 i=i+1;
				 
			 }else {
				 
//				 com.itextpdf.text.Paragraph p115 = new com.itextpdf.text.Paragraph(k[i],NORMAL);
//				 p115.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
//				 documento.add( p115);
//				 controle =true; 
				 
			 }

			  
			 
//		 }

		 
		 if (i == 9 ) {
			 
			table = PreencherValores(table,det);
			 
			 documento.add(table);
			 
			 controle =true;
			 
//			  documento.add(Chunk.NEWLINE);
//			  documento.add(Chunk.NEWLINE);
//			  documento.add(Chunk.NEWLINE);
			 
			
			 
		 }
		 

		 
//		 if (i == 6 ) {
//			 
//			 com.itextpdf.text.Paragraph p110 = new com.itextpdf.text.Paragraph(k[i],MDBold);
//			  p110.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			 documento.add( p110);
//			 
//			 controle =true;
//							 
//		 }
//		 
//		 if (i == 7 ) {
//			 
//			 com.itextpdf.text.Paragraph p110 = new com.itextpdf.text.Paragraph(k[i],MDBold);
//			  p110.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
//			 documento.add( p110);
//			 
//			 controle =true;
//			
//			 
//		 }
		 

		 
		 
		 
		 if (i == 12 ) {
	
			 com.itextpdf.text.Paragraph p12 = new com.itextpdf.text.Paragraph(k[i],MDBold);
			  p12.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			  
				 com.itextpdf.text.Paragraph p13 = new com.itextpdf.text.Paragraph(k[i +1],MDBold);
				  p13.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
				  
				  p12.add(p13);
				  
				  i = i + 4;
				 documento.add( p12);
//			 documento.add( p13);
			 
			 controle =true;
			
			 
		 }
		 

		 
		 if (i == 10) {
	
			 documento.add(image3);
			 
			 i=i+1;
			 
			 controle =true;
			 
			 
		 } 
		 
		 
		 if (i == 11) {
				
//			 documento.add(image3);
//			 
//			 i=i+1;
			 
			 controle =true;
			 
			 
		 } 
		 
		 
		 if (controle){
			 
			 controle =false; 
			 
			 
		 }else
		
		 {
			 
			 
			 com.itextpdf.text.Paragraph p36 = new com.itextpdf.text.Paragraph(k[i],NORMAL);
			 p36.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
//			 p36.setExtraParagraphSpace(2f);
			 documento.add(p36);
			
			 if(i/2==0){
					
				 documento.add(Chunk.NEWLINE);
				
			}else {
				
				
			}
			 controle =false;
			 
		 }
		 
		 
		// documento.setPageCount(i);
		 
	 }


	 
	 documento.close();

	fileForPdf.close();

	
}


private PdfPTable PreencherValores(PdfPTable table, com.tecsoluction.robo.entidade.Detento det) {

	
	PdfPTable tab = table;
	
	 Font tabcontent = new Font(Font.FontFamily.COURIER, 9,
             Font.NORMAL);
	 tabcontent.setColor(BaseColor.GRAY);
	
	int index =0; 
	
	for (int i = 0; i < det.getViolacoes().size(); i++) {
		
		String datfim = null;
		
		String datini = null;
		
		if(det.getViolacoes().get(i).getDatafinalizacao() != null){
			
			 datfim = FormatadorData(det.getViolacoes().get(i).getDatafinalizacao());
			
		}else {
			
			
			datfim = "00/00/0000 00:00";
			
			
		}
		
		
		if(det.getViolacoes().get(i).getDatainicio() != null){
			
			datini = FormatadorData(det.getViolacoes().get(i).getDatainicio());
			
		}else {
			
			
			datini = "00/00/0000 00:00";
			
			
		}
		
	

		
		index = i + 1;
		String s = "" + index;
				
        PdfPCell cell7 = new PdfPCell(new Paragraph(s,tabcontent));
        cell7.setBorderColor(BaseColor.WHITE);
        cell7.setBackgroundColor(new BaseColor(208, 228, 245));

//        cell7.setPaddingLeft(5);
        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell7);
        
        PdfPCell cell8 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getAlarme(),tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell8.setBorderColor(BaseColor.WHITE);
        cell8.setBackgroundColor(new BaseColor(208, 228, 245));
        cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell8);
        
        PdfPCell cell88 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getDuracaoalarme(),tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell88.setBorderColor(BaseColor.WHITE);
        cell88.setBackgroundColor(new BaseColor(208, 228, 245));
        cell88.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell88.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell88);
        
        PdfPCell cell9 = new PdfPCell(new Paragraph(det.getProcesso(),tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell9.setBorderColor(BaseColor.WHITE);
        cell9.setBackgroundColor(new BaseColor(208, 228, 245));
        cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell9);
        
        PdfPCell cell10 = new PdfPCell(new Paragraph(det.getVep(),tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell10.setBorderColor(BaseColor.WHITE);
        cell10.setBackgroundColor(new BaseColor(208, 228, 245));
        cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell10);
        
        PdfPCell cell11 = new PdfPCell(new Paragraph(datini,tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell11.setBorderColor(BaseColor.WHITE);
        cell11.setBackgroundColor(new BaseColor(208, 228, 245));
        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell11);
        
        PdfPCell cell12 = new PdfPCell(new Paragraph(datfim,tabcontent));
//        cell7.setBorderColor(BaseColor.BLUE);
//        cell7.setPaddingLeft(5);
        cell12.setBorderColor(BaseColor.WHITE);
        cell12.setBackgroundColor(new BaseColor(208, 228, 245));
        cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
        tab.addCell(cell12);
        
//        PdfPCell cell13 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getDuracao(),tabcontent));
////        cell7.setBorderColor(BaseColor.BLUE);
////        cell7.setPaddingLeft(5);
//        cell13.setBorderColor(BaseColor.WHITE);
//        cell13.setBackgroundColor(new BaseColor(208, 228, 245));
//        cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        tab.addCell(cell13);
		
	}
	
	
	return tab;
}




private String FormatadorEmail(com.tecsoluction.robo.entidade.Detento det) {

	String mail = new String();
	String estabelecimento = new String();
	
	String MedidaCautelarFundo = new String();
	String MedidaCautelarConv = new String();
	String PEDomiciliar = new String();
	String MedidaProtetivaFundo = new String();
	String MedidaProtetivaConv = new String();
	
	estabelecimento = det.getEstabelecimento();
	
	PEDomiciliar = "PE - Domiciliar";
	MedidaCautelarFundo = "Fundo a fundo - Medida Cautelar";	
	MedidaCautelarConv = "Convênio - Medida Cautelar";
	MedidaProtetivaFundo ="Fundo a fundo - Medida Protetiva";
	MedidaProtetivaConv = "Convênio - Medida Protetiva";
	
	if(estabelecimento.trim().toUpperCase().equals(PEDomiciliar.trim().toUpperCase())){
		
		
		mail = "cemer.domiciliar@seres.pe.gov.br";
		
	}
	
	else if(estabelecimento.trim().toUpperCase().equals(MedidaCautelarFundo.trim().toUpperCase())){
		
		
		mail = "cemer.cautelar@seres.pe.gov.br";
		
	}
	
	else if(estabelecimento.trim().toUpperCase().equals(MedidaCautelarConv.trim().toUpperCase())){
		
		
		mail = "cemer.cautelar@seres.pe.gov.br";
		
	}
	
	else if(estabelecimento.trim().toUpperCase().equals(MedidaProtetivaFundo.trim().toUpperCase())){
		
		
		mail = "cemer.mariadapenha@seres.pe.gov.br";
		
	}
	
	else if(estabelecimento.trim().toUpperCase().equals(MedidaProtetivaConv.trim().toUpperCase())){
		
		
		mail = "cemer.mariadapenha@seres.pe.gov.br";
		
	}
	
	else {
		
		mail = "cemer.cautelardomiciliar@seres.pe.gov.br";
		
		
	}
	
	
	
	
	return mail;
}


private String FormatadorData(String data){
	
	String dataformatadastring = null;
	
	SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
	Date dat = null;
	
	
	try {
		dat = fmt.parse(data);
	//	 trace.setText("ok format data");
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 trace.setText("nok format data");
		// detentoErro.add(arg0)
	}
	
	SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yy HH:mm");    
	//Date dat2 = null;
	
	//dat2 = fmt2.format(dat);
	
	dataformatadastring=fmt2.format(dat);
	
	
	return dataformatadastring;
}


private String FormatadorData(Date data){
	
	String dataformatadastring = null;
	
//	SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
//	Date dat = null;
//	
//	
//	try {
//		dat = fmt.parse(data);
//	//	 trace.setText("ok format data");
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		 trace.setText("nok format data");
//		// detentoErro.add(arg0)
//	}
	
	SimpleDateFormat fmt2 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");    
	//Date dat2 = null;
	
	//dat2 = fmt2.format(dat);
	
	dataformatadastring=fmt2.format(data);
	
	dataformatadastring.replace("\\", "-");
	dataformatadastring.replace(" ", "-");
	dataformatadastring.replace(":", "-");
	
	System.out.println("data formatda " + dataformatadastring);
	
	
	return dataformatadastring;
}



private String GerarNumeroOficio(Date data){
	
	Random gerador = new Random();
	
	String num = null;
	
	num = PegarDia(data) + ".";
	
	num = num + PegarMesInt(data);
	
	num = num + gerador.nextInt(1000);
	
	num = num + "/" + PegarAno(data);
	
//	 trace.setText("gerar numero oficio" + num);
	
	
	return num;
}


private String PegarMesInt(Date data){
	

	
	Calendar c = new GregorianCalendar();
	c.setTime(data);
	String mesnome = "";

	int mes =c.get(c.MONTH);
	
	mes = mes + 1;
	
	String ms = String.valueOf(mes);
	
	
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
	
	
//	trace.setText("pegar mes int " + ms);
	
	return ms;
}


private boolean validaDetentoEmail(com.tecsoluction.robo.entidade.Detento detento){
	
	boolean valido = true;
	
	String nulo = null;
	String branco = "";
	

		
	if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){
			
		valido = false;
		
		detento.addErros("Nome");
		
//		ValidaDetentoInformacao("Nome Detento Branco ou Nulo");
			
		}
		
	if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){
			
		valido = false;
		detento.addErros("Email");
		
//		ValidaDetentoInformacao("Email Detento Branco ou Nulo");
			
		}
		
		//String pronto = detento.getProntuario();
		
	if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){
			
		valido = false;
		detento.addErros("Prontuario");
			
//		ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");
			
		}
		
//	if( (detento.getProcesso()!=null && detento.getProcesso()!="" && detento.getVep()!=null && detento.getVep()!="") ){
//			
//		
//		valido = false;
//		detento.addErros("Processo/VEP PREENCHIDO 2");
//		
////		ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
//			
//	
//	}
	
	
	if( ((detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty())) && ((detento.getVep()!=null)&&(!detento.getVep().isEmpty())) ){
		
		
		valido = false;
		detento.addErros("Processo/VEP PREENCHIDO 60");
		
//		ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			
	
	}
	
	 if((detento.getVara() == null || detento.getVara().equals(branco)) ){
			
		valido = false;
		detento.addErros("Vara");
		
//		ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			
		}
	
	if( (detento.getProcesso()==null && detento.getVep()==null) ){
			
		
		valido = false;
		detento.addErros("Processo/VEP NULOS");
		
//		ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			
	
	}
	 
//		if((detento.getProcesso()==null) && (detento.getVep()==null) || (detento.getProcesso()=="") && (detento.getVep()=="")){
//			
//			valido = false;
//			detento.addErros("Processo/VEP NULOS-brancos");
//			
////			Validadet2Informacao("Processo det2 Branco ou Nulo");
//				
//			}
	
	
	if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){
		
		
		valido = false;
		detento.addErros("Processo/VEP BRANCOS 666");
		
//		ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			
	
	}
		
	 if((detento.getViolacoes().toString()==null || detento.getViolacoes().toString().equals(branco)) ){
			
		valido = false;
		detento.addErros("Violacoes");
		
//		ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			
		}
	 
	 if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
			
		valido = false;
		detento.addErros("CARACTRISTICA");
		
//		ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			
		}
	 
//	 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
//			
//		valido = false;
//		detento.addErros("Estabelecimento");
//		
////		ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
//			
//		}
	 
	 

		
		return valido;
	
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


@FXML
private void gerarall(){
	
//	Image image = new Image(getClass().getResourceAsStream("/images/settings (1).png"),64.0,64.0,true,true);
//	imgvconf.setImage(image);
//	btconf.setGraphic(imgvconf);
//	btconf.setTextFill(Color.WHITE);
	
	progressind.setVisible(true);
	
	System.out.println("Gerar all oficio");
	
//	List<Detento> dt = 
			
//			tableview.get.getTreeItem();
	
	for (int i = 0; i < detentoFinal.size(); i++) {
		
		com.tecsoluction.robo.entidade.Detento det = detentoFinal.get(i);
		
		
		if(det.getStatusdoc().equals("VALIDADO")){
			
			String retorno = GerarDocumento(det);
			
		}else {
			
			
			
			
			
		}
		
		
//		trace.textProperty().unbind();
//		trace.setText("Gerado: " + retorno);
		
		
	}
	
	
	
	
	
	
}


private void QuantificarResultados(){
	
	
//	detentoserros = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
//	detentosucesso = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
	
	detentoserros.clear();
	detentosucesso.clear();
	
	
	for (int i = 0; i < getDetentoFinal().size(); i++) {
	
		com.tecsoluction.robo.entidade.Detento det = getDetentoFinal().get(i);
		
		if(det.getStatusdoc().equals("ERROR")) {
			
//			det.setStatusdoc("ERROR");
			
			detentoserros.add(det);
			
			
		}
		
		if(det.getStatusdoc().equals("VALIDADO")) {
			
//			det.setStatusdoc("SUCESSO");
			
			detentosucesso.add(det);
			
			
		}
		
		
		if(det.getStatusdoc().equals("SUCESSO")) {
			
//			det.setStatusdoc("SUCESSO");
			
			detentosucesso.add(det);
			
			
		}
		
		
		
	}
	
	
	
}



@FXML
private void carregararquivo(){
	
//	Image image = new Image(getClass().getResourceAsStream("/images/settings (1).png"),64.0,64.0,true,true);
//	imgvconf.setImage(image);
//	btconf.setGraphic(imgvconf);
//	btconf.setTextFill(Color.WHITE);
	
	System.out.println("carregar oficio");
	
//	progressind.setVisible(true);
	
	
	  if(jcbfiltro.isSelected()){
		  
		 
//    	  GerenciadorFiltro gerenciafiltro;
      	
    	  carregarexcel(); 
		  
		 
          
          if(!filtros.isEmpty()) {
        	  

        	  
              gerenciafiltro = new GerenciadorFiltro(filtrosent, registrosNotificar, detentoFinal);
              
              setRegistros(gerenciafiltro.getRegistros());
              
              setDetentos(gerenciafiltro.getDetentos());
              
          	//lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
              
              filter();
              
              System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
              System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());

              
              System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
              System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());

              System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
              System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
          
              System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
              System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
              
              
              }else {
            	  
            	  initColuna();
            	  setCellFactoryColuna();
            	  InserirDadosTabela(detentoFinal);
            	  InitTabela();
            	 
            	  
            	  
            	  
              }
       
     
         
          
          

      } else {
     	 
     	 
     	 
    		carregarexcel();
     	 
     	 
      }
	

	
	 
	
	
	
	
	
	
}


private void CriaTab(com.tecsoluction.robo.entidade.Detento det){
	
	tabhtml =	"<html><body> <table style=\" border: 1px solid #1C6EA4; background-color: #EEEEEE; width: 100%; text-align: left; border-collapse: collapse;> <caption>Alarmes</caption> <thead style=\" background: #1C6EA4; border-bottom: 2px solid #444444;\">" +
			" <tr> " +
			"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Qtd</th>"+

			//"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Nome</th>"+

			"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Alarme</th>"+

			" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Processo</th>" +

			" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Vep</th>" +

			" <th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Inicio</th>"+
			"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Fim</th>"+
			"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Duração</th>"+
			 "</tr> </thead>" +

			" <tbody>";
	
	for (int i = 0; i < det.getViolacoes().size(); i++) {
		
		int incremento = i +1;
		
		Violacao viola = det.getViolacoes().get(i);
		
		String datanull = null;
		
		if(viola.getDatafinalizacao() == null) {
			
			
			 datanull = "01/01/2000 00:00";
			
		}else {
			
			datanull = viola.getDatafinalizacao();
			
			
		}
	
	
	
	
	tabhtml = tabhtml + "<tr style=\" background: #D0E4F5; \"> <td>" + incremento  + "</td>" +
			"<td style=\" font-size: 14px;\">" + det.getNome()  + "</td>" +
					"<td style=\" font-size: 14px;\">" + viola.getAlarme()  + "</td>" +
						"<td style=\" font-size: 14px;\">" + det.getProcesso() + " </td> " +
						"<td style=\" font-size: 14px;\">" + det.getVep() + " </td> " +
					"<td style=\" font-size: 14px;\">" + FormatadorData(viola.getDatainicio() ) + " </td> " +
						"<td style=\" font-size: 14px;\">" + FormatadorData(datanull)  + " </td> " +
					"<td style=\" font-size: 14px;\">" + viola.getDuracao()+ " </td> " + "</tr>" ;
	

	}
	
	tabhtml = tabhtml  + " </tbody> </table> <html><body>";
	
	}



public void PreencherVaraComCaracteristica(com.tecsoluction.robo.entidade.Detento det){
	
	
	
    String varaex = null;
    
    
    int cont =0;
    
	for (Violacao vio : det.getViolacoes()) {
    	
//    	builder.append("\r\n" + vio.getAlarme() + "\r\n");
//    	alarme = alarme +"," + vio.getAlarme();
    	
    	if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){
    		
    		cont = cont +1;
    		
    		varaex = vio.getCaracteristica();
    		det.setVara(varaex);
    		
    		
    		
    	}
    	
    	 if(varaex == null || varaex ==""){
 	    	
 	    	varaex="vazia";
 	    	
 	    	det.setStatusdoc("ERROR");
 	    	det.getErros().add("SEM VARA PREENCHIDA(CARACTERISTICA)");
 	    	
// 	    	detentoserros.add(det);
 	    	
 	    	
 	    }else {
 	    	
// 	    	det.setStatusdoc("VALIDADO");
// 	    	detentosucesso.add(det);
 	    	
 	    	
 	    }
    	

		
	}
	
	
}




@FXML
void mostrarerros(ActionEvent event) {
	
	
	 if(jcbmostrarerros.isSelected()){
    	 
//    	 QuantificarResultados();
    	 
    	 initColuna();
       	 setCellFactoryColuna();
       	
       	InserirDadosTabela(detentoserros);
      	 
       	InitTabela();
         
//        btcarregarRegistro.fire();
         
         
         
         trace.setText("Registros com Erros: " + detentoserros.size() + " Detentos");

    	 
    	 
     } else {
    	 
//    	 QuantificarResultados();
    	 
    	 initColuna();
       	 setCellFactoryColuna();
       	
       	InserirDadosTabela(detentoFinal);
      	 
       	InitTabela();
    	 
        trace.setText("Registros Total: " + detentoFinal.size() + " Detentos");

}


}



@FXML
void mostrarvalidos(ActionEvent event) {
	
	
	 if(jcbmostrarvalidos.isSelected()){
    	 
//    	 QuantificarResultados();
    	 
    	 initColuna();
       	 setCellFactoryColuna();
       	
       	InserirDadosTabela(detentosucesso);
      	 
       	InitTabela();
         
//        btcarregarRegistro.fire();
         
         
         
         trace.setText("Registros Validados: " + detentosucesso.size() + " Detentos");

    	 
    	 
     } else {
    	 
//    	 QuantificarResultados();
    	 
    	 initColuna();
       	 setCellFactoryColuna();
       	
       	InserirDadosTabela(detentoFinal);
      	 
       	InitTabela();
    	 
        trace.setText("Registros Total: " + detentoFinal.size() + " Detentos");

}


}

private List<String> PreencherComboBox(){
	
	List<String> atributos = new ArrayList<String>();
	
	

        for (int i = 0 ; i < detentoFinal.size() ; i++){  
        	
        	com.tecsoluction.robo.entidade.Detento det = detentoFinal.get(i);
        	
        	
        	if(!(det.getVara() == null) && !(atributos.contains(det.getVara()))) {
        		
        		
        		atributos.add(det.getVara());
        		
        	}
          
          
        }     
	
	
	
	
	
	return atributos;
}


private void ValidaDetentoBefore() {

//	boolean valido = false;
	
	List<com.tecsoluction.robo.entidade.Detento > detnews = new ArrayList<com.tecsoluction.robo.entidade.Detento >();
	
	List<com.tecsoluction.robo.entidade.Detento > deterror = new ArrayList<com.tecsoluction.robo.entidade.Detento >();
	
	List<com.tecsoluction.robo.entidade.Detento > detall = new ArrayList<com.tecsoluction.robo.entidade.Detento >();

	
	for(com.tecsoluction.robo.entidade.Detento  deten: detentoFinal){
		
		
    	FormatadorEmail(deten);
    	
    	PreencherVaraComCaracteristica(deten);
    	
    	validodet = validaDetentoEmail(deten);
		
		
//		validodet = DelayValida(deten);
		
		
		if(validodet){
			
		
			deten.setStatusdoc("VALIDADO");
			detnews.add(deten);
			
		}else {
			
			deten.setStatusdoc("ERROR");
			deterror.add(deten);
			
		}
		
		detall.add(deten);
		
//		detentoFinal.add(deten);
		
		
//		    Platform.runLater(() ->
//		    {
//		    	
//
//		    	PreencherTextFields(deten);
//
//		   
//		    });
		
		
		
		
	}
	
	detentosucesso.clear();
	
	detentosucesso.addAll(detnews);
	
	detentoserros.clear();
	
	detentoserros.addAll(deterror);
	
	detentoFinal.clear();
//	
	detentoFinal.addAll(detall);
	
	
	System.out.println("qtd detentos all before: " + detentoFinal.size());
//	System.out.println("qtd reg : " + registros.size());
	System.out.println("qtd Error det before : " + detentoserros.size());
	
	
		




}


//public void PreencherVaraComCaracteristica(com.tecsoluction.robo.entidade.Detento  det){
//	
//	
//	
//    String varaex = null;
//    
//    
//    int cont =0;
//    
//	for (Violacao vio : det.getViolacoes()) {
//    	
////    	builder.append("\r\n" + vio.getAlarme() + "\r\n");
////    	alarme = alarme +"," + vio.getAlarme();
//    	
//    	if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){
//    		
//    		cont = cont +1;
//    		
//    		varaex = vio.getCaracteristica();
//    		det.setVara(varaex);
//    		
//    		
//    		
//    	}
//    	
//    	 if(varaex == null || varaex ==""){
// 	    	
// 	    	varaex="vazia";
// 	    	
// 	    	det.setStatusdoc("ERROR");
// 	    	det.getErros().add("SEM VARA PREENCHIDA(CARACTERISTICA)");
// 	    	
// 	    	//detentoserros.add(det);
// 	    	
// 	    	
// 	    }else {
// 	    	
// 	    //	det.setStatusdoc("VALIDADO");
// 	    //	detentosucesso.add(det);
// 	    	
// 	    	
// 	    }
//    	
//
//		
//	}
//	
//	
//}



private com.tecsoluction.robo.entidade.Detento ConverterDetento(Detento deten){
	
//	progressind.setVisible(true);
	
	com.tecsoluction.robo.entidade.Detento detento = new com.tecsoluction.robo.entidade.Detento() ;
	
	List<Violacao>  vios = null;
	vios = deten.det.getViolacoes();
	
//	List<Notificacao>  not = deten.notificacoesobj;
	
//	detento.setNumero(deten.numero.getValue());
	detento.setNome(deten.nome.getValueSafe());
	detento.setProntuario(deten.prontuario.getValueSafe());
	detento.setEstabelecimento(deten.Estabelecimento.getValueSafe());
	detento.setPerfil(deten.perfilatual.getValueSafe());
//	detento.setArtigos(deten.artigos.getValue());
	detento.setStatusenvio(deten.statusenvio.getValueSafe());
	detento.setEmail(deten.email.getValueSafe());
	detento.setProcesso(deten.processo.getValueSafe());
	detento.setVep(deten.vep.getValueSafe());
	detento.getErros().clear();
	detento.addErros("Erros Apagados no Reevio");
	detento.setViolacoes(vios);
	detento.setVara(deten.vara.getValueSafe());
	
	String caracteristica = deten.caracteristica.getValueSafe();
	detento.getViolacoes().get(0).setCaracteristica(caracteristica);
	
	
	
	
	
	
	
	
	
	return detento;
	
}







@FXML
void aplicarfiltro(ActionEvent event) {
	
	
	List<Registro> reg = new ArrayList<Registro>();
	
	Field atributo = null;
	String clase = "com.tecsoluction.robo.entidade.Registro";
	Class<?> classDef = null;
	Object object = null;
	String atrib = "caracteristica";
	
	try {
		classDef =	Class.forName(clase);
		  
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	try {
		object = classDef.newInstance();
	} catch (InstantiationException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (IllegalAccessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	
	
String valor = jtxtfilter.getText();

try {
	atributo = object.getClass().getField(atrib);
} catch (NoSuchFieldException | SecurityException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
atributo.setAccessible(true);

reg = gerenciafiltro.FiltroInclusivo(getRegistros(), atributo, valor);

setRegistros(reg);

filter();

	ValidaDetentoBefore();

	initColuna();
	 setCellFactoryColuna();
	
	InserirDadosTabela(detentoFinal);
	 
	InitTabela();
	

jcbvara.getItems().setAll(PreencherComboBox());

progressind.setVisible(false);

trace.setText("Registros com Filtro Inclusivo: " + detentoFinal.size() +" Detentos");



}



}
    

