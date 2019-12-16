package com.infy.telstraassignment_1.model;

import java.util.Date;

public class Canada {

    private String title;
    private String description;
//    private String imageHref;




    /**
     *
     * @param title
     * @param description
//     * @param imageHref
     */
    public Canada( String title, String description) {

        this.title = title;
        this.description = description;
//        this.imageHref = imageHref;

    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getImageHref() {
//        return imageHref;
//    }
//
//    public void setImageHref(String imageHref) {
//        this.imageHref = imageHref;
//    }







    @Override
    public String toString() {
        return "Title : '" + title + '\''+" Description : '" + description + '\'';
    }
}

