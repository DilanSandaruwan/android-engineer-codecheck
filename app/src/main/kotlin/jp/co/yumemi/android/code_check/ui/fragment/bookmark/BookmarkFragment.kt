package jp.co.yumemi.android.code_check.ui.fragment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentBookmarkBinding
import jp.co.yumemi.android.code_check.ui.viewmodel.bookmark.BookmarkViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    lateinit var binding: FragmentBookmarkBinding
    lateinit var viewModel: BookmarkViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }
    private fun initObservers() {
        viewModel.bookmarkedReposAll?.observe(viewLifecycleOwner){
            val x = it
        }
    }

}