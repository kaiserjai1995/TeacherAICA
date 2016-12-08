package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.SolveProblemItems;

/**
 * Created by jairus on 12/8/16.
 */

public class SolveProblemRVAdapter extends RecyclerView.Adapter<SolveProblemRVAdapter.SolveProblemViewHolder>  {
    public static class SolveProblemViewHolder extends RecyclerView.ViewHolder {

        CardView cv_solve_problem;
        TextView solve_problem_problem_title;
        TextView solve_problem_equation;
        ImageView solve_problem_question_image;

        public SolveProblemViewHolder(View itemView) {
            super(itemView);
            cv_solve_problem = (CardView)itemView.findViewById(R.id.cv_solve_problem);
            solve_problem_problem_title = (TextView)itemView.findViewById(R.id.solve_problem_problem_title);
            solve_problem_equation = (TextView)itemView.findViewById(R.id.solve_problem_equation);
            solve_problem_question_image = (ImageView)itemView.findViewById(R.id.solve_problem_question_image);
        }
    }

    List<SolveProblemItems> solve_problem_items;
    private Context mContext;

    public SolveProblemRVAdapter(Context context, List<SolveProblemItems> solve_problem_items){
        this.solve_problem_items = solve_problem_items;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SolveProblemRVAdapter.SolveProblemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_solve_problem, viewGroup, false);
        SolveProblemRVAdapter.SolveProblemViewHolder ltvh = new SolveProblemRVAdapter.SolveProblemViewHolder(v);
        return ltvh;
    }

    @Override
    public void onBindViewHolder(final SolveProblemRVAdapter.SolveProblemViewHolder solveProblemItemsViewHolder, final int i) {
        solveProblemItemsViewHolder.solve_problem_problem_title.setText(solve_problem_items.get(i).solve_problem_problem_title);
        solveProblemItemsViewHolder.solve_problem_equation.setText(solve_problem_items.get(i).solve_problem_equation);
        solveProblemItemsViewHolder.solve_problem_question_image.setImageResource(solve_problem_items.get(i).solve_problem_question_image);

    }

    @Override
    public int getItemCount() {
        return solve_problem_items.size();
    }
}
