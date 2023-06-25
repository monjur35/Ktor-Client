package com.example.ktorclient.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ktorclient.databinding.ItemRowBinding
import com.example.ktorclient.response.allCharacterResponse.Result

class PagingAdapter :  PagingDataAdapter<Result, PagingAdapter.PagingViewHolder>(
DiffUtilCallback()
) {


    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
       holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        val view=ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagingViewHolder(view)
    }

    inner class PagingViewHolder(private val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: Result?) {
            Log.e("TAG", "bind: "+item?.name )
            binding.img.load(item?.image)
        }
    }


    class DiffUtilCallback: DiffUtil.ItemCallback<Result>(){

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.name==newItem.name
        }
    }

}
