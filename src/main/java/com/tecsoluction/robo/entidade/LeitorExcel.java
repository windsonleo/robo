package com.tecsoluction.robo.entidade;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
public class LeitorExcel implements Serializable  {
	
	 /**
	 * 
	 */
	
	final static Logger logger = LoggerFactory.getLogger(LeitorExcel.class);

	
	private static final long serialVersionUID = 1L;

	private String SAMPLE_XLSX_FILE_PATH = "";
	
	public List<Registro> registros;
	
	private List<Registro> registrosfinal;
	
	private List<Registro> registrosinvalidos;
	
	private List<Registro> registrosfiltradoscodigo;
	
	private List<Registro> registrosfiltradossistema;
	
	private List<Filtro> filtros = new ArrayList<Filtro>();
	
	private GerenciadorFiltro gerenciafiltro ;
	
	//private CopyTask copyTask;
	
	
	
//	final double ii=0.0;
	
	
	public LeitorExcel() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public LeitorExcel(String path){
		
		this.SAMPLE_XLSX_FILE_PATH = path;
		
		
	}
	
	public LeitorExcel(String path,List<Filtro> filtros){
		
		this.SAMPLE_XLSX_FILE_PATH = path;
		this.filtros = filtros;
		
		
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
	
	
	public List<Registro> readRegistersFromExcelFile(String excelFilePath) throws IOException {
		
		registros = new ArrayList<Registro>();
		
	//	registros.clear();
		
//		registrosfinal.clear();
	
		
		//List<Violacao> listViolacoes = new ArrayList<Violacao>();
		
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();


		
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			Registro registro = new Registro();
			//Violacao violacao = new Violacao();
			
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();
				
				DataFormatter dataFormatter = new DataFormatter();
				 String cellValue = dataFormatter.formatCellValue(nextCell);
//				 System.out.println(cellValue);
				
				 switch (columnIndex) {
				
				case 0:
					registro.setNome(cellValue.toUpperCase().trim());
				 //System.out.print(registro.getNome());
					break;
				
				
//				case 1:
//					registro.setIdmonitorado(cellValue);
//				
////					 System.out.print(registro.getIdmonitorado());
//					break;
					
				case 1:
					registro.setProntuario(cellValue.toUpperCase().trim());
//					 System.out.print(registro.getProntuario());
				
					break;
				case 2:
					registro.setEstabelecimento(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getEstabelecimento());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
				case 3:
					registro.setPerfil(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getPerfilatual());
					//detento.setPrice((double) getCellValue(nextCell));
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
					registro.setDatainicio(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getDatainicio());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
				case 5:
					registro.setDataviolacao(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getDataviolacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
				case 6:
					registro.setDatafinalizacao(cellValue.toUpperCase().trim());
					
					//detento.setPrice((double) getCellValue(nextCell));
//					 System.out.print(registro.getDatafinalizacao());

					break;
					
				case 7:
					registro.setDuracao(cellValue.toUpperCase().trim());
//					 System.out.print(registro.getDuracao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
//				case 8:
//					registro.setNotificacao(cellValue.toUpperCase().trim());
//					
////					 System.out.print(registro.getNotificacao());
//					//detento.setPrice((double) getCellValue(nextCell));
//					break;
					
				case 8:
					registro.setDescricao(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getNotificacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
				case 9:
					registro.setCaracteristica(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getNotificacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
				case 10:
					registro.setEmail(cellValue);
					
//					 System.out.print(registro.getNotificacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
					
				case 11:
					registro.setAlarme(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getNotificacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					
					
				case 12:
					registro.setProcesso(cellValue.toUpperCase().trim());
					
//					 System.out.print(registro.getNotificacao());
					//detento.setPrice((double) getCellValue(nextCell));
					break;
					

				
				 
			case 13:
				registro.setVep(cellValue.toUpperCase().trim());
				
//				 System.out.print(registro.getNotificacao());
				//detento.setPrice((double) getCellValue(nextCell));
				break;
				
				
			case 14:
				registro.setIdviolacao(cellValue.toUpperCase().trim());
				
//				 System.out.print(registro.getNotificacao());
				//detento.setPrice((double) getCellValue(nextCell));
				break;
				
				
				
			case 15:
				registro.setIdnotitificacao(cellValue.toUpperCase().trim());
				
//				 System.out.print(registro.getNotificacao());
				//detento.setPrice((double) getCellValue(nextCell));
				break;
				

			
				 
			case 16:
				registro.setDuracaoalarme(cellValue.toUpperCase().trim());
				
//				 System.out.print(registro.getNotificacao());
				//detento.setPrice((double) getCellValue(nextCell));
				break;
				

			}
				
				
				
			}
			
			
			
			
			//detento.addViolacao(violacao);
			registros.add(registro);
			//listViolacoes.add(violacao);
		}
		
		
		
//		gerenciafiltro = new GerenciadorFiltro(filtros, registros);
//		
//		registrosfinal = gerenciafiltro.getRegistros();
	//	registrosfiltradossistema = gerenciafiltro.getExcluidos();
		
		
	//	System.out.println("leitor excel : " + registros.toString());
	//	System.out.println("leitor excel : " + listViolacoes.toString());
		
	//	setViolacoess(listViolacoes);

		workbook.close();
		inputStream.close();
		
//		CarregarHandler();

		return registros;
	}
	 
	
	public List<Registro> Filtrar() {

		
		registrosfinal	= new ArrayList<Registro>();
		
		gerenciafiltro = new GerenciadorFiltro(filtros, getRegistros());
		
		registrosfinal = gerenciafiltro.getRegistros();
		

		return registrosfinal;
	} 
	
	
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

     
		
	} 
	


