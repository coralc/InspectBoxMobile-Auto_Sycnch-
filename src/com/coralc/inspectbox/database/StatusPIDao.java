package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les choix
 */
public class StatusPIDao extends BaseDaoImpl<StatusPI, Integer> {

	protected StatusPIDao(Class<StatusPI> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public StatusPIDao(ConnectionSource connectionSource,
			DatabaseTableConfig<StatusPI> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(StatusPI statusPI) {
		try {
			return super.create(statusPI);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public CreateOrUpdateStatus createOrUpdate(StatusPI statusPI) {
		try {
			return super.createOrUpdate(statusPI);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	@Override
	public List<StatusPI> queryForAll() {
		try {
			return super.queryForAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public StatusPI queryForId(Integer idTag) {
		try {
			return super.queryForId(idTag);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	/*
	 * Retourne le liste des choix ou idTypeReponse = @idTypeReponse
	 */
	
}