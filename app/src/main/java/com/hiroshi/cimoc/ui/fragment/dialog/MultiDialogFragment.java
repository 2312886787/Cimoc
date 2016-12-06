package com.hiroshi.cimoc.ui.fragment.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;

import com.hiroshi.cimoc.R;
import com.hiroshi.cimoc.ui.view.DialogView;

/**
 * Created by Hiroshi on 2016/12/2.
 */

public class MultiDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] item = getArguments().getStringArray(DialogView.EXTRA_DIALOG_ITEMS);
        boolean[] check = getArguments().getBooleanArray(DialogView.EXTRA_DIALOG_CHOICE_ITEMS);
        builder.setTitle(getArguments().getInt(DialogView.EXTRA_DIALOG_TITLE))
                .setMultiChoiceItems(item, check, null)
                .setPositiveButton(R.string.dialog_positive, this);
        return builder.create();
    }

    private boolean[] getCheckedArray(DialogInterface dialog) {
        SparseBooleanArray array = ((AlertDialog) dialog).getListView().getCheckedItemPositions();
        int size = array.size();
        boolean[] result = new boolean[size];
        for (int i = 0; i != size; ++i) {
            result[i] = array.valueAt(i);
        }
        return result;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        int requestCode = getArguments().getInt(DialogView.EXTRA_DIALOG_REQUEST_CODE);
        Bundle bundle = new Bundle();
        bundle.putBooleanArray(DialogView.EXTRA_DIALOG_RESULT_VALUE, getCheckedArray(dialogInterface));
        DialogView target = (DialogView) (getTargetFragment() != null ? getTargetFragment() : getActivity());
        target.onDialogResult(requestCode, bundle);
    }

    public static MultiDialogFragment newInstance(int title, String[] item, boolean[] check, int requestCode) {
        MultiDialogFragment fragment = new MultiDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DialogView.EXTRA_DIALOG_TITLE, title);
        bundle.putStringArray(DialogView.EXTRA_DIALOG_ITEMS, item);
        bundle.putBooleanArray(DialogView.EXTRA_DIALOG_CHOICE_ITEMS, check);
        bundle.putInt(DialogView.EXTRA_DIALOG_REQUEST_CODE, requestCode);
        fragment.setArguments(bundle);
        return fragment;
    }

}
