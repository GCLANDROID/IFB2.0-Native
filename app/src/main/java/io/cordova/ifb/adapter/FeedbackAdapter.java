package io.cordova.ifb.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.cordova.ifb.R;
import io.cordova.ifb.activity.FeedbackAprilActivity;
import io.cordova.ifb.module.FeedBackModel;


/**
 * Created by LENOVO on 12/8/2017.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private ArrayList<FeedBackModel> mAttandanceModelList=new ArrayList<>();
    private Context context;



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_raw, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final FeedBackModel attandanceModel = mAttandanceModelList.get(position);
        holder.tvQuestion.setText(mAttandanceModelList.get(position).getQuestion());
        holder.tvAns.setText(mAttandanceModelList.get(position).getAnswer());

        if (mAttandanceModelList.get(position).isSelected()){
            holder.imgLike.setVisibility(View.VISIBLE);
        }else {
            holder.imgLike.setVisibility(View.GONE);
        }



        // holder.view.setBackgroundColor(attandanceModel.isSelected() ? Color.CYAN : Color.WHITE);
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attandanceModel.setSelected(!attandanceModel.isSelected());
                // holder.view.setBackgroundColor(attandanceModel.isSelected() ? Color.CYAN : Color.WHITE);

                if (attandanceModel.isSelected()) {

                        holder.imgLike.setVisibility(View.VISIBLE);
                        mAttandanceModelList.get(position).setSelected(true);
                        notifyDataSetChanged();

                        ((FeedbackAprilActivity) context).updateAttendanceStatus(position, true );



                } else {
                    holder.imgLike.setVisibility(View.GONE);

                    mAttandanceModelList.get(position).setSelected(false);
                    notifyDataSetChanged();
                }

            }
        });

        if (!mAttandanceModelList.get(position).getQuestion().equals("")){
            holder.llItemName.setVisibility(View.VISIBLE);
        }else {
            holder.llItemName.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mAttandanceModelList == null ? 0 : mAttandanceModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
      TextView tvQuestion,tvAns;
      ImageView imgLike;
      LinearLayout llItemName,llMain;

        private MyViewHolder(View itemView) {
            super(itemView);
            llItemName=(LinearLayout)itemView.findViewById(R.id.llItemName);
            llMain=(LinearLayout)itemView.findViewById(R.id.llMain);

            imgLike=(ImageView)itemView.findViewById(R.id.imgLike);

            tvQuestion=(TextView)itemView.findViewById(R.id.tvQuestion);
            tvAns=(TextView)itemView.findViewById(R.id.tvAns);


        }
    }

    public FeedbackAdapter(ArrayList<FeedBackModel> mAttandanceModelList, Context context) {
        this.mAttandanceModelList = mAttandanceModelList;
        this.context = context;
    }
}
