package com.mycompany.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.Joueur;
import com.mycompany.beans.Tournoi;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

@WebServlet("/ajoutertournoi")
public class AjouterTournoi extends HttpServlet {

private TournoiDao tournoiDao;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterTournoi() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	@Override
	public void init() throws ServletException {
		// On crée une instance de DaoFactory
		DaoFactory daoFactory = DaoFactory.getInstance();
		// On instancie tournoiDao comme un objet JoueurDaoImpl pour accéder à la méthode ajouter()
		tournoiDao = new TournoiDaoImpl(daoFactory);
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
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutertournoi.jsp").forward(request, response);
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
		
		Tournoi tournoi = new Tournoi();
		
		// On récupère les données des inputs de ajouterjoueur.jsp
		tournoi.setNom(request.getParameter("txtNom"));
		tournoi.setCode(request.getParameter("txtCode"));
		
		// On appelle la méthode ajouter() pour ajouter le nouveau joueur en bdd
		tournoiDao.ajouter(tournoi);
		
		// On crée la List<Tournoi> tournois et on la set avec la nouvelle liste
		// request.setAttribute("tournois", tournoiDao.lister());
		// On le met pas on fait un sendRedirect vers la servlet ListJoueur qui exécute déjà cette instruction dans le doGet() avant d'afficher la page listtournoi.jsp:
		
		// On affiche la nouvelle liste à l'aide de la page listtournoi.jsp updated
		response.sendRedirect("/AppliJoueurs/listtournoi");
	}
	
}
