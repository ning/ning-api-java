package com.ning.api.client.tests;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.access.Users;
import com.ning.api.client.access.Users.UserFinder;
import com.ning.api.client.access.Users.UserLister;
import com.ning.api.client.item.User;
import com.ning.api.client.item.UserField;
import com.ning.api.client.tests.BaseIT;

@Test(groups = { "User" })
public class UsersIT extends BaseIT {

	@Parameters( { "xapi-host", "http-port", "https-port", "subdomain",
			"consumer-key", "consumer-secret", "user-email", "user-password" })
	public UsersIT(String xapiHost, int defaultHttpPort,
			int DefaultHttpsPort, String subdomain, String consumerKey,
			String consumerSecret, String userEmail, String userPassword) {
		super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
				consumerKey, consumerSecret, userEmail, userPassword);
	}

	@Test
	public void Users() {
		conn.users();
	}

	@Test(groups = { "updaters" })
	public void updater() {
		Users users = conn.users();

		// Find current user
		UserFinder userFinder = users.finder(null);
		User user = userFinder.findByCurrentAuthor();

		// Update the status message
		user.setStatusMessage("Hello from Java Client");
		users.updater(user).update();
	}

	@Test(groups = { "counters" })
	public void counter() {
		Users users = conn.users();
		DateTime now = new DateTime();
		users.counter(now.minusDays(5)).count();
	}

	@Test(groups = { "counters" })
	public void counterMembers() {
		Users users = conn.users();
		DateTime now = new DateTime();
		users.counter(now.minusDays(5)).onlyMembers().count();
	}

	@Test(groups = { "counters" })
	public void counterUnapproved() {
		Users users = conn.users();
		DateTime now = new DateTime();
		users.counter(now.minusDays(5)).unapproved().count();
	}

	@Test(groups = { "finders" })
	public void finder() {
		Users users = conn.users();
		UserFinder userFinder = users.finder(null);

		// Find the current member's ID
		String currentUserId = getCurrentUserIdString();

		// Perform a search for the current member by ID
		User foundUser = userFinder.find(currentUserId);
		String foundUserId = foundUser.id().toString();
		assert foundUser != null : "Unable to find user with ID: "
				+ currentUserId;

		// Make sure the current member ID matches the found member's ID
		assert foundUserId.equals(currentUserId) : "Found user ID("
				+ foundUserId + ") doesn't match current user ID("
				+ currentUserId + ")";
	}

	@Test(groups = { "finders" })
	public void findCurrentAuthor() {
		Users users = conn.users();
		UserFinder userFinder = users.finder(null);
		User user = userFinder.findByCurrentAuthor();
		assert user != null : "Unable to find the current user";
	}

	@Test(groups = { "listers" })
	public void listerForAlpha() {
		Users users = conn.users();
		UserLister recentLister = users.listerForAlpha(UserField.fullName);
		iterateUsers(recentLister);
	}

	@Test(groups = { "listers" })
	public void listerForAlphaMembers() {
		Users users = conn.users();
		UserLister recentLister = users.listerForAlpha(UserField.fullName);
		recentLister = recentLister.onlyMembers();
		iterateUsers(recentLister);
	}

	@Test(groups = { "listers" })
	public void listerForAlphaUnapproved() {
		Users users = conn.users();
		UserLister recentLister = users.listerForAlpha(UserField.fullName);
		recentLister = recentLister.unapproved();
		iterateUsers(recentLister);
	}

	@Test(groups = { "listers" })
	public void listerForRecent() {
		Users users = conn.users();
		UserLister recentLister = users.listerForRecent(UserField.fullName);
		iterateUsers(recentLister);
	}

	@Test(groups = { "listers" })
	public void listerForRecentMembers() {
		Users users = conn.users();
		UserLister recentLister = users.listerForRecent(UserField.fullName);
		recentLister = recentLister.onlyMembers();
		iterateUsers(recentLister);
	}

	@Test(groups = { "listers" })
	public void listerForRecentUnapproved() {
		Users users = conn.users();
		UserLister recentLister = users.listerForRecent(UserField.fullName);
		recentLister = recentLister.unapproved();
		iterateUsers(recentLister);
	}

	/**
	 * Iterates five times over the given lister
	 *
	 * @param lister
	 *            UserLister to iterate over
	 */
	private void iterateUsers(UserLister lister) {
		int count = 0;
		Iterator<User> userIterator = lister.iterator();
		while (userIterator.hasNext()) {
			userIterator.next();
			if (count > 5) {
				break;
			} else {
				count += 1;
			}
		}
	}

}
