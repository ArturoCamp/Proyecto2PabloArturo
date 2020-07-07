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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Pablo
 */
public class VentanaCargar extends JFrame implements ActionListener{
     JComboBox combo1;
 JLabel lblMsj;
    JButton btnAceptar,btnVolver;
    JTextArea AreaTexto;
String ip;
String nUsuario;
	/**
	 * @param args
	 */
	 public VentanaCargar(String ip,String nusuario) throws IOException {
             this.ip=ip;
             this.nUsuario=nusuario;
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setSize(800, 600);
  getContentPane().setLayout(null);
  setLocationRelativeTo(null);
  
  iniciarComponentes();
 }
	public void iniciarComponentes() throws IOException{
        
lblMsj=new JLabel();
  lblMsj.setText("Selección: ");
  lblMsj.setBounds(398, 10, 180, 20);
  combo1=new JComboBox();
  combo1.setBounds(10, 10, 148, 20);
 btnAceptar=new JButton("Aceptar");
 btnAceptar.setBounds(168, 10, 100, 20);

 btnVolver=new JButton("volver");
  btnVolver.setBounds(288, 10, 100, 20);
 //FORMA 1
 //String arregloItems[]=new String[]{"Seleccione","Opcion1","Opcion2"};
 //combo1.setModel(new DefaultComboBoxModel<>(arregloItems));
  
 //FORMA 2 
 String nombre[]=recuperarNombresArchivos();
   for (int k = 0; k < nombre.length; k++) {
   combo1.addItem(nombre[k]);
   
   }

  
  add(combo1);
  add(lblMsj);
  add(btnAceptar);
  add(btnVolver);
  //add(AreaTexto);
  btnAceptar.addActionListener(this);
  //add(AreaTexto);
  btnVolver.addActionListener(this);
        }
 


    @Override
    public void actionPerformed(ActionEvent e) {
     if (e.getSource()==btnAceptar) {
      
         try {
             lblMsj.setText(combo1.getSelectedIndex()+" - "+combo1.getSelectedItem());
             CargarArchivo((String) combo1.getSelectedItem());
            // Archivo archivo =new Archivo();
          // AreaTexto.setText((String) combo1.getSelectedItem());
            
         } catch (IOException ex) {
             Logger.getLogger(VentanaCargar.class.getName()).log(Level.SEVERE, null, ex);
         }
    }else if(e.getSource()==btnVolver){
          VentanaEnviarArchivos ventana =new VentanaEnviarArchivos(ip, nUsuario);
            ventana.setVisible(true);
            this.dispose();
    }
        
        
        }



public String[] recuperarNombresArchivos() throws UnknownHostException, IOException{
 
 InetAddress direccion = InetAddress.getByName(ip);
        Socket socket = new Socket(direccion, 4400);

        DataOutputStream Output = new DataOutputStream(socket.getOutputStream());
        Output.writeUTF("recuperarNombres");
        Output.writeUTF(nUsuario);
DataInputStream dis = new DataInputStream(socket.getInputStream());

        System.out.println("Acepto servidor");
        int tamaCarpeta = dis.readInt();
           String []vector = new String[tamaCarpeta];
             for (int k = 0; k < tamaCarpeta; k++) {
             vector[k]=dis.readUTF();
             
             }
        return vector;
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
               System.out.println("cliente"+tam);
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
	
	
		
	
	

