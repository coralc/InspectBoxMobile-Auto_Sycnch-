package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Nfc", daoClass = NfcDao.class)
public class Nfc {
	public static final String TABLE_NAME = "Nfc";
	public static final String PATH_FIELD_NAME = "IdNiveau";
	public static final String TAG_FIELD_NAME = "NfcTag";
	
	
	@DatabaseField(id = true, columnName = PATH_FIELD_NAME)
	private int idNiveau;
	
	@DatabaseField(columnName = TAG_FIELD_NAME)
	private String nfcTag;
	
	public Nfc() {
		// needed by ormlite
	}
	public Nfc(Nfc i) {
		this.setIdNiveau(i.getIdNiveau());
		this.setNfcTag(i.getNfcTag());
		
		
	}
	
	public int getIdNiveau() {
		return idNiveau;
	}


	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}
	
	public String getNfcTag() {
		return nfcTag;
	}
	public void setNfcTag(String nfcTag) {
		this.nfcTag = nfcTag;
	}


	


	
	
	
}