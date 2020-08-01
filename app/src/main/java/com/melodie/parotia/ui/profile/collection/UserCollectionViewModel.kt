package com.melodie.parotia.ui.profile.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.user.ListUserCollectionsUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserCollectionViewModel constructor(
    private val username: LiveData<String>
) : ViewModel() {
    private val listUserCollectionsUseCase = ListUserCollectionsUseCase(
        UserRepository(),
        Dispatchers.IO
    )

    fun getCollections(): Flow<PagingData<Collection>> {
        return listUserCollectionsUseCase(username.value!!)
    }
}

class UserCollectionViewModelFactory(private val username: LiveData<String>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserCollectionViewModel::class.java)) {
            return UserCollectionViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
