package knack.college.learnenglish.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.WordFromDictionary;

public class DictionaryRecyclerViewAdapter
        extends RecyclerView.Adapter<DictionaryRecyclerViewAdapter.LearnEnglishViewHolder> {

    public DictionaryRecyclerViewAdapter(ArrayList<WordFromDictionary> words) {
        wordFromDictionaries = words;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LearnEnglishViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new LearnEnglishViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.learn_english_word_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(LearnEnglishViewHolder learnEnglishViewHolder, int i) {
        wordFromDictionaries.get(i);

        learnEnglishViewHolder.dictionaryEnglishWordTextView
                .setText(wordFromDictionaries.get(i).getEnglishWord());
        learnEnglishViewHolder.dictionaryTranslateWordTextView
                .setText(wordFromDictionaries.get(i).getTranslateWord());
    }

    @Override
    public int getItemCount() {
        return wordFromDictionaries.size();
    }

    private ArrayList<WordFromDictionary> wordFromDictionaries;

    static class LearnEnglishViewHolder extends RecyclerView.ViewHolder {

        CardView dictionaryCardView;
        TextView dictionaryEnglishWordTextView;
        TextView dictionaryTranslateWordTextView;

        LearnEnglishViewHolder(View itemView) {
            super(itemView);

            dictionaryCardView = (CardView) itemView.findViewById(R.id.dictionaryCardView);
            dictionaryEnglishWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryEnglishWordTextView);
            dictionaryTranslateWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryTranslateWordTextView);
        }
    }
}
