package com.melodie.parotia.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.melodie.parotia.databinding.FragmentUserContentBinding

abstract class UserContentFragment<T : Any, VH : RecyclerView.ViewHolder> : Fragment() {
    protected lateinit var binding: FragmentUserContentBinding
    protected lateinit var adapter: PagingDataAdapter<T, VH>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserContentBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.recyclerView)
        adapter = createAdapter()
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribe()
    }

    abstract fun setupRecyclerView(view: RecyclerView)

    abstract fun createAdapter(): PagingDataAdapter<T, VH>

    abstract fun subscribe()
}
