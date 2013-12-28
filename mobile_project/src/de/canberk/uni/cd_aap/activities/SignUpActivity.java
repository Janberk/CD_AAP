package de.canberk.uni.cd_aap.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.R;

public class SignUpActivity extends Activity {

	private EditText et_firstname;
	private EditText et_lastname;
	private EditText et_username;
	private EditText et_email;
	private EditText et_password;
	private Button btn_signup;
	private TextView tv_login_link;

	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up_screen);
		initElements();

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				postRequest();
			}
		});

		tv_login_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivity(intent);
			}
		});
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

		firstname = et_firstname.getText().toString();
		lastname = et_lastname.getText().toString();
		username = et_username.getText().toString();
		email = et_email.getText().toString();
		password = et_password.getText().toString();

		params.add(new BasicNameValuePair("firstname", firstname));
		params.add(new BasicNameValuePair("lastname", lastname));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		return params;

	}

	public void postRequest() {
		String file = "user_data.php";
		String uri = "http://10.0.2.2:80/development/examples/registration_form/backend_android/";
		String fullUri = uri + file;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(fullUri);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(createParams(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Toast.makeText(this, "Caught UnsupportedEncodingException " + e,
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpclient.execute(httpPost);
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
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}