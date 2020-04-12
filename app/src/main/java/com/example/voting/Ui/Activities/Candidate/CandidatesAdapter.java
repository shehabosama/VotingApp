package com.example.voting.Ui.Activities.Candidate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.voting.R;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.model.Candidate;
import com.example.voting.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.CenterViewHolder> {
    private CenterInterAciton interAction;
    private Context context;
    private List<Candidate> list;

    public CandidatesAdapter(Context context, List<Candidate> list, CenterInterAciton interAction) {
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
        Candidate candidate   = list.get(position);
        String userId = AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,context,"");
        if(AppPreferences.getBoolean(userId+candidate.getId(),context,false)){
            holder.setListener(candidate);
            holder.chickImageView.setVisibility(View.VISIBLE);
            holder.textViewCenterName.setText("االاسم : "+candidate.getName());
            holder.textViewAge.setText("السن : "+candidate.getAge());
            holder.textViewProgram.setText("البرنامج الانتخابي : "+candidate.getElectoral_program());
            holder.textViewSymbol.setText("الرمز : "+candidate.getSymbol()+" \n"+"رقم المرشح : "+candidate.getId());
            Picasso.with(context)
                    .load(Urls.MAIN_URL+candidate.getImage()).placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.candidImage);

        }else {
            holder.setListener(candidate);
            holder.chickImageView.setVisibility(View.INVISIBLE);
            holder.textViewCenterName.setText("االاسم : "+candidate.getName());
            holder.textViewAge.setText("السن : "+candidate.getAge());
            holder.textViewProgram.setText("البرنامج الانتخابي : "+candidate.getElectoral_program());
            holder.textViewSymbol.setText("الرمز : "+candidate.getSymbol()+" \n"+"رقم المرشح : "+candidate.getId());
            Picasso.with(context)
                    .load(Urls.MAIN_URL+candidate.getImage()).placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.candidImage);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface CenterInterAciton {
        void onClickItem(Candidate candidate);
    }
    public class CenterViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCenterName,textViewAge,textViewProgram,textViewSymbol;
        ImageView chickImageView,candidImage;
        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCenterName = itemView.findViewById(R.id.text_center_name);
            textViewAge = itemView.findViewById(R.id.text_age);
            textViewProgram = itemView.findViewById(R.id.text_program_description);
            textViewSymbol = itemView.findViewById(R.id.text_symbol);
            chickImageView = itemView.findViewById(R.id.check_image);
            candidImage = itemView.findViewById(R.id.candidImage);

        }

        public void setListener(final Candidate candidate) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String userId = AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,context,"");
                    if(AppPreferences.getBoolean(userId,context,false)){
                        UiUtilities.showToast(context,"لقد ابديت بصوتك ليمكن ان تبدي بصوتك مرتين");

                    }else{
                        if(interAction!=null){
                            interAction.onClickItem(candidate);
                            chickImageView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }
    }
}
