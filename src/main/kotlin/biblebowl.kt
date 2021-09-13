package net.markdrew.biblebowl

const val INDENT_POETRY_LINES = 4

const val DATA_DIR = "data"
const val PRODUCTS_DIR = "products"
const val BANNER = """
      ______                        ____  _ __    __        ____                __
     /_  __/__  _  ______ ______   / __ )(_) /_  / /__     / __ )____ _      __/ /
      / / / _ \| |/_/ __ `/ ___/  / __  / / __ \/ / _ \   / __  / __ \ | /| / / / 
     / / /  __/>  </ /_/ (__  )  / /_/ / / /_/ / /  __/  / /_/ / /_/ / |/ |/ / /  
    /_/  \___/_/|_|\__,_/____/  /_____/_/_.___/_/\___/  /_____/\____/|__/|__/_/   
"""

fun rangeLabel(singular: String, range: IntRange, separator: String = "-", plural: String = "${singular}s"): String =
    if (range.count() == 1) "$singular$separator${range.first}"
    else "$plural$separator${range.first}-${range.last}"
