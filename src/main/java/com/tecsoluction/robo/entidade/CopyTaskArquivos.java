package com.tecsoluction.robo.entidade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CopyTaskArquivos extends Task<List<File>> {
	
	private List<File> registros;
 
	
	public CopyTaskArquivos(List<File> registros) {
		
		this.registros = registros;
	
	}
	
	

	
    public CopyTaskArquivos() {
		// TODO Auto-generated constructor stub
    	registros = new ArrayList<File>();
	}




	@Override
    protected List<File> call() throws Exception {
 
       // File dir = new File("C:/Windows");
//        Registro[] files = null;
        int count = registros.size();
 
        List<File> copied = new ArrayList<File>();
        int i = 0;
        for (File file : registros) {
            if (file !=null) {
                this.copy(file);
                copied.add(file);
            }
            i++;
            this.updateProgress(i, count);
        }
        return copied;
    }
 
    private void copy(File file) throws Exception {
        this.updateMessage("Buscando:" + file.getName() );
        Thread.sleep(100);
    }
 
}
