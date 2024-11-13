package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCurrentAgentUseCase @Inject constructor(private val currentSettingRepository: CurrentSettingRepository) {
    operator  fun invoke() : StateFlow<AgentDomain?>{
        return currentSettingRepository.currentAgentFlow
    }
}