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

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...                                        | So that I can ... |
|----|----|------------------------------------------------------|-------------------|
|v1.0|user| start and close the application                      | use it only when needed |
|v1.0|user| add my financial transactions                        | track the flow of my money |
|v1.0|user| categorise my entries as income and spendings        | better undestand my financials |
|v1.0|user| add income and expenditure categories                | see my overall net gain or loss |
|v1.0|user| have a password to my account                        | protect my account informations |
|v2.0|user| read the amount of money left in my allocated budget | gauge how much to spend for the remaining period |
|v2.0|user| set expenses and incomes as recurring | do not need to manually add them each time |
|v2.0|user| be alerted when I overspend my budget | try to curb my spendings | 
## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
