package com.example.mycontactbook;

public class Contact {

    //data members
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String city;
    private String note;
    private String timestamp;


    public Contact(int id, String firstName, String lastName, String email, String phone, String city, String note, String timestamp) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.note = note;
        this.timestamp = timestamp;
    }

    //getters and setters
    public int getId() {
        return (this.id);
    }

    public String getFirstName() {
        return (this.firstName);
    }

    public String getLastName() {
        return (this.lastName);
    }

    public String getEmail() {
        return (this.email);
    }

    public String getPhone() {
        return (this.phone);
    }

    public String getCity() {
        return (this.city);
    }

    public String getNote() {
        return (this.note);
    }

    public String getTimestamp() {
        return (this.timestamp);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return (false);
        if (this.getClass() != obj.getClass())
            return (false);
        Contact person = (Contact) obj;
        if (this.firstName.equals(person.firstName) &&
                this.lastName.equals(person.lastName) &&
                this.email.equals(person.email) &&
                this.phone.equals(person.phone) &&
                this.city.equals(person.city) &&
                this.note.equals(person.note) &&
                this.timestamp.equals(person.timestamp))
            return (true);
        return (false);
    }

    @Override
    public String toString() {
        return ("First Name: " + this.firstName +
                "\nLast Name: " + this.lastName +
                "\nEmail: " + this.email +
                "\nPhone: " + this.phone +
                "\nCity: " + this.city +
                "\nNote: " + this.note +
                "\nDate Added: " + this.timestamp);
    }
}