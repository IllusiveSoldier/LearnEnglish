package knack.college.learnenglish.fragments;


import android.app.DialogFragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.dialogs.AddWordToDictionaryDialog;
import knack.college.learnenglish.dialogs.DeleteWordFromDictionaryDialog;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishToast;
import knack.college.learnenglish.model.WordFromDictionary;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG;
import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG;


public class DictionaryFragment extends Fragment {

    FloatingActionButton addToDatabaseButton;
    RecyclerView dictionaryRecyclerView;
    SwipeRefreshLayout dictionarySwipeRefreshLayout;

    private Random random = new Random();
    private ArrayList<String> colors = new ArrayList<>();

    private LearnEnglishToast toast;

    private LearnEnglishAdapter learnEnglishAdapter;

    private Dictionary dictionary;

    private ArrayList<WordFromDictionary> wordFromDictionaries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        toast = new LearnEnglishToast(getActivity());

        dictionary = new Dictionary(getActivity().getApplicationContext());

        addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
        addToDatabaseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(getRandomColor())));
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddWordToDictionaryDialog();
                dialogFragment.show(getActivity().getFragmentManager(),
                        UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG);
            }
        });

        dictionaryRecyclerView = (RecyclerView) view.findViewById(R.id.dictionaryRecyclerView);

        try {
            dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                    .getApplicationContext()));
            dictionaryRecyclerView.setHasFixedSize(true);

            wordFromDictionaries = (ArrayList<WordFromDictionary>) dictionary.getAllWordsList();
            learnEnglishAdapter = new LearnEnglishAdapter();
            dictionaryRecyclerView.setAdapter(learnEnglishAdapter);
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
                    wordFromDictionaries = (ArrayList<WordFromDictionary>) dictionary
                            .getAllWordsList();
                    if (wordFromDictionaries.size() == learnEnglishAdapter.getItemCount()) {
                        learnEnglishAdapter.notifyDataSetChanged();
                    } else {
                        learnEnglishAdapter = new LearnEnglishAdapter();
                        dictionaryRecyclerView.setAdapter(learnEnglishAdapter);
                    }
                } catch (Exception ex) {
                    toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
                }

                dictionarySwipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }


    private class LearnEnglishHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {
        CardView dictionaryCardView;
        TextView dictionaryEnglishWordTextView;
        TextView dictionaryTranslateWordTextView;
        ImageView learnEnglishWordItemImageView;

        LearnEnglishHolder(View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(this);
            dictionaryCardView = (CardView) itemView.findViewById(R.id.dictionaryCardView);
            dictionaryEnglishWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryEnglishWordTextView);
            dictionaryTranslateWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryTranslateWordTextView);
            learnEnglishWordItemImageView = (ImageView) itemView
                    .findViewById(R.id.learnEnglishWordItemImageView);
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                DialogFragment dialogFragment = new DeleteWordFromDictionaryDialog();
                dialogFragment.show(getActivity().getFragmentManager(),
                        UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG);
            } catch (Exception ex) {
                toast.show(ex);
            }


            return false;
        }
    }

    private class LearnEnglishAdapter extends RecyclerView.Adapter<LearnEnglishHolder> {

        @Override
        public LearnEnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LearnEnglishHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.learn_english_word_item, parent, false));
        }

        @Override
        public void onBindViewHolder(LearnEnglishHolder holder, int position) {
            wordFromDictionaries.get(position);

            holder.dictionaryEnglishWordTextView
                    .setText(wordFromDictionaries.get(position).getEnglishWord());
            holder.dictionaryTranslateWordTextView
                    .setText(wordFromDictionaries.get(position).getTranslateWord());
            holder.learnEnglishWordItemImageView.setBackgroundColor(Color
                    .parseColor(getRandomColor()));
        }

        @Override
        public int getItemCount() {
            return wordFromDictionaries.size();
        }
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
