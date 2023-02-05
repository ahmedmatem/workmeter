package com.ahmedmatem.android.workmeter.ui.camera

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.ahmedmatem.android.workmeter.base.BaseFragment
import com.ahmedmatem.android.workmeter.base.NavigationCommand
import com.ahmedmatem.android.workmeter.databinding.FragmentCameraBinding
import com.ahmedmatem.android.workmeter.utils.clearFullScreen
import com.ahmedmatem.android.workmeter.utils.saveBitmapInGallery
import com.ahmedmatem.android.workmeter.utils.setFullScreen
import com.ahmedmatem.android.workmeter.R
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment() {
    override val viewModel: CameraViewModel by viewModels()

    private val args: CameraFragmentArgs by navArgs()

    private val permissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted ->
        if(isGranted){
            startCamera()
        } else {
            viewModel.showToast.value = "Permissions not granted."
            // TODO: Explain why permission is important to be granted
        }
    }

    private lateinit var binding: FragmentCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private var bitmap: Bitmap? = null
    private lateinit var captureSound: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)

        captureSound = MediaPlayer.create(requireContext(), R.raw.camera_shutter_click)

        if(allPermissionsGranted()){
            startCamera()
        } else {
            permissionsLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.apply {
            /**
             * Set onClickListener for camera buttons
             */

            captureButton.setOnClickListener {
                takePhoto()
            }

            resumeCameraButton.setOnClickListener {
                startCamera()
                updateUI(previewPaused = false)
                unlockScreen()
            }

            photoOkButton.setOnClickListener {
                bitmap?.let {bitmap ->
                    // Save photo in Gallery
                    val uri = saveBitmapInGallery(bitmap, FILENAME_FORMAT, RELATIVE_PATH)
                    // Save photo in local database
                    viewModel.savePhoto(args.worksheetId, uri.toString())
                    // Navigate back
                    viewModel.navigationCommand.value = NavigationCommand.Back
                }
            }
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isCameraShutterVisible.collect { visible ->
                    if(visible)
                        binding.cameraShutter.visibility = View.VISIBLE
                    else
                        binding.cameraShutter.visibility = View.GONE
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFullScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        clearFullScreen()
        unlockScreen()
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(requireContext()),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    /** Get bitmap from camera preview holder */
                    bitmap = binding.viewFinder.bitmap
                    
                    /** Steps on capture success */
                    viewModel.runCaptureEffect(CAMERA_SHUTTER_EFFECT_DURATION)
                    stopCamera()
                    captureSound.start()
                    updateUI(previewPaused = true)
                    lockScreen()
                }

                override fun onError(exception: ImageCaptureException) {
                    // TODO: Implement ImageCaptureException
                    // ...
                }
            }
        )
    }

    private fun updateUI(previewPaused: Boolean) {
        if(previewPaused) {
            // hide Capture button
            binding.captureButton.visibility = View.GONE
            // show Ok and Resume Camera buttons
            binding.photoOkButton.visibility = View.VISIBLE
            binding.resumeCameraButton.visibility = View.VISIBLE
        } else {
            // show Capture button
            binding.captureButton.visibility = View.VISIBLE
            // hide Ok and Resume Camera buttons
            binding.photoOkButton.visibility = View.GONE
            binding.resumeCameraButton.visibility = View.GONE
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            // Used to bind lifecycle of camera to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            //Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun stopCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
        cameraProvider.unbindAll()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission( requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun lockScreen() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
    }

    private fun unlockScreen() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    companion object {
        private const val TAG = "CameraFragment"
        private const val RELATIVE_PATH = "Pictures/Workmeter-Image"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val CAMERA_SHUTTER_EFFECT_DURATION : Long = 100L
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                /*Manifest.permission.RECORD_AUDIO*/
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}