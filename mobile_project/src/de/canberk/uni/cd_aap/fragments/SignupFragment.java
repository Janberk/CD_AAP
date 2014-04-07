package de.canberk.uni.cd_aap.fragments;

import android.content.Intent;
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
import de.canberk.uni.cd_aap.data.DAOUser;
import de.canberk.uni.cd_aap.model.User;
import de.canberk.uni.cd_aap.util.UtilMethods;

public class SignupFragment extends Fragment {

	private DAOUser daoUser;

	private EditText et_firstname;
	private EditText et_lastname;
	private EditText et_username;
	private EditText et_email;
	private EditText et_password;
	private EditText et_confirmPassword;
	private Button btn_signup;
	private TextView tv_login_link;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		daoUser = new DAOUser(getActivity());
		daoUser.open();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_signup, null);
		initElements(view);

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				signupUser(v);
			}
		});

		tv_login_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						LoginActivity.class);
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
		et_confirmPassword = (EditText) view
				.findViewById(R.id.et_confirmPassword);
		btn_signup = (Button) view.findViewById(R.id.btn_signup);
		tv_login_link = (TextView) view.findViewById(R.id.tv_login_link);
	}

	public void signupUser(View view) {
		// Get user details.
		String enteredFirstName = et_firstname.getText().toString();
		String enteredLastName = et_lastname.getText().toString();
		String enteredUserName = et_username.getText().toString();
		String enteredEmail = et_email.getText().toString();
		String enteredPassword = et_password.getText().toString();
		String enteredConfirmPassword = et_confirmPassword.getText().toString();

		// Check if all fields have been completed.
		if (enteredFirstName.equals("") || enteredLastName.equals("")
				|| enteredUserName.equals("") || enteredEmail.equals("")
				|| enteredPassword.equals("")
				|| enteredConfirmPassword.equals("")) {
			Toast.makeText(getActivity(),
					"Please ensure all fields have been completed.",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Check password match.
		if (!enteredPassword.equals(enteredConfirmPassword)) {
			Toast.makeText(getActivity(), "The password does not match.",
					Toast.LENGTH_SHORT).show();
			et_password.setText("");
			et_confirmPassword.setText("");
			return;
		}

		// Encrypt password with MD5.
		enteredPassword = UtilMethods.md5(enteredPassword);

		// Check database for existing users.
		// User user = daoUser.findUserByLogin(enteredEmail, enteredPassword);

		if (daoUser.alreadyExists(enteredEmail, enteredUserName)) {
			Toast.makeText(getActivity(), "This user data already exists!",
					Toast.LENGTH_SHORT).show();
		} else {
			User user = new User(enteredFirstName, enteredLastName,
					enteredUserName, enteredEmail, enteredPassword);
			long id = daoUser.addUser(user);

			if (id > 0) {
				Toast.makeText(getActivity(), "Your account was created",
						Toast.LENGTH_SHORT).show();
				// saveLoggedInUId(id, username,
				// newPassword.getText().toString());
				Intent intent = new Intent(view.getContext(),
						LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			} else {
				Toast.makeText(getActivity(), "Failt to create new account",
						Toast.LENGTH_SHORT).show();
			}
		}
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