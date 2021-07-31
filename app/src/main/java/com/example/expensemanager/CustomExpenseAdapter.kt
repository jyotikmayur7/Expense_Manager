package com.example.expensemanager

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomExpenseAdapter(val expenses: List<Expenses>): RecyclerView.Adapter<CustomExpenseAdapter.ExpenseViewHolder>() {


    class ExpenseViewHolder(view: View): RecyclerView.ViewHolder(view){
        val item = view.findViewById<TextView>(R.id.item)
        val cost = view.findViewById<TextView>(R.id.cost)
    }

    override fun getItemId(position: Int): Long {
        return expenses.get(position).item.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ExpenseViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.cost.text = expenses[position].getFormattedPrice()
        holder.item.text = expenses[position].item
    }

    override fun getItemCount(): Int {
        return expenses.size
    }


}