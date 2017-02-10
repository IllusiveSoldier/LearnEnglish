package knack.college.learnenglish;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import knack.college.learnenglish.model.LearnEnglishStatistic;
import knack.college.learnenglish.model.LearnEnglishToast;
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.StatisticDictionaryTrainingItem;

public class StatisticDictionaryTrainingActivity extends AppCompatActivity {

    LearnEnglishStatistic statistic;
    ArrayList<StatisticDictionaryTrainingItem> statisticDictionaryTrainingItems;
    LearnEnglishToast toast;
    RandomColor color = new RandomColor();

    RecyclerView statisticRecyclerView;
    StatisticDictionaryTrainingAdapter statisticDictionaryTrainingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_dictionary_training);

        statisticRecyclerView = (RecyclerView) findViewById(R.id.statisticRecyclerView);
        statisticRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        statisticRecyclerView.setHasFixedSize(true);

        statisticDictionaryTrainingAdapter = new StatisticDictionaryTrainingAdapter();

        statistic = new LearnEnglishStatistic(this);
        toast = new LearnEnglishToast(this);
        try {
            statisticDictionaryTrainingItems = (ArrayList<StatisticDictionaryTrainingItem>)
                    statistic.getAllItemsList();
            statisticRecyclerView.setAdapter(statisticDictionaryTrainingAdapter);
        } catch (Exception ex) {
            toast.show(ex);
        }
    }

    private class StatisticDictionaryTrainingHolder extends RecyclerView.ViewHolder {
        CardView statisticDictionaryTrainingCardView;
        ImageView statisticDictionaryTrainingImageView;
        TextView countWordsInDictionaryTextView;
        TextView countCorrectAnswerTextView;
        TextView countWrongAnswerTextView;

        StatisticDictionaryTrainingHolder(View itemView) {
            super(itemView);

            statisticDictionaryTrainingCardView = (CardView) itemView
                    .findViewById(R.id.statisticDictionaryTrainingCardView);
            statisticDictionaryTrainingImageView = (ImageView) itemView
                    .findViewById(R.id.statisticDictionaryTrainingImageView);
            countWordsInDictionaryTextView = (TextView) itemView
                    .findViewById(R.id.countWordsInDictionaryTextView);
            countCorrectAnswerTextView = (TextView) itemView
                    .findViewById(R.id.countCorrectAnswerTextView);
            countWrongAnswerTextView = (TextView) itemView
                    .findViewById(R.id.countWrongAnswerTextView);
        }
    }

    private class StatisticDictionaryTrainingAdapter
            extends RecyclerView.Adapter<StatisticDictionaryTrainingHolder> {

        @Override
        public StatisticDictionaryTrainingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StatisticDictionaryTrainingHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.statistic_dictionary_training_item, parent, false));
        }

        @Override
        public void onBindViewHolder(StatisticDictionaryTrainingHolder holder, int position) {
            statisticDictionaryTrainingItems.get(position);

            holder.statisticDictionaryTrainingImageView.setBackgroundColor(Color
                    .parseColor(color.getRandomColor()));
            holder.countWordsInDictionaryTextView
                    .setText("Всего слов в словаре: " + statisticDictionaryTrainingItems.get(position)
                            .getCountWordsInDictionary());
            holder.countCorrectAnswerTextView
                    .setText("Правильных ответов: " + statisticDictionaryTrainingItems.get(position).getCountCorrectAnswer());
            holder.countWrongAnswerTextView
                    .setText("Неправильных ответов: " + statisticDictionaryTrainingItems.get(position).getCountWrongAnswer());
        }

        @Override
        public int getItemCount() {
            return statisticDictionaryTrainingItems.size();
        }
    }
}
