package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Historique", daoClass = HistoriqueDao.class)
public class Historique {
	
	public static final String IDHIS_FIELD_NAME = "Idhis";
	public static final String VALEUR_FIELD_NAME = "Valeur";
	public static final String OBJET_FIELD_NAME = "IdObjet";
	public static final String TIME_FIELD_NAME = "DateHist";
	
	@DatabaseField(generatedId = true, columnName = IDHIS_FIELD_NAME)
	private int idhis;
	@DatabaseField(columnName = VALEUR_FIELD_NAME, canBeNull = false)
	private String valeur;
	@DatabaseField(columnName = OBJET_FIELD_NAME ,canBeNull = false)
	private Integer idObjet;
	@DatabaseField(columnName = TIME_FIELD_NAME ,canBeNull = false)
	private String dateHist;
	
	public int getIdhis() {
		return idhis;
	}
	public void setIdhis(int idhis) {
		this.idhis = idhis;
	}
	
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	public Integer getIdObjet() {
		return idObjet;
	}
	public void setIdObjet(Integer idObjet) {
		this.idObjet = idObjet;
	}
	public String getDateHist() {
		return dateHist;
	}
	public void setDateHist(String dateHist) {
		this.dateHist = dateHist;
	}
	
}
