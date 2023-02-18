package notification

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.Content
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.model.Message
import aws.sdk.kotlin.services.ses.model.SendEmailRequest
import config.ConfigurationHelper

object SesClient : MessagingClient {
    override suspend fun sendMessage(body: String, recipient: List<String>) {
        val sesSdkClient = SesClient.fromEnvironment()
        val request = SendEmailRequest {
            message = Message {
                destination = Destination.invoke { toAddresses = recipient }
                subject = Content.invoke { data = "ATIS Alert" }
                source = ConfigurationHelper.sendFromAddress
                body {
                  html = Content.invoke { data = body }
                }
            }
        }
        sesSdkClient.sendEmail(request)
    }
}