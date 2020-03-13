package com.tecsoluction.robo.controller;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import com.itextpdf.text.log.SysoCounter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.lowagie.text.DocumentException;
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.Autenticador;
import com.tecsoluction.robo.entidade.CopyTask;
import com.tecsoluction.robo.entidade.CopyTaskAnalise;
import com.tecsoluction.robo.entidade.CopyTaskArquivos;
import com.tecsoluction.robo.entidade.CopyTaskDet;
import com.tecsoluction.robo.entidade.CriarExcel;
import com.tecsoluction.robo.entidade.Detento;
import com.tecsoluction.robo.entidade.Filtro;
import com.tecsoluction.robo.entidade.GerenciadorFiltro;
import com.tecsoluction.robo.entidade.JanelaBarraDeTarefa;
import com.tecsoluction.robo.entidade.LeitorExcel;
import com.tecsoluction.robo.entidade.ManipuladorArquivo;
import com.tecsoluction.robo.entidade.Registro;
import com.tecsoluction.robo.entidade.ServicoLote;
import com.tecsoluction.robo.entidade.UnificarDetento;
import com.tecsoluction.robo.entidade.ValidacaoRegistro;
import com.tecsoluction.robo.entidade.Violacao;
import com.tecsoluction.robo.util.HeaderFooterPageEvent;
import com.tecsoluction.robo.util.WordPoi;

import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Shear;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class MainController implements Initializable{

	final static Logger logger = LoggerFactory.getLogger(MainController.class);

	


	@FXML
	private  Label trace,lblligar,lbltotreg1,lblteste,lblanalisar,lblcriar,lblretornoligar,lblretornotarefa,lblretornoautomatico;


	@FXML
	private JFXToggleButton jtgbligar;

	@FXML
	private JFXToggleButton jtgbcriartarefa;


	@FXML
	private JFXToggleButton jtgbfiltrosistema;

	@FXML
	private JFXToggleButton jtgbteste ;

	@FXML
	private JFXToggleButton jtgbanalisararquivo;
	@FXML
	private JFXToggleButton jtgbtmonitorarrede;


	@FXML
	private JFXToggleButton jtgexibirvalidacao;

	@FXML
	private JFXButton btconf;

	@FXML
	private ImageView imgvconf;

	@FXML
	private ImageView imgvoff;

	@FXML
	private JFXButton btoff;

	@FXML
	private JFXButton  btoficio;

	@FXML
	private ImageView imgvoficio;

	@FXML
	private ImageView imgvservern;


	@FXML
	private JFXButton  btserver;

	@FXML
	private ImageView imgvserver;

	@FXML
	private ImageView  imgvfiltro;



	private static double xOffset = 0;
	private static double yOffset = 0;


	@FXML
	private VBox vbox;

	@FXML
	private VBox vbval;


	@FXML
	private VBox  vbetapas;

	@FXML
	private VBox  vbeta;

	@FXML
	private VBox  vboval;

	@FXML
	private VBox  vbvalida ;

	@FXML
	private AnchorPane   anchor;

	@FXML
	private AnchorPane   aavalida;

	@FXML
	private AnchorPane anchorvalida;

	@FXML
	private VBox vbbvalida;


	private File filesaved;
	
	

	private File fileanalise;


	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	public ProgressIndicator progressind;
	
	@FXML
	public ProgressIndicator progressindanalisar;

	@FXML
	public ProgressIndicator progressindtarefa;
	@FXML
	public ProgressIndicator  progressindligar;
	@FXML
	public ProgressIndicator progressindteste ;


	@FXML
	public ProgressIndicator  progressindfiltro;


	@FXML
	private SubScene subcena;
	@FXML
	private PerspectiveCamera camara;
	@FXML
	private ParallelCamera camaraparalelo;


	@FXML
	private AmbientLight luzambiente;

	@FXML
	private PointLight  pontoluz;
	@FXML
	private AnchorPane anchorcena;

	//	    @FXML
	//	    private PointLight pointluz;


	@FXML
	private AnchorPane anchorinfo ;

	public ConfiguracaoController configcontroler;

	public FiltroController filtrocontrol;  


	private String nomeArquivo;

	private String nomeModelo;


	private File arquivoLocalizado;
	
	private File filllana;

	private CopyTaskArquivos copyTask;

	private CopyTask copyTaskreg;

	private CopyTaskAnalise copyTaskregg;
	
	
	private CopyTaskDet copyTaskdet;


	private CopyTaskDet copyTaskdetfiltro;


	private List<File> arquivos;


	private List<Registro> registros = new ArrayList<Registro>();
	
	private List<Registro> registrosanalise = new ArrayList<Registro>();

	private List<Detento> detentos = new ArrayList<Detento>();

	private List<Registro> registrosNotificar = new ArrayList<Registro>();

	private List<Detento> detentoFinal = new ArrayList<Detento>();


	private List<Detento> detentoEnviado = new ArrayList<Detento>();

	private List<Detento> detentosREnviado = new ArrayList<Detento>();

	private List<Detento> detentoErro = new ArrayList<Detento>();

	private List<Detento> detentosPendetes = new ArrayList<Detento>();

	private List<Detento> detentosPendetescont = new ArrayList<Detento>();

	private List<Registro> registroFiltrados2 = new ArrayList<Registro>();

	private List<Registro> registroFiltrados = new ArrayList<Registro>();

	private List<Registro> registrosSubstituto = new ArrayList<Registro>();

	private List<Registro> registrosSubstitutosemNoti = new ArrayList<Registro>();

	private List<Registro> registrosSubstitutosAll = new ArrayList<Registro>();

	private List<Registro> registroscancela = new ArrayList<Registro>();

	private List<Registro> registrosduplicidade = new ArrayList<Registro>();

	private List<Violacao> violacaoesRemovidas = new ArrayList<Violacao>();

	private List<Detento> detentosremovidos = new ArrayList<Detento>();

	private List<Detento> detentosrepetidos = new ArrayList<Detento>();

	private List<Detento> detentosviolavazia = new ArrayList<Detento>();
	
	private List<Registro> validos = new ArrayList<Registro>();
	
	private List<Registro> invalidos = new ArrayList<Registro>();

	private List<Filtro> filtros = new ArrayList<Filtro>();
	
	private List<Object> objExclude = new ArrayList<Object>();



	public OficioController oficiocontroler;

	public ServidorController servercontroler;

	@FXML
	private JFXButton btminimizar;


	@FXML
	private JFXButton btfechar;

	@FXML
	private JFXButton btmaximinizar;


	private boolean isMaximize =false;

	private ManipuladorArquivo manipulador;


	private JanelaBarraDeTarefa barratarefa ;

	private File arquivoConf;

	private File arquivoFiltro;

	private File  dirfinal;

	private String hostconf;

	private String usuarioconf;

	private String senhaconf;

	private String portaconf;

	private String protocoloconf;

	private boolean existearquivo=false;

	public ServicoLote servicolote;

	private String numoficio;

	@FXML
	private JFXCheckBox jcbemail;

	@FXML
	private JFXCheckBox jcbvalidarbefore;


	private Timeline fiveSecondsWonder;

	private Timeline timeping;



	@FXML
	private Label lblpendente;


	@FXML
	private Label lblsucesso;

	@FXML
	private Label lblerro;

	@FXML
	private Label lbltotal;

	@FXML
	private Label lbltotalenv;

	@FXML
	private Label lbltotreg;

	@FXML
	private Label lblviolaremovida;

	@FXML
	private Label lblnet;

	@FXML
	private Label lbltotregfiltrado;

	@FXML
	private Label lblfiltrosistema;


	@FXML
	private Label lbldetentoremovidos;


	@FXML
	private Label lblfiltrosaplicados;

	@FXML
	private Label lblfilternome;


	@FXML
	private Label lbltotviolavazia;

	@FXML
	private Label lblfiltrosaplicados2;

	@FXML
	private Label lblnomearquivoanalise;
	
	@FXML
	private Label lblservern;

	@FXML
	private VBox vbresult;


	@FXML
	private VBox vvb;


	@FXML			
	private VBox vbresultreg;

	@FXML
	private HBox hbox;


	@FXML
	private AnchorPane anchoresiult;

	@FXML
	private JFXButton btstop;

	@FXML
	private JFXButton btpause;
	
	@FXML
	private JFXButton	btcarregarArquivoAnalise;

	@FXML
	private JFXButton btplay;

	@FXML
	private JFXButton btrefresh;

	@FXML
	private JFXButton  btregerar;

	@FXML
	private ToolBar toolbar;



	@FXML
	private JFXButton btstopauto;

	@FXML
	private JFXButton btpauseauto;

	@FXML
	private JFXButton btplayauto;

	@FXML
	private JFXButton btrefreshauto;

	@FXML
	private ToolBar toolbarauto;

	@FXML
	private JFXButton  btapagar;
	@FXML
	private JFXButton btopensucesso;

	@FXML
	private JFXButton btopenerro;

	@FXML
	private JFXButton btopenpendente;

	private boolean isLocalizarArquivo = true;

	private boolean isservice = false;

	private boolean isautomatico = false;

	private HashMap<Integer, String> lines= new HashMap<Integer, String>();

	private HashMap<Integer, Filtro> linesfilter= new HashMap<Integer, Filtro>();

	private int indexarq = 0;


	public PreloaderController preloadcontrol;


	//	    @FXML
	//	    private JFXButton btshowregs; 


	//	    @FXML
	//	    private JFXButton  btshow;

	@FXML
	private JFXButton  btfiltro;


	//	    @FXML
	//	    private JFXButton  btinfo;

	//		private boolean showreg = false;


	//		private boolean showmail = false;

	@FXML
	private ImageView imgvnet;

	@FXML
	private ImageView imgvshow;



	//		@FXML
	//		private Label lblnet;


	private boolean conected = false;


	public DetentoTabelaController detentotabelacontrol;


	public SplashController splashcontrol;

	@FXML
	private Label lblrede;

	private boolean isconfigurado = false;

	private boolean isconfiguradoserver = false;

	@FXML
	private Tooltip jtooltipfiltro;

	@FXML
	private Tooltip jtooltipdiretorio;

	@FXML
	private Tooltip	jtooltiparquivo;

	private boolean diretoriook = false;

	private  String processofinal = new String();

	private String tabhtml;

	private GerenciadorFiltro gerenciafiltro;


	@FXML
	private Tooltip toltipreenviados;

	@FXML
	private Tooltip jtopsubstituta;

	@FXML
	private Tooltip jtoltipcancela;

	@FXML
	private Label lbnotsubst;

	@FXML
	private Label lbnoticancela;

	@FXML
	private Label 	lbviolavazia;

	@FXML
	private Label lbltotregfil;

	@FXML
	private Label lbldiretorio,lblarquivo,lblmodelo;


	//		private boolean showinfo = false;
	//
	//
	//		private boolean showajuda =false;

	@FXML
	private AnchorPane anchorajuda;


	//	    @FXML
	private JFXButton  btimportar = new JFXButton();
	
	private JFXButton  btimportar2 = new JFXButton();


	private JFXButton  btconverter = new JFXButton();


	@FXML
	private ImageView imgvstep1,imgvstep2,imgvstep3,imgvstep4,imgvstep5,imgvstep6;

	@FXML
	private Group group;

	private RotateTransition	rotate;


	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXDrawer drawer;


	@FXML
	private JFXHamburger hamburgerhelp;

	@FXML
	private JFXDrawer drawerhelp;

	@FXML
	private JFXTextField   jtxfvalidanome,jtxfvalidavara,jtxfvalidaprocesso,
	jtxfvalidavep,jtxfvalidaemail,jtxtfenvio;
	@FXML
	private ImageView  imgvvalidanome,imgvvalidavara,
	imgvvalidaprocesso,imgvvalidavep,imgvvalidaemail,imgvvalidaenvio,imgvvalidatotal;

	@FXML			
	private VBox vboxvalidacao;


	private int qtdregistrolidos=0;
	
	private int qtdregistrofinal=0;

	private int qtdregistrosunificados=0;

	private int  qtddetentocomerro=0;

	private int  qtddetentorepetido=0;

	private int  qtdviolavazia=0;

	private int  qtdsubstituida=0;

	private int qtdnotcancela=0;

	private int qtdregistrosfiltrados=0;


	private boolean validodet = false;


	private boolean isprimeiro = true;


	private UnificarDetento unificar;


	@FXML
	private Label lbltotalenviado;

	@FXML
	private Label lbltotnotsubs;

	@FXML
	private Tooltip jtooltipfiltro2;

	@FXML
	private Tooltip jtoltipviolavazia;
	@FXML
	private Label lblfilternome2;


	@FXML
	private Label lbltotnotcancela;



	private Image image ;
	private Image imagee;

	private Image imaget ;

	private Image image2;


	private Image image22 ;

	private Image imagee2 ;


	@FXML
	private JFXCheckBox	jcbvisualizarvalidacao;


	private JFXButton btconverterfilter  = new JFXButton();

	
	private JFXButton btanalise  = new JFXButton();

	private CriarExcel criarexcel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {


		CarregarObjetosStage();

		CarregarToogles();

		carregarImagem();

		CriarMatriz();


		//		boolean boleanoconf = stageManager.isIsconfigurado();
		//		
		//		boolean boleanoconfserver = stageManager.isIsconfiguradoserver();

		isconfiguradoserver = stageManager.isIsconfiguradoserver();


		//		VerificarConfFiltroInit();

		initDrawer();

		initDrawerHelp();
		//   AcaoBtLigar();

		//		   Importarregistros();
		//		   Converterregistros();

		Converterregistros();

		Importarregistros();

		EventoProcurarArquivo();

		ExecutarFiltroSistema();
		
		ExecutarAnaliseRegistro();


	}



	private void ExecutarAnaliseRegistro() {
		EventHandler<ActionEvent> eventtt = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 


				progressind.setVisible(true);
				progressindanalisar.setVisible(true);

				lblanalisar.setText("Análisando ..");
				
			//	RotacionarCena();

				

//				PosExecute();
				
				
//				registroFiltrados.clear();
				
//				RotacionarCena();
				
//				 try {
//					 
//					 filtroCodigo(registrosanalise);
//
//
//					} catch (NullPointerException eX) {
//						// TODO Auto-generated catch block
//						eX.printStackTrace();
//						trace.textProperty().unbind();
//						trace.setText("Erros nO FILTRO" + eX);
//						jtgbanalisararquivo.setSelected(false);
//						ErrorAlert("Erros na FILTRO");
//						
//
//					}
//				
//				
//					AnaliseRegistro(registrosNotificar);						
//					PreAnalise();

				
				
				
				copyTaskregg = new CopyTaskAnalise(registrosanalise);
				
			
				
				
			//	AnaliseRegistro(registrosanalise);
				
			//	AnaliseRegistro(registros);
				

//				System.out.println("detentos :" + getDetentoFinal().size());


				progressind.progressProperty().unbind();

				progressind.progressProperty().bind(copyTaskregg.progressProperty());



				trace.textProperty().unbind();

				trace.textProperty().bind(copyTaskregg.messageProperty());






				copyTaskregg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
						new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent t) {
						List<Registro> copied = copyTaskregg.getValue();



						trace.textProperty().unbind();
						trace.textProperty().bind(copyTaskregg.messageProperty());

						trace.textProperty().unbind();
						trace.setText("Analise Finalizada");




//						lblfiltrosistema.setText("Usar Filtro Sistema");
						progressind.setVisible(false);
						progressindanalisar.setVisible(false);
						
						lblanalisar.setText("Análisar Registros ..");
					//	jtgbanalisararquivo.setSelected(false);
						
						if((invalidos.isEmpty() ) && (validos.isEmpty()) ){
							
							trace.setText("Análise com Erros: nos dois vazio");
							AlertaAbrirArquivoExecel(filllana);
							
						}else if((!validos.isEmpty() && (invalidos.isEmpty()))) {
							
							
							trace.setText("Análise sem Erros");
							AlertaSubstituirArquivoExecel(fileanalise);
							
						}
						
						else if((!validos.isEmpty() && (!invalidos.isEmpty()))) {
							
							
							trace.setText("Análise com Erros e acertoss");
							AlertaAbrirArquivoExecel(filllana);
							
						}
						else {
							
							trace.setText("doideiria");

							
							
						}
						
						
//						PreencherAjuda();
						qtdregistrolidos = registrosanalise.size();
						
					//	PararRotacionarCena();
						
						AtualizarQuadroRegistros();
						AtualizarQuadro();

					}




				});


				// Start the Task.
				new Thread(copyTaskregg).start();


			}




		}; 



		btanalise.setOnAction(eventtt);

		//     btconverterfilter.fire();




	}		
	



private void filtroCodigo(List<Registro> registrosanalise2) throws NullPointerException  {

		

 
//	SubstitutoAll();
	

registrosNotificar = new ArrayList<Registro>();


 //registroFiltrados.clear();


String cancela = "cancela";

String substitui = "substitui";

String nulo=null;

String branco="";


String idnotificacao = new String();

for (int i = 0; i < registrosanalise2.size(); i++) {

Registro registro = registrosanalise2.get(i);

idnotificacao = registro.getIdnotitificacao();

if((idnotificacao == null) || (idnotificacao.equals(branco) ) ) {
	
	registro.addErros("id notificacao nula");
	
//	if(registrosNotificar.contains(registro)){
//		
//		
//	}else {
//		
		registrosNotificar.add(registro);
//		
//		
//	}
	
	i++;
	registro = registrosanalise2.get(i);
	idnotificacao = registro.getIdnotitificacao();
	
}

for (int j = 0; j < registrosanalise2.size(); j++) {

	Registro regaux = registrosanalise2.get(j);

	String idnotificacaoaux = new String();

	idnotificacaoaux = regaux.getIdnotitificacao();
	
//	
	if((idnotificacaoaux == null) || (idnotificacaoaux.equals(branco) ) ) {
		
		//regaux.addErros("id notificacao nula");
		
//		if(registrosNotificar.contains(regaux)){
//			
//			
//		}else {
//			
//			registrosNotificar.add(regaux);
//			
//			
//		}
		//registrosNotificar.add(regaux);	
		j++;

		regaux = registrosanalise2.get(j);
		idnotificacaoaux = regaux.getIdnotitificacao();
	}
	

	if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(idnotificacao.toUpperCase())) {

		if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(cancela.toUpperCase())){



			if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(substitui.toUpperCase())){


				registro.setIsremove(true);
				regaux.setIssubstituto(true);
				regaux.setIsremove(false);



				registrosSubstituto.add(regaux);	
				qtdsubstituida = qtdsubstituida +1;
			
			}else {

				//							registro.setIsremove(true);
				regaux.setIsremove(true);
				//							registro.setIssubstituto(false);
				regaux.setIssubstituto(false);
				registro.setIsremove(true);




				registroscancela.add(regaux);
				qtdnotcancela  = qtdnotcancela +1;




			}




			if(registro.isIssubstituto() && registro.isIsremove()){

				registrosduplicidade.add(registro);

			}



		}


	} else {


		if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(idnotificacaoaux.toUpperCase())) {

			if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())){



				if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){


					regaux.setIsremove(true);
					registro.setIssubstituto(true);
					registro.setIsremove(false);



					registrosSubstituto.add(registro);	
					qtdsubstituida = qtdsubstituida +1;

				}else {

					registro.setIsremove(true);
					registro.setIssubstituto(false);
					regaux.setIsremove(true);



					registroscancela.add(registro);
					qtdnotcancela  = qtdnotcancela +1;

				}






				if(registro.isIssubstituto() && registro.isIsremove()){

					registrosduplicidade.add(registro);

				}



			}


		} else {

//			regaux.addErros("id notificacao nula");
//			registrosNotificar.add(regaux);



		}



	}

	



}






if((registro.getDescricao() == null || registro.getDescricao()=="" ||
		registro.getDescricao().toUpperCase().contains("SEM VIOLAÇÃO")||registro.getDescricao().toUpperCase().contains("EVENTO RESTAURADO")||registro.getDescricao().toUpperCase().contains("SEM NOTIFICAÇÃO")||registro.getDescricao().toUpperCase().contains("SEM RELATÓRIO")||registro.isIsremove()) && (!registro.isIssubstituto())){					


	if(registro.getDescricao().toUpperCase().contains("SUBSTITUI")){

		registro.setIssubstituto(true);
		registrosNotificar.add(registro);

	}else {

		registroFiltrados.add(registro);	
		qtdregistrosfiltrados = qtdregistrosfiltrados +1;


	}




}



else {

	registrosNotificar.add(registro);


}


}


SubstituitosSemNotificao(registrosSubstituto);

detentos.clear();
detentos.addAll(RegistroToDetento(registrosNotificar));
setRegistros(registrosNotificar);



System.out.println("Inicio da Remoção2");
//				trace.setText("Inicio da Remoção");

System.out.println("detentos" + detentos.size());

//removeduplicado();
//
//
//AtualizarQuadroRegistros();
//AtualizarQuadro();

AtualizarQuadroRegistros();
AtualizarQuadro();

		
		
	}



	private void CarregarObjetosStage() {

		arquivoLocalizado = stageManager.getArquivoLocalizado();
		arquivoConf = stageManager.getArquivoConf();
		arquivoFiltro = stageManager.getArquivoFilter();
		arquivos = stageManager.getArquivos();

		if(arquivoLocalizado == null || arquivoConf ==null|| arquivoFiltro ==null || arquivos.isEmpty()){


			VerificarConfFiltroInit();


		}else {


			//		VerificarConfFiltroInit();
			CriarDiretorio(arquivoLocalizado);
			RecuperarInfoConf(arquivoConf);
			RecuperarInfoFilter(arquivoFiltro);



		}

		//	System.out.println("Objetos do stage: " + arquivoLocalizado.getName());
		//	
		//	
		//	System.out.println("Objetos do stage: " + arquivoConf.getName());
		//	
		//	
		//	System.out.println("Objetos do stage: " + arquivoFiltro.getName());
		//	
		//	
		//	System.out.println("Objetos do stage: " + arquivos.toString());


	}



	private void carregarImagem() {



		image= new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);

		imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);

		imaget= new Image(getClass().getResourceAsStream("/images/icons8-time-machine-30.png"),36.0,36.0,true,true);
		image2= new Image(getClass().getResourceAsStream("/images/icons8-time-machine-30.png"),24.0,24.0,true,true);

		imagee2= new Image(getClass().getResourceAsStream("/images/icons8-delete-48.png"),36.0,36.0,true,true);
		image22= new Image(getClass().getResourceAsStream("/images/icons8-checked-checkbox-48.png"),36.0,36.0,true,true);



	}



	private void initDrawerHelp() {

		drawerhelp.setSidePane(anchorajuda);
		drawerhelp.setDefaultDrawerSize(270.0);

		HamburgerSlideCloseTransition burgertask = new HamburgerSlideCloseTransition(hamburgerhelp);
		burgertask.setRate(-1);
		hamburgerhelp.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)-> {
			burgertask.setRate(burgertask.getRate()*-1);
			burgertask.play();

			if(drawerhelp.isHidden())
			{
				//        	drawerhelp.setDefaultDrawerSize(300.0);
				drawerhelp.open();
			}else
				//        	drawerhelp.setDefaultDrawerSize(0.0);
				drawerhelp.close();


		});



	}



	public void initDrawer() {


		//		try {
		//					
		//			
		//					AnchorPane hboxaux = FXMLLoader.load(getClass().getResource("/fxml/drawer.fxml"));
		//					drawermenu.setSidePane(hbox);
		//			} catch (Exception e) {
		//		            Logger.getLogger(InicialController.class.getName()).log(Level.SEVERE, null, e);
		//				}


		drawer.setSidePane(vvb);

	//	System.out.println("drawer size : " + anchor.getWidth());
		if(vvb.getWidth() > 309.0){


		}else{



		}

		//				drawer.setDefaultDrawerSize(290.0);
		drawer.setDefaultDrawerSize(233.0);
		
		System.out.println(" drawer espaco inicial" + drawer.getDefaultDrawerSize());
		
		HamburgerNextArrowBasicTransition burgertask = new HamburgerNextArrowBasicTransition(hamburger);
		burgertask.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)-> {
			burgertask.setRate(burgertask.getRate()*-1);
			burgertask.play();

			if(drawer.isHidden())
			{
				drawer.open();
			}else
				drawer.close();


		});









	}

	@FXML
	private void RetirarMargin() {

		//drawerhelp.setDefaultDrawerSize(0.0);
		drawerhelp.setDefaultDrawerSize(300.0);	


	}

	@FXML
	private void ColocarMargin() {

		//drawerhelp.setDefaultDrawerSize(300.0);
		drawerhelp.setDefaultDrawerSize(300.0);	


	}




	//	private void Delay() {
	//
	//
	//		
	//		new Service<Integer>() {
	//	        @Override
	//	        public void start() {
	//	            super.start();
	//	            
	//	            
	//	            progressind.setVisible(true); 
	//
	//	        }
	//
	//	        @Override
	//	        protected Task<Integer> createTask() {
	//	            return new Task<Integer>() {
	//	                @Override
	//	                protected Integer call() throws Exception {         	  
	//	                        Thread.sleep(2000);
	//	                       
	//	                        return 1;
	//	                }
	//	            };
	//	        }
	//
	//	        @Override
	//	        protected void succeeded() {
	////	        opensplash();
	//	        AbrirAnimacao();
	//	     
	//	        progressind.setVisible(false);
	//			 
	//	        }
	//	        
	//	        
	//	        
	//	    }.start();
	//		
	//		
	//		
	//	    
	//	    
	//	    
	//	    
	//
	//	}
	//	 private boolean VerificarArquivoConfiguracao(File arquivoConf2) {
	//		
	//		 boolean exist = arquivoConf2.exists();
	//		 
	//		 
	//		 return exist;
	//
	//		 
	//	}


	private void carregarexcel(File file) {


		//		progressind.setVisible(true);
		//		 progressindligar.setVisible(true);

		if ((file != null) && (file.getAbsolutePath() != null)) {

			// trace.setText("Arquivo Válido...Aguarde Enquanto Carrega");

			String extensao = getFileExtension(file.getName());


			if((extensao.contains("xlsx")) && (extensao!= null)) {

				LeitorExcel leitor = new LeitorExcel(file.getAbsolutePath(),filtros);

				String path = leitor.getSAMPLE_XLSX_FILE_PATH();

				try {

					//            			progressind.setVisible(true);

					//            				progressind.setVisible(true);
					//            				 progressindligar.setVisible(true);

					registros = leitor.readRegistersFromExcelFile(path);
				
					registroFiltrados2 =	leitor.Filtrar();
					
					detentos = RegistroToDetento(registros);
//					qtdregistrolidos = registros.size();

					
					
				//	qtdregistrolidos = leitor.getRegistros().size();
				//	qtdregistrofinal = leitor.getRegistrosfinal().size();
					
					
					qtdregistrolidos= registros.size();
					
					qtdregistrofinal = registroFiltrados2.size();
					
					//            			 filter();

					// System.out.println("Detentos : " + detentos);
					// trace.setText("Registros Convertidos :" + detentos.size());

					//            			 unificar = new UnificarDetento(registros);
					//            			 unificar.filter();

					//	 System.out.println("Detentos class : " + unificar.getDetentoFinal().toString());


					AtualizarQuadroRegistros();
					AtualizarQuadro();

					//            			 filter();

					//progressind.setVisible(false);



				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("falha ao ler registro : " + e);
					trace.textProperty().unbind();
					trace.setText("Erro" + e);
					progressind.setVisible(false);
					progressindligar.setVisible(false);



				}


			}else {



			}



		}else {

			trace.setText("Carregue um Arquivo valido..");


		}






	}



	private void filter() throws NullPointerException {



					   progressind.setVisible(true);
		//			   progressindligar.setVisible(true);


		SubstitutoAll();

		// trace.setText("Filtrando Violações");
		registrosNotificar = new ArrayList<Registro>();
		//			  registroscancela.clear();
		//			  registrosSubstituto.clear();
		//			  detentosviolavazia.clear();
				//	  registroFiltrados.clear();


		String cancela = "cancela";

		String substitui = "substitui";
		
		String nulo=null;

		String branco="";

		//				qtdsubstituida = 0;
		//				qtdnotcancela  = 0;


		//  List<Registro> regs = new ArrayList<Registro>();

		String idnotificacao = new String();

		for (int i = 0; i < getRegistros().size(); i++) {

			Registro registro = getRegistros().get(i);

			idnotificacao = registro.getIdnotitificacao();
		
			if((idnotificacao== null) || (idnotificacao.equals(branco) ) ) {
				
				registro.addErros("id notificacao nula");
				
//				if(registrosNotificar.contains(registro)){
//					
//					
//				}else {
//					
					registrosNotificar.add(registro);
//					
//					
//				}
				//registrosNotificar.add(registro);
			
				
				
				i++;
				registro = getRegistros().get(i);
				idnotificacao = registro.getIdnotitificacao();
				
			}
			

			for (int j = 0; j < getRegistros().size(); j++) {

				Registro regaux = getRegistros().get(j);

				String idnotificacaoaux = new String();

				idnotificacaoaux = regaux.getIdnotitificacao();
				
				
				if((idnotificacaoaux== null) || (idnotificacaoaux.equals(branco) ) ) {
					
				//	regaux.addErros("id notificacao nula");
					
//					if(registrosNotificar.contains(regaux)){
//						
//						
//					}else {
//						
//						registrosNotificar.add(regaux);
//						
//						
//					}
					
					//registrosNotificar.add(regaux);	
					
					
					
					j++;

					regaux = getRegistros().get(j);
					idnotificacaoaux = regaux.getIdnotitificacao();
				}

				if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(idnotificacao.toUpperCase())) {

					if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(cancela.toUpperCase())){

						//	registro.setIsremove(true);


						if(regaux.getDescricao() != null && regaux.getDescricao()!="" && regaux.getDescricao().toUpperCase().contains(substitui.toUpperCase())){


							//							registro.setIssubstituto(false);
							registro.setIsremove(true);
							regaux.setIssubstituto(true);
							regaux.setIsremove(false);


							//							if(registrosSubstituto.contains(regaux)){



							//							}else {

							registrosSubstituto.add(regaux);	
							qtdsubstituida = qtdsubstituida +1;
							//							}

							//							if(registrosNotificar.contains(regaux)){
							//								
							//								
							//								
							//							}else {
							//								
							//								registrosNotificar.add(regaux);	
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



							//							registrosNotificar.add(regaux);
							//							registroFiltrados.add(registro);

							//							qtdregistrosfiltrados = qtdregistrosfiltrados +1;

						}else {

							//							registro.setIsremove(true);
							regaux.setIsremove(true);
							//							registro.setIssubstituto(false);
							regaux.setIssubstituto(false);
							registro.setIsremove(true);




							//							if(registroscancela.contains(regaux)){



							//							}else {

							registroscancela.add(regaux);
							qtdnotcancela  = qtdnotcancela +1;




							//							if(registroFiltrados.contains(regaux)){
							//								
							//								
							//								
							//							}else {
							//								
							//								registroFiltrados.add(regaux);	
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
							//							registroFiltrados.add(regaux);
							//							
							//							qtdregistrosfiltrados = qtdregistrosfiltrados +2;


						}




						if(registro.isIssubstituto() && registro.isIsremove()){

							registrosduplicidade.add(registro);

						}



					}


				} else {


					if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(idnotificacaoaux.toUpperCase())) {

						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())){

							//	registro.setIsremove(true);


							if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){


								//								registro.setIssubstituto(false);
								regaux.setIsremove(true);
								registro.setIssubstituto(true);
								registro.setIsremove(false);


								//								if(registrosSubstituto.contains(registro)){



								//								}else {

								registrosSubstituto.add(registro);	
								qtdsubstituida = qtdsubstituida +1;
								//								}

								//								if(registrosNotificar.contains(registro)){
								//									
								//									
								//									
								//								}else {
								//									
								//									registrosNotificar.add(registro);	
								////									qtdsubstituida = qtdsubstituida +1;
								//								}

								//								if(registroFiltrados.contains(registro)){
								//									
								//									
								//									
								//								}else {
								//									
								//									registroFiltrados.add(registro);	
								//									qtdregistrosfiltrados = qtdregistrosfiltrados +1;
								//								}



								//								registrosNotificar.add(registro);
								//								registroFiltrados.add(registro);

								//								qtdregistrosfiltrados = qtdregistrosfiltrados +1;

							}else {

								//								registro.setIsremove(true);
								registro.setIsremove(true);
								//								registro.setIssubstituto(false);
								registro.setIssubstituto(false);
								regaux.setIsremove(true);




								//								if(registroscancela.contains(registro)){



								//								}else {

								registroscancela.add(registro);
								qtdnotcancela  = qtdnotcancela +1;

							}


							//								if(registroFiltrados.contains(registro)){
							//									
							//									
							//									
							//								}else {
							//									
							//									registroFiltrados.add(registro);	
							//									qtdregistrosfiltrados = qtdregistrosfiltrados +1;
							//								}
							//								
							//								if(registroFiltrados.contains(registro)){
							//									
							//									
							//									
							//								}else {
							//									
							//									registroFiltrados.add(registro);	
							//									qtdregistrosfiltrados = qtdregistrosfiltrados +1;
							//								}


							//registroscancela.add(registro);

							//								registroFiltrados.add(registro);
							//								registroFiltrados.add(registro);
							//								
							//								qtdregistrosfiltrados = qtdregistrosfiltrados +2;







							if(registro.isIssubstituto() && registro.isIsremove()){

								registrosduplicidade.add(registro);

							}



						}


					} else {


//						regaux.addErros("id notificacao nula");
//						registrosNotificar.add(regaux);



					}



				}



				//						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(idnotificacaoaux.toUpperCase())) {
				//						
				//						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())){
				//						
				//							regaux.setIsremove(true);
				//			
				//						
				//						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
				//							
				//							
				////							registro.setIssubstituto(false);
				//							registro.setIssubstituto(true);
				//							registro.setIsremove(false);
				//							
				//							qtdsubstituida = qtdsubstituida +1;
				//							
				//							registrosSubstituto.add(registro);
				//						
				//						}else {
				//							
				////							registro.setIsremove(true);
				//							registro.setIsremove(true);
				////							registro.setIssubstituto(false);
				//							registro.setIssubstituto(false);
				//							
				//							qtdnotcancela  = qtdnotcancela +1;
				//							
				//							registroscancela.add(registro);
				//							//registroscancela.add(registro);
				//							
				//							
				//						}
				//						
				//						
				//						
				//						
				//						if(regaux.isIssubstituto() && regaux.isIsremove()){
				//							
				//							registrosduplicidade.add(regaux);
				//							
				//						}
				//					
				//						}
				//						
				//						
				//						}else {
				//							
				//							
				//							
				//							
				//						}

			}




			//				if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(cancela.toUpperCase())) {
			//					
			//					//	registro.setIsremove(true);
			//			
			//						
			//						if(registro.getDescricao() != null && registro.getDescricao()!="" && registro.getDescricao().toUpperCase().contains(substitui.toUpperCase())){
			//							
			//							
			////							registro.setIssubstituto(false);
			//						//	registro.setIsremove(true);
			//							registro.setIssubstituto(true);
			//							registro.setIsremove(false);
			//							
			//							qtdsubstituida = qtdsubstituida +1;
			//							
			//							registrosSubstituto.add(registro);
			//							
			//							registrosNotificar.add(registro);
			////							registroFiltrados.add(registro);
			//							
			////							qtdregistrosfiltrados = qtdregistrosfiltrados +1;
			//						
			//						}else {
			//							
			////							registro.setIsremove(true);
			//							registro.setIsremove(true);
			////							registro.setIssubstituto(false);
			////							regaux.setIssubstituto(false);
			////							registro.setIsremove(true);
			//							
			//							qtdnotcancela  = qtdnotcancela +1;
			//							
			//							registroscancela.add(registro);
			//							//registroscancela.add(registro);
			//							
			////							registroFiltrados.add(registro);
			////							registroFiltrados.add(regaux);
			////							
			////							qtdregistrosfiltrados = qtdregistrosfiltrados +2;
			//							
			//							
			//						}
			//						
			//						
			//						
			//						
			//						if(registro.isIssubstituto() && registro.isIsremove()){
			//							
			//							registrosduplicidade.add(registro);
			//							
			//						}
			//						
			//						
			//				}	


			if((registro.getDescricao() == null || registro.getDescricao()=="" ||
					registro.getDescricao().toUpperCase().contains("SEM VIOLAÇÃO")||registro.getDescricao().toUpperCase().contains("EVENTO RESTAURADO")||registro.getDescricao().toUpperCase().contains("SEM NOTIFICAÇÃO")||registro.getDescricao().toUpperCase().contains("SEM RELATÓRIO")||registro.isIsremove()) && (!registro.isIssubstituto())){					


				//					registroFiltrados.add(registro);

				//					
				//					if(registroFiltrados.contains(registro)){
				//						

				//						
				//					}else {
				if(registro.getDescricao().toUpperCase().contains("SUBSTITUI")){

					registro.setIssubstituto(true);
					registrosNotificar.add(registro);

				}else {


					registroFiltrados.add(registro);	
					qtdregistrosfiltrados = qtdregistrosfiltrados +1;

				}


				//					}




				//getRegistros().remove(registro);

				//registroFiltrados.add(registro);

				//					registroFiltrados.add(registro);

			}

			//				if(registro.getNotificacao().contains("[SEM VIOLAÇÃO")&&(registro.getNotificacao().contains("cancela"))){
			//					
			//					
			//					
			//					
			//				}

			else {

				//					if(registrosNotificar.contains(registro)){



				//					}else {


				registrosNotificar.add(registro);

				//					}

			}

			//				registrosNotificar.add(registro);

		}


		//	registroFiltrados.addAll(registrosNotificar);

		SubstituitosSemNotificao(registrosSubstituto);

		detentos.clear();
		detentos.addAll(RegistroToDetento(registrosNotificar));
		setRegistros(registrosNotificar);


		System.out.println("Inicio da Remoção");
		//				trace.setText("Inicio da Remoção");

		System.out.println("detentos" + detentos.size());

		removeduplicado();


		AtualizarQuadroRegistros();
		AtualizarQuadro();


	} 


	private void SubstituitosSemNotificao(List<Registro> registrosSubstituto2) {


		registrosSubstitutosemNoti = new ArrayList<Registro>();

		for (Registro reg : registrosSubstitutosAll){

			if(registrosSubstituto2.contains(reg)){



			}else {

				registrosSubstitutosemNoti.add(reg);


			}


		}



		//			
		//			
		//			
		//			return registrosSubstitutosemNoti;
	}



	private void SubstitutoAll(){

		registrosSubstitutosAll = new ArrayList<Registro>();

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

		//				progressind.setVisible(true);
		//				progressindligar.setVisible(true);

		List<Detento> detfinal = RemoverDuplicidade();


		detentos.clear();
		detentos.addAll(detfinal);

		System.out.println("fim da Remoção");
		//				trace.setText("fim da Remoção");

		System.out.println("detentos fim" + detentos.size());

		//				trace.setText("detentos fim" + detentos.size());



		AtualizarQuadroRegistros();
		AtualizarQuadro();


	}






	private List<Detento> RemoverDuplicidade(){


		//				progressind.setVisible(true);
		//				 progressindligar.setVisible(true);

		detentoFinal = new ArrayList<Detento>();
		detentosremovidos = new ArrayList<Detento>();
		detentosrepetidos= new ArrayList<Detento>();

		int count = 0;
		int countv = 0;
		qtdviolavazia =0;
		qtddetentorepetido=0;


		//percorrer os detentos e comparar se existe
		for (int j = 0; j < detentos.size(); j++) {

			Detento detentoCompara = detentos.get(j);

			List<Violacao> violacoesCompara = detentoCompara.getViolacoes();

			if(ExisteDetento(detentoCompara)){


				violacoesCompara = RecuperarDetentoRepetido(detentoCompara);
				List<Violacao> vios = removeViolacoesInvalidas(violacoesCompara);

				count =count+1;

				if(vios.isEmpty() ){

					//detentosremovidos.add(detentoCompara);
					detentoFinal.remove(detentoCompara);
					detentosremovidos.add(detentoCompara);


				}else {


					detentoCompara.setViolacoes(vios);


				}

				//						detentosrepetidos.add(detentoCompara);

				//						detentosremovidos.add(detentoCompara);

				System.out.println("entrou duplicidade: " + count);
				qtddetentorepetido = count;


			}else {


				detentoCompara.setViolacoes(removeViolacoesInvalidas(violacoesCompara));

			}



			System.out.println("fim detento" + j);


			if(detentoCompara.getViolacoes().isEmpty()){

				countv=countv+1;

				System.out.println("dettento viola vazia :" + countv);

				System.out.println("dettento viola vazia : " + detentoCompara.getProntuario());

				detentosviolavazia.add(detentoCompara);


				qtdviolavazia = qtdviolavazia +1 ;



				//detentosremovidos.add(detentoCompara);

				//						detentoFinal.remove(detentoCompara);

				//	countv=countv+1;

			} else {

				detentoFinal.add(detentoCompara);

			}



		}





		if(jcbvalidarbefore.isSelected()){


			//                	DelayValida();

			ValidaDetentoBefore();


			//        		    Platform.runLater(() ->
			//        		    {
			//        		    	
			//        		    	DelayValida();
			//        
			//        		   
			//        		    });



		}

		//                if(!filtros.isEmpty()) {
		//               	 
		//                    progressind.setVisible(true);
		//                    progressindligar.setVisible(true);
		//                    
		//                //    carregarexcel(arquivoLocalizado);
		//                    
		//                    gerenciafiltro = new GerenciadorFiltro(filtros, registrosNotificar, detentoFinal);
		//                    
		//                    setRegistros(gerenciafiltro.getRegistros());
		//                    
		//                    setDetentos(gerenciafiltro.getDetentos());
		//                    
		////                    filter();
		//                    
		//                	lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
		//                	jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
		//                    
		////                	filter();
		//                    
		//                    System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
		//                    System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());
		//
		//                    
		//                    System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
		//                    System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
		//
		//                    System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
		//                    System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
		//                
		//                    System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
		//                    System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
		//                    
		//                    
		//                    }else {
		//                   	 
		//                   	                   	 
		//                   	 
		//                    }





		//   qtddetentorepetido = count;




		System.out.println("qtd detentos : " + detentos.size());
		System.out.println("qtd reg : " + registros.size());
		System.out.println("qtd final det fimal : " + detentoFinal.size());
		AtualizarQuadroRegistros();
		AtualizarQuadro();


		return detentoFinal;





	}


	//private void ChamarFilter() {
	//	
	//	
	//	boolean jaexecutou
	//
	//    if(!filtros.isEmpty()) {
	//   	 
	//        progressind.setVisible(true);
	//        progressindligar.setVisible(true);
	//        
	//        gerenciafiltro = new GerenciadorFiltro(filtros, registrosNotificar, detentoFinal);
	//        
	//        setRegistros(gerenciafiltro.getRegistros());
	//        
	//        setDetentos(gerenciafiltro.getDetentos());
	//        
	//    	lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
	//    	jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
	//        
	//    	filter();
	//        
	//        System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
	//        System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());
	//
	//        
	//        System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
	//        System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
	//
	//        System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
	//        System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
	//    
	//        System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
	//        System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
	//        
	//        
	//        }
	//	
	//	
	//	
	//	
	//	
	//	
	//		}



	private void ValidaDetentoBefore() {

		//	boolean valido = false;

		List<Detento> detnews = new ArrayList<Detento>();

		List<Detento> deterror = new ArrayList<Detento>();

		List<Detento> detall = new ArrayList<Detento>();




		for(Detento deten: detentoFinal){


			FormatadorEmail(deten);

			PreencherVaraComCaracteristica(deten);

			validodet = validaDetentoEmail(deten);


			//		validodet = DelayValida(deten);


			if(validodet){

				detnews.add(deten);


			}else {

				deten.setStatusenvio("ERROR");
				deterror.add(deten);

			}

			detall.add(deten);


			if(jcbvisualizarvalidacao.isSelected()){
				PreencheDetentoValidacao(deten);

			}

			//		    Platform.runLater(() ->
			//		    {
			//		    	
			//
			//		    	PreencherTextFields(deten);
			//
			//		   
			//		    });


			//	    Platform.runLater(() ->
			//	    {
			//
			//	    	PreencheDetentoValidacao(deten);
			//	    	AtualizarQuadro();
			//	    	//LimpaValidacao();
			//	   
			//	    });



		}

		detentoFinal.clear();

		detentoFinal.addAll(detall);

		detentoErro.clear();

		detentoErro.addAll(deterror);

		AtualizarQuadro();

		//	CalcularQtdPendentescont();


		System.out.println("qtd detentos validos before: " + detentoFinal.size());
		//	System.out.println("qtd reg : " + registros.size());
		System.out.println("qtd Error det before : " + detentoErro.size());







	}

	//
	//private void PreencherTextFields(Detento deten) {
	//
	//	
	//	
	//	jtxfvalidanome.setText(deten.getNome());
	//	jtxfvalidavara.setText(deten.getVara());
	//	jtxfvalidaprocesso.setText(deten.getProcesso());
	//	jtxfvalidavep.setText(deten.getVep());
	//	jtxfvalidaemail.setText(deten.getEmail());
	//	jtxtfenvio.setText("");
	//	
	//	
	//	
	//	
	//	
	//}



	public boolean DelayValida(){

		//	boolean valido = false;

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

						updateMessage("Validando Detentos");

						//                	FormatadorEmail(det);
						//                	
						//                	PreencherVaraComCaracteristica(det);
						//                	
						//                	validodet = validaDetentoEmail(det);


						//                	Thread.sleep(2000);

						//                	FormatadorEmail(deten);
						//                	
						//                	PreencherVaraComCaracteristica(deten);
						//                	
						//                	validodet = validaDetentoEmail(deten);


						ValidaDetentoBefore();
						Thread.sleep(5000);

						updateMessage("Fim Validação.");

						return null;
					}
				};
			}

			@Override
			protected void succeeded() {



				progressind.setVisible(false);



			}



		}.start();





		return validodet;
	}

	//		 private List<Notificacao> removeNotificacaoInvalidas(List<Violacao> vios) {
	//
	//			 List<Notificacao> noti = new ArrayList<Notificacao>();
	//			 List<Violacao> vio = vios;
	//			 
	//			 for (int i = 0; i < vios.size(); i++) {
	//					
	//					Violacao v = vios.get(i);
	//					
	//					
	//					if(v.getNotificacoes().isEmpty()){
	//						
	//						
	//						vio.remove(v);
	//						
	//					}else {
	//						
	//						
	//						noti.addAll(v.getNotificacoes());
	//						v.set
	//						
	//					}
	//					
	//					
	//					
	////					for (int j = 0; j < v.getNotificacoes().size(); j++) {
	////						
	////						com.tecsoluction.robo.entidade.Notificacao nott = v.getNotificacoes().get(j);
	////							
	////						
	////					
	////					
	////					}
	//					
	//					
	//					
	//			 }
	//			 
	//			 
	//			 
	//			 
	//			 
	//			 
	//			 return noti;
	//		}

	private List<Violacao> RecuperarDetentoRepetido(Detento detento){

		List<Violacao> repetidos = new ArrayList<Violacao>();

		for (int i = 0; i < getDetentoFinal().size(); i++) {

			Detento detentoaux = getDetentoFinal().get(i);

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


					//						else {
						//							
					//							
					//							
					//							vios.remove(v);
					//							
					//							System.out.println("entrou no if principal: remove violacao 2" + v.getId());	
					//
					//							
					//						}



					if (v.getNotificacoes().isEmpty() || v.isIsremove()) {


						System.out.println("entrou no if principal:remove violacao, notificaacao vazia" + v.getId());	


						vios.remove(v);

					}





				}

			}





		}

		return vios;

	}


	private List<Detento> RegistroToDetento(List<Registro> registros2) {


		List<Detento> detentos = new ArrayList<Detento>();	


		for (int i = 0; i < registros2.size(); i++) {

			Registro reg =	registros2.get(i);		
			Detento det = new Detento(reg);
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


	private boolean ExisteDetento(Detento det) {

		boolean existe = false;
		//trace.setText("Verificando existencia de Detento");



		for (int i = 0; i < getDetentoFinal().size(); i++) {

			Detento detento = getDetentoFinal().get(i);

			if(detento.getProntuario().equals(det.getProntuario())){



				existe = true;
				return existe;

			}




		}




		return existe;
	}


	static String getFileExtension(String filename) {
		if (filename.contains("."))
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}



	public void procurarArquivos() {


		//	progressind.setVisible(true);
		//	progressindligar.setVisible(true);

		if(nomeArquivo !=null && nomeArquivo!=""){

			String path = System.getProperty("user.home");
			String pathfinal = path + "\\" + "Desktop";

			System.out.println("path  :" + pathfinal);
			//	trace.setText("path :" + pathfinal);

			arquivos = buscarArquivoPorNome(nomeArquivo, pathfinal);


		}else {


			ErrorAlert("Preencha o Nome do Arquivo na Configuração!");
			progressind.setVisible(false);
			progressindligar.setVisible(false);


		}



	}



	public void ErrorAlert(String msg){

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Foda");
		alert.setHeaderText("Execução com Erro.");
		alert.setContentText("Erro : " + msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/fodaa.png").toString()));
		jtgbligar.setSelected(false);
		alert.showAndWait();
	}


	public void InfoAlert(String msg){

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Foda");
		alert.setHeaderText("Aviso de Possiveis Erros.");
		alert.setContentText("Aviso: "+msg);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/fodaa.png").toString()));
		alert.showAndWait();
	}


	private void CarregarToogles(){

		jtgbligar.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){

					progressind.setVisible(true);
					progressindligar.setVisible(true);
					lblligar.setText("Localizar Arquivo ON");
					lblligar.setTextFill(Color.TURQUOISE);
				//	lblfiltrosaplicados2.setText("0");





					//		    	registrosSubstitutosAll.clear();
					//		    	registrosSubstitutosemNoti.clear();
					//		    	registroscancela.clear();

					//				qtdsubstituida = 0;
					//				qtdnotcancela  = 0;
					//				qtdviolavazia=0;
					//				qtddetentorepetido=0;
					//				qtdregistrosfiltrados=0;
					//				registroscancela.clear();
					//				registrosSubstituto.clear();
					//				registroFiltrados.clear();

					LimpaValidacao();
					//AcaoBtLigar();

					btimportar.fire();
					
					AtualizarQuadroRegistros();
					InicializarListas();


				}

				if(oldValue){

					progressindligar.setVisible(false);
					progressind.setVisible(false);
					lblligar.setText("Localizar Arquivo");
					lblligar.setTextFill(Color.WHITE);
					//jtgbligar.setOnAction(null);
					lblretornoligar.setVisible(false);
					
					
					if(copyTaskdet != null && copyTaskdet.getState().equals(State.RUNNING)){


						copyTaskdet.cancel();
					}

					if(copyTaskreg != null && copyTaskreg.getState().equals(State.RUNNING)){


						copyTaskreg.cancel();
					}

					if(copyTask != null && copyTask.getState().equals(State.RUNNING)){


						copyTask.cancel();
					}

					//				btconverter.setOnAction(null);
					//				btimportar.setOnAction(null);




				}



			}
		});



		jtgbcriartarefa.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){

					progressindtarefa.setVisible(true);
					lblcriar.setText("Enviar Email Lote ON");
					lblcriar.setTextFill(Color.TURQUOISE);

					//				jtgexibirvalidacao.setDisable(true);


					try {
						enviarall();
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}




				}

				if(oldValue){

					progressindtarefa.setVisible(false);
					progressind.setVisible(false);
					lblcriar.setText("Enviar Email Lote");
					lblcriar.setTextFill(Color.WHITE);

					lblretornotarefa.setVisible(false);



					servicolote.reset();
					//stop();


				}


			}


		});



		jtgbteste.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){

					progressindteste.setVisible(true);
					//	trace.setText("Execução Automática ON");
					lblteste.setText("Execução Automática ON");
					lblteste.setTextFill(Color.TURQUOISE);
					isLocalizarArquivo=true;
					isautomatico = true;
					//		     	isservice=true;

					TarefaBanco();




				}

				if(oldValue){


					progressindteste.setVisible(false);
					//	    		progressindtarefa.setVisible(false);
					progressind.setVisible(false);
					lblteste.setText("Execução Automática");
					lblteste.setTextFill(Color.WHITE);
					isautomatico = false;
					//	trace.setText("Execução Automática OFF");
					//	fiveSecondsWonder.stop();
					jtgbteste.setOnAction(null);
					//	jtgbcriartarefa.setSelected(false);
					//	    		fiveSecondsWonder.getKeyFrames().clear();
					//	    		fiveSecondsWonder = null;
					//	    		ControleServiceParar();
					//	ControleTimelineParar();
					//	    		servicolote.cancel();
					//	    		isservice=false;
					//	    		isLocalizarArquivo=false;
					//	    		jtgbligar.setSelected(false);
					lblretornoautomatico.setVisible(false);
					//	    		  PararRotacionarCena();




				}


			}
		});


		jtgbtmonitorarrede.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){


					//				TimePing();
					lblrede.setText("EXECUTANDO");
					imgvnet.setDisable(false);
					lblnet.setDisable(false);


				}

				if(oldValue){

					if(timeping != null) {

						timeping.stop();

					}

					lblrede.setText("AGUARDANDO");
					imgvnet.setDisable(true);
					lblnet.setDisable(true);


				}


			}
		});




		jtgexibirvalidacao.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){


					vboxvalidacao.setVisible(true);
					//				jtgexibirvalidacao.setDisable(true);
					LimpaValidacao();



				}

				if(oldValue){


					vboxvalidacao.setVisible(false);
					//				jtgexibirvalidacao.setDisable(false);
					LimpaValidacao();

				}



			}
		});



		jtgbfiltrosistema.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){

					progressindfiltro.setVisible(true);
					progressind.setVisible(true);
					lblfiltrosistema.setText("Filtrando Registros");
					lblfiltrosistema.setTextFill(Color.TURQUOISE);
					
					
					ExecutarFiltro();

//					btconverterfilter.fire();

					//		    	ExecutarFiltroSistema();

					//PosExecute();

				//	AtualizarQuadroRegistros();
				//	InicializarListas();

				}

				if(oldValue){

					progressindfiltro.setVisible(false);
					progressind.setVisible(false);
					lblfiltrosistema.setText("Usar Filtro Sistema");
					lblfiltrosistema.setTextFill(Color.WHITE);




				}


			}



		});
		
		
		jtgbanalisararquivo.selectedProperty().addListener(new ChangeListener<Boolean>() {


			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if(newValue){

					progressindanalisar.setVisible(true);
					progressind.setVisible(true);
					lblanalisar.setText("Analisando . . .");
					lblanalisar.setTextFill(Color.TURQUOISE);
//
//					btconverterfilter.fire();

					//		    	ExecutarFiltroSistema();

					//PosExecute();
					
//					AnalisarArquivos();
					
					registroFiltrados.clear();
					
					AnalisarArquivos();
					
//					 try {
//						 
//						 filtroCodigo(registrosanalise);
//
//
//						} catch (NullPointerException eX) {
//							// TODO Auto-generated catch block
//							eX.printStackTrace();
//							trace.textProperty().unbind();
//							trace.setText("Erros nO FILTRO" + eX);
//							jtgbanalisararquivo.setSelected(false);
//							ErrorAlert("Erros na FILTRO");
//							
//
//						}
//					
//					
//						AnaliseRegistro(registrosNotificar);	
//					
//					
//					
////					registroFiltrados.clear();
//					
//					btanalise.fire();
					AtualizarQuadroRegistros();
//					InicializarListas();


				}

				if(oldValue){

					progressindanalisar.setVisible(false);
					progressind.setVisible(false);
					lblanalisar.setText("Analisar Arquivo");
					lblanalisar.setTextFill(Color.WHITE);




				}


			}

			private void AnalisarArquivos() {

				 try {
				 
				 filtroCodigo(registrosanalise);
	
	
				} catch (NullPointerException eX) {
					// TODO Auto-generated catch block
					eX.printStackTrace();
					trace.textProperty().unbind();
					trace.setText("Erros nO FILTRO" + eX);
					jtgbanalisararquivo.setSelected(false);
					ErrorAlert("Erros na FILTRO");
					
	
				}
			
			
				AnaliseRegistro(registrosNotificar);
				
//				PreAnalise();
			
			
			
//			registroFiltrados.clear();
			
			btanalise.fire();
				
				
				
			}



		});


	}


	protected void ExecutarFiltro() {

		PosExecute();

		
		btconverterfilter.fire();
		
	}



//	protected void AnalisarArquivos() {
//
//		 try {
//			 
//			 filtroCodigo(registrosanalise);
//
//
//			} catch (NullPointerException eX) {
//				// TODO Auto-generated catch block
//				eX.printStackTrace();
//				trace.textProperty().unbind();
//				trace.setText("Erros nO FILTRO" + eX);
//				jtgbanalisararquivo.setSelected(false);
//				ErrorAlert("Erros na FILTRO");
//				
//
//			}
//		
//		
//			AnaliseRegistro(registrosNotificar);
//			
//			PreAnalise();
//		
//		
//		
////		registroFiltrados.clear();
//		
//		btanalise.fire();
//		
//		
//	}



	public void ExecutarFiltroSistema() {


		EventHandler<ActionEvent> eventtt = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 


				progressind.setVisible(true);
				progressindfiltro.setVisible(true);

				lblfiltrosistema.setText("Filtrando Detentos Sistema");



//				PosExecute();




				copyTaskdetfiltro = new CopyTaskDet(getDetentoFinal());

				System.out.println("detentos :" + getDetentoFinal().size());


				progressind.progressProperty().unbind();

				progressind.progressProperty().bind(copyTaskdetfiltro.progressProperty());



				trace.textProperty().unbind();

				trace.textProperty().bind(copyTaskdetfiltro.messageProperty());






				copyTaskdetfiltro.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
						new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent t) {
						List<Detento> copied = copyTaskdetfiltro.getValue();



						trace.textProperty().unbind();
						trace.textProperty().bind(copyTaskdetfiltro.messageProperty());

						trace.textProperty().unbind();
						trace.setText("Filtrados: " + copied.size() + " Detentos");




						lblfiltrosistema.setText("Usar Filtro Sistema");
						progressind.setVisible(false);
						progressindfiltro.setVisible(false);
						PreencherAjuda();
						
						AtualizarQuadro();
						AtualizarQuadroRegistros();


					}




				});


				// Start the Task.
				new Thread(copyTaskdetfiltro).start();


			}




		}; 



		btconverterfilter.setOnAction(eventtt);

		//     btconverterfilter.fire();




	}



	public void AcaoBtLigar() {



		EventoProcurarArquivo();

		//	btimportar.fire();
		AtualizarQuadroRegistros();
		InicializarListas();


	}

	public void RestartarListas() {




		detentoEnviado = null;

		detentosREnviado = null;
		//
		//	  detentoErro = new ArrayList<Detento>();
		//	  detentosPendetes = new ArrayList<Detento>();
		//	  detentoFinal = new ArrayList<Detento>();

		//	   
		//	 registroFiltrados = new ArrayList<Registro>();
		//	 
		//	 registrosSubstituto= new ArrayList<Registro>();
		////	   
		//	 violacaoesRemovidas = new ArrayList<Violacao>();
		////	   
		//	 detentosremovidos = new ArrayList<Detento>();
		////	 
		//	 detentosrepetidos = new ArrayList<Detento>();
		//	 
		//	 registroscancela = new ArrayList<Registro>();
		//	 
		//	 registrosduplicidade = new ArrayList<Registro>();
		//	 
		//	 detentosviolavazia = new ArrayList<Detento>();


		registroFiltrados = null;

		registrosSubstituto= null;
		//	   
		violacaoesRemovidas =null;
		//	   
		detentosremovidos = null;
		//	 
		detentosrepetidos = null;

		registroscancela = null;

		registrosduplicidade  = null;

		detentosviolavazia = null;

		registrosSubstitutosAll = null;
		registrosSubstitutosemNoti=null;

		registroFiltrados=null;

		registrosNotificar=null;




		//	 registrosNotificar = new ArrayList<Registro>();


		//		qtdsubstituida = 0;
		//		qtdnotcancela  = 0;
		//		qtdviolavazia=0;
		//		qtdregistrosfiltrados=0;
		//		qtddetentorepetido=0;
		//		registroscancela.clear();
		//		registrosSubstituto.clear();


	}


	public  void SalvarListas() {


		criarexcel();
		
		SalvarListasErro();
		
//		try {
//			Escrever();
//		} catch (InvalidFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	
	public  void SalvarListasErro() {


//		criarexcel();
		
//		try {
			Escrever();
//		} catch (InvalidFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}


	public void ControleTimeline() {


		if (fiveSecondsWonder.getStatus() == Animation.Status.PAUSED ) {

			fiveSecondsWonder.play();
		}
		else if(fiveSecondsWonder.getStatus() == Animation.Status.RUNNING) {
			fiveSecondsWonder.pause();
		}

		else {
			fiveSecondsWonder.play();
		}



	}


	public void ControleTimelineParar() {


		if (fiveSecondsWonder !=null &&  fiveSecondsWonder.getStatus()== Animation.Status.RUNNING ) {

			fiveSecondsWonder.stop();
		} 

	}


	//public void ControleServiceParar() {
	//	
	//	
	//    if ((servicolote.getState().RUNNING != null )) {
	//    	
	//    	servicolote.cancel();
	//    }
	//    else if(servicolote.getState().CANCELLED != null) {
	//    	//servicolote.reset();
	//    }
	//    
	//    else if(servicolote.getState().FAILED != null) {
	//    	servicolote.cancel();
	//    }
	//    
	//    else if(servicolote.getState().READY != null) {
	//    	servicolote.cancel();
	//    }
	//    else {
	//    	
	//    	servicolote.cancel();
	//    	
	//    	
	//    }
	//
	//
	//	
	//}


	//public void ControleServiceIniciar() {
	//	
	//	
	//    if ((servicolote.getState() == State.RUNNING )) {
	//    	
	//    //	servicolote.cancel();
	//    }
	//    
	//    else if(servicolote.getState() == State.CANCELLED) {
	//    	
	//    	servicolote.reset();
	//    	servicolote.restart();
	//    }
	//    
	//    else if(servicolote.getState() == State.FAILED) {
	//    	servicolote.reset();
	//    	servicolote.restart();
	//    }
	//    
	//    else if(servicolote.getState() == State.READY) {
	//    	
	//    	servicolote.start();
	//    }
	//    
	//    else if(servicolote.getState() == State.SUCCEEDED) {
	//    	
	//    	servicolote.restart();
	//    }
	//
	//	
	//}


	//private File ProcurarArquivo(String nome) {
	//	
	//	progressind.setVisible(true);
	//
	//	boolean diretorioTem = false;
	//	
	//	File diretorio = new File("C:/");
	//	
	//	File arquivo = null;
	//	
	//	File[] listFiles = diretorio.listFiles(new FileFilter() {
	//		
	//		
	//		public boolean accept(File pathname) {
	//			return pathname.getName().contains(nome); // apenas arquivos que começam com a letra "a"
	//		}
	//	});
	//
	//	
	//	for(File f : listFiles) {
	//		
	//		System.out.println(f.getName());
	//		
	//		if(f.getName().equals(nome)){
	//			
	//			arquivoLocalizado = new File(f.getAbsolutePath());
	//			
	//			arquivo = f;
	//			
	//			System.out.println("Arquivo Encontrado :" + arquivoLocalizado.getName());
	//			
	//			trace.setText("Arquivo Encontrado :" + arquivoLocalizado.getName());
	//			
	//			diretorioTem = true;
	//			
	//			progressind.setVisible(false);
	//			
	//		}else {
	//			
	//			diretorioTem = false;
	//			
	//			System.out.println("Arquivo nao Encontrado :" );
	//			trace.setText("Arquivo nao Encontrado fase 1");
	//			
	//		}
	//		
	//	}
	//	
	//	
	//	
	//	if(diretorioTem){
	//		
	//		System.out.println("tem o arquivo no diretorio" + diretorio.getAbsolutePath());
	//		
	//	}else {
	//		
	//		progressind.setVisible(true);
	//		System.out.println("nao tem o arquivo no diretorio: " + diretorio.getAbsolutePath());
	//		trace.setText("nao tem o arquivo no diretorio: " + diretorio.getAbsolutePath());
	//		
	//		trace.setText("Procurar arquivo fase 2");
	//		System.out.println("Procurar arquivo fase 2");
	//		
	//		while (!diretorioTem) {
	//			
	//		diretorio = new File(diretorio.getParent());
	//		
	//		diretorio.listFiles(new FileFilter() {
	//			
	//			
	//			public boolean accept(File pathname) {
	//				return pathname.getName().contains(nome); // apenas arquivos que começam com a letra "a"
	//			
	//			
	//			}
	//		});
	//		
	//		for(File f : listFiles) {
	//			
	//			System.out.println(f.getName());
	//			
	//			if(f.getName().equals(nome)){
	//				
	//				arquivoLocalizado = new File(f.getAbsolutePath());
	//				
	//				arquivo = f;
	//				
	//				System.out.println("Arquivo Encontrado fase 2 :" + arquivoLocalizado.getName());
	//				trace.setText("Arquivo Encontrado :" + arquivoLocalizado.getName());
	//				
	//				diretorioTem = true;
	//				progressind.setVisible(false);
	//				
	//			}else {
	//				
	//				System.out.println("Arquivo nao Encontrado :" );
	//				trace.setText("Arquivo nao Encontrado fase 2");
	//				
	//				diretorioTem = false;
	//				
	//			}
	//			
	//		}
	//		
	//		
	//	}
	//	
	//	}
	//	
	//	
	//	
	//	return arquivo;
	//	
	//}

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

					//            	trace.textProperty().unbind();
					//            	trace.setText("Busca:  " + arquivo);
					//            	trace.textProperty().bind();

					lista.add(arquivo);
				}
			}
		}
		else if (arquivo.getName().equalsIgnoreCase(palavra)){

			System.out.println("Busca elseif: 2" + arquivo);

			//    	trace.textProperty().unbind();
			//    	trace.setText("Busca: " + arquivo);

			lista.add(arquivo);
		}
		else if (arquivo.getName().indexOf(palavra) > -1){
			System.out.println("Busca elseif: 3" + arquivo);

			//    	trace.textProperty().unbind();
			//    	trace.setText("Busca: " + arquivo);

			lista.add(arquivo);
		}
		return lista;
	}




	//private void CriarSubcena(){
	//	
	//   
	//	 // Group torusGroup = new Group();
	//	  
	//	    for (int i = 0; i < 10; i++) {
	//	        Random r = new Random();
	//	        //A lot of magic numbers in here that just artificially constrain the math
	//	        float randomRadius = (float) ((r.nextFloat() * 300) + 50);
	//	        float randomTubeRadius = (float) ((r.nextFloat() * 100) + 1);
	//	        int randomTubeDivisions = (int) ((r.nextFloat() * 64) + 1);
	//	        int randomRadiusDivisions = (int) ((r.nextFloat() * 64) + 1);
	//	        Color randomColor = new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble());
	//	        boolean ambientRandom = r.nextBoolean();
	//	        boolean fillRandom = r.nextBoolean();
	//	        
	//	        if (i == 0) {
	//	          //  torusGroup.getChildren().add(bill);
	//	        }
	//	     //   TorusMesh torus = new TorusMesh(randomTubeDivisions, randomRadiusDivisions, randomRadius, randomTubeRadius);
	//	        double translationX = Math.random() * 1024 * 1.95;
	//	        if (Math.random() >= 0.5) {
	//	            translationX *= -1;
	//	        }
	//	        double translationY = Math.random() * 1024 * 1.95;
	//	        if (Math.random() >= 0.5) {
	//	            translationY *= -1;
	//	        }
	//	        double translationZ = Math.random() * 1024 * 1.95;
	//	        if (Math.random() >= 0.5) {
	//	            translationZ *= -1;
	//	        }
	//	        
	//	        Translate translate = new Translate(translationX, translationY, translationZ);
	//	        
	//	        Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
	//	        Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
	//	        Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);
	//	      
	//	      camara.setTranslateX(translationX);
	//	      camara.setTranslateY(translationY);
	//	      camara.setTranslateX(translationZ);
	//	      
	//	      Point3D ponto3d = new Point3D(rotateX.getAngle(), rotateY.getAngle(), rotateZ.getAngle());
	//	      
	//	      camara.setRotationAxis(ponto3d);
	//	      
	//	      camara.setRotate(ponto3d.getY());
	//	      
	//	      //torus.getTransforms().addAll(translate, rotateX, rotateY, rotateZ);
	//	        //torus.getTransforms().add(translate);
	//	      //  torusGroup.getChildren().add(torus);
	//	    }
	//	  
	//	//    vbox.getChildren().addAll(torusGroup);
	//	 //   anchorcena.getChildren().add(subcena);
	//	    
	//	    
	//	    
	//	    //Enable subScene resizing
	//	    subcena.widthProperty().bind(anchorcena.widthProperty());
	//	    subcena.heightProperty().bind(anchorcena.heightProperty());
	//	    subcena.setFocusTraversable(true);
	//	
	//	
	//	
	//}



	@FXML
	private void logout() throws IOException {



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

						updateMessage("Fechando");


						Thread.sleep(1000);

						updateMessage("Fechar.");

						return 1;
					}
				};
			}

			@Override
			protected void succeeded() {



				progressind.setVisible(false);

				Platform.exit();	
				System.exit(0);

			}



		}.start();





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
	private void blinkconf(){

		Image image = new Image(getClass().getResourceAsStream("/images/settings (1).png"),64.0,64.0,true,true);
		imgvconf.setImage(image);
		btconf.setGraphic(imgvconf);
		btconf.setTextFill(Color.WHITESMOKE);


	}


	@FXML
	private void blinkconfexit(){

		Image image = new Image(getClass().getResourceAsStream("/images/settings (2).png"),64.0,64.0,true,true);
		imgvconf.setImage(image);
		btconf.setGraphic(imgvconf);
		btconf.setTextFill(Color.TURQUOISE);


	}


	@FXML
	private void blinkoff(){

		Image image = new Image(getClass().getResourceAsStream("/images/power-button.png"),64.0,64.0,true,true);
		imgvoff.setImage(image);
		btoff.setGraphic(imgvoff);
		btoff.setTextFill(Color.WHITESMOKE);

	}



	@FXML
	private void blinkoffexit(){

		Image image = new Image(getClass().getResourceAsStream("/images/power-button (1).png"),64.0,64.0,true,true);
		imgvoff.setImage(image);
		btoff.setGraphic(imgvoff);
		btoff.setTextFill(Color.TURQUOISE);

	}

	@FXML
	private void blinkoficio(){

		Image image = new Image(getClass().getResourceAsStream("/images/icons8-ficheiro-submódulo-150.png"),64.0,64.0,true,true);
		imgvoficio.setImage(image);
		btoficio.setGraphic(imgvoficio);
		btoficio.setTextFill(Color.WHITESMOKE);

	}



	@FXML
	private void blinkoficioexit(){

		Image image = new Image(getClass().getResourceAsStream("/images/icons8-ficheiro-submódulo-150 (1).png"),64.0,64.0,true,true);
		imgvoficio.setImage(image);
		btoficio.setGraphic(imgvoficio);
		btoficio.setTextFill(Color.TURQUOISE);

	}


	@FXML
	private void blinkserver(){

		Image image = new Image(getClass().getResourceAsStream("/images/database (3).png"),64.0,64.0,true,true);
		imgvserver.setImage(image);
		btserver.setGraphic(imgvserver);
		btserver.setTextFill(Color.WHITESMOKE);


	}



	@FXML
	private void blinkserverexit(){

		Image image = new Image(getClass().getResourceAsStream("/images/database (4).png"),64.0,64.0,true,true);
		imgvserver.setImage(image);
		btserver.setGraphic(imgvserver);
		btserver.setTextFill(Color.TURQUOISE);


	}


	@FXML
	private void blinkfiltro(){

		Image image = new Image(getClass().getResourceAsStream("/images/filter.png"),64.0,64.0,true,true);
		imgvfiltro.setImage(image);
		btfiltro.setGraphic(imgvfiltro);
		btfiltro.setTextFill(Color.WHITESMOKE);


	}



	@FXML
	private void blinkfiltroexit(){

		Image image = new Image(getClass().getResourceAsStream("/images/filter (1).png"),64.0,64.0,true,true);
		imgvfiltro.setImage(image);
		btfiltro.setGraphic(imgvfiltro);
		btfiltro.setTextFill(Color.TURQUOISE);


	}











	@FXML
	private void configuracao(){


		//	trace.setText("Clicado em Configuracao");

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/conf.fxml"));

			Parent root =loader.load();

			configcontroler = (ConfiguracaoController)loader.getController();

			//	        configcontroler.Inicializar(detentoEnviado,detentoErro);


			configcontroler.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT CONFIG");

					//	VerificarConfFiltroInit();

					configcontroler.fechar();



					desborrar();


				}

			});


			configcontroler.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em salvar nome arquivo");

					configcontroler.salvarnome();
					
					nomeArquivo = configcontroler.getNomeArquivo();

					nomeModelo = configcontroler.getNomeModelo();

					System.out.println("nomearquivo: " + nomeArquivo);
					System.out.println("nomemodelo: " + nomeModelo);

					stageManager.setIsconfigurado(true);

					VerificarConfFiltroInitConfig();


					configcontroler.fechar();

					desborrar();





				}

			});




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Configuração");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);

			scene.setFill(null);
			s1.setScene(scene);
			// 
			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de configuracao:"+ e);

		}


		borrar();

	}


	@FXML
	private void oficio(){


		//		trace.setText("Clicado em Oficio");
		//		
		//		trace.setText("Clicado em Ofico");

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/geraroficio.fxml"));

			Parent root =loader.load();

			oficiocontroler = (OficioController)loader.getController();

			List<Detento> dets = new ArrayList<Detento>();

			dets.addAll(detentoFinal);

			oficiocontroler.Inicializar(filtros);

			oficiocontroler.RecebeDiretorio(arquivoLocalizado,nomeModelo,dirfinal);


			oficiocontroler.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT Oficio");
					//VerificarConfFiltroInit();
					oficiocontroler.fechar();
					desborrar();


				}

			});


			//	        oficiocontroler.getBtgeraroficio().setOnAction(new EventHandler<ActionEvent>() {
			//
			//	            @Override
			//	            public void handle(ActionEvent event) {
			//	            	
			//	            	System.out.println("passou em GERAR oficio");
			//	            //	VerificarConfFiltroInit();
			//	            	desborrar();
			//
			////	            	oficiocontroler.salvarnome();
			//	            	
			////	            	nomeArquivo = oficiocontroler.getNomeArquivo();
			//	            	
			////	            	System.out.println("nomearquivo: " + nomeArquivo);
			//	            
			//	            }
			//	        
			//	        });




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Oficio");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);


			scene.setFill(null);
			s1.setScene(scene);
			// scene.setFill(null);

			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de oficio:"+ e);

		}

		borrar();


	}


	@FXML
	private void server(){


		//		trace.setText("Clicado em Server");
		//		
		//		trace.setText("Clicado em Server");

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/servidor.fxml"));

			Parent root =loader.load();

			servercontroler = (ServidorController)loader.getController();

			//	        configcontroler.Inicializar(detentoEnviado,detentoErro);


			servercontroler.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT Server");
					//VerificarConfFiltroInit();
					servercontroler.fechar();
					desborrar();


				}

			});


			servercontroler.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em salvar Server"); 	

					//	            	if(lines != null){
					//	            	nomeArquivo = lines.get(0);
					//	            	}

					System.out.println("nomearquivo SERVER: " + nomeArquivo);

					usuarioconf = servercontroler.getJtxtusuario().getText();
					senhaconf = servercontroler.getJtxtsenha().getText();
					hostconf = servercontroler.getJtxthost().getText();
					protocoloconf = servercontroler.getJtxtprotocolo().getText();
					portaconf = servercontroler.getJtxtporta().getText();

					//  	RecuperarInfoConf(arquivoConf);

					//	VerificarConfFiltroInit();

					stageManager.setIsconfiguradoserver(true);

					isconfiguradoserver=true;

					conected = servercontroler.isIsconect();


					//	            	VerificarConexaoNet();

					VerificarConfFiltroInitServer();

					servercontroler.fechar();
					desborrar();

				}

			});




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Server");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);


			scene.setFill(null);
			s1.setScene(scene);

			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de servidor:"+ e);

		}

		borrar();


	}



	private void TarefaBanco(){

		if(fiveSecondsWonder == null){


			fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(20), new EventHandler<ActionEvent>() {


				@Override
				public void handle(ActionEvent event) {

					System.out.println("this is called every 1 min on UI thread");


					//  jtgbligar.setSelected(true);



					//		        
					//		        for (int i = 0; i < 10; i++) { 
					//		    	
					//		    	 double translationX = Math.random() * 1024 * 1.95;
					//			        if (Math.random() >= 0.5) {
					//			            translationX *= -1;
					//			        }
					//			        double translationY = Math.random() * 1024 * 1.95;
					//			        if (Math.random() >= 0.5) {
					//			            translationY *= -1;
					//			        }
					//			        double translationZ = Math.random() * 1024 * 1.95;
					//			        if (Math.random() >= 0.5) {
					//			            translationZ *= -1;
					//			        }
					//			        
					//			        Translate translate = new Translate(translationX, translationY, translationZ);
					//		       
					//		    	
					//		    	 	Rotate rotateX = new Rotate(Math.random() * 360, Rotate.X_AXIS);
					//			        Rotate rotateY = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
					//			        Rotate rotateZ = new Rotate(Math.random() * 360, Rotate.Z_AXIS);
					//			      
					//			      camara.setTranslateX(translationX);
					//			      camara.setTranslateY(translationY);
					//			      camara.setTranslateX(translationZ);
					//			      
					//			      Point3D ponto3d = new Point3D(rotateX.getAngle(), rotateY.getAngle(), rotateZ.getAngle());
					//			      
					//			 //     camara.setRotationAxis(ponto3d);
					//			      
					//			      camara.setRotate(ponto3d.getX());
					//			      
					//			      	subcena.widthProperty().bind(anchorcena.widthProperty());
					//				    subcena.heightProperty().bind(anchorcena.heightProperty());
					//				    subcena.setFocusTraversable(true);





					//		        }



					//	   		    Platform.runLater(() ->
					//	   		    {

					//			      	subcena.widthProperty().bind(vbox.widthProperty());
					//				    subcena.heightProperty().bind(vbox.heightProperty());
					//				    subcena.setFocusTraversable(true);



					//	   		    });

					//		        EventoProcurarArquivo();
					//		      
					//		        
					//		        try {
					//					enviarall();
					//				} catch (AddressException e) {
					//					// TODO Auto-generated catch block
					//					e.printStackTrace();
					//				} catch (MessagingException e) {
					//					// TODO Auto-generated catch block
					//					e.printStackTrace();
					//				}

					//  servicolote.start();

					//		        CriarSubcena();




					ExecutarBuscarArquivo(); 



					Platform.runLater(() ->
					{
						ExecutarServico();

					});



					//  ExecutarTimeLine();


				}





			}));

			fiveSecondsWonder.setCycleCount(Animation.INDEFINITE);	         
			fiveSecondsWonder.setAutoReverse(false);
			fiveSecondsWonder.play();

		}else {

			//ExecutarTimeLine();
			//ControleTimeline();
			//servicolote.cancel();
			//fiveSecondsWonder.stop();

			if(servicolote!= null && servicolote.isRunning()) {



			}else {

				//   ExecutarBuscarArquivo(); 

			}


			ExecutarTimeLine();

		}



	} 


	public void ExecutarBuscarArquivo() {

		if(isLocalizarArquivo){

			jtgbligar.fire();
			isLocalizarArquivo = true;

		}else {


			isLocalizarArquivo = false;

		}

	}



	public void ExecutarServico() {

		if(isLocalizarArquivo && fiveSecondsWonder.getStatus()== Status.RUNNING){

			jtgbcriartarefa.setSelected(false);

		}else {


			jtgbcriartarefa.setSelected(true);

		}



	}


	public void ExecutarTimeLine() {



		if(fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.RUNNING) {
			//			isLocalizarArquivo = false;
			jtgbligar.setSelected(false);
			isLocalizarArquivo = false;
			//			isLocalizarArquivo = false;

		}else if (fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.CANCELLED) {

			jtgbligar.setSelected(true);
			isLocalizarArquivo = true;
			//			isLocalizarArquivo = true;

			//			isLocalizarArquivo = true;

		}
		//			else if (fiveSecondsWonder.getStatus() == Status.STOPPED && servicolote.getState() == State.CANCELLED) {
		//			
		//			jtgbligar.setSelected(false);
		//			
		////			isLocalizarArquivo = true;
		//			
		//		}

		else if (fiveSecondsWonder.getStatus() == Status.STOPPED && servicolote.getState() == State.RUNNING) {

			jtgbcriartarefa.setSelected(false);
			isLocalizarArquivo = false;

			//			isLocalizarArquivo = true;

		}	else if (fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.FAILED) {

			jtgbcriartarefa.setSelected(true);

			isLocalizarArquivo = false;

		}else if (fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.SUCCEEDED) {

			jtgbteste.setSelected(true);
			isLocalizarArquivo = false;


		}else if ((jtgbligar.isSelected()) &&(fiveSecondsWonder.getStatus() == Status.RUNNING))  {

			//			(servicolote.getState() == State.RUNNING)
			jtgbcriartarefa.setSelected(false);
			isLocalizarArquivo = false;


		}

		else if (fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.READY) {

			//			(servicolote.getState() == State.RUNNING)
			jtgbcriartarefa.setSelected(false);
			servicolote.restart();
			isLocalizarArquivo = true;


		}

		else if (fiveSecondsWonder.getStatus() == Status.RUNNING && servicolote.getState() == State.SCHEDULED) {

			//		(servicolote.getState() == State.RUNNING)
			jtgbteste.setSelected(true);
			isLocalizarArquivo = true;


		}



	}


	public void EventoProcurarArquivo(){



		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 



				if((arquivoLocalizado == null) || (!arquivoLocalizado.exists())) {



					System.out.println("entrou no arquivo localizado nulo: ");

					//	            		progressind.setVisible(true);
					//	            		progressindligar.setVisible(true);
					//	            		
					//	            		VerificarConfFiltroInit();
					//	            		CriarDiretorio(arquivoLocalizado);
					//	            		RecuperarInfoConf(arquivoConf);
					//	            		RecuperarInfoFilter(arquivoFiltro);

					//	            	procurarArquivos();
					//	            	arquivoLocalizado =  RetornarArquivoValido(arquivos);


					//	            	if(arquivoLocalizado.exists()){
					//	            		
					//	            			CriarDiretorio(arquivoLocalizado);
					//	            		 	RecuperarInfoConf(arquivoConf);
					//	 	    	        	RecuperarInfoFilter(arquivoFiltro); 
					//	            		
					//	            
					//	            		
					//	            	}else {
					//	            		
					//	            		try {
					//	    					arquivoLocalizado.createNewFile();
					//	    					CriarDiretorio(arquivoLocalizado);
					//	    					RecuperarInfoConf(arquivoConf);
					//	 	    	        	RecuperarInfoFilter(arquivoFiltro);
					//	    			
					//	    				} catch (IOException er) {
					//	    					// TODO Auto-generated catch block
					//	    					er.printStackTrace();
					//	    				}
					//	            		
					//	            		
					//	            		
					//	            	}



				}else {

					//	            		VerificarConfFiltroInit();

					//	            		CriarDiretorio(arquivoLocalizado);
					//	            		RecuperarInfoConf(arquivoConf);
					//	            		RecuperarInfoFilter(arquivoFiltro);




				}
				//	            	



				copyTask = new CopyTaskArquivos(arquivos);

				System.out.println("ARQUIVOS :" + getArquivos().size());


				progressind.progressProperty().unbind();

				progressind.progressProperty().bind(copyTask.progressProperty());



				trace.textProperty().unbind();

				trace.textProperty().bind(copyTask.messageProperty());






				copyTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, 
						new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent t) {
						List<File> copied = copyTask.getValue();



						trace.textProperty().unbind();
						trace.setText("Importados: " + copied.size() + " Registros");
						trace.textProperty().bind(copyTask.messageProperty());

						boolean isprocessado = IniciaExecucao();

						if(isprocessado){


							progressindligar.setVisible(false);
						//	jtgbligar.setSelected(false);

							copyTask.cancel();

							trace.textProperty().unbind();
							trace.setText("Arquivo já Processado Encontrado");





						} else {



							System.out.println("Arquivo Localizado: " + arquivoLocalizado.getName());

							trace.textProperty().unbind();
							trace.setText("Arquivo Localizado: " + arquivoLocalizado.getPath());
							//	                                     trace.textProperty().bind();
							trace.textProperty().bind(copyTask.messageProperty());

							progressind.setVisible(false);
							progressindligar.setVisible(false);


							//	                                     Importarregistros();

							btimportar.fire();

							//	             	            		carregarexcel(arquivoLocalizado);

							//	           	            		 if(!filtros.isEmpty()){
							//	           	            			 
							//	           	            			  Platform.runLater(() ->
							//	                                  	    {
							//
							//	                                  	    	PosExecute();
							//	                                  	    	
							//	                                  	    	
							//	           	                                 PreencherAjuda();
							//	           	                                 
							//	           	                                 trace.textProperty().unbind();
							//	           	                                 trace.setText("Detentos: " + detentoFinal.size() + " Válidos");
							//	           	                                 lblretornoligar.setVisible(true);
							//	                                  	    	
							//	                                  	    	
							//	                                  	   
							//	                                  	    });
							//	           	            			 
							//	           	            			 
							//	           	            			 
							//	           	            		 }






							//	                                     if(!filtros.isEmpty()){
							//	                                       	 
							//	                                    	 carregarexcel(arquivoLocalizado);
							//	                                       	 
							//	                                  	    Platform.runLater(() ->
							//	                                 	    {
							//
							//	                                 	    	PosExecute();
							//	                                 	    	
							//	                                 	    	
							//	                                 	    	 progressind.setVisible(false);
							//	        	                                 progressindligar.setVisible(false);
							////	        	                                 filter();
							//	        	                                 PreencherAjuda();
							//	        	                                 
							//	        	                                 trace.textProperty().unbind();
							//	        	                                 trace.setText("Registros Covertidos e Filtrados: " + detentoFinal.size() + " Detentos");
							//	        	                                 lblretornoligar.setVisible(true);
							//	                                 	    	
							//	                                 	    	
							//	                                 	   
							//	                                 	    });
							//	                                     	 
							//	                                     	 
							//	                                      }else {
							//	                                     	 
							//	                                     	 
							//	                                    	  carregarexcel(arquivoLocalizado);
							//	                                 	    	 progressind.setVisible(false);
							//	        	                                 progressindligar.setVisible(false);
							//	                                     	 
							//	                                     	 
							//	                                      }





							// isLocalizarArquivo=false;

							//	                                     if(jcbvalidarbefore.isSelected()){
							//	                                    	 
							//	                                    	 DelayValida();
							//	                                    	 
							//	                                    	 
							//	                                     }

							//	                                     
							//	                                   if(isautomatico) {
							//	                                     // automatico so funciona com esse descomentado
							//	                                  //   jtgbligar.setSelected(false);
							//	                                     
							//	                                   }
							//	                                   
							//	                                     isLocalizarArquivo=false;
							//	                                 
						}

						//	                                 if(!filtros.isEmpty()) {
						//	                                	 
						//		                                 progressind.setVisible(true);
						//		                                 progressindligar.setVisible(true);
						//		                                 
						//		                             //    carregarexcel(arquivoLocalizado);
						//	                                     
						//	                                     gerenciafiltro = new GerenciadorFiltro(filtros, registrosNotificar, detentoFinal);
						//	                                     
						//	                                     setRegistros(gerenciafiltro.getRegistros());
						//	                                     
						//	                                     setDetentos(gerenciafiltro.getDetentos());
						//	                                     
						//	                                     filter();
						//	                                     
						//	                                 	lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
						//	                                 	jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
						//	                                     
						////	                                 	filter();
						//	                                     
						//	                                     System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
						//	                                     System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());
						//
						//	                                     
						//	                                     System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
						//	                                     System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
						//	              
						//	                                     System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
						//	                                     System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
						//	                                 
						//	                                     System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
						//	                                     System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
						//	                                     
						//	                                     
						//	                                     }else {
						//	                                    	 
						//	                                    	 
						//	                                    	// carregarexcel(arquivoLocalizado);
						//	                                    	 
						//	                                    	 
						//	                                     }


						//	                                 trace.textProperty().bind(copyTask.messageProperty());
						//	                                 trace.textProperty().unbind();

						//  progressind.setVisible(false);

						//	                                 if(arquivoLocalizado == null) {
						//	                                	 
						//	                                 arquivoLocalizado =  RetornarArquivoValido(arquivos);
						//	                                 System.out.println("Arquivo Localizado: " + arquivoLocalizado.getPath());
						//	                                 
						////	                                 trace.textProperty().unbind();
						//	                                 
						//	                                 CriarDiretorio(arquivoLocalizado);
						////	                                 trace.textProperty().unbind();
						//	                                 RecuperarInfoConf(arquivoConf);
						//	                                 RecuperarInfoFilter(arquivoFiltro);
						//
						//	                                 
						//	                                 
						//	                                 }else {
						//	                                	 
						////		                                 trace.textProperty().unbind();
						////		                                 RecuperarInfoConf(arquivoConf);
						////		                                 RecuperarInfoFilter(arquivoFiltro);
						//	                                	 
						//	                                	 
						//	                                 }

						//	                                 if(arquivoLocalizado == null) {
						//	                                	 
						//	                                	 progressindligar.setVisible(false);
						//	                                	 jtgbligar.setSelected(false);
						//	                                	 
						//	                                	 copyTask.cancel();
						//	                                	 
						////	                                	 ErrorAlert("Arquivo Não Encontrado");
						//	                                	 trace.textProperty().unbind();
						//	                                	 trace.setText("Arquivo Excel não Encontrado");
						//	                                                        	 
						//	                                	
						//	                                	 
						//	                                 }

						//	                                 boolean isprocessado = IniciaExecucao();
						//	                                 
						//	                                 if(isprocessado){
						//	                                	 
						//	                                	 
						//	                                	 progressindligar.setVisible(false);
						//	                                	 jtgbligar.setSelected(false);
						//	                                	 
						//	                                	 copyTask.cancel();
						//	                                	 
						//	                                	 trace.textProperty().unbind();
						//	                                	 trace.setText("Arquivo já Processado Encontrado");
						//	                                	 
						//
						//	                                	 
						//	                                	
						//	                                	 
						//	                                 }else {
						//	                                 
						////	                                 existearquivo = VerificarArquivoConfiguracao(arquivoConf);
						//
						//		                                 
						//		                                 System.out.println("Arquivo Localizado: " + arquivoLocalizado.getPath());
						//		                                
						////		                                 trace.textProperty().unbind();
						////		                                 trace.setText("Arquivo Localizado: " + arquivoLocalizado.getPath());
						////		                                 trace.textProperty().bind();
						////		                                 carregarexcel(arquivoLocalizado);
						//		                                // isLocalizarArquivo=false;
						//		                                 
						//		                               if(isautomatico) {
						//		                                 // automatico so funciona com esse descomentado
						//		                              //   jtgbligar.setSelected(false);
						//		                                 
						//		                               }
						//		                               
						//			                             isLocalizarArquivo=false;
						//	                                 
						//	                                 }
						//	                                 
						//	                               
						//	                                 
						//	                               //  trace.textProperty().unbind();
						//	                                 
						//	                                 if(!filtros.isEmpty()) {
						//	                                 
						//	                                 gerenciafiltro = new GerenciadorFiltro(filtros, registrosNotificar, detentoFinal);
						//	                                 
						//	                                 setRegistros(gerenciafiltro.getRegistros());
						//	                                 
						//	                                 setDetentos(gerenciafiltro.getDetentos());
						//	                                 
						//	                             	lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
						//	                                 
						//	                                 filter();
						//	                                 
						//	                                 System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
						//	                                 System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());
						//
						//	                                 
						//	                                 System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
						//	                                 System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());
						//	          
						//	                                 System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
						//	                                 System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());
						//	                             
						//	                                 System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
						//	                                 System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());
						//	                                 
						//	                                 
						//	                                 }




						//	                                 PosExecute();

						//	                                 filter();
						//	                                 progressind.setVisible(false);
						//	                                 progressindligar.setVisible(false);
						////	                                 filter();
						//	                                 PreencherAjuda();
						//	                                 
						//	                                 trace.textProperty().unbind();
						//	                                 trace.setText("Registros Covertidos e Filtrados: " + detentoFinal.size() + " Detentos");
						//	                                 lblretornoligar.setVisible(true);







					}




				});


				// Start the Task.
				new Thread(copyTask).start();


			} 


		}; 



		//	         btimportar.fire();
		//jtgbligar.setOnAction(event);
		//	        btimportar.fire();

		btimportar2.setOnAction(event);
		//	        btimportar.fire();


	}


	private void Importarregistros(){

		EventHandler<ActionEvent> eventt = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 


				progressind.setVisible(true);
				progressindligar.setVisible(true);

				lblligar.setText("Importando Registros");


				CarregarArquivoExcel();
				//filtroCodigo(registros);



				copyTaskreg = new CopyTask(registros);

				System.out.println("registros :" + getRegistros().size());


				progressind.progressProperty().unbind();

				progressind.progressProperty().bind(copyTaskreg.progressProperty());



				trace.textProperty().unbind();

				trace.textProperty().bind(copyTaskreg.messageProperty());






				copyTaskreg.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
						new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent t) {
						List<Registro> copied = copyTaskreg.getValue();



						trace.textProperty().unbind();
						trace.setText("Registros Importados: " + copied.size() + " Registros");

						//  trace.textProperty().bind(copyTaskreg.messageProperty());


						progressind.setVisible(false);
						progressindligar.setVisible(false);

						//	                                 Converterregistros();
						
					//	AnaliseRegistro(registrosNotificar);

						btconverter.fire();



					}




				});


				// Start the Task.
				new Thread(copyTaskreg).start();


			}




		}; 



		btimportar.setOnAction(eventt);
		//	        btimportar.fire();


	}



	private void CarregarArquivoExcel() {

		carregarexcel(arquivoLocalizado);



	} 



	private void Converterregistros(){

		EventHandler<ActionEvent> eventtt = new EventHandler<ActionEvent>() { 
			public void handle(ActionEvent e) 
			{ 

				//carregarexcel(arquivoLocalizado);

				progressind.setVisible(true);
				progressindligar.setVisible(true);

				lblligar.setText("Convertendo");


				 try {
						FiltrarRegistros();

					} catch (NullPointerException eX) {
						// TODO Auto-generated catch block
						eX.printStackTrace();
						trace.textProperty().unbind();
						trace.setText("Erros na Conversão" + eX);
						jtgbligar.setSelected(false);
						ErrorAlert("Erros na Conversão");
						

					}




				copyTaskdet = new CopyTaskDet(detentoFinal);

				System.out.println("detentos :" + getDetentos().size());


				progressind.progressProperty().unbind();

				progressind.progressProperty().bind(copyTaskdet.progressProperty());



				trace.textProperty().unbind();

				trace.textProperty().bind(copyTaskdet.messageProperty());






				copyTaskdet.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
						new EventHandler<WorkerStateEvent>() {

					@Override
					public void handle(WorkerStateEvent t) {
						List<Detento> copied = copyTaskdet.getValue();



						trace.textProperty().unbind();
						trace.setText("Convertidos: " + copied.size() + " Detentos");
						//     trace.textProperty().bind(copyTaskdet.messageProperty());
						//	                                 progressind.setVisible(false);
						//	                                 progressindligar.setVisible(false);





						//	           	            		 if(!filtros.isEmpty()){
						//           	            			 
						//           	            			  Platform.runLater(() ->
						//                                  	    {
						//
						//                                  	    	PosExecute();
						//                                  	    	
						//                                  	    	
						//           	                                 PreencherAjuda();
						//           	                                 
						//           	                                 trace.textProperty().unbind();
						//           	                                 trace.setText("Detentos: " + detentoFinal.size() + " Válidos");
						//           	                                 lblretornoligar.setVisible(true);
						//           	                                 
						//           	                   
						//                                  	    	
						//                                  	    	
						//                                  	   
						//                                  	    });
						//           	            			 
						//           	            			 
						//           	            			 
						//           	            		 }


						lblligar.setText("Localizar Arquivo");
						progressind.setVisible(false);
						progressindligar.setVisible(false);
						
						PreencherAjuda();


					}




				});


				// Start the Task.
				new Thread(copyTaskdet).start();


			}




		}; 



		btconverter.setOnAction(eventtt);
		//	        btconverter.fire();


	}


	private void FiltrarRegistros() throws NullPointerException {


		filter();

	} 


	public void MetodoInicicial() {


		progressind.setVisible(true);
		progressindligar.setVisible(true);


		if(arquivoLocalizado == null) {

			procurarArquivos();
			arquivoLocalizado =  RetornarArquivoValido(arquivos);
			if(!arquivoLocalizado.exists()){

				CriarDiretorio(arquivoLocalizado);

			}else {



			}

			RecuperarInfoConf(arquivoConf);
			RecuperarInfoFilter(arquivoFiltro); 
			//            System.out.println("entrou Arquivo Localizado: null");



		} else {

			//	 PosExecute();
			//                CriarDiretorio(arquivoLocalizado);
			//                RecuperarInfoConf(arquivoConf);
			//                RecuperarInfoFilter(arquivoFiltro);



			System.out.println("não entrou Arquivo Localizado: null");

		}

	}



	private void PosExecute() {
		//		
		//      progressind.setVisible(true);
		//      progressindligar.setVisible(true);


		//			RestartarListas();




		



		if(gerenciafiltro == null) {

			gerenciafiltro = new GerenciadorFiltro(filtros, registrosNotificar, detentoFinal);

			setRegistros(gerenciafiltro.getRegistros());

			setDetentos(gerenciafiltro.getDetentos());

			 try {
					FiltrarRegistros();

				} catch (NullPointerException eX) {
					// TODO Auto-generated catch block
					eX.printStackTrace();
					trace.textProperty().unbind();
					trace.setText("Erros na Conversão posexecute" + eX);
					jtgbligar.setSelected(false);
					ErrorAlert("Erros na Conversão posexecute");
					

				}

			lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
			jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());



			System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
			System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());


			System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
			System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());

			System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
			System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());

			System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
			System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());

		}else {
//
//
			gerenciafiltro.setFiltros(filtros);
			gerenciafiltro.setRegistros(registrosNotificar);
			gerenciafiltro.setDetentos(detentoFinal);
		//	gerenciafiltro.getExcluidos().clear();

			gerenciafiltro.InitBusca(filtros);

			setRegistros(gerenciafiltro.getRegistros());

			setDetentos(gerenciafiltro.getDetentos());
			
			 try {
					FiltrarRegistros();

				} catch (NullPointerException eX) {
					// TODO Auto-generated catch block
					eX.printStackTrace();
					trace.textProperty().unbind();
					trace.setText("Erros na Conversão PreAnalise" + eX);
					jtgbligar.setSelected(false);
					ErrorAlert("Erros na Conversão PreAnalise");
					

				}
//
//
//
//			filter();
//
//			lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
//			jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
//
//			System.out.println("gerencia filtro excluidos no null :" + gerenciafiltro.getExcluidos().toString());
//			System.out.println("gerencia filtro excluidos qtd no null :" + gerenciafiltro.getExcluidos().size());
//
//
//			System.out.println("gerencia filtro detentos no null :" + gerenciafiltro.getDetentos().toString());
//			System.out.println("gerencia filtro detentos qtd no null :" + gerenciafiltro.getDetentos().size());
//
//			System.out.println("gerencia filtro registro no null :" + gerenciafiltro.getRegistros().toString());
//			System.out.println("gerencia filtro registro qtd no null:" + gerenciafiltro.getRegistros().size());
//
//			System.out.println("gerencia filtro filtro no null :" + gerenciafiltro.getFiltros().toString());
//			System.out.println("gerencia filtro filtro qtd no null :" + gerenciafiltro.getFiltros().size());
//
//
		}


		//		progressind.setVisible(false);
		//		progressindfiltro.setVisible(false);
		
		
		
		lblfiltrosaplicados2.setText("" + objExclude.size());
		jtooltipfiltro2.setText(objExclude.toString());

		System.out.println("gerencia filtro excluidos no null :" + gerenciafiltro.getExcluidos().toString());
		System.out.println("gerencia filtro excluidos qtd no null :" + gerenciafiltro.getExcluidos().size());


		System.out.println("gerencia filtro detentos no null :" + gerenciafiltro.getDetentos().toString());
		System.out.println("gerencia filtro detentos qtd no null :" + gerenciafiltro.getDetentos().size());

		System.out.println("gerencia filtro registro no null :" + gerenciafiltro.getRegistros().toString());
		System.out.println("gerencia filtro registro qtd no null:" + gerenciafiltro.getRegistros().size());

		System.out.println("gerencia filtro filtro no null :" + gerenciafiltro.getFiltros().toString());
		System.out.println("gerencia filtro filtro qtd no null :" + gerenciafiltro.getFiltros().size());
		
		
		
		lblfiltrosaplicados.setText("" + filtros.size());
		jtooltipfiltro.setText("" + filtros.toString());



	}




	private void RecuperarInfoConf(File arquivoConf) {

		//manipulador = new ManipuladorArquivo();

		//		progressind.setVisible(true);
		//		 progressindligar.setVisible(true);

		lines= manipulador.getLines();

		if(lines.isEmpty()){

			manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
			manipulador.Escrever(arquivoConf.getPath(),nomeModelo);

			if(isconfiguradoserver){
				manipulador.Escrever(arquivoConf.getPath(),usuarioconf);
				manipulador.Escrever(arquivoConf.getPath(),senhaconf);
				manipulador.Escrever(arquivoConf.getPath(),hostconf);
				manipulador.Escrever(arquivoConf.getPath(),protocoloconf);
				manipulador.Escrever(arquivoConf.getPath(),portaconf);
			}
			//	lines = manipulador.getLines();

			//		trace.setText(manipulador.Ler(arquivoConf));

		}else {

			if(nomeArquivo != null && nomeModelo != null) {

				manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
				manipulador.Escrever(arquivoConf.getPath(),nomeModelo);

				if(usuarioconf != null && senhaconf != null && hostconf != null && protocoloconf != null && portaconf != null) {

					manipulador.Escrever(arquivoConf.getPath(),usuarioconf);
					manipulador.Escrever(arquivoConf.getPath(),senhaconf);
					manipulador.Escrever(arquivoConf.getPath(),hostconf);
					manipulador.Escrever(arquivoConf.getPath(),protocoloconf);
					manipulador.Escrever(arquivoConf.getPath(),portaconf);


				}	


			}



			//			manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
			//			manipulador.Escrever(arquivoConf.getPath(),nomeModelo);
			//			manipulador.Escrever(arquivoConf.getPath(),usuarioconf);
			//			manipulador.Escrever(arquivoConf.getPath(),senhaconf);
			//			manipulador.Escrever(arquivoConf.getPath(),hostconf);
			//			manipulador.Escrever(arquivoConf.getPath(),protocoloconf);
			//			manipulador.Escrever(arquivoConf.getPath(),portaconf);

			if(nomeArquivo == null && nomeModelo == null) {
			
			nomeArquivo = lines.get(0);
			nomeModelo = lines.get(1);
			
			}else {
				
				
				
			}

			if(isconfiguradoserver){

				usuarioconf= lines.get(2);
				senhaconf= lines.get(3);
				hostconf= lines.get(4); 
				protocoloconf= lines.get(5);
				portaconf= lines.get(6);	

			}else {

				if(nomeArquivo == null && nomeModelo == null) {

					nomeArquivo = lines.get(0);
					nomeModelo = lines.get(1);

				}

				if(usuarioconf == null && senhaconf == null && hostconf == null && protocoloconf == null && portaconf == null) {

					usuarioconf= lines.get(2);
					senhaconf= lines.get(3);
					hostconf= lines.get(4); 
					protocoloconf= lines.get(5);
					portaconf= lines.get(6);


				}


			}
			//			usuarioconf= lines.get(2);
			//			senhaconf= lines.get(3);
			//			hostconf= lines.get(4); 
			//			protocoloconf= lines.get(5);
			//			portaconf= lines.get(6);

		}

		PreencherAjuda();
		CarregarInfo();


	}



	private void RecuperarInfoFilter(File arquivoFilter) {

		//lines= new HashMap<Integer, String>();

		//	 progressindligar.setVisible(true);
		//	 progressind.setVisible(true);

		linesfilter= new HashMap<Integer, Filtro>();

		linesfilter= manipulador.getLinesfiter();

		//		int count = linesfilter.size();

		//int countt = 0;

		Set<Integer> keyset = null;

		if(!filtros.isEmpty()) {


			for (int i = 0; i < filtros.size(); i++) {

				Filtro fil = filtros.get(i);
				//	filtros.add(fil);
				manipulador.EscreverFilter(arquivoFiltro.getPath(),fil);


			}


		}

		if(!linesfilter.isEmpty() && filtros.isEmpty()){
			//	filtros.clear();

			keyset = linesfilter.keySet();

			for(Integer s : keyset){


				String[] lin = linesfilter.get(s).getValor().split(",");
				Filtro fil = new Filtro();
				fil.setChave(lin[0]);
				fil.setValor(lin[1]);
				fil.setObjeto(lin[2]);

				filtros.add(fil);


			}

			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),filtros.toString());
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),nomeModelo);
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),usuarioconf);
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),senhaconf);
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),hostconf);
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),protocoloconf);
			//		manipulador.EscreverFilter(arquivoFiltro.getPath(),portaconf);

			//		linesfilter = manipulador.getLinesfiter();
			//		trace.textProperty().unbind();
			//		trace.setText(manipulador.Ler(arquivoFiltro));

		}else {



			//				for (int i = 0; i < filtros.size(); i++) {
			//					
			//					Filtro fil = filtros.get(i);
			//				//	filtros.add(fil);
			//					manipulador.EscreverFilter(arquivoFiltro.getPath(),fil);
			//					
			//				}


		}


		//	linesfilter = manipulador.getLinesfiter();
		//			trace.textProperty().unbind();
		//			trace.setText(manipulador.LerFilter(arquivoFiltro));

		PreencherAjuda();
		CarregarInfo();
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
					stageManager.setArquivoLocalizado(fil);
					arquivoLocalizado = stageManager.getArquivoLocalizado();
					//				progressind.setVisible(false);

				}

			}




		}

		return fil;



	}

	@FXML
	void minimizar() {


		stageManager.getPrimaryStage().setIconified(true);

	}	


	@FXML
	void tray() {

		if(barratarefa == null){
			barratarefa = new JanelaBarraDeTarefa(this);

		}else {

		}	

		Platform.runLater(() -> {
			stageManager.getPrimaryStage().hide();
			//	stageManager.getPrimaryStage().requestFocus();
			barratarefa.getTray().getMenu().setEnabled(true);
		});

	}	

	@FXML
	public
	void maximinizar() {




		if(!isMaximize){


			stageManager.getPrimaryStage().setMaximized(true);
			isMaximize = true;


		}else {


			stageManager.getPrimaryStage().setMaximized(false);
			isMaximize = false;




		}

	}


	private void CriarDiretorio(File arquivoLocalizado) {

		//		progressind.setVisible(true);
		//		
		//		progressindligar.setVisible(true);

		Date data = new Date();


		String datmes = PegarMes(data);
		String datdia = PegarDia(data);
		String datano = PegarAno(data);
	//	String datfinal = "oficios" +"\\" + datano + "\\" + datmes + "\\"+ datdia +"\\";
		String datfinal = "oficios" +"\\" + datano + "\\" + datmes + "\\"+ datdia +"\\";


		//		File dirfinal = null;



		dirfinal = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), ""),"\\"+ datfinal);

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
		//	aux = aux + "configuracao" + "\\" + "conf.txt" ;
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
		//	aux = aux + "configuracao" + "\\" + "filtro.txt" ;

			aux = aux + "configuracao" + "\\" + "filtro.txt" ;

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


		CarregarInfo();

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



	public void enviarall() throws AddressException,
	MessagingException{

		if(servicolote == null){

			servicolote = new ServicoLote( this);
			servicolote.start();



		}else {


		//	servicolote.restart();
			
			servicolote.reset();
			servicolote.start();


		}

		//	    	 automatico so funciona com isso

		//	    	 if(servicolote != null && fiveSecondsWonder.getStatus() ==Status.RUNNING) {
		//	    		 
		//	    		
		//	    	//	ControleServiceIniciar();
		//	    		
		//	    		 servicolote.restart();
		//	    		 
		//	    		 
		//	    	 }


		//	    	 vbresult.setVisible(true);
		//	    	 toolbar.setVisible(true);

		jtgbcriartarefa.setDisable(false);
		toolbar.setDisable(false);



	}

	public void enviarEmail(Detento det) throws SendFailedException,InterruptedException {


		//		String sucesso ="SUCESSO";
		//		String nul = "";





		try {
			CriarHtmlEmail(det);


			//   			  Platform.runLater(() ->
			//          	    {
			//
			//
			//          	    	AddLinhaMatriz(det);
			//          	    	
			//          	   
			//          	    });





		} catch (SocketTimeoutException | TimeoutException | InterruptedException | MessagingException e) {

			det.setStatusenvio("ERROR");
			String erro = handleMessagingException(e);
			det.addErros("Envio :" + e.getMessage());

			System.out.println("erro handler" + erro);


			if (detentoErro.contains(det)){



			}  
			else {

				detentoErro.add(det);

			}

			e.printStackTrace();
		}



		Platform.runLater(() ->
		{

			//	    	PreencheDetentoValidacao(det);
			AtualizarQuadro();
			//	LimpaValidacao();

		});



	} 

	private void CriarHtmlEmail(Detento detento) throws TimeoutException, InterruptedException,
	MessagingException,SendFailedException,SocketTimeoutException{


		if(detentoEnviado.contains(detento) || detentosREnviado.contains(detento) ){

			return; 

		}else {

			String  diretorioaux = System.getProperty("user.dir");

			String diretorio = diretorioaux + "/src/main/resources/images/";



			String caminho = null;

			File arquivo = null;

			String nome = GerarDocumento(detento);

			String cami = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+ "pdf/" + detento.getNome() + ".pdf";

			System.out.println("caminho: "+ dirfinal.getPath()+"\\" + "pdf" +"\\" + detento.getNome() + ".pdf");

			System.out.println("caminho nome: "+ nome);



			try {

				caminho=new File(dirfinal.getPath()+"\\" + "pdf" +"\\" + detento.getNome() + ".pdf").getPath();
				//				 caminho=new File(dirfinal.getPath()+"\\" + "word" +"\\" + detento.getNome() + ".doc").getPath();

				arquivo = new File(nome);
				System.out.println("existe: "+ arquivo.getCanonicalPath());

				//	 File fi = new File(caminho);

				if(!arquivo.exists()){

					System.out.println("entrou no nao existe: "+ arquivo.getCanonicalPath());

					//						 arquivo.createNewFile();
					//						 fi.createNewFile();

				}


			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				trace.setText("error CriarHtmlEmail " + e1);
				detento.setStatusenvio("ERROR");

				detento.addErros("PDF");

				if (detentoErro.contains(detento)){

					//  detentoErro.remove(det);


				}  
				else {

					detentoErro.add(detento);

				}

				//					detentoErro.add(detento);
				//	AtualizarQuadro();
			}



			String to = null;
			String to2 = null;



			if(jcbemail.isSelected()){

				to = detento.getEmail();  
				to2 = "alonysantos.adv@gmail.com";

			}else {

				to = "windson.m.bezerra@gmail.com"; 
				//   emailDe = "alony.santos@seres.pe.gov.br";
				to2 = "alonysantos.adv@gmail.com";

			}



			boolean isok = false; 


			String html = CriarBody(detento);

			System.out.println("html: " + html);





			Properties props = new Properties();
			props.setProperty("mail.smtps.user", usuarioconf);   //setei o login
			props.setProperty("mail.smtp.password", senhaconf ); // e a senha
			props.setProperty("mail.transport.protocol", protocoloconf);
			props.put("mail.smtp.starttls.enable","true"); //não sei ao certo para que serve, mas tive que colocar...
			props.setProperty("mail.smtp.auth", "true");  //setei a autenticação
			props.put("mail.smtp.connectiontimeout", "10000");
			props.put("mail.smtp.timeout", "10000");

			props.setProperty("mail.smtp.starttls.required","true");
			props.setProperty( "mail.smtp.quitwait", "false");
			props.setProperty("mail.smtp.host", hostconf);
			String user = props.getProperty("mail.smtps.user");
			String passwordd = props.getProperty("mail.smtp.password");
			props.put("mail.smtp.port",portaconf);
			//props.put("mail.smtp.ssl", "smtps.expresso.pe.gov.br");
			props.put("mail.smtp.ssl.trust", hostconf);


			//  trace.setText("pos properties: "); 

			Autenticador auth = null;

			//auth = new SimpleAuth ("seuusuarioparalogin","suasenhaparalogin");

			auth = new Autenticador (user, passwordd);



			// Get the Session object.
			Session session = Session.getInstance(props, auth);
			session.setDebug(true);



			System.out.println("pathauto : " + nome);
			//  trace.setText("pathauto:  " + nome);

			try {

				MimeBodyPart messageBodyPart = new MimeBodyPart();


				messageBodyPart.setContent(html, "text/html;charset=utf-8");



				MimeBodyPart messageBodyPart2 = new MimeBodyPart();  	   
				String filename =detento.getNome() + ".pdf";
				DataSource source = new FileDataSource(caminho); 

				messageBodyPart2.setDataHandler(new DataHandler(source));  
				messageBodyPart2.setFileName(filename);




				// creates multi-part
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);
				multipart.addBodyPart(messageBodyPart2);

				String assinaturaid = "alonyass";
				//    
				MimeBodyPart imagePart = new MimeBodyPart();
				imagePart.setHeader("Content-ID", "<" + assinaturaid + ">");
				imagePart.setDisposition(MimeBodyPart.INLINE);



				System.out.println("chegou img ");


				String imageFilePath = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img3.png";

				System.out.println("img: "+ arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + "img3.png");


				try {
					imagePart.attachFile(imageFilePath);
				}

				catch (IOException ex) {
					ex.printStackTrace();
					trace.setText("error img ass " + ex);
				}







				multipart.addBodyPart(imagePart);



				Message message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(usuarioconf));

				// Set To: header field of the header.
				message.addRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));

				//		   message.addRecipients(Message.RecipientType.BCC,
				//		            InternetAddress.parse(emailDe));
				//		   
				message.addRecipients(Message.RecipientType.BCC,
						InternetAddress.parse(to2));

				// Set Subject: header field
				message.setSubject("Detento: " + detento.getNome());

				// Send the actual HTML message, as big as you like
				message.setContent(multipart);

				// Transport transport = session.getTransport("smtps");

				message.saveChanges();

				// Send message
				Transport.send(message);
				//		   salvaremailSent(message);


				System.out.println("Sent message successfully....");

				isok=true;

				detento.setStatusenvio("SUCESSO");

				if(detentoErro.contains(detento)){

					//  detentosREnviado.add(det);

					System.out.println("LISTA ERRO CONTEM DETENTO no sucesso "+ detento);

					if(detentoEnviado.contains(detento)){

						detentoErro.remove(detento);

					}else {

						detentoErro.remove(detento);
						detentoEnviado.add(detento);
						detentosREnviado.add(detento);


					}


					//					  detentosREnviado.add(det);
					//					  detentoEnviado.add(det);
					//					  detentosREnviado.add(det);

				}else {

					detentoEnviado.add(detento);



				}


				//   			  Platform.runLater(() ->
				//        	    {





				//        	    });

				//  PreencherValidacaoEnvio(detento, isok);


			} catch (Exception e) {

				isok=false;

				e.printStackTrace();

				detento.setStatusenvio("ERROR");
				String erro = handleMessagingException(e);
				detento.addErros("Envio:" + e.getMessage());

				System.out.println("erro handler" + erro);


				if (detentoErro.contains(detento)){



				}  
				else {

					detentoErro.add(detento);

				}



				System.out.println("erro" + e.getMessage());
				throw new RuntimeException(e);


			}finally {

				if(jcbvisualizarvalidacao.isSelected()){
					PreencheDetentoValidacao(detento);
					PreencherValidacaoEnvio(detento, isok);

				}
				// AtualizarQuadro();



			} 


			//	    } catch (MessagingException e) {
			//		   e.printStackTrace();



		}



	}	    


	//	} 	



	public String CriarBody(Detento detento){


		StringBuilder stringbuilder = new StringBuilder();

		Date data = new Date();

		String qtd = String.valueOf(detento.getViolacoes().size() );


		System.out.println("entrou body 1 ");

		String html =


				//				"<p align=\"middle\" ><img src= \"" + src2 + "\" alt=\"governo_desc\" width=\"300px;\" height=\"168px;\" align=\"middle\" />"
				//						+ "</p>"+


				"<h2 align=\"middle\" >Ofício : " + numoficio +"</h2>"+


				"<p align=\"justify\"> Cumprimentando-o cordialmente, informamos a V.Exa. que o monitorado eletronicamente <b> "+detento.getNome() +" </b>,<br> processo/VEP nº <b>"+processofinal+"</b>, não vem cumprindo as regras do monitoramento eletrônico, onde cometeu um total de <b>" + qtd +"</b> violação (ões), entre ela (s): <b>"+detento.getViolacoes().get(0).getAlarme()+"</b>, no dia/hora <b>" + FormatadorData(detento.getViolacoes().get(0).getDataviolacao()) + ".</b> Sendo necessário, o(s) mapa(s) de violação(ões) será(ão) enviado(s) eletronicamente, devendo ser solicitado através do e-mail: <b> "+ FormatadorEmail(detento) + "</b></p>"

				 +"<p align=\"justify\">Nesta oportunidade, nos colocamos a disposição para quaisquer esclarecimentos, e aproveitamos para renovar os nossos protestos de elevada estima, consideração e apreço.</p>"

+	" <table style=\" border: 1px solid #1C6EA4; background-color: #EEEEEE; width: 100%; text-align: left; border-collapse: collapse;> <caption>Alarmes</caption> <thead style=\" background: #1C6EA4; border-bottom: 2px solid #444444;\">" +
" <tr> " +
"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Qtd</th>"+

	//"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Nome</th>"+

	"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Alarme</th>"+
	"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Duração Alarme</th>"+

	" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Processo</th>" +

	" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Vep</th>" +

	" <th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Inicio</th>"+
	"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Fim</th>"+
	//	"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Duração</th>"+
	"</tr> </thead>" +

	" <tbody>";





		stringbuilder.append(html);
		//stringbuilder.append(tabhtml);

		System.out.println("passou body 1 ");



		for (int i = 0; i < detento.getViolacoes().size(); i++) {

			int incremento = i +1;

			Violacao viola = detento.getViolacoes().get(i);

			String datanull = null;

			if(viola.getDatafinalizacao() == null) {


				datanull = "01/01/2000 00:00";

			}else {

				datanull = viola.getDatafinalizacao();


			}

			System.out.println("entrou for body 1 ");

			//			tabhtml = tabhtml + "<tr style=\" background: #D0E4F5; \"> <td>" + incremento  + "</td>" +
			//"<td style=\" font-size: 14px;\">" + detento.getNome()  + "</td>" +
			//		"<td style=\" font-size: 14px;\">" + viola.getAlarme()  + "</td>" +
			//			"<td style=\" font-size: 14px;\">" + detento.getProcesso() + " </td> " +
			//			"<td style=\" font-size: 14px;\">" + detento.getVep() + " </td> " +
			//		"<td style=\" font-size: 14px;\">" + FormatadorData(viola.getDatainicio() ) + " </td> " +
			//			"<td style=\" font-size: 14px;\">" + FormatadorData(datanull)  + " </td> " +
			//		"<td style=\" font-size: 14px;\">" + viola.getDuracao()+ " </td> " + "</tr>" + " </tbody> </table> ";

			stringbuilder.append("<tr style=\" background: #D0E4F5; \"> <td>" + incremento  + "</td>");
			//			stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getNome()  + "</td>");
			stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getAlarme()  + "</td>");
			stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getDuracaoalarme()  + "</td>");
			stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getProcesso() + " </td> " );
			stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getVep() + " </td> " );
			stringbuilder.append("<td style=\" font-size: 14px;\">" + FormatadorData(viola.getDatainicio() ) + " </td> " );
			stringbuilder.append("<td style=\" font-size: 14px;\">" + FormatadorData(datanull)  + " </td> " + "</tr>");
			//			stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getDuracao()+ " </td> " + "</tr>");


		}

		System.out.println("passou for body 1 ");


		String src = "cid:alonyass";
		//		String srcc = "cid:pe";

		System.out.println("entrou html2 ");

		String html2 = "</tbody> </table> <h3 align=\"middle\" > <i>Recife, " + PegarDia(data) +" de "+ PegarMes(data) + " de " + PegarAno(data) + "</i></h3>" +

	//stringbuilder.append("<h1 align=\"right\" > <i>Recife, " + new Date() +"</i></h1>");
	//"<p align=\"middle\" >"
	"<p align=\"middle\"><img src= \"" + src + "\" alt=\"assinatura_desc\" width=\"100px;\" height=\"100px;\" /> <br/> <br/>" +
	//			+ "</p>"
	"<b>Alony Santos <br/> Gerente </b>" 
	+ " </p>" ;
		// "<p align=\"middle\" style=\" font-family: \"Arial Black\", Gadget, sans-serif;\"> Gerente </p>";

		System.out.println("passou html2 ");

		stringbuilder.append(html2);







		return stringbuilder.toString();
	}


	private String GerarDocumento(Detento det) {

		//		progressind.setVisible(true);

		processofinal = new String();

		String path =null;
		String pathauto=null;

		String  diretorioaux = System.getProperty("user.dir");
		//		 
		String diretorio = diretorioaux + "/src/main/resources/modelo/";

		Date data = new Date();

		String totalviola = String.valueOf(det.getViolacoes().size());



		pathauto = dirfinal.getPath()+"\\" + "word"+ "\\" + det.getNome() + ".doc" ;

		String diret = dirfinal.getPath()+"\\" + "word"+ "\\";


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



		int cont =0;

		String datanull = null;

		//	    String caracteristica = null;



		for (Violacao vio : det.getViolacoes()) {

			builder.append("\r\n" + vio.getAlarme() + "\r\n");
			alarme = alarme +"," + vio.getAlarme();

			if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){

				cont = cont +1;

				varaex = vio.getCaracteristica();
				det.setVara(varaex);



			}



			if(vio.getDataviolacao() == null) {


				datanull = "01/01/2000 00:00";

			}else {

				datanull = vio.getDataviolacao();


			}




		}

		cont = 0;

		vep = det.getVep();

		processo = det.getProcesso();


		//	    if((processo != null || !processo.isEmpty() ||  !processo.equals("") ) || (vep != null || !vep.isEmpty() ||  !vep.equals(""))){
		//	    	
		//	    	 if(vep == null || vep.isEmpty() ||  vep.equals("")) {
		//	    	    	
		//	    	    	
		//	    		    processofinal = det.getProcesso();
		//	    		    	
		//	    		   
		//	    		    }
		//	    		    
		//	    		    
		//	    	 else if(processo == null || processo.isEmpty() ||  processo.equals("")) {
		//	    		    	
		//	    		    	
		//	    		    processofinal= det.getVep();
		//	    		    	
		//	    		    	   
		//	    		    }
		//	    	
		//	    	
		//	    }


		if((processo != null && !processo.equals("") ) || (vep != null &&  !vep.equals(""))){

			if(vep == null || vep.isEmpty() ||  vep.equals("")) {


				processofinal = det.getProcesso();


			}


			if(processo == null || processo.isEmpty() ||  processo.equals("")) {


				processofinal= det.getVep();


			}


		}





		if(varaex == null || varaex ==""){

			varaex="vazia";


		}

		System.out.println("varaex:" + varaex);

		System.out.println("processo final:" + processofinal);

		System.out.println("processo final p :" + det.getProcesso());


		System.out.println("processo final v :" + det.getVep());

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


		System.out.println("teste patth: " + arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + nomeModelo);

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

		String vara = det.getViolacoes().get(0).getCaracteristica();

		if(vara == null || vara ==""){

			vara="vazia";


		}

		System.out.println("vara:" + vara);

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

		boolean isvalido = false;
		isvalido = validaDetentoEmail(det);


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

			maps.put("VARALO",varaex);
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
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					//							det.setStatusenvio("ERROR");
					//							detentoErro.add(det);
					e.printStackTrace();
					AtualizarQuadro();
				} catch (com.itextpdf.text.DocumentException e) {
					// TODO Auto-generated catch block

					//

					//							det.setStatusenvio("ERROR");
					e.printStackTrace();
					//							detentoErro.add(det);
					AtualizarQuadro();
				}


				//	ConverterWordPdf(doc,det,fs);

				//	trace.setText("ok replace");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//					det.setStatusenvio("ERROR");
				//					detentoErro.add(det);				
				trace.setText("erro replace" + e);
				AtualizarQuadro();
			}





		} else {

			det.setStatusenvio("ERROR");
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


	public void PreencherVaraComCaracteristica(Detento det){



		String varaex = null;


		int cont =0;

		for (Violacao vio : det.getViolacoes()) {

			//	    	builder.append("\r\n" + vio.getAlarme() + "\r\n");
			//	    	alarme = alarme +"," + vio.getAlarme();

			if((cont < 1) && (vio.getCaracteristica()!= null) && (vio.getCaracteristica()!= "")){

				cont = cont +1;

				varaex = vio.getCaracteristica();
				det.setVara(varaex);



			}

			if(varaex == null || varaex ==""){

				varaex="vazia";

				det.setStatusdoc("ERROR");
				det.getErros().add("SEM VARA PREENCHIDA(CARACTERISTICA)");

				//detentoserros.add(det);


			}else {

				//	det.setStatusdoc("VALIDADO");
				//	detentosucesso.add(det);


			}



		}


	}



	private String FormatadorEmail(Detento det) {

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

		if(data != null && data!=""){



		}else {

			data =  "00/00/0000 00:00";

		}


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

		//		SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
		//		Date dat = null;
		//		
		//		
		//		try {
		//			dat = fmt.parse(data);
		//		//	 trace.setText("ok format data");
		//		} catch (ParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//			 trace.setText("nok format data");
		//			// detentoErro.add(arg0)
		//		}

		SimpleDateFormat fmt2 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");    
		//Date dat2 = null;

		//dat2 = fmt2.format(dat);

		dataformatadastring=fmt2.format(data);

		dataformatadastring.replace("\\", "-");
		dataformatadastring.replace(" ", "-");
		dataformatadastring.replace(":", "-");

		System.out.println("data formataada " + dataformatadastring);


		return dataformatadastring;
	}

	private String GerarNumeroOficio(Date data){

		Random gerador = new Random();

		String num = null;

		num = PegarDia(data) + ".";

		num = num + PegarMesInt(data);

		num = num + gerador.nextInt(1000);

		num = num + "/" + PegarAno(data);

		//		 trace.setText("gerar numero oficio" + num);


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


		//		trace.setText("pegar mes int " + ms);

		return ms;
	}


	private boolean validaDetentoEmail(Detento detento){

		boolean valido = true;

		String nulo = null;
		String branco = "";

		//		detento.PreencheVara();

		//String nome = detento.getNome();

		if((jcbemail.isSelected())) {

			if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){

				valido = false;

				detento.addErros("Nome");

				//			PreencherValidacaoNome(detento, valido);

				//			ValidaDetentoInformacao("Nome Detento Branco ou Nulo");

			}else {

				//				PreencherValidacaoNome(detento, valido);


			}

			if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){

				valido = false;
				detento.addErros("Email");

				//			ValidaDetentoInformacao("Email Detento Branco ou Nulo");

			}else {

				//				PreencherValidacaoEmail(detento, valido);


			}

			//String pronto = detento.getProntuario();

			if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){

				valido = false;
				detento.addErros("Prontuario");

				//				ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");

			}

			if((detento.getProcesso()==null)&&(detento.getVep()==null) ){

				valido = false;
				detento.addErros("Processo/VEP NULOS");

				//				ValidaDetentoInformacao("Processo Detento Branco ou Nulo");

				//				PreencherValidacaoProcesso(detento, valido);
				//				PreencherValidacaoVep(detento, valido);

			}else {

				//					PreencherValidacaoProcesso(detento, valido);
				//					PreencherValidacaoVep(detento, valido);


			}

			if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){

				valido = false;
				detento.addErros("Violações");

				//				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}

			if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){

				valido = false;
				detento.addErros("CARACTRISTICA");

				//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}

			if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){

				valido = false;
				detento.addErros("Estabelecimento");

				//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}


			if((detento.getVara() == null || detento.getVara().equals(branco)) ){

				valido = false;
				detento.addErros("Vara");

				//					PreencherValidacaoVara(detento, valido);

				//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}else {

				//						PreencherValidacaoVara(detento, valido);

			}


			//				 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
			//						
			//					valido = false;
			//					detento.addErros("Vara");
			//					
			////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//						
			//					}


			//				 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
			//						
			//						valido = false;
			//						detento.addErros("Equip");
			//						
			////						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//							
			//						}

			if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){


				valido = false;
				detento.addErros("Processo/VEP VAZIO");

				//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");

				//					PreencherValidacaoProcesso(detento, valido);
				//					PreencherValidacaoVep(detento, valido);

			}else {
				//						
				//						PreencherValidacaoProcesso(detento, valido);
				//						PreencherValidacaoVep(detento, valido);


			}


			if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){


				valido = false;
				detento.addErros("Processo/VEP PREENCHIDOS");

				//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");


				//					PreencherValidacaoProcesso(detento, valido);
				//					PreencherValidacaoVep(detento, valido);

			}else {

				//						PreencherValidacaoProcesso(detento, valido);
				//						PreencherValidacaoVep(detento, valido);


			}


			//				if( (detento.getProcesso()!=null) || (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
			//					
			//					
			//					valido = true;
			////					detento.addErros("Processo/VEP PREENCHIDOS");
			//					
			////					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			//						
			//				
			//					PreencherValidacaoProcesso(detento, false);
			//					PreencherValidacaoVep(detento, valido);
			//						
			//					}else {
			//						
			//						valido = false;
			//						
			//						PreencherValidacaoProcesso(detento, valido);
			//						PreencherValidacaoVep(detento, valido);
			//						
			//						
			//					}


			//				if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()==null)||(detento.getVep().isEmpty()) ){
			//					
			//					
			//					valido = true;
			////					detento.addErros("Processo/VEP PREENCHIDOS");
			//					
			////					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			//						
			//				
			//					PreencherValidacaoProcesso(detento, valido);
			//					PreencherValidacaoVep(detento, false);
			//						
			//					}else {
			//						
			//						valido = false;
			//						
			//						PreencherValidacaoProcesso(detento, valido);
			//						PreencherValidacaoVep(detento, valido);
			//						
			//						
			//					}




		}

		else {



			//			 if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){
			//					
			//				valido = false;
			//				detento.addErros("Prontuario");
			//					
			////				ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");
			//					
			//				}
			//				
			//			if((detento.getProcesso()==null)&&(detento.getVep()==null) ){
			//					
			//				valido = false;
			//				detento.addErros("Processo/VEP NULOS");
			//				
			//				PreencherValidacaoProcesso(detento, valido);
			//				PreencherValidacaoVep(detento, valido);
			//					
			//				}else {
			//					
			//					PreencherValidacaoProcesso(detento, valido);
			//					PreencherValidacaoVep(detento, valido);
			//					
			//					
			//				}
			//				
			//			 if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){
			//					
			//				valido = false;
			//				detento.addErros("Violações");
			//				
			////				ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//					
			//				}
			//			 
			//			 if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){
			//					
			//					valido = false;
			//					detento.addErros("CARACTRISTICA");
			//					
			////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//						
			//					}
			//			 
			//			 if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){
			//					
			//					valido = false;
			//					detento.addErros("Estabelecimento");
			//					
			////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//						
			//					}
			//			 
			//			 
			//			 if((detento.getVara() == null || detento.getVara().equals(branco)) ){
			//					
			//					valido = false;
			//					detento.addErros("Vara");
			//					
			//					PreencherValidacaoVara(detento, valido);
			//				
			//			 
			//			 }else {
			//					
			//					PreencherValidacaoVara(detento, valido);
			//					
			//				}
			//				 
			//				 
			////				 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
			////						
			////					valido = false;
			////					detento.addErros("Vara");
			////					
			//////					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			////						
			////					}
			//				 
			//				 
			////				 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
			////						
			////						valido = false;
			////						detento.addErros("Equip");
			////						
			//////						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			////							
			////						}
			//			 
			//				if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){
			//					
			//					
			//					valido = false;
			//					detento.addErros("Processo/VEP VAZIO");
			//					
			//					PreencherValidacaoProcesso(detento, valido);
			//					PreencherValidacaoVep(detento, valido);
			//						
			//					}else {
			//						
			//						PreencherValidacaoProcesso(detento, valido);
			//						PreencherValidacaoVep(detento, valido);
			//						
			//						
			//					}
			//				
			//				
			//				if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
			//					
			//					
			//					valido = false;
			//					detento.addErros("Processo/VEP PREENCHIDOS");
			//					PreencherValidacaoProcesso(detento, valido);
			//					PreencherValidacaoVep(detento, valido);
			//						
			//					}else {
			//						
			//						PreencherValidacaoProcesso(detento, valido);
			//						PreencherValidacaoVep(detento, valido);
			//						
			//						
			//					}


			if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){

				valido = false;

				detento.addErros("Nome");

				//				PreencherValidacaoNome(detento, valido);

				//				ValidaDetentoInformacao("Nome Detento Branco ou Nulo");

			}else {

				//					PreencherValidacaoNome(detento, valido);


			}

			if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){

				valido = false;
				detento.addErros("Email");

				//				ValidaDetentoInformacao("Email Detento Branco ou Nulo");

				//				PreencherValidacaoEmail(detento, valido);

			}else {

				//					PreencherValidacaoEmail(detento, valido);


			}

			//String pronto = detento.getProntuario();

			if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){

				valido = false;
				detento.addErros("Prontuario");

				//					ValidaDetentoInformacao("Prontuario Detento Branco ou Nulo");

			}

			if((detento.getProcesso()==null)&&(detento.getVep()==null) ){

				valido = false;
				detento.addErros("Processo/VEP NULOS");

				//					ValidaDetentoInformacao("Processo Detento Branco ou Nulo");

				//					PreencherValidacaoProcesso(detento, valido);
				//					PreencherValidacaoVep(detento, valido);

			}else {

				//						PreencherValidacaoProcesso(detento, valido);
				//						PreencherValidacaoVep(detento, valido);


			}

			if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){

				valido = false;
				detento.addErros("Violações");

				//					ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}

			if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){

				valido = false;
				detento.addErros("CARACTRISTICA");

				//						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}

			if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){

				valido = false;
				detento.addErros("Estabelecimento");

				//						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}


			if((detento.getVara() == null || detento.getVara().equals(branco)) ){

				valido = false;
				detento.addErros("Vara");

				//						PreencherValidacaoVara(detento, valido);

				//						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");

			}else {

				//							PreencherValidacaoVara(detento, valido);

			}


			//					 if((detento.getVara().toString()==null||(detento.getVara().toString().equals(branco)) )){
			//							
			//						valido = false;
			//						detento.addErros("Vara");
			//						
			////						ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//							
			//						}


			//					 if((detento.getEquipamentos().toString()==null||(detento.getEquipamentos().toString().equals(branco)) )){
			//							
			//							valido = false;
			//							detento.addErros("Equip");
			//							
			////							ValidaDetentoInformacao("Violações Detento Branco ou Nulo");
			//								
			//							}

			if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){


				valido = false;
				detento.addErros("Processo/VEP VAZIO");

				//						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");

				//						PreencherValidacaoProcesso(detento, valido);
				//						PreencherValidacaoVep(detento, valido);

			}else {

				//							PreencherValidacaoProcesso(detento, valido);
				//							PreencherValidacaoVep(detento, valido);


			}


			if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){


				valido = false;
				detento.addErros("Processo/VEP PREENCHIDOS");

				//						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");


				//						PreencherValidacaoProcesso(detento, valido);
				//						PreencherValidacaoVep(detento, valido);

			}else {

				//							PreencherValidacaoProcesso(detento, valido);
				//							PreencherValidacaoVep(detento, valido);


			}


			//					if( (detento.getProcesso()!=null) || (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){
			//						
			//						
			//						valido = true;
			////						detento.addErros("Processo/VEP PREENCHIDOS");
			//						
			////						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			//							
			//					
			//						PreencherValidacaoProcesso(detento, false);
			//						PreencherValidacaoVep(detento, valido);
			//							
			//						}else {
			//							
			//							valido = false;
			//							
			//							PreencherValidacaoProcesso(detento, valido);
			//							PreencherValidacaoVep(detento, valido);
			//							
			//							
			//						}


			//					if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()==null)||(detento.getVep().isEmpty()) ){
			//						
			//						
			//						valido = true;
			////						detento.addErros("Processo/VEP PREENCHIDOS");
			//						
			////						ValidaDetentoInformacao("Processo Detento Branco ou Nulo");
			//							
			//					
			//						PreencherValidacaoProcesso(detento, valido);
			//						PreencherValidacaoVep(detento, false);
			//							
			//						}else {
			//							
			//							valido = false;
			//							
			//							PreencherValidacaoProcesso(detento, valido);
			//							PreencherValidacaoVep(detento, valido);
			//							
			//							
			//						}




		}

		return valido;

	}



	private void PreencheDetentoValidacao(Detento detento){

		boolean valido = true;

		String nulo = null;
		String branco = "";


		try{

			LimpaValidacao();

			if((jcbemail.isSelected())) {

				if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){

					valido = false;


					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoNome(detento, valido);

					}

				}else {
					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoNome(detento, valido);
					}

				}

				if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){

					valido = false;


				}else {
					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoEmail(detento, valido);
					}

				}


				if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){

					valido = false;


				}

				if((detento.getProcesso()==null)&&(detento.getVep()==null) ){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);

					}

				}else {

					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);

					}


				}

				if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){

					valido = false;


				}

				if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){

					valido = false;


				}

				if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){

					valido = false;


				}


				if((detento.getVara() == null || detento.getVara().equals(branco)) ){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoVara(detento, valido);
					}

				}else {
					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoVara(detento, valido);

					}

				}



				if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){


					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);

					}

				}else {
					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}



				}


				if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){


					valido = false;


					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}
				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}

				}






			}

			else {


				if((detento.getNome()==null ||(detento.getNome().equals(branco)) )){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoNome(detento, valido);
					}

				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoNome(detento, valido);
					}

				}

				if((detento.getEmail()==null|| detento.getEmail().equals(branco)) ){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoEmail(detento, valido);

					}

				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoEmail(detento, valido);

					}
				}


				if((detento.getProntuario()==null||(detento.getProntuario().equals(branco)) )){

					valido = false;


				}

				if((detento.getProcesso()==null)&&(detento.getVep()==null) ){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){

						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}
				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}

				}

				if((detento.getViolacoes().toString()==null||(detento.getViolacoes().toString().equals(branco)) )){

					valido = false;


				}

				if((detento.getViolacoes().get(0).getCaracteristica()==null || detento.getViolacoes().get(0).getCaracteristica().equals(branco)) ){

					valido = false;

				}

				if((detento.getEstabelecimento().toString()==null||(detento.getEstabelecimento().toString().equals(branco)) )){

					valido = false;


				}


				if((detento.getVara() == null || detento.getVara().equals(branco)) ){

					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoVara(detento, valido);
					}

				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoVara(detento, valido);
					}
				}



				if( (detento.getProcesso()!=null) && (detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(detento.getVep().isEmpty()) ){


					valido = false;

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}
				}else {
					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}

				}


				if( (detento.getProcesso()!=null) && (!detento.getProcesso().isEmpty()) && (detento.getVep()!=null)&&(!detento.getVep().isEmpty()) ){


					valido = false;


					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}
				}else {

					if(jcbvisualizarvalidacao.isSelected()){
						PreencherValidacaoProcesso(detento, valido);
						PreencherValidacaoVep(detento, valido);
					}

				}







			}

		}catch (Exception e) {




		}


	}




	private void ConverterWordPdf2(HWPFDocument document, Detento det) throws DocumentException, MalformedURLException, IOException, com.itextpdf.text.DocumentException{


		System.out.println("Starting the test");  
		//	    fs = new POIFSFileSystem(new FileInputStream("D:/Resume.doc"));  

		// Image img = new Image(getClass().getResourceAsStream("/images/img1.png"));

		//		 final Range           range    = document.getRange();

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

		//fileForPdf = new FileOutputStream(new File(dirfinal.getPath()+"/pdf/" + det.getNome() + ".pdf"));
		File fil = new File(dirfinal.getPath()+"/pdf/");
		fil.mkdirs();

		File filarq = new File(dirfinal.getPath()+"/pdf/" + det.getNome() +".pdf");



		fileForPdf = new FileOutputStream(filarq);

		we.close();


		com.itextpdf.text.Document documento = new com.itextpdf.text.Document(PageSize.A4, 20, 20, 50, 25);

		com.itextpdf.text.pdf.PdfWriter writer = PdfWriter.getInstance(documento, fileForPdf);

		HeaderFooterPageEvent event = new HeaderFooterPageEvent(arquivoLocalizado);	
		writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 559, 788));
		//			writer.
		//			writer.
		writer.setPageEvent(event);

		Font smallBold = new Font(Font.FontFamily.COURIER, 10,
				Font.BOLD);

		Font MDBold = new Font(Font.FontFamily.COURIER, 12,
				Font.BOLD);

		Font NORMAL = new Font(Font.FontFamily.COURIER, 12,
				Font.NORMAL);

		Font tabheader = new Font(Font.FontFamily.COURIER, 11,
				Font.BOLD);
		tabheader.setColor(BaseColor.WHITE);

		Font tabcontent = new Font(Font.FontFamily.COURIER, 9,
				Font.NORMAL);
		tabcontent.setColor(BaseColor.LIGHT_GRAY);
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

		//		 documento.add(image);

		PdfPTable table = new PdfPTable(7); // 3 columns.
		table.setWidthPercentage(90); //Width 100%
		table.setSpacingBefore(3f); //Space before table
		table.setSpacingAfter(3f); //Space after table


		//Set Column widths
		float[] columnWidths = {2f, 3f, 3f,3f,3f, 3f, 2f};
		table.setWidths(columnWidths);

		//	        Text t1 = new Text("1");
		//	        Text t2 = new Text(det.getViolacoes().get(0).getAlarme());
		//	        Text t3 = new Text(det.getAlarme_posicao());
		//	        Text t4 = new Text(det.getAlarme_posicao());
		//	        Text t5 = new Text(det.getAlarme_posicao());
		//	        Text t6 = new Text(det.getAlarme_posicao());
		//	        Text t7 = new Text(det.getAlarme_posicao());

		//	        PdfPCell cell11 = new PdfPCell(new Paragraph("111"));
		//	        cell11.setBorderColor(BaseColor.BLUE);
		//	        cell11.setPaddingLeft(10);
		//	        cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
		//	        cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);

		PdfPCell cell1 = new PdfPCell(new Paragraph("QTD",tabheader));
		cell1.setBorderColor(BaseColor.WHITE);
		cell1.setBackgroundColor(new BaseColor(28, 110, 164));
		cell1.setPaddingLeft(5);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

		//  cell1.addElement(cell11);

		//	        PdfPCell cell22 = new PdfPCell(new Paragraph(det.getViolacoes().get(0).getAlarme()));
		//	        cell22.setBorderColor(BaseColor.BLUE);
		//	        cell22.setPaddingLeft(5);
		//	        cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
		//	        cell22.setVerticalAlignment(Element.ALIGN_MIDDLE);

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

		//	        PdfPCell cell7 = new PdfPCell(new Paragraph("DURACAO",tabheader));
		//	        cell7.setBorderColor(BaseColor.WHITE);
		//	        cell7.setBackgroundColor(new BaseColor(28, 110, 164));
		//	        cell7.setPaddingLeft(5);
		//	        cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		//	        cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
		//	        cell7.

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
		//	        table.addCell(cell7);
		//	        table.addCell(cell11);
		//	        table.addCell(cell22);


		//	        table.addCell(cell2);
		//	        table.addCell(cell3);

		//	        Document doc = new Document();
		//	        doc.add(table);



		for (int i = 0; i < k.length; i++) {






			//			 if(i == 0) {
			//				 
			//				 
			//				  com.itextpdf.text.Paragraph p1 = new com.itextpdf.text.Paragraph(k[i],smallBold);
			//				  p1.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			//				  
			//				  com.itextpdf.text.Paragraph p8 = new com.itextpdf.text.Paragraph(k[i + 1],smallBold);
			//				  p8.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			//				
			//				  com.itextpdf.text.Paragraph p9 = new com.itextpdf.text.Paragraph(k[i + 2],smallBold);
			//				  p9.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			//				  i = i + 2;
			//				 documento.add(p1);
			//				 documento.add(p8);
			//				 documento.add(p9);
			//				 
			//				 controle =true;
			//				 
			//			 }

			//			 if(i == 22) {
			//				 
			//				  com.itextpdf.text.Paragraph p18 = new com.itextpdf.text.Paragraph();
			//
			//				  p18.add(new com.itextpdf.text.Paragraph(k[i],smallBold));
			//				  p18.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
			//
			//				  
			//				 documento.add(p18);
			//				 
			//				 controle =true;
			//				 
			//			 }

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
				//					  p152.

				// documento.add(image2);
				documento.add( p151);
				documento.add(Chunk.NEWLINE);
				documento.add(Chunk.NEWLINE);
				//					  documento.add(Chunk.NEWLINE);
				//					  documento.add(Chunk.NEWLINE);
				//					  documento.add(Chunk.NEWLINE);
				//					  documento.add(Chunk.NEWLINE);
				documento.add( p153);
				documento.add(Chunk.NEWLINE);
				//				  documento.add(Chunk.NEWLINE);
				documento.add( p152);
				documento.add(Chunk.NEWLINE);
				//				  documento.add(Chunk.NEWLINE);
				//				 documento.
				i=i+1;
				controle =true;


			}


			if (i == 1 ) {
				//	 k[i] = k[i].replaceAll("\\cM?\r?\n", "");

				com.itextpdf.text.Paragraph p11 = new com.itextpdf.text.Paragraph(k[i],MDBold);
				p11.setAlignment(com.itextpdf.text.Element.ALIGN_RIGHT);
				//				  p11.setPaddingTop(-3f);
				// documento.add(image2);
				documento.add( p11);
				documento.add(Chunk.NEWLINE);
				//				 i=i+1;
				controle =true;


			}


			if (i == 2 ) {
				//	 k[i] = k[i].replaceAll("\\cM?\r?\n", "");

				int indexini = k[i+1].indexOf(" Direito da : ");
				//				 int indexfim = k[i].indexOf("gov.br");
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
			//			 if (i == 10 ) {


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

				//				 com.itextpdf.text.Paragraph p1123 = new com.itextpdf.text.Paragraph(ee,MDBold);
				//				 p1123.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
				//				 documento.add( p1123);
				//				 controle =true; 


				//				 com.itextpdf.text.Paragraph p1124 = new com.itextpdf.text.Paragraph(ee,NORMAL);
				//				 p1124.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
				//				 documento.add( p1124);
				//				 controle =true; 


			}

			//			 if(k[i].contains(" dia citado : ")){
			//				 
			//				 int indexini = k[i].indexOf(" dia citado : ");
			////				 int indexfim = k[i].indexOf(", que ");
			//				 String ees = k[i].substring(indexini +1);
			//				 String pps = k[i].substring(0, indexini);
			////				 String ss = k[i].substring(indexfim);
			//				 
			//				 com.itextpdf.text.Paragraph p11220 = new com.itextpdf.text.Paragraph(pps,NORMAL);
			//				 p11220.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
			//				 p11220.add(new com.itextpdf.text.Phrase(ees,MDBold));
			////				 p11220.add(new com.itextpdf.text.Phrase(ss,NORMAL));
			//				 
			//				 documento.add(p11220);
			//				 controle =true; 
			//				 
			////				 com.itextpdf.text.Paragraph p1123 = new com.itextpdf.text.Paragraph(ee,MDBold);
			////				 p1123.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
			////				 documento.add( p1123);
			////				 controle =true; 
			//				 
			//				 
			////				 com.itextpdf.text.Paragraph p1124 = new com.itextpdf.text.Paragraph(ee,NORMAL);
			////				 p1124.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
			////				 documento.add( p1124);
			////				 controle =true; 
			//				 
			//				 
			//			 }




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

				//					 com.itextpdf.text.Paragraph p111 = new com.itextpdf.text.Paragraph(e,mail);
				//					 p111.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
				//					 documento.add( p111);
				//					 controle =true;
				//					 documento.add(Chunk.NEWLINE);

				//					 i=i+1;

			}else {

				//					 com.itextpdf.text.Paragraph p115 = new com.itextpdf.text.Paragraph(k[i],NORMAL);
				//					 p115.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED); 
				//					 documento.add( p115);
				//					 controle =true; 

			}



			//			 }


			if (i == 9 ) {

				table = PreencherValores(table,det);

				documento.add(table);

				controle =true;
				//				 
				//				  documento.add(Chunk.NEWLINE);
				//				  documento.add(Chunk.NEWLINE);
				//				  documento.add(Chunk.NEWLINE);



			}



			//			 if (i == 6 ) {
			//				 
			//				 com.itextpdf.text.Paragraph p110 = new com.itextpdf.text.Paragraph(k[i],MDBold);
			//				  p110.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			//				 documento.add( p110);
			//				 
			//				 controle =true;
			//								 
			//			 }
			//			 
			//			 if (i == 7 ) {
			//				 
			//				 com.itextpdf.text.Paragraph p110 = new com.itextpdf.text.Paragraph(k[i],MDBold);
			//				  p110.setAlignment(com.itextpdf.text.Element.ALIGN_LEFT);
			//				 documento.add( p110);
			//				 
			//				 controle =true;
			//				
			//				 
			//			 }





			if (i == 12 ) {

				com.itextpdf.text.Paragraph p12 = new com.itextpdf.text.Paragraph(k[i],MDBold);
				p12.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

				com.itextpdf.text.Paragraph p13 = new com.itextpdf.text.Paragraph(k[i +1],MDBold);
				p13.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

				p12.add(p13);

				i = i + 4;
				documento.add( p12);
				//	 documento.add( p13);

				controle =true;


			}



			if (i == 10) {

				documento.add(image3);

				i=i+1;

				controle =true;


			} 


			if (i == 11) {

				//				 documento.add(image3);
				//				 
				//				 i=i+1;

				controle =true;


			} 


			if (controle){

				controle =false; 


			}else

			{


				com.itextpdf.text.Paragraph p36 = new com.itextpdf.text.Paragraph(k[i],NORMAL);
				p36.setAlignment(com.itextpdf.text.Element.ALIGN_JUSTIFIED);
				//				 p36.setExtraParagraphSpace(2f);
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

	private PdfPTable PreencherValores(PdfPTable table, Detento det) {


		PdfPTable tab = table;

		Font tabcontent = new Font(Font.FontFamily.COURIER, 9,
				Font.NORMAL);
		tabcontent.setColor(BaseColor.GRAY);

		int index =0; 

		for (int i = 0; i < det.getViolacoes().size(); i++) {

			index = i + 1;
			String s = "" + index;

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

			PdfPCell cell7 = new PdfPCell(new Paragraph(s,tabcontent));
			cell7.setBorderColor(BaseColor.WHITE);
			cell7.setBackgroundColor(new BaseColor(208, 228, 245));

			//	        cell7.setPaddingLeft(5);
			cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell7);

			PdfPCell cell8 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getAlarme(),tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell8.setBorderColor(BaseColor.WHITE);
			cell8.setBackgroundColor(new BaseColor(208, 228, 245));
			cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell8);

			PdfPCell cell88 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getDuracaoalarme(),tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell88.setBorderColor(BaseColor.WHITE);
			cell88.setBackgroundColor(new BaseColor(208, 228, 245));
			cell88.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell88.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell88);

			PdfPCell cell9 = new PdfPCell(new Paragraph(det.getProcesso(),tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell9.setBorderColor(BaseColor.WHITE);
			cell9.setBackgroundColor(new BaseColor(208, 228, 245));
			cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell9);

			PdfPCell cell10 = new PdfPCell(new Paragraph(det.getVep(),tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell10.setBorderColor(BaseColor.WHITE);
			cell10.setBackgroundColor(new BaseColor(208, 228, 245));
			cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell10);

			PdfPCell cell11 = new PdfPCell(new Paragraph(datini,tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell11.setBorderColor(BaseColor.WHITE);
			cell11.setBackgroundColor(new BaseColor(208, 228, 245));
			cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell11.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell11);

			PdfPCell cell12 = new PdfPCell(new Paragraph(datfim,tabcontent));
			//	        cell7.setBorderColor(BaseColor.BLUE);
			//	        cell7.setPaddingLeft(5);
			cell12.setBorderColor(BaseColor.WHITE);
			cell12.setBackgroundColor(new BaseColor(208, 228, 245));
			cell12.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell12.setVerticalAlignment(Element.ALIGN_MIDDLE);
			tab.addCell(cell12);

			//	        PdfPCell cell13 = new PdfPCell(new Paragraph(det.getViolacoes().get(i).getDuracao(),tabcontent));
			////	        cell7.setBorderColor(BaseColor.BLUE);
			////	        cell7.setPaddingLeft(5);
			//	        cell13.setBorderColor(BaseColor.WHITE);
			//	        cell13.setBackgroundColor(new BaseColor(208, 228, 245));
			//	        cell13.setHorizontalAlignment(Element.ALIGN_CENTER);
			//	        cell13.setVerticalAlignment(Element.ALIGN_MIDDLE);
			//	        tab.addCell(cell13);

		}


		return tab;
	}



	public void AtualizarQuadro(){

		int soma = getDetentoEnviado().size() + getDetentosREnviado().size();



		CalcularQtdPendentes();

		lbltotal.setText(" " + getDetentoFinal().size());
		lblsucesso.setText(" " +getDetentoEnviado().size());
		lblerro.setText(" "+ getDetentoErro().size());
		lblpendente.setText(" "+ getDetentosPendetes().size());
		lbltotalenv.setText(" " + getDetentosREnviado().size());
		toltipreenviados.setText(" " + getDetentosREnviado().toString());
		lbltotalenviado.setText(" " + getDetentoEnviado().size());

		ExisteEnvio();


	}


	public void CalcularQtdPendentes(){


		detentosPendetes = new ArrayList<Detento>();

		isprimeiro=false;

		for (int i = 0; i < getDetentoFinal().size(); i++) {

			Detento det = getDetentoFinal().get(i);

			if(det.getStatusenvio().equals("PENDENTE")) {

				if(!detentosPendetes.contains(det)){


					detentosPendetes.add(det);

				}else {




				}


			}



		}


	}


	//	public void CalcularQtdPendentescont(){
	//		
	//		
	////		detentosPendetes = new ArrayList<Detento>();
	//		
	////		isprimeiro=false;
	//		
	//		for (int i = 0; i < getDetentoFinal().size(); i++) {
	//			
	//			Detento det = getDetentoFinal().get(i);
	//			
	//			if(det.getStatusenvio().equals("PENDENTE")) {
	//				
	//				if(!detentosPendetescont.contains(det)){
	//					
	//					
	//					detentosPendetescont.add(det);
	//					
	//				}else {
	//					
	//					
	//					
	//					
	//				}
	//				
	//				
	//			}
	//			
	//			
	//			
	//		}
	//		
	//	
	//}


	@FXML
	void refresh() {

		//		CalcularQtdPendentes();
		toolbar.setDisable(false);
		//		servicolote.reset();
		trace.textProperty().unbind();
		trace.setText("Execução Reiniciada");
		jtgbcriartarefa.setSelected(true);
		jtgbcriartarefa.setDisable(true);
		progressind.setVisible(true);
		servicolote.restart();
		//		CalcularQtdPendentes();
		AtualizarQuadro();
		AtualizarQuadroRegistros();
		//		ControleServiceIniciar();



	}

	@FXML
	void play() {



		toolbar.setDisable(false);
		btstop.setDisable(false);
		//		btpause.setDisable(false);
		trace.textProperty().unbind();
		trace.setText("Execução Iniciada");
		//trace.setText("Execução Iniciada");
		//		servicolote.start();
		progressind.setVisible(true);
		//		CalcularQtdPendentes();
		AtualizarQuadro();
		AtualizarQuadroRegistros();
		jtgbcriartarefa.setSelected(true);
		jtgbcriartarefa.setDisable(true);
		//		ControleServiceIniciar();



	}

	@FXML
	void pause() {

		trace.textProperty().unbind();
		trace.setText("Execução Pausada");
		progressind.setVisible(false);
		try {
			servicolote.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		CalcularQtdPendentes();
		AtualizarQuadro();
		AtualizarQuadroRegistros();
		//		ControleServiceIniciar();



	}


	@FXML
	void stop() {


		//		if(fiveSecondsWonder != null && fiveSecondsWonder.getStatus() == Status.RUNNING){
		//			
		//			fiveSecondsWonder.stop();
		//			lblretornoautomatico.setText("Serviço Parado");
		//			lblretornoautomatico.setVisible(true);
		//			
		//			
		//			
		//		}

		toolbar.setDisable(false);
		//		btpause.setDisable(true);
		btplay.setDisable(false);
		btrefresh.setDisable(false);
		servicolote.cancel();
		//		btstop.setDisable(true);
		//		progressind.setVisible(false);
		//		progressindtarefa.setVisible(false);
		jtgbcriartarefa.setDisable(true);
		jtgbcriartarefa.setSelected(false);
		//		CalcularQtdPendentes();
		AtualizarQuadro();
		AtualizarQuadroRegistros();
		//		ControleServiceParar();
		trace.textProperty().unbind();
		trace.setText("Execução - Parada");
	}


	//	@FXML
	public void criarexcel() {

		File diretorio = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "resultado");

		if(diretorio.isDirectory()){
			
			
		}else {
			
			diretorio.mkdirs();
			
		}
		

		File relatorio = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "resultado" +"\\" + "resultado_envio_" + FormatadorData(new Date()) + ".xlsx");

		if(criarexcel == null){

			criarexcel = new CriarExcel(relatorio.getPath(), detentoErro,detentoEnviado,detentosPendetes);



		}else {

			criarexcel.setSAMPLE_XLSX_FILE_PATH(relatorio.getPath());
			criarexcel.setDetentos(detentoErro);
			criarexcel.setDetentosucesso(detentoEnviado);
			criarexcel.setDetentospendentes(detentosPendetes);




		}

		try {
			criarexcel.Criar();
			trace.textProperty().unbind();
			trace.setText("Arquivo Criado: " + relatorio.getPath());
			//				btoficio.setDisable(true);
			//	btsalvarxlsx.setDisable(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			trace.textProperty().unbind();
			trace.setText("Erro na Criação do Arquivo: " + e);
		}


//		criarexcel=null;



	}


	private void ExisteEnvio(){



		if(detentosREnviado.size() > 0 || detentoErro.size() > 0) {


			btregerar.setDisable(false);


		}else {

			btregerar.setDisable(true);

		}

	}


	public HashMap<Integer, String> valoresConfig(){

		HashMap<Integer, String> aux = manipulador.getLines();

		Set<Integer> keys = aux.keySet();

		for (Integer integer : keys) {

			System.out.println("valores conf: " + integer +"valor: " + aux.get(integer));




		}




		return aux;
	}



	private void CriarMatriz(){

		boolean existeMatrix = false;

		File matriz = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "resultado" +"\\" + "matriz" + "\\" + "matriz" + ".xlsx");


		if(criarexcel == null){

			criarexcel = new CriarExcel(matriz.getPath());


		}else {

			criarexcel.setSAMPLE_XLSX_FILE_PATH_MATRIX(matriz.getPath());
			criarexcel.setFile(matriz);

		}


		//criar.setSAMPLE_XLSX_FILE_PATH_MATRIX(matriz.getPath());


		existeMatrix = criarexcel.ExisteMatrix();

		if(existeMatrix){

			trace.setText(" matrix ja criada: ");
			System.out.println("matrix ja criada:");


		}else {

			trace.setText(" criando matrix ");
			System.out.println("criando matrix");

			try {
				criarexcel.CriarMatrix();

				trace.setText(" matrix criada ");
				System.out.println("matrix criada");

			} catch (IOException e) {
				e.printStackTrace();

				trace.setText("erro ao criar matrix: " + e.getMessage());
				System.out.println("erro matrix : " + e.getMessage());



			}


		}


//		criarexcel = null;
		




	}



	public void AddLinhaMatriz(List<Detento> dets) throws InvalidFormatException, IOException{



		File matriz = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "resultado" +"\\" + "matriz" + "\\" + "matriz" + ".xlsx");

		System.out.println("path matriz  : " + matriz.getPath());

		CriarExcel criar = new CriarExcel(matriz.getPath(),dets,registrosSubstitutosemNoti);

		//	criar.setSAMPLE_XLSX_FILE_PATH_MATRIX(matriz.getPath());


		criar.AtualizarMatriz(dets);
		//				det.setMatrix(true);
		//				trace.textProperty().unbind();
		//				trace.setText("linha detento: matrix crianda ");
		System.out.println("linha detento: matrix criada");


	}




	public void Escrever(){



		File matriz = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "resultado" +"\\" + "matriz" + "\\" + "matriz" + ".xlsx");

		System.out.println("path matriz escrever  : " + matriz.getPath());
		logger.info("path matriz escrever  : " + matriz.getPath());


		if(criarexcel == null){

			criarexcel = new CriarExcel(matriz.getPath(),detentoEnviado,registrosSubstitutosemNoti);


		}else {

			criarexcel.setSAMPLE_XLSX_FILE_PATH_MATRIX(matriz.getPath());
			criarexcel.setSubstitutosSemNotificacao(registrosSubstitutosemNoti);
			criarexcel.setDetentosMatriz(detentoEnviado);
			criarexcel.setFile(matriz);


		}


		try {
			criarexcel.write();
			trace.textProperty().unbind();
			trace.setText("Matriz Atualizada: " + matriz.getPath());
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			trace.textProperty().unbind();
			trace.setText("Erro na Atualizacao da Matriz InvalidFormatException: " + e);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			trace.textProperty().unbind();
			trace.setText("Erro na Atualizacao da Matriz IOException: " + e);
		}
		
		logger.info("escrever matriz " + matriz.getPath());

//		criarexcel = null;


	}



	public HashMap<Integer, Filtro> valoresFilter(){

		HashMap<Integer, Filtro> aux = manipulador.getLinesfiter();

		Set<Integer> keys = aux.keySet();

		for (Integer integer : keys) {

			System.out.println("valores conf: " + integer +"valor: " + aux.get(integer));




		}




		return aux;
	}



	private void AtualizarQuadroRegistros(){

		int sub = registrosNotificar.size();
		//int sub2 = registrosNotificar.size();
		int violaremovida =0;
		int totalregistrofiltrado = qtdregistrolidos - sub;
		
		int totalregistrofiltradosys = qtdregistrolidos + registroFiltrados.size();

		int totall = qtdregistrolidos + objExclude.size();
		
		int totalregistro =0;
		int totaldetentoremovido =0;

		violaremovida = detentoFinal.size();
		totalregistrofiltrado = detentosrepetidos.size();
		totalregistro = registros.size();
		totaldetentoremovido = detentosremovidos.size();
		lbltotreg.setText("" + totall);
		lbltotregfiltrado.setText("" + qtddetentorepetido);
		lblviolaremovida.setText("" + violaremovida);
		lbldetentoremovidos.setText("" + registrosNotificar.size());
		lbltotreg1.setText("" + qtdregistrolidos);
//		lblfiltrosaplicados.setText("" + filtros.size());
//		jtooltipfiltro.setText("" + filtros.toString());
		//		lblfilternome.setText("" + filtros.toString());
		lbltotregfil.setText("" + registroFiltrados.size());
		lbltotviolavazia.setText("" + registrosSubstitutosemNoti.size());
		lbltotnotsubs.setText("" + registrosSubstitutosAll.size());
		lbltotnotcancela.setText("" + registroscancela.size());
		jtopsubstituta.setText(registrosSubstitutosAll.toString());
		jtoltipcancela.setText(registroscancela.toString());
		jtoltipviolavazia.setText(registrosSubstitutosemNoti.toString());

		System.out.println("registro duplicidade qtd: "  + registrosduplicidade.size());
		System.out.println("registro duplicidade: "  + registrosduplicidade.toString());


		System.out.println("registro substituto qtd: "  + registrosSubstitutosAll.size());
		System.out.println("registro substituto all: "  + registrosSubstitutosAll.toString());


		System.out.println("registro substituto sem notificação qtd: "  + registrosSubstitutosemNoti.size());
		System.out.println("registro substituto sem notificação : "  + registrosSubstitutosemNoti.toString());

		System.out.println("registro registroFiltrados qtd: "  + registroFiltrados.size());
		System.out.println("registro registroFiltrados  : "  + registroFiltrados.toString());
		




	}


	public void FinalizarExecucao(){


		String novonome = arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(),"");

		novonome = novonome + arquivoLocalizado.getName().substring(0, arquivoLocalizado.getName().indexOf(".xlsx"));

		File fil = new File(novonome + ".xlsx");



		File arquivoNovo = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "") + nomeArquivo + "-win" +".xlsx");

		try {
			//				arquivoNovo.createNewFile();

			ManipuladorArquivo.copy(arquivoLocalizado, arquivoNovo);
			arquivoLocalizado.renameTo(new File(novonome + "_" + indexarq + ".xlsx"));
			indexarq++;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		arquivoNovo.renameTo(fil);


		System.out.println(" fim execução nome arquivo localizado: "+ arquivoLocalizado.getPath());

//		File fill = new File(arquivoNovo.getPath().replace(arquivoNovo.getName(), "") + "processados" +"\\" + nomeArquivo + "_"+indexarq+".xlsx");
//
//		File diretori = new File(arquivoNovo.getPath().replace(arquivoNovo.getName(), "") + "processados" +"\\");
//
//		if(diretori.isDirectory()){
//
//			System.out.println("é diretorio: " + diretori.getPath());				
//		}else {
//
//			diretori.mkdirs();
//			System.out.println("diretorio criado: " + diretori.getPath());
//
//
//		}
//
//		System.out.println(" fim execução nome arquivo novo: "+ arquivoNovo.getPath());


//		try {
//			ManipuladorArquivo.copy(arquivoLocalizado, fill,true);
//			//				arquivoLocalizado.delete();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		SalvarListas();

	}


	public boolean IniciaExecucao(){

		boolean jafoiexecutado = false;

		jafoiexecutado = arquivoLocalizado.getName().contains("_0");	

		System.out.println(" inicio execução nome arquivo: "+ arquivoLocalizado.getName());


		return jafoiexecutado;
	}


	void borrar() {

		//		GaussianBlur blur = new GaussianBlur(55);
		//		stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

		stageManager.Borrar();

	}	





	void desborrar() {


		//		stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(null);

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
						Thread.sleep(2000);

						return 1;
					}
				};
			}

			@Override
			protected void succeeded() {

				stageManager.DesBorrar();

			}



		}.start();




	}


	public void InicializarListas(){

	//	RestartarListas();

		//		registros.clear();
		//		detentos.clear();
		//		detentoFinal.clear();

		lbltotalenviado.setText("0");


		registroFiltrados = new ArrayList<Registro>();

		registrosSubstituto= new ArrayList<Registro>();
		//		   
		violacaoesRemovidas = new ArrayList<Violacao>();
		//		   
		detentosremovidos = new ArrayList<Detento>();
		//		 
		detentosrepetidos = new ArrayList<Detento>();

		registroscancela = new ArrayList<Registro>();

		registrosduplicidade = new ArrayList<Registro>();

		detentosviolavazia = new ArrayList<Detento>();

		registrosSubstitutosAll = new ArrayList<Registro>();
		registrosSubstitutosemNoti= new ArrayList<Registro>();


		detentoErro = new ArrayList<Detento>();
		detentoEnviado = new ArrayList<Detento>();
		detentosREnviado = new ArrayList<Detento>();


//		registroFiltrados= new ArrayList<Registro>();

//		registrosNotificar= new ArrayList<Registro>();


		AtualizarQuadro();
		AtualizarQuadroRegistros();


	}







	//	@FXML
	//	void tooglereg() {
	//		
	//		if (showreg) {
	//			
	//		anchoresiult.setVisible(false);
	//		showreg =false;
	//		
	//		}else {
	//			
	//			anchoresiult.setVisible(true);
	//			
	//			showreg =true;
	//		}
	//
	//	}


	//	@FXML
	//	void toogleshow() {
	//		
	//		
	//		
	//		if (showmail) {
	//			
	//			Image image = new Image(getClass().getResourceAsStream("/images/icons8-menu-2-100.png"),64.0,64.0,true,true);
	//			imgvshow.setImage(image);
	//			btshow.setGraphic(imgvshow);
	////			btoficio.setTextFill(Color.WHITE);
	//			
	//			vvb.setVisible(false);
	//			
	//			
	//		showmail =false;
	//		
	//		}else {
	//			
	//			Image image = new Image(getClass().getResourceAsStream("/images/icons8-menu-2-100 (1).png"),64.0,64.0,true,true);
	//			imgvshow.setImage(image);
	//			btshow.setGraphic(imgvshow);
	//			
	//			vvb.setVisible(true);
	//			
	//			showmail =true;
	//		}
	//		
	//
	//	}
	//	
	//	
	//	
	//	@FXML
	//	void rotateleftmail() {
	//		
	//		RotateTransition	rotate = new RotateTransition(Duration.millis(1000), btshow);
	//	//	trans.setFromAngle(0.0);
	//		rotate.setToAngle(360.0);
	//		rotate.setAxis(new Point3D(360.0,0.0, 0.0));
	//		// Let the animation run forever
	//		rotate.setCycleCount(1);
	//		// Reverse direction on alternating cycles
	//		rotate.setAutoReverse(false);
	//		// Play the Animation
	//		rotate.play();
	//		
	//
	//	}
	//	
	//	
	//	@FXML
	//	void rotaterightmail() {
	//		
	//		RotateTransition	rotate = new RotateTransition(Duration.millis(1000), btshow);
	//	//	trans.setFromAngle(0.0);
	//		rotate.setToAngle(360.0);
	//		rotate.setAxis(new Point3D(0.0,360.0, 0.0));
	//		// Let the animation run forever
	//		rotate.setCycleCount(1);
	//		// Reverse direction on alternating cycles
	//		rotate.setAutoReverse(false);
	//		// Play the Animation
	//		rotate.play();
	//
	//		
	//
	//	}


	//	@FXML
	//	void rotateleft() {
	//		
	//		RotateTransition	rotate = new RotateTransition(Duration.millis(1000), btshow);
	//	//	trans.setFromAngle(0.0);
	//		rotate.setToAngle(360.0);
	//		rotate.setAxis(new Point3D(360.0,0.0, 0.0));
	//		// Let the animation run forever
	//		rotate.setCycleCount(1);
	//		// Reverse direction on alternating cycles
	//		rotate.setAutoReverse(false);
	//		// Play the Animation
	//		rotate.play();
	//		
	//
	//	}


	//	@FXML
	//	void rotateright() {
	//		
	//		RotateTransition	rotate = new RotateTransition(Duration.millis(1000), btshow);
	//	//	trans.setFromAngle(0.0);
	//		rotate.setToAngle(360.0);
	//		rotate.setAxis(new Point3D(0.0,360.0, 0.0));
	//		// Let the animation run forever
	//		rotate.setCycleCount(1);
	//		// Reverse direction on alternating cycles
	//		rotate.setAutoReverse(false);
	//		// Play the Animation
	//		rotate.play();
	//
	//		
	//
	//	}

	@FXML
	void filtro(){


		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/filtro.fxml"));

			Parent root =loader.load();

			filtrocontrol = (FiltroController)loader.getController();

			//        configcontroler.Inicializar(detentoEnviado,detentoErro);

			filtrocontrol.InitList(filtros);

			filtrocontrol.InitManipulador(manipulador);


			filtrocontrol.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT FILTRO");

					filtros.clear();

					filtros = filtrocontrol.getFiltrosentite();

					System.out.println("filtros main:" + filtros.toString());

					//	VerificarConfFiltroInit();

					//            	VerificarConfFiltroInitFiltro();

					filtrocontrol.fechar();

					desborrar();


				}

			});


			filtrocontrol.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em salvar FILTRO");

					//            	filtrocontrol.salvarnome();

					//            	nomeArquivo = configcontroler.getNomeArquivo();
					//            	
					//            	nomeModelo = configcontroler.getNomeModelo();

					filtrocontrol.salvarfiltro(event);

					filtros.clear();

					filtros = filtrocontrol.getFiltrosentite();

					VerificarConfFiltroInitFiltro();


					System.out.println("filtros main salvar:" + filtros.toString());

					filtrocontrol.fechar();

					desborrar();





				}

			});




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Filtro");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//            GaussianBlur blur = new GaussianBlur(55);

			//            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//    		stageManager.Borrar();

			Scene scene = new Scene(root);

			scene.setFill(null);
			s1.setScene(scene);
			// 
			s1.show();

			//    		 s1.setOnCloseRequest(event ->
			//
			//    		 stageManager.DesBorrar()
			//    		
			//    		);


		}catch (Exception e) {

			System.out.println("erro controle de filtro:"+ e);

		}


		borrar();

	}

	//	private void VerificarConexaoNet(){
	//		
	//		
	//		if(conected) {
	//			
	//			Image imgok = new Image(getClass().getResourceAsStream("/images/wifi.png"),64.0,64.0,true,true);
	//			imgvnet.setImage(imgok);
	////			lblnet.setText("Conectado");
	//			lblnet.setTextFill(Color.TURQUOISE);
	//			
	//			
	//			
	//			Image imgo = new Image(getClass().getResourceAsStream("/images/icons8-servidor-100 (1).png"),30.0,30.0,true,true);
	//			imgvservern.setImage(imgo);
	//			lblservern.setText("OK");
	//			lblservern.setTextFill(Color.TURQUOISE);
	//			
	//			
	//		}else {
	//			
	//			
	//			Image imgerr = new Image(getClass().getResourceAsStream("/images/wifi (2).png"),64.0,64.0,true,true);
	//			imgvnet.setImage(imgerr);		
	//			lblnet.setTextFill(Color.CRIMSON);
	//			
	//			Image imgo = new Image(getClass().getResourceAsStream("/images/icons8-servidor-100.png"),30.0,30.0,true,true);
	//			imgvservern.setImage(imgo);
	//			lblservern.setText("NOK");
	//			lblservern.setTextFill(Color.CRIMSON);
	//			
	//			
	//		}
	//		
	//		
	//	}


	//	@FXML
	//	void pararnet() {
	//		
	//
	//		timeping.stop();
	//		
	//
	//	}


	//	public String[] TesteNet(){
	//		
	//		List<String> array = new ArrayList<String>();
	//		
	////		array.add("java PingIp localhost");
	////		array.add("192.168.0.5");
	//		array.add("172.217.29.110");
	////		array.add("216.58.222.78");
	////		array.add("186.192.90.5");
	////		array.add("157.240.14.35");
	//		
	//		
	//		  String[] itemsArray = new String[array.size()];
	//	        itemsArray = array.toArray(itemsArray);
	//		
	//
	//		
	//		return itemsArray;
	//		
	//	}




	//	private void TimePing(){
	//		
	//		if(timeping == null) {
	//			
	//			 timeping = new Timeline(new KeyFrame(Duration.millis(5000), new EventHandler<ActionEvent>() {
	//					
	//					
	//				    @Override
	//				    public void handle(ActionEvent event) {
	//				    	
	//				        System.out.println("ping chamado");
	//				        
	//				        if (consegueConectar("http://www.google.com.br")) {
	//							System.out.println("conseguiu conectar");
	//				           
	//							lblnet.textProperty().unbind();
	//							lblnet.setText("OK");
	//							conected = true;
	//							VerificarConexaoNet();
	//				         	
	//						} else {
	//							System.out.println("nao conseguiu conectar");
	//							
	//							lblnet.textProperty().unbind();
	//							lblnet.setText("NOK");
	//							conected = false;
	//							VerificarConexaoNet();
	//						}
	//				        
	//				        
	//				        
	//
	//							
	////							subcena.getRotate();
	//				        
	//				        
	////				       boolean bo =  exists("http://www.facebook.com.br");
	////				       trace.setText("V OU F : " + bo);
	//				        
	//
	//			         	
	//			         	
	////					    Platform.runLater(() ->
	////					    {
	////							trace.textProperty().unbind();
	////				         	trace.setText("");				       
	////					    });
	//				        
	//				        
	//				    }
	//
	//
	//
	//			        	 
	//			         }));
	//				
	//			 timeping.setCycleCount(Animation.INDEFINITE);	         
	//			 timeping.setAutoReverse(false);
	//			 timeping.play();
	//			
	//			
	//		}else {
	//			
	//			timeping.play();
	//			
	//			
	//		}
	//		
	//		
	//		
	//		
	//		
	//	}


	//	public static boolean consegueConectar(String address) {
	//		try {
	//			URL url = new URL(address);
	//			URLConnection connection = url.openConnection();
	//			connection.connect();
	//			return true;
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//			return false;
	//		}
	//	}



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


	//	public void testeIp(String[] ip){
	//		
	//	    if (ip.length == 1) {
	//	      InetAddress address = null;
	//	      try {
	//	        address = InetAddress.getByName(ip[0]);
	//	      } catch (UnknownHostException e) {
	//	        System.out.println("Cannot lookup host "+ip[0]);
	//            trace.textProperty().unbind();
	//            trace.setText("Cannot lookup host "+ip[0]);
	//	        return;
	//	      }
	//	      try {
	//	        if (address.isReachable(100)) {
	//	          long nanos = 0;
	//	          long millis = 0;
	//	          long iterations = 0;
	//	          while (true) {
	//	            iterations++;
	//	            try {
	//	              nanos = System.nanoTime();
	//	              address.isReachable(10); // this invocation is the offender
	//	              nanos = System.nanoTime()-nanos;
	//	            } catch (IOException e) {
	//	              System.out.println("Failed to reach host");
	//	            }
	//	            millis = Math.round(nanos/Math.pow(10,6));
	//	            System.out.println("Resposta do IP: "+address.getHostAddress()+" com de tempo="+millis+" ms");
	//	          
	//	            trace.textProperty().unbind();
	//	            trace.setText("Resposta do IP: "+address.getHostAddress()+" com de tempo="+millis+" ms");
	//	            try {
	//	              Thread.sleep(Math.max(0, 1000-millis));
	//	            } catch (InterruptedException e) {
	//	              break;
	//	            }
	//	          }
	//	          System.out.println("Iterations: "+iterations);
	//	            trace.textProperty().unbind();
	//	            trace.setText("Iterations: "+iterations);
	//	        } else {
	//	          System.out.println("Host "+address.getHostName()+" is not reachable even once.");
	//	        }
	//	      } catch (IOException e) {
	//	        System.out.println("Network error.");
	//            trace.textProperty().unbind();
	//            trace.setText("Network error.");
	//	      }
	//	    } else {
	//	      System.out.println("Usage: java isReachableTest <host>");
	//	    }
	//		
	//	  }

	@FXML
	void refreshauto() {


		System.out.println("refresh execução automatica");

		fiveSecondsWonder.playFromStart();


	}

	@FXML
	void playauto() {



		toolbarauto.setDisable(false);

		if(fiveSecondsWonder != null && fiveSecondsWonder.getStatus() == Status.PAUSED ){
			fiveSecondsWonder.play();

		}else {

			fiveSecondsWonder.playFromStart();


		}
		System.out.println("play execução automatica");


	}

	@FXML
	void pauseauto() {

		System.out.println("pause execução automatica");
		fiveSecondsWonder.pause();



	}


	@FXML
	void stopauto() {

		System.out.println("para execução automatica");

		fiveSecondsWonder.stop();
		progressindteste.setVisible(false);


	}


	@FXML
	void opensucesso() {

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detentotabela.fxml"));

			Parent root =loader.load();

			detentotabelacontrol = (DetentoTabelaController)loader.getController();

			detentotabelacontrol.Inicializar(detentoEnviado,this);


			detentotabelacontrol.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT CONFIG");


					detentotabelacontrol.fechar();
					AtualizarQuadro();

					desborrar();


				}

			});


			//	        detentotabelacontrol.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {
			//
			//	            @Override
			//	            public void handle(ActionEvent event) {
			//	            	
			//	            	System.out.println("passou em salvar nome arquivo");
			//
			////	            	configcontroler.salvarnome();
			////	            	
			////	            	nomeArquivo = configcontroler.getNomeArquivo();
			////	            	
			////	            	nomeModelo = configcontroler.getNomeModelo();
			//	            	
			//	            	detentotabelacontrol.fechar();
			//	            	AtualizarQuadro();
			//	            	desborrar();
			//	            	
			////	            	System.out.println("nomearquivo: " + nomeArquivo);
			//	            	
			//	            	
			//	            
			//	            }
			//	        
			//	        });




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Tabela Sucesso");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);

			scene.setFill(null);
			s1.setScene(scene);
			// 
			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de tabela sucesso:"+ e);

		}


		borrar();

	}


	@FXML
	void openerro() {

		System.out.println("abrir erro");

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detentotabela.fxml"));

			Parent root =loader.load();

			detentotabelacontrol = (DetentoTabelaController)loader.getController();

			detentotabelacontrol.Inicializar(detentoErro,this);


			detentotabelacontrol.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT CONFIG");


					detentotabelacontrol.fechar();
					AtualizarQuadro();

					desborrar();


				}

			});


			//	        detentotabelacontrol.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {
			//
			//	            @Override
			//	            public void handle(ActionEvent event) {
			//	            	
			//	            	System.out.println("passou em salvar Tabela Erro");
			//
			////	            	configcontroler.salvarnome();
			////	            	
			////	            	nomeArquivo = configcontroler.getNomeArquivo();
			////	            	
			////	            	nomeModelo = configcontroler.getNomeModelo();
			//	            	
			//	            	detentotabelacontrol.fechar();
			//	            	AtualizarQuadro();
			//	            	desborrar();
			//	            	
			////	            	System.out.println("nomearquivo: " + nomeArquivo);
			//	            	
			//	            	
			//	            
			//	            }
			//	        
			//	        });


			//	        detentotabelacontrol.getButtomcoluna().editCommitEvent();




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Tabela Erro");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);

			scene.setFill(null);
			s1.setScene(scene);
			// 
			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de tabela sucesso:"+ e);

		}


		borrar();

	}



	@FXML
	void openpendente() {

		System.out.println("abrir pendente");

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detentotabela.fxml"));

			Parent root =loader.load();

			detentotabelacontrol = (DetentoTabelaController)loader.getController();

			detentotabelacontrol.Inicializar(detentosPendetes,this);


			detentotabelacontrol.getBtfechar().setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					System.out.println("passou em LOGOUT pendente");


					detentotabelacontrol.fechar();
					AtualizarQuadro();

					desborrar();


				}

			});


			//	        detentotabelacontrol.getBtsalvar().setOnAction(new EventHandler<ActionEvent>() {
			//
			//	            @Override
			//	            public void handle(ActionEvent event) {
			//	            	
			////	            	System.out.println("passou em salvar nome arquivo");
			//
			////	            	configcontroler.salvarnome();
			////	            	
			////	            	nomeArquivo = configcontroler.getNomeArquivo();
			////	            	
			////	            	nomeModelo = configcontroler.getNomeModelo();
			//	            	
			//	            	detentotabelacontrol.fechar();
			//	            	AtualizarQuadro();
			//	            	desborrar();
			//	            	
			////	            	System.out.println("nomearquivo: " + nomeArquivo);
			//	            	
			//	            	
			//	            
			//	            }
			//	        
			//	        });




			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.setTitle("Tabela Pendente");
			//stageManager.getPrimaryStage().initStyle(StageStyle.TRANSPARENT);
			//   Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-services-48.png"));
			// primaryStage.initStyle(StageStyle.UNDECORATED);
			//    s1.getIcons().add(imageIcon);

			//	            GaussianBlur blur = new GaussianBlur(55);

			//	            getStageManager().

			//         stageManager.getPrimaryStage().getOwner().getScene().getRoot().setEffect(blur);

			//	    		stageManager.Borrar();

			Scene scene = new Scene(root);

			scene.setFill(null);
			s1.setScene(scene);
			// 
			s1.show();

			//	    		 s1.setOnCloseRequest(event ->
			//
			//	    		 stageManager.DesBorrar()
			//	    		
			//	    		);


		}catch (Exception e) {

			System.out.println("erro controle de tabela pendente:"+ e);

		}


		borrar();



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




	void AbrirAnimacao() {

		try{

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/preload.fxml"));

			Parent root =loader.load();

			preloadcontrol = (PreloaderController)loader.getController();

			preloadcontrol.Inicializar();

			//		     


			//		     preloadcontrol.getBtabreMain().setOnAction(new EventHandler<ActionEvent>() {
			//
			//		         @Override
			//		         public void handle(ActionEvent event) {
			//		         	
			//
			//		        	 preloadcontrol.fechar();
			//		        	 desborrar();
			//		         
			//		         }
			//		     
			//		     });


			//		     preloadcontrol.getBtconfigurar().setOnAction(new EventHandler<ActionEvent>() {
			//
			//		         @Override
			//		         public void handle(ActionEvent event) {
			//		         	
			//
			//
			//		        	 preloadcontrol.fechar();
			//		        	 desborrar();
			//		        	 
			//
			//		         
			//		         }
			//		     
			//		     });


			//		     preloadcontrol.getBtgerardocumento().setOnAction(new EventHandler<ActionEvent>() {
			//
			//		         @Override
			//		         public void handle(ActionEvent event) {
			//		        	 
			//		        	 preloadcontrol.fechar();
			//		        	 desborrar();
			//	
			//		         
			//		         }
			//		     
			//		     });





			javafx.stage.Window win = new Popup() ;

			Stage s1 = new Stage();
			s1.initOwner(win);
			s1.initModality(Modality.APPLICATION_MODAL);
			// s1.setFill(null);
			s1.initStyle(StageStyle.TRANSPARENT);
			//		 		s1.setTitle("Configuração de Email");
			//		       Image imageIcon = new Image(getClass().getResourceAsStream("/images/icons8-database-50.png"));
			//		         s1.getIcons().add(imageIcon);



			Scene scene = new Scene(root);
			scene.setFill(null);
			scene.getRoot().setStyle("-fx-background-color: transparent;");


			s1.setScene(scene);
			s1.show();



		} catch (Exception e) {

			System.out.println("erro controle de preload:"+ e);

		}


		borrar();
		//				   opensplash();




	}






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
	//	}



	@FXML
	public void regerar(){



		System.out.println("reGerar oficio");

		criarexcel();
		Escrever();


	}

	private File VerificarArquivoDeConfig(){

		File f = null;

		String nomearquivoconf = "conf";

		//		String nomearquivofiltro = "filtro.txt";

		String path = System.getProperty("user.home");
		String pathfinal = path + "\\" + "Desktop";

		//		System.out.println("path  :" + pathfinal);
		//	trace.setText("path :" + pathfinal);

		List<File> arquivosconf = buscarArquivoPorNome(nomearquivoconf, pathfinal);

		f=RetornarArquivoValidoConf(arquivosconf);

		//		if((!f.exists()) && (f.getParent().isEmpty())){
		//			
		//			CriarDiretorio(f);
		//		}

		return f;

	}

	private File VerificarArquivoDeFiltro(){

		File f = null;
		String nomearquivofiltro = "filtro";

		String path = System.getProperty("user.home");
		String pathfinal = path + "\\" + "Desktop";

		//		System.out.println("path  :" + pathfinal);
		//	trace.setText("path :" + pathfinal);

		List<File> arquivosfiltro = buscarArquivoPorNome(nomearquivofiltro, pathfinal);

		f = RetornarArquivoValidoFiltro(arquivosfiltro);

				if((f == null) || (!f.isDirectory())){
					
					CriarDiretorio(f);
				}

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
					//					trace.textProperty().unbind();
					trace.setText("Arquivo Encontrado :" + fil.getName());

					//	progressind.setVisible(false);

				}

			}




		}

		return fil;



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

					//					trace.textProperty().unbind();
					trace.setText("Arquivo Encontrado :" + fil.getPath());

					//	progressind.setVisible(false);

				}

			}




		}

		return fil;



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



		PreencherAjuda();

	}

	//	}



	private void VerificarConfFiltroInit() {



		File arquivoconf = VerificarArquivoDeConfig();

		File arquivofiltro = VerificarArquivoDeFiltro();


		if(arquivofiltro != null && arquivoconf != null) {

			PreencherValoresArquivados(arquivoconf,arquivofiltro);

			arquivoFiltro = arquivofiltro;
			arquivoConf = arquivoconf;


		}else {

			
			trace.setText("Nenhum Arquivo de Configuração ou Filtro Encontrado.");	


		}

		//		if(nomeArquivo != null && nomeModelo != null){
		//				
		//				procurarArquivos();
		//				
		//				arquivoLocalizado = RetornarArquivoValido(arquivos);
		//				
		//				if(arquivoLocalizado != null){
		//					
		//					 CriarDiretorio(arquivoLocalizado);
		//		             trace.textProperty().unbind();
		//		             RecuperarInfoConf(arquivoConf);
		//		             RecuperarInfoFilter(arquivoFiltro);
		//		          //   manipulador = new ManipuladorArquivo(arquivoConf,arquivoFiltro);
		//		             
		//		 			PreencherValoresArquivados(arquivoConf,arquivoFiltro);
		//
		//					
		//				}
		//			
		//			
		//		}else {
		//			
		//			
		//			
		//
		//			
		//			
		//		}

		//		if(nomeArquivo != null && nomeModelo != null){
		//			
		//			procurarArquivos();
		//			
		//			arquivoLocalizado = RetornarArquivoValido(arquivos);
		//			
		//			if(arquivoLocalizado != null){
		//				
		//				 CriarDiretorio(arquivoLocalizado);
		//	             trace.textProperty().unbind();
		//	             RecuperarInfoConf(arquivoConf);
		//	             RecuperarInfoFilter(arquivoFiltro);
		//	          //   manipulador = new ManipuladorArquivo(arquivoConf,arquivoFiltro);
		//				
		//			}
		//			
		//			
		//			
		//			 
		//			
		//			
		//		}else {
		//			
		//			
		////			nomeArquivo = lines.get(0);
		////			nomeModelo = lines.get(1);
		////			this.usuarioconf= lines.get(2);
		////			this.senhaconf= lines.get(3);
		////			this.hostconf= lines.get(4); 
		////			this.protocoloconf= lines.get(5);
		////			this.portaconf= lines.get(6);
		//			
		//			
		//		}
		//		


		//		if(arquivofiltro != null && arquivoconf !=null){
		//			
		//			//manipulador = new ManipuladorArquivo(arquivoconf,arquivofiltro);
		//			PreencherValoresArquivados(arquivoconf,arquivofiltro);


		//			this.arquivoConf = arquivoconf;
		//			this.arquivoFiltro = arquivofiltro;
		//			this.nomeArquivo = lines.get(0);
		//			this.nomeModelo = lines.get(1);
		//			this.usuarioconf= lines.get(2);
		//			this.senhaconf= lines.get(3);
		//			this.hostconf= lines.get(4); 
		//			this.protocoloconf= lines.get(5);
		//			this.portaconf= lines.get(6);



		//			System.out.println("nome arquivo if :" + nomeArquivo);
		//			System.out.println("nome modelo if :" + nomeModelo);
		//			
		//		}else {
		//			
		//			
		//			
		//			trace.setText("Nenhum Arquivo de Configuração ou Filtro Encontrado.");
		//			
		//		}




		//    	if(usuarioconf != null && senhaconf != null && hostconf != null && protocoloconf != null && portaconf != null) {
		//			
		//    	
		//    		
		////    		lines= new HashMap<Integer, String>();
		////	
		////    		lines= manipulador.getLines();
		////    		
		////    		System.out.println("line: " + lines.size());
		//
		//    		manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
		//    		manipulador.Escrever(arquivoConf.getPath(),nomeModelo);
		//    		manipulador.Escrever(arquivoConf.getPath(),usuarioconf);
		//    		manipulador.Escrever(arquivoConf.getPath(),senhaconf);
		//    		manipulador.Escrever(arquivoConf.getPath(),hostconf);
		//    		manipulador.Escrever(arquivoConf.getPath(),protocoloconf);
		//    		manipulador.Escrever(arquivoConf.getPath(),portaconf);
		//    		
		//    		
		//			
		//		}else {
		//			
		//			
		////			lines= new HashMap<Integer, String>();
		////			
		////			
		////			lines= manipulador.getLines();
		////			
		//			nomeArquivo = lines.get(0);
		//			nomeModelo = lines.get(1);
		//			usuarioconf= lines.get(2);
		//			senhaconf= lines.get(3);
		//			hostconf= lines.get(4); 
		//			protocoloconf= lines.get(5);
		//			portaconf= lines.get(6);
		//			
		//			
		//			
		//			
		//		}

		//		PreencherAjuda();

	}

	private void VerificarConfFiltroInitConfig() {


		if(nomeArquivo != null && nomeModelo != null){

			procurarArquivos();

			arquivoLocalizado = RetornarArquivoValido(arquivos);

			if(arquivoLocalizado != null){

				CriarDiretorio(arquivoLocalizado);
				//  trace.textProperty().unbind();
				RecuperarInfoConf(arquivoConf);
				RecuperarInfoFilter(arquivoFiltro);



			}


		}else {


			nomeArquivo = lines.get(0);
			nomeModelo = lines.get(1);



		}

		PreencherAjuda();


	}



	private void VerificarConfFiltroInitServer() {


		if(usuarioconf != null && senhaconf != null && hostconf != null && protocoloconf != null && portaconf != null) {



			//    		lines= new HashMap<Integer, String>();
			//	
			//    		lines= manipulador.getLines();
			//    		
			//    		System.out.println("line: " + lines.size());

			//    		if(nomeArquivo != null && nomeModelo != null) {
			//    			
			//        		manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
			//        		manipulador.Escrever(arquivoConf.getPath(),nomeModelo);
			//    			
			//    		}

			//    		manipulador.Escrever(arquivoConf.getPath(),nomeArquivo);
			//    		manipulador.Escrever(arquivoConf.getPath(),nomeModelo);
			manipulador.Escrever(arquivoConf.getPath(),usuarioconf);
			manipulador.Escrever(arquivoConf.getPath(),senhaconf);
			manipulador.Escrever(arquivoConf.getPath(),hostconf);
			manipulador.Escrever(arquivoConf.getPath(),protocoloconf);
			manipulador.Escrever(arquivoConf.getPath(),portaconf);



		}else {

			if(nomeArquivo != null && nomeModelo != null) {

				nomeArquivo = lines.get(0);
				nomeModelo = lines.get(1);

			}

			usuarioconf= lines.get(2);
			senhaconf= lines.get(3);
			hostconf= lines.get(4); 
			protocoloconf= lines.get(5);
			portaconf= lines.get(6);




		}

		PreencherAjuda();


	}


	private void VerificarConfFiltroInitFiltro() {



		PreencherValoresArquivados(arquivoConf,arquivoFiltro);

		PreencherAjuda();



	}


	//	private void CriaTab(com.tecsoluction.robo.entidade.Detento det){
	//		
	//		tabhtml =	"<html><body> <table style=\" border: 1px solid #1C6EA4; background-color: #EEEEEE; width: 100%; text-align: left; border-collapse: collapse;> <caption>Alarmes</caption> <thead style=\" background: #1C6EA4; border-bottom: 2px solid #444444;\">" +
	//				" <tr> " +
	//				"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Qtd</th>"+
	//
	//				//"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Nome</th>"+
	//
	//				"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Alarme</th>"+
	//
	//				" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Processo</th>" +
	//
	//				" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Vep</th>" +
	//
	//				" <th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Inicio</th>"+
	//				"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Fim</th>"+
	//				"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Duração</th>"+
	//				 "</tr> </thead>" +
	//
	//				" <tbody>";
	//		
	//		for (int i = 0; i < det.getViolacoes().size(); i++) {
	//			
	//			int incremento = i +1;
	//			
	//			Violacao viola = det.getViolacoes().get(i);
	//			
	//			String datanull = null;
	//			
	//			if(viola.getDatafinalizacao() == null) {
	//				
	//				
	//				 datanull = "01/01/2000 00:00";
	//				
	//			}else {
	//				
	//				datanull = viola.getDatafinalizacao();
	//				
	//				
	//			}
	//		
	//		
	//		
	//		
	//		tabhtml = tabhtml + "<tr style=\" background: #D0E4F5; \"> <td>" + incremento  + "</td>" +
	////				"<td style=\" font-size: 14px;\">" + det.getNome()  + "</td>" +
	//						"<td style=\" font-size: 14px;\">" + viola.getAlarme()  + "</td>" +
	//							"<td style=\" font-size: 14px;\">" + det.getProcesso() + " </td> " +
	//							"<td style=\" font-size: 14px;\">" + det.getVep() + " </td> " +
	//						"<td style=\" font-size: 14px;\">" + FormatadorData(viola.getDatainicio() ) + " </td> " +
	//							"<td style=\" font-size: 14px;\">" + FormatadorData(datanull)  + " </td> " +
	//						"<td style=\" font-size: 14px;\">" + viola.getDuracao()+ " </td> " + "</tr>";
	//		
	//
	//		}
	//		
	//		
	//		tabhtml = tabhtml  + " </tbody> </table> </body></html> ";
	//		
	//		}






	//	@FXML
	//	void info() {
	//		
	//		System.out.println("info");
	//		
	//		
	//		//anchorinfo.setVisible(true);
	//		
	//		
	//		if (showinfo ) {
	//			
	//			
	//			anchorinfo.setVisible(false);
	//			
	//			
	//			showinfo =false;
	//		
	//		}else {
	//			
	//			if(showajuda){
	//							
	//				anchorinfo.relocate(5.0, 130.0);
	//				anchorinfo.setVisible(true);
	//							
	//						}else {
	//							
	//							anchorinfo.setVisible(true);
	//							
	//						}
	//			
	//			//anchorinfo.setVisible(true);
	//			
	//			showinfo =true;
	//		}
	//
	//		
	//	
	//	}




	void CarregarInfo(){


		lbldiretorio.setText(arquivoLocalizado.getParent().toUpperCase().toString());
		lblarquivo.setText(arquivoLocalizado.getName().toUpperCase());
		lblmodelo.setText(nomeModelo);

		jtooltiparquivo.setText(lblarquivo.getText());;
		jtooltipdiretorio.setText(lbldiretorio.getText());;



	}



		@FXML
		void drawerespaco() {
			
			System.out.println(" drawer espaco");
			
			double widarquivo = lblarquivo.getWidth();
			double widdiretorio = lbldiretorio.getWidth();
			
			if(widarquivo >= widdiretorio ) {
				
				drawer.setDefaultDrawerSize(widarquivo - 12.0);
				
			}else {
				
				drawer.setDefaultDrawerSize(widdiretorio - 12.0);

				
			}
			
			System.out.println(" drawer espaco arq" + widarquivo);
			
			System.out.println(" drawer espaco dir" + widdiretorio);
			
			System.out.println(" drawer espaco final" + drawer.getDefaultDrawerSize());

		}
	//	


	void PreencherAjuda() {

		Image image = new Image(getClass().getResourceAsStream("/images/icons8-checked-checkbox-48.png"),30.0,30.0,true,true);

		Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),30.0,30.0,true,true);


		if((nomeArquivo != null ) || !(lines.get(0)== null)){

			imgvstep1.setImage(image);

		}else {


			imgvstep1.setImage(imagee);

		}

		if((nomeModelo != null ) || !(lines.get(1) == null)){

			imgvstep2.setImage(image);

		}else {

			imgvstep2.setImage(imagee);



		}

		if(!(lines.get(2) == null) && !(lines.get(3)== null) && !(lines.get(4)== null) && !(lines.get(5)== null) && !(lines.get(6)== null)){

			imgvstep3.setImage(image);

		}else {

			imgvstep3.setImage(imagee);



		}


		if(!(arquivoConf==null)){

			imgvstep4.setImage(image);

		}else {


			imgvstep4.setImage(imagee);


		}


		if(!(filtros.isEmpty())){

			imgvstep5.setImage(image);

		}else {


			imgvstep5.setImage(imagee);


		}


		if(!(arquivoFiltro == null)){

			imgvstep6.setImage(image);

		}else {


			imgvstep6.setImage(imagee);



		}



	}


	void initAnchor(){


		// stageManager.getPrimaryStage().getScene().setRoot(rectangleGroup);

		Shear sh = new Shear();
		sh.setY(-0.1);
		sh.setX(0.0);
		sh.setPivotX (0);  
		sh.setPivotY (50);  
		vvb.getTransforms().add(sh);
		//   group.getChildren().add(vvb);




	}


	void initinc(){


		// stageManager.getPrimaryStage().getScene().setRoot(rectangleGroup);

		Shear sh = new Shear();
		sh.setY(-0.1);
		sh.setX(0.0);
		sh.setPivotX (0);  
		sh.setPivotY (0);  
		vvb.getTransforms().add(sh);
		//   group.getChildren().add(vvb);




	}



	void BackAnchor(){



		Shear sh = new Shear();
		sh.setY(0.1);
		sh.setX(0.0);
		sh.setPivotX (0);  
		sh.setPivotY (0);
		vvb.getTransforms().add(sh);
		//   group.getChildren().add(vvb);


	}

	void PreencherValidacaoNome(Detento detento,boolean bol){

		jtxfvalidanome.setText(detento.getNome());

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);


		if(bol){

			imgvvalidanome.setImage(image);


		}else {

			imgvvalidanome.setImage(imagee);

		}

	}


	void PreencherValidacaoVara(Detento detento,boolean bol){

		jtxfvalidavara.setText(detento.getVara());

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);


		if(bol){

			imgvvalidavara.setImage(image);


		}else {

			imgvvalidavara.setImage(imagee);

		}

	}


	void PreencherValidacaoProcesso(Detento detento,boolean bol){

		jtxfvalidaprocesso.setText(detento.getProcesso());

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);


		if(bol){

			imgvvalidaprocesso.setImage(image);


		}else {

			imgvvalidaprocesso.setImage(imagee);

		}

	}


	void PreencherValidacaoVep(Detento detento,boolean bol){

		jtxfvalidavep.setText(detento.getVep());

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);


		if(bol){

			imgvvalidavep.setImage(image);


		}else {

			imgvvalidavep.setImage(imagee);

		}

	}



	void PreencherValidacaoEmail(Detento detento,boolean bol){

		jtxfvalidaemail.setText(detento.getEmail());

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);



		if(bol){

			imgvvalidaemail.setImage(image);


		}else {

			imgvvalidaemail.setImage(imagee);

		}

	}

	
	
	@FXML
	void apagar(){


		
		InicializarListas();
		//RestartarListas();
		arquivoLocalizado = stageManager.getArquivoLocalizado();

		lblnomearquivoanalise.setText("");
		trace.setText("Arquivo Localizado : " + arquivoLocalizado.getName());
		registroFiltrados.clear();
		registrosNotificar.clear();
		detentoFinal.clear();
		qtdregistrolidos = 0;
		qtddetentorepetido=0;
		lblfiltrosaplicados.setText("0");
		lblfiltrosaplicados2.setText("0");
		jtooltipfiltro2.setText("");
		jtooltipfiltro.setText("");
		AtualizarQuadro();
		AtualizarQuadroRegistros();
		jtgbligar.setSelected(false);
		jtgbfiltrosistema.setSelected(false);
		jtgbanalisararquivo.setSelected(false);
		
		CarregarInfo();

	}


	void LimpaValidacao(){



		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-time-machine-30.png"),24.0,24.0,true,true);
		//	Image image2 = new Image(getClass().getResourceAsStream("/images/icons8-time-machine-30.png"),36.0,36.0,true,true);

		jtxfvalidanome.setText("");
		jtxfvalidavara.setText("");
		jtxfvalidaprocesso.setText("");
		jtxfvalidavep.setText("");
		jtxfvalidaemail.setText("");
		jtxtfenvio.setText("");

		imgvvalidanome.setImage(image2);
		imgvvalidavara.setImage(image2);
		imgvvalidaprocesso.setImage(image2);
		imgvvalidavep.setImage(image2);
		imgvvalidaemail.setImage(image2);
		imgvvalidaenvio.setImage(image2);
		imgvvalidatotal.setImage(imaget);




	}


	private void PreencherValidacaoEnvio(Detento detento, boolean b) {

		//	jtxtfenvio.setText("");

		//	Image image = new Image(getClass().getResourceAsStream("/images/icons8-checkmark-48.png"),24.0,24.0,true,true);
		//
		//	Image imagee = new Image(getClass().getResourceAsStream("/images/error.png"),24.0,24.0,true,true);
		//	
		//	Image image2 = new Image(getClass().getResourceAsStream("/images/icons8-checked-checkbox-48.png"),36.0,36.0,true,true);
		//
		//	Image imagee2 = new Image(getClass().getResourceAsStream("/images/icons8-delete-48.png"),36.0,36.0,true,true);


		if(b){

			imgvvalidaenvio.setImage(image);
			imgvvalidatotal.setImage(image22);
			jtxtfenvio.setText("SUCESSO");


		}else {

			imgvvalidaenvio.setImage(imagee);
			imgvvalidatotal.setImage(imagee2);
			jtxtfenvio.setText("ERROR: " + detento.getErros().toString());

		}	




		//	vboxvalidacao.requestFocus();


	}

	private String handleMessagingException(Exception e) {
		StringBuffer exmess = new StringBuffer();

		String msg = new String("");


		do {
			if (e instanceof SendFailedException) {
				SendFailedException sfex = (SendFailedException)e;
				Address[] invalid = sfex.getInvalidAddresses();               
				if (invalid != null) {
					exmess.append("\n    ** Invalid Addresses");
					if (invalid != null) {
						for (int i = 0; i < invalid.length; i++)
							exmess.append("         " + invalid[i]);
					}
				}
				Address[] validUnsent = sfex.getValidUnsentAddresses();
				if (validUnsent != null) {
					exmess.append("\n    ** ValidUnsent Addresses");
					if (validUnsent != null) {
						for (int i = 0; i < validUnsent.length; i++)
							exmess.append("         "+validUnsent[i]);
					}
				}
				Address[] validSent = sfex.getValidSentAddresses();
				if (validSent != null) {
					exmess.append("\n    ** ValidSent Addresses");
					if (validSent != null) {
						for (int i = 0; i < validSent.length; i++)
							exmess.append("         "+validSent[i]);
					}
				}
			}
			if (e instanceof MessagingException)
				e = ((MessagingException)e).getNextException();
			else
				e = null;

			if (e instanceof TimeoutException){
				e = ((MessagingException)e).getNextException();
				msg = ((TimeoutException)e).getMessage();
			} else {
				e = null;
				msg = null;
			}
			if (e instanceof InterruptedException){
				e = ((MessagingException)e).getNextException();
				msg = ((InterruptedException)e).getMessage();
				//        	regerar();

			} else{
				e = null;
				msg = null;
			}
			//        if (e instanceof SendFailedException)
			//            e = ((SendFailedException)e).getNextException();
			//        else
			//            e = null;

			if (e instanceof SocketTimeoutException){
				e = ((MessagingException)e).getNextException();

				msg = ((SocketTimeoutException)e).getMessage();
			} else{
				e = null;
				msg = null;

			}


		} while (e != null || msg!=null);
		return exmess.toString() +"msg :" + msg;
	}

	
	@FXML
	   private void openanaalise() throws IOException {

	      FileChooser filechoose = new FileChooser();
	  	  filechoose.setTitle("escolha a Planilha");
	  	  
	  	fileanalise = filechoose.showOpenDialog(btcarregarArquivoAnalise.getParent().getScene().getWindow());
	  	 
	  	  System.out.println("caminho absoluto: " + fileanalise.getAbsolutePath());
	  	  System.out.println("caminho canonico: " + fileanalise.getCanonicalPath());
	  	  System.out.println("caminho canonico: " + fileanalise.getPath());
	  	  
	  	  
	  	  
	  	  
	        if ((fileanalise != null) && (fileanalise.getAbsolutePath() != null)) {
	      		 
	        	
	        	String extensao = getFileExtension(fileanalise.getName());
	        	
	        	
	        	if((extensao.contains("xlsx")) && (extensao!= null)) {
	        		
	          		 LeitorExcel leitor = new LeitorExcel(fileanalise.getAbsolutePath(),filtros);
	           		 
	           		 String path = leitor.getSAMPLE_XLSX_FILE_PATH();
	           		 
	       	  	  if (fileanalise != null) {

	      	  		lblnomearquivoanalise.setText(fileanalise.getName());
	      	  		lblnomearquivoanalise.setVisible(true);

	      	  	  }else {
	      	  		  
	      	  		  
	      	  	  }
	       	  	  
	       	  //	  List<Registro> registrosanalise = null;
	           		 
	           		 try {

	           		//	progressind.setVisible(true);
	           			registrosanalise = leitor.readRegistersFromExcelFile(path);
						registroFiltrados2 =leitor.Filtrar();

					//	detentos = RegistroToDetento(registrosanalise);
						detentos = RegistroToDetento(registroFiltrados2);
						
						objExclude = leitor.getGerenciafiltro().getExcluidos();
						
	       				System.out.println("registro ini: " + registrosanalise.size());
	       				System.out.println("registro fim: " + registroFiltrados2.size());
//
	       				System.out.println("registros excluido: " + objExclude.size());
	       				System.out.println("registros ini2: " + leitor.getRegistros().size());

						//qtdregistrolidos = leitor.getRegistros().size();
						
						
					//	registroFiltrados2 =	leitor.getRegistros();
						
						qtdregistrolidos= registrosanalise.size();
						
						qtdregistrofinal = registroFiltrados2.size();

	           		//	AnaliseRegistro(registrosanalise);
	           			 
	           		//	 detentoscarregados = RegistroToDetento(registros);
//	           			PreAnalise();
	           			
	           	    	
	           	   // 	filter();
	           			
	           		//	jtxtfarquivo.setDisable(true);
//	           	    	 btcarregar.setDisable(true);
	           			
	           			jtgbanalisararquivo.setDisable(false);
	           			
	       			} catch (IOException e) {
	       				e.printStackTrace();
	       				System.out.println("falha ao ler registro : " + e);
	       			//	jtxtfarquivo.setDisable(false);
//	            	 btcarregar.setDisable(false);
//	         	    btfilter.setDisable(true);
//	         	    btremoveduplicado.setDisable(true);
	       				
	       				jtgbanalisararquivo.setDisable(true);
	       			}
	        		
	        		
	        	}else {
	        		
	        		ErrorAlert(extensao);
	        	//	jtxtfarquivo.setDisable(false);
//	            	 btcarregar.setDisable(false);
//	         	    btfilter.setDisable(true);
//	         	    btremoveduplicado.setDisable(true);
       				jtgbanalisararquivo.setDisable(true);

	        		
	        	}
	       		 

	      		               		 
	      	 }else {
	      		 
	      		trace.setText("Carregue um Arquivo valido..");
   				jtgbanalisararquivo.setDisable(true);

	      	//	jtxtfarquivo.setDisable(false);
//	        	 btcarregar.setDisable(false);
//	        	 
//	     	    btfilter.setDisable(true);
//	     	    btremoveduplicado.setDisable(true);
	      		 
	      		 
	      	 }
	  	  
	  	  

	        jtgbanalisararquivo.setSelected(true);
	        jtgbligar.setDisable(false);
	        jtgbfiltrosistema.setDisable(false);
	    
	    }



	private void AnaliseRegistro(List<Registro> registrosanalise) {

		
		ValidacaoRegistro valida = new ValidacaoRegistro();
		
		validos = new ArrayList<Registro>();
		
		 invalidos = new ArrayList<Registro>();

		
		for(Registro reg : registrosanalise){
			
		boolean valido = valida.validaRegistro(reg); 
		
		if(valido){
			
			validos.add(reg);
			
		}else {
			
			invalidos.add(reg);
			
		}
			
		}
		
			System.out.println("registro validos : " + validos.size());
				System.out.println("registro invalidos : " + invalidos.size());
				
				
				PreAnalise();
				
			//	String nomearq = arquivoLocalizado.getPath().
				
			//	File fill = new File()
				
//			File fill = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "analise" +"\\" );
//
//			 filllana = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "analise" +"\\" + fileanalise.getName()+ "_analise_" + FormatadorData(new Date())+ ".xlsx" );
//
//			if(!fill.isDirectory()){
//				
//				fill.mkdir();
//				
//			}else {
//				
//				
//				
//			}
//				
//				CriarExcel criar = new CriarExcel(validos,invalidos);
//
//				try {
//					criar.CriarAnalise(validos, invalidos, filllana);
//					
//					trace.textProperty().unbind();
//					trace.setText("Arquivo Analise Criado");
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					trace.textProperty().unbind();
//					trace.setText("Erro ao Criar Arquivo Analise" + e);
//				}
				
//				if(invalidos.size() > 0){
//					
//					trace.setText("Análise com Erros: " + invalidos.size());
//					AlertaAbrirArquivoExecel(filll);
//					
//				}else {
//					
//					
//					trace.setText("Análise sem Erros");
//					AlertaSubstituirArquivoExecel(fileanalise);
//					
//				}
				
				
				
				
		
		
	}
	
	
public void AlertaSubstituirArquivoExecel(File arquivoloc){
		
	 	ButtonType btnSim = new ButtonType("Ok");
	 	ButtonType btncancel = new ButtonType("Cancelar",ButtonBar.ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		//alert.setGraphic(new ImageView(this.getClass().getResource("/images/fdd.png").toString()));
		alert.setTitle("Foda");
		alert.setHeaderText( "Confirma Importar esse Arquivo do Excel ? ");
		alert.setContentText("Analise sem erros, Deseja Importar o Arquivo : "+arquivoloc.getName()+" para o Foda ? " +"\n "+".");
		alert.getButtonTypes().setAll(btnSim, btncancel);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/images/fodaa.png").toString()));
		alert.showAndWait().ifPresent(b -> {
        
			if (b == btnSim) {
              
//				try {
//					enviarEmail(user);
//				} catch (AddressException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				stageManager.setArquivoLocalizado(arquivoloc);
				arquivoLocalizado = stageManager.getArquivoLocalizado();			
				//jtgbligar.setDisable(true);
			//	jtgbfiltrosistema.setDisable(true);
				
           
			} else {
				
				//jtgbligar.setDisable(false);
				//jtgbfiltrosistema.setDisable(false);
				arquivoLocalizado = stageManager.getArquivoLocalizado();
				
			}
	});
		
}
public void AlertaAbrirArquivoExecel(File arquivoloc){
	
 	ButtonType btnSim = new ButtonType("Ok");
 	ButtonType btncancel = new ButtonType("Cancelar",ButtonBar.ButtonData.CANCEL_CLOSE);
	Alert alert = new Alert(AlertType.CONFIRMATION);
	//alert.setGraphic(new ImageView(this.getClass().getResource("/images/fdd.png").toString()));
	alert.setTitle("FODA" );
	alert.setHeaderText("Confirma Abrir Arquivo de Erro ? ");
	alert.setContentText("Analise com erros, Deseja Abrir Arquivo : "+arquivoloc.getName()+" e visualizar os erros ? " +"\n "+".");
	alert.getButtonTypes().setAll(btnSim, btncancel);
	Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	stage.getIcons().add(new Image(this.getClass().getResource("/images/fodaa.png").toString()));
	alert.showAndWait().ifPresent(b -> {
    
		if (b == btnSim) {
          
//			try {
//				enviarEmail(user);
//			} catch (AddressException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			 Desktop desktop = Desktop.getDesktop();
			 try {
				desktop.open(arquivoloc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				trace.setText("Abrir Erros" + e);

			}
			 
			 
			 
       
		} else {
			

			
			
		}
		
		stageManager.setArquivoLocalizado(fileanalise);
		arquivoLocalizado = stageManager.getArquivoLocalizado();
        jtgbanalisararquivo.setDisable(false);
      //  jtgbligar.setDisable(false);
        jtgbfiltrosistema.setDisable(false);
		
});
	
}



private void PreAnalise() {

		detentos = RegistroToDetento(validos);
//
//		gerenciafiltro = new GerenciadorFiltro(filtros, validos,detentos);
//
//		setRegistros(gerenciafiltro.getRegistros());
//
//		setDetentos(gerenciafiltro.getDetentos());
	
	if(gerenciafiltro == null) {
		
		gerenciafiltro = new GerenciadorFiltro(filtros, validos, detentos);

		setRegistros(gerenciafiltro.getRegistros());

		setDetentos(gerenciafiltro.getDetentos());
	

		 try {
				FiltrarRegistros();

			} catch (NullPointerException eX) {
				// TODO Auto-generated catch block
				eX.printStackTrace();
				trace.textProperty().unbind();
				trace.setText("Erros na Conversão PreAnalise" + eX);
				jtgbligar.setSelected(false);
				ErrorAlert("Erros na Conversão PreAnalise");
				

			}

	//	lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
	
//		if(gerenciafiltro.getExcluidos().size() > 0){
//			
//			jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
//
//		}else {
//			
//			jtooltipfiltro2.setText("nenhuma exclusão");
//
//			
//		}
		
		
//		lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
//		jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
		
		

		
	}else{
		
		gerenciafiltro.setFiltros(filtros);
		gerenciafiltro.setRegistros(validos);
		gerenciafiltro.setDetentos(detentos);
		//gerenciafiltro.getExcluidos().clear();

		gerenciafiltro.InitBusca(filtros);

		setRegistros(gerenciafiltro.getRegistros());

		setDetentos(gerenciafiltro.getDetentos());
		
		 try {
				FiltrarRegistros();

			} catch (NullPointerException eX) {
				// TODO Auto-generated catch block
				eX.printStackTrace();
				trace.textProperty().unbind();
				trace.setText("Erros na Conversão PreAnalise" + eX);
				jtgbligar.setSelected(false);
				ErrorAlert("Erros na Conversão PreAnalise");
				

			}
		
		
		
		
	}


		System.out.println("gerencia filtro excluidos :" + gerenciafiltro.getExcluidos().toString());
		System.out.println("gerencia filtro excluidos qtd :" + gerenciafiltro.getExcluidos().size());


		System.out.println("gerencia filtro detentos :" + gerenciafiltro.getDetentos().toString());
		System.out.println("gerencia filtro detentos qtd :" + gerenciafiltro.getDetentos().size());

		System.out.println("gerencia filtro registro :" + gerenciafiltro.getRegistros().toString());
		System.out.println("gerencia filtro registro qtd :" + gerenciafiltro.getRegistros().size());

		System.out.println("gerencia filtro filtro :" + gerenciafiltro.getFiltros().toString());
		System.out.println("gerencia filtro filtro qtd :" + gerenciafiltro.getFiltros().size());

//	}else {
//
//
//		gerenciafiltro.setFiltros(filtros);
//		gerenciafiltro.setRegistros(registrosNotificar);
//		gerenciafiltro.setDetentos(detentoFinal);
//		gerenciafiltro.getExcluidos().clear();
//
//		gerenciafiltro.InitBusca(filtros);
//
//		setRegistros(gerenciafiltro.getRegistros());
//
//		setDetentos(gerenciafiltro.getDetentos());
//
//
//
//		filter();
//
//		lblfiltrosaplicados2.setText("" + gerenciafiltro.getExcluidos().size());
//		jtooltipfiltro2.setText(gerenciafiltro.getExcluidos().toString());
//
//		System.out.println("gerencia filtro excluidos no null :" + gerenciafiltro.getExcluidos().toString());
//		System.out.println("gerencia filtro excluidos qtd no null :" + gerenciafiltro.getExcluidos().size());
//
//
//		System.out.println("gerencia filtro detentos no null :" + gerenciafiltro.getDetentos().toString());
//		System.out.println("gerencia filtro detentos qtd no null :" + gerenciafiltro.getDetentos().size());
//
//		System.out.println("gerencia filtro registro no null :" + gerenciafiltro.getRegistros().toString());
//		System.out.println("gerencia filtro registro qtd no null:" + gerenciafiltro.getRegistros().size());
//
//		System.out.println("gerencia filtro filtro no null :" + gerenciafiltro.getFiltros().toString());
//		System.out.println("gerencia filtro filtro qtd no null :" + gerenciafiltro.getFiltros().size());
//
//
//	}


	//		progressind.setVisible(false);
	//		progressindfiltro.setVisible(false);
		

			
//			if(invalidos.size() > 0){
//				
//				trace.setText("Análise com Erros: " + invalidos.size());
//				AlertaAbrirArquivoExecel(filll);
//				
//			}else {
//				
//				
//				trace.setText("Análise sem Erros");
//				AlertaSubstituirArquivoExecel(fileanalise);
//				
//			}
		
	
		
		
		lblfiltrosaplicados2.setText("" + objExclude.size());
		jtooltipfiltro2.setText(objExclude.toString());
	
	
	lblfiltrosaplicados.setText("" + filtros.size());
	jtooltipfiltro.setText("" + filtros.toString());
	
	SalvarExcelAnaliseRegistro();
	
	AtualizarQuadroRegistros();
	AtualizarQuadro();



}


public void SalvarExcelAnaliseRegistro(){
	
	
	List<Registro> totalinvalidos = new ArrayList<Registro>();
	
//	totalinvalidos.addAll(registroFiltrados);
	totalinvalidos.addAll(invalidos);
	
	
	File fill = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "analise" +"\\" );

	 filllana = new File(arquivoLocalizado.getPath().replace(arquivoLocalizado.getName(), "")+"\\" + "analise" +"\\" + fileanalise.getName()+ "_analise_" + FormatadorData(new Date())+ ".xlsx" );

	if(!fill.isDirectory()){
		
		fill.mkdir();
		
	}else {
		
		
		
	}
		
		CriarExcel criar = new CriarExcel(registrosNotificar,totalinvalidos);

		try {
			criar.CriarAnalise(registrosNotificar, totalinvalidos, filllana);
			
			trace.textProperty().unbind();
			trace.setText("Arquivo Analise Criado");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			trace.textProperty().unbind();
			trace.setText("Erro ao Criar Arquivo Analise" + e);
		}
	
	
	
}
	

}











