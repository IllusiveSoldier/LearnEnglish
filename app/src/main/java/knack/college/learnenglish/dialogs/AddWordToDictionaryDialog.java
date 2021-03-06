package knack.college.learnenglish.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.toasts.ToastWrapper;
import knack.college.learnenglish.model.translate.TranslateDAO;
import knack.college.learnenglish.model.translate.Translator;

import static knack.college.learnenglish.model.Constant.Translator.EN_RU;
import static knack.college.learnenglish.model.Constant.Translator.RU_EN;


public class AddWordToDictionaryDialog extends DialogFragment {
    private static final String TRANSLATION_ERROR_MESSAGE = "Не удалось перевести";

    // Controls
    private EditText englishWordEditText;
    private EditText translateWordEditText;
    private ImageView translateButton;
    private Snackbar snackbar;

    private ToastWrapper toast;
    private Dictionary dictionary;
    private Exception exception;
    private Validator validator = new Validator();

    private String bufferEnglishWord;
    private String bufferTranslateWord;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_word_to_dictonary_dialog_window, null);

        initializeToast();
        initializeDictionary();
        initializeControls(view, builder);

        return builder.create();
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(getActivity().getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    getResources().getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeDictionary() {
        try {
            dictionary = new Dictionary(getActivity().getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string.error_message_failed_initialize_dictionary)
            );
        }
    }

    private void initializeControls(View view, AlertDialog.Builder builder) {
        try {
            englishWordEditText = (EditText) view.findViewById(R.id.englishWordEditText);
            englishWordEditText.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            translateWordEditText = (EditText) view.findViewById(R.id.translateWordEditText);
            translateWordEditText.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            translateButton = (ImageView) view.findViewById(R.id.translateButton);

            builder.setView(view)
                    .setPositiveButton(R.string.title_add, null)
                    .setNegativeButton(
                            R.string.title_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            }
                    );

            translateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (englishWordEditText.hasFocus()) {
                            final String englishWord = englishWordEditText.getText().toString();
                            new GetTranslateWord().execute(englishWord, RU_EN);
                        } else if (translateWordEditText.hasFocus()) {
                            final String translationWord = translateWordEditText.getText().toString();
                            new GetTranslateWord().execute(translationWord, EN_RU);
                        }
                    } catch (Exception ex) {
                        toast.show(TRANSLATION_ERROR_MESSAGE);
                    }
                }
            });

            translateButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!englishWordEditText.getText().toString().isEmpty()
                            && !translateWordEditText.getText().toString().isEmpty()) {
                        bufferEnglishWord = englishWordEditText.getText().toString();
                        bufferTranslateWord = translateWordEditText.getText().toString();

                        clearEditTextControls();

                        snackbar = Snackbar
                                .make(
                                    v,
                                    getResources().getString(R.string.hint_values_is_removed),
                                    Snackbar.LENGTH_LONG
                                )
                                .setAction(
                                    getResources().getString(R.string.hint_undo),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            englishWordEditText.setText(bufferEnglishWord);
                                            translateWordEditText.setText(bufferTranslateWord);
                                        }
                                    }
                                );
                        if (Build.VERSION.SDK_INT >= 23) {
                            snackbar.setActionTextColor(
                                    ContextCompat.getColor(getActivity().getApplicationContext(),
                                            R.color.bright_green)
                            );
                        } else {
                            snackbar.setActionTextColor(
                                    getResources().getColor(R.color.bright_green)
                            );
                        }
                        snackbar.show();
                    }

                    return true;
                }
            });
        } catch (Exception e) {
            toast.show(getResources().getString(
                    R.string.error_message_failed_initialize_controls)
            );
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (negativeButton != null) {
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            dictionary.addWordWithTranslate(
                                    englishWordEditText.getText().toString(),
                                    translateWordEditText.getText().toString()
                            );
                            dialog.cancel();
                        } catch (Exception ex) {
                            toast.show(ex.getMessage());
                        }
                    }
                });
            }
        }
    }

    private class GetTranslateWord extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            translateButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... params) {
            String response = null;
            try {
                Translator translator = new Translator();
                if (params[1].equals(EN_RU) && params[0] != null && !params[0].isEmpty()) {
                    response = translator.translateRus(params[0]);
                } else if (params[1].equals(RU_EN) && params[0] != null && !params[0].isEmpty()) {
                    response = translator.translateEng(params[0]);
                }
            } catch (Exception ex) {
                exception = ex;
            }

            return response;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (exception != null) {
                toast.show(TRANSLATION_ERROR_MESSAGE);
            }
            try {
                ObjectMapper mapper = new ObjectMapper();
                TranslateDAO translateDAO = mapper.readValue(result, TranslateDAO.class);
                final String readyTranslationsString = translateDAO.getText().get(0);

                if (readyTranslationsString != null
                        && validator.isRussianCharactersInWord(readyTranslationsString)) {
                    translateWordEditText.setText(readyTranslationsString);
                } else if (readyTranslationsString != null
                        && validator.isEnglishCharactersInWord(readyTranslationsString)) {
                    englishWordEditText.setText(readyTranslationsString);
                }
            } catch (Exception ex) {
                toast.show(TRANSLATION_ERROR_MESSAGE);
            }

            translateButton.setEnabled(true);
        }
    }

    /** Метод, который очищает текст с EditText'ов */
    private void clearEditTextControls() {
        try {
            if (englishWordEditText != null && translateWordEditText != null) {
                englishWordEditText.setText("");
                translateWordEditText.setText("");
            }
        } catch (Exception ex) {
            toast.show("Не удалось очистить поля");
        }
    }
}
