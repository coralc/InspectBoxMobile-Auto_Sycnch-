package com.coralc.inspectbox.adapter;

import java.util.List;

import com.coralc.inspectbox.pojo.ContenuNiveau;
import com.coralc.inspectbox.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContenuNiveauAdapter extends ArrayAdapter<ContenuNiveau> {
	private int layoutResourceId;
	private List<ContenuNiveau> data = null;
	private LayoutInflater inflater = null;

	public ContenuNiveauAdapter(Context context, int layoutResourceId, List<ContenuNiveau> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.inflater = ((Activity)context).getLayoutInflater();
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// Si la vue n'est pas recyclée
		if (convertView == null) {
			// On récupère le layout
			convertView = inflater.inflate(layoutResourceId, parent, false);

			holder = new ViewHolder();
			// On place les widgets de notre layout dans le holder
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			
			// puis on insère le holder en tant que tag dans le layout
			convertView.setTag(holder);
		} else {
			// Si on recycle la vue, on récupère son holder en tag
			holder = (ViewHolder) convertView.getTag();
		}

		// Dans tous les cas, on récupère le ContenuNiveau concerné
		ContenuNiveau c = data.get(position);
		// Si cet élément existe vraiment
		if (c != null) {
			// On place dans le holder les informations sur le contact
			holder.imgIcon.setImageResource(c.getIcone());
			holder.txtTitle.setText(c.getLibelle());
			
		}
		return convertView;
	}

	static class ViewHolder {
		ImageView imgIcon;
		TextView txtTitle;
		
	}
}