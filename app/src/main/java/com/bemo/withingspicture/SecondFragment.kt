package com.bemo.withingspicture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bemo.withingspicture.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            parentFragmentManager.beginTransaction().addToBackStack(null)
            parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_main, FirstFragment()).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(urls: ArrayList<String>): SecondFragment {
            return SecondFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("EXTRA_URLS", urls)
                }
            }
        }
    }
}