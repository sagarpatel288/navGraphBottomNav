package com.srdpatel.bottomnavigationtutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.srdpatel.bottomnavigationtutorial.AppConstants.LOG_APP_NAME
import com.srdpatel.bottomnavigationtutorial.databinding.FragmentFragment1Binding
import kotlinx.coroutines.launch

class Fragment1 : Fragment() {

    private var binding: FragmentFragment1Binding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOG_APP_NAME + "Fragment1", "onCreate: " + LOG_APP_NAME)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :onCreateView: binding is: $binding")
        binding = FragmentFragment1Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :onViewCreated: ")
        setObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setClickListeners: ")
        binding?.idButtonPlusOne?.setOnClickListener {
            Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setClickListeners: onClickedPlusOne")
            sharedViewModel.increaseNumber()
        }

        binding?.idButtonNext?.setOnClickListener {
            findNavController().navigate(Fragment1Directions.actionFragment1ToFragment2())
        }
    }

    private fun setObserver() {
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setObserver: ")
        // FIXME srdpatel: 11/03/22 If we use "this" as the owner, we will get duplicate observers everytime.
        //region viewLifeCycleOwner
        /*sharedViewModel.liveDataTitle.observe(this) {
            Log.d(
                " :$LOG_APP_NAME: ",
                "Fragment1: :setObserver: fragment as a lifeCycleOwner: onChanged: $it"
            )
            binding?.idTextViewNumber?.text = it?.toString()
        }*/

        // comment by srdpatel: 22/03/22 Comment out "viewLifecycleOwner" while observing from "fragment as a lifecycleOwner" for proper conclusion.
        sharedViewModel.liveDataTitle.observe(viewLifecycleOwner) {
            Log.d(
                " :$LOG_APP_NAME: ",
                "Fragment1: :setObserver: viewLifeCycleOwner: onChanged: $it"
            )
            binding?.idTextViewNumber?.text = it?.toString()
        }
        //endregion

        //region repeatOnLifeCycle
        lifecycleScope.launchWhenStarted {
            sharedViewModel.numbers.collect {
                Log.d(
                    " :$LOG_APP_NAME: ",
                    "Fragment1: :setObserver: launchWhenStarted: collected: $it"
                )
                binding?.idTextViewNumber?.text = it.toString()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(State.STARTED) {
                sharedViewModel.numbers.collect {
                    Log.d(
                        " :$LOG_APP_NAME: ",
                        "Fragment1: :setObserver: repeatOnLifeCycle: collected: $it"
                    )
                    binding?.idTextViewNumber?.text = it.toString()
                }
            }
        }
        //endregion
    }

    override fun onDestroyView() {
        Log.d(
            LOG_APP_NAME + "Fragment1",
            "onDestroyView: $LOG_APP_NAME"
        )
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d(LOG_APP_NAME + "Fragment1", "onDetach: $LOG_APP_NAME")
        super.onDetach()
    }
}