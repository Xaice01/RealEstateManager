package com.xavier_carpentier.realestatemanager.domain.location

import android.location.Location
import javax.inject.Inject

class GetLastLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(): Location? {
        return locationRepository.getLastLocation()
    }
}