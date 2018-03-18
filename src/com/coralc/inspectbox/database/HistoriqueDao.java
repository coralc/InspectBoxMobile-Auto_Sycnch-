package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class HistoriqueDao extends BaseDaoImpl< Historique, Integer> {
	protected HistoriqueDao(Class<Historique> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public HistoriqueDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Historique> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(Historique historique) {
		try {
			return super.create(historique);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	};
	@Override
	public CreateOrUpdateStatus createOrUpdate(Historique historique) {
		try {
			return super.createOrUpdate(historique);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	};

	public List<Historique> queryWhereIdObjet(int idObjet) {
		try {
			QueryBuilder<Historique, Integer> qb = queryBuilder();
			qb.where().eq(Historique.OBJET_FIELD_NAME, idObjet);
			qb.orderBy(Historique.TIME_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
