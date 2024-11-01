package com.example.stockwiz

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.stockwiz.model.Product

class InventoryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT, $COLUMN_QUANTITY INTEGER, $COLUMN_LOW_STOCK INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertProduct(name: String, quantity: Int, lowStock: Int): Boolean {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(COLUMN_NAME, name)
                put(COLUMN_QUANTITY, quantity)
                put(COLUMN_LOW_STOCK, lowStock)
            }
            return db.insert(TABLE_NAME, null, values) != -1L
        }
    }

    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM $TABLE_NAME", null).use { cursor ->
                while (cursor.moveToNext()) {
                    productList.add(
                        Product(
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LOW_STOCK))
                        )
                    )
                }
            }
        }
        return productList
    }

    fun deleteProduct(id: Int): Boolean {
        writableDatabase.use { db ->
            return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString())) > 0
        }
    }

    companion object {
        private const val DATABASE_NAME = "inventory.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "products"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_QUANTITY = "quantity"
        private const val COLUMN_LOW_STOCK = "low_stock"
    }
}
