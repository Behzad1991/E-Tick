package com.heyworld.e_tick.Profile;

public class ConstructorProfile {

    public String email;
    public String dob;

    public ConstructorProfile (){
    }

    public ConstructorProfile (String email, String dob){

        this.email = email;
        this.dob = dob;
    }

    public String getEmail (){
        return email;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public String getDob(){
        return dob;
    }

    public void setDob(String dob){this.dob = dob;}
}
