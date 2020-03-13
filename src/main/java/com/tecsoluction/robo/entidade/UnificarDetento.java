package com.tecsoluction.robo.entidade;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UnificarDetento {
	
	
	private ArrayList<Registro> registrosNotificar = new ArrayList<Registro>();
	
	private List<Registro> registros = new ArrayList<Registro>();

	private ArrayList<Detento> detentos = new ArrayList<Detento>();

	private ArrayList<Detento> detentoFinal= new ArrayList<Detento>();

	private ArrayList<Detento> detentosrepetidos= new ArrayList<Detento>();

	private ArrayList<Detento> detentosremovidos= new ArrayList<Detento>();


	public UnificarDetento(List<Registro> reg) {
		// TODO Auto-generated constructor stub

	
	this.registros =reg;
	}
	

	
	
	public UnificarDetento() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public void filter() {

		   
		 
		   registrosNotificar = new ArrayList<Registro>();
		   		   
		   String idnotificacao = new String();
		   
			for (int i = 0; i < getRegistros().size(); i++) {
				
			Registro registro = getRegistros().get(i);
			
			idnotificacao = registro.getIdnotitificacao();
			
			for (int j = 0; j < getRegistros().size(); j++) {
				
				Registro regaux = getRegistros().get(j);
				
				if(regaux.getDescricao().toUpperCase().contains(idnotificacao.toUpperCase())
						)
				
				{
				
				registro.setIsremove(true);
				
			//	registroFiltrados.add(regaux);
				
				
				
				
				
				}else {
					
					
					
					
					
				}
				
			}
			
			if(registro.getDescricao() == null || registro.getDescricao()=="" ||
					registro.getDescricao().contains("[SEM VIOLAÇÃO")||registro.getDescricao().contains("[EVENTO RESTAURADO")||registro.getDescricao().contains("[sem notificação")||registro.getDescricao().contains("[evento restaurado") ||registro.isIsremove()){					
				//getRegistros().remove(registro);
				
				//registroFiltrados.add(registro);
				
//				registroFiltrados.add(registro);
				
			}
			
//			if(registro.getNotificacao().contains("[SEM VIOLAÇÃO")&&(registro.getNotificacao().contains("cancela"))){
//				
//				
//				
//				
//			}
			
			else {
				
				registrosNotificar.add(registro);
				
			}
			
				
			}
			
			
		//	registroFiltrados.addAll(registrosNotificar);
			
			detentos.clear();
			detentos.addAll(RegistroToDetento(registrosNotificar));
			setRegistros(registrosNotificar);
			
			
			System.out.println("Inicio da Remoção");
//			trace.setText("Inicio da Remoção");
			
			System.out.println("detentos" + detentos.size());
			
			removeduplicado();
			
			
//			 AtualizarQuadroRegistros();
//			 AtualizarQuadro();
	    
	    
	    } 
	 
	 
	 
	 
	public void removeduplicado() {
		 
//			progressind.setVisible(true);
//			progressindligar.setVisible(true);
			
			List<Detento> detfinal = RemoverDuplicidade();

			
			detentos.clear();
			detentos.addAll(detfinal);
			
			System.out.println("fim da Remoção");
//			trace.setText("fim da Remoção");
			
			System.out.println("detentos fim" + detentos.size());
			
//			trace.setText("detentos fim" + detentos.size());
			

			
//			 AtualizarQuadroRegistros();
//			 AtualizarQuadro();
			 

		 }
	 
	 
	 
	 
	 
	 
	public List<Detento> RemoverDuplicidade(){
			

//			progressind.setVisible(true);
//			 progressindligar.setVisible(true);
			
			detentoFinal = new ArrayList<Detento>();
			detentosremovidos = new ArrayList<Detento>();
			detentosrepetidos= new ArrayList<Detento>();
			
			int count = 0;
			int countv = 0;
			
			
			//percorrer os detentos e comparar se existe
			for (int j = 0; j < detentos.size(); j++) {
				
				Detento detentoCompara = detentos.get(j);

				List<Violacao> violacoesCompara = detentoCompara.getViolacoes();
				
				if(ExisteDetento(detentoCompara)){
					
					
					violacoesCompara = RecuperarDetentoRepetido(detentoCompara);
					List<Violacao> vios = removeViolacoesInvalidas(violacoesCompara);
					
					count =count+1;
					
					if(vios.isEmpty() ){
						
						//detentosremovidos.add(detentoCompara);
						detentoFinal.remove(detentoCompara);
						
						
					}else {
						
						
						detentoCompara.setViolacoes(vios);
					
						
					}
					
//					detentosrepetidos.add(detentoCompara);
					
					
					System.out.println("entrou duplicidade: " + count);

					
				}else {
					

					detentoCompara.setViolacoes(removeViolacoesInvalidas(violacoesCompara));
					
				}
				
				
				
				
				System.out.println("fim detento" + j);
		
				
				if(detentoCompara.getViolacoes().isEmpty()){
					
					System.out.println("dettento viola vazia" + countv);
					
					//detentosremovidos.add(detentoCompara);
					
//					detentoFinal.remove(detentoCompara);
					
					countv=countv+1;
					
				}else {
					
					detentoFinal.add(detentoCompara);
					
				}
				
			
				
			}
			
          
//          if(jcbvalidarbefore.isSelected()){
//          	
//          	
//          	ValidaDetentoBefore();
//         	 
//         	 
//         	 
//          }
			
			
			
			
			
			

		System.out.println("qtd detentos : " + detentos.size());
		System.out.println("qtd reg : " + registros.size());
		System.out.println("qtd final det fimal : " + detentoFinal.size());
		
		
		
			
			
			
			return detentoFinal;
	 
	 }
	 
	 
	public List<Violacao> removeViolacoesInvalidas(List<Violacao> violas) {
			
			List<Violacao> vios = violas;
			
			String procurarpor1 = new String("EVENTO RESTAURADO");
			
			String procurarpor2 = new String("SEM VIOLAÇÃO");
			
			String procurarpor3 = new String("SEM VIOLACAO");
			
			String procurarpor4 = new String("SEM NOTIFICAÇÃO");
			
			String procurarpor5 = new String("SEM NOTIFICACAO");
			String procurarpor6 = new String("JÁ NOTIFICADO");
			String procurarpor7 = new String("JA NOTIFICADO");
			
			String procurarpor8 = new String("CANCELADO");
			
			String procurarpor9 = new String("CANCELADA");
			
			String procurarpor10 = new String("SEM RELATÓRIO");
			
			String procurarpor11 = new String("SEM RELATORIO");
			
			String procurarpor12 = new String("CANCELA");
			
			String procurarpor13 = new String("cancela");
			
			
			
			
			for (int i = 0; i < violas.size(); i++) {
				
				Violacao v = violas.get(i);
				
				
				
				for (int j = 0; j < v.getNotificacoes().size(); j++) {
					
					com.tecsoluction.robo.entidade.Notificacao noti = v.getNotificacoes().get(j);
					
					if (noti.getDescricao().toLowerCase().contains(procurarpor1.toLowerCase()) || noti.getDescricao().toLowerCase().contains(procurarpor2.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor3.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor4.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor5.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor6.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor7.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor8.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor9.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor10.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor11.toLowerCase())
					||noti.getDescricao().toLowerCase().contains(procurarpor12.toLowerCase())||noti.getDescricao().toLowerCase().contains(procurarpor13.toLowerCase()))
				
				{
				
					if(v.getNotificacoes().size() > 0){
						
						
						v.removeNotificacao(noti);
						

						
					}else {
						
						
						
						vios.remove(v);

						
					}
					
					
					
					

					

				}
					
				}
				

					
					  
					
				}
			
			return vios;
				
			}
	 
	 
	public List<Detento> RegistroToDetento(List<Registro> registros2) {
			

			List<Detento> detentos = new ArrayList<Detento>();	
			
			
			for (int i = 0; i < registros2.size(); i++) {
				
				Registro reg =	registros2.get(i);		
				Detento det = new Detento(reg);
				Violacao viola = new Violacao(reg);
				
				com.tecsoluction.robo.entidade.Notificacao notificacao = new com.tecsoluction.robo.entidade.Notificacao(reg);
				
				viola.setDetento(det);
				notificacao.setViolacao(viola);
				
				viola.addNotificao(notificacao);
				det.addViolacao(viola);

				detentos.add(det);
				
			}
					 
			return detentos;

}
	 
	 
	 
	public boolean ExisteDetento(Detento det) {

			boolean existe = false;
			//trace.setText("Verificando existencia de Detento");
				
			
			
			for (int i = 0; i < getDetentoFinal().size(); i++) {
				
			Detento detento = getDetentoFinal().get(i);
			
			if(detento.getProntuario().equals(det.getProntuario())){
				
				
				//detentosremovidos.add(det);
				existe = true;
				return existe;
				
			}
			
		
				
				
			}
			
			 
			
			 
			return existe;
		}
	 
	 
	public List<Violacao> RecuperarDetentoRepetido(Detento detento){
			
			List<Violacao> repetidos = new ArrayList<Violacao>();
			
			for (int i = 0; i < getDetentoFinal().size(); i++) {
				
			Detento detentoaux = getDetentoFinal().get(i);
			
			if(detentoaux.getProntuario().equals(detento.getProntuario())){
				
				
				detentoaux.getViolacoes().addAll(detento.getViolacoes());
				repetidos = detentoaux.getViolacoes();
				
				
				getDetentoFinal().remove(detentoaux);
				//esse detentosremovidos.add(detentoaux);
				
			//	detentosrepetidos.add(detentoaux);
				
				
				
			}else {
				
				
				
			}
			

				
				
			}
			
			
			return repetidos;
		}

}
