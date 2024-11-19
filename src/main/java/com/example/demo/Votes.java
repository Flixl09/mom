package com.example.demo;

import org.json.JSONObject;

public class Votes {
    private int oevp;
    private int spoe;
    private int fpoe;
    private int gruene;
    private int neos;
    private int kpoe;

    public Votes(int oevp, int spoe, int fpoe, int gruene, int neos, int kpoe) {
        this.oevp = oevp;
        this.spoe = spoe;
        this.fpoe = fpoe;
        this.gruene = gruene;
        this.neos = neos;
        this.kpoe = kpoe;
    }

    public String toJson() {
        return new JSONObject()
                .put("oevp", oevp)
                .put("spoe", spoe)
                .put("fpoe", fpoe)
                .put("gruene", gruene)
                .put("neos", neos)
                .put("kpoe", kpoe)
                .toString();
    }
}
