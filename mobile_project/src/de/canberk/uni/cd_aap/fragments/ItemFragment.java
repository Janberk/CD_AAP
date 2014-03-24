package de.canberk.uni.cd_aap.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.data.DAOItem;
import de.canberk.uni.cd_aap.model.Item;
import de.canberk.uni.cd_aap.util.AllItems;
import de.canberk.uni.cd_aap.util.ItemType;
import de.canberk.uni.cd_aap.util.Logger;

public class ItemFragment extends Fragment implements OnItemSelectedListener {

	public static final String ITEM_ID = "de.canberk.uni.cd_aap.ID";
	public static final String USER_TAG = "de.canberk.uni.cd_aap.user_tag";

	public static final int ALBUM = 0;
	public static final int BOOK = 1;
	public static final int MOVIE = 2;

	private static final int RESULT_LOAD_IMAGE = 0;
	private static final int RESULT_OK = 0;

	private Logger log = new Logger();

	private DAOItem daoItem;

	private Item item;

	private EditText et_detailsTitle;
	private EditText et_detailsGenre;
	private EditText et_detailsContent;

	private CheckBox cb_detailsFavorite;
	private Spinner sp_detailsType;
	private TextView tv_timestamp;

	private ImageView iv_itemCover;

	private Button btn_detailsEdit;
	private Button btn_detailsSave;

	public void initElements(View theView) {
		et_detailsTitle = (EditText) theView.findViewById(R.id.et_detailsTitle);
		et_detailsGenre = (EditText) theView.findViewById(R.id.et_detailsGenre);
		et_detailsContent = (EditText) theView
				.findViewById(R.id.et_itemContent);
		cb_detailsFavorite = (CheckBox) theView
				.findViewById(R.id.cb_detailsFavorite);
		sp_detailsType = (Spinner) theView.findViewById(R.id.spinner_itemType);
		tv_timestamp = (TextView) theView.findViewById(R.id.tv_timestamp);
		iv_itemCover = (ImageView) theView.findViewById(R.id.iv_cover);
		btn_detailsEdit = (Button) theView.findViewById(R.id.btn_detailsEdit);
		btn_detailsSave = (Button) theView.findViewById(R.id.btn_detailsSave);
	}

	public static ItemFragment newItemFragment(int id, String userTag) {

		Bundle passedData = new Bundle();
		passedData.putSerializable(ITEM_ID, id);
		passedData.putSerializable(USER_TAG, userTag);

		ItemFragment itemFragment = new ItemFragment();
		itemFragment.setArguments(passedData);

		return itemFragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int id = (Integer) getArguments().getSerializable(ITEM_ID);
		String user = (String) getArguments().getSerializable(USER_TAG);

		AllItems allItems = AllItems.get(getActivity(), user);

		daoItem = allItems.getDaoItem();
		daoItem.open();

		item = daoItem.getItem(id);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View theView = inflater.inflate(R.layout.fragment_item_details2, container,
				false);
		initElements(theView);

		iv_itemCover.setImageResource(R.drawable.movie_cover);

		et_detailsTitle.setText(item.getTitle());
		et_detailsGenre.setText(item.getGenre());

		tv_timestamp.setText(item.getCreationDateAsString(item
				.getCreationDate()));

		cb_detailsFavorite.setChecked(item.isFavorite());

		TextWatcher editTextWatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (et_detailsTitle.hasFocus() == true) {
					item.setTitle(s.toString());
				} else if (et_detailsGenre.hasFocus() == true) {
					item.setGenre(s.toString());
				} else if (et_detailsContent.hasFocus() == true) {
					// TODO content zu Objekten hinzufügen
					// item.setGenre(s.toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
		et_detailsTitle.addTextChangedListener(editTextWatcher);
		et_detailsGenre.addTextChangedListener(editTextWatcher);

		cb_detailsFavorite
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

						item.setFavorite(!item.isFavorite());

					}

				});

		btn_detailsSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveDetails();
			}
		});

		btn_detailsEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				editDetails();
			}
		});

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.item_types,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_detailsType.setAdapter(adapter);
		sp_detailsType.setSelection(getSpinnerSelectionIndex());
		sp_detailsType.setOnItemSelectedListener(this);

		return theView;

	}

	public int getSpinnerSelectionIndex() {

		int selectionIndex = 0;
		ItemType type = item.getType();

		switch (type) {
		case Album:
			selectionIndex = ALBUM;
			break;
		case Book:
			selectionIndex = BOOK;
			break;
		case Movie:
			selectionIndex = MOVIE;
			break;

		default:
			break;
		}

		return selectionIndex;

	}

	public void editDetails() {

		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(intent, RESULT_LOAD_IMAGE);

	}

	public void saveDetails() {
		daoItem.updateItem(item);
		log.info(item.toString());
		getActivity().finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		String typeAsString = (String) parent.getItemAtPosition(position);
		ItemType type = ItemType.valueOf(typeAsString);
		item.setType(type);

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onPause() {
		daoItem.close();
		super.onPause();
	}

	@Override
	public void onResume() {
		daoItem.open();
		super.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			iv_itemCover.setImageBitmap(BitmapFactory.decodeFile(picturePath));

		}
	}

}