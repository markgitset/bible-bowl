package analysis

import org.tensorflow.SavedModelBundle
import java.lang.AutoCloseable
import org.tensorflow.Tensor
import java.nio.file.Paths
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.Lists
import com.google.common.io.Resources
import java.io.File
import java.io.IOException
import java.lang.RuntimeException
import java.net.URL
import java.nio.IntBuffer
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator
import java.util.zip.ZipInputStream
import java.util.zip.ZipEntry
import kotlin.io.path.ExperimentalPathApi

/**
 * easy-bert is a dead simple API for using [Google's high quality BERT language model](https://github.com/google-research/bert).
 *
 * The easy-bert Java bindings allow you to run pre-trained BERT models generated with easy-bert's Python tools. You can also used pre-generated models on Maven
 * Central.
 * <br></br>
 * <br></br>
 *
 *
 * To load a model from your local filesystem, you can use:
 *
 * <blockquote>
 * <pre>
 * `try(Bert bert = Bert.load(new File("/path/to/your/model/"))) {
 * // Embed some sequences
 * }`
 * </pre>
 * </blockquote>
 *
 * If the model is on your classpath (e.g. if you're pulling it in via Maven), you can use:
 *
 * <blockquote>
 * <pre>
 * `try(Bert bert = Bert.load("/resource/path/to/your/model/")) {
 * // Embed some sequences
 * }`
 * </pre>
 * </blockquote>
 *
 * See [the easy-bert GitHub Repository](https://github.com/robrua/easy-bert) for information about model available via Maven Central.
 * <br></br>
 * <br></br>
 *
 *
 * Once you have a BERT model loaded, you can get sequence embeddings using [com.robrua.nlp.bert.Bert.embedSequence],
 * [com.robrua.nlp.bert.Bert.embedSequences], [com.robrua.nlp.bert.Bert.embedSequences], or
 * [com.robrua.nlp.bert.Bert.embedSequences]:
 *
 * <blockquote>
 * <pre>
 * `float[] embedding = bert.embedSequence("A sequence");
 * float[][] embeddings = bert.embedSequence("Multiple", "Sequences");`
 * </pre>
 * </blockquote>
 *
 * If you want per-token embeddings, you can use [com.robrua.nlp.bert.Bert.embedTokens], [com.robrua.nlp.bert.Bert.embedTokens],
 * [com.robrua.nlp.bert.Bert.embedTokens], or [com.robrua.nlp.bert.Bert.embedTokens]:
 *
 * <blockquote>
 * <pre>
 * `float[][] embedding = bert.embedTokens("A sequence");
 * float[][][] embeddings = bert.embedTokens("Multiple", "Sequences");`
 * </pre>
 * </blockquote>
 *
 * @author Rob Rua (https://github.com/robrua)
 * @version 1.0.3
 * @since 1.0.3
 *
 * @see [The easy-bert GitHub Repository](https://github.com/robrua/easy-bert)
 * @see [Google's BERT GitHub Repository](https://github.com/google-research/bert)
 */
@ExperimentalPathApi
class Bert private constructor(private val bundle: SavedModelBundle,
                               private val model: ModelDetails,
                               vocabulary: Path) : AutoCloseable {

    private inner class Inputs(inputIds: IntBuffer,
                               inputMask: IntBuffer,
                               segmentIds: IntBuffer,
                               count: Int) : AutoCloseable {
        private val shape = longArrayOf(count.toLong(), model.maxSequenceLength.toLong())
        val inputIds: Tensor<Int> = Tensor.create(shape, inputIds)
        val inputMask: Tensor<Int> = Tensor.create(shape, inputMask)
        val segmentIds: Tensor<Int> = Tensor.create(shape, segmentIds)
        init {
            println("inputs shape = ${shape.joinToString()}")
        }
        override fun close() {
            inputIds.close()
            inputMask.close()
            segmentIds.close()
        }
    }

    private class ModelDetails {
        var doLowerCase = false
        var inputIds: String? = null
        var inputMask: String? = null
        var segmentIds: String? = null
        var pooledOutput: String? = null
        var sequenceOutput: String? = null
        var maxSequenceLength = 0
    }

    private val separatorTokenId: Int
    private val startTokenId: Int
    val tokenizer: FullTokenizer = FullTokenizer(vocabulary, model.doLowerCase)

    init {
        val ids = tokenizer.convert(arrayOf(START_TOKEN, SEPARATOR_TOKEN))
        startTokenId = ids[0]
        separatorTokenId = ids[1]
    }

    override fun close() {
        bundle.close()
    }

    /**
     * Gets a pooled BERT embedding for a single sequence. Sequences are usually individual sentences, but don't have
     * to be.
     *
     * @param sequence
     * the sequence to embed
     * @return the pooled embedding for the sequence
     * @since 1.0.3
     */
    fun embedSequence(sequence: String): FloatArray {
        getInputs(sequence).use { inputs ->
            val output = bundle.session().runner()
                    .feed(model.inputIds, inputs.inputIds)
                    .feed(model.inputMask, inputs.inputMask)
                    .feed(model.segmentIds, inputs.segmentIds)
                    .fetch(model.pooledOutput)
                    .run()
            output[0].use { embedding ->
                val converted = Array(1) { FloatArray(embedding.shape()[1].toInt()) }
                embedding.copyTo(converted)
                return converted[0]
            }
        }
    }

    /**
     * Gets pooled BERT embeddings for multiple sequences. Sequences are usually individual sentences, but don't have
     * to be. The sequences will be processed in parallel as a single batch input to the TensorFlow model.
     *
     * @param sequences
     * the sequences to embed
     * @return the pooled embeddings for the sequences, in the order the input [java.lang.Iterable] provided them
     * @since 1.0.3
     */
    fun embedSequences(sequences: Iterable<String>?): Array<FloatArray> {
        val list: List<String> = Lists.newArrayList(sequences)
        return embedSequences(*list.toTypedArray())
    }

    /**
     * Gets pooled BERT embeddings for multiple sequences. Sequences are usually individual sentences, but don't have to be.
     * The sequences will be processed in parallel as a single batch input to the TensorFlow model.
     *
     * @param sequences
     * the sequences to embed
     * @return the pooled embeddings for the sequences, in the order the input [java.util.Iterator] provided them
     * @since 1.0.3
     */
    fun embedSequences(sequences: Iterator<String>?): Array<FloatArray> {
        val list: List<String> = Lists.newArrayList(sequences)
        return embedSequences(*list.toTypedArray())
    }

    /**
     * Gets pooled BERT embeddings for multiple sequences. Sequences are usually individual sentences, but don't have to be.
     * The sequences will be processed in parallel as a single batch input to the TensorFlow model.
     *
     * @param sequences
     * the sequences to embed
     * @return the pooled embeddings for the sequences, in the order they were provided
     * @since 1.0.3
     */
    fun embedSequences(vararg sequences: String): Array<FloatArray> {
        getInputs(sequences).use { inputs ->
            val output = bundle.session().runner()
                    .feed(model.inputIds, inputs.inputIds)
                    .feed(model.inputMask, inputs.inputMask)
                    .feed(model.segmentIds, inputs.segmentIds)
                    .fetch(model.pooledOutput)
                    .run()
            output[0].use { embedding ->
                val converted = Array(sequences.size) { FloatArray(embedding.shape()[1].toInt()) }
                embedding.copyTo(converted)
                return converted
            }
        }
    }

    /**
     * Gets BERT embeddings for each of the tokens in multiple sequences. Sequences are usually individual sentences, but don't have to be.
     * The sequences will be processed in parallel as a single batch input to the TensorFlow model.
     *
     * @param sequences
     * the sequences to embed
     * @return the token embeddings for the sequences, in the order the input [java.lang.Iterable] provided them
     * @since 1.0.3
     */
    fun embedTokens(sequences: Iterable<String>?): Array<Array<FloatArray>> {
        val list: List<String> = Lists.newArrayList(sequences)
        return embedTokens(*list.toTypedArray())
    }

    /**
     * Gets BERT embeddings for each of the tokens in multiple sequences. Sequences are usually individual sentences, but don't have to be.
     * The sequences will be processed in parallel as a single batch input to the TensorFlow model.
     *
     * @param sequences
     * the sequences to embed
     * @return the token embeddings for the sequences, in the order the input [java.util.Iterator] provided them
     * @since 1.0.3
     */
    fun embedTokens(sequences: Iterator<String>?): Array<Array<FloatArray>> {
        val list: List<String> = Lists.newArrayList(sequences)
        return embedTokens(*list.toTypedArray())
    }

    /**
     * Gets BERT embeddings for each of the tokens in single sequence. Sequences are usually individual sentences,
     * but don't have to be.
     *
     * @param sequence the sequence to embed
     * @return the token embeddings for the sequence
     * @since 1.0.3
     */
    fun embedTokens(sequence: String): Array<FloatArray> {
        getInputs(sequence).use { inputs ->
            val output: MutableList<Tensor<*>> = bundle.session().runner()
                    .feed(model.inputIds, inputs.inputIds)
                    .feed(model.inputMask, inputs.inputMask)
                    .feed(model.segmentIds, inputs.segmentIds)
                    .fetch(model.sequenceOutput)
                    .run()
            output[0].use { embedding: Tensor<*> ->
                // create empty tensor
                val converted: Array<Array<FloatArray>> = Array(1) {
                    Array(embedding.shape()[1].toInt()) {
                        FloatArray(embedding.shape()[2].toInt())
                    }
                }
                embedding.copyTo(converted)
                return converted[0]
            }
        }
    }

    /**
     * Gets BERT embeddings for each of the tokens in multiple sequences. Sequences are usually individual sentences,
     * but don't have to be. The sequences will be processed in parallel as a single batch input to the TensorFlow
     * model.
     *
     * @param sequences the sequences to embed
     * @return the token embeddings for the sequences, in the order they were provided
     * @since 1.0.3
     */
    fun embedTokens(vararg sequences: String): Array<Array<FloatArray>> {
        getInputs(sequences).use { inputs ->
            val output = bundle.session().runner()
                    .feed(model.inputIds, inputs.inputIds)
                    .feed(model.inputMask, inputs.inputMask)
                    .feed(model.segmentIds, inputs.segmentIds)
                    .fetch(model.sequenceOutput)
                    .run()
            output[0].use { embedding: Tensor<*> ->
                val converted = Array(sequences.size) {
                    Array(embedding.shape()[1].toInt()) {
                        FloatArray(embedding.shape()[2].toInt())
                    }
                }
                embedding.copyTo(converted)
                return converted
            }
        }
    }

    private fun getInputs(sequence: String): Inputs {
        val tokens: Array<String> = tokenizer.tokenize(sequence)
        println("tokens = " + tokens.joinToString())
        val inputIds = IntBuffer.allocate(model.maxSequenceLength)
        val inputMask = IntBuffer.allocate(model.maxSequenceLength)
        val segmentIds = IntBuffer.allocate(model.maxSequenceLength)

        /*
         * In BERT:
         * inputIds are the indexes in the vocabulary for each token in the sequence
         * inputMask is a binary mask that shows which inputIds have valid data in them
         * segmentIds are meant to distinguish paired sequences during training tasks. Here they're always 0 since
         * we're only doing inference.
         */
        val ids: IntArray = tokenizer.convert(tokens)
        inputIds.put(startTokenId)
        inputMask.put(1)
        segmentIds.put(0)
        var i = 0
        while (i < ids.size && i < model.maxSequenceLength - 2) {
            inputIds.put(ids[i])
            inputMask.put(1)
            segmentIds.put(0)
            i++
        }
        inputIds.put(separatorTokenId)
        inputMask.put(1)
        segmentIds.put(0)
        while (inputIds.position() < model.maxSequenceLength) {
            inputIds.put(0)
            inputMask.put(0)
            segmentIds.put(0)
        }
        inputIds.rewind()
        inputMask.rewind()
        segmentIds.rewind()
        return Inputs(inputIds, inputMask, segmentIds, 1)
    }

    private fun getInputs(sequences: Array<out String>): Inputs {
        val tokens = tokenizer.tokenize(*sequences)
        val inputIds = IntBuffer.allocate(sequences.size * model.maxSequenceLength)
        val inputMask = IntBuffer.allocate(sequences.size * model.maxSequenceLength)
        val segmentIds = IntBuffer.allocate(sequences.size * model.maxSequenceLength)

        /*
         * In BERT:
         * inputIds are the indexes in the vocabulary for each token in the sequence
         * inputMask is a binary mask that shows which inputIds have valid data in them
         * segmentIds are meant to distinguish paired sequences during training tasks. Here they're always 0 since
         * we're only doing inference.
         */
        var instance = 1
        for (token in tokens) {
            val ids = tokenizer.convert(token)
            inputIds.put(startTokenId)
            inputMask.put(1)
            segmentIds.put(0)
            var i = 0
            while (i < ids.size && i < model.maxSequenceLength - 2) {
                inputIds.put(ids[i])
                inputMask.put(1)
                segmentIds.put(0)
                i++
            }
            inputIds.put(separatorTokenId)
            inputMask.put(1)
            segmentIds.put(0)
            while (inputIds.position() < model.maxSequenceLength * instance) {
                inputIds.put(0)
                inputMask.put(0)
                segmentIds.put(0)
            }
            instance++
        }
        inputIds.rewind()
        inputMask.rewind()
        segmentIds.rewind()
        return Inputs(inputIds, inputMask, segmentIds, sequences.size)
    }

    companion object {
        private const val FILE_COPY_BUFFER_BYTES = 1024 * 1024
        private const val MODEL_DETAILS = "model.json"
        private const val SEPARATOR_TOKEN = "[SEP]"
        private const val START_TOKEN = "[CLS]"
        private const val VOCAB_FILE = "vocab.txt"

        /**
         * Loads a pre-trained BERT model from a TensorFlow saved model saved by the easy-bert Python utilities
         *
         * @param model
         * the model to load
         * @return a ready-to-use BERT model
         * @since 1.0.3
         */
        fun load(model: File): Bert {
            return load(Paths.get(model.toURI()))
        }

        /**
         * Loads a pre-trained BERT model from a TensorFlow saved model saved by the easy-bert Python utilities
         *
         * @param path
         * the path to load the model from
         * @return a ready-to-use BERT model
         * @since 1.0.3
         */
        fun load(path: Path): Bert {
            val absPath = path.toAbsolutePath()
            val model: ModelDetails = ObjectMapper().readValue(
                    absPath.resolve("assets").resolve(MODEL_DETAILS).toFile(),
                    ModelDetails::class.java
            )
            return Bert(
                    SavedModelBundle.load(absPath.toString(), "serve"),
                    model,
                    absPath.resolve("assets").resolve(VOCAB_FILE)
            )
        }

        /**
         * Loads a pre-trained BERT model from a TensorFlow saved model saved by the easy-bert Python utilities. The target resource should be in .zip format.
         *
         * @param resource
         * the resource path to load the model from - should be in .zip format
         * @return a ready-to-use BERT model
         * @since 1.0.3
         */
        @Suppress("UnstableApiUsage")
        fun load(resource: String): Bert {
            var directory: Path? = null
            return try {
                // Create a temp directory to unpack the zip into
                val model: URL = Resources.getResource(resource)
                directory = Files.createTempDirectory("easy-bert-")
                ZipInputStream(Resources.asByteSource(model).openBufferedStream()).use { zip ->
                    // Copy each zip entry into the temp directory
                    var entry: ZipEntry? = zip.nextEntry
                    while (entry != null) {
                        val path = directory.resolve(entry.name)
                        if (entry.name.endsWith("/")) {
                            Files.createDirectories(path)
                        } else {
                            Files.createFile(path)
                            Files.newOutputStream(path).use { output ->
                                val buffer = ByteArray(FILE_COPY_BUFFER_BYTES)
                                var bytes: Int
                                while (zip.read(buffer).also { bytes = it } > 0) {
                                    output.write(buffer, 0, bytes)
                                }
                            }
                        }
                        zip.closeEntry()
                        entry = zip.nextEntry
                    }
                }

                // Load a BERT model from the temp directory
                load(directory)
            } catch (e: IOException) {
                throw RuntimeException(e)
            } finally {
                // Clean up the temp directory
                if (directory != null && Files.exists(directory)) {
                    try {
                        Files.walk(directory)
                                .sorted(Comparator.reverseOrder())
                                .forEach { file: Path ->
                                    try {
                                        Files.delete(file)
                                    } catch (e: IOException) {
                                        throw RuntimeException(e)
                                    }
                                }
                    } catch (e: IOException) {
                        throw RuntimeException(e)
                    }
                }
            }
        }
    }

}