package com.xavier_carpentier.realestatemanager.domain.location

import android.location.Location

interface LocationRepository {
    suspend fun getLastLocation() :Location?
}