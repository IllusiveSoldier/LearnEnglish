package knack.college.learnenglish.fragments;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import knack.college.learnenglish.R;
import knack.college.learnenglish.dialogs.AssignmentDialog;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.toasts.Toast;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ASSIGNMENT_DIALOG;


public class ProfileFragment extends Fragment {

    FloatingActionButton deleteDictionaryButton;
    TextView numberOfWordsValue;

    private Toast toast;
    RandomColor color;
    Dictionary dictionary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        toast = new Toast(getActivity());
        color = new RandomColor();
        dictionary = new Dictionary(getActivity().getApplicationContext());

        deleteDictionaryButton = (FloatingActionButton) view
                .findViewById(R.id.deleteDictionaryButton);
        deleteDictionaryButton.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor(color.getRandomColor())));
        deleteDictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogFragment dialogFragment = new AssignmentDialog();
                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                            UNIQUE_NAME_ASSIGNMENT_DIALOG);
                } catch (Exception ex) {
                    toast.show(ex);
                }
            }
        });

        numberOfWordsValue = (TextView) view.findViewById(R.id.numberOfWordsValue);
        try {
            numberOfWordsValue.setText(String.valueOf(dictionary.getNumberOfWords()));
        } catch (Exception ex) {
            toast.show(ex);
        }


        return view;
    }
}
