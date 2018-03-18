package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet DatesModifications
 */
@DatabaseTable(tableName = "StatutHS", daoClass = StatutHSDao.class)
public class StatutHS {

	public static final String TABLE_NAME = "StatutHS";
	public static final String PATH_FIELD_NAME = "Niveauid";
	public static final String STATUTVALUE_FIELD_NAME = "StatutValue";

	
	
	public static final int HS = 1; // icone noir
	public static final int nHS = 0;// icone blanc
	
	
	
	@DatabaseField(id = true, columnName = PATH_FIELD_NAME)
	private int niveauid;
	public int getNiveauid() {
		return niveauid;
	}



	public void setNiveauid(int niveauid) {
		this.niveauid = niveauid;
	}

	@DatabaseField(columnName = STATUTVALUE_FIELD_NAME)
	private int statutValue;

	public StatutHS() {
		// needed by ormlite
	}

	

	public int getStatutValue() {
		return statutValue;
	}

	public void setStatutValue(int statutValue) {
		this.statutValue = statutValue;
	}

	
}