package knack.college.learnenglish.fragments;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishStatistic;
import knack.college.learnenglish.model.LearnEnglishToast;


public class ProfileFragment extends Fragment {

    FloatingActionButton deleteDictionaryButton;
    TextView numberOfWordsValue;
    /** Custom'ный Toast */
    private LearnEnglishToast toast;
    /** Статистика приложения */
    LearnEnglishStatistic statistic;
    ArrayList<String> colors = new ArrayList<>();
    private Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        toast = new LearnEnglishToast(getActivity());
        statistic = new LearnEnglishStatistic(getActivity());

        deleteDictionaryButton = (FloatingActionButton) view.findViewById(R.id.deleteDictionaryButton);
        deleteDictionaryButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(getRandomColor())));
        deleteDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dictionary dictionary = new Dictionary(getActivity().getApplicationContext());
                    dictionary.clear();
                } catch (Exception ex) {
                    toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
                }
            }
        });

        numberOfWordsValue = (TextView) view.findViewById(R.id.numberOfWordsValue);
        try {
            numberOfWordsValue.setText(String.valueOf(statistic.getNumberOfWordsInDictionary()));
        } catch (Exception ex) {
            toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
        }


        return view;
    }

    private String getRandomColor() {
        colors.add("#FF1744");
        colors.add("#F50057");
        colors.add("#D500F9");
        colors.add("#651FFF");
        colors.add("#3D5AFE");
        colors.add("#2979FF");
        colors.add("#00B0FF");
        colors.add("#00E5FF");
        colors.add("#1DE9B6");
        colors.add("#00E676");
        colors.add("#76FF03");
        colors.add("#C6FF00");
        colors.add("#FFEA00");
        colors.add("#FFC400");
        colors.add("#FF9100");
        colors.add("#FF3D00");


        return colors.get(random.nextInt(colors.size()));
    }
}
