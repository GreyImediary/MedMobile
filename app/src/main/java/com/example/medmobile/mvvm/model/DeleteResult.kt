package com.example.medmobile.mvvm.model

data class DeleteResult (
    val raw: DeleteRaw,
    val affected: Int
)

data class DeleteRaw(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val serverStatus: Int,
    val warningCount: Int,
    val message: String,
    val protocol41: Boolean,
    val changedRows: Int
)