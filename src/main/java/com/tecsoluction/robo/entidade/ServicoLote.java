package com.tecsoluction.robo.entidade;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tecsoluction.robo.controller.MainController;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoLote extends Service<Integer> {
	
	
	final static Logger logger = LoggerFactory.getLogger(ServicoLote.class);

	
	private MainController logincontrol;
	
	
	
	public ServicoLote( MainController logincontroler) {

	this.logincontrol = logincontroler;
	
	}
	
	
	@Override
	protected void cancelled() {
		super.cancelled();
		
		logger.info("cancelled servico lote");
		
		logincontrol.getLblretornotarefa().setText("Envio Cancelado");
		logincontrol.getLblretornotarefa().setVisible(true);
		logincontrol.getJtgbanalisararquivo().setSelected(false);
		
	//	logincontrol.SalvarListasErro();
		
		logincontrol.FinalizarExecucao();
		
		logincontrol.AtualizarQuadro();
		
	
		
		this.reset();
	//	this.start();

				
		
	}
	
	
	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		
		logincontrol.AtualizarQuadro();
		
		return super.cancel();
		
		
		
	}
	
	@Override
	public void start() {
		super.start();
		
		logger.info("start servico lote");
		
		logincontrol.getLblretornotarefa().setVisible(false);
		
		logincontrol.getProgressind().progressProperty().unbind();
		logincontrol.getProgressind().progressProperty().bind(this.progressProperty());
		
		logincontrol.getTrace().textProperty().unbind();
		logincontrol.getTrace().textProperty().bind(this.messageProperty());
		
		logincontrol.RotacionarCena();
		
		logincontrol.AtualizarQuadro();
		
		

	}
	
	
	@Override
	protected void failed() {
		super.failed();
		
		logger.info("failed servico lote");

//		logincontrol.getProgressind().setVisible(true);
		logincontrol.getLblretornotarefa().setText("Falhas");
		logincontrol.getLblretornotarefa().setVisible(true);
		
		logincontrol.PararRotacionarCena();
		logincontrol.AtualizarQuadro();
//		  Platform.runLater(() ->
//	    {


//			try {
//				logincontrol.Escrever();
//			} catch (InvalidFormatException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	    	
	   
//    });
		
		
		 logincontrol.SalvarListasErro();
		
//		logincontrol.Escrever();

		
		
		this.reset();
		this.start();
		
//		restart();
		
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		logger.info("finalize servico lote");
		
//		logincontrol.AtualizarQuadro();
//		logincontrol.SalvarListasErro();
		logincontrol.PararRotacionarCena();
		logincontrol.AtualizarQuadro();
	
		this.reset();
		
	}
	
	
	@Override
	protected void running(){
		super.running();
		logger.info("running servico lote");
		logincontrol.getProgressind().setVisible(true);
		logincontrol.AtualizarQuadro();
		
		

	}
	
	
	
	@Override
	protected void succeeded(){
		super.succeeded();
		
		//this.start();
		logger.info("sucusses servico lote");
		logincontrol.AtualizarQuadro();
		logincontrol.getProgressind().setVisible(false);
		logincontrol.getProgressindtarefa().setVisible(false);
		logincontrol.getTrace().textProperty().unbind();
		logincontrol.getLblretornotarefa().setText("Sucesso");
		
		logincontrol.getJtgbanalisararquivo().setDisable(false);
//		logincontrol.getJtgbligar().setDisable(false);
//		logincontrol.getJtgbfiltrosistema().setDisable(false);
		
		logincontrol.getJtgbanalisararquivo().setSelected(false);

		
//		try {
//			logincontrol.AddLinhaMatriz(logincontrol.getDetentoEnviado());
//		} catch (InvalidFormatException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		logincontrol.criarexcel();
		
	
//		logincontrol.Escrever();
		
//		logincontrol.SalvarListas();
		
	//	logincontrol.SalvarListasErro();
		
		logincontrol.FinalizarExecucao();
		
		if(logincontrol.isIsautomatico()) {
		
		// paraenvio automatico tem que descomentar
//		 logincontrol.SalvarListas();
		 logincontrol.setLocalizarArquivo(true);
//		 logincontrol.FinalizarExecucao();
		 logincontrol.InicializarListas();

		       
		}else {
			
			logincontrol.getJtgbcriartarefa().setSelected(false);
//			logincontrol.getJtgbligar().setSelected(false);
//			logincontrol.getJtgbfiltrosistema().setSelected(false);

			logincontrol.getToolbar().setDisable(true);
		//	logincontrol.getJtgbcriartarefa().setDisable(false);
//			logincontrol.getJtgexibirvalidacao().setDisable(false);
//			logincontrol.SalvarListas();
//			logincontrol.FinalizarExecucao();
			
			
		}
		
		logincontrol.RotacionarCena();
		 
//		 	logincontrol.SalvarListas();
		 
//			logincontrol.AtualizarQuadro();
			
//			logger.info("sucusses servico lote");
			
			this.reset();


	}
	
	
	
	@Override
	public void restart(){
		super.restart();
		
		logger.info("restart servico lote");
		
		logincontrol.getProgressind().setVisible(true);
		logincontrol.getProgressind().progressProperty().unbind();
		logincontrol.getProgressind().progressProperty().bind(this.progressProperty());
		
		logincontrol.getTrace().textProperty().unbind();
		logincontrol.getTrace().textProperty().bind(this.messageProperty());
		logincontrol.AtualizarQuadro();
		
	
		
		
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		super.reset();
		
		logger.info("reset servico lote");
		
		logincontrol.getProgressind().setVisible(false);
//		logincontrol.getProgressind().progressProperty().unbind();
//		logincontrol.getProgressind().progressProperty().bind(this.progressProperty());
//		
//		logincontrol.getTrace().textProperty().unbind();
//		logincontrol.getTrace().textProperty().bind(this.messageProperty());
		logincontrol.AtualizarQuadro();
	}
	
	
	
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {         	  
                   
            	

            	 
            	 int size = logincontrol.getDetentosPendetes().size();

            	 
            	 for (int i = 0; i < logincontrol.getDetentoFinal().size(); i++) {
            		 

            		 
            		 updateProgress(i,logincontrol.getDetentoFinal().size()); 
            		 
            		 Detento det = logincontrol.getDetentoFinal().get(i);
            		 
            		 if(!(det.getStatusenvio().equals("PENDENTE"))){
      				   

      				   
      			   }else {
      				   
      				 try
      	            {
      				   
      				   updateMessage("Enviando: " + det.getNome() );     				   		   
      				   logincontrol.enviarEmail(det); 
      				 //  Thread.sleep(150);  
      				   
      	            }catch (InterruptedException e) {

      	            	det.getErros().add("Interrompido" + e.getMessage());
      	            	System.out.println("interrompido: " + e.getMessage());
      	            	
      	            	logger.info("catch servico lote" + det.getNome());
      	            	
      	            //	logincontrol.SalvarListasErro();
 
      	            	//logincontrol.getDetentoErro().add(det);
      	      	
//      	            	try {
//      	            		
//      	  			logincontrol.Escrever();
//      	  			
//      	  		} catch (InvalidFormatException | IOException ex) {
//      	  			// TODO Auto-generated catch block
//      	  			ex.printStackTrace();
//      	  		}
      	            	
      	            
      	            
      	            }
      				   
      				   
      			   }


            		 
            		
            		 
            	 }
            	 

         		   
         		  updateMessage("Envio Finalizado.");
         		  
         		  

                   
                    return null;
            }
        };
    }
    

//    public void AtualizaListaDetentoMassa(){
//    	
//    //	 List<Detento> dets = new ArrayList<Detento>();
//    	 
//    	 String erro = "ERROR";
//    	 
//    	  for (int i = 0; i < getDetentoFinal().size(); i++) {
//    		  
//    		  Detento det = getDetentoFinal().get(i);
//    		  
//    		  if(det.getStatusenvio().equals(erro)){
//    			  
//    			  detentoFinal.remove(det);
//    			  
//    		  }
//    		
//    	}
//
//
//    		
//    		
//    	}
    

}
