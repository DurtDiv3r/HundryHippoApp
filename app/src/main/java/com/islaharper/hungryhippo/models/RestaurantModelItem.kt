package com.islaharper.hungryhippo.models

import android.os.Parcel
import android.os.Parcelable

data class RestaurantModelItem(
    val address: String?,
    val delivery_charge: Int?,
    val hours: Hours?,
    val image: String?,
    var menus: List<Menu?>?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(Hours::class.java.classLoader),
        parcel.readString(),
        parcel.createTypedArrayList(Menu),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeValue(delivery_charge)
        parcel.writeParcelable(hours, flags)
        parcel.writeString(image)
        parcel.writeTypedList(menus)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RestaurantModelItem> {
        override fun createFromParcel(parcel: Parcel): RestaurantModelItem {
            return RestaurantModelItem(parcel)
        }

        override fun newArray(size: Int): Array<RestaurantModelItem?> {
            return arrayOfNulls(size)
        }
    }
}