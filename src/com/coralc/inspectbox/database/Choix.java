package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet choix
 */
@DatabaseTable(tableName = "Choix", daoClass = ChoixDao.class)
public class Choix {

	public static final String IDCHOIX_FIELD_NAME = "IdChoix";
	public static final String VALEUR_FIELD_NAME = "Valeur";
	public static final String NONCONFORME_FIELD_NAME = "NonConforme";
	public static final String IDTYPEREPONSE_FIELD_NAME = "IdTypeReponse";
	public static final String TRI_FIELD_NAME = "Tri";
	
	@DatabaseField(id = true, columnName = IDCHOIX_FIELD_NAME)
	private int idChoix;
	@DatabaseField(columnName = VALEUR_FIELD_NAME, canBeNull = true)
	private String valeur;
	@DatabaseField(columnName = NONCONFORME_FIELD_NAME)
	private boolean nonConforme;
	@DatabaseField(columnName = IDTYPEREPONSE_FIELD_NAME)
	private int idTypeReponse;
	@DatabaseField(columnName = TRI_FIELD_NAME)
	private int tri;
	
	public Choix() {
		// needed by ormlite
	}
	
	public Choix(int idChoix) {
		setIdChoix(idChoix);
	}
	
	public int getIdChoix() {
		return idChoix;
	}



	public void setIdChoix(int idChoix) {
		this.idChoix = idChoix;
	}



	public String getValeur() {
		return valeur;
	}



	public void setValeur(String valeur) {
		this.valeur = valeur;
	}



	public boolean isNonConforme() {
		return nonConforme;
	}



	public void setNonConforme(boolean nonConforme) {
		this.nonConforme = nonConforme;
	}



	public int getIdTypeReponse() {
		return idTypeReponse;
	}



	public void setIdTypeReponse(int idTypeReponse) {
		this.idTypeReponse = idTypeReponse;
	}



	public int getTri() {
		return tri;
	}



	public void setTri(int tri) {
		this.tri = tri;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			return ((Choix) o).getIdChoix() == this.getIdChoix();
		}
		return false;
	}

	@Override
	public String toString() {
		return valeur == null ? "" : valeur;
	}
}