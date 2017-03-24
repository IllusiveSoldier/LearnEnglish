package knack.college.learnenglish.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.statistic.DictionaryTrainingStatistic;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static android.app.Activity.RESULT_OK;
import static knack.college.learnenglish.model.Constant.CORRECT;
import static knack.college.learnenglish.model.Constant.CORRECT_ANSWER;
import static knack.college.learnenglish.model.Constant.NUMBER_WORDS;
import static knack.college.learnenglish.model.Constant.WRONG;
import static knack.college.learnenglish.model.Constant.WRONG_ANSWER;


public class TrainingTFragment extends Fragment {

    private static final int REQ_CODE_SPEECH_INPUT = 1;

    // Controls
    private CardView cardView;
    private TextView title;
    private TextView englishWord;
    private ImageButton imageButton;
    private ProgressBar progressBar;

    // Toast
    private ToastWrapper toast;
    // Dictionary
    private Dictionary dictionary;
    // Words from dictionary
    private ArrayList<WordFromDictionary> words;
    // Random
    private Random random;
    // Random item index
    private int randomItemId;
    // Validator
    private Validator validator;
    // Dictionary training statistic
    private DictionaryTrainingStatistic dictionaryTrainingStatistic;

    // Words number, correct words number, wrong words number
    private int wordsNumber;
    private int correctWordsNumber;
    private int wrongWordsNumber;

    private String userSpeechText;

    // Statistic type
    private static final String TYPE = "speechToText";
    // Statistic hash map
    private HashMap<String, String> statisticMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_dictionary_training, container, false);

        initializeToast();
        initializeControls(view);
        initializeDictionary();
        initializeWordsFromDictionary();
        setData();
        initializeValidator();
        initializeDictionaryTrainingStatistic();
        initializeStatisticMap();

        return view;
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

    private void initializeControls(View view) {
        try {
            cardView = (CardView) view.findViewById(R.id.cardView);

            title = (TextView) view.findViewById(R.id.title);
            title.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            englishWord = (TextView) view.findViewById(R.id.englishWord);
            englishWord.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    promptSpeechInput();
                }
            });

            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_initialize_controls));
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

    private void initializeWordsFromDictionary() {
        try {
            words = dictionary.getAllWordsList();
            // set words number
            wordsNumber = words.size();
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_get_words));
        }
    }

    private void setData() {
        try {
            if (wordsNumber > 0) {
                progressBar.setMax(wordsNumber);
                random = new Random();

                // set random item index
                randomItemId = random.nextInt(words.size());
                // Word from dictionary class-wrapper
                WordFromDictionary word = words.get(randomItemId);
                if (word != null) {
                    // set english word
                    final String translateWord = word.getTranslateWord();
                    this.englishWord.setText(translateWord);
                }
            }
        } catch (Exception e) {
            toast.show(
                    getResources().getString(
                            R.string.error_message_failed_initialize_data_to_display
                    )
            );
        }
    }

    private void initializeValidator() {
        try {
            validator = new Validator();
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string.error_message_failed_initialize_validator)
            );
        }
    }

    private void initializeDictionaryTrainingStatistic() {
        try {
            dictionaryTrainingStatistic = new DictionaryTrainingStatistic(getActivity()
                    .getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string
                            .error_message_failed_initialize_dictionary_training_statistic)
            );
        }
    }

    private void initializeStatisticMap() {
        try {
            statisticMap = new HashMap<String, String>();
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string
                            .error_message_failed_initialize_statistic_map)
            );
        }
    }

    private void checkAnswer() {
        try {
            if (words.size() > 0) {
                WordFromDictionary word = words.get(randomItemId);
                if (word != null) {
                    if (validator.isTranslation(word.getEnglishWord(), userSpeechText)) {
                        cardView.setCardBackgroundColor(ContextCompat.getColor(
                                getActivity().getApplicationContext(), R.color.green
                        ));
                        statisticMap.put(words.get(randomItemId).getGuid(),
                                String.valueOf(CORRECT));
                        correctWordsNumber++;
                        setCardViewTextColor(R.color.white);
                        clearEnglishWordControl();
                        words.remove(randomItemId);
                        if (words.size() > 0) {
                            randomItemId = random.nextInt(words.size());
                            final String translateWord = words.get(randomItemId).getTranslateWord();
                            this.englishWord.setText(translateWord);
                        } else {
                            dictionaryTrainingStatistic.addRecord(
                                    TYPE,
                                    statisticMap,
                                    new Date().getTime()
                            );

                            Bundle bundle = new Bundle();
                            bundle.putString(NUMBER_WORDS, String.valueOf(wordsNumber));
                            bundle.putString(CORRECT_ANSWER, String.valueOf(correctWordsNumber));
                            bundle.putString(WRONG_ANSWER, String.valueOf(wrongWordsNumber));

                            Fragment showDictionaryTrainingResult =
                                    new ShowDictionaryTrainingResultFragment();
                            showDictionaryTrainingResult.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.activity_task, showDictionaryTrainingResult)
                                    .commit();
                        }
                        progressBar.setProgress(correctWordsNumber + wrongWordsNumber);
                    } else {
                        cardView.setCardBackgroundColor(ContextCompat.getColor(
                                getActivity().getApplicationContext(), R.color.colorAccent
                        ));
                        statisticMap.put(words.get(randomItemId).getGuid(),
                                String.valueOf(WRONG));
                        wrongWordsNumber++;
                        setCardViewTextColor(R.color.white);
                        clearEnglishWordControl();
                        words.remove(randomItemId);
                        if (words.size() > 0) {
                            randomItemId = random.nextInt(words.size());
                            final String translateWord = words.get(randomItemId).getTranslateWord();
                            this.englishWord.setText(translateWord);
                        } else {
                            dictionaryTrainingStatistic.addRecord(
                                    TYPE,
                                    statisticMap,
                                    new Date().getTime()
                            );

                            Bundle bundle = new Bundle();
                            bundle.putString(NUMBER_WORDS, String.valueOf(wordsNumber));
                            bundle.putString(CORRECT_ANSWER, String.valueOf(correctWordsNumber));
                            bundle.putString(WRONG_ANSWER, String.valueOf(wrongWordsNumber));

                            Fragment showDictionaryTrainingResult =
                                    new ShowDictionaryTrainingResultFragment();
                            showDictionaryTrainingResult.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.activity_task, showDictionaryTrainingResult)
                                    .commit();
                        }
                        progressBar.setProgress(correctWordsNumber + wrongWordsNumber);
                    }
                }
            } else {
                toast.show(getResources().getString(R.string.title_notFoundWords));
            }
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_check_answer));
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US.toString());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.title_translate_here));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity().getApplicationContext(),
                    getString(R.string.error_message_your_device_not_supported_speech_input),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    userSpeechText = result.get(0);

                    toast.show(userSpeechText);

                    checkAnswer();
                }
                break;
            }
        }
    }

    private void setCardViewTextColor(int color) {
        try {
            title.setTextColor(
                    ContextCompat.getColor(getActivity().getApplicationContext(), color)
            );
            englishWord.setTextColor(
                    ContextCompat.getColor(getActivity().getApplicationContext(), color)
            );
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_set_color_text));
        }
    }

    private void clearEnglishWordControl() {
        try {
            if (englishWord != null) {
                englishWord.setText("");
            }
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_clean_control));
        }
    }
}
