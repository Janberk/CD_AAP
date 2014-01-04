package de.canberk.uni.cd_aap.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.HTTPRequestHandler;
import de.canberk.uni.cd_aap.R;

public class LogInActivity extends Activity {

	public static final String MY_PREFERENCES = "MyPrefs";
	public static final String EMAIL = "emailKey";
	public static final String PASSWORD = "passwordKey";

	public static final int RESPONSE_CODE_SUCCESS = 0;
	public static final int RESPONSE_CODE_EMPTY_FIELDS = 1;
	public static final int RESPONSE_CODE_INVALID_DATA = 2;
	public static final int RESPONSE_CODE_NO_GET = 3;

	private HTTPRequestHandler httpPost;

	private SharedPreferences sharedPreferences;

	private EditText et_email;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_signup_link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_screen);
		initElements();

		httpPost = new HTTPRequestHandler(
				"http://10.0.2.2:80/development/examples/registration_form/backend_android/check_login.php");

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncHTTPRequest().execute(httpPost.getUri());
			}
		});

		tv_signup_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SignUpActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		sharedPreferences = getSharedPreferences(MY_PREFERENCES,
				Context.MODE_PRIVATE);
		if (sharedPreferences.contains(EMAIL)) {
			if (sharedPreferences.contains(PASSWORD)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}
		super.onResume();
	}

	public class AsyncHTTPRequest extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			return httpPost.readHTTPPostResponse(createParams());
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			checkResult(result);
		}

	}

	private void checkResult(String result) {
		if (result != null && result.matches("-?\\d+")) {
			int key = Integer.valueOf(result);
			switch (key) {
			case RESPONSE_CODE_SUCCESS:
				login();
				break;
			case RESPONSE_CODE_EMPTY_FIELDS:
				Toast.makeText(getApplicationContext(),
						"Please fill in required fields.", Toast.LENGTH_LONG)
						.show();
				break;
			case RESPONSE_CODE_INVALID_DATA:
				Toast.makeText(getApplicationContext(),
						"You entered invalid user data. Please try again.",
						Toast.LENGTH_LONG).show();
				break;
			case RESPONSE_CODE_NO_GET:
				Toast.makeText(getApplicationContext(),
						"ERROR while trying to connect to server!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
		Toast.makeText(getApplicationContext(), result,
				Toast.LENGTH_LONG).show();
	}

	public void initElements() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_signup_link = (TextView) findViewById(R.id.tv_signup_link);
	}

	public void login() {
		Editor editor = sharedPreferences.edit();
		String email = et_email.getText().toString();
		String password = et_password.getText().toString();
		editor.putString(EMAIL, email);
		editor.putString(PASSWORD, password);
		editor.commit();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

//	private String createParams() {
//		// String email = et_email.getText().toString();
//		// boolean validEmail = validateEmail(email);
//		// String password = et_password.getText().toString();
//		// if (validEmail) {
//		// return "?email=" + email + "&password=" + password;
//		// }else {
//		// // TODO email validierung
//		// return "?email=&password=";
//		// }
//		String email = et_email.getText().toString();
//		String password = et_password.getText().toString();
//		return "?email=" + email + "&password=" + password;
//	}
	
	public List<NameValuePair> createParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);

		String email = et_email.getText().toString();
		String password = et_password.getText().toString();
		
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		return params;
	}

	// private boolean validateEmail(String email) {
	// String validEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	//
	// Pattern pattern = Pattern.compile(validEmail);
	// Matcher matcher = pattern.matcher(email);
	//
	// return matcher.matches();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}