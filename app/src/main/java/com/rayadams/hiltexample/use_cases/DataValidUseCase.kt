package com.rayadams.hiltexample.use_cases

import javax.inject.Inject

class DataValidUseCase @Inject constructor() {
    operator fun invoke(firstName:String, lastName:String, phoneNumber:String): Boolean =
        firstName.isNotBlank() && lastName.isNotBlank() && phoneNumber.isNotBlank()
}