package knack.college.learnenglish.dialogs;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.toasts.ToastWrapper;

public class ActionsDictionaryDialog extends DialogFragment {

    private Dictionary dictionary;
    private ToastWrapper toast;
    private AlertDialog.Builder builder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        initializeToast();
        initializeDictionary();
        initializeDialog();

        return builder.create();
    }

    private void initializeDialog() {
        try {
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.title_whatToDo)
                    .setItems(R.array.actions_dictionary_dialog_items,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            clearDictionary();
                                            break;
                                        case 1:
                                            removeDictionary();
                                            break;
                                    }
                                }
                            });
        } catch (Exception e) {
            toast.show(
                    getActivity().getResources()
                            .getString(R.string.error_message_failed_initialize_dialog)
            );
        }
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(getActivity().getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    getActivity().getResources()
                            .getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeDictionary() {
        try {
            dictionary = new Dictionary(getActivity().getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getActivity().getResources()
                            .getString(R.string.error_message_failed_initialize_dictionary)
            );
        }
    }

    private void clearDictionary() {
        try {
            dictionary.clear();
        } catch (Exception e) {
            toast.show(
                    getActivity().getResources()
                            .getString(R.string.error_message_failed_clear_dictionary)
            );
        }
    }

    private void removeDictionary() {
        try {
            dictionary.delete();
        } catch (Exception e) {
            toast.show(
                    getActivity().getResources()
                            .getString(R.string.error_message_failed_remove_dictionary)
            );
        }
    }
}
