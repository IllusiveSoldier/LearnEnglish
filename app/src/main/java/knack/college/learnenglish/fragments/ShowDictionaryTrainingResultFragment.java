package knack.college.learnenglish.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import knack.college.learnenglish.R;

import static knack.college.learnenglish.model.Constant.CORRECT_ANSWER;
import static knack.college.learnenglish.model.Constant.NUMBER_WORDS;
import static knack.college.learnenglish.model.Constant.WRONG_ANSWER;

public class ShowDictionaryTrainingResultFragment extends Fragment {

    TextView complete;
    TextView results;
    TextView numberWords;
    TextView numberWordsValue;
    TextView correctAnswer;
    TextView correctAnswerValue;
    TextView wrongAnswer;
    TextView wrongAnswerValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_show_dictionary_training_result, container, false);

        complete = (TextView) view.findViewById(R.id.complete);
        complete.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        results = (TextView) view.findViewById(R.id.results);
        results.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        numberWords = (TextView) view.findViewById(R.id.numberWords);
        numberWords.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        numberWordsValue = (TextView) view.findViewById(R.id.numberWordsValue);
        numberWordsValue.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));
        if (getArguments() != null && getArguments().containsKey(NUMBER_WORDS)) {
            String numberWords = (String) getArguments().get(NUMBER_WORDS);
            numberWordsValue.setText(numberWords);
        }

        correctAnswer = (TextView) view.findViewById(R.id.correctAnswer);
        correctAnswer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        correctAnswerValue = (TextView) view.findViewById(R.id.correctAnswerValue);
        correctAnswerValue.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));
        if (getArguments() != null && getArguments().containsKey(CORRECT_ANSWER)) {
            String numberWords = (String) getArguments().get(CORRECT_ANSWER);
            correctAnswerValue.setText(numberWords);
        }

        wrongAnswer = (TextView) view.findViewById(R.id.wrongAnswer);
        wrongAnswer.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));

        wrongAnswerValue = (TextView) view.findViewById(R.id.wrongAnswerValue);
        wrongAnswerValue.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/Roboto/Roboto-Light.ttf"));
        if (getArguments() != null && getArguments().containsKey(WRONG_ANSWER)) {
            String numberWords = (String) getArguments().get(WRONG_ANSWER);
            wrongAnswerValue.setText(numberWords);
        }


        return view;
    }

}
