package com.coralc.inspectbox.database;

import java.sql.SQLException;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class TypeReponseDao extends BaseDaoImpl<TypeReponse, Integer> {

	protected TypeReponseDao(Class<TypeReponse> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public TypeReponseDao(ConnectionSource connectionSource,
			DatabaseTableConfig<TypeReponse> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(TypeReponse choix) {
		try {
			return super.create(choix);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	@Override
	public TypeReponse queryForId(Integer idtyp) {
		try {
			return super.queryForId(idtyp);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	
}
