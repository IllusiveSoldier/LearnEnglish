package knack.college.learnenglish.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import knack.college.learnenglish.R;
import knack.college.learnenglish.adapters.DictionaryRecyclerViewAdapter;
import knack.college.learnenglish.dialogs.AddWordToDatabase;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.WordFromDictionary;

import static knack.college.learnenglish.model.Constant.Dialog.UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG;
import static knack.college.learnenglish.model.Constant.KeysForDebug.ERROR_KEY_FOR_DEBUG;


public class DictionaryFragment extends Fragment {

    FloatingActionButton addToDatabaseButton;
    RecyclerView dictionaryRecyclerView;
    SwipeRefreshLayout dictionarySwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
        addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new AddWordToDatabase();
                dialogFragment.show(getActivity().getFragmentManager(),
                        UNIQUE_NAME_ADD_WORD_TO_DATABASE_DIALOG);
            }
        });

        dictionaryRecyclerView = (RecyclerView) view.findViewById(R.id.dictionaryRecyclerView);

        try {
            dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                    .getApplicationContext()));
            dictionaryRecyclerView.setHasFixedSize(true);

            initializeAdapter (
                    (ArrayList<WordFromDictionary>) new Dictionary(getActivity()
                            .getApplicationContext()).getAllWordsList()
            );
        } catch (Exception ex) {
            Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
        }

        dictionarySwipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.dictionarySwipeRefreshLayout);
        dictionarySwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        dictionarySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dictionarySwipeRefreshLayout.setRefreshing(true);

                try {
                    initializeAdapter (
                            (ArrayList<WordFromDictionary>) new Dictionary(getActivity()
                                    .getApplicationContext()).getAllWordsList());
                } catch (Exception ex) {
                    Log.d(ERROR_KEY_FOR_DEBUG, ex.getMessage());
                }

                dictionarySwipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }



    private void initializeAdapter(ArrayList<WordFromDictionary> words) throws Exception {
        dictionaryRecyclerView.setAdapter(new DictionaryRecyclerViewAdapter(words));
    }
}
