package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishToast;


public class AddWordToDictionaryDialog extends DialogFragment {

    private EditText englishWordEditText;
    private EditText translateWordEditText;
    private LearnEnglishToast toast;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_word_to_dictonary_dialog_window, null);

        englishWordEditText = (EditText) view.findViewById(R.id.englishWordEditText);
        translateWordEditText = (EditText) view.findViewById(R.id.translateWordEditText);

        toast = new LearnEnglishToast(getActivity());


        builder.setView(view)
                .setPositiveButton(R.string.title_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Dictionary dictionary = new Dictionary(getActivity().getApplicationContext());
                            dictionary.addWordWithTranslate(englishWordEditText.getText().toString(),
                                    translateWordEditText.getText().toString());
                        } catch (Exception ex) {
                            toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
                        }
                    }
                })
                .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddWordToDictionaryDialog.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}
