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

import edu.its.solveexponents.teacheraica.R;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialFiveFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialFourFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialOneFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialSevenFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialSixFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialThreeFragment;
import edu.its.solveexponents.teacheraica.content.reading_materials.ReadingMaterialTwoFragment;
import edu.its.solveexponents.teacheraica.model.ReadingMaterialsTopic;

/**
 * Created by jairus on 8/1/16.
 */

public class ReadingMaterialsRVAdapter extends RecyclerView.Adapter<ReadingMaterialsRVAdapter.ReadingMaterialsTopicViewHolder>  {
    public static class ReadingMaterialsTopicViewHolder extends RecyclerView.ViewHolder {

        CardView cv_reading_materials;
        TextView reading_materials_topic_title;
        TextView reading_materials_topic_desc;
        ImageView reading_materials_topic_image;

        public ReadingMaterialsTopicViewHolder(View itemView) {
            super(itemView);
            cv_reading_materials = (CardView)itemView.findViewById(R.id.cv_reading_materials);
            reading_materials_topic_title = (TextView)itemView.findViewById(R.id.reading_materials_topic_title);
            reading_materials_topic_desc = (TextView)itemView.findViewById(R.id.reading_materials_topic_desc);
            reading_materials_topic_image = (ImageView)itemView.findViewById(R.id.reading_materials_topic_image);
        }
    }

    List<ReadingMaterialsTopic> reading_materials_topic;
    private Context mContext;

    public ReadingMaterialsRVAdapter(Context context, List<ReadingMaterialsTopic> reading_materials_topic){
        this.reading_materials_topic = reading_materials_topic;
        this.mContext = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ReadingMaterialsRVAdapter.ReadingMaterialsTopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row_reading_materials, viewGroup, false);
        ReadingMaterialsRVAdapter.ReadingMaterialsTopicViewHolder rmtvh = new ReadingMaterialsRVAdapter.ReadingMaterialsTopicViewHolder(v);
        return rmtvh;
    }

    @Override
    public void onBindViewHolder(final ReadingMaterialsRVAdapter.ReadingMaterialsTopicViewHolder readingMaterialsTopicViewHolder, final int i) {
        readingMaterialsTopicViewHolder.reading_materials_topic_title.setText(reading_materials_topic.get(i).reading_materials_topic_title);
        readingMaterialsTopicViewHolder.reading_materials_topic_desc.setText(reading_materials_topic.get(i).reading_materials_topic_desc);
        readingMaterialsTopicViewHolder.reading_materials_topic_image.setImageResource(reading_materials_topic.get(i).reading_materials_topic_image);

        readingMaterialsTopicViewHolder.cv_reading_materials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Class fragmentClass;

                switch (reading_materials_topic.get(i).reading_materials_topic_title) {
                    case "Positive Integer Exponents":
                        fragmentClass = ReadingMaterialOneFragment.class;
                        break;
                    case "Base Raised to Zero":
                        fragmentClass = ReadingMaterialTwoFragment.class;
                        break;
                    case "Addition of Exponents with Same Bases":
                        fragmentClass = ReadingMaterialThreeFragment.class;
                        break;
                    case "Multiplication of Bases with the Same Exponents":
                        fragmentClass = ReadingMaterialFourFragment.class;
                        break;
                    case "Multiplication of Exponents to Find the Power of a Power":
                        fragmentClass = ReadingMaterialFiveFragment.class;
                        break;
                    case "Subtraction of Exponents":
                        fragmentClass = ReadingMaterialSixFragment.class;
                        break;
                    case "Negative Integer Exponents":
                        fragmentClass = ReadingMaterialSevenFragment.class;
                        break;
                    default:
                        fragmentClass = ReadingMaterialOneFragment.class;
                }

                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reading_materials_topic.size();
    }
}
