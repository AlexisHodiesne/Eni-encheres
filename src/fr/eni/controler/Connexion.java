package fr.eni.controler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.bll.ConnexionForm;
import fr.eni.bo.Utilisateur;

@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
		this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request );

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute("sessionUtilisateur", utilisateur );
        } else {
            session.setAttribute("sessionUtilisateur", null );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute("form", form );
        request.setAttribute("utilisateur", utilisateur );
        
        if ( form.getErreurs().isEmpty() ) {
        	response.sendRedirect("http://localhost:8080/encheres/");
        } else {
        	this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
        }
    }
}
