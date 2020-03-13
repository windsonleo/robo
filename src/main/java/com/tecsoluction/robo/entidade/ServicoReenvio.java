package com.tecsoluction.robo.entidade;

import com.tecsoluction.robo.controller.DetentoTabelaController;
import com.tecsoluction.robo.controller.MainController;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicoReenvio extends Service<Integer> {


	
	private MainController logincontrol;
	
	private DetentoTabelaController detentotabcontrol;
	
	private Detento detento;
	
	
	private int tentativas = 1;
	
	
	
	
	public ServicoReenvio( MainController logincontroler,DetentoTabelaController dettabcontrol, Detento det) {

	this.logincontrol = logincontroler;
	this.detentotabcontrol = dettabcontrol;
	this.detento = det;
	
	}
	
	
	@Override
	protected void cancelled() {
		// TODO Auto-generated method stub
		super.cancelled();

		logincontrol.AtualizarQuadro();
				
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void start() {
		super.start();
		detentotabcontrol.getProgressind().progressProperty().unbind();
		detentotabcontrol.getProgressind().progressProperty().bind(this.progressProperty());
		
		detentotabcontrol.getTrace().textProperty().unbind();
		detentotabcontrol.getTrace().textProperty().bind(this.messageProperty());
//		tentativas = tentativas +1;
		
		logincontrol.AtualizarQuadro();
	}
	
	
	@Override
	protected void failed() {
//		// TODO Auto-generated method stub
		super.failed();
		
		detentotabcontrol.getProgressind().setVisible(true);
//		logincontrol.getTrace().textProperty().unbind();
//		logincontrol.getLblretornotarefa().setText("Falhas");
//		logincontrol.getLblretornotarefa().setVisible(true);
		detentotabcontrol.Inicializar(logincontrol.getDetentoErro(), logincontrol);
		logincontrol.AtualizarQuadro();
		
		tentativas = tentativas +1;
		
		if(tentativas >= 5){
			
			detentotabcontrol.getTrace().textProperty().unbind();
			detentotabcontrol.getTrace().setText("Cancelado, Tentativas Sem Sucesso: " + tentativas);
			detentotabcontrol.getProgressind().setVisible(false);
			
			this.cancelled();
			
		}else {
			
			this.restart();
			
			
		}
//		AtualizaListaDetentoMassa();
		
//		System.out.println("detento removivo status error" + getDetentoFinal().toString());
		
//		logincontrol.carregartabela(getDetentoFinal());
		
	
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
//		logincontrol.getTrace().textProperty().unbind();
	//	logincontrol.getJtgbcriartarefa().setDisable(true);
		detentotabcontrol.Inicializar(logincontrol.getDetentoErro(), logincontrol);
		logincontrol.AtualizarQuadro();
//		logincontrol.SalvarListas();
		
	}
	
	
	@Override
	protected void running() {
		// TODO Auto-generated method stub
		super.running();
//		logincontrol.getTrace().textProperty().unbind();
		detentotabcontrol.getProgressind().setVisible(true);
//		logincontrol.getBtstop().setDisable(false);
		logincontrol.AtualizarQuadro();
	}
	
	
	
	@Override
	protected void succeeded() {
		super.succeeded();
		
		
		logincontrol.AtualizarQuadro();
		detentotabcontrol.getProgressind().setVisible(false);
		//logincontrol.getProgressindtarefa().setVisible(false);
		detentotabcontrol.getTrace().textProperty().unbind();
		detentotabcontrol.getTrace().textProperty().bind(this.messageProperty());
		
		detentotabcontrol.Inicializar(logincontrol.getDetentoErro(), logincontrol);

			 
		 
	}
	
	
    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {         	  
                   
         		   
         		  updateMessage("Reenvio Iniciado. " + detento.getNome() + " Tentativas : " + tentativas);
         		  Thread.sleep(500);
         		  
         		  logincontrol.enviarEmail(detento);
         		  
         		  updateMessage("Reenvio: " + detento.getNome() + "  Status: " + detento.getStatusenvio());
                   
                    return null;
            }
        };
    }
    


    

}
