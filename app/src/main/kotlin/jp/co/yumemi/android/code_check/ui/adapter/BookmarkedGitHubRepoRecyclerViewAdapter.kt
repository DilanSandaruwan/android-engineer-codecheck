package jp.co.yumemi.android.code_check.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constant.StringConstant
import jp.co.yumemi.android.code_check.databinding.LayoutBookmarkedItemBinding
import jp.co.yumemi.android.code_check.model.BookmarkGithubRepoItem

class BookmarkedGitHubRepoRecyclerViewAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<BookmarkGithubRepoItem, BookmarkedGitHubRepoRecyclerViewAdapter.ViewHolder>(
    diff_util
) {
    /**
     * ViewHolder class for holding the views of each item in the RecyclerView.
     *
     * @param binding The view representing an item.
     */
    class ViewHolder(val binding: LayoutBookmarkedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Interface for defining item click events.
     */
    interface OnItemClickListener {
        /**
         * Called when an item is clicked.
         *
         * @param item The clicked BookmarkGithubRepoItem item.
         */
        fun itemClick(item: BookmarkGithubRepoItem)
        fun deleteClick(itemId: Long)
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
        val binding = LayoutBookmarkedItemBinding.inflate(inflater, parent, false)
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

        // Bind the data to the corresponding views in the ViewHolder
        holder.apply {
            binding.apply {
                repositoryNameView.text = holderItem.name
                repositoryOwnerTypeTextView.text = holderItem.ownerType
                repositoryLanguageTextView.text =
                    holderItem.language ?: StringConstant.NO_LANGUAGE_FOUND
                repositoryStargazersTextView.text = holderItem.stargazersCount.toString()
                repositoryForksTextView.text = holderItem.forksCount.toString()
                repositoryOpenIssuesTextView.text = holderItem.openIssuesCount.toString()

                holderItem.avatarUrl?.let {
                    ownerIconView.load(it) {
                        crossfade(true)
                        placeholder(R.drawable.placeholder_repository)
                        transformations(CircleCropTransformation())
                        listener(onError = { _, _ ->
                            placeholder(R.drawable.placeholder_repository)
                        })
                    }
                }

                ivNavigateToNext.setOnClickListener {
                    itemClickListener.itemClick(holderItem)
                }

                ivDeleteBookmark.setOnClickListener {
                    itemClickListener.deleteClick(holderItem.id)
                }
            }
        }
    }

    companion object {
        /**
         * DiffUtil.ItemCallback for calculating the differences between old and new items in the list.
         */
        val diff_util = object : DiffUtil.ItemCallback<BookmarkGithubRepoItem>() {
            /**
             * Checks if the items have the same identity.
             *
             * @param oldItem The old item.
             * @param newItem The new item.
             * @return True if the items have the same identity, false otherwise.
             */
            override fun areItemsTheSame(
                oldItem: BookmarkGithubRepoItem,
                newItem: BookmarkGithubRepoItem
            ): Boolean {
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
                oldItem: BookmarkGithubRepoItem,
                newItem: BookmarkGithubRepoItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}