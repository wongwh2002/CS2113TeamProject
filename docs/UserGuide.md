# User Guide

## Introduction

Wiagi is a simple command line application that helps students who are beginning their financial
independence journey by offering a wide range of essential tools and features such as budgeting, saving, 
and investment analysis. 
 
- [Quick start](#Quick-Start)
- [Features](#Features)
  - [Adding an entry](#adding-an-entry)
    - [Adding a spending: `add {$SPENDING}`](#adding-a-spending-add-spending)
    - [Adding an income: `add {$INCOME}`](#adding-an-income-add-income)
  - [Listing all entries: `list`](#listing-all-entries-list)
    - [Listing all spendings: `list spendings`](#listing-all-spendings-list-spendings)
    - [Listing all incomes: `list incomes`](#listing-all-incomes-list-incomes)
  - [Deleting an entry](#deleting-an-entry)
    - [Deleting an income: `delete income {$INDEX}`](#deleting-an-income-delete-income-index)
    - [Deleting a spending: `delete spending {$INDEX}`](#deleting-a-spending-delete-spending-index)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## Quick Start
 
1. Ensure that you have Java 17 or above installed.
2. Down the latest version of `Wiagi` from [here](https://github.com/AY2425S1-CS2113-W14-1/tp/releases/latest).
3. Copy the file to the folder you want to use as the home folder.
4. Open a command terminal, navigate to the folder you have placed the jar file in, 
and use the `java -jar Wiagi.java.jar` command to run the application.

## Features
> **Notes about the command format:**  
> 
> - Words in `{$UPPER_CASE}` are the parameters to be supplied by the user. <br>
> e.g., in `add spending {$SPENDING} {$DESCRIPTION}`, `{$SPENDING}` and `{$DESCRIPTION}` are parameters which 
> can be used as `add spending 4 dinner`.
>  
> - Items in square brackets are optional. <br>
> e.g. `add lunch {$SPENDING} {$DESCRIPTION} [/$DATE]` can be used as 
> `add spending 4 lunch` or `add spending 4 lunch /2024-10-20`.
 
### Adding an entry
#### Adding a spending: `add {$SPENDING}`
// Description

Format: `todo n/TODO_NAME d/DEADLINE`
 
Example input: <br> 
`input`

```
output
```

#### Adding an income: `add {$INCOME}`
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

```
output
```

### Listing all entries: `list`
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

```
output
```
#### Listing all spendings: `list spendings`
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

```
output
```

#### Listing all incomes: `list incomes`
// Description

Format: `todo n/TODO_NAME d/DEADLINE`

Example input: <br>
`input`

```
output
```

### Deleting an entry
#### Deleting an income: `delete income {$INDEX}`
Deletes the specified income from the list. 
The income to delete is specified by its index.<br>
Run the [`list incomes`](#listing-all-incomes-list-incomes) command to check the index of the income.

Format: `delete income {$INDEX}`

Example input: <br>
`delete income 1`

```
    ____________________________________________________________
        Successfully deleted!
    ____________________________________________________________
```

#### Deleting a spending: `delete spending {$INDEX}`
Deletes the specified spending from the list.
The spending to delete is specified by its index.<br>
Run the [`list spendings`](#listing-all-spendings-list-spendings) command to check the index of the spending.

Format: `delete spending {$INDEX}`

Example input: <br>
`delete spending 1`

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
