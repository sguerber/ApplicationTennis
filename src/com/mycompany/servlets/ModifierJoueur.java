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
 * Servlet implementation class ModifierJoueur
 */
@WebServlet("/modifierjoueur")
public class ModifierJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JoueurDao joueurDao;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierJoueur() {
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
		// On récupère l'id du joueur afin de l'afficher dans la page modifierjoueur.jsp si on a cliqué sur le bouton "Modifier"
		String id = request.getParameter("idjoueur");
		long id1 = Long.parseLong(id);
		
		
		if (request.getParameter("action").equals("Modifier"))
		{
			Joueur joueur = joueurDao.lecture(id1);
			
			request.setAttribute("joueur", joueur);
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifierjoueur.jsp").forward(request, response);
		}
		else 
		{
			joueurDao.supprimer(id1);
//			request.setAttribute("joueurs", joueurDao.lister());
			response.sendRedirect("/AppliJoueurs/listjoueur");
		}
		
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
		joueur.setNom(request.getParameter("txtNom"));
		joueur.setPrenom(request.getParameter("txtPrenom"));
		joueur.setSexe(request.getParameter("opSexe"));
		int id = Integer.parseInt(request.getParameter("idjoueur"));
		joueur.setId(id);
		joueurDao.modifier(joueur);
		response.sendRedirect("/AppliJoueurs/listjoueur");
		
	}

}
