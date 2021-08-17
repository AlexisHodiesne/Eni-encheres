package fr.eni.bll;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import fr.eni.bo.Utilisateur;
import fr.eni.dalDAO.DAOException;
import fr.eni.dalDAO.UtilisateurDao;

public class InscriptionForm {
	private static final String CHAMP_PSEUDO = "pseudo";
	private static final String CHAMP_EMAIL = "email";
	private static final String CHAMP_PASS = "motdepasse";
	private static final String CHAMP_CONF = "confirmation";
	private static final String CHAMP_NOM = "nom";
	private static final String CHAMP_PRENOM = "prenom";
	private static final String CHAMP_TELEPHONE = "telephone";
	private static final String CHAMP_RUE = "rue";
	private static final String CHAMP_CP = "CP";
	private static final String CHAMP_VILLE = "ville";
	private static final String ALGO_CHIFFREMENT = "SHA-256";
	private UtilisateurDao utilisateurDao;

	public InscriptionForm(UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Utilisateur inscrireUtilisateur(HttpServletRequest request) {
		String pseudo = request.getParameter(CHAMP_PSEUDO);
		String email = request.getParameter(CHAMP_EMAIL);
		String motDePasse = request.getParameter(CHAMP_PASS);
		String confirmation = request.getParameter(CHAMP_CONF);
		String nom = request.getParameter(CHAMP_NOM);
		String prenom = request.getParameter(CHAMP_PRENOM);
		String telephone = request.getParameter(CHAMP_TELEPHONE);
		String rue = request.getParameter(CHAMP_RUE);
		String CP = request.getParameter(CHAMP_CP);
		String ville = request.getParameter(CHAMP_VILLE);

		Utilisateur utilisateur = new Utilisateur();
		try {
			traiterPseudo(pseudo, utilisateur);
			traiterNom(nom, utilisateur);
			traiterPrenom(prenom, utilisateur);
			traiterVille(ville, utilisateur);
			traiterRue(rue, utilisateur);
			traiterEmail(email, utilisateur);
			traiterCP(CP, utilisateur);
			traiterTelephone(telephone, utilisateur);
			traiterMotsDePasse(motDePasse, confirmation, utilisateur);

			if (erreurs.isEmpty()) {
				utilisateurDao.creer(utilisateur);
				resultat = "Succès de l'inscription.";
			} else {
				resultat = "Échec de l'inscription.";
			}
		} catch (DAOException e) {
			resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
			e.printStackTrace();
		}

		return utilisateur;
	}
	
	private void traiterRue(String rue, Utilisateur utilisateur) {
		try {
			validationRue(rue);
		} catch (FormValidationException e) {
			setErreur(CHAMP_RUE, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setRue(rue);
	}
	
	private void traiterCP(String CP, Utilisateur utilisateur) {
		try {
			validationCP(CP);
		} catch (FormValidationException e) {
			setErreur(CHAMP_CP, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setCP(CP);
	}
	
	private void traiterTelephone(String telephone, Utilisateur utilisateur) {
		try {
			validationTelephone(telephone);
		} catch (FormValidationException e) {
			setErreur(CHAMP_TELEPHONE, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setTelephone(telephone);
	}

	private void traiterPseudo(String pseudo, Utilisateur utilisateur) {
		try {
			validationPseudo(pseudo);
		} catch (FormValidationException e) {
			setErreur(CHAMP_PSEUDO, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setPseudo(pseudo);
	}
	
	private void traiterNom(String nom, Utilisateur utilisateur) {
		try {
			validationNom(nom);
		} catch (FormValidationException e) {
			setErreur(CHAMP_NOM, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setNom(nom);
	}
	
	private void traiterPrenom(String prenom, Utilisateur utilisateur) {
		try {
			validationPrenom(prenom);
		} catch (FormValidationException e) {
			setErreur(CHAMP_PRENOM, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setPrenom(prenom);
	}
	private void traiterVille(String ville, Utilisateur utilisateur) {
		try {
			validationVille(ville);
		} catch (FormValidationException e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		utilisateur.setVille(ville);
	}
	
	private void traiterEmail(String email, Utilisateur utilisateur) {
		try {
			validationEmail(email);
		} catch (FormValidationException e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		utilisateur.setEmail(email);
	}

	private void traiterMotsDePasse(String motDePasse, String confirmation, Utilisateur utilisateur) {
		try {
			validationMotsDePasse(motDePasse, confirmation);
		} catch (FormValidationException e) {
			setErreur(CHAMP_PASS, e.getMessage());
			setErreur(CHAMP_CONF, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
		 * efficacement.
		 * 
		 * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage aléatoire et
		 * un grand nombre d'itérations de la fonction de hashage.
		 * 
		 * La String retournée est de longueur 56 et contient le hash en Base64.
		 */
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
		passwordEncryptor.setPlainDigest(false);
		String motDePasseChiffre = passwordEncryptor.encryptPassword(motDePasse);

		utilisateur.setMotDePasse(motDePasseChiffre);
	}

	/* Validation de l'adresse email */
	private void validationEmail(String email) throws FormValidationException {
		if (email != null) {
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
				throw new FormValidationException("Merci de saisir une adresse mail valide.");
			} else if (utilisateurDao.trouver(email) != null) {
				throw new FormValidationException(
						"Cette adresse email est déjà utilisée, merci d'en choisir une autre.");
			}
		} else {
			throw new FormValidationException("Merci de saisir une adresse mail.");
		}
	}

	private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception {
		if (motDePasse != null && confirmation != null) {
			if (!motDePasse.equals(confirmation)) {
				throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			} else if (motDePasse.length() < 3) {
				throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
			}
		} else {
			throw new Exception("Merci de saisir et confirmer votre mot de passe.");
		}
	}

	private void validationPseudo(String pseudo) throws Exception {
		if (pseudo != null && pseudo.length() < 3) {
			throw new Exception("Le pseudo doit contenir au moins 3 caractères.");
		}
	}

	private void validationNom(String nom) throws Exception {
		if (nom != null && nom.length() < 3) {
			throw new Exception("Le nom doit contenir au moins 3 caractères.");
		}
	}

	private void validationPrenom(String prenom) throws Exception {
		if (prenom != null && prenom.length() < 3) {
			throw new Exception("Le prenom doit contenir au moins 3 caractères.");
		}
	}

	private void validationTelephone(String telephone) throws Exception {
		if (!telephone.matches("^0[1-6]{1}(([0-9]{2}){4})|((\\s[0-9]{2}){4})|((-[0-9]{2}){4})$")) {
			throw new Exception("Le numéro doit être au format: 01 46 70 89 12 | 01-46-70-89-12 | 0146708912");
		}
	}

	private void validationRue(String rue) throws Exception {
		if (rue != null && rue.length() < 5) {
			throw new Exception("La rue doit contenir au moins 5 caractères.");
		}
	}

	private void validationCP(String CP) throws Exception {
		if (!CP.matches("^[0-9]{5}$")) {
			throw new Exception("Un code postal est composé de 5 chiffres !");
		}
	}

	private void validationVille(String ville) throws Exception {
		if (ville != null && ville.length() < 3) {
			throw new Exception("La ville doit contenir au moins 3 caractères.");
		}
	}

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	public static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur.trim();
		}
	}
}
