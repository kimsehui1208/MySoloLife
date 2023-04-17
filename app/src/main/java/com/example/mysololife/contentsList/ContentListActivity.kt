package com.example.mysololife.contentsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysololife.R
import com.example.mysololife.utils.FBAuth
import com.example.mysololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ContentListActivity : AppCompatActivity() {

    lateinit var myRef : DatabaseReference

    val bookmarkIdList = mutableListOf<String>()

    lateinit var rvAdapter : ContentRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_list)

        val items = ArrayList<ContentModel>()
        val itemKeyList = ArrayList<String>()

        rvAdapter = ContentRVAdapter(baseContext, items, itemKeyList, bookmarkIdList)

        // Write a message to the database
        val database = Firebase.database

        val category = intent.getStringExtra("category")


        if(category == "category1"){

             myRef = database.getReference("contents")

        }else if(category == "category2"){

            myRef = database.getReference("contents2")

        }else if(category == "category3"){

            myRef = database.getReference("contents3")

        }else if(category == "category4"){

            myRef = database.getReference("contents4")

        }else if(category == "category5"){

            myRef = database.getReference("contents5")

        }else if(category == "category6"){

            myRef = database.getReference("contents6")

        }else if(category == "category7"){

            myRef = database.getReference("contents7")

        }else if(category == "category8"){

            myRef = database.getReference("contents8")

        }



        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for(dataModel in dataSnapshot.children){
                    Log.d("ContentListActivity",dataModel.toString())
                    Log.d("ContentListActivity",dataModel.key.toString())
                    val item = dataModel.getValue(ContentModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                rvAdapter.notifyDataSetChanged()//비동기식 방식에 적용되는 부분, recyclerview를 들어갈 수 있도록 해주는것이다.
                Log.d("ContentListActivity",items.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)


        val rv : RecyclerView = findViewById(R.id.rv)

        rv.adapter = rvAdapter

        rv.layoutManager = GridLayoutManager(this,2)

        getBookmarkData()

    }

    private fun getBookmarkData(){

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                bookmarkIdList.clear()

                for(dataModel in dataSnapshot.children){
                    bookmarkIdList.add(dataModel.key.toString())
                }
                Log.d("ContentListActivity",bookmarkIdList.toString())
                rvAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)


    }


}