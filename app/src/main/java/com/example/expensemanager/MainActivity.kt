package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    val expensesList = mutableListOf<Expenses>()
    lateinit var expenseAdapter: CustomExpenseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expenses: ListView = findViewById(R.id.expenses)
        val totalCost: TextView = findViewById(R.id.totalExpensesAmount)
        val add: Button = findViewById(R.id.add)
        val reset: Button = findViewById(R.id.reset)


        expenseAdapter = CustomExpenseAdapter(expensesList)
        expenses.adapter = expenseAdapter

        totalCost.text = Expenses.total(expensesList)

    }
}