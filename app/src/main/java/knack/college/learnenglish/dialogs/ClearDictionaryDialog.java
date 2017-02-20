package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.toasts.Toast;

public class ClearDictionaryDialog extends DialogFragment {
    Toast toast;
    Dictionary dictionary;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.clear_dictionary_dialog, null);

        toast = new Toast(getActivity());
        dictionary = new Dictionary(getActivity().getApplicationContext());

        builder.setView(view)
                .setPositiveButton(R.string.title_clearDictionary,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            dictionary.clear();
                        } catch (Exception ex) {
                            toast.show(ex);
                        }
                    }
                })
                .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });


        return builder.create();
    }
}
