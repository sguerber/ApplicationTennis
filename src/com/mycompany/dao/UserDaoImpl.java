package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mycompany.beans.User;

public class UserDaoImpl {
	private DaoFactory daoFactory;

	public UserDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	// pour vérifier si l'utilisateur est dans la bdd tennis (table connexion) ou pas 
	public User isValidLogin (String login, String password)
	{
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM connexion WHERE login=? AND password=?";
			statement = connexion.prepareStatement(strSql);
			// le premier ? est le login
			statement.setString(1, login);
			// le deuxième ? est le password
			statement.setString(2, password);
			
			ResultSet rs = statement.executeQuery();
			
			if ( rs.next() ) {
				
				return new User(
						rs.getInt( "id" ),
						rs.getString( "login" ),
						rs.getString( "password" ),
						rs.getInt( "profil" )
				);
			} else {
				return null;
			}
		}
		catch ( Exception exception ){
			throw new RuntimeException( exception );
		}
	}
	
	
}
