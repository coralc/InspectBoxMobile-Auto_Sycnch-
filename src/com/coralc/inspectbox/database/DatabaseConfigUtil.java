package com.coralc.inspectbox.database;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

	private static final Class<?>[] classes = new Class[] {
	    Utilisateur.class,
	    Inspection.class,
	    LibelleNiveau.class,
	    Niveau.class,
	    NiveauObjet.class,
	    Parametrage.class,
	    TypeReponse.class,
	    Choix.class,
	    Team.class,
	    Objeteam.class,
	    Statutfin.class,
	    StatutHS.class,
	    DatesModifications.class,
	    Historique.class,
	    Cloture.class,
	    StatutInac.class,
	    Nfc.class,
	   
	    StatusPI.class
	  };
	
	public static void main(String[] args) throws SQLException, IOException {
		writeConfigFile("ormlite_config.txt", classes);
	}
}
