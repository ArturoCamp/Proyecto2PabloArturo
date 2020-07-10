/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

//import com.sun.xml.internal.ws.api.message.saaj.SAAJFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Pablo
 */
public class VentanaCargar extends JFrame implements ActionListener {
    
    JComboBox combo1;
    //JLabel lblMsj;
    JButton btnAceptar, btnVolver, btnTodos;
    JTextArea AreaTexto;
    String ip;
    String nUsuario;
    int posicion = 0;

    /**
     * @param args
     */
    public VentanaCargar(String ip, String nusuario) throws IOException {
        this.ip = ip;
        this.nUsuario = nusuario;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        
        iniciarComponentes();
    }
    
    public void iniciarComponentes() throws IOException {
        
        //lblMsj = new JLabel();
//        lblMsj.setText("Selección: ");
//        lblMsj.setBounds(398, 10, 180, 20);
        combo1 = new JComboBox();
        combo1.setBounds(10, 10, 148, 20);
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(168, 10, 100, 20);
        
        btnVolver = new JButton("volver");
        btnVolver.setBounds(288, 10, 100, 20);
        
        btnTodos = new JButton("Cargar Todo");
        btnTodos.setBounds(398, 10, 200, 20);
        //FORMA 1
        //String arregloItems[]=new String[]{"Seleccione","Opcion1","Opcion2"};
        //combo1.setModel(new DefaultComboBoxModel<>(arregloItems));

        //FORMA 2 
        String nombre[] = recuperarNombresArchivos();
        for (int k = 0; k < nombre.length; k++) {
            combo1.addItem(nombre[k]);
            
        }
        
        add(combo1);
       // add(lblMsj);
        add(btnAceptar);
        add(btnVolver);
        add(btnTodos);
        btnTodos.addActionListener(this);
        //add(AreaTexto);
        btnAceptar.addActionListener(this);
        //add(AreaTexto);
        btnVolver.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAceptar) {
            
            try {
//                lblMsj.setText(combo1.getSelectedIndex() + " - " + combo1.getSelectedItem());
                CargarArchivo((String) combo1.getSelectedItem());
                // Archivo archivo =new Archivo();
                // AreaTexto.setText((String) combo1.getSelectedItem());
JOptionPane.showMessageDialog(null,"se ha cargado el archivo");
            } catch (IOException ex) {
                Logger.getLogger(VentanaCargar.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource() == btnVolver) {
            VentanaEnviarArchivos ventana = new VentanaEnviarArchivos(ip, nUsuario);
            ventana.setVisible(true);
            this.dispose();
        } else if (e.getSource() == btnTodos) {
            try {
                String nombres[] = recuperarNombresArchivos();
                int tam = nombres.length;

                //
                for (int k = 0; k < nombres.length; k++) {
                    if (k == 0) {
                        BorrarContenidoCarpeta();
                    }
                    RecuperarTodosArchivos(nUsuario, nombres);
                }
                JOptionPane.showMessageDialog(null,"se ha recuperado todo los archivos");
                
            } catch (IOException ex) {
                Logger.getLogger(VentanaCargar.class.getName()).log(Level.SEVERE, null, ex);
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

    public String[] recuperarNombresArchivos() throws UnknownHostException, IOException {
        
        InetAddress direccion = InetAddress.getByName(ip);
        Socket socket = new Socket(direccion, 4400);
        
        DataOutputStream Output = new DataOutputStream(socket.getOutputStream());
        Output.writeUTF("recuperarNombres");
        Output.writeUTF(nUsuario);
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("Acepto servidor");
        int tamaCarpeta = dis.readInt();
        String[] vector = new String[tamaCarpeta];
        for (int k = 0; k < tamaCarpeta; k++) {
            vector[k] = dis.readUTF();
            
        }
        return vector;
    }
    
    public void BorrarContenidoCarpeta() throws IOException {
        String vect[] = obtenerListado(nUsuario);
        for (int i = 0; i < vect.length; i++) {
            File dir = new File("directorios//" + nUsuario + "//" + vect[i]);
            //System.out.println("directorios//" + nombreUsuario + "//" + vect[i]);
//JOptionPane.showMessageDialog(null, "directorios//" + nombreUsuario + "//" + vect[i]);
            dir.delete();
            
        }

        // String nombreUsuario = dis.readUTF();
        //String vect[] = obtenerListado(nombreUsuario);
    }
    
    public void RecuperarTodosArchivos(String nombreDocumento, String[] nombres) throws IOException {

        //ServerSocket servidor = new ServerSocket(4400);
        //while (true) {
        InetAddress direccion = InetAddress.getByName(ip);
        Socket socket = new Socket(direccion, 4400);
        
        DataOutputStream Output = new DataOutputStream(socket.getOutputStream());
        Output.writeUTF("cargar");
        Output.writeUTF(nUsuario);
        Output.writeUTF(nombres[posicion]);
        posicion += 1;
        System.out.println("Esperando recepcion de archivos...");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("Acepto servidor");

/////////////para leer los archivos
///////////tamano0 del archivo
        int tam = dis.readInt();
        //  String nombreUsuario = dis.readUTF();
        System.out.println("cliente" + tam);
        String nombreArchivo = dis.readUTF();
        System.out.println("Recibiendo archivo " + nombreArchivo);
        
        File directorio = new File("directorios//" + nUsuario + "//");
        FileOutputStream fos = new FileOutputStream(directorio + "//" + nombreArchivo);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        
        byte[] buffer = new byte[tam];
        
        for (int j = 0; j < buffer.length; j++) {
            buffer[j] = (byte) in.read();
        }
        out.write(buffer);

        //ut.write(buffer);
        // Cerramos flujos
        socket.close();
        
    }
    
    public void CargarArchivo(String nombreDocumento) throws IOException {
        //ServerSocket servidor = new ServerSocket(4400);
        //while (true) {
        InetAddress direccion = InetAddress.getByName(ip);
        Socket socket = new Socket(direccion, 4400);
        
        DataOutputStream Output = new DataOutputStream(socket.getOutputStream());
        Output.writeUTF("cargar");
        Output.writeUTF(nUsuario);
        Output.writeUTF(nombreDocumento);
        
        System.out.println("Esperando recepcion de archivos...");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        System.out.println("Acepto servidor");

/////////////para leer los archivos
///////////tamano0 del archivo
        int tam = dis.readInt();
        //  String nombreUsuario = dis.readUTF();
        System.out.println("cliente" + tam);
        String nombreArchivo = dis.readUTF();
        System.out.println("Recibiendo archivo " + nombreArchivo);
        
        File directorio = new File("directorios//" + nUsuario + "//");
        FileOutputStream fos = new FileOutputStream(directorio + "//" + nombreArchivo);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        
        byte[] buffer = new byte[tam];
        
        for (int j = 0; j < buffer.length; j++) {
            buffer[j] = (byte) in.read();
        }
        out.write(buffer);

        //ut.write(buffer);
        // Cerramos flujos
        socket.close();
    }
    
}
