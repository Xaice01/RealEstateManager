package com.xavier_carpentier.realestatemanager.domain.current_setting

import com.xavier_carpentier.realestatemanager.domain.agent.model.AgentDomain
import javax.inject.Inject

class SetCurrentAgentUseCase @Inject constructor(private val currentSettingRepository: CurrentSettingRepository) {
    operator fun invoke(currentAgent :AgentDomain) {
        currentSettingRepository.setCurrentAgent(currentAgent)
    }

}