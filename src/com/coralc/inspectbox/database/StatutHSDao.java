package com.coralc.inspectbox.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class StatutHSDao extends BaseDaoImpl<StatutHS, Integer> {
	protected StatutHSDao(Class<StatutHS> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public StatutHSDao(ConnectionSource connectionSource, DatabaseTableConfig<StatutHS> tableConfig)
			throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(StatutHS statuths) {
		try {
			return super.create(statuths);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public CreateOrUpdateStatus createOrUpdate(StatutHS statut) {
		try {
			return super.createOrUpdate(statut);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	public void delete(int idniveau) {
		try {
			
			DeleteBuilder<StatutHS, Integer> db = deleteBuilder ();
			db.where().eq(StatutHS.PATH_FIELD_NAME, idniveau);
			db.delete();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public StatutHS queryForId(Integer idniveau) {
		try {
			return super.queryForId(idniveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
}
