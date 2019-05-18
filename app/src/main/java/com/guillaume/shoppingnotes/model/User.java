package com.guillaume.shoppingnotes.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.firebase.database.annotations.NotNull;

@Entity(tableName = "users")
public class User implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;

    private String lastname;

    private String firstname;

    private String email;

    private String password;

    public User() { }

    @Ignore
    public User(@NonNull String id, String lastname, String firstname, String email, String password) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }

    @Ignore
    private User(Parcel in) {
        this.id = in.readString();
        this.lastname = in.readString();
        this.firstname = in.readString();
        this.email = in.readString();
        this.password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(lastname);
        dest.writeString(firstname);
        dest.writeString(email);
        dest.writeString(password);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @NonNull
    public String getId() { return id; }

    public void setId(@NonNull String id) { this.id = id; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
