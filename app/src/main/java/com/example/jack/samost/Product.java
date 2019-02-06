package com.example.jack.samost;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@DatabaseTable(tableName = "product")
public class Product implements Parcelable {
    @DatabaseField(generatedId = true)
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
    private String dateString;

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate2 = dateFormat.format(creation_date);
        this.dateString = creationDate2;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readFloat();
        tags = in.readString();
        seller = in.readString();
        category = in.readString();
        image = in.createByteArray();
        prosfora = in.readInt();
        dateString = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return readFromParcel(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String creationDate2 = dateFormat.format(creationDate);
        this.dateString = creationDate2;
        this.creation_date = creationDate;
    }

    public int getProsfora() {
        return prosfora;
    }

    public void setProsfora(int prosfora) {
        this.prosfora = prosfora;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(image.length);
        dest.writeByteArray(image);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(category);
        dest.writeString(seller);
        dest.writeString(tags);

        dest.writeString(dateString);
        dest.writeInt(prosfora);
    }

    private static Product readFromParcel(Parcel in) {
        int id = in.readInt();
        byte[] _byte = new byte[in.readInt()];
        in.readByteArray(_byte);
        String desc = in.readString();
        String name = in.readString();
        float price = in.readFloat();
        String category = in.readString();
        String seller = in.readString();
        String tags = in.readString();
        String dateString = in.readString();
        int prosfora = in.readInt();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date mdate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Product(id, name, desc, price, tags, seller, category, _byte, new Date(dateString), prosfora);
    }


}
