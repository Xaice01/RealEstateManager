package com.xavier_carpentier.realestatemanager.domain.property_type.model

import androidx.annotation.StringRes

data class PropertyTypeDomain(val id: Long, val databaseName: String, @StringRes val stringRes: Int)
