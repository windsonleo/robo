package com.tecsoluction.robo.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriarExcel implements Serializable  {
	
	 /**
	 * 
	 */
	
	final static Logger logger = LoggerFactory.getLogger(CriarExcel.class);

	
	private static final long serialVersionUID = 1L;
	private String SAMPLE_XLSX_FILE_PATH = "";
	private  String SAMPLE_XLSX_FILE_PATH_MATRIX = "";
	private Detento detento;
	private List<Detento> detentos;
	private List<Detento> detentosucesso;
	private List<Detento> detentospendentes;
	
	private  List<Detento> detentosMatriz;
	
	private   List<Registro> substitutosSemNotificacao;	
	
	private   List<Registro> regvalidos;	
	
	private   List<Registro> reginvalidos;	



	private  File file;
	
	
	public CriarExcel(String path,List<Detento>dets,List<Detento>detsucesso,List<Detento>detpend){
		
		this.SAMPLE_XLSX_FILE_PATH = path;
		this.detentos = dets;
		this.detentosucesso = detsucesso;
		this.detentospendentes = detpend;
		
	}
	
	
	public CriarExcel(String path,List<Detento> det,List<Registro> subst){
		
		this.SAMPLE_XLSX_FILE_PATH_MATRIX = path;
		this.detentosMatriz = det;
		this.file = new File(path);
		this.substitutosSemNotificacao = subst;
		
	
	}
	
	
	public CriarExcel(String path){
		
		this.SAMPLE_XLSX_FILE_PATH_MATRIX = path;
		this.file = new File(path);
		
	}
	
	public CriarExcel(List<Registro> regvalidos,List<Registro> reginvalidos){
		
		this.regvalidos = regvalidos;
		this.reginvalidos =reginvalidos;
		
	}
	
	
	
	@SuppressWarnings("unused")
	private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
			throws IOException {
		Workbook workbook = null;

		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}
	
	
	public List<Detento> createdExcelFile(String excelFilePath) throws IOException {
		//detentos = new ArrayList<Detento>();
		//List<Violacao> listViolacoes = new ArrayList<Violacao>();
		String[] headers = new String[] { "Nome", "Prontuario", "Estabelecimento","Perfil","Data Inicio","Data Violação","Data Finalização","Duração","Descrição","Caracteristica","Email","Alarme","Processo","Vep","IdViolacao","Idnotificação","Duração Alarme","Envio","Erros" };
		
	//	FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook();
		Sheet firstSheet = workbook.createSheet("Envios Erros");
		
		
		for (int rn=0; rn<headers.length; rn++) {
			   Row r = firstSheet.createRow(rn);
			   r.createCell(0).setCellValue(headers[rn]);
			}
		
		 firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
	//	boolean primeiraVez = true;

		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
		//	Detento registro = new Detento();
			//Violacao violacao = new Violacao();
			
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();
				
				DataFormatter dataFormatter = new DataFormatter();
				 String cellValue = dataFormatter.formatCellValue(nextCell);
//				 System.out.println(cellValue);
				
				 switch (columnIndex) {
				
				case 0:
					
						
						firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getNome());
						

					break;
				
				
//				case 1:
//					registro.setIdmonitorado(cellValue);
//				
////					 System.out.print(registro.getIdmonitorado());
//					break;
					
				case 1:
					
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getProntuario());

					break;
				case 2:
				
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getEstabelecimento());

					break;
				case 3:
					
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getPerfil());

					break;
					
//				case 5:
//					registro.setArtigos(cellValue);
////					System.out.print(registro.getArtigos());
//					
//					//detento.setPrice((double) getCellValue(nextCell));
//					break;
					
//				case 6:
//					registro.setAtivo(cellValue);
//					 
////					 System.out.print(registro.getAtivo());
//					//detento.setPrice((double) getCellValue(nextCell));
//					break;
//					
//				case 7:
//					registro.setAlarme(cellValue);
//				
////					 System.out.print(registro.getAlarme());
//					//detento.setPrice((double) getCellValue(nextCell));
//					break;
					
				case 4:

					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().get(0).getDataviolacao());

					
					
					break;
					
				case 5:

					
					
					break;
					
				case 6:
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().get(0).getDuracao());
					
					//detento.setPrice((double) getCellValue(nextCell));
//					 System.out.print(registro.getDatafinalizacao());

					break;
					
				case 7:

					
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().get(0).getCaracteristica());

					
					
					break;
					
				case 8:

					
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getEmail());

					
					break;
					
				case 9:

					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getStatusenvio());

					
					
					break;
					
				case 10:

					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getErros().toString());

					
					break;
					
					
				case 11:

					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().size());

					break;
					
					
				case 12:
					firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getProcesso());

					break;
					

				
				 
			case 13:
				firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getVep());

				break;
				
			case 14:
				firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().get(0).getId());

				break;
				
				
			case 15:
				firstSheet.createRow(columnIndex).createCell(columnIndex).setCellValue(detentos.get(columnIndex).getViolacoes().get(0).getNotificacoes().get(0).getId());

				break;
				

			}
				
				
				
			}
			
			
			//detento.addViolacao(violacao);
			//detentos.add(registro);
			//listViolacoes.add(violacao);
		}
		
		
		
		
		 try (FileOutputStream fos = 
                 new FileOutputStream(new File(SAMPLE_XLSX_FILE_PATH))) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

		return detentos;
	}
	 
	
//	private Object getCellValue(Cell cell) {
//		switch (cell.getCellTypeEnum()) {
//		case STRING:
//			return cell.getStringCellValue().toUpperCase();
//
//		case BOOLEAN:
//			return cell.getBooleanCellValue();
//
//		case NUMERIC:
//			return cell.getNumericCellValue();
//		}
//
//		return null;
//	} 
	
	
//	private void CarregarHandler() {
//		
//		  EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
//	            public void handle(ActionEvent e) 
//	            { 
//	                // set progress to different level of progressindicator 
//
//	            	
//	            	logincontrol.getProgressind().setProgress(0.0);
//	            	
//	        		copyTask = new CopyTask(registros);
//	        		
//	        		logincontrol.getProgressind().progressProperty().unbind();
//	        		
//	        		logincontrol.getProgressind().progressProperty().bind(copyTask.progressProperty());
//	        		
//	        		logincontrol.getTrace().textProperty().unbind();
//	        		
//	        		logincontrol.getTrace().textProperty().bind(copyTask.messageProperty());
//
//
//	                 copyTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
//	                         new EventHandler<WorkerStateEvent>() {
//
//	                             @Override
//	                             public void handle(WorkerStateEvent t) {
//	                                 List<Registro> copied = copyTask.getValue();
//	                                 logincontrol.getTrace().textProperty().unbind();
//	                                 logincontrol.getTrace().setText("Copied: " + copied.size());
//	                                 
//	                                 System.out.println("copied:" + copied.toString());
//	                                 return;
//	                             }
//	                         });
//
//	                 // Start the Task.
//	                 new Thread(copyTask).start();
//	            	
//	            	
//
//	            } 
//	  
//	        }; 
//		
//		

 
	
	public void Criar() throws IOException{
		
		String[] headers = new String[] { "Nome", "Prontuario", "Estabelecimento","Perfil","Data Inicio","Data Violação","Data Finalização","Duração","Descrição","Caracteristica","Email","Alarme","Processo","Vep","IdViolacao","Idnotificação","Duração Alarme","Envio","Erros" };

		
		Workbook workbook = new XSSFWorkbook();
		Sheet firstSheet = workbook.createSheet("Envios Erros");
		
		Sheet secondSheet = workbook.createSheet("Envios Sucesso");
		
		Sheet treeSheet = workbook.createSheet("Envios Pendentes");
		
		 CellStyle style =  workbook.createCellStyle();
	      //  HSSFPalette palette =  ((HSSFWorkbook) workbook).getCustomPalette();
	        
	        
	     //   palette.setColorAtIndex(palette.getColor(36).getIndex(), (byte) 255, (byte) 255 , (byte) 204 ); //amarelo
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         //   style.set
		
		FileOutputStream fos = null;

		try {
		
			fos = new FileOutputStream(new File(SAMPLE_XLSX_FILE_PATH));
		
			Row r = firstSheet.createRow(0);
			
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r.createCell(rn).setCellValue(headers[rn]);
			   
//			  
//			   r.setRowStyle(style);
			   
			   firstSheet.autoSizeColumn(rn);
			}
	//	r.setRowStyle(style);
		
	//	row.createCell(0).setCellStyle(style);
		// firstSheet = workbook.getSheetAt(0);
		
		int p = 1;
		
		//firstSheet.autoSizeColumn(0);

		for (Detento cd : getDetentos()) {
	//	firstSheet.autoSizeColumn(i);
			
			
			String nome = null;
			String prontuario= null;
			String estabelecimento= null;
			String perfil= null;

			String datainicio = null;
			String dataviola= null;
			String datafim= null;
			String duracao= null;
			String desc= null;
			String carac= null;
			String email= null;
			String alarme= null;
			
			String proc= null;
			String vep= null;
			String idviola= null;
			String idnoti= null;
			String durac= null;
			String envio= null;
			String erros= null;
			
			
			List<Notificacao> nots = new ArrayList<Notificacao>();
			
			List<Violacao> viola = new ArrayList<Violacao>();

			List<String> durss  = new ArrayList<String>();
			
			
			
			for(Violacao v : cd.getViolacoes()) {
				
				durss.add(v.getDuracaoalarme());	
				viola.add(v);
				
				
				
				
				
				for(Notificacao n : v.getNotificacoes()){
					
				//	idnoti1 = idnoti1 + n.getId();
					
					nots.add(n);
					
				}
				
			}
			
			
			
			
//		Row row = firstSheet.createRow(p);
		
//		for (int j = 0; j < viola.size(); j++) {
			
			int s =0;
			int iddesc = 0;
			for(Violacao vio : viola) {	
			
//			Violacao vio = viola.get(j);
			
			int idnotitotal = vio.getNotificacoes().size();
			
//			int iddesc = 0;
			
//			System.out.println("valor j " + j);
//			System.out.println("valor idnotitotal" + idnotitotal);
//			System.out.println("valor viola" + viola.size());
//			System.out.println("valor idnoti" + idnoti);
			
			
			if(iddesc < idnotitotal){
				idnoti = vio.getNotificacoes().get(iddesc).getId();
			
				iddesc = iddesc++;
				
			}else {
				
				
//				iddesc = idnotitotal-1;
				
				
			}
			
			 
		
			Row row = firstSheet.createRow(p);
			
			row.createCell(0).setCellValue(cd.getNome());
			//	firstSheet.autoSizeColumn(s);
			row.createCell(1).setCellValue(cd.getProntuario());
			//	firstSheet.autoSizeColumn(s);
			row.createCell(2).setCellValue(cd.getEstabelecimento());
			//	firstSheet.autoSizeColumn(s);
			row.createCell(3).setCellValue(cd.getPerfil());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(4).setCellValue(vio.getDatainicio());
			//	firstSheet.autoSizeColumn(s);
						
			row.createCell(5).setCellValue(vio.getDataviolacao());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(6).setCellValue(vio.getDatafinalizacao());
			//	firstSheet.autoSizeColumn(s);
			
			
			row.createCell(7).setCellValue(vio.getDuracao());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(8).setCellValue(vio.getNotificacoes().get(iddesc).getDescricao());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(9).setCellValue(vio.getCaracteristica());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(10).setCellValue(cd.getEmail());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(11).setCellValue(vio.getAlarme());
			//	firstSheet.autoSizeColumn(s);
			
			
			row.createCell(12).setCellValue(cd.getProcesso());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(13).setCellValue(cd.getVep());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(14).setCellValue(vio.getId());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(15).setCellValue(idnoti);
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(16).setCellValue(vio.getDuracaoalarme());
			//	firstSheet.autoSizeColumn(s);
			
			
			row.createCell(17).setCellValue(cd.getStatusenvio());
			//	firstSheet.autoSizeColumn(s);
			
			row.createCell(18).setCellValue(cd.getErros().toString());
			//	firstSheet.autoSizeColumn(s);
			
			s++;
			p++;
//			iddesc++;
			
		}
		
//		firstSheet.autoSizeColumn(i);
//		row.createCell(0).setCellValue(cd.getNome());
//		firstSheet.autoSizeColumn(0);
//		row.createCell(1).setCellValue(cd.getProntuario());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(2).setCellValue(cd.getEstabelecimento());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(3).setCellValue(cd.getPerfil());
//		firstSheet.autoSizeColumn(i);
		
		

		


		

		
		
//		row.createCell(4).setCellValue(cd.getViolacoes().get(0).getDataviolacao());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(5).setCellValue(cd.getViolacoes().get(0).getDuracao());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(6).setCellValue(cd.getViolacoes().get(0).getCaracteristica());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(7).setCellValue(cd.getEmail());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(8).setCellValue(cd.getStatusenvio());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(9).setCellValue(cd.getErros().toString());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(10).setCellValue(cd.getViolacoes().size());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(11).setCellValue(cd.getProcesso());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(12).setCellValue(cd.getVep());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(13).setCellValue(cd.getViolacoes().toString());
//		firstSheet.autoSizeColumn(i);
		
	//	String idnoti1 = new String("");
		

		

		
		
//		row.createCell(14).setCellValue(nots.toString());
//		firstSheet.autoSizeColumn(i);
//		row.createCell(15).setCellValue(durss.toString());
//		firstSheet.autoSizeColumn(i);
	//	firstSheet.autoSizeColumn(i);

//		p++;

		} // fim do for
		
		
		Row r2 = secondSheet.createRow(0);
		
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r2.createCell(rn).setCellValue(headers[rn]);
			   
//			  
//			   r.setRowStyle(style);
			   
			   secondSheet.autoSizeColumn(rn);
			}
	//	r.setRowStyle(style);
		
	//	row.createCell(0).setCellStyle(style);
		// secondSheet = workbook.getSheetAt(0);
		
		int q = 1;
		
		//secondSheet.autoSizeColumn(0);
		
//		detentos.clear();
//		detentos.addAll(getDetentosucesso());

		for (Detento cd : detentosucesso) {
	//	secondSheet.autoSizeColumn(i);
	//	Row row = secondSheet.createRow(j);
			
			String idnotii= null;
		
		List<Notificacao> nots = new ArrayList<Notificacao>();
		
		List<Violacao> viola = new ArrayList<Violacao>();

		List<String> durss  = new ArrayList<String>();
		
		
		
		for(Violacao v : cd.getViolacoes()) {
			
			durss.add(v.getDuracaoalarme());	
			viola.add(v);
			
			
			
			
			
			for(Notificacao n : v.getNotificacoes()){
				
			//	idnoti1 = idnoti1 + n.getId();
				
				nots.add(n);
				
			}
			
		}
		
		
		
		
//	Row roww = secondSheet.createRow(q);
	
		
	int w = 0;	
//	for (int w = 0; w < viola.size(); w++) {
	int iddesc = 0;
	for(Violacao vio : viola) {	
		
//		Violacao vio = viola.get(w);
		
		
		int idnotitotal = vio.getNotificacoes().size();
		
		
		if(iddesc < idnotitotal){
			
			idnotii = vio.getNotificacoes().get(iddesc).getId();
			 iddesc = iddesc++;
			
		}else {
			
			
//			iddesc = idnotitotal-1;
		}
		
		
	
		Row roww = secondSheet.createRow(q);
		
		roww.createCell(0).setCellValue(cd.getNome());
		//secondSheet.autoSizeColumn(w);
		roww.createCell(1).setCellValue(cd.getProntuario());
		//	secondSheet.autoSizeColumn(w);
		roww.createCell(2).setCellValue(cd.getEstabelecimento());
		//secondSheet.autoSizeColumn(w);
		roww.createCell(3).setCellValue(cd.getPerfil());
		//	secondSheet.autoSizeColumn(w);
		
		roww.createCell(4).setCellValue(vio.getDatainicio());
		//	secondSheet.autoSizeColumn(w);
					
		roww.createCell(5).setCellValue(vio.getDataviolacao());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(6).setCellValue(vio.getDatafinalizacao());
		//secondSheet.autoSizeColumn(w);
		
		
		roww.createCell(7).setCellValue(vio.getDuracao());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(8).setCellValue(vio.getNotificacoes().get(iddesc).getDescricao());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(9).setCellValue(vio.getCaracteristica());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(10).setCellValue(cd.getEmail());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(11).setCellValue(vio.getAlarme());
		//secondSheet.autoSizeColumn(w);
		
		
		roww.createCell(12).setCellValue(cd.getProcesso());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(13).setCellValue(cd.getVep());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(14).setCellValue(vio.getId());
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(15).setCellValue(idnotii);
		//secondSheet.autoSizeColumn(w);
		
		roww.createCell(16).setCellValue(vio.getDuracaoalarme());
		//secondSheet.autoSizeColumn(w);
		
		
		roww.createCell(17).setCellValue(cd.getStatusenvio());
		//	secondSheet.autoSizeColumn(w);
		
		roww.createCell(18).setCellValue(cd.getErros().toString());
		//secondSheet.autoSizeColumn(w);
		
		w++;
		q++;
//		iddesc++;
		
	}
		
		
		
//		secondSheet.autoSizeColumn(j);
//		row.createCell(0).setCellValue(cd.getNome());
//		secondSheet.autoSizeColumn(0);
//		row.createCell(1).setCellValue(cd.getProntuario());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(2).setCellValue(cd.getEstabelecimento());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(3).setCellValue(cd.getPerfil());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(4).setCellValue(cd.getViolacoes().get(0).getDataviolacao());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(5).setCellValue(cd.getViolacoes().get(0).getDuracao());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(6).setCellValue(cd.getViolacoes().get(0).getCaracteristica());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(7).setCellValue(cd.getEmail());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(8).setCellValue(cd.getStatusenvio());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(9).setCellValue(cd.getErros().toString());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(10).setCellValue(cd.getViolacoes().size());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(11).setCellValue(cd.getProcesso());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(12).setCellValue(cd.getVep());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(13).setCellValue(cd.getViolacoes().toString());
//		secondSheet.autoSizeColumn(j);
//		
//		List<Notificacao> notss = new ArrayList<Notificacao>();
//		
//		List<String> durs  = new ArrayList<String>();
//		
//		for(Violacao v : cd.getViolacoes()) {
//			
//			durs.add(v.getDuracaoalarme());
//			
//			for(Notificacao n : v.getNotificacoes()){
//				
//			//	idnoti1 = idnoti1 + n.getId();
//				
//				notss.add(n);
//				
//			}
//			
//		}
//		
//		row.createCell(14).setCellValue(notss.toString());
//		secondSheet.autoSizeColumn(j);
//		row.createCell(15).setCellValue(durs.toString());
//		secondSheet.autoSizeColumn(j);
//	//	secondSheet.autoSizeColumn(i);

//		q++;

		} // fim do for
		
		
		Row r3 = treeSheet.createRow(0);
		
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r3.createCell(rn).setCellValue(headers[rn]);
			   
//			  
//			   r.setRowStyle(style);
			   
			   treeSheet.autoSizeColumn(rn);
			}
		
		
		
		int k = 1;
		
		//secondSheet.autoSizeColumn(0);
		
//		detentos.clear();
//		detentos.addAll(getDetentosucesso());

		for (Detento cd : detentospendentes) {
	//	secondSheet.autoSizeColumn(i);
	//	Row row = treeSheet.createRow(k);
		
			String idnotiii= null;
		
		List<Notificacao> nots = new ArrayList<Notificacao>();
		
		List<Violacao> viola = new ArrayList<Violacao>();

		List<String> durss  = new ArrayList<String>();
		
		
		
		for(Violacao v : cd.getViolacoes()) {
			
			durss.add(v.getDuracaoalarme());	
			viola.add(v);
			
			
			
			
			
			for(Notificacao n : v.getNotificacoes()){
				
			//	idnoti1 = idnoti1 + n.getId();
				
				nots.add(n);
				
			}
			
		}
		
		
		
		
//	Row royy = treeSheet.createRow(k);
	
//	for (int y = 0; y < viola.size(); y++) {
		
		int y=0;
		int iddesc = 0;
		for(Violacao vio : viola) {	
		
//		Violacao vio = viola.get(y);
		
		Row royy = treeSheet.createRow(k);
		
	int idnotitotal = vio.getNotificacoes().size();
	
	
		
		if(iddesc < idnotitotal){
			
			idnotiii = vio.getNotificacoes().get(iddesc).getId();
			
			iddesc = iddesc++;

			
		}else {
			
//			iddesc = idnotitotal-1;
			
		}
		
		
	
		
		
		royy.createCell(0).setCellValue(cd.getNome());
		//treeSheet.autoSizeColumn(y);
		royy.createCell(1).setCellValue(cd.getProntuario());
		//	treeSheet.autoSizeColumn(y);
		royy.createCell(2).setCellValue(cd.getEstabelecimento());
		//	treeSheet.autoSizeColumn(y);
		royy.createCell(3).setCellValue(cd.getPerfil());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(4).setCellValue(vio.getDatainicio());
		//treeSheet.autoSizeColumn(y);
					
		royy.createCell(5).setCellValue(vio.getDataviolacao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(6).setCellValue(vio.getDatafinalizacao());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(7).setCellValue(vio.getDuracao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(8).setCellValue(vio.getNotificacoes().get(iddesc).getDescricao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(9).setCellValue(vio.getCaracteristica());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(10).setCellValue(cd.getEmail());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(11).setCellValue(vio.getAlarme());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(12).setCellValue(cd.getProcesso());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(13).setCellValue(cd.getVep());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(14).setCellValue(vio.getId());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(15).setCellValue(idnotiii);
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(16).setCellValue(vio.getDuracaoalarme());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(17).setCellValue(cd.getStatusenvio());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(18).setCellValue(cd.getErros().toString());
		//treeSheet.autoSizeColumn(y);
		
		y++;
		k++;
//		iddesc++;
		
	}
		
		
		
		
		
////		secondSheet.autoSizeColumn(j);
//		row.createCell(0).setCellValue(cd.getNome());
//		treeSheet.autoSizeColumn(0);
//		row.createCell(1).setCellValue(cd.getProntuario());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(2).setCellValue(cd.getEstabelecimento());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(3).setCellValue(cd.getPerfil());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(4).setCellValue(cd.getViolacoes().get(0).getDataviolacao());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(5).setCellValue(cd.getViolacoes().get(0).getDuracao());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(6).setCellValue(cd.getViolacoes().get(0).getCaracteristica());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(7).setCellValue(cd.getEmail());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(8).setCellValue(cd.getStatusenvio());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(9).setCellValue(cd.getErros().toString());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(10).setCellValue(cd.getViolacoes().size());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(11).setCellValue(cd.getProcesso());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(12).setCellValue(cd.getVep());
//		treeSheet.autoSizeColumn(k);
//		row.createCell(13).setCellValue(cd.getViolacoes().toString());
//		treeSheet.autoSizeColumn(k);
//		
//		List<Notificacao> notss = new ArrayList<Notificacao>();
//		
//		List<String> durs  = new ArrayList<String>();
//		
//		for(Violacao v : cd.getViolacoes()) {
//			
//			durs.add(v.getDuracaoalarme());
//			
//			for(Notificacao n : v.getNotificacoes()){
//				
//			//	idnoti1 = idnoti1 + n.getId();
//				
//				notss.add(n);
//				
//			}
//			
//		}
//		
//		row.createCell(14).setCellValue(notss.toString());
//		secondSheet.autoSizeColumn(k);
//		row.createCell(15).setCellValue(durs.toString());
//		secondSheet.autoSizeColumn(k);
//	//	secondSheet.autoSizeColumn(i);

//		k++;

		} // fim do for
		
		
		workbook.write(fos);

		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Erro ao exportar arquivo" + e);
		} finally {
		
		fos.flush();
		fos.close();
//		workbook.close();
		
		
	//	workbook.close();
		}
		
		
		}
	
	
public void CriarAnalise(List<Registro> regvalidos,List<Registro> reginvalidos,File file) throws IOException{
		
		String[] headers = new String[] { "Nome", "Prontuario", "Estabelecimento","Perfil","Data Inicio","Data Violação","Data Finalização","Duração","Descrição","Caracteristica","Email","Alarme","Processo","Vep","IdViolacao","Idnotificação","Duração Alarme","Envio","Erros" };

		
		Workbook workbook = new XSSFWorkbook();
	//	Sheet firstSheet = workbook.createSheet("Envios Erros");
		
		Sheet secondSheet = workbook.createSheet("Registro Invalido");
		
		Sheet treeSheet = workbook.createSheet("Registro valido");
		
		 CellStyle style =  workbook.createCellStyle();
	      //  HSSFPalette palette =  ((HSSFWorkbook) workbook).getCustomPalette();
	        
	        
	     //   palette.setColorAtIndex(palette.getColor(36).getIndex(), (byte) 255, (byte) 255 , (byte) 204 ); //amarelo
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         //   style.set
		
		FileOutputStream fos = null;

		try {
		
			fos = new FileOutputStream(new File(file.getPath()));
		
			Row r = secondSheet.createRow(0);
			
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r.createCell(rn).setCellValue(headers[rn]);
			   
//			  
//			   r.setRowStyle(style);
			   
			   secondSheet.autoSizeColumn(rn);
			}
	//	r.setRowStyle(style);
		
	//	row.createCell(0).setCellStyle(style);
		// firstSheet = workbook.getSheetAt(0);
		
		int p = 1;

		
		int q = 1;
		


		for (Registro cd : reginvalidos) {

		
			
		
	int w = 0;	
	
		
	
		Row roww = secondSheet.createRow(q);
		
		roww.createCell(0).setCellValue(cd.getNome());
		//treeSheet.autoSizeColumn(y);
		roww.createCell(1).setCellValue(cd.getProntuario());
		//	treeSheet.autoSizeColumn(y);
		roww.createCell(2).setCellValue(cd.getEstabelecimento());
		//	treeSheet.autoSizeColumn(y);
		roww.createCell(3).setCellValue(cd.getPerfil());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(4).setCellValue(cd.getDatainicio());
		//treeSheet.autoSizeColumn(y);
					
		roww.createCell(5).setCellValue(cd.getDataviolacao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(6).setCellValue(cd.getDatafinalizacao());
		//treeSheet.autoSizeColumn(y);
		
		
		roww.createCell(7).setCellValue(cd.getDuracao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(8).setCellValue(cd.getDescricao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(9).setCellValue(cd.getCaracteristica());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(10).setCellValue(cd.getEmail());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(11).setCellValue(cd.getAlarme());
		//treeSheet.autoSizeColumn(y);
		
		
		roww.createCell(12).setCellValue(cd.getProcesso());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(13).setCellValue(cd.getVep());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(14).setCellValue(cd.getIdviolacao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(15).setCellValue(cd.getIdnotitificacao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(16).setCellValue(cd.getDuracaoalarme());
		//treeSheet.autoSizeColumn(y);
		
		
		roww.createCell(17).setCellValue(cd.getTipoviolacao());
		//treeSheet.autoSizeColumn(y);
		
		roww.createCell(18).setCellValue(cd.getErros().toString());
		//secondSheet.autoSizeColumn(w);
		
		w++;
		q++;
//		iddesc++;
		
	}
		
		

		 // fim do for
		
		
		Row r3 = treeSheet.createRow(0);
		
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r3.createCell(rn).setCellValue(headers[rn]);
			   
//			  
//			   r.setRowStyle(style);
			   
			   treeSheet.autoSizeColumn(rn);
			}
		
		
		
		int k = 1;
		

		for (Registro cd : regvalidos) {

				
		Row royy = treeSheet.createRow(k);

		royy.createCell(0).setCellValue(cd.getNome());
		//treeSheet.autoSizeColumn(y);
		royy.createCell(1).setCellValue(cd.getProntuario());
		//	treeSheet.autoSizeColumn(y);
		royy.createCell(2).setCellValue(cd.getEstabelecimento());
		//	treeSheet.autoSizeColumn(y);
		royy.createCell(3).setCellValue(cd.getPerfil());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(4).setCellValue(cd.getDatainicio());
		//treeSheet.autoSizeColumn(y);
					
		royy.createCell(5).setCellValue(cd.getDataviolacao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(6).setCellValue(cd.getDatafinalizacao());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(7).setCellValue(cd.getDuracao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(8).setCellValue(cd.getDescricao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(9).setCellValue(cd.getCaracteristica());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(10).setCellValue(cd.getEmail());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(11).setCellValue(cd.getAlarme());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(12).setCellValue(cd.getProcesso());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(13).setCellValue(cd.getVep());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(14).setCellValue(cd.getIdviolacao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(15).setCellValue(cd.getIdnotitificacao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(16).setCellValue(cd.getDuracaoalarme());
		//treeSheet.autoSizeColumn(y);
		
		
		royy.createCell(17).setCellValue(cd.getTipoviolacao());
		//treeSheet.autoSizeColumn(y);
		
		royy.createCell(18).setCellValue(cd.getErros().toString());
		//treeSheet.autoSizeColumn(y);
		
		//y++;
		k++;
//		iddesc++;
		
	}
		
		
		
		workbook.write(fos);

		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Erro ao exportar arquivo" + e);
		} finally {
		
		fos.flush();
		fos.close();
//		workbook.close();
		
		
	//	workbook.close();
		}
		
		
		}


public void CriarMatrix() throws IOException {
		
		String[] headers = new String[] { "Nome", "Prontuario", "Estabelecimento","Perfil","Data Inicio","Data Violação","Data Finalização","Duração","Descrição","Caracteristica","Email","Alarme","Processo","Vep","IdViolacao","Idnotificação","Duração Alarme","Envio","Erros","Matrix","Data Envio" };

		
		Workbook workbook = new XSSFWorkbook();
		Sheet firstSheet = workbook.createSheet("Envios Consolidado");
		Sheet secondSheet = workbook.createSheet("Substitutas sem Notificação");

		
		 CellStyle style =  workbook.createCellStyle();
	        
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		FileOutputStream fos = null;

		try {
		
			fos = new FileOutputStream(new File(SAMPLE_XLSX_FILE_PATH_MATRIX));
		
			Row r = firstSheet.createRow(0);
			
		for (int rn=0; rn<headers.length; rn++) {
			   
			   r.createCell(rn).setCellValue(headers[rn]);
			   
//			  
			   
			   firstSheet.autoSizeColumn(rn);
			}
		
		
		Row r2 = secondSheet.createRow(0);
		
	for (int rn=0; rn<headers.length; rn++) {
		   
		   r2.createCell(rn).setCellValue(headers[rn]);
		   
//		  
		   
		   secondSheet.autoSizeColumn(rn);
		}


		workbook.write(fos);

		} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Erro ao criar matriz " + e);
		} finally {
		try {
		fos.flush();
		fos.close();
//		workbook.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		workbook.close();
		
		}
		}



public boolean ExisteMatrix() {
	
	boolean existe = false;
	
	File matrix = null;
	

	try {
	
		 matrix = new File (SAMPLE_XLSX_FILE_PATH_MATRIX);
	
		if(matrix.exists()) {
			
			existe = true;
			
		}else {
			
			File f = new File(matrix.getPath().replace(matrix.getName(), ""));
			
			if( f.mkdirs() ){
			    System.out.println("Diretório matriz  criado");
			    
			    matrix.createNewFile();
//			    oficiook = true;
			    
			}else{
				
				
			    System.out.println("Diretório matriz não criado");
//			    oficiook = false;
			}
			
			existe = false;
			
		}
	


	}catch (Exception e) {

	System.out.println("exception existe matrix: " + e.getMessage());
	
	
	}
	


	return existe;

}
	
	



private  void modifyMatriz() throws InvalidFormatException, IOException {
    // Obtain a workbook from the excel file
    Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH_MATRIX));

    // Get Sheet at index 0
    Sheet sheet = workbook.getSheetAt(0);

    // Get Row at index 1
    Row row = sheet.getRow(1);
    
    // Get the Cell at index 2 from the above row
    Cell cell = row.getCell(2);

    // Create the cell if it doesn't exist
    if (cell == null)
        cell = row.createCell(2);

    // Update the cell's value
    cell.setCellType(CellType.STRING);
    cell.setCellValue("Updated Value");

    // Write the output to the file
    FileOutputStream fileOut = new FileOutputStream(SAMPLE_XLSX_FILE_PATH_MATRIX);
    workbook.write(fileOut);
    fileOut.close();

    // Closing the workbook
    workbook.close();
}


private  void removerLinhaMatriz(Row row, int num) throws InvalidFormatException, IOException {
    // Obtain a workbook from the excel file
    Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH_MATRIX));

    // Get Sheet at index 0
    Sheet sheet = workbook.getSheetAt(0);

    // Get Row at index 1
     row = sheet.getRow(1);


    // Write the output to the file
    FileOutputStream fileOut = new FileOutputStream(SAMPLE_XLSX_FILE_PATH_MATRIX);
    workbook.write(fileOut);
    fileOut.close();

    // Closing the workbook
    workbook.close();
}


private  void AdicionarLinhaMatriz(Row row, int num) throws InvalidFormatException, IOException {
    // Obtain a workbook from the excel file
    Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH_MATRIX));

    // Get Sheet at index 0
    Sheet sheet = workbook.getSheetAt(0);

    // Get Row at index 1
     int ultimalinha = sheet.getLastRowNum();
     
     
     
     
     
     
     


    // Write the output to the file
    FileOutputStream fileOut = new FileOutputStream(SAMPLE_XLSX_FILE_PATH_MATRIX);
    workbook.write(fileOut);
    fileOut.close();

    // Closing the workbook
    workbook.close();
}

		
public void AtualizarMatriz(List<Detento> cds) {
    // Obtain a workbook from the excel file
    
	
	
	Workbook workbook = null;
     FileOutputStream fileOut = null;
    
    
     
   try{
	   
	   fileOut = new FileOutputStream(file);
//	    fis = new FileInputStream(file);
	   workbook = new XSSFWorkbook();
	   Sheet sheet = workbook.createSheet("Envios Consolidado");
	  int numrow = sheet.getLastRowNum();
	   
	
     
		
		

		
	   int p =1;
		for(Detento cd: cds){
			
			List<Violacao> viola = new ArrayList<Violacao>();
			
			
		for(Violacao v : cd.getViolacoes()) {
			
			//durss.add(v.getDuracaoalarme());	
			viola.add(v);
			
			
			
		}

		
		
		int s =0;
		int iddesc = 0;
		String idnoti = null;
	
		for(Violacao vio : viola) {	
				
		int idnotitotal = vio.getNotificacoes().size();
		
		
		if(iddesc < idnotitotal){
			idnoti = vio.getNotificacoes().get(iddesc).getId();
		
			iddesc = iddesc++;
			
		}else {
			
			
//			iddesc = idnotitotal-1;
			
			
		}
		
		 
	
		Row row = sheet.createRow(p);
		
		row.createCell(0).setCellValue(cd.getNome());
		sheet.autoSizeColumn(s);
		row.createCell(1).setCellValue(cd.getProntuario());
		sheet.autoSizeColumn(s);
		row.createCell(2).setCellValue(cd.getEstabelecimento());
		sheet.autoSizeColumn(s);
		row.createCell(3).setCellValue(cd.getPerfil());
		sheet.autoSizeColumn(s);
		
		row.createCell(4).setCellValue(vio.getDatainicio());
		sheet.autoSizeColumn(s);
					
		row.createCell(5).setCellValue(vio.getDataviolacao());
		sheet.autoSizeColumn(s);
		
		row.createCell(6).setCellValue(vio.getDatafinalizacao());
		sheet.autoSizeColumn(s);
		
		
		row.createCell(7).setCellValue(vio.getDuracao());
		sheet.autoSizeColumn(s);
		
		row.createCell(8).setCellValue(vio.getNotificacoes().get(iddesc).getDescricao());
		sheet.autoSizeColumn(s);
		
		row.createCell(9).setCellValue(vio.getCaracteristica());
		sheet.autoSizeColumn(s);
		
		row.createCell(10).setCellValue(cd.getEmail());
		sheet.autoSizeColumn(s);
		
		row.createCell(11).setCellValue(vio.getAlarme());
		sheet.autoSizeColumn(s);
		
		
		row.createCell(12).setCellValue(cd.getProcesso());
		sheet.autoSizeColumn(s);
		
		row.createCell(13).setCellValue(cd.getVep());
		sheet.autoSizeColumn(s);
		
		row.createCell(14).setCellValue(vio.getId());
		sheet.autoSizeColumn(s);
		
		row.createCell(15).setCellValue(idnoti);
		sheet.autoSizeColumn(s);
		
		row.createCell(16).setCellValue(vio.getDuracaoalarme());
		sheet.autoSizeColumn(s);
		
		
		row.createCell(17).setCellValue(cd.getStatusenvio());
		sheet.autoSizeColumn(s);
		
		row.createCell(18).setCellValue(cd.getErros().add("dfghjk"));
		sheet.autoSizeColumn(s);
		
		row.createCell(19).setCellValue(cd.isMatrix());
		sheet.autoSizeColumn(s);
		
		s++;
		p++;
//		ultimalinha++;
		
		
	}
		
		
		
		}
     
     
    // Write the output to the file
    
    workbook.write(fileOut);

//    fis.close();

    System.out.println("adicioonou linha a matriz");


    // Closing the workbook
   
   }catch (IOException  e){ 

   System.out.println("erro ao adicioonar linha a matriz" + e.getStackTrace());
 
   
   }
   
   finally {
	   
//	   fileOut.flush();
//	  fileOut.close();
	  
//	 workbook.close();
	   try{
		   
	    fileOut.flush();
	    fileOut.close();
	    workbook.close();
	    
	   }catch (IOException InvalidFormatException  ) {

	   
	   
	   
	   }
}

	
	
	
	
}



public  void write() throws IOException, InvalidFormatException 
{ 
    InputStream inp = new FileInputStream(file); 
    Workbook wb = WorkbookFactory.create(inp); 
    Sheet sheet = wb.getSheetAt(0); 
    Sheet sheet2 = wb.getSheetAt(1); 
    int num = sheet.getLastRowNum(); 
    int num2 = sheet2.getLastRowNum(); 
    
    
	//FileOutputStream fos = null;

	try {
		
		//fos = new FileOutputStream(file);
    
    for(Detento detento: detentosMatriz) {
    	
    	int numviolacao=0;
    	
    	if(!detento.isMatrix()){
    		
    		
    	
    	for(Violacao violacao: detento.getViolacoes()){
    		
    		int idnotitotal = violacao.getNotificacoes().size();
    		
    		
    		String idnoti = null;
			int iddesc = 0;
			
			if(iddesc < idnotitotal){
    			idnoti = violacao.getNotificacoes().get(iddesc).getId();
    		
    			iddesc = iddesc++;
    			
    		}else {
    			
    			
//    			iddesc = idnotitotal-1;
    			
    			
    		}
    		
    		 
    	
    		Row row = sheet.createRow(num+1);
    		
    		row.createCell(0).setCellValue(detento.getNome());
//    		sheet.autoSizeColumn(numviolacao);
    		row.createCell(1).setCellValue(detento.getProntuario());
//    		sheet.autoSizeColumn(numviolacao);
    		row.createCell(2).setCellValue(detento.getEstabelecimento());
//    		sheet.autoSizeColumn(numviolacao);
    		row.createCell(3).setCellValue(detento.getPerfil());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(4).setCellValue(violacao.getDatainicio());
//    		sheet.autoSizeColumn(numviolacao);
    					
    		row.createCell(5).setCellValue(violacao.getDataviolacao());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(6).setCellValue(violacao.getDatafinalizacao());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		
    		row.createCell(7).setCellValue(violacao.getDuracao());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(8).setCellValue(violacao.getNotificacoes().get(iddesc).getDescricao());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(9).setCellValue(violacao.getCaracteristica());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(10).setCellValue(detento.getEmail());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(11).setCellValue(violacao.getAlarme());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		
    		row.createCell(12).setCellValue(detento.getProcesso());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(13).setCellValue(detento.getVep());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(14).setCellValue(violacao.getId());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(15).setCellValue(idnoti);
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(16).setCellValue(violacao.getDuracaoalarme());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		
    		row.createCell(17).setCellValue(detento.getStatusenvio());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(18).setCellValue(detento.getErros().toString());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		detento.setMatrix(true);
    		row.createCell(19).setCellValue(detento.isMatrix());
//    		sheet.autoSizeColumn(numviolacao);
    		
    		row.createCell(20).setCellValue(FormatadorData(new Date()));
//    		sheet.autoSizeColumn(numviolacao);
    		
    		num++;
    		numviolacao++;
    	}
    	
    	} else {
    		
    		System.out.println("else matrix " + detento);
    		
    	}
    	
    }
    
    int numreg=0;
//    int s=0;
    for(Registro registro : substitutosSemNotificacao) {
    	
    	Row row = sheet2.createRow(num2+1);
		
		row.createCell(0).setCellValue(registro.getNome());
//		sheet2.autoSizeColumn(numreg);
		row.createCell(1).setCellValue(registro.getProntuario());
//		sheet2.autoSizeColumn(numreg);
		row.createCell(2).setCellValue(registro.getEstabelecimento());
//		sheet2.autoSizeColumn(numreg);
		row.createCell(3).setCellValue(registro.getPerfil());
//		sheet2.autoSizeColumn(numreg);
		
		row.createCell(4).setCellValue(registro.getDatainicio());
//		sheet2.autoSizeColumn(numreg);
					
		row.createCell(5).setCellValue(registro.getDataviolacao());
//		sheet2.autoSizeColumn(numreg);
		
		row.createCell(6).setCellValue(registro.getDatafinalizacao());
		//	sheet2.autoSizeColumn(numreg);
		
		
		row.createCell(7).setCellValue(registro.getDuracao());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(8).setCellValue(registro.getDescricao());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(9).setCellValue(registro.getCaracteristica());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(10).setCellValue(registro.getEmail());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(11).setCellValue(registro.getAlarme());
		//sheet2.autoSizeColumn(numreg);
		
		
		row.createCell(12).setCellValue(registro.getProcesso());
		//	sheet2.autoSizeColumn(numreg);
		
		row.createCell(13).setCellValue(registro.getVep());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(14).setCellValue(registro.getIdviolacao());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(15).setCellValue(registro.getIdnotitificacao());
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(16).setCellValue(registro.getDuracaoalarme());
		//sheet2.autoSizeColumn(numreg);
		
		
		row.createCell(17).setCellValue("SUCESSO");
		//sheet2.autoSizeColumn(numreg);
		
		row.createCell(18).setCellValue("[]");
		//	sheet2.autoSizeColumn(numreg);
		
//		registro.setMatrix(true);
		row.createCell(19).setCellValue("TRUE");
		//	sheet2.autoSizeColumn(numreg);
		
		row.createCell(20).setCellValue(FormatadorData(new Date()));
		//	sheet2.autoSizeColumn(numreg);
		
		num2++;
		numreg++;
    	
    }
    
    FileOutputStream fos = new FileOutputStream(file);
    
    inp.close();
    wb.write(fos); 
	fos.flush();
	fos.close();
    
	} catch (Exception e) {
	e.printStackTrace();
	System.out.println("Erro ao exportar arquivo" + e);
	} finally {

	inp.close();	
	wb.close();
 //  inp.close();
   
	}
    
    
    // Now this Write the output to a file 
//    FileOutputStream fileOut = new FileOutputStream(file);

//    fos.flush();
//    
//    fos.close(); 
//    wb.close();
    
} 





private String FormatadorData(Date data){
	
	String dataformatadastring = null;
	
//	SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy HH:mm");    
//	Date dat = null;
//	
//	
//	try {
//		dat = fmt.parse(data);
//	//	 trace.setText("ok format data");
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		 trace.setText("nok format data");
//		// detentoErro.add(arg0)
//	}
	
	SimpleDateFormat fmt2 = new SimpleDateFormat("dd/MM/yy HH:mm");    
	//Date dat2 = null;
	
	//dat2 = fmt2.format(dat);
	
	dataformatadastring=fmt2.format(data);
	
//	dataformatadastring.replace("\\", "-");
//	dataformatadastring.replace(" ", "-");
//	dataformatadastring.replace(":", "-");
	
	System.out.println("data formatdaaa " + dataformatadastring);
	
	
	return dataformatadastring;
}






}
		
	


