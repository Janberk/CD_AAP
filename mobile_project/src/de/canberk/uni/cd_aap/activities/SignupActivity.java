package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.fragments.SignupFragment;

public class SignupActivity extends FragmentActivityBuilder {

	@Override
	protected Fragment createFragment() {

		return new SignupFragment();

	}

}