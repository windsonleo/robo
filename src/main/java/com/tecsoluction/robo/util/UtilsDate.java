package com.tecsoluction.robo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.control.DatePicker;

public class UtilsDate {
	
	
	
	
	
	
	 public static Date toDate(DatePicker datePicker) {
	        if(datePicker.getValue() == null){
	            return null;
	        }
	        LocalDate ld = datePicker.getValue();
	        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
	        Date date = Date.from(instant);

	        return date;
	    }

	    /**
	     * Converte Date para LocalDate
	     *
	     * @param d
	     * @return LocalDate
	     */
	    public static LocalDate toLocalDate(Date d) {
	        Instant instant = Instant.ofEpochMilli(d.getTime());
	        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	        return localDate;
	    }
	    
	    
	    public static Date toDate(Date d) {
	    	
	    	SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");  
	    	Date dat = null;
	    	try {
				 dat = fmt.parse(d.toLocaleString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	


	        return dat;
	    }
	    
	    
	    

}
