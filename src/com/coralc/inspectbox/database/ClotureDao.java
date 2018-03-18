package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class ClotureDao extends BaseDaoImpl<Cloture, Integer> {
	
	protected ClotureDao(Class<Cloture> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public ClotureDao(ConnectionSource connectionSource, DatabaseTableConfig<Cloture> tableConfig)
			throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(Cloture cloture) {
		try {
			return super.create(cloture);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public List<Cloture> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CreateOrUpdateStatus createOrUpdate(Cloture cloture) {
		try {
			return super.createOrUpdate(cloture);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	
	public Cloture queryWhereniveau(int idniveau) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Cloture.PATH_FIELD_NAME, idniveau).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}