package knack.college.learnenglish.fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import knack.college.learnenglish.R;
import knack.college.learnenglish.TaskActivity;
import knack.college.learnenglish.model.Task;
import knack.college.learnenglish.model.TaskGenerator;
import knack.college.learnenglish.model.toasts.ToastWrapper;

import static knack.college.learnenglish.model.Constant.FRAGMENT_CODE;


public class TaskFragment extends Fragment {
    RecyclerView taskRecyclerView;
    SwipeRefreshLayout taskSwipeRefreshLayout;

    ToastWrapper toastWrapper;
    LearnEnglishAdapter learnEnglishAdapter;
    TaskGenerator taskGenerator;

    ArrayList<Task> tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        toastWrapper = new ToastWrapper(getActivity());
        taskGenerator = new TaskGenerator(getActivity());
        try {
            tasks = taskGenerator.getActualTask();
        } catch (Exception ex) {
            toastWrapper.show(ex.toString());
        }

        taskRecyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        taskRecyclerView.setHasFixedSize(true);

        learnEnglishAdapter = new LearnEnglishAdapter();
        taskRecyclerView.setAdapter(learnEnglishAdapter);

        taskSwipeRefreshLayout = (SwipeRefreshLayout)
                view.findViewById(R.id.taskSwipeRefreshLayout);
        taskSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskSwipeRefreshLayout.setRefreshing(true);

                try {
                    tasks = taskGenerator.getActualTask();
                    if (tasks.size() == learnEnglishAdapter.getItemCount()) {
                        learnEnglishAdapter.notifyDataSetChanged();
                    } else {
                        learnEnglishAdapter = new LearnEnglishAdapter();
                        taskRecyclerView.setAdapter(learnEnglishAdapter);
                    }
                } catch (Exception ex) {
                    toastWrapper.show(ex.toString());
                    taskSwipeRefreshLayout.setRefreshing(false);
                }

                taskSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private class LearnEnglishHolder extends RecyclerView.ViewHolder {
        CardView taskCardView;
        ImageView taskItemImageView;
        TextView taskName;

        LearnEnglishHolder(View itemView) {
            super(itemView);

            taskCardView = (CardView) itemView.findViewById(R.id.taskCardView);
            taskItemImageView = (ImageView) itemView.findViewById(R.id.taskItemImageView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            taskName.setTypeface(
                    Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Roboto/Roboto-Light.ttf")
            );
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
            holder.taskName.setText(tasks.get(position).getTitle());

            final int id = position;

            holder.taskCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =
                            new Intent(getActivity().getApplicationContext(), TaskActivity.class);
                    intent.putExtra(FRAGMENT_CODE, tasks.get(id).getName());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tasks != null ? tasks.size() : 0;
        }
    }
}
