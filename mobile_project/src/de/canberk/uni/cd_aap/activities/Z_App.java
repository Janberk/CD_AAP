package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.fragments.ItemFragment;

public class Z_App extends FragmentActivityBuilder {

	@SuppressWarnings("static-access")
	@Override
	protected Fragment createFragment() {

		int itemId = (Integer) getIntent().getSerializableExtra(
				ItemFragment.ITEM_ID);
		String userTag = (String) getIntent().getSerializableExtra(
				ItemFragment.USER_TAG);

		return new ItemFragment().newItemFragment(itemId, userTag);

	}

}