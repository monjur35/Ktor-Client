package com.example.ktorclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
                  Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show()
              }
              is Resource.Success -> {
                //  val allCharacterResponse = resource.data (not getting data from resource)
                  // Do something with the successful response, e.g., update UI
                  Toast.makeText(this,"${resource.result.results}",Toast.LENGTH_SHORT).show()
                  Log.e("TAG", "onCreate: ${resource.result.toString()}", )

              }
              is Resource.Error -> {
                  val errorMessage = resource.exception
                  Toast.makeText(this,"${errorMessage.message}",Toast.LENGTH_SHORT).show()
              }
              else -> {
                  // Handle other possible states or handle the null case
              }
          }
        }

    }
}