package com.example.mrtayyab.sqlitedbkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    lateinit var mAddBtn: Button
    lateinit var mViewBtn: Button
    lateinit var mViewAll: Button
    lateinit var mUpdatedBtn: Button
    lateinit var myDeleteBtn: Button

    lateinit var mId : EditText
    lateinit var mName :EditText
    lateinit var mProfession : EditText
    lateinit var mSalary : EditText

    lateinit var myDb: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mAddBtn =findViewById(R.id.myAddBtn)
        mViewBtn=findViewById(R.id.myViewBtn)
        mViewAll =findViewById(R.id.myViewAllBtn)
        mUpdatedBtn =findViewById(R.id.myUpdateBtn)
        myDeleteBtn =findViewById(R.id.myDeleteBtn)

        mId =findViewById(R.id.myId)
        mName =findViewById(R.id.myName)
        mProfession =findViewById(R.id.myProfession)
        mSalary =findViewById(R.id.mySalary)

        myDb = DatabaseHelper(this)


        AddData()
        getData()
        updateData()
        deleteData()
        viewAllData()


    }



    fun AddData() {

        mAddBtn.setOnClickListener(View.OnClickListener {

            val name = mName.text.toString().trim()
            val profession = mProfession.text.toString().trim()
            val salary = mSalary.text.toString().trim()


            if (TextUtils.isEmpty(name)) {
                mName.error = "Enter name"
                return@OnClickListener
            }

            if (TextUtils.isEmpty(profession)) {
                mProfession.error = "Enter profession"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(salary)) {
                mSalary.error = "Enter salary"
                return@OnClickListener
            }

            val isInserted = myDb.intertData(name, profession, salary)

            if (isInserted == true) {
                Toast.makeText(applicationContext, "Data inserted ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Data could not be inserted ", Toast.LENGTH_SHORT).show()

            }
        })

    }

    fun viewAllData() {

        mViewAll.setOnClickListener(View.OnClickListener {
            val res = myDb.getAllData()

            if (res.getCount() == 0) {
                showMessage("Error ", "Nothing found")
                return@OnClickListener

            } else {
                val buffer = StringBuffer()
                while (res.moveToNext()) {
                    buffer.append("Id:" + res.getString(0) + "\n")
                    buffer.append("Name: " + res.getString(1) + "\n\n")
                    buffer.append("Profession: " + res.getString(2) + "\n\n")
                    buffer.append("Salary: " + res.getString(3) + "\n\n")

                }

                showMessage("Data", buffer.toString())
            }
        })


    }


    fun deleteData() {
        myDeleteBtn.setOnClickListener(View.OnClickListener {
            val id = mId.getText().toString().trim()
            if (TextUtils.isEmpty(id)) {
                mName.error = "Enter id"
            }

            val deleterows = myDb.daleteData(id)
            if (deleterows!! > 0 ) {
                Toast.makeText(applicationContext, "Data deleted ", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, "Data could not deleted ", Toast.LENGTH_SHORT).show()

            }
        })

    }

    fun updateData() {

        mUpdatedBtn.setOnClickListener(View.OnClickListener {
            val id = mId.getText().toString().trim()
            val name = mName.text.toString().trim()
            val profession = mProfession.text.toString().trim()
            val salary = mSalary.text.toString().trim()

            if (TextUtils.isEmpty(id)) {
                mName.error = "Enter id"
                return@OnClickListener
            }

            if (TextUtils.isEmpty(name)) {
                mName.error = "Enter name"
                return@OnClickListener
            }

            if (TextUtils.isEmpty(profession)) {
                mProfession.error = "Enter profession"
                return@OnClickListener
            }
            if (TextUtils.isEmpty(salary)) {
                mSalary.error = "Enter salary"
                return@OnClickListener
            }

            val isUpdated = myDb.updateData(id, name, profession, salary)
            if (isUpdated == true) {
                Toast.makeText(applicationContext, "Data updated ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Data could not be updated ", Toast.LENGTH_SHORT).show()

            }
        })


    }




//// ---- GET DATA


    fun getData() {
        mViewBtn.setOnClickListener(View.OnClickListener {
            val id = mId.getText().toString().trim()

            if (TextUtils.isEmpty(id)) {
                mId.setError("Enter ID")
                return@OnClickListener
            }

            val res = myDb.getData(id)

            var data: String? = null

            if (res.moveToFirst()) {
                data = "Id:" + res.getString(0) + "\n" +
                        "Name:" + res.getString(1) + "\n" +
                        "Profession:" + res.getString(2) + "\n" +
                        "Salary:" + res.getString(3) + "\n"
            }

            showMessage("Data", data)
        })
    }


    private fun showMessage(title: String, message: String?) {

        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }


}
