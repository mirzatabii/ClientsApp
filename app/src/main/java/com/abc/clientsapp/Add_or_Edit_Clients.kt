package com.abc.clientsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abc.clientsapp.Database.DatabaseHelper
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_or__edit__clients.*
import kotlinx.android.synthetic.main.item_clients.*

class Add_or_Edit_Clients : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or__edit__clients)
        if (intent.getStringExtra(Const.WHAT).equals(Const.ADD)){
            btn_add.text = "ADD"
        }
        else{
            btn_add.text = "UPDATE"
            var cli : Client = intent.getParcelableExtra(Const.CLIENT_KEY)
            clientTxtName.setText(cli.name)
            clientTxtAddress.setText(cli.address)
        }
        btn_add.setOnClickListener{
            if (intent.getStringExtra(Const.WHAT) == Const.ADD)
            {
                addclient()
                finish()
            }
            else {
                updateclient(intent.getParcelableExtra(Const.CLIENT_KEY))
            }
        }

    }
    fun logmsg(msg:String)
    {
        Log.d("TAG",msg)
    }
    fun validateinput():Boolean
    {
        if (clientTxtName?.text.toString().trim().equals("")||clientTxtAddress?.text.toString().trim().equals(""))
        {
            Toast.makeText(this,"Please Fill all the Fields", Toast.LENGTH_SHORT).show()
            return false
        }
        else
            return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!=null)
        {
            when(item.itemId)
            {
                android.R.id.home->finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun addclient()
    {
        if(validateinput())
        {
            DatabaseHelper.getinstnce(this).addclient(clientTxtName.text.toString(),clientTxtAddress.text.toString())
        }
    }
    fun updateclient(client: Client)
    {
        if(validateinput())
        {
            DatabaseHelper.getinstnce(this).updateclient(client.id,client.name,client.address)
        }
    }
}
