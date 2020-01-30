package com.kaspersky.kaspresso.logger

/**
 * The interface for base logging with 3 levels: info, debug and error.
 */
interface Logger {

    /**
     * Info level of logging.
     */
    fun i(text: String)

    /**
     * Debug level of logging.
     */
    fun d(text: String)

    /**
     * Error level of logging.
     */
    fun e(text: String)

    /**
     * Info level of logging with tag.
     */
    fun i(tag: String, text: String)

    /**
     * Debug level of logging with tag.
     */
    fun d(tag: String, text: String)

    /**
     * Error level of logging with tag.
     */
    fun e(tag: String, text: String)

    fun шепнуть(text: String) = i(text)

    fun сказать(text: String) = d(text)

    fun проорать(text: String) = e(text)

    fun шепнуть(tag: String, text: String) = i(tag, text)

    fun сказать(tag: String, text: String) = d(tag, text)

    fun проорать(tag: String, text: String) = e(tag, text)
}