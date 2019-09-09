package com.example.slumsurvey;

import java.io.Serializable;

public class appformfirebase  implements Serializable {
    private String nameofslum="info", headoffamily="info", gender="info", category="info", religion="info", fathername="info", hofage="info", mobilenumber="info", address="info", familyincome="info", nationality="info", aadhar="info", numberofmembers="info", imageUrl="info", imagename;

    public appformfirebase(String nameofslum, String headoffamily, String gender, String category, String religion, String fathername, String hofage, String mobilenumber, String address, String familyincome, String nationality, String aadhar, String numberofmembers, String imageUrl, String imagename) {
        this.nameofslum = nameofslum;
        this.headoffamily = headoffamily;
        this.gender = gender;
        this.category = category;
        this.religion = religion;
        this.fathername = fathername;
        this.hofage = hofage;
        this.mobilenumber = mobilenumber;
        this.address = address;
        this.familyincome = familyincome;
        this.nationality = nationality;
        this.aadhar = aadhar;
        this.numberofmembers = numberofmembers;
        this.imageUrl = imageUrl;
        this.imagename=imagename;
    }

    public String getNameofslum() {
        return nameofslum;
    }

    public String getHeadoffamily() {
        return headoffamily;
    }

    public String getGender() {
        return gender;
    }

    public String getCategory() {
        return category;
    }

    public String getReligion() {
        return religion;
    }

    public String getFathername() {
        return fathername;
    }

    public String getHofage() {
        return hofage;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public String getAddress() {
        return address;
    }

    public String getFamilyincome() {
        return familyincome;
    }

    public String getNationality() {
        return nationality;
    }

    public String getAadhar() {
        return aadhar;
    }

    public String getNumberofmembers() {
        return numberofmembers;
    }

    public String getImageUrl() {return imageUrl; }

    public String getImagename() {return imagename; }

    public void setNameofslum(String nameofslum) {
        this.nameofslum = nameofslum;
    }

    public void setHeadoffamily(String headoffamily) {
        this.headoffamily = headoffamily;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public void setHofage(String hofage) {
        this.hofage = hofage;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFamilyincome(String familyincome) {
        this.familyincome = familyincome;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setImagename(String imagename) { this.imagename = imagename; }

    public void setNumberofmembers(String numberofmembers) {
        this.numberofmembers = numberofmembers;
    }
}
