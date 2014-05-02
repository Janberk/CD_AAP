package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.fragments.ItemListFragment;

// Open Iconic — www.useiconic.com/open

public class ItemListActivity extends FragmentActivityBuilder {

	@SuppressWarnings("static-access")
	@Override
	protected Fragment createFragment() {

		String listTag = (String) getIntent().getSerializableExtra(
				ProjectConstants.KEY_LIST_TAG);

		if (listTag == null) {
			return new ItemListFragment();
		}

		return new ItemListFragment().newItemListFragment(listTag);

	}

}