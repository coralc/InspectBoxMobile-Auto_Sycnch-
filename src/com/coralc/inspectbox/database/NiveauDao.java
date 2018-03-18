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
public class NiveauDao extends BaseDaoImpl<Niveau, Integer> {

	protected NiveauDao(Class<Niveau> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public NiveauDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Niveau> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(Niveau niveau) {
		try {
			return super.create(niveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	@Override
	public Niveau queryForId(Integer idNiveau) {
		try {
			return super.queryForId(idNiveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public List<Niveau> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public CreateOrUpdateStatus createOrUpdate(Niveau niveau) {
		try {
			return super.createOrUpdate(niveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	/*
	 * Retourne le liste des niveaux ou idNiveauParent = @idParent
	 * Trié par ordre de Tri
	 */
	public List<Niveau> queryWhereIdParentEqOrderByTri(int idParent) {
		try {
			QueryBuilder<Niveau, Integer> qb = queryBuilder();
			qb.where().eq(Niveau.IDNIVEAUPARENT_FIELD_NAME, idParent);
			qb.orderBy(Niveau.TRI_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Niveau queryWhereidObjet(Integer idNiveauobjet) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(Niveau.IDNIVEAUOBJET_FIELD_NAME, idNiveauobjet).prepare());
					
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Niveau queryWherecodebarre(String codebarre) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(Niveau.CODEBARRE_FIELD_NAME, codebarre).prepare());
					
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Niveau queryWhereNextTri(Integer idniveaup, Integer tri ) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(Niveau.IDNIVEAUPARENT_FIELD_NAME, idniveaup).and()
					.eq(Niveau.TRI_FIELD_NAME, tri+1).prepare());
					
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}