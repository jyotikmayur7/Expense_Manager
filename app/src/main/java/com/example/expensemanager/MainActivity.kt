package com.example.expensemanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

const val EXPENSE_KEY = "EXPENSE_KEY"

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

        reset.setOnClickListener{
            resetList()
        }

        expenseAdapter = CustomExpenseAdapter(expensesList)
        expenses.adapter = expenseAdapter

        totalCost.text = Expenses.total(expensesList)
    }

    private fun resetList(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.confirm_reset))
            setMessage(getString(R.string.confirm_message))

            setPositiveButton("YES"){
                dialog, which ->
                expensesList.clear()
                saveExpenses()
            }

            setNegativeButton("NO"){
                dialog, which ->
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun saveExpenses(){
        val gson = Gson()
        val expenses = expensesList.map { gson.toJson(it) }

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putStringSet(EXPENSE_KEY, expenses.toSet())
            commit()
        }
        expenseAdapter.notifyDataSetChanged()
    }
}