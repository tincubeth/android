package in3

import java.net.HttpURLConnection
import java.net.URL

class IN3 {

    private external fun init(): Long
    private val ptr: Long

    init {
        System.loadLibrary("in3")
        ptr = init()
    }

    /** number of seconds requests can be cached.  */
    var cacheTimeout: Int
        external get
        external set

    /** the limit of nodes to store in the client.  */
    var nodeLimit: Int
        external get
        external set

    /** the client key to sign requests  */
    var key: ByteArray
        external get
        external set

    /** number of max bytes used to cache the code in memory  */
    var maxCodeCache: Int
        external get
        external set

    /** number of number of blocks cached  in memory  */
    var maxBlockCache: Int
        external get
        external set

    /** the type of proof used  */
    var proof: Proof
        external get
        external set

    /** the number of request send when getting a first answer  */
    var requestCount: Int
        external get
        external set

    /** the number of signatures used to proof the blockhash.  */
    var signatureCount: Int
        external get
        external set

    /** min stake of the server. Only nodes owning at least this amount will be chosen.  */
    var minDeposit: Long
        external get
        external set

    /** if specified, the blocknumber *latest* will be replaced by blockNumber- specified value  */
    var replaceLatestBlock: Int
        external get
        external set

    /** the number of signatures in percent required for the request */
    var finality: Int
        external get
        external set

    /** the max number of attempts before giving up */
    var maxAttempts: Int
        external get
        external set

    /** specifies the number of milliseconds before the request times out. increasing may be helpful if the device uses a slow connection.  */
    var timeout: Int
        external get
        external set

    /** servers to filter for the given chain. The chain-id based on EIP-155. */
    var chainId: Long
        external get
        external set

    /** if true the nodelist will be automaticly updated if the lastBlock is newer  */
    var isAutoUpdateList: Boolean
        external get
        external set

    /** provides the ability to cache content */
    var storageProvider: StorageProvider
        external get
        external set

    /** send a request. The request must a valid json-string with method and params  */
    external fun send(request: String): String

    private external fun free()

    protected fun finalize() {
        free()
    }

    companion object {

        @JvmStatic
        fun sendRequest(urls: Array<String>, payload: ByteArray): Array<ByteArray?> {
            val result = arrayOfNulls<ByteArray>(urls.size)
            // todo run it in threads....
            for (i in urls.indices) {
                try {
                    val url = URL(urls[i])
                    val con = url.openConnection()
                    val http = con as HttpURLConnection
                    http.requestMethod = "POST"
                    http.useCaches = false
                    http.doOutput = true
                    http.setRequestProperty("Content-Type", "application/json")
                    http.setRequestProperty("Accept", "application/json")
                    http.setRequestProperty("charsets", "utf-8")
                    http.connect()
                    http.outputStream.use { it.write(payload) }
                    result[i] = http.inputStream.use { it.readBytes() }
                } catch (ex: Exception) {
                    result[i] = null
                }

            }
            return result
        }

    }
}