package kz.butik.data.api

interface JsonResponse<M> {
    fun toModel(): M
}

interface LocalModel<R> {
    fun toModel(): R
}