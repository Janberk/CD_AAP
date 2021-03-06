package de.canberk.uni.cd_aap.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.activities.LoginActivity;
import de.canberk.uni.cd_aap.activities.Z_App;
import de.canberk.uni.cd_aap.data.DAOItem;
import de.canberk.uni.cd_aap.data.ProjectConstants;
import de.canberk.uni.cd_aap.model.Book;
import de.canberk.uni.cd_aap.model.Item;
import de.canberk.uni.cd_aap.model.Movie;
import de.canberk.uni.cd_aap.model.MusicAlbum;
import de.canberk.uni.cd_aap.util.AllItems;
import de.canberk.uni.cd_aap.util.ItemAdapter;
import de.canberk.uni.cd_aap.util.ItemType;
import de.canberk.uni.cd_aap.util.Logger;
import de.canberk.uni.cd_aap.util.UtilMethods;

public class ItemListFragment extends Fragment {

	public static boolean editMode;

	private SelectListFragment selectList = new SelectListFragment();
	private Logger log = new Logger();
	private DAOItem daoItem;

	private ListView listView;
	private ArrayList<Item> itemList;
	private ItemAdapter adapter;

	private Button btn_addItem;
	private EditText et_newItemTitle;

	private ImageView iv_selectList;
	private ImageView iv_settings;
	private ImageView iv_deleteItem;
	private ImageView iv_logout;

	private String titleOfNewItem = null;
	private int idOfNewItem = 0;

	private String createdBy;

	private String tag = null;

	private int containerId = 0;

	public static ItemListFragment newItemListFragment(String listTag) {

		Bundle passedData = new Bundle();
		passedData.putSerializable(ProjectConstants.KEY_LIST_TAG, listTag);

		ItemListFragment itemListFragment = new ItemListFragment();
		itemListFragment.setArguments(passedData);

		return itemListFragment;

	}

	private void initElements(View view) {
		listView = (ListView) view.findViewById(R.id.itemList);
		et_newItemTitle = (EditText) view.findViewById(R.id.et_newItemTitle);
		btn_addItem = (Button) view.findViewById(R.id.btn_add);

		iv_selectList = (ImageView) view.findViewById(R.id.iv_menu_showList);
		iv_selectList.setOnClickListener(new View.OnClickListener() {

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			@Override
			public void onClick(View v) {
				fragmentTransaction.replace(containerId, selectList);
				fragmentTransaction.commit();
			}
		});

		iv_settings = (ImageView) view.findViewById(R.id.iv_menu_settings);
		iv_settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Settings", Toast.LENGTH_LONG)
						.show();
			}
		});

		iv_deleteItem = (ImageView) view.findViewById(R.id.iv_menu_delete);
		iv_deleteItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				editMode = UtilMethods.modeSwitcher(editMode);

				int childCount = listView.getChildCount();

				for (int i = 0; i < childCount; i++) {
					View view = listView.getChildAt(i);

					if (view != null) {
						CheckBox cb_itemDelete = (CheckBox) view
								.findViewById(R.id.cb_itemDelete);
						ImageView iv_deleteSingleItem = (ImageView) view
								.findViewById(R.id.iv_deleteSingleItem);
						cb_itemDelete.setChecked(false);

						if (cb_itemDelete.getVisibility() == View.GONE) {
							cb_itemDelete.setVisibility(View.VISIBLE);
							iv_deleteSingleItem.setVisibility(View.VISIBLE);
						} else {
							cb_itemDelete.setVisibility(View.GONE);
							iv_deleteSingleItem.setVisibility(View.GONE);
						}
					}
				}
			}
		});

		iv_logout = (ImageView) view.findViewById(R.id.iv_menu_logout);
		iv_logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "Logout", Toast.LENGTH_LONG)
						.show();
				logout();
			}
		});

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		containerId = ((ViewGroup) getView().getParent()).getId();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		createdBy = getUser();

		setHasOptionsMenu(true);

		Bundle bundle = getArguments();

		editMode = false;

		if (bundle == null) {
			tag = ProjectConstants.TAG_ALL;
		} else {
			tag = (String) bundle.get(ProjectConstants.KEY_LIST_TAG);
		}
		AllItems allItems = AllItems.get(getActivity(), createdBy);
		daoItem = allItems.getDaoItem();
		daoItem.open();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_view, null);

		initElements(view);

		if (tag.equals(ProjectConstants.TAG_ALL)) {
			itemList = daoItem.getAllItems(createdBy);
		} else if (tag.equals(ProjectConstants.TAG_ALBUM)) {
			itemList = daoItem.getItemsByType(ItemType.Album, createdBy);
		} else if (tag.equals(ProjectConstants.TAG_BOOK)) {
			itemList = daoItem.getItemsByType(ItemType.Book, createdBy);
		} else if (tag.equals(ProjectConstants.TAG_MOVIE)) {
			itemList = daoItem.getItemsByType(ItemType.Movie, createdBy);
		}

		for (Item item : itemList) {
			log.info(item.toString());
		}

		adapter = new ItemAdapter(this.getActivity(), itemList, this);
		adapter.setNotifyOnChange(true);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				Item clickedItem = (Item) listView.getAdapter().getItem(
						position);
				Intent newIntent = new Intent(getActivity(), Z_App.class);
				newIntent.putExtra(ProjectConstants.KEY_ITEM_ID,
						clickedItem.getId());
				startActivityForResult(newIntent, 0);
			}
		});

		btn_addItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				titleOfNewItem = et_newItemTitle.getText().toString();
				idOfNewItem = daoItem.getItemCount() + 1;

				FragmentManager fragManager = getActivity()
						.getSupportFragmentManager();

				DialogSetItemTypeFragment typeDialog = DialogSetItemTypeFragment
						.newInstance();

				typeDialog.setTargetFragment(ItemListFragment.this,
						ProjectConstants.REQUEST_CODE);

				typeDialog.show(fragManager,
						ProjectConstants.KEY_CREATE_NEW_ITEM);

				et_newItemTitle.setText("");
			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != Activity.RESULT_OK) {
			return;
		}
		if (requestCode == ProjectConstants.REQUEST_CODE) {

			Item item = createItem(data);

			daoItem.addItem(item);
			log.info(item.toString());
			itemList.add(0, item);
			adapter.refresh(itemList);
		}

	}

	public Item createItem(Intent data) {
		String typeAsString = (String) data
				.getSerializableExtra(ProjectConstants.KEY_TYPE);
		ItemType type = ItemType.valueOf(typeAsString);
		Item item = null;

		switch (type) {
		case Album:
			item = new MusicAlbum(createdBy, titleOfNewItem, typeAsString, "",
					false);
			item.setId(idOfNewItem);
			break;
		case Book:
			item = new Book(createdBy, titleOfNewItem, typeAsString, "", false);
			item.setId(idOfNewItem);
			break;
		case Movie:
			item = new Movie(createdBy, titleOfNewItem, typeAsString, "", false);
			item.setId(idOfNewItem);
			break;

		default:
			break;
		}
		return item;
	}

	public String getUser() {
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(ProjectConstants.KEY_MY_PREFERENCES,
						Context.MODE_PRIVATE);
		String user = sharedPreferences.getString(LoginFragment.EMAIL, "");
		return user;
	}

	public void logout() {
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(ProjectConstants.KEY_MY_PREFERENCES,
						Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
		getActivity().moveTaskToBack(true);
		getActivity().finish();
		Intent intent = new Intent(getActivity().getApplicationContext(),
				LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void onPause() {
		editMode = false;
		daoItem.close();
		super.onPause();
	}

	@Override
	public void onResume() {
		editMode = false;
		daoItem.open();
		itemList.clear();
		if (tag.equals(ProjectConstants.TAG_ALL)) {
			itemList.addAll(daoItem.getAllItems(createdBy));
		} else if (tag.equals(ProjectConstants.TAG_ALBUM)) {
			itemList.addAll(daoItem.getItemsByType(ItemType.Album, createdBy));
		} else if (tag.equals(ProjectConstants.TAG_BOOK)) {
			itemList.addAll(daoItem.getItemsByType(ItemType.Book, createdBy));
		} else if (tag.equals(ProjectConstants.TAG_MOVIE)) {
			itemList.addAll(daoItem.getItemsByType(ItemType.Movie, createdBy));
		}
		adapter.refresh(itemList);
		super.onResume();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// FragmentManager fragmentManager = getFragmentManager();
		// FragmentTransaction fragmentTransaction = fragmentManager
		// .beginTransaction();
		//
		// int containerId = ((ViewGroup) (getView().getParent())).getId();

		switch (item.getItemId()) {
		case R.id.menuItem_settings:
			Toast.makeText(getActivity(), "Settings", Toast.LENGTH_LONG).show();
			// fragmentTransaction.replace(containerId, selectList);
			// fragmentTransaction.commit();
			return true;

		case R.id.menuItem_logout:
			Toast.makeText(getActivity(), "Logout", Toast.LENGTH_LONG).show();
			logout();
			return true;

		case R.id.menuItem_delete:
			Toast.makeText(getActivity(), "Delete", Toast.LENGTH_LONG).show();
			return true;

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public DAOItem getDaoItem() {
		return daoItem;
	}

}