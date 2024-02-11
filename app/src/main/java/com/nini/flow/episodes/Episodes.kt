package com.nini.flow.episodes

import com.google.gson.annotations.SerializedName

data class EpisodesResponse(
    val info: Info?,
    val results: List<Episode>?
) {
    data class Info(
        val count: Int,
        val next: String?,
        val pages: Int,
        val prev: String?
    )

    data class Episode(
        @SerializedName("air_date")
        val airDate: String?,
        val characters: List<String>,
        val created: String,
        val episode: String,
        val id: Int,
        val name: String,
        val url: String
    )
}