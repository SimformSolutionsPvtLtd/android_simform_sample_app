package com.simformsolutions.sample.app.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.simformsolutions.sample.app.UserInfoQuery
import com.simformsolutions.sample.app.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for [MainActivity]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val apolloClient: ApolloClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _userMessage = MutableStateFlow("")
    val userMessage = _userMessage.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            apolloClient.query(UserInfoQuery()).toFlow()
                .catch {
                    Timber.e(TAG, it.stackTrace)
                }.collect {
                    it.data?.viewer?.name?.let { name ->
                        _userMessage.tryEmit(name)
                    }
                }
        }
    }

    companion object {
        private val TAG = MainViewModel::class.java.canonicalName
    }
}