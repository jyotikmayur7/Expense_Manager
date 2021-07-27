package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expenses: ListView = findViewById(R.id.expenses)
        val totalCost: TextView = findViewById(R.id.totalExpensesAmount)

        val expensesList= arrayListOf(Expenses("Groceries",5000f),
                                Expenses("Transportation", 8000f),
                                Expenses("Rent", 50000f),
                                Expenses("Cell Phone",800f),
                                Expenses("Utility Bills", 6500f),
                                Expenses("Insurance", 5000f))

        expenses.adapter = CustomExpenseAdapter(expensesList)

        totalCost.text = Expenses.total(expensesList)

    }
}