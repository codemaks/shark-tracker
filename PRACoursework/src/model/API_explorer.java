package model;

import api.jaws.Jaws;

/**
 * Created by Evou on 24/02/2016.
 */
public class API_explorer {

    private Jaws jawsApi;

    public API_explorer() {
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
    }

    public String getAcknowledgement(){
       return jawsApi.getAcknowledgement();
    }
    public void getAllSharks(){
    //TODO auto-generated method stub
    }
}
