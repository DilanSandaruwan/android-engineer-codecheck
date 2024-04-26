package jp.co.yumemi.android.code_check.util.exception

import java.io.IOException

/**
 * Exception class representing a network unavailable error.
 * This exception is thrown when a network operation fails due to the absence of network connectivity.
 *
 * @param message The detail message for this exception.
 * @constructor Creates an instance of NetworkUnavailableException with the provided message.
 */
class NetworkUnavailableException(message: String) : IOException(message)