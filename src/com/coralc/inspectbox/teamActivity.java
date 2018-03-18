package com.coralc.inspectbox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.Team;
import com.coralc.inspectbox.database.Utilisateur;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class teamActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	// Widgets
	
	private Button btnCancel;
	private Button btnLogin;
	
	private ListView lVTeams;
	// Variables
	private int userId;
	private static String BUNDLE_USERID = "userId";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.team);
		
		if(savedInstanceState != null) {
			userId = savedInstanceState.getInt(teamActivity.BUNDLE_USERID);
		}
		// Initalisation des widgets
				
				
		btnLogin = (Button) findViewById(R.id.btnLoginteam);
		lVTeams = (ListView) findViewById(R.id.lVTeam);
		btnCancel = (Button) findViewById(R.id.btnCancelteam);
		
		// Binding des évènements
		lVTeams.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Utilisateur c = (Utilisateur) parent.getItemAtPosition(position);
		
						//Utilisateur user = (Utilisateur) parent.getItemAtPosition(pos);
						userId = c.getIdUtilisateur();
						}

					
				});
		
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
				
				
				 
				if ((user != null )&&(verifTeamTime()))
						{
					
					// on sauvegarde l'id de l'utilisateur loggé
					Parametrage param = getHelper().getParametrageDao().queryForId(1);
					param.setIdUtilisateur(user.getIdUtilisateur());
					getHelper().getParametrageDao().update(param);
					Intent intent = new Intent(teamActivity.this, NiveauActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("userId", userId);
					
					intent.putExtras(bundle);
					startActivity(intent);
				} 
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Binding des données
		
bindData();
	}
	
	private Boolean verifTeamTime()
	{
		Date maDate = new Date(); 
	Boolean intime=false;
	Boolean stop=false;
		int day=maDate.getDay();
		if (day==7)
			day=0;
		int hour=maDate.getHours();
		
		int mn=maDate.getMinutes();
		
		
		// chercher le jour de travail 
		
		//Team team = getHelper().getTeamDao().queryWheredaywork(Integer.toString(day), userId);
		List <Team> teaml = getHelper().getTeamDao().queryWheredayworklist(Integer.toString(day), userId);
		if (teaml.size() !=0)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Date datebeg;
			Date dateend;
			Date actuelle;
			for (Team team : teaml) {
				
			if (stop==false)
			
			
			try {
				 actuelle=sdf.parse(Integer.toString(hour)+":"+Integer.toString(mn));
				datebeg = sdf.parse(team.getBeginHour());
				dateend=sdf.parse(team.getEndHour());
				if (actuelle.before(dateend)&& actuelle.after(datebeg))
				{
					//good
					intime=true;
				stop=true;
				}
				else
				{
					//not in time zone
					intime=false;
					
					
					
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		else
		{
			Toast.makeText(this, getText(R.string.notintimezone), Toast.LENGTH_LONG)
			.show();
		}
		
		if (intime ==false)
			Toast.makeText(this, getText(R.string.notintimezone), Toast.LENGTH_LONG)
			.show();
		
		return intime;
	}
	private void bindData() {
		List<Utilisateur> users = getHelper().getUtilisateurDao().queryForAll();
		if(userId == 0) {
			Parametrage param = getHelper().getParametrageDao().queryForId(1);
			userId = param.getIdUtilisateur();
			}
		ArrayAdapter<Utilisateur> adapter = new ArrayAdapter<Utilisateur>(this,
				R.layout.my_spinner_style, users);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
		lVTeams.setAdapter(adapter);
		
		if (userId != 0) {
			Utilisateur u = getHelper().getUtilisateurDao().queryForId(userId);
			lVTeams.setSelection(adapter.getPosition(u));
		
	}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(teamActivity.BUNDLE_USERID, userId);
	};

	

}
