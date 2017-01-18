package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.yarolegovich.lovelydialog.LovelyCustomDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.Problem;
import io.github.kexanie.library.MathView;

import static io.github.kexanie.library.R.id.KaTeX;
import static java.lang.Math.round;

/**
 * Created by jairus on 7/6/16.
 */

    //TODO: Display details regarding student's performance on Overall, Generated, and Custom

    //TODO: Add Hints Used Details to View History Fragment

public class ViewHistoryFragment extends Fragment {

    ArrayList<Problem> problems, solvedProblems, unsolvedProblems, generatedProblems, customProblems;
    Typeface font;

    public ViewHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_view_history, container, false);

        problems = LoginActivity.teacheraicadb.getProblems();
        solvedProblems = LoginActivity.teacheraicadb.getSolvedProblems();
        unsolvedProblems = LoginActivity.teacheraicadb.getUnsolvedProblems();
        generatedProblems = LoginActivity.teacheraicadb.getGeneratedProblems();
        customProblems = LoginActivity.teacheraicadb.getCustomProblems();

        LinearLayout no_problems_view = (LinearLayout) rootView.findViewById(R.id.no_problems_view);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");

        final LinearLayout view_history_layout = (LinearLayout) rootView.findViewById(R.id.view_history_layout);
        view_history_layout.setOrientation(LinearLayout.VERTICAL);

        FloatingActionButton all_questions = (FloatingActionButton) rootView.findViewById(R.id.all_questions);
        all_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LovelyCustomDialog(getContext())
                        .setCancelable(true)
                        .setView(R.layout.history_performance)
                        .configureView(new LovelyCustomDialog.ViewConfigurator() {
                            @Override
                            public void configureView(View v) {
                                TextView total_questions = (TextView) v.findViewById(R.id.total_questions);
                                total_questions.setTypeface(font, Typeface.BOLD);
                                total_questions.setText("" + problems.size());

                                TextView total_solved_questions = (TextView) v.findViewById(R.id.total_solved_questions);
                                total_solved_questions.setTypeface(font, Typeface.BOLD);
                                total_solved_questions.setText("" + solvedProblems.size());

                                TextView total_unsolved_questions = (TextView) v.findViewById(R.id.total_unsolved_questions);
                                total_unsolved_questions.setTypeface(font, Typeface.BOLD);
                                total_unsolved_questions.setText("" + unsolvedProblems.size());

                                TextView efficiency_solving_questions = (TextView) v.findViewById(R.id.efficiency_solving_questions);
                                efficiency_solving_questions.setTypeface(font, Typeface.BOLD);
                                double result_init = (double)solvedProblems.size() / (double)problems.size();
                                double result_tent = result_init * 50;
                                double result = round(result_tent + 50);
                                efficiency_solving_questions.setText("" + result + "%");

                                TextView total_generated_questions = (TextView) v.findViewById(R.id.total_generated_questions);
                                total_generated_questions.setTypeface(font, Typeface.BOLD);
                                total_generated_questions.setText("" + generatedProblems.size());

                                TextView total_custom_questions = (TextView) v.findViewById(R.id.total_custom_questions);
                                total_custom_questions.setTypeface(font, Typeface.BOLD);
                                total_custom_questions.setText("" + customProblems.size());
                            }
                        })
                        .setTitle("Summary of Performance")
                        .setTopColorRes(R.color.teal)
                        .setIcon(R.drawable.aica)
                        .setTitleGravity(1)
                        .show();
            }
        });


        if (problems.size() != 0) {
            no_problems_view.setVisibility(View.GONE);

            for (int i = 0; i < problems.size(); i++) {
                Problem problem = problems.get(i);

                //Layout - Title

                LinearLayout history_layout = new LinearLayout(rootView.getContext());
                LinearLayout.LayoutParams history_layout_layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                history_layout_layoutParams.setMargins(10, 10, 10, 10);
                history_layout.setLayoutParams(history_layout_layoutParams);
                history_layout.setOrientation(LinearLayout.VERTICAL);

                final RelativeLayout layout_row_title = new RelativeLayout(rootView.getContext());
                RelativeLayout.LayoutParams layout_row_title_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout_row_title_layoutParams.setMargins(10, 10, 10, 10);
                layout_row_title.setLayoutParams(layout_row_title_layoutParams);
                layout_row_title.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                layout_row_title.setPadding(30, 30, 30, 0);
                layout_row_title.setBackgroundResource(R.drawable.card_background);

                ImageView problem_solved_image_in = new ImageView(rootView.getContext());
                RelativeLayout.LayoutParams problem_solved_image_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                problem_solved_image_in_layoutParams.setMargins(20, 10, 30, 0);
                problem_solved_image_in_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                problem_solved_image_in_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                problem_solved_image_in.setId(1000 + i);
                problem_solved_image_in.setLayoutParams(problem_solved_image_in_layoutParams);

                if (problem.getStatus().equals("Solved") || problem.getStatus().equals("Hinted and Solved"))
                    problem_solved_image_in.setImageResource(R.drawable.correct);
                else
                    problem_solved_image_in.setImageResource(R.drawable.incorrect);

                TextView equation_title_in = new TextView(rootView.getContext());
                equation_title_in.setId(2000 + i);
                RelativeLayout.LayoutParams equation_title_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                equation_title_in_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                equation_title_in_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image_in.getId());
                equation_title_in_layoutParams.setMargins(0, 8, 0, 1);
                equation_title_in.setLayoutParams(equation_title_in_layoutParams);
                equation_title_in.setText("Equation");
                equation_title_in.setTextSize(20);
                equation_title_in.setTypeface(font, Typeface.BOLD);

                MathView problem_text_in = new MathView(rootView.getContext(), null);
                problem_text_in.setId(3000 + i);
                RelativeLayout.LayoutParams problem_text_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                problem_text_in_layoutParams.setMargins(0, 5, 0, 0);
                problem_text_in_layoutParams.addRule(RelativeLayout.BELOW, equation_title_in.getId());
                problem_text_in_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image_in.getId());
                problem_text_in.setLayoutParams(problem_text_in_layoutParams);
                problem_text_in.setEngine(KaTeX);
                problem_text_in.config(
                        "MathJax.Hub.Config({\n" +
                                "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                                "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                                "         SVG: { linebreaks: { automatic: true } }\n" +
                                "});");
                problem_text_in.setText("\\(\\large " + problem.getProblem() + " \\)");

                TextView date_in = new TextView(rootView.getContext());
                date_in.setId(4000 + i);
                RelativeLayout.LayoutParams date_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                date_in_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                date_in_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                date_in.setLayoutParams(date_in_layoutParams);
                date_in.setText(problem.getDate());

                TextView solved_status_in = new TextView(rootView.getContext());
                solved_status_in.setId(5000 + i);
                RelativeLayout.LayoutParams solved_status_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                solved_status_in_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image_in.getId());
                solved_status_in_layoutParams.addRule(RelativeLayout.BELOW, problem_text_in.getId());
                solved_status_in_layoutParams.setMargins(0, 2, 0, 1);
                solved_status_in.setLayoutParams(solved_status_in_layoutParams);
                solved_status_in.setTextSize(15);
                solved_status_in.setTypeface(font, Typeface.BOLD);
                if (problem.getStatus().isEmpty()) {
                    solved_status_in.setText("App Crash");
                } else {
                    solved_status_in.setText(problem.getStatus());
                }

                TextView equation_type_in = new TextView(rootView.getContext());
                equation_type_in.setId(6000 + i);
                RelativeLayout.LayoutParams equation_type_in_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                equation_type_in_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image_in.getId());
                equation_type_in_layoutParams.addRule(RelativeLayout.BELOW, solved_status_in.getId());
                equation_type_in.setLayoutParams(equation_type_in_layoutParams);
                equation_type_in.setTextSize(15);
                equation_type_in.setTypeface(font);
                equation_type_in.setText(problem.getProblemType());

                //Layout to Expand

                final ExpandableLayout row_problem = new ExpandableLayout(rootView.getContext());
                ExpandableLayout.LayoutParams row_problem_layoutParams = new ExpandableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                row_problem_layoutParams.setMargins(10, 10, 10, 10);
                row_problem.setBackgroundResource(R.drawable.card_background);
                row_problem.setLayoutParams(row_problem_layoutParams);
                row_problem.setDuration(300);
                row_problem.setParallax(0.8F);
                row_problem.setInterpolator(new LinearInterpolator());

                //Layout - Content

                final RelativeLayout layout_row_problem = new RelativeLayout(rootView.getContext());
                RelativeLayout.LayoutParams layout_row_problem_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layout_row_problem_layoutParams.setMargins(10, 10, 10, 0);
                layout_row_problem.setLayoutParams(layout_row_problem_layoutParams);
                layout_row_problem.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
                layout_row_problem.setPadding(30, 30, 30, 30);
                layout_row_problem.setVisibility(View.GONE);

                TextView details_title = new TextView(rootView.getContext());
                details_title.setId(10000 + i);
                RelativeLayout.LayoutParams details_title_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                details_title_layoutParams.setMargins(20, 0, 0, 10);
                details_title.setLayoutParams(details_title_layoutParams);
                details_title.setText("Solution Details");
                details_title.setTextSize(18);
                details_title.setTypeface(font, Typeface.BOLD);

                ImageView details_image = new ImageView(rootView.getContext());
                RelativeLayout.LayoutParams details_image_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                details_image_layoutParams.setMargins(20, 3, 30, 0);
                details_image_layoutParams.addRule(RelativeLayout.BELOW, details_title.getId());
                details_image.setId(11000 + i);
                details_image.setLayoutParams(details_image_layoutParams);
                details_image.setBackgroundResource(R.drawable.math_symbols);

                TextView time_created = new TextView(rootView.getContext());
                time_created.setId(12000 + i);
                RelativeLayout.LayoutParams time_created_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                time_created_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                time_created_layoutParams.addRule(RelativeLayout.BELOW, details_title.getId());
                time_created.setLayoutParams(time_created_layoutParams);
                time_created.setTextSize(15);
                time_created.setTypeface(font, Typeface.BOLD);
                time_created.setText("Started Solving At: \t\t" + problem.getTime_created());

                TextView time_stopped = new TextView(rootView.getContext());
                time_stopped.setId(13000 + i);
                RelativeLayout.LayoutParams time_stopped_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                time_stopped_layoutParams.addRule(RelativeLayout.BELOW, time_created.getId());
                time_stopped_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                time_stopped.setLayoutParams(time_stopped_layoutParams);
                time_stopped.setTextSize(15);
                time_stopped.setTypeface(font, Typeface.BOLD);
                if (problem.getTime_stopped().isEmpty()) {
                    time_stopped.setText("Stopped Solving At: \t" + problem.getTime_created());
                } else {
                    time_stopped.setText("Stopped Solving At: \t" + problem.getTime_stopped());
                }

                TextView time_elapsed = new TextView(rootView.getContext());
                time_elapsed.setId(14000 + i);
                RelativeLayout.LayoutParams time_elapsed_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                time_elapsed_layoutParams.addRule(RelativeLayout.BELOW, time_stopped.getId());
                time_elapsed_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                time_elapsed.setLayoutParams(time_elapsed_layoutParams);
                time_elapsed.setTextSize(15);
                time_elapsed.setTypeface(font, Typeface.BOLD);
                if (problem.getTimeElapsed() == null) {
                    time_elapsed.setText("Time Elapsed: \t0 seconds");
                } else {
                    time_elapsed.setText("Time Elapsed: \t" + problem.getTimeElapsed() + " seconds");
                }

                ArrayList<String> solutionSteps = problem.getSolution();

                TextView num_solution_steps = new TextView(rootView.getContext());
                num_solution_steps.setId(15000 + i);
                RelativeLayout.LayoutParams num_solution_steps_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                num_solution_steps_layoutParams.addRule(RelativeLayout.BELOW, time_elapsed.getId());
                num_solution_steps_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                num_solution_steps.setLayoutParams(num_solution_steps_layoutParams);
                num_solution_steps.setTextSize(15);
                num_solution_steps.setTypeface(font, Typeface.BOLD);
                num_solution_steps.setText("Solution Steps Made: \t" + solutionSteps.size());

                TextView solution_steps_title = new TextView(rootView.getContext());
                solution_steps_title.setId(16000 + i);
                RelativeLayout.LayoutParams solution_steps_title_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                solution_steps_title_layoutParams.addRule(RelativeLayout.BELOW, num_solution_steps.getId());
                solution_steps_title_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                solution_steps_title_layoutParams.setMargins(0, 10, 0, 0);
                solution_steps_title.setLayoutParams(solution_steps_title_layoutParams);
                solution_steps_title.setTextSize(15);
                solution_steps_title.setTypeface(font, Typeface.BOLD);
                solution_steps_title.setText("Solution Steps:");

                TextView solution_steps = new TextView(rootView.getContext());
                solution_steps.setId(17000 + i);
                RelativeLayout.LayoutParams solution_steps_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                solution_steps_layoutParams.addRule(RelativeLayout.BELOW, solution_steps_title.getId());
                solution_steps_layoutParams.addRule(RelativeLayout.END_OF, details_image.getId());
                solution_steps.setLayoutParams(solution_steps_layoutParams);
                solution_steps.setTextSize(15);
                solution_steps.setTypeface(font, Typeface.BOLD);

                String solutionString = "";

                for (int j = 0; j < solutionSteps.size(); j++) {
                    String solutionStep = solutionSteps.get(j);
                    solutionString += "\t\tStep " + (j + 1) + ": \t" + solutionStep + "\n";
                }

                if (problem.getStatus().equals("Solved") ||
                        problem.getStatus().equals("Hinted and Solved")) {
                    solution_steps.setText(solutionString);
                } else {
                    if (solutionSteps.size() == 0) {
                        solution_steps.setText("\tNo Solutions Have Been Entered");
                    } else {
                        solution_steps.setText(solutionString + "\n--STOPPED HERE--");
                    }
                }

                TextView space = new TextView(rootView.getContext());
                RelativeLayout.LayoutParams space_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 200);
                space.setLayoutParams(space_layoutParams);

                layout_row_problem.addView(num_solution_steps);
                layout_row_problem.addView(time_elapsed);
                layout_row_problem.addView(details_image);
                layout_row_problem.addView(details_title);
                layout_row_problem.addView(solution_steps);
                layout_row_problem.addView(solution_steps_title);
                layout_row_problem.addView(time_stopped);
                layout_row_problem.addView(time_created);

                row_problem.addView(layout_row_problem);

                layout_row_title.addView(equation_type_in);
                layout_row_title.addView(space);
                layout_row_title.addView(solved_status_in);
                layout_row_title.addView(date_in);
                layout_row_title.addView(problem_text_in);
                layout_row_title.addView(problem_solved_image_in);
                layout_row_title.addView(equation_title_in);

                history_layout.addView(layout_row_title);
                history_layout.addView(row_problem);

                view_history_layout.addView(history_layout);

                history_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (row_problem.isExpanded()) {
                            row_problem.collapse();
                        } else {
                            layout_row_problem.setVisibility(View.VISIBLE);
                            row_problem.expand(true);
                        }
                    }
                });
            }
        } else if (problems.size() == 0) {
            no_problems_view.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

