package com.mycompany.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mycompany.beans.User;
import com.mycompany.dao.DaoFactory;
import com.mycompany.dao.HashClass;
import com.mycompany.dao.UserDaoImpl;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserDaoImpl userDaoImpl;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
    	// on crée une instance de DaoFactory
    	DaoFactory daoFactory = DaoFactory.getInstance();
    	userDaoImpl = new UserDaoImpl(daoFactory);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Une fois qu'on est logué on affiche la page listjoueur.jsp en local (donc sans passer par le doGet() de la servlet ListJoueur.java)
		// this.getServletContext().getRequestDispatcher("/WEB-INF/listjoueur.jsp").forward(request, response);
		
		String login = request.getParameter("txtLogin");
		String password = request.getParameter("txtPassword");
		
		// on récupère le password hashé
		password = HashClass.sha1(password);
		User connectedUser = userDaoImpl.isValidLogin(login, password);
		
		if (connectedUser != null)
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("connectedUser", connectedUser);
			// Pour afficher listejoueur.jsp mis à jour on fait appel à la servlet ListJoueur (à l'aide de son mapping) :
			response.sendRedirect("/AppliJoueurs/listjoueur");
			// On a utilisé la méthode sendRedirect pour dire qu'on fait appel au serveur (tomcat)
		}
		else
		{
			response.sendRedirect("/AppliJoueurs/login");
		}
	}

}
