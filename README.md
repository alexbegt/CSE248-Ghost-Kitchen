# CSE248
This repository contains all of my work for CSE248 spring semester.

In the CSE248 folder is my spring boot project

In this project we had to create a ghost kitchen website that allows users to check out, view their orders.

NOTE: I was unable to finish the checkout due to issues beyond my control, this was not an easy project for me.

For the backend, we used spring boot.

For the database, I went with mysql.

And for the front end I used html controlled by spring boot's web along with thymeleaf and used bootstrap for html.

When you download this project, you first need to go into application.properties and set the google recaptcha site and secret key, and fill in your email informating, including the host, the username and password and your database information. THE PROJECT WILL NOT WORK WITH THIS MISSING.

This project supports MySQL and H2.


Once you start the project and view your localip:9090, it will bring you to the login page, where you can reset your password, register and login.

This login page ensures you enter a email and password to be able to log in, and if the user has 2fa enabled, you must enter the code from your app.

Login will also send you an email if you are logging in from an unknown device or ip, you need to confirm the link that is sent to your email to be able to login.

Registration is well, registration, you enter in your email, your first and last name, your passsword, check off if you want to use two factor authentication and you need to verify that you are not a robot.

Once you log in, you are brought to the account home page which lists, view cart, view restaurants, view orders, change address, change credit card, change password and log out.

Clicking view cart will show you the current cart that the user has, currently it lets you add food items from mutliple different restaurants due to time constraints.

Clicking view restaurants brings you to a page that shows you the list of restauranets and clicking on one of them allows you to view their menu

Looking at the menu, you can click on one of the items for it to be added to you cart, if it fails to add to your cart, you get a message up at the top stating why.

Clicking view cart brings you to your cart where in theroy you would be able to remove an item from the cart by clicking on it but that feature wasn't implemented.

In theroy, I would've had a checkout button that you would click that would've allowed the user to process their order to be sent to the restaurant. 

Under the user's homepage, that's the only place that you are able to change your address and your credit card information, in theroy If checkout was complete, It would be an option linked there

clicking view orders shows you your order details.

Clicking change password brings you to a page where you put in your old password along with the new one, and if the password is the valid format, it changes your password.

Clicking change address brings you to a page where you put your new address information in.

Clicking change credit card brings you to a page where you put your new credit card information in.

clicking log out, well logs you out.

At the bottom of the page there is a button that allows you to change what langauge you are view the website in, currently we support english and spanish, but the spanish translation was no-where complete.


There are two default users in the database, one is admin@admin.com and the other is user@user.com, both have orders so that you can preview the order details.



If I had more time to debug and finish my project, I would go back and finish the work on the checkout page redirecting you to an order and giving an invoice.



Overall, I really enjoyed this project and I would go back and work on spring boot more.

I enjoyed taking all the CSE Courses at Suffolk, and enjoyed taking the ones taught by you Professor Chen. (No Offense to anyone else)

Thank you for teaching me new stuff I did not know about.

Enjoy your summer, and feel free to email me/contact me whenever.

-Alexander Behrhof