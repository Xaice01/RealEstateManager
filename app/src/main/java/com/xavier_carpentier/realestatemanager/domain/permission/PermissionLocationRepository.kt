package com.xavier_carpentier.realestatemanager.domain.permission

import kotlinx.coroutines.flow.StateFlow

interface PermissionLocationRepository {
    fun getPermissionLocation(): StateFlow<Boolean>
    fun setPermissionLocation(value: Boolean)
}