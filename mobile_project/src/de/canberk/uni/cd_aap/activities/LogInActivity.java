package de.canberk.uni.cd_aap.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
import de.canberk.uni.cd_aap.R;

public class LogInActivity extends Activity {

	private String responseToString;

	private EditText et_email;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_signup_link;

	private String email;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_screen);
		initElements();

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new ProcessRequest().execute(createFullUri());
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

	public class ProcessRequest extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(params[0]);

			try {
				HttpResponse response = httpclient.execute(httpGet);
				if (response != null) {

					InputStream inputstream = response.getEntity().getContent();
					responseToString = convertStreamToString(inputstream);
					return responseToString;
				} else {
					return "Unable to complete your request";
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (result != null) {
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void initElements() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_signup_link = (TextView) findViewById(R.id.tv_signup_link);
	}

	private String createGetParameters() {
		email = et_email.getText().toString();
		password = et_password.getText().toString();
		String result = "?email=" + email + "&password=" + password;
		return result;
	}

	private String createFullUri() {
		String file = "check_login.php";
		String uri = "http://10.0.2.2:80/development/examples/registration_form/backend_android/";
		String fullUri = uri + file + createGetParameters();

		return fullUri;
	}

	private String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder sb = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			Toast.makeText(this, "Stream Exception", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
