package kr.co.lion.memoproject

import android.os.Parcel
import android.os.Parcelable

class MemoClass(var subtitle:String?, var date:String?, var content:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(subtitle)
        parcel.writeString(date)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoClass> {
        override fun createFromParcel(parcel: Parcel): MemoClass {
            return MemoClass(parcel)
        }

        override fun newArray(size: Int): Array<MemoClass?> {
            return arrayOfNulls(size)
        }
    }
}