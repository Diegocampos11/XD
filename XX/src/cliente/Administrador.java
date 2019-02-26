package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import servidor.Servidor;

public class Administrador {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm:ss" );
		System.setProperty( "javax.net.ssl.trustStore", "./certificadosPractica/AlmacenClientePractica" );
		System.setProperty( "javax.net.ssl.trustStorePassword" , "clientediego");
		
		SSLSocketFactory fabricaSSLSocket = ( SSLSocketFactory ) SSLSocketFactory.getDefault(); 
		try {
			SSLSocket sock = (SSLSocket) fabricaSSLSocket.createSocket( "127.0.0.1", Servidor.PUERTO );
			Scanner s = new Scanner ( System.in );
			System.out.println("Bienvenido administrador/a!!");//A continuación ingrese los datos de un espectaculo...");
			DataOutputStream salida = new DataOutputStream( sock.getOutputStream() );
			boolean continuar = true;//continuar ingresando espectaculos
			while ( continuar ) {
				System.out.println("Nuevo espectaculo...\nIngrese el nombre:");
				salida.writeUTF( s.nextLine() );
				//
				String fecha = "";
				boolean fechaNoValida = true;
				while ( fechaNoValida ) {
					System.out.println("Ingrese la fecha y hora ('aaaa-mm-dd hh:mm:ss', formato 24 horas sin comillas):");
					fecha = s.nextLine();
					try {
						new Date( ( (java.util.Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse( fecha ) ).getTime() );
						fechaNoValida = false;
					} catch (ParseException e) {
						System.out.println("Error, debe de ingresar el siguiente formato: 'aaaa-mm-dd hh:mm:ss' (24 horas sin comillas)");//año-mes-dia hora:minutos:segundos
					}
				}
				salida.writeUTF( fecha );
				System.out.println("Ingrese el precio de entrada:");
				salida.writeUTF( s.nextLine() );
				System.out.println("Ingrese el número de filas:");
				salida.writeUTF( s.nextLine() );
				System.out.println("Ingrese el número de butacas por fila:");
				salida.writeUTF( s.nextLine() );
				System.out.println("¿Desea continuar?\nSí es así escriba s, de lo contrario escriba n");
				String res = s.nextLine();
				continuar = ( res.toLowerCase().equals("s") ) ? true : false;
				salida.writeUTF( res );
			}
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}