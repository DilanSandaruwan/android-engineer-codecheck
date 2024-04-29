package jp.co.yumemi.android.code_check.ui.fragment.profileview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.databinding.FragmentGithubProfileBinding

/**
 * Fragment responsible for displaying a GitHub user profile in a WebView.
 */
class GithubProfileFragment : Fragment() {

    // Navigation arguments passed to this fragment
    private val navArgs: GithubProfileFragmentArgs by navArgs()

    // URL of the GitHub user profile to be displayed
    var profileUrl: String? = null

    // View binding for the fragment layout
    private lateinit var binding: FragmentGithubProfileBinding

    /**
     * Sets up the WebView to display the GitHub user profile.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container The parent view that the fragment UI should be attached to.
     * @param savedInstanceState The previously saved state, if any.
     *
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve the profile URL from navigation arguments
        profileUrl = navArgs.profileUrl

        // Inflate the layout for this fragment using view binding
        FragmentGithubProfileBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = this@GithubProfileFragment
        }
        return binding.root
    }

    /**
     * Called immediately after [onCreateView] is called.
     * Sets up the WebView to display the GitHub user profile.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
    }

    /**
     * Configures the WebView settings and loads the profile URL.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webViewProfile.run {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            // Load the profile URL in the WebView
            profileUrl?.let { loadUrl(it) }
        }
    }
}