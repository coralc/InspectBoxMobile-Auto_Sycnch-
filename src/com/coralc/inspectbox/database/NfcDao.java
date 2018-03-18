package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class NfcDao extends BaseDaoImpl<Nfc, Integer> {
	
	protected NfcDao(Class<Nfc> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public NfcDao(ConnectionSource connectionSource, DatabaseTableConfig<Nfc> tableConfig)
			throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(Nfc nfc) {
		try {
			return super.create(nfc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public List<Nfc> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CreateOrUpdateStatus createOrUpdate(Nfc nfc) {
		try {
			return super.createOrUpdate(nfc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	public Nfc queryWhereexist(int idNiveau, String type) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Nfc.PATH_FIELD_NAME, idNiveau).prepare()
					);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Nfc queryWhereniveau(int idniveau) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Nfc.PATH_FIELD_NAME, idniveau).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Nfc queryWheretag(String tag) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Nfc.TAG_FIELD_NAME, tag).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}