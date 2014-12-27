package aitmyhelloapp.examples.android.ait.hu.classschedulefinal;



import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 */
public class ConfirmationFragment extends DialogFragment {




    public static final String TAG = "ConfirmationFragment";
    public static final String KEY_MSG = "KEY_MSG";

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg = getArguments().getString(KEY_MSG);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.confirmation);
        builder.setMessage(msg);


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
