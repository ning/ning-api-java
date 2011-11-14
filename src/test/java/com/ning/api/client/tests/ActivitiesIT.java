package com.ning.api.client.tests;

import java.util.Iterator;

import org.joda.time.DateTime;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ning.api.client.access.Activities;
import com.ning.api.client.access.Activities.Lister;
import com.ning.api.client.item.Activity;
import com.ning.api.client.item.ActivityField;

@Test(groups = { "Activity" })
public class ActivitiesIT extends BaseIT {

	@Parameters( { "xapi-host", "http-port", "https-port", "subdomain",
			"consumer-key", "consumer-secret", "user-email", "user-password" })
	public ActivitiesIT(String xapiHost, int defaultHttpPort,
			int DefaultHttpsPort, String subdomain, String consumerKey,
			String consumerSecret, String userEmail, String userPassword) {
		super(xapiHost, defaultHttpPort, DefaultHttpsPort, subdomain,
				consumerKey, consumerSecret, userEmail, userPassword);
	}

	@Test
	public void Activities() {
		conn.activities();
	}

	@Test(groups = { "deleters" })
	public void deleter() {
		Activities activities = conn.activities();
		Lister activityLister = activities.listerForRecent(ActivityField.title);
		Activity activityItem = activityLister.iterator().next();
		activities.deleter(activityItem.id()).delete();
	}

	@Test(groups = { "counters" })
	public void counter() {
		Activities activity = conn.activities();
		DateTime now = new DateTime();
		activity.counter(now.minusDays(5)).count();
	}

	@Test(groups = { "counters" })
	public void counterForAuthor() {
		String currentAuthor = getCurrentUserAuthorString();
		Activities activities = conn.activities();
		DateTime now = new DateTime();
		activities.counter(now.minusDays(5)).author(currentAuthor).count();
	}

	@Test(groups = { "listers" })
	public void listerForRecent() {
		Activities activities = conn.activities();
		Lister activityLister = activities.listerForRecent(ActivityField.title);
		iterateActivity(activityLister);
	}

	@Test(groups = { "listers" })
	public void listerForRecentAuthor() {
		String currentAuthor = getCurrentUserAuthorString();
		Activities activities = conn.activities();
		Lister activityLister = activities.listerForRecent(ActivityField.title);
		activityLister = activityLister.author(currentAuthor);
		iterateActivity(activityLister);
	}

	/**
	 * Iterate over the given Lister five times
	 *
	 * @param lister
	 *            Lister to iterate over
	 */
	private void iterateActivity(Lister lister) {
		int count = 0;
		Iterator<Activity> activityIterator = lister.iterator();
		while (activityIterator.hasNext()) {
			activityIterator.next();
			if (count > 5) {
				break;
			} else {
				count += 1;
			}
		}
	}
}
