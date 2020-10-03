package com.anspace.reviews.Model;

import java.util.ArrayList;

public class ParentCategory {
    private String catName;
    private ArrayList<SubjectCard> subjectList;



    public ParentCategory() {
    }

    public ParentCategory(String catName, ArrayList<SubjectCard> subjectList) {
        this.catName = catName;
        this.subjectList = subjectList;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public ArrayList<SubjectCard> getSubjectList() {
        return subjectList;
    }

    public void setSubjectList(ArrayList<SubjectCard> subjectList) {
        this.subjectList = subjectList;
    }
}
