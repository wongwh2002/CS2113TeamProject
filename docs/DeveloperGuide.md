# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

[Original Source](https://github.com/nus-cs2113-AY2425S1/tp)

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

### Storage Component
#### Motivation behind the component:
+ Allows the user to save changes, so that they can resume where they left off.
+ Allows advanced users to edit files directly, enabling fast, manual adjustments.

#### How the Storage Component works:
+ Variables and File Path:
  + `incomes` → `./incomes.txt`
  + `spendings` → `./spendings.txt`
  + `password` → `./password.txt`

+ To save edited lists: 
  + It is done when users type `bye`, which signals the end of the program. 
  + The lists are serialised to a user-editable format in their respective files.

+ To load saved lists:
  + It is done upon program startup, when `Wiagi` is constructed.
  + Within `Wiagi` constructor, it will create a new instance of `Storage`, which will then de-serialise the data at the 
  `incomes` and `spendings` file paths to an `IncomeList` and `SpendingList` respectively.
  + `Wiagi` will then retrieve the lists in `Storage` to initialise its lists.
  + Data corruption in the file triggers an exception, often due to user-editing errors.
  + For missing files (e.g., new users), files are created and the initialised lists will be empty..

+ To load password:
  + The hashed password will simply be loaded from the password file.
  + For missing files (e.g., new users), users will be prompted to set a new password at the start of the
  program. The entered password will then be hashed and stored in a newly created password file.

#### Implementation:
#### Storage class
The `Storage` class is a class that stores `incomes`, `spendings` and `password`. 
Upon instantiation, it will call `IncomeListStorage.load()`, `SpendingListStorage.load()` and `LoginStorage.load()`, 
which will initialise the variables in `Storage` respectively.

#### save method in `IncomeListStorage` `SpendingListStorage`
Both classes have similar implementation for `save()`, except that `SpendingListStorage` saves budget details on the 
first line.
+ Format: `daily budget | monthly budget | yearly budget`
+ A for loop will loop through the list, and get each of the attributes of each entry within it and separate them by 
`|`. Hence, each entry will be written line by line to the file.
+ Format: `amount | description | date | tag | recurrence frequency | last recurrence date | last recurrence day`
  + E.g. `add income 10 part time /2024-10-10/ *job* ~monthly~` will be stored as
    `10.0|part time|2024-10-10|job|MONTHLY|2024-10-10|10`
![storageSave.png](./Diagrams/Storage/saveStorageSequenceDiagram.drawio.png)

#### load method in `IncomeListStorage` `SpendingListStorage`
Both classes have similar implementation for `load()`, except that `SpendingListStorage` also loads budget details.
+ A while loop will loop through the file with a scanner to read line by line till the end of the file is reached.
+ It splits each line by `|` to access each attributes, convert date and last recurrence date to `LocalDate` type, 
and add it to the lists.
+ During the process, if a line is corrupted, an exception will be caught and user will be informed.
![storageLoad.png](./Diagrams/Storage/loadStorageSequenceDiagram.drawio.png)

#### load method in `LoginStorage`
+ It first checks if the password file exists.
  + If yes, it will use a scanner to read the file and initialise `password` in `Storage`.
  + Else, it will call `createNewUser()`, which creates a new password file and use `getNewUserPassword()` to scan for
  the user input. Then, it will be hashed, stored in the file, and be used to initialise `password` in `Storage`.
![storageLoad.png](./Diagrams/Storage/loginStorageSequenceDiagram.drawio.png)

### Recurrence Component

#### Motivation behind the component:</br>
+ Allows the user to set specific expenditure and incomes as recurring events to increase efficiency when using the
  application
+ Users may have differing frequencies for recurring events thus application gives them a few common options

Illustrated below is the class diagram for the Recurrence Component:</br>
</br>
<img src="./Diagrams/Recurrence/recurrenceClassDiagram.png" alt="recurrenceClassDiagram" width="800"/>
</br>
</br>
Illustrated below is the sequence diagram of the Recurrence Component: </br>
</br>
<img src="./Diagrams/Recurrence/recurrenceSequenceDiagram.png" alt="recurrenceSequenceDiagram" width="700"/>
</br>
For the reference fragment of 'load from storage', refer to [Storage component](#storage). </br>
For the reference fragment of 'add recurring entry', refer to 
[checkIncomeRecurrence / checkSpendingRecurrence](#checkincomerecurrence--checkspendingrecurrence-method) method. </br>


#### How the Recurrence Component works:</br>
+ Upon running the application by the user, `Storage` component will load the `IncomeList` and `SpendingList` members of
`Wiagi` to retrieve past data.
+ Both list are then iterated through. Each member of the list is parsed through `Parser` which returns the type of 
recurrence it is (eg. `DailyRecurrence`, `null`) which is encapsulated as a `Recurrence` object.
+ If `Recurrence` is not `null` (ie. a recurring entry), it checks the entry and adds to the `SpendingList` and 
`IncomeList` if needed. </br>

#### Implementation:
#### Recurrence class
The `Recurrence` class is a abstract class that provides the interface for checking `Income` and `Spending` and adding 
recurring entries into the list. </br>
The following are the abstract methods defined: </br>
+ `checkSpendingRecurrence`
+ `checkIncomeRecurrence`

The following are child classes of `Recurrence`:
+ `DailyRecurrence`: Handles entries labelled as daily recurring events
+ `MonthlyRecurrence`: Handles entries labelled as monthly recurring events
+ `YearlyRecurrence`: Handles entries labelled as yearly recurring events

##### parseRecurrence method
Class: `Parser` </br>
Method Signature: </br>
```
public static Recurrence parseRecurrence(Type entry)
```
Functionality: </br>
1. Takes in child class of `Type` (ie. `Spending`, `Income`)
2. Matches the `reccurenceFrequency` attribute with switch case to determine which `Recurrence` child to return
3. Returns `DaillyRecurrence`, `MonthlyRecurrence`, `YearlyRecurrence` or `null`(If not a recurring entry).

##### checkIncomeRecurrence / checkSpendingRecurrence method
Class: `DailyRecurrence`, `MonthlyRecurrence`, `YearlyRecurrence` </br>
Method Signature: </br>
```
@Override
public void checkIncomeRecurrence(Income recurringIncome, IncomeList incomes)
@Override
public void checkSpendingRecurrence(Spending recurringSpending, SpendingList spendings)
```
Below illustrates the functionality of the checkIncomeRecurrence method through a sequence diagram </br>
</br>
<img src="./Diagrams/Recurrence/addRecurrenceEntry.png" alt="addRecurrenceEntry" width="700"/> </br>
Note that recurrence frequency is either 1 day (daily), 1 month (monthly) or 1 year (yearly). </br>
Since checkSpendingRecurrence method follows the same sequence as checkIncomeRecurrence method, the diagram is omitted 
for brevity.

Functionality: </br>
1. Checks `lastRecurred` attribute of `recurringIncome`/`recurringSpending` against the current date via `LocalDate.now`
2. According to the type of recurrence, check if enough time has passed between the 2 dates
3. Adds additional recurring entries into the `IncomeList`/`SpendingList` if needed.

##### updateRecurrence method
Class: `SpendingList`, `IncomeList` </br>
Method Signature:
```
public static Recurrence parseRecurrence(Type entry)
```
Functionality: </br>
1. Loops through its list and calls upon `Parser#parseRecurrence` to determine type of `Recurrence`
2. Calls upon `Recurrence#checkSpendingRecurrence` or `Recurrence#checkIncomeRecurrence` to update list if the new 
recurring entry is supposed to be added

#### Here are some things to take note:
+ Entries are only added when user logs in, which is not determinable, thus many additional entries may be added at once
(eg. user last logged in 4 days ago with one daily recurring entry in the list. When the user logs in, 4 days of entries
will be backlogged and added). List is thus also sorted by date after recurrence is done.
+ Additional entries added by `Recurrence` are being set to not recurring events to prevent double recurring entries
added in the future
+ Recurring entries stores `dayOfRecurrence` to counter varying days in months. Below is a example scenario: 
  + Monthly recurring entry dated at 31st August
  + Since September ends on the 30th, recurring entry is added on the 30 September and `lastRecurred` is stored as 
  30th September
  + `dayOfRecurrence` is used to track the real date of recurrence since the day will be overwritten

### Budgets
Daily, monthly, and yearly spending totals are calculated everytime a ListCommand is received. This is done by
iterating through each of the spendings stored in SpendingList and comparing their dates to the respective daily,
monthly, and yearly dates.

### Deleting an entry
The user deletes an entry by sending a delete command which specifies the index of the income/spending to be deleted. 
The income or spending will be deleted from its corresponding list using its index. 



## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories
Priorities: High (must have) - * * *, Medium (nice to have) - * *, Low (unlikely to have) - *

| Priority | As a ... | I want to ...                                        | So that I can ...                                |
|----------|----------|------------------------------------------------------|--------------------------------------------------|
| ***      | user     | start and close the application                      | use it only when needed                          |
| ***      | user     | add my financial transactions                        | track the flow of my money                       |
| ***      | user     | categorise my entries as income and spendings        | better understand my financials                  |
| ***      | user     | add income and expenditure categories                | see my overall net gain or loss                  |
| ***      | user     | see all my spendings                                 | know what I spent on                             |
| ***      | user     | delete my entries                                    | correct my mistakes                              |
| ***      | user     | have a password to my account                        | protect my account information                   |
| **       | user     | edit my incomes and spendings                        | correct my mistakes                              |
| **       | user     | categorise my expenses                               | see what I spend on                              |
| **       | user     | categorise my incomes                                | see where my savings come from                   |
| **       | user     | read the amount of money left in my allocated budget | gauge how much to spend for the remaining period |
| **       | user     | set expenses and incomes as recurring                | do not need to manually add them each time       |
| **       | Student  | set budgets for each category of expense             | make better financial decisions                  |
| *        | user     | be alerted when I overspend my budget                | try to curb my spendings                         |


## Use cases

### Use case: Add an Entry

**Adding an income entry with optional input for date and tag**

**MSS**

1. User inputs to add income with description, amount, date, and tag.
2. Wiagi adds the income to the income list.
3. If the date is not provided, Wiagi will use the current date.
4. If the tag is not provided, Wiagi will use the default tag (Empty String).
5. Wiagi will display a message to the user that the income has been added.

Use case ends.

**Extensions**
1. User enters invalid input.
   1. If the user inputs invalid formatting, Wiagi will display an error message.
   2. If the user inputs an invalid amount, Wiagi will display an error message.
   3. If the user inputs an invalid date, Wiagi will display an error message.
   
   Use case restarts at step 1.

### Use case: Delete an Entry

**Deleting an income or spending from the list**

**MSS**

1. User requests to list all incomes
2. Wiagi shows a list of all incomes
3. User takes note of the index of the income to delete and requests to delete the entry
by specifying the index
4. Wiagi deletes the entry
  
Use case ends.

**Extensions**
- 1a. The list is empty. 
<br>Use case ends. 
- 3a. The given index is invalid.
  - 3a1. Wiagi displays an error message.
<br>Use case restarts at step 1.

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
