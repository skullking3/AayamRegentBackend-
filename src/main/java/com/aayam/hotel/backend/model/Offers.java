package com.aayam.hotel.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Offers") // Exact collection name matched from image_bc2919.png
public class Offers {

    @Id
    private String id; // MongoDB default ObjectId auto mapping string
    private String tag;
    private String title;
    private String desc;
    private String btnText;

    // No-Args Constructor
    public Offers() {
    }

    // All-Args Constructor
    public Offers(String id, String tag, String title, String desc, String btnText) {
        this.id = id;
        this.tag = tag;
        this.title = title;
        this.desc = desc;
        this.btnText = btnText;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public String getBtnText() { return btnText; }
    public void setBtnText(String btnText) { this.btnText = btnText; }
}