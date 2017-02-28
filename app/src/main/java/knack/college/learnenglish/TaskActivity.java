package knack.college.learnenglish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import knack.college.learnenglish.fragments.DictionaryTrainingFragment;
import knack.college.learnenglish.fragments.DictionaryTrainingSecondFragment;

import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.ALL_WORDS_FROM_DICTIONARY_SECOND_EDITION;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY;
import static knack.college.learnenglish.model.Constant.FORGOTTEN_WORDS_FROM_DICTIONARY_SECOND_EDITION;
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
        if (intent != null) {
            if (intent.getStringExtra(FRAGMENT_CODE) != null) {
                fragmentCode = intent.getStringExtra(FRAGMENT_CODE);

                // Select show fragment
                if (ALL_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAGMENT_CODE, ALL_WORDS_FROM_DICTIONARY);

                    Fragment dictionaryTrainingFragment = new DictionaryTrainingFragment();
                    dictionaryTrainingFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, dictionaryTrainingFragment)
                            .commit();
                } else if (FORGOTTEN_WORDS_FROM_DICTIONARY.equals(fragmentCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAGMENT_CODE, FORGOTTEN_WORDS_FROM_DICTIONARY);

                    Fragment dictionaryTrainingFragment = new DictionaryTrainingFragment();
                    dictionaryTrainingFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, dictionaryTrainingFragment)
                            .commit();
                } else if (ALL_WORDS_FROM_DICTIONARY_SECOND_EDITION.equals(fragmentCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAGMENT_CODE, ALL_WORDS_FROM_DICTIONARY);

                    Fragment dictionaryTrainingFragmentSecond =
                            new DictionaryTrainingSecondFragment();
                    dictionaryTrainingFragmentSecond.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, dictionaryTrainingFragmentSecond)
                            .commit();
                } else if (FORGOTTEN_WORDS_FROM_DICTIONARY_SECOND_EDITION.equals(fragmentCode)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FRAGMENT_CODE, FORGOTTEN_WORDS_FROM_DICTIONARY);

                    Fragment dictionaryTrainingFragmentSecond =
                            new DictionaryTrainingSecondFragment();
                    dictionaryTrainingFragmentSecond.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_task, dictionaryTrainingFragmentSecond)
                            .commit();
                }
            }
        }
    }
}
