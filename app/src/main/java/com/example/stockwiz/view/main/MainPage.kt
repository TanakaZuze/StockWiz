package com.example.stockwiz.view.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stockwiz.InventoryDatabaseHelper
import com.example.stockwiz.model.Product

@Composable
fun MainPage(
    inventoryDbHelper: InventoryDatabaseHelper,
    modifier: Modifier = Modifier // Modifier for layout customization
) {
    // variables to hold the item details
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var itemLowStock by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var productList by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        productList = inventoryDbHelper.getAllProducts()
    }

    // main page UI with scrollable Column
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Make the Column scrollable
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("Welcome to StockWiz!", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Text field for item name
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Enter item name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Enter item quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = itemLowStock,
            onValueChange = { itemLowStock = it },
            label = { Text("Enter low stock threshold") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (itemName.isNotBlank() && itemQuantity.isNotBlank() && itemLowStock.isNotBlank()) {
                    // Converting inputs to integers
                    val quantity = itemQuantity.toIntOrNull() ?: 0
                    val lowStock = itemLowStock.toIntOrNull() ?: 0

                    // Adding item to the database
                    val success = inventoryDbHelper.insertProduct(itemName, quantity, lowStock)

                    if (success) {
                        message = "Item added successfully!"
                        productList = inventoryDbHelper.getAllProducts()
                    } else {
                        message = "Error adding item."
                    }

                    // Clearing input fields after adding
                    itemName = ""
                    itemQuantity = ""
                    itemLowStock = ""
                } else {
                    message = "Please fill in all fields."
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(message, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Display list of products
        productList.forEach { product ->
            ProductCard(product = product, onDelete = {
                inventoryDbHelper.deleteProduct(product.id)
                productList = inventoryDbHelper.getAllProducts()
            }, onUpdate = {})
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProductCard(product: Product, onDelete: () -> Unit, onUpdate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Name: ${product.name}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Quantity: ${product.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Low Stock: ${product.lowStockLevel}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row {
                Button(onClick = onUpdate) {
                    Text("Update")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onDelete) {
                    Text("Delete")
                }
            }
        }
    }
}
