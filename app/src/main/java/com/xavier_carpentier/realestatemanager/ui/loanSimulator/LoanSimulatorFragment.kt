package com.xavier_carpentier.realestatemanager.ui.loanSimulator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.xavier_carpentier.realestatemanager.databinding.FragmentLoanSimulatorBinding
import com.xavier_carpentier.realestatemanager.ui.create.CreatePropertyViewModel

class LoanSimulatorFragment : Fragment() {

    private var _binding: FragmentLoanSimulatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loanSimulatorViewModel =
            ViewModelProvider(this).get(LoanSimulatorViewModel::class.java)


        _binding = FragmentLoanSimulatorBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}