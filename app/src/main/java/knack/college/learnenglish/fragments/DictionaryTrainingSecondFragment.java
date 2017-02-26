package knack.college.learnenglish.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.Validator;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.CORRECT_ANSWER;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FRAGMENT_CODE;
import static knack.college.learnenglish.model.Constant.NUMBER_WORDS;
import static knack.college.learnenglish.model.Constant.WRONG_ANSWER;


public class DictionaryTrainingSecondFragment extends Fragment {
    TextView title;
    TextView englishWord;
    TextView how;
    TextView translate;
    TextView question;
    ImageButton no;
    ImageButton yes;

    private Dictionary dictionary;
    private Validator validator = new Validator();
    private Random random;
    private Toast toast;
    private ArrayList<WordFromDictionary> wordFromDictionaries;

    private int randomIndexEnglish = 0;
    private int randomIndexTranslate = 0;

    int wordsCount;
    int correctAnswer;
    int wrongAnswer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_dictionary_training, container, false);

        title = (TextView) view.findViewById(R.id.title);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        englishWord = (TextView) view.findViewById(R.id.englishWord);
        englishWord.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        how = (TextView) view.findViewById(R.id.how);
        how.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        translate =  (TextView) view.findViewById(R.id.translate);
        translate.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        question = (TextView) view.findViewById(R.id.question);
        question.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        no = (ImageButton) view.findViewById(R.id.no);

        yes = (ImageButton) view.findViewById(R.id.yes);

        if (getArguments() != null && getArguments().containsKey(FRAGMENT_CODE)) {
            String fragmentCode = (String) getArguments().get(FRAGMENT_CODE);
            try {
                toast = new Toast(getActivity());
                dictionary = new Dictionary(getActivity().getApplicationContext());
                random = new Random();

                if (ALL_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    wordFromDictionaries = (ArrayList<WordFromDictionary>)
                            dictionary.getAllWordsList();
                } else if (FORGOTTEN_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    wordFromDictionaries = (ArrayList<WordFromDictionary>)
                            dictionary.getForgottenWords();
                }

                wordsCount = wordFromDictionaries.size();

                if (wordFromDictionaries != null) {
                    randomIndexEnglish = random.nextInt(wordFromDictionaries.size());
                    englishWord.setText(wordFromDictionaries.get(randomIndexEnglish)
                            .getEnglishWord());

                    randomIndexTranslate = random.nextInt(wordFromDictionaries.size());
                    translate.setText(wordFromDictionaries.get(randomIndexTranslate)
                            .getTranslateWord());
                }

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!validator.isTranslation(wordFromDictionaries.get(randomIndexEnglish)
                                .getEnglishWord(), wordFromDictionaries.get(randomIndexTranslate)
                                .getEnglishWord())) {
                            correctAnswer++;
                            wordFromDictionaries.remove(randomIndexEnglish);
                            if (wordFromDictionaries.size() > 0) {
                                randomIndexEnglish = random.nextInt(wordFromDictionaries.size());
                                englishWord.setText(wordFromDictionaries.get(randomIndexEnglish)
                                        .getEnglishWord());
                                randomIndexTranslate = random.nextInt(wordFromDictionaries.size());
                                translate.setText(wordFromDictionaries.get(randomIndexTranslate)
                                        .getTranslateWord());
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
                            }
                        } else {
                            wrongAnswer++;
                            wordFromDictionaries.remove(randomIndexEnglish);
                            if (wordFromDictionaries.size() > 0) {
                                randomIndexEnglish = random.nextInt(wordFromDictionaries.size());
                                englishWord.setText(wordFromDictionaries.get(randomIndexEnglish)
                                        .getEnglishWord());
                                randomIndexTranslate = random.nextInt(wordFromDictionaries.size());
                                translate.setText(wordFromDictionaries.get(randomIndexTranslate)
                                        .getTranslateWord());
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
                            }
                        }
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validator.isTranslation(wordFromDictionaries.get(randomIndexEnglish)
                                .getEnglishWord(), wordFromDictionaries.get(randomIndexTranslate)
                                .getEnglishWord())) {
                            correctAnswer++;
                            wordFromDictionaries.remove(randomIndexEnglish);
                            if (wordFromDictionaries.size() > 0) {
                                randomIndexEnglish = random.nextInt(wordFromDictionaries.size());
                                englishWord.setText(wordFromDictionaries.get(randomIndexEnglish)
                                        .getEnglishWord());
                                randomIndexTranslate = random.nextInt(wordFromDictionaries.size());
                                translate.setText(wordFromDictionaries.get(randomIndexTranslate)
                                        .getTranslateWord());
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
                            }
                        } else {
                            wrongAnswer++;
                            wordFromDictionaries.remove(randomIndexEnglish);
                            if (wordFromDictionaries.size() > 0) {
                                randomIndexEnglish = random.nextInt(wordFromDictionaries.size());
                                englishWord.setText(wordFromDictionaries.get(randomIndexEnglish)
                                        .getEnglishWord());
                                randomIndexTranslate = random.nextInt(wordFromDictionaries.size());
                                translate.setText(wordFromDictionaries.get(randomIndexTranslate)
                                        .getTranslateWord());
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
                            }
                        }
                    }
                });

            } catch (Exception ex) {
                toast.show(ex);
            }
        }

        return view;
    }

}
