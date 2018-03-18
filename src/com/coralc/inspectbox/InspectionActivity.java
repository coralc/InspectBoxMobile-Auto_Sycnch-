package com.coralc.inspectbox;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import java.text.DateFormat;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coralc.inspectbox.database.Choix;
import com.coralc.inspectbox.database.Cloture;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Historique;
import com.coralc.inspectbox.database.Inspection;
import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.database.Objeteam;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.StatusPI;
import com.coralc.inspectbox.database.StatutHS;
import com.coralc.inspectbox.database.StatutInac;
import com.coralc.inspectbox.database.TypeReponse;
import com.coralc.inspectbox.database.Utilisateur;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class InspectionActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Widgets
	private TextView txtObjet;
	private TextView txtChemin;
	private TextView txtUnite;
	
	
	private EditText editValuerep;
	private EditText editDaterep;
	
	private Button btnCancel;
	private CheckBox defect;
	
	private Button btnNextInspect;
	private Button btnShowDtp;
	private Spinner spinChoix;
	private View listeLayout;
	private View valeurLayout;
	private View dateLayout;

	// Variables
	private int idNiveauObjet;
	private int idNiveau;
	private int idNiveaubat;
	private int idNiveauSit;
	private String chemin;
	private int idChoix;
	private Integer userId;
	private Date lastDate ;
	private String lastDatestring;
	private String firstDatestring;
	private Date firstDate ;
	private String msgL;
	private ArrayList<String> listLibelles = new ArrayList<String>();
	final private static int DIALOG_LIMIT = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inspection);

		// Initalisation des widgets
		txtObjet = (TextView) findViewById(R.id.txtObjet);
		txtChemin = (TextView) findViewById(R.id.txtChemin);
		
		btnCancel = (Button) findViewById(R.id.btnCancel);
		defect=(CheckBox)findViewById(R.id.checkBoxdefect);
		//btnPhoto = (Button) findViewById(R.id.btnPhoto);
		btnNextInspect = (Button) findViewById(R.id.btnNextInspect);
		btnShowDtp = (Button) findViewById(R.id.btnShowDtp);
		listeLayout = findViewById(R.id.includeListe);
		valeurLayout = findViewById(R.id.includeValue);
		dateLayout = findViewById(R.id.includeDate);
		spinChoix = (Spinner) listeLayout.findViewById(R.id.spinChoix);
		editValuerep = (EditText) valeurLayout.findViewById(R.id.editValuerep);
		
		txtUnite = (TextView) valeurLayout.findViewById(R.id.txtUnite);
		editDaterep = (EditText) dateLayout.findViewById(R.id.editDaterep);

		// récupération des valeurs
		Intent intent = this.getIntent();
		idNiveauObjet = intent.getIntExtra("idNiveauObjet", 0);
		idNiveau = intent.getIntExtra("idNiveau", 0);
		chemin = intent.getStringExtra("chemin");
		
		String[] splitArray = null;
		 splitArray = chemin.split(">");
		 
		  for(int i = 0; i< splitArray.length;i++){
			 
			  this.listLibelles.add(splitArray[i]);
		  }
		
		String path=intent.getStringExtra("mapPath");
		System.out.println("path"+path);
		idNiveaubat=intent.getIntExtra("idNiveaubat", 0);
		userId = intent.getIntExtra("userId", 0);
		
		
		
		Niveau niveau = getHelper().getNiveauDao().queryForId(idNiveaubat); //site
		if (niveau!=null)
		{
			idNiveauSit=niveau.getIdNiveauParent();
		}
		

		
		// s'il y a eut un changement de config, on recharge ces valeurs
		if (savedInstanceState != null) {
			//InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			 // mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
			
			idNiveauObjet = savedInstanceState.getInt("idNiveauObjet");
			userId = savedInstanceState.getInt("userId");
		}
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		View view = this.getCurrentFocus();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		// Binding des évènements
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Niveau niveauxp = getHelper().getNiveauDao().queryForId(idNiveaubat); //site
				Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
				Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
				 Niveau niveauactuel=getHelper().getNiveauDao().queryWhereidObjet(idNiveauObjet);
				
				if (cloture!=null)
				{
					System.out.println("step1");
					finish();
				}
				else
				{
					System.out.println("step2");
					Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(
							idNiveau, idNiveauObjet);
					finish();

					/*if (inspection == null ) {
						System.out.println("step3");
						AlertDialog.Builder alert = new AlertDialog.Builder(InspectionActivity.this);
						alert.setTitle(getText(R.string.attention));
						alert.setMessage(getText(R.string.msginspectionvideavecphotos));
						alert.setPositiveButton(getText(R.string.oui),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										dialog.dismiss();
										//finish();
retour2();
									}
								});

						alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								dialog.dismiss();
							}
						});

						AlertDialog alertDialog = alert.create();
						alertDialog.show();
					} else {
						
						System.out.println("step4");
						if (user.getTypeUser().endsWith("S"))
						 {
							removeLastLibelle();
							 removeLastLibelle();
							
								
							 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
								
									Intent intent = new Intent(InspectionActivity.this,NiveausoloActivity.class);
									Bundle bundle = new Bundle();
									
									bundle.putInt("userId", userId);
									bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
									bundle.putString("chemin", getChemin());
									bundle.putBoolean("next",true);
									intent.putExtras(bundle);
									startActivity(intent);
						 }
						else
						{
							removeLastLibelle();
							 removeLastLibelle();
							 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
								
									Intent intent = new Intent(InspectionActivity.this,NiveauActivity.class);
									Bundle bundle = new Bundle();
									
									bundle.putInt("userId", userId);
									bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
									bundle.putString("chemin", getChemin());
									bundle.putBoolean("next",true);
									intent.putExtras(bundle);
									startActivity(intent);
						}
					//}*/
				}
				
			}
		});
		defect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		       @Override
		       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		    	   if (isChecked)
		    	   {
		    		   editValuerep.setEnabled(false);
		    		  // editValuerep.setFocusable(false);
		    			editDaterep.setEnabled(false);
		    			//editDaterep.setFocusable(false);
		    			//validateAndSave();
		    	   }
		    	   else
		    	   {
		    		   
		    		   editValuerep.setEnabled(true);
		    		   editValuerep.setFocusable(true);
		    			editDaterep.setEnabled(true);
		    			editDaterep.setFocusable(true);
		    	   }

		       }
		   }
		); 
		
		btnNextInspect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				validateAndSave();
			}
		});
		
		
		btnShowDtp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DatePickerFragment dtp = new DatePickerFragment();
				dtp.setEditDate(editDaterep);
				dtp.show(getFragmentManager(), "datePicker");
			}
		});
		spinChoix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				Choix choix = (Choix) parent.getItemAtPosition(pos);
				idChoix = choix.getIdChoix();
			}

			public void onNothingSelected(AdapterView<?> parent) {
				idChoix = 0;
			}
		});
		
		editValuerep.setOnKeyListener(new EditText.OnKeyListener() {
		
		    public boolean onKey(View v, int keyCode, KeyEvent event)
		    {
		        if (event.getAction() == KeyEvent.ACTION_DOWN)
		        {
		            switch (keyCode)
		            {
		            
		                case KeyEvent.KEYCODE_ENTER:
		                	
		                	validateAndSave();
		                	
		                	return true;
		                default:
		                	return false;
		            }
		        }
		        
		        return true;
		    }
		});
		bindData();
	}
	 
	@Override
	protected void onResume() {
		super.onResume();
		// Binding des données
		//InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  //mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		View view = this.getCurrentFocus();
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
	   
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		bindData();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("idNiveauObjet", idNiveauObjet);
		InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
	};

	/*
	 * Lie les données à afficher à la vue
	 */
	private void retour2()
	{
		Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
		 Niveau niveauactuel=getHelper().getNiveauDao().queryWhereidObjet(idNiveauObjet);
		
		if (user.getTypeUser().endsWith("S"))
		 {
			removeLastLibelle();
			 removeLastLibelle();
			
				
			 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
				
					Intent intent = new Intent(InspectionActivity.this,NiveausoloActivity.class);
					Bundle bundle = new Bundle();
					
					bundle.putInt("userId", userId);
					bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
					bundle.putString("chemin", getChemin());
					bundle.putBoolean("next",true);
					intent.putExtras(bundle);
					startActivity(intent);
		 }
		else
		{
			removeLastLibelle();
			 removeLastLibelle();
			 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
				
					Intent intent = new Intent(InspectionActivity.this,NiveauActivity.class);
					Bundle bundle = new Bundle();
					
					bundle.putInt("userId", userId);
					bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
					bundle.putString("chemin", getChemin());
					bundle.putBoolean("next",true);
					intent.putExtras(bundle);
					startActivity(intent);
		}
	}
	private void bindData() {
		
		
		invalidateOptionsMenu();
		
		Niveau niveauxp = getHelper().getNiveauDao().queryForId(idNiveaubat); //site
		Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
		defect.setChecked(false);
		if (cloture!=null)
		{
		spinChoix.setEnabled(false);
		
		editValuerep.setEnabled(false);
		editDaterep.setEnabled(false);
		defect.setEnabled(false);
		}
		// chargement des données utiles
		
		NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
		TypeReponse tr = getHelper().getTypeReponseDao().queryForId(no.getIdTypereponse());
		
				

		// on vide tous les contenus
		
		editDaterep.setText("");
		editValuerep.setText("");

		// Visibilité et accessibilité
		listeLayout.setVisibility(View.GONE);
		dateLayout.setVisibility(View.GONE);
		valeurLayout.setVisibility(View.GONE);
		
		// On lie les données
		txtObjet.setText(no.getLibelle());
		txtChemin.setText(chemin);

		// On vérifie s'il existait déjà une inspection
		Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(idNiveau,idNiveauObjet);
				

		if (inspection != null) {
			if (! (inspection.isPause()))
			if (inspection.getDefekt()==true && inspection.getReponse()==null)
			defect.setChecked(inspection.getDefekt());
		}

		// Saisie en mode Liste
		if (tr.getModeReponse().equals(TypeReponse.MODE_LISTE)) {
			listeLayout.setVisibility(View.VISIBLE);
			idChoix = 0;

			// Binding de la liste de choix
			List<Choix> choix = getHelper().getChoixDao().queryWhereIdTypereponse(
					tr.getIdTypeReponse());
			ArrayAdapter<Choix> adapter = new ArrayAdapter<Choix>(this, R.layout.my_spinner_style,choix);
					
			//choix.add(0, new Choix(0));
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
			spinChoix.setAdapter(adapter);
			if (inspection != null)
				if (inspection.isPause()==false)
			if ( inspection.getIdChoix() != 0) {
				Choix c = getHelper().getChoixDao().queryForId(inspection.getIdChoix());
				spinChoix.setSelection(adapter.getPosition(c));
			}
		}
		// Saisie en mode valeur
		else if (tr.getModeReponse().equals(TypeReponse.MODE_VALEUR)) {
			txtUnite.setText(no.getUnitmeasure());
			valeurLayout.setVisibility(View.VISIBLE);
			//InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			 // mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
			
			if (inspection != null) {
				editValuerep.setText(inspection.getReponse() == null ? "" : inspection.getReponse());
						
			}
			
		}
		else if (tr.getModeReponse().equals(TypeReponse.MODE_DATE)) {
			txtUnite.setText(no.getUnitmeasure());
			dateLayout.setVisibility(View.VISIBLE);

			if (inspection != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
				String strDate = inspection.getReponse();

				Date d;
				try {
					d = sdf.parse(strDate);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				editDaterep.setText(format.format(d));
			}
		} 
	}

	/*
	 * Vérifie la validité des valeurs des widgets (ne doit pas être appelé
	 * avant bindData), sauvegarde et charge l'élément suivant
	 */
	@SuppressWarnings("deprecation")
	private void validateAndSave() {
		//verifier si la tournee n 'est pas cloturer
		Niveau niveauxp = getHelper().getNiveauDao().queryForId(idNiveaubat); //site
		Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
		if (cloture==null)
		{
			// chargement des données utiles
			NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
			TypeReponse tr = getHelper().getTypeReponseDao().queryForId(no.getIdTypereponse());
			

			if (tr.getModeReponse().equals(TypeReponse.MODE_LISTE)) {
				if (idChoix == 0) {
					Toast.makeText(this, getText(R.string.reponseobligatoire), Toast.LENGTH_LONG).show();
							
				} else {
					saveData(false);
				}
			} 
			else if (tr.getModeReponse().equals(TypeReponse.MODE_DATE)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						
				try {
					if   (! defect.isChecked())
					format.parse(editDaterep.getText().toString());
					saveData(false);
				} catch (ParseException e) {
					editDaterep.setError(getText(R.string.datenonvalide));
				}

			} else if ((tr.getModeReponse().equals(TypeReponse.MODE_VALEUR)) ) {
				
					if (defect.isChecked())
						{
					saveData(false);	
						}
					else
					{
					double val = 0;
					try {
						
						
						val = Double.parseDouble(editValuerep.getText().toString());
					} catch (NumberFormatException nfe) {
						editValuerep.setError(getText(R.string.valeurtypedec));
						return;
					}
					 
					// la valeur basse
					
					 if (val<no.getLowborder())
					{
						
						if (val >= no.getLowlimit())
						{ 
							NumberFormat formatter = new DecimalFormat("#0.00");
							String min=formatter.format(no.getLowborder());
							String max=formatter.format(no.getHighborder());
							final String minalert= no.getLowborderalert();
							String msg = getText(R.string.valeursecuriteformat).toString()+" "+min+ " "+getText(R.string.valeursecuriteformat2)+" "+ max+
									" "+ getText(R.string.valeursecuriteformat3);
							
							AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setTitle(getText(R.string.attention));
							alert.setMessage(msg);
							alert.setIcon(R.drawable.alert);
							alert.setPositiveButton(getText(R.string.oui),
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											makealert(minalert);
											
											//dialog.dismiss();
										}
									});

							alert.setNegativeButton(getText(R.string.non),
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											editValuerep.setText("");
											dialog.dismiss();
										}
									});

							AlertDialog alertDialog = alert.create();
							 WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

							 wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
							    //y position

							alertDialog.show();
							
						}
						else
						{
							NumberFormat formatter = new DecimalFormat("#0.00");     
							String min=formatter.format(no.getLowlimit());
							String max=formatter.format(no.getHighlimit());
							
							//editValuerep.setError(getText(R.string.valeurdoitetreentreformat)+min+getText(R.string.valeurdoitetreentreformat2)+max+getText(R.string.valeurdoitetreentreformat3));
							msgL=getText(R.string.valeurdoitetreentreformat)+min+getText(R.string.valeurdoitetreentreformat2)+max+getText(R.string.valeurdoitetreentreformat3);
							editValuerep.setText("");
							editValuerep.setError(msgL);
							InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							  mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
						}
					}
					//higt valeur
					else if (val>no.getHighborder())
					{
						if (val <= no.getHighlimit())
						{
							NumberFormat formatter = new DecimalFormat("#0.00");
							String min=formatter.format(no.getLowborder());
							String max=formatter.format(no.getHighborder());
							final String maxalert= no.getHighborderalert();
							String msg = getText(R.string.valeursecuriteformat).toString()+" "+min+" "+getText(R.string.valeursecuriteformat2)+" "+max+
									" "+getText(R.string.valeursecuriteformat3);
							
							AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setTitle(getText(R.string.attention));
							alert.setMessage(msg);
							alert.setIcon(R.drawable.alert);
							alert.setPositiveButton(getText(R.string.oui),
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											makealert(maxalert);
											//saveData(true);
											//dialog.dismiss();
										}
									});

							alert.setNegativeButton(getText(R.string.non),
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int whichButton) {
											editValuerep.setText("");
											dialog.dismiss();
										}
									});

							AlertDialog alertDialog = alert.create();
							 WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
							 wmlp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
							alertDialog.show();
						}
						else
						{
							NumberFormat formatter = new DecimalFormat("#0.00");     
							String min=formatter.format(no.getLowlimit());
							String max=formatter.format(no.getHighlimit());
							
							//editValuerep.setError(getText(R.string.valeurdoitetreentreformat)+min+getText(R.string.valeurdoitetreentreformat2)+max+getText(R.string.valeurdoitetreentreformat3));
							msgL=getText(R.string.valeurdoitetreentreformat)+min+getText(R.string.valeurdoitetreentreformat2)+max+getText(R.string.valeurdoitetreentreformat3);
							editValuerep.setText("");
							editValuerep.setError(msgL);
							InputMethodManager mgr=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							  mgr.showSoftInput(this.editValuerep,InputMethodManager.SHOW_FORCED);
						}
					}
					
			
			
					else {
						saveData(false);
					}	
				}
					
				
			}
		}
		else
		{
			Toast.makeText(this, getText(R.string.validaimp), Toast.LENGTH_LONG)
			.show();
		}
		
	}

	/*
	 * Sauvegarde les données et charge l'objet suivant ou ferme l'activité
	 * cette méthode ne doit pas être appelée directement, utilisez
	 * validateAndSave
	 */
	private void makealert( String msg)
	{
		/*  AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
          
          // set the message to display
          alertbox.setMessage(msg);
          alertbox.setIcon(R.drawable.ic_launcher);
          // add a neutral button to the alert box and assign a click listener
          alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
               
              // click listener on the alert box
              public void onClick(DialogInterface arg0, int arg1) {
                  // the button was clicked
                 
              }
          });
           
          // show it
          alertbox.show();*/
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(msg)
	           
	            .setCancelable(false)
	            .setIcon(R.drawable.alert)
	            
	           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                
	                public void onClick(DialogInterface dialog, int which) {
	                	saveData(true);
	                	dialog.dismiss();
	                     }
	            });
	 
	        builder.create().show();        // create and show the alert dialog
	 
	    
	}
	private void saveData( boolean limit) {
		// chargement des données utiles
		NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
		TypeReponse tr = getHelper().getTypeReponseDao().queryForId(no.getIdTypereponse());
			Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(idNiveau,idNiveauObjet);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		

		if (inspection == null) {
			

			inspection = new Inspection();
			
			
			inspection.setDateInformation(df.format(new Date()));
			UUID uuid = UUID.randomUUID();
			inspection.setIdInformation(uuid.toString());
		}
		inspection.setPause(false);
		inspection.setIdNiveau(idNiveau);
		inspection.setIdNiveauObjet(idNiveauObjet);
		inspection.setIdNiveaubat(idNiveaubat);
		

		// on parse la réponse selon le cas
		if (tr.getModeReponse().equals(TypeReponse.MODE_LISTE)) {
			inspection.setIdChoix(idChoix);
			inspection.setReponse(null);
			inspection.setLimite(false);
			Choix choix =getHelper().getChoixDao().queryForId(idChoix);
				if (choix.isNonConforme()==true)
				{
					inspection.setStatut(5);
					inspection.setDefekt(true);
				}
				
			else
				
				{
				inspection.setDefekt(false);
				inspection.setStatut(1);
				}
			
			
			
		} 
		else if (tr.getModeReponse().equals(TypeReponse.MODE_DATE)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					inspection.setLimite(false);
			try {
				if   (! defect.isChecked())
				{
					Date d = format.parse(editDaterep.getText().toString());
					inspection.setReponse(sdf.format(d));
					
						inspection.setDefekt(false);
						inspection.setStatut(1);
						
						
				}
				else	{
					inspection.setStatut(5); inspection.setDefekt(true);
				}
					
				
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		else if (tr.getModeReponse().equals(TypeReponse.MODE_VALEUR)) {
			 
				if   (! defect.isChecked())
				{
					double val = Double.parseDouble(editValuerep.getText().toString());
					inspection.setReponse(String.valueOf(val));
					inspection.setDefekt(false);
					if (limit==true)
					{
						inspection.setLimite(true);
						inspection.setStatut(4);
					}
						
					else
					{
						inspection.setLimite(false);inspection.setStatut(1);
					}
					
				}
				else 	
					{
					inspection.setDefekt(true);
					inspection.setStatut(5);
					}
			
					}

		getHelper().getInspectionDao().createOrUpdate(inspection);
		// on enregistre le pdata
		
		
			
			
		
		// on enregistre la valeur ds historique 
		if ((tr.getModeReponse().equals(TypeReponse.MODE_VALEUR)) &&  (! defect.isChecked()))
		{
			
			Historique historique = new Historique();
			historique.setIdObjet(idNiveauObjet);
			historique.setDateHist(df.format(new Date()));
			double val = Double.parseDouble(editValuerep.getText().toString());
			historique.setValeur(String.valueOf(val));
			
			getHelper().getHistoriqueDao().createOrUpdate(historique);
		}
		
		// on verifie si c innaccesible
	
		StatutInac statutInac=getHelper().getStatutInacDao().queryForId(idNiveau);
		if (statutInac!=null)
			getHelper().getStatutInacDao().delete(idNiveau);
		
		//voir si la trnee est complete
		Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
		System.out.println("user"+userId);
		 Date maDate = new Date(); 
			@SuppressWarnings("deprecation")
			int day=maDate.getDay();
		if (user.getTypeUser().equals("S"))
		{
			int nbrenv=0;
			int nbreins=0;
			 
			List<Niveau> listNiveaub = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(
					idNiveauSit);
			for (Niveau n : listNiveaub) {
				
				List<Niveau> listNiveaue = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(
						n.getIdNiveau());
				nbrenv+=listNiveaue.size();
				List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubat(n.getIdNiveau());
				nbreins+=inspectionbat.size();
			}
			if (nbrenv==nbreins)
			{
				//obtenir dates 
				for (Niveau n : listNiveaub) {
					
					
					List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubatByDate(n.getIdNiveau());
					List <Inspection> inspectionbat2 = getHelper().getInspectionDao().queryWhereIdNiveaubatByDateF(n.getIdNiveau());
					
						
						for (Inspection i : inspectionbat)
						{
							try
						       {
						        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date d2 = df2.parse(i.getDateInformation());
							lastDate=d2;
							lastDatestring=df2.format(i.getDateInformation());
							break;
							
							 }catch (Exception ex ){
						          ex.printStackTrace();
						       }
						}
						System.out.println("lastDate"+lastDate);
						for (Inspection i : inspectionbat2)
						{
							try
						       {
						        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date d2 = df2.parse(i.getDateInformation());
							firstDate=d2;
							firstDatestring=df2.format(i.getDateInformation());
							break;
							
							 }catch (Exception ex ){
						          ex.printStackTrace();
						       }
						}
						System.out.println("firstDate"+firstDate);
						}
				//tournee finis cloturer
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				 alert.setTitle(getText(R.string.attention));
					alert.setMessage(getText(R.string.confirmcloturett));
		  				alert.setPositiveButton(getText(R.string.oui),
		  						new DialogInterface.OnClickListener() {
		  							public void onClick(DialogInterface dialog, int whichButton) {
		  								 DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  								 Date maDate = new Date(); 
		  								@SuppressWarnings("deprecation")
		  								int day=maDate.getDay();
		  								Cloture cloture = new Cloture();
		  								cloture.setIdNiveau(idNiveauSit);
		  								cloture.setStatutCloture(1);
		  								cloture.setUserCloture(userId);
		  								cloture.setDateCloture(df2.format(lastDate));
		  								cloture.setDayCloture(day);
		  								cloture.setDebDateCloture(df2.format(firstDate));
		  								getHelper().getClotureDao().createOrUpdate(cloture);
		  								pddata(idNiveauSit);
		  								dialog.dismiss();
		  								Intent intent = new Intent(InspectionActivity.this, NiveausoloActivity.class);
		  								Bundle bundle = new Bundle();
		  								bundle.putInt("userId", userId);
		  								
		  								intent.putExtras(bundle);
		  								startActivity(intent);
		  							}
		  						});
				
				
		  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
		  					public void onClick(DialogInterface dialog, int whichButton) {
		  						dialog.dismiss();
		  						finish();
		  					}
		  				});
		  				
		       	   
				AlertDialog alertDialog = alert.create();
				alertDialog.show();
				
			}
			else
			{
				nextInspect3();
			}
			
		}
		else //eq
		{
			 int totniv=0;
			 int totinsp=0;
			
				//Date lastDat = null;
				
			 List<Objeteam> objteam = getHelper().getObjeteamDao().queryWhereDay(userId, day);
			 List<Niveau> listNiveauxb = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idNiveauSit);
			 	
			 for (Niveau nb : listNiveauxb) { //bat
				 
					List<Niveau> listNiveauxc = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(nb.getIdNiveau()); //eq
					
						for (Niveau nc : listNiveauxc) 
						{ 
							for (Objeteam o : objteam) {
								
								if (o.getIdObjet()==nc.getIdNiveauObjet())
								{
									totniv++;
									  break;
									
								}
										
								
							}
							
						}
					
					
					
				}
			//derniere date 
			  
				for (Niveau n : listNiveauxb) {
					
					
					List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubatByDate(n.getIdNiveau());
					getHelper().getInspectionDao().queryWhereIdNiveaubatByDateF(n.getIdNiveau());
					
						for (Inspection i : inspectionbat)
						{	
							for (Objeteam o : objteam) {
								
								
								if (o.getIdObjet()==i.getIdNiveauObjet())
								{ totinsp++;
									try
								       {
								        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date d2 = df2.parse(i.getDateInformation());
									if (lastDate==null) 
										{
										lastDate=d2;	
										}
										
									if (firstDate==null)
										{
										firstDate=d2;		
										}
									 if (lastDate.after(d2))
										 {
										 lastDate=d2; 	
										 }
									 if (firstDate.before(d2))
										 {
										 firstDate=d2; 
										 }
									 }catch (Exception ex ){
								          ex.printStackTrace();
								       }
									break;
								}
							
						}
					
					}
					
				}
				if (totinsp==totniv)
				{
					
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					 alert.setTitle(getText(R.string.attention));
						alert.setMessage(getText(R.string.confirmcloturett));
			  				alert.setPositiveButton(getText(R.string.oui),
			  						new DialogInterface.OnClickListener() {
			  							public void onClick(DialogInterface dialog, int whichButton) {
			  								 DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  								 Date maDate = new Date(); 
			  								@SuppressWarnings("deprecation")
			  								int day=maDate.getDay();
			  								Cloture cloture = new Cloture();
			  								cloture.setIdNiveau(idNiveauSit);
			  								cloture.setStatutCloture(1);
			  								cloture.setDateCloture(df2.format(lastDate));
			  								cloture.setDebDateCloture(df2.format(firstDate));
			  								cloture.setUserCloture(userId);
			  								cloture.setDayCloture(day);
			  								getHelper().getClotureDao().createOrUpdate(cloture);
			  								pddata(idNiveauSit);
			  								dialog.dismiss();
			  								Intent intent = new Intent(InspectionActivity.this, NiveauActivity.class);
			  								Bundle bundle = new Bundle();
			  								bundle.putInt("userId", userId);
			  								
			  								intent.putExtras(bundle);
			  								startActivity(intent);
			  								
			  								
			  							}
			  						});
					
					
			  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
			  					public void onClick(DialogInterface dialog, int whichButton) {
			  						dialog.dismiss();
			  					}
			  				});
			  				
			       	   
					AlertDialog alertDialog = alert.create();
					alertDialog.show();
			       	   
					
				}
				else
				{
					nextInspect3();
				}
				
		
	


		}
		
		
	}
	private void pddata (int idsite)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Parametrage param = getHelper().getParametrageDao().queryForId(1);
		Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
		Niveau nArea = getHelper().getNiveauDao().queryForId(idsite);
		
		StatusPI spiArea = new StatusPI();
		spiArea.setAreaTag(nArea.getAreaTag());
		spiArea.setTagDescription(nArea.getLibelle());
		spiArea.setStatus(1);
		spiArea.setTimestamp(sdf.format(new Date()));
		
		spiArea.setClientName(param.getCodeClient());
		getHelper().getStatusPIDao().create(spiArea);	
		if (user.getTypeUser().endsWith("S"))
		{
			List<Niveau> listNiveaub = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idsite);
			for (Niveau n : listNiveaub) {
				
				List<Niveau> listNiveaue = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(n.getIdNiveau());
				for (Niveau m : listNiveaue) {
					StatusPI spi = new StatusPI();
					spi.setTagName(m.getLibelle());
					NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(m.getIdNiveauObjet());
					if (no!=null)
					{
						spi.setTagDescription(no.getLibelle());
						spi.setLowLimit(String.valueOf(no.getLowlimit()));
						spi.setHighLimit(String.valueOf(no.getHighlimit()));
						spi.setLowBorder(String.valueOf(no.getLowborder()));
						spi.setHighBorder(String.valueOf(no.getHighborder()));
						spi.setTagUnit(no.getUnitmeasure());
					}
					// si c hs
					StatutHS statutHS=getHelper().getStatutHSDao().queryForId(n.getIdNiveau());
					 StatutInac statutInac =getHelper().getStatutInacDao().queryForId(m.getIdNiveau());
					if (statutHS !=null)
					{

						spi.setStatus(6);
						spi.setTimestamp(null);
						spi.setValue(null);
						
					}
					else if (statutInac!=null)
					{
						spi.setStatus(3);
						spi.setTimestamp(null);
						spi.setValue(null);
					}
					else
					{
Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(m.getIdNiveau(), m.getIdNiveauObjet());
						
						if (inspection!=null)
						{
							
							spi.setStatus(inspection.getStatut());
							spi.setTimestamp(inspection.getDateInformation());
							if (inspection.getReponse()!=null)
							spi.setValue(inspection.getReponse());
							else if (inspection.getIdChoix()!=null) //choix
							{
								Choix choix =getHelper().getChoixDao().queryForId(inspection.getIdChoix());
								spi.setValue(choix.getValeur());
								
							}
							else spi.setValue(null);
								
							
						}
						else
						{
							spi.setStatus(2);
							spi.setTimestamp(null);
							spi.setValue(null);
						}
					}
					
						
						
					spi.setClientName(param.getCodeClient());
					getHelper().getStatusPIDao().create(spi);	
				}
			}
			
			
			
			
			
		}//fin simple user
		else  // equipe queryWhereDaybyobjtbyuser
		{
			Date maDate = new Date(); 
			@SuppressWarnings("deprecation")
			int day=maDate.getDay();
			
			List<Niveau> listNiveaub = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idsite);
			for (Niveau n : listNiveaub) {
				
				List<Niveau> listNiveaue = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(n.getIdNiveau());
				for (Niveau m : listNiveaue) {
					StatusPI spi = new StatusPI();
					spi.setTagName(m.getLibelle());
					Objeteam objteam = getHelper().getObjeteamDao().queryWhereDaybyobjtbyuser(userId, day, m.getIdNiveauObjet());
					if (objteam !=null)
					{
					
					NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(m.getIdNiveauObjet());
					spi.setTagDescription(no.getLibelle());
					spi.setLowLimit(String.valueOf(no.getLowlimit()));
					spi.setHighLimit(String.valueOf(no.getHighlimit()));
					spi.setLowBorder(String.valueOf(no.getLowborder()));
					spi.setHighBorder(String.valueOf(no.getHighborder()));
					spi.setTagUnit(no.getUnitmeasure());
					// si c hs
					StatutHS statutHS=getHelper().getStatutHSDao().queryForId(n.getIdNiveau());
					 StatutInac statutInac =getHelper().getStatutInacDao().queryForId(m.getIdNiveau());
					if (statutHS !=null)
					{

						spi.setStatus(6);
						spi.setTimestamp(null);
						spi.setValue(null);
						
					}
					else if (statutInac!=null)
					{
						spi.setStatus(3);
						spi.setTimestamp(null);
						spi.setValue(null);
					}
					else
					{
Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(m.getIdNiveau(), m.getIdNiveauObjet());
						
						if (inspection!=null)
						{
							
							spi.setStatus(inspection.getStatut());
							spi.setTimestamp(inspection.getDateInformation());
							if (inspection.getReponse()!=null)
							spi.setValue(inspection.getReponse());
							else if (inspection.getIdChoix()!=null) //choix
							{
								Choix choix =getHelper().getChoixDao().queryForId(inspection.getIdChoix());
								spi.setValue(choix.getValeur());
								
							}
							else
								spi.setValue(null);
							
						}
						else
						{
							spi.setStatus(2);
							spi.setTimestamp(null);
							spi.setValue(null);
						}
					}
					
						
						
					spi.setClientName(param.getCodeClient());
					getHelper().getStatusPIDao().create(spi);
				}
				}
			}
		}
		
	}
	private boolean verifteam(Integer idobj)
	
	{
		boolean rt=false;
		Date maDate = new Date(); 
		 @SuppressWarnings("deprecation")
			int day=maDate.getDay();
		Objeteam objteam = getHelper().getObjeteamDao().queryWhereobjetday(idobj,userId, day);
		if (objteam!=null) rt=true;
		return rt;
	}
	private void nextInspect3()
	{
		boolean done=false;
		Utilisateur user = getHelper().getUtilisateurDao().queryForId(userId);
		NiveauObjet objetactuel = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
		 System.out.println("objetactuel"+objetactuel.getLibelle());
		 Niveau niveauactuel=getHelper().getNiveauDao().queryWhereidObjet(idNiveauObjet);
		 System.out.println("niveauactuel"+niveauactuel.getLibelle());
		 
		 if (user.getTypeUser().endsWith("S"))
		 {
			 //voir si la tournee est complete 
			 //compter le nombre d inspections dans la tournee:
			 List<Niveau> listnivainspecter = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(niveauactuel.getIdNiveauParent());
			 //compter nombre inspection faites:
			 List <Inspection> inspectiondone = getHelper().getInspectionDao().queryWhereIdNiveaubat(niveauactuel.getIdNiveauParent());
			 if (listnivainspecter.size()==inspectiondone.size()) done=true;
			 if (done) // retour analgenten
			 {
				 removeLastLibelle();
				 removeLastLibelle();
				 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
					
						Intent intent = new Intent(InspectionActivity.this,NiveausoloActivity.class);
						Bundle bundle = new Bundle();
						
						bundle.putInt("userId", userId);
						bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
						bundle.putString("chemin", getChemin());
						bundle.putBoolean("next",true);
						intent.putExtras(bundle);
						startActivity(intent);
					
			 }
			 else // afficher next inspection 
			 {
				 Niveau niveaunext=getHelper().getNiveauDao().queryWhereNextTri(niveauactuel.getIdNiveauParent(),niveauactuel.getTri());
				 
				 if (niveaunext!=null)
				 {
					 System.out.println("niveaunext"+niveaunext.getLibelle());
					 // modifier le chemin
					 removeLastLibelle();
					 this.listLibelles.add(niveaunext.getLibelle());
					 // definir l objet cible
					 NiveauObjet objetnext = getHelper().getNiveauObjetDao().queryForId(niveaunext.getIdNiveauObjet());
					if (objetnext!=null)
					{
						TypeReponse tr = getHelper().getTypeReponseDao().queryForId(objetnext.getIdTypereponse());
						Parametrage param = getHelper().getParametrageDao().queryForId(1);
						if (param.isCodebarre()==false)
						{
							idNiveauObjet=objetnext.getIdNiveauobjet();
						chemin=getChemin();
						idNiveau=niveaunext.getIdNiveau();
						idNiveaubat=niveaunext.getIdNiveauParent();
						bindData();
						}
					else if (tr.getModeReponse().equals("Valeur"))
					{
						idNiveauObjet=objetnext.getIdNiveauobjet();
						chemin=getChemin();
						idNiveau=niveaunext.getIdNiveau();
						idNiveaubat=niveaunext.getIdNiveauParent();
						bindData();
					}
					else
					{
						// ici code barre 
						finish();
						/*removeLastLibelle();
						 removeLastLibelle();
						 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
							
								Intent intent = new Intent(InspectionActivity.this,NiveausoloActivity.class);
								Bundle bundle = new Bundle();
								
								bundle.putInt("userId", userId);
								bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
								bundle.putString("chemin", getChemin());
								bundle.putBoolean("next",true);
								intent.putExtras(bundle);
								startActivity(intent);
						
				*/	}	
						
						
					}
					else
					{
						Toast.makeText(this, getText(R.string.nooffenbjet), Toast.LENGTH_LONG)
						.show();
						finish();
					}
					 	
					
					
					 
				 }
				 else
				 {
					 finish();
				 }
			 }
			 
			
			 
			 
			 
		 }// end simple user
		 else // equipe 
		 {
			 Date maDate = new Date(); 
			 @SuppressWarnings("deprecation")
				int day=maDate.getDay();
			 // voir si la tournee est complete
			 // compter nbre inspection a faire dans la loop
			 Integer nbrinspafaire=0;
			 List<Niveau> listnivainspecter = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(niveauactuel.getIdNiveauParent());
			 for (Niveau nb1 : listnivainspecter) {
						
					Objeteam objteam = getHelper().getObjeteamDao().queryWhereobjetday(nb1.getIdNiveauObjet(),userId, day);
					if (objteam!=null)
					{
						nbrinspafaire++;
					}
				}
				// compter le nombre des inspections faites:
				List <Inspection> inspectionsfaites = getHelper().getInspectionDao().queryWhereIdNiveaubat(niveauactuel.getIdNiveauParent());
				Integer totalinspfaites=0;
				for (Inspection in1 : inspectionsfaites) {
					Objeteam objteam = getHelper().getObjeteamDao().queryWhereobjetday(in1.getIdNiveauObjet(),userId, day);
					if (objteam!=null)
					{
						totalinspfaites++;
					}
				}
					
				if ( totalinspfaites==nbrinspafaire) done=true;
			if (done)
			{
				// fin de loop et revenir a analgenten
				 removeLastLibelle();
				 removeLastLibelle();
				 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
					
						Intent intent = new Intent(InspectionActivity.this,NiveauActivity.class);
						Bundle bundle = new Bundle();
						
						bundle.putInt("userId", userId);
						bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
						bundle.putString("chemin", getChemin());
						bundle.putBoolean("next",true);
						intent.putExtras(bundle);
						startActivity(intent);
				}
			else
			{
				// on cherche le next objet à inspecter
				Niveau niveaunext=getHelper().getNiveauDao().queryWhereNextTri(niveauactuel.getIdNiveauParent(),niveauactuel.getTri());
				while (( niveaunext!=null)	&& (verifteam(niveaunext.getIdNiveauObjet())==false))
				{
					niveaunext=getHelper().getNiveauDao().queryWhereNextTri(niveaunext.getIdNiveauParent(),niveaunext.getTri());
					
				}
				if (niveaunext!=null)
				{
					System.out.println("niveaunext"+niveaunext.getLibelle());
					 // modifier le chemin
					 removeLastLibelle();
					 this.listLibelles.add(niveaunext.getLibelle());
					 // definir l objet cible
					 NiveauObjet objetnext = getHelper().getNiveauObjetDao().queryForId(niveaunext.getIdNiveauObjet());
					if (objetnext!=null)
					{
						TypeReponse tr = getHelper().getTypeReponseDao().queryForId(objetnext.getIdTypereponse());
						Parametrage param = getHelper().getParametrageDao().queryForId(1);
						if (param.isCodebarre()==false)
						{
							idNiveauObjet=objetnext.getIdNiveauobjet();
						chemin=getChemin();
						idNiveau=niveaunext.getIdNiveau();
						idNiveaubat=niveaunext.getIdNiveauParent();
						bindData();
						}
					else if (tr.getModeReponse().equals("Valeur"))
					{
						idNiveauObjet=objetnext.getIdNiveauobjet();
						chemin=getChemin();
						idNiveau=niveaunext.getIdNiveau();
						idNiveaubat=niveaunext.getIdNiveauParent();
						bindData();
					}
					else
					{
						// ici code barre 
						finish();
						/*removeLastLibelle();
						 removeLastLibelle();
						 Niveau nivbatenv=getHelper().getNiveauDao().queryForId(niveauactuel.getIdNiveauParent());
							
								Intent intent = new Intent(InspectionActivity.this,NiveauActivity.class);
								Bundle bundle = new Bundle();
								
								bundle.putInt("userId", userId);
								bundle.putInt("idniveau",nivbatenv.getIdNiveauParent());//monter un niv
								bundle.putString("chemin", getChemin());
								bundle.putBoolean("next",true);
								intent.putExtras(bundle);
								startActivity(intent);
					*/}
						
						
					}
					else
					{
						Toast.makeText(this, getText(R.string.nooffenbjet), Toast.LENGTH_LONG)
						.show();
						finish();
					}
					 
				}
				else
				{
					finish();
				}
				
			}
				
			 
			 
			 
			 
			 
		 }// end equipe
		
	}
	private void removeLastLibelle() {
		System.out.println("lib");
		if (listLibelles.size() > 0) {
			listLibelles.remove(listLibelles.size() - 1);
		}
	}
	private String getChemin() {
		StringBuilder sb = new StringBuilder();
		Boolean first = true;
		for (String s : listLibelles) {
			if (!first)
				sb.append(" > ");
			sb.append(s);
			first = false;
		}

		return sb.toString();
	}
	
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private EditText editDate;

		public EditText getEditDate() {
			return editDate;
		}

		public void setEditDate(EditText editDate) {
			this.editDate = editDate;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			String dateStr = editDate.getText().toString();
			SimpleDateFormat format = new SimpleDateFormat(Global.DATE_FORMAT_VIEW, Locale.FRENCH);

			try {
				Date d = format.parse(dateStr);
				c.setTime(d);
			} catch (ParseException e) {
				Log.e(this.getClass().getName(), e.getMessage());
			}

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			GregorianCalendar c = new GregorianCalendar(year, month, day);
			Date d = c.getTime();
			SimpleDateFormat format = new SimpleDateFormat(Global.DATE_FORMAT_VIEW, Locale.FRENCH);
			editDate.setText(format.format(d));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
		String lib=no.getLibelle();
		int i=lib.indexOf("Begehung");
		if (i>0)
		{
			inflater.inflate(R.menu.inspectbegehungen, menu);
		}
		else
			inflater.inflate(R.menu.inspect, menu);
					
				
				
			
			
			return true;
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        //On regarde quel item a été cliqué grâce à son id et on déclenche une action
        switch (item.getItemId()) {
           case R.id.menu_historique:
             voirHistorique();
               return true;
               
           case R.id.menu_Rundgang:
               interrompre ();
               return true;
           case R.id.menu_cb:
         	   codebarreinacc();
                return true;     
           case R.id.menu_historiqueb:
               voirHistorique();
                 return true;
                 
             case R.id.menu_Rundgangb:
                 interrompre ();
                 return true;
        }
        return false;}
	 public void codebarreinacc()
	 {
		 Niveau niveauxp = getHelper().getNiveauDao().queryForId(idNiveaubat); 
			Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
			 
			if (cloture!=null)
			{
				Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
				.show();
			}
			else
			{
				
				 
					 StatutInac statutInac=getHelper().getStatutInacDao().queryForId(idNiveau);
					 if (statutInac!=null)
						 Toast.makeText(this, getText(R.string.interdiblokacce), Toast.LENGTH_LONG)
							.show(); 
					 else 
					 {
						 List <Inspection> inspection = getHelper().getInspectionDao().queryWhereIdNiveau(idNiveauObjet);
						 
								if (inspection.size()==0)
								{
									AlertDialog.Builder alert = new AlertDialog.Builder(this);
									 alert.setTitle(getText(R.string.attention));
										alert.setMessage(getText(R.string.confirminacc));
							  				alert.setPositiveButton(getText(R.string.oui),
							  						new DialogInterface.OnClickListener() {
							  							public void onClick(DialogInterface dialog, int whichButton) {
							  								//onBackPressed();
							  								codebarreinacc2();
							  								dialog.dismiss();
							  							}
							  						});
									
									
							  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
							  					public void onClick(DialogInterface dialog, int whichButton) {
							  						dialog.dismiss();
							  					}
							  				});
							  				
							       	   
									AlertDialog alertDialog = alert.create();
									alertDialog.show();
								}
								else
									 Toast.makeText(this, getText(R.string.interdiblokacce2), Toast.LENGTH_LONG)
										.show();	
						 
					 }
					 
				 
			}
		 
		 
		
		
	 }
	 public void codebarreinacc2()
	 {
		 DateFormat df = new SimpleDateFormat(Global.DATETIME_FORMAT_DATA, Locale.GERMAN);
				
		 StatutInac statutInac = new StatutInac();
		 statutInac.setIdNiveau(idNiveau);
		 statutInac.setStatutValue(true);
		 
		 statutInac.setObjet(idNiveauObjet);
		 statutInac.setDateinacc(df.format(new Date()));
		 getHelper().getStatutInacDao().createOrUpdate(statutInac);
		 nextInspect3();
	 }
	public void voirHistorique()
	{
		List<Historique> historiqueList = getHelper().getHistoriqueDao().queryWhereIdObjet(idNiveauObjet);
		if (historiqueList.size()==0)
			Toast.makeText(this, getText(R.string.interdithistoire), Toast.LENGTH_LONG)
			.show();
		else
		{
			Intent intent = new Intent(InspectionActivity.this, HistoriqueActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("idNiveauObjet", idNiveauObjet);
			
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}
	public void interrompre ()
	{
		Niveau niveauxp = getHelper().getNiveauDao().queryForId(idNiveaubat); //site
	Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
	if (cloture==null)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		 alert.setTitle(getText(R.string.attention));
			alert.setMessage(getText(R.string.confirmpauseI));
  				alert.setPositiveButton(getText(R.string.oui),
  						new DialogInterface.OnClickListener() {
  							public void onClick(DialogInterface dialog, int whichButton) {
  								savepause();
  								dialog.dismiss();
  							}
  						});
		
		
  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
  					public void onClick(DialogInterface dialog, int whichButton) {
  						dialog.dismiss();
  					}
  				});
  				
       	   
		AlertDialog alertDialog = alert.create();
		alertDialog.show();
	}
	else
		Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
		.show();	
		
	}
	public void savepause()
	{
		
		Inspection inspection = getHelper().getInspectionDao().queryWhereIdNiveauEtObjet(idNiveau,
				idNiveauObjet);

		if (inspection == null) {
			

			inspection = new Inspection();
			
			
			
			UUID uuid = UUID.randomUUID();
			inspection.setIdInformation(uuid.toString());
		}
		DateFormat df = new SimpleDateFormat(Global.DATETIME_FORMAT_DATA, Locale.GERMAN);
		inspection.setDateInformation(df.format(new Date()));
		inspection.setIdNiveau(idNiveau);
		inspection.setIdNiveauObjet(idNiveauObjet);
		inspection.setIdNiveaubat(idNiveaubat);
		inspection.setPause(true);
		

		getHelper().getInspectionDao().createOrUpdate(inspection);
		finish();
	}
	@Override
	 protected Dialog onCreateDialog(int id) {
	 
	  AlertDialog dialogDetails = null;
	 
	  switch (id) {
	  case DIALOG_LIMIT:
	   LayoutInflater inflater = LayoutInflater.from(this);
	   View dialogview = inflater.inflate(R.layout.dialog_limit, null);
	 
	   AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
	   dialogbuilder.setTitle(getText(R.string.attention));
	   dialogbuilder.setView(dialogview);
	   dialogDetails = dialogbuilder.create();
	 
	   break;
	 
	  }
	 
	  return dialogDetails;
	 }
	
	
	 @Override
	 protected void onPrepareDialog(int id, Dialog dialog) {
	 
	  switch (id) {
	  case DIALOG_LIMIT:
	   final AlertDialog alertDialog = (AlertDialog) dialog;
	   Button loginbutton = (Button) alertDialog
	     .findViewById(R.id.btn_savelimit);
	   Button cancelbutton = (Button) alertDialog
	     .findViewById(R.id.btn_cancellimit);
	   TextView txtlimit=(TextView) alertDialog
			     .findViewById(R.id.limitdial);
	   txtlimit.setText(msgL);
	 
	   loginbutton.setOnClickListener(new View.OnClickListener() {
		   
		    @Override
		    public void onClick(View v) {
		     
		    	saveData(true);
		    	alertDialog.dismiss();
			 
		    }
		   });
		 
		   cancelbutton.setOnClickListener(new View.OnClickListener() {
		 
		    @Override
		    public void onClick(View v) {
		     alertDialog.dismiss();
		     editValuerep.setText("");
		    
			 
		    }
		   });
		   break;

		  }
		 }
	 
}
