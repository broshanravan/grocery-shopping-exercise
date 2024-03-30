# Grocery Shopping Application

This application is to be used by the customers to conduct their shopping
as well as being used by members of staff to add new goods to the stock
It has been developed in java using swing library.
This application has has two screens.

The primary screen is used by the customers or members of staff in the checkout to
complete the shopping process and record the payment received.

The second screen will be used by the members of admin team. In order to use this screen the member of staff needs to
have special credentials as upon the entry point the system asks for UN and PW

For the purpose of testing a default set of credential has been created

There are a some unit tests provided for this application to cover most of middle tear functionality of the application.
Currently the data on inventory and users are bening maintained in a local Map which on the point of entry will be populated with 
some essential goods
Each product has a bar code with consits of five character of witch the first two must be alphabetical and the rest are numerical.
When a new product is being added the application will first checks if the barcode is already in usein which case the user will be informed as well as the validity of the other data being entered in the admin screen
The data maintenance was intended to be conducted using H2 database, but to be able to deliver the application ASAP the use od local HashMap has been 
adopted.

