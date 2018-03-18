package com.coralc.inspectbox.sync.serializable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import com.coralc.inspectbox.database.NiveauObjet;

/*
 * Cette classe implémente l'interface KvmSerializable
 * Elle permet de récupérer les NiveauObjet depuis l'appel au WebService
 */
public class KvmNiveauObjet extends NiveauObjet implements KvmSerializable {
	
	public KvmNiveauObjet() {
		super();
	}

	@Override
	public Object getProperty(int index) {
		switch (index) {
		case 0:
			return this.getCodebarre();
		case 1:
			return this.getIdNiveauobjetparent();
		case 2:
			return this.getIdNiveauobjet();
		case 3:
			return this.getIdTypereponse();
		case 4:
			return this.getLibelle();
		case 5:
			return this.getTri();
		case 6:
			return this.getUnitmeasure();
		case 7:
			return this.getLowborder();
		case 8:
			return this.getHighborder();
		case 9:
			return this.getLowlimit();
		case 10:
			return this.getHighlimit();
		case 11:
			return this.getLowborderalert();
		case 12:
			return this.getHighborderalert();
		default:
			return null;
		}
	}

	@Override
	public int getPropertyCount() {
		return 13;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int index, Hashtable properties, 
			PropertyInfo info) {
		switch (index) {
		case 0:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "codeBarre";
			break;
		
		case 1:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveauObjetParent";
			break;
		case 2:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idNiveauobjet";
			break;
		case 3:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "idTypereponse";
			break;
		case 4:
			info.type =  PropertyInfo.STRING_CLASS;
			info.name = "libelle";
			break;
		
		case 5:
			info.type =  PropertyInfo.INTEGER_CLASS;
			info.name = "tri";
			break;
		case 6:
			info.type =  PropertyInfo.STRING_CLASS;
			info.name = "unitmeasure";
			break;
		case 7:
			info.type = Double.class;
			info.name = "lowborder";
			break;
		case 8:
			info.type =  Double.class;
			info.name = "highborder";
			break;
		case 9:
			info.type =  Double.class;
			info.name = "lowlimit";
			break;
		case 10:
			info.type =  Double.class;
			info.name = "highlimit";
			break;
		case 11:
			info.type =  PropertyInfo.STRING_CLASS;
			info.name = "lowborderalert";
			break;
		case 12:
			info.type =  PropertyInfo.STRING_CLASS;
			info.name = "highborderalert";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int index, Object value) {
		switch (index) {
		case 0:
			this.setCodebarre(value.toString());
			break;
		case 1:
			this.setIdNiveauobjetparent(Integer.parseInt(value.toString()));
			break;
		case 2:
			this.setIdNiveauobjet(Integer.parseInt(value.toString()));
			break;
		case 3:
			this.setIdTypereponse(Integer.parseInt(value.toString()));
			break;
		case 4:
			this.setLibelle(value.toString());
			break;
		case 5:
			this.setTri(Integer.parseInt(value.toString()));
			break;
		case 6:
			this.setUnitmeasure(value.toString());
			break;
		case 7:
			this.setLowborder(Double.parseDouble(value.toString()));
			break;
		case 8:
			this.setHighborder(Double.parseDouble(value.toString()));
			break;
		
		case 9:
			this.setLowlimit(Double.parseDouble(value.toString()));
			break;
		case 10:
			this.setHighlimit(Double.parseDouble(value.toString()));
			break;
		case 11:
			this.setLowborderalert(value.toString());
			break;
		case 12:
			this.setHighborderalert(value.toString());
			break;
		default:
			break;
		}
	}
	
}
