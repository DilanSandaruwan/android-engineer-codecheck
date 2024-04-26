/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.MainActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentRepoDetailsBinding

class RepoDetailsFragment : Fragment(R.layout.fragment_repo_details) {

    private val args: RepoDetailsFragmentArgs by navArgs()

    private var binding: FragmentRepoDetailsBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentRepoDetailsBinding.bind(view)

        var item = args.item

        _binding.ownerIconView.load(item.ownerIconUrl);
        _binding.nameView.text = item.name;
        _binding.languageView.text = item.language;
        _binding.starsView.text = "${item.stargazersCount} stars";
        _binding.watchersView.text = "${item.watchersCount} watchers";
        _binding.forksView.text = "${item.forksCount} forks";
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }
}
