
package Domain;

/**
 *
 * @author Pablo
 */
public class Usuarios {
    String nombre;

    String id;

    public Usuarios(String nombre, String id) {
        this.nombre = nombre;
  
        this.id = id;
    }
    
    public Usuarios(){
    
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


  
    
    
}
