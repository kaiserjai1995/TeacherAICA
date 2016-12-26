package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyCustomDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

import java.util.List;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.algo.Randomizer;
import edu.its.solveexponents.teacheraica.content.MainFragment;
import edu.its.solveexponents.teacheraica.content.SolveProblemActivity;
import edu.its.solveexponents.teacheraica.model.ModeInput;
import io.github.kexanie.library.MathView;

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

    private static class StepListener extends AbstractEvalStepListener {
        /**
         * Listens to the evaluation step in the evaluation engine.
         */
        @Override
        public void add(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            System.out.println("Depth " + recursionDepth + " Iteration " + iterationCounter + ": " + inputExpr.toString() + " ==> "
                    + resultExpr.toString() + " ==> " + hint);
        }
    }

    List<ModeInput> mode_input;
    private Context mContext;

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

                        int level = MainFragment.teacheraicadb.getCurrentLevel();
                        int sublevel = MainFragment.teacheraicadb.getCurrentSublevel(level);

                        String equation = Randomizer.getRandomEquation(level, sublevel);

                        showGeneratedProblemView(equation);
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

    public void showGeneratedProblemView(final String equation) {

        //Randomize Algorithm

        new LovelyCustomDialog(mContext)
            .setView(R.layout.generated_mode_problem_view)
            .setTopColorRes(R.color.darkRed)
            .setTitle(R.string.generated_problem_title)
            .setIcon(R.drawable.aica)
            .setTitleGravity(1)
            .setMessageGravity(1)
            .setCancelable(false)
                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                    @Override
                    public void configureView(View v) {
                        MathView generated_problem = (MathView)v.findViewById(R.id.generated_problem);

                        generated_problem.setText("$$" + equation + "$$");
                    }
                })
            .setListener(R.id.solve_problem_btn, true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, SolveProblemActivity.class);

                    ExprEvaluator util = new ExprEvaluator();
                    EvalEngine engine = util.getEvalEngine();
                    engine.setStepListener(new StepListener());
                    IExpr result = util.evaluate(equation);
                    System.out.println("Result: " + result.toString());

                    i.putExtra("generated", true);
                    i.putExtra("level", MainFragment.teacheraicadb.getCurrentLevel());
                    i.putExtra("sublevel", MainFragment.teacheraicadb.getCurrentSublevel(MainFragment.teacheraicadb.getCurrentLevel()));
                    i.putExtra("equation", equation);
                    i.putExtra("result", result.toString());
                    mContext.startActivity(i);
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

        new LovelyTextInputDialog(mContext)
                .setTopColorRes(R.color.darkGreen)
                .setTitle(R.string.input_problem_title)
                .setTitleGravity(1)
                .setIcon(R.drawable.aica)
                .setHint("Enter VALID equation input")
                .setCancelable(true)
                .setInputFilter("Invalid input! Please check and try again.", new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String equation) {
                        Boolean match;

                        try {
                            match = true;

                            if (equation.matches("^-?\\d+$")) {
                                match = false;
                            }
                            if (equation.matches("(\\w+)")) {
                                match = false;
                            }

                            ExprEvaluator util = new ExprEvaluator();
                            EvalEngine engine = util.getEvalEngine();
                            engine.setStepListener(new StepListener());
                            IExpr result = util.evaluate(equation);
                            System.out.println("Result: " + result.toString());

                            if (result.isAST()) {
                                match = true;
                            }
                            if (result.isIndeterminate()) {
                                match = false;
                            }

                            // disable trace mode if the step listener isn't necessary anymore
                            engine.setTraceMode(false);

                        } catch (SyntaxError e) {
                            // catch Symja parser errors here
                            System.out.println(e.getMessage());
                            match = false;
                        } catch (MathException me) {
                            // catch Symja math errors here
                            System.out.println(me.getMessage());
                            match = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                            match = false;
                        }
                        return match;
                    }
                })
                .setConfirmButton("VALIDATE EQUATION", new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(final String equation) {

                        new LovelyCustomDialog(mContext)
                                .setIcon(R.drawable.aica)
                                .setTitle("SOLVE THIS EQUATION?")
                                .setView(R.layout.input_mode_problem_view)
                                .setTitleGravity(1)
                                .setTopColorRes(R.color.darkGreen)
                                .setCancelable(false)
                                .configureView(new LovelyCustomDialog.ViewConfigurator() {
                                    @Override
                                    public void configureView(View v) {
                                        MathView input_mode_problem = (MathView)v.findViewById(R.id.input_mode_problem);

                                        input_mode_problem.setText("$$" + equation + "$$");
                                    }
                                })
                                .setListener(R.id.submit_problem_btn, true, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(mContext, SolveProblemActivity.class);

                                        ExprEvaluator util = new ExprEvaluator();
                                        EvalEngine engine = util.getEvalEngine();
                                        engine.setStepListener(new StepListener());
                                        IExpr result = util.evaluate(equation);
                                        System.out.println("Result: " + result.toString());

                                        i.putExtra("generated", false);
                                        i.putExtra("equation", equation);
                                        i.putExtra("result", result.toString());

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
                })
                .show();
    }
}
