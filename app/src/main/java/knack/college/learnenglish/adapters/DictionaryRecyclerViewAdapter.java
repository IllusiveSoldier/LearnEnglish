package knack.college.learnenglish.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.WordFromDictionary;

public class DictionaryRecyclerViewAdapter
        extends RecyclerView.Adapter<DictionaryRecyclerViewAdapter.LearnEnglishViewHolder> {

    private Random random = new Random();
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<WordFromDictionary> wordFromDictionaries = new ArrayList<>();


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
        learnEnglishViewHolder.learnEnglishWordItemImageView.setBackgroundColor(Color
                .parseColor(getRandomColor()));
    }

    @Override
    public int getItemCount() {
        return wordFromDictionaries.size();
    }

    static class LearnEnglishViewHolder extends RecyclerView.ViewHolder {

        CardView dictionaryCardView;
        TextView dictionaryEnglishWordTextView;
        TextView dictionaryTranslateWordTextView;
        ImageView learnEnglishWordItemImageView;

        LearnEnglishViewHolder(View itemView) {
            super(itemView);

            dictionaryCardView = (CardView) itemView.findViewById(R.id.dictionaryCardView);
            dictionaryEnglishWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryEnglishWordTextView);
            dictionaryTranslateWordTextView = (TextView) itemView
                    .findViewById(R.id.dictionaryTranslateWordTextView);
            learnEnglishWordItemImageView = (ImageView) itemView
                    .findViewById(R.id.learnEnglishWordItemImageView);
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
