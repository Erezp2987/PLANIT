package com.erez_p.model;

import com.erez_p.model.BASE.BaseEntity;

import java.io.Serializable;

public class Person extends BaseEntity implements Serializable {
    private long personPassport;
    private long personDateOfBirth;
    private String personName;
    private String familyName;
    private String userId;
    private String picture;

    public Person(long personPassport, long personDateOfBirth, String personName, String familyName, String userId, String picture) {
        this.personPassport = personPassport;
        this.personDateOfBirth = personDateOfBirth;
        this.personName = personName;
        this.familyName = familyName;
        this.userId = userId;
        this.picture = picture;
    }
    public long getPersonPassport() { return personPassport; }
    public void setPersonPassport(long personPassport) { this.personPassport = personPassport; }

    public long getPersonDateOfBirth() { return personDateOfBirth; }
    public void setPersonDateOfBirth(long personDateOfBirth) { this.personDateOfBirth = personDateOfBirth; }

    public String getPersonName() { return personName; }
    public void setPersonName(String personName) { this.personName = personName; }

    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }

    public String getUserId() { return userId; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}
