package com.mycompany.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.beans.Joueur;
import com.mycompany.beans.User;

public class JoueurDaoImpl implements JoueurDao {
	
	private DaoFactory daoFactory;
	
	public JoueurDaoImpl (DaoFactory daoFactory)
	{
		this.daoFactory = daoFactory;
	}

	
	@Override
    public Joueur lecture(Long id) {
        Connection connexion = null;
        PreparedStatement statement = null; 

        try{
            connexion = daoFactory.getConnection();
            statement=connexion.prepareStatement("SELECT * FROM JOUEUR WHERE ID=?");

            statement.setLong(1, id);
            ResultSet rs= statement.executeQuery();

                if ( rs.next() ) {

                    return new Joueur(
                            rs.getInt( "id" ),
                            rs.getString( "prenom" ),
                            rs.getString( "nom" ),
                            rs.getString( "sexe" )
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
    public void modifier(Joueur joueur) {
        Connection connexion = null;
        PreparedStatement statement = null; 

        try{

            connexion = daoFactory.getConnection();
            statement=connexion.prepareStatement("UPDATE JOUEUR SET NOM=?,PRENOM=?, SEXE=? WHERE ID=?");


              statement.setString(1, joueur.getNom());
              statement.setString(2, joueur.getPrenom() );
              statement.setString(3, joueur.getSexe());
              long idl=Long.valueOf(joueur.getId());
              statement.setLong(4, idl);
              statement.executeUpdate();
        }
         catch ( Exception exception ) {
            throw new RuntimeException( exception );
        }


    }
	
	public void ajouter (Joueur joueur)
	{
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "INSERT INTO joueur(NOM,PRENOM,SEXE) VALUES(?,?,?)";
			statement = connexion.prepareStatement(strSql);
			
			statement.setString(1, joueur.getNom());
			statement.setString(2, joueur.getPrenom());
			statement.setString(3, joueur.getSexe());
			
			statement.executeUpdate();
			
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	};
	public List<Joueur> lister()
	{
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM joueur";
			statement = connexion.prepareStatement(strSql);
			
			
			ResultSet rs = statement.executeQuery();
			
			while ( rs.next() ) {
				
				joueurs.add( new Joueur( rs.getInt( "ID" ), rs.getString( "PRENOM" ), rs.getString( "NOM" ),
						rs.getString( "SEXE" ) ) );
			
			} 
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	
		return joueurs;
	}
	
	public void supprimer(Long id) {
        Connection connexion = null;
        PreparedStatement statement = null; 

        try{

            connexion = daoFactory.getConnection();

            statement=connexion.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");
            statement.setLong(1, id);
            statement.executeUpdate();
        }
         catch ( Exception exception ) {
            throw new RuntimeException( exception );
        }


    }
	
	public List<Joueur> rechercher(String chaine)
	{
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		
		Connection connexion = null;
		PreparedStatement statement = null;
		
		try {
			
			// on récupère la connexion (liée au user)
			connexion = daoFactory.getConnection();
			String strSql = "SELECT * FROM joueur WHERE nom like ? OR prenom like ? ";
//			String strSql = "SELECT * FROM joueur WHERE nom like ' %" + chaine + "%' OR prenom like '%" + chaine + "%' ";
			
			statement = connexion.prepareStatement(strSql);
			
			statement.setString(1, "%" + chaine + "%");
			statement.setString(2, "%" + chaine + "%");
			
			ResultSet rs = statement.executeQuery();
			
			while ( rs.next() ) {
				
				joueurs.add( new Joueur( rs.getInt( "ID" ), rs.getString( "PRENOM" ), rs.getString( "NOM" ),
						rs.getString( "SEXE" ) ) );
			
			} 
		}
		catch ( Exception exception )
		{
			throw new RuntimeException( exception );
		}
		
	
		return joueurs;
	}
}
