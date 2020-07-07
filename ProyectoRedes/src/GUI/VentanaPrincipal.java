/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Pablo
 */
public class VentanaPrincipal extends JFrame implements ActionListener {
    
    JMenuBar barra = new JMenuBar();//LA DECLARAMOS E INSTANCIAMOS.

    JMenu programa = new JMenu("   Inicio");
    JMenu ayuda = new JMenu("Ayuda");
    JMenuItem ingresarUsuario = new JMenuItem("Ingresar Usuarios");
    JMenuItem cargarUsuario=new JMenuItem("Cargar Usuario");
    
    JMenu guardar = new JMenu("Guardar");
    
    public VentanaPrincipal() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        Init();
    }
    
    public void Init() {
        this.setSize(500, 500);
        this.setJMenuBar(barra); 
        barra.add(programa);
        programa.add(ingresarUsuario);
        programa.add(cargarUsuario);
        this.ingresarUsuario.addActionListener(this);
    }//FIN DEL CONSTRUCTOR DE LA CLASE Ventana


    public void actionPerformed(ActionEvent ae) {

        if(ae.getSource()==ingresarUsuario){
                System.out.print("entre");
               // VentanaRegistrarUsuarios ventana=new VentanaRegistrarUsuarios();
           // ventana.setVisible(true);
           this.dispose();
        }
    }
    
}//FIN DE LA CLASE Ventana


