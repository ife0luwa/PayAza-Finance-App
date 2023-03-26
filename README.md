## Paystack API Integration with Spring Boot
This is a Spring Boot application that integrates with Paystack APIs to retrieve and process financial data. The application provides the following features:

* Signup user /api/auth/signup
* Login and authenticate every request with jwt /api/auth/login
* Initiate funds deposit from Paystack /api/wallet/deposit
* Create a web hook with ngrok to serve as callback url to retrieve the payment verification payload /api/wallet/callback
* Persist the resulting data and update user wallet and transaction logs

### Technical Details
The following technical details have been implemented in the application:

* Authentication and authorization have been handled securely and scalably using JSON Web Tokens (JWT) from Spring Security.
* Dependency injection has been implemented in the architecture to help with modularity and testability.
* Error handling and retries for the API calls have been implemented using Spring @Retryable annotation to ensure reliability and fault tolerance.
* Unit testing has been done using Mockito and JUnit.
* The APIs have been versioned using the @RequestMapping annotation to ensure backward compatibility with older versions of the application.


### Installation
To run the application, follow the steps below:

* Clone the repository from GitHub.
* Run the mvn clean install command to install dependencies and build the application.
* Set up your Paystack account and API keys.
* Set up ngrok for local testing and retrieve the callback URL.
* Configure the application properties with the API keys and callback URL.
* Run the application using mvn spring-boot:run or run the PaystackApplication class.


### Usage
Once the application is running, you can use the following endpoints to interact with the Paystack APIs:

`/api/auth/signup`: Create a new user account <br>
`/api/auth/login`: Login to an existing user account <br>
`/api/wallet/deposit`: Initiate funds deposit from Paystack <br>
`/api/wallet/callback`: Retrieve payment verification payload <br>
