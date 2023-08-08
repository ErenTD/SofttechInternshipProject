package com.erentd.softtechinternshipproject.model

data class EpisodeModel(
    val id : Int,
    val name : String,
    val air_date : String,
    val episode : String,
    val characters : List<String>,
    val url : String,
    val created : String
)
