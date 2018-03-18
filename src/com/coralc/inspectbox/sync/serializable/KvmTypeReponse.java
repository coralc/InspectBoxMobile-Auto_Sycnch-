package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.TypeReponse;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les TypeReponse depuis l'appel au WebService
 */
public class KvmTypeReponse extends TypeReponse implements KvmSerializable {
	
	public KvmTypeReponse() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdTypeReponse();
		case 1:
			return this.getLibelle();
		case 2:
			return this.getModeReponse();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 3;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idTypeReponse";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "libelle";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "modeReponse";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdTypeReponse(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setLibelle(value.toString());
			break;
		case 2:
			this.setModeReponse(value.toString());
			break;
		default:
			break;
		}
	}
	
}
