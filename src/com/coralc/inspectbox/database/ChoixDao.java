package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les choix
 */
public class ChoixDao extends BaseDaoImpl<Choix, Integer> {

	protected ChoixDao(Class<Choix> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public ChoixDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Choix> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(Choix choix) {
		try {
			return super.create(choix);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	@Override
	public Choix queryForId(Integer idChoix) {
		try {
			return super.queryForId(idChoix);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	/*
	 * Retourne le liste des choix ou idTypeReponse = @idTypeReponse
	 */
	public List<Choix> queryWhereIdTypereponse(int idTypeReponse) {
		try {
			QueryBuilder<Choix, Integer> qb = queryBuilder();
			qb.where().eq(Choix.IDTYPEREPONSE_FIELD_NAME, idTypeReponse);
			qb.orderBy(Choix.TRI_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}