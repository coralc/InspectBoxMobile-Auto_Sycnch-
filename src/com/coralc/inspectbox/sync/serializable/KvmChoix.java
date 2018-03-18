package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Choix;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Choix depuis l'appel au WebService
 */
public class KvmChoix extends Choix implements KvmSerializable {
	
	public KvmChoix() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdChoix();
		case 1:
			return this.getIdTypeReponse();
		case 2:
			return this.isNonConforme();
		case 3:
			return this.getTri();
		case 4:
			return this.getValeur();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 5;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idChoix";
			break;
		case 1:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idTypereponse";
			break;
		case 2:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "nonConforme";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "tri";
			break;
		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "valeur";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdChoix(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setIdTypeReponse(Integer.parseInt(value.toString()));
			break;
		case 2:
			this.setNonConforme(Boolean.parseBoolean(value.toString()));
			break;
		case 3:
			this.setTri(Integer.parseInt(value.toString()));
			break;
		case 4:
			this.setValeur(value.toString());
			break;
		default:
			break;
		}
	}
	
}
