package com.xavier_carpentier.realestatemanager.ui.listProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xavier_carpentier.realestatemanager.databinding.FragmentListPropertyBinding

class ListPropertyFragment : Fragment() {

    private var _binding: FragmentListPropertyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listPropertyViewModel =
            ViewModelProvider(this).get(ListPropertyViewModel::class.java)

        _binding = FragmentListPropertyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textListProperty
        listPropertyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}