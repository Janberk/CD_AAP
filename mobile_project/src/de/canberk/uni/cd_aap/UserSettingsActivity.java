package de.canberk.uni.cd_aap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_settings_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_settings, menu);
		return true;
	}

}
