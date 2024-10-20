# User Guide

## Introduction

Wiagi is a simple command line application that helps students who are beginning their financial
independence journey by offering a wide range of essential tools and features such as budgeting, saving, 
and investment analysis. 
 
- [Quick start](#Quick-Start)
- [Features](#Features)
  - [Adding an entry](#adding-an-entry)
    - [Adding a spending](#adding-a-spending)
    - [Adding an income](#adding-an-income)
  - [Listing all entries](#listing-all-entries)
    - [Listing all spendings](#listing-all-spendings)
    - [Listing all incomes](#listing-all-incomes)
  - [Deleting an entry](#deleting-an-entry)
    - [Deleting an income](#deleting-an-income)
    - [Deleting a spending](#deleting-a-spending)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## Quick Start
 
1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `Wiagi` from [here](https://github.com/AY2425S1-CS2113-W14-1/tp/releases/latest).
3. Copy the file to the folder you want to use as the home folder.
4. Open a command terminal, navigate to the folder you have placed the jar file in, 
and use the `java -jar Wiagi.java.jar` command to run the application.
5. You will be prompted to set a password. Set a password and enter the password to log in. 

## Features
> **Notes about the command format:**  
> 
> - Words in `{$UPPER_CASE}` are the parameters to be supplied by the user. <br>
> e.g., in `add spending {$AMOUNT} {$DESCRIPTION}`, `{$AMOUNT}` and `{$DESCRIPTION}` are parameters which 
> can be used as `add spending 4 dinner`.
>  
> - Items in square brackets are optional. <br>
> e.g. `add spending {$AMOUNT} {$DESCRIPTION} [/$DATE]` can be used as 
> `add spending 4 lunch` or `add spending 4 lunch /2024-10-20`.
 
### Adding an entry
#### Adding a spending:
// Description

Format: `todo n/TODO_NAME d/DEADLINE`
 
Example input: <br> 
`input`

Example output:
```
output
```

#### Adding an income:
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

Example output:
```
output
```

### Listing all entries:
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

Example output:
```
output
```
#### Listing all spendings:
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

Example output:
```
output
```

#### Listing all incomes:
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

Example output:
```
output
```

### Deleting an entry
#### Deleting an income:
Deletes the specified income from the list. 
The income to delete is specified by its index.<br>
Run the [`list incomes`](#listing-all-incomes) command to check the index of the income.

Format: `delete income {$INDEX}`

Example input: <br>
`delete income 1`

Example output:
```
    ____________________________________________________________
        Successfully deleted!
    ____________________________________________________________
```

#### Deleting a spending:
Deletes the specified spending from the list.
The spending to delete is specified by its index.<br>
Run the [`list spendings`](#listing-all-spendings) command to check the index of the spending.

Format: `delete spending {$INDEX}`

Example input: <br>
`delete spending 1`

Example output:
```
    ____________________________________________________________
    Successfully deleted!
    ____________________________________________________________
```
## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
