package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet Team
 */
@DatabaseTable(tableName = "Objeteam", daoClass = ObjeteamDao.class)
public class Objeteam {

	public static final String IDOBJTEAM_FIELD_NAME = "idField";
	public static final String TEAM_FIELD_NAME = "idTeam";
	public static final String OBJET_FIELD_NAME = "idObjet";
	public static final String LUN_FIELD_NAME = "lun";
	public static final String MAR_FIELD_NAME = "mar";
	public static final String MER_FIELD_NAME = "mer";
	public static final String JEU_FIELD_NAME = "jeu";
	public static final String VEN_FIELD_NAME = "ven";
	public static final String SAM_FIELD_NAME = "sam";
	public static final String DIM_FIELD_NAME = "dim";
	
	@DatabaseField(id = true, columnName = IDOBJTEAM_FIELD_NAME)
	private int idField;
	@DatabaseField(columnName = TEAM_FIELD_NAME, canBeNull = true)
	private int idTeam;
	@DatabaseField(columnName = OBJET_FIELD_NAME)
	private int idObjet;
	@DatabaseField(columnName = LUN_FIELD_NAME)
	private boolean lun;
	@DatabaseField(columnName = MAR_FIELD_NAME)
	private boolean mar;
	@DatabaseField(columnName = MER_FIELD_NAME)
	private boolean mer;
	@DatabaseField(columnName = JEU_FIELD_NAME)
	private boolean jeu;
	@DatabaseField(columnName = VEN_FIELD_NAME)
	private boolean ven;
	@DatabaseField(columnName = SAM_FIELD_NAME)
	private boolean sam;
	@DatabaseField(columnName = DIM_FIELD_NAME)
	private boolean dim;
	
	public Objeteam() {
		// needed by ormlite
	}
	
	public Objeteam(int IdField) {
		setIdField(IdField);
	}
	
	
	public int getIdField() {
		return idField;
	}

	public void setIdField(int idField) {
		this.idField = idField;
	}

	public int getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(int idTeam) {
		this.idTeam = idTeam;
	}

	public int getIdObjet() {
		return idObjet;
	}

	public void setIdObjet(int idObjet) {
		this.idObjet = idObjet;
	}

	public boolean isLun() {
		return lun;
	}

	public void setLun(boolean lun) {
		this.lun = lun;
	}

	public boolean isMar() {
		return mar;
	}

	public void setMar(boolean mar) {
		this.mar = mar;
	}

	public boolean isMer() {
		return mer;
	}

	public void setMer(boolean mer) {
		this.mer = mer;
	}

	public boolean isJeu() {
		return jeu;
	}

	public void setJeu(boolean jeu) {
		this.jeu = jeu;
	}

	public boolean isVen() {
		return ven;
	}

	public void setVen(boolean ven) {
		this.ven = ven;
	}

	public boolean isSam() {
		return sam;
	}

	public void setSam(boolean sam) {
		this.sam = sam;
	}

	public boolean isDim() {
		return dim;
	}

	public void setDim(boolean dim) {
		this.dim = dim;
	}

	

	
}