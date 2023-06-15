package com.example.ktorclient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.ktorclient.databinding.ItemRowBinding
import com.example.ktorclient.response.allCharacterResponse.Result

class RvAdapter (private val list: List<Result>):RecyclerView.Adapter<RvAdapter.RvViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
       val view=ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RvViewHolder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
       holder.bind(list[position])
    }



    inner class RvViewHolder(private val binding:ItemRowBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(result: Result) {
            binding.img.load(result.image)
        }
    }
}
