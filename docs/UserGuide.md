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
  - [Setting a budget](#setting-a-budget)
  - [Editing an entry](#editing-an-entry)
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

### Login
#### Creation of new user:
First time users will be prompted to create a new password. Users are to type in his/her preferred password.
Subsequent logins will not require this step. <br>
Expected display for first time users:
```
	____________________________________________________________
	Hi! You seem to be new, are you ready?!
	Please enter your new account password:
```
Expected output after successfully creating password: <br>
Note that this is also the startup page for returning users
```
	____________________________________________________________
	Hello from
	__        __  ___      /\       ____   ___
	\ \      / / |_ _|    /  \     / ___| |_ _|
	 \ \ /\ / /   | |    / /\ \   | |  _   | |
	  \ V  V /    | |   / ____ \  | |_| |  | |
	   \_/\_/    |___| /_/    \_\  \____| |___|
	____________________________________________________________
	____________________________________________________________
	Please Enter Login Credentials:
```
### Adding an entry
#### Adding a spending:
// Description
Adds an entry into user spending list. Entry will be displayed at the latest index. <br>
Run the [`list spendings`](#listing-all-spendings) command to display the list with the new entry.

Format: `todo n/TODO_NAME d/DEADLINE`
Format: `add spending {$AMOUNT} {$DESCRIPTION} [/$DATE]`

Example input: <br>
`input`
`add spending 100 telegram premium /2024-10-20`

Example output:
```
output
	____________________________________________________________
	Entry successfully added!
	____________________________________________________________
```

#### Adding an income:
// Description
Adds an entry into user income list. Entry will be displayed at the latest index. <br>
Run the [`list incomes`](#listing-all-incomes) command to display the list with the new entry.

Format: `todo n/TODO_NAME d/DEADLINE`
Format: `add income {$AMOUNT} {$DESCRIPTION} [/$DATE]`

Example input: <br>
`input`
`add income 10000 commission /2024-01-01`

Example output:
```
output
	____________________________________________________________
	Entry successfully added!
	____________________________________________________________
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

### Setting a Budget

The `budget` command allows you to set a daily, monthly, or yearly budget for your spendings.

**Format**: `budget {$PERIOD} {$AMOUNT}`

- `{$PERIOD}`: Specifies the period for the budget. It can be `daily`, `monthly`, or `yearly`.
- `{$AMOUNT}`: The budget amount to be set.

**Example input**:<br>
`
budget daily 50
`

**Example output**:
```
    ____________________________________________________________
    Successfully set daily budget of: 50
    ____________________________________________________________
```

### Editing an Entry

The `edit` command allows you to edit the amount, description, or date of an existing income or spending entry.

**Format**: `edit {$TYPE} {$INDEX} {$FIELD} {$NEW_VALUE}`

- `{$TYPE}`: Specifies the type of entry to be edited. It can be `spending` or `income`.
- `{$INDEX}`: The index of the entry to be edited (1-based index).
- `{$FIELD}`: The field to be edited. It can be `amount`, `description`, or `date`.
- `{$NEW_VALUE}`: The new value to be set for the specified field.

**Example input**:<br>
`edit spending 1 amount 100`

**Example output**:
```
    ____________________________________________________________
    Edit Successful!
    ____________________________________________________________
```



## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
