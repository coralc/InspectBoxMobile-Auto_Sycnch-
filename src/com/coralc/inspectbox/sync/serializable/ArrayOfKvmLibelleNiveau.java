package com.coralc.inspectbox.sync.serializable;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/*
 * Tableau de KvmLibelleNiveau serializable
 */
public class ArrayOfKvmLibelleNiveau extends ArrayList<KvmLibelleNiveau> implements KvmSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1097998358298285239L;

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
		info.type = KvmLibelleNiveau.class;
	}

	@Override
	public void setProperty(int index, Object value) {
		this.add((KvmLibelleNiveau)value);
	}
	
}
