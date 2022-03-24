package com.srdpatel.bottomnavigationtutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.srdpatel.bottomnavigationtutorial.databinding.FragmentFragment3Binding
import kotlinx.coroutines.launch

class Fragment3 : Fragment() {
    private var binding: FragmentFragment3Binding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(AppConstants.LOG_APP_NAME + "Fragment3", "onCreate: " + AppConstants.LOG_APP_NAME)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment3Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        Log.d(" :${AppConstants.LOG_APP_NAME}: ", "Fragment3: :setClickListeners: ")
        binding?.idButtonPlusOne?.setOnClickListener {
            Log.d(" :${AppConstants.LOG_APP_NAME}: ", "Fragment3: :setClickListeners: onClickedPlusOne")
            sharedViewModel.increaseNumber()
        }

        binding?.idButtonNext?.setOnClickListener {
            findNavController().navigate(Fragment3Directions.actionFragment3ToFragment1())
        }
    }

    private fun setObserver() {
        Log.d(" :${AppConstants.LOG_APP_NAME}: ", "Fragment3: :setObserver: ")
        sharedViewModel.liveDataTitle.observe(this) {
            Log.d(" :${AppConstants.LOG_APP_NAME}: ", "Fragment3: :setObserver: onChanged: $it")
            binding?.idTextViewNumber?.text = it?.toString()
        }

        // comment by srdpatel: 22/03/22 Comment out "viewLifecycleOwner" while observing from "fragment as a lifecycleOwner" for proper conclusion.
        sharedViewModel.liveDataTitle.observe(viewLifecycleOwner) {
            Log.d(
                " :${AppConstants.LOG_APP_NAME}: ",
                "Fragment3: :setObserver: viewLifeCycleOwner: onChanged: $it"
            )
            binding?.idTextViewNumber?.text = it?.toString()
        }
        //endregion

        //region repeatOnLifeCycle
        lifecycleScope.launchWhenStarted {
            sharedViewModel.numbers.collect {
                Log.d(
                    " :${AppConstants.LOG_APP_NAME}: ",
                    "Fragment3: :setObserver: launchWhenStarted: collected: $it"
                )
                binding?.idTextViewNumber?.text = it.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.numbers.collect {
                    Log.d(
                        " :${AppConstants.LOG_APP_NAME}: ",
                        "Fragment3: :setObserver: repeatOnLifeCycle: collected: $it"
                    )
                    binding?.idTextViewNumber?.text = it.toString()
                }
            }
        }
        //endregion
    }

    override fun onDestroyView() {
        Log.d(
            AppConstants.LOG_APP_NAME + "Fragment3",
            "onDestroyView: " + AppConstants.LOG_APP_NAME
        )
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d(AppConstants.LOG_APP_NAME + "Fragment3", "onDetach: " + AppConstants.LOG_APP_NAME)
        super.onDetach()
    }
}