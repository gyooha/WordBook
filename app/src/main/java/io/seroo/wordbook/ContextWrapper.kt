package io.seroo.wordbook

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import java.io.File
import javax.inject.Inject

interface ContextWrapper {
    fun startActivity(intent: Intent)
    fun getFileSendIntent(file: File): Intent
}

@ActivityScoped
class ContextWrapperImpl @Inject constructor(
    @ActivityContext
    private val context: Context
): ContextWrapper {

    override fun startActivity(intent: Intent) {
        context.startActivity(intent)
    }

    override fun getFileSendIntent(file: File): Intent {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

        return Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "text/plain")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        }
    }
}