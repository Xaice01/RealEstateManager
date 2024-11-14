package com.xavier_carpentier.realestatemanager.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.xavier_carpentier.realestatemanager.R
import com.xavier_carpentier.realestatemanager.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val agent = resources.getStringArray(R.array.agent_names)
        val spinner = binding.agentSpinner

        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, agent)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCurrency.collect { currency ->
                    when (currency?.displayName) {
                        "Euro" -> binding.radioEuro.isChecked = true
                        "Dollar" -> binding.radioDollar.isChecked = true
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentAgent.collect { agent ->
                    val position =
                        (binding.agentSpinner.adapter as ArrayAdapter<String>).getPosition(agent?.second)
                    binding.agentSpinner.setSelection(position)
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentDollarRate.collect { rate ->
                    rate?.let {
                        binding.dollarRateEditText.setTextKeepState(it.toString())
                    } ?: run {
                        binding.dollarRateEditText.setTextKeepState("")
                    }
                }
            }
        }


        binding.validateButton.setOnClickListener {
            //TODO save the info
            Toast.makeText(requireContext(),"info save",Toast.LENGTH_LONG).show()



            //val selectedCurrency = if (binding.radioEuro.isChecked) "Euro" else "Dollar"
            //val selectedAgent = binding.agentSpinner.selectedItem as String
            //val dollarRate = binding.dollarRateEditText.text.toString()
//
            //viewModel.setSelectedCurrency(selectedCurrency)
            //viewModel.setAgent(selectedAgent)
            //viewModel.setDollarRate(dollarRate)

            // Afficher les choix sélectionnés (vous pouvez également passer ces valeurs à une autre activité ou fragment)
            //println("Devise choisie : $selectedCurrency")
            //println("Agent choisi : $selectedAgent")
            //println("Cours du dollar : $dollarRate")

            // Fermer le DialogFragment
            parentFragmentManager.popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}