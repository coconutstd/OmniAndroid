package com.example.omniandroid

import android.content.Context

class Main (
    var name: String,
    var image: String,
    var content: String
) {
    fun getImageId(context: Context): Int {
        return context.resources.getIdentifier(image, "drawable", context.packageName)
    }
}