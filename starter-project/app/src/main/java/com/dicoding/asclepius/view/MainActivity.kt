package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var classification: String
    private var probability: Float? = null

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.galleryButton.setOnClickListener {
            try {
                startGallery()
            } catch (e: Exception) {
                showToast("Gagal membuka gallery Error:${e}")
            }
        }
        binding.analyzeButton.setOnClickListener {
            if (currentImageUri != null) {
                analyzeImage()
            } else {
                showToast("Silahkan pilih gambar terlebih dahulu")
            }
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcherIntentGallery.launch(intent)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentImageUri = result.data?.data as Uri
            startCrop(currentImageUri)
        }
    }

    private fun startCrop(imageUri: Uri?) {
        val destinationUri = createOutputFileUri()
        if (imageUri != null) {
            UCrop.of(imageUri, destinationUri)
                .withAspectRatio(4f, 3f)
                .withMaxResultSize(224, 224)
                .start(this@MainActivity)
            showImage(imageUri)
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = data?.let { UCrop.getOutput(it) }
            showImage(currentImageUri)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(it) }
            showToast("Error: $cropError")
        }
    }

    private fun createOutputFileUri(): Uri {
        val filename = "cropped_image.png"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, filename)
        return file.toUri()
    }

    private fun showImage(currentImageUri: Uri?) {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        binding.previewImageView.setImageURI(currentImageUri)
    }


    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        try {
            val imageClassifierHelper = ImageClassifierHelper(
                context = this,
                modelName = "cancer_classification.tflite",
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResults(results: List<Classifications>?) {
                        runOnUiThread {
                            results?.let {
                                if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                    println(it)
                                    probability = it[0].categories[0].score * 100
                                    classification = it[0].categories[0].label
                                    moveToResult()
                                } else {
                                    showToast("Gagal menganalisis gambar")
                                }
                            }
                        }
                    }
                })
            currentImageUri?.let { imageClassifierHelper.classifyStaticImage(it) }
        } catch (e: Exception) {
            showToast("Gagal menganalisis gambar")
        }
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("imageUri", currentImageUri.toString())
        intent.putExtra("label", classification)
        intent.putExtra("probability", probability)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}