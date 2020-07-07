/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Emilio Araya
 */
public class connection {
    static Connection cn;
    
    public static Connection getConnection(String conn){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cn = DriverManager.getConnection(conn);
                                           //  "jdbc:sqlserver://163.178.107.10:1433;databaseName=redesB51675;user=laboratorios;password=Saucr.118";
        }catch (Exception e){
            e.printStackTrace();
            cn = null;
        }
        return cn;
    }
    
}
