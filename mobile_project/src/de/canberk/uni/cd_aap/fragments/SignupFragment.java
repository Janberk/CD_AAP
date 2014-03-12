package de.canberk.uni.cd_aap.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.activities.LoginActivity;
import de.canberk.uni.cd_aap.util.HTTPRequestHandler;

public class SignupFragment extends Fragment {

	private HTTPRequestHandler httpPost;

	private EditText et_firstname;
	private EditText et_lastname;
	private EditText et_username;
	private EditText et_email;
	private EditText et_password;
	private Button btn_signup;
	private TextView tv_login_link;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		httpPost = new HTTPRequestHandler(
				"http://10.0.2.2:80/development/examples/registration_form/backend_android/user_data.php");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_signup, null);
		initElements(view);

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncHTTPRequest().execute(httpPost.getUri());
			}
		});

		tv_login_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), LoginActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}

	public void initElements(View view) {
		et_firstname = (EditText) view.findViewById(R.id.et_firstname);
		et_lastname = (EditText) view.findViewById(R.id.et_lastname);
		et_username = (EditText) view.findViewById(R.id.et_username);
		et_email = (EditText) view.findViewById(R.id.et_email);
		et_password = (EditText) view.findViewById(R.id.et_password);
		btn_signup = (Button) view.findViewById(R.id.btn_signup);
		tv_login_link = (TextView) view.findViewById(R.id.tv_login_link);
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
			case LoginFragment.RESPONSE_CODE_SUCCESS:
				Toast.makeText(getActivity().getApplicationContext(),
						"User was saved successfully. Back to log in.",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				break;
			case LoginFragment.RESPONSE_CODE_EMPTY_FIELDS:
				Toast.makeText(getActivity().getApplicationContext(),
						"Please fill in all fields.", Toast.LENGTH_LONG).show();
				break;
			case LoginFragment.RESPONSE_CODE_INVALID_DATA:
				Toast.makeText(getActivity().getApplicationContext(),
						"You entered invalid data. Please try again.",
						Toast.LENGTH_LONG).show();
				break;
			case LoginFragment.RESPONSE_CODE_NO_GET:
				Toast.makeText(getActivity().getApplicationContext(),
						"ERROR while trying to connect to server!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
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
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}