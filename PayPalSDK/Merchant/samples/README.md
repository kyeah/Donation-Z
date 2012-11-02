PayPal Java Merchant API sample using the sdk
============================================
	The merchant-sample project is a API scratch pad application that allows you to test PayPal's APIs. This application is a handy complement to the API reference guides available on x.com. The samples also demonstrate how you can use the SDK to call the API methods in your own application.

To build and run the merchant-sample:
------------------------------------
* 	update the sdk_config.properties in the merchant-sample/WebContent/WEB-INF/ directory with your API credentials.
*	simply run ant in the merchant-sample directory - it would rebuild the SDK if the sdk jar file doesn't exist already.
*	copy the dist/merchant-sample.war into your tomcat/webapps directory
*	start your tomcat if it's not already running
*	access http://<your-server-host:port>/merchant-sample/ in your browser to play with the test pages.

