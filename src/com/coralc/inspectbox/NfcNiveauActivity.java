package com.coralc.inspectbox;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.coralc.inspectbox.adapter.ContenuNiveauAdapter;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.LibelleNiveau;
import com.coralc.inspectbox.database.Nfc;
import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.pojo.ContenuNiveau;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class NfcNiveauActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	// Widgets
	private ListView lvNiveau;
	private TextView txtChemin;
	private TextView txtLibNiveau;

	// Variables TAG
	private NfcAdapter mNfcAdapter;
	 private PendingIntent mPendingIntent;
	 private static IntentFilter[] mIntentFilters;
	 private static String[][] mNFCTechLists;
	private int idNiveauchoisi;

	private String textag;
	
 
	// pile des niveaux
	private Stack<Integer> idNiveaux = new Stack<Integer>();
	// pile des id niveau objet
	private Stack<Integer> idNiveauxObjet = new Stack<Integer>();
	// pour le chemin
	private ArrayList<String> listLibelles = new ArrayList<String>();
	public static final String MIME_TEXT_PLAIN = "text/plain";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.niveaunfc);

		// on restore l'état précédent si nécessaire
		if (savedInstanceState != null) {
			idNiveaux.clear();
			idNiveauxObjet.clear();
			
			idNiveaux.addAll(savedInstanceState.getIntegerArrayList("idNiveaux"));
			idNiveauxObjet.addAll(savedInstanceState.getIntegerArrayList("idNiveauxObjet"));
			listLibelles = savedInstanceState.getStringArrayList("listLibelles");
		}

		// Initalisation des widgets
		txtChemin = (TextView) findViewById(R.id.txtCheminnfc);
		lvNiveau = (ListView) findViewById(R.id.lvNiveaunfc);
		txtLibNiveau = (TextView) findViewById(R.id.txtLibNiveaunfc);
		lvNiveau.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// Binding des évènements

		lvNiveau.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				ContenuNiveau c = (ContenuNiveau) parent.getItemAtPosition(position);
				
				if (c.getIdNiveauObjet()!=0)
				{
					idNiveauchoisi=c.getIdNiveau();
					
				}
				else
				{
					idNiveauchoisi=0;
					textag="";
					// Si le type de réponse de l'élément sélectionné est vide
					// on est encore dans une liste
					
						listLibelles.add(c.getLibelle());

						if (c.getIdNiveau() != 0) {
							idNiveaux.push(c.getIdNiveau());
						}
						if (c.getIdNiveauObjet() != 0) {
							idNiveauxObjet.push(c.getIdNiveauObjet());
							
						}

						bindData();
				}
				
				
			}
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
	protected void onResume() {
		super.onResume();
		if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
		// Binding des données
		// Dans cette activité on affiche les données dans le onResume car
		// d'autres activités ont une influence sur les données affichées
		bindData();
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
			Intent intent = new Intent(NfcNiveauActivity.this, ParametrageActivity.class);
			startActivity(intent);
		} else {
			removeLastNiveauOuObjet();
			removeLastLibelle();
			bindData();
		}
	}

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
		   // get the tag object for the discovered tag
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			getNewTag(tag, intent);
			  Niveau niveau = getHelper().getNiveauDao().queryForId(idNiveauchoisi);
	       String s1 = bin2hex(tag.getId());
	       if (! s1.equals(""))
	       {
	    	   textag=s1;  
	    	   if (  (idNiveauchoisi==0))
	    	   {
	    		   Toast.makeText(this, getText(R.string.nfclevelchoose), Toast.LENGTH_LONG)
					.show();
	    	   }
	    	   else
	    	   {
	    		   //chercher doublons dans Niveau  et NFC table
	    		   Niveau nv = getHelper().getNiveauDao().queryWherecodebarre(s1);
	    		   Nfc nfc = getHelper().getNfcDao().queryWheretag(s1); 
	    		   
	    		   if ((nv==null) && (nfc==null)) // nouveau tag 
	    		   {
	    			   
	    			   //chercher si ce niveau a deja une ancienne tag
	    			 
	    			   Nfc nfc1 = getHelper().getNfcDao().queryWhereniveau(idNiveauchoisi); 
	    			   
	    			   if ((niveau.getCodebarre()!=null) || (nfc1!=null))
	    			   {
	    				    
	    				   AlertDialog.Builder alert = new AlertDialog.Builder(this);
							 alert.setTitle(getText(R.string.attention));
								alert.setMessage(getText(R.string.confirreedit)+" "+getText(R.string.confirreedit2));
					   				alert.setPositiveButton(getText(R.string.oui),
					   						new DialogInterface.OnClickListener() {
					   							public void onClick(DialogInterface dialog, int whichButton) {
					   								updateTag(textag);
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
		    			
	    				   
	    			   }  // end chercher si ce niveau a deja une ancienne tag
	    			   else //element vide sans ancienne tag 
	    			   {
	    				  
	    				   ecrireTag(s1);
	    			   }
	    		   } // end if nouvelle tag 
	    		   else if ( (nv!=null) && (nfc==null)) // tag existe dans la table niveau 
	    		   {
	    			   if (nv.getIdNiveau()!=idNiveauchoisi)
	    			   { final int nivtosend=nv.getIdNiveau();
	    				  
	    				   AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setTitle(getText(R.string.attention));
							alert.setMessage(getText(R.string.confecrase)+" "+nv.getLibelle()+" "+getText(R.string.confecrase3));
					   		alert.setPositiveButton(getText(R.string.oui),
					   		new DialogInterface.OnClickListener() {
					   			public void onClick(DialogInterface dialog, int whichButton) {
					   				ecraserTag(textag , nivtosend);
					   				 
					   				
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
	    				   Toast.makeText(this, getText(R.string.donothing), Toast.LENGTH_LONG)
	    					.show();
	    						bindData();
	    			   }
	    			   
	    		   }
	    		   else	 if ((nfc!=null)&& (nv==null)) // tag existe dans table nfc 
	    		   {
	    			   if (nfc.getIdNiveau()!=idNiveauchoisi)
	    			   { 
	    				   final int nfctosend =nfc.getIdNiveau();
	    			   Niveau nvc=getHelper().getNiveauDao().queryForId(nfc.getIdNiveau());
	    				   
	    				   AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setTitle(getText(R.string.attention));
							alert.setMessage(getText(R.string.confecrase)+" "+nvc.getLibelle()+" "+getText(R.string.confecrase3));
					   		alert.setPositiveButton(getText(R.string.oui),
					   		new DialogInterface.OnClickListener() {
					   			public void onClick(DialogInterface dialog, int whichButton) {
					   				ecraserTagFromNFC(textag , nfctosend);
					   				 
					   				
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
	    				   Toast.makeText(this, getText(R.string.donothing), Toast.LENGTH_LONG)
	    					.show();
	    						bindData();
	    			   }
	    		   }
	    		   else // existe dans nfc et niveau
	    		   {
	    			   if (nv.getIdNiveau()==idNiveauchoisi)
	    			   { 
	    				   effacer (textag);
	    			   }
	    			   else ecraserTag(textag , nv.getIdNiveau());
		   				 
	    				   
	    		   }
	    	   }
	    	
	       }
	       else
	       {
	    	   Toast.makeText(this, getText(R.string.erresyst), Toast.LENGTH_LONG)
				.show();
	       }
	       
	   
	        
	 }
	 

	 
	
	 
	 private void ecrireTag(String txt)
	 {
		//ecrire la nvelle tag 	
		 Nfc nvnfc = new Nfc();
			nvnfc.setIdNiveau(idNiveauchoisi);
			nvnfc.setNfcTag(txt);
			getHelper().getNfcDao().createOrUpdate(nvnfc);
				Toast.makeText(this, getText(R.string.nfcsuccee), Toast.LENGTH_LONG)
			.show();
			bindData();
	 }
	 
	 private void ecraserTag(String txt , int idniveau)
	 {
		//ecraser ancien 
		 Nfc nfc = getHelper().getNfcDao().queryWhereniveau(idniveau);
		 if (nfc==null)
		 {
			 Nfc nvnfc1 = new Nfc();
				nvnfc1.setIdNiveau(idniveau);
				nvnfc1.setNfcTag(null);
				getHelper().getNfcDao().createOrUpdate(nvnfc1); 
			 
		 }
		 else
		 {
			 nfc.setNfcTag(null);  getHelper().getNfcDao().createOrUpdate(nfc);
		 }
		// ecrire le nouveau
		 Nfc nfc1 = getHelper().getNfcDao().queryWhereniveau(idNiveauchoisi);
		 if (nfc1==null)
		 {
			 Nfc nvnfc1 = new Nfc();
				nvnfc1.setIdNiveau(idNiveauchoisi);
				nvnfc1.setNfcTag(txt);
				getHelper().getNfcDao().createOrUpdate(nvnfc1); 
			 
		 }
		 else
		 {
			 nfc1.setNfcTag(txt); getHelper().getNfcDao().createOrUpdate(nfc1);
		 }
		 Toast.makeText(this, getText(R.string.nfcsuccee), Toast.LENGTH_LONG)
			.show();
				bindData();
	 }
	 private void ecraserTagFromNFC(String txt , int idniveau)
	 {
		 
		 Nfc nfc = getHelper().getNfcDao().queryWhereniveau(idniveau);
		 
		if ( nfc !=null)
		 {
			  try {
				getHelper().getNfcDao().delete(nfc);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		// ecrire le nouveau
		 Nfc nfc1 = getHelper().getNfcDao().queryWhereniveau(idNiveauchoisi);
		 if (nfc1==null)
		 {
			 Nfc nvnfc1 = new Nfc();
				nvnfc1.setIdNiveau(idNiveauchoisi);
				nvnfc1.setNfcTag(txt);
				getHelper().getNfcDao().createOrUpdate(nvnfc1); 
			 
		 }
		 else
		 {
			 nfc1.setNfcTag(txt); getHelper().getNfcDao().createOrUpdate(nfc1);
		 }
		 Toast.makeText(this, getText(R.string.nfcsuccee), Toast.LENGTH_LONG)
			.show();
				bindData();
		 
	 }
	 private void effacer (String txt)
	 {
		 Nfc nfc1 = getHelper().getNfcDao().queryWhereniveau(idNiveauchoisi);
		 if (nfc1!=null)
		 {
			 try {
					getHelper().getNfcDao().delete(nfc1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 Toast.makeText(this, getText(R.string.nfcsuccee), Toast.LENGTH_LONG)
				.show();
					
		 }
		 else
		 {
			 Toast.makeText(this, getText(R.string.donothing), Toast.LENGTH_LONG)
				.show();
					
		 }
		 bindData();	
	 }
private void updateTag(String txt)
	 {
		 //mise a jour table nfc
		 Nfc nfc = getHelper().getNfcDao().queryWhereniveau(idNiveauchoisi);
		if (nfc!=null)
		{
			nfc.setNfcTag(txt);
			 getHelper().getNfcDao().createOrUpdate(nfc);
		}
		else
		{
			Nfc nvnfc = new Nfc();
			nvnfc.setIdNiveau(idNiveauchoisi);
			
			nvnfc.setNfcTag(txt);
			getHelper().getNfcDao().createOrUpdate(nvnfc);
			
		}
		 
		
		 Toast.makeText(this, getText(R.string.nfcsuccee), Toast.LENGTH_LONG)
			.show();
		 bindData(); 
		 
	 }
	 
	
	 
	 
	 
	 
	 /*
	 * Lie les données à afficher à la vue
	 */
	private void bindData() {
		int idNiveau = getLastIdNiveau();
		int idNiveauObjet = getLastIdNiveauObjet();
		 
		// on affiche le chemin
		txtChemin.setText(getChemin());

		// on rempli la liste des ContenuNiveau avec des niveaux ou des niveaux
		// objet
		List<ContenuNiveau> listContenuNiveau = null;
		
	
		
	
		if (idNiveauObjet != 0) {
			List<NiveauObjet> listNiveauxObjet = getHelper().getNiveauObjetDao()
					.queryWhereIdObjetOrderByTri(idNiveauObjet);
			
			
			listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveauxObjet.size());
			for (NiveauObjet n : listNiveauxObjet) {
				listContenuNiveau.add(new ContenuNiveau(n));
			}

		} 
		else {
			List<Niveau> listNiveaux = getHelper().getNiveauDao().queryWhereIdParentEqOrderByTri(
					idNiveau);
			
			
			
				listContenuNiveau = new ArrayList<ContenuNiveau>(listNiveaux.size());
				for (Niveau n : listNiveaux) {
					
					listContenuNiveau.add(new ContenuNiveau(n));
				}
			
			
		}

		// On affiche le libellé du niveau en cours et le chemin
		LibelleNiveau libNiveau = getHelper().getLibelleNiveauDao().queryWhereNumAndTypeEq(
				getNum(listContenuNiveau), getTypeLibelleNiveau(listContenuNiveau));
		if (libNiveau != null) {
			
			txtLibNiveau.setText(libNiveau.getLibelle());
		

		
		
		for (ContenuNiveau c : listContenuNiveau) {
			 
			if (c.getIdNiveauObjet()!=0)
			{
				//Nfc nfcdb=getHelper().getNfcDao().queryWhereexist(c.getIdNiveau(),"O");
				Nfc nfcdb2=getHelper().getNfcDao().queryWhereniveau(c.getIdNiveau());
				 
				if (nfcdb2!=null)
				{
					c.setIcone(R.drawable.jaune);
					}
				else
				{ 
					NiveauObjet nvobj = getHelper().getNiveauObjetDao().queryForId(c.getIdNiveauObjet());
					 
					if (nvobj.getCodebarre()!=null)
					{
						c.setIcone(R.drawable.vert);
					}
					else
						c.setIcone(R.drawable.blanc);
					
					
				}
				
			}
			
			
		}

		// enfin on affiche la liste
		ContenuNiveauAdapter adapter = new ContenuNiveauAdapter(this, R.layout.listview_item_row,
				listContenuNiveau);

		lvNiveau.setAdapter(adapter);
	}
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
		if (listLibelles.size() > 0) {
			listLibelles.remove(listLibelles.size() - 1);
		}
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
}
