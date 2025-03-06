package com.xavier_carpentier.realestatemanager.data.permission

import com.xavier_carpentier.realestatemanager.domain.permission.PermissionLocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PermissionLocationRepositoryImpl @Inject constructor(): PermissionLocationRepository {

    private val _permissionLocation = MutableStateFlow(false)
    private val permissionLocation = _permissionLocation.asStateFlow()

    override fun getPermissionLocation(): StateFlow<Boolean> {
        return permissionLocation
    }

    override fun setPermissionLocation(value: Boolean) {
        _permissionLocation.value = value
    }


}