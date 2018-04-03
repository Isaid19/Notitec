package com.example.juanisaid.notitec.LoginAzure.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rigoberto on 14/03/18.
 */

public class UserProfileAzure implements Parcelable {

    @SerializedName("@odata.context")
    @Expose
    private String odataContext;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("businessPhones")
    @Expose
    private List<String> businessPhones = null;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("givenName")
    @Expose
    private String givenName;
    @SerializedName("jobTitle")
    @Expose
    private String jobTitle;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("mobilePhone")
    @Expose
    private String mobilePhone;
    @SerializedName("officeLocation")
    @Expose
    private String officeLocation;
    @SerializedName("preferredLanguage")
    @Expose
    private Object preferredLanguage;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("userPrincipalName")
    @Expose
    private String userPrincipalName;
    public final static Creator<UserProfileAzure> CREATOR = new Creator<UserProfileAzure>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserProfileAzure createFromParcel(Parcel in) {
            return new UserProfileAzure(in);
        }

        public UserProfileAzure[] newArray(int size) {
            return (new UserProfileAzure[size]);
        }

    };

    protected UserProfileAzure(Parcel in) {
        this.odataContext = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.businessPhones, (String.class.getClassLoader()));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.givenName = ((String) in.readValue((String.class.getClassLoader())));
        this.jobTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.mail = ((String) in.readValue((String.class.getClassLoader())));
        this.mobilePhone = ((String) in.readValue((String.class.getClassLoader())));
        this.officeLocation = ((String) in.readValue((String.class.getClassLoader())));
        this.preferredLanguage = ((Object) in.readValue((Object.class.getClassLoader())));
        this.surname = ((String) in.readValue((String.class.getClassLoader())));
        this.userPrincipalName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public UserProfileAzure() {
    }

    public String getOdataContext() {
        return odataContext;
    }

    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getBusinessPhones() {
        return businessPhones;
    }

    public void setBusinessPhones(List<String> businessPhones) {
        this.businessPhones = businessPhones;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public Object getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(Object preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(odataContext);
        dest.writeValue(id);
        dest.writeList(businessPhones);
        dest.writeValue(displayName);
        dest.writeValue(givenName);
        dest.writeValue(jobTitle);
        dest.writeValue(mail);
        dest.writeValue(mobilePhone);
        dest.writeValue(officeLocation);
        dest.writeValue(preferredLanguage);
        dest.writeValue(surname);
        dest.writeValue(userPrincipalName);
    }

    public int describeContents() {
        return 0;
    }

}