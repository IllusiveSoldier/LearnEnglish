package knack.college.learnenglish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.LearnEnglishToast;


public class SettingsFragment extends Fragment {

    Button deleteDictionaryButton;

    private LearnEnglishToast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        toast = new LearnEnglishToast(getActivity());

        deleteDictionaryButton = (Button) view.findViewById(R.id.deleteDictionaryButton);
        deleteDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dictionary dictionary = new Dictionary(getActivity().getApplicationContext());
                    dictionary.clear();
                } catch (Exception ex) {
                    toast.show(ex.getMessage(), R.mipmap.ic_sentiment_very_dissatisfied_black_24dp);
                }
            }
        });


        return view;
    }

}
