package com.example.ministery.MainScreen;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageFirstScreen  implements Parcelable {

    private int image;

    public ImageFirstScreen(int image) {
        this.image = image;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    protected ImageFirstScreen(Parcel in) {
    }

    public static final Creator<ImageFirstScreen> CREATOR = new Creator<ImageFirstScreen> () {
        @Override
        public ImageFirstScreen createFromParcel(Parcel in) {
            return new ImageFirstScreen ( in );
        }

        @Override
        public ImageFirstScreen[] newArray(int size) {
            return new ImageFirstScreen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
