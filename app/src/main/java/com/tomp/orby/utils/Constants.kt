package com.tomp.orby.utils

object Constants {

    const val HTTP_ERROR_BAD_REQUEST = 400
    const val HTTP_ERROR_CONFLICT = 409
    const val HTTP_ERROR_LOGIN_SITE_PROHIBE = 403
    const val HTTP_ERROR_LOGIN_SITE_DONT_EXIST = 406
    const val HTTP_SUCCESS_EMPTY = 204
    const val HTTP_SUCCESS = 200

    const val LANGUAGE_SPANISH = "es"
    const val LANGUAGE_CHINESE = "zh"
    const val LANGUAGE_CHINA = "cn"
    const val LANGUAGE_FRENCH = "fr"
    const val LANGUAGE_JAPAN = "ja"
    const val LANGUAGE_ENGLISH = "en"

    const val FLAVOR_RECETTE = "dev"
    const val FLAVOR_INTEGRATION = "inte"
    private const val FLAVOR_INTEGRATION_EMEA = "inteEMEA"
    private const val FLAVOR_INTEGRATION_ASIA = "inteAsia"
    const val FLAVOR_PRODUCTION = "prod"
    private const val FLAVOR_PRODUCTION_EMEA = "prodEMEA"
    private const val FLAVOR_PRODUCTION_ASIA = "prodAsia"

    fun isNotRVD(flavor: String) =
        flavor == FLAVOR_INTEGRATION_EMEA || flavor == FLAVOR_PRODUCTION_EMEA
                || flavor == FLAVOR_INTEGRATION_ASIA || flavor == FLAVOR_PRODUCTION_ASIA

    const val TIME_BETWEEN_SYNC_REMOTE_CONGIG = 3600L

    const val ACCEPTABLE_MARGIN_FOLDER_EXPENSION = 25

    const val DURATION_ANIMATION_S = 300L

    const val ACCEPTABLE_DELAY_DOUBLE_TOUCH = 600L

    const val SIZING_TOAST = 10f

    const val MAX_MIN_IN_HOUR = 59
    const val MAX_HOUR_IN_DAY = 23
    const val MAX_DAY_IN_YEAR = 365

    const val IMAGE_ROTATION_0 = 0f
    const val IMAGE_ROTATION_180 = 180f
    const val ROTATION_PROPERTY_NAME = "rotation"

    const val TRANSLATION_Y_PROPERTY_NAME = "y"

    const val METIER_TRI_ID = 4L

}
