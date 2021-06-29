package com.T_Y.model;

import java.io.Serializable;

public class HangoutsResult implements Serializable {
    private String name;
    private String headline;
    private String rating;
    private String link;

    public HangoutsResult() {
        this.setName("tempName");
        this.setHeadline("tempHeadline");
        this.setRating("tempRating");
        this.setLink("tempLink");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HangoutsResult(String headline, String rating) {
        setHeadline(headline);
        setRating(rating);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHeadline() {
        return headline;
    }

    ;

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
