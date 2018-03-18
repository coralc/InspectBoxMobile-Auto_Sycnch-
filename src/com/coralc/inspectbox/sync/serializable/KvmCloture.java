package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Cloture;

public class KvmCloture extends Cloture implements KvmSerializable {
	public KvmCloture() {
		super();
	}
	
	public KvmCloture(Cloture i) {
		super(i);
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdNiveau();
		case 1:
			return this.getDateCloture();
		case 2:
			return this.getDebDateCloture();
		case 3:
			return this.getStatutCloture();
		case 4:
			return this.getUserCloture();
		case 5:
			return this.getDayCloture();
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
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveau";
			break;
		
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "dateCloture";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "debDateCloture";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "statutCloture";
			break;
		case 4:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "userCloture";
			break;
		case 5:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "dayCloture";
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
			this.setDateCloture(value.toString());
			break;
		case 2:
			this.setDebDateCloture(value.toString());
			break;
		case 3:
			this.setStatutCloture(Integer.parseInt(value.toString()));
			break;
		case 4:
			this.setUserCloture(Integer.parseInt(value.toString()));
			break;
		case 5:
			this.setDayCloture(Integer.parseInt(value.toString()));
			break;
		default:
			break;
		}
	}
	
}
