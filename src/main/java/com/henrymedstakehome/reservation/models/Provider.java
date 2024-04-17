package com.henrymedstakehome.reservation.models;

public class Provider  {
    
    private String firstName;
    private String lastName;
    private String providerId;

    // public Provider(String firstName, String lastName, String providerId) {
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    //     this.providerId = providerId;
    // }

    public Provider() {
    }

    public String getProviderId() {
        return this.providerId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String setLastName() {
        return this.lastName;
    }

    public void setProviderId(String providerId) {
         this.providerId = providerId;
    }

    public void setFirstName(String firstName) {
         this.firstName = firstName;
    }

    public void setLastName(String lastName) {
         this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
         if (o == this) {
            return true;
        }

        if (!(o instanceof Provider)) {
            return false;
        }
         
        Provider p = (Provider) o;
        return this.providerId.equals(p.getProviderId());
    }
    
}
