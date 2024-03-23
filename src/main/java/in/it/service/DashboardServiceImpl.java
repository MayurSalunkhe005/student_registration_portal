package in.it.service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.it.binding.Quote;

@Service
public class DashboardServiceImpl implements DashboardService {

	private String quoteUrl = "https://type.fit/api/quotes";

	private Quote[] quotes = null;

	@Override
	public String getQuote() {

		if (quotes == null) {
			RestTemplate rt = new RestTemplate();
			ResponseEntity<String> forEntity = rt.getForEntity(quoteUrl, String.class);
			String body = forEntity.getBody();

			ObjectMapper mappper = new ObjectMapper();

			try {
				quotes = mappper.readValue(body, Quote[].class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Random r = new Random();
		int nextInt = r.nextInt(quotes.length - 1);
		return quotes[nextInt].getText();
	}
}