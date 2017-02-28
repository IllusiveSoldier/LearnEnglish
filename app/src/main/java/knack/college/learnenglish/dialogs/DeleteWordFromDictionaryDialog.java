package knack.college.learnenglish.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.toasts.Toast;

public class DeleteWordFromDictionaryDialog extends DialogFragment {

    Toast toast;
    public static final String TAG_SELECTED = "selected";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_word_from_dictionary_dialog_window, null);

        toast = new Toast(getActivity());

        builder.setView(view)
               .setPositiveButton(R.string.title_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra(TAG_SELECTED, 1);
                        getTargetFragment().onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK, intent
                        );
                    }
                })
               .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra(TAG_SELECTED, 0);
                        getTargetFragment().onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_CANCELED, intent
                        );
                    }
               });

        return builder.create();
    }
}
