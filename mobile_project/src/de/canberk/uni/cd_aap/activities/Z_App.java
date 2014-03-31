package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.fragments.ItemFragment;

public class Z_App extends FragmentActivityBuilder {

	@SuppressWarnings("static-access")
	@Override
	protected Fragment createFragment() {

		int itemId = (Integer) getIntent().getSerializableExtra(
				ProjectConstants.KEY_ITEM_ID);
		String userTag = (String) getIntent().getSerializableExtra(
				ProjectConstants.KEY_USER_TAG);

		return new ItemFragment().newItemFragment(itemId, userTag);

	}

}