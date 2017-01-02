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

import edu.its.solveexponents.teacheraica.content.user_manuals.LecturesManualFragment;
import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.content.user_manuals.ReadingMaterialsManualFragment;
import edu.its.solveexponents.teacheraica.content.user_manuals.SolveExponentsManualFragment;
import edu.its.solveexponents.teacheraica.model.ManualTopic;

/**
 * Created by jairus on 7/7/16.
 */

public class UserManualRVAdapter extends RecyclerView.Adapter<UserManualRVAdapter.ManualTopicViewHolder> {
    public static class ManualTopicViewHolder extends RecyclerView.ViewHolder {

        CardView cv_user_manual;
        TextView manual_topic_title;
        TextView manual_topic_desc;
        ImageView manual_topic_image;

        public ManualTopicViewHolder(View itemView) {
            super(itemView);
            cv_user_manual = (CardView) itemView.findViewById(R.id.cv_user_manual);
            manual_topic_title = (TextView) itemView.findViewById(R.id.manual_topic_title);
            manual_topic_desc = (TextView) itemView.findViewById(R.id.manual_topic_desc);
            manual_topic_image = (ImageView) itemView.findViewById(R.id.manual_topic_image);
        }
    }

    List<ManualTopic> manual_topic;
    private Context mContext;

    public UserManualRVAdapter(Context context, List<ManualTopic> manual_topic) {
        this.manual_topic = manual_topic;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ManualTopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_user_manual, viewGroup, false);
        ManualTopicViewHolder mtvh = new ManualTopicViewHolder(v);
        return mtvh;
    }

    @Override
    public void onBindViewHolder(final ManualTopicViewHolder manualTopicViewHolder, final int i) {
        manualTopicViewHolder.manual_topic_title.setText(manual_topic.get(i).manual_topic_title);
        manualTopicViewHolder.manual_topic_desc.setText(manual_topic.get(i).manual_topic_desc);
        manualTopicViewHolder.manual_topic_image.setImageResource(manual_topic.get(i).manual_topic_image);

        manualTopicViewHolder.cv_user_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass;

                switch (manual_topic.get(i).manual_topic_title) {
                    case "Solve Exponents Section Manual":
                        fragmentClass = SolveExponentsManualFragment.class;
                        break;
                    case "Reading Materials Section Manual":
                        fragmentClass = ReadingMaterialsManualFragment.class;
                        break;
                    case "Lectures Section Manual":
                        fragmentClass = LecturesManualFragment.class;
                        break;
                    default:
                        fragmentClass = SolveExponentsManualFragment.class;
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
        return manual_topic.size();
    }
}
