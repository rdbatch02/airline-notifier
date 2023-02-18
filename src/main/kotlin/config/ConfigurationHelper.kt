package config

import aws.sdk.kotlin.services.ssm.SsmClient
import aws.sdk.kotlin.services.ssm.getParameter
import kotlinx.coroutines.runBlocking

object ConfigurationHelper {
    private val ssmClient by lazy { runBlocking { SsmClient.fromEnvironment() } }
    val filterKeywords: List<String> = getSSMOrEnv("/atis/filter-keywords", "FILTER_KEYWORDS").split(",")
    val sendFromAddress: String = getSSMOrEnv("/atis/send-from-address", "SEND_FROM_ADDRESS")
    val sendToAddresses: List<String> = getSSMOrEnv("/atis/send-to-address", "SEND_TO_ADDRESS").split(",")

    private fun getSSMOrEnv(ssmPath: String, environmentName: String?): String {
        val ssmResponse = runBlocking { getSsmParam(ssmPath) }
        if (ssmResponse != null) {
            return ssmResponse
        }
        if (environmentName != null && System.getenv(environmentName) != null) {
            return System.getenv(environmentName)
        }
        throw ConfigurationException(ssmPath, environmentName ?: "NO_ENV_SPECIFIED")
    }

    private suspend fun getSsmParam(paramName: String): String? {
        val paramResponse = ssmClient.getParameter { name = paramName }
        return paramResponse.parameter?.value
    }
}