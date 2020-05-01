package com.mycompany.beans;

public class Joueur {
	private int id;
	private String prenom;
	private String nom;
	private String sexe;
	
	public Joueur(int id, String prenom, String nom, String sexe) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.sexe = sexe;
	}

	// toujours mettre un constructeur par défaut sans paramètre
	public Joueur ()
	{
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getSexe() {
		return sexe;
	}
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}
	
	
}
