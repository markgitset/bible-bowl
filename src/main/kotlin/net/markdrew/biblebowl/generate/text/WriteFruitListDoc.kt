package net.markdrew.biblebowl.generate.text

import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.BooleanDefaultTrue
import org.docx4j.wml.ObjectFactory
import org.docx4j.wml.P
import java.io.File
import java.math.BigInteger

fun main() {
    val wordPackage: WordprocessingMLPackage = createFruitList()
    val outputFile = File("fruits.docx")
    wordPackage.save(outputFile)
}

fun createFruitList(): WordprocessingMLPackage {
    val fruits = listOf(
        "Apple" to "A round fruit with a red, green, or yellow skin and a white flesh.",
        "Banana" to "A long, curved fruit with a yellow skin and soft, sweet flesh.",
        "Orange" to "A spherical fruit with a tough, bright orange skin and a sweet, juicy flesh.",
        "Grape" to "A small, juicy fruit with a thin skin and a sweet, pulpy flesh.",
        "Strawberry" to "A sweet, juicy fruit with a red skin and small, edible seeds on the outside.",
        "Pineapple" to "A tropical fruit with a tough, spiky skin and a sweet, juicy flesh inside.",
        "Watermelon" to "A large, juicy fruit with a green skin and a sweet, pink or red flesh inside."
    )

    val wordPackage = WordprocessingMLPackage.createPackage()
    val factory = ObjectFactory()

    val title = factory.createP()
    val titleText = factory.createText()
    titleText.value = "List of Fruits"
//    titleText.isBold = true
    title.content.add(factory.createR().apply {
        content.add(titleText)
    })
    wordPackage.mainDocumentPart.content.add(title)

    val unorderedList: P = factory.createP().apply {
        pPr = factory.createPPr().apply {
            pStyle = factory.createPPrBasePStyle().apply {
                `val` = "ListBullet"
            }
        }
    }

    fruits.forEach { (fruit, description) ->
        val bullet = factory.createP().apply {
            pPr = factory.createPPr().apply {
                numPr = factory.createPPrBaseNumPr().apply {
                    ilvl = factory.createPPrBaseNumPrIlvl().apply { `val` = BigInteger.ZERO }
                    numId = factory.createPPrBaseNumPrNumId().apply { `val` = BigInteger.ONE }
                }
//                pStyle = factory.createPPrBasePStyle().apply { `val` = "Normal" }
                pStyle = factory.createPPrBasePStyle().apply { `val` = "ListBullet" }
                rPr = factory.createParaRPr()
            }
        }
        bullet.content.add(factory.createR().apply {
            rPr = factory.createRPr().apply {
                b = BooleanDefaultTrue()
            }
            content.add(factory.createText().apply {
                value = fruit
//                isBold = true
            })
        })
        bullet.content.add(factory.createR().apply {
            content.add(factory.createText().apply { value = ": $description" })
        })
//        unorderedList.content.add(bullet)
//    wordPackage.mainDocumentPart.content.add(unorderedList)
    wordPackage.mainDocumentPart.content.add(bullet)
    }

    return wordPackage
}
