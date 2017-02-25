package knack.college.learnenglish.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.statistic.DictionaryTrainingStatistic;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.CORRECT_ANSWER;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FRAGMENT_CODE;
import static knack.college.learnenglish.model.Constant.NUMBER_WORDS;
import static knack.college.learnenglish.model.Constant.WRONG_ANSWER;

public class DictionaryTrainingFragment extends Fragment {

    TextView title;
    TextView dictionaryTrainingEnglishWordTextView;
    EditText dictionaryTrainingTranslateWordEditText;
    ImageButton checkAnswerButton;
    ProgressBar progressBar;

    List<WordFromDictionary> wordFromDictionaries = new ArrayList<>();
    int randomIndex = 0;

    int wordsCount;
    int correctAnswer;
    int wrongAnswer;

    Random random = new Random();
    Validator validator = new Validator();

    private Toast toast;
    DictionaryTrainingStatistic statistic;
    Dictionary dictionary;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary_training, container, false);
        toast = new Toast(getActivity());
        dictionary = new Dictionary(getActivity().getApplicationContext());

        if (getArguments() != null && getArguments().containsKey(FRAGMENT_CODE)) {
            String fragmentCode = (String) getArguments().get(FRAGMENT_CODE);
            try {
                if (ALL_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    wordFromDictionaries = dictionary.getAllWordsList();
                } else if (FORGOTTEN_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    wordFromDictionaries = dictionary.getForgottenWords();
                }
            } catch (Exception ex) {
                toast.show(ex);
            }
        }

        toast = new Toast(getActivity());
        statistic = new DictionaryTrainingStatistic(getActivity());
        dictionary = new Dictionary(getActivity().getApplicationContext());

        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        dictionaryTrainingEnglishWordTextView = (TextView)
                view.findViewById(R.id.dictionaryTrainingEnglishWordTextView);
        dictionaryTrainingEnglishWordTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        dictionaryTrainingTranslateWordEditText = (EditText)
                view.findViewById(R.id.dictionaryTrainingTranslateWordEditText);
        dictionaryTrainingTranslateWordEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        try {
            wordsCount = wordFromDictionaries.size();
            progressBar.setMax(wordsCount);
            // Генерируем псевдослучайный индекс для коллекции слов,
            // по которому получим случайное слово
            if (wordFromDictionaries.size() > 0) {
                randomIndex = random.nextInt(wordFromDictionaries.size());
                // Выводим на контрол слово
                dictionaryTrainingEnglishWordTextView.setText(
                        wordFromDictionaries.get(randomIndex).getEnglishWord()
                );
            } else if (wordFromDictionaries.size() == 0) {
                toast.show(getActivity().getApplication().getResources()
                        .getString(R.string.title_notFoundWords),
                        R.mipmap.ic_sentiment_very_satisfied_black_24dp);
            }
        } catch (Exception ex) {
            toast.show(ex);
        }

        // При нажатии проверяется ответ
        checkAnswerButton = (ImageButton) view.findViewById(R.id.checkAnswerButton);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkAnswer();
                } catch (Exception ex) {
                    toast.show(ex);
                }
            }
        });

        // При долгом нажатии подсказка
        checkAnswerButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (wordFromDictionaries.size() > 0) {
                    toast.show(wordFromDictionaries.get(randomIndex).getTranslateWord(),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp, android.widget.Toast.LENGTH_SHORT);
                } else if (wordFromDictionaries.size() == 0) {
                    toast.show(getActivity().getApplication().getResources()
                                    .getString(R.string.title_notFoundWords),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                }

                return true;
            }
        });

        return view;
    }

    private void checkAnswer() throws Exception {
        if (wordFromDictionaries.size() > 0) {
            if (validator.isTranslation(wordFromDictionaries.get(randomIndex)
                    .getTranslateWord(), dictionaryTrainingTranslateWordEditText.getText()
                    .toString())) {
                correctAnswer++;
                // отмечаем дату тренировки слова
                dictionary.setLastTrainingWordDate(wordFromDictionaries.get(randomIndex).getGuid());
                // Очищаем поле ввода
                dictionaryTrainingTranslateWordEditText.setText("");
                // Удаляем слово из коллекции
                wordFromDictionaries.remove(randomIndex);
                // Если, коллекция не пустая - выводим нооые слово
                if (wordFromDictionaries.size() > 0) {
                    randomIndex = random.nextInt(wordFromDictionaries.size());
                    // Выводим на контрол слово
                    dictionaryTrainingEnglishWordTextView.setText(
                            wordFromDictionaries.get(randomIndex).getEnglishWord());
                } else if (wordFromDictionaries.size() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(NUMBER_WORDS, String.valueOf(wordsCount));
                    bundle.putString(CORRECT_ANSWER, String.valueOf(correctAnswer));
                    bundle.putString(WRONG_ANSWER, String.valueOf(wrongAnswer));

                    Fragment showDictionaryTrainingResult =
                            new ShowDictionaryTrainingResultFragment();
                    showDictionaryTrainingResult.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, showDictionaryTrainingResult)
                            .commit();

                    statistic.setWordsCount(wordsCount);
                    statistic.setCorrectAnswer(correctAnswer);
                    statistic.setWrongAnswer(wrongAnswer);
                    statistic.addRecord();

                    checkAnswerButton.setEnabled(false);
                    dictionaryTrainingEnglishWordTextView.setText("");
//                    toast.show(getResources().getString(R.string.title_wordsIsEmpty),
//                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                    dictionaryTrainingTranslateWordEditText.setText("");
                }
                progressBar.setProgress(correctAnswer + wrongAnswer);
            } else {
                wrongAnswer++;
                // Очищаем поле ввода
                dictionaryTrainingTranslateWordEditText.setText("");
                // Удаляем слово из коллекции
                wordFromDictionaries.remove(randomIndex);
                // Если, коллекция не пустая - выводим нооые слово
                if (wordFromDictionaries.size() > 0) {
                    randomIndex = random.nextInt(wordFromDictionaries.size());
                    // Выводим на контрол слово
                    dictionaryTrainingEnglishWordTextView.setText(
                            wordFromDictionaries.get(randomIndex).getEnglishWord());
                } else if (wordFromDictionaries.size() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(NUMBER_WORDS, String.valueOf(wordsCount));
                    bundle.putString(CORRECT_ANSWER, String.valueOf(correctAnswer));
                    bundle.putString(WRONG_ANSWER, String.valueOf(wrongAnswer));

                    Fragment showDictionaryTrainingResult =
                            new ShowDictionaryTrainingResultFragment();
                    showDictionaryTrainingResult.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, showDictionaryTrainingResult)
                            .commit();

                    statistic.setWordsCount(wordsCount);
                    statistic.setCorrectAnswer(correctAnswer);
                    statistic.setWrongAnswer(wrongAnswer);
                    statistic.addRecord();

                    checkAnswerButton.setEnabled(false);
                    dictionaryTrainingEnglishWordTextView.setText("");
//                    toast.show(getResources().getString(R.string.title_wordsIsEmpty),
//                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                    dictionaryTrainingTranslateWordEditText.setText("");
                }
                progressBar.setProgress(correctAnswer + wrongAnswer);
            }
        } else if (wordFromDictionaries.size() == 0) {
            toast.show(getResources().getString(R.string.title_notFoundWords),
                    R.mipmap.ic_sentiment_very_satisfied_black_24dp);
        }
    }
}
