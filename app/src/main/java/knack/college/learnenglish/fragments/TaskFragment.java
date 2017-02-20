package knack.college.learnenglish.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import knack.college.learnenglish.DictionaryTrainingActivity;
import knack.college.learnenglish.R;
import knack.college.learnenglish.model.RandomColor;
import knack.college.learnenglish.model.toasts.Toast;


public class TaskFragment extends Fragment {
    RecyclerView taskRecyclerView;

    Toast toast;
    LearnEnglishAdapter learnEnglishAdapter;
    private RandomColor color = new RandomColor();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        toast = new Toast(getActivity());

        taskRecyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        taskRecyclerView.setHasFixedSize(true);

        learnEnglishAdapter = new LearnEnglishAdapter();
        taskRecyclerView.setAdapter(learnEnglishAdapter);

        return view;
    }

    private class LearnEnglishHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        CardView taskCardView;
        ImageView taskItemImageView;
        TextView taskName;

        LearnEnglishHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            taskCardView = (CardView) itemView.findViewById(R.id.taskCardView);
            taskItemImageView = (ImageView) itemView.findViewById(R.id.taskItemImageView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity().getApplicationContext(),
                        DictionaryTrainingActivity.class);
                startActivity(intent);
        }
    }

    private class LearnEnglishAdapter extends RecyclerView.Adapter<LearnEnglishHolder> {

        @Override
        public LearnEnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new LearnEnglishHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.task_item, parent, false));
        }

        @Override
        public void onBindViewHolder(LearnEnglishHolder holder, int position) {
            holder.taskName.setText("Прогон по словарю");
            holder.taskItemImageView.setBackgroundColor(Color.parseColor(color.getRandomColor()));
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }
}
