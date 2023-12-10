package com.itielfelix.examen1


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, "history.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table history (id INTEGER primary key autoincrement, player1 TEXT not null, croupier TEXT not null, winner TEXT not null, date TEXT not null)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteQuery = "drop table if exists history"
        db!!.execSQL(deleteQuery)
        onCreate(db)
    }
    fun addRow(p1: String, p2:String, winner: String, date: String){
        val rowInfo = ContentValues()
        rowInfo.put("player1",p1)
        rowInfo.put("croupier",p2)
        rowInfo.put("winner",winner)
        rowInfo.put("date",date)

        val db = this.writableDatabase
        db.insert("history",null,rowInfo)
        db.close()
    }
    @SuppressLint("Recycle")
    fun listData():MutableList<String>{
        val lista:MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val sql = "select * from history"
        val result = db.rawQuery(sql,null)
        if(result.moveToFirst()){
            do {
                var register = ""
                register = result.getString(result.getColumnIndex("id").toInt()) + "/" +result.getString(result.getColumnIndex("player1").toInt()) + "/" +
                        result.getString(result.getColumnIndex("croupier").toInt()) + "/" +result.getString(result.getColumnIndex("winner").toInt())+ "/" +result.getString(result.getColumnIndex("date").toInt())
                lista.add(register)

            }while(result.moveToNext())
            db.close()
        }
        return lista
    }

}