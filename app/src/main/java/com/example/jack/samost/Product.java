package com.example.jack.samost;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "product")
public class Product {
    @DatabaseField(generatedId  = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String description;
    @DatabaseField
    private float price;
    @DatabaseField
    private String tags;
    @DatabaseField
    private String seller;
    @DatabaseField
    private String category;
    @DatabaseField(columnDefinition = "MEDIUMBLOB not null",
            dataType = DataType.BYTE_ARRAY)
    byte[] image;
    @DatabaseField
    private Date creation_date;
    @DatabaseField
    private int prosfora;

    public Product() {
    }

    public Product(int id, String name, String description, float price, String tags, String seller, String category, byte[] image, Date creationDate, int prosfora) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.tags = tags;
        this.seller = seller;
        this.category = category;
        this.image = image;
        this.creation_date = creationDate;
        this.prosfora = prosfora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = Arrays.copyOf(image, image.length);
    }

    public Date getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(Date creationDate) {
        this.creation_date = creationDate;
    }

    public int getProsfora() {
        return prosfora;
    }

    public void setProsfora(int prosfora) {
        this.prosfora = prosfora;
    }
}
