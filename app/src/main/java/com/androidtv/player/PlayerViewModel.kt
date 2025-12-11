package com.androidtv.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    val exoHolder = ExoPlayerHolder(application)

    fun playUrl(url: String) {
        viewModelScope.launch(Dispatchers.Main) {
            exoHolder.prepareAndPlay(url)
        }
    }

    override fun onCleared() {
        // Clean up if not using lifecycleObservers elsewhere
        exoHolder.player.release()
        super.onCleared()
    }
}