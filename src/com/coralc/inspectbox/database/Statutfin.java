package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet DatesModifications
 */
@DatabaseTable(tableName = Statutfin.TABLE_NAME, daoClass = StatutfinDao.class)
public class Statutfin {

	public static final String TABLE_NAME = "Statutfin";
	public static final String PATH_FIELD_NAME = "NiveauPath";
	public static final String STATUTVALUE_FIELD_NAME = "StatutValue";
	
	// Les différents statuts possible sont :
	// on peut combiner les statut avec l'opérateur logique & pour avoir le
	// résultat de la combinaison
	public static final int VIDE = 0; // icone blanc
	public static final int TERMINE = 1; // icone vert
	public static final int EN_COURS = 2; // icone violet
	public static final int HS = 3; // icone noir
	public static final int DEFECT = 4;// icone jaune
	
	
	
	@DatabaseField(id = true, columnName = PATH_FIELD_NAME)
	private String niveauPath;
	@DatabaseField(columnName = STATUTVALUE_FIELD_NAME)
	private int statutValue;

	public Statutfin() {
		// needed by ormlite
	}

	public String getNiveauPath() {
		return niveauPath;
	}

	public void setNiveauPath(String niveauPath) {
		this.niveauPath = niveauPath;
	}

	public int getStatutValue() {
		return statutValue;
	}

	public void setStatutValue(int statutValue) {
		this.statutValue = statutValue;
	}

	@Override
	public String toString() {
		return niveauPath;
	}
}