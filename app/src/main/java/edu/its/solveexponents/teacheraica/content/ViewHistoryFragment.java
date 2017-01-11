package edu.its.solveexponents.teacheraica.content;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.Problem;
import io.github.kexanie.library.MathView;

import static io.github.kexanie.library.R.id.KaTeX;

/**
 * Created by jairus on 7/6/16.
 */

public class ViewHistoryFragment extends Fragment {

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
        View rootView = inflater.inflate(R.layout.fragment_view_history, container, false);

        ArrayList<Problem> problems = MainFragment.teacheraicadb.getProblems();

        LinearLayout view_history_layout = (LinearLayout) rootView.findViewById(R.id.view_history_layout);
        view_history_layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < problems.size(); i++) {
            Problem problem = problems.get(i);

            final FoldingCell row_problem = new FoldingCell(rootView.getContext());

            LinearLayout.LayoutParams row_problem_layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            row_problem_layoutParams.setMargins(10, 10, 10, 10);

            row_problem.setBackgroundResource(R.drawable.card_background);
            row_problem.setLayoutParams(row_problem_layoutParams);

            FrameLayout cell_content_view_out = new FrameLayout(rootView.getContext());
            LinearLayout.LayoutParams cell_content_view_out_layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            cell_content_view_out.setLayoutParams(cell_content_view_out_layoutParams);
//        cell_content_view_out.setVisibility(View.GONE);

            RelativeLayout layout_row_problem = new RelativeLayout(rootView.getContext());
            LinearLayout.LayoutParams layout_row_problem_layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            layout_row_problem.setLayoutParams(layout_row_problem_layoutParams);
            layout_row_problem.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
            layout_row_problem.setPadding(30, 30, 30, 30);

            ImageView problem_solved_image = new ImageView(rootView.getContext());
            RelativeLayout.LayoutParams problem_solved_image_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            problem_solved_image_layoutParams.setMargins(20, 10, 30, 0);
            problem_solved_image_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            problem_solved_image_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            problem_solved_image.setId(1000 + i);
            problem_solved_image.setLayoutParams(problem_solved_image_layoutParams);
            problem_solved_image.setImageResource(R.drawable.correct);

            Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");

            TextView equation_title = new TextView(rootView.getContext());
            equation_title.setId(2000+i);
            RelativeLayout.LayoutParams equation_title_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            equation_title_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            equation_title_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image.getId());
            equation_title_layoutParams.setMargins(0, 8, 0, 1);
            equation_title.setLayoutParams(equation_title_layoutParams);
            equation_title.setText("Equation");
            equation_title.setTextSize(20);
            equation_title.setTypeface(font, Typeface.BOLD);

            MathView problem_text = new MathView(rootView.getContext(), null);
            RelativeLayout.LayoutParams problem_text_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            problem_text_layoutParams.setMargins(0, 0, 0, 10);
            problem_text_layoutParams.addRule(RelativeLayout.BELOW, equation_title.getId());
            problem_text_layoutParams.addRule(RelativeLayout.END_OF, problem_solved_image.getId());
            problem_text.setLayoutParams(problem_text_layoutParams);
            problem_text.setEngine(KaTeX);
            problem_text.config(
                    "MathJax.Hub.Config({\n" +
                            "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                            "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                            "         SVG: { linebreaks: { automatic: true } }\n" +
                            "});");
            problem_text.setText("\\(\\large" + problem.getProblem() + "\\)");

            TextView date_time = new TextView(rootView.getContext());
            RelativeLayout.LayoutParams date_time_layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            date_time_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            date_time_layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            date_time.setLayoutParams(date_time_layoutParams);
            date_time.setText(problem.getDate());

            layout_row_problem.addView(date_time);
            layout_row_problem.addView(problem_text);
            layout_row_problem.addView(problem_solved_image);
            layout_row_problem.addView(equation_title);

            cell_content_view_out.addView(layout_row_problem);

            row_problem.addView(cell_content_view_out);

            view_history_layout.addView(row_problem);


//        FrameLayout cell_content_view_in = new FrameLayout(rootView.getContext());


            row_problem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    row_problem.initialize(1000, Color.BLACK, 4);
                    row_problem.toggle(false);
                }
            });


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

