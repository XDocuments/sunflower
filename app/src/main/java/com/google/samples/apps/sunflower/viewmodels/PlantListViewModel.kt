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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.data.PlantRepository

/**
 * 植物类表页面ViewModel
 * 继承ViewModel对象，plantRepository成员变量传入
 */
class PlantListViewModel internal constructor(plantRepository: PlantRepository) : ViewModel() {

    //声明种植区域LiveData
    private val growZoneNumber = MutableLiveData<Int>(NO_GROW_ZONE)

    //声明植物列表LiveData，感知生命周期可观察数据
    val plants: LiveData<List<Plant>> = growZoneNumber.switchMap {
        //growZoneNumber LivaDatab变化，通过switchMap转换
        //根据不同的种植区域，请求plantRepository获取不同的植物，返回LiveData<List<Plant>>类型
        if (it == NO_GROW_ZONE) {
            plantRepository.getPlants()
        } else {
            plantRepository.getPlantsWithGrowZoneNumber(it)
        }
    }

    fun setGrowZoneNumber(num: Int) {
        //更新区域数据，关联植物列表数据获取更新
        growZoneNumber.value = num
    }

    fun clearGrowZoneNumber() {
        growZoneNumber.value = NO_GROW_ZONE
    }

    fun isFiltered() = growZoneNumber.value != NO_GROW_ZONE

    companion object {
        private const val NO_GROW_ZONE = -1
    }
}
