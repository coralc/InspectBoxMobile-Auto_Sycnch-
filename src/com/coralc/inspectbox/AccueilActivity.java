package com.coralc.inspectbox;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.Utilisateur;
import com.coralc.inspectbox.sync.IAsyncTaskCallback;
import com.coralc.inspectbox.sync.SyncTask;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class AccueilActivity extends OrmLiteBaseActivity<DatabaseHelper> implements IAsyncTaskCallback {

	// Widgets
	private ImageButton btnInspection;
	private ImageButton btnParam;
	private ImageButton btnExit;
	private ImageButton btnSync;
	final private static int DIALOG_LOGIN = 1;
	final private static int DIALOG_EXIT = 2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		// Initalisation des widgets
		btnInspection = (ImageButton) findViewById(R.id.btnInspection);
		btnParam = (ImageButton) findViewById(R.id.btnParam);
		btnExit = (ImageButton) findViewById(R.id.btnExit);
		btnSync = (ImageButton) findViewById(R.id.btnSync);

		// Binding des évènements
		btnExit.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				
					showDialog(DIALOG_EXIT);
					
					
				
				
			}
		});
		btnInspection.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<Utilisateur> users = getHelper().getUtilisateurDao().queryForAll();
				
				if (users.size()>0)
				{
					Intent intent = new Intent(AccueilActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				else
					Toast.makeText(AccueilActivity.this, getText(R.string.nodata), Toast.LENGTH_LONG)
					.show();
				
				
				
			}
		});
		btnParam.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(DIALOG_LOGIN);
				
				
			}
		});
		btnSync.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isOnline())
				{
					List<Utilisateur> users = getHelper().getUtilisateurDao().queryForAll();
					
					if (users.size()>0)
					{// enfin on lance la synchronisation :
						// on créer un nouveau fragment
						TaskFragment taskFragment = new TaskFragment();
						taskFragment.setTitle(getText(R.string.app_name).toString());
						taskFragment.setText(getText(R.string.syncencours).toString());
						// on lui assigne une tâche
						taskFragment.setTask(new SyncTask(getHelper(), true, false,false));

						// affiche le fragment qui lance la tache associée
						taskFragment.show(getFragmentManager(), "TaskFragmentTag");
					}
					else
						Toast.makeText(AccueilActivity.this, getText(R.string.nodata), Toast.LENGTH_LONG)
						.show();
				}
				else
				{
					Toast.makeText(AccueilActivity.this, getText(R.string.nocx), Toast.LENGTH_LONG)
					.show();
				}
			
				
				
			}
		});
	}
	
	@Override
	public void onTaskComplete(int result) {
		if (result < 0) {
			Toast.makeText(this, this.getText(R.string.synccanceled), Toast.LENGTH_LONG).show();
		} else if (result == 100) {
			Toast.makeText(this, this.getText(R.string.synccomplete), Toast.LENGTH_LONG).show();
		} else if (result == 0) {
			Toast.makeText(this, this.getText(R.string.mdperrone), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, this.getText(R.string.erreursync), Toast.LENGTH_LONG).show();
		}
	}
	@Override
	 protected Dialog onCreateDialog(int id) {
	 
	  AlertDialog dialogDetails = null;
	 
	  switch (id) {
	  case DIALOG_LOGIN:
	   LayoutInflater inflater = LayoutInflater.from(this);
	   View dialogview = inflater.inflate(R.layout.dialog_mdp, null);
	 
	   AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
	   dialogbuilder.setTitle(getText(R.string.cltmdps));
	   dialogbuilder.setView(dialogview);
	   dialogDetails = dialogbuilder.create();
	 
	   break;
	  case DIALOG_EXIT:
		   LayoutInflater inflater2 = LayoutInflater.from(this);
		   View dialogview2 = inflater2.inflate(R.layout.dialog_exit, null);
		 
		   AlertDialog.Builder dialogbuilder2 = new AlertDialog.Builder(this);
		   dialogbuilder2.setTitle(getText(R.string.cltmdps));
		   dialogbuilder2.setView(dialogview2);
		   dialogDetails = dialogbuilder2.create();
		 
		   break;
	  }
	 
	  return dialogDetails;
	 }
	
	
	 @Override
	 protected void onPrepareDialog(int id, Dialog dialog) {
	 
	  switch (id) {
	  case DIALOG_LOGIN:
	   final AlertDialog alertDialog = (AlertDialog) dialog;
	   Button loginbutton = (Button) alertDialog
	     .findViewById(R.id.btn_savepassp);
	   Button cancelbutton = (Button) alertDialog
	     .findViewById(R.id.btn_cancelpassp);
	   final EditText pass1 = (EditText) alertDialog
	     .findViewById(R.id.passeditt);
	   
	 
	   loginbutton.setOnClickListener(new View.OnClickListener() {
		   
		    @Override
		    public void onClick(View v) {
		     
		    	Parametrage param = getHelper().getParametrageDao().queryForId(1);
		    	if (param.getPasseappli()!=null)
		    	{
		    		if (param.getPasseappli().equals(pass1.getText().toString()))
		    		{
		    			Intent intent = new Intent(AccueilActivity.this, ParametrageActivity.class);
		    			startActivity(intent);
		    		}
		    		else
		    		{
		    			Toast.makeText(AccueilActivity.this, getText(R.string.wrongpass), Toast.LENGTH_LONG)
		    			.show();
		    		}
		    	}
		     
		     alertDialog.dismiss();
		     pass1.setText("");
			 
		    }
		   });
		 
		   cancelbutton.setOnClickListener(new View.OnClickListener() {
		 
		    @Override
		    public void onClick(View v) {
		     alertDialog.dismiss();
		     pass1.setText("");
			 
		    }
		   });
		   break;
	  case DIALOG_EXIT:
		   final AlertDialog alertDialog2 = (AlertDialog) dialog;
		   Button loginbutton2 = (Button) alertDialog2
		     .findViewById(R.id.btn_saveexit);
		   Button cancelbutton2 = (Button) alertDialog2
		     .findViewById(R.id.btn_cancelexit);
		   final EditText passexit = (EditText) alertDialog2
		     .findViewById(R.id.exitedit);
		   
		 
		   loginbutton2.setOnClickListener(new View.OnClickListener() {
			   
			    @Override
			    public void onClick(View v) {
			     
			    	Parametrage param = getHelper().getParametrageDao().queryForId(1);
			    	if (param.getPasseappli()!=null)
			    	{
			    		if (param.getPasseappli().equals(passexit.getText().toString()))
			    		{
			    			
			    			Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.addCategory(Intent.CATEGORY_HOME);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
			    			
			    		}
			    		else
			    		{
			    			Toast.makeText(AccueilActivity.this, getText(R.string.wrongpass), Toast.LENGTH_LONG)
			    			.show();
			    		}
			    	}
			    	passexit.setText("");
			     alertDialog2.dismiss();
			     
				 
			    }
			   });
			 
			   cancelbutton2.setOnClickListener(new View.OnClickListener() {
			 
			    @Override
			    public void onClick(View v) {
			     alertDialog2.dismiss();
			     passexit.setText("");
				 
			    }
			   });
			   break;
		  }
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
		
		
		
		    public boolean isOnline() {
			    ConnectivityManager cm =
			        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    return netInfo != null && netInfo.isConnectedOrConnecting();
			} 
	
	
}
