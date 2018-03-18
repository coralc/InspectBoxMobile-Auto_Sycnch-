package com.coralc.inspectbox.database;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet TypeReponse
 */
@DatabaseTable(tableName = "TypeReponse")
public class TypeReponse {

	public static final String IDTYPEREPONSE_FIELD_NAME = "IdTypeReponse";
	public static final String MODEREPONSE_FIELD_NAME = "ModeReponse";
	public static final String LIBELLE_FIELD_NAME = "Libelle";
	public static final String IDCLIENT_FIELD_NAME = "IdClient";
	public static final String MASQUE_FIELD_NAME = "Masque";
	public static final String CLETIMESTAMP_FIELD_NAME = "CleTimestamp";
	
	public static final String MODE_LISTE = "Liste";
	public static final String MODE_VALEUR = "Valeur";
	public static final String MODE_DATE	 = "Date";
	@DatabaseField(id = true, columnName = IDTYPEREPONSE_FIELD_NAME)
	private Integer idTypeReponse;
	@DatabaseField(columnName = IDCLIENT_FIELD_NAME)
	private Integer idclient;
	@DatabaseField(columnName = LIBELLE_FIELD_NAME, canBeNull = true)
	private String libelle;
	@DatabaseField(columnName = MODEREPONSE_FIELD_NAME, canBeNull = true)
	private String modeReponse;
	@DatabaseField(columnName = MASQUE_FIELD_NAME)
	private boolean masque;
	@DatabaseField(columnName = CLETIMESTAMP_FIELD_NAME)
	private Date clefTimestamp;

	public TypeReponse() {
		// needed by ormlite
	}

	public TypeReponse(Integer idTypeReponse) {
		setIdTypeReponse(idTypeReponse);
	}

	public Integer getIdTypeReponse() {
		return idTypeReponse;
	}

	public void setIdTypeReponse(Integer idTypeReponse) {
		this.idTypeReponse = idTypeReponse;
	}

	public Integer getIdclient() {
		return idclient;
	}

	public void setIdclient(Integer idclient) {
		this.idclient = idclient;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getModeReponse() {
		return modeReponse;
	}

	public void setModeReponse(String modeReponse) {
		this.modeReponse = modeReponse;
	}

	public boolean isMasque() {
		return masque;
	}

	public void setMasque(boolean masque) {
		this.masque = masque;
	}

	public Date getClefTimestamp() {
		return clefTimestamp;
	}

	public void setClefTimestamp(Date clefTimestamp) {
		this.clefTimestamp = clefTimestamp;
	}

	@Override
	public String toString() {
		return libelle;
	}
}