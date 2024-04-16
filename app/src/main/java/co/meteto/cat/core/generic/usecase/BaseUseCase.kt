package co.meteto.cat.core.generic.usecase


interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}