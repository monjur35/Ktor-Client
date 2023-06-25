package com.example.ktorclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import com.example.ktorclient.adapters.PagingAdapter
import com.example.ktorclient.databinding.FragmentHomePaginatedBinding
import com.example.ktorclient.viewModel.PagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragmentPaginated : Fragment() {
    private lateinit var binding:FragmentHomePaginatedBinding
    private val viewModel:PagingViewModel by viewModels()
    private lateinit var pagingAdapter: PagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomePaginatedBinding.inflate(inflater)
        pagingAdapter= PagingAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvWW.apply {
            adapter=pagingAdapter
        }

/*        binding.dsfs.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getAllCharacters().collectLatest {
                    binding.progressBar.visibility=View.GONE
                    Log.e("TAG", "onViewCreated: "+it.toString() )
                    pagingAdapter.submitData(it)
                    pagingAdapter.notifyDataSetChanged()
                }
            }
            Log.e("TAG", "onViewCreated: sadfsd")
            Toast.makeText(requireContext(), "UUUU", Toast.LENGTH_SHORT).show()
        }
        binding.dsfs.visibility=View.GONE*/

        lifecycleScope.launch {
            viewModel.getAllCharacters().collectLatest {
                binding.progressBar.visibility=View.GONE
                Log.e("TAG", "onViewCreated: "+it.toString() )
                pagingAdapter.submitData(it)
                pagingAdapter.notifyDataSetChanged()
            }
        }
    }
}