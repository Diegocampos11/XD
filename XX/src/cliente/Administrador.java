package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
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
			//cliente conectado
			//System.out.println("Me he conectado");			
			/*SSLSession sesionSSL = sock.getSession();//coge la hora cuando se se obtiene la sesion SSL
			System.out.println( "Host "+ sesionSSL.getPeerHost() );
			System.out.println( "Cifrado "+ sesionSSL.getCipherSuite() );
			System.out.println( "Protocolo "+ sesionSSL.getProtocol() );
			System.out.println( "Id sesion "+ new BigInteger( sesionSSL.getId() ) );
			System.out.println( "hora Sesion "+ sdf.format( new Date( sesionSSL.getCreationTime() ) ) );*/
			/*DataInputStream entrada = new DataInputStream( sock.getInputStream() );
			String saludo = entrada.readUTF();
			System.out.println( saludo );
			entrada.close();*/
			Scanner s = new Scanner ( System.in );
			System.out.println("Bienvenido administrador/a!!");//A continuación ingrese los datos de un espectaculo...");
			DataOutputStream salida = new DataOutputStream( sock.getOutputStream() );
			boolean continuar = true;//continuar ingresando espectaculos
			while ( continuar ) {
				System.out.println("Nuevo espectaculo...\nIngrese el nombre:");
				salida.writeUTF( s.nextLine() );
				System.out.println("Ingrese la fecha:");
				salida.writeUTF( s.nextLine() );
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
