package de.canberk.uni.cd_aap.activities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
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

public class SignUpActivity extends Activity {

	private HTTPRequestHandler httpPost;

	private EditText et_firstname;
	private EditText et_lastname;
	private EditText et_username;
	private EditText et_email;
	private EditText et_password;
	private Button btn_signup;
	private TextView tv_login_link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_screen);
		initElements();

		httpPost = new HTTPRequestHandler(
				"http://10.0.2.2:80/development/examples/registration_form/backend_android/user_data.php");

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncHTTPRequest().execute(httpPost.getUri());
			}
		});

		tv_login_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), LogInActivity.class);
				startActivity(intent);
			}
		});
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
		if (result != null) {
			int key = Integer.valueOf(result);
			switch (key) {
			case LogInActivity.RESPONSE_CODE_SUCCESS:
				Toast.makeText(getApplicationContext(),
						"User was saved successfully. Back to log in.",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getApplicationContext(),
						LogInActivity.class);
				startActivity(intent);
				break;
			case LogInActivity.RESPONSE_CODE_EMPTY_FIELDS:
				Toast.makeText(getApplicationContext(),
						"Please fill in all fields.", Toast.LENGTH_LONG).show();
				break;
			case LogInActivity.RESPONSE_CODE_INVALID_DATA:
				Toast.makeText(getApplicationContext(),
						"You entered invalid data. Please try again.",
						Toast.LENGTH_LONG).show();
				break;
			case LogInActivity.RESPONSE_CODE_NO_GET:
				Toast.makeText(getApplicationContext(),
						"ERROR while trying to connect to server!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}

	public void initElements() {
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_username = (EditText) findViewById(R.id.et_username);
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_signup = (Button) findViewById(R.id.btn_signup);
		tv_login_link = (TextView) findViewById(R.id.tv_login_link);
	}

	public List<NameValuePair> createParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);

		String firstname = et_firstname.getText().toString();
		String lastname = et_lastname.getText().toString();
		String username = et_username.getText().toString();
		String email = et_email.getText().toString();
		String password = et_password.getText().toString();

		params.add(new BasicNameValuePair("firstname", firstname));
		params.add(new BasicNameValuePair("lastname", lastname));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		return params;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}