package knack.college.learnenglish.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import knack.college.learnenglish.R;
import knack.college.learnenglish.StatisticDictionaryTrainingActivity;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishStatistic;
import knack.college.learnenglish.model.LearnEnglishToast;


public class AssignmentDialog extends DialogFragment {

    private Dictionary dictionary;
    private LearnEnglishStatistic statistic;
    private LearnEnglishToast toast;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dictionary = new Dictionary(getActivity().getApplicationContext());
        statistic = new LearnEnglishStatistic(getActivity());
        toast = new LearnEnglishToast(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_whatToDo)
                .setItems(R.array.assignmentSelect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(getActivity().getApplicationContext(),
                                        StatisticDictionaryTrainingActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                try {
                                    statistic.clear();
                                } catch (Exception ex) {
                                    toast.show(ex);
                                }
                                break;
                            case 2:
                                try {
                                    dictionary.defaultWords();
                                } catch (Exception ex) {
                                    toast.show(ex);
                                }
                                break;
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
