package com.erentd.softtechinternshipproject.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: CharacterLocation,
    val location: CharacterLocation,
    val image: String,
    val episode: List<String>,
    val created: String
)
