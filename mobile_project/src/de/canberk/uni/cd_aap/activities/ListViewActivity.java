package de.canberk.uni.cd_aap.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import de.canberk.uni.cd_aap.CustomArrayAdapter;
import de.canberk.uni.cd_aap.Item;
import de.canberk.uni.cd_aap.Movie;
import de.canberk.uni.cd_aap.R;

@SuppressLint("NewApi")
public class ListViewActivity extends Activity {

	private EditText et_newItemName;
	private Button btn_add;
	private String itemTitle;
	private List<Item> items = new ArrayList<Item>();
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_screen);

		listView = (ListView) findViewById(R.id.listview);

		et_newItemName = (EditText) findViewById(R.id.et_newItemName);
		btn_add = (Button) findViewById(R.id.btn_add);

		final CustomArrayAdapter adapter = new CustomArrayAdapter(this, items);
		listView.setAdapter(adapter);

		// listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		// {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, final View view,
		// int position, long id) {
		// final Movie item = (Movie) parent.getItemAtPosition(position);
		// view.animate().setDuration(2000).alpha(0)
		// .withEndAction(new Runnable() {
		// @Override
		// public void run() {
		// list.remove(item);
		// adapter.notifyDataSetChanged();
		// view.setAlpha(1);
		// }
		// });
		// }
		//
		// });

		btn_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intent = new Intent(getApplicationContext(),
				// ItemDetailActivity.class);
				// startActivity(intent);
				// addItem();
				addItem();
				adapter.notifyDataSetChanged();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Click ListItem Number " + position, Toast.LENGTH_LONG)
				// .show();
				Intent intent = new Intent(getApplicationContext(),
						ItemDetailActivity.class);
				startActivity(intent);
			}

		});

	}

	// private void addSomeDummies() {
	// Item movie_01 = new Movie("Star Trek");
	// Item movie_02 = new Movie("Brave Heart");
	// Item movie_03 = new Movie("Forrest Gump");
	// Item movie_04 = new Movie("Escape Plan");
	// movie_04.setGenre("Action, sonst fällt mir auch nichts ein hahahahahahhaaha");
	// Item book_01 = new Book("Robinson Crusoe");
	//
	// items.add(movie_01);
	// items.add(movie_02);
	// items.add(movie_03);
	// items.add(movie_04);
	// items.add(book_01);
	//
	// }

	public void addItem() {
		itemTitle = et_newItemName.getText().toString();
		items.add(0, new Movie(itemTitle));

	}

	// private class StableArrayAdapter extends ArrayAdapter<Movie> {
	//
	// HashMap<Movie, Integer> mIdMap = new HashMap<Movie, Integer>();
	//
	// public StableArrayAdapter(Context context, int textViewResourceId,
	// List<Movie> objects) {
	// super(context, textViewResourceId, objects);
	// for (int i = 0; i < objects.size(); ++i) {
	// mIdMap.put(objects.get(i), i);
	// }
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// Movie item = getItem(position);
	// return mIdMap.get(item);
	// }
	//
	// @Override
	// public boolean hasStableIds() {
	// return true;
	// }
	//
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_view, menu);
		return true;
	}

}
