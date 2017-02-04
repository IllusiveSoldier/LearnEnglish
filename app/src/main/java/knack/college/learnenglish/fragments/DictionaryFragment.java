package knack.college.learnenglish.fragments;


import android.app.DialogFragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.adapters.DictionaryRecyclerViewAdapter;
import knack.college.learnenglish.dialogs.AddWordToDatabase;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishToast;
import knack.college.learnenglish.model.WordFromDictionary;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG;


public class DictionaryFragment extends Fragment {

    FloatingActionButton addToDatabaseButton;
    RecyclerView dictionaryRecyclerView;
    SwipeRefreshLayout dictionarySwipeRefreshLayout;

    private Random random = new Random();
    private ArrayList<String> colors = new ArrayList<>();

    private LearnEnglishToast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        toast = new LearnEnglishToast(getActivity());

        addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
        addToDatabaseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(getRandomColor())));
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddWordToDatabase();
                dialogFragment.show(getActivity().getFragmentManager(),
                        UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG);
            }
        });

        dictionaryRecyclerView = (RecyclerView) view.findViewById(R.id.dictionaryRecyclerView);

        try {
            dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                    .getApplicationContext()));
            dictionaryRecyclerView.setHasFixedSize(true);

            initializeAdapter (
                    (ArrayList<WordFromDictionary>) new Dictionary(getActivity()
                            .getApplicationContext()).getAllWordsList()
            );
        } catch (Exception ex) {
            toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
        }

        dictionarySwipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.dictionarySwipeRefreshLayout);
        dictionarySwipeRefreshLayout.setColorSchemeColors(Color.parseColor(getRandomColor()));
        dictionarySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dictionarySwipeRefreshLayout.setRefreshing(true);

                try {
                    initializeAdapter (
                            (ArrayList<WordFromDictionary>) new Dictionary(getActivity()
                                    .getApplicationContext()).getAllWordsList());
                } catch (Exception ex) {
                    toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
                }

                dictionarySwipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }



    private void initializeAdapter(ArrayList<WordFromDictionary> words) throws Exception {
        dictionaryRecyclerView.setAdapter(new DictionaryRecyclerViewAdapter(words));
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
