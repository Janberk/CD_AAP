package de.canberk.uni.cd_aap.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import de.canberk.uni.cd_aap.DatabaseHandler;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.User;

public class SaveUserActivity extends Activity {
	
	private DatabaseHandler db;

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
		setContentView(R.layout.save_user_screen);
		initElements();

		btn_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				firstname = et_firstname.getText().toString();
				lastname = et_lastname.getText().toString();
				username = et_username.getText().toString();
				email = et_email.getText().toString();
				password = et_password.getText().toString();
				
				User user = new User(firstname, lastname, username, email, password);
				crudOperations(user);
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
	
	public void crudOperations(User newUser) {
		Log.d("Insert: ", "Inserting ..");
		db.addUser(newUser);

		Log.d("Reading: ", "Reading all contacts..");
		List<User> users = db.getAllUsers();

		for (User user : users) {
			String log = user.toString();
			Log.d("Created User: ", log);
		}
	}

	public void initElements() {
		db = new DatabaseHandler(this);
		et_firstname = (EditText) findViewById(R.id.et_firstname);
		et_lastname = (EditText) findViewById(R.id.et_lastname);
		et_username = (EditText) findViewById(R.id.et_username);
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_signup = (Button) findViewById(R.id.btn_signup);
		tv_login_link = (TextView) findViewById(R.id.tv_login_link);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save_user, menu);
		return true;
	}

}