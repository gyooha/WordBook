package io.seroo.wordbook

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

interface ContextWrapper {
    fun startActivity(intent: Intent)
}

@ActivityScoped
class ContextWrapperImpl @Inject constructor(
    @ActivityContext
    private val context: Context
): ContextWrapper {

    override fun startActivity(intent: Intent) {
        context.startActivity(intent)
    }
}