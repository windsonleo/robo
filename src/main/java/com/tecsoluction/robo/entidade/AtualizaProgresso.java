//package com.tecsoluction.robo.entidade;
//
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class AtualizaProgresso extends AsyncTask<Object, Object, Object> {
//   
//	
//	private MainController controller;
//  
//    
//    
//    public AtualizaProgresso(MainController controller) {
//        this.controller = controller;
//    }
//    
//    
//    
//    @Override
//	public
//    void onPreExecute() {
//    
//        //This method runs on UI Thread before background task has started
////        this.controller.AtualizaLabel("Starting Download");
//    	
//    	System.out.println("preexecute");
//    }
//    
//
//   
//   
//    @Override
//    public void progressCallback(Object... params) {
//        //This method update your UI Thread during the execution of background thread
//        double progress = (double)params[0];
//      //  this.controller.AtualizaProgress(progress);
//        System.out.println("progressCallback");
//    }
//
//
//
//	@Override
//	public Object doInBackground(Object... params) {
//		// TODO Auto-generated method stub
//		
//		 //This method runs on background thread
//	    
//	   // boolean downloading = true;
//		
//		int i = 0;
//	    
//	        while (i < 5){
//	        
//	            /*++
//	            * Your download code
//	            */
//	        	i=i++;
//	        	
//	        	System.out.println("publishProgress chamada");
//	            double progress = 65.5 ;//Your progress calculation 
//	            publishProgress(progress);
//	        }
//	        //This method runs on UI Thread after background task has done
//	      
//		return null;
//	}
//
//
//
//	@Override
//	public void onPostExecute(Object params) {
//		// TODO Auto-generated method stub
//		 System.out.println("publishProgress chamada");
//		 // this.controller.AtualizaLabel("Download is Done");
//	}
//	
//	
//	@Override
//	public void publishProgress(Object... progressParams) {
//		// TODO Auto-generated method stub
//		super.publishProgress(progressParams);
//		 System.out.println("publishProgress");
//	}
//
//
//
//
//
//
//}
//
