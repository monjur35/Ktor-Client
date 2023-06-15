package com.example.ktorclient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ktorclient.databinding.FragmentHomeBinding
import com.example.ktorclient.utils.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import nl.adaptivity.xmlutil.serialization.writeAsXML


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel:MyViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCharacters().observe(viewLifecycleOwner){resources->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    binding.tttttttttttttt.text=resources.result.results[0].name
      }
                is Resource.Error -> {
                    binding.progressBar.visibility=View.GONE
                    val errorMessage = resources.exception.message
                    Toast.makeText(requireContext(),"$errorMessage", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Handle other possible states or handle the null case
                }
            }

        }

    }


}