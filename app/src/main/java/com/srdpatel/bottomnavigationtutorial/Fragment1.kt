package com.srdpatel.bottomnavigationtutorial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.srdpatel.bottomnavigationtutorial.AppConstants.LOG_APP_NAME
import com.srdpatel.bottomnavigationtutorial.databinding.FragmentFragment1Binding

class Fragment1 : Fragment() {

    private var binding: FragmentFragment1Binding? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(AppConstants.LOG_APP_NAME + "Fragment1", "onCreate: " + AppConstants.LOG_APP_NAME)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFragment1Binding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setClickListeners()
    }

    private fun setClickListeners() {
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setClickListeners: ")
        binding?.idButtonPlusOne?.setOnClickListener {
            Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setClickListeners: onClickedPlusOne")
            sharedViewModel.increaseNumber()
        }
    }

    private fun setObserver() {
        Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setObserver: ")
        // FIXME srdpatel: 11/03/22 If we use "this" as the owner, we will get duplicate observers everytime.
        sharedViewModel.liveDataTitle.observe(viewLifecycleOwner) {
            Log.d(" :$LOG_APP_NAME: ", "Fragment1: :setObserver: onChanged: $it")
            binding?.idTextViewNumber?.text = it?.toString()
        }
    }

    override fun onDestroyView() {
        Log.d(
            AppConstants.LOG_APP_NAME + "Fragment1",
            "onDestroyView: " + AppConstants.LOG_APP_NAME
        )
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d(AppConstants.LOG_APP_NAME + "Fragment1", "onDetach: " + AppConstants.LOG_APP_NAME)
        super.onDetach()
    }
}