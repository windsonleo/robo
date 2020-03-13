package com.tecsoluction.robo.entidade;

import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CopyTaskFinanceiro extends Task<List<RegistroFinanceiro>> {
	
	private List<RegistroFinanceiro> registros;
 
	
	public CopyTaskFinanceiro(List<RegistroFinanceiro> registros) {
		
		this.registros = registros;
	
	}
	
	

	
    public CopyTaskFinanceiro() {
		// TODO Auto-generated constructor stub
    	registros = new ArrayList<RegistroFinanceiro>();
	}




	@Override
    protected List<RegistroFinanceiro> call() throws Exception {
 
       // File dir = new File("C:/Windows");
//        Registro[] files = null;
        int count = registros.size();
 
        List<RegistroFinanceiro> copied = new ArrayList<RegistroFinanceiro>();
        int i = 0;
        for (RegistroFinanceiro file : registros) {
            if (file !=null) {
                this.copy(file);
                copied.add(file);
            }
            i++;
            this.updateProgress(i, count);
        }
        return copied;
    }
 
    private void copy(RegistroFinanceiro file) throws Exception {
        this.updateMessage("Copying: " + file.getNome() );
        Thread.sleep(2);
    }
 
}
