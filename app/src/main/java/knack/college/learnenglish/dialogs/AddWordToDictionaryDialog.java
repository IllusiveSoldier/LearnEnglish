package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.toasts.Toast;


public class AddWordToDictionaryDialog extends DialogFragment {

    private EditText englishWordEditText;
    private EditText translateWordEditText;

    private Toast toast;
    private Dictionary dictionary;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.add_word_to_dictonary_dialog_window, null);

        englishWordEditText = (EditText) view.findViewById(R.id.englishWordEditText);
        translateWordEditText = (EditText) view.findViewById(R.id.translateWordEditText);

        dictionary = new Dictionary(getActivity().getApplicationContext());
        toast = new Toast(getActivity());

        builder.setView(view)
                .setPositiveButton(R.string.title_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            new AddWordTask().execute(englishWordEditText.getText().toString(),
                                    translateWordEditText.getText().toString());
                        } catch (Exception ex) {
                            toast.show(ex);
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

    private class AddWordTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                dictionary.addWordWithTranslate(params[0], params[1]);
            } catch (Exception ex) {
                toast.showSafe(ex);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
