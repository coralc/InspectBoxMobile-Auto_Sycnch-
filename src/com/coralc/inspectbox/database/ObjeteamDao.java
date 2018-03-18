package com.coralc.inspectbox.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

/**
 * Class Dao personalisée pour les choix
 */
public class ObjeteamDao extends BaseDaoImpl<Objeteam, Integer> {

	protected ObjeteamDao(Class<Objeteam> dataClass) throws SQLException {
		super(dataClass);
		// TODO Auto-generated constructor stub
	}
	
	public ObjeteamDao(ConnectionSource connectionSource,
			DatabaseTableConfig<Objeteam> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	@Override
	public int create(Objeteam objetteam) {
		try {
			return super.create(objetteam);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};
	
	@Override
	public Objeteam queryForId(Integer idField) {
		try {
			return super.queryForId(idField);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	};

	
	 
	 
	public List<Objeteam> queryWhereidTime(int idUser) {
		try {
			QueryBuilder<Objeteam, Integer> qb = queryBuilder();
			qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser);
			
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	// liste objet par equipe et par jour
	public List<Objeteam> queryWhereDay(int idUser, int day ) {
		try {
			QueryBuilder<Objeteam, Integer> qb = queryBuilder();
			
			if (day==1)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.LUN_FIELD_NAME, true);
			}
			if (day==2)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.MAR_FIELD_NAME, true);
			}
			if (day==3)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.MER_FIELD_NAME, true);
			}
			if (day==4)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.JEU_FIELD_NAME, true);
			}
			if (day==5)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.VEN_FIELD_NAME, true);
			}
			if (day==6)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.SAM_FIELD_NAME, true);
			}
			if (day==7)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.DIM_FIELD_NAME, true);
			}
			if (day==0)
			{qb.where().eq(Objeteam.TEAM_FIELD_NAME, idUser)
				.and()
				.eq(Objeteam.DIM_FIELD_NAME, true);
			}
			return query(qb.prepare());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Objeteam queryWhereDaybyobjtbyuser(int idUser, int day ,int objet ) {
		try {
			
			if (day==1)
			{	return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.LUN_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==2)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.MAR_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==3)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.MER_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==4)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.JEU_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==5)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.VEN_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==6)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.SAM_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==7)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.DIM_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else if (day==0)
			{return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, objet).and()
					.eq(Objeteam.DIM_FIELD_NAME, true).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			}
			else
				return null;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public Objeteam queryWhereobjet(int idObjet, int idUser) {
		try {
			
			return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, idObjet).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).prepare());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Objeteam queryWhereobjetday(int idObjet, int idUser, int day) {
		try {
			String d="";
		
			if (day==1)
				d=Objeteam.LUN_FIELD_NAME;
			if (day==2)
				d=Objeteam.MAR_FIELD_NAME;
			if (day==3)
				d=Objeteam.MER_FIELD_NAME;
			if (day==4)
				d=Objeteam.JEU_FIELD_NAME;
			if (day==5)
				d=Objeteam.VEN_FIELD_NAME;
			if (day==6)
				d=Objeteam.SAM_FIELD_NAME;
			if (day==7)
				d=Objeteam.DIM_FIELD_NAME;
			if (day==0)
				d=Objeteam.DIM_FIELD_NAME;
			if (d.equals(""))
			return null;
			else
			return queryForFirst(queryBuilder().where()
					.eq(Objeteam.OBJET_FIELD_NAME, idObjet).and()
					.eq(Objeteam.TEAM_FIELD_NAME, idUser).and()
					.eq(d, true).prepare());
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
	}
	
}