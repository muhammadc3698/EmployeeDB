package com.example.EmployeeDB.employee;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager 
{
    @Id //Makes ID the primary key
    private long id;                //Employee's Id
    private String firstName;       //Employee's First name 
    private String lastName;        //Employee's Last name 
    private String email;           //Employee's email
    private long phone;             //Employee's phone number

    public Manager(long id, String firstName, String lastName, String email, long phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
        this.phone = phone;
    }
}
