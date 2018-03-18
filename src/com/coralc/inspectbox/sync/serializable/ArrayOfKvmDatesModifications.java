package com.coralc.inspectbox.sync.serializable;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/*
 * Tableau de KvmDatesModifications serializable
 */
public class ArrayOfKvmDatesModifications extends ArrayList<KvmDatesModifications> implements KvmSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5501308606672173243L;

	@Override
	public Object getProperty(int index) {
		return this.get(index);
	}

	@Override
	public int getPropertyCount() {
		return 1;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties,
			PropertyInfo info) {
		info.name = "return";
		info.type = KvmDatesModifications.class;
	}

	@Override
	public void setProperty(int index, Object value) {
		this.add((KvmDatesModifications)value);
	}
	
	public Boolean containsTable(String table) {
		for(KvmDatesModifications d : this) {
			if(d.getNomTable().equals(table)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * retourne le premier élément KvmDatesModifications correspondant au nomTable ou null
	 */
	public KvmDatesModifications find(String nomTable) {
		for(KvmDatesModifications d : this) {
			if(d.getNomTable().equals(nomTable)) {
				return d;
			}
		}
		return null;
	}
}
