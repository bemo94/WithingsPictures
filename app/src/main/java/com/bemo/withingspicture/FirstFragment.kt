package com.bemo.withingspicture

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bemo.withingspicture.databinding.FragmentFirstBinding
import com.bemo.withingspicture.infrastructure.PictureController
import com.bemo.withingspicture.infrastructure.PicturePresenter
import com.bemo.withingspicture.infrastructure.PictureView
import com.bemo.withingspicture.infrastructure.PictureViewModel
import com.bemo.withingspicture.interactor.PictureInteractor
import com.bemo.withingspicture.repository.PictureRepository
import kotlinx.android.synthetic.main.content_main.*

class FirstFragment : Fragment(), PictureView {

    private var _binding: FragmentFirstBinding? = null
    private var threadManager: CoroutineContextSwitcher = CoroutineContextSwitcher.newInstance()
    private lateinit var imageListAdapter: ImageListAdapter
    private val controller = PictureController(
        PictureInteractor(
            PictureRepository(),
            PicturePresenter(this)
        )
    )

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagesRecyclerView.setHasFixedSize(true)
        binding.contentViewFlipper.visibility = GONE
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            binding.contentViewFlipper.visibility = VISIBLE
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyBoard()
                binding.contentViewFlipper.displayedChild = 0
                threadManager.onBackgroundThread {
                    controller.getPictures(binding.searchEditText.text.toString())
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun displayPicture(pictureViewModel: PictureViewModel) = threadManager.onMainThread {
        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.imagesRecyclerView.layoutManager = layoutManager
        imageListAdapter = ImageListAdapter(requireContext(), pictureViewModel.imagesUrl)
        binding.imagesRecyclerView.adapter = imageListAdapter
        binding.validateButton.visibility = VISIBLE
        binding.validateButton.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, SecondFragment.newInstance(imageListAdapter.getSelectedImages())).commit()
        }
        binding.contentViewFlipper.displayedChild = 1
    }

    override fun displayEmpty() = threadManager.onMainThread {
        binding.validateButton.visibility = GONE
        binding.contentViewFlipper.displayedChild = 2
    }

    private fun hideKeyBoard() {
        val iMm =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        binding.searchEditText.clearFocus()
    }
}