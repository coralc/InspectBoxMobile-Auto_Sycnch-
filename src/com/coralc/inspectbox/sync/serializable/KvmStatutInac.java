package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;


import com.coralc.inspectbox.database.StatutInac;

public class KvmStatutInac extends StatutInac implements KvmSerializable {
	public KvmStatutInac() {
		super();
	}
	
	public KvmStatutInac(StatutInac i) {
		super(i);
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdNiveau();
		
		case 1:
			return this.getDateinacc();
		case 2:
			return this.getObjet();
		case 3:
			return this.isStatutValue();
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
			info.name = "idNiveau";
			break;
		
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "dateinacc";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "objet";
			break;
		case 3:
			info.type = PropertyInfo.BOOLEAN_CLASS;
			info.name = "StatutValue";
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
			this.setDateinacc(value.toString());
			break;
		case 2:
			this.setObjet(Integer.parseInt(value.toString()));
			break;
		case 3:
			this.setStatutValue(Boolean.parseBoolean(value.toString()));
			break;
		
		default:
			break;
		}
	}
	
}
