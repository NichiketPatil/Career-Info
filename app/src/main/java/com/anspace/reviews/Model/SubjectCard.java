package com.anspace.reviews.Model;

import android.graphics.drawable.Drawable;

public class SubjectCard {
    private String subjectName;
//    private String cardColor;
    private Drawable drawable;

    public SubjectCard(String subjectName,Drawable drawable) {
        this.subjectName = subjectName;
        this.drawable = drawable;
    }

    public SubjectCard() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

//    public String getCardColor() {
//        return cardColor;
//    }
//
//    public void setCardColor(String cardColor) {
//        this.cardColor = cardColor;
//    }


    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
