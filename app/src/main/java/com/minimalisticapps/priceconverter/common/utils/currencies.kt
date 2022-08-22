package com.minimalisticapps.priceconverter.common.utils

// Data Sources:
//      - https://en.wikipedia.org/wiki/List_of_circulating_currencies
//      - https://en.wikipedia.org/wiki/Currency_symbol

data class Currency(
    // List of unicode flags of countries who use the shitcoin
    val flags: List<String>,

    // Probably useless at it is not unique
    val sign: String? = null
)

fun getFlagsForCurrency(currencyCode: String): List<String> {
    var flags = ALLOWED_ISO_CURRENCIES[currencyCode]?.flags ?: listOf()
    if (currencyCode in CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG) {
        flags = flags.slice(0 until 1)
    }

    return flags
}

val CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG = listOf<String>() // "GBP", "EUR", "USD"

val ALLOWED_ISO_CURRENCIES = mapOf(
    "AED" to Currency(
        sign = "د.إ",
        flags = listOf("\uD83C\uDDE6\uD83C\uDDEA")
    ), // United Arab Emirates dirham
    "AFN" to Currency(
        sign = "؋",
        flags = listOf("\uD83C\uDDE6\uD83C\uDDEB")
    ), // Afghan afghani
    "ALL" to Currency(
        sign = "L",
        flags = listOf("\uD83C\uDDE6\uD83C\uDDF1")
    ), // Albanian lek
    "AMD" to Currency(sign = "֏", flags = listOf("\uD83C\uDDE6\uD83C\uDDF2")), // Armenian dram
    "ANG" to Currency(
        flags = listOf(
            "\uD83C\uDDE8\uD83C\uDDFC", // Curaçao
            "\uD83C\uDDF8\uD83C\uDDFD", // Sint Maarten
        )
    ), // Netherlands Antillean guilder
    "AOA" to Currency(sign = "Kz", flags = listOf("\uD83C\uDDE6\uD83C\uDDF4")), // Angolan kwanza
    "ARS" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF7")), // Argentine peso
    "AUD" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF7")), // Australian dollar
    "AWG" to Currency(sign = "ƒ", flags = listOf("\uD83C\uDDE6\uD83C\uDDFC")), // Aruban florin
    "AZN" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDFF")), // Azerbaijani manat
    "BAM" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDE6")), // Bosnia and Herzegovina convertible mark
    "BBD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDE7")), // Barbados dollar
    "BDT" to Currency(sign = "৳", flags = listOf("\uD83C\uDDE7\uD83C\uDDE9")), // Bangladeshi taka
    "BGN" to Currency(sign = "лв.", flags = listOf("\uD83C\uDDE7\uD83C\uDDEC")), // Bulgarian lev
    "BHD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDED")), // Bahraini dinar
    "BIF" to Currency(sign = "FBu", flags = listOf("\uD83C\uDDE7\uD83C\uDDEE")), // Burundian franc
    "BMD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF2")), // Bermudian dollar
    "BND" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF3")), // Brunei dollar
    "BOB" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF4")), // Boliviano
    "BRL" to Currency(sign = "R\$", flags = listOf("\uD83C\uDDE7\uD83C\uDDF7")), // Brazilian real
    "BSD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF8")), // Bahamian dollar
    "BTN" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF9")), // Bhutanese ngultrum
    "BWP" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDFC")), // Botswana pula
    "BYN" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDFE")), // Belarusian ruble
    "BZD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDFF")), // Belize dollar
    "CAD" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDE6")), // Canadian dollar
    "CDF" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDE9")), // Congolese franc
    "CHF" to Currency(
        flags = listOf(
            "\uD83C\uDDE8\uD83C\uDDED", // Switzerland
            "\uD83C\uDDF1\uD83C\uDDEE", // Liechtenstein
        )
    ), // Swiss franc
    "CLP" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDF1")), // Chilean peso
    "COP" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDF4")), // Colombian peso
    "CRC" to Currency(sign = "₡", flags = listOf("\uD83C\uDDE8\uD83C\uDDF7")), // Costa Rican colon
    "CUC" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFA")), // Cuban convertible peso
    "CUP" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFA")), // Cuban peso
    "CVE" to Currency(
        sign = "Esc",
        flags = listOf("\uD83C\uDDE8\uD83C\uDDFB")
    ), // Cape Verdean escudo
    "CZK" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFF")), // Czech koruna
    "DJF" to Currency(flags = listOf("\uD83C\uDDE9\uD83C\uDDEF")), // Djiboutian franc
    "DKK" to Currency(
        sign = "kr",
        flags = listOf(
            "\uD83C\uDDE9\uD83C\uDDF0", // Denmark
            "\uD83C\uDDEB\uD83C\uDDF4", // Faroe Islands
            "\uD83C\uDDEC\uD83C\uDDF1", // Greenland
        )
    ), // Danish krone
    "DOP" to Currency(flags = listOf("\uD83C\uDDE9\uD83C\uDDF2")), // Dominican peso
    "DZD" to Currency(sign = "دج", flags = listOf("\uD83C\uDDE9\uD83C\uDDFF")), // Algerian dinar
    "EGP" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDEC")), // Egyptian pound
    "ERN" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDF7")), // Eritrean nakfa
    "ETB" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDF9")), // Ethiopian birr
    "EUR" to Currency(
        sign = "€",
        flags = listOf(
            "\uD83C\uDDEA\uD83C\uDDFA", // EU
            "\uD83C\uDDE6\uD83C\uDDFD", // Åland Islands
            "\uD83C\uDDE6\uD83C\uDDE9", // Andorra
            "\uD83C\uDDE6\uD83C\uDDF9", // Austria
            "\uD83C\uDDE7\uD83C\uDDEA", // Belgium
            "\uD83C\uDDE8\uD83C\uDDFE", // Cyprus
            "\uD83C\uDDEA\uD83C\uDDEA", // Estonia
            "\uD83C\uDDEB\uD83C\uDDEE", // Finland
            "\uD83C\uDDEB\uD83C\uDDF7", // France
            // "", // French Southern and Antarctic Lands
            "\uD83C\uDDE9\uD83C\uDDEA", // Germany
            "\uD83C\uDDEC\uD83C\uDDF7", // Greece
            "\uD83C\uDDEC\uD83C\uDDF5", // Guadeloupe
            "\uD83C\uDDEE\uD83C\uDDEA", // Ireland
            "\uD83C\uDDEE\uD83C\uDDF9", // Italy
            "\uD83C\uDDF1\uD83C\uDDFB", // Latvia
            "\uD83C\uDDF1\uD83C\uDDF9", // Lithuania
            "\uD83C\uDDF1\uD83C\uDDFA", // Luxembourg
            "\uD83C\uDDF2\uD83C\uDDF9", // Malta
            "\uD83C\uDDEC\uD83C\uDDEB", // French Guiana
            "\uD83C\uDDF2\uD83C\uDDF6", // Martinique
            "\uD83C\uDDFE\uD83C\uDDF9", // Mayotte
            "\uD83C\uDDF2\uD83C\uDDE8", // Monaco
            "\uD83C\uDDF2\uD83C\uDDEA", // Montenegro
            "\uD83C\uDDF3\uD83C\uDDF1", // Netherlands
            "\uD83C\uDDF5\uD83C\uDDF9", // Portugal
            "\uD83C\uDDF7\uD83C\uDDEA", // Réunion
            "\uD83C\uDDE7\uD83C\uDDF1", // Saint Barthélemy
            "\uD83C\uDDF2\uD83C\uDDEB", // Saint Martin
            "\uD83C\uDDF5\uD83C\uDDF2", // Saint Pierre and Miquelon
            "\uD83C\uDDF8\uD83C\uDDF2", // San Marino
            "\uD83C\uDDF8\uD83C\uDDF0", // Slovakia
            "\uD83C\uDDF8\uD83C\uDDEE", // Slovenia
            "\uD83C\uDDEA\uD83C\uDDF8", // Spain
            "\uD83C\uDDFB\uD83C\uDDE6", // Vatican City
        )
    ), // Euro
    "FJD" to Currency(flags = listOf("\uD83C\uDDEB\uD83C\uDDEF")), // Fiji dollar
    "FKP" to Currency(flags = listOf("\uD83C\uDDEB\uD83C\uDDF0")), // Falkland Islands pound
    "GBP" to Currency(
        sign = "₤",
        flags = listOf(
            "\uD83C\uDDEC\uD83C\uDDE7", // United Kingdom,
            "\uD83C\uDDEE\uD83C\uDDF2", // Isle of Man
            "\uD83C\uDDEF\uD83C\uDDEA", // Jersey
            "\uD83C\uDDEC\uD83C\uDDEC", // Guernsey
            "\uD83C\uDDF9\uD83C\uDDE6", // Tristan da Cunha
        )
    ), // Pound sterling
    "GEL" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDEA")), // Georgian lari
    "GHS" to Currency(sign = "₵", flags = listOf("\uD83C\uDDEC\uD83C\uDDED")), // Ghanaian cedi
    "GIP" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDEE")), // Gibraltar pound
    "GMD" to Currency(sign = "D", flags = listOf("\uD83C\uDDEC\uD83C\uDDF2")), // Gambian dalasi
    "GNF" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDF3")), // Guinean franc
    "GTQ" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDF9")), // Guatemalan quetzal
    "GYD" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDFE")), // Guyanese dollar
    "HKD" to Currency(flags = listOf("\uD83C\uDDED\uD83C\uDDF0")), // Hong Kong dollar
    "HNL" to Currency(sign = "L", flags = listOf("\uD83C\uDDED\uD83C\uDDF3")), // Honduran lempira
    "HRK" to Currency(sign = "kn", flags = listOf("\uD83C\uDDED\uD83C\uDDF7")), // Croatian kuna
    "HTG" to Currency(sign = "G", flags = listOf("\uD83C\uDDED\uD83C\uDDF9")), // Haitian gourde
    "HUF" to Currency(sign = "Ft", flags = listOf("\uD83C\uDDED\uD83C\uDDFA")), // Hungarian forint
    "IDR" to Currency(flags = listOf("\uD83C\uDDEE\uD83C\uDDE9")), // Indonesian rupiah
    "ILS" to Currency(
        sign = "₪",
        flags = listOf("\uD83C\uDDEE\uD83C\uDDF1")
    ), // Israeli new shekel
    "INR" to Currency(
        flags = listOf(
            "\uD83C\uDDEE\uD83C\uDDF3", // India
            "\uD83C\uDDE7\uD83C\uDDF9", // Bhutan
        )
    ), // Indian rupee
    "IQD" to Currency(sign = "د.ع", flags = listOf("\uD83C\uDDEE\uD83C\uDDF6")), // Iraqi dinar
    "IRR" to Currency(sign = "﷼", flags = listOf("\uD83C\uDDEE\uD83C\uDDF7")), // Iranian rial
    "ISK" to Currency(flags = listOf("\uD83C\uDDEE\uD83C\uDDF8")), // Icelandic króna (plural: krónur)
    "JMD" to Currency(flags = listOf("\uD83C\uDDEF\uD83C\uDDF2")), // Jamaican dollar
    "JOD" to Currency(sign = "د.أ", flags = listOf("\uD83C\uDDEF\uD83C\uDDF4")), // Jordanian dinar
    "JPY" to Currency(sign = "¥", flags = listOf("\uD83C\uDDEF\uD83C\uDDF5")), // Japanese yen
    "KES" to Currency(flags = listOf("\uD83C\uDDF0\uD83C\uDDEA")), // Kenyan shilling
    "KGS" to Currency(flags = listOf("\uD83C\uDDF0\uD83C\uDDEC")), // Kyrgyzstani som
    "KHR" to Currency(sign = "៛", flags = listOf("\uD83C\uDDF0\uD83C\uDDED")), // Cambodian riel
    "KMF" to Currency(flags = listOf("\uD83C\uDDF0\uD83C\uDDF2")), // Comoro franc
    "KPW" to Currency(
        sign = "₩",
        flags = listOf("\uD83C\uDDF0\uD83C\uDDF5")
    ), // North Korean won
    "KRW" to Currency(
        sign = "₩",
        flags = listOf("\uD83C\uDDF0\uD83C\uDDF7")
    ), // South Korean won
    "KWD" to Currency(sign = "د.ك", flags = listOf("\uD83C\uDDF0\uD83C\uDDFC")), // Kuwaiti dinar
    "KYD" to Currency(flags = listOf("\uD83C\uDDF0\uD83C\uDDFE")), // Cayman Islands dollar
    "KZT" to Currency(sign = "₸", flags = listOf("\uD83C\uDDF0\uD83C\uDDFF")), // Kazakhstani tenge
    "LAK" to Currency(
        sign = "₭",
        flags = listOf("\uD83C\uDDF1\uD83C\uDDE6")
    ), // Lao kip
    "LBP" to Currency(flags = listOf("\uD83C\uDDF1\uD83C\uDDE7")), // Lebanese pound
    "LKR" to Currency(flags = listOf("\uD83C\uDDF1\uD83C\uDDF0")), // Sri Lankan rupee
    "LRD" to Currency(flags = listOf("\uD83C\uDDF1\uD83C\uDDF7")), // Liberian dollar
    "LSL" to Currency(flags = listOf("\uD83C\uDDF1\uD83C\uDDF8")), // Lesotho loti
    "LYD" to Currency(sign = ".د", flags = listOf("\uD83C\uDDF1\uD83C\uDDFE")), // Libyan dinar
    "MAD" to Currency(
        sign = "د.م.",
        flags = listOf(
            "\uD83C\uDDF2\uD83C\uDDE6", // Marocco
            "\uD83C\uDDEA\uD83C\uDDED", // Western Sahara
        )
    ), // Moroccan dirham
    "MDL" to Currency(sign = "L", flags = listOf("\uD83C\uDDF2\uD83C\uDDE9")), // Moldovan leu
    "MGA" to Currency(sign = "Ar", flags = listOf("\uD83C\uDDF2\uD83C\uDDEC")), // Malagasy ariary
    "MKD" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDF0")), // Macedonian denar
    "MMK" to Currency(sign = "K", flags = listOf("\uD83C\uDDF2\uD83C\uDDF2")), // Myanmar kyat
    "MNT" to Currency(
        sign = "₮",
        flags = listOf("\uD83C\uDDF2\uD83C\uDDF3")
    ), // Mongolian tögrög
    "MOP" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDF4")), // Macanese pataca
    "MRU" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDF7")), // Mauritanian ouguiya
    "MUR" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDFA")), // Mauritian rupee
    "MVR" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDFB")), // Maldivian rufiyaa
    "MWK" to Currency(sign = "K", flags = listOf("\uD83C\uDDF2\uD83C\uDDFC")), // Malawian kwacha
    "MXN" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDFD")), // Mexican peso
    "MYR" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDFE")), // Malaysian ringgit
    "MZN" to Currency(flags = listOf("\uD83C\uDDF2\uD83C\uDDFF")), // Mozambican metical
    "NAD" to Currency(flags = listOf("\uD83C\uDDF3\uD83C\uDDE6")), // Namibian dollar
    "NGN" to Currency(
        sign = "₦",
        flags = listOf("\uD83C\uDDF3\uD83C\uDDEC")
    ), // Nigerian naira
    "NIO" to Currency(flags = listOf("\uD83C\uDDF3\uD83C\uDDEE")), // Nicaraguan córdoba
    "NOK" to Currency(sign = "kr", flags = listOf("\uD83C\uDDF3\uD83C\uDDF4")), // Norwegian krone
    "NPR" to Currency(flags = listOf("\uD83C\uDDF3\uD83C\uDDF5")), // Nepalese rupee
    "NZD" to Currency(
        flags = listOf(
            "\uD83C\uDDF3\uD83C\uDDFF", // New Zealand
            "\uD83C\uDDE8\uD83C\uDDF0", // Cook Islands
            "\uD83C\uDDF3\uD83C\uDDFA", // Niue
            "\uD83C\uDDF5\uD83C\uDDF3", // Pitcairn Islands
            "\uD83C\uDDF9\uD83C\uDDF0", // Tokelau
        )
    ), // New Zealand dollar
    "OMR" to Currency(sign = "ر.ع. ", flags = listOf("\uD83C\uDDF4\uD83C\uDDF2")), // Omani rial
    "PAB" to Currency(flags = listOf("\uD83C\uDDF5\uD83C\uDDE6")), // Panamanian balboa
    "PEN" to Currency(flags = listOf("\uD83C\uDDF5\uD83C\uDDEA")), // Peruvian sol
    "PGK" to Currency(
        sign = "K",
        flags = listOf("\uD83C\uDDF5\uD83C\uDDEC")
    ), // Papua New Guinean kina
    "PHP" to Currency(
        sign = "₱",
        flags = listOf("\uD83C\uDDF5\uD83C\uDDED")
    ), // Philippine peso
    "PKR" to Currency(flags = listOf("\uD83C\uDDF5\uD83C\uDDF0")), // Pakistani rupee
    "PLN" to Currency(
        sign = "zł",
        flags = listOf("\uD83C\uDDF5\uD83C\uDDF1")
    ), // Polish złoty
    "PYG" to Currency(
        sign = "₲",
        flags = listOf("\uD83C\uDDF5\uD83C\uDDFE")
    ), // Paraguayan guaraní
    "QAR" to Currency(sign = "ر.ق", flags = listOf("\uD83C\uDDF6\uD83C\uDDE6")), // Qatari riyal
    "RON" to Currency(sign = "L", flags = listOf("\uD83C\uDDF7\uD83C\uDDF4")), // Romanian leu
    "RSD" to Currency(sign = "дин", flags = listOf("\uD83C\uDDF7\uD83C\uDDF8")), // Serbian dinar
    "CNY" to Currency(
        flags = listOf(
            "\uD83C\uDDE8\uD83C\uDDF3", // China
        )
    ), // Renminbi
    "RUB" to Currency(
        sign = "₽",
        flags = listOf("\uD83C\uDDF7\uD83C\uDDFA")
    ), // Russian ruble
    "RWF" to Currency(flags = listOf("\uD83C\uDDF7\uD83C\uDDFC")), // Rwandan franc
    "SAR" to Currency(sign = "ر.س", flags = listOf("\uD83C\uDDF8\uD83C\uDDE6")), // Saudi riyal
    "SBD" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDE7")), // Solomon Islands dollar
    "SCR" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDE8")), // Seychelles rupee
    "SDG" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDE9")), // Sudanese pound
    "SEK" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDEA")), // Swedish krona (plural: kronor)
    "SGD" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDEC")), // Singapore dollar
    "SHP" to Currency(
        flags = listOf(
            "\uD83C\uDDF8\uD83C\uDDED", // Saint Helena
            "\uD83C\uDDE6\uD83C\uDDE8", // Ascension Island
        )
    ), // Saint Helena pound
    "SLL" to Currency(
        sign = "Le",
        flags = listOf("\uD83C\uDDF8\uD83C\uDDF1")
    ), // Sierra Leonean leone
    "SOS" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDF4")), // Somali shilling
    "SRD" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDF7")), // Surinamese dollar
    "SSP" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDF8")), // South Sudanese pound
    "STN" to Currency(
        sign = "Db",
        flags = listOf("\uD83C\uDDF8\uD83C\uDDF9")
    ), // São Tomé and Príncipe dobra
    "SVC" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDFB")), // Salvadoran colón
    "SYP" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDFE")), // Syrian pound
    "SZL" to Currency(flags = listOf("\uD83C\uDDF8\uD83C\uDDFF")), // Swazi lilangeni
    "THB" to Currency(sign = "฿", flags = listOf("\uD83C\uDDF9\uD83C\uDDED")), // Thai baht
    "TJS" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDEF")), // Tajikistani somoni
    "TMT" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDF2")), // Turkmenistan manat
    "TND" to Currency(sign = "د.ت", flags = listOf("\uD83C\uDDF9\uD83C\uDDF3")), // Tunisian dinar
    "TOP" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDF4")), // Tongan paʻanga
    "TRY" to Currency(sign = "₺", flags = listOf("\uD83C\uDDF9\uD83C\uDDF7")), // Turkish lira
    "TTD" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDF9")), // Trinidad and Tobago dollar
    "TWD" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDF9")), // New Taiwan dollar
    "TZS" to Currency(flags = listOf("\uD83C\uDDF9\uD83C\uDDFF")), // Tanzanian shilling
    "UAH" to Currency(
        sign = "₴",
        flags = listOf("\uD83C\uDDFA\uD83C\uDDE6")
    ), // Ukrainian hryvnia
    "UGX" to Currency(flags = listOf("\uD83C\uDDFA\uD83C\uDDEC")), // Ugandan shilling
    "USD" to Currency(
        sign = "$",
        flags = listOf(
            "\uD83C\uDDFA\uD83C\uDDF8", // United States
            "\uD83C\uDDE6\uD83C\uDDF8", // American Samoa
            "\uD83C\uDDEE\uD83C\uDDF4", // British Indian Ocean Territory
            "\uD83C\uDDFB\uD83C\uDDEC", // British Virgin Islands
            "\uD83C\uDDE7\uD83C\uDDF6", // Caribbean Netherlands
            "\uD83C\uDDEA\uD83C\uDDE8", // Ecuador
            "\uD83C\uDDF8\uD83C\uDDFB", // El Salvador
            "\uD83C\uDDEC\uD83C\uDDFA", // Guam
            "\uD83C\uDDF2\uD83C\uDDED", // Marshall Islands
            "\uD83C\uDDEB\uD83C\uDDF2", // Federated States of Micronesia
            "\uD83C\uDDF2\uD83C\uDDF5", // Northern Mariana Islands
            "\uD83C\uDDF5\uD83C\uDDFC", // Palau
            "\uD83C\uDDF5\uD83C\uDDE6", // Panama
            "\uD83C\uDDF5\uD83C\uDDF7", // Puerto Rico
            "\uD83C\uDDF9\uD83C\uDDF1", // Timor-Leste
            "\uD83C\uDDF9\uD83C\uDDE8", // Turks and Caicos Islands
            "\uD83C\uDDFB\uD83C\uDDEE", // U.S. Virgin Islands
        )
    ), // United States dollar
    "UYU" to Currency(flags = listOf("\uD83C\uDDFA\uD83C\uDDFE")), // Uruguayan peso
    "UYW" to Currency(
        flags = listOf(
            "\uD83C\uDDFA\uD83C\uDDFE", // Uruguay
        )
    ), // Unidad previsional
    "UZS" to Currency(flags = listOf("\uD83C\uDDFA\uD83C\uDDFF")), // Uzbekistan som
    "VED" to Currency(flags = listOf("\uD83C\uDDFB\uD83C\uDDEA")), // Venezuelan bolívar digital
    "VES" to Currency(flags = listOf("\uD83C\uDDFB\uD83C\uDDEA")), // Venezuelan bolívar soberano
    "VND" to Currency(flags = listOf("\uD83C\uDDFB\uD83C\uDDF3")), // Vietnamese đồng
    "VUV" to Currency(sign = "Vt", flags = listOf("\uD83C\uDDFB\uD83C\uDDFA")), // Vanuatu vatu
    "WST" to Currency(flags = listOf("\uD83C\uDDFC\uD83C\uDDF8")), // Samoan tala
    "XAF" to Currency(
        flags = listOf(
            "\uD83C\uDDE8\uD83C\uDDF2", // Cameroon
            "\uD83C\uDDE8\uD83C\uDDEB", // Central African Republic
            "\uD83C\uDDE8\uD83C\uDDEC", // Republic of the Congo
            "\uD83C\uDDF9\uD83C\uDDE9", // Chad
            "\uD83C\uDDEC\uD83C\uDDF6", // Equatorial Guinea
            "\uD83C\uDDEC\uD83C\uDDE6", // Gabon
        )
    ), // CFA franc BEAC
    "XAG" to Currency(flags = listOf()), // Silver (one troy ounce)
    "XAU" to Currency(flags = listOf()), // Gold (one troy ounce)
    "XCD" to Currency(
        flags = listOf(
            "\uD83C\uDDE6\uD83C\uDDEE", // Anguilla
            "\uD83C\uDDE6\uD83C\uDDEC", // Antigua and Barbuda
            "\uD83C\uDDE9\uD83C\uDDF2", // Dominica
            "\uD83C\uDDEC\uD83C\uDDE9", // Grenada
            "\uD83C\uDDF2\uD83C\uDDF8", // Montserrat
            "\uD83C\uDDF0\uD83C\uDDF3", // Saint Kitts and Nevis
            "\uD83C\uDDF1\uD83C\uDDE8", // Saint Lucia
            "\uD83C\uDDFB\uD83C\uDDE8", // Saint Vincent and the Grenadines
        )
    ), // East Caribbean dollar
    "XOF" to Currency(
        flags = listOf(
            "\uD83C\uDDE7\uD83C\uDDEF", // Benin
            "\uD83C\uDDE7\uD83C\uDDEB", // Burkina Faso
            "\uD83C\uDDE8\uD83C\uDDEE", // Côte d'Ivoire
            "\uD83C\uDDEC\uD83C\uDDFC", // Guinea-Bissau
            "\uD83C\uDDF2\uD83C\uDDF1", // Mali
            "\uD83C\uDDF3\uD83C\uDDEA", // Niger
            "\uD83C\uDDF8\uD83C\uDDF3", // Senegal
            "\uD83C\uDDF9\uD83C\uDDEC", // Togo
        )
    ), // CFA franc BCEAO (West African CFA franc)
    "XPD" to Currency(flags = listOf()), // Palladium (one troy ounce)
    "XPF" to Currency(
        flags = listOf(
            "\uD83C\uDDF5\uD83C\uDDEB", // French Polynesia
            "\uD83C\uDDF3\uD83C\uDDE8", // New Caledonia
            "\uD83C\uDDFC\uD83C\uDDEB", // Wallis and Futuna
        )
    ), // CFP franc (franc Pacifique)
    "XPT" to Currency(flags = listOf()), // Platinum (one troy ounce)
    "YER" to Currency(sign = "ر.ي", flags = listOf("\uD83C\uDDFE\uD83C\uDDEA")), // Yemeni rial
    "ZAR" to Currency(
        sign = "R",
        flags = listOf(
            "\uD83C\uDDF8\uD83C\uDDFF", // Eswatini
            "\uD83C\uDDF1\uD83C\uDDF8", // Lesotho
            "\uD83C\uDDF3\uD83C\uDDE6", // Namibia
            "\uD83C\uDDFF\uD83C\uDDE6", // South Africa
        )
    ), // South African rand
    "ZMW" to Currency(sign = "K", flags = listOf("\uD83C\uDDFF\uD83C\uDDF2")), // Zambian kwacha
    "ZWL" to Currency(flags = listOf("\uD83C\uDDFF\uD83C\uDDFC")), // Zimbabwean dollar
)
