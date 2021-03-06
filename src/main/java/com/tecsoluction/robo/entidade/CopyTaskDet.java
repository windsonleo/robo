package com.tecsoluction.robo.entidade;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CopyTaskDet extends Task<List<Detento>> {
	
	private List<Detento> registros;
 
	
	public CopyTaskDet(List<Detento> registros) {
		
		this.registros = registros;
	
	}
	
	

	
    public CopyTaskDet() {
		// TODO Auto-generated constructor stub
    	registros = new ArrayList<Detento>();
	}




	@Override
    protected List<Detento> call() throws Exception {
 
       // File dir = new File("C:/Windows");
//        Registro[] files = null;
        int count = registros.size();
 
        List<Detento> copied = new ArrayList<Detento>();
        int i = 0;
        for (Detento file : registros) {
            if (file !=null) {
                this.copy(file);
                copied.add(file);
            }
            i++;
            this.updateProgress(i, count);
        }
        return copied;
    }
 
    private void copy(Detento file) throws Exception {
        this.updateMessage("Convertendo: " + file.getNome() );
        
        Thread.sleep(5);
    }
 
}
