package com.finalproject.andreivancea.ntviewer.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.finalproject.andreivancea.ntviewer.R;

/**
 * Created by andrei.vancea on 1/31/2017.
 */

public class IPSetDialog extends DialogFragment {

    private static final String TAG = "IPSetDialog";
    private String ipAddress = "";
    private IPSetDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.ip_set_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.dialog_btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        View v = getDialog().findViewById(R.id.ip_set_edit_text);
                        if (v != null) {
                            EditText ip = (EditText) v;
                            String aux = ip.getText().toString();
                            if (isValidIpAddress(aux)) {
                                ipAddress = aux;
                                mListener.onDialogPositiveClick(ipAddress);
                            } else {
                                Toast.makeText(v.getContext(), "Invalid ip addresss!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e(TAG, "View is null!");
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        IPSetDialog.this.getDialog().cancel();
                    }
                }).setTitle(R.string.ip_set_dialog_title);
        return builder.create();
    }

    public void attachListener(Object obj) {
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (IPSetDialogListener) obj;
        } catch (ClassCastException e) {
            throw new ClassCastException(obj.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    private boolean isValidIpAddress(String ip) {
        boolean valid = true;
        if (!ip.matches("(?:[0-9]{1,3}\\.){3}[0-9]{1,3}")) {
            valid = false;
        }
        return valid;
    }

    public interface IPSetDialogListener {
        void onDialogPositiveClick(String ipAddress);
    }
}
