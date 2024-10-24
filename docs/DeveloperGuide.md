# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}

## Components Interaction
### Storage
To load data from previous session: 
Within Wiagi constructor, Storage class is constructed, which will load and initialise incomes, spendings and 
password by de-serialising the text at their distinct file paths. Wiagi will then initialise it incomes and spendings 
based on the member in the Storage class.
![storageLoad.png](storageLoad.png)

To save data for current session:
After the command bye is sent by the user, incomes and spendings will be serialised and overwrite texts in 
their distinct file paths. 
![storageSave.png](storageSave.png)