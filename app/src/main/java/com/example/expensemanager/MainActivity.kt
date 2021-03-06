package com.example.expensemanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

const val EXPENSE_KEY = "EXPENSE_KEY"

class MainActivity : AppCompatActivity() {
    val expensesList = mutableListOf<Expenses>()
    lateinit var expenseAdapter: CustomExpenseAdapter
    lateinit var totalCost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expenses = findViewById<RecyclerView>(R.id.expenses).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

            expenseAdapter = CustomExpenseAdapter(expensesList)
            adapter = expenseAdapter.apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
        }
        totalCost = findViewById(R.id.totalExpensesAmount)
        val add: Button = findViewById(R.id.add)
        val reset: Button = findViewById(R.id.reset)

        loadExpenses()

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
        setErrorListener(itemView)
        setErrorListener(priceView)

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
                else{
                    Toast.makeText(this@MainActivity,"Invalid Input", Toast.LENGTH_SHORT).show()
                }
            }

            setNegativeButton("CANCEL"){
                _, _ ->
            }
            show()
        }
    }

    private fun setErrorListener(editText: EditText){
        editText.error = if(editText.text.toString().isNotEmpty()) null else "Field Cannot be Empty"
        editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.error = if(editText.text.toString().isNotEmpty()) null else "Field Cannot be Empty"
            }
        })
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