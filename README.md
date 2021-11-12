# Application
Each user has an account in which he receives money from his company. We call it an endowment.
The user benefits from these vouchers under two different wallet type :
#### Gift cards

The distribution of gift cards has a lifespan of 365 days, beyond this period it will no longer be counted in the user's balance.
#### Meal vouchers

Meal vouchers expire at the end of February of the year following the date of distribution.
For example: if the date of the distribution is 01/01/2020, the end of the distribution is 02/28/2021.
Likewise, if the distribution starts on 01/15/2020 it will also end on 02/28/2021.

###What the api provides
From an input file that initializes companies and users:
A REST controller to be able to : 
* Distribute gift cards to the user by the company if the company balance allows it.
* Calculate the user's balance.
* Generate the results (wallet amounts) in an output.json file