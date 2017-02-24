package knack.college.learnenglish.model;

import android.app.Activity;

import java.util.ArrayList;

import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY_TRAINING_TITLE;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE;

/** Класс, генерирующий задания */
public class TaskGenerator {
    private Activity activity;
    private Dictionary dictionary;
    private Toast toast;

    public TaskGenerator(Activity act) {
        activity = act;

        initializeData();
    }

    private void initializeData() {
        dictionary = new Dictionary(activity.getApplicationContext());
        toast = new Toast(activity);
    }

    public ArrayList<Task> getActualTask() throws Exception {
        ArrayList<Task> tasks = new ArrayList<Task>();

        if (dictionary != null && dictionary.getNumberOfWords() > 0) {
            Task allWordsTrainingTask = new Task();
            allWordsTrainingTask.setName(ALL_WORDS_FROM_DICTIONARY);
            allWordsTrainingTask.setTitle(ALL_WORDS_FROM_DICTIONARY_TRAINING_TITLE);
            tasks.add(allWordsTrainingTask);

            if (dictionary.getForgottenWords().size() > 0) {
                Task forgottenWordsTrainingTask = new Task();
                forgottenWordsTrainingTask.setName(FORGOTTEN_WORDS_FROM_DICTIONARY);
                forgottenWordsTrainingTask.setTitle(FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE);
                tasks.add(forgottenWordsTrainingTask);
            }
        }

        return tasks;
    }
}
