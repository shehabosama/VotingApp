package com.example.voting.Ui.Activities.Centers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.voting.R;
import com.example.voting.common.model.Centers;

import java.util.List;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.CenterViewHolder> {
    private CenterInterAciton interAction;
    private Context context;
    private List<Centers> list;

    public CenterAdapter(Context context, List<Centers> list, CenterInterAciton interAction) {
        this.context = context;
        this.list = list;
        this.interAction = interAction;
    }

    @NonNull
    @Override
    public CenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_display_center_item, parent, false);

        return new CenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CenterViewHolder holder, int position) {
        Centers centers   = list.get(position);
        holder.textViewCenterName.setText(centers.getName());
        holder.setListener(centers);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface CenterInterAciton {

        void onClickItem(Centers center);
    }

    public class CenterViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCenterName;

        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCenterName = itemView.findViewById(R.id.text_center_name);
        }

        public void setListener(final Centers center) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(interAction!=null)
                       interAction.onClickItem(center);
                }
            });
        }
    }
}
