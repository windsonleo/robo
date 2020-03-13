package com.tecsoluction.robo.controller;


import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.CopyTaskReenvio;
import com.tecsoluction.robo.entidade.Notificacao;
import com.tecsoluction.robo.entidade.ServicoReenvio;
import com.tecsoluction.robo.entidade.Violacao;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class DetentoTabelaController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(DetentoTabelaController.class);

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
	    private JFXTextField jtxtfiltro;

	    @FXML
	    private ImageView imgvcon;

	    @FXML
	    private AnchorPane anchorcena;

	    @FXML
	    private Label trace;

//	    @FXML
//	    private JFXButton btsalvar;

	    @FXML
	    private JFXButton btfechar;
	    
	    @FXML
	    private JFXButton btrefresh;

	    @FXML
	    private ProgressIndicator progressind;
		
	    
	    private  ObservableList<Detento> filtros = FXCollections.observableArrayList();
	    
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
	    
//	    private JFXTreeTableColumn<Detento,String> valorcoluna ;
//	  
//	    private JFXTreeTableColumn<Detento,String> objetocoluna;
	    
//	    
//	    private JFXTreeTableColumn<Detento,String> artigoscoluna;
	    
	    private JFXTreeTableColumn<Detento,String> statusenviocoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> processocoluna;
	    
	    
	    private JFXTreeTableColumn<Detento,String> vepcoluna;
	    
	    private JFXTreeTableColumn<Detento,String> erroscoluna ;
	  
	    private JFXTreeTableColumn<Detento,String> varacoluna;
	    											
	    private JFXTreeTableColumn<Detento,String> caracteristicacoluna;
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
	    
	    
	    private List<com.tecsoluction.robo.entidade.Detento> detentos;
	    
	    @FXML
	    private JFXTextField  filterField;
	    
	    
	    @FXML
	    private Label size;
	    
	    private MainController maincontrole;
	    
	    
	    public CopyTaskReenvio  copyreg;
	    
	    public ServicoReenvio servicoreenvio;
	    
//	    public DetentoTabelaController detentotabcontrol;
	    
	    
	    
	    
	    public DetentoTabelaController() {

	    detentos = new ArrayList<>();
	    
	    }
	    
	    
	    
	    public DetentoTabelaController(List<com.tecsoluction.robo.entidade.Detento> detentos) {

		   this.detentos = detentos; 
		    
	    }
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		Inicializar (detentos,maincontrole);
		
		 
		
		
	}	
	
	
	
	
	

     
   public  void Inicializar (List<com.tecsoluction.robo.entidade.Detento> dets, MainController ma){
    	 
    	 this.detentos = new ArrayList<com.tecsoluction.robo.entidade.Detento>();
    	 
    	 this.filtros  = FXCollections.observableArrayList();
    	 
    	 
    	 this.maincontrole = ma;
    	 
    	// filtros.clear();
    	 
    	 
    	 detentos.clear();
    	 detentos.addAll((dets));
    	 
    	 
    	 initColuna();
     	 setCellFactoryColuna();
     
     	InserirDadosTabela(detentos);
     	
     	 InitTabela();
     	
     	
    	 
     	
     	
     
    	
    	 
     }
     
     
     private void initColuna(){
    	 
    	 numerocoluna  = new JFXTreeTableColumn<Detento,String>("Notificação");
    	 numerocoluna.setMinWidth(100);
//      	 numerocoluna.setMaxWidth(500);
    	 numerocoluna.setVisible(true);
    	 numerocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
    		    if(numerocoluna.validateValue(param)) return param.getValue().getValue().notificacao;
    		    else return numerocoluna.getComputedValue(param);
    		});
 		
    	 nomecoluna = new JFXTreeTableColumn<Detento,String>("Nome");
    		nomecoluna.setMinWidth(150);
//          	nomecoluna.setMaxWidth(250);
 		//valorcoluna.setPrefWidth(100);
    	 nomecoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(nomecoluna.validateValue(param)) return param.getValue().getValue().nome;
		    else return nomecoluna.getComputedValue(param);
		});
 		
    	 idmonitoradocoluna = new JFXTreeTableColumn<Detento,String>("Violação");
    	 	idmonitoradocoluna.setMinWidth(100);
//          	idmonitoradocoluna.setMaxWidth(500);
		//valorcoluna.setPrefWidth(100);
    	 idmonitoradocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(idmonitoradocoluna.validateValue(param)) return param.getValue().getValue().violacoes;
		    else return idmonitoradocoluna.getComputedValue(param);
		});
    	 
    	 
    	 prontuariocoluna = new JFXTreeTableColumn<Detento,String>("Prontuario");
    		prontuariocoluna.setMinWidth(50);
//          	prontuariocoluna.setMaxWidth(80);
          	prontuariocoluna.setVisible(false);
		//valorcoluna.setPrefWidth(100);
    	 prontuariocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(prontuariocoluna.validateValue(param)) return param.getValue().getValue().prontuario;
		    else return prontuariocoluna.getComputedValue(param);
		});
    	 
    	 
    	 Estabelecimentocoluna = new JFXTreeTableColumn<Detento,String>("Estabelecimento");
    		Estabelecimentocoluna.setMinWidth(50);
//          	Estabelecimentocoluna.setMaxWidth(80);
    	 Estabelecimentocoluna.setVisible(false);
		//valorcoluna.setPrefWidth(100);
    	 Estabelecimentocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(Estabelecimentocoluna.validateValue(param)) return param.getValue().getValue().Estabelecimento;
		    else return Estabelecimentocoluna.getComputedValue(param);
		});
    	 
    	 
    	 perfilatualcoluna = new JFXTreeTableColumn<Detento,String>("Perfilatual");
    		perfilatualcoluna.setMinWidth(50);
//          	perfilatualcoluna.setMaxWidth(80);    	 
    	 perfilatualcoluna.setVisible(false);
		//valorcoluna.setPrefWidth(100);
    	 perfilatualcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(perfilatualcoluna.validateValue(param)) return param.getValue().getValue().perfilatual;
		    else return perfilatualcoluna.getComputedValue(param);
		});
    	 
    	 
//    	 artigoscoluna = new JFXTreeTableColumn<Detento,String>("artigos");
//    	 artigoscoluna.setPrefWidth(80);
//    	 artigoscoluna.setVisible(false);
//		//valorcoluna.setPrefWidth(100);
//    	 artigoscoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
//		    if(artigoscoluna.validateValue(param)) return param.getValue().getValue().artigos;
//		    else return artigoscoluna.getComputedValue(param);
//		});
    	 
    	 
    	 statusenviocoluna = new JFXTreeTableColumn<Detento,String>("statusenvio");
    		statusenviocoluna.setMinWidth(80);
//          	statusenviocoluna.setMaxWidth(80);	
          	//valorcoluna.setPrefWidth(100);
    	 statusenviocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(statusenviocoluna.validateValue(param)) return param.getValue().getValue().statusenvio;
		    else return statusenviocoluna.getComputedValue(param);
		});
    	 
    	 
    	 emailcoluna = new JFXTreeTableColumn<Detento,String>("email");
    	 	emailcoluna.setMinWidth(100);
//          	emailcoluna.setMaxWidth(120);
          	//valorcoluna.setPrefWidth(100);
    	 emailcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(emailcoluna.validateValue(param)) return param.getValue().getValue().email;
		    else return emailcoluna.getComputedValue(param);
		});
    	 
    	 processocoluna = new JFXTreeTableColumn<Detento,String>("processo");
    		processocoluna.setMinWidth(80);
//          	processocoluna.setMaxWidth(120);
          	//valorcoluna.setPrefWidth(100);
    	 processocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(processocoluna.validateValue(param)) return param.getValue().getValue().processo;
		    else return processocoluna.getComputedValue(param);
		});
    	 
    	 vepcoluna = new JFXTreeTableColumn<Detento,String>("vep");
    	 	vepcoluna.setMinWidth(80);
//          	vepcoluna.setMaxWidth(120);	
          	//valorcoluna.setPrefWidth(100);
    	 vepcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(vepcoluna.validateValue(param)) return param.getValue().getValue().vep;
		    else return vepcoluna.getComputedValue(param);
		});
    	 
    	 erroscoluna = new JFXTreeTableColumn<Detento,String>("erros");
    		erroscoluna.setMinWidth(250);
//          	erroscoluna.setMaxWidth(500); 
          	//valorcoluna.setPrefWidth(100);
    	 erroscoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(erroscoluna.validateValue(param)) return param.getValue().getValue().erros;
		    else return erroscoluna.getComputedValue(param);
		});
    	 
    	 varacoluna = new JFXTreeTableColumn<Detento,String>("vara");
    		varacoluna.setMinWidth(80);
//          	varacoluna.setMaxWidth(120); 
          	//    	 varacoluna.setEditable(true);
//    	 varacoluna.
		//valorcoluna.setPrefWidth(100);
    	 varacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(varacoluna.validateValue(param)) return param.getValue().getValue().vara;
		    else return varacoluna.getComputedValue(param);
		});
    	 
    	 caracteristicacoluna = new JFXTreeTableColumn<Detento,String>("Caracteristica");
    		caracteristicacoluna.setMinWidth(80);
//          	caracteristicacoluna.setMaxWidth(400);	
          	//valorcoluna.setPrefWidth(100);
    	 caracteristicacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(caracteristicacoluna.validateValue(param)) return param.getValue().getValue().caracteristica;
		    else return caracteristicacoluna.getComputedValue(param);
		});
    	 
 		
    	 
    	 dataviolacoluna = new JFXTreeTableColumn<Detento,String>("Data Violação");
    		dataviolacoluna.setMinWidth(80);
//          	dataviolacoluna.setMaxWidth(80); 
          	dataviolacoluna.setVisible(false);
		//valorcoluna.setPrefWidth(100);
    	 dataviolacoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Detento, String> param) ->{
		    if(dataviolacoluna.validateValue(param)) return param.getValue().getValue().dataviola;
		    else return dataviolacoluna.getComputedValue(param);
		});
    	 
    	 
//    	 CriarNovaInstanciaBotao();
    	 
    	 buttomcoluna =  new JFXTreeTableColumn<Detento,Boolean>("Renviar");
    		buttomcoluna.setMinWidth(50);
//          	buttomcoluna.setMaxWidth(50); 
          	
//          	 CriarNovaInstanciaBotao2();
    	 
    	 buttomExccoluna =  new JFXTreeTableColumn<Detento,Boolean>("Excluir");
    	  	buttomExccoluna.setMinWidth(50);
//          	buttomExccoluna.setMaxWidth(50); 
          	//    	 dataviolacoluna.setPrefWidth(100);
//    	 dataviolacoluna.setVisible(false);
 
     }
     
     
     
     
     @SuppressWarnings({ "unchecked" })
	private void InitTabela() {
    	 
    	 
    	 final TreeItem<Detento>  root = new RecursiveTreeItem<Detento>(filtros, RecursiveTreeObject::getChildren);
    	 
    	// tableview = new JFXTreeTableView<Filtro>(root);
    	 
    	 tableview.setShowRoot(false);
       	 tableview.setEditable(true);
       	 tableview.setRoot(root);
//       	 tableview.getChildrenUnmodifiable
       	 tableview.getColumns().setAll(nomecoluna, idmonitoradocoluna,numerocoluna,
       			 prontuariocoluna,Estabelecimentocoluna,perfilatualcoluna,varacoluna,caracteristicacoluna,
       			erroscoluna,vepcoluna,processocoluna,emailcoluna,statusenviocoluna,dataviolacoluna,buttomcoluna,buttomExccoluna);
       	 
     	InitComponentes();
       	 
       //	anchor.getChildren().add(tableview);
        	
    	 
     }
     
     
     
     
     private void InitComponentes() {

    	 
    	 //filterField = new JFXTextField();
    	 filterField.textProperty().addListener((o,oldVal,newVal)->{
    		 tableview.setPredicate(filtro -> filtro.getValue().nome.get().contains(newVal)
//    	                 || filtro.getValue().valor.get().contains(newVal)
//    				 || filtro.getValue().nome.get().contains(newVal)
    				 || filtro.getValue().violacoes.get().contains(newVal)
//    				 || filtro.getValue().caracteristica.get().contains(newVal)
    				 || filtro.getValue().prontuario.get().contains(newVal)
    				 || filtro.getValue().Estabelecimento.get().contains(newVal)
    				 || filtro.getValue().perfilatual.get().contains(newVal)
    				 || filtro.getValue().notificacao.get().contains(newVal)
//    				 || filtro.getValue().vara.get().contains(newVal)
    				 || filtro.getValue().erros.get().contains(newVal)
//    				 || filtro.getValue().vep.get().contains(newVal)
//    				 || filtro.getValue().processo.get().contains(newVal)
    				 || filtro.getValue().email.get().contains(newVal)
    				 || filtro.getValue().statusenvio.get().contains(newVal)
//    				 || filtro.getValue().artigos.get().contains(newVal)
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
		
		
//		artigoscoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
//   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
//   	 
//		artigoscoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
//    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
//                .artigos.set(t.getNewValue());
//});
		
		
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
		
//		artigoscoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
//	   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
//	   	 
//		artigoscoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
//	    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
//	                .artigos.set(t.getNewValue());
//	});
			
			
		dataviolacoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
		   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
		   	 
		dataviolacoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
		    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
		                .dataviola.set(t.getNewValue());
		});
		
		
		
		caracteristicacoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
	   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
		
		caracteristicacoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
		    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
		                .caracteristica.set(t.getNewValue());
		});
//				
//				statusenviocoluna.setCellFactory((TreeTableColumn<Detento, String> param) -> 
//			   	 new GenericEditableTreeTableCell<Detento,String>(new TextFieldEditorBuilder()));
//			   	 
//					statusenviocoluna.setOnEditCommit((CellEditEvent<Detento, String> t)->{
//			    ((Detento) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
//			                .statusenvio.set(t.getNewValue());
//			});
    	 
    	 
    buttomcoluna.setCellFactory(cellFactory);
    buttomExccoluna.setCellFactory(cellFactorydel);
	
	}
     
     
     

	public void InserirDadosTabela(List<com.tecsoluction.robo.entidade.Detento> detentos){
    	 
//    	 filtros.clear();
    	 
    	 for(int i = 0 ; i< detentos.size(); i++){
    		
    		 com.tecsoluction.robo.entidade.Detento detento = detentos.get(i);
    		 
    		 
    		 if(filtros.equals(detento)){
    			 
    			 
    			 
    		 }else {
    			 
    			 filtros.add(new Detento(detento));
    			 
    		 }
    		
    	
    	 
    	 }
    	 

    	 
     }
     
     
     Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>> cellFactory
     = //
     new Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>>() {
         @Override
         public TreeTableCell<com.tecsoluction.robo.controller.DetentoTabelaController.Detento, Boolean> call(final TreeTableColumn<Detento, Boolean> param) {
             final TreeTableCell<Detento, Boolean> cell = new TreeTableCell<Detento, Boolean>() {

            	 
            	 
            	 Image imgSend = new Image(getClass().getResourceAsStream("/images/icons8-encaminhar-mensagem-96.png"),25.0,25.0,true,true);
            	 
               final JFXButton   btn = new JFXButton();
                 
//               this.DetentoTabelaController.class.btn(bt);
               
                com.tecsoluction.robo.entidade.Detento det = null;
                
                
                
                 
//                 boolean iserro = false;

                 @Override
                 public void updateItem(Boolean item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                         setText(null);
                     } else {
                    	 
                    	 TreeItem<Detento> empresa = getTreeTableView().getTreeItem(getIndex());
                    	 
//                    	 Detento dete = empresa.getValue();
                    	 
                    	 det = empresa.getValue().det;
                    	 
                    	 if(!det.getStatusenvio().equals("ERROR")){
                    		 
                    		 btn.setDisable(true);
                    		 
                    		 
                    	 }else {
                       
                    	 btn.setButtonType(JFXButton.ButtonType.RAISED);
                         btn.setOnAction(event -> {

                         
//                        		progressind.setVisible(true);
                        	
                        	 TreeItem.valueChangedEvent();
                        	 
//                        	 event.get
             				Detento value =	empresa.getValue();
             				
//             				cell.commitEdit(true);
            				det = value.det;
            				
//            				String va = event.getEventType().
            				
//            				if(det.getStatusenvio().equals("ERROR")){
//            					
//            					iserro = true;
//            					 btn.setDisable(false);
//            					
//            				}else {
//            					
//            					 btn.setDisable(true);
//            					iserro = false;
//            				}
            				
            				
            				
                        	 
//            				 ReenviarDetento(empresa);
//                        	 
//                        	 System.out.println(" clicado id:" + empresa);
//                        	 
//                        	 	copyreg = new CopyTaskReenvio(det,maincontrole);
//            	            	
//            	        		progressind.progressProperty().unbind();
//            	        		
//            	        		progressind.progressProperty().bind(copyreg.progressProperty());
//            	        		
//
//            	        		
//            	        		trace.textProperty().unbind();
//            	        		
//            	        		trace.textProperty().bind(copyreg.messageProperty());
//
//
//            	        		copyreg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
//            	                         new EventHandler<WorkerStateEvent>() {
//
//
//            								@Override
//            	                             public void handle(WorkerStateEvent t) {
//            	                                 com.tecsoluction.robo.entidade.Detento copied = copyreg.getValue();
//            	                                
//
//            	                                
//            	                                
//            	                                 trace.textProperty().unbind();
//            	                                 trace.setText("Reenviado: " + copied );
//            	                                
//
//            	                                 
//            	                                 progressind.setVisible(false);
//            	                                 
//            	                                
//            								
//            								}
//
//
//
//
//            	                         });
//            	           
//
//            	                 // Start the Task.
//            	                 new Thread(copyreg).start();
//            	                 
//            	            
//            	        
//            	               
//
//                        	 
//                         
//                         
//                         
//                         });
                         
                   //	  btn.setOnAction(event);

            				 ReenviarDetento(empresa);
            				
                         
                    	 });
                         
                    	
                    	 
                    	 }
                    	 
                    	 
                    	 ImageView ivv = new ImageView();
  				        ivv.setImage(imgSend);
  				        ivv.setPreserveRatio(true);
  				        ivv.setSmooth(true);
  				        ivv.setCache(true);
  				        btn.setGraphic(ivv);
                    	 
                    	 
                         setGraphic(btn);
                         setText(null);
                         
                         

                     }
                     
                     
                 }

				private void ReenviarDetento(TreeItem<Detento> empresa) {
					
				
				Detento value =	empresa.getValue();

				det = value.det;

				
				System.out.println("valor detento: " + value.nome);

		    	
		    	det = ConverterDetento(value);
		    	
		    	
		    	boolean isvalido = ValidarDetento(det);
		    	
		    	if(isvalido){
		    		
		    		
		    		
		    		Reenviar(det);
					
//		    		maincontrole.enviarEmail(det);
//		    		
//		    		
//					filtros.remove(value);
//					detentos.remove(value.det);
//					detentos.add(det);
//					
//					trace.textProperty().unbind();
//					trace.setText("Reenviado Sucesso: " + det.getNome());
//					
//					Inicializar(maincontrole.getDetentoErro(), maincontrole);

		    		
		    	}else {
		    		
		    		
//					detentos.remove(value.det);
//					detentos.remove(value.det);
//					filtros.remove(value);
					
//					detentos.add(det);
		    		
		    		Detento val = new Detento(det);
		    	//	value.det = det;
		    		
		    		filtros.remove(value);
		    		filtros.add(val);
					 initColuna();
			     	 setCellFactoryColuna();
			     	
//			     	InserirDadosTabela(detentos);
			    	 
			     	InitTabela();
			     	trace.textProperty().unbind();			     	
			     	trace.setText("Reenviado Falha: " + val.nome + val.erros);
		    		
		    		
		    		
		    		
		    	}
		    	
//		    	if(servicoreenvio.isRunning()){
//		    		
//		    		servicoreenvio.cancel();
//		    		
//		    	}
				

//	   		    Platform.runLater(() ->
//	   		    {
//	   		    	
//	   		 	
//			    	progressind.setVisible(false);
//
//	   		   
//	   		    });
				
			
				
				
//				 initColuna();
//		     	 setCellFactoryColuna();
//		     	
//		     	//InserirDadosTabela(detentos);
//		    	 
//		     	InitTabela();
				
				
				
					
					
					
					
				}


             };
             return cell;
         }
     };
     
     Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>> cellFactorydel
     = //
     new Callback<TreeTableColumn<Detento, Boolean>, TreeTableCell<Detento, Boolean>>() {
         @Override
         public TreeTableCell<com.tecsoluction.robo.controller.DetentoTabelaController.Detento, Boolean> call(final TreeTableColumn<Detento, Boolean> param) {
             final TreeTableCell<Detento, Boolean> cell = new TreeTableCell<Detento, Boolean>() {

          	   Image imgDel = new Image(getClass().getResourceAsStream("/images/del.png"));
          	   
                 final JFXButton  btn2 = new JFXButton();
                 
                 com.tecsoluction.robo.entidade.Detento det = null;

                 @Override
                 public void updateItem(Boolean item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                         setText(null);
                     } else {
                         btn2.setButtonType(JFXButton.ButtonType.RAISED);
                         btn2.setOnAction(event -> {

                         
                        	 TreeItem<Detento> empresa = getTreeTableView().getTreeItem(getIndex());
                        	 
//             				Detento value =	empresa.getValue();
//            				det = value.det;
                        	 
            				DeleteDetento(empresa);
                        	 
                        	 System.out.println(" clicado id:" + empresa);

     	
                         
                         
                         
                         });
                         
                         ImageView ivv = new ImageView();
 				        ivv.setImage(imgDel);
 				        ivv.setPreserveRatio(true);
 				        ivv.setSmooth(true);
 				        ivv.setCache(true);
 				     btn2.setGraphic(ivv);
 				        
                         setGraphic(btn2);
                         setText(null);
                         
//         				if(det.getStatusenvio().equals("SUCESSO")||det.getStatusenvio().equals("PENDENTE")){
//        					
//        					btn.setDisable(true);
//        					
//        				}
                     }
                     
                     
                 }

				private void DeleteDetento(TreeItem<Detento> empresa) {

				Detento value =	empresa.getValue();
				 det = value.det;
				
				System.out.println("valor detento: " + value.nome);
				System.out.println("valor detento dtet VARA EXCLUDE: " + det.getVara());
				
				
//				setCellFactoryColuna();	
				
				detentos.remove(det);
				
				filtros.remove(value);
				
			//	Inicializar(maincontrole.getDetentoErro(), maincontrole);
				
				 initColuna();
		     	 setCellFactoryColuna();
		     	
//		     	InserirDadosTabela(detentos);
		    	 
		     	InitTabela();
		     	
		     	trace.setText("Excluido: " + det.getNome());
					
					
					
					
				}
             };
             return cell;
         }
     };
    
 
     
     
     public void Reenviar(com.tecsoluction.robo.entidade.Detento det){
    	 
    	 
    	 if(servicoreenvio == null){
 		servicoreenvio = new ServicoReenvio(maincontrole, this, det); 
 		
 		servicoreenvio.start();
    	
    	 }else {
    		 
    		 
    		 servicoreenvio.setDetento(det);
    		 servicoreenvio.setTentativas(1);
    		 servicoreenvio.restart();
    		 
    	 }
    	 
    	 
     }
     
     
		private boolean ValidarDetento(com.tecsoluction.robo.entidade.Detento det2) {
			
			
//			progressind.setVisible(true);
			
			boolean valido = true;
			
//			String nulo = null;
			String branco = "";
			
			
			PreencherVaraComCaracteristica(det2);
					
					
			 if((det2.getProntuario()==null||(det2.getProntuario().equals(branco)) )){
					
					valido = false;
					det2.addErros("Prontuario");
						
//					Validadet2Informacao("Prontuario det2 Branco ou Nulo");
						
					}
					
				if((det2.getProcesso()==null)&&(det2.getVep()==null) ){
						
					valido = false;
					det2.addErros("Processo/VEP NULOS");
					
//					Validadet2Informacao("Processo det2 Branco ou Nulo");
						
					}
					
				 if((det2.getViolacoes().toString()==null||(det2.getViolacoes().toString().equals(branco)) )){
						
					valido = false;
					det2.addErros("Violações");
					
//					Validadet2Informacao("Violações det2 Branco ou Nulo");
						
					}
				 
				 if((det2.getViolacoes().get(0).getCaracteristica()==null || det2.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
						
						valido = false;
						det2.addErros("CARACTRISTICA");
						
//						Validadet2Informacao("Violações det2 Branco ou Nulo");
							
						}
				 
				 if((det2.getEstabelecimento().toString()==null||(det2.getEstabelecimento().toString().equals(branco)) )){
						
						valido = false;
						det2.addErros("Estabelecimento");
						
//						Validadet2Informacao("Violações det2 Branco ou Nulo");
							
						}
				 
				 
				 if((det2.getVara() == null || det2.getVara().equals(branco)) ){
						
						valido = false;
						det2.addErros("Vara");
						
//						Validadet2Informacao("Violações det2 Branco ou Nulo");
							
						}
					 
					 
//					 if((det2.getVara().toString()==null||(det2.getVara().toString().equals(branco)) )){
//							
//						valido = false;
//						det2.addErros("Vara");
//						
////						Validadet2Informacao("Violações det2 Branco ou Nulo");
//							
//						}
					 
					 
//					 if((det2.getEquipamentos().toString()==null||(det2.getEquipamentos().toString().equals(branco)) )){
//							
//							valido = false;
//							det2.addErros("Equip");
//							
////							Validadet2Informacao("Violações det2 Branco ou Nulo");
//								
//							}
				 
//					if( (det2.getProcesso()!=null) && (!det2.getProcesso().isEmpty()) && (det2.getVep()!=null)&&(!det2.getVep().isEmpty()) ){
//						
//						
//						valido = false;
//						det2.addErros("Processo/VEP PREENCHIDO");
//						
////						Validadet2Informacao("Processo det2 Branco ou Nulo");
//							
//					
//					}
					
					if( (det2.getProcesso()!=null) && (!det2.getProcesso().isEmpty()) && (det2.getVep()!=null)&&(!det2.getVep().isEmpty()) ){
						
						
						valido = false;
						det2.addErros("Processo/VEP PREENCHIDO 6");
						
//						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
							
					
					}
					
					
					if( (det2.getProcesso()!=null) && (det2.getProcesso().isEmpty()) && (det2.getVep()!=null)&&(det2.getVep().isEmpty()) ){
						
						
						valido = false;
						det2.addErros("Processo/VEP VAZIOS 6");
						
//						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
							
					
					}
			
			
			
			return valido;
			

		}
     
     
     @FXML
     void addfiltro(ActionEvent event) {

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
	
//    @FXML
//    void salvarfiltro(ActionEvent event) {
//
//    }
//	
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
                
        		trace.textProperty().bind(this.messageProperty());
	        
	        
	        }

	        @Override
	        protected Task<Integer> createTask() {
	            return new Task<Integer>() {
	                @Override
	                protected Integer call() throws Exception {         	  
	                       
	                	updateMessage("Fechando Janela Tabela Detentos.");
	                	
	                	
	                	Thread.sleep(2000);
	                	
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
	
	
	
	
	class Detento extends RecursiveTreeObject<Detento>{
//		StringProperty chave;
//		StringProperty valor;
//		StringProperty objeto;
		
		com.tecsoluction.robo.entidade.Detento det;
		
//		List<Violacao> violacoesobj;
//		
//		List<Notificacao> notificacoesobj;
		
		StringProperty numero;

		StringProperty nome;
		    
		StringProperty idmonitorado;
		    
		StringProperty prontuario;
		    
		StringProperty Estabelecimento	;
		StringProperty perfilatual;
		StringProperty artigos;
		StringProperty statusenvio;

//		  @JsonIgnore
//		  @OneToMany(fetch=FetchType.EAGER)
//		    private List<Violacao> violacoes = new ArrayList<Violacao>();
		
		StringProperty violacoes;
		
		StringProperty notificacao;
		   
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
				,String vara,String dataviola,String caracteristica, String notif)
		
		{
			this.numero = new SimpleStringProperty(numero);
			this.nome  = new SimpleStringProperty(nome);
			this.violacoes  = new SimpleStringProperty(violacoes);
			this.notificacao = new SimpleStringProperty(notif);
			
		
			
			
			this.prontuario  = new SimpleStringProperty(prontuario);
			
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
			this.caracteristica = new SimpleStringProperty(this.det.getViolacoes().get(0).getCaracteristica());
//			this.equipamentos  = new SimpleStringProperty(equipamentos);
			
			
			
			
			//this.address = new SimpleStringProperty(address);
			
		}
		
		
		
		public Detento(com.tecsoluction.robo.entidade.Detento dete) {
			
			
//			List<Notificacao> nots = new ArrayList<Notificacao>();
			
//			nots = retornarVio(dete.getViolacoes());
			
			this.det = dete;
			
//			this.numero = new SimpleStringProperty(dete.getNumero());
			this.nome  = new SimpleStringProperty(dete.getNome());
			this.violacoes  = new SimpleStringProperty(dete.getViolacoes().toString());
			this.notificacao = new SimpleStringProperty(retornarVio(dete.getViolacoes()).toString());
			this.dataviola  = new SimpleStringProperty(FormatadorData(dete.getViolacoes().get(0).getDataviolacao()));
			
			this.prontuario  = new SimpleStringProperty(dete.getProntuario());
			
			this.Estabelecimento  = new SimpleStringProperty(dete.getEstabelecimento());
			this.perfilatual  = new SimpleStringProperty(dete.getPerfil());
//			this.artigos  = new SimpleStringProperty(dete.getArtigos());
			this.statusenvio  = new SimpleStringProperty(dete.getStatusenvio());
			this.email  = new SimpleStringProperty(dete.getEmail());
			this.processo  = new SimpleStringProperty(dete.getProcesso());
			this.vep  = new SimpleStringProperty(dete.getVep());
//			this.ult_posicao  = new SimpleStringProperty(dete.getUlt_posicao());
//			this.alarme_posicao  = new SimpleStringProperty(dete.getAlarme_posicao());
//			this.equipamentos  = new SimpleStringProperty(dete.getEquipamentos());
			
//			this.telefone  = new SimpleStringProperty(dete.getTelefone());
//			this.valortotal  = new SimpleStringProperty(dete.getValortotal());
			this.erros  = new SimpleStringProperty(dete.getErros().toString());
			this.vara  = new SimpleStringProperty(dete.getVara());
			this.caracteristica = new SimpleStringProperty(dete.getViolacoes().get(0).getCaracteristica());

//			this.equipamentos  = new SimpleStringProperty(equipamentos);
			
//			this.violacoesobj = dete.getViolacoes();
			
//			this.notificacoesobj = retornarVio (dete.getViolacoes());
						
//			det.setViolacoes(violacoesobj);
			
		}
		
		
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
		
	
	
	private com.tecsoluction.robo.entidade.Detento ConverterDetento(Detento deten){
		
//		progressind.setVisible(true);
		
		com.tecsoluction.robo.entidade.Detento detento = new com.tecsoluction.robo.entidade.Detento() ;
		
		List<Violacao>  vios = null;
		
		vios = deten.det.getViolacoes();
		
		List<Violacao>  violaaux = new ArrayList<Violacao>();
		
		List<Notificacao> nots = null; 
		
		
		for (int i = 0; i < vios.size(); i++) {
			
			Violacao v = vios.get(i);
			
			nots = new ArrayList<Notificacao>();
			
			for (int j = 0; j < v.getNotificacoes().size(); j++) {
				
				Notificacao n = v.getNotificacoes().get(j);
				
				nots.add(n);
				
				
			}
			
			v.setNotificacoes(nots);
			
			violaaux.add(v);
			
			
		}
		
		detento.setViolacoes(violaaux);
		
		
		
//		List<Notificacao>  not = deten.notificacoesobj;
		
	//	detento.setNumero(deten.numero.getValue());
		detento.setNome(deten.nome.getValueSafe());
		detento.setProntuario(deten.prontuario.getValueSafe());
		detento.setEstabelecimento(deten.Estabelecimento.getValueSafe());
		detento.setPerfil(deten.perfilatual.getValueSafe());
//		detento.setArtigos(deten.artigos.getValue());
		detento.setStatusenvio(deten.statusenvio.getValueSafe());
		detento.setEmail(deten.email.getValueSafe());
		detento.setProcesso(deten.processo.getValueSafe());
		detento.setVep(deten.vep.getValueSafe());
		detento.getErros().clear();
		detento.addErros("Erros Apagados no Reevio");
//		detento.setViolacoes(vios);
		detento.setVara(deten.vara.getValueSafe());
		
		String caracteristica = deten.caracteristica.getValueSafe();
		detento.getViolacoes().get(0).setCaracteristica(caracteristica);
		
		
		
		
		
		
		
		
		
		return detento;
		
	}
	
	
	
	


    @FXML
    void refresh(ActionEvent event) {
    	
    	 initColuna();
     	 setCellFactoryColuna();
     	 InitTabela();
    	

    }
    
    
    public void PreencherVaraComCaracteristica(com.tecsoluction.robo.entidade.Detento det){
    	
    	
    	
        String varaex = null;
        
        
        int cont =0;
        
    	for (Violacao vio : det.getViolacoes()) {
        	
//        	builder.append("\r\n" + vio.getAlarme() + "\r\n");
//        	alarme = alarme +"," + vio.getAlarme();
        	
        	if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){
        		
        		cont = cont +1;
        		
        		varaex = vio.getCaracteristica();
        		det.setVara(varaex);
        		
        		
        		
        	}
        	
        	 if(varaex == null || varaex ==""){
     	    	
     	    	varaex="vazia";
     	    	
     	    	det.setStatusenvio("ERROR");
     	    	det.getErros().add("SEM VARA PREENCHIDA(CARACTERISTICA)");
     	    	
     	    	//detentoserros.add(det);
     	    	
     	    	
     	    }
        	

    		
    	}
    	
    	
    }
    
    
    public String FormatadorData(String data){
    	
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
    
    
    
//    
//    void CriarNovaInstanciaBotao(){
//    	
//    	btn = new JFXButton();
//    	
//    	
//    }
//    
//    
//    void CriarNovaInstanciaBotao2(){
//    	
//    	btn2 = new JFXButton();
//    	
//    	
//    }


}


	
    

