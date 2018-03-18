package com.coralc.inspectbox.adapter;

import java.util.List;

import com.coralc.inspectbox.pojo.ContenuHisto;
import com.coralc.inspectbox.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContenuHisAdapter extends ArrayAdapter<ContenuHisto> {
	private int layoutResourceId;
	private List<ContenuHisto> data = null;
	private LayoutInflater inflater = null;

	public ContenuHisAdapter(Context context, int layoutResourceId, List<ContenuHisto> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.inflater = ((Activity)context).getLayoutInflater();
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// Si la vue n'est pas recycl�e
		if (convertView == null) {
			// On r�cup�re le layout
			convertView = inflater.inflate(layoutResourceId, parent, false);

			holder = new ViewHolder();
			// On place les widgets de notre layout dans le holder
			holder.txtdate = (TextView) convertView.findViewById(R.id.txtDate);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitlei);

			// puis on ins�re le holder en tant que tag dans le layout
			convertView.setTag(holder);
		} else {
			// Si on recycle la vue, on r�cup�re son holder en tag
			holder = (ViewHolder) convertView.getTag();
		}

		// Dans tous les cas, on r�cup�re le ContenuNiveau concern�
		ContenuHisto c = data.get(position);
		// Si cet �l�ment existe vraiment
		if (c != null) {
			// On place dans le holder les informations sur le contact
			holder.txtTitle.setText(c.getValeur());
			holder.txtdate.setText(c.getDatet());
			
			
		}
		return convertView;
	}

	static class ViewHolder {
		
		TextView txtTitle;
		TextView txtdate;
	}
}