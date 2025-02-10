 package com.chat.utils;


 import java.util.HashSet;
 import java.util.Set;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;

 public class UserValidation<T> {

     private static String regex;

     private static Pattern pattern;

     private static Matcher match;

     public static <E> boolean isNull(E value) {

         return (value == null);
     }

     public static boolean isValidPhoneNumber(String phoneNumber) {
         if (isNull(phoneNumber))
             return false;

         regex = "^[0][1][1205][0-9]{8}$";

         pattern = Pattern.compile(regex);

         match = pattern.matcher(phoneNumber);

         return match.find();

     }

     public static boolean isVaildName(String userName) {
         if (isNull(userName))
             return false;

         regex = "^[A-Za-z]{3,12}$";

         pattern = Pattern.compile(regex);

         match = pattern.matcher(userName);

         return match.find();

     }

     public static boolean isVaildEmail(String userEmail) {

         regex = "^[A-Za-z]\\w+@gmail.com$";

         pattern = Pattern.compile(regex);

         match = pattern.matcher(userEmail);

         return match.find();

     }

     public static boolean isValidPassword(String password) {

         regex = "^[A-Za-z]{4}\\w{4,8}$";

         pattern = Pattern.compile(regex);

         match = pattern.matcher(password);

         return match.find();

     }

     public static boolean isValidGender(String gender)

     {
         return gender.equals("male") || gender.equals("female");
     }

     public static boolean isValidCountry(String country) {
         return COUNTRIES.contains(country);
     }


     private static final Set<String> COUNTRIES = new HashSet<>();

     static {
         COUNTRIES.add("Afghanistan");
         COUNTRIES.add("Albania");
         COUNTRIES.add("Algeria");
         COUNTRIES.add("Andorra");
         COUNTRIES.add("Angola");
         COUNTRIES.add("Antigua and Barbuda");
         COUNTRIES.add("Argentina");
         COUNTRIES.add("Armenia");
         COUNTRIES.add("Australia");
         COUNTRIES.add("Austria");
         COUNTRIES.add("Azerbaijan");
         COUNTRIES.add("Bahamas");
         COUNTRIES.add("Bahrain");
         COUNTRIES.add("Bangladesh");
         COUNTRIES.add("Barbados");
         COUNTRIES.add("Belarus");
         COUNTRIES.add("Belgium");
         COUNTRIES.add("Belize");
         COUNTRIES.add("Benin");
         COUNTRIES.add("Bhutan");
         COUNTRIES.add("Bolivia");
         COUNTRIES.add("Bosnia and Herzegovina");
         COUNTRIES.add("Botswana");
         COUNTRIES.add("Brazil");
         COUNTRIES.add("Brunei");
         COUNTRIES.add("Bulgaria");
         COUNTRIES.add("Burkina Faso");
         COUNTRIES.add("Burundi");
         COUNTRIES.add("Cabo Verde");
         COUNTRIES.add("Cambodia");
         COUNTRIES.add("Cameroon");
         COUNTRIES.add("Canada");
         COUNTRIES.add("Central African Republic");
         COUNTRIES.add("Chad");
         COUNTRIES.add("Chile");
         COUNTRIES.add("China");
         COUNTRIES.add("Colombia");
         COUNTRIES.add("Comoros");
         COUNTRIES.add("Congo (Congo-Brazzaville)");
         COUNTRIES.add("Costa Rica");
         COUNTRIES.add("Croatia");
         COUNTRIES.add("Cuba");
         COUNTRIES.add("Cyprus");
         COUNTRIES.add("Czechia (Czech Republic)");
         COUNTRIES.add("Denmark");
         COUNTRIES.add("Djibouti");
         COUNTRIES.add("Dominica");
         COUNTRIES.add("Dominican Republic");
         COUNTRIES.add("Ecuador");
         COUNTRIES.add("Egypt");
         COUNTRIES.add("El Salvador");
         COUNTRIES.add("Equatorial Guinea");
         COUNTRIES.add("Eritrea");
         COUNTRIES.add("Estonia");
         COUNTRIES.add("Eswatini");
         COUNTRIES.add("Ethiopia");
         COUNTRIES.add("Fiji");
         COUNTRIES.add("Finland");
         COUNTRIES.add("France");
         COUNTRIES.add("Gabon");
         COUNTRIES.add("Gambia");
         COUNTRIES.add("Georgia");
         COUNTRIES.add("Germany");
         COUNTRIES.add("Ghana");
         COUNTRIES.add("Greece");
         COUNTRIES.add("Grenada");
         COUNTRIES.add("Guatemala");
         COUNTRIES.add("Guinea");
         COUNTRIES.add("Guinea-Bissau");
         COUNTRIES.add("Guyana");
         COUNTRIES.add("Haiti");
         COUNTRIES.add("Honduras");
         COUNTRIES.add("Hungary");
         COUNTRIES.add("Iceland");
         COUNTRIES.add("India");
         COUNTRIES.add("Indonesia");
         COUNTRIES.add("Iran");
         COUNTRIES.add("Iraq");
         COUNTRIES.add("Ireland");
         COUNTRIES.add("Israel");
         COUNTRIES.add("Italy");
         COUNTRIES.add("Jamaica");
         COUNTRIES.add("Japan");
         COUNTRIES.add("Jordan");
         COUNTRIES.add("Kazakhstan");
         COUNTRIES.add("Kenya");
         COUNTRIES.add("Kiribati");
         COUNTRIES.add("Kuwait");
         COUNTRIES.add("Kyrgyzstan");
         COUNTRIES.add("Laos");
         COUNTRIES.add("Latvia");
         COUNTRIES.add("Lebanon");
         COUNTRIES.add("Lesotho");
         COUNTRIES.add("Liberia");
         COUNTRIES.add("Libya");
         COUNTRIES.add("Liechtenstein");
         COUNTRIES.add("Lithuania");
         COUNTRIES.add("Luxembourg");
         COUNTRIES.add("Madagascar");
         COUNTRIES.add("Malawi");
         COUNTRIES.add("Malaysia");
         COUNTRIES.add("Maldives");
         COUNTRIES.add("Mali");
         COUNTRIES.add("Malta");
         COUNTRIES.add("Marshall Islands");
         COUNTRIES.add("Mauritania");
         COUNTRIES.add("Mauritius");
         COUNTRIES.add("Mexico");
         COUNTRIES.add("Micronesia");
         COUNTRIES.add("Moldova");
         COUNTRIES.add("Monaco");
         COUNTRIES.add("Mongolia");
         COUNTRIES.add("Montenegro");
         COUNTRIES.add("Morocco");
         COUNTRIES.add("Mozambique");
         COUNTRIES.add("Myanmar (Burma)");
         COUNTRIES.add("Namibia");
         COUNTRIES.add("Nauru");
         COUNTRIES.add("Nepal");
         COUNTRIES.add("Netherlands");
         COUNTRIES.add("New Zealand");
         COUNTRIES.add("Nicaragua");
         COUNTRIES.add("Niger");
         COUNTRIES.add("Nigeria");
         COUNTRIES.add("North Korea");
         COUNTRIES.add("North Macedonia");
         COUNTRIES.add("Norway");
         COUNTRIES.add("Oman");
         COUNTRIES.add("Pakistan");
         COUNTRIES.add("Palau");
         COUNTRIES.add("Panama");
         COUNTRIES.add("Papua New Guinea");
         COUNTRIES.add("Paraguay");
         COUNTRIES.add("Peru");
         COUNTRIES.add("Philippines");
         COUNTRIES.add("Poland");
         COUNTRIES.add("Portugal");
         COUNTRIES.add("Qatar");
         COUNTRIES.add("Romania");
         COUNTRIES.add("Russia");
         COUNTRIES.add("Rwanda");
         COUNTRIES.add("Saint Kitts and Nevis");
         COUNTRIES.add("Saint Lucia");
         COUNTRIES.add("Saint Vincent and the Grenadines");
         COUNTRIES.add("Samoa");
         COUNTRIES.add("San Marino");
         COUNTRIES.add("Sao Tome and Principe");
         COUNTRIES.add("Saudi Arabia");
         COUNTRIES.add("Senegal");
         COUNTRIES.add("Serbia");
         COUNTRIES.add("Seychelles");
         COUNTRIES.add("Sierra Leone");
         COUNTRIES.add("Singapore");
         COUNTRIES.add("Slovakia");
         COUNTRIES.add("Slovenia");
         COUNTRIES.add("Solomon Islands");
         COUNTRIES.add("Somalia");
         COUNTRIES.add("South Africa");
         COUNTRIES.add("South Korea");
         COUNTRIES.add("South Sudan");
         COUNTRIES.add("Spain");
         COUNTRIES.add("Sri Lanka");
         COUNTRIES.add("Sudan");
         COUNTRIES.add("Suriname");
         COUNTRIES.add("Sweden");
         COUNTRIES.add("Switzerland");
         COUNTRIES.add("Syria");
         COUNTRIES.add("Taiwan");
         COUNTRIES.add("Tajikistan");
         COUNTRIES.add("Tanzania");
         COUNTRIES.add("Thailand");
         COUNTRIES.add("Togo");
         COUNTRIES.add("Tonga");
         COUNTRIES.add("Trinidad and Tobago");
         COUNTRIES.add("Tunisia");
         COUNTRIES.add("Turkey");
         COUNTRIES.add("Turkmenistan");
         COUNTRIES.add("Tuvalu");
         COUNTRIES.add("Uganda");
         COUNTRIES.add("Ukraine");
         COUNTRIES.add("United Arab Emirates");
         COUNTRIES.add("United Kingdom");
         COUNTRIES.add("United States");
         COUNTRIES.add("Uruguay");
         COUNTRIES.add("Uzbekistan");
         COUNTRIES.add("Vanuatu");
         COUNTRIES.add("Vatican City");
         COUNTRIES.add("Venezuela");
         COUNTRIES.add("Vietnam");
         COUNTRIES.add("Yemen");
         COUNTRIES.add("Zambia");
         COUNTRIES.add("Zimbabwe");
     }




 }
