package com.xavier_carpentier.realestatemanager.domain.permission

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class GetPermissionLocationAsStateFlowUseCase @Inject constructor(
    private val permissionLocationRepository: PermissionLocationRepository
) {
    operator fun invoke(): StateFlow<Boolean> {
        return permissionLocationRepository.getPermissionLocation()
    }
}