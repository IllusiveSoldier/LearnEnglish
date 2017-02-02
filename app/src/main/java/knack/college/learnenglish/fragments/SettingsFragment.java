package knack.college.learnenglish.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import knack.college.learnenglish.R;
import knack.college.learnenglish.model.Dictionary;

import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;


public class SettingsFragment extends Fragment {

    Button deleteDictionaryButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);

        deleteDictionaryButton = (Button) view.findViewById(R.id.deleteDictionaryButton);
        deleteDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dictionary dictionary = new Dictionary(getActivity().getApplicationContext());
                    dictionary.clear();
                } catch (Exception ex) {
                    Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
                }
            }
        });


        return view;
    }

}
