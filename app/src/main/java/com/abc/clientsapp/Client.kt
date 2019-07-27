package com.abc.clientsapp

import android.os.Parcel
import android.os.Parcelable
class Client :Parcelable {
    var id:Int = 0
    var name:String =""
    var address:String = ""
    constructor()
    constructor(id:Int,name:String,address: String)
    {
        this.id = id
        this.name = name
        this.address = address
    }

    override fun toString(): String {
        return "client_Table(id = '$id', name='$name', address = '$address')"
    }
    protected constructor(`in`: Parcel)  {
        id = `in`.readInt()
        name = `in`.readString()!!
        address = `in`.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Client> {
        override fun createFromParcel(parcel: Parcel): Client {
            return Client(parcel)
        }

        override fun newArray(size: Int): Array<Client?> {
            return arrayOfNulls(size)
        }
    }
}