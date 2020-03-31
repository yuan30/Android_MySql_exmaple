package com.example.amysqlexmaple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class NegativeIonRVAdapter extends RecyclerView.Adapter<NegativeIonRVAdapter.ViewHolder>
        implements  View.OnClickListener{

    private Context mContext;
    private List<NegativeIonModel> negativeIonModelList;
    private OnItemClickListener onItemClickListener;

    public NegativeIonRVAdapter(Context mContext, List<NegativeIonModel> negativeIonModelList){
        this.mContext = mContext;
        this.negativeIonModelList = negativeIonModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_negativeion, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        v.setTag(viewHolder);
        //v.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            holder.tvNegativeIonValue.setText(negativeIonModelList.get(position).getNegativeIonValue());
            holder.tvPm25Value.setText(negativeIonModelList.get(position).getPm25Value());
            holder.tvTemperatureValue.setText(negativeIonModelList.get(position).getTemperatureValue());
            holder.tvHumidityValue.setText(negativeIonModelList.get(position).getHumidityValue());
            holder.tvTimeValue.setText(negativeIonModelList.get(position).getTimeValue());
    }

    @Override
    public void onClick(View v) { // rv上物件點擊的實作，點擊將資訊傳給有實作下面介面的RDActivity

    }

    public interface OnItemClickListener{ // 由ReceiveDataActivity來實作，來將資訊傳回畫面，做其他功能
         void OnItemClick(View view, int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvNegativeIonValue, tvPm25Value;
        private TextView tvTemperatureValue, tvHumidityValue;
        private TextView tvTimeValue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNegativeIonValue = itemView.findViewById(R.id.tv_NegativeIonValue);
            tvPm25Value = itemView.findViewById(R.id.tv_Pm25Value);
            tvTemperatureValue = itemView.findViewById(R.id.tv_TemperatureValue);
            tvHumidityValue = itemView.findViewById(R.id.tv_HumidityValue);
            tvTimeValue = itemView.findViewById(R.id.tv_TimeValue);
        }
    }

    @Override
    public int getItemCount() {
        return negativeIonModelList.size();
    }
}
