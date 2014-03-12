package de.canberk.uni.cd_aap.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import de.canberk.uni.cd_aap.model.Item;

public class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {

	public static final int ALBUM = 0;
	public static final int BOOK = 1;
	public static final int MOVIE = 2;

	public CustomSpinnerAdapter(Context context, int resource,
			int simpleSpinnerItem) {
		super(context, resource, simpleSpinnerItem);
	}

	public int getSpinnerSelectionIndex(Item item) {

		int selectionIndex = 0;
		ItemType type = item.getType();

		switch (type) {
		case Album:
			selectionIndex = ALBUM;
			break;
		case Book:
			selectionIndex = BOOK;
			break;
		case Movie:
			selectionIndex = MOVIE;
			break;

		default:
			break;
		}

		return selectionIndex;

	}

}
