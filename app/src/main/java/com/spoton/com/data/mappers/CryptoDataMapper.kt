package com.spoton.com.data.mappers

import com.spoton.com.domain.models.CryptoModel
import com.spoton.com.remote.models.CryptoResponseModel

object CryptoDataMapper {

    fun mapCharacter(model: CryptoResponseModel): List<CryptoModel> {

        val list : ArrayList<CryptoModel> = arrayListOf()

        for(item in model.data) {
            list.add( CryptoModel(
                name = item.name,
                id = item.id,
                rank = item.rank,
                symbol = item.symbol,
                supply = item.supply,
                maxSupply = item.maxSupply,
                marketCapUsd = item.marketCapUsd,
                volumeUsd24Hr = item.volumeUsd24Hr,
                priceUsd = item.priceUsd,
                changePercent24Hr = item.changePercent24Hr,
                vwap24Hr = item.vwap24Hr,
                explorer = item.explorer
            ))
        }
        return list
    }
}