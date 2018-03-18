package com.coralc.inspectbox.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Objet Niveau
 */
@DatabaseTable(tableName = Niveau.NIVEAU_TABLE_NAME, daoClass = NiveauDao.class)
public class Niveau {
	public static final String NIVEAU_TABLE_NAME = "Niveau";
	
	public static final String IDNIVEAU_FIELD_NAME = "IdNiveau";
	public static final String IDTYPEREPONSE_FIELD_NAME = "IdTypeReponse";
	public static final String LIBELLE_FIELD_NAME = "Libelle";
	public static final String IDNIVEAUPARENT_FIELD_NAME = "IdNiveauParent";
	public static final String IDNIVEAUOBJET_FIELD_NAME = "IdNiveauObjet";
	public static final String TRI_FIELD_NAME = "Tri";
	public static final String CODEBARRE_FIELD_NAME = "CodeBarre";
	public static final String AREATAG_FIELD_NAME = "AreaTag";
	public static final String LEVEL_FIELD_NAME = "Level";
	@DatabaseField(id = true, columnName = IDNIVEAU_FIELD_NAME)
	private int idNiveau;
	@DatabaseField(columnName = IDTYPEREPONSE_FIELD_NAME)
	private int idTypeReponse;
	@DatabaseField(columnName = LIBELLE_FIELD_NAME)
	private String libelle;
	@DatabaseField(columnName = IDNIVEAUPARENT_FIELD_NAME, canBeNull = true)
	private int idNiveauParent;
	@DatabaseField(columnName = IDNIVEAUOBJET_FIELD_NAME)
	private int idNiveauObjet;
	@DatabaseField(columnName = TRI_FIELD_NAME)
	private int tri;
	@DatabaseField(columnName = CODEBARRE_FIELD_NAME, canBeNull = true)
	private String codebarre;
	@DatabaseField(columnName = AREATAG_FIELD_NAME, canBeNull = true)
	private String areaTag;
	@DatabaseField(columnName = LEVEL_FIELD_NAME)
	private int level;
	public Niveau() {
		// needed by ormlite
	}
	
	public Niveau(int idNiveau) {
		setIdNiveau(idNiveau);
	}

	public int getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(int idNiveau) {
		this.idNiveau = idNiveau;
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
	
	public String getCodebarre() {
		return codebarre;
	}

	public void setCodebarre(String codebarre) {
		this.codebarre = codebarre;
	}

	public String getAreaTag() {
		return areaTag;
	}

	public void setAreaTag(String areaTag) {
		this.areaTag = areaTag;
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