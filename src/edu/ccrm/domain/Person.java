package edu.ccrm.domain;

public abstract class Person {
    protected String fullName;
    protected String email;

    public abstract String getProfile();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}