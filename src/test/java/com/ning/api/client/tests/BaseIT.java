package com.ning.api.client.tests;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.ning.api.client.NingClient;
import com.ning.api.client.access.NingConnection;
import com.ning.api.client.access.Users.UserFinder;
import com.ning.api.client.auth.ConsumerKey;
import com.ning.api.client.item.Token;
import com.ning.api.client.item.User;

public class BaseIT {
	public String xapiHost;

	public int defaultHttpPort;
	public int defaultHttpsPort;

	public String subdomain;
	public String consumerKey;
	public String consumerSecret;

	public String userEmail;
	public String userPassword;

	protected NingConnection conn;
	protected String userKey;
	protected String userToken;

	@Parameters( { "xapi-host", "http-port", "https-port", "subdomain",
			"consumer-key", "consumer-secret", "user-email", "user-password" })
	public BaseIT(String xapiHost, int defaultHttpPort, int DefaultHttpsPort,
			String subdomain, String consumerKey, String consumerSecret,
			String userEmail, String userPassword) {
		this.xapiHost = xapiHost;
		this.defaultHttpPort = defaultHttpPort;
		this.defaultHttpsPort = DefaultHttpsPort;
		this.subdomain = subdomain;
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
	}

	public String getDefaultApiHost() {
		return xapiHost;
	}

	public String getDefaultNetwork() {
		return subdomain;
	}

	public int getDefaultHttpPort() {
		return defaultHttpPort;
	}

	public int getDefaultHttpsPort() {
		return defaultHttpsPort;
	}

	public ConsumerKey getConsumerKey() {
		return new ConsumerKey(consumerKey, consumerSecret);
	}

	public NingClient getNingClient() {
		NingClient client = new NingClient(getDefaultNetwork(),
				getConsumerKey(), getDefaultApiHost(), getDefaultHttpPort(),
				getDefaultHttpsPort());
		return client;
	}

	public Token getUserToken() {
		NingClient client = getNingClient();
		try {
			return client.createToken(userEmail, userPassword);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@BeforeTest(alwaysRun = true)
	public void setup() {
		NingClient client = getNingClient();
		this.conn = client.connect(getUserToken());
	}

	@AfterTest(alwaysRun = true)
	public void cleanUp() {
		// important: close so that connections get properly disposed of:
		if (conn != null) {
			conn.close();
		}
	}

	public User getCurrentUser() {
		UserFinder userFinder = conn.users().finder(null);
		return userFinder.findByCurrentAuthor();
	}

	public String getCurrentUserAuthorString() {
		return getCurrentUser().getAuthor();
	}

	public String getCurrentUserIdString() {
		return getCurrentUser().id().toString();
	}

}
