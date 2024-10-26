# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

[Original Source](https://github.com/nus-cs2113-AY2425S1/tp)

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}
### Storage
To load data from previous session:
Within Wiagi constructor, Storage class is constructed, which will load and initialise incomes, spendings and
password by de-serialising the text at their distinct file paths. Wiagi will then initialise it incomes and spendings
based on the member in the Storage class.
![storageLoad.png](./Diagrams/storageLoad.png)

To save data for current session:
After the command bye is sent by the user, incomes and spendings will be serialised and overwrite texts in
their distinct file paths.
![storageSave.png](./Diagrams/storageSave.png)

### Budgets
Daily, monthly, and yearly spending totals are calculated everytime a ListCommand is received. This is done by
iterating through each of the spendings stored in SpendingList and comparing their dates to the respective daily,
monthly, and yearly dates.


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
| ***      | user     | categorise my entries as income and spendings        | better undestand my financials                   |
| ***      | user     | add income and expenditure categories                | see my overall net gain or loss                  |
| ***      | user     | see all my spendings                                 | know what I spent on                             |
| ***      | user     | delete my entries                                    | correct my mistakes                              |
| ***      | user     | have a password to my account                        | protect my account informations                  |
| **       | user     | edit my incomes and spendings                        | correct my mistakes                              |
| **       | user     | categorise my expenses                               | see what I spend on                              |
| **       | user     | categorise my incomes                                | see where my savings come from                   |
| **       | user     | read the amount of money left in my allocated budget | gauge how much to spend for the remaining period |
| **       | user     | set expenses and incomes as recurring                | do not need to manually add them each time       |
| **       | Student  | set budgets for each category of expense             | make better financial decisions                  |
| *        | user     | be alerted when I overspend my budget                | try to curb my spendings                         |


## Use cases

### Use case: Add an Entry

** Adding an income entry with optional input for date and tag **

** MSS **

1. User inputs to add income with description, amount, date, and tag.
2. Wiagi adds the income to the income list.
3. If the date is not provided, Wiagi will use the current date.
4. If the tag is not provided, Wiagi will use the default tag (Empty String).
5. Wiagi will display a message to the user that the income has been added.

Use case ends.

** Extensions **
1. User enters invalid input.
   1. If the user inputs invalid formatting, Wiagi will display an error message.
   2. If the user inputs an invalid amount, Wiagi will display an error message.
   3. If the user inputs an invalid date, Wiagi will display an error message.
   
   Use case restarts at step 1.

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
