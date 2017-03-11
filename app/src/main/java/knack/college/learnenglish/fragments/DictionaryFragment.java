package knack.college.learnenglish.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import knack.college.learnenglish.R;
import knack.college.learnenglish.dialogs.AddWordToDictionaryDialog;
import knack.college.learnenglish.dialogs.DeleteWordFromDictionaryDialog;
import knack.college.learnenglish.model.Dictionary;
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.WordFromDictionary;
import knack.college.learnenglish.model.toasts.ToastWrapper;


public class DictionaryFragment extends Fragment {
    private static final int REQUEST_SELECTED = 1;
    private static final String UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG =
            "addToDictionaryDialog";
    private static final String UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG =
            "deleteFromDictionaryDialog";
    private static final String FAILED_OUTPUT_WORDS = "Не получилось вывести слова";
    private static final String FAILED_UNDO_ACTION = "Не получилось отменить действие";
    private static final String FAILED_DELETE_WORD = "Не получилось удалить слово";
    private static final String FAILED_VIEW_DELETE_DIALOG = "Не получилось показать диалог удаления";

    // Controls
    private FloatingActionButton addToDatabaseButton;
    private RecyclerView dictionaryRecyclerView;
    private SwipeRefreshLayout dictionarySwipeRefreshLayout;
    private View view;
    // Toast
    private ToastWrapper toast;

    private Snackbar snackbar;
    private RandomColor color;
    private LearnEnglishAdapter learnEnglishAdapter;
    private Dictionary dictionary;
    private ArrayList<WordFromDictionary> words;

    private int itemId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        initializeToast();
        initializeControls();
        initializeDictionary();
        initializeWordsFromDictionary();

        return view;
    }

    private void initializeToast() {
        try {
            toast = new ToastWrapper(getActivity().getApplicationContext());
        } catch (Exception e) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    getResources().getString(R.string.error_message_failed_initialize_toast),
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void initializeControls() {
        try {
            color = new RandomColor();

            addToDatabaseButton = (FloatingActionButton) view.findViewById(R.id.addToDatabaseButton);
            addToDatabaseButton.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(color.getRandomColor()))
            );
            addToDatabaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment dialogFragment = new AddWordToDictionaryDialog();
                    dialogFragment.show(getActivity().getSupportFragmentManager(),
                            UNIQUE_NAME_ADD_WORD_TO_DICTIONARY_DIALOG);
                }
            });

            dictionaryRecyclerView = (RecyclerView) view.findViewById(R.id.dictionaryRecyclerView);
            dictionaryRecyclerView.setLayoutManager(
                    new LinearLayoutManager(getActivity().getApplicationContext())
            );
            dictionaryRecyclerView.setHasFixedSize(true);

            learnEnglishAdapter = new LearnEnglishAdapter();

            dictionaryRecyclerView.setAdapter(learnEnglishAdapter);

            dictionarySwipeRefreshLayout =
                    (SwipeRefreshLayout) view.findViewById(R.id.dictionarySwipeRefreshLayout);
            dictionarySwipeRefreshLayout
                    .setColorSchemeColors(Color.parseColor(color.getRandomColor()));
            dictionarySwipeRefreshLayout
                    .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                          @Override
                          public void onRefresh() {
                              refreshWords();
                          }
                      }
                    );
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_initialize_controls));
        }
    }

    private void initializeDictionary() {
        try {
            dictionary = new Dictionary(getActivity().getApplicationContext());
        } catch (Exception e) {
            toast.show(
                    getResources().getString(R.string.error_message_failed_initialize_dictionary)
            );
        }
    }

    private void initializeWordsFromDictionary() {
        try {
            words = (ArrayList<WordFromDictionary>) dictionary.getAllWordsList();
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_get_words));
        }
    }

    private void refreshWords() {
        try {
            dictionarySwipeRefreshLayout.setRefreshing(true);
            words = (ArrayList<WordFromDictionary>) dictionary.getAllWordsList();
            if (words.size() == learnEnglishAdapter.getItemCount()) {
                learnEnglishAdapter.notifyDataSetChanged();
            } else {
                learnEnglishAdapter = new LearnEnglishAdapter();
                dictionaryRecyclerView.setAdapter(learnEnglishAdapter);
            }
            dictionarySwipeRefreshLayout.setRefreshing(false);
        } catch (Exception e) {
            toast.show(getResources().getString(R.string.error_message_failed_update_data));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECTED:
                    int selected =
                            data.getIntExtra(DeleteWordFromDictionaryDialog.TAG_SELECTED, -1);
                    if (selected == 1) {
                        try {
                            final WordFromDictionary wordFromDictionary =
                                    words.get(itemId);

                            dictionary.deleteWord(words.get(itemId).getGuid());

                            snackbar = Snackbar
                                    .make(
                                        view,
                                        getResources().getString(R.string.hint_word_is_remove),
                                        Snackbar.LENGTH_LONG
                                    )
                                    .setAction(
                                        getResources().getString(R.string.hint_undo),
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                try {
                                                    dictionary.restoreWord(wordFromDictionary);
                                                } catch (Exception ex) {
                                                    toast.show(FAILED_UNDO_ACTION);
                                                }
                                            }
                                        }
                                    );
                            if (Build.VERSION.SDK_INT >= 23) {
                                snackbar.setActionTextColor(ContextCompat.getColor(getActivity()
                                        .getApplicationContext(), R.color.bright_green)
                                );
                            } else {
                                snackbar.setActionTextColor(getResources().getColor(
                                        R.color.bright_green)
                                );
                            }
                            snackbar.show();
                        } catch (Exception ex) {
                            toast.show(FAILED_DELETE_WORD);
                        }
                    }
                    break;
            }
        }
    }

    private class LearnEnglishHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener {
        CardView dictionaryCardView;
        TextView dictionaryEnglishWordTextView;
        TextView dictionaryTranslateWordTextView;
        ImageView learnEnglishWordItemImageView;

        LearnEnglishHolder(View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(this);
            dictionaryCardView = (CardView) itemView.findViewById(R.id.dictionaryCardView);

            dictionaryEnglishWordTextView =
                    (TextView) itemView.findViewById(R.id.dictionaryEnglishWordTextView);
            dictionaryEnglishWordTextView.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf")
            );

            dictionaryTranslateWordTextView =
                    (TextView) itemView.findViewById(R.id.dictionaryTranslateWordTextView);
            dictionaryTranslateWordTextView.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf")
            );

            learnEnglishWordItemImageView =
                    (ImageView) itemView.findViewById(R.id.learnEnglishWordItemImageView);
        }

        @Override
        public boolean onLongClick(View v) {
            try {
                itemId = dictionaryRecyclerView.getChildLayoutPosition(v);
                openWeightPicker();
            } catch (Exception ex) {
                toast.show(FAILED_VIEW_DELETE_DIALOG);
            }

            return false;
        }
    }

    private class LearnEnglishAdapter extends RecyclerView.Adapter<LearnEnglishHolder> {

        @Override
        public LearnEnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LearnEnglishHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.learn_english_word_item, parent, false));
        }

        @Override
        public void onBindViewHolder(LearnEnglishHolder holder, int position) {
            holder.dictionaryEnglishWordTextView.setText(
                    words.get(position).getEnglishWord()
            );
            holder.dictionaryTranslateWordTextView.setText(
                    words.get(position).getTranslateWord()
            );
            holder.learnEnglishWordItemImageView.setBackgroundColor(
                    Color.parseColor(color.getRandomColor())
            );
        }

        @Override
        public int getItemCount() {
            return words.size();
        }
    }

    public void openWeightPicker() {
        DialogFragment fragment = new DeleteWordFromDictionaryDialog();
        fragment.setTargetFragment(this, REQUEST_SELECTED);
        fragment.show(getFragmentManager(), UNIQUE_NAME_DELETE_WORD_FROM_DICTIONARY_DIALOG);
    }
}
