Overview
========

[ning-api-java][1] is a Java client for accessing the [Ning][2] API.

The [Ning API Documentation][3] contains a complete API reference and
tutorials.


Support
=======

If you need help with using this library, you can find us on the [Ning Build
Network][4]. If you find an issue with this library, please file a bug on our
[issues page][5].


Integration Tests
=================

This project contains integration tests to verify the functionality of the
Ning API. You can run them yourself by updating `/src/test/testng.xml` with the
details of your Ning Site and your Ning API credentials.

*These tests will create content on your site, it is recommended you run these
tests on a test Ning site.*

Once you have updated `/src/test/testng.xml` you can run the tests using:

	mvn verify

This command will build, package, and run the integration tests. You can find
a detailed report of the results in `/target/failsafe-reports/`.


[1]: https://github.com/ning/ning-api-java
[2]: http://ning.com/
[3]: http://developer.ning.com/
[4]: http://build.ning.com/
[5]: https://github.com/ning/ning-api-java/issues
