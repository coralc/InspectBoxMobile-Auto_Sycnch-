package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class StatutInacDao extends BaseDaoImpl<StatutInac, Integer> {
	protected StatutInacDao(Class<StatutInac> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public StatutInacDao(ConnectionSource connectionSource, DatabaseTableConfig<StatutInac> tableConfig)
			throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(StatutInac statutInac) {
		try {
			return super.create(statutInac);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public List<StatutInac> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public CreateOrUpdateStatus createOrUpdate(StatutInac statutInac) {
		try {
			return super.createOrUpdate(statutInac);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public StatutInac queryForId(Integer idniveau) {
		try {
			return super.queryForId(idniveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	public List<StatutInac> queryWhereInacc() {
		try {
			QueryBuilder<StatutInac, Integer> qb = queryBuilder();
			qb.where().eq(StatutInac.STATUTVALUE_FIELD_NAME, true);
			
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<StatutInac> queryWhereDateafter() {
		try {
			QueryBuilder<StatutInac, Integer> qb = queryBuilder();
			qb.where().eq(StatutInac.STATUTVALUE_FIELD_NAME, true);
			qb .orderBy(StatutInac.DATE_FIELD_NAME, true);
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(int idniveau) {
		try {
			
			DeleteBuilder<StatutInac, Integer> db = deleteBuilder ();
			db.where().eq(StatutInac.PATH_FIELD_NAME, idniveau);
			db.delete();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}


