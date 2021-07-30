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
    lateinit var totalCost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expenses: ListView = findViewById(R.id.expenses)
        totalCost = findViewById(R.id.totalExpensesAmount)
        val add: Button = findViewById(R.id.add)
        val reset: Button = findViewById(R.id.reset)

        loadExpenses()

        expenseAdapter = CustomExpenseAdapter(expensesList)
        expenses.adapter = expenseAdapter

        reset.setOnClickListener{
            resetList()
        }

        add.setOnClickListener{
            addExpense()
        }
    }

    private fun resetList(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle(getString(R.string.confirm_reset))
            setMessage(getString(R.string.confirm_message))

            setPositiveButton("YES"){
                _, _ ->
                expensesList.clear()
                saveExpenses()
            }

            setNegativeButton("NO"){
                _, _ ->
            }
           show()
        }
    }

    private  fun addExpense(){
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.expense_add, null)
        val itemView = dialogView.findViewById<EditText>(R.id.item)
        val priceView = dialogView.findViewById<EditText>(R.id.price)
        itemView.error = "Field cannot be empty"
        priceView.error = "Field cannot be empty"

        with(builder){
            setView(dialogView)
            setTitle("Add Expense")

            setPositiveButton("ADD"){
                _, _ ->
                val item = itemView.text.toString()
                val price = priceView.text.toString()
                if(item.isNotBlank() && price.isNotBlank()){
                    expensesList.add(Expenses(item, price.toFloat()))
                    saveExpenses()
                }
            }

            setNegativeButton("CANCEL"){
                _, _ ->
            }
            show()
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
        totalCost.text = Expenses.total(expensesList)
        expenseAdapter.notifyDataSetChanged()
    }

    private fun loadExpenses(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val gson = Gson()
        val expenses = sharedPref.getStringSet(EXPENSE_KEY, null)
        expenses?.forEach{
            expensesList.add(gson.fromJson(it, Expenses::class.java))
        }
        totalCost.text = Expenses.total(expensesList)
    }
}