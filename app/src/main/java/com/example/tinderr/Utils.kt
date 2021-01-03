package com.example.tinderr

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.example.tinderr.models.CountryCode
import com.google.android.material.tabs.TabLayout

private const val TAG = "Utils"

object Utils {
    fun getCountryCodes(): ArrayList<CountryCode> {
        return arrayListOf(
            CountryCode("Israel", "+972", "IL"),
            CountryCode("Afghanistan", "+93", "AF"),
            CountryCode("Albania", "+355", "AL"),
            CountryCode("Algeria", "+213", "DZ"),
            CountryCode("AmericanSamoa", "1 684", "AS"),
            CountryCode("Andorra", "+376", "AD"),
            CountryCode("Angola", "+244", "AO"),
            CountryCode("Anguilla", "1 264", "AI"),
            CountryCode("Antigua and Barbuda", "+1268", "AG"),
            CountryCode("Argentina", "+54", "AR"),
            CountryCode("Armenia", "+374", "AM"),
            CountryCode("Aruba", "+297", "AW"),
            CountryCode("Australia", "+61", "AU"),
            CountryCode("Austria", "+43", "AT"),
            CountryCode("Azerbaijan", "+994", "AZ"),
            CountryCode("Bahamas", "1 242", "BS"),
            CountryCode("Bahrain", "+973", "BH"),
            CountryCode("Bangladesh", "+880", "BD"),
            CountryCode("Barbados", "1 246", "BB"),
            CountryCode("Belarus", "+375", "BY"),
            CountryCode("Belgium", "+32", "BE"),
            CountryCode("Belize", "+501", "BZ"),
            CountryCode("Benin", "+229", "BJ"),
            CountryCode("Bermuda", "1 441", "BM"),
            CountryCode("Bhutan", "+975", "BT"),
            CountryCode("Bosnia and Herzegovina", "+387", "BA"),
            CountryCode("Botswana", "+267", "BW"),
            CountryCode("Brazil", "+55", "BR"),
            CountryCode("Bulgaria", "+359", "BG"),
            CountryCode("Burkina Faso", "+226", "BF"),
            CountryCode("Burundi", "+257", "BI"),
            CountryCode("Cambodia", "+855", "KH"),
            CountryCode("Cameroon", "+237", "CM"),
            CountryCode("Canada", "+1", "CA"),
            CountryCode("Cape Verde", "+238", "CV"),
            CountryCode("Cayman Islands", "+ 345", "KY"),
            CountryCode("Central African Republic", "+236", "CF"),
            CountryCode("Chad", "+235", "TD"),
            CountryCode("Chile", "+56", "CL"),
            CountryCode("China", "+86", "CN"),
            CountryCode("Christmas Island", "+61", "CX"),
            CountryCode("Colombia", "+57", "CO"),
            CountryCode("Comoros", "+269", "KM"),
            CountryCode("Congo", "+242", "CG"),
            CountryCode("Cook Islands", "+682", "CK"),
            CountryCode("Costa Rica", "+506", "CR"),
            CountryCode("Croatia", "+385", "HR"),
            CountryCode("Cuba", "+53", "CU"),
            CountryCode("Cyprus", "+537", "CY"),
            CountryCode("Czech Republic", "+420", "CZ"),
            CountryCode("Denmark", "+45", "DK"),
            CountryCode("Djibouti", "+253", "DJ"),
            CountryCode("Dominica", "1 767", "DM"),
            CountryCode("Dominican Republic", "1 849", "DO"),
            CountryCode("Ecuador", "+593", "EC"),
            CountryCode("Egypt", "+20", "EG"),
            CountryCode("El Salvador", "+503", "SV"),
            CountryCode("Equatorial Guinea", "+240", "GQ"),
            CountryCode("Eritrea", "+291", "ER"),
            CountryCode("Estonia", "+372", "EE"),
            CountryCode("Ethiopia", "+251", "ET"),
            CountryCode("Faroe Islands", "+298", "FO"),
            CountryCode("Fiji", "+679", "FJ"),
            CountryCode("Finland", "+358", "FI"),
            CountryCode("France", "+33", "FR"),
            CountryCode("French Guiana", "+594", "GF"),
            CountryCode("French Polynesia", "+689", "PF"),
            CountryCode("Gabon", "+241", "GA"),
            CountryCode("Gambia", "+220", "GM"),
            CountryCode("Georgia", "+995", "GE"),
            CountryCode("Germany", "+49", "DE"),
            CountryCode("Ghana", "+233", "GH"),
            CountryCode("Gibraltar", "+350", "GI"),
            CountryCode("Greece", "+30", "GR"),
            CountryCode("Greenland", "+299", "GL"),
            CountryCode("Grenada", "1 473", "GD"),
            CountryCode("Guadeloupe", "+590", "GP"),
            CountryCode("Guam", "1 671", "GU"),
            CountryCode("Guatemala", "+502", "GT"),
            CountryCode("Guinea", "+224", "GN"),
            CountryCode("Guinea-Bissau", "+245", "GW"),
            CountryCode("Guyana", "+595", "GY"),
            CountryCode("Haiti", "+509", "HT"),
            CountryCode("Honduras", "+504", "HN"),
            CountryCode("Hungary", "+36", "HU"),
            CountryCode("Iceland", "+354", "IS"),
            CountryCode("India", "+91", "IN"),
            CountryCode("Indonesia", "+62", "ID"),
            CountryCode("Iraq", "+964", "IQ"),
            CountryCode("Ireland", "+353", "IE"),
            CountryCode("Israel", "+972", "IL"),
            CountryCode("Italy", "+39", "IT"),
            CountryCode("Jamaica", "1 876", "JM"),
            CountryCode("Japan", "+81", "JP"),
            CountryCode("Jordan", "+962", "JO"),
            CountryCode("Kazakhstan", "7 7", "KZ"),
            CountryCode("Kenya", "+254", "KE"),
            CountryCode("Kiribati", "+686", "KI"),
            CountryCode("Kuwait", "+965", "KW"),
            CountryCode("Kyrgyzstan", "+996", "KG"),
            CountryCode("Latvia", "+371", "LV"),
            CountryCode("Lebanon", "+961", "LB"),
            CountryCode("Lesotho", "+266", "LS"),
            CountryCode("Liberia", "+231", "LR"),
            CountryCode("Liechtenstein", "+423", "LI"),
            CountryCode("Lithuania", "+370", "LT"),
            CountryCode("Luxembourg", "+352", "LU"),
            CountryCode("Madagascar", "+261", "MG"),
            CountryCode("Malawi", "+265", "MW"),
            CountryCode("Malaysia", "+60", "MY"),
            CountryCode("Maldives", "+960", "MV"),
            CountryCode("Mali", "+223", "ML"),
            CountryCode("Malta", "+356", "MT"),
            CountryCode("Marshall Islands", "+692", "MH"),
            CountryCode("Martinique", "+596", "MQ"),
            CountryCode("Mauritania", "+222", "MR"),
            CountryCode("Mauritius", "+230", "MU"),
            CountryCode("Mayotte", "+262", "YT"),
            CountryCode("Mexico", "+52", "MX"),
            CountryCode("Monaco", "+377", "MC"),
            CountryCode("Mongolia", "+976", "MN"),
            CountryCode("Montenegro", "+382", "ME"),
            CountryCode("Montserrat", "+1664", "MS"),
            CountryCode("Morocco", "+212", "MA"),
            CountryCode("Myanmar", "+95", "MM"),
            CountryCode("Namibia", "+264", "NA"),
            CountryCode("Nauru", "+674", "NR"),
            CountryCode("Nepal", "+977", "NP"),
            CountryCode("Netherlands", "+31", "NL"),
            CountryCode("Netherlands Antilles", "+599", "AN"),
            CountryCode("New Caledonia", "+687", "NC"),
            CountryCode("New Zealand", "+64", "NZ"),
            CountryCode("Nicaragua", "+505", "NI"),
            CountryCode("Niger", "+227", "NE"),
            CountryCode("Nigeria", "+234", "NG"),
            CountryCode("Niue", "+683", "NU"),
            CountryCode("Norfolk Island", "+672", "NF"),
            CountryCode("Northern Mariana Islands", "1 670", "MP"),
            CountryCode("Norway", "+47", "NO"),
            CountryCode("Oman", "+968", "OM"),
            CountryCode("Pakistan", "+92", "PK"),
            CountryCode("Palau", "+680", "PW"),
            CountryCode("Panama", "+507", "PA"),
            CountryCode("Papua New Guinea", "+675", "PG"),
            CountryCode("Paraguay", "+595", "PY"),
            CountryCode("Peru", "+51", "PE"),
            CountryCode("Philippines", "+63", "PH"),
            CountryCode("Poland", "+48", "PL"),
            CountryCode("Portugal", "+351", "PT"),
            CountryCode("Puerto Rico", "1 939", "PR"),
            CountryCode("Qatar", "+974", "QA"),
            CountryCode("Romania", "+40", "RO"),
            CountryCode("Rwanda", "+250", "RW"),
            CountryCode("Samoa", "+685", "WS"),
            CountryCode("San Marino", "+378", "SM"),
            CountryCode("Saudi Arabia", "+966", "SA"),
            CountryCode("Senegal", "+221", "SN"),
            CountryCode("Serbia", "+381", "RS"),
            CountryCode("Seychelles", "+248", "SC"),
            CountryCode("Sierra Leone", "+232", "SL"),
            CountryCode("Singapore", "+65", "SG"),
            CountryCode("Slovakia", "+421", "SK"),
            CountryCode("Slovenia", "+386", "SI"),
            CountryCode("Solomon Islands", "+677", "SB"),
            CountryCode("South Africa", "+27", "ZA"),
            CountryCode("Spain", "+34", "ES"),
            CountryCode("Sri Lanka", "+94", "LK"),
            CountryCode("Sudan", "+249", "SD"),
            CountryCode("Suriname", "+597", "SR"),
            CountryCode("Swaziland", "+268", "SZ"),
            CountryCode("Sweden", "+46", "SE"),
            CountryCode("Switzerland", "+41", "CH"),
            CountryCode("Tajikistan", "+992", "TJ"),
            CountryCode("Thailand", "+66", "TH"),
            CountryCode("Togo", "+228", "TG"),
            CountryCode("Tokelau", "+690", "TK"),
            CountryCode("Tonga", "+676", "TO"),
            CountryCode("Trinidad and Tobago", "1 868", "TT"),
            CountryCode("Tunisia", "+216", "TN"),
            CountryCode("Turkey", "+90", "TR"),
            CountryCode("Turkmenistan", "+993", "TM"),
            CountryCode("Turks and Caicos Islands", "1 649", "TC"),
            CountryCode("Tuvalu", "+688", "TV"),
            CountryCode("Uganda", "+256", "UG"),
            CountryCode("Ukraine", "+380", "UA"),
            CountryCode("United Arab Emirates", "+971", "AE"),
            CountryCode("United Kingdom", "+44", "GB"),
            CountryCode("United States", "+1", "US"),
            CountryCode("Uruguay", "+598", "UY"),
            CountryCode("Uzbekistan", "+998", "UZ"),
            CountryCode("Vanuatu", "+678", "VU"),
            CountryCode("Wallis and Futuna", "+681", "WF"),
            CountryCode("Yemen", "+967", "YE"),
            CountryCode("Zambia", "+260", "ZM"),
            CountryCode("Zimbabwe", "+263", "ZW"),
            CountryCode("land Islan", "", "AX"),
            CountryCode("Antarcti", "", "AQ"),
            CountryCode("Brunei Darussalam", "+673", "BN"),
            CountryCode("Cocos (Keeling) Islands", "+61", "CC"),
            CountryCode("Cote d'Ivoire", "+225", "CI"),
            CountryCode("Falkland Islands (Malvinas)", "+500", "FK"),
            CountryCode("Guernsey", "+44", "GG"),
            CountryCode("Hong Kong", "+852", "HK"),
            CountryCode("Iran, Islamic Republic of", "+98", "IR"),
            CountryCode("Isle of Man", "+44", "IM"),
            CountryCode("Jersey", "+44", "JE"),
            CountryCode("Korea, Republic of", "+82", "KR"),
            CountryCode("Libyan Arab Jamahiriya", "+218", "LY"),
            CountryCode("Macao", "+853", "MO"),
            CountryCode("Moldova, Republic of", "+373", "MD"),
            CountryCode("Mozambique", "+258", "MZ"),
            CountryCode("Pitcairn", "+872", "PN"),
            CountryCode("Réunion", "+262", "RE"),
            CountryCode("Russia", "+7", "RU"),
            CountryCode("Saint Barthélemy", "+590", "BL"),
            CountryCode("Saint Kitts and Nevis", "1 869", "KN"),
            CountryCode("Saint Lucia", "1 758", "LC"),
            CountryCode("Saint Martin", "+590", "MF"),
            CountryCode("Saint Pierre and Miquelon", "+508", "PM"),
            CountryCode("Sao Tome and Principe", "+239", "ST"),
            CountryCode("Somalia", "+252", "SO"),
            CountryCode("Svalbard and Jan Mayen", "+47", "SJ"),
            CountryCode("Syrian Arab Republic", "+963", "SY"),
            CountryCode("Taiwan, Province of China", "+886", "TW"),
            CountryCode("Tanzania, United Republic of", "+255", "TZ"),
            CountryCode("Timor-Leste", "+670", "TL"),
            CountryCode("Viet Nam", "+84", "VN"),
            CountryCode("Virgin Islands, British", "1 284", "VG"),
            CountryCode(
                "Virgin Islands, U.S.", "1 340", "VI"
            ),
            CountryCode(
                "British Indian Ocean Territory",
                "+246",
                "IO"
            ),
            CountryCode(
                "South Georgia and the South Sandwich Islands",
                "+500",
                "GS"
            ),
            CountryCode(
                "Bolivia, Plurinational State of",
                "+591",
                "BO"
            ),
            CountryCode(
                "Congo, The Democratic Republic of the",
                "+243",
                "CD"
            ),
            CountryCode(
                "Holy See (Vatican City State)",
                "+379",
                "VA"
            ),
            CountryCode(
                "Korea, Democratic People's Republic of",
                "+850",
                "KP"
            ),
            CountryCode(
                "Lao People's Democratic Republic",
                "+856",
                "LA"
            ),
            CountryCode(
                "Macedonia, The Former Yugoslav Republic of",
                "+389",
                "MK"
            ),
            CountryCode(
                "Micronesia, Federated States of",
                "+691",
                "FM"
            ),
            CountryCode(
                "Palestinian Territory, Occupied",
                "+970",
                "PS"
            ),
            CountryCode(
                "Saint Helena, Ascension and Tristan Da Cunha",
                "+290",
                "SH"
            ),
            CountryCode(
                "Saint Vincent and the Grenadines",
                "+1 784",
                "VC"
            ),
            CountryCode(
                "Venezuela, Bolivarian Republic of",
                "+58",
                "VE"
            ),
        )
    }

    // Update button style based on user actions
    fun Button.updateButton(enabled: Boolean) {
        isEnabled = enabled
        if (enabled) {
            setTextColor(Color.WHITE)
        } else {
            setTextColor(Color.parseColor("#cccccc"))
        }
    }

    fun isEmailValid(s: String?): Boolean {
        if (!s.isNullOrBlank()) {
            return Patterns.EMAIL_ADDRESS.matcher(s).matches()
        }
        return false
    }

    @JvmStatic
    fun getResFromAttribute(activity: Activity, attr: Int, resources: Resources): Drawable? {
        if (attr == 0)
            return null
        val typedValue = TypedValue()
        activity.theme.resolveAttribute(attr, typedValue, true)
        return ResourcesCompat.getDrawable(
            resources,
            typedValue.resourceId,
            null
        )
    }
}