package com.tecsoluction.robo.entidade;

import java.util.ArrayList;
import java.util.List;

import com.tecsoluction.robo.controller.MainController;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyTaskReenvio extends Task<Detento> {
	
	private Detento det;
	
	private MainController main;
 
	
	public CopyTaskReenvio(Detento registros,MainController m) {
		
		this.det = registros;
		this.main = m;
	
	}
	
	

	
    public CopyTaskReenvio() {
		// TODO Auto-generated constructor stub
    	det = new Detento();
    	main = new MainController();
	}




	@Override
    protected Detento call() throws Exception {
 
       // File dir = new File("C:/Windows");
//        Registro[] files = null;
//        int count = registros.size();
		
		int count = 100;
		
		 int x = 0;
 
        List<Integer> copied = new ArrayList<Integer>();
//        int i = 0;
       
        
        main.enviarEmail(det);
        
        for (int i = 0; i < 100; i++) {
            if (det !=null) {
                this.copy(det);
                 x = i +i;
                copied.add(x);
            }
            i++;
            this.updateProgress(i, count);
        }
        return det;
    }
 
    private void copy(Detento file) throws Exception {
        this.updateMessage("Renviando: " + file.getNome() );
        
        Thread.sleep(200);
    }
 
}
