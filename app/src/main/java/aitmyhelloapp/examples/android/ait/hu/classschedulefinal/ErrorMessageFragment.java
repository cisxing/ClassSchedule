package aitmyhelloapp.examples.android.ait.hu.classschedulefinal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ErrorMessageFragment#} factory method to
 * create an instance of this fragment.
 *
 */
public class ErrorMessageFragment extends DialogFragment {
    public static final String TAG = "MessageFragment";
    public static final String KEY_MSG = "KEY_MSG";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg = getArguments().getString(KEY_MSG);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.timeConflict);
        builder.setMessage(msg);

        //TODO: try out how to make the custom view for the dialog
        //builder.setView()


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);

    }

}
