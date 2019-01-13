# Foreign-exchange
An application that displays foreign exchange rates taken from Bank of Lithuania to user specification.

This application is written in JAVA using Spring framework. Application is portable and relies on internet connetion to function properly.
## Features

- User can specify a period for the request.
- User can specify one or more currencies to be called and displayed to specified period of time.
- Calculates currency exchange rate for the given period.

## Installation and running (win)

Foreign-exchange app requires JRE 1.8 to be installed on the machine and internet connection to run.

- Pull source code to a local repository.
- Open root folder.
- Open 'run.bat'.
- Follow instructions.

### Using the app

- Enter desired date for the start of a period for which the results will be collected. If left blank the app will use current date as input.

- Enter date for the end of period. If the start date is current date this will automaticaly be changed to current date and the period will be treated as one day and only display current rates but no change.

- Enter currency codes (aud, usd, gbp, etc.). You can add multiple currency codes each separated by pressing 'Enter'. Double pressing 'Enter' will lead to application sending requests and displaying data.

- App terminates after results are printed.

## Possible improvements

- Implementing a SOAP consumer and JAXB. (This aproach while considered, later abandoned due to webservice giving 403 responses when requests were made.)
- The ability for the user to execute multiple queries instead of having to restart the application.
- Saving parsed and formatted data.
- UI redesign.
- More tests.

### Known bugs

- Not all currencies can be requested.
- Minor ui issues.

