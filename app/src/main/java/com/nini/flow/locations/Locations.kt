package com.nini.flow.locations

data class LocationsResponse(
    val info: Info?,
    val results: List<Location>?
){
    data class Info(
        val count: Int,
        val next: String?,
        val pages: Int,
        val prev: String?
    )

    data class Location(
        val created: String,
        val dimension: String,
        val id: Int,
        val name: String,
        val residents: List<String>,
        val type: String,
        val url: String
    )
}
