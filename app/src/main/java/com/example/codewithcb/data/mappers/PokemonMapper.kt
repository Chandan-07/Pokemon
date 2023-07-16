package com.example.codewithcb.data.mappers

import com.example.codewithcb.domain.models.AttacksModel
import com.example.codewithcb.domain.models.CharacterModel
import com.example.codewithcb.domain.models.ImageData
import com.example.codewithcb.domain.models.WeaknessModel
import com.example.codewithcb.remote.models.Attacks
import com.example.codewithcb.remote.models.PokemonResponseModel
import com.example.codewithcb.remote.models.Weakness

object PokemonMapper {

    fun mapCharacter(model: PokemonResponseModel): List<CharacterModel> {

        val list : ArrayList<CharacterModel> = arrayListOf()

        for(item in model.data) {
            list.add( CharacterModel(
                name = item.name,
                hp = item.hp,
                images = ImageData(item.images.small, item.images.large),
                level = item.level,
                types = item.types,
                subtypes = item.subtypes,
                weaknesses = item.weaknesses?.let { mapWeakness(it) },
                resistances = item.resistances?.let { mapWeakness(it) },
                attacks = item.attacks?.let { mapAttack(it) },
                abilities = item.abilities?.let { mapAttack(it) }
            ))
        }
        return list
    }

    fun mapAttack(attacks: List<Attacks>) : ArrayList<AttacksModel> {

        val attacksList : ArrayList<AttacksModel> = arrayListOf()

        if (attacks !=null) {
            for (item in attacks) {
                item?.name?.let { AttacksModel(it) }?.let { attacksList.add(it) }
            }
        }

        return attacksList

    }
    fun mapWeakness(attacks: ArrayList<Weakness>) : ArrayList<WeaknessModel> {

        val attacksList : ArrayList<WeaknessModel> = arrayListOf()

        if (attacks !=null) {
            for (item in attacks) {
                item?.type?.let { item.value?.let { it1 -> WeaknessModel(it, it1) } }?.let { attacksList.add(it) }
            }
        }

        return attacksList

    }
}