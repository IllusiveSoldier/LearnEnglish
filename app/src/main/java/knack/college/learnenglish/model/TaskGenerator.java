package knack.college.learnenglish.model;

import android.app.Activity;
import knack.college.learnenglish.model.toasts.Toast;

import java.util.ArrayList;

import static knack.college.learnenglish.model.Constant.*;

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

            Task allWordsTrainingTaskSecondEdition = new Task();
            allWordsTrainingTaskSecondEdition.setName(ALL_WORDS_FROM_DICTIONARY_SECOND_EDITION);
            allWordsTrainingTaskSecondEdition.setTitle(ALL_WORDS_FROM_DICTIONARY_TRAINING_TITLE_SECOND_EDITION);
            tasks.add(allWordsTrainingTaskSecondEdition);

            if (dictionary.getForgottenWords().size() > 0) {
                Task forgottenWordsTrainingTask = new Task();
                forgottenWordsTrainingTask.setName(FORGOTTEN_WORDS_FROM_DICTIONARY);
                forgottenWordsTrainingTask.setTitle(FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE);
                tasks.add(forgottenWordsTrainingTask);

                Task forgottenWordsTrainingTaskSecondEdition = new Task();
                forgottenWordsTrainingTaskSecondEdition.setName(FORGOTTEN_WORDS_FROM_DICTIONARY_SECOND_EDITION);
                forgottenWordsTrainingTaskSecondEdition.setTitle(FORGOTTEN_WORDS_FROM_DICTIONARY_TITLE_SECOND_EDITION);
                tasks.add(forgottenWordsTrainingTaskSecondEdition);
            }
        }

        return tasks;
    }
}
