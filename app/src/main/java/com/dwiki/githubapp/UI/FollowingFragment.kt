package com.dwiki.githubapp.UI

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.githubapp.Adapter.ListUserAdapter
import com.dwiki.githubapp.Model.ListUserResponse
import com.dwiki.githubapp.ViewModel.FollowingViewModel
import com.dwiki.githubapp.databinding.FragmentFollowingBinding


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var username:String
    private lateinit var userAdapter: ListUserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        _binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username = arguments?.getString(DetailActivity.USERNAME).toString()
        userAdapter = ListUserAdapter(ArrayList())

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.getfollowingData(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner){
            if (it != null) userAdapter.getItem(it)
        }
        setAdapter()
        userAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListUserResponse) {
                activity?.let {
                    showDetailFollowing(it, data)
                }
            }
        })
    }

    private fun showDetailFollowing(
        it: FragmentActivity,
        data: ListUserResponse
    ) {
        val intent = Intent(it, DetailActivity::class.java)
        intent.putExtra("Data", data)
        it.startActivity(intent)
    }

    private fun setAdapter() {
        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvFollowing.visibility = View.GONE
                pgFollowing.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvFollowing.visibility = View.VISIBLE
                pgFollowing.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}