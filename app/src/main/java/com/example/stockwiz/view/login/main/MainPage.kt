package com.example.stockwiz.view.login.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainPage() {
    // State for the product input dialog
    var showDialog by remember { mutableStateOf(false) }
    var stockName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var lowStockLevel by remember { mutableStateOf("") }

    // State to hold the list of products
    val productList = remember { mutableStateListOf<Product>() }

    // Main Column layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White), // Main background color
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        // Product List Header
        Text(text = "Product List", style = MaterialTheme.typography.headlineSmall)

        // Display Product Table
        ProductTable(products = productList)

        // Add Product Button
        Button(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.Start) // Move button to the bottom left corner
                .padding(16.dp)
        ) {
            Text(text = "Add Product")
        }

        // Product Input Dialog
        if (showDialog) {
            ProductInputDialog(
                onDismiss = { showDialog = false },
                onAddProduct = { name, qty, lowStock ->
                    productList.add(Product(name, qty.toInt(), lowStock.toInt()))
                    stockName = ""
                    quantity = ""
                    lowStockLevel = ""
                    showDialog = false
                },
                stockName = stockName,
                onStockNameChange = { stockName = it },
                quantity = quantity,
                onQuantityChange = { quantity = it },
                lowStockLevel = lowStockLevel,
                onLowStockLevelChange = { lowStockLevel = it }
            )
        }
    }
}

@Composable
fun ProductInputDialog(
    onDismiss: () -> Unit,
    onAddProduct: (String, String, String) -> Unit,
    stockName: String,
    onStockNameChange: (String) -> Unit,
    quantity: String,
    onQuantityChange: (String) -> Unit,
    lowStockLevel: String,
    onLowStockLevelChange: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Product") },
        text = {
            Column {
                OutlinedTextField(
                    value = stockName,
                    onValueChange = onStockNameChange,
                    label = { Text("Stock Name") }
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = onQuantityChange,
                    label = { Text("Quantity") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = lowStockLevel,
                    onValueChange = onLowStockLevelChange,
                    label = { Text("Low Stock Level") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAddProduct(stockName, quantity, lowStockLevel)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun ProductTable(products: List<Product>) {
    Column {
        // Header Row
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Stock Name", modifier = Modifier.weight(1f))
            Text(text = "Quantity", modifier = Modifier.weight(1f))
            Text(text = "Low Stock Level", modifier = Modifier.weight(1f))
        }
        Divider()

        // Product Rows
        products.forEach { product ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = product.name, modifier = Modifier.weight(1f))
                Text(text = product.quantity.toString(), modifier = Modifier.weight(1f))
                Text(text = product.lowStockLevel.toString(), modifier = Modifier.weight(1f))
            }
        }
    }
}

// Data class for Product
data class Product(val name: String, val quantity: Int, val lowStockLevel: Int)

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    MainPage()
}
