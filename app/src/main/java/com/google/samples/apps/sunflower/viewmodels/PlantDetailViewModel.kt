/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.samples.apps.sunflower.data.GardenPlantingRepository
import com.google.samples.apps.sunflower.data.PlantRepository
import kotlinx.coroutines.launch

/**
 * 植物详情ViewModel
 * PlantRepository所有植物数据源
 * GardenPlantingRepository我的植物数据源
 * plantId植物id
 */
class PlantDetailViewModel(
        plantRepository: PlantRepository,
        private val gardenPlantingRepository: GardenPlantingRepository,
        private val plantId: String
) : ViewModel() {

    //进来之后没有数据变化，所以没有使用可观察数据类型或者LiveData(可观察且感知生命周期)
    //植物是否已经种植，如果已经种植不展示添加按钮
    val isPlanted = gardenPlantingRepository.isPlanted(plantId)
    val plant = plantRepository.getPlant(plantId)

    //将植物添加到我的花园中
    fun addPlantToGarden() {
        viewModelScope.launch {
            gardenPlantingRepository.createGardenPlanting(plantId)
        }
    }
}
