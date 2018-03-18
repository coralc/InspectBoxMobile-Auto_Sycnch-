package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet Team
 */
@DatabaseTable(tableName = "Team" ,daoClass = TeamDao.class)
public class Team {

	public static final String IDTEAM_FIELD_NAME = "idteam";
	public static final String BEGIN_FIELD_NAME = "beginHour";
	public static final String END_FIELD_NAME = "endHour";
	public static final String DAYWORK_FIELD_NAME = "dayOfWork";
	public static final String USER_FIELD_NAME = "idUser";
	
	@DatabaseField(id = true, columnName = IDTEAM_FIELD_NAME)
	private Integer idteam;
	@DatabaseField(columnName = BEGIN_FIELD_NAME, canBeNull = false)
	private String beginHour;
	@DatabaseField(columnName = END_FIELD_NAME ,canBeNull = false)
	private String endHour;
	@DatabaseField(columnName = DAYWORK_FIELD_NAME, canBeNull = false)
	private String dayOfWork;
	@DatabaseField(columnName = USER_FIELD_NAME, canBeNull = false)
	private Integer idUser;
	
	public Team() {
		// needed by ormlite
	}
	
	public Team(Integer Idteam) {
		setIdteam(Idteam);
	}
	
	

	

	

	public Integer getIdteam() {
		return idteam;
	}

	public void setIdteam(Integer idteam) {
		this.idteam = idteam;
	}

	public String getBeginHour() {
		return beginHour;
	}

	public void setBeginHour(String beginHour) {
		this.beginHour = beginHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getDayOfWork() {
		return dayOfWork;
	}

	public void setDayOfWork(String dayOfWork) {
		this.dayOfWork = dayOfWork;
	}

	


	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return Integer.toString(idteam);
	}
	
}