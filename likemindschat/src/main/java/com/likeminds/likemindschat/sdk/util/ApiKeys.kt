package com.likeminds.likemindschat.sdk.util

import com.likeminds.likemindschat.BuildConfig

// todo: check
class ApiKeys {
    companion object {
        //return project id of the firebase
        fun getProjectId(): String {
            return if (BuildConfig.DEBUG) {
                DebugKeys.PROJECT_ID
            } else {
                ProdKeys.PROJECT_ID
            }
        }

        //return application id of the firebase
        fun getAppId(): String {
            return if (BuildConfig.DEBUG) {
                DebugKeys.APP_ID
            } else {
                ProdKeys.APP_ID
            }
        }

        //return api key of the firebase
        fun getApiKey(): String {
            return if (BuildConfig.DEBUG) {
                DebugKeys.API_KEY
            } else {
                ProdKeys.API_KEY
            }
        }

        //return database url of the firebase
        fun getDataBaseUrl(): String {
            return if (BuildConfig.DEBUG) {
                DebugKeys.DATABASE_URL
            } else {
                ProdKeys.DATABASE_URL
            }
        }
    }

    class DebugKeys {
        companion object {
            const val PROJECT_ID = "Y29sbGFibWF0ZXMtYmV0YQ=="
            const val APP_ID = "MTozMTc0MTk5ODE0Mjc6YW5kcm9pZDo0MWQ2MjhiNzExNDIzZTkzZDJmODA2"
            const val API_KEY = "QUl6YVN5QldqRFFFaVlLZFFiUU52b2lWdnZPbl9jYnVmUXp2V3Vv"
            const val DATABASE_URL = "aHR0cHM6Ly9jb2xsYWJtYXRlcy1iZXRhLmZpcmViYXNlaW8uY29t"
        }
    }

    class ProdKeys {
        companion object {
            const val PROJECT_ID = "Y29sbGFibWF0ZXMtM2Q2MDE="
            const val APP_ID = "MTo2NDU3MTY0NTg3OTM6YW5kcm9pZDpiODY4Yjk0YjY2ODM5MDVl"
            const val API_KEY = "QUl6YVN5RE4xMFR3Q1BWTWRMRUU2dnZUaWdsS0hHbGtUSVlLZHVj"
            const val DATABASE_URL = "aHR0cHM6Ly9jb2xsYWJtYXRlcy0zZDYwMS5maXJlYmFzZWlvLmNvbQ=="
        }
    }
}