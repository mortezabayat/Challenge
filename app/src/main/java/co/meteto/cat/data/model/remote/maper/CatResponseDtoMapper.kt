package co.meteto.cat.data.model.remote.maper

import co.meteto.cat.data.model.remote.dto.response.CatResponseDto
import co.meteto.cat.domain.model.Cat


fun CatResponseDto.mapFromEntity() = Cat(
    id = this.id,
    url = this.url,
    width = this.width,
    height = this.height
)

fun Cat.mapFromDomain() = CatResponseDto(
    id = this.id,
    url = this.url,
    width = this.width,
    height = this.height
)

fun List<CatResponseDto>.mapFromListModel(): List<Cat> {
    return this.map {
        it.mapFromEntity()
    }
}

fun List<Cat>.mapFromListDomain(): List<CatResponseDto> {
    return this.map {
        it.mapFromDomain()
    }
}