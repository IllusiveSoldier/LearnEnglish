package knack.college.learnenglish.model;

import android.app.Activity;

import knack.college.learnenglish.R;

/** Класс, который реализует методы по доступу к статистике приложения */
public class LearnEnglishStatistic {
    /** Activity */
    private Activity activity;
    /** Количество слов в словаре */
    private int numberOfWordsInDictionary;
    /** Custom'ный toast */
    private LearnEnglishToast toast;

    // Конструкторы
    public LearnEnglishStatistic(Activity act) {
        activity = act;

        initializeToast();
    }

    /** Метод, который возвращает количество слов в словаре */
    public int getNumberOfWordsInDictionary() {
        try {
            Dictionary dictionary = new Dictionary(activity.getApplicationContext());
            numberOfWordsInDictionary = dictionary.getNumberOfWords();
        } catch (Exception ex) {
            toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
        }


        return numberOfWordsInDictionary;
    }

    /** Метод, который иницилизирует Toast */
    private void initializeToast() {
        toast = new LearnEnglishToast(activity);
    }
}
