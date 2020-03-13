package com.tecsoluction.robo.controller;


import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.tecsoluction.robo.conf.StageManager;
import com.tecsoluction.robo.entidade.Autenticador;
import com.tecsoluction.robo.entidade.Detento;
import com.tecsoluction.robo.entidade.Violacao;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Controller
public class ServidorController implements Initializable{

	
	final static Logger logger = LoggerFactory.getLogger(ServidorController.class);


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
	    private JFXTextField jtxtusuario;

	    @FXML
	    private JFXPasswordField jtxtsenha;

	    @FXML
	    private JFXTextField jtxtversenha;

	    @FXML
	    private ImageView imgvverpassword;

	    @FXML
	    private JFXTextField jtxthost;

	    @FXML
	    private JFXTextField jtxtporta;

	    @FXML
	    private JFXTextField jtxtprotocolo;

	    @FXML
	    private Label lbvalida;

//	    @FXML
//	    private JFXButton btsalvarserver;
//
//	    @FXML
//	    private JFXButton bttestarserver;

	    @FXML
	    private AnchorPane anchorcena;

	    @FXML
	    private Label trace;

	    @FXML
	    private JFXButton btsalvar;

	    @FXML
	    private JFXButton bttestar;

	    @FXML
	    private JFXButton btfechar;

	    @FXML
	    private ProgressIndicator progressind;	
	    
	    private boolean visibile = false;
	    
	    private boolean isconect = false;
	    @FXML
	    private ImageView imgvcon;
		
	    public ServidorController() {
			// TODO Auto-generated constructor stub
		}
	    
	    



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
		
		 
		
		
	}	
	
	
	
	
	

     
     void Inicializar (){
    	 
    	 
    
    	
    	 
     }
     
 
    
    
 	@FXML
 	public void salvarnome(){
 		

 		trace.setText("Clicado em Salvar Nome");
 		
// 		nomeArquivo = jtxtfarquivonome.getText();
 		
 		
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
	                       
	                	updateMessage("Fechando Janela Servidor .");
	                	
	                	
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
	
	@FXML
	public void mostrarsenha() {
		// TODO Auto-generated method stub
		
		if(visibile){
			
			//String senha = jtxtsenha.getText();
			jtxtversenha.setText("");
			jtxtversenha.setVisible(false);
			jtxtversenha.setDisable(true);
			
			visibile = false;
			
			
		}else {
			
			String senha = jtxtsenha.getText();
			jtxtversenha.setText(senha);
			jtxtversenha.setVisible(true);
			jtxtversenha.setDisable(false);	
			
			visibile = true;
			
			
		}


		
	}
	
	
	@FXML
	void salvarserver() {
		
		
		
		
		

	}
	
	
	

@FXML
void testar(ActionEvent event)  throws AddressException,
MessagingException{
	
	new Service<Integer>() {
        @Override
        public void start() {
            super.start();
            
         progressind.setVisible(true);
           
   		progressind.progressProperty().unbind();
   		progressind.progressProperty().bind(this.progressProperty());

   		trace.textProperty().unbind();
          

//    		
    		trace.textProperty().bind(this.messageProperty());
        
        
        }

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {         	  
                       
                	updateMessage("Enviando email Teste . . .");
                	
                	 enviar();
                	
                	 for (int i = 0; i < 20; i++) {
                       //  Thread.sleep(200);
                		
                		 Thread.sleep(200);
                         updateProgress(i + 1, 20);                   
                        
                     }
                	
                        
                        
                        updateMessage("Finalizado teste.");
                       
                        return null;
                }
            };
            
            

        }
        


        @Override
        protected void succeeded() {

      //  desabilitarButoesServer();	
        	
        	 progressind.setVisible(false);
        	 
        	 trace.textProperty().unbind();
//        	 trace.setText("Copied: " + getValue());
        	 trace.setText("OK");
        	 
        	 AtualizarIcon();

		 
        }
        
        @Override
        protected void failed() {
        	
        	//AtualizarQuadro();
        	
//        	restart();
//        	try {
//				finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        	progressind.setVisible(false);
			restart();
			trace.textProperty().unbind();
        	trace.setText("NOK");
        	 AtualizarIcon();
//        	AtualizarQuadro();
        	
        	//restart();
        	
        }
        
        
        @Override
        protected void running() {
        	//super.running();
        //	AtualizarQuadro();
        	
        	//  updateProgress(this.getWorkDone(), this.getTotalWork());
        	
        }
        

        
        
        
    }.start();
    
    
    
	
	
    


}

//@FXML
//void testarserver(ActionEvent event)  throws AddressException,
//MessagingException{
//	
//	new Service<Integer>() {
//        @Override
//        public void start() {
//            super.start();
//            
//         progressind.setVisible(true);
//           
//   		progressind.progressProperty().unbind();
//   		progressind.progressProperty().bind(this.progressProperty());
//
//   		trace.textProperty().unbind();
//          
//
////    		
//    		trace.textProperty().bind(this.messageProperty());
//        
//        
//        }
//
//        @Override
//        protected Task<Integer> createTask() {
//            return new Task<Integer>() {
//                @Override
//                protected Integer call() throws Exception {         	  
//                       
//                	updateMessage("Enviando email Teste . . .");
//                	
//                	 enviar();
//                	
//                	 for (int i = 0; i < 20; i++) {
//                       //  Thread.sleep(200);
//                		
//                		 Thread.sleep(200);
//                         updateProgress(i + 1, 20);                   
//                        
//                     }
//                	
//                        
//                        
//                        updateMessage("Finalizado teste.");
//                       
//                        return null;
//                }
//            };
//            
//            
//
//        }
//        
//
//
//        @Override
//        protected void succeeded() {
//
//      //  desabilitarButoesServer();	
//        	
//        	 progressind.setVisible(false);
//        	 
//        	 trace.textProperty().unbind();
////        	 trace.setText("Copied: " + getValue());
//        	 trace.setText("OK");
//
//		 
//        }
//        
//        @Override
//        protected void failed() {
//        	
//        	//AtualizarQuadro();
//        	
////        	restart();
////        	try {
////				finalize();
////			} catch (Throwable e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
//        	progressind.setVisible(false);
//        	cancel();
//        	 trace.textProperty().unbind();
//        	trace.setText("NOK");
////        	AtualizarQuadro();
//        	
//        	//restart();
//        	
//        }
//        
//        
//        @Override
//        protected void running() {
//        	//super.running();
//        //	AtualizarQuadro();
//        	
//        	//  updateProgress(this.getWorkDone(), this.getTotalWork());
//        	
//        }
//        
//
//        
//        
//        
//    }.start();
//    
//    
//    
//	
//	
//    
//
//
//}


void enviar(){
	
	Violacao violacao = new Violacao();
	violacao.setAlarme("alarme teste");
	violacao.setCaracteristica("caracteristica teste");
	violacao.setDatafinalizacao("01/01/2019");
	violacao.setDatainicio("01/01/2019");
	violacao.setDataviolacao("01/01/2019");
	violacao.setDuracao("teste duracao");
//	violacao.setNotificacao("notificação teste");
	//violacao.se
	
	
	Detento detento = new Detento();
	
//	detento.setArtigos("teste Artigo");
	detento.setEmail("email teste");
	detento.setEstabelecimento("estabelecimento teste");
	detento.setNome("teste nome detento");
	detento.setPerfil("teste perfil");
	detento.setProcesso("teste processo");
	detento.setProntuario("1414141414");
	detento.setStatusenvio("PENDENTE");
	detento.setVep("252525252525");
	detento.addViolacao(violacao);
	
	
	 String  diretorioaux = System.getProperty("user.dir");
	 
	 String diretorio = diretorioaux + "/src/main/resources/images/";
	 
	   String to = "windson.m.bezerra@gmail.com";

	    String from = jtxtusuario.getText().trim();
	    
	    String from2 = "alonysantos.adv@gmail.com";
	    
	    String html = CriarBody(detento);
	    
	    System.out.println("html: " + html);
	 
	 
	Properties props = new Properties();
   props.setProperty("mail.smtps.user", jtxtusuario.getText().trim());   //setei o login
   props.setProperty("mail.smtp.password", jtxtsenha.getText().trim()); // e a senha
   props.setProperty("mail.transport.protocol", jtxtprotocolo.getText().trim());
   props.put("mail.smtp.starttls.enable","true"); //não sei ao certo para que serve, mas tive que colocar...
   props.setProperty("mail.smtp.auth", "true");  //setei a autenticação  
   props.setProperty("mail.smtp.starttls.required","true");
   props.setProperty( "mail.smtp.quitwait", "false");
   props.setProperty("mail.smtp.host", jtxthost.getText().trim());
   String user = props.getProperty("mail.smtps.user");
   String passwordd = props.getProperty("mail.smtp.password");
   props.put("mail.smtp.port",jtxtporta.getText().trim());
   props.put("mail.smtp.ssl.trust", jtxthost.getText().trim());
   
   
   
//   props.put("proxySet","true");
//   props.put("socksProxyHost","200.238.112.93");
//   props.put("socksProxyPort","587");
   
//   props.put("mail.smtp.socketFactory.port", jtxtporta.getText().trim());
//   props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//   props.put("mail.smtp.socketFactory.fallback", "false");
//   SecurityManager security = System.getSecurityManager();
   
//   security.checkPropertiesAccess(); 
// security.checkPropertiesAccess();
   
   
   
   
   Autenticador auth = null;
	
	
	auth = new Autenticador (user, passwordd);
   
   

   // Get the Session object.
	Session session = Session.getInstance(props, auth);
	session.setDebug(true);
	
	  MimeBodyPart messageBodyPart = new MimeBodyPart();
	    try {
			messageBodyPart.setContent(html, "text/html;charset=utf-8");
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    


	    Multipart multipart = new MimeMultipart();
	    try {
			multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    

	    
//	    trace.setText("before send  ");

	    try {
	    	
	    	//Smt
	          // Create a default MimeMessage object.
	          Message message = new MimeMessage(session);

	 	   // Set From: header field of the header.
		   message.setFrom(new InternetAddress(jtxtusuario.getText().trim()));

		   // Set To: header field of the header.
		   message.addRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(jtxtusuario.getText().trim()));
		   
//		   message.addRecipients(Message.RecipientType.BCC,
//		            InternetAddress.parse(jtxtusuario.getText().trim()));
//		   
		   message.addRecipients(Message.RecipientType.BCC,
		            InternetAddress.parse(from2));

		   // Set Subject: header field
		   message.setSubject("Detento: " + detento.getNome());

		   // Send the actual HTML message, as big as you like
		   message.setContent(multipart);
		   
		   
		   message.saveChanges();

		   // Send message
		   Transport.send(message);
		  
		   	
		   System.out.println("Sent message successfully....");
		   
		//   trace.setText("Sent message successfully....");
		   
//		   btsalvarserver.setDisable(false);
//		   bttestarserver.setDisable(true);
		   
//			imgserver = new Image(getClass().getResourceAsStream("/images/icons8-ok-48.png"));
//			imgvserver.setImage(imgserver);
//			imgvserver.setPreserveRatio(true);
//			imgvserver.setSmooth(true);
//			imgvserver.setCache(true);
			
			progressind.setVisible(false);
			
			isconect =true;
			AtualizarIcon();
			
//			serverok=true;
		   
		   

	    } catch (MessagingException e) {
		   e.printStackTrace();		
		   
		   isconect =false;
			AtualizarIcon();
	//	   trace.setText("eRROR  " + e);		   
//		   System.out.println("erro" + e.getMessage());
		   
//			imgserver = new Image(getClass().getResourceAsStream("/images/error.png"));
//			imgvserver.setImage(imgserver);
//			imgvserver.setPreserveRatio(true);
//			imgvserver.setSmooth(true);
//			imgvserver.setCache(true);
			
//			   btsalvarserver.setDisable(true);
//			   bttestarserver.setDisable(false);
			   
			   progressind.setVisible(false);
			   
//			   EmailErrorAlert("falha no teste de envio");
//			   
//			   serverok=false;
		   
		   throw new RuntimeException(e);
		   
		   
	    }
	
}
 	
private void AtualizarIcon() {

	
	
	if(isconect){
		
		Image imgok = new Image(getClass().getResourceAsStream("/images/wifi (3).png"),64.0,64.0,true,true);
		imgvcon.setImage(imgok);
		bttestar.setGraphic(imgvcon);
		bttestar.setTextFill(Color.LAWNGREEN);
		

		
	}else {
		
		
		Image imgerr = new Image(getClass().getResourceAsStream("/images/wifi (2).png"),64.0,64.0,true,true);
		imgvcon.setImage(imgerr);
		bttestar.setGraphic(imgvcon);
		bttestar.setTextFill(Color.CRIMSON);
		
		
	}
	
}





public String CriarBody(Detento detento){
	
	StringBuilder stringbuilder = new StringBuilder();
	
	String src2 = "cid:govpe";
	
	String html =
			
			
			"<p align=\"middle\" ><img src= \"" + src2 + "\" alt=\"governo_desc\" width=\"300px;\" height=\"168px;\" align=\"middle\" />"
					+ "</p>"+
			
			
			"<h2 align=\"middle\" >Notificação :</h2>"+
			
			
			"<p> <b>" + detento.getNome() + "</b> teve um total de <b>" + detento.getViolacoes().size() + "</b> violação(s) "
			+ "descumprindo a regra do Monitoramento Eletrônico na Cidade de Recife"
			+ "Dependendo da natureza da multa que você recebeu, ainda é possível pedir a sua conversão em advertência.Isso, sem contar que você sempre pode recorrer das infrações e tentar cancelar a multa, os pontos e outras penalidades que elas geram.Neste artigo, vou lhe explicar como fazer a indicação de condutor corretamente e não correr o risco de ter o seu pedido negado. </p>"
			
			 +
			
" <table style=\" border: 1px solid #1C6EA4; background-color: #EEEEEE; width: 100%; text-align: left; border-collapse: collapse;> <caption>Lista de Violacoes</caption> <thead style=\" background: #1C6EA4; border-bottom: 2px solid #444444;\">" +
" <tr> " +
"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">count</th>"+

"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Nome</th>"+

"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Alerta</th>"+

" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Processo</th>" +

" <th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Vep</th>" +

" <th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Inicio</th>"+
"<th style=\" background:#1C6EA4;  font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Fim</th>"+
"<th style=\" background:#1C6EA4; font-size: 15px; font-weight: bold; color: #FFFFFF; border-left: 2px solid #1C6EA4;\">Duração</th>"+
 "</tr> </thead>" +

" <tbody>";
 
	
	stringbuilder.append(html);
	
	for (int i = 0; i < detento.getViolacoes().size(); i++) {
		
		int incremento = i +1;
		
		Violacao viola = detento.getViolacoes().get(i);
		
		stringbuilder.append("<tr style=\" background: #D0E4F5; \"> <td>" + incremento  + "</td>");
		stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getNome()  + "</td>");
		stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getAlarme()  + "</td>");
		stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getProcesso() + " </td> " );
		stringbuilder.append("<td style=\" font-size: 14px;\">" + detento.getVep() + " </td> " );
		stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getDatainicio() + " </td> " );
		stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getDatafinalizacao() + " </td> " );
		stringbuilder.append("<td style=\" font-size: 14px;\">" + viola.getDuracao() + " </td> " + "</tr>");
		
		
	}
	
	
	String src = "cid:alonyass";
//	String srcc = "cid:pe";

String html2 = "</tbody> </table> <h3 align=\"middle\" > <i>Recife, " + new Date() + "</i></h3>" +

//stringbuilder.append("<h1 align=\"right\" > <i>Recife, " + new Date() +"</i></h1>");
"<p align=\"middle\" ><img src= \"" + src + "\" alt=\"assinatura_desc\" width=\"100px;\" height=\"100px;\" />"
		+ "</p>" + "<p align=\"middle\" style=\" font-family: \"Arial Black\", Gadget, sans-serif;\"> Diretor de Operaçoes </p>" + 
"<p align=\"middle\"><b>Alony Santos</b> </p>"  ;
			
	
	
stringbuilder.append(html2);


		
			
			
	
	
	return stringbuilder.toString();
}

}


	
    

