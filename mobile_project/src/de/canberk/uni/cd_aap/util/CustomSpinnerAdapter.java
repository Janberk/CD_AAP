package de.canberk.uni.cd_aap.util;

import android.content.Context;
import android.widget.ArrayAdapter;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.model.Item;

public class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {

	public CustomSpinnerAdapter(Context context, int resource,
			int simpleSpinnerItem) {
		super(context, resource, simpleSpinnerItem);
	}

	public int getSpinnerSelectionIndex(Item item) {

		int selectionIndex = 0;
		ItemType type = item.getType();

		switch (type) {
		case Album:
			selectionIndex = ProjectConstants.ALBUM;
			break;
		case Book:
			selectionIndex = ProjectConstants.BOOK;
			break;
		case Movie:
			selectionIndex = ProjectConstants.MOVIE;
			break;

		default:
			break;
		}

		return selectionIndex;

	}

}
