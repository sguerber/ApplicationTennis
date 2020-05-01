package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Joueur;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;

/**
 * Servlet implementation class AjouterJoueur
 */
@WebServlet("/ajouterjoueur")
public class AjouterJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JoueurDao joueurDao;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterJoueur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	@Override
	public void init() throws ServletException {
		// On crée une instance de DaoFactory
		DaoFactory daoFactory = DaoFactory.getInstance();
		// On instancie joueurDao comme un objet JoueurDaoImpl pour accéder à la méthode ajouter()
		joueurDao = new JoueurDaoImpl(daoFactory);
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// On vérifie si une session est active
		HttpSession session = request.getSession(true);
		if(session.getAttribute("connectedUser") == null){
		    response.sendRedirect("/AppliJoueurs/login");
		    return;
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajouterjoueur.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// On vérifie si une session est active
		HttpSession session = request.getSession(true);
		if(session.getAttribute("connectedUser") == null){
		    response.sendRedirect("/AppliJoueurs/login");
		    return;
		}
		
		Joueur joueur = new Joueur();
		
		// On récupère les données des inputs de ajouterjoueur.jsp
		joueur.setNom(request.getParameter("txtNom"));
		joueur.setPrenom(request.getParameter("txtPrenom"));
		joueur.setSexe(request.getParameter("opSexe"));
		
		// On appelle la méthode ajouter() pour ajouter le nouveau joueur en bdd
		joueurDao.ajouter(joueur);
		
		// On crée la List<Joueur> joueurs et on la set avec la nouvelle liste
		// request.setAttribute("joueurs", joueurDao.lister());
		// On le met pas on fait un sendRedirect vers la servlet ListJoueur qui exécute déjà cette instruction dans le doGet() avant d'afficher la page listjoueur.jsp:
		
		// On affiche la nouvelle liste à l'aide de la page listjoueur.jsp updated
		response.sendRedirect("/AppliJoueurs/listjoueur");
	}

}
