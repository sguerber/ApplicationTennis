package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.JoueurDao;
import com.mycompany.dao.JoueurDaoImpl;
import com.mycompany.dao.UserDaoImpl;

/**
 * Servlet implementation class ListJoueur
 */
@WebServlet("/listjoueur")
public class ListJoueur extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JoueurDaoImpl joueurDaoImpl;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListJoueur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    
    public void init() throws ServletException {
    	// on crée une instance de DaoFactory
    	DaoFactory daoFactory = DaoFactory.getInstance();
    	joueurDaoImpl = new JoueurDaoImpl(daoFactory);
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
		request.setAttribute("joueurs", joueurDaoImpl.lister());
		
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
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
			if (joueurDaoImpl.rechercher(request.getParameter("search")).size() == 0)
			{
				request.setAttribute("nboccurrence", 0);
			}
			
			request.setAttribute("joueurs", joueurDaoImpl.rechercher(request.getParameter("search")));
			this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
			
		}
		else if (request.getParameter("action1").equals("Deconnexion")){
            session.setAttribute( "connectedUser",null );            
            response.sendRedirect( "/AppliJoueurs/login" );
            return;
        
     }    
		
	}

	

}
