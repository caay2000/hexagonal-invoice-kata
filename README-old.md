# Hexagonal Invoice Kata

<img align="right" width="200" height="183" src="kata-readme/hexagonal-gif.gif">The idea of this kata is to learn how to implement a hexagonal architecture project while understanding some benefits that it will bring to you and your team.

<sub>_Hexagon loading image property of [Ferenc Horvat](https://dribbble.com/ferenchorvat)_</sub>

## Table of Contents

- [Disclaimers](#disclaimers)
- [Why?](#why)
- [How?](#how)
- [Kata](#kata)
    * [1. Introduction](#1-introduction)
    * [2. Non-Hexagonal Solution](#2-non-hexagonal-solution)
    * [3. Migrating to Hexagonal Architecture](#3-migrating-to-hexagonal-architecture)
    * [4. Hexagonal Architecture Solution](#4-hexagonal-architecture-solution)
- [Summary](#summary)

## Disclaimers

This is a simple exercise to practice a with an hexagonal architecture. You can read multiple articles about hexagonal, and each one will give you a different view, different implementation details or different names. This is what worked for me the lasts years. It does not mean that other solutions are worse or better, it's just my experience. Of course, following this architecture model, there are more advanced concepts, but the idea of this kata is to start with hexagonal, so it needs to be simple and demonstrate the basic concepts.

The same concepts can be applied to other languages or situations, but for sure, they will need to be adapted and/or may could be any framework facilitating it.

As said, this is an exercise just for hexagonal architecture. Said that, do not expect that I enforce any other concept/behaviour during this kata. You can always do it following TDD, DDD, apply all the design principles that you want or do a code full of smells, if you understand how hexagonal architecture can be implemented, how to use it and when, that's the main objective of this kata.

You can always contact me, write an issue or directly create a PR if you want to propose, modify, update or correct anything in this project.

## Why?

There are multiple explanations, articles and even books out there about the pro/cons of hexagonal architecture that you can read to understand them, but as a summary, I would say:

- **Infrastructure independence**. It allows you to not be dependant to any infrastructure, call it framework, database client, messaging system or any other concept you can imagine. It facilitates to modify for example a database by a REST endpoint, without modifying your business logic.
- **Business logic isolation**. Your business logic is the core of your application. You can develop the core concepts without being involved in I/O. It facilitates the incremental delivery strategy of your application, as lots of technology decisions can be replaced quiclky if hexagonal architecture is implemented correctly in your project.
- **Easy to test**. It facilitates the unit testing of your application. There are multiple concepts out there about testing, but this architecture allows you to fully test your core concepts independenctly of your infrastructure, and viceversa.
- **High Maintainability**. As your code is isolated, independent and easily testable, it facilitates the maintainability of your project. It allows you to introduce new features without to much effort, and refactor exsiting ones really quick.

## How?

There are only three important rules in hexagonal architecture:

- 1 You need at least 3 layers of abstraction
- 2 You can never trespass more than one layer at a time
- 3 You can never go from an inner layer to an outer layer

That's something that will never change in any hexagonal architecture. TODO

It's impossible to explain a complex concept like this one without a drawing. I tried to create a really simple one, with the minimum lines and concepts to explain just the basics.

![Hexagonal Diagram](kata-readme/hexagonal.png)

TODO

### Components (TODO)

- Domain Model
- Ports
- Adapters

## Kata

You need maven and kotlin for this kata. It has been compiled with jvm 1.6, so it should be compatible with any version 1.6+.

To compile the whole project, just execute 'mvn clean install' from terminal, or compile the project with intellij.

### 1. Introduction

Your new job starts today in a very famous media group. You have been told that they want a new application to provide data to a new platform they are starting to develop. They will need account, product and invoice information, and the last intern created a simple application that accomplishes what they want nowadays. Anyway, you have been told, that you will need to add new features to it quite soon, so it's better to start taking a look at it.

#### 1.1 Open the `hexagonal-kata-kotlin-application` module

TODO

You will see a simple Ktor application (Ktor & Koin are not relevant to the kata itself) with 3 REST endpoints: `getAccountByAccountId`
, `getProductsByAccountId` and `getInvoiceByAccountId`. These endpoints call an `InvoiceService`, and this class is the one that retrieves account and product information from the company currently services.

#### 1.2 Execute `AccountControllerTest` to check that everything works

There is an `AccountControllerTest` class that checks that everything works as expected. You can execute it to see that everything is green, working and fantastic. Take a look at the rest of the application and be sure that you understand every bit of it before continuing.

### 2. Non-Hexagonal Solution

**(15 - 30 min)**

If you are already convinced of the benefits of hexagonal architecture you can skip this part of the kata. Here we will familiarise more with our current application, and we will test our current achitecture adding and modifying exsiting features/dependencies. The main idea, is to check what happens in a non-organised architecture, and later on compare the same changes with an hexagonal architecture

#### 2.1 Change the external dependency

After checking that our application is green and working, we received a requirement. Seems that we have been consuming data from a really old clients/respositories, so we'll need to update to a newer versions for the next features we'll need to implement soon.

To do this, we'll just need to update the external dependencies version property in our `pom.xml`, from version 1 to version 2

```xml

<properties>
    <external.services.version>2</external.services.version>
</properties>
```

After updating the dependency version, you'll realise that your application is broken. Let's try to solve it and make it compile again. You'll need to change the InvoiceService class because now, the external model of productClient has changed. The needed changes are the following ones

```kotlin
fun getInvoiceByAccountId(accountId: String): Invoice {
    val account = accountClient.getAccountById(accountId)
    val listAccountProducts = productClient.getProductsByAccountId(accountId)
    val productInformation = productRepository.getProductInformation()

    val accountProducts = listAccountProducts.filter {
        it.endDate == null < ----change!!!
    }.map {
        productInformation.first { data -> data.id == it.id } < ----change!!!
    }

    return Invoice(
        account = account,
        products = accountProducts,
        totalAmount = accountProducts.map { it.price }.sum() < ----change!!!
    )
}
TODO add line numbers
```

After solving the compilation problems, let's try to run the tests again...

Failing... To make the tests pass again, you will need to modify the controller response, because now your response is different from before. This could be done easily implementing some data classes in the controller following the json response format. Jackson will do the rest.

#### 2.2 New features to be implemented

Now that we have our external services version updated to version 2, we are ready to add a new feature to our application.

We want to add some discounts to some of our best customers. Premium accounts will have better prices. To do this, you just need to check which account is premium, with the new endpoint on the AccountClient (`getPremiumFeatures(accountId: String)`) and apply the premium price (instead of the original one) for all its products. We have been notified also that some fields in our JSON should be renamed. `productName` and `productPrice`, should be now, `name` and `price`.

To check that everything works as expected, copy all files from kata-test-data 2.b/invoice to your tests resources path and execute again all the tests. Try to achieve a green build.

#### 2.3 Another External Services version

Next step is to just update to the latest version of external services, version 3. After updating, you will realise that the product repository disappeared. Now, everything is integrated in ProductClient. You'll need to modify you application implementation to solve this. After solving the implementation details, everything should work as before, but...

Seems that our tests are failing... why? Our new version of the external dependencies brings more changes than we expected. There is some PII (Personally Identifiable Information) returned now from the AccountClient, and seems that we are showing it in our invoice! That information should never be shown, so avoid printing it in the invoice!

After all this changes, we finally have our application completely updated and working.

#### 2.4 Non-Hexagonal Summary

Let's think about what problems did you find while doing this exercise. The main problem is that everytime we change our external dependency, we'll need to update our application code, our business code. Is this right? why do we need to change our business code, if there is no new features?

When we added a new feature, that's ok, we had to change our business code to implement the new feature, but for just updating an external dependency?

Another problem was that we were showing PII information in our invoice, but we did not do any change to expose them... hopefully we had tests before sending it to production.

Did you find disturbing something more?

To start the next point, you can revert/stash our current code and leave the code as it was on the start of the exercise.

### 3. Migrating to Hexagonal Architecture

Ok, let's begin with our Hexagonal Architecture exercise. Let's first think what should be our first steps. Take a look again to the hexagonal diagram:

![Hexagonal Diagram](kata-readme/hexagonal.png)

#### 3.1. Create the contracts

First of all, we're going to create our outbound model. Why? Because it's the contract that our business logic should accomplish. We need an application to fulfill invoices, so let's create the outbound model.

- Create a model folder with outbound and outboundinbound sub-folders.

- Create the outbound model contract (remember, what we will offer to our consumers). Our application should return all the data needed for an invoice, so let's create that API in the exernal package:
  ```kotlin
  interface InvoiceApi {
      fun getInvoice(accountId: AccountId): Invoice
  }
  ```
- The models needed can be added to a data class in the same outbound package (notice that AccountId can be done as a typealias of String).

We have now our outbound model, let's think about the outboundinbound ones. Which data we will need, and where do we think we can retrieve it from? As we already know what will be our next features and where we'll need to retrieve the data from, that will be easier than usually. (_In real world projects, maybe you'll need to iterate some times before discovering the correct models._) We'll need account information, products of an account and products information ... Simplifiying it, we'll need Account Data and Product Data, so let's create two different
apis in our outboundinbound model folder:

- Create AccountApi and ProductApi interfaces. What data do we need? think twice about what contracts should this interfaces define
  ```kotlin
  interface ProductApi {
    fun getProducts(accountId: AccountId): List<Product>
  }
  ```
  ```kotlin
  interface AccountApi {
    fun getAccount(accountId: AccountId): Account
  }
  ```

Now we have our inbound model. We created the contract that we'll use to retrieve the date we need. But there is something missing in our contracts. We know what functions we can call or we offer, but not the data we should send/receive. Let's think about the models:

- Invoice should return everything we need to accomplish our tests, account data, products data and total amount of the invoice. At the moment, you can use the Account and Product models from the outboundinbound apis, even dough is not a good practice, but let's do it atm
- For our outboundinbound models, we need Account data, and products data. So, as we used this models in the invoice, whatever we include here, will be returned later on by the Invoice model

Now that we have our models in place, we can build our business logic

#### 3.2 Implement our business logic, and our adapters

Let's start creating a new InvoiceService following the rules below (just remove, rename or do whatever you want with the actual file).

- Our InvoiceService should implement our outbound api
- it should receive the outboundinbound apis as parameters

It should be quite easy to create the business logic, as our outboundinbound apis are really simple and return all the data we need.

Now, let's move to our Controller. We should change it. First of all, it should receive as parameter our outbound api. And you almost have it.

The only piece missing in our application are the adapters. The adapters, are the pieces that will fulfil our outboundinbound apis. Let's start with the AccountAdapter.

- Create the AccountAdapter in the infrastructure package.
- It should implement the AccountApi
- It will need to receive as parameter the external AccountClient, where we retrieve the date from.
- Just do the call to the client and translate the data to your own model

Do exactly the same with the ProductAdapter, but remember it has two external dependencies

- Create the ProductAdapter in the same package.
- It should implement the ProductApi
- It will need to receive both ProductClient and ProductRepository
- Just retrieve all the data you need from the external dependencies, and translate it to you own outboundinbound model

#### 3.3 Put it all together with Dependency Injection

Now that we have both adapters, controller and business logic in place, following our contracts, let's put it all together. We'll need to modify the Application.kt class, the one in charge of our dependency injection. This is where we replace our Apis by current implementations.

If you are not familiar with Koin or any other DependencyInjection framework, I'll give you the solution below:

```kotlin
install(Koin) {
    modules(module {
        single { AccountClient(configuration = AccountClientConfiguration()) }
        single { ProductClient(configuration = ProductClientConfiguration()) }
        single { ProductRepository(configuration = ProductRepositoryConfiguration()) }

        single {
            InvoiceService(
                accountApi = AccountAdapter(accountClient = get()),
                productApi = ProductAdapter(productClient = get(), productRepository = get())
            )
        }
        single { InvoiceController(invoiceApi = get<InvoiceService>()) }
    })
}
TODO add line numbers
```

**Now, let's execute the tests. We have some failing tests right? To solve them, we just need to create the response object we need (outboundinbound to the controller), and map our outbound model to it. TODO TODO TODO**

Now, we have everything green. Our application now follows the hexagonal architecture principles. We are ready to implement any new feature easily. Let's check it out.

#### 3.4 Migrating to hexagonal architecture summary

As you can see, now our application is quite well organised. We have 3 different packages

- **domain**: it contains our services, our use cases, our business logic, the code that does things in our domain. In this case, it gets raw data from any kind of inbound model, operates and transfoms it, and returns it to our consumers. In this package, we just have business logic, no I/O operation, no external dependencies. You can have all your business tests isolated from any dependency, no external noises.
- **infrastructure**: it contains all the adapters, inbound or outbound, that will plug in our ports, our apis. In the outbound side, we have our InvoiceController, that will return a json of the information retrieved from our business logic. And in the inbound part, we'll have two different adapters, that will get information from Databases, Clients or any other external dependency to fulfil our inbound api, our inbound port contract.
- **model**: it contains contracts, our ports, the apis our consumers/adapters will call/implement. We expose our InvoiceApi contract in the outbound port, offering that contract to anyone who wants to get invoice information. And we also offer two inbound contracts, AccountApi and ProductApi, for our application to retrieve all the raw data it needs.

Now, depending on the work we need to do, we know clearly what we should modify. For example, and external dependency modification: we don't need to change our business logic, just change the adapters.

### 4. Hexagonal Architecture Solution

Let's start with the exercise in this new hexagonal architecture fashion

#### 4.1. Change the external dependency

We want to do the same exercise we made before without hexagonal architecture. The first step is to update the external dependecies to version 2

> To do this, we'll just need to update the external dependencies version property in our `pom.xml`, from version 1 to version 2

```xml

<properties>
    <external.services.version>2</external.services.version>
</properties>
```

Compile, and you will see some compilation problems. Just as before. Do you remember what we had to do before? This small change implied changes in every piece of our application. Let's see now what we need to change:

Check the ProductAdapter, that is the one failing. Now, the external ProductClient returns a different object. Instead of a Map<String, LocalDate?>, it now returns a List<AccountProduct>. This means that we'll need to change how we accomplish our contract. You get the same data, but in a different way. Ok, you will return the same data, because our contract is not modified, the only thing that changed is an external dependency. Ok, get this new data and populate our Product model correctly. Run the tests. Green

Think about it. Did your business logic change? no! Then, if you modify your business logic, something is wrong! Let's move on.

#### 4.2 New features to be implemented

> Now that we have our external services version updated to version 2, we are ready to add a new feature to our application.
>
> We want to add some discounts to some of our best customers. Premium accounts will have better prices. To do this, you just need to check which account is premium, with the new endpoint on the AccountClient (`getPremiumFeatures(accountId: String)`) and apply the premium price (instead of the original one) for all its products. We have been notified also that some fields in our JSON should be renamed. `productName` and `productPrice`, should be now, `name` and `price`.

Now, we need a new feature, so we'll need to change our business logic. Also, this new feature requires new data, so we'll need to modify our inbound contracts. Let's see:

We need to know if a customer is premium or not.

- Add this field to our existing inbound model Account. It's just a boolean
- Update AccountAdapter file, to call the new client function and to return its value in our new field.

We also need to know the premium price of the products.

- Add a premiumPrice field in our inbound model Product.
- Update ProductAdapter to fill also that field

Now, we have that new data available for our business logic. Let's implement the feature:

- In the InvoiceService, use the premiumAccount field to decide if the price of the product is premium or normal.
- Remember that the totalAmount is also different if the account is premium

Let's check that everything works as expected

- Copy all files from kata-test-data 2.b/invoice to your tests resources path
- Execute again all the tests
- Adapt if needed the json response. _The best way to do it, is create your own objects for that response, but you can always take advantage of the ones from the outbound model **TODO**_
- Achieve a green build.

#### 4.3 Another External Services version

> Next step is to just update to the latest version of external services, version 3. After updating, you will realise that the product repository disappeared. Now, everything is integrated in ProductClient.

Before, this change caused a lot of problems, we had to modify all our logic to remove the repository. Now, with hexagonal, you just need to change the adapter, again.

- Copy all files from kata-test-data 2.c/invoice to your tests resources path
- Execute again all the tests
- Everything green.

Remember that before, without hexagonal, apart of the repository change already mentioned, we also had problems with some PII fields that were returned on the invoice. We had tests and discovered it quickly... but this thing didn't happened now. Why? Because we are controlling what we return, we are not using external objects in our controllers.

## Summary

TODO

## Appendix

|                   	| v1                                                        | v2                                                                                                                                                                | v3                                                                                                                                                                |
|-------------------	|--------------------------------------------------------	|----------------------------------------------------------------------------------------------------------------------------------------------------------------	|----------------------------------------------------------------------------------------------------------------------------------------------------------------	|
| AccountClient | Account {<br>&nbsp;&nbsp;accountId:String<br>&nbsp;&nbsp;name:String<br>&nbsp;&nbsp;address:String<br>&nbsp;&nbsp;city:String<br>&nbsp;&nbsp;postalCode:String<br>&nbsp;&nbsp;email:String<br>} | Account {<br>&nbsp;&nbsp;accountId:String<br>&nbsp;&nbsp;name:String<br>&nbsp;&nbsp;address:String<br>&nbsp;&nbsp;city:String<br>&nbsp;&nbsp;postalCode:String<br>&nbsp;&nbsp;email:String<br>} | Account {<br>&nbsp;&nbsp;accountId:String<br>&nbsp;&nbsp;name:String<br>&nbsp;&nbsp;address:String<br>&nbsp;&nbsp;city:String<br>&nbsp;&nbsp;postalCode:String<br>&nbsp;&nbsp;email:String<br>&nbsp;&nbsp;birthDate:LocalDate<br>&nbsp;&nbsp;gender:Gender<br>} |
| ProductClient | Map&lt;String,LocalDate?&gt;| List&lt;AccountProduct&gt;&nbsp;{<br>&nbsp;&nbsp;id:String<br>&nbsp;&nbsp;premiumAccount:Boolean<br>&nbsp;&nbsp;startDate:LocalDate<br>&nbsp;&nbsp;endDate:LocalDate?<br>} | AccountProducts {<br>&nbsp;&nbsp;premiumAccount: Boolean<br>&nbsp;&nbsp;products: List&lt;Product&gt;&nbsp;{<br>&nbsp;&nbsp;&nbsp;&nbsp;id:String<br>&nbsp;&nbsp;&nbsp;&nbsp;name:String<br>&nbsp;&nbsp;&nbsp;&nbsp;price:BigDecimal<br>&nbsp;&nbsp;&nbsp;&nbsp;premiumPrice:BigDecimal<br>&nbsp;&nbsp;&nbsp;&nbsp;startDate:LocalDate<br>&nbsp;&nbsp;&nbsp;&nbsp;endDate:LocalDate?<br>&nbsp;&nbsp;}<br>} |
| ProductRepository | Product {<br>&nbsp;&nbsp;id:String<br>&nbsp;&nbsp;productName:String<br>&nbsp;&nbsp;productPrice:Int<br>} | Product {<br>&nbsp;&nbsp;id:String<br>&nbsp;&nbsp;name:String<br>&nbsp;&nbsp;price:Int<br>&nbsp;&nbsp;premiumPrice:BigInteger<br>} | |
