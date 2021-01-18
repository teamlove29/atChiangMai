package com.alw.atchiangmai.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alw.atchiangmai.Model.CurrencyData
import com.alw.atchiangmai.R
import kotlinx.android.synthetic.main.currency_list.view.*

class ExchangeRecyclerAdapter(var currencyData: ArrayList<CurrencyData>) : RecyclerView.Adapter<ExchangeRecyclerAdapter.ExchangViewHolder>() {
    class ExchangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.currency_list,parent,false)
        return ExchangViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExchangViewHolder, position: Int) {
        val currency = currencyData[position]
        holder.itemView.textViewCurrency.text = currency.currency
        holder.itemView.textViewCurrencyThai.text = currency.thaiCurrency
    }

    override fun getItemCount(): Int {
        return currencyData.size
    }
}