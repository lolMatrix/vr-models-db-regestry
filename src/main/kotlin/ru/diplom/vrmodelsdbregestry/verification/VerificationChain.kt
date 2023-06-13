package ru.diplom.vrmodelsdbregestry.verification

import ru.diplom.vrmodelsdbregestry.verification.context.VerificationContext

interface VerificationChain {
    fun verify(context: VerificationContext)
}