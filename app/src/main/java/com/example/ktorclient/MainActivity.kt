package com.example.ktorclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.ktorclient.utils.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      viewModel.getAllCharacters().observe(this){resource->
          Log.e("TAG", "onCreate: $resource")
          when (resource) {
              is Resource.Loading -> {
                  // Show loading indicator or perform any necessary UI updates
              }
              is Resource.Success -> {
                //  val allCharacterResponse = resource.data (not getting data from resource)
                  // Do something with the successful response, e.g., update UI
              }
              is Resource.Error -> {
                 // val errorMessage = resource.message
                  // Handle the error state, e.g., show an error message
              }
              else -> {
                  // Handle other possible states or handle the null case
              }
          }
        }

    }
}