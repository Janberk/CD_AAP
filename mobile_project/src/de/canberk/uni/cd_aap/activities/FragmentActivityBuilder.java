package de.canberk.uni.cd_aap.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.util.AppContextUtil;

public abstract class FragmentActivityBuilder extends FragmentActivity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		AppContextUtil.setContext(getApplicationContext());

		setContentView(R.layout.activity_z_app);

		FragmentManager fragManager = getSupportFragmentManager();

		Fragment theFragment = fragManager
				.findFragmentById(R.id.fragmentContainer);

		if (theFragment == null) {

			theFragment = createFragment();

			fragManager.beginTransaction()
					.add(R.id.fragmentContainer, theFragment).commit();

		}

	}

}