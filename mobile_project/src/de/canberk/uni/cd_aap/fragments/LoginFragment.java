package de.canberk.uni.cd_aap.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.activities.ItemListActivity;
import de.canberk.uni.cd_aap.activities.SignupActivity;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.util.HTTPRequestHandler;

public class LoginFragment extends Fragment {

	public static final String EMAIL = "emailKey";
	public static final String PASSWORD = "passwordKey";

	public static final int RESPONSE_CODE_SUCCESS = 0;
	public static final int RESPONSE_CODE_EMPTY_FIELDS = 1;
	public static final int RESPONSE_CODE_INVALID_DATA = 2;
	public static final int RESPONSE_CODE_NO_GET = 3;

	private HTTPRequestHandler httpPost;

	private SharedPreferences sharedPreferences;

	private TextWatcher watcher;

	private EditText et_email;
	private EditText et_password;
	private Button btn_login;
	private TextView tv_signup_link;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		httpPost = new HTTPRequestHandler(
				"http://10.0.2.2:80/development/examples/registration_form/backend_android/check_login.php");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login, null);
		initElements(view);

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new AsyncHTTPRequest().execute(httpPost.getUri());
			}
		});

		tv_signup_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), SignupActivity.class);
				startActivity(intent);
			}
		});
		et_email.addTextChangedListener(watcher);

		return view;
	}

	private void initElements(View view) {
		et_email = (EditText) view.findViewById(R.id.et_email);
		et_password = (EditText) view.findViewById(R.id.et_password);
		btn_login = (Button) view.findViewById(R.id.btn_login);
		btn_login.setEnabled(false);
		tv_signup_link = (TextView) view.findViewById(R.id.tv_signup_link);
		watcher = new LocalTextWatcher();
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

	private class LocalTextWatcher implements TextWatcher {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			boolean validEmail = validateEmail(et_email.getText().toString());

			if (!validEmail && editTextIsEmpty(et_email)) {
				et_email.setError("Invalid Email!");
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			boolean validEmail = validateEmail(et_email.getText().toString());
			if (validEmail) {
				btn_login.setEnabled(true);
			}
		}
	}

	public boolean editTextIsEmpty(EditText edittext) {
		if (edittext.getText().toString().trim().length() < 1) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validateEmail(String email) {
		String validEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(validEmail);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	private void checkResult(String result) {
		if (result != null && result.matches("-?\\d+")) {
			int key = Integer.valueOf(result);
			switch (key) {
			case RESPONSE_CODE_SUCCESS:
				login();
				break;
			case RESPONSE_CODE_EMPTY_FIELDS:
				Toast.makeText(getActivity().getApplicationContext(),
						"Please fill in required fields.", Toast.LENGTH_LONG)
						.show();
				break;
			case RESPONSE_CODE_INVALID_DATA:
				Toast.makeText(getActivity().getApplicationContext(),
						"You entered invalid user data. Please try again.",
						Toast.LENGTH_LONG).show();
				break;
			case RESPONSE_CODE_NO_GET:
				Toast.makeText(getActivity().getApplicationContext(),
						"ERROR while trying to connect to server!",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
		Toast.makeText(getActivity().getApplicationContext(), result,
				Toast.LENGTH_LONG).show();
	}

	public void login() {
		Editor editor = sharedPreferences.edit();
		String email = et_email.getText().toString();
		String password = et_password.getText().toString();
		editor.putString(EMAIL, email);
		editor.putString(PASSWORD, password);
		editor.commit();
		Intent intent = new Intent(getActivity(), ItemListActivity.class);
		startActivity(intent);
	}

	public List<NameValuePair> createParams() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);

		String email = et_email.getText().toString();
		String password = et_password.getText().toString();

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
		sharedPreferences = getActivity().getSharedPreferences(
				ProjectConstants.KEY_MY_PREFERENCES, Context.MODE_PRIVATE);
		if (sharedPreferences.contains(EMAIL)) {
			if (sharedPreferences.contains(PASSWORD)) {
				Intent intent = new Intent(getActivity(),
						ItemListActivity.class);
				startActivity(intent);
			}
		} else {
			this.onCreate(null);
			et_email.setText("");
			et_password.setText("");
			btn_login.setEnabled(false);
		}
		super.onResume();
	}

}