/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorredes;

import Data.connection;
import Domain.Usuarios;
import GUI.Ventana;
import GUI.VentanaPrincipal;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.ListAdapter;
import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
//import org.apache.commons.io.FileUtils;

/**
 *
 * @author Pablo
 */
public class ServidorRedes {

    static ArrayList<Usuarios> lista = new ArrayList<>(100);

    /**
     * @param args the command line arguments
     */
    public static void iniciarServidor() throws IOException {
        ServerSocket servidor = new ServerSocket(4400);

        System.out.println("Esperando recepcion de archivos...");
        boolean badera = true;
        while (badera) {

            try {

                Socket cliente = servidor.accept();

                // Creamos flujo de entrada para leer los datos que envia el cliente 
                DataInputStream dis = new DataInputStream(cliente.getInputStream());
                String dato = dis.readUTF();

                if (dato.equals("guardar")) {
                    // Obtenemos el nombre del archivo
                    String nombreArchivo = dis.readUTF().toString();

                    // Obtenemos el tamaño del archivo
                    int tam = dis.readInt();
                    String nombreUsuario = dis.readUTF();

                    System.out.println("Recibiendo archivo " + nombreArchivo);

                    File directorio = new File("directorios//" + nombreUsuario + "//");
                    FileOutputStream fos = new FileOutputStream(directorio + "//" + nombreArchivo);
                    BufferedOutputStream out = new BufferedOutputStream(fos);
                    BufferedInputStream in = new BufferedInputStream(cliente.getInputStream());
                   
                    byte[] buffer = new byte[tam];

                    for (int i = 0; i < buffer.length; i++) {
                        
                        buffer[i] = (byte) in.read();
                    }

                    // Escribimos el archivo 
                    out.write(buffer);

                    // Cerramos flujos
                    out.flush();
                    in.close();
                    out.close();
                    //cliente.close();

                    System.out.println("Archivo Recibido " + nombreArchivo);

                } else if (dato.equals("recuperarNombres")) {
                    /*encio nombre del usuario para aceder a sus datos*/
                    String nombre = dis.readUTF();
                    File carpeta = new File("directorios//" + nombre + "//");

                    String[] listado = carpeta.list();

                    DataOutputStream Output = new DataOutputStream(cliente.getOutputStream());

                    Output.writeInt(listado.length);

                    for (int i = 0; i < listado.length; i++) {
                        Output.writeUTF(listado[i]);
                    }

                } else if (dato.equals("cargar")) {

                    String nombre = dis.readUTF();
                    String nombreDocumento = dis.readUTF();
                    DataOutputStream Output = new DataOutputStream(cliente.getOutputStream());
                    File archivo = new File("directorios//" + nombre + "//" + nombreDocumento);

                    // Enviamos el tamaño del archivo
                    int tamanoArchivo = (int) archivo.length();
                    Output.writeInt(tamanoArchivo);
                    /////nombre del archivo
                    System.out.println(tamanoArchivo);
                    Output.writeUTF(nombreDocumento);
                    // System.out.println(tamanoArchivo);

                    FileInputStream fis = new FileInputStream("directorios//" + nombre + "//" + nombreDocumento);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    BufferedOutputStream bos = new BufferedOutputStream(cliente.getOutputStream());

                    byte[] buffer = new byte[tamanoArchivo];

                    // Leemos el archivo y lo introducimos en el array de bytes 
                    bis.read(buffer);

                    for (int j = 0; j < buffer.length; j++) {
                        bos.write(buffer[j]);
                    }

                    System.out.println("Archivo Enviado: " + archivo.getName());
                    cliente.close();
                } else if (dato.equals("Actualizar")) {

                    // Obtenemos el nombre del archivo
                    String nombreArchivo = dis.readUTF().toString();

                    // Obtenemos el tamaño del archivo
                    int tam = dis.readInt();
                    String nombreUsuario = dis.readUTF();

                    System.out.println("Recibiendo archivo " + nombreArchivo);

                    File directorio = new File("directorios//" + nombreUsuario + "//");
                                // File dir = new File("directorios//" + nombreUsuario + "//");
                  
                    // FileUtils.deleteDirectory(new File("C:/test/ABC/"));
                    // directorio.delete();
                    //directorio = new File("directorios//" + nombreUsuario + "//");
                    FileOutputStream fos = new FileOutputStream(directorio + "//" + nombreArchivo);
                    BufferedOutputStream out = new BufferedOutputStream(fos);
                    BufferedInputStream in = new BufferedInputStream(cliente.getInputStream());

                    byte[] buffer = new byte[tam];

                    for (int i = 0; i < buffer.length; i++) {
                        buffer[i] = (byte) in.read();
                    }

                    // Escribimos el archivo 
                    out.write(buffer);

                    // Cerramos flujos
                    out.flush();
                    in.close();
                    out.close();
                    //cliente.close();

                    System.out.println("Archivo Recibido " + nombreArchivo);

                } else if (dato.equals("borrar")) {
                  String nombreUsuario = dis.readUTF();
                    int tamCliente = dis.readInt();
                  String vect[] = obtenerListado(nombreUsuario);
                  if(tamCliente!=vect.length){  
                  for (int i = 0; i < vect.length; i++) {
              File dir= new File("directorios//" + nombreUsuario + "//" + vect[i]);
                        //System.out.println("directorios//" + nombreUsuario + "//" + vect[i]);
//JOptionPane.showMessageDialog(null, "directorios//" + nombreUsuario + "//" + vect[i]);
                        dir.delete();

                    }}
                }
            } catch (Exception e) {
                System.out.println("Recibir: " + e.toString());
            }

        }
    }

    public static String[] obtenerListado(String nombreUsuario) {

        File carpeta = new File("directorios//" + nombreUsuario + "//");
        String[] listado = carpeta.list();
        if (listado == null || listado.length == 0) {
            System.out.println("No hay elementos dentro de la carpeta actual");
            return listado;
        } else {
            for (int i = 0; i < listado.length; i++) {
                System.out.println(listado[i]);
            }
            return listado;
        }
    }
// public static String[] borrarListado(String nombreUsuario) {
//
//        File carpeta = new File("directorios//" + nombreUsuario + "//");
//        String[] listado = carpeta.list();
//        if (listado == null || listado.length == 0) {
//            System.out.println("No hay elementos dentro de la carpeta actual");
//            return listado;
//        } else {
//            for (int i = 0; i < listado.length; i++) {
//                System.out.println(listado[i]);
//            }
//            return listado;
//        }
//    }

    public  String descifra(byte[] cifrado) throws Exception {
	final Cipher aes = obtieneCipher(false);
	final byte[] bytes = aes.doFinal(cifrado);
	final String sinCifrar = new String(bytes, "UTF-8");
	return sinCifrar;
}
    private Cipher obtieneCipher(boolean paraCifrar) throws Exception {
	final String frase = "FraseLargaConDiferentesLetrasNumerosYCaracteresEspeciales_áÁéÉíÍóÓúÚüÜñÑ1234567890!#%$&()=%_NO_USAR_ESTA_FRASE!_";
	final MessageDigest digest = MessageDigest.getInstance("SHA");
	digest.update(frase.getBytes("UTF-8"));
	final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

	final Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	if (paraCifrar) {
		aes.init(Cipher.ENCRYPT_MODE, key);
	} else {
		aes.init(Cipher.DECRYPT_MODE, key);
	}

	return aes;
}
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        //Ventana ventana = new Ventana();
        VentanaPrincipal v = new VentanaPrincipal();
        v.setVisible(true);
        iniciarServidor();
    }

    public void crearDirectorio(String nombre) {

        File directorio = new File("directorios//" + nombre);
        System.out.println("C:\\Users\\Pablo\\Desktop\\ServidorRedes" + nombre);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }

    }

}
