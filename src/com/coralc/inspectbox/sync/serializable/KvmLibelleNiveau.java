package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.LibelleNiveau;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les LibelleNiveau depuis l'appel au WebService
 */
public class KvmLibelleNiveau extends LibelleNiveau implements KvmSerializable {
	
	public KvmLibelleNiveau() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdLibelleniveau();
		case 1:
			return this.getLibelle();
		case 2:
			return this.getNum();
		case 3:
			return this.getTypeniveau();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 4;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idLibelleNiveau";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "libelle";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "num";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "typeNiveau";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdLibelleniveau(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setLibelle(value.toString());
			break;
		case 2:
			this.setNum(Integer.parseInt(value.toString()));
			break;
		case 3:
			this.setTypeniveau(Integer.parseInt(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
