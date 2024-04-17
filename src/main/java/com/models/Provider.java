package com.models;

public class Provider  {
    
    private String firstName;
    private String lastName;
    private String providerId;

    public Provider(String firstName, String lastName, String providerId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.providerId = providerId;
    }

    public String getProviderId() {
        return this.providerId;
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
