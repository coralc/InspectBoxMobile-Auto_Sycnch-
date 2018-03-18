package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet NiveauObjet
 */
@DatabaseTable(tableName = NiveauObjet.NIVEAUOBJET_TABLE_NAME, daoClass = NiveauObjetDao.class)
public class NiveauObjet {

	public static final String NIVEAUOBJET_TABLE_NAME = "NiveauObjet";
	
	public static final String IDNIVEAUOBJET_FIELD_NAME = "IdNiveauObjet";
	public static final String IDTYPEREP_FIELD_NAME = "IdTypeReponse";
	public static final String IDNIVEAUOBJETPARENT_FIELD_NAME = "IdNiveauObjetParent";
	public static final String LIBELLE_FIELD_NAME = "Libelle";
	public static final String TRI_FIELD_NAME = "Tri";
	public static final String CODEBARRE_FIELD_NAME = "CodeBarre";
	public static final String REPONSEMINVALUE_FIELD_NAME = "Lowlimit";
	public static final String REPONSEMAXVALUE_FIELD_NAME = "Highlimit";
	public static final String LOWBORDER_FIELD_NAME = "Lowborder";
	public static final String HIGHBORDER_FIELD_NAME = "Highborder";
	public static final String UNITMEASURE_FIELD_NAME = "Unitmeasure";
	public static final String LOWBORDERALERT_FIELD_NAME = "Lowborderalert";
	public static final String HIGHBORDERALERT_FIELD_NAME = "Highborderalert";
	@DatabaseField(id = true, columnName = IDNIVEAUOBJET_FIELD_NAME)
	private int idNiveauobjet;
	@DatabaseField(columnName = IDTYPEREP_FIELD_NAME)
	private int idTypereponse;
	@DatabaseField(columnName = IDNIVEAUOBJETPARENT_FIELD_NAME)
	private int idNiveauobjetparent;
	@DatabaseField(columnName = LIBELLE_FIELD_NAME, canBeNull = true)
	private String libelle;
	
	@DatabaseField(columnName = TRI_FIELD_NAME)
	private int tri;
	@DatabaseField(columnName = CODEBARRE_FIELD_NAME, canBeNull = true)
	private String codebarre;
	@DatabaseField(columnName = UNITMEASURE_FIELD_NAME, canBeNull = true)
	private String unitmeasure;
	
	@DatabaseField(columnName = REPONSEMINVALUE_FIELD_NAME, canBeNull = true)
	private double lowlimit;
	@DatabaseField(columnName = REPONSEMAXVALUE_FIELD_NAME, canBeNull = true)
	private double highlimit;
	@DatabaseField(columnName = LOWBORDER_FIELD_NAME, canBeNull = true)
	private double lowborder;
	@DatabaseField(columnName = HIGHBORDER_FIELD_NAME, canBeNull = true)
	private double highborder;
	@DatabaseField(columnName = LOWBORDERALERT_FIELD_NAME, canBeNull = true)
	private String lowborderalert;
	@DatabaseField(columnName = HIGHBORDERALERT_FIELD_NAME, canBeNull = true)
	private String highborderalert;
	
	public NiveauObjet() {
		// needed by ormlite
	}

	public NiveauObjet(int idNiveauobjet) {
		setIdNiveauobjet(idNiveauobjet);
	}

	public int getIdNiveauobjet() {
		return idNiveauobjet;
	}

	public void setIdNiveauobjet(int idNiveauobjet) {
		this.idNiveauobjet = idNiveauobjet;
	}

	public int getIdTypereponse() {
		return idTypereponse;
	}

	public void setIdTypereponse(int idTypereponse) {
		this.idTypereponse = idTypereponse;
	}

	public int getIdNiveauobjetparent() {
		return idNiveauobjetparent;
	}

	public void setIdNiveauobjetparent(int idNiveauobjetparent) {
		this.idNiveauobjetparent = idNiveauobjetparent;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getTri() {
		return tri;
	}

	public void setTri(int tri) {
		this.tri = tri;
	}

	public String getCodebarre() {
		return codebarre;
	}

	public void setCodebarre(String codebarre) {
		this.codebarre = codebarre;
	}

	

	public String getUnitmeasure() {
		return unitmeasure;
	}

	public void setUnitmeasure(String unitmeasure) {
		this.unitmeasure = unitmeasure;
	}

	

	public double getLowlimit() {
		return lowlimit;
	}

	public void setLowlimit(double lowlimit) {
		this.lowlimit = lowlimit;
	}

	public double getHighlimit() {
		return highlimit;
	}

	public void setHighlimit(double highlimit) {
		this.highlimit = highlimit;
	}

	public double getLowborder() {
		return lowborder;
	}

	public void setLowborder(double lowborder) {
		this.lowborder = lowborder;
	}

	public double getHighborder() {
		return highborder;
	}

	public void setHighborder(double highborder) {
		this.highborder = highborder;
	}

	

	public String getLowborderalert() {
		return lowborderalert;
	}

	public void setLowborderalert(String lowborderalert) {
		this.lowborderalert = lowborderalert;
	}

	public String getHighborderalert() {
		return highborderalert;
	}

	public void setHighborderalert(String highborderalert) {
		this.highborderalert = highborderalert;
	}

	@Override
	public String toString() {
		return libelle;
	}
}