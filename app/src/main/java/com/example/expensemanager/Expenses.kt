package com.example.expensemanager

import java.text.NumberFormat
import java.util.*

data class Expenses(val item: String, val cost: Float){
    fun getFormattedPrice(): String = formatter.format(cost)

    companion object {
        private val formatter: NumberFormat = NumberFormat.getCurrencyInstance()

        init {
            formatter.currency = Currency.getInstance("INR")
        }

        fun total(expenses : ArrayList<Expenses>) : String {
            var total: Float = 0f
            expenses.forEach {
                total += it.cost
            }
            return formatter.format(total)
        }
    }
}
