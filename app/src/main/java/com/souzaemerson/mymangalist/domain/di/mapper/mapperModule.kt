package com.souzaemerson.mymangalist.domain.di.mapper

import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomain
import com.souzaemerson.mymangalist.domain.mapper.TransformResultIntoDomainImpl
import org.koin.dsl.module

val setMapperModule = module {
    factory<TransformResultIntoDomain> {
        TransformResultIntoDomainImpl()
    }
}