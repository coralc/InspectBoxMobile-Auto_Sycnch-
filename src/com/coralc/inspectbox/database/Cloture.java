package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Cloture", daoClass = ClotureDao.class)
public class Cloture {
	public static final String TABLE_NAME = "Cloture";
	public static final String PATH_FIELD_NAME = "IdNiveau";
	public static final String TIME_FIELD_NAME = "DateCloture";
	public static final String FTIME_FIELD_NAME = "DebDateCloture";
	public static final String Statut_FIELD_NAME = "StatutCloture";
	public static final String USER_FIELD_NAME = "UserCloture";
	public static final String DAY_FIELD_NAME = "DayCloture";
	@DatabaseField(id = true, columnName = PATH_FIELD_NAME)
	private int idNiveau;
	@DatabaseField(columnName = TIME_FIELD_NAME)
	private String dateCloture;
	@DatabaseField(columnName = FTIME_FIELD_NAME)
	private String debDateCloture;
	@DatabaseField(columnName = Statut_FIELD_NAME)
	private int statutCloture;
	@DatabaseField(columnName = USER_FIELD_NAME)
	private int userCloture;
	@DatabaseField(columnName = DAY_FIELD_NAME)
	private int dayCloture;
	public Cloture() {
		// needed by ormlite
	}
	public Cloture(Cloture i) {
		this.setIdNiveau(i.getIdNiveau());
		this.setDateCloture(i.getDateCloture());
		this.setDebDateCloture(i.getDebDateCloture());
		this.setStatutCloture(i.getStatutCloture());
		this.setUserCloture(i.getUserCloture());
		this.setDayCloture(i.getDayCloture());
	}
	
	public int getIdNiveau() {
		return idNiveau;
	}


	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}


	public String getDateCloture() {
		return dateCloture;
	}


	public void setDateCloture(String dateCloture) {
		this.dateCloture = dateCloture;
	}


	public String getDebDateCloture() {
		return debDateCloture;
	}
	public void setDebDateCloture(String debDateCloture) {
		this.debDateCloture = debDateCloture;
	}
	public int getStatutCloture() {
		return statutCloture;
	}


	public void setStatutCloture(int statutCloture) {
		this.statutCloture = statutCloture;
	}
	public int getUserCloture() {
		return userCloture;
	}
	public void setUserCloture(int userCloture) {
		this.userCloture = userCloture;
	}
	public int getDayCloture() {
		return dayCloture;
	}
	public void setDayCloture(int dayCloture) {
		this.dayCloture = dayCloture;
	}


	
	
	
}