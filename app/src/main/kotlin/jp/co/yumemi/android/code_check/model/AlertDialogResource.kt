package jp.co.yumemi.android.code_check.model

import jp.co.yumemi.android.code_check.R

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
