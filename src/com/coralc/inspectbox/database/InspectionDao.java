package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les inspections
 */
public class InspectionDao extends BaseDaoImpl<Inspection, Integer> {

	protected InspectionDao(Class<Inspection> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public InspectionDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Inspection> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(Inspection inspection) {
		try {
			return super.create(inspection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public CreateOrUpdateStatus createOrUpdate(Inspection inspection) {
		try {
			return super.createOrUpdate(inspection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public List<Inspection> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Retourne le premier libelle dont le num est = @num et le type de niveau =
	 * 
	 * @type
	 */
	public Inspection queryWhereIdNiveauEtObjet(int idNiveau, int idNiveauObjet) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(Inspection.IDNIVEAU_FIELD_NAME, idNiveau).and()
					.eq(Inspection.IDNIVEAUOBJET_FIELD_NAME, idNiveauObjet).prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Inspection> queryWhereIdNiveaubat(int idNiveaubat) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUBAT_FIELD_NAME, idNiveaubat);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveaubatByDate(int idNiveaubat) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUBAT_FIELD_NAME, idNiveaubat);
			qb .orderBy(Inspection.DATEINFORMATION_FIELD_NAME, false);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveaubatByDateF(int idNiveaubat) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUBAT_FIELD_NAME, idNiveaubat);
			qb .orderBy(Inspection.DATEINFORMATION_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveaubatisdefect(int idNiveaubat) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUBAT_FIELD_NAME, idNiveaubat)
			.and()
				.eq(Inspection.DEFECT_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveaubatislimit(int idNiveaubat) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUBAT_FIELD_NAME, idNiveaubat)
			.and()
				.eq(Inspection.LIMIT_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveau(int idNiveau) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAU_FIELD_NAME, idNiveau);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Inspection queryWhereIdNiveauONE(int idNiveau) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Inspection.IDNIVEAU_FIELD_NAME, idNiveau).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveauisdefect(int idNiveau) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAU_FIELD_NAME, idNiveau)
			.and()
				.eq(Inspection.DEFECT_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdNiveauislimit(int idNiveau) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAU_FIELD_NAME, idNiveau)
			.and()
				.eq(Inspection.LIMIT_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdObjet(int idObjet) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUOBJET_FIELD_NAME, idObjet);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Inspection queryWhereIdObjetunik(int idObjet) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Inspection.IDNIVEAUOBJET_FIELD_NAME, idObjet).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Inspection> queryWhereIdObjetisdefect(int idObjet) {
		try {
			QueryBuilder<Inspection, Integer> qb = queryBuilder();
			qb.where().eq(Inspection.IDNIVEAUOBJET_FIELD_NAME, idObjet)
			.and()
				.eq(Inspection.DEFECT_FIELD_NAME, true);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * Compte le nombre d'inspection ayant le même niveau
	 */
	public long queryCountWithSameLevel(int idNiveauObjetParent, int niveauId) {
		try {
			if (idNiveauObjetParent == 0) {
				return queryRawValue("SELECT count(*) FROM %s WHERE %s = ?",
						Inspection.INSPECTION_TABLE_NAME, Inspection.IDNIVEAU_FIELD_NAME,
						String.valueOf(niveauId));
			} else {
				return queryRawValue(
						String.format(
								"SELECT COUNT(*) FROM %s AS i INNER JOIN %s AS no ON i.%s = no.%s WHERE i.%s = ? AND no.%s = ?",
								Inspection.INSPECTION_TABLE_NAME,
								NiveauObjet.NIVEAUOBJET_TABLE_NAME,
								Inspection.IDNIVEAUOBJET_FIELD_NAME,
								NiveauObjet.IDNIVEAUOBJET_FIELD_NAME,
								Inspection.IDNIVEAU_FIELD_NAME,
								NiveauObjet.IDNIVEAUOBJETPARENT_FIELD_NAME),
						String.valueOf(niveauId), String.valueOf(idNiveauObjetParent));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}