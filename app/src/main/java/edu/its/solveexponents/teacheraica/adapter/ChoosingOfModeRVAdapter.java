package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.content.MainFragment;
import edu.its.solveexponents.teacheraica.content.SolveProblemActivity;
import edu.its.solveexponents.teacheraica.model.ModeInput;
import edu.its.solveexponents.teacheraica.model.TeacherAICADB;

/**
 * Created by jairus on 8/2/16.
 */

public class ChoosingOfModeRVAdapter extends RecyclerView.Adapter<ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder>  {
    public static class ChoosingOfModeViewHolder extends RecyclerView.ViewHolder {

        CardView cv_choosing_of_mode;
        TextView mode_title;

        public ChoosingOfModeViewHolder(View itemView) {
            super(itemView);
            cv_choosing_of_mode = (CardView)itemView.findViewById(R.id.cv_choosing_of_mode);
            mode_title = (TextView)itemView.findViewById(R.id.mode_title);
        }
    }

    List<ModeInput> mode_input;
    private Context mContext;

    public static TeacherAICADB teacheraicadb;
    
    public ChoosingOfModeRVAdapter(Context context, List<ModeInput> mode_input){
        this.mode_input = mode_input;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_choosing_of_mode, viewGroup, false);
        ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder comvh = new ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder(v);
        return comvh;
    }

    @Override
    public void onBindViewHolder(final ChoosingOfModeRVAdapter.ChoosingOfModeViewHolder choosingOfModeViewHolder, final int i) {
        choosingOfModeViewHolder.mode_title.setText(mode_input.get(i).mode_title);

        if (i == 0) {
            choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_generated);
            // Replace setBackgroundResource with code below if API Level of device is below 21
//
//            choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.colorAccent);
        }
        if (i == 1) {
            choosingOfModeViewHolder.cv_choosing_of_mode.setBackgroundResource(R.drawable.bg_input);
            // Replace setBackgroundResource with code below if API Level of device is below 21
//
//            choosingOfModeViewHolder.cv_choosing_of_mode.setCardBackgroundColor(R.color.colorPrimaryDark);
        }

        choosingOfModeViewHolder.cv_choosing_of_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (mode_input.get(i).mode_title) {
                    case "Solve COMPUTER-GENERATED Problems":
                        showGeneratedProblemView();
                        break;
                    case "Solve USER-INPUT Problems":
                        showInputProblemView();
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mode_input.size();
    }

    public void showGeneratedProblemView() {

        //Place Randomize Algorithm Here

        new LovelyCustomDialog(mContext)
            .setView(R.layout.generated_mode_problem_view)
            .setTopColorRes(R.color.darkRed)
            .setTitle(R.string.generated_problem_title)
            .setIcon(R.drawable.aica)
            .setTitleGravity(1)
            .setMessageGravity(1)
            .setCancelable(false)
            .setListener(R.id.solve_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, SolveProblemActivity.class);
                    i.putExtra("generated", true);
                    i.putExtra("level", MainFragment.teacheraicadb.getCurrentLevel());
                    mContext.startActivity(i);
                }
            })
            .setListener(R.id.next_problem_btn, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Next Problem Clicked", Toast.LENGTH_SHORT).show();
                }
            })
            .setListener(R.id.cancel_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove Problem data here and randomize
                }
            })
            .show();
    }

    public void showInputProblemView() {

        new LovelyCustomDialog(mContext)
            .setView(R.layout.input_mode_problem_view)
            .setTopColorRes(R.color.darkGreen)
            .setTitle(R.string.input_problem_title)
            .setIcon(R.drawable.aica)
            .setTitleGravity(1)
            .setMessageGravity(1)
            .setCancelable(true)
            .setListener(R.id.submit_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, SolveProblemActivity.class);
                    i.putExtra("generated", false);
                    mContext.startActivity(i);
                }
            })
            .setListener(R.id.cancel_input_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            })
            .show();

    }
}
