package com.coralc.inspectbox;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.coralc.inspectbox.adapter.ContenuHisAdapter;
import com.coralc.inspectbox.database.DatabaseHelper;
import com.coralc.inspectbox.database.Historique;
import com.coralc.inspectbox.database.NiveauObjet;
import com.coralc.inspectbox.pojo.ContenuHisto;
import com.coralc.inspectbox.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class HistoriqueActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	private ListView lvHisto;
	private TextView txtObjet;
	
	private TextView txtChemin;
	private int idNiveauObjet;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.histo);

		// Initalisation des widgets
		
		lvHisto = (ListView) findViewById(R.id.lvHisto);
		txtObjet = (TextView) findViewById(R.id.txtObjeth);
		txtChemin = (TextView) findViewById(R.id.txtCheminh);
		
		
		// récupération des valeurs
		Intent intent = this.getIntent();
		idNiveauObjet = intent.getIntExtra("idNiveauObjet", 0);
		

		// s'il y a eut un changement de config, on recharge ces valeurs
		if (savedInstanceState != null) {
			idNiveauObjet = savedInstanceState.getInt("idNiveauObjet");
			
		}

		bindData();
		
		
	}
public void bindData()
{
	NiveauObjet no = getHelper().getNiveauObjetDao().queryForId(idNiveauObjet);
	txtObjet.setText(no.getLibelle());
	
	List<Historique> historiqueList = getHelper().getHistoriqueDao().queryWhereIdObjet(idNiveauObjet);
	
	List<ContenuHisto> listContenuNiveau = null;
	listContenuNiveau = new ArrayList<ContenuHisto>(historiqueList.size());
	for (Historique h : historiqueList) {
		listContenuNiveau.add(new ContenuHisto(h));
		
		
	}
	
	ContenuHisAdapter adapter = new ContenuHisAdapter(this, R.layout.listhisto_item_row,
			listContenuNiveau);

	lvHisto.setAdapter(adapter);
}
}