package de.canberk.uni.cd_aap.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import de.canberk.uni.cd_aap.R;

public class MainActivity extends Activity {

	private Button btn_logout;
	private TextView tv_welcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		initElements();
		tv_welcome.setText(getUser());

		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();
			}
		});
	}

	public void initElements() {
		btn_logout = (Button) findViewById(R.id.btn_logout);
		tv_welcome = (TextView) findViewById(R.id.tv_welcome);
	}
	
	public String getUser() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				LogInActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
		String user = sharedPreferences.getString(LogInActivity.EMAIL, "");
		return user;
	}

	public void logout() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				LogInActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		moveTaskToBack(true);
		MainActivity.this.finish();
		Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
