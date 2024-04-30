package jp.co.yumemi.android.code_check.model

import android.widget.ExpandableListView.OnChildClickListener

data class AlertDialogResource(
    val title: String = "",
    val message: String = "",
    val positiveText: String = "",
    val negativeText: String = "",
    val positiveClickListener: () -> Unit,
    val negativeClickListener: () -> Unit,
    val iconResId: Int,
)
