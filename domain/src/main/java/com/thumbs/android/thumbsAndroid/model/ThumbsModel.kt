package com.thumbs.android.thumbsAndroid.model

import com.google.gson.annotations.SerializedName


data class Thumb(
    var thumbId: Int,
    var name: String,
    var condition: Condition
)

data class Condition(
    var affection: Affection,
    var health: Health,
    var satiety: Satiety,
    var hygiene: Hygiene
)

data class Affection(
    var label: String,
    var value: Int?
)


data class Health(
    var label: String,
    var value: Int?
)

//위생
data class Hygiene(
    var label : String,
    var value : Int?
)

data class Satiety(
    var label : String,
    var value : Int?
)
