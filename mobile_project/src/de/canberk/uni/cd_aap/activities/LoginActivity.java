package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.fragments.LoginFragment;

public class LoginActivity extends FragmentActivityBuilder {//

	@Override
	protected Fragment createFragment() {

		return new LoginFragment();

	}

}