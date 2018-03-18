package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.DatesModifications;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les DatesModifications depuis l'appel au WebService
 */
public class KvmDatesModifications extends DatesModifications implements KvmSerializable {
	
	public KvmDatesModifications() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getNomTable();
		case 1:
			return this.getTimestamp();
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
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "nomTable";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "timestamp";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setNomTable(value.toString());
			break;
		case 1:
			this.setTimestamp(value.toString());
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		return this.getNomTable();
	}
}
