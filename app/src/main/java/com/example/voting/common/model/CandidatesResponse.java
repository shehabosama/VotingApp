package com.example.voting.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.List;

public class CandidatesResponse {
    @SerializedName("candidates")
    @Expose
    private List<Candidate> candidates = null;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
