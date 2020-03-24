package com.example.voting.Ui.Activities.Register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.voting.R;
import com.example.voting.common.model.Centers;

import java.util.List;

public class CenterAdapter extends BaseAdapter {

    private Context context;
    List<Centers> listitems;

    public CenterAdapter(Context context, List<Centers> items){
        this.listitems=items;
        this.context = context;

    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int i) {
        return listitems.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       // LayoutInflater layoutInflater=LayoutInflater.from(context).getLayoutInflater();
         view = LayoutInflater.from(context).inflate(R.layout.spiner_row, viewGroup, false);
       // View view3=layoutInflater.inflate(R.layout.row_lay,null);
        final Centers debtor = listitems.get(i);
        final TextView textView=view.findViewById(R.id.textname);
            textView.setText(debtor.getName());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if (tablesSpinnerInterAction!=null)
//                   tablesSpinnerInterAction.onClickTableItem(table);
//            }
//        });
        return view;
    }
}