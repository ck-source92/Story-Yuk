package com.dwicandra.storyyukk.ui.activity.fragment.post

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.databinding.FragmentPostBinding
import com.dwicandra.storyyukk.reduceFileImage
import com.dwicandra.storyyukk.rotateBitmap
import com.dwicandra.storyyukk.ui.HomeViewModelFactory
import com.dwicandra.storyyukk.ui.adapter.StoryPagingAdapter
import com.dwicandra.storyyukk.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null

    private val binding get() = _binding!!
    private val postViewModel by viewModels<PostViewModel> {
        HomeViewModelFactory.getInstance(
            requireContext()
        )
    }

    private lateinit var storyPagingAdapter: StoryPagingAdapter
    private var getFile: File? = null

    private var lastLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)

        storyPagingAdapter = StoryPagingAdapter()

        binding.camera.setOnClickListener { startTakePhoto() }
        binding.gallery.setOnClickListener { startGallery() }
        binding.upload.setOnClickListener { postStory() }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permReqLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun postStory() {
        when {
            getFile == null -> {
                Toast.makeText(
                    requireContext(),
                    "Images is Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.descEditText.text.isEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    "Description is Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                val description = binding.descEditText.text.toString()

                val text = description.toRequestBody("text/plain".toMediaType())

                val file = reduceFileImage(getFile as File)

                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                postViewModel.uploadImage(imageMultipart, text)
                findNavController().popBackStack()
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        com.dwicandra.storyyukk.createTempFile(requireContext()).also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.dwicandra.storyyukk",
                file
            )
            currentPhotoPath = file.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }

    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile

            binding.previewImageView.apply {
                setImageURI(selectedImg)
                background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.background_stroke_outline
                    )
                }
                clipToOutline = true
            }
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                false
            )
            binding.previewImageView.apply {
                setImageBitmap(result)
                background = context?.let { context ->
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.background_stroke_outline
                    )
                }
                clipToOutline = true
            }
        }
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val granted = REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }
            if (!granted) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

}