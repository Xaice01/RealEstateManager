package com.xavier_carpentier.realestatemanager.ui.listProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.xavier_carpentier.realestatemanager.databinding.FragmentListPropertyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPropertyFragment : Fragment() {

    private val listPropertyViewModel: ListPropertyViewModel by viewModels()

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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            // ⚠ Use `State.CREATED` (and not `State.STARTED`) to ensure the `registerForActivityResult` are functioning properly
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    listPropertyViewModel.uiState.collect(::onPropertyState)


                }
            }
        }
    }

    fun onPropertyState(state: ListPropertyUIState) {
        when (state) {
            is ListPropertyUIState.Empty -> {
                binding.textListPropertyEmpty.isVisible=true
                binding.textListPropertySuccess.isGone=true
                binding.progressBarListProperty.isGone=true
            }

            is ListPropertyUIState.Loading -> {
                binding.progressBarListProperty.isVisible=true
                binding.textListPropertySuccess.isGone=true
                binding.textListPropertyEmpty.isGone=true
            }

            is ListPropertyUIState.Success -> {
                binding.textListPropertyEmpty.isGone=true
                binding.textListPropertySuccess.isVisible=true
                binding.progressBarListProperty.isGone=true

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}