package com.coralc.inspectbox;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import com.coralc.inspectbox.adapter.ContenuNiveauAdapter;
import com.coralc.inspectbox.database.Choix;
import com.coralc.inspectbox.database.Cloture;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Inspection;
import com.coralc.inspectbox.database.LibelleNiveau;
import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.StatusPI;
import com.coralc.inspectbox.database.StatutHS;
import com.coralc.inspectbox.database.StatutInac;
import com.coralc.inspectbox.database.TypeReponse;
import com.coralc.inspectbox.pojo.ContenuNiveau;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class NiveausoloActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	// Widgets
	private ListView lvNiveau;
	private TextView txtChemin;
	private TextView txtLibNiveau;
	LinearLayout mTagContent;
	// Variables
	private Integer niveauHS;
	private Integer niveauObjetHS;
	private String libelleHS;
	private String iconHS="";
	
	private NfcAdapter mNfcAdapter;
	 private PendingIntent mPendingIntent;
	 private static IntentFilter[] mIntentFilters;
	 private static String[][] mNFCTechLists;
	 // pile des niveaux
	private Stack<Integer> idNiveaux = new Stack<Integer>();
	private Date lastDate ;
	private Date firstDate ;
	//private String lastDateString ;
	//private String firstDateString ;
	
	// pile des id niveau objet
	private Stack<Integer> idNiveauxObjet = new Stack<Integer>();
	// pour le chemin
	private ArrayList<String> listLibelles = new ArrayList<String>();
private Integer userId;
private Integer idniveau;
private Boolean next;
private String cheminnext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.niveau);
		
		
		// on restore l'état précédent si nécessaire
		if (savedInstanceState != null) {
			idNiveaux.clear();
			idNiveauxObjet.clear();
			idNiveaux.addAll(savedInstanceState.getIntegerArrayList("idNiveaux"));
			idNiveauxObjet.addAll(savedInstanceState.getIntegerArrayList("idNiveauxObjet"));
			listLibelles = savedInstanceState.getStringArrayList("listLibelles");
		}

		// Initalisation des widgets
		txtChemin = (TextView) findViewById(R.id.txtChemin);
		lvNiveau = (ListView) findViewById(R.id.lvNiveau);
		txtLibNiveau = (TextView) findViewById(R.id.txtLibNiveau);
		 mTagContent = (LinearLayout) findViewById(R.id.niveau);
		Intent intent = this.getIntent();
		userId = intent.getIntExtra("userId", 0);
		idniveau=intent.getIntExtra("idniveau", 0);
		next=intent.getBooleanExtra("next", false);
		cheminnext=intent.getStringExtra("chemin");
		if (next)
		{
			String[] splitArray = null;
			 splitArray = cheminnext.split(">");
			 
			  for(int i = 0; i< splitArray.length;i++){
				  System.out.println("string ici"+splitArray[i]);
				  this.listLibelles.add(splitArray[i]);
			  }
		idNiveaux.push(0);
		idNiveaux.push(idniveau);
		}

		// s'il y a eut un changement de config, on recharge ces valeurs
		if (savedInstanceState != null) {
			userId = savedInstanceState.getInt("userId");
			
		}
	
		
	   
		// Binding des évènements
		lvNiveau.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ContenuNiveau c = (ContenuNiveau) parent.getItemAtPosition(position);

				niveauHS=c.getIdNiveau();
				
				niveauObjetHS=c.getIdNiveauObjet();
				if (c.getIdTypeReponse()!=0)
				libelleHS="O";
				else
					libelleHS="N";
				if ((c.getIcone()==R.drawable.gris)|| (c.getIcone()==R.drawable.noir))
					iconHS="Y";
						
			}
		});
	

		lvNiveau.setOnItemLongClickListener(new  OnItemLongClickListener() {
			public boolean  onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				ContenuNiveau c = (ContenuNiveau) parent.getItemAtPosition(position);
				
				
				// Si le type de réponse de l'élément sélectionné est vide
				// on est encore dans une liste
				//if (c.getIdTypeReponse() == 0) {
				//if (c.getIdNiveauObjet() == 0)  {
				if (c.getLevel()!= 3) {
					if ((c.getIcone()==R.drawable.noir) && (c.getIdNiveauParent()!=0))
					{
						Toast.makeText(NiveausoloActivity.this, getText(R.string.objeths), Toast.LENGTH_LONG)
						.show();
					}
					else
					{
						listLibelles.add(c.getLibelle());

						if (c.getIdNiveau() != 0) {
							idNiveaux.push(c.getIdNiveau());
						}
						if (c.getIdNiveauObjet() != 0) {
							idNiveauxObjet.push(c.getIdNiveauObjet());
						}
						
						bindData();
					}
					
					
					niveauHS=0;
					niveauObjetHS=0;
					libelleHS="";
					iconHS="";
					} else {
					// le type réponse != 0
					// on est dans un élément de contrôle
						if (c.getIdNiveauObjet()==0)
						{
							Toast.makeText(NiveausoloActivity.this, getText(R.string.emptyTag), Toast.LENGTH_LONG)
							.show();
						}
						else
						{
							niveauHS=0;
							niveauObjetHS=0;
							libelleHS="";
							iconHS="";
							NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(c.getIdNiveauObjet());
							TypeReponse tr = getHelper().getTypeReponseDao().queryForId(no.getIdTypereponse());
							Parametrage param = getHelper().getParametrageDao().queryForId(1);
							if (tr!=null)
							{
								if(tr.getModeReponse().equals("Valeur"))
								{
									listLibelles.add(c.getLibelle());
									System.out.println("getChelin()"+getChemin());
									Niveau niveau=getHelper().getNiveauDao().queryForId(c.getIdNiveau());
									Intent intent = new Intent(NiveausoloActivity.this, InspectionActivity.class);
									Bundle bundle = new Bundle();
									bundle.putInt("idNiveauObjet", c.getIdNiveauObjet());
									bundle.putInt("idNiveau", c.getIdNiveau());
									bundle.putString("chemin", getChemin());
									bundle.putString("mapPath", getMapPath());
									bundle.putInt("idNiveaubat", niveau.getIdNiveauParent());
									bundle.putInt("userId", userId);
									
									intent.putExtras(bundle);
									startActivity(intent);
									removeLastLibelle();
								}
								
								else //list
									
								{
									if (param.isCodebarre()==false)
									{ listLibelles.add(c.getLibelle());
									Niveau niveau=getHelper().getNiveauDao().queryForId(c.getIdNiveau());
									Intent intent = new Intent(NiveausoloActivity.this, InspectionActivity.class);
									Bundle bundle = new Bundle();
									bundle.putInt("idNiveauObjet", c.getIdNiveauObjet());
									bundle.putInt("idNiveau", c.getIdNiveau());
									bundle.putString("chemin", getChemin());
									bundle.putString("mapPath", getMapPath());
									bundle.putInt("idNiveaubat", niveau.getIdNiveauParent());
									bundle.putInt("userId", userId);
									
									intent.putExtras(bundle);
									startActivity(intent);
									removeLastLibelle();
								}
									else Toast.makeText(NiveausoloActivity.this, getText(R.string.mustcodebarre), Toast.LENGTH_LONG)
									.show();
								}
								
							}
							else
							{
								makeAlert();
							}
						}
						
						
						
						
						
					
				}
				return true; }

			
		});
		mNfcAdapter  = NfcAdapter.getDefaultAdapter(this);											
		if (mNfcAdapter  == null) {
			 
		}
		// Android system will populate it with the details of the tag when it is scanned
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
 
		//Launched by tag scan ? 
		Tag tag = (Tag)getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
		if(tag != null && (getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)
				|| getIntent().getAction().equals(NfcAdapter.ACTION_TECH_DISCOVERED) 
				|| getIntent().getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED))){			
			 
			getNewTag(tag, getIntent());
			//Clear intent
			setIntent(null);
		}	
	}

	
	
	static{
		 
		// add intent filter
		IntentFilter mndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);				
		IntentFilter mtech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		IntentFilter mtag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		try {
			//Handles all MIME based dispatches !!! specify only the ones that you need.
			mndef.addDataType("*/*");   															
 
		}
		catch (MalformedMimeTypeException e) {
			throw new RuntimeException("fail", e);
		}
 
		mIntentFilters = new IntentFilter[] {mndef,mtech,mtag };
		//array of TAG TECHNOLOGIES that your application wants to handle 
		mNFCTechLists = new String[][] { new String[] { NfcA.class.getName()},				
				new String[] {NfcB.class.getName()},
				new String[] {NfcV.class.getName()}, 
				new String[] {IsoDep.class.getName()}, 
				new String[] {Ndef.class.getName()}};
	}
	private void getNewTag(Tag tag, Intent intent){
		if(tag == null) return;                              
        //Indicate to childs that a new tag has been detected
		onNewTag(tag);				
	}
	public void onNewTag(Tag tag){}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		// TODO: Peut être mieux que onResume, à tester si le onResume ne
		// fonctionne pas comme voulu
	}
	static String bin2hex(byte[] data) {
	    return String.format("%0" + (data.length * 2) + "X", new BigInteger(1,data));
	}
	
	 @Override
	    public void onNewIntent(Intent intent) {
		 
	        
		 Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			getNewTag(tag, intent);  
		
		        
		        String s1 = bin2hex(tag.getId());
	        
		        if (!s1.equals(""))
		        {
		        	Niveau nv = getHelper().getNiveauDao().queryWherecodebarre(s1);
		        	if (nv!=null)
		        	{

	        			
	        			
	        			Niveau nvb = getHelper().getNiveauDao().queryForId(nv.getIdNiveauParent());
        				Niveau nvs = getHelper().getNiveauDao().queryForId(nvb.getIdNiveauParent());
        				
        				listLibelles.clear();
        				idNiveaux.clear();
        				
        				listLibelles.add(nvs.getLibelle());
        				idNiveaux.push(nvs.getIdNiveau());
        				
    					idNiveaux.push(nvb.getIdNiveau());
    					listLibelles.add(nvb.getLibelle());
    					
    					idNiveaux.push(nv.getIdNiveau());
    					listLibelles.add(nv.getLibelle());
    					
    					Intent intent2 = new Intent(NiveausoloActivity.this, InspectionActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("idNiveauObjet", nv.getIdNiveauObjet());
						bundle.putInt("idNiveau", nv.getIdNiveau());
						bundle.putString("chemin", getChemin());
						bundle.putString("mapPath", getMapPath());
						bundle.putInt("idNiveaubat", nv.getIdNiveauParent());
						bundle.putInt("userId", userId);
						intent2.putExtras(bundle);
						startActivity(intent2);
						removeLastLibelle();
						removeLastNiveauOuObjet();
		        	}
		        	else // code barre existe pas
		        	{
		        		AlertDialog.Builder alert = new AlertDialog.Builder(this);
						 alert.setTitle(getText(R.string.attention));
						 alert.setIcon(R.drawable.alert);
							alert.setMessage(getText(R.string.noncode));
				   				alert.setPositiveButton(getText(R.string.ok),
				   						new DialogInterface.OnClickListener() {
				   							public void onClick(DialogInterface dialog, int whichButton) {
				   								
				   								dialog.dismiss();
				   							}
				   						});
						
						
				   				
				   				
				        	   
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
		        	}
		        }
		        else
		        {
		        	AlertDialog.Builder alert = new AlertDialog.Builder(this);
					 alert.setTitle(getText(R.string.erreur));
					 alert.setIcon(R.drawable.error);
						alert.setMessage(getText(R.string.emptycode));
			   				alert.setPositiveButton(getText(R.string.ok),
			   						new DialogInterface.OnClickListener() {
			   							public void onClick(DialogInterface dialog, int whichButton) {
			   								
			   								dialog.dismiss();
			   							}
			   						});
					
					
			   				
			   				
			        	   
					AlertDialog alertDialog = alert.create();
					alertDialog.show();
		        }
	       
	    }
	@Override
	protected void onResume() {
		super.onResume();
		
		// Binding des données
		bindData();
		if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
    
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putIntegerArrayList("idNiveaux", new ArrayList<Integer>(idNiveaux));
		outState.putIntegerArrayList("idNiveauxObjet", new ArrayList<Integer>(idNiveauxObjet));
		outState.putStringArrayList("listLibelles", listLibelles);
	};

	@Override
	public void onBackPressed() {
		
		if (idNiveaux.empty() && idNiveauxObjet.empty()) {
			Intent intent = new Intent(this, AccueilActivity.class);
			startActivity(intent);
		} else {
			Niveau niv=getHelper().getNiveauDao().queryForId(getLastIdNiveau());
			Cloture cloture=getHelper().getClotureDao().queryWhereniveau(getLastIdNiveau());
			
		if (niv!=null)
		{
			if ((niv.getIdNiveauParent()==0)&& (cloture==null))
			{
				cloture();
			}
			else
			{
				removeLastNiveauOuObjet();
				removeLastLibelle();
				bindData();
				
			}
		}
		else
		{
			removeLastNiveauOuObjet();
			removeLastLibelle();
			bindData();
		}
			
			
		}
	}
	
	/*
	 * lire les bat non terminé
	 */
	
	private void makeAlert()
	{
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle(R.string.erreur)
	           .setMessage(getText(R.string.errortab))
	            .setCancelable(false)
	            .setIcon(R.drawable.error)
	            
	           .setNeutralButton("OK", new DialogInterface.OnClickListener() {
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                     }
	            });
	 
	        builder.create().show();        // create and show the alert dialog
	 
	}
	private void bindData() {
		int idNiveau = getLastIdNiveau();
		int idNiveauObjet = getLastIdNiveauObjet();

		// on affiche le chemin
		
		txtChemin.setText(getChemin());
		invalidateOptionsMenu();
		// on rempli la liste des ContenuNiveau avec des niveaux ou des niveaux
		// objet
		List<ContenuNiveau> listContenuNiveau = null;
		Integer lev=0;
		if (idNiveauObjet != 0) {
			
			List<NiveauObjet> listNiveauxObjet = getHelper().getNiveauObjetDao()
					.queryWhereIdObjetParentEqOrderByTri(idNiveauObjet);
			
			listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveauxObjet.size());
			for (NiveauObjet n : listNiveauxObjet) {
				
				listContenuNiveau.add(new ContenuNiveau(n));
				
			}


		} else {
			
			List<Niveau> listNiveaux = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(
					idNiveau);
			
			listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveaux.size());
			
			for (Niveau n : listNiveaux) {
				
				listContenuNiveau.add(new ContenuNiveau(n));
				
				
			}
for (Niveau n : listNiveaux) {
				if (n.getIdNiveauParent()==0)lev=1;
				else if (n.getIdNiveauObjet()==0)lev=2;
				else lev=3;
				
				break;
			}

		}
							
			// On affiche le libellé du niveau en cours et le chemin
			
			LibelleNiveau libNiveau = getHelper().getLibelleNiveauDao().queryWhereNumAndTypeEq(
					lev, 1);
			
			
		//LibelleNiveau libNiveau = getHelper().getLibelleNiveauDao().queryWhereNumAndTypeEq(
			//	getNum(listContenuNiveau), getTypeLibelleNiveau(listContenuNiveau));
		if (getNum(listContenuNiveau)==1 && getTypeLibelleNiveau(listContenuNiveau)==1)
			{
			txtChemin.setText("");
			listLibelles.clear();
			}
		
		if (libNiveau != null) {
			
			txtLibNiveau.setText(libNiveau.getLibelle());
		}

		getMapPath();
		for (ContenuNiveau c : listContenuNiveau) {
			if ((c.getIdNiveauParent()!=0) )
			{
					if (c.getIdNiveauObjet()==0) //bat
					{ 
						StatutHS statutHS=getHelper().getStatutHSDao().queryForId(c.getIdNiveau());
						Cloture cloture=getHelper().getClotureDao().queryWhereniveau(getLastIdNiveau());
						List<Niveau> listeq = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(c.getIdNiveau());
					
					
					
						List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubat(c.getIdNiveau());
						List <Inspection> inspectionbatdefect = getHelper().getInspectionDao().queryWhereIdNiveaubatisdefect(c.getIdNiveau());
						List <Inspection> inspectionbatlimit = getHelper().getInspectionDao().queryWhereIdNiveaubatislimit(c.getIdNiveau());
						
						
						if (statutHS!=null)
						{
							c.setIcone(R.drawable.noir);
						}
						else 
						{
							if (cloture!=null)
							{
								if (( inspectionbatdefect.size()>0)&& (inspectionbat.size()==listeq.size())) 	c.setIcone(R.drawable.jaune1);
								else if (( inspectionbatdefect.size()>0) && (inspectionbat.size()!=listeq.size())) 	c.setIcone(R.drawable.rouge);
								else  c.setIcone(R.drawable.vert);
											
								
							}//end cloture!null
							else
							{
								if( inspectionbat.size()==0) 				c.setIcone(R.drawable.gris);
								else if (( inspectionbatdefect.size()>0)) 	c.setIcone(R.drawable.rouge);
								else if (( inspectionbatlimit.size()>0)) 	c.setIcone(R.drawable.jaune1);
								else if (inspectionbat.size()!=listeq.size() )    c.setIcone(R.drawable.violet);
								else if ((inspectionbat.size()==listeq.size() )) 
									{
									boolean pause=false;
									for (Inspection in : inspectionbat) {
										if (in.isPause()==true)
										{
										pause=true;
										break;
										}
									}
									if (pause==true) 							c.setIcone(R.drawable.violet);
									
									else										c.setIcone(R.drawable.vert);
										
									}
												
							}
						}
					
					
					
					
					
						
					} //end bat
						
						
						
						
					else //eq
					{	
						
						String newligne=System.getProperty("line.separator"); 
					if (c.getIdNiveauObjet()!=0)
					{
						NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(c.getIdNiveauObjet());
						if (! no.getLibelle().equals("")) c.setLibelle(c.getLibelle()+newligne+no.getLibelle());
											
					}
						Niveau niveauxp = getHelper().getNiveauDao().queryForId(getLastIdNiveau()); //site
						Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
						
						StatutInac statutInac=getHelper().getStatutInacDao().queryForId(c.getIdNiveau());
						if (statutInac!=null)
						{
							c.setIcone(R.drawable.inacc);
						}
						else
						{
							List <Inspection> inspectiondefect = getHelper().getInspectionDao().queryWhereIdNiveauisdefect(c.getIdNiveau());
							List <Inspection> inspectionlimit = getHelper().getInspectionDao().queryWhereIdNiveauislimit(c.getIdNiveau());
							Inspection inspectionone= getHelper().getInspectionDao().queryWhereIdNiveauONE(c.getIdNiveau());
							
							if (cloture !=null)
							{
								if (( inspectiondefect.size()>0)) 			c.setIcone(R.drawable.rouge);
								else if (( inspectionlimit.size()>0)) 		c.setIcone(R.drawable.jaune1);
								else  if 	(inspectionone!=null	)		c.setIcone(R.drawable.vert);
								else  										c.setIcone(R.drawable.gris); 
							}
							else
							{
								if (( inspectiondefect.size()>0)) 			c.setIcone(R.drawable.rouge);
								else if (( inspectionlimit.size()>0)) 			c.setIcone(R.drawable.jaune1);
								else if (inspectionone==null) 				c.setIcone(R.drawable.gris);
								else if (inspectionone.isPause()==true)		c.setIcone(R.drawable.violet);
								else 										c.setIcone(R.drawable.vert);				
							}
							
						}
						
						
						
					}
					
				
				
				
				
				
		
		} //end if not site
else 
{

	Cloture cloture=getHelper().getClotureDao().queryWhereniveau(c.getIdNiveau());
	int nbreq=0;
	int nbins=0;
	int nbinsd=0;
	int nbinsl=0;
	boolean hs=false;
	boolean pause=false;
List<Niveau> listbat = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(c.getIdNiveau());
for (Niveau nb1 : listbat) {
	StatutHS statutHS=getHelper().getStatutHSDao().queryForId(nb1.getIdNiveau());
	if (statutHS!=null)
	{
		c.setIcone(R.drawable.noir);
		hs=true;
		break;
	}
	
}

if (hs==false)
{
	for (Niveau nb1 : listbat) {
		
		List<Niveau> listeq = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(nb1.getIdNiveau());
		nbreq+=listeq.size();
		
		
		List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubat(nb1.getIdNiveau());
		nbins+=inspectionbat.size();
		List <Inspection> inspectionbatdefect = getHelper().getInspectionDao().queryWhereIdNiveaubatisdefect(nb1.getIdNiveau());
		nbinsd+=inspectionbatdefect.size();
		List <Inspection> inspectionbatlimit = getHelper().getInspectionDao().queryWhereIdNiveaubatislimit(nb1.getIdNiveau());
		nbinsl+=inspectionbatlimit.size();
		for (Inspection in : inspectionbat) {
			if (in.isPause()==true)
			{
			pause=true;
			break;
			}
		}
	}
			if (cloture!=null)
			{
				if (( nbinsd>0)&& (nbins==nbreq)) 	c.setIcone(R.drawable.jaune1);
				else if (( nbinsd>0) && (nbins!=nbreq)) 	c.setIcone(R.drawable.rouge);
				else  c.setIcone(R.drawable.vert);
							
				
			}//end cloture!null
			else
			{
				if( nbins==0) 				c.setIcone(R.drawable.gris);
				else if (( nbinsd>0)) 	c.setIcone(R.drawable.rouge);
				else if (( nbinsl>0)) 	c.setIcone(R.drawable.jaune1);
				else if (nbins!=nbreq )    c.setIcone(R.drawable.violet);
				else if ((nbins==nbreq )) 
					{
					
					
					if (pause==true) 							c.setIcone(R.drawable.violet);
					
					else										c.setIcone(R.drawable.vert);
						
					}
								
			}
		
		
	
}

				
}
			
				
			
			}  //end for 
		Integer pos=0;
		for (ContenuNiveau c : listContenuNiveau) {
			if (c.getIdNiveauObjet()!=0)
			{
				
				Inspection inspectionpos= getHelper().getInspectionDao().queryWhereIdNiveauONE(c.getIdNiveau());
				if (inspectionpos!=null) 
				{
					pos++;
					
				}
				else
					break;
				
			}
		}
		// enfin on affiche la liste
		
		ContenuNiveauAdapter adapter = new ContenuNiveauAdapter(this, R.layout.listview_item_row,
				listContenuNiveau);

		lvNiveau.setAdapter(adapter);
		lvNiveau.setSelection(pos);
		adapter.notifyDataSetChanged(); 
		//reload the data, refresh is my own method, you can
		//use notifyDataSetChanged()/Invalidated()
		
	}


	/*
	 * retourne le numéro du niveau (la profondeur) profondeur la plus petite =
	 * 1
	 */
	private int getNum(List<ContenuNiveau> l) {
		
		for (ContenuNiveau c : l) {
			
			if (c.getIdNiveauObjet() != 0) {
				
				return idNiveauxObjet.size() + 1;
			}
		}
		System.out.println("idNiveaux.size() + 1***"+(idNiveaux.size()+1));
		return idNiveaux.size() + 1;
	}
	
	private int getTypeLibelleNiveau(List<ContenuNiveau> l) {
		for (ContenuNiveau c : l) {
			if (c.getIdNiveauObjet() != 0) {
				return LibelleNiveau.TYPE_NIVEAUOBJET;
			}
		}
		return LibelleNiveau.TYPE_NIVEAU;
	}
	
	/*
	 * Retourne le dernier id niveau retourne 0 si la pile est vide
	 */
	private int getLastIdNiveau() {
		if (idNiveaux.empty()) {
			return 0;
		} else {
			return idNiveaux.peek();
		}
	}

	/*
	 * Retourne le dernier id niveau objet retourne 0 si la pile est vide
	 */
	private int getLastIdNiveauObjet() {
		if (idNiveauxObjet.empty()) {
			return 0;
		} else {
			return idNiveauxObjet.peek();
		}
	}
	
	




	


	/*
	 * retire le dernier idNiveau ou idNiveauObjet empilé
	 */
	private void removeLastNiveauOuObjet() {
		if (!idNiveauxObjet.empty()) {
			idNiveauxObjet.pop();
			// cas particulier : lors de la transition entre un niveau et niveau
			// objet
			// on a empilé 1 idNiveauObjet ET un idNiveau
			// dans ce cas on doit dépiler les 2
			if (idNiveauxObjet.empty()) {
				if (!idNiveaux.empty()) {
					idNiveaux.pop();
				}
			}
		} else if (!idNiveaux.empty()) {
			idNiveaux.pop();
		}
	}

	private void removeLastLibelle() {
		System.out.println("lib");
		if (listLibelles.size() > 0) {
			listLibelles.remove(listLibelles.size() - 1);
		}
	}

	/*
	 * le mapPath est généré de la manière suivante : à chaque fois que l'on
	 * parcours un niveau, on ajoute "N + IdNiveau" à chaque fois que l'on
	 * parcours un niveau objet dont le TypeParam != 0 on ajoute
	 * "O + IdNiveauObjet" ex : N1N23N46O8 on enregistrera le mapPath dans la
	 * table des Statuts, avec le code statut cela servira à déterminer quel
	 * icône afficher dans les niveaux inférieurs
	 */
	private String getMapPath() {
		StringBuilder sb = new StringBuilder();
		// On ajoute les niveaux
		for (Object o : idNiveaux.toArray()) {
			Integer id = (Integer) o;
			sb.append("N").append(id);
		}
		// On ajoute les niveaux objet
		for (Object o : idNiveauxObjet.toArray()) {
			Integer id = (Integer) o;
			sb.append("O").append(id);
		}

		return sb.toString();
	}

	/*
	 * Construit le chemin pour l'affichage
	 */
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
boolean stop=false;
		// Création d'un MenuInflater qui va permettre d'instancier un Menu XML
		// en un objet Menu
if (getLastIdNiveau()!=0)
		{
	List<Niveau> listNiveau = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(getLastIdNiveau());
		
		if (listNiveau.size()!=0)
		{
			for (Niveau n : listNiveau) {
				if (n.getIdNiveauObjet()!=0)
					{
					stop=true;
					break;
					}
			}
			if (stop==false)
			{
				MenuInflater inflater = getMenuInflater();
				
				inflater.inflate(R.menu.login, menu);
			}
			else
			{
					MenuInflater inflater = getMenuInflater();
				
				inflater.inflate(R.menu.main_menu, menu);
			}
		}
		
		
		}
else
{
	MenuInflater inflater = getMenuInflater();
	
	inflater.inflate(R.menu.cloturemenu, menu);
}
			
			return true;
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
         //On regarde quel item a été cliqué grâce à son id et on déclenche une action
         switch (item.getItemId()) {
            
            case R.id.menu_anafangen:
            	onBackPressed();
                return true;
            case R.id.menu_beendensite:
            	clotureSite();
                return true;
            case R.id.menu_beenden:
            	cloture();
                return true;
                
            case R.id.menu_Betrieb:
         	   horservicedata();
                return true;
                
            case R.id.filtre_alls:
                bindData();
                 return true;
                 
            case R.id.filtre_offens:
                bindDataoffens();
                return true;
                
            case R.id.filtre_pbs:
                bindDatapbs();
                return true;
               
            case R.id.menu_retour:
         	   bindData();
                return true;
                
          /*  case R.id.menu_rundgang:
         	   interrompre();
                return true;
                */
            
         }
         return false;}
	
	
	public void codebarreinacc()
	 {
		 Niveau niveauxp = getHelper().getNiveauDao().queryForId(getLastIdNiveau()); //site
			Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
			if (cloture!=null)
			{
				Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
				.show();
			}
			else
			{
				
				 if ((niveauHS==0)|| (niveauHS== null))
				 {
					 Toast.makeText(this, getText(R.string.interditactions), Toast.LENGTH_LONG)
		 			.show();
				 }
				 else
				 {
					 StatutInac statutInac=getHelper().getStatutInacDao().queryForId(niveauHS);
					 if (statutInac!=null)
						 Toast.makeText(this, getText(R.string.interdiblokacce), Toast.LENGTH_LONG)
							.show(); 
					 else 
					 {
						 List <Inspection> inspection = getHelper().getInspectionDao().queryWhereIdNiveau(niveauHS);
						 
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
		 
		 
		
		
	 }
	public void codebarreinacc2()
	 {
		DateFormat df = new SimpleDateFormat(Global.DATETIME_FORMAT_DATA, Locale.FRENCH);
		
		 StatutInac statutInac = new StatutInac();
		 statutInac.setIdNiveau(niveauHS);
		 statutInac.setStatutValue(true);
		 statutInac.setObjet(niveauObjetHS);
		 statutInac.setDateinacc(df.format(new Date()));
		 getHelper().getStatutInacDao().createOrUpdate(statutInac);
		 bindData();
	 }
	public void interrompre()
	{
		Niveau niveauxp = getHelper().getNiveauDao().queryForId(getLastIdNiveau()); //site
		Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
		if (cloture!=null)
		{
			Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
			.show();
		}
		else
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			 alert.setTitle(getText(R.string.attention));
				alert.setMessage(getText(R.string.confirmpause));
	   				alert.setPositiveButton(getText(R.string.oui),
	   						new DialogInterface.OnClickListener() {
	   							public void onClick(DialogInterface dialog, int whichButton) {
	   								onBackPressed();
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
		
		
	}
	private void bindDatapbs() {
		int idNiveau = getLastIdNiveau();
		
		List<ContenuNiveau> listContenuNiveau = null;
		boolean ecrit=false;
		
		
		List<Niveau> listNiveaux = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idNiveau); //site
		listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveaux.size());
		for (Niveau n : listNiveaux) {
			
				List <Inspection> inspectionbatdefect = getHelper().getInspectionDao().queryWhereIdNiveauisdefect(n.getIdNiveau());
				if (inspectionbatdefect.size()>0)
				{
					
						listContenuNiveau.add(new ContenuNiveau(n));
						ecrit=true; 
						
						
					}	
			
		
			
			
				}
			
		if (ecrit==true)
		{
			for (ContenuNiveau c : listContenuNiveau) {
				
					c.setIcone(R.drawable.rouge);
				
			
		}
			ContenuNiveauAdapter adapter = new ContenuNiveauAdapter(this, R.layout.listview_item_row,
					listContenuNiveau);

			lvNiveau.setAdapter(adapter);
		}else
		{
			Toast.makeText(this, getText(R.string.nooffenbjet), Toast.LENGTH_LONG)
			.show();
		}
		
		
	}
	private void bindDataoffens() {
		int idNiveau = getLastIdNiveau();
		
		List<ContenuNiveau> listContenuNiveau = null;
		boolean ecrit=false;
		
		
		
		List<Niveau> listNiveaux = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idNiveau); //site
		listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveaux.size());
		for (Niveau n : listNiveaux) {
			
				List <Inspection> inspectionbatdefect = getHelper().getInspectionDao().queryWhereIdNiveauisdefect(n.getIdNiveau());
				Inspection inspectionbatone = getHelper().getInspectionDao().queryWhereIdNiveauONE(n.getIdNiveau());
				if ((inspectionbatdefect.size()==0)&& (inspectionbatone==null))
				{	listContenuNiveau.add(new ContenuNiveau(n));
						ecrit=true; 
				}
				else if ( (inspectionbatone.isPause()==true))
				{
					listContenuNiveau.add(new ContenuNiveau(n));
					ecrit=true; 
					
				}
			}
			
		Niveau niveauxp = getHelper().getNiveauDao().queryForId(getLastIdNiveau()); //site
				
		Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauxp.getIdNiveauParent());
		
		if (ecrit==true)
		{
			for (ContenuNiveau c : listContenuNiveau) {
					Inspection inspectionbatone = getHelper().getInspectionDao().queryWhereIdNiveauONE(c.getIdNiveau());
					if (cloture!=null)
					{
						c.setIcone(R.drawable.vert);
					}
					else
					{
						if (inspectionbatone==null)
							c.setIcone(R.drawable.gris);
						else if (inspectionbatone.isPause())
							c.setIcone(R.drawable.violet);
						else
							c.setIcone(R.drawable.vert);
					}
				
				
				
			
		}
			ContenuNiveauAdapter adapter = new ContenuNiveauAdapter(this, R.layout.listview_item_row,
					listContenuNiveau);

			lvNiveau.setAdapter(adapter);
		}else
		{
			Toast.makeText(this, getText(R.string.nooffenbjet), Toast.LENGTH_LONG)
			.show();
		}
		
		
	}
	
	public void horservicedata()
    {
	 Cloture cloture=getHelper().getClotureDao().queryWhereniveau(getLastIdNiveau());
	if (cloture!=null)
	{
		Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
		.show();
	}
	else
	{
		 if ((niveauHS!=0) && (libelleHS.equals("N"))&& (iconHS.equals("Y")))
 	   {
		StatutHS statutHS=getHelper().getStatutHSDao().queryForId(niveauHS);
		
		if (statutHS ==null) 
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			 alert.setTitle(getText(R.string.attention));
				alert.setMessage(getText(R.string.confirmHS));
	   				alert.setPositiveButton(getText(R.string.oui),
	   						new DialogInterface.OnClickListener() {
	   							public void onClick(DialogInterface dialog, int whichButton) {
	   								horsService();
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
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
				 alert.setTitle(getText(R.string.attention));
					alert.setMessage(getText(R.string.confirmnnHS));
		   				alert.setPositiveButton(getText(R.string.oui),
		   						new DialogInterface.OnClickListener() {
		   							public void onClick(DialogInterface dialog, int whichButton) {
		   								nothorsService();
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
		
		
		
	}
 	   else
 		   Toast.makeText(this, getText(R.string.interditaction), Toast.LENGTH_LONG)
 			.show();
	}
   
   		   
          
    
   	 
    }
	 public void nothorsService()
	 {
		 StatutHS statutHS=getHelper().getStatutHSDao().queryForId(niveauHS);
		 if (statutHS!=null)
		 getHelper().getStatutHSDao().delete(niveauHS);
		 if (getLastIdNiveau() != 0) {
		idNiveaux.push(getLastIdNiveau());
		}
		if (getLastIdNiveauObjet() != 0) {
		idNiveauxObjet.push(getLastIdNiveauObjet());
		}

		bindData();
		niveauHS=0;
		niveauObjetHS=0;
	 }
	public void horsService()
	{
			
		StatutHS statuths = new StatutHS();
		statuths.setNiveauid(niveauHS);
		statuths.setStatutValue(1);
			getHelper().getStatutHSDao().createOrUpdate(statuths);
			// mettre a jour statuspi
			

			if (getLastIdNiveau() != 0) {
				idNiveaux.push(getLastIdNiveau());
			}
			if (getLastIdNiveauObjet() != 0) {
				idNiveauxObjet.push(getLastIdNiveauObjet());
			}

			bindData();
			niveauHS=0;
			niveauObjetHS=0;
			
	}
	
	public void clotureSite()
	{
		if (niveauHS==null)
		 {
			 Toast.makeText(this, getText(R.string.nfclevelchoose), Toast.LENGTH_LONG)
			.show();
		 }
		else
		{
			 Cloture cloture=getHelper().getClotureDao().queryWhereniveau(niveauHS);
				if (cloture!=null)
				{
					Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
	    			.show();
				}
				else
				{
					 // vérifier si la tournée est complete ou pas 
					 int totniv=0;
					 int totinsp=0;
					 
					 List<Niveau> listNiveauxb = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(niveauHS);
					 	
					 for (Niveau nb : listNiveauxb) { //bat
						 
							List<Niveau> listNiveauxc = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(nb.getIdNiveau()); //eq
							
								totniv+=listNiveauxc.size();
							
							
							
						}
					//derniere date 
					  
						for (Niveau n : listNiveauxb) {
							
							
							List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubatByDate(n.getIdNiveau());
							List <Inspection> inspectionbat2 = getHelper().getInspectionDao().queryWhereIdNiveaubatByDateF(n.getIdNiveau());
							
								totinsp+=inspectionbat.size();
								for (Inspection i : inspectionbat)
								{
									try
								       {
								        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									df2.parse(i.getDateInformation());
									break;
									
									 }catch (Exception ex ){
								          ex.printStackTrace();
								       }
								}
								for (Inspection i : inspectionbat2)
								{
									try
								       {
								        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date d2 = df2.parse(i.getDateInformation());
									firstDate=d2; 
									break;
									
									 }catch (Exception ex ){
								          ex.printStackTrace();
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
					  								 savecloturesite(4);
					  								
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
						{
							AlertDialog.Builder alert = new AlertDialog.Builder(this);
							 alert.setTitle(getText(R.string.attention));
								alert.setMessage(getText(R.string.confirmcloture));
					  				alert.setPositiveButton(getText(R.string.oui),
					  						new DialogInterface.OnClickListener() {
					  							public void onClick(DialogInterface dialog, int whichButton) {
					  								//onBackPressed();
					  								cloturedefsite();
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
				}
			
		}
		
		
	}
	
	
	
	public void cloture()
	{
		 // verifier si c pas deja cloturer
		 Cloture cloture=getHelper().getClotureDao().queryWhereniveau(getLastIdNiveau());
			if (cloture!=null)
			{
				Toast.makeText(this, getText(R.string.interdiclot), Toast.LENGTH_LONG)
    			.show();
			}
			else
			{
				 // vérifier si la tournée est complete ou pas 
				 int totniv=0;
				 int totinsp=0;
				 Boolean hsok=false;
				 List<Niveau> listNiveauxb = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(getLastIdNiveau());
				 	
				 for (Niveau nb : listNiveauxb) { //bat
					 
						List<Niveau> listNiveauxc = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(nb.getIdNiveau()); //eq
						
							totniv+=listNiveauxc.size();
							 StatutHS statutHS=getHelper().getStatutHSDao().queryForId(nb.getIdNiveau());
						if  (statutHS!=null)
						{
							if (statutHS.getStatutValue()==1) hsok=true;
								
						}
						
					}
				//derniere date 
				  
					for (Niveau n : listNiveauxb) {
						
						
						List <Inspection> inspectionbat = getHelper().getInspectionDao().queryWhereIdNiveaubatByDate(n.getIdNiveau());
						List <Inspection> inspectionbat2 = getHelper().getInspectionDao().queryWhereIdNiveaubatByDateF(n.getIdNiveau());
						
							totinsp+=inspectionbat.size();
							for (Inspection i : inspectionbat)
							{
								try
							       {
							        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								df2.parse(i.getDateInformation());
								lastDate=df2.parse(i.getDateInformation());
								//lastDateString= df2.format(i.getDateInformation());
								break;
								
								 }catch (Exception ex ){
							          ex.printStackTrace();
							       }
							}
							for (Inspection i : inspectionbat2)
							{
								try
							       {
							        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date d2 = df2.parse(i.getDateInformation());
								firstDate=d2; 
								//firstDateString=df2.format(i.getDateInformation());
								break;
								
								 }catch (Exception ex ){
							          ex.printStackTrace();
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
				  								 savecloture(4);
				  								
				  								dialog.dismiss();
				  							}
				  						});
						
						
				  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
				  					public void onClick(DialogInterface dialog, int whichButton) {
				  						dialog.dismiss();
				  						removeLastNiveauOuObjet();
				  						removeLastLibelle();
				  						bindData();
				  					}
				  				});
				  				
				       	   
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
				       	   
						
					}
					else if (hsok  )
					{
						AlertDialog.Builder alert = new AlertDialog.Builder(this);
						 alert.setTitle(getText(R.string.attention));
							alert.setMessage(getText(R.string.confirmcloturett));
				  				alert.setPositiveButton(getText(R.string.oui),
				  						new DialogInterface.OnClickListener() {
				  							public void onClick(DialogInterface dialog, int whichButton) {
				  								 savecloture(4);
				  								
				  								dialog.dismiss();
				  							}
				  						});
						
						
				  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
				  					public void onClick(DialogInterface dialog, int whichButton) {
				  						dialog.dismiss();
				  						removeLastNiveauOuObjet();
				  						removeLastLibelle();
				  						bindData();
				  					}
				  				});
				  				
				       	   
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
					}
					else 
					{
						AlertDialog.Builder alert = new AlertDialog.Builder(this);
						 alert.setTitle(getText(R.string.attention));
							alert.setMessage(getText(R.string.confirmcloture));
				  				alert.setPositiveButton(getText(R.string.oui),
				  						new DialogInterface.OnClickListener() {
				  							public void onClick(DialogInterface dialog, int whichButton) {
				  								//onBackPressed();
				  								cloturedef();
				  								dialog.dismiss();
				  							}
				  						});
						
						
				  				alert.setNegativeButton(getText(R.string.non), new DialogInterface.OnClickListener() {
				  					public void onClick(DialogInterface dialog, int whichButton) {
				  						dialog.dismiss();
				  						removeLastNiveauOuObjet();
				  						removeLastLibelle();
				  						bindData();
				  					}
				  				});
				  				
				       	   
						AlertDialog alertDialog = alert.create();
						alertDialog.show();
					}
			}
		
	}
	public void cloturedef()
	 {
		 
		 final CharSequence[] items = { getText(R.string.cloturemeteo), getText(R.string.cloturepanne),getText(R.string.cloturepanne2), getText(R.string.clotureautre) };
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 builder.setTitle(getText(R.string.clotureraison));
	         builder.setSingleChoiceItems(items, -1,
	                 new DialogInterface.OnClickListener() {
	                     public void onClick(DialogInterface dialog, int item) {
	                        savecloture(item);
	                         dialog.dismiss();
	                         
	                     }
	                 });
	         AlertDialog alert = builder.create();
	         alert.show();
	 }
	
	public void cloturedefsite()
	 {
		 
		 final CharSequence[] items = { getText(R.string.cloturemeteo), getText(R.string.cloturepanne),getText(R.string.cloturepanne2), getText(R.string.clotureautre) };
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
			 builder.setTitle(getText(R.string.clotureraison));
	         builder.setSingleChoiceItems(items, -1,
	                 new DialogInterface.OnClickListener() {
	                     public void onClick(DialogInterface dialog, int item) {
	                        savecloturesite(item);
	                         dialog.dismiss();
	                         
	                     }
	                 });
	         AlertDialog alert = builder.create();
	         alert.show();
	 }
	
	public void savecloture(int stat)
	 {  Date maDate = new Date(); 
	 @SuppressWarnings("deprecation")
		int day=maDate.getDay();
		 Cloture cloture = new Cloture();
			cloture.setIdNiveau(getLastIdNiveau());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datechoose="";
			//List <StatutInac> inaclist = getHelper().getStatutInacDao().queryWhereDateafter();
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				
	if (firstDate !=null)
	{
		
		cloture.setDateCloture(df.format(firstDate));
	}
	else
		cloture.setDateCloture(df.format(new Date()));
	
	if (lastDate!=null) 
	{
		
		cloture.setDebDateCloture(df.format(lastDate));
		
	}
	else
	{
		cloture.setDebDateCloture(df.format(new Date()));
	}
			

int statenv=0;
 

if (stat==4)
{
	cloture.setStatutCloture(1); statenv=1;
}

else
			{cloture.setStatutCloture(stat+2); statenv=stat+2;}
cloture.setUserCloture(userId);
cloture.setDayCloture(day);
getHelper().getClotureDao().createOrUpdate(cloture);
pddata(getLastIdNiveau() , statenv);
//this.idNiveaux.push(0);
//this.idNiveauxObjet.push(0);

removeLastNiveauOuObjet();
removeLastLibelle();
idNiveaux.clear();
idNiveauxObjet.clear();
bindData();
			} catch (Exception ex ){
		          ex.printStackTrace();
		       }
	 }

	
	public void savecloturesite(int stat)
	 {  Date maDate = new Date(); 
	 @SuppressWarnings("deprecation")
		int day=maDate.getDay();
		 Cloture cloture = new Cloture();
			cloture.setIdNiveau(niveauHS);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String datechoose="";
			
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				
				if (firstDate !=null)
				{
					
					cloture.setDateCloture(df.format(firstDate));
				}
				else
					cloture.setDateCloture(df.format(new Date()));
				
				if (lastDate!=null) 
				{
					
					cloture.setDebDateCloture(df.format(lastDate));
					
				}
				else
				{
					cloture.setDebDateCloture(df.format(new Date()));
				}

int statenv=0;

cloture.setDateCloture(df.format(new Date()));
if (stat==4)
{
	cloture.setStatutCloture(1); statenv=1;
}

else
			{cloture.setStatutCloture(stat+2); statenv=stat+2;}
cloture.setUserCloture(userId);
cloture.setDayCloture(day);
getHelper().getClotureDao().createOrUpdate(cloture);
pddata(niveauHS , statenv);

bindData();
			} catch (Exception ex ){
		          ex.printStackTrace();
		       }
	 }
	
	
	private void pddata (int idsite ,int statenv)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Parametrage param = getHelper().getParametrageDao().queryForId(1);
		Niveau nArea = getHelper().getNiveauDao().queryForId(idsite);
		StatusPI spiArea = new StatusPI();
		spiArea.setAreaTag(nArea.getAreaTag());
		spiArea.setTagDescription(nArea.getLibelle());
		spiArea.setStatus(statenv);
		 
		spiArea.setTimestamp(sdf.format(new Date()));
		spiArea.setClientName(param.getCodeClient());
		getHelper().getStatusPIDao().create(spiArea);	
			List<Niveau> listNiveaub = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(idsite);
			for (Niveau n : listNiveaub) {
				
				List<Niveau> listNiveaue = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(n.getIdNiveau());
				for (Niveau m : listNiveaue) {
					StatusPI spi = new StatusPI();
					
					NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(m.getIdNiveauObjet());
					
					spi.setTagName(m.getLibelle());
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
