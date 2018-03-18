package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet DatesModifications
 */
@DatabaseTable(tableName = "StatutInac", daoClass = StatutInacDao.class)
public class StatutInac {

	public static final String TABLE_NAME = "StatutInac";
	public static final String PATH_FIELD_NAME = "IdNiveau";
	public static final String STATUTVALUE_FIELD_NAME = "StatutValue";
	public static final String OBJETVALUE_FIELD_NAME = "Objet";
	public static final String DATE_FIELD_NAME = "Dateinacc";
	
	
	
	
	@DatabaseField(id = true, columnName = PATH_FIELD_NAME)
	private int idNiveau;
	@DatabaseField(columnName = STATUTVALUE_FIELD_NAME)
	private boolean statutValue;
	@DatabaseField(columnName = OBJETVALUE_FIELD_NAME)
	private int objet;
	@DatabaseField(columnName = DATE_FIELD_NAME)
	private String dateinacc;
	public StatutInac() {
		// needed by ormlite
	}
	public StatutInac(StatutInac i) {
		this.setIdNiveau(i.getIdNiveau());
		this.setDateinacc(i.getDateinacc());
		this.setObjet(i.getObjet());
		this.setStatutValue(i.isStatutValue());
		
	}
	public int getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}

	public boolean isStatutValue() {
		return statutValue;
	}

	public void setStatutValue(boolean statutValue) {
		this.statutValue = statutValue;
	}

	public int getObjet() {
		return objet;
	}

	public void setObjet(int objet) {
		this.objet = objet;
	}

	public String getDateinacc() {
		return dateinacc;
	}

	public void setDateinacc(String dateinacc) {
		this.dateinacc = dateinacc;
	}

	

	
}