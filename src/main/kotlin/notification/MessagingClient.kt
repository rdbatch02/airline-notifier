package notification

interface MessagingClient {
    suspend fun sendMessage(body: String, recipient: List<String>)
}