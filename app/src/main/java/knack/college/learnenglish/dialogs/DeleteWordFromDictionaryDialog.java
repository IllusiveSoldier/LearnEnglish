package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import knack.college.learnenglish.R;
import knack.college.learnenglish.model.LearnEnglishToast;

public class DeleteWordFromDictionaryDialog extends DialogFragment {

    private LearnEnglishToast toast;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_word_to_dictonary_dialog_window, null);

        toast = new LearnEnglishToast(getActivity());

        builder.setView(view)
                .setPositiveButton(R.string.title_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {

                        } catch (Exception ex) {
                            toast.show(ex);
                        }
                    }
                })
                .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteWordFromDictionaryDialog.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}
