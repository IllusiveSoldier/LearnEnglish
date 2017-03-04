package knack.college.learnenglish;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;

import knack.college.learnenglish.fragments.DictionaryFragment;
import knack.college.learnenglish.fragments.ProfileFragment;
import knack.college.learnenglish.fragments.TaskFragment;
import knack.college.learnenglish.model.toasts.Toast;

public class LearnEnglishActivity extends AppCompatActivity {

    private static final String ACTION_BAR_TITLE = "Learn English (Alpha)";

    private TabLayout tabLayout;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_learn_english);

        toast = new Toast(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        getSupportActionBar().setElevation(0);
        Spannable title = new SpannableString(ACTION_BAR_TITLE);
        title.setSpan(
                new ForegroundColorSpan(
                        getResources().getColor(R.color.black)
                ),
                0,
                title.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        getSupportActionBar().setTitle(title);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        setupTabIcons();
    }

    private void setupTabIcons() {
        try {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_sort_by_alpha_black_24dp);
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_event_note_black_24dp);
            tabLayout.getTabAt(2).setIcon(R.mipmap.ic_settings_black_24dp);
        } catch (Exception ex) {
            toast.show(ex);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DictionaryFragment(), getString(R.string.title_dictionary));
        adapter.addFrag(new TaskFragment(), getString(R.string.title_tasks));
        adapter.addFrag(new ProfileFragment(), getString(R.string.title_profile));

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
