package com.example.expensemanager

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class CustomExpenseAdapter(val expenses: ArrayList<Expenses>): BaseAdapter() {
    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(position: Int): Any {
        return expenses.get(position)
    }

    override fun getItemId(position: Int): Long {
        return expenses.get(position).item.hashCode().toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}