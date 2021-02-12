package com.rohitthebest.multiselectinrecyclerview.data

data class Category(
    var timestamp: Long? = System.currentTimeMillis(),
    var categoryName: String?,
    var keyValue: String?,
)