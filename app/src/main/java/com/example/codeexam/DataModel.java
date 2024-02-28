package com.example.codeexam;

public class DataModel {

    private String fullName;
    private String emailAddress;
    private String mobileNumber;
    private int age;

    public DataModel(String fullName, String emailAddress, String mobileNumber, int age) {
        this.fullName = String.valueOf(fullName);
        this.emailAddress = String.valueOf(emailAddress);
        this.mobileNumber = String.valueOf(mobileNumber);
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
