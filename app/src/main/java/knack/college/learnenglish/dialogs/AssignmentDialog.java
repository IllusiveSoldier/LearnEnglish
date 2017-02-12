package knack.college.learnenglish.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.toasts.Toast;


public class AssignmentDialog extends DialogFragment {

    private Dictionary dictionary;
    private Toast toast;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dictionary = new Dictionary(getActivity().getApplicationContext());
        toast = new Toast(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_whatToDo)
                .setItems(R.array.assignmentSelect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 3:
                                try {
                                    dictionary.clear();
                                } catch (Exception ex) {
                                    toast.show(ex);
                                }
                                break;
                        }
                    }
                });

        return builder.create();
    }
}