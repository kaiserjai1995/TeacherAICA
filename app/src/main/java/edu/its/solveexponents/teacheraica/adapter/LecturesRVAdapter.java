package edu.its.solveexponents.teacheraica.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.its.solveexponents.teacheraica.content.lectures.LectureSubtractionOfExponentsFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureAdditionOfExponentsWithTheSameBasesFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureBaseRaisedToZeroFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureNegativeIntegerExponentsFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureIntroductionFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureMultiplicationOfExponentsToFindThePowerOfAPowerFragment;
import edu.its.solveexponents.teacheraica.content.lectures.LectureMultiplicationOfBasesWithTheSameExponentsFragment;
import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.model.LecturesTopic;

/**
 * Created by jairus on 8/1/16.
 */

public class LecturesRVAdapter extends RecyclerView.Adapter<LecturesRVAdapter.LecturesTopicViewHolder> {
    public static class LecturesTopicViewHolder extends RecyclerView.ViewHolder {

        CardView cv_lectures;
        TextView lectures_topic_title;
        TextView lectures_topic_desc;
        ImageView lectures_topic_image;

        public LecturesTopicViewHolder(View itemView) {
            super(itemView);
            cv_lectures = (CardView) itemView.findViewById(R.id.cv_lectures);
            lectures_topic_title = (TextView) itemView.findViewById(R.id.lectures_topic_title);
            lectures_topic_desc = (TextView) itemView.findViewById(R.id.lectures_topic_desc);
            lectures_topic_image = (ImageView) itemView.findViewById(R.id.lectures_topic_image);
        }
    }

    List<LecturesTopic> lectures_topic;
    private Context mContext;

    public LecturesRVAdapter(Context context, List<LecturesTopic> lectures_topic) {
        this.lectures_topic = lectures_topic;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LecturesTopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_lectures, viewGroup, false);
        LecturesTopicViewHolder ltvh = new LecturesTopicViewHolder(v);
        return ltvh;
    }

    @Override
    public void onBindViewHolder(final LecturesTopicViewHolder lecturesTopicViewHolder, final int i) {
        lecturesTopicViewHolder.lectures_topic_title.setText(lectures_topic.get(i).lectures_topic_title);
        lecturesTopicViewHolder.lectures_topic_desc.setText(lectures_topic.get(i).lectures_topic_desc);
        lecturesTopicViewHolder.lectures_topic_image.setImageResource(lectures_topic.get(i).lectures_topic_image);

        lecturesTopicViewHolder.cv_lectures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass;

                switch (lectures_topic.get(i).lectures_topic_title) {
                    case "Introduction":
                        fragmentClass = LectureIntroductionFragment.class;
                        break;
                    case "Base Raised to Zero":
                        fragmentClass = LectureBaseRaisedToZeroFragment.class;
                        break;
                    case "Addition of Exponents with the Same Bases":
                        fragmentClass = LectureAdditionOfExponentsWithTheSameBasesFragment.class;
                        break;
                    case "Multiplication of Bases with the Same Exponents":
                        fragmentClass = LectureMultiplicationOfBasesWithTheSameExponentsFragment.class;
                        break;
                    case "Multiplication of Exponents to Find the Power of a Power":
                        fragmentClass = LectureMultiplicationOfExponentsToFindThePowerOfAPowerFragment.class;
                        break;
                    case "Subtraction of Exponents":
                        fragmentClass = LectureSubtractionOfExponentsFragment.class;
                        break;
                    case "Negative Integer Exponents":
                        fragmentClass = LectureNegativeIntegerExponentsFragment.class;
                        break;
                    default:
                        fragmentClass = LectureIntroductionFragment.class;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lectures_topic.size();
    }
}
