package com.ajtechnology.chatty.data

data class UserData(
    var userid:String? = "",
    var name: String? ="",
    var number: String? = "",
    var imageUrl: String? = ""
){
    fun toMap() = mapOf(
        "userId" to userid,
        "name" to name,
        "number" to number,
        "imageUrl" to imageUrl
    )
}
