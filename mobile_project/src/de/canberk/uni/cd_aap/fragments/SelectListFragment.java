package de.canberk.uni.cd_aap.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.activities.ItemListActivity;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.util.SelectItemAdapter;

public class SelectListFragment extends Fragment {

	private ListView selectList;
	private ArrayList<String> lists;
	private SelectItemAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_select_list_view, null);

		selectList = (ListView) view.findViewById(R.id.selectList);
		lists = new ArrayList<String>();
		lists.add(ProjectConstants.TAG_ALL);
		lists.add(ProjectConstants.TAG_ALBUM);
		lists.add(ProjectConstants.TAG_BOOK);
		lists.add(ProjectConstants.TAG_MOVIE);

		adapter = new SelectItemAdapter(getActivity(), lists);
		selectList.setAdapter(adapter);

		selectList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int position, long id) {

						String clickedString = (String) selectList.getAdapter()
								.getItem(position);

						Intent newIntent = new Intent(getActivity(),
								ItemListActivity.class);
						newIntent.putExtra(ProjectConstants.KEY_LIST_TAG,
								clickedString);

						startActivityForResult(newIntent, 0);

					}
				});

		return view;
	}

}