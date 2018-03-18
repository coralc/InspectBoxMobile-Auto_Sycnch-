package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.StatusPI;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les Inspections depuis l'appel au WebService
 */
public class KvmStatusPI extends StatusPI implements KvmSerializable {
	
	public KvmStatusPI() {
		super();
	}
	
	public KvmStatusPI(StatusPI i) {
		super(i);
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getAreaTag();
		case 1:
			return this.getTagName();
		case 2:
			return this.getTagDescription();
		case 3:
			return this.getTagUnit();
		case 4:
			return this.getStatus();
		case 5:
			return this.getValue();
		case 6:
			return this.getLowLimit();
		case 7:
			return this.getHighLimit();
		case 8:
			return this.getLowBorder();
		case 9:
			return this.getHighBorder();
		case 10:
			return this.getTimestamp();
		case 11:
			return this.getClientName();
		
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 12;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "areaTag";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "tagName";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "tagDescription";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "tagUnit";
			break;
		case 4:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "status";
			break;
		case 5:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "value";
			break;
		case 6:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "lowLimit";
			break;
		case 7:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "highLimit";
			break;
		case 8:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "lowBorder";
			break;
		case 9:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "highBorder";
			break;
		case 10:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "timestamp";
			break;
		case 11:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "clientName";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setAreaTag(value.toString());
			break;
		case 1:
			this.setTagName(value.toString());
			break;
		case 2:
			this.setTagDescription(value.toString());
			break;
		case 3:
			this.setTagUnit(value.toString());
			break;
		case 4:
			this.setStatus(Integer.parseInt(value.toString()));
			break;
		case 5:
			this.setValue(value.toString());
			break;
		case 6:
			this.setLowLimit(value.toString());
			break;
		case 7:
			this.setHighLimit(value.toString());
			break;
		case 8:
			this.setLowBorder(value.toString());
			break;
		case 9:
			this.setHighBorder(value.toString());
			break;
		case 10:
			this.setTimestamp(value.toString());
			break;
		case 11:
			this.setClientName(value.toString());
			break;
		default:
			break;
		}
	}
	
}
