package jp.co.yumemi.android.code_check.util.component

import androidx.fragment.app.FragmentManager
import jp.co.yumemi.android.code_check.model.AlertDialogResource
import jp.co.yumemi.android.code_check.ui.component.dialog.CustomDialogFragment

object DialogUtil {
    fun showAlertDialog(alertDialogResource: AlertDialogResource, childFragmentManager: FragmentManager) {
        // Create a custom dialog fragment with the provided success details.
        val dialog = CustomDialogFragment.newInstance(
            title = alertDialogResource.title,
            message = alertDialogResource.message,
            positiveText = alertDialogResource.positiveText,
            negativeText = alertDialogResource.negativeText,
            positiveClickListener = alertDialogResource.positiveClickListener,
            negativeClickListener = alertDialogResource.negativeClickListener,
            iconResId = alertDialogResource.iconResId
        )
        // Show the error dialog using the child fragment manager and a defined tag.
        dialog.show(childFragmentManager, alertDialogResource.tag)
    }
}