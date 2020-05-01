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
import com.mycompany.dao.TournoiDao;
import com.mycompany.dao.TournoiDaoImpl;

@WebServlet("/modifiertournoi")
public class ModifierTournoi extends HttpServlet {

	private TournoiDao tournoiDao;

	@Override
	public void init() throws ServletException {

		DaoFactory daoFactory = DaoFactory.getInstance();
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
		// On récupère l'id du joueur afin de l'afficher dans la page modifiertournoi.jsp si on a cliqué sur le bouton "Modifier"
		String id = request.getParameter("idtournoi");
		long id1 = Long.parseLong(id);
		
		
		if (request.getParameter("action").equals("Modifier"))
		{
			Tournoi tournoi = tournoiDao.lecture(id1);
			
			request.setAttribute("tournoi", tournoi);
			this.getServletContext().getRequestDispatcher("/WEB-INF/modifiertournoi.jsp").forward(request, response);
		}
		else 
		{
			tournoiDao.supprimer(id1);
			response.sendRedirect("/AppliJoueurs/listtournoi");
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
		
		
		Tournoi tournoi = new Tournoi();
		tournoi.setNom(request.getParameter("txtNom"));
		tournoi.setCode(request.getParameter("txtCode"));
		int id = Integer.parseInt(request.getParameter("idtournoi"));
		tournoi.setId(id);
		tournoiDao.modifier(tournoi);
		response.sendRedirect("/AppliJoueurs/listtournoi");
		
	}
	
}
