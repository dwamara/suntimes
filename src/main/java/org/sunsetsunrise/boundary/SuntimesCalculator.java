package org.sunsetsunrise.boundary;

import com.google.gson.Gson;
import org.sunsetsunrise.entity.RemoteServiceResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class SuntimesCalculator {

	public static void main(String[] args) throws Exception {
		final SuntimesCalculator calculator = new SuntimesCalculator();
		final RemoteServiceResponse suntimes = calculator.getSuntimes("36.7201600", "-4.4203400", "2017-01-26");
		System.out.println();
	}

	public RemoteServiceResponse getSuntimes(final String latitude, final String longitude, final String date) throws Exception {
		validateCoordinate(latitude);
		validateCoordinate(longitude);
		validateDateFormat(date);
		return parseJsonResponse(readUrl(buildUrl(latitude, longitude, date)));
	}

	public RemoteServiceResponse getSuntimes(final String latitude, final String longitude) throws Exception {
		return getSuntimes(latitude, longitude, null);
	}

	private String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	private RemoteServiceResponse parseJsonResponse(final String jsonResponse) {
		if (responseIsSuccess(jsonResponse)) {
			return new Gson().fromJson(jsonResponse, RemoteServiceResponse.class);
		}
		return null;
	}

	private String buildUrl(final String latitude, final String longitude, final String date) {
		final StringBuffer sb = new StringBuffer("http://api.sunrise-sunset.org/json?");
		sb.append("lat=").append(latitude).append("&");
		sb.append("lng=").append(longitude).append("&");;

		if (date != null && !date.isEmpty()) {
			sb.append("date=").append(date);
		}

		return sb.toString();
	}
	private boolean validateCoordinate(final String coordiante) {
		return true;
	}

	private boolean validateDateFormat(final String date) {
		return true;
	}

	private boolean responseIsSuccess(final String jsonResponse) {
		return jsonResponse.contains("OK");
	}
}
