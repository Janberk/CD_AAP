package de.canberk.uni.cd_aap.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import de.canberk.uni.cd_aap.R;
import de.canberk.uni.cd_aap.data.ProjectConstants;

public class DialogSetItemTypeFragment extends DialogFragment implements
		OnItemSelectedListener {

	private AlertDialog alert;

	private Spinner sp_itemTypeInDialog;
	private Button btn_saveItemType;

	private String typeAsString = "";

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View theView = getActivity().getLayoutInflater().inflate(
				R.layout.fragment_type_dialog, null);

		initSpinner(theView);
		initButton(theView);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(theView);
		alert = builder.create();

		return alert;
	}

	public static DialogSetItemTypeFragment newInstance() {
		DialogSetItemTypeFragment typeDialog = new DialogSetItemTypeFragment();

		return typeDialog;
	}

	public void initSpinner(View theView) {
		sp_itemTypeInDialog = (Spinner) theView
				.findViewById(R.id.spinner_itemTypeInDialog);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this.getActivity(), R.array.item_types,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_itemTypeInDialog.setAdapter(adapter);
		sp_itemTypeInDialog.setOnItemSelectedListener(this);
	}

	private void initButton(View theView) {
		btn_saveItemType = (Button) theView.findViewById(R.id.btn_saveItemType);
		btn_saveItemType.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendResult(Activity.RESULT_OK);
				alert.cancel();
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		typeAsString = (String) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null)
			return;

		Intent theIntent = new Intent();
		theIntent.putExtra(ProjectConstants.KEY_TYPE, typeAsString);

		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, theIntent);
	}

}