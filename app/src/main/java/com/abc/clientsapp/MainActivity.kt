package com.abc.clientsapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.abc.clientsapp.Const.WHAT
import com.abc.clientsapp.Const.ADD
import com.abc.clientsapp.Database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var adapter = ClientAdapter(this@MainActivity)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainClientList.adapter = adapter
        fab1.setOnClickListener {
            var intent = Intent(this@MainActivity,Add_or_Edit_Clients::class.java)
            intent.putExtra(WHAT,ADD)
            startActivity(intent)
        }

    }
    class ClientAdapter() : BaseAdapter() {
        private var list:ArrayList<Client> ?= null
        private var context:Context? = null
        constructor(context: Context?) : this()
        {
            this.list = DatabaseHelper.getinstnce(context!!).getallclients()
            this.context = context
        }
        fun updatelist() {
            this.list = DatabaseHelper.getinstnce(context!!).getallclients()
            notifyDataSetChanged()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            var convertView : View? = view
            if (convertView == null){
            convertView = View.inflate(context, R.layout.item_clients, null)}
            var txtId : TextView = convertView?.findViewById(R.id.clientTxtId) as TextView
            var txtName : TextView = convertView.findViewById(R.id.clientTxtName) as TextView
            var txtAddress : TextView = convertView.findViewById(R.id.clientTxtAddress) as TextView
            var imgEdt: ImageView = convertView.findViewById(R.id.edt_client) as ImageView
            var imgDlt: ImageView = convertView.findViewById(R.id.dlt_client) as ImageView
            txtId.text = list?.get(position)?.id.toString()
            txtName.text = list?.get(position)?.name
            txtAddress.text = list?.get(position)?.address
            imgEdt.setOnClickListener {

                var intent = Intent(context, Add_or_Edit_Clients::class.java)
                intent.putExtra(Const.WHAT, Const.UPDATE)
                intent.putExtra(Const.CLIENT_KEY, (list?.get(position)) as Parcelable)
                context?.startActivity(intent)

            }
            imgDlt.setOnClickListener {

                DatabaseHelper.getinstnce(context!!).deleteclient(list?.get(position)?.id)
                list?.removeAt(position)
                notifyDataSetChanged()
            }
            return convertView
        }
        override fun getItem(position: Int): Client? {
            return list?.get(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return list!!.size
        }
    }

}

