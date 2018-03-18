package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet parametrage
 */
@DatabaseTable(tableName = "Parametrage")
public class Parametrage {
	
	public static final String IDPARAMETRAGE_FIELD_NAME = "Id";
	public static final String CODECLIENT_FIELD_NAME = "CodeClient";
	public static final String PASSCLIENT_FIELD_NAME = "MotDePasseClient";
	public static final String URLCLIENT_FIELD_NAME = "UrlClient";
	public static final String TERMINAL_FIELD_NAME = "NbTerminal";
	public static final String IDUTILISATEUR_FIELD_NAME = "IdUtilisateur";
	public static final String BARECODE_FIELD_NAME = "Codebarre";
	public static final String passappli_FIELD_NAME = "Passeappli";
	
	@DatabaseField(id = true, columnName = IDPARAMETRAGE_FIELD_NAME)
	private int id;
	@DatabaseField(columnName = CODECLIENT_FIELD_NAME)
	private String codeClient;
	@DatabaseField(columnName = PASSCLIENT_FIELD_NAME)
	private String motdepasseClient;
	@DatabaseField(columnName = URLCLIENT_FIELD_NAME)
	private String urlClient;
	@DatabaseField(columnName = TERMINAL_FIELD_NAME)
	private String nbTerminal;
	@DatabaseField(columnName = IDUTILISATEUR_FIELD_NAME)
	private int idUtilisateur;
	@DatabaseField(columnName = BARECODE_FIELD_NAME)
	private boolean codebarre;
	@DatabaseField(columnName = passappli_FIELD_NAME)
	private String passeappli;
	Parametrage() {
		this.id = 1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	public String getMotdepasseClient() {
		return motdepasseClient;
	}

	public void setMotdepasseClient(String motdepasseClient) {
		this.motdepasseClient = motdepasseClient;
	}

	public String getUrlClient() {
		return urlClient;
	}

	public void setUrlClient(String urlClient) {
		this.urlClient = urlClient;
	}

	public String getNbTerminal() {
		return nbTerminal;
	}

	public void setNbTerminal(String nbTerminal) {
		this.nbTerminal = nbTerminal;
	}

	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public boolean isCodebarre() {
		return codebarre;
	}

	public void setCodebarre(boolean codebarre) {
		this.codebarre = codebarre;
	}

	public String getPasseappli() {
		return passeappli;
	}

	public void setPasseappli(String passeappli) {
		this.passeappli = passeappli;
	}

	@Override
	public String toString() {
		return codeClient;
	}
}