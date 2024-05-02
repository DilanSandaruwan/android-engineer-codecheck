package jp.co.yumemi.android.code_check.ui.adapter

import android.view.LayoutInflater
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constant.StringConstant
import jp.co.yumemi.android.code_check.databinding.LayoutItemBinding
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem
import jp.co.yumemi.android.code_check.model.GitHubAccount

class GitHubRepoRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GitHubAccount, GitHubRepoRecyclerViewAdapter.ViewHolder>(diff_util) {

    private var bookmarkedRepoItems: List<BookmarkGithubRepoItem>? = null

    /**
     * ViewHolder class for holding the views of each item in the RecyclerView.
     *
     * @param view The view representing an item.
     */
    class ViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Interface for defining item click events.
     */
    interface OnItemClickListener {
        /**
         * Called when an item is clicked.
         *
         * @param item The clicked GitHubAccount item.
         */
        fun itemClick(item: GitHubAccount, isBookmarked: Boolean)
    }

    /**
     * Creates a ViewHolder by inflating the item layout.
     *
     * @param parent The parent ViewGroup.
     * @param viewType The type of the view.
     * @return The created ViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    /**
     * Binds the data to the views of the ViewHolder at the specified position in the list.
     *
     * @param holder The ViewHolder to bind the data to.
     * @param position The position of the item in the list.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data for the current item at the given position
        val holderItem = getItem(position)

        //Check the item is available in saved github list
        val isBookmarked: Boolean = bookmarkedRepoItems?.any { it.id == holderItem.id } == true


        holder.apply {
            // Bind the data to the corresponding views in the ViewHolder
            binding.apply {
                repositoryNameView.text = holderItem.name
                repositoryDescriptionTextView.text = holderItem.description
                repositoryLanguageTextView.text =
                    holderItem.language ?: StringConstant.NO_LANGUAGE_FOUND
                repositoryStargazersTextView.text = holderItem.stargazersCount.toString()

                holderItem.owner?.avatarUrl?.let {
                    ownerIconView.load(it) {
                        crossfade(true)
                        placeholder(R.drawable.placeholder_repository)
                        transformations(CircleCropTransformation())
                        listener(onError = { _, _ ->
                            placeholder(R.drawable.placeholder_repository)
                        })
                    }
                }

                ivBookmarked.visibility = if (isBookmarked) {
                    VISIBLE
                } else {
                    INVISIBLE
                }

                // Set an onClickListener to handle item clicks and trigger an action
                holder.itemView.setOnClickListener {
                    itemClickListener.itemClick(holderItem, isBookmarked)
                }
            }
        }

    }

    fun mentionBookmarkedRepo(bookmarkedRepoList: List<BookmarkGithubRepoItem>) {
        bookmarkedRepoItems = bookmarkedRepoList

    }

    companion object {
        /**
         * DiffUtil.ItemCallback for calculating the differences between old and new items in the list.
         */
        val diff_util = object : DiffUtil.ItemCallback<GitHubAccount>() {
            /**
             * Checks if the items have the same identity.
             *
             * @param oldItem The old item.
             * @param newItem The new item.
             * @return True if the items have the same identity, false otherwise.
             */
            override fun areItemsTheSame(oldItem: GitHubAccount, newItem: GitHubAccount): Boolean {
                return oldItem.name == newItem.name
            }

            /**
             * Checks if the contents of the items are the same.
             *
             * @param oldItem The old item.
             * @param newItem The new item.
             * @return True if the contents of the items are the same, false otherwise.
             */
            override fun areContentsTheSame(
                oldItem: GitHubAccount,
                newItem: GitHubAccount
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}