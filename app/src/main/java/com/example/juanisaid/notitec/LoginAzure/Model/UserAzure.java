package com.example.juanisaid.notitec.LoginAzure.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.microsoft.aad.adal.AuthenticationResult;

import java.net.UnknownServiceException;

/**
 * Created by rigoberto on 3/8/2018.
 */

public class UserAzure implements Parcelable {

    private String id;
    private String name;
    private String email;
    private String role;

    private String pathImage;
    private AuthenticationResult authenticationResult;

    public UserAzure(){

    }

    public UserAzure(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        role = in.readString();
        pathImage = in.readString();
    }

    public static final Creator<UserAzure> CREATOR = new Creator<UserAzure>() {
        @Override
        public UserAzure createFromParcel(Parcel in) {
            return new UserAzure(in);
        }

        @Override
        public UserAzure[] newArray(int size) {
            return new UserAzure[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public AuthenticationResult getAuthenticationResult() {
        return authenticationResult;
    }

    public void setAuthenticationResult(AuthenticationResult authenticationResult) {
        this.authenticationResult = authenticationResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(role);
        dest.writeString(pathImage);
    }
}
