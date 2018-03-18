package com.coralc.inspectbox.database;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les libellé niveaux
 */
public class LibelleNiveauDao extends BaseDaoImpl<LibelleNiveau, Integer> {

	protected LibelleNiveauDao(Class<LibelleNiveau> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public LibelleNiveauDao(ConnectionSource connectionSource,
			DatabaseTableConfig<LibelleNiveau> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(LibelleNiveau niveau) {
		try {
			return super.create(niveau);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	/*
	 * Retourne le premier libelle dont le num est = @num et le type de niveau =
	 * @type
	 */
	public LibelleNiveau queryWhereNumAndTypeEq(int num, int type) {
		try {
			return queryForFirst(queryBuilder().where()
					.eq(LibelleNiveau.NUM_FIELD_NAME, num)
					.and()
					.eq(LibelleNiveau.TYPENIVEAU_FIELD_NAME, type).prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}