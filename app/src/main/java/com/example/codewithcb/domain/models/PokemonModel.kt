package com.example.codewithcb.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModel(
    var name: String,
    val images: ImageData,
    val types: ArrayList<String>,
    val hp: String,
    val level: Int,
    val subtypes: ArrayList<String>?,
    val resistances: ArrayList<WeaknessModel>?,
    val weaknesses: ArrayList<WeaknessModel>?,
    val attacks: List<AttacksModel>?,
    val abilities: ArrayList<AttacksModel>?,
) : Parcelable

@Parcelize
data class ImageData(
    val small: String,
    val large: String
) : Parcelable

@Parcelize
data class AttacksModel(
    val name: String
): Parcelable

@Parcelize
data class WeaknessModel(
    val type: String,
    val value: String,
): Parcelable
