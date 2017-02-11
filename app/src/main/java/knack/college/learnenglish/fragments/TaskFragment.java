package knack.college.learnenglish.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import knack.college.learnenglish.DictionaryTrainingActivity;
import knack.college.learnenglish.R;
import knack.college.learnenglish.model.toasts.Toast;


public class TaskFragment extends Fragment {

    Button beginDictionaryTrainingButton;

    private Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        toast = new Toast(getActivity());

        beginDictionaryTrainingButton = (Button) view.findViewById(R.id.beginDictionaryTrainingButton);
        beginDictionaryTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        DictionaryTrainingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
