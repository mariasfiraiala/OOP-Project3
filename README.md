Copyright 2022 Maria Sfiraiala (maria.sfiraiala@stud.acs.upb.ro)

# POO TV - Project2

<div align="left"><img src="https://i.imgur.com/S1ROjQr.gif" width="250px"></div>

## Design Patterns Used

1. `Singleton Pattern`

   This pattern (or better called, anti-pattern) was used in order to simulate the presence of a global variable, `session`. Due to this value being required to globally maintain its value, in order for `currentUser` and  `currenPage` to be easily updated from other methods, the choice of using a Singleton pattern was obvious.

   However, the `Singleton Pattern` has its disadvantages: Singleton makes it difficult to identify the instance in its calling class; its object becomes directly accessible without being passed through the constructors.
   As the code grows, it's more and more difficult to identify how different objects are related, therefore introducing ambiguities.

1. `Factory Pattern`

   This pattern was used in order to manage the creation of pages that inherit the abstract class `Page.java`.
   One of the main reason as to why we decided to use this pattern is that Factory provides abstraction between implementation and client classes through inheritance.
   Our code becomes more robust, less coupled and easy to extend, therefore coming closer to what an OOP project should strive be.

1. `Strategy Pattern`

   We used Strategy in order to improve the sorting mechanism.
   By dynamically applying each sorting method, without checking from what class it is coming from, as we wanted to just make the decision once and forget about it.
   Another reason is that Strategy permits decoupling the decision about which strategy to use from the code that needs to execute the strategy, therefore, the platform flow is just :sparkles: better :sparkles:.

1. `Facade Pattern`

   This one should be obvious as to why we used it.
   It allows the user (ergo, the teacher assistant reading this README) to see the nice, clean part of our code (the entry point to the application).
   Facade covers the complexity and lets the customer use our product without worrying about the internals.

## Structure of the Project

* `src/`
    * `commands/` &rarr; implementation of `on page` and `change page` commands + utils
      * `Commands.java` &rarr; utils commands include output related ones and deep copy methods
      * `Database.java` &rarr; database add and delete commands
      * `Recommendation.java` &rarr; recommendation for premium user algorithm
    * `info/` &rarr; classes that extend the information from input
      * `Movie.java`
      * `Notification.java`
      * `User.java`
    * `input/` &rarr; classes used for getting input, some of them will bes used as is
      * `ActionInput.java` &rarr; used as is, no need to add more info
      * `ContainsInput.java`
      * `CredentialsInput.java` &rarr; used as is, no need to add more info
      * `DataInput.java` &rarr; aggregates info from all the other classes in this package
      * `FiltersInput.java` &rarr; used as is, no need to add more info; contains instances of `ContainsInput` and `SortInput`
      * `MovieInput.java` &rarr; will be extended by `Movie`
      * `SortInput.java`
      * `UserInput.java` &rarr; will be extended by `User`
    * `pages/` &rarr; classes used for building the hierarchy
      * `Authenticated.java`
      * `Login.java`
      * `Logout.java`
      * `Movies.java`
      * `Page.java` &rarr; abstract class, every other special page will extend it
      * `PageFactory.java` &rarr; Factory design pattern used for creating pages
      * `PageHierarchy.java` &rarr; utility class, simply creates the particular hierarchy of our platform
      * `Register.java`
      * `SeeDetails.java`
      * `Upgrades.java`
    * `platform/`
      * `FacadeSession.java` &rarr; Facade design pattern used for easing user experience
      * `Session.java` &rarr; class that starts the session, refreshes it (after each test) and stores essential info (movies and users database, current user and page)
    * `strategy/`
      * `SortByDuration.java` &rarr; sorts movies based on duration; implements `SortStrategy`
      * `SortByRating.java` &rarr; sorts movies based on rating; implements `SortStrategy`
      * `SortStrategy.java` &rarr; interface used for Strategy design pattern when sorting based on multiple factors

## Program Flow

The program starts by creating (or refreshing) the session.
The current session will take care of creating the database used for movies and users, together with initializing each user with the input info + its visible movies (depending on the country).

It's the `Session` that also sends commands to static methods like `changePage` or `onPage`.
Once the control is passed to these methods, the `Session` instance will be updated only regarding the current user and page.

Inside the `Commands` methods, we'll let each page take care of its own actions.
Each page will also change the current page and user, when needed.

**Note**: A key element in this process is the page hierarchy that was constructed at the start of the session, it stores the pages, that have info not only about next pages but also about the actions that can be performed on them.

The page takes care of itself by an inheritance mechanism got from its parent (the `changePage` method) and by having its own methods, applied at the right time.