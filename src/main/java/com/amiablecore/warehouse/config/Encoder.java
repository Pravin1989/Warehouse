package com.amiablecore.warehouse.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
	Random rand = new Random();

	public static void main(String[] args) {
		Encoder encoder = new Encoder();
		System.out.println(encoder.encodeLogindetails("pravin123"));
		// String name = "Pravin123";
		// char charAt = name.charAt(0);
		// int asc = (int) charAt;
		// Random rand = new Random();
		// int i = asc - rand.nextInt(10) + rand.nextInt(15);
		// System.out.println((char) i);
	}

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
						sb.append(countriesMap.get(key).length() + rand.nextInt(1000));
					}
				}
			} else {
				countryName = countriesMap.get(cs);
				StringBuilder odd = new StringBuilder();
				StringBuilder even = new StringBuilder();
				for (int index = 0; index < countryName.length(); index++) {
					char character = countryName.charAt(0);
					int asciCode = (int) character;
					int value = (int) asciCode - rand.nextInt(4) + rand.nextInt(7);
					if (index % 2 == 0) {
						even.append((char) value);
					} else {
						odd.append((char) value);
					}
				}
				odd.append(symbols[rand.nextInt(26)]);
				odd.append(rand.nextInt(100));
				even.append(symbols[rand.nextInt(26)]);
				even.append(rand.nextInt(50));
				sb.append(odd).append(even);
			}
		}
		return sb.toString().trim();
	}

	String[] countries = { "australia", "Angola", "bahrain", "Bangladesh", "colombia", "Cambodia", "denmark",
			"Djibouti", "ElSalvador", "euador", "finland", "Fiji", "grenada", "Germany", "honduras", "Hungary", "iran",
			"Israel", "jamaica", "Japan", "kazakhstan", "Kosovo", "liberia", "Libya", "malaysia", "Madagascar", "nepal",
			"Namibia", "orisa", "Oman", "Panama", "paraguay", "Qatar", "quadrilateral", "russia", "Romania", "slovakia",
			"SanMarino", "thailand", "Tanzania", "UnitedArabEmirates", "uruguay", "vanuatu", "Vatican", "witticism",
			"Widespread", "xenophobia", "XENIATROPHOBIA", "yearning", "Yemen", "Zambia", "zimbabwe" };
	String[] symbols = { "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "+", "_", "=", "<", ">", "?", "{", "|",
			"}", "[", "]", "/", "\"", ":", ";" };
	Map<String, String> countriesMap = new HashMap<>();
}
