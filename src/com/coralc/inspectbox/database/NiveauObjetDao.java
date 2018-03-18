package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les niveaux
 */
public class NiveauObjetDao extends BaseDaoImpl<NiveauObjet, Integer> {

	protected NiveauObjetDao(Class<NiveauObjet> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public NiveauObjetDao(ConnectionSource connectionSource,
			DatabaseTableConfig<NiveauObjet> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(NiveauObjet niveauObjet) {
		try {
			return super.create(niveauObjet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public NiveauObjet queryForId(Integer idNiveauObjet) {
		try {
			return super.queryForId(idNiveauObjet);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public CreateOrUpdateStatus createOrUpdate(NiveauObjet niveauobj) {
		try {
			return super.createOrUpdate(niveauobj);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	/*
	 * Retourne le liste des niveaux objet ou idNiveauObjetParent =
	 * 
	 * @idObjetParent Trié par ordre de Tri
	 */
	public List<NiveauObjet> queryWhereIdObjetParentEqOrderByTri(int idObjetParent) {
		try {
			QueryBuilder<NiveauObjet, Integer> qb = queryBuilder();
			qb.where().eq(NiveauObjet.IDNIVEAUOBJETPARENT_FIELD_NAME, idObjetParent);
			qb.orderBy(NiveauObjet.TRI_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<NiveauObjet> queryWhereIdObjetOrderByTri(int idObjetParent) {
		try {
			QueryBuilder<NiveauObjet, Integer> qb = queryBuilder();
			qb.where().eq(NiveauObjet.IDNIVEAUOBJET_FIELD_NAME, idObjetParent);
			qb.orderBy(NiveauObjet.TRI_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/*
	 * Compte le nombre de niveau objet ayant le même niveau
	 */
	public long queryCountWithSameLevel(int idNiveauObjetParent, int niveauId) {
		try {
			if (idNiveauObjetParent == 0) {
				return queryRawValue(
						String.format(
								"SELECT count(*) FROM %s AS no INNER JOIN %s AS n ON n.%s = no.%s WHERE n.%s = ?",
								NiveauObjet.NIVEAUOBJET_TABLE_NAME, Niveau.NIVEAU_TABLE_NAME,
								Niveau.IDNIVEAUOBJET_FIELD_NAME,
								NiveauObjet.IDNIVEAUOBJET_FIELD_NAME, Niveau.IDNIVEAU_FIELD_NAME),
						String.valueOf(niveauId));
			} else {
				return queryRawValue(String.format("SELECT COUNT(*) FROM %s WHERE %s = ?",
						NiveauObjet.NIVEAUOBJET_TABLE_NAME,
						NiveauObjet.IDNIVEAUOBJETPARENT_FIELD_NAME),
						String.valueOf(idNiveauObjetParent));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Retourne le prochain niveau objet de la liste ou null
	 */
	public NiveauObjet queryNextNiveauObjet(NiveauObjet no) {
		if (no.getIdNiveauobjetparent() == 0)
			return null;
		try {
			QueryBuilder<NiveauObjet, Integer> qb = queryBuilder();
			qb.where().eq(NiveauObjet.IDNIVEAUOBJETPARENT_FIELD_NAME, no.getIdNiveauobjetparent()).and()
					.gt(NiveauObjet.TRI_FIELD_NAME, no.getTri());
			qb.orderBy(NiveauObjet.TRI_FIELD_NAME, true);
			return queryForFirst(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public NiveauObjet queryNextNiveauObjet2(NiveauObjet no) {
		
		try {
			
			QueryBuilder<NiveauObjet, Integer> qb = queryBuilder();
			qb.where().gt(NiveauObjet.IDNIVEAUOBJET_FIELD_NAME , no.getIdNiveauobjet());
			qb.orderBy(NiveauObjet.IDNIVEAUOBJET_FIELD_NAME, true);
			return queryForFirst(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public NiveauObjet queryWherecodebarre(String codebarre) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(NiveauObjet.CODEBARRE_FIELD_NAME, codebarre).prepare());
					
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}