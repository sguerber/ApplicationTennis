package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Tournoi;

public class TournoiDaoImpl implements TournoiDao {

	private DaoFactory daoFactory;
	
	public TournoiDaoImpl (DaoFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}

	
	@Override
    public Tournoi lecture(Long id) {
        Connection connexion = null;
        PreparedStatement statement = null; 

        try{
            connexion = daoFactory.getConnection();
            statement=connexion.prepareStatement("SELECT * FROM TOURNOI WHERE ID=?");

            statement.setLong(1, id);
            ResultSet rs= statement.executeQuery();

                if ( rs.next() ) {

                    return new Tournoi (
                            rs.getInt( "id" ),
                            rs.getString( "nom" ),
                            rs.getString( "code" )
                    );
                } else {
                    return null;
                }
        }
         catch ( Exception exception ) {
            throw new RuntimeException( exception );
        }

    }
	
	@Override
    public void modifier(Tournoi tournoi) {
        Connection connexion = null;
        PreparedStatement statement = null; 

        try{

            connexion = daoFactory.getConnection();
            statement=connexion.prepareStatement("UPDATE TOURNOI SET NOM=?,CODE=? WHERE ID=?");


              statement.setString(1, tournoi.getNom());
              statement.setString(2, tournoi.getCode() );
              long idl=Long.valueOf(tournoi.getId());
              statement.setLong(3, idl);
              statement.executeUpdate();
        }
         catch ( Exception exception ) {
            throw new RuntimeException( exception );
        }


    }
	
	public void ajouter (Tournoi tournoi)
	{
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "INSERT INTO tournoi(NOM,CODE) VALUES(?,?)";
			statement = connexion.prepareStatement(strSql);
			
			statement.setString(1, tournoi.getNom());
			statement.setString(2, tournoi.getCode());
			
			statement.executeUpdate();
			
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	};
	public List<Tournoi> lister()
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM tournoi";
			statement = connexion.prepareStatement(strSql);
			
			
			ResultSet rs = statement.executeQuery();
			
			while ( rs.next() ) {
				
				tournois.add( new Tournoi( rs.getInt( "ID" ), rs.getString( "NOM" ), rs.getString( "CODE" ) ) );
			
			} 
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	
		return tournois;
	}
	
	public void supprimer(Long id) {
        Connection connexion = null;
         PreparedStatement statement = null; 

        try{

            connexion = daoFactory.getConnection();

            statement=connexion.prepareStatement("DELETE FROM TOURNOI WHERE ID=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        }
         catch ( Exception exception ) {
            throw new RuntimeException( exception );
        }


    }
	
	public List<Tournoi> rechercher(String chaine)
	{
		ArrayList<Tournoi> tournois = new ArrayList<Tournoi>();
		
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM tournoi WHERE nom like ? OR code like ? ";
//			String strSql = "SELECT * FROM tournoi WHERE nom like ' %" + chaine + "%' OR code like '%" + chaine + "%' ";
			
			statement = connexion.prepareStatement(strSql);
			
			statement.setString(1, "%" + chaine + "%");
			statement.setString(2, "%" + chaine + "%");
			
			ResultSet rs = statement.executeQuery();
			
			while ( rs.next() ) {
				
				tournois.add( new Tournoi( rs.getInt( "ID" ), rs.getString( "NOM" ), rs.getString( "CODE" ) ) );
			
			} 
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	
		return tournois;
	}
	
}
