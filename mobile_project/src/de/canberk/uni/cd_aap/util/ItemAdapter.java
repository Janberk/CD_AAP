package de.canberk.uni.cd_aap.util;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.model.Item;

public class ItemAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> itemList;
	private final Context context;
	
	private CheckBox cb_itemDelete;

	public ItemAdapter(Context context, ArrayList<Item> items) {
		super(context, android.R.layout.simple_list_item_1, items);
		this.context = context;
		this.itemList = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_item, null);
		}

		Item theItem = getItem(position);
		ItemType type = (ItemType) itemList.get(position).getType();
		ImageView iv_itemIcon = (ImageView) convertView
				.findViewById(R.id.iv_itemIcon);

		iv_itemIcon.setFadingEdgeLength(2);
		UtilMethods.setCustomIconToTypeOfMedia(iv_itemIcon, type);

		TextView tv_itemGenre = (TextView) convertView
				.findViewById(R.id.tv_itemGenre);
		tv_itemGenre.setText(theItem.getGenre());

		TextView tv_itemTitle = (TextView) convertView
				.findViewById(R.id.tv_itemTitle);
		tv_itemTitle.setText(theItem.getTitle());

		CheckBox cb_itemFavorite = (CheckBox) convertView
				.findViewById(R.id.cb_itemFavorite);
		cb_itemFavorite.setChecked(theItem.isFavorite());

		cb_itemDelete = (CheckBox) convertView
				.findViewById(R.id.cb_itemDelete);
		cb_itemDelete.setVisibility(View.GONE);

		return convertView;

	}

	public void refresh(ArrayList<Item> itemList) {
		this.itemList = itemList;
		notifyDataSetChanged();
	}

}