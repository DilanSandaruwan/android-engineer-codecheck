package jp.co.yumemi.android.code_check.ui.fragment.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constant.StringConstant
import jp.co.yumemi.android.code_check.databinding.FragmentBookmarkBinding
import jp.co.yumemi.android.code_check.model.AlertDialogResource
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem
import jp.co.yumemi.android.code_check.ui.adapter.BookmarkedGitHubRepoRecyclerViewAdapter
import jp.co.yumemi.android.code_check.ui.viewmodel.bookmark.BookmarkViewModel
import jp.co.yumemi.android.code_check.util.component.DialogUtil.showAlertDialog

/**
 * Fragment responsible for displaying bookmarked GitHub repositories.
 */
@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    lateinit var binding: FragmentBookmarkBinding
    lateinit var viewModel: BookmarkViewModel
    private lateinit var adapter: BookmarkedGitHubRepoRecyclerViewAdapter

    /**
     * Initializes the fragment's view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment UI should be attached to.
     * @param savedInstanceState The previously saved state, if any.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentBookmarkBinding.inflate(inflater, container, false).apply {
            // Data Binding setup
            binding = this
            ViewModelProvider(requireActivity())[BookmarkViewModel::class.java].apply {
                viewModel = this
                bookmarkVM = this
            }

            lifecycleOwner = this@BookmarkFragment
        }
        return binding.root
    }

    /**
     * Called when the view hierarchy associated with the fragment is created.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The previously saved state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        // Initialize and configure the RecyclerView for displaying GitHub repositories.
        setupRecyclerView()
    }

    /**
     * Sets up the RecyclerView for displaying bookmarked GitHub repositories.
     */
    private fun setupRecyclerView() {
        // Create a LinearLayoutManager to manage the layout of items in the RecyclerView.
        val repoSearchLayoutManager = LinearLayoutManager(requireContext())

        // Add a divider between RecyclerView items for improved visual separation.
        val repoSearchDividerItemDecoration =
            DividerItemDecoration(requireContext(), repoSearchLayoutManager.orientation)

        // Initialize the RecyclerView adapter with an item click callback.
        adapter = BookmarkedGitHubRepoRecyclerViewAdapter(object :
            BookmarkedGitHubRepoRecyclerViewAdapter.OnItemClickListener {
            override fun itemClick(item: BookmarkGithubRepoItem) {
                navigateToRepositoryInWebView(item)
            }

            override fun deleteClick(itemId: Long) {
                showAlertDialog(
                    AlertDialogResource(
                        title = getString(R.string.confirmation_title),
                        message = getString(R.string.are_you_sure_you_want_to_delete_this_bookmark),
                        positiveText = getString(R.string.confirm_yes),
                        negativeText = getString(R.string.confirm_no),
                        positiveClickListener = { viewModel.deleteFavourite(itemId) },
                        negativeClickListener = { },
                        iconResId = R.drawable.ic_dialog_info,
                        StringConstant.CONFIRM_DELETE_BOOKMARK
                    ), childFragmentManager
                )
            }
        },resources)

        with(binding.recyclerView) {
            this.layoutManager = repoSearchLayoutManager
            addItemDecoration(repoSearchDividerItemDecoration)
            adapter = this@BookmarkFragment.adapter
        }
    }

    /**
     * Navigates to the GitHub profile fragment to display the details of a bookmarked repository.
     *
     * @param item The bookmarked GitHub repository item.
     */
    fun navigateToRepositoryInWebView(item: BookmarkGithubRepoItem) {
        item.htmlUrl?.let {
            findNavController().navigate(
                BookmarkFragmentDirections.actionBookmarkFragmentToGithubProfileFragment(it)
            )
        }
    }

    /**
     * Initializes observers for LiveData objects.
     */
    private fun initObservers() {
        viewModel.apply {
            bookmarkedReposAll?.observe(viewLifecycleOwner) {
                it?.let {
                    if (it.isNotEmpty()){
                        binding.recyclerView.visibility = VISIBLE
                        binding.lytNoBookmarksFound.visibility = GONE

                    } else {
                        binding.recyclerView.visibility = GONE
                        binding.lytNoBookmarksFound.visibility = VISIBLE
                    }
                }
                adapter.submitList(it)
            }

            localDbResponse.observe(viewLifecycleOwner) {
                it?.let {
                    when (it.isSuccess) {
                        true -> {
                            showAlertDialog(
                                AlertDialogResource(
                                    title = getString(R.string.success_title),
                                    message = getString(R.string.successfully_deleted),
                                    positiveText = getString(R.string.response_ok),
                                    positiveClickListener = { viewModel.resetLocalDbResponse() },
                                    negativeClickListener = { },
                                    iconResId = R.drawable.ic_dialog_success,
                                    tag = StringConstant.CONFIRM_DELETE_BOOKMARK_SUCCESS
                                ), childFragmentManager
                            )
                        }

                        false -> {
                            showAlertDialog(
                                AlertDialogResource(
                                    title = getString(R.string.error_title),
                                    message = getString(R.string.error_occurred_while_deleting),
                                    positiveText = getString(R.string.response_cancel),
                                    positiveClickListener = { viewModel.resetLocalDbResponse() },
                                    negativeClickListener = { },
                                    iconResId = R.drawable.ic_dialog_error,
                                    tag = StringConstant.CONFIRM_DELETE_BOOKMARK_ERROR
                                ), childFragmentManager
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when the fragment's view is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.apply {
            localDbResponse.removeObservers(viewLifecycleOwner)
            bookmarkedReposAll?.removeObservers(viewLifecycleOwner)
        }
    }

}