package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;


import com.coralc.inspectbox.database.Team;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Choix depuis l'appel au WebService
 */
public class KvmTeam extends Team implements KvmSerializable {
	
	public KvmTeam() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdteam();
		case 1:
			return this.getBeginHour();
		case 2:
			return this.getEndHour();
		case 3:
			return this.getDayOfWork();
		case 4:
			return this.getIdUser();
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
			info.name = "idteam";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "beginHour";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "endHour";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "dayOfWork";
			break;
		case 4:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idUser";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setIdteam(Integer.parseInt(value.toString()));
			break;
		case 1:
			this.setBeginHour(value.toString());
			break;
		case 2:
			this.setEndHour(value.toString());
			break;
		case 3:
			this.setDayOfWork(value.toString());
			break;
		case 4:
			this.setIdUser(Integer.parseInt(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
