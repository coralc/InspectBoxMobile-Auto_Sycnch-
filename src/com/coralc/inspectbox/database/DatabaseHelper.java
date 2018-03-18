package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.coralc.inspectbox.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class utilisé pour la création et les update de la base.
 * Cette classe fournit également les DAO des autres classes
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// nom de la base de donnée
	private static final String DATABASE_NAME = "inspectboxsa.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 5;

	// l'objet DAO à utiliser pour accéder à la table Utilisateur
	private RuntimeExceptionDao<Utilisateur, Integer> utilisateurDao = null;
	
	// l'objet DAO à utiliser pour accéder à la table Inspection
	private RuntimeExceptionDao<Parametrage, Integer> parametrageDao = null;

	// l'objet DAO à utiliser pour accéder à la table paramétrage
	private InspectionDao inspectionDao = null;
	private StatusPIDao statusPIDao = null;

	// l'objet DAO à utiliser pour accéder à la table LibelleNiveau
	private LibelleNiveauDao libelleNiveauDao = null;

	// l'objet DAO à utiliser pour accéder à la table Niveau
	private NiveauDao niveauDao = null;

	private ClotureDao clotureDao = null;
	private NfcDao nfcDao = null;
	private HistoriqueDao historiqueDao = null;
	private StatutInacDao statutInacDao=null;
	// l'objet DAO à utiliser pour accéder à la table NiveauObjet
	private NiveauObjetDao niveauObjetDao = null;
	
	
	// l'objet DAO à utiliser pour accéder à la table TypeReponse
	private RuntimeExceptionDao<TypeReponse, Integer> typeReponseDao = null;

	
	private TeamDao teamDao = null;
	// l'objet DAO à utiliser pour accéder à la table Choix
	private ChoixDao choixDao = null;
	//private UtilisateurDao userDao = null;
	// l'objet DAO à utiliser pour accéder à la table Team
		
		// l'objet DAO à utiliser pour accéder à la table ObjetTeam
				private ObjeteamDao objeteamDao = null;

	// l'objet DAO à utiliser pour accéder à la table DatesModifications
	private RuntimeExceptionDao<DatesModifications, String> datesModificationDao = null;

	// l'objet DAO à utiliser pour accéder à la table Statut
	private StatutfinDao statutfinDao = null;
	
	private StatutHSDao statutHSDao = null;
	
	
	public DatabaseHelper(Context context) {
		// le fichier ormlite_config doit être regénéré par l'execustion de
		// DatabaseConfigUtil.java
		// à chaque modification d'une classe de mapping
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/**
	 * Appelé lorsque la base de donnée est créer pour la première fois
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Utilisateur.class);
			TableUtils.createTable(connectionSource, Inspection.class);
			TableUtils.createTable(connectionSource, LibelleNiveau.class);
			TableUtils.createTable(connectionSource, Niveau.class);
			TableUtils.createTable(connectionSource, NiveauObjet.class);
			TableUtils.createTable(connectionSource, Parametrage.class);
			TableUtils.createTable(connectionSource, TypeReponse.class);
			TableUtils.createTable(connectionSource, Choix.class);
			TableUtils.createTable(connectionSource, DatesModifications.class);
			TableUtils.createTable(connectionSource, Statutfin.class);
			TableUtils.createTable(connectionSource, StatutHS.class);
			TableUtils.createTable(connectionSource, Team.class);
			TableUtils.createTable(connectionSource, Objeteam.class);
			TableUtils.createTable(connectionSource, Cloture.class);
			TableUtils.createTable(connectionSource, Historique.class);
			TableUtils.createTable(connectionSource, StatutInac.class);
			TableUtils.createTable(connectionSource, Nfc.class);
			
			TableUtils.createTable(connectionSource, StatusPI.class);
			
			// on crée le paramétrage par défaut
			Parametrage param = new Parametrage();
			param.setUrlClient("http://bayernoil.traq360.biz");
			param.setCodeClient("");
			param.setMotdepasseClient("");
			param.setNbTerminal("");
			param.setPasseappli("every4u");
			getParametrageDao().create(param);

		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Appelé lorsque la base de donnée est upgradée vers une version plus
	 * récente
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Utilisateur.class, true);
			TableUtils.dropTable(connectionSource, Inspection.class, true);
			TableUtils.dropTable(connectionSource, LibelleNiveau.class, true);
			TableUtils.dropTable(connectionSource, Niveau.class, true);
			TableUtils.dropTable(connectionSource, NiveauObjet.class, true);
			TableUtils.dropTable(connectionSource, Parametrage.class, true);
			TableUtils.dropTable(connectionSource, TypeReponse.class, true);
			TableUtils.dropTable(connectionSource, Choix.class, true);
			TableUtils.dropTable(connectionSource, DatesModifications.class, true);
			TableUtils.dropTable(connectionSource, Statutfin.class, true);
			TableUtils.dropTable(connectionSource, StatutHS.class, true);
			TableUtils.dropTable(connectionSource, Team.class, true);
			TableUtils.dropTable(connectionSource, Objeteam.class, true);
			TableUtils.dropTable(connectionSource, Cloture.class, true);
			TableUtils.dropTable(connectionSource, Historique.class, true);
			TableUtils.dropTable(connectionSource, StatutInac.class, true);
			TableUtils.dropTable(connectionSource, Nfc.class, true);
		
			TableUtils.dropTable(connectionSource, StatusPI.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<Utilisateur, Integer> getUtilisateurDao() {
		
			utilisateurDao = getRuntimeExceptionDao(Utilisateur.class);
			
		return utilisateurDao;
	}


	public RuntimeExceptionDao<Parametrage, Integer> getParametrageDao() {
		if (parametrageDao == null) {
			parametrageDao = getRuntimeExceptionDao(Parametrage.class);
		}
		return parametrageDao;
	}

	public InspectionDao getInspectionDao() {
		if (inspectionDao == null) {
			try {
				inspectionDao = getDao(Inspection.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return inspectionDao;
	}
	public StatusPIDao getStatusPIDao() {
		if (statusPIDao == null) {
			try {
				statusPIDao = getDao(StatusPI.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return statusPIDao;
	}
	public LibelleNiveauDao getLibelleNiveauDao() {
		if (libelleNiveauDao == null) {
			try {
				libelleNiveauDao = getDao(LibelleNiveau.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return libelleNiveauDao;
	}

	public NiveauDao getNiveauDao() {
		if (niveauDao == null) {
			try {
				niveauDao = getDao(Niveau.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return niveauDao;
	}

	
	public ClotureDao getClotureDao() {
		if (clotureDao == null) {
			try {
				clotureDao = getDao(Cloture.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return clotureDao;
	}
	
	public HistoriqueDao getHistoriqueDao() {
		if (historiqueDao == null) {
			try {
				historiqueDao = getDao(Historique.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return historiqueDao;
	}
	public StatutInacDao getStatutInacDao() {
		if (statutInacDao == null) {
			try {
				statutInacDao = getDao(StatutInac.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return statutInacDao;
	}
	
	public NiveauObjetDao getNiveauObjetDao() {
		if (niveauObjetDao == null) {
			try {
				niveauObjetDao = getDao(NiveauObjet.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return niveauObjetDao;
	}

	

	public RuntimeExceptionDao<TypeReponse, Integer> getTypeReponseDao() {
		if (typeReponseDao == null) {
			typeReponseDao = getRuntimeExceptionDao(TypeReponse.class);
		}
		return typeReponseDao;
	}

	
	public TeamDao getTeamDao() {
		
		if (teamDao == null) {
			try {
				teamDao = getDao(Team.class);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
				
			}
		}
		
		return teamDao;
	}
	public ChoixDao getChoixDao() {
		if (choixDao == null) {
			try {
				choixDao = getDao(Choix.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return choixDao;
	}
	
	
	
	public NfcDao getNfcDao() {
		if (nfcDao == null) {
			try {
				nfcDao = getDao(Nfc.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return nfcDao;
	}
	
	

	
	public ObjeteamDao getObjeteamDao() {
		if (objeteamDao == null) {
			try {
				objeteamDao = getDao(Objeteam.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return objeteamDao;
	}
	public RuntimeExceptionDao<DatesModifications, String> getDatesModificationsDao() {
		if (datesModificationDao == null) {
			datesModificationDao = getRuntimeExceptionDao(DatesModifications.class);
		}
		return datesModificationDao;
	}

	public StatutfinDao getStatutfinDao() {
		if (statutfinDao == null) {
			try {
				statutfinDao = getDao(Statutfin.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return statutfinDao;
	}
	public StatutHSDao getStatutHSDao() {
		if (statutHSDao == null) {
			try {
				statutHSDao = getDao(StatutHS.class);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return statutHSDao;
	}

	/*
	 * cette méthode génère tous les statuts
	 * par défaut la valeur sera Statut.VIDE
	 */
	public void generateAllStatuts() {
		try {
			TableUtils.dropTable(getConnectionSource(), Statutfin.class, true);
			TableUtils.createTable(getConnectionSource(), Statutfin.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		generateAllStatutsRec(0, "");
	}

	/*
	 * Génération récursive des statuts
	 */
	private void generateAllStatutsRec(int idNiveau, String mapPath) {

		List<Niveau> niveaux = getNiveauDao().queryWhereIdParentEqOrderByTri(idNiveau);

		for (Niveau n : niveaux) {
			if (n.getIdNiveauObjet() != 0) {
				generateAllStatutsRec(n.getIdNiveau(), n.getIdNiveauObjet(),
						mapPath + "N" + n.getIdNiveau() + "O" + n.getIdNiveauObjet());
			} else {
				generateAllStatutsRec(n.getIdNiveau(), mapPath + "N" + n.getIdNiveau());
			}
		}
	}

	/*
	 * Génération récursive des statuts
	 */
	private void generateAllStatutsRec(int idNiveau, int idNiveauObjet, String mapPath) {
		List<NiveauObjet> niveauxObjet = getNiveauObjetDao().queryWhereIdObjetParentEqOrderByTri(
				idNiveauObjet);

		for (NiveauObjet no : niveauxObjet) {
			if (no.getIdTypereponse() != 0) {
				// création du statut
				Statutfin statut = new Statutfin();
				statut.setNiveauPath(mapPath);
				statut.setStatutValue(Statutfin.VIDE);
				getStatutfinDao().createOrUpdate(statut);
			} else {
				generateAllStatutsRec(idNiveau, no.getIdNiveauobjet(), mapPath + "O" + no.getIdNiveauobjet());
			}
		}
	}

	/**
	 * Ferme les connexions et vide le cache des DAO
	 */
	@Override
	public void close() {
		super.close();
		utilisateurDao = null;
		parametrageDao = null;
		inspectionDao = null;
		libelleNiveauDao = null;
		niveauDao = null;
		niveauObjetDao = null;
		statusPIDao=null;
		typeReponseDao = null;
		
		datesModificationDao = null;
		statutfinDao = null;
		statutHSDao = null;
		teamDao=null;
		objeteamDao=null;
		clotureDao=null;
		historiqueDao=null;
		statutInacDao=null;
		nfcDao=null;
	}
}
