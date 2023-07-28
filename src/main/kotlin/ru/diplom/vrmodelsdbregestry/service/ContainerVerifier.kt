package ru.diplom.vrmodelsdbregestry.service

interface ContainerVerifier {
    fun verifyContainer(fileName: String, containerBytes: ByteArray)
}