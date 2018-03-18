package com.coralc.inspectbox;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.Team;
import com.coralc.inspectbox.database.Utilisateur;
import com.coralc.inspectbox.sync.IAsyncTaskCallback;
import com.coralc.inspectbox.sync.SyncTask;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmChoix;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmDatesModifications;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmLibelleNiveau;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmNiveau;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmNiveauObjet;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmObjeteam;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmTeam;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmTypeReponse;
import com.coralc.inspectbox.sync.serializable.ArrayOfKvmUtilisateur;
import com.coralc.inspectbox.sync.serializable.Global;
import com.coralc.inspectbox.sync.serializable.KvmChoix;
import com.coralc.inspectbox.sync.serializable.KvmDatesModifications;
import com.coralc.inspectbox.sync.serializable.KvmLibelleNiveau;
import com.coralc.inspectbox.sync.serializable.KvmNiveau;
import com.coralc.inspectbox.sync.serializable.KvmNiveauObjet;
import com.coralc.inspectbox.sync.serializable.KvmObjeteam;
import com.coralc.inspectbox.sync.serializable.KvmTeam;
import com.coralc.inspectbox.sync.serializable.KvmTypeReponse;
import com.coralc.inspectbox.sync.serializable.KvmUtilisateur;
import com.coralc.inspectbox.R;
import com.coralc.utils.Sha1;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends OrmLiteBaseActivity<DatabaseHelper> implements IAsyncTaskCallback  {
	// Widgets
	private EditText editPwd;
	private Button btnCancel;
	private Button btnLogin;
	private Spinner spinUser;
	private TextView labpass ;
	// Variables
	private int userId;
	private int mode;
	// Nom des variables Bundle
	private static String BUNDLE_USERID = "userId";
	private static String BUNDLE_mode = "mode";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		// on restore l'état précédent si nécessaire
		if(savedInstanceState != null) {
			userId = savedInstanceState.getInt(LoginActivity.BUNDLE_USERID);
			mode =savedInstanceState.getInt(LoginActivity.BUNDLE_mode);
		}

		// Initalisation des widgets
		editPwd = (EditText) findViewById(R.id.editPwd);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		spinUser = (Spinner) findViewById(R.id.spinUser);
		labpass=(TextView )findViewById(R.id.txtPwd);
		editPwd.setVisibility(View.GONE);
		labpass.setVisibility(View.GONE);
		// Binding des évènements
		spinUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Utilisateur user = (Utilisateur) parent.getItemAtPosition(pos);
				userId = user.getIdUtilisateur();
				Utilisateur useroreq = getHelper().getUtilisateurDao().queryForId(userId);
				 if (useroreq!=null)
						if(useroreq.getTypeUser().equals("S"))
						{
							editPwd.setVisibility(View.VISIBLE);
							labpass.setVisibility(View.VISIBLE);
							mode=1;
						}
						else
						{
							editPwd.setVisibility(View.GONE);
							labpass.setVisibility(View.GONE);
							mode=2;
						}
				
				
			}

			public void onNothingSelected(AdapterView<?> parent) {
				userId = 0;
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, AccueilActivity.class);
				startActivity(intent);
			}
		});
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				verif_synchro();
				
				
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Binding des données
		bindData();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(LoginActivity.BUNDLE_USERID, userId);
		outState.putInt(LoginActivity.BUNDLE_mode, mode);
	};

	/*
	 * Lie les données à afficher à la vue
	 */
	private void bindData() {
		// Binding des données
		// Récupération de la liste des utilisateurs
		List<Utilisateur> users = getHelper().getUtilisateurDao().queryForAll();
		// Si aucun utilisateur n'a été sélectionné on récupère le dernier utilisateur loggé
		if(userId == 0) {
			Parametrage param = getHelper().getParametrageDao().queryForId(1);
			userId = param.getIdUtilisateur();
		}

		// Binding des utilisateurs
		if (users.size() == 0) {
			// si la liste est vide on affiche une option par défaut
			Utilisateur defaultUser = new Utilisateur(0);
			defaultUser.setPrenom("");
			defaultUser.setNom("");
			users.add(defaultUser);
		}
		ArrayAdapter<Utilisateur> adapter = new ArrayAdapter<Utilisateur>(this,
				R.layout.my_spinner_style, users);
		//adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		
		spinUser.setAdapter(adapter);
		
		// permet de restaurer la sélection
		if (userId != 0) {
			Utilisateur u = getHelper().getUtilisateurDao().queryForId(userId);
			
		
	}}

	
	private void verif_synchro()
	{
		
		TaskFragment taskFragment = new TaskFragment();
		taskFragment.setTitle(getText(R.string.app_name).toString());
		taskFragment.setText(getText(R.string.autosyncencours).toString());
		// on lui assigne une tâche
		taskFragment.setTask(new SyncTask(getHelper(), false, false,true));

		// affiche le fragment qui lance la tache associée
		taskFragment.show(getFragmentManager(), "TaskFragmentTag");
		
		//login();
	}
	
	private void login()
	{
		Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
		if (mode==2)
		{
			if ((user != null )&&(verifTeamTime()))
			{
		
		// on sauvegarde l'id de l'utilisateur loggé
		Parametrage param = getHelper().getParametrageDao().queryForId(1);
		param.setIdUtilisateur(user.getIdUtilisateur());
		getHelper().getParametrageDao().update(param);
		Intent intent = new Intent(LoginActivity.this, NiveauActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("userId", userId);
		
		intent.putExtras(bundle);
		startActivity(intent);
	} 
		}
		else if (mode==1)
		{
			

			if (user != null
					&& Sha1.convert(editPwd.getText().toString().getBytes()).equals(
							user.getMotDePasse())) {
				
				// on sauvegarde l'id de l'utilisateur loggé
				Parametrage param = getHelper().getParametrageDao().queryForId(1);
				param.setIdUtilisateur(user.getIdUtilisateur());
				getHelper().getParametrageDao().update(param);
				Intent intent = new Intent(LoginActivity.this, NiveausoloActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				editPwd.setError(getString(R.string.mdperrone));
			}
		}
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
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
	
		 		MenuInflater inflater = getMenuInflater();
					
					inflater.inflate(R.menu.version, menu);
				
			
			
			
				
				return true;
			
		}
	 @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	    	PackageInfo pInfo;
			try {
				pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				String version = pInfo.versionName;
			       
		        MenuItem profileMenuItem = menu.findItem(R.id.menu_version);
		        if(profileMenuItem != null) {
		           profileMenuItem.setTitle("V "+version);
		        }
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        return super.onPrepareOptionsMenu(menu);
	    }

	 @Override
		public void onTaskComplete(int result) {
			if (result < 0) {
				Toast.makeText(this, this.getText(R.string.synccanceled), Toast.LENGTH_LONG).show();
			} else if (result == 100) {
				Toast.makeText(this, this.getText(R.string.synccomplete), Toast.LENGTH_LONG).show(); login();
			} else if (result == 0) {
				Toast.makeText(this, this.getText(R.string.noSynch), Toast.LENGTH_LONG).show(); 
				login();
			} else {
				Toast.makeText(this, this.getText(R.string.erreursync), Toast.LENGTH_LONG).show();
			}
		}
	
}
