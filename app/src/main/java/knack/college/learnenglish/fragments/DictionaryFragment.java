package knack.college.learnenglish.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import knack.college.learnenglish.R;
import knack.college.learnenglish.dialogs.AddWordToDatabase;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG;


public class DictionaryFragment extends Fragment {

    FloatingActionButton addToDatabaseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dectionary, container, false);

        addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddWordToDatabase();
                dialogFragment.show(getActivity().getFragmentManager(),
                        UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG);
            }
        });


        return view;
    }
}
