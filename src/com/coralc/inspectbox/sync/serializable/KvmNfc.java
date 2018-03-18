package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.Nfc;

public class KvmNfc extends Nfc implements KvmSerializable {
	public KvmNfc() {
		super();
	}
	
	public KvmNfc(Nfc i) {
		super(i);
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getIdNiveau();
		
		case 1:
			return this.getNfcTag();
		
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 2;
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
			info.name = "nfcTag";
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
			this.setNfcTag(value.toString());
			break;
		
		
		default:
			break;
		}
	}
	
}
