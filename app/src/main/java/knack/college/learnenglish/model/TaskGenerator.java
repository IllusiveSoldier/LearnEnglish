package knack.college.learnenglish.model;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.Constant.BRAINSTORM_TASK_TITLE;

/** Класс, генерирующий задания */
public class TaskGenerator {
    private Activity activity;
    private ToastWrapper toast;
    private Dictionary dictionary;

    public TaskGenerator(Activity act) {
        activity = act;

        initializeToast();
        initializeDictionary();
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

    private void initializeToast() {
        try {
            toast = new ToastWrapper(activity.getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    activity.getApplicationContext(),
                    activity.getResources()
                            .getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeDictionary() {
        try {
            dictionary = new Dictionary(activity.getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    activity.getResources()
                            .getString(R.string.error_message_failed_initialize_dictionary)
            );
        }
    }
}
