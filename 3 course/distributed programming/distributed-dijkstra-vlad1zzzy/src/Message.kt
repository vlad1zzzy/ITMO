package dijkstra.messages

sealed class Message

data class MessageWithDistance(val distance: Long) : Message()

object MessageCreate : Message()

object MessageRemove : Message()

object MessageReject : Message()