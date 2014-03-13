package de.canberk.uni.cd_aap.activities;

import android.support.v4.app.Fragment;
import de.canberk.uni.cd_aap.fragments.ItemListFragment;

public class ItemListActivity extends FragmentActivityBuilder {

	@SuppressWarnings("static-access")
	@Override
	protected Fragment createFragment() {

		String tag = (String) getIntent().getSerializableExtra(
				ItemListFragment.LIST_TAG);

		if (tag == null) {
			return new ItemListFragment();
		}

		return new ItemListFragment().newItemListFragment(tag);

	}

}