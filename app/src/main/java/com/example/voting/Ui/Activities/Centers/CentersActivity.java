package com.example.voting.Ui.Activities.Centers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.voting.R;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.CenterResponse;
import com.example.voting.common.model.Centers;

import java.util.ArrayList;
import java.util.List;

public class CentersActivity extends BaseActivity implements CentersContract.Model.onFinishedListener,CentersContract.View , CenterAdapter.CenterInterAciton {
    private PresenterCenters presenter;
    private RecyclerView recyclerView;
    private List<Centers> centers;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);
        initializeViews();
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        centers = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new PresenterCenters(this,this);
        presenter.performGetAllCenters();
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void onFinished(String result) {

    }

    @Override
    public void loadCentersDate(CenterResponse centerResponse) {
        centers.clear();
        centers.addAll(centerResponse.getCenters());
        CenterAdapter adapter = new CenterAdapter(this,centers,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void onClickItem(final Centers center) {
        UiUtilities.showActionDialog(this, "تاكيد", "هل انت متاكد انك تريد اختيار هذا المركز لو اخترت ذالك المركز لا يمكن التراجع في قرارك", "تاكيد", "خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UiUtilities.showToast(CentersActivity.this,center.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UiUtilities.showToast(CentersActivity.this,String.valueOf(center.getId()));

            }
        });
    }

    @Override
    public void showProgress() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("من فضلك انتظر ثواني");
        dialog.setMessage("جاري اعداد طلبك");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void hideProgress() {

        dialog.dismiss();
    }
}
