package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet Utilisateur
 */
@DatabaseTable(tableName = "utilisateur")
public class Utilisateur {

	public static final String IDUTILISATEUR_FIELD_NAME = "IdUtilisateur";
	public static final String CODEACCES_FIELD_NAME = "CodeAcces";
	public static final String PASSWORD_FIELD_NAME = "MotDePasse";
	public static final String NOM_FIELD_NAME = "Nom";
	public static final String PRENOM_FIELD_NAME = "Prenom";
	public static final String TYPE_FIELD_NAME="TypeUser";
	@DatabaseField(id = true, columnName = IDUTILISATEUR_FIELD_NAME)
	private int idUtilisateur;
	@DatabaseField(columnName = CODEACCES_FIELD_NAME, canBeNull = true)
	private String codeAcces;
	@DatabaseField(columnName = PASSWORD_FIELD_NAME, canBeNull = true)
	private String motDePasse;
	@DatabaseField(columnName = NOM_FIELD_NAME, canBeNull = true)
	private String nom;
	@DatabaseField(columnName = PRENOM_FIELD_NAME, canBeNull = true)
	private String prenom;
	@DatabaseField(columnName = TYPE_FIELD_NAME, canBeNull = true)
	private String typeUser;
	public Utilisateur() {
		// needed by ormlite
	}
	
	public Utilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}
	
	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public String getCodeAcces() {
		return codeAcces;
	}

	public void setCodeAcces(String codeAcces) {
		this.codeAcces = codeAcces;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
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
	
	

	public String getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(String typeUser) {
		this.typeUser = typeUser;
	}

	@Override
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			return ((Utilisateur) o).getIdUtilisateur() == this.getIdUtilisateur();
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		//sb.append(prenom).append(" ").append(nom);
		sb.append(codeAcces);
		return sb.toString();
	}
}