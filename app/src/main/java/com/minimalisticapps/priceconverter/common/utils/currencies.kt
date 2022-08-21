package com.minimalisticapps.priceconverter.common.utils

data class Currency(val flags: List<String>)

fun getFlagsForCurrency(currencyCode: String): List<String> {
    var flags = ALLOWED_ISO_CURRENCIES[currencyCode]?.flags ?: listOf()
    if (currencyCode in CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG) {
        flags = flags.slice(0 until 1)
    }

    return flags
}

val CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG = listOf("GBP", "EUR")

val ALLOWED_ISO_CURRENCIES = mapOf(
    "AED" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDEA")), // United Arab Emirates dirham
    "AFN" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDEB")), // Afghan afghani
    "ALL" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF1")), // Albanian lek
    "AMD" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF2")), // Armenian dram
    "ANG" to Currency(
        flags = listOf(
            "\uD83C\uDDE8\uD83C\uDDFC", // Curaçao
            "\uD83C\uDDF8\uD83C\uDDFD", // Sint Maarten
        )
    ), // Netherlands Antillean guilder
    "AOA" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF4")), // Angolan kwanza
    "ARS" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF7")), // Argentine peso
    "AUD" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDF7")), // Australian dollar
    "AWG" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDFC")), // Aruban florin
    "AZN" to Currency(flags = listOf("\uD83C\uDDE6\uD83C\uDDFF")), // Azerbaijani manat
    "BAM" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDE6")), // Bosnia and Herzegovina convertible mark
    "BBD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDE7")), // Barbados dollar
    "BDT" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDE9")), // Bangladeshi taka
    "BGN" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDEC")), // Bulgarian lev
    "BHD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDED")), // Bahraini dinar
    "BIF" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDEE")), // Burundian franc
    "BMD" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF2")), // Bermudian dollar
    "BND" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF3")), // Brunei dollar
    "BOB" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF4")), // Boliviano
    "BRL" to Currency(flags = listOf("\uD83C\uDDE7\uD83C\uDDF7")), // Brazilian real
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
    "CRC" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDF7")), // Costa Rican colon
    "CUC" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFA")), // Cuban convertible peso
    "CUP" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFA")), // Cuban peso
    "CVE" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFB")), // Cape Verdean escudo
    "CZK" to Currency(flags = listOf("\uD83C\uDDE8\uD83C\uDDFF")), // Czech koruna
    "DJF" to Currency(flags = listOf("\uD83C\uDDE9\uD83C\uDDEF")), // Djiboutian franc
    "DKK" to Currency(
        flags = listOf(
            "\uD83C\uDDE9\uD83C\uDDF0", // Denmark
            "\uD83C\uDDEB\uD83C\uDDF4", // Faroe Islands
            "\uD83C\uDDEC\uD83C\uDDF1", // Greenland
        )
    ), // Danish krone
    "DOP" to Currency(flags = listOf("\uD83C\uDDE9\uD83C\uDDF2")), // Dominican peso
    "DZD" to Currency(flags = listOf("\uD83C\uDDE9\uD83C\uDDFF")), // Algerian dinar
    "EGP" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDEC")), // Egyptian pound
    "ERN" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDF7")), // Eritrean nakfa
    "ETB" to Currency(flags = listOf("\uD83C\uDDEA\uD83C\uDDF9")), // Ethiopian birr
    "EUR" to Currency(
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
        flags = listOf(
            "\uD83C\uDDEC\uD83C\uDDE7", // United Kingdom,
            "\uD83C\uDDEE\uD83C\uDDF2", // Isle of Man
            "\uD83C\uDDEF\uD83C\uDDEA", // Jersey
            "\uD83C\uDDEC\uD83C\uDDEC", // Guernsey
            "\uD83C\uDDF9\uD83C\uDDE6", // Tristan da Cunha
        )
    ), // Pound sterling
    "GEL" to Currency(flags = listOf("\uD83C\uDDEC\uD83C\uDDEA")), // Georgian lari
    "GHS" to Currency(flags = listOf()), // Ghanaian cedi
    "GIP" to Currency(flags = listOf()), // Gibraltar pound
    "GMD" to Currency(flags = listOf()), // Gambian dalasi
    "GNF" to Currency(flags = listOf()), // Guinean franc
    "GTQ" to Currency(flags = listOf()), // Guatemalan quetzal
    "GYD" to Currency(flags = listOf()), // Guyanese dollar
    "HKD" to Currency(flags = listOf()), // Hong Kong dollar
    "HNL" to Currency(flags = listOf()), // Honduran lempira
    "HRK" to Currency(flags = listOf()), // Croatian kuna
    "HTG" to Currency(flags = listOf()), // Haitian gourde
    "HUF" to Currency(flags = listOf()), // Hungarian forint
    "IDR" to Currency(flags = listOf()), // Indonesian rupiah
    "ILS" to Currency(flags = listOf()), // Israeli new shekel
    "INR" to Currency(flags = listOf()), // Indian rupee
    "IQD" to Currency(flags = listOf()), // Iraqi dinar
    "IRR" to Currency(flags = listOf()), // Iranian rial
    "ISK" to Currency(flags = listOf()), // Icelandic króna (plural: krónur)
    "JMD" to Currency(flags = listOf()), // Jamaican dollar
    "JOD" to Currency(flags = listOf()), // Jordanian dinar
    "JPY" to Currency(flags = listOf()), // Japanese yen
    "KES" to Currency(flags = listOf()), // Kenyan shilling
    "KGS" to Currency(flags = listOf()), // Kyrgyzstani som
    "KHR" to Currency(flags = listOf()), // Cambodian riel
    "KMF" to Currency(flags = listOf()), // Comoro franc
    "KPW" to Currency(flags = listOf()), // North Korean won
    "KRW" to Currency(flags = listOf()), // South Korean won
    "KWD" to Currency(flags = listOf()), // Kuwaiti dinar
    "KYD" to Currency(flags = listOf()), // Cayman Islands dollar
    "KZT" to Currency(flags = listOf()), // Kazakhstani tenge
    "LAK" to Currency(flags = listOf()), // Lao kip
    "LBP" to Currency(flags = listOf()), // Lebanese pound
    "LKR" to Currency(flags = listOf()), // Sri Lankan rupee
    "LRD" to Currency(flags = listOf()), // Liberian dollar
    "LSL" to Currency(flags = listOf()), // Lesotho loti
    "LYD" to Currency(flags = listOf()), // Libyan dinar
    "MAD" to Currency(flags = listOf()), // Moroccan dirham
    "MDL" to Currency(flags = listOf()), // Moldovan leu
    "MGA" to Currency(flags = listOf()), // Malagasy ariary
    "MKD" to Currency(flags = listOf()), // Macedonian denar
    "MMK" to Currency(flags = listOf()), // Myanmar kyat
    "MNT" to Currency(flags = listOf()), // Mongolian tögrög
    "MOP" to Currency(flags = listOf()), // Macanese pataca
    "MRU" to Currency(flags = listOf()), // Mauritanian ouguiya
    "MUR" to Currency(flags = listOf()), // Mauritian rupee
    "MVR" to Currency(flags = listOf()), // Maldivian rufiyaa
    "MWK" to Currency(flags = listOf()), // Malawian kwacha
    "MXN" to Currency(flags = listOf()), // Mexican peso
    "MYR" to Currency(flags = listOf()), // Malaysian ringgit
    "MZN" to Currency(flags = listOf()), // Mozambican metical
    "NAD" to Currency(flags = listOf()), // Namibian dollar
    "NGN" to Currency(flags = listOf()), // Nigerian naira
    "NIO" to Currency(flags = listOf()), // Nicaraguan córdoba
    "NOK" to Currency(flags = listOf()), // Norwegian krone
    "NPR" to Currency(flags = listOf()), // Nepalese rupee
    "NZD" to Currency(flags = listOf()), // New Zealand dollar
    "OMR" to Currency(flags = listOf()), // Omani rial
    "PAB" to Currency(flags = listOf()), // Panamanian balboa
    "PEN" to Currency(flags = listOf()), // Peruvian sol
    "PGK" to Currency(flags = listOf()), // Papua New Guinean kina
    "PHP" to Currency(flags = listOf()), // Philippine peso
    "PKR" to Currency(flags = listOf()), // Pakistani rupee
    "PLN" to Currency(flags = listOf()), // Polish złoty
    "PYG" to Currency(flags = listOf()), // Paraguayan guaraní
    "QAR" to Currency(flags = listOf()), // Qatari riyal
    "RON" to Currency(flags = listOf()), // Romanian leu
    "RSD" to Currency(flags = listOf()), // Serbian dinar
    "CNY" to Currency(flags = listOf()), // Renminbi[14]
    "RUB" to Currency(flags = listOf()), // Russian ruble
    "RWF" to Currency(flags = listOf()), // Rwandan franc
    "SAR" to Currency(flags = listOf()), // Saudi riyal
    "SBD" to Currency(flags = listOf()), // Solomon Islands dollar
    "SCR" to Currency(flags = listOf()), // Seychelles rupee
    "SDG" to Currency(flags = listOf()), // Sudanese pound
    "SEK" to Currency(flags = listOf()), // Swedish krona (plural: kronor)
    "SGD" to Currency(flags = listOf()), // Singapore dollar
    "SHP" to Currency(flags = listOf()), // Saint Helena pound
    "SLL" to Currency(flags = listOf()), // Sierra Leonean leone
    "SOS" to Currency(flags = listOf()), // Somali shilling
    "SRD" to Currency(flags = listOf()), // Surinamese dollar
    "SSP" to Currency(flags = listOf()), // South Sudanese pound
    "STN" to Currency(flags = listOf()), // São Tomé and Príncipe dobra
    "SVC" to Currency(flags = listOf()), // Salvadoran colón
    "SYP" to Currency(flags = listOf()), // Syrian pound
    "SZL" to Currency(flags = listOf()), // Swazi lilangeni
    "THB" to Currency(flags = listOf()), // Thai baht
    "TJS" to Currency(flags = listOf()), // Tajikistani somoni
    "TMT" to Currency(flags = listOf()), // Turkmenistan manat
    "TND" to Currency(flags = listOf()), // Tunisian dinar
    "TOP" to Currency(flags = listOf()), // Tongan paʻanga
    "TRY" to Currency(flags = listOf()), // Turkish lira
    "TTD" to Currency(flags = listOf()), // Trinidad and Tobago dollar
    "TWD" to Currency(flags = listOf()), // New Taiwan dollar
    "TZS" to Currency(flags = listOf()), // Tanzanian shilling
    "UAH" to Currency(flags = listOf()), // Ukrainian hryvnia
    "UGX" to Currency(flags = listOf()), // Ugandan shilling
    "USD" to Currency(flags = listOf("\uD83C\uDDFA\uD83C\uDDF8")), // United States dollar
    "UYU" to Currency(flags = listOf()), // Uruguayan peso
    "UYW" to Currency(flags = listOf()), // Unidad previsional
    "UZS" to Currency(flags = listOf()), // Uzbekistan som
    "VED" to Currency(flags = listOf()), // Venezuelan bolívar digital
    "VES" to Currency(flags = listOf()), // Venezuelan bolívar soberano
    "VND" to Currency(flags = listOf()), // Vietnamese đồng
    "VUV" to Currency(flags = listOf()), // Vanuatu vatu
    "WST" to Currency(flags = listOf()), // Samoan tala
    "XAF" to Currency(flags = listOf()), // CFA franc BEAC
    "XAG" to Currency(flags = listOf()), // Silver (one troy ounce)
    "XAU" to Currency(flags = listOf()), // Gold (one troy ounce)
    "XCD" to Currency(flags = listOf()), // East Caribbean dollar
    "XOF" to Currency(flags = listOf()), // CFA franc BCEAO
    "XPD" to Currency(flags = listOf()), // Palladium (one troy ounce)
    "XPF" to Currency(flags = listOf()), // CFP franc (franc Pacifique)
    "XPT" to Currency(flags = listOf()), // Platinum (one troy ounce)
    "YER" to Currency(flags = listOf()), // Yemeni rial
    "ZAR" to Currency(flags = listOf()), // South African rand
    "ZMW" to Currency(flags = listOf()), // Zambian kwacha
)
