package jp.co.yumemi.android.code_check.model

import jp.co.yumemi.android.code_check.R

/**
 * Data class representing the resources for an AlertDialog.
 *
 * @param title The title text of the AlertDialog.
 * @param message The message text of the AlertDialog.
 * @param positiveText The text for the positive button of the AlertDialog.
 * @param negativeText The text for the negative button of the AlertDialog.
 * @param positiveClickListener The click listener for the positive button of the AlertDialog.
 * @param negativeClickListener The click listener for the negative button of the AlertDialog.
 * @param iconResId The resource ID for the icon of the AlertDialog.
 * @param tag An optional tag for identifying the AlertDialog.
 */
data class AlertDialogResource(
    val title: String = "",
    val message: String = "",
    val positiveText: String = "",
    val negativeText: String = "",
    val positiveClickListener: () -> Unit = {},
    val negativeClickListener: () -> Unit = {},
    val iconResId: Int = R.drawable.ic_dialog_info,
    val tag: String?
)
