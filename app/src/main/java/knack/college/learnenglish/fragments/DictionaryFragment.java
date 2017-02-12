package knack.college.learnenglish.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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

import knack.college.learnenglish.R;
import knack.college.learnenglish.dialogs.AddWordToDictionaryDialog;
import knack.college.learnenglish.dialogs.DeleteWordFromDictionaryDialog;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG;
import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG;


public class DictionaryFragment extends Fragment {

    FloatingActionButton addToDatabaseButton;
    RecyclerView dictionaryRecyclerView;
    SwipeRefreshLayout dictionarySwipeRefreshLayout;

    private static final int REQUEST_SELECTED = 1;

    private Toast toast;
    private RandomColor color = new RandomColor();

    private LearnEnglishAdapter learnEnglishAdapter;
    private Dictionary dictionary;
    private ArrayList<WordFromDictionary> wordFromDictionaries = new ArrayList<>();

    private int itemId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        toast = new Toast(getActivity());
        dictionary = new Dictionary(getActivity().getApplicationContext());

        addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
        addToDatabaseButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color
                .getRandomColor())));
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddWordToDictionaryDialog();
                dialogFragment.show(getActivity().getSupportFragmentManager(),
                        UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG);
            }
        });

        dictionarySwipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.dictionarySwipeRefreshLayout);
        dictionarySwipeRefreshLayout.setColorSchemeColors(Color.parseColor(color.getRandomColor()));
        dictionarySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dictionarySwipeRefreshLayout.setRefreshing(true);

                try {
                    new GetWordsTask().execute();
                    if (wordFromDictionaries.size() == learnEnglishAdapter.getItemCount()) {
                        learnEnglishAdapter.notifyDataSetChanged();
                    } else {
                        learnEnglishAdapter = new LearnEnglishAdapter();
                        dictionaryRecyclerView.setAdapter(learnEnglishAdapter);
                    }
                } catch (Exception ex) {
                    toast.show(ex);
                }

                dictionarySwipeRefreshLayout.setRefreshing(false);
            }
        });

        dictionaryRecyclerView = (RecyclerView) view.findViewById(R.id.dictionaryRecyclerView);

        try {
            dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                    .getApplicationContext()));
            dictionaryRecyclerView.setHasFixedSize(true);

            new GetWordsTask().execute();
            learnEnglishAdapter = new LearnEnglishAdapter();
            dictionaryRecyclerView.setAdapter(learnEnglishAdapter);
        } catch (Exception ex) {
            toast.show(ex);
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECTED:
                    int selected = data.getIntExtra(DeleteWordFromDictionaryDialog.TAG_SELECTED, -1);
                    if (selected == 1) {
                        try {
                            new DeleteWordTask().execute();
                        } catch (Exception ex) {
                            toast.show(ex);
                        }
                    }
                    break;
            }
        }
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
                itemId = dictionaryRecyclerView.getChildLayoutPosition(v);
                openWeightPicker();
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
                    .parseColor(color.getRandomColor()));
        }

        @Override
        public int getItemCount() {
            return wordFromDictionaries.size();
        }
    }

    public void openWeightPicker() {
        DialogFragment fragment = new DeleteWordFromDictionaryDialog();
        fragment.setTargetFragment(this, REQUEST_SELECTED);
        fragment.show(getFragmentManager(), UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG);
    }

    private class GetWordsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                wordFromDictionaries = (ArrayList<WordFromDictionary>) dictionary
                        .getAllWordsList();
            } catch (Exception ex) {
                toast.showSafe(ex);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class DeleteWordTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                dictionary.deleteWord(wordFromDictionaries.get(itemId).getGuid());
            } catch (Exception ex) {
                toast.showSafe(ex);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
