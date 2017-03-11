package knack.college.learnenglish.model;

import android.app.Activity;

import java.util.ArrayList;

import static knack.college.learnenglish.model.Constant.BRAINSTORM_TASK_TITLE;

/** Класс, генерирующий задания */
public class TaskGenerator {
    private Activity activity;
    private Dictionary dictionary;

    public TaskGenerator(Activity act) {
        activity = act;

        initializeData();
    }

    private void initializeData() {
        dictionary = new Dictionary(activity.getApplicationContext());
    }

    public ArrayList<Task> getActualTask() throws Exception {
        ArrayList<Task> tasks = new ArrayList<Task>();

        if (dictionary != null && dictionary.getNumberOfWords() > 0) {
            Task allWordsTrainingTask = new Task();
            allWordsTrainingTask.setName(BRAINSTORM_TASK_TITLE);
            allWordsTrainingTask.setTitle(BRAINSTORM_TASK_TITLE);
            tasks.add(allWordsTrainingTask);
        }

        return tasks;
    }
}
