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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.google.samples.apps.sunflower.adapters.GardenPlantingAdapter
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.databinding.FragmentGardenBinding
import com.google.samples.apps.sunflower.utilities.InjectorUtils
import com.google.samples.apps.sunflower.viewmodels.GardenPlantingListViewModel

/**
 * 我的花园Fragment
 */
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    //构造获取ViewModel对象
    private val viewModel: GardenPlantingListViewModel by viewModels {
        InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //绑定布局
        binding = FragmentGardenBinding.inflate(inflater, container, false)

        //设置列表适配器
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        //设置添加植物按钮监听器
        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)

        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        //LiveData观察我的植物结合，
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            //如果有植物，更新hasPlantings LiveData，布局更定显示列表视图
            binding.hasPlantings = !result.isNullOrEmpty()
            //更新列表数据集合
            adapter.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
                PLANT_LIST_PAGE_INDEX
    }
}
