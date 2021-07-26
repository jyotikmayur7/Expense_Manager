package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expeses: ListView = findViewById(R.id.expenses)
        val expensesList = arrayOf("Groceries", "Transportation", "Rent", "Cell Phone", "Utility Bills", "Insurance")


    }
}