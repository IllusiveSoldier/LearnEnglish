package knack.college.learnenglish.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.Constant.CORRECT_ANSWER;
import static knack.college.learnenglish.model.Constant.NUMBER_WORDS;
import static knack.college.learnenglish.model.Constant.WRONG_ANSWER;

public class ShowDictionaryTrainingResultFragment extends Fragment {

    // Controls
    private TextView complete;
    private TextView results;
    private TextView condition;
    private TextView numberWords;
    private TextView numberWordsValue;
    private TextView correctAnswer;
    private TextView correctAnswerValue;
    private TextView wrongAnswer;
    private TextView wrongAnswerValue;
    // Toast
    private ToastWrapper toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_show_dictionary_training_result,
                container,
                false
        );

        initializeToast();
        initializeControls(view);

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
            complete = (TextView) view.findViewById(R.id.complete);
            complete.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            results = (TextView) view.findViewById(R.id.results);
            results.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            condition = (TextView) view.findViewById(R.id.condition);
            condition.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            numberWords = (TextView) view.findViewById(R.id.numberWords);
            numberWords.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            numberWordsValue = (TextView) view.findViewById(R.id.numberWordsValue);
            numberWordsValue.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );


            correctAnswer = (TextView) view.findViewById(R.id.correctAnswer);
            correctAnswer.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            correctAnswerValue = (TextView) view.findViewById(R.id.correctAnswerValue);
            correctAnswerValue.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            wrongAnswer = (TextView) view.findViewById(R.id.wrongAnswer);
            wrongAnswer.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            wrongAnswerValue = (TextView) view.findViewById(R.id.wrongAnswerValue);
            wrongAnswerValue.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                            "fonts/Roboto/Roboto-Light.ttf")
            );

            calculateCondition(getArguments());

            if (getArguments() != null && getArguments().containsKey(CORRECT_ANSWER)) {
                String numberWords = (String) getArguments().get(CORRECT_ANSWER);
                correctAnswerValue.setText(numberWords);
            }

            if (getArguments() != null && getArguments().containsKey(NUMBER_WORDS)) {
                String numberWords = (String) getArguments().get(NUMBER_WORDS);
                numberWordsValue.setText(numberWords);
            }

            if (getArguments() != null && getArguments().containsKey(WRONG_ANSWER)) {
                String numberWords = (String) getArguments().get(WRONG_ANSWER);
                wrongAnswerValue.setText(numberWords);
            }


        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_initialize_controls));
        }
    }

    private void calculateCondition(Bundle bundle) {
        try {
            if (bundle != null) {
                int numberOfWords = 0;
                int correctAnswer = 0;
                int wrongAnswer = 0;
                if (bundle.containsKey(NUMBER_WORDS) && bundle.get(NUMBER_WORDS) != null) {
                    numberOfWords = Integer.valueOf((String) bundle.get(NUMBER_WORDS));
                } else {
                    this.correctAnswer.setText(getActivity().getResources()
                            .getString(R.string.title_value_not_found));
                }
                if (bundle.containsKey(CORRECT_ANSWER) && bundle.get(CORRECT_ANSWER) != null) {
                    correctAnswer = Integer.valueOf((String) bundle.get(CORRECT_ANSWER));
                } else {
                    this.correctAnswer.setText(getActivity().getResources()
                            .getString(R.string.title_value_not_found));
                }
                if (bundle.containsKey(WRONG_ANSWER) && bundle.get(WRONG_ANSWER) != null) {
                    wrongAnswer = Integer.valueOf((String) bundle.get(WRONG_ANSWER));
                } else {
                    this.correctAnswer.setText(getActivity().getResources()
                            .getString(R.string.title_value_not_found));
                }

                double percent = new BigDecimal((correctAnswer * 100) / numberOfWords)
                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (percent <= 50) {
                    condition.setTextColor(ContextCompat.getColor(
                            getActivity().getApplicationContext(), R.color.red));
                    condition.setText("ПЛОХО");
                } else if (percent > 50 && percent <= 80) {
                    condition.setTextColor(ContextCompat.getColor(
                            getActivity().getApplicationContext(), R.color.orange));
                    condition.setText("ХОРОШО");
                } else if (percent > 80) {
                    condition.setTextColor(ContextCompat.getColor(
                            getActivity().getApplicationContext(), R.color.green));
                    condition.setText("ОТЛИЧНО");
                }
            }
        } catch (Exception e) {

        }
    }
}
