/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class VentanaEnviarArchivos extends javax.swing.JFrame {

    String ip = "";
    String nUsuario;

    /**
     * Creates new form VentanaEnviarArchivos
     */
    public VentanaEnviarArchivos() {
        initComponents();
        // enviarArchivo();
    }
      public VentanaEnviarArchivos(String ip,String nUsuario) {
        initComponents();
        this.ip=ip;
        this.nUsuario=nUsuario;
        // enviarArchivo();
    }

    public VentanaEnviarArchivos(String nobreUsuario) {
        nUsuario = nobreUsuario;
        initComponents();

    }
//    private String nombreArchivo = "C:\\Users\\Pablo\\Desktop\\minion.docx";
//String nombreArchivo="Semana 1.pdf";

    public void enviarArchivo(String nombreArchivo) {
        System.out.print(nUsuario);
        try {
//"192.168.1.4"
            // Creamos la direccion IP de la maquina que recibira el archivo
            InetAddress direccion = InetAddress.getByName(ip);

            // Creamos el Socket con la direccion y elpuerto de comunicacion
            Socket socket = new Socket(direccion, 4400);
            socket.setSoTimeout(8000);
            socket.setKeepAlive(true);

            // Creamos el archivo que vamos a enviar
            System.out.println(nombreArchivo);
            File archivo = new File(nombreArchivo);

            int tamanoArchivo = (int) archivo.length();

            DataOutputStream Output = new DataOutputStream(socket.getOutputStream());

            // System.out.println("Enviando Archivo551515: " + archivo);
            Output.writeUTF("guardar");
            // Enviamos el nombre del archivo 
            Output.writeUTF(archivo.getName());

            // Enviamos el tamaño del archivo
            Output.writeInt(tamanoArchivo);
            Output.writeUTF(nUsuario);

            FileInputStream fis = new FileInputStream(nombreArchivo);
            BufferedInputStream bis = new BufferedInputStream(fis);

            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            // Creamos un array de tipo byte con el tamaño del archivo 
            byte[] buffer = new byte[tamanoArchivo];

            // Leemos el archivo y lo introducimos en el array de bytes 
            bis.read(buffer);

            // Realizamos el envio de los bytes que conforman el archivo
            for (int i = 0; i < buffer.length; i++) {
                bos.write(buffer[i]);
            }

            System.out.println("Archivo Enviado: " + archivo.getName());
            // Cerramos socket y flujos    
            bis.close();
            bos.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

//   public static String Encriptar(String texto) {
//
//        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
//        String base64EncryptedString = "";
//
//        try {
//
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
//            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
//
//            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
//            Cipher cipher = Cipher.getInstance("DESede");
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//
//            byte[] plainTextBytes = texto.getBytes("utf-8");
//            byte[] buf = cipher.doFinal(plainTextBytes);
//            byte[] base64Bytes = Base64.encodeBase64(buf);
//            base64EncryptedString = new String(base64Bytes);
//
//        } catch (Exception ex) {
//        }
//        return base64EncryptedString;
//}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser2 = new javax.swing.JFileChooser();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton2.setText("Conectar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("IP Servidor");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Cliente");

        jMenu1.setText("Menu");

        jMenuItem1.setText("Guardar Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Cargar Archivo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(448, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ip = jTextField2.getText();
        JOptionPane.showMessageDialog(null, "conectando al servidor");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        int respuesta = fileChooser.showOpenDialog(this);

        if (respuesta == JFileChooser.APPROVE_OPTION) {

            File archivoElegido = fileChooser.getSelectedFile();

            System.out.println(archivoElegido.getName() + archivoElegido.getAbsolutePath());
            enviarArchivo(archivoElegido.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "se guardo exitosamente");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            // try {
            // TODO add your handling code here:
            VentanaCargar ventana =new VentanaCargar(ip,nUsuario);
            ventana.setVisible(true);
            this.dispose();
   
        } catch (IOException ex) {
            Logger.getLogger(VentanaEnviarArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jMenuItem2ActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaEnviarArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaEnviarArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaEnviarArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaEnviarArchivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaEnviarArchivos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
