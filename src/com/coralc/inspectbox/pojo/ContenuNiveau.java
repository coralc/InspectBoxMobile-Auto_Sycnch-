package com.coralc.inspectbox.pojo;

import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.R;

public class ContenuNiveau {
	private int idTypeReponse;
	private String libelle;
	private int idNiveauParent;
	private int idNiveauObjet;
	private int tri;
	private int icone;
	private int idNiveau;
private int level;

	public ContenuNiveau(Niveau n) {
		
		setIdTypeReponse(n.getIdTypeReponse());
		
			setLibelle(n.getLibelle());
		setIdNiveau(n.getIdNiveau());
		setIdNiveauObjet(n.getIdNiveauObjet());
		setTri(n.getTri());
		setLevel(n.getLevel());
		setIdNiveauParent(n.getIdNiveauParent());
		
		icone = R.drawable.gris;
		
	}
	
	public ContenuNiveau(NiveauObjet n) {
		setIdTypeReponse(n.getIdTypereponse());
		setLibelle(n.getLibelle());
		setIdNiveauObjet(n.getIdNiveauobjet());
		setTri(n.getTri());
		
		icone = R.drawable.gris;
	}

	public int getIdTypeReponse() {
		return idTypeReponse;
	}

	public void setIdTypeReponse(int idTypeReponse) {
		this.idTypeReponse = idTypeReponse;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getIdNiveauParent() {
		return idNiveauParent;
	}

	public void setIdNiveauParent(int idNiveauParent) {
		this.idNiveauParent = idNiveauParent;
	}

	public int getIdNiveauObjet() {
		return idNiveauObjet;
	}

	public void setIdNiveauObjet(int idNiveauObjet) {
		this.idNiveauObjet = idNiveauObjet;
	}

	public int getTri() {
		return tri;
	}

	public void setTri(int tri) {
		this.tri = tri;
	}

	public int getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
	}

	public int getIcone() {
		return icone;
	}

	public void setIcone(int icone) {
		this.icone = icone;
	}

	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return libelle;
	}
}
