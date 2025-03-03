package com.xavier_carpentier.realestatemanager.domain.filter

import com.xavier_carpentier.realestatemanager.domain.current_setting.utils.ConversePrice
import com.xavier_carpentier.realestatemanager.domain.filter.model.FilterDomain
import javax.inject.Inject

class SetFilterPropertyWithPictureUseCase @Inject constructor(
    private val filterRepository: FilterRepository,
    private val conversePrice: ConversePrice
) {
    operator fun invoke(filter: FilterDomain) {
        filterRepository.setFilter(filter.copy(minPrice = conversePrice.conversePriceToData(filter.minPrice), maxPrice = conversePrice.conversePriceToData(filter.maxPrice)))

    }

}