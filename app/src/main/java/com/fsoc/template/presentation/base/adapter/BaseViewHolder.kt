/*
 * Copyright © OMRON HEALTHCARE Co., Ltd. 2020. All rights reserved.
 */

package com.fsoc.template.presentation.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(parent: ViewGroup, layoutID: Int) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
) {
    abstract fun bindData(model: T, context: Context)
}
