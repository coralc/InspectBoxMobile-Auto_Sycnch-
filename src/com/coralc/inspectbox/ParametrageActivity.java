package com.coralc.inspectbox;

import java.sql.SQLException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.coralc.inspectbox.database.Cloture;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Historique;
import com.coralc.inspectbox.database.Inspection;
import com.coralc.inspectbox.database.Nfc;
import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.StatutHS;
import com.coralc.inspectbox.database.StatutInac;
import com.coralc.inspectbox.sync.IAsyncTaskCallback;
import com.coralc.inspectbox.sync.SyncTask;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.table.TableUtils;

public class ParametrageActivity extends OrmLiteBaseActivity<DatabaseHelper> implements
		IAsyncTaskCallback {
	// Widgets
	private EditText editUrl;
	private EditText editPwd;
	private EditText editClient;
	private EditText editTerminal;
	private Button btnCancel;
	private Button btnSynchroniser;
 private CheckBox codebarre;
 private DatabaseHelper helper = null;
	// Variables
 final private static int DIALOG_LOGIN = 1;
 final private static int DIALOG_DEL = 2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametrage);

		// on restore l'état précédent si nécessaire
		if (savedInstanceState != null) {
			// rien a faire dans cette activité
		}

		// Initalisation des widgets
		editUrl = (EditText) findViewById(R.id.editUrl);
		editClient = (EditText) findViewById(R.id.editClient);
		editPwd = (EditText) findViewById(R.id.editPwd);
		editTerminal = (EditText) findViewById(R.id.editTerminal);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSynchroniser = (Button) findViewById(R.id.btnSynchroniser);
		codebarre=(CheckBox)findViewById(R.id.barcodecheck);
		// Binding des évènements
		codebarre.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		    	Parametrage param = getHelper().getParametrageDao().queryForId(1);
				
				param.setCodebarre(isChecked);
				getHelper().getParametrageDao().update(param);
		        

		    }
		});
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List <Nfc> nfcs=getHelper().getNfcDao().queryForAll();
				if (nfcs.size()==0)
				{
					Intent intent = new Intent(ParametrageActivity.this, AccueilActivity.class);
					startActivity(intent);
				}
				else
					Toast.makeText(ParametrageActivity.this, getText(R.string.gosync), Toast.LENGTH_LONG).show();
				
			}
		});
		btnSynchroniser.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// pour commencer on vérifie la validitié des données entrées
				if (!validateWidgets())
					return;

				// ensuite on sauvegarde les nouveaux paramètres
				Parametrage param = getHelper().getParametrageDao().queryForId(1);
				param.setUrlClient(editUrl.getText().toString());
				param.setCodeClient(editClient.getText().toString());
				param.setMotdepasseClient(editPwd.getText().toString());
				param.setNbTerminal(editTerminal.getText().toString());
				param.setCodebarre(codebarre.isChecked());
				getHelper().getParametrageDao().update(param);

				// enfin on lance la synchronisation :
				// on créer un nouveau fragment
				if (isOnline())
				{
					TaskFragment taskFragment = new TaskFragment();
					taskFragment.setTitle(getText(R.string.app_name).toString());
					taskFragment.setText(getText(R.string.syncencours).toString());
					// on lui assigne une tâche
					taskFragment.setTask(new SyncTask(getHelper(), true, true,false));

					// affiche le fragment qui lance la tache associée
					taskFragment.show(getFragmentManager(), "TaskFragmentTag");
				}
				else
				{
					Toast.makeText(ParametrageActivity.this, getText(R.string.nocx), Toast.LENGTH_LONG)
					.show();
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	};

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

	/*
	 * Lie les données à afficher à la vue
	 */
	private void bindData() {
		// Récupération des paramètres
		Parametrage param = getHelper().getParametrageDao().queryForId(1);

		// Binding des données
		editUrl.setText(param.getUrlClient());
		editClient.setText(param.getCodeClient());
		editPwd.setText(param.getMotdepasseClient());
		editTerminal.setText(param.getNbTerminal());
		codebarre.setChecked(param.isCodebarre());
	}

	/*
	 * Vérifie la validité des valeurs des widgets
	 */
	private boolean validateWidgets() {
		boolean valid = true;

		if (editUrl.getText().length() == 0) {
			editUrl.setError(getString(R.string.champobligatoire));
			valid = false;
		}
		if (editClient.getText().length() == 0) {
			editClient.setError(getString(R.string.champobligatoire));
			valid = false;
		}
		if (editPwd.getText().length() == 0) {
			editPwd.setError(getString(R.string.champobligatoire));
			valid = false;
		}
		if (editTerminal.getText().length() == 0) {
			editTerminal.setError(getString(R.string.champobligatoire));
			valid = false;
		}
		return valid;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

				MenuInflater inflater = getMenuInflater();
				
				inflater.inflate(R.menu.param, menu);
			
			
			return true;
		
	}
	
	 @SuppressWarnings({ "deprecation" })
	public boolean onOptionsItemSelected(MenuItem item) {
         
         switch (item.getItemId()) {
            case R.id.menu_changepsw:
            	showDialog(DIALOG_LOGIN);
                return true;
                
            case R.id.menu_nfc:
                ecrirenfc();
                return true;
            case R.id.menu_delete:
            	AlertDialog.Builder alert = new AlertDialog.Builder(ParametrageActivity.this);
				alert.setTitle(getText(R.string.attention));
				alert.setMessage(getText(R.string.msgdel));
				alert.setPositiveButton(getText(R.string.oui),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
								showDialog(DIALOG_DEL);
							}
						});

				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

				AlertDialog alertDialog = alert.create();
				alertDialog.show();
            	
                return true;
         }
         return false;
         }
	 private void effacer()
	 {helper=getHelper();
		 try {
				TableUtils.dropTable(getHelper().getConnectionSource(), Inspection.class, true);
				TableUtils.createTable(helper.getConnectionSource(), Inspection.class);
				TableUtils.dropTable(helper.getConnectionSource(), StatutHS.class, true);
				TableUtils.createTable(helper.getConnectionSource(), StatutHS.class);
				TableUtils.dropTable(helper.getConnectionSource(), Cloture.class, true);
				TableUtils.createTable(helper.getConnectionSource(), Cloture.class);
				TableUtils.dropTable(helper.getConnectionSource(), Historique.class, true);
				TableUtils.createTable(helper.getConnectionSource(), Historique.class);
				TableUtils.dropTable(helper.getConnectionSource(), Nfc.class, true);
				TableUtils.createTable(helper.getConnectionSource(), Nfc.class);
				TableUtils.dropTable(helper.getConnectionSource(), StatutInac.class, true);
				TableUtils.createTable(helper.getConnectionSource(), StatutInac.class);
				Toast.makeText(ParametrageActivity.this, getText(R.string.delok), Toast.LENGTH_LONG)
    			.show();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	 }
	 private void ecrirenfc()
	 {
		 List<Niveau> nvs = getHelper().getNiveauDao().queryForAll();
		 if (nvs.size()==0)
		 {
			 Toast.makeText(this, this.getText(R.string.nodata), Toast.LENGTH_LONG).show();
		 }
		 else
		 {
			 Intent intent = new Intent(ParametrageActivity.this, NfcNiveauActivity.class);
				startActivity(intent);
			 
		 }
			
	 }
	 @Override
	 protected Dialog onCreateDialog(int id) {
	 
	  AlertDialog dialogDetails = null;
	 
	  switch (id) {
	  case DIALOG_LOGIN:
	   LayoutInflater inflater = LayoutInflater.from(this);
	   View dialogview = inflater.inflate(R.layout.dialog_layout, null);
	 
	   AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
	   dialogbuilder.setTitle(getText(R.string.cltmdps));
	   dialogbuilder.setView(dialogview);
	   dialogDetails = dialogbuilder.create();
	 
	   break;
	  
	  case DIALOG_DEL:
		   LayoutInflater inflater2 = LayoutInflater.from(this);
		  
		   View dialogview2 = inflater2.inflate(R.layout.dialog_mdp, null);
		 
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
	  case DIALOG_DEL:
		   final AlertDialog alertDialog1 = (AlertDialog) dialog;
		   Button loginbutton1 = (Button) alertDialog1
		     .findViewById(R.id.btn_savepassp);
		   Button cancelbutton1 = (Button) alertDialog1
		     .findViewById(R.id.btn_cancelpassp);
		   final EditText pass11 = (EditText) alertDialog1
		     .findViewById(R.id.passeditt);
		   
		 
		   loginbutton1.setOnClickListener(new View.OnClickListener() {
			   
			    @Override
			    public void onClick(View v) {
			     
			    	Parametrage param = getHelper().getParametrageDao().queryForId(1);
			    	if (param.getPasseappli()!=null)
			    	{
			    		if (param.getPasseappli().equals(pass11.getText().toString()))
			    		{
			    			effacer();
			    		}
			    		else
			    		{
			    			Toast.makeText(ParametrageActivity.this, getText(R.string.wrongpass), Toast.LENGTH_LONG)
			    			.show();
			    		}
			    	}
			     
			     alertDialog1.dismiss();
			     pass11.setText("");
				 
			    }
			   });
			 
			   cancelbutton1.setOnClickListener(new View.OnClickListener() {
			 
			    @Override
			    public void onClick(View v) {
			     alertDialog1.dismiss();
			     pass11.setText("");
				 
			    }
			   });
			   break;
	  case DIALOG_LOGIN:
	   final AlertDialog alertDialog = (AlertDialog) dialog;
	   Button loginbutton = (Button) alertDialog
	     .findViewById(R.id.btn_savepass);
	   Button cancelbutton = (Button) alertDialog
	     .findViewById(R.id.btn_cancelpass);
	   final EditText pass1 = (EditText) alertDialog
	     .findViewById(R.id.nv_pass);
	   final EditText pass2 = (EditText) alertDialog
	     .findViewById(R.id.nv_pass2);
	 
	   loginbutton.setOnClickListener(new View.OnClickListener() {
		   
		    @Override
		    public void onClick(View v) {
		     
		     
		     if (pass1.getText().toString().equals(pass2.getText().toString())&& (!pass1.getText().toString().equals("")))
		     {
		    	 Parametrage param = getHelper().getParametrageDao().queryForId(1);
					param.setPasseappli(pass1.getText().toString());
					getHelper().getParametrageDao().update(param);
					Toast.makeText(ParametrageActivity.this, getText(R.string.passucee), Toast.LENGTH_LONG)
					.show();
		     }
		     else
		     {
		    	 Toast.makeText(ParametrageActivity.this, getText(R.string.wrongpass), Toast.LENGTH_LONG)
					.show();
		     }
		     alertDialog.dismiss();
		     pass1.setText("");
			 pass2.setText("");
		    }
		   });
		 
		   cancelbutton.setOnClickListener(new View.OnClickListener() {
		 
		    @Override
		    public void onClick(View v) {
		     alertDialog.dismiss();
		     pass1.setText("");
			 pass2.setText("");
		    }
		   });
		   break;
		  }
		 }
	 public boolean isOnline() {
		    ConnectivityManager cm =
		        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    return netInfo != null && netInfo.isConnectedOrConnecting();
		}
}
