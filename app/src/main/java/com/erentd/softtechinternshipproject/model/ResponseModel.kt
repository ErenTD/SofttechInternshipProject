package com.erentd.softtechinternshipproject.model

data class ResponseModel(
    val info: InfoModel,
    val results: List<CharacterModel>
)
