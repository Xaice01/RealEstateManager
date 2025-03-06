package com.xavier_carpentier.realestatemanager.domain.permission

import javax.inject.Inject


class SetPermissionLocationUseCase @Inject constructor(
    private val permissionLocationRepository: PermissionLocationRepository
) {
    operator fun invoke(value: Boolean) {
        permissionLocationRepository.setPermissionLocation(value)
    }
}