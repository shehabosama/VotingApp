package com.example.voting.Ui.Activities.Centers;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.voting.R;
import com.example.voting.Ui.Activities.Login.LoginActivity;
import com.example.voting.Ui.Activities.Settings.SettingsActivity;
import com.example.voting.common.HelperStuffs.AppPreferences;
import com.example.voting.common.HelperStuffs.Constants;
import com.example.voting.common.HelperStuffs.UiUtilities;
import com.example.voting.common.base.BaseActivity;
import com.example.voting.common.model.Candidate;
import com.example.voting.common.model.CandidatesResponse;
import com.example.voting.common.model.Centers;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class CentersActivity extends BaseActivity implements CandidatesContract.Model.onFinishedListener, CandidatesContract.View , CandidatesAdapter.CenterInterAciton {
    private PresenterCandidates presenter;
    private RecyclerView recyclerView;
    private List<Candidate> candidates;
    private ProgressDialog dialog;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
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
        candidates = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        toolbar = findViewById(R.id.tool_Bar);
        toolbar.setTitle("الرئيسيه");
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);        // MaterialSearchView materialSearchView = new MaterialSearchView(getActivity());
        searchView.closeSearch();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new PresenterCandidates(this,this);
        UiUtilities.showToast(getApplicationContext(),AppPreferences.getString(Constants.AppPreferences.USER_CENTER,CentersActivity.this,""));
        presenter.performGetAllCandidates(AppPreferences.getString(Constants.AppPreferences.USER_CENTER,CentersActivity.this,""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.log_out:
                AppPreferences.setString(Constants.AppPreferences.LOGGED_IN_USER_KEY,"",this);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setListeners() {
        searchView.setOnSearchViewListener(searchViewListener);
        searchView.setOnQueryTextListener(onQueryTextListener);
    }
    private MaterialSearchView.SearchViewListener searchViewListener = new MaterialSearchView.SearchViewListener() {
        @Override
        public void onSearchViewShown() {

        }

        @Override
        public void onSearchViewClosed() {
            // presenter.performGetAllFlats(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
            CandidatesAdapter flatAdapter = new CandidatesAdapter(getApplicationContext(), candidates, new CandidatesAdapter.CenterInterAciton() {
                @Override
                public void onClickItem(Candidate candidate) {
                    voteToCandidate(candidate);
                }
            });
            recyclerView.setAdapter(flatAdapter);
            }
    };
    private MaterialSearchView.OnQueryTextListener onQueryTextListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(newText != null && !newText.isEmpty()){
                //   SearchForPeopleAndFrinds(newText);
                List<Candidate> found = new ArrayList<>();
                for (Candidate candidate:candidates) {
                    if(candidate.getName().contains(newText))
                        found.add(candidate);
                }
                CandidatesAdapter flatAdapter = new CandidatesAdapter(getApplicationContext(), found, new CandidatesAdapter.CenterInterAciton() {
                    @Override
                    public void onClickItem(Candidate candidate) {
                        voteToCandidate(candidate);
                    }
                });

                recyclerView.setAdapter(flatAdapter);

            }else{
                CandidatesAdapter flatAdapter = new CandidatesAdapter(getApplicationContext(), candidates, new CandidatesAdapter.CenterInterAciton() {
                    @Override
                    public void onClickItem(Candidate candidate) {
                        voteToCandidate(candidate);
                    }
                });
                recyclerView.setAdapter(flatAdapter);
            }
            return true;
        }
    };
    @Override
    public void onFinished(String result) {
        UiUtilities.showToast(getApplicationContext(),result);
    }

    @Override
    public void loadCandidatesData(CandidatesResponse candidatesResponse) {
        candidates.clear();
        for(int i=1;i<candidatesResponse.getCandidates().size();i++){
            candidates.add(candidatesResponse.getCandidates().get(i));
        }

        CandidatesAdapter adapter = new CandidatesAdapter(this, candidates,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void onClickItem(final Candidate candidate) {
       voteToCandidate(candidate);
    }

    public void voteToCandidate(final Candidate candidate){
        UiUtilities.showActionDialog(this, "تاكيد", "هل انت متاكد انك تريد اختيار هذا المرشح ؟ لانه لا يمكن التراجع عن قرارك", "تاكيد", "خروج", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userId = AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,CentersActivity.this,"");
                presenter.updateCandidStatus(String.valueOf(candidate.getId()),userId);
                AppPreferences.setBoolean(userId+candidate.getId(),true,getApplicationContext());
                AppPreferences.setBoolean(userId,true,getApplicationContext());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
