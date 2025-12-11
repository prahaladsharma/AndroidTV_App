package com.androidtv.player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.ExoDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File


@UnstableApi
object CacheProvider {
    private var simpleCache: SimpleCache? = null

    @OptIn(UnstableApi::class)
    fun getCache(context: Context): SimpleCache {
        return simpleCache ?: synchronized(this) {
            val cacheFolder = File(context.cacheDir, "media")
            val evictor = LeastRecentlyUsedCacheEvictor(100L * 1024L * 1024L) // 100MB
            val dbProvider = ExoDatabaseProvider(context)
            SimpleCache(cacheFolder, evictor, dbProvider).also { simpleCache = it }
        }
    }
}