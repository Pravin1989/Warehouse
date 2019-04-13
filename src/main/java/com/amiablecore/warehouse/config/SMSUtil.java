package com.amiablecore.warehouse.config;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSUtil {
	public static HttpURLConnection connectionGet;
	private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
	public static StringBuilder urlAddress = new StringBuilder(
			"http://182.18.162.128/api/mt/SendSMS?user=sandeep95&password=123456&senderid=EZEEWM&channel=Trans&DCS=0&flashsms=0");

	public static HttpURLConnection getGetConnection(String urlAdress) {

		try {
			URL urlGet = new URL(urlAdress);
			connectionGet = (HttpURLConnection) urlGet.openConnection();
			connectionGet.setRequestMethod("GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connectionGet;
	}

	public static boolean sendSms(String message, String number) {
		boolean status = false;
		urlAddress.append("&number=91" + number);
		urlAddress.append("&route=08");
		try {
			urlAddress.append("&text=" + URLEncoder.encode(message, "UTF-8"));
			HttpURLConnection conn = getGetConnection(urlAddress.toString());
			System.out.println(urlAddress.toString());
			if (conn.getResponseCode() == 200) {
				status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		logger.info("SMS Sent Successfully !!!");
		return status;
	}

//	public static void main(String[] args) {
//		String message = "ALERT:\n" + "Dear Customer, Your<Jeera> commodity lot (Lot No. LT134) has been deposited in"
//				+ "<WH12> warehouse.\n For more report details, please log in your EZEE"
//				+ "WMS account on www.ezeewms.com  or call 919067176059";
//
//		System.out.println(sendSms(message, "9518708922"));
//	}
}
