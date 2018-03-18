package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Niveau;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Niveau depuis l'appel au WebService
 */
public class KvmNiveau extends Niveau implements KvmSerializable {
	
	public KvmNiveau() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getCodebarre();
		case 1:
			return this.getAreaTag();
		case 2:
			return this.getIdNiveau();
		case 3:
			return this.getIdNiveauParent();
		case 4:
			return this.getIdNiveauObjet();
		case 5:
			return this.getIdTypeReponse();
		case 6:
			return this.getLibelle();
		case 7:
			return this.getTri();
		case 8:
			return this.getLevel();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 9;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "codeBarre";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "areaTag";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveau";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveauParent";
			break;
		case 4:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveauobjet";
			break;
		case 5:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idTypereponse";
			break;
		case 6:
			info.type =  PropertyInfo.STRING_CLASS;
			info.name = "libelle";
			break;
		case 7:
			info.type =  PropertyInfo.INTEGER_CLASS;
			info.name = "tri";
			break;
		case 8:
			info.type =  PropertyInfo.INTEGER_CLASS;
			info.name = "level";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setCodebarre(value.toString());
			break;
		case 1:
			this.setAreaTag(value.toString());
			break;
		case 2:
			this.setIdNiveau(Integer.parseInt(value.toString()));
			break;
		case 3:
			this.setIdNiveauParent(Integer.parseInt(value.toString()));
			break;
		case 4:
			this.setIdNiveauObjet(Integer.parseInt(value.toString()));
			break;
		case 5:
			this.setIdTypeReponse(Integer.parseInt(value.toString()));
			break;
		case 6:
			this.setLibelle(value.toString());
			break;
		case 7:
			this.setTri(Integer.parseInt(value.toString()));
			break;
		case 8:
			this.setLevel(Integer.parseInt(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
