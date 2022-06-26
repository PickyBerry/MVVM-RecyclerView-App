package com.example.recyclerviewexercise.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewexercise.UserActionListener
import com.example.recyclerviewexercise.UsersAdapter
import com.example.recyclerviewexercise.databinding.FragmentUsersListBinding
import com.example.recyclerviewexercise.model.User
import com.example.recyclerviewexercise.tasks.EmptyResult
import com.example.recyclerviewexercise.tasks.ErrorResult
import com.example.recyclerviewexercise.tasks.PendingResult
import com.example.recyclerviewexercise.tasks.SuccessResult

class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter
    private val viewModel: UsersListViewModel by viewModels { factory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(viewModel)

        viewModel.users.observe(viewLifecycleOwner, Observer {
            hideAll()
            when (it) {
                is SuccessResult -> {
                    binding.recyclerView.visibility=View.VISIBLE
                    adapter.users=it.data
                }
                is ErrorResult -> {
                    binding.tryAgainContainer.visibility=View.VISIBLE
                }
                is PendingResult -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
                is EmptyResult -> {
                    binding.noUsersTextView.visibility=View.VISIBLE
                }
            }
        })

        viewModel.actionShowDetails.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let{user->navigator().showDetails(user)}
        })
        viewModel.actionShowToast.observe(viewLifecycleOwner, Observer {
            it.getValue()?.let{messageRes->navigator().toast(messageRes)}
        })
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun hideAll(){
        binding.recyclerView.visibility=View.GONE
        binding.progressBar.visibility=View.GONE
        binding.tryAgainContainer.visibility=View.GONE
        binding.noUsersTextView.visibility=View.GONE
    }
}