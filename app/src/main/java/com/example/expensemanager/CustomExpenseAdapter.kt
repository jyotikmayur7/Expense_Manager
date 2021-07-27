package com.example.expensemanager

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

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

    override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
        val expenseView: View
        val viewHolder: ViewHolder

        if(convertView == null){
            expenseView = LayoutInflater.from(container.context).inflate(R.layout.list_item,container,false)
            viewHolder = ViewHolder()
            with(viewHolder){
                item = expenseView.findViewById(R.id.item)
                cost = expenseView.findViewById(R.id.cost)
                expenseView.tag = this
            }
        }
        else{
            expenseView = convertView
            viewHolder = expenseView.tag as ViewHolder
        }

        with(viewHolder){
            item.text = expenses.get(position).item
            cost.text = expenses.get(position).cost.toString()
        }

        return expenseView
    }

    private class ViewHolder{
        lateinit var item: TextView
        lateinit var cost: TextView
    }
}