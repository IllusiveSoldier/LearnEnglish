package knack.college.learnenglish.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.statistic.DictionaryTrainingStatistic;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_CLEAR_DICTIONARY_DIALOG;


public class AssignmentDialog extends DialogFragment {

    Dictionary dictionary;
    private Toast toast;
    private DictionaryTrainingStatistic statistic;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dictionary = new Dictionary(getActivity().getApplicationContext());
        toast = new Toast(getActivity());
        statistic = new DictionaryTrainingStatistic(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_whatToDo)
                .setItems(R.array.assignmentSelect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 1:
                                try {
                                    statistic.clear(getActivity().getApplicationContext());
                                } catch (Exception ex) {
                                    toast.show(ex);
                                }
                                break;
                            case 3:
                                try {
                                    DialogFragment dialogFragment = new ClearDictionaryDialog();
                                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                                            UNIQUE_NAME_CLEAR_DICTIONARY_DIALOG);
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
