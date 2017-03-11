package knack.college.learnenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import knack.college.learnenglish.fragments.TrainingFFragment;
import knack.college.learnenglish.fragments.TrainingSFragment;

import static knack.college.learnenglish.model.Constant.BRAINSTORM_TASK_TITLE;
import static knack.college.learnenglish.model.Constant.FRAGMENT_CODE;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Fragment code
        String fragmentCode;

        // Get info from intent
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(FRAGMENT_CODE) != null) {
            fragmentCode = intent.getStringExtra(FRAGMENT_CODE);

            // Select show fragment
            if (fragmentCode.equals(BRAINSTORM_TASK_TITLE)) {
                Random random = new Random();
                final int randomItemId = random.nextInt(2);

                if (randomItemId == 0) {
                    Fragment trainingFFragment = new TrainingFFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(
                                    R.id.activity_task,
                                    trainingFFragment
                            ).commit();
                } else if (randomItemId == 1) {
                    Fragment trainingSFragment = new TrainingSFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(
                                    R.id.activity_task,
                                    trainingSFragment
                            ).commit();
                }
            }
        }
    }
}
