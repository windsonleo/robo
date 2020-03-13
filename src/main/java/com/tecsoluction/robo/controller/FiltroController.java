package com.tecsoluction.robo.controller;


import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

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
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.ManipuladorArquivo;

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
public class FiltroController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(FiltroController.class);

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
	    private JFXButton btaddfiltro;

	    @FXML
	    private ImageView imgvcon;

	    @FXML
	    private AnchorPane anchorcena;

	    @FXML
	    private Label trace;

	    @FXML
	    private JFXButton btsalvar;

	    @FXML
	    private JFXButton btfechar;

	    @FXML
	    private ProgressIndicator progressind;
		
	    
	    private  ObservableList<Filtro> filtros = FXCollections.observableArrayList();
	    
	   public List<com.tecsoluction.robo.entidade.Filtro> filtrosentite = new ArrayList<com.tecsoluction.robo.entidade.Filtro>();
	    
	    @FXML
	    private JFXTreeTableView<Filtro> tableview ;
	    
//	    private JFXTreeTableColumn<Filtro,Boolean> deletecoluna ;
	    
	    private JFXTreeTableColumn<Filtro,String> chavecoluna;
	    
	    private JFXTreeTableColumn<Filtro,String> valorcoluna ;
	  
	    private JFXTreeTableColumn<Filtro,String> objetocoluna;
	    
	    private JFXTreeTableColumn<Filtro,Boolean> buttomExccoluna;
	    
	    @FXML
	    private JFXTextField  filterField;
	    
	    
	    @FXML
	    private Label size ;
	    
	    @FXML
	    private JFXCheckBox jchbdetento;
	    
	    @FXML
	    private JFXCheckBox jchbnotificacao;
	    @FXML
	    private JFXCheckBox jchbviolacao;
	    @FXML
	    private JFXCheckBox jchbregistro;	  
	    
	    
	    @FXML
	    private JFXComboBox<String>  jcbcampo ;
	    
	    
	    private String objetoalvo="";
	    
	    public ManipuladorArquivo manipulador;
	    
	    private com.tecsoluction.robo.entidade.Filtro filtrosalvo;
	    
	    
	    public FiltroController() {

	    
	    
	    }
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
//		
//		if(getFiltrosentite().isEmpty()){
//		
//		Inicializar ();
//		
//		} else {
//			
//			
//			PreencherTabComList();
//			Inicializar ();
//			
//		}
		
		 
		
		
	}	
	
	
	
	
	

     
     private void PreencherTabComList() {

    	 
    	 for (int i = 0; i < getFiltrosentite().size(); i++) {
			
    		 com.tecsoluction.robo.entidade.Filtro fil = getFiltrosentite().get(i);
		
    	 	Filtro f = new Filtro(fil);
    	 
    	 	filtros.add(f); 
    	 
    	 }
    	 
	}





	public void Inicializar (){
    	 
    	 
    	 initColuna();
     	 setCellFactoryColuna();
     	
     	InserirDadosFiltro();
     	
     	PreencherTabComList();
    	 
     	InitTabela();
     	
     	PreencherComboCampo();
     	
     	
     	
     
    	
    	 
     }
	
	
	public void InitList (List<com.tecsoluction.robo.entidade.Filtro> fils){
   	 
   	 
		filtrosentite.clear();
		filtrosentite.addAll(fils);
		
		 initColuna();
    	
		setCellFactoryColuna();
		PreencherTabComList();
    	InitTabela();
    	
    	PreencherComboCampo();
    	
    
   	
   	 
    }
     
     
     private void initColuna(){
    	 
    	chavecoluna  = new JFXTreeTableColumn<Filtro,String>("Chave");
    	 chavecoluna.setPrefWidth(100);
    	 chavecoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Filtro, String> param) ->{
    		    if(chavecoluna.validateValue(param)) return param.getValue().getValue().chave;
    		    else return chavecoluna.getComputedValue(param);
    		});
 		
    	 valorcoluna = new JFXTreeTableColumn<Filtro,String>("Valor");
 		valorcoluna.setPrefWidth(100);
 		//valorcoluna.setPrefWidth(100);
 		valorcoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Filtro, String> param) ->{
		    if(valorcoluna.validateValue(param)) return param.getValue().getValue().valor;
		    else return valorcoluna.getComputedValue(param);
		});
 		
   	 objetocoluna = new JFXTreeTableColumn<Filtro,String>("Objeto");
   	objetocoluna.setPrefWidth(100);
		//valorcoluna.setPrefWidth(100);
   	objetocoluna.setCellValueFactory((TreeTableColumn.CellDataFeatures<Filtro, String> param) ->{
		    if(objetocoluna.validateValue(param)) return param.getValue().getValue().objeto;
		    else return objetocoluna.getComputedValue(param);
		});
 		
    buttomExccoluna =  new JFXTreeTableColumn<Filtro,Boolean>("Excluir");
 
     }
     
     
     
     
  
	@SuppressWarnings("unchecked")
	private void InitTabela() {
    	 
    	 
    	 final TreeItem<Filtro>  root = new RecursiveTreeItem<Filtro>(filtros, RecursiveTreeObject::getChildren);
    	 
    	// tableview = new JFXTreeTableView<Filtro>(root);
    	 
    	 tableview.setShowRoot(false);
       	 tableview.setEditable(true);
       	 tableview.setRoot(root);
//       	 tableview.getChildrenUnmodifiable
       	 tableview.getColumns().setAll(chavecoluna, valorcoluna, objetocoluna,buttomExccoluna);
       	 
     	InitComponentes();
       	 
       //	anchor.getChildren().add(tableview);
        	
    	 
     }
     
     
     
     
     private void InitComponentes() {

    	 
    	 //filterField = new JFXTextField();
    	 filterField.textProperty().addListener((o,oldVal,newVal)->{
    		 tableview.setPredicate(filtro -> filtro.getValue().chave.get().contains(newVal)
    	                 || filtro.getValue().objeto.get().contains(newVal)
    	                 || filtro.getValue().valor.get().contains(newVal));
    	 });
    	  
//    	 Label size = new Label();
    	 size.textProperty().bind(Bindings.createStringBinding(()->tableview.getCurrentItemsCount()+"",
    			 tableview.currentItemsCountProperty())); 
    	 
    	 
	}





	private void setCellFactoryColuna(){
    	 
    	 
    	 chavecoluna.setCellFactory((TreeTableColumn<Filtro, String> param) -> 
    	 new GenericEditableTreeTableCell<Filtro,String>(new TextFieldEditorBuilder()));
    	 
    	 chavecoluna.setOnEditCommit((CellEditEvent<Filtro, String> t)->{
     ((Filtro) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).chave
                 .set(t.getNewValue());
 });
  
    	 valorcoluna.setCellFactory((TreeTableColumn<Filtro, String> param) -> 
    	 new GenericEditableTreeTableCell<Filtro,String>(new TextFieldEditorBuilder()));
    	 
    	 valorcoluna.setOnEditCommit((CellEditEvent<Filtro, String> t)->{
     ((Filtro) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                 .valor.set(t.getNewValue());
 });
    	 
    	 objetocoluna.setCellFactory((TreeTableColumn<Filtro, String> param) -> 
    	 new GenericEditableTreeTableCell<Filtro,String>(new TextFieldEditorBuilder()));
    	 
    	 objetocoluna.setOnEditCommit((CellEditEvent<Filtro, String> t)->{
     ((Filtro) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue())
                 .objeto.set(t.getNewValue());
 });
    	 
    	 
    	 buttomExccoluna.setCellFactory(cellFactorydel);
    	 
     }
	
		
	 Callback<TreeTableColumn<Filtro, Boolean>, TreeTableCell<Filtro, Boolean>> cellFactorydel
     = //
     new Callback<TreeTableColumn<Filtro, Boolean>, TreeTableCell<Filtro, Boolean>>() {
         @Override
         public TreeTableCell<com.tecsoluction.robo.controller.FiltroController.Filtro, Boolean> call(final TreeTableColumn<Filtro, Boolean> param) {
             final TreeTableCell<Filtro, Boolean> cell = new TreeTableCell<Filtro, Boolean>() {

          	   Image imgDel = new Image(getClass().getResourceAsStream("/images/del.png"));
          	   
                 final JFXButton btn = new JFXButton();
                 
                 com.tecsoluction.robo.entidade.Filtro det = null;

                 @Override
                 public void updateItem(Boolean item, boolean empty) {
                     super.updateItem(item, empty);
                     if (empty) {
                         setGraphic(null);
                         setText(null);
                     } else {
                         btn.setButtonType(JFXButton.ButtonType.RAISED);
                         btn.setOnAction(event -> {

                         
                        	 TreeItem<Filtro> empresa = getTreeTableView().getTreeItem(getIndex());
                        	 
                        	 Filtro value =	empresa.getValue();
            				det = value.filter;
                        	 
            				DeleteFiltro(empresa);
                        	 
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
                         
//         				if(det.getStatusenvio().equals("SUCESSO")||det.getStatusenvio().equals("PENDENTE")){
//        					
//        					btn.setDisable(true);
//        					
//        				}
                     }
                     
                     
                 }

				private void DeleteFiltro(TreeItem<Filtro> empresa) {

					Filtro value =	empresa.getValue();
//				com.tecsoluction.robo.entidade.Filtro 
					det = value.filter;
				
				System.out.println("valor detento: " + value.chave);
				System.out.println("valor detento dtet: " + det);
				trace.setText("Reenviar: " + value.valor);
				
			//	getTreeTableView().getTreeItem(getIndex());
				
				filtrosentite.remove(det);
				filtros.remove(value);
				
				 setCellFactoryColuna();
			     	
		    	 
			     	InitTabela();
			     	
//			     	 PreencherTabComList();
			     	
//			     	PreencherComboCampo();
				
				
					
				
					
					
					
				}
             };
             return cell;
         }
     };
     
     
     
 
	private void InserirDadosFiltro(){
    	 
    	 
//    	 for(int i = 0 ; i< 2; i++){
//    		 filtros.add(new Filtro("DESCRICAO","SEM VIOLACAO" +i,"NOTIFICACAO"));
//    		}
//    	 
//    	 for(int j = 0 ; j< 2; j++){
//    		 filtros.add(new Filtro("ALERTA","FIM DE BATERIA"+j,"DETENTO"));
//    		}
//    	 
//    	 for(int k = 0 ; k< 2; k++){
//    		 filtros.add(new Filtro("DURACAO","MAIOR 120 MINUTOS"+k,"VIOLAÇÃO"));
//    		}
    
    	 
     }
     
 
    
    
     @FXML
     void addfiltro(ActionEvent event) {
    	 
    	 filtrosalvo = new com.tecsoluction.robo.entidade.Filtro();
    	 
    	 filtrosalvo.setChave(jcbcampo.getSelectionModel().getSelectedItem());
    	 filtrosalvo.setValor(jtxtfiltro.getText());
    	 filtrosalvo.setObjeto(objetoalvo);
    	 
    	 filtrosentite.add(filtrosalvo);
    	 
    	 Filtro f = new Filtro(filtrosalvo);
    	 
    	 filtros.addAll(f); 
    	 
    	 jtxtfiltro.setText("");
    	 jcbcampo.getSelectionModel().clearSelection();
    	 

     	 setCellFactoryColuna();
     	
    	 
     	InitTabela();
     	
     	PreencherComboCampo();
    	 
    	 
    	 
    	 

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
    void salvarfiltro(ActionEvent event) {
    	
//    	this.manipulador = manipu;
    	
//    	for (int i = 0; i < filtrosentite.size(); i++) {
//    		
//    		com.tecsoluction.robo.entidade.Filtro fill = filtrosentite.get(i);
//			
//    		manipulador.EscreverFilter(manipulador.getFilefiltro().getPath(), fill);
//    	
//    	
//    	}
    	
    	
    	
    	

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
                
        		trace.textProperty().bind(this.messageProperty());
	        
	        
	        }

	        @Override
	        protected Task<Integer> createTask() {
	            return new Task<Integer>() {
	                @Override
	                protected Integer call() throws Exception {         	  
	                       
	                	updateMessage("Fechando Janela Filtro .");
	                	
	                	
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
	
	
	
	
	class Filtro extends RecursiveTreeObject<Filtro>{
		
		com.tecsoluction.robo.entidade.Filtro filter ;
		StringProperty chave;
		StringProperty valor;
		StringProperty objeto;
		
		public Filtro(String chave,String valor,String obj)
		{
			this.chave = new SimpleStringProperty(chave);
			this.valor  = new SimpleStringProperty(valor);
			this.objeto  = new SimpleStringProperty(obj);
			//this.address = new SimpleStringProperty(address);
			
		}
		
		public Filtro(com.tecsoluction.robo.entidade.Filtro filt)
		{
			this.filter = filt;
			this.chave = new SimpleStringProperty(filt.getChave());
			this.valor  = new SimpleStringProperty(filt.getValor());
			this.objeto  = new SimpleStringProperty(filt.getObjeto());
			//this.address = new SimpleStringProperty(address);
			
		}
		
		
	}
	
	
	
	private void PreencherComboCampo(){
		
		boolean validcheckbox = false;
		validcheckbox=ValidarCheckBox();
		
		if(validcheckbox) {
			
			PreencherCombo();
			
			
			
		}
		

		
		
		
		
	}
	
	
	private void PreencherCombo() {

	
		
		if(jchbdetento.isSelected()){
			
			jcbcampo.getItems().setAll(PegarAtributosEntidade("com.tecsoluction.robo.entidade.Detento"));
			objetoalvo="Detento";
			
		}
		if(jchbnotificacao.isSelected()) {
			
			jcbcampo.getItems().setAll(PegarAtributosEntidade("com.tecsoluction.robo.entidade.Notificacao"));
			objetoalvo="Notificacao";
		
		}
		if(jchbregistro.isSelected()) {
			
			jcbcampo.getItems().setAll(PegarAtributosEntidade("com.tecsoluction.robo.entidade.Registro"));
			objetoalvo="Registro";
		
		}
		
		if(jchbviolacao.isSelected()){
			

			jcbcampo.getItems().setAll(PegarAtributosEntidade("com.tecsoluction.robo.entidade.Violacao"));
			objetoalvo="Violacao";
		
		}
		
		
	}
	
	
	private List<String> PegarAtributosEntidade(String nomeEntidade){
		
		List<String> atributos = new ArrayList<String>();
		
		 Object object = null;  
	      
		 
		 try {  
	            Class<?> classDef = Class.forName(nomeEntidade);  
	            object = classDef.newInstance();  
	            } catch (InstantiationException e) {  
	                  System.out.println(e);  
	            } catch (IllegalAccessException e) {  
	                  System.out.println(e);  
	            } catch (ClassNotFoundException e) {  
	                  System.out.println(e);  
	            }     

	        Field[] fields  =   object.getClass().getDeclaredFields();  
	        System.out.println("Os Campos da Classe São : ");  

	        for (int i = 0 ; i < fields.length ; i++){  
	        	
	            System.out.println("Nome : " + fields[i].getName()); 
	            atributos.add(fields[i].getName());
	        }     
		
		
		
		
		
		return atributos;
	}





	private boolean ValidarCheckBox(){
		
		boolean isvalid = false;
		int count =0;
		
		if(jchbdetento.isSelected()){
			
			count = count +1;
			
			isvalid =ExisteMaisDeUmSelecionado(count);
			
			if(isvalid){
				
				
			}
			
			
		}
		if(jchbnotificacao.isSelected()) {
			
			count = count +1;
			isvalid =ExisteMaisDeUmSelecionado(count);
			
			if(isvalid){
				
				
			}
			
		}
		if(jchbregistro.isSelected()) {
			
			count = count +1;
			isvalid =ExisteMaisDeUmSelecionado(count);
			
			if(isvalid){
				
				
			}
			
		}
		
		if(jchbviolacao.isSelected()){
			
			count = count +1;
			isvalid =ExisteMaisDeUmSelecionado(count);
			
			if(isvalid){
				
				
			}
			
			
		}
		
		
		
		return isvalid;
	}
	
	
	private boolean ExisteMaisDeUmSelecionado(int count){
		
		if(count > 1) {
			
			
			
			return false;
		}
		
		return true;
	}
	
	
	
	
	
	
    @FXML
    void acaocheck() {

    	PreencherComboCampo();
	
	
	}





	public void InitManipulador(ManipuladorArquivo manipulador2) {

		this.manipulador = manipulador2;
		
		
		
		
	}
	
	

}


	
    

