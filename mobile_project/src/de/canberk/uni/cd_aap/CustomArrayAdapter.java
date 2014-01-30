package de.canberk.uni.cd_aap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomArrayAdapter extends ArrayAdapter<Movie> {

	private final Context context;
	private final Movie[] values;

	public CustomArrayAdapter(Context context, Movie[] values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);

		TextView tv_title = (TextView) rowView.findViewById(R.id.tv_title);
		TextView tv_genre = (TextView) rowView.findViewById(R.id.tv_genre);
		tv_title.setText(values[position].getTitle());
		tv_genre.setText(values[position].getGenre());

		// ImageView imageView = (ImageView)
		// rowView.findViewById(R.id.list_item_icon);

		return rowView;
	}

}
