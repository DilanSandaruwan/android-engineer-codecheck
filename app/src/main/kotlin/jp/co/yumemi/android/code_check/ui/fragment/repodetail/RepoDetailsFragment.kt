/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragment.repodetail

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constant.StringConstant
import jp.co.yumemi.android.code_check.databinding.FragmentRepoDetailsBinding
import jp.co.yumemi.android.code_check.model.AlertDialogResource
import jp.co.yumemi.android.code_check.model.GitHubAccount
import jp.co.yumemi.android.code_check.ui.component.dialog.CustomDialogFragment
import jp.co.yumemi.android.code_check.ui.viewmodel.repodetail.RepoDetailsViewModel
import jp.co.yumemi.android.code_check.util.component.DialogUtil.showAlertDialog

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepoDetailsBinding
    private var isFabSheetOpen = false
    private var isBookmarked = false
    private lateinit var gitHubAccount: GitHubAccount

    /**
     * Initializes the ViewModel and NavArgs.
     */
    private val viewModel: RepoDetailsViewModel by viewModels()
    private val args: RepoDetailsFragmentArgs by navArgs()

    /**
     * Inflates the layout and initializes the ViewModel.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        gitHubAccount = args.repository
        isBookmarked = args.isBookmarked

        binding = FragmentRepoDetailsBinding.inflate(layoutInflater, container, false).apply {
            detailsVM = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    /**
     * Sets up the user interface and observes changes in the repository details.
     *
     * @param view The root view for the fragment.
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe changes in the repository details and update the UI
        viewModel.gitHubRepoDetails.observe(viewLifecycleOwner) { gitHubAccount ->
            binding.ownerIconView.load(gitHubAccount.owner?.avatarUrl) {
                crossfade(true)
                placeholder(R.drawable.placeholder_repository)
                transformations(CircleCropTransformation())
                listener(onError = { _, _ ->
                    placeholder(R.drawable.placeholder_repository)
                })
            }

            // Add an OnClickListener to the floating action button
            binding.navFloatingActionButton.setOnClickListener {
                toggleActionButtonPanel()
            }

            binding.fabOpenInBrowser.setOnClickListener {
                val url = gitHubAccount.htmlUrl
                if (gitHubAccount.htmlUrl.isNullOrBlank()) {
                    showErrorDialog(getString(R.string.url_not_found))
                } else {
                    //openUrlInBrowser(url!!)
                    navigateToProfileViewScreen(url!!)
                }
                toggleActionButtonPanel()
            }

            binding.fabBookmark.setOnClickListener {

                val confirmationMessage = viewModel.favouriteStatus.value?.let {
                    if (it) {
                        R.string.are_you_sure_you_want_to_delete_this_bookmark
                    } else {
                        R.string.are_you_sure_you_want_to_save_this_as_a_bookmark
                    }
                } ?: R.string.are_you_sure_you_want_to_save_this_as_a_bookmark

                val tagDialogFragment = viewModel.favouriteStatus.value?.let {
                    if (it) {
                        StringConstant.CONFIRM_DELETE_BOOKMARK
                    } else {
                        StringConstant.CONFIRM_ADD_BOOKMARK
                    }
                } ?: StringConstant.CONFIRM_ADD_BOOKMARK

                showAlertDialog(
                    AlertDialogResource(
                        title = getString(R.string.confirmation_title),
                        message = getString(confirmationMessage),
                        positiveText = getString(R.string.confirm_yes),
                        negativeText = getString(R.string.confirm_no),
                        positiveClickListener = {
                            when (viewModel.favouriteStatus.value) {
                                true -> viewModel.deleteFavourite(gitHubAccount.id)
                                else -> viewModel.saveAsBookmark()
                            }

                            toggleActionButtonPanel()
                        },
                        negativeClickListener = { },
                        iconResId = R.drawable.ic_dialog_info,
                        tag = tagDialogFragment
                    ), childFragmentManager
                )
            }
        }

        viewModel.favouriteStatus.observe(viewLifecycleOwner) {
            if (it) {
                binding.ivBookmarked.visibility = VISIBLE
                binding.fabBookmark.setImageResource(R.drawable.ic_bookmark_remove_48dp)
                binding.fabBookmark.imageTintList = ColorStateList.valueOf(
                    resources.getColor(
                        R.color.bookmarked_color,
                        null
                    )
                ) // Apply bookmarked color

            } else {
                binding.ivBookmarked.visibility = GONE
                binding.fabBookmark.setImageResource(R.drawable.ico_bookmark_add_24dp)
                binding.fabBookmark.imageTintList = ColorStateList.valueOf(
                    resources.getColor(
                        R.color.default_bookmark_color,
                        null
                    )
                ) // Apply default color
            }
        }

        viewModel.localDbResponse.observe(viewLifecycleOwner) {
            it?.let {
                when (it.isSuccess) {
                    true -> {
                        showAlertDialog(
                            AlertDialogResource(
                                title = getString(R.string.success_title),
                                message = it.message.toString(),
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
                                message = it.message.toString(),
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

        // Set the repository details in the ViewModel based on arguments passed to the fragment
        viewModel.setRepoDetails(args.repository)
        isBookmarked = when (savedInstanceState) {
            null -> args.isBookmarked
            else -> viewModel.favouriteStatus.value ?: false
        }
        viewModel.setFavouriteStatus(isBookmarked)
    }

    private fun toggleActionButtonPanel() {
        if (isFabSheetOpen) {
            binding.sheetFabAll.visibility = INVISIBLE
        } else {
            binding.sheetFabAll.visibility = VISIBLE
        }
        isFabSheetOpen = !isFabSheetOpen
    }

    /**
     * Opens a URL in a web browser.
     *
     * @param url The URL to open.
     */
    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val chooser = Intent.createChooser(intent, getString(R.string.select_a_browser))
        startActivity(chooser)
    }

    private fun navigateToProfileViewScreen(url: String) {
        findNavController().navigate(
            url.let {
                RepoDetailsFragmentDirections.actionRepoDetailsFragmentToGithubProfileFragment(
                    it
                )
            }
        )
    }

    /**
     * Displays an error dialog with a given error message.
     *
     * @param errMsg The error message to be displayed in the dialog.
     */
    private fun showErrorDialog(errMsg: String) {
        // Create a custom dialog fragment with the provided error details.
        val dialog = CustomDialogFragment.newInstance(
            title = getString(R.string.error_title),
            message = errMsg,
            positiveText = getString(R.string.response_ok),
            negativeText = "",
            positiveClickListener = { },
            negativeClickListener = { },
            iconResId = R.drawable.ic_dialog_error
        )
        // Show the error dialog using the child fragment manager and a defined tag.
        dialog.show(childFragmentManager, StringConstant.ERROR_DIALOG_DETAILS_TAG)

    }

    private fun showSuccessDialog(successMsg: String) {
        // Create a custom dialog fragment with the provided success details.
        val dialog = CustomDialogFragment.newInstance(
            title = getString(R.string.success_title),
            message = successMsg,
            positiveText = getString(R.string.response_ok),
            negativeText = "",
            positiveClickListener = { },
            negativeClickListener = { },
            iconResId = R.drawable.ic_dialog_success
        )
        // Show the error dialog using the child fragment manager and a defined tag.
        dialog.show(childFragmentManager, StringConstant.SUCCESS_DIALOG_TAG)

    }

    /**
     * Called when the view is about to be destroyed.
     * Removes observers to prevent potential memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()

        // Remove observers for GitHub repository details to avoid potential memory leaks.
        viewModel.gitHubRepoDetails.removeObservers(viewLifecycleOwner)
    }
}