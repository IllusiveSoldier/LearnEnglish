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

import com.fasterxml.jackson.databind.ObjectMapper;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.toasts.Toast;
import knack.college.learnenglish.model.translate.TranslateDAO;
import knack.college.learnenglish.model.translate.Translator;

import static knack.college.learnenglish.model.Constant.ExceptionMessage.ENTER_WORDS_FOR_TRANSLATE_MESSAGE;
import static knack.college.learnenglish.model.Constant.ExceptionMessage.TRANSLATION_ERROR_MESSAGE;
import static knack.college.learnenglish.model.Constant.Translator.EN_RU;
import static knack.college.learnenglish.model.Constant.Translator.RU_EN;


public class AddWordToDictionaryDialog extends DialogFragment {

    private EditText englishWordEditText;
    private EditText translateWordEditText;
    private ImageView translateButton;
    private Snackbar snackbar;

    private Toast toast;
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

        englishWordEditText = (EditText) view.findViewById(R.id.englishWordEditText);
        englishWordEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        translateWordEditText = (EditText) view.findViewById(R.id.translateWordEditText);
        translateWordEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        translateButton = (ImageView) view.findViewById(R.id.translateButton);

        dictionary = new Dictionary(getActivity().getApplicationContext());
        toast = new Toast(getActivity());

        builder.setView(view)
               .setPositiveButton(R.string.title_add, null)
               .setNegativeButton(R.string.title_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddWordToDictionaryDialog.this.getDialog().cancel();
                    }
                });

        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (englishWordEditText.getText().toString().isEmpty()) {
                        if (!translateWordEditText.getText().toString().isEmpty()) {
                            String russianWord = translateWordEditText.getText().toString();
                            if (!russianWord.isEmpty()) {
                                new GetTranslateWord().execute(russianWord, EN_RU);
                            }
                        } else {
                            toast.show(ENTER_WORDS_FOR_TRANSLATE_MESSAGE);
                        }
                    } else if (translateWordEditText.getText().toString().isEmpty()) {
                        if (!englishWordEditText.getText().toString().isEmpty()) {
                            String englishWord = englishWordEditText.getText().toString();
                            if (!englishWord.isEmpty()) {
                                new GetTranslateWord().execute(englishWord, RU_EN);
                            }
                        } else {
                            toast.show(ENTER_WORDS_FOR_TRANSLATE_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    toast.show(ex);
                }
            }
        });

        translateButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                bufferEnglishWord = englishWordEditText.getText().toString();
                bufferTranslateWord = translateWordEditText.getText().toString();

                englishWordEditText.setText("");
                translateWordEditText.setText("");

                snackbar = Snackbar
                        .make(v, getResources().getString(R.string.hint_values_is_removed),
                                Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.hint_undo),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        englishWordEditText.setText(bufferEnglishWord);
                                        translateWordEditText.setText(bufferTranslateWord);
                                    }
                                });
                if (Build.VERSION.SDK_INT >= 23) {
                    snackbar.setActionTextColor(ContextCompat.getColor(getActivity()
                            .getApplicationContext(),
                            R.color.bright_green)
                    );
                } else {
                    snackbar.setActionTextColor(getResources().getColor(R.color.bright_green));
                }
                snackbar.show();

                return true;
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(false);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            // Вешаем listener на кнопку "Добавить", теперь диалог не закрывается
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dictionary.addWordWithTranslate(englishWordEditText.getText().toString(),
                                translateWordEditText.getText().toString());
                        dialog.cancel();
                    } catch (Exception ex) {
                        toast.show(ex);
                    }
                }
            });
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
                if (params[1].equals(EN_RU)) {
                    if (params[0] != null && !params[0].isEmpty()) {
                        response = translator.translateRus(params[0]);
                    }
                } else if (params[1].equals(RU_EN)) {
                    if (params[0] != null && !params[0].isEmpty()) {
                        response = translator.translateEng(params[0]);
                    }
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
                String readyTranslationsString = translateDAO.getText().get(0);
                if (readyTranslationsString != null) {
                    if (validator.isRussianCharactersInWord(readyTranslationsString)) {
                        translateWordEditText.setText(readyTranslationsString);
                    } else if (validator.isEnglishCharactersInWord(readyTranslationsString)) {
                        englishWordEditText.setText(readyTranslationsString);
                    }
                }
            } catch (Exception ex) {
                toast.show(TRANSLATION_ERROR_MESSAGE);
            }

            translateButton.setEnabled(true);
        }
    }
}
