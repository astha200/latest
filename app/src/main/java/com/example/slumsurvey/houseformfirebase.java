package com.example.slumsurvey;

public class houseformfirebase {

    private String area, areabuilt, conhouse, room, toilet, kitchen, yearsofstaying, consent, imageUrl, imagename;

    public houseformfirebase(String area, String areabuilt, String conhouse, String room, String toilet, String kitchen, String yearsofstaying, String consent, String imageUrl, String imagename) {
        this.area = area;
        this.areabuilt = areabuilt;
        this.conhouse = conhouse;
        this.room = room;
        this.toilet = toilet;
        this.kitchen = kitchen;
        this.yearsofstaying = yearsofstaying;
        this.consent = consent;
        this.imageUrl = imageUrl;
        this.imagename=imagename;
    }

    public String getArea() {
        return area;
    }

    public String getAreabuilt() {
        return areabuilt;
    }

    public String getConhouse() {
        return conhouse;
    }

    public String getRoom() {
        return room;
    }

    public String getToilet() {
        return toilet;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getYearsofstaying() {
        return yearsofstaying;
    }

    public String getConsent() {
        return consent;
    }

    public String getImageUrl() { return imageUrl; }

    public String getImagename() { return imagename; }

    public void setArea(String area) {
        this.area = area;
    }

    public void setAreabuilt(String areabuilt) {
        this.areabuilt = areabuilt;
    }

    public void setConhouse(String conhouse) {
        this.conhouse = conhouse;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public void setYearsofstaying(String yearsofstaying) {
        this.yearsofstaying = yearsofstaying;
    }

    public void setConsent(String consent) {
        this.consent = consent;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public void setImagename(String imagename) { this.imagename = imagename; }
}
