package de.canberk.uni.cd_aap.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import de.canberk.uni.cd_aap.R;

public class ItemDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_detail, menu);
		return true;
	}

}