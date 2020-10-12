package com.heyworld.e_tick.Profile;

public class ConstructorProfile {

    public String email;

    public ConstructorProfile (){
    }

    public ConstructorProfile (String email){
        this.email = email;
    }

    public String getEmail (){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }
}
