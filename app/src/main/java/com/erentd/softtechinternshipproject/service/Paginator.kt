package com.erentd.softtechinternshipproject.service

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}