package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;


import com.coralc.inspectbox.database.Objeteam;


/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les TeamObjet depuis l'appel au WebService
 */
public class KvmObjeteam extends Objeteam implements KvmSerializable {
	
	public KvmObjeteam() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdField();
		case 1:
			return this.getIdObjet();
		case 2:
			return this.getIdTeam();
		case 3:
			return this.isLun();
		case 4:
			return this.isMar();
		case 5:
			return this.isMer();
		case 6:
			return this.isJeu();
		case 7:
			return this.isVen();
		case 8:
			return this.isSam();
		case 9:
			return this.isDim();
		
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 10;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idField";
			break;
		case 1:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idObjet";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idTeam";
			break;
		case 3:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "lun";
			break;
		case 4:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "mar";
			break;
		case 5:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "mer";
			break;
		case 6:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "jeu";
			break;
		case 7:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "ven";
			break;
		case 8:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "sam";
			break;
		case 9:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "dim";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdField(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setIdObjet(Integer.parseInt(value.toString()));
			break;
		case 2:
			this.setIdTeam(Integer.parseInt(value.toString()));
			break;
		case 3:
			this.setLun(Boolean.parseBoolean(value.toString()));
			break;
		case 4:
			this.setMar(Boolean.parseBoolean(value.toString()));
			break;
		case 5:
			this.setMer(Boolean.parseBoolean(value.toString()));
			break;
		case 6:
			this.setJeu(Boolean.parseBoolean(value.toString()));
			break;
		case 7:
			this.setVen(Boolean.parseBoolean(value.toString()));
			break;
		case 8:
			this.setSam(Boolean.parseBoolean(value.toString()));
			break;
		case 9:
			this.setDim(Boolean.parseBoolean(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
