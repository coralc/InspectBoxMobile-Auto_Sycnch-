package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet libelleNiveau
 */
@DatabaseTable(tableName = "LibelleNiveau", daoClass = LibelleNiveauDao.class)
public class LibelleNiveau {

	public static final String IDNIVEAU_FIELD_NAME = "IdLibelleNiveau";
	public static final String NUM_FIELD_NAME = "Num";
	public static final String TYPENIVEAU_FIELD_NAME = "TypeNiveau";
	public static final String LIBELLE_FIELD_NAME = "Libelle";
	
	// Les différents types de niveaux possible sont :
	public static final int TYPE_NIVEAU = 1;
	public static final int TYPE_NIVEAUOBJET = 2;
	public static final int TYPE_CLIENT = 3;

	@DatabaseField(id = true, columnName = IDNIVEAU_FIELD_NAME)
	private int idLibelleniveau;
	@DatabaseField(columnName = NUM_FIELD_NAME)
	private int num;
	@DatabaseField(columnName = TYPENIVEAU_FIELD_NAME)
	private int typeniveau;
	@DatabaseField(columnName = LIBELLE_FIELD_NAME, canBeNull = true)
	private String libelle;

	public LibelleNiveau() {
		// needed by ormlite
	}

	public LibelleNiveau(int idLibelleniveau) {
		setIdLibelleniveau(idLibelleniveau);
	}

	public int getIdLibelleniveau() {
		return idLibelleniveau;
	}

	public void setIdLibelleniveau(int idLibelleniveau) {
		this.idLibelleniveau = idLibelleniveau;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTypeniveau() {
		return typeniveau;
	}

	public void setTypeniveau(int typeniveau) {
		this.typeniveau = typeniveau;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return libelle;
	}
}