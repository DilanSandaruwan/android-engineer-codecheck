/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.ui.adapter.GitHubRepoRecyclerViewAdapter
import jp.co.yumemi.android.code_check.ui.viewmodel.home.HomeViewModel
import jp.co.yumemi.android.code_check.ui.viewmodel.home.item

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        val _binding = FragmentHomeBinding.bind(view)


        val _viewModel = HomeViewModel(context!!)


        val _layoutManager = LinearLayoutManager(context!!)

        val _dividerItemDecoration =

            DividerItemDecoration(context!!, _layoutManager.orientation)

        val _adapter = GitHubRepoRecyclerViewAdapter(object :

            GitHubRepoRecyclerViewAdapter.OnItemClickListener {

            override fun itemClick(item: item) {

                gotoRepositoryFragment(item)

            }

        })




        _binding.searchInputText

            .setOnEditorActionListener { editText, action, _ ->

                if (action == EditorInfo.IME_ACTION_SEARCH) {

                    editText.text.toString().let {

                        _viewModel.searchResults(it).apply {

                            _adapter.submitList(this)

                        }

                    }

                    return@setOnEditorActionListener true

                }

                return@setOnEditorActionListener false

            }




        _binding.recyclerView.also {

            it.layoutManager = _layoutManager

            it.addItemDecoration(_dividerItemDecoration)

            it.adapter = _adapter

        }

    }


    fun gotoRepositoryFragment(item: item) {

        val _action =

            HomeFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)

        findNavController().navigate(_action)

    }

}