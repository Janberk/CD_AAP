package de.canberk.uni.cd_aap;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<Item> {

	private final Context context;
	private final List<Item> values;

	private TextView tv_title;
	private TextView tv_genre;

	public CustomArrayAdapter(Context context, List<Item> values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);

			tv_title = (TextView) rowView.findViewById(R.id.tv_title);
			tv_genre = (TextView) rowView.findViewById(R.id.tv_genre);
			tv_title.setText((values.get(position)).getTitle());
			tv_genre.setText((values.get(position)).getGenre());

		// ImageView imageView = (ImageView)
		// rowView.findViewById(R.id.list_item_icon);

		return rowView;
	}

}
