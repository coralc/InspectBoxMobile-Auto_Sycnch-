package com.coralc.inspectbox.sync;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.coralc.inspectbox.R;
import com.coralc.inspectbox.TaskFragment;
import com.coralc.inspectbox.database.Choix;
import com.coralc.inspectbox.database.Cloture;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.DatesModifications;
import com.coralc.inspectbox.database.Inspection;
import com.coralc.inspectbox.database.LibelleNiveau;
import com.coralc.inspectbox.database.Nfc;
import com.coralc.inspectbox.database.Niveau;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.database.Objeteam;
import com.coralc.inspectbox.database.Parametrage;
import com.coralc.inspectbox.database.StatusPI;
import com.coralc.inspectbox.database.StatutHS;
import com.coralc.inspectbox.database.StatutInac;
import com.coralc.inspectbox.database.Statutfin;

import com.coralc.inspectbox.database.Team;
import com.coralc.inspectbox.database.TypeReponse;
import com.coralc.inspectbox.database.Utilisateur;
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
import com.coralc.inspectbox.sync.serializable.KvmCloture;
import com.coralc.inspectbox.sync.serializable.KvmDatesModifications;
import com.coralc.inspectbox.sync.serializable.KvmInspection;
import com.coralc.inspectbox.sync.serializable.KvmLibelleNiveau;
import com.coralc.inspectbox.sync.serializable.KvmNfc;
import com.coralc.inspectbox.sync.serializable.KvmNiveau;
import com.coralc.inspectbox.sync.serializable.KvmNiveauObjet;
import com.coralc.inspectbox.sync.serializable.KvmObjeteam;
import com.coralc.inspectbox.sync.serializable.KvmStatusPI;
import com.coralc.inspectbox.sync.serializable.KvmStatutInac;

import com.coralc.inspectbox.sync.serializable.KvmTeam;
import com.coralc.inspectbox.sync.serializable.KvmTypeReponse;
import com.coralc.inspectbox.sync.serializable.KvmUtilisateur;
import com.coralc.utils.Sha1;
import com.j256.ormlite.table.TableUtils;

import android.util.Log;
import android.widget.Toast;

/*
 * Classe utilisée pour lancer le téléchargement des données de synchronisation
 */
public class SyncTask extends BaseTask  {

	private DatabaseHelper helper = null;
	private Integer progress = 0;
	private Boolean doUpload;
	private Boolean doDownload;
	private Boolean doMAJ;
	public SyncTask(DatabaseHelper helper, Boolean doUpload, Boolean doDownload, Boolean doMAJ) {
		this.helper = helper;
		this.doUpload = doUpload;
		this.doDownload = doDownload;
		this.doMAJ=doMAJ;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
	}

	@Override
	protected Integer doInBackground(Void... params) {
		progress = 0;
		String loginCheck = "";
		Parametrage param = helper.getParametrageDao().queryForId(1);
		// on vérifie le login avant toute chose
		final SoapSerializationEnvelope loginEnvelope = call("checkLogin", param);

		try {
			Object responselog = (Object) loginEnvelope.getResponse();
			loginCheck = responselog == null ? "false" : responselog.toString();
		} catch (SoapFault e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}

		if (loginCheck.equals("true")) {
			publishProgress(progress = 4);

			if (doUpload) {
				upload(param, doDownload ? 48 : 96);
			}
			if (doDownload) {
				download(param, doUpload ? 48 : 96);
			}
			if (doMAJ)
			{
				progress=miseAjour(param);
				if (progress ==0) return progress;
			}

			publishProgress(progress = 100);
		}
		return progress;
	}

	private void download(Parametrage param, int totalProgress) {
		System.out.println("ici download");
		// on a besoin de générer les statuts si la table niveau ou niveauobjet
		// a changé
		Boolean needGenStatut = false;
		int nbTask = 9;
		int progressInc = totalProgress / nbTask;

		SoapSerializationEnvelope enveloppe = null;
		// si le login est juste, on récupère la liste des tables à mettre à
		// jour
		enveloppe = call("getDatesModifications", param);

		ArrayOfKvmDatesModifications tables = (ArrayOfKvmDatesModifications) enveloppe.bodyIn;
		if (tables == null)
			tables = new ArrayOfKvmDatesModifications();
		publishProgress(progress += progressInc);
		if (isCancelled())
			return;

		

		// Les utilisateurs
		DatesModifications t = null;
		
		if ((t = tables.find("utilisateur")) != null) {
			
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
			
			
				// on vide la table
				try {
					TableUtils.dropTable(helper.getConnectionSource(), Utilisateur.class, true);
					
					TableUtils.createTable(helper.getConnectionSource(), Utilisateur.class);
					
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), Utilisateur.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getUtilisateurs", param);
				
				ArrayOfKvmUtilisateur utilisateurs = (ArrayOfKvmUtilisateur) enveloppe.bodyIn;
				
				for (KvmUtilisateur u : utilisateurs) {
					//u.getTypeUser();
					helper.getUtilisateurDao().create(u);
					}
				helper.getDatesModificationsDao().createOrUpdate(t);
				
			//}
		}
		publishProgress(progress += progressInc);
		
		if (isCancelled())
			return;

		// Les Choix
		if ((t = tables.find("choix")) != null) {
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
				try {
					TableUtils.dropTable(helper.getConnectionSource(), Choix.class, true);
					TableUtils.createTable(helper.getConnectionSource(), Choix.class);
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), Choix.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getChoix", param);
				ArrayOfKvmChoix choix = (ArrayOfKvmChoix) enveloppe.bodyIn;
				for (KvmChoix c : choix) {
					helper.getChoixDao().create(c);
				}
				helper.getDatesModificationsDao().createOrUpdate(t);
				//}
		}
		publishProgress(progress += progressInc);
		if (isCancelled())
			return;

		
		// LesEquipes
				if ((t = tables.find("team")) != null) {
					
					helper.getDatesModificationsDao().queryForId(t.getNomTable());
						try {
							TableUtils.dropTable(helper.getConnectionSource(), Team.class, true);
							TableUtils.createTable(helper.getConnectionSource(), Team.class);
						} catch (SQLException e) {
							e.printStackTrace();
							Log.e(SyncTask.class.getName(), Team.class.getName(), e);
							throw new RuntimeException(e);
						}
						
						// on charge les données dans la base sql lite
						enveloppe = call("getTeam", param);
						
						ArrayOfKvmTeam team = (ArrayOfKvmTeam) enveloppe.bodyIn;
						
						for (KvmTeam tm : team) {
							
							helper.getTeamDao().create(tm);
						}
						
						helper.getDatesModificationsDao().createOrUpdate(t);
						
						
						//}
				}
				publishProgress(progress += progressInc);
				
				if (isCancelled())
					return;
				
				// LesEquipes objets
				if ((t = tables.find("objeteam")) != null) {
					
					helper.getDatesModificationsDao().queryForId(t.getNomTable());
						try {
							TableUtils.dropTable(helper.getConnectionSource(), Objeteam.class, true);
							TableUtils.createTable(helper.getConnectionSource(), Objeteam.class);
						} catch (SQLException e) {
							e.printStackTrace();
							Log.e(SyncTask.class.getName(), Objeteam.class.getName(), e);
							throw new RuntimeException(e);
						}

						// on charge les données dans la base sql lite
						enveloppe = call("getObjeteam", param);
						ArrayOfKvmObjeteam teamobj = (ArrayOfKvmObjeteam) enveloppe.bodyIn;
						for (KvmObjeteam tmob : teamobj) {
							helper.getObjeteamDao().create(tmob);
						}
						helper.getDatesModificationsDao().createOrUpdate(t);
						//}
				}
				publishProgress(progress += progressInc);
				if (isCancelled())
					return;
				
				
		// Les TypesReponses
		if ((t = tables.find("typereponse")) != null) {
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
			//if (oldt == null || !oldt.getTimestamp().equals(t.getTimestamp())) {
				// on vide la table
				try {
					TableUtils.dropTable(helper.getConnectionSource(), TypeReponse.class, true);
					TableUtils.createTable(helper.getConnectionSource(), TypeReponse.class);
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), TypeReponse.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getTypeReponses", param);
				ArrayOfKvmTypeReponse typereponses = (ArrayOfKvmTypeReponse) enveloppe.bodyIn;
				for (KvmTypeReponse o : typereponses) {
					
					helper.getTypeReponseDao().create(o);
				}
				helper.getDatesModificationsDao().createOrUpdate(t);
				//}
		}
		publishProgress(progress += progressInc);
		if (isCancelled())
			return;

		

		// Les Niveaux
		if ((t = tables.find("niveau")) != null) {
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
			//if (oldt == null || !oldt.getTimestamp().equals(t.getTimestamp())) {
				// on vide la table
				try {
					
					TableUtils.dropTable(helper.getConnectionSource(), Niveau.class, true);
					TableUtils.createTable(helper.getConnectionSource(), Niveau.class);
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), Niveau.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getNiveaux", param);
				ArrayOfKvmNiveau niveaux = (ArrayOfKvmNiveau) enveloppe.bodyIn;
				for (KvmNiveau o : niveaux) {
					helper.getNiveauDao().create(o);
				}
				helper.getDatesModificationsDao().createOrUpdate(t);
				needGenStatut = true;
				//}
		}
		publishProgress(progress += progressInc);
		if (isCancelled())
			return;

		// Les libellé niveaux
		if ((t = tables.find("libelleniveau")) != null) {
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
			//if (oldt == null || !oldt.getTimestamp().equals(t.getTimestamp())) {
				// on vide la table
				try {
					
					TableUtils.dropTable(helper.getConnectionSource(), LibelleNiveau.class, true);
					TableUtils.createTable(helper.getConnectionSource(), LibelleNiveau.class);
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), LibelleNiveau.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getLibelleNiveaux", param);
				ArrayOfKvmLibelleNiveau libniveaux = (ArrayOfKvmLibelleNiveau) enveloppe.bodyIn;
				for (KvmLibelleNiveau o : libniveaux) {
					helper.getLibelleNiveauDao().create(o);
				}
				helper.getDatesModificationsDao().createOrUpdate(t);
				//}
		}
		publishProgress(progress += progressInc);
		if (isCancelled())
			return;

		// Les niveaux objets
		if ((t = tables.find("niveauobjet")) != null) {
			helper.getDatesModificationsDao().queryForId(t.getNomTable());
			//if (oldt == null || !oldt.getTimestamp().equals(t.getTimestamp())) {
				
				// on vide la table
				try {
					TableUtils.dropTable(helper.getConnectionSource(), NiveauObjet.class, true);
					TableUtils.createTable(helper.getConnectionSource(), NiveauObjet.class);
				} catch (SQLException e) {
					Log.e(SyncTask.class.getName(), NiveauObjet.class.getName(), e);
					throw new RuntimeException(e);
				}

				// on charge les données dans la base sql lite
				enveloppe = call("getNiveauObjets", param);
				ArrayOfKvmNiveauObjet niveauxobjets = (ArrayOfKvmNiveauObjet) enveloppe.bodyIn;
				for (KvmNiveauObjet o : niveauxobjets) {
					
					helper.getNiveauObjetDao().create(o);
				}
				helper.getDatesModificationsDao().createOrUpdate(t);
				needGenStatut = true;
				//}
		}

		publishProgress(progress += progressInc);
		if (isCancelled())
			return;
		if (needGenStatut) {
			helper.generateAllStatuts();
		}
	}

	private void upload(Parametrage param, int totalProgress) {
		System.out.println("ici upload");
		int nbTask = 7;
		int progressInc = totalProgress / nbTask;
		
		// on récupère les inspections
		List<Inspection> inspections = helper.getInspectionDao().queryForAll();
		List<StatusPI> statusPI = helper.getStatusPIDao().queryForAll();
		List<Cloture> clotures = helper.getClotureDao().queryForAll();
		List<StatutInac> statutInacs = helper.getStatutInacDao().queryWhereInacc();
		List <Nfc> nfcs=helper.getNfcDao().queryForAll();
		
		 
		
		// on transmet les inspections
		
		callSetInspections(param, inspections);
		publishProgress(progress += progressInc);
		
		callSetStatusPI(param,statusPI);
		publishProgress(progress += progressInc);
		
		callSetStatutInac (param, statutInacs);
		publishProgress(progress += progressInc);
		
		callSetCloture(param, clotures);
		publishProgress(progress += progressInc);
		
		callSetNfc(param,nfcs);
		publishProgress(progress += progressInc);
		

		// nettoyage
		try {
			TableUtils.dropTable(helper.getConnectionSource(), Inspection.class, true);
			TableUtils.createTable(helper.getConnectionSource(), Inspection.class);
			TableUtils.dropTable(helper.getConnectionSource(), StatusPI.class, true);
			TableUtils.createTable(helper.getConnectionSource(), StatusPI.class);
			TableUtils.dropTable(helper.getConnectionSource(), StatutHS.class, true);
			TableUtils.createTable(helper.getConnectionSource(), StatutHS.class);
			TableUtils.dropTable(helper.getConnectionSource(), Cloture.class, true);
			TableUtils.createTable(helper.getConnectionSource(), Cloture.class);
			//TableUtils.dropTable(helper.getConnectionSource(), Historique.class, true);
			//TableUtils.createTable(helper.getConnectionSource(), Historique.class);
			TableUtils.dropTable(helper.getConnectionSource(), Nfc.class, true);
			TableUtils.createTable(helper.getConnectionSource(), Nfc.class);
			TableUtils.dropTable(helper.getConnectionSource(), StatutInac.class, true);
			TableUtils.createTable(helper.getConnectionSource(), StatutInac.class);
			publishProgress(progress += progressInc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// on réinitialise les statuts
		helper.getStatutfinDao().updateAllStatut(Statutfin.VIDE);
		// on supprime les photos locales
		
		publishProgress(progress += progressInc);
	}

	
	private SoapObject createBaseRequest(String method, Parametrage param) {
		SoapObject request = new SoapObject(Global.NAMESPACE, method);

		request.addProperty("CodeAcces", param.getCodeClient());
		request.addProperty("ClefClient", Sha1.convert(param.getMotdepasseClient().getBytes()));
		request.addProperty("VersionService", "1");
		request.addProperty("idTerminal", param.getNbTerminal());
		return request;
	}
	
	/*
	 * Appelle la méthode d'un webservice en fonction du paramétrage
	 */
	private SoapSerializationEnvelope call(String method, Parametrage param) {

		String urlWS = param.getUrlClient() + Global.WS_END;
		SoapObject request = createBaseRequest(method, param);

		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.setOutputSoapObject(request);

		// Mapping des tables
		env.addMapping(Global.NAMESPACE, "getDatesModificationsResponse",
				ArrayOfKvmDatesModifications.class);
		env.addMapping(Global.NAMESPACE, "clefTimestamp", KvmDatesModifications.class);
		// Mapping des utilisateurs
		env.addMapping(Global.NAMESPACE, "getUtilisateursResponse", ArrayOfKvmUtilisateur.class);
		env.addMapping(Global.NAMESPACE, "utilisateur", KvmUtilisateur.class);
		// Mapping des choix
		env.addMapping(Global.NAMESPACE, "getChoixResponse", ArrayOfKvmChoix.class);
		env.addMapping(Global.NAMESPACE, "choix", KvmChoix.class);
		// Mapping des teams
		env.addMapping(Global.NAMESPACE, "getTeamResponse", ArrayOfKvmTeam.class);
		env.addMapping(Global.NAMESPACE, "team", KvmTeam.class);
		// Mapping des objets teams
		env.addMapping(Global.NAMESPACE, "getObjeteamResponse", ArrayOfKvmObjeteam.class);
		env.addMapping(Global.NAMESPACE, "objeteam", KvmObjeteam.class);
		// Mapping des niveaux
		env.addMapping(Global.NAMESPACE, "getNiveauxResponse", ArrayOfKvmNiveau.class);
		env.addMapping(Global.NAMESPACE, "niveau", KvmNiveau.class);
		// Mapping des libellés niveaux
		env.addMapping(Global.NAMESPACE, "getLibelleNiveauxResponse", ArrayOfKvmLibelleNiveau.class);
		env.addMapping(Global.NAMESPACE, "libelleniveau", KvmLibelleNiveau.class);
		// Mapping des niveaux objets
		env.addMapping(Global.NAMESPACE, "getNiveauObjetsResponse", ArrayOfKvmNiveauObjet.class);
		env.addMapping(Global.NAMESPACE, "niveauobjet", KvmNiveauObjet.class);
		// Mapping des niveaux objets
		env.addMapping(Global.NAMESPACE, "getTypeReponsesResponse", ArrayOfKvmTypeReponse.class);
		env.addMapping(Global.NAMESPACE, "typereponse", KvmTypeReponse.class);
		
				
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		ht.setXmlVersionTag(Global.XML_TAG);
		// ht.debug = true;
		try {
			System.out.println("synchro stp8"+request.getNamespace()+ "**"+request.getName());
			ht.call(request.getNamespace() + request.getName(), env);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return env;
	}

	
	private void callSetNfc  (Parametrage param, List<Nfc> nfcs)
	{
		
		
		if(nfcs.size() == 0) return;
		
		String urlWS = param.getUrlClient() + Global.WS_END;
		SoapObject request = createBaseRequest("setNfc", param);
		request.addProperty("IdTerminal", param.getNbTerminal());
		
		
		for(int i = 0; i < nfcs.size(); ++i) {
			
			PropertyInfo pi = new PropertyInfo();
			pi.setName("Nfc");
			pi.setValue(new KvmNfc(nfcs.get(i)));
			pi.setType(KvmNfc.class);
			request.addProperty(pi);
		}
		
		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.setOutputSoapObject(request);
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		ht.setXmlVersionTag(Global.XML_TAG);
		//ht.debug = true;
		try {
			ht.call(request.getNamespace() + request.getName(), env);
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
			DatesModifications oldt = helper.getDatesModificationsDao().queryForId("niveau");
			if (oldt!=null)
			{
				
				helper.getDatesModificationsDao().delete(oldt);
				
			}
			
			DatesModifications oldt2 = helper.getDatesModificationsDao().queryForId("niveauobjet");
			if (oldt2!=null)
			{
				oldt2.setTimestamp(null);
				helper.getDatesModificationsDao().delete(oldt2);
				
			}
			
			download(param, doUpload ? 48 : 96);
			// TODO: Parser et vérifier la réponse
		} catch (IOException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		
	}
	private void callSetCloture (Parametrage param, List<Cloture> clotures)
	{
		if(clotures.size() == 0) return;
		String urlWS = param.getUrlClient() + Global.WS_END;
		SoapObject request = createBaseRequest("setCloture", param);
		request.addProperty("IdTerminal", param.getNbTerminal());
		
		System.out.println("cloture ici "+clotures.size());
		for(int i = 0; i < clotures.size(); ++i) {
			
			PropertyInfo pi = new PropertyInfo();
			pi.setName("Cloture");
			pi.setValue(new KvmCloture(clotures.get(i)));
			pi.setType(KvmCloture.class);
			request.addProperty(pi);
		}
		
		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.setOutputSoapObject(request);
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		ht.setXmlVersionTag(Global.XML_TAG);
		//ht.debug = true;
		try {
			ht.call(request.getNamespace() + request.getName(), env);
			// TODO: Parser et vérifier la réponse
		} catch (IOException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	private void callSetStatutInac (Parametrage param, List<StatutInac> statutInac)
	{
		if(statutInac.size() == 0) return;
		String urlWS = param.getUrlClient() + Global.WS_END;
		SoapObject request = createBaseRequest("setStatutInac", param);
		request.addProperty("IdTerminal", param.getNbTerminal());
		
		
		for(int i = 0; i < statutInac.size(); ++i) {
			
			PropertyInfo pi = new PropertyInfo();
			pi.setName("StatutInac");
			pi.setValue(new KvmStatutInac(statutInac.get(i)));
			pi.setType(KvmStatutInac.class);
			request.addProperty(pi);
		}
		
		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.setOutputSoapObject(request);
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		ht.setXmlVersionTag(Global.XML_TAG);
		//ht.debug = true;
		try {
			ht.call(request.getNamespace() + request.getName(), env);
			// TODO: Parser et vérifier la réponse
		} catch (IOException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	private void sendEtat (Parametrage param)
	{
		String res="";
		// on vérifie le login avant toute chose
		final SoapSerializationEnvelope loginEnvelope = call("majdone", param);

		try {
			Object responselog = (Object) loginEnvelope.getResponse();
			res = responselog == null ? "false" : responselog.toString();
			System.out.println("resul ici send Etat **********"+res);
			
			
			
			
		} catch (SoapFault e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	private int miseAjour(Parametrage param) {
		String res="";
		// on vérifie le login avant toute chose
		final SoapSerializationEnvelope loginEnvelope = call("checkNew", param);

		try {
			Object responselog = (Object) loginEnvelope.getResponse();
			res = responselog == null ? "false" : responselog.toString();
			System.out.println("resul ici **********"+res);
			//new SyncTask(helper, true, true,false);
			
			if (res.equals("true"))
			{
				System.out.println("is true1");

				upload(param, 48);
				download(param, 48 );
				sendEtat(param);
				return 100;
			}
			else
			{
				return 0;
			}
		} catch (SoapFault e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
	}
	
	
	private void callSetInspections(Parametrage param, List<Inspection> inspections) {
		if(inspections.size() == 0) return;
		
		String urlWS = param.getUrlClient() + Global.WS_END;
		
		SoapObject request = createBaseRequest("setInspections", param);
		
		request.addProperty("IdTerminal", param.getNbTerminal());
		
		request.addProperty("IdUtilisateur", param.getIdUtilisateur());
		
		for(int i = 0; i < inspections.size(); ++i) {
			
			PropertyInfo pi = new PropertyInfo();
			pi.setName("Inspection");
			pi.setValue(new KvmInspection(inspections.get(i)));
			pi.setType(KvmInspection.class);
			request.addProperty(pi);
		}
		
		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		env.setOutputSoapObject(request);
		
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		
		ht.setXmlVersionTag(Global.XML_TAG);
		//ht.debug = true;
		System.out.println("ins stp8"+request.getNamespace()+request.getName());
		try {
			ht.call(request.getNamespace() + request.getName(), env);
			// TODO: Parser et vérifier la réponse
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	private void callSetStatusPI(Parametrage param, List<StatusPI> statusPI) {
		
		if(statusPI.size() == 0) return;
		
		String urlWS = param.getUrlClient() + Global.WS_END;
		SoapObject request = createBaseRequest("setStatusPI", param);
		request.addProperty("IdTerminal", param.getNbTerminal());
		request.addProperty("IdUtilisateur", param.getIdUtilisateur());
		
		for(int i = 0; i < statusPI.size(); ++i) {
			PropertyInfo pi = new PropertyInfo();
			pi.setName("StatusPI");
			pi.setValue(new KvmStatusPI(statusPI.get(i)));
			pi.setType(KvmStatusPI.class);
			request.addProperty(pi);
		}
		
		SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		env.setOutputSoapObject(request);
		final HttpTransportSE ht = new HttpTransportSE(urlWS);
		ht.setXmlVersionTag(Global.XML_TAG);
		//ht.debug = true;
		try {
			ht.call(request.getNamespace() + request.getName(), env);
			// TODO: Parser et vérifier la réponse
		} catch (IOException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (XmlPullParserException e) {
			Log.e(SyncTask.class.getName(), e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	
}
