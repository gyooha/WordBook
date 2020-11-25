package io.seroo.data.common

sealed class LocalError: Throwable()

object WordNotFoundException: LocalError()