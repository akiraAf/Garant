package com.app.garant.presenter.viewModel.viewModelimpl.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.garant.data.request.auth.DocumentRequest
import com.app.garant.data.response.profile.account.DocumentResponse
import com.app.garant.data.response.profile.account.regions.RegionResponse
import com.app.garant.domain.repository.UserRepository
import com.app.garant.presenter.viewModel.profile.AccountScreenViewModel
import com.app.garant.utils.eventValueFlow
import com.app.garant.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountScreenViewModelImpl @Inject constructor(private val userRepository: UserRepository) :
    ViewModel(), AccountScreenViewModel {

    override val errorFlow = eventValueFlow<String>()
    override val successFlow = eventValueFlow<RegionResponse>()
    override val successFlowCity = eventValueFlow<ArrayList<String>>()
    override val progressFlow = eventValueFlow<Boolean>()
    override val successFlowRegion = eventValueFlow<ArrayList<String>>()
    override val successFlowDistrict = eventValueFlow<ArrayList<String>>()
    override val successFlowProfession = eventValueFlow<ArrayList<String>>()
    override val successFlowDoc = eventValueFlow<DocumentResponse>()

    private var regionsName = ArrayList<String>()
    private var citiesName = ArrayList<String>()
    private var districtsName = ArrayList<String>()
    private var professions = ArrayList<String>()

    override fun getRegionCity(region: String, city: String) {
        if (!isConnected()) {
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        userRepository.getRegion().onEach {
            it.onSuccess {
                progressFlow.emit(false)
                citiesName.clear()
                districtsName.clear()
                for (i in 0 until it.size) {
                    for (j in 0 until it[i].cities.size) {
                        if (region == it[i].name)
                            citiesName.add(it[i].cities[j].name)
                        for (k in 0 until it[i].cities[j].districts.size) {
                            if (city == it[i].cities[j].name)
                                districtsName.add(it[i].cities[j].districts[k].name)
                        }
                    }
                }
                successFlowCity.emit(citiesName)
                successFlowDistrict.emit(districtsName)
            }

            it.onFailure {
                progressFlow.emit(false)
                errorFlow.emit(it.message.toString())
            }
        }.launchIn(viewModelScope)

    }

    override fun getProfession() {
        userRepository.getProfession().onEach {
            it.onSuccess {
                professions.clear()
                it.map { professions.add(it.name) }
                successFlowProfession.emit(professions)
            }

            it.onFailure {
                errorFlow.emit(it.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun sendDocuments(request: DocumentRequest) {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }

        userRepository.sendDocuments(request).onEach {
            it.onSuccess {
                progressFlow.emit(false)
                successFlowDoc.emit(it)
            }

            it.onFailure {
                progressFlow.emit(false)
                errorFlow.emit(it.message.toString())
            }
        }.launchIn(viewModelScope)
    }

    override fun getRegionsName() {
        if (!isConnected()) {
            return
        }

        viewModelScope.launch {
            progressFlow.emit(true)
        }

        userRepository.getRegionNames().onEach {
            it.onSuccess {
                progressFlow.emit(false)
                it.map { regionsName.add(it.name) }
                successFlowRegion.emit(regionsName)
            }

            it.onFailure {
                progressFlow.emit(false)
                errorFlow.emit(it.message.toString())
            }
        }.launchIn(viewModelScope)
    }


}