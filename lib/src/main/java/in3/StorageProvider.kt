package in3

interface StorageProvider {
    fun getItem(key: String): ByteArray
    fun setItem(key: String, content: ByteArray)
}
