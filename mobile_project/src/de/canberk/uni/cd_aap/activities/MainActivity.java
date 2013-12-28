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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.R;

@SuppressLint("NewApi") // Test
public class MainActivity extends Activity {

	private StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();

	private EditText et_email;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_signup_link;

	private String email;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		StrictMode.setThreadPolicy(policy);
		initElements();

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getRequest();
			}
		});

		tv_signup_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SaveUserActivity.class);
				startActivity(intent);
			}
		});

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

	private void getRequest() {
		String file = "check_login.php";
		String uri = "http://10.0.2.2:80/development/examples/registration_form/backend_android/";
		String fullUri = uri + file + createGetParameters();

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(fullUri);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			if (response != null) {
				String line = "";
				InputStream inputstream = response.getEntity().getContent();
				line = convertStreamToString(inputstream);
				Toast.makeText(this, line, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Unable to complete your request",
						Toast.LENGTH_LONG).show();
			}
		} catch (ClientProtocolException e) {
			Toast.makeText(this, "Caught ClientProtocolException " + e,
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "Caught IOException " + e, Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} catch (Exception e) {
			Toast.makeText(this, "Caught Exception " + e, Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}

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
