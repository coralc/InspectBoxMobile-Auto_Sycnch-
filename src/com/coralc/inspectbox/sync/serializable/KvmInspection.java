package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Inspection;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Inspections depuis l'appel au WebService
 */
public class KvmInspection extends Inspection implements KvmSerializable {
	
	public KvmInspection() {
		super();
	}
	
	public KvmInspection(Inspection i) {
		super(i);
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdNiveau();
		case 1:
			return this.getIdNiveauObjet();
		case 2:
			return this.getIdInformation();
		case 3:
			return this.getIdChoix();
		case 4:
			return this.getReponse();
		
		case 5:
			return this.getIdNiveaubat();
		
		case 6:
			return this.getDateInformation();
		case 7:
			return this.getDefekt();
		case 8:
			return this.getLimite();
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
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveau";
			break;
		case 1:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveauObjet";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "idInformation";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idChoix";
			break;
		case 4:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "reponse";
			break;
		
		
		case 5:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveaubat";
			break;
		case 6:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "dateInformation";
			break;
		case 7:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "defekt";
			break;
		case 8:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "limite";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdNiveau(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setIdNiveauObjet(Integer.parseInt(value.toString()));
			break;
		case 2:
			this.setIdInformation(value.toString());
			break;
		case 3:
			this.setIdChoix(Integer.parseInt(value.toString()));
			break;
		case 4:
			this.setReponse(value.toString());
			break;
		
		
		case 5:
			this.setIdNiveaubat(Integer.parseInt(value.toString()));
			break;
		case 6:
			this.setDateInformation(value.toString());
			break;
		case 7:
			this.setDefekt(Boolean.parseBoolean(value.toString()));
			break;
		case 8:
			this.setLimite(Boolean.parseBoolean(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
