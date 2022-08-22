package com.minimalisticapps.priceconverter.common.utils

// Data Sources:
//      - https://en.wikipedia.org/wiki/ISO_4217#Active_codes
//      - https://en.wikipedia.org/wiki/List_of_circulating_currencies
//      - https://en.wikipedia.org/wiki/Currency_symbol

data class Currency(
    val name: String,
    val territories: List<Territory>,

    // Probably useless at it is not unique
    val sign: String? = null
)

data class Territory(
    // Flag of the country/territory
    val flag: String,
    val name: String
)

fun getFlagsForCurrency(currencyCode: String): List<String> {
    var flags =
        ALLOWED_ISO_CURRENCIES[currencyCode]?.territories?.map { territory -> territory.flag }
            ?.toSet() // make unique
            ?.toList()
            ?: listOf()

    if (currencyCode in CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG) {
        flags = flags.slice(0 until 1)
    }

    return flags
}

val CURRENCIES_TO_SHOW_ONLY_FIRST_FLAG = listOf<String>() // "GBP", "EUR", "USD"

val ALLOWED_ISO_CURRENCIES = mapOf(
    "AED" to Currency(
        name = "United Arab Emirates dirham",
        sign = "د.إ",
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE6\uD83C\uDDEA",
                name = "United Arab Emirates"
            )
        ),
    ),
    "AFN" to Currency(
        name = "Afghan afghani",
        sign = "؋",
        territories = listOf(Territory(flag = "\uD83C\uDDE6\uD83C\uDDEB", name = "Afghanistan"))
    ),
    "ALL" to Currency(
        sign = "L",
        territories = listOf(Territory(flag = "\uD83C\uDDE6\uD83C\uDDF1", name = "Albania")),
        name = "Albanian lek"
    ),
    "AMD" to Currency(
        sign = "֏",
        territories = listOf(Territory(flag = "\uD83C\uDDE6\uD83C\uDDF2", name = "Armenia")),
        name = "Armenian dram"
    ),
    "ANG" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDFC", name = "Curaçao"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDFD", name = "Sint Maarten"),
        ),
        name = "Netherlands Antillean guilder"
    ),
    "AOA" to Currency(
        sign = "Kz",
        territories = listOf(Territory(flag = "\uD83C\uDDE6\uD83C\uDDF4", name = "Angola")),
        name = "Angolan kwanza"
    ),
    "ARS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE6\uD83C\uDDF7",
                name = "Argentine"
            )
        ),
        name = "Argentine peso"
    ),
    "AUD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE6\uD83C\uDDF7",
                name = "Australia"
            )
        ),
        name = "Australian dollar"
    ),
    "AWG" to Currency(
        sign = "ƒ",
        territories = listOf(Territory(flag = "\uD83C\uDDE6\uD83C\uDDFC", name = "Aruba")),
        name = "Aruban florin"
    ),
    "AZN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE6\uD83C\uDDFF",
                name = "Azerbaijan"
            )
        ),
        name = "Azerbaijani manat"
    ),
    "BAM" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDE6",
                name = "Bosnia and Herzegovina"
            )
        ),
        name = "Bosnia and Herzegovina convertible mark"
    ),
    "BBD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDE7",
                name = "Barbados"
            )
        ),
        name = "Barbados dollar"
    ),
    "BDT" to Currency(
        sign = "৳",
        territories = listOf(Territory(flag = "\uD83C\uDDE7\uD83C\uDDE9", name = "Bangladesh")),
        name = "Bangladeshi taka"
    ),
    "BGN" to Currency(
        sign = "лв.",
        territories = listOf(Territory(flag = "\uD83C\uDDE7\uD83C\uDDEC", name = "Bulgaria")),
        name = "Bulgarian lev"
    ),
    "BHD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDED",
                name = "Bahrain"
            )
        ),
        name = "Bahraini dinar"
    ),
    "BIF" to Currency(
        sign = "FBu",
        territories = listOf(Territory(flag = "\uD83C\uDDE7\uD83C\uDDEE", name = "Burundi")),
        name = "Burundian franc"
    ),
    "BMD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDF2",
                name = "Bermuda"
            )
        ),
        name = "Bermudian dollar"
    ),
    "BND" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDF3",
                name = "Brunei"
            )
        ),
        name = "Brunei dollar"
    ),
    "BOB" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDF4",
                name = "Bolivia"
            )
        ),
        name = "Boliviano"
    ),
    "BRL" to Currency(
        sign = "R\$",
        territories = listOf(Territory(flag = "\uD83C\uDDE7\uD83C\uDDF7", name = "Brazil")),
        name = "Brazilian real"
    ),
    "BSD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDF8",
                name = "Bahamas"
            )
        ),
        name = "Bahamian dollar"
    ),
    "BTN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDF9",
                name = "Bhutan"
            )
        ),
        name = "Bhutanese ngultrum"
    ),
    "BWP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDFC",
                name = "Botswana"
            )
        ),
        name = "Botswana pula"
    ),
    "BYN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDFE",
                name = "Belarus"
            )
        ),
        name = "Belarusian ruble"
    ),
    "BZD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE7\uD83C\uDDFF",
                name = "Belize"
            )
        ),
        name = "Belize dollar"
    ),
    "CAD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDE6",
                name = "Canada"
            )
        ),
        name = "Canadian dollar"
    ),
    "CDF" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDE9",
                name = "Democratic Republic of the Congo"
            )
        ),
        name = "Congolese franc"
    ),
    "CHF" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDED", name = "Switzerland"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDEE", name = "Liechtenstein"),
        ),
        name = "Swiss franc"
    ),
    "CLP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDF1",
                name = "Chile"
            )
        ),
        name = "Chilean peso"
    ),
    "COP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDF4",
                name = "Colombia"
            )
        ),
        name = "Colombian peso"
    ),
    "CRC" to Currency(
        sign = "₡",
        territories = listOf(Territory(flag = "\uD83C\uDDE8\uD83C\uDDF7", name = "Costa Rica")),
        name = "Costa Rican colon"
    ),
    "CUC" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDFA",
                name = "Cuba"
            )
        ),
        name = "Cuban convertible peso"
    ),
    "CUP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDFA",
                name = "Cuba"
            )
        ),
        name = "Cuban peso"
    ),
    "CVE" to Currency(
        sign = "Esc",
        territories = listOf(Territory(flag = "\uD83C\uDDE8\uD83C\uDDFB", name = "Cabo Verde")),
        name = "Cape Verdean escudo"
    ),
    "CZK" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE8\uD83C\uDDFF",
                name = "Czechia"
            )
        ),
        name = "Czech koruna"
    ),
    "DJF" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE9\uD83C\uDDEF",
                name = "Djibouti"
            )
        ),
        name = "Djiboutian franc"
    ),
    "DKK" to Currency(
        sign = "kr",
        territories = listOf(
            Territory(flag = "\uD83C\uDDE9\uD83C\uDDF0", name = "Denmark"),
            Territory(flag = "\uD83C\uDDEB\uD83C\uDDF4", name = "Faroe Islands"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDF1", name = "Greenland"),
        ),
        name = "Danish krone"
    ),
    "DOP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDE9\uD83C\uDDF2",
                name = "Dominican Republic"
            )
        ),
        name = "Dominican peso"
    ),
    "DZD" to Currency(
        sign = "دج",
        territories = listOf(Territory(flag = "\uD83C\uDDE9\uD83C\uDDFF", name = "Algeria")),
        name = "Algerian dinar"
    ),
    "EGP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEA\uD83C\uDDEC",
                name = "Egypt"
            )
        ),
        name = "Egyptian pound"
    ),
    "ERN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEA\uD83C\uDDF7",
                name = "Eritrea"
            )
        ),
        name = "Eritrean nakfa"
    ),
    "ETB" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEA\uD83C\uDDF9",
                name = "Ethiopia"
            )
        ),
        name = "Ethiopian birr"
    ),
    "EUR" to Currency(
        sign = "€",
        territories = listOf(
            Territory(flag = "\uD83C\uDDEA\uD83C\uDDFA", name = "EU"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDFD", name = "Åland Islands"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDE9", name = "Andorra"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDF9", name = "Austria"),
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDEA", name = "Belgium"),
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDFE", name = "Cyprus"),
            Territory(flag = "\uD83C\uDDEA\uD83C\uDDEA", name = "Estonia"),
            Territory(flag = "\uD83C\uDDEB\uD83C\uDDEE", name = "Finland"),
            Territory(flag = "\uD83C\uDDEB\uD83C\uDDF7", name = "France"),
            Territory(flag = "\uD83C\uDDE9\uD83C\uDDEA", name = "Germany"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDF7", name = "Greece"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDF5", name = "Guadeloupe"),
            Territory(flag = "\uD83C\uDDEE\uD83C\uDDEA", name = "Ireland"),
            Territory(flag = "\uD83C\uDDEE\uD83C\uDDF9", name = "Italy"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDFB", name = "Latvia"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDF9", name = "Lithuania"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDFA", name = "Luxembourg"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDF9", name = "Malta"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDEB", name = "French Guiana"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDF6", name = "Martinique"),
            Territory(flag = "\uD83C\uDDFE\uD83C\uDDF9", name = "Mayotte"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDE8", name = "Monaco"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDEA", name = "Montenegro"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDF1", name = "Netherlands"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDF9", name = "Portugal"),
            Territory(flag = "\uD83C\uDDF7\uD83C\uDDEA", name = "Réunion"),
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDF1", name = "Saint Barthélemy"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDEB", name = "Saint Martin"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDF2", name = "Saint Pierre and Miquelon"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDF2", name = "San Marino"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDF0", name = "Slovakia"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDEE", name = "Slovenia"),
            Territory(flag = "\uD83C\uDDEA\uD83C\uDDF8", name = "Spain"),
            Territory(flag = "\uD83C\uDDFB\uD83C\uDDE6", name = "Vatican City"),
        ),
        name = "Euro"
    ),
    "FJD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEB\uD83C\uDDEF",
                name = "Fiji"
            )
        ),
        name = "Fiji dollar"
    ),
    "FKP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEB\uD83C\uDDF0",
                name = "Falkland Islands"
            )
        ),
        name = "Falkland Islands pound"
    ),
    "GBP" to Currency(
        sign = "₤",
        territories = listOf(
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDE7", name = "United Kingdom,"),
            Territory(flag = "\uD83C\uDDEE\uD83C\uDDF2", name = "Isle of Man"),
            Territory(flag = "\uD83C\uDDEF\uD83C\uDDEA", name = "Jersey"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDEC", name = "Guernsey"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDE6", name = "Tristan da Cunha"),
        ),
        name = "Pound sterling"
    ),
    "GEL" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEC\uD83C\uDDEA",
                name = "Georgia"
            )
        ),
        name = "Georgian lari"
    ),
    "GHS" to Currency(
        sign = "₵",
        territories = listOf(Territory(flag = "\uD83C\uDDEC\uD83C\uDDED", name = "Ghana")),
        name = "Ghanaian cedi"
    ),
    "GIP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEC\uD83C\uDDEE",
                name = "Gibraltar"
            )
        ),
        name = "Gibraltar pound"
    ),
    "GMD" to Currency(
        sign = "D",
        territories = listOf(Territory(flag = "\uD83C\uDDEC\uD83C\uDDF2", name = "Gambia")),
        name = "Gambian dalasi"
    ),
    "GNF" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEC\uD83C\uDDF3",
                name = "Guinea"
            )
        ),
        name = "Guinean franc"
    ),
    "GTQ" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEC\uD83C\uDDF9",
                name = "Guatemala"
            )
        ),
        name = "Guatemalan quetzal"
    ),
    "GYD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEC\uD83C\uDDFE",
                name = "Guyana"
            )
        ),
        name = "Guyanese dollar"
    ),
    "HKD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDED\uD83C\uDDF0",
                name = "Hong Kong"
            )
        ),
        name = "Hong Kong dollar"
    ),
    "HNL" to Currency(
        sign = "L",
        territories = listOf(Territory(flag = "\uD83C\uDDED\uD83C\uDDF3", name = "Honduras")),
        name = "Honduran lempira"
    ),
    "HRK" to Currency(
        sign = "kn",
        territories = listOf(Territory(flag = "\uD83C\uDDED\uD83C\uDDF7", name = "Croatia")),
        name = "Croatian kuna"
    ),
    "HTG" to Currency(
        sign = "G",
        territories = listOf(Territory(flag = "\uD83C\uDDED\uD83C\uDDF9", name = "Haiti")),
        name = "Haitian gourde"
    ),
    "HUF" to Currency(
        sign = "Ft",
        territories = listOf(Territory(flag = "\uD83C\uDDED\uD83C\uDDFA", name = "Hungary")),
        name = "Hungarian forint"
    ),
    "IDR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEE\uD83C\uDDE9",
                name = "Indonesia"
            )
        ),
        name = "Indonesian rupiah"
    ),
    "ILS" to Currency(
        sign = "₪",
        territories = listOf(Territory(flag = "\uD83C\uDDEE\uD83C\uDDF1", name = "Israel")),
        name = "Israeli new shekel"
    ),
    "INR" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDEE\uD83C\uDDF3", name = "India"),
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDF9", name = "Bhutan"),
        ),
        name = "Indian rupee"
    ),
    "IQD" to Currency(
        sign = "د.ع",
        territories = listOf(Territory(flag = "\uD83C\uDDEE\uD83C\uDDF6", name = "Iraq")),
        name = "Iraqi dinar"
    ),
    "IRR" to Currency(
        sign = "﷼",
        territories = listOf(Territory(flag = "\uD83C\uDDEE\uD83C\uDDF7", name = "Iran")),
        name = "Iranian rial"
    ),
    "ISK" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEE\uD83C\uDDF8",
                name = "Iceland"
            )
        ),
        name = "Icelandic króna (plural: krónur)"
    ),
    "JMD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDEF\uD83C\uDDF2",
                name = "Jamaica"
            )
        ),
        name = "Jamaican dollar"
    ),
    "JOD" to Currency(
        sign = "د.أ",
        territories = listOf(Territory(flag = "\uD83C\uDDEF\uD83C\uDDF4", name = "Jordan")),
        name = "Jordanian dinar"
    ),
    "JPY" to Currency(
        sign = "¥",
        territories = listOf(Territory(flag = "\uD83C\uDDEF\uD83C\uDDF5", name = "Japan")),
        name = "Japanese yen"
    ),
    "KES" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF0\uD83C\uDDEA",
                name = "Kenya"
            )
        ),
        name = "Kenyan shilling"
    ),
    "KGS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF0\uD83C\uDDEC",
                name = "Kyrgyzstan"
            )
        ),
        name = "Kyrgyzstani som"
    ),
    "KHR" to Currency(
        sign = "៛",
        territories = listOf(Territory(flag = "\uD83C\uDDF0\uD83C\uDDED", name = "Cambodia")),
        name = "Cambodian riel",
    ),
    "KMF" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF0\uD83C\uDDF2",
                name = "Comoros"
            )
        ),
        name = "Comoro franc",
    ),
    "KPW" to Currency(
        sign = "₩",
        territories = listOf(Territory(flag = "\uD83C\uDDF0\uD83C\uDDF5", name = "North Korea")),
        name = "North Korean won",
    ),
    "KRW" to Currency(
        sign = "₩",
        territories = listOf(Territory(flag = "\uD83C\uDDF0\uD83C\uDDF7", name = "South Korea")),
        name = "South Korean won",
    ),
    "KWD" to Currency(
        sign = "د.ك",
        territories = listOf(Territory(flag = "\uD83C\uDDF0\uD83C\uDDFC", name = "Kuwait")),
        name = "Kuwaiti dinar",
    ),
    "KYD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF0\uD83C\uDDFE",
                name = "Cayman Islands"
            )
        ),
        name = "Cayman Islands dollar",
    ),
    "KZT" to Currency(
        sign = "₸",
        territories = listOf(Territory(flag = "\uD83C\uDDF0\uD83C\uDDFF", name = "Kazakhstan")),
        name = "Kazakhstani tenge",
    ),
    "LAK" to Currency(
        sign = "₭",
        territories = listOf(Territory(flag = "\uD83C\uDDF1\uD83C\uDDE6", name = "Laos")),
        name = "Lao kip",
    ),
    "LBP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF1\uD83C\uDDE7",
                name = "Lebanon"
            )
        ),
        name = "Lebanese pound",
    ),
    "LKR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF1\uD83C\uDDF0",
                name = "Sri Lanka"
            )
        ),
        name = "Sri Lankan rupee",
    ),
    "LRD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF1\uD83C\uDDF7",
                name = "Liberia"
            )
        ),
        name = "Liberian dollar",
    ),
    "LSL" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF1\uD83C\uDDF8",
                name = "Lesotho"
            )
        ),
        name = "Lesotho loti"
    ),
    "LYD" to Currency(
        sign = ".د",
        territories = listOf(Territory(flag = "\uD83C\uDDF1\uD83C\uDDFE", name = "Libya")),
        name = "Libyan dinar"
    ),
    "MAD" to Currency(
        sign = "د.م.",
        territories = listOf(
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDE6", name = "Marocco"),
            Territory(flag = "\uD83C\uDDEA\uD83C\uDDED", name = "Western Sahara"),
        ),
        name = "Moroccan dirham"
    ),
    "MDL" to Currency(
        sign = "L",
        territories = listOf(Territory(flag = "\uD83C\uDDF2\uD83C\uDDE9", name = "Moldova")),
        name = "Moldovan leu"
    ),
    "MGA" to Currency(
        sign = "Ar",
        territories = listOf(Territory(flag = "\uD83C\uDDF2\uD83C\uDDEC", name = "Madagascar")),
        name = "Malagasy ariary"
    ),
    "MKD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDF0",
                name = "North Macedonia"
            )
        ),
        name = "Macedonian denar"
    ),
    "MMK" to Currency(
        sign = "K",
        territories = listOf(Territory(flag = "\uD83C\uDDF2\uD83C\uDDF2", name = "Myanmar")),
        name = "Myanmar kyat"
    ),
    "MNT" to Currency(
        sign = "₮",
        territories = listOf(Territory(flag = "\uD83C\uDDF2\uD83C\uDDF3", name = "Mongolia")),
        name = "Mongolian tögrög"
    ),
    "MOP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDF4",
                name = "Macau"
            )
        ),
        name = "Macanese pataca"
    ),
    "MRU" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDF7",
                name = "Mauritania"
            )
        ),
        name = "Mauritanian ouguiya"
    ),
    "MUR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDFA",
                name = "Mauritius"
            )
        ),
        name = "Mauritian rupee"
    ),
    "MVR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDFB",
                name = "Maldives"
            )
        ),
        name = "Maldivian rufiyaa"
    ),
    "MWK" to Currency(
        sign = "K",
        territories = listOf(Territory(flag = "\uD83C\uDDF2\uD83C\uDDFC", name = "Malawi")),
        name = "Malawian kwacha"
    ),
    "MXN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDFD",
                name = "Mexico"
            )
        ),
        name = "Mexican peso"
    ),
    "MYR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDFE",
                name = "Malaysia"
            )
        ),
        name = "Malaysian ringgit"
    ),
    "MZN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF2\uD83C\uDDFF",
                name = "Mozambique"
            )
        ),
        name = "Mozambican metical"
    ),
    "NAD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF3\uD83C\uDDE6",
                name = "Namibia"
            )
        ),
        name = "Namibian dollar"
    ),
    "NGN" to Currency(
        sign = "₦",
        territories = listOf(Territory(flag = "\uD83C\uDDF3\uD83C\uDDEC", name = "Nigeria")),
        name = "Nigerian naira"
    ),
    "NIO" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF3\uD83C\uDDEE",
                name = "Nicaragua"
            )
        ),
        name = "Nicaraguan córdoba"
    ),
    "NOK" to Currency(
        sign = "kr",
        territories = listOf(
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDF4", name = "Norway"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDF4", name = "Svalbard"),
//            Territory(flag = "\uD83C\uDDF3\uD83C\uDDF4", name = "Jan Mayen"), // uninhabited
//            Territory(flag = "\uD83C\uDDF3\uD83C\uDDF4", name = "Bouvet Island"), // uninhabited
        ),
        name = "Norwegian krone"
    ),
    "NPR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF3\uD83C\uDDF5",
                name = "Nepal"
            )
        ),
        name = "Nepalese rupee"
    ),
    "NZD" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDFF", name = "New Zealand"),
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDF0", name = "Cook Islands"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDFA", name = "Niue"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDF3", name = "Pitcairn Islands"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDF0", name = "Tokelau"),
        ),
        name = "New Zealand dollar"
    ),
    "OMR" to Currency(
        sign = "ر.ع. ",
        territories = listOf(Territory(flag = "\uD83C\uDDF4\uD83C\uDDF2", name = "Oman")),
        name = "Omani rial"
    ),
    "PAB" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF5\uD83C\uDDE6",
                name = "Panama"
            )
        ),
        name = "Panamanian balboa"
    ),
    "PEN" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF5\uD83C\uDDEA",
                name = "Peru"
            )
        ),
        name = "Peruvian sol"
    ),
    "PGK" to Currency(
        sign = "K",
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF5\uD83C\uDDEC",
                name = "Papua New Guinean"
            )
        ),
        name = "Papua New Guinean kina"
    ),
    "PHP" to Currency(
        sign = "₱",
        territories = listOf(Territory(flag = "\uD83C\uDDF5\uD83C\uDDED", name = "Philippines")),
        name = "Philippine peso"
    ),
    "PKR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF5\uD83C\uDDF0",
                name = "Pakistan"
            )
        ),
        name = "Pakistani rupee"
    ),
    "PLN" to Currency(
        sign = "zł",
        territories = listOf(Territory(flag = "\uD83C\uDDF5\uD83C\uDDF1", name = "Poland")),
        name = "Polish złoty"
    ),
    "PYG" to Currency(
        sign = "₲",
        territories = listOf(Territory(flag = "\uD83C\uDDF5\uD83C\uDDFE", name = "Paraguay")),
        name = "Paraguayan guaraní"
    ),
    "QAR" to Currency(
        sign = "ر.ق",
        territories = listOf(Territory(flag = "\uD83C\uDDF6\uD83C\uDDE6", name = "Qatar")),
        name = "Qatari riyal"
    ),
    "RON" to Currency(
        sign = "L",
        territories = listOf(Territory(flag = "\uD83C\uDDF7\uD83C\uDDF4", name = "Romania")),
        name = "Romanian leu"
    ),
    "RSD" to Currency(
        sign = "дин",
        territories = listOf(Territory(flag = "\uD83C\uDDF7\uD83C\uDDF8", name = "Serbia")),
        name = "Serbian dinar"
    ),
    "CNY" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDF3", name = "China"),
        ),
        name = "Renminbi"
    ),
    "RUB" to Currency(
        sign = "₽",
        territories = listOf(Territory(flag = "\uD83C\uDDF7\uD83C\uDDFA", name = "Russia")),
        name = "Russian ruble"
    ),
    "RWF" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF7\uD83C\uDDFC",
                name = "Rwanda"
            )
        ),
        name = "Rwandan franc"
    ),
    "SAR" to Currency(
        sign = "ر.س",
        territories = listOf(Territory(flag = "\uD83C\uDDF8\uD83C\uDDE6", name = "Saudi Arabia")),
        name = "Saudi riyal"
    ),
    "SBD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDE7",
                name = "Solomon Islands"
            )
        ),
        name = "Solomon Islands dollar"
    ),
    "SCR" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDE8",
                name = "Seychelles"
            )
        ),
        name = "Seychelles rupee"
    ),
    "SDG" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDE9",
                name = ""
            )
        ),
        name = "Sudanese pound"
    ),
    "SEK" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDEA",
                name = "Sweden"
            )
        ),
        name = "Swedish krona (plural: kronor)"
    ),
    "SGD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDEC",
                name = "Singapore"
            )
        ),
        name = "Singapore dollar"
    ),
    "SHP" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDED", name = "Saint Helena"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDE8", name = "Ascension Island"),
        ),
        name = "Saint Helena pound"
    ),
    "SLL" to Currency(
        sign = "Le",
        territories = listOf(Territory(flag = "\uD83C\uDDF8\uD83C\uDDF1", name = "Sierra Leone")),
        name = "Sierra Leonean leone"
    ),
    "SOS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDF4",
                name = "Somalia"
            )
        ),
        name = "Somali shilling"
    ),
    "SRD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDF7",
                name = "Suriname"
            )
        ),
        name = "Surinamese dollar"
    ),
    "SSP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDF8",
                name = "South Sudan"
            )
        ),
        name = "South Sudanese pound"
    ),
    "STN" to Currency(
        sign = "Db",
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDF9",
                name = "São Tomé and Príncipe"
            )
        ),
        name = "São Tomé and Príncipe dobra"
    ),
    "SVC" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDFB",
                name = "El Salvador"
            )
        ),
        name = "Salvadoran colón"
    ),
    "SYP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDFE",
                name = "Syria"
            )
        ),
        name = "Syrian pound"
    ),
    "SZL" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF8\uD83C\uDDFF",
                name = "Eswatini"
            )
        ),
        name = "Swazi lilangeni"
    ),
    "THB" to Currency(
        sign = "฿",
        territories = listOf(Territory(flag = "\uD83C\uDDF9\uD83C\uDDED", name = "Thailand")),
        name = "Thai baht"
    ),
    "TJS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDEF",
                name = "Tajikistan"
            )
        ),
        name = "Tajikistani somoni"
    ),
    "TMT" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDF2",
                name = "Turkmenistan"
            )
        ),
        name = "Turkmenistan manat"
    ),
    "TND" to Currency(
        sign = "د.ت",
        territories = listOf(Territory(flag = "\uD83C\uDDF9\uD83C\uDDF3", name = "Tunisia")),
        name = "Tunisian dinar"
    ),
    "TOP" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDF4",
                name = "Tonga"
            )
        ),
        name = "Tongan paʻanga"
    ),
    "TRY" to Currency(
        sign = "₺",
        territories = listOf(Territory(flag = "\uD83C\uDDF9\uD83C\uDDF7", name = "Turkey")),
        name = "Turkish lira"
    ),
    "TTD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDF9",
                name = "Trinidad and Tobago"
            )
        ),
        name = "Trinidad and Tobago dollar"
    ),
    "TWD" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDF9",
                name = "Taiwan"
            )
        ),
        name = "New Taiwan dollar"
    ),
    "TZS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDF9\uD83C\uDDFF",
                name = "Tanzania"
            )
        ),
        name = "Tanzanian shilling"
    ),
    "UAH" to Currency(
        sign = "₴",
        territories = listOf(Territory(flag = "\uD83C\uDDFA\uD83C\uDDE6", name = "Ukraine")),
        name = "Ukrainian hryvnia"
    ),
    "UGX" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFA\uD83C\uDDEC",
                name = "Uganda"
            )
        ),
        name = "Ugandan shilling"
    ),
    "USD" to Currency(
        sign = "$",
        territories = listOf(
            Territory(flag = "\uD83C\uDDFA\uD83C\uDDF8", name = "United States"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDF8", name = "American Samoa"),
            Territory(flag = "\uD83C\uDDEE\uD83C\uDDF4", name = "British Indian Ocean Territory"),
            Territory(flag = "\uD83C\uDDFB\uD83C\uDDEC", name = "British Virgin Islands"),
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDF6", name = "Caribbean Netherlands"),
            Territory(flag = "\uD83C\uDDEA\uD83C\uDDE8", name = "Ecuador"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDFB", name = "El Salvador"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDFA", name = "Guam"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDED", name = "Marshall Islands"),
            Territory(flag = "\uD83C\uDDEB\uD83C\uDDF2", name = "Federated States of Micronesia"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDF5", name = "Northern Mariana Islands"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDFC", name = "Palau"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDE6", name = "Panama"),
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDF7", name = "Puerto Rico"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDF1", name = "Timor-Leste"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDE8", name = "Turks and Caicos Islands"),
            Territory(flag = "\uD83C\uDDFB\uD83C\uDDEE", name = "U.S. Virgin Islands"),
        ),
        name = "United States dollar"
    ),
    "UYU" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFA\uD83C\uDDFE",
                name = "Uruguay"
            )
        ),
        name = "Uruguayan peso"
    ),
    "UYW" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDFA\uD83C\uDDFE", name = "Uruguay"),
        ),
        name = "Unidad previsional"
    ),
    "UZS" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFA\uD83C\uDDFF",
                name = "Uzbekistan"
            )
        ),
        name = "Uzbekistan som"
    ),
    "VED" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFB\uD83C\uDDEA",
                name = "Venezuela"
            )
        ),
        name = "Venezuelan bolívar digital"
    ),
    "VES" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFB\uD83C\uDDEA",
                name = "Venezuela"
            )
        ),
        name = "Venezuelan bolívar soberano"
    ),
    "VND" to Currency(
        sign = "₫",
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFB\uD83C\uDDF3",
                name = "Vietnam"
            )
        ),
        name = "Vietnamese đồng"
    ),
    "VUV" to Currency(
        sign = "Vt",
        territories = listOf(Territory(flag = "\uD83C\uDDFB\uD83C\uDDFA", name = "Vanuatu")),
        name = "Vanuatu vatu"
    ),
    "WST" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFC\uD83C\uDDF8",
                name = "Samoa"
            )
        ),
        name = "Samoan tala"
    ),
    "XAF" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDF2", name = "Cameroon"),
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDEB", name = "Central African Republic"),
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDEC", name = "Republic of the Congo"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDE9", name = "Chad"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDF6", name = "Equatorial Guinea"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDE6", name = "Gabon"),
        ),
        name = "CFA franc BEAC"
    ),
    "XCD" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDEE", name = "Anguilla"),
            Territory(flag = "\uD83C\uDDE6\uD83C\uDDEC", name = "Antigua and Barbuda"),
            Territory(flag = "\uD83C\uDDE9\uD83C\uDDF2", name = "Dominica"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDE9", name = "Grenada"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDF8", name = "Montserrat"),
            Territory(flag = "\uD83C\uDDF0\uD83C\uDDF3", name = "Saint Kitts and Nevis"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDE8", name = "Saint Lucia"),
            Territory(flag = "\uD83C\uDDFB\uD83C\uDDE8", name = "Saint Vincent and the Grenadines"),
        ),
        name = "East Caribbean dollar"
    ),
    "XOF" to Currency(
        name = "CFA franc BCEAO (West African CFA franc)",
        territories = listOf(
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDEF", name = "Benin"),
            Territory(flag = "\uD83C\uDDE7\uD83C\uDDEB", name = "Burkina Faso"),
            Territory(flag = "\uD83C\uDDE8\uD83C\uDDEE", name = "Côte d'Ivoire"),
            Territory(flag = "\uD83C\uDDEC\uD83C\uDDFC", name = "Guinea-Bissau"),
            Territory(flag = "\uD83C\uDDF2\uD83C\uDDF1", name = "Mali"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDEA", name = "Niger"),
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDF3", name = "Senegal"),
            Territory(flag = "\uD83C\uDDF9\uD83C\uDDEC", name = "Togo"),
        )
    ),
    "XPF" to Currency(
        territories = listOf(
            Territory(flag = "\uD83C\uDDF5\uD83C\uDDEB", name = "French Polynesia"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDE8", name = "New Caledonia"),
            Territory(flag = "\uD83C\uDDFC\uD83C\uDDEB", name = "Wallis and Futuna"),
        ),
        name = "CFP franc (franc Pacifique)"
    ),
    "YER" to Currency(
        sign = "ر.ي",
        territories = listOf(Territory(flag = "\uD83C\uDDFE\uD83C\uDDEA", name = "Yemen")),
        name = "Yemeni rial"
    ),
    "ZAR" to Currency(
        sign = "R",
        territories = listOf(
            Territory(flag = "\uD83C\uDDF8\uD83C\uDDFF", name = "Eswatini"),
            Territory(flag = "\uD83C\uDDF1\uD83C\uDDF8", name = "Lesotho"),
            Territory(flag = "\uD83C\uDDF3\uD83C\uDDE6", name = "Namibia"),
            Territory(flag = "\uD83C\uDDFF\uD83C\uDDE6", name = "South Africa"),
        ),
        name = "South African rand"
    ),
    "ZMW" to Currency(
        sign = "K",
        territories = listOf(Territory(flag = "\uD83C\uDDFF\uD83C\uDDF2", name = "Zambia")),
        name = "Zambian kwacha"
    ),
    "ZWL" to Currency(
        territories = listOf(
            Territory(
                flag = "\uD83C\uDDFF\uD83C\uDDFC",
                name = "Zimbabwe"
            )
        ),
        name = "Zimbabwean dollar"
    ),
)
