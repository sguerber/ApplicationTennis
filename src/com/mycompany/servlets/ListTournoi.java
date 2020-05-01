package com.mycompany.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.TournoiDaoImpl;

@WebServlet("/listtournoi")
public class ListTournoi extends HttpServlet {

	private TournoiDaoImpl tournoiDaoImpl;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ListTournoi() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    public void init() throws ServletException {
    	// on crée une instance de DaoFactory
    	DaoFactory daoFactory = DaoFactory.getInstance();
    	tournoiDaoImpl = new TournoiDaoImpl(daoFactory);
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
				
		
		// On ajoute l'attribut "joueurs" à l'objet requête et on le set (pour récupérer joueurs du côté de listjoueur.jsp)
		request.setAttribute("tournois", tournoiDaoImpl.lister());
		
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/listtournoi.jsp").forward(request, response);
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
		
		
		// Si on appelle le doPost depuis le bouton de Recherche :
		if (request.getParameter("action1").equals("Rechercher"))
		{
		
			// Si zéro résultat
			if (tournoiDaoImpl.rechercher(request.getParameter("search")).size() == 0)
			{
				request.setAttribute("nboccurrence", 0);
			}
			
			request.setAttribute("tournois", tournoiDaoImpl.rechercher(request.getParameter("search")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/listtournoi.jsp").forward(request, response);
			
		}
		else if (request.getParameter("action1").equals("Deconnexion")){
            session.setAttribute( "connectedUser",null );            
            response.sendRedirect( "/AppliJoueurs/login" );
            return;
        
     }    
		
	}
}
