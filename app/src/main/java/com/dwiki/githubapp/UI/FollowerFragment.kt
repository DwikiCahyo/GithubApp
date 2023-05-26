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
import com.dwiki.githubapp.ViewModel.FollowerViewModel
import com.dwiki.githubapp.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel:FollowerViewModel by viewModels()
    private lateinit var username:String
    private lateinit var userAdapter: ListUserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View{
       _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username = arguments?.getString(DetailActivity.USERNAME).toString()

        userAdapter = ListUserAdapter(ArrayList())


        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.getfollowerData(username)
        viewModel.getListFollower().observe(viewLifecycleOwner){
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
            rvFollower.setHasFixedSize(true)
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.adapter = userAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvFollower.visibility = View.GONE
                pgFollower.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvFollower.visibility = View.VISIBLE
                pgFollower.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}