package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet DatesModifications
 */
@DatabaseTable(tableName = "DatesModifications")
public class DatesModifications {

	public static final String NOMTABLE_FIELD_NAME = "NomTable";
	public static final String TIMESTAMP_FIELD_NAME = "TimeStamp";

	@DatabaseField(id = true, columnName = NOMTABLE_FIELD_NAME)
	private String nomTable;
	@DatabaseField(columnName = TIMESTAMP_FIELD_NAME)
	private String timestamp;

	public DatesModifications() {
		// needed by ormlite
	}

	public String getNomTable() {
		return nomTable;
	}

	public void setNomTable(String nomTable) {
		this.nomTable = nomTable;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		
		return nomTable;
	}
}