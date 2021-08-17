package fr.eni.bo;

public class Utilisateur {
	private Long no_utilisateur;
	private String pseudo;
	private String motdepasse;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String CP;
	private String ville;
	private String credit;
	private Boolean administrateur;
	
	public Utilisateur() {}
	
	public Utilisateur(String email, String motdepasse) {
		super();
		this.email = email;
		this.motdepasse = motdepasse;
	}
	
	public Utilisateur(String pseudo, String motdepasse, String nom, String prenom, String email, String telephone, String rue, String CP, String ville) {
		super();
		this.pseudo = pseudo;
		this.motdepasse = motdepasse;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.CP = CP;
		this.ville = ville;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
			this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getMotDePasse() {
		return motdepasse;
	}
	public void setMotDePasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public boolean getAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(Boolean administrateur) {
		this.administrateur = administrateur;
	}
	public Long getNo_utilisateur() {
		return no_utilisateur;
	}
	public void setNo_utilisateur(Long no_utilisateur) {
		this.no_utilisateur = no_utilisateur;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCP() {
		return CP;
	}
	public void setCP(String cP) {
		CP = cP;
	}
}
