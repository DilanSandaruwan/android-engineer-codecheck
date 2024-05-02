package jp.co.yumemi.android.code_check.constant

/**
 * Object containing constant strings used throughout the application.
 */
object StringConstant {

    /** Tag used for the add confirmation dialog fragment.  */
    const val CONFIRM_ADD_BOOKMARK = "custom_dialog_confirm_add_bookmark"

    /** Tag used for the delete confirmation dialog fragment.  */
    const val CONFIRM_DELETE_BOOKMARK = "custom_dialog_confirm_delete_bookmark"

    /** Tag used for the delete confirmation dialog fragment when succeeded.  */
    const val CONFIRM_DELETE_BOOKMARK_SUCCESS = "custom_dialog_confirm_delete_bookmark_success"

    /** Tag used for the delete confirmation dialog fragment when an error occurred.  */
    const val CONFIRM_DELETE_BOOKMARK_ERROR = "custom_dialog_confirm_delete_bookmark_error"

    /** Tag used for logging purposes within this codebase.  */
    const val TAG = "Code Test"

    /** String representing "no language found" used in various UI elements.  */
    const val NO_LANGUAGE_FOUND = "---"

    /** Tag used for the custom dialog fragment indicating no term search available.  */
    const val NO_TERM_SEARCH_DIALOG_TAG = "custom_dialog_no_term_search"

    /** Tag used for the custom dialog fragment displaying error details.  */
    const val ERROR_DIALOG_DETAILS_TAG = "custom_dialog_error_details"

    /** Tag used for the generic error dialog fragment.  */
    const val ERROR_DIALOG_TAG = "custom_dialog_error"

    /** Tag used for the generic success dialog fragment.  */
    const val SUCCESS_DIALOG_TAG = "custom_dialog_success"

    /****** Dialog Fragment Constants ******/

    /** Title text used in the common dialog fragment.  */
    const val DIALOG_FRAGMENT_TITLE = "Dialog Title"

    /** Message text used in the common dialog fragment.  */
    const val DIALOG_FRAGMENT_MESSAGE = "Dialog Message"

    /** Text for the positive button ("OK") in the common dialog fragment.  */
    const val DIALOG_FRAGMENT_OK = "OK"

    /** Text for the negative button ("Cancel") in the common dialog fragment.  */
    const val DIALOG_FRAGMENT_CANCEL = "Cancel"
}