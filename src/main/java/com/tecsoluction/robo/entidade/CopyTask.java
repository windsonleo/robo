package com.tecsoluction.robo.entidade;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CopyTask extends Task<List<Registro>> {
	
	private List<Registro> registros;
 
	
	public CopyTask(List<Registro> registros) {
		
		this.registros = registros;
	
	}
	
	

	
    public CopyTask() {
		// TODO Auto-generated constructor stub
    	registros = new ArrayList<Registro>();
	}




	@Override
    protected List<Registro> call() throws Exception {
 
       // File dir = new File("C:/Windows");
//        Registro[] files = null;
        int count = registros.size();
 
        List<Registro> copied = new ArrayList<Registro>();
        int i = 0;
        for (Registro file : registros) {
            if (file !=null) {
                this.copy(file);
                copied.add(file);
            }
            i++;
            this.updateProgress(i, count);
        }
        return copied;
    }
 
    private void copy(Registro file) throws Exception {
        this.updateMessage("Importando: " + file.getNome() );
        Thread.sleep(5);
    }
 
}
