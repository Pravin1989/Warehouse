package com.pravin.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
//	public static void main(String[] args) {
//		Encode test = new Encode();
//		System.out.println(test.encrpt("Pravin123"));
//	}

	public String encodeLogindetails(String s) {
		StringBuilder sb = new StringBuilder();
		System.out.println(countries.length);
		for (String country : countries) {
			countriesMap.put(String.valueOf(country.charAt(0)), country);
		}
		CharSequence cs;
		String countryName = null;
		for (int i = 0; i < s.length(); i++) {
			cs = String.valueOf(s.charAt(i));
			if (StringUtils.isNumeric(cs)) {
				int counter = 0;
				Set<String> keys = countriesMap.keySet();
				for (String key : keys) {
					counter++;
					if (counter == Integer.parseInt(cs.toString())) {
						sb.append(countriesMap.get(key).length());
					}
				}
			} else {
				countryName = countriesMap.get(cs);
				StringBuilder odd = new StringBuilder();
				StringBuilder even = new StringBuilder();
				for (int index = 0; index < countryName.length(); index++) {
					if (index % 2 == 0) {
						even.append(countryName.charAt(index));
					} else {
						odd.append(countryName.charAt(index));
					}
				}
				sb.append(odd).append(even);
			}
		}
		return sb.toString();
	}

	String[] countries = { "australia", "Angola", "bahrain", "Bangladesh", "colombia", "Cambodia", "denmark",
			"Djibouti", "ElSalvador", "euador", "finland", "Fiji", "grenada", "Germany", "honduras", "Hungary", "iran",
			"Israel", "jamaica", "Japan", "kazakhstan", "Kosovo", "liberia", "Libya", "malaysia", "Madagascar", "nepal",
			"Namibia", "orisa", "Oman", "Panama", "paraguay", "Qatar", "quadrilateral", "russia", "Romania", "slovakia",
			"SanMarino", "thailand", "Tanzania", "UnitedArabEmirates", "uruguay", "vanuatu", "Vatican", "witticism",
			"Widespread", "xenophobia", "XENIATROPHOBIA", "yearning", "Yemen", "Zambia", "zimbabwe" };
	Map<String, String> countriesMap = new HashMap<>();
}
