package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;

import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;


public class AddWordToDatabase extends DialogFragment {

    private EditText englishWordEditText;
    private EditText translateWordEditText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_to_database_dialog_window, null);

        englishWordEditText = (EditText) view.findViewById(R.id.englishWordEditText);
        translateWordEditText = (EditText) view.findViewById(R.id.translateWordEditText);

        builder.setView(view)
                .setPositiveButton(R.string.title_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Dictionary dictionary = new Dictionary(getActivity().getApplicationContext());
                            dictionary.addWordWithTranslate(englishWordEditText.getText().toString(),
                                    translateWordEditText.getText().toString());
                        } catch (Exception ex) {
                            Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
                        }
                    }
                })
                .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddWordToDatabase.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}
