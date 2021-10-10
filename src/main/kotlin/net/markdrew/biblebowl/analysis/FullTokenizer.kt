package analysis

import com.google.common.io.Resources
import com.robrua.nlp.bert.BasicTokenizer
import com.robrua.nlp.bert.Tokenizer
import com.robrua.nlp.bert.WordpieceTokenizer
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.bufferedReader
import kotlin.io.path.useLines

/**
 * A port of the BERT FullTokenizer in the [BERT GitHub Repository](https://github.com/google-research/bert).
 *
 * It's used to segment input sequences into the BERT tokens that exist in the model's vocabulary. These tokens are
 * later converted into inputIds for the model.
 *
 * It basically just feeds sequences to the [com.robrua.nlp.bert.BasicTokenizer] then passes those results to the
 * [com.robrua.nlp.bert.WordpieceTokenizer]
 *
 * @author Rob Rua (https://github.com/robrua)
 * @version 1.0.3
 * @since 1.0.3
 *
 * @see [The Python tokenization code this is ported from](https://github.com/google-research/bert/blob/master/tokenization.py)
 *
 * Creates a BERT [com.robrua.nlp.bert.FullTokenizer]
 *
 * @param vocabularyPath the path to the BERT vocabulary file to use for tokenization
 * @param doLowerCase whether to convert sequences to lower case during tokenization
 * @since 1.0.3
 */
@ExperimentalPathApi
class FullTokenizer @JvmOverloads constructor(vocabularyPath: Path,
                                              doLowerCase: Boolean = DEFAULT_DO_LOWER_CASE) : Tokenizer() {
    private val basic: BasicTokenizer = BasicTokenizer(doLowerCase)
    val vocabulary: Map<String, Int> = loadVocabulary(vocabularyPath)
    private val wordpiece: WordpieceTokenizer = WordpieceTokenizer(vocabulary)

    /**
     * Creates a BERT [com.robrua.nlp.bert.FullTokenizer]
     *
     * @param vocabulary
     * the BERT vocabulary file to use for tokenization
     * @since 1.0.3
     */
    constructor(vocabulary: File) : this(Paths.get(vocabulary.toURI()), DEFAULT_DO_LOWER_CASE)

    /**
     * Creates a BERT [com.robrua.nlp.bert.FullTokenizer]
     *
     * @param vocabulary
     * the BERT vocabulary file to use for tokenization
     * @param doLowerCase
     * whether to convert sequences to lower case during tokenization
     * @since 1.0.3
     */
    constructor(vocabulary: File, doLowerCase: Boolean) : this(Paths.get(vocabulary.toURI()), doLowerCase)

    /**
     * Creates a BERT [com.robrua.nlp.bert.FullTokenizer]
     *
     * @param vocabularyResource
     * the resource path to the BERT vocabulary file to use for tokenization
     * @since 1.0.3
     */
    constructor(vocabularyResource: String) : this(toPath(vocabularyResource), DEFAULT_DO_LOWER_CASE)

    /**
     * Creates a BERT [com.robrua.nlp.bert.FullTokenizer]
     *
     * @param vocabularyResource
     * the resource path to the BERT vocabulary file to use for tokenization
     * @param doLowerCase
     * whether to convert sequences to lower case during tokenization
     * @since 1.0.3
     */
    constructor(vocabularyResource: String, doLowerCase: Boolean) : this(toPath(vocabularyResource), doLowerCase)

    /**
     * Converts BERT sub-tokens into their inputIds
     *
     * @param tokens
     * the tokens to convert
     * @return the inputIds for the tokens
     * @since 1.0.3
     */
    fun convert(tokens: Array<String>): IntArray =
            tokens.map { key: String -> vocabulary[key]!! }.toIntArray()

    //    @Override
    //    public String[] tokenize(final String sequence) {
    //        return Arrays.stream(wordpiece.tokenize(basic.tokenize(sequence)))
    //                .flatMap(Stream::of)
    //                .toArray(String[]::new);
    //    }
    override fun tokenize(sequence: String): Array<String> =
            wordpiece.tokenize(*basic.tokenize(sequence)).flatten().toTypedArray()

    //    @Override
    //    public String[][] tokenize(final String... sequences) {
    //        return Arrays.stream(basic.tokenize(sequences))
    //                .map((final String[] tokens) -> Arrays.stream(wordpiece.tokenize(tokens))
    //                    .flatMap(Stream::of)
    //                    .toArray(String[]::new)
    //                ).toArray(String[][]::new);
    //    }
    override fun tokenize(vararg sequences: String): Array<Array<String>> = basic.tokenize(*sequences)
            .map { tokens -> wordpiece.tokenize(*tokens).flatten().toTypedArray() }
            .toTypedArray()

    companion object {

        private const val DEFAULT_DO_LOWER_CASE = false

        private fun loadVocabulary(file: Path): Map<String, Int> = file.useLines { lines ->
            lines.mapIndexed { index, line -> line.trim() to index }.toMap()
        }

        @Suppress("UnstableApiUsage")
        private fun toPath(resource: String): Path =
                Paths.get(Resources.getResource(resource).toURI())

    }

}