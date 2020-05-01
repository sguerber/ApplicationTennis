// Cette classe permet de connecter notre appli JavaEE à la bdd tennis

package com.mycompany.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
	
    private String url;
    private String username;
    private String password;
     
     
     
   public DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
   
   
   
   	// On charge le driver avec getInstance()
    public static DaoFactory getInstance() {
    	 try {
             Class.forName("com.mysql.cj.jdbc.Driver");
         } catch (ClassNotFoundException e) {
         }
    	
    	
        // connexion à ma bdd
        DaoFactory instance = new DaoFactory(
        		"jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris", "root", "");
        return instance;
    }
    
    
    
    // Ensuite on se connecte avec getConnection()
    public Connection getConnection() throws SQLException {
        Connection connexion = DriverManager.getConnection(url, username, password);
       
        return connexion; 
    }

   
}