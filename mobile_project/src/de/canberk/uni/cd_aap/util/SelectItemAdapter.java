package de.canberk.uni.cd_aap.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.canberk.uni.cd_aap.R;

public class SelectItemAdapter extends ArrayAdapter<String> {

	private ArrayList<String> listEntries;
	private final Context context;

	public SelectItemAdapter(Context context, ArrayList<String> listEntries) {
		super(context, android.R.layout.simple_list_item_1, listEntries);
		this.context = context;
		this.listEntries = listEntries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.select_list_item, null);
		}

		String typeAsString = listEntries.get(position);
		ItemType type = null;

		if (typeAsString.equals(ItemType.All.name())) {
			type = ItemType.All;
		} else if (typeAsString.equals(ItemType.Album.name())) {
			type = ItemType.Album;
		} else if (typeAsString.equals(ItemType.Book.name())) {
			type = ItemType.Book;
		} else if (typeAsString.equals(ItemType.Movie.name())) {
			type = ItemType.Movie;
		}

		ImageView iv_itemIcon = (ImageView) convertView
				.findViewById(R.id.iv_itemIcon);

		iv_itemIcon.setFadingEdgeLength(2);
		UtilMethods.setCustomIconToTypeOfMedia(iv_itemIcon, type);

		String text = getItem(position);

		TextView tv_listEntry = (TextView) convertView
				.findViewById(R.id.tv_selectList);
		tv_listEntry.setText(text);

		return convertView;

	}

	public void refresh(ArrayList<String> listEntries) {
		this.listEntries = listEntries;
		notifyDataSetChanged();
	}

}