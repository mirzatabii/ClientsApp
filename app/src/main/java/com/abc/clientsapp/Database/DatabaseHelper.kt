package com.abc.clientsapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.abc.clientsapp.Client
import com.abc.clientsapp.Const.DATABASE_NAME
import com.abc.clientsapp.Const.DATABASE_VERSION
import kotlin.collections.ArrayList

class DatabaseHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    val CLIENT_TABLE : String = "client_Table"
    val CLIENT_ID : String = "id"
    val CLIENT_NAME : String = "name"
    val CLIENT_ADDRESS : String = "address"
    companion object{
                        private var instance : DatabaseHelper? = null
                        fun getinstnce(ctx:Context):DatabaseHelper
                        {
                            if (instance==null)
                            {
                                instance = DatabaseHelper(ctx, DATABASE_NAME,null, DATABASE_VERSION)
                            }
                            return instance!!
                }
    }
    val createsql : String = "create table $CLIENT_TABLE($CLIENT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CLIENT_NAME TEXT , $CLIENT_ADDRESS TEXT)"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createsql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CLIENT_TABLE")
        onCreate(db)
    }
    fun addclient(name: String?,address:String?):Long
    {
        val values= ContentValues()
        values.put(CLIENT_NAME,name)
        values.put(CLIENT_ADDRESS,address)
        return this.writableDatabase.insert(CLIENT_TABLE,null,values)
    }
    fun updateclient(id:Int?,name: String?,address:String?):Int
    {
        val values = ContentValues()
        values.put(CLIENT_NAME,name)
        values.put(CLIENT_ADDRESS,address)
        return this.writableDatabase.update(CLIENT_TABLE,values,"$CLIENT_ID=?", arrayOf("$id"))
    }
    fun deleteclient(id: Int?):Int
    {
        return this.writableDatabase.delete(CLIENT_TABLE,"$CLIENT_ID=?", arrayOf("$id"))
    }
    fun getallclients() :ArrayList<Client>
    {
        var list: ArrayList<Client> = ArrayList()
        var cursor:Cursor = this.writableDatabase.query(CLIENT_TABLE, arrayOf(CLIENT_ID,CLIENT_NAME,CLIENT_ADDRESS),null,null,null,null,null)
        if (cursor.moveToFirst())
        {
            do {
                var cl = Client()
                cl.id = cursor.getInt(0)
                cl.name = cursor.getString(1)
                cl.address = cursor.getString(2)
                list.add(cl)
            }while (cursor.moveToNext())
        }
        this.writableDatabase.close()
        return list
    }
}