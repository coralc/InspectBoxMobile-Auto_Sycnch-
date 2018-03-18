package com.coralc.inspectbox.sync.serializable;

import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/*
 * Tableau de KvmUtilisateur serializable
 */
public class ArrayOfKvmUtilisateur extends ArrayList<KvmUtilisateur> implements KvmSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -289730300015274609L;

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
		//info.namespace = null;
		info.type = KvmUtilisateur.class;
	}

	@Override
	public void setProperty(int index, Object value) {
		this.add((KvmUtilisateur)value);
	}
	
}
