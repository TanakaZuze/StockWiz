package com.example.stockwiz.model

data class Product(
    val id: Int,
    val name: String,
    val quantity: Int,
    val lowStockLevel: Int
)
