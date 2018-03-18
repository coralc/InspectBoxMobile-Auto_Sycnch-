package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les teams
 */
public class TeamDao extends BaseDaoImpl<Team, Integer> {

	protected TeamDao(Class<Team> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public TeamDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Team> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(Team team) {
		try {
			return super.create(team);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	
	public Team queryForId(Integer idTeam) {
		try {
			return super.queryForId(idTeam);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	public Team queryWheredaywork(String day, int idUser) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Team.DAYWORK_FIELD_NAME, day).and()
					.eq(Team.USER_FIELD_NAME, idUser).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Team> queryWheredayworklist (String day, int idUser) {
		try {
			QueryBuilder<Team, Integer> qb = queryBuilder();
			qb.where().eq(Team.DAYWORK_FIELD_NAME, day)
			.and()
			.eq(Team.USER_FIELD_NAME, idUser);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Team> queryWhereAllteam () {
		try {
			QueryBuilder<Team, Integer> qb = queryBuilder();
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}