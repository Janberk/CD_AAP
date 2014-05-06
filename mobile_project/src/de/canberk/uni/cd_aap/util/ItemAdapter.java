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
import de.canberk.uni.cd_aap.fragments.ItemListFragment;
import de.canberk.uni.cd_aap.model.Item;

public class ItemAdapter extends ArrayAdapter<Item> {

	private ArrayList<Item> itemList;
	private final Context context;

	public ItemAdapter(Context context, ArrayList<Item> items) {
		super(context, android.R.layout.simple_list_item_1, items);
		this.context = context;
		this.itemList = items;
	}

	// TODO ViewHolder Pattern
	static class ViewHolder {
		ImageView iv_itemIcon;
		TextView tv_itemGenre;
		TextView tv_itemTitle;
		CheckBox cb_itemFavorite;
		CheckBox cb_itemDelete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewHolder holder;

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder();

			holder.iv_itemIcon = (ImageView) convertView
					.findViewById(R.id.iv_itemIcon);
			holder.tv_itemGenre = (TextView) convertView
					.findViewById(R.id.tv_itemGenre);
			holder.tv_itemTitle = (TextView) convertView
					.findViewById(R.id.tv_itemTitle);
			holder.cb_itemFavorite = (CheckBox) convertView
					.findViewById(R.id.cb_itemFavorite);
			holder.cb_itemDelete = (CheckBox) convertView
					.findViewById(R.id.cb_itemDelete);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Item theItem = getItem(position);
		ItemType type = (ItemType) itemList.get(position).getType();

		holder.iv_itemIcon.setFadingEdgeLength(2);
		UtilMethods.setCustomIconToTypeOfMedia(holder.iv_itemIcon, type);
		holder.tv_itemGenre.setText(theItem.getGenre());
		holder.tv_itemTitle.setText(theItem.getTitle());
		holder.cb_itemFavorite.setChecked(theItem.isFavorite());
		
		if (ItemListFragment.editMode) {
			holder.cb_itemDelete.setVisibility(View.VISIBLE);
		} else {
			holder.cb_itemDelete.setVisibility(View.GONE);
		}		

		return convertView;

	}

	public void refresh(ArrayList<Item> itemList) {
		this.itemList = itemList;
		notifyDataSetChanged();
	}

}