package com.coralc.inspectbox.pojo;

import com.coralc.inspectbox.database.Historique;

public class ContenuHisto {
	
	
	private String datet;
	private int idObjet;
	private String valeur;
	public ContenuHisto(Historique h) {
		setValeur(h.getValeur());
		setDatet(h.getDateHist());
		setIdObjet(h.getIdObjet());
		
	}
	


	public String getDatet() {
		return datet;
	}


	public void setDatet(String datet) {
		this.datet = datet;
	}


	public int getIdObjet() {
		return idObjet;
	}


	public void setIdObjet(int idObjet) {
		this.idObjet = idObjet;
	}


	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	
}
