package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet inspection
 */
@DatabaseTable(tableName = Inspection.INSPECTION_TABLE_NAME, daoClass = InspectionDao.class)
public class Inspection {
	public static final String INSPECTION_TABLE_NAME = "Inspection";
	
	public static final String IDINSPECTION_FIELD_NAME = "IdInspection";
	public static final String IDNIVEAUBAT_FIELD_NAME = "IdNiveaubat";
	public static final String IDNIVEAU_FIELD_NAME = "IdNiveau";
	public static final String IDNIVEAUOBJET_FIELD_NAME = "IdNiveauObjet";
	public static final String IDINFORMATION_FIELD_NAME = "IdInformation";
	public static final String DATEINFORMATION_FIELD_NAME = "DateInformation";
	public static final String IDCHOIX_FIELD_NAME = "IdChoix";
	public static final String REPONSE_FIELD_NAME = "Reponse";
	public static final String Statut_FIELD_NAME = "Statut";
	public static final String PAUSE_FIELD_NAME = "Pause";
	public static final String DEFECT_FIELD_NAME = "Defekt";
	public static final String LIMIT_FIELD_NAME = "Limite";
	
	@DatabaseField(generatedId = true, columnName = IDINSPECTION_FIELD_NAME)
	private int idInspection;
	@DatabaseField(columnName = IDNIVEAUBAT_FIELD_NAME)
	private int idNiveaubat;
	@DatabaseField(columnName = IDNIVEAU_FIELD_NAME)
	private int idNiveau;
	@DatabaseField(columnName = IDNIVEAUOBJET_FIELD_NAME)
	private int idNiveauObjet;
	@DatabaseField(columnName = IDINFORMATION_FIELD_NAME)
	private String idInformation;
	@DatabaseField(columnName = DATEINFORMATION_FIELD_NAME)
	private String dateInformation;
	@DatabaseField(columnName = IDCHOIX_FIELD_NAME, canBeNull = true)
	private Integer idChoix;
	@DatabaseField(columnName = REPONSE_FIELD_NAME)
	private String reponse;
	
	@DatabaseField(columnName = Statut_FIELD_NAME)
	private int statut ;
	
	@DatabaseField(columnName = PAUSE_FIELD_NAME)
	private boolean pause;
	@DatabaseField(columnName = DEFECT_FIELD_NAME)
	private Boolean defekt;
	@DatabaseField(columnName = LIMIT_FIELD_NAME)
	private Boolean limite;
	public Inspection() {
		// needed by ormlite
	}
	
	public Inspection(Inspection i) {
		
		this.setDateInformation(i.getDateInformation());
		this.setIdChoix(i.getIdChoix());
		this.setIdInformation(i.getIdInformation());
		this.setIdInspection(i.getIdInspection());
		this.setIdNiveau(i.getIdNiveau());
		this.setIdNiveaubat(i.getIdNiveaubat());
		this.setIdNiveauObjet(i.getIdNiveauObjet());
		this.setReponse(i.getReponse());
		this.setPause(i.isPause());
		this.setDefekt(i.getDefekt());
		this.setLimite(i.getLimite());
	}

	public int getIdInspection() {
		return idInspection;
	}

	public void setIdInspection(int idInspection) {
		this.idInspection = idInspection;
	}

	public int getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}

	public int getIdNiveauObjet() {
		return idNiveauObjet;
	}

	public void setIdNiveauObjet(int idNiveauObjet) {
		this.idNiveauObjet = idNiveauObjet;
	}

	public String getIdInformation() {
		return idInformation;
	}

	public void setIdInformation(String idInformation) {
		this.idInformation = idInformation;
	}

	public String getDateInformation() {
		return dateInformation;
	}

	public void setDateInformation(String dateInformation) {
		this.dateInformation = dateInformation;
	}

	public Integer getIdChoix() {
		return idChoix;
	}

	public void setIdChoix(Integer idChoix) {
		this.idChoix = idChoix;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	
	

	

	public int getIdNiveaubat() {
		return idNiveaubat;
	}

	public void setIdNiveaubat(int idNiveaubat) {
		this.idNiveaubat = idNiveaubat;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	@Override
	public String toString() {
		return Integer.toString(idInspection);
	}

	public Boolean getDefekt() {
		return defekt;
	}

	public void setDefekt(Boolean defekt) {
		this.defekt = defekt;
	}

	public Boolean getLimite() {
		return limite;
	}

	public void setLimite(Boolean limit) {
		this.limite = limit;
	}
}