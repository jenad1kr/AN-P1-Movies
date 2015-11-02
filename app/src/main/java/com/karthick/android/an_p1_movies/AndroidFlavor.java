package com.karthick.android.an_p1_movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karthick.jenadoss on 11/1/2015.
 */
public class AndroidFlavor implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() { return versionName + "--" + versionNumber + "--" + image;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(versionName);
        dest.writeString(versionNumber);
        dest.writeInt(image);

    }

    String versionName;
    String versionNumber;
    int image; // drawable reference id

    public AndroidFlavor(String vName, String vNumber, int image)
    {
        this.versionName = vName;
        this.versionNumber = vNumber;
        this.image = image;
    }

    public AndroidFlavor(Parcel in){
        versionName = in.readString();
        versionNumber = in.readString();
        image = in.readInt();
    }

    public  final Parcelable.Creator<AndroidFlavor> CREATOR = new Parcelable.Creator<AndroidFlavor>(){
        @Override
        public AndroidFlavor createFromParcel(Parcel source) {
            return new AndroidFlavor(source);
        }

        @Override
        public AndroidFlavor[] newArray(int size) {
            return new AndroidFlavor[size];
        }
    };

}
