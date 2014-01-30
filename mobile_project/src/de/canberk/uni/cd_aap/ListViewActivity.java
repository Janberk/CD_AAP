package de.canberk.uni.cd_aap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ListViewActivity extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view_screen);
		
		final ListView listView = (ListView) findViewById(R.id.listview);

		Movie movie_01 = new Movie("Star Trek");
		Movie movie_02 = new Movie("Brave Heart");
		Movie movie_03 = new Movie("Forrest Gump");
		Movie movie_04 = new Movie("Escape Plan");
		movie_04.setGenre("Action, sonst fällt mir auch nichts ein hahahahahahhaaha");
		Movie movie_05 = new Movie("Captain Phillips");

		// final ArrayList<Movie> list = new ArrayList<Movie>();
		//
		// list.add(movie_01);
		// list.add(movie_02);
		// list.add(movie_03);
		// list.add(movie_04);
		// list.add(movie_05);

		final Movie[] movies = new Movie[5];

		movies[0] = movie_01;
		movies[1] = movie_02;
		movies[2] = movie_03;
		movies[3] = movie_04;
		movies[4] = movie_05;

		final CustomArrayAdapter adapter = new CustomArrayAdapter(this, movies);
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

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}

		});

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
