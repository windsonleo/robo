package com.tecsoluction.robo.util;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PingIp {
	
private String[] ip;



public PingIp() {
	// TODO Auto-generated constructor stub
}

public PingIp(String[] x) {

this.ip = x;



}



public void testeIp(){
	
	    if (ip.length == 1) {
	      InetAddress address = null;
	      try {
	        address = InetAddress.getByName(ip[0]);
	      } catch (UnknownHostException e) {
	        System.out.println("Cannot lookup host "+ip[0]);
	        return;
	      }
	      try {
	        if (address.isReachable(5000)) {
	          long nanos = 0;
	          long millis = 0;
	          long iterations = 0;
	          while (true) {
	            iterations++;
	            try {
	              nanos = System.nanoTime();
	              address.isReachable(500); // this invocation is the offender
	              nanos = System.nanoTime()-nanos;
	            } catch (IOException e) {
	              System.out.println("Failed to reach host");
	            }
	            millis = Math.round(nanos/Math.pow(10,6));
	            System.out.println("Resposta do IP: "+address.getHostAddress()+" com de tempo="+millis+" ms");
	            try {
	              Thread.sleep(Math.max(0, 1000-millis));
	            } catch (InterruptedException e) {
	              break;
	            }
	          }
	          System.out.println("Iterations: "+iterations);
	        } else {
	          System.out.println("Host "+address.getHostName()+" is not reachable even once.");
	        }
	      } catch (IOException e) {
	        System.out.println("Network error.");
	      }
	    } else {
	      System.out.println("Usage: java isReachableTest <host>");
	    }
	  }

}
