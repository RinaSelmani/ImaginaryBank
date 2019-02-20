package com.imaginarybank.imaginarybank.ui.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imaginarybank.imaginarybank.R
import com.imaginarybank.imaginarybank.databinding.ListFragmentBinding

/**
 * Created by RinaSelmani on 17-Feb-19.
 */
class ListLocationsFragment : Fragment() {
    lateinit var listAdapter: ListAdapter
    lateinit var binding: ListFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        listAdapter = ListAdapter()
        binding.listRecycleview.layoutManager = LinearLayoutManager(context)
        binding.model = ListLocationViewModel(binding, listAdapter)
        return binding.root
    }
}