package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les status
 */
public class StatutfinDao extends BaseDaoImpl<Statutfin, Integer> {

	protected StatutfinDao(Class<Statutfin> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}

	public StatutfinDao(ConnectionSource connectionSource, DatabaseTableConfig<Statutfin> tableConfig)
			throws SQLException {
		super(connectionSource, tableConfig);
	}

	@Override
	public int create(Statutfin statut) {
		try {
			return super.create(statut);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	@Override
	public CreateOrUpdateStatus createOrUpdate(Statutfin statut) {
		try {
			return super.createOrUpdate(statut);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	/*
	 * Détermine le statut à partir d'un mapPath Il s'agit de la combinaison par
	 * l'opérateur logique & de tous les status comprenant le même mapPath
	 */
	public int getStatut(String mapPath) {
		try {
			List<Statutfin> statuts = query(queryBuilder().where()
					.like(Statutfin.PATH_FIELD_NAME, mapPath + "%").prepare());
			if (statuts.size() > 0) {
				int returnVal = Statutfin.VIDE | Statutfin.EN_COURS | Statutfin.TERMINE;
				for (Statutfin s : statuts) {
					returnVal &= s.getStatutValue();
				}
				return returnVal;
			} else {
				return Statutfin.VIDE;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Met à jour tous les statuts avec la valeur @statutValue
	 */
	public void updateAllStatut(int statutValue) {
		try {
			UpdateBuilder<Statutfin, Integer> updateBuilder = updateBuilder();
			updateBuilder.updateColumnValue(Statutfin.STATUTVALUE_FIELD_NAME, statutValue);
			updateBuilder.update();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
}