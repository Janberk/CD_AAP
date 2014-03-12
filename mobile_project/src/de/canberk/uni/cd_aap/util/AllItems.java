package de.canberk.uni.cd_aap.util;

import java.util.ArrayList;

import android.content.Context;
import de.canberk.uni.cd_aap.data.DAOItem;
import de.canberk.uni.cd_aap.model.Item;

public class AllItems {

	// Singleton class...
	private static AllItems allItems;

	@SuppressWarnings("unused")
	private Context applicationContext;

	private DAOItem daoItem;

	private ArrayList<Item> itemList;

	private AllItems(Context applicationContext) {
		this.applicationContext = applicationContext;

		setDaoItem(new DAOItem(applicationContext));

		if (daoItem != null) {
			itemList = daoItem.getAllItems();
		}

	}

	public ArrayList<Item> reorderItemList(ArrayList<Item> itemList) {
		ArrayList<Item> list = new ArrayList<Item>();
		for (Item item : itemList) {
			list.add(0, item);
		}
		return list;
	}

	public static AllItems get(Context context) {

		if (allItems == null) {
			allItems = new AllItems(context.getApplicationContext());
		}
		return allItems;

	}

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public Item getItem(int id) {

		for (Item theItem : itemList) {
			// if (theItem.getId().equals(id)) {
			if (theItem.getId() == id) {
				return theItem;
			}
		}
		return null;

	}

	public DAOItem getDaoItem() {
		return daoItem;
	}

	public void setDaoItem(DAOItem daoItem) {
		this.daoItem = daoItem;
	}

}