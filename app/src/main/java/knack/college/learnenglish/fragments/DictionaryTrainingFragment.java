package knack.college.learnenglish.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.statistic.DictionaryTrainingStatistic;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FRAGMENT_CODE;

public class DictionaryTrainingFragment extends Fragment {

    TextView dictionaryTrainingEnglishWordTextView;
    EditText dictionaryTrainingTranslateWordEditText;
    ImageButton checkAnswerButton;
    ImageButton helpAnswerButton;
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
                    wordFromDictionaries = (ArrayList<WordFromDictionary>)
                            dictionary.getAllWordsList();
                } else if (FORGOTTEN_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    wordFromDictionaries = (ArrayList<WordFromDictionary>)
                            dictionary.getForgottenWords();
                }
            } catch (Exception ex) {
                toast.show(ex);
            }
        }

        toast = new Toast(getActivity());
        statistic = new DictionaryTrainingStatistic(getActivity());
        dictionary = new Dictionary(getActivity().getApplicationContext());

        dictionaryTrainingEnglishWordTextView = (TextView)
                view.findViewById(R.id.dictionaryTrainingEnglishWordTextView);
        dictionaryTrainingTranslateWordEditText = (EditText)
                view.findViewById(R.id.dictionaryTrainingTranslateWordEditText);
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

        helpAnswerButton = (ImageButton) view.findViewById(R.id.helpAnswerButton);
        helpAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordFromDictionaries.size() > 0) {
                    toast.show(wordFromDictionaries.get(randomIndex).getTranslateWord(),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp, android.widget.Toast.LENGTH_SHORT);
                } else if (wordFromDictionaries.size() == 0) {
                    toast.show(getActivity().getApplication().getResources()
                            .getString(R.string.title_notFoundWords),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                }
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
                    statistic.setWordsCount(wordsCount);
                    statistic.setCorrectAnswer(correctAnswer);
                    statistic.setWrongAnswer(wrongAnswer);
                    statistic.addRecord();

                    checkAnswerButton.setEnabled(false);
                    dictionaryTrainingEnglishWordTextView.setText("");
                    toast.show(getResources().getString(R.string.title_wordsIsEmpty),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                    dictionaryTrainingTranslateWordEditText.setText("");
                }
                dictionaryTrainingTranslateWordEditText.getBackground().setColorFilter(
                        Color.parseColor(RandomColor.Colors.GREEN),
                        PorterDuff.Mode.SRC_ATOP);
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
                    statistic.setWordsCount(wordsCount);
                    statistic.setCorrectAnswer(correctAnswer);
                    statistic.setWrongAnswer(wrongAnswer);
                    statistic.addRecord();

                    checkAnswerButton.setEnabled(false);
                    dictionaryTrainingEnglishWordTextView.setText("");
                    toast.show(getResources().getString(R.string.title_wordsIsEmpty),
                            R.mipmap.ic_sentiment_very_satisfied_black_24dp);
                    dictionaryTrainingTranslateWordEditText.setText("");
                }
                dictionaryTrainingTranslateWordEditText.getBackground().setColorFilter(
                        ContextCompat.getColor(getActivity().getApplicationContext(),
                                R.color.colorAccent),
                        PorterDuff.Mode.SRC_ATOP);
                progressBar.setProgress(correctAnswer + wrongAnswer);
            }
        } else if (wordFromDictionaries.size() == 0) {
            toast.show(getResources().getString(R.string.title_notFoundWords),
                    R.mipmap.ic_sentiment_very_satisfied_black_24dp);
        }
    }
}
