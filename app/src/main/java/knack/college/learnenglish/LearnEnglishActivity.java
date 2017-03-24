package knack.college.learnenglish;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import knack.college.learnenglish.fragments.DictionaryFragment;
import knack.college.learnenglish.fragments.TaskFragment;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.statistic.DictionaryTrainingStatistic;
import knack.college.learnenglish.model.toasts.ToastWrapper;

public class LearnEnglishActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ToastWrapper toast;

    // Dictionary wrapper
    private Dictionary dictionary;
    // Dictionary training statistic
    private DictionaryTrainingStatistic dictionaryTrainingStatistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_english);

        initializeToast();
        initializeControls();
        initializeDictionary();
        initializeDictionaryTrainingStatistic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeAllData:
                try {
                    dictionary.delete();
                    dictionaryTrainingStatistic.delete();
                } catch (Exception e) {
                    toast.show(
                            getResources().getString(R.string.error_message_failed_remove_all_data)
                    );
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    getApplicationContext(),
                    getResources().getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeControls() {
        try {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            setSupportActionBar(toolbar);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);


            setupTabIcons();
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string.error_message_failed_initialize_controls)
            );
        }
    }

    private void initializeDictionary() {
        try {
            dictionary = new Dictionary(getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string.error_message_failed_initialize_dictionary)
            );
        }
    }

    private void initializeDictionaryTrainingStatistic() {
        try {
            dictionaryTrainingStatistic = new DictionaryTrainingStatistic(getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string
                            .error_message_failed_initialize_dictionary_training_statistic)
            );
        }
    }

    private void setupTabIcons() {
        try {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_sort_by_alpha_black_24dp);
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_event_note_black_24dp);
        } catch (Exception ex) {
            toast.show(ex.toString());
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DictionaryFragment(), getString(R.string.title_dictionary));
        adapter.addFrag(new TaskFragment(), getString(R.string.title_trainings));

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private final ArrayList<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onPause () {
        super.onPause();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}
