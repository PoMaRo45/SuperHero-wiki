package com.example.listapi

import com.example.listapi.database.HeroEntity
import com.example.listapi.model.DataAllItem

interface OnClickListener {
    fun onClick(user: DataAllItem)
    fun onFavoriteClick(user: HeroEntity)
}
