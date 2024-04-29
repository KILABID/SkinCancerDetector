package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val label = intent.getStringExtra("label")
        val confidence = intent.getFloatExtra("probability", 0f)
        val imageUri = intent.getStringExtra("imageUri")
        binding.resultText.text = "$label ${confidence.toInt()}%"
        if (imageUri != null) {
            binding.resultImage.setImageURI(imageUri.toUri())
        }
        val listAdapter = ListItemAdapter()
        binding.rvHealth.layoutManager = LinearLayoutManager(this)
        binding.rvHealth.adapter = listAdapter
        listAdapter.setOnItemClickListener { item ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(item.url))
            startActivity(intent)
        }
        resultViewModel = ViewModelProvider(this)[ResultViewModel::class.java]
        resultViewModel.getHealthNews()
        resultViewModel.listItem.observe(this) { article ->
            listAdapter.submitList(article)
        }

    }
}