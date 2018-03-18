package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Utilisateur;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Utilisateur depuis l'appel au WebService
 */
public class KvmUtilisateur extends Utilisateur implements KvmSerializable {
	
	public KvmUtilisateur() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getCodeAcces();
		case 1:
			return this.getIdUtilisateur();
		case 2:
			return this.getMotDePasse();
		case 3:
			return this.getNom();
		case 4:
			return this.getPrenom();
		case 5:
			return this.getTypeUser();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 6;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "codeAcces";
			break;
		case 1:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idUtilisateur";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "motDePasse";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "nom";
			break;
		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "prenom";
			break;
		case 5:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "typeUser";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setCodeAcces(value.toString());
			break;
		case 1:
			this.setIdUtilisateur(Integer.parseInt(value.toString()));
			break;
		case 2:
			this.setMotDePasse(value.toString());
			break;
		case 3:
			this.setNom(value.toString());
			break;
		case 4:
			this.setPrenom(value.toString());
			break;
		case 5:
			this.setTypeUser(value.toString());
			break;
		default:
			break;
		}
	}
	
}
