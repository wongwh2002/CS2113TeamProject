# User Guide
## Introduction

Wiagi is a simple command line application that helps students who are beginning their financial
independence journey by offering a wide range of essential tools and features such as budgeting, saving, 
and investment analysis. 
 
- [Quick start](#quick-start)
- [Features](#features)
  - [Login](#login) 
    - [Creation of new user](#creation-of-new-user)
  - [Adding an entry](#adding-an-entry)
    - [Adding a spending](#adding-a-spending)
    - [Adding an income](#adding-an-income)
  - [Listing all entries](#listing-all-entries)
    - [Listing spendings](#listing-spendings)
    - [Listing incomes](#listing-incomes)
    - [Listing all tags](#listing-all-tags)
    - [Listing all of specific tag](#listing-all-of-specific-tag)
  - [Deleting an entry](#deleting-an-entry)
    - [Deleting an income](#deleting-an-income)
    - [Deleting a spending](#deleting-a-spending)
  - [Getting user instructions](#getting-user-instructions)
  - [Setting a budget](#setting-a-budget)
  - [Editing an entry](#editing-an-entry)
  - [Finding an entry](#finding-an-entry)
  - [Exiting the program](#exiting-the-program)
  - [Saving the data](#saving-the-data)
  - [Editing the data file](#editing-the-data-file)
    - [Format of data storage for password](#format-of-data-storage-for-password)
    - [Format of data storage for income and spending](#format-of-data-storage-for-income-and-spending)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## Quick Start
 
1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `Wiagi` from [here](https://github.com/AY2425S1-CS2113-W14-1/tp/releases/latest).
3. Copy the file to the folder you want to use as the home folder.
4. Open a command terminal, navigate to the folder you have placed the jar file in, 
and use the `java -jar [CS2113-W14-1][WIAGI].jar` command to run the application.
5. You will be prompted to set a password and budget. Enter the password you set to log in. 

## Features
> **Notes about the command format:**  
> 
> - Words in `{$UPPER_CASE}` are the **<ins>compulsory</ins>** parameters. <br>
> e.g., in `add spending {$AMOUNT} {$DESCRIPTION}`, `{$AMOUNT}` and `{$DESCRIPTION}` are parameters which 
> can be used as `add spending 4 dinner`.
>  
> - Words in `[$UPPER_CASE]` are the optional parameters. <br>
> e.g. `add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/]` can be used as 
> `add spending 4 lunch` or `add spending 4 lunch /2024-10-20/`.

### Login
#### Creation of new user:
First time users will be prompted to create a new password. Users are to type in their preferred password.
Subsequent logins will not require this step. <br>
The password will be whitespace-sensitive and case-sensitive.<br>

> <span style="color:#f5220d">TO NOTE</span> <br>
> The program does not provide a forget password feature to recover password as of the current iteration. Please take note of the
> password you entered. Failure to recall password requires a hard reset of the program by exiting the program and deleting the password data file.

Expected display for first time users:
```
	____________________________________________________________
	Hi! You seem to be new, are you ready?!
	Please enter your new account password:
```
To help users manage their finances well, users are also prompted to enter their daily, monthly and yearly budgets.
Users should enter valid amounts for each budget type <br>
- An integer or number with decimals 
- Greater than 0 but smaller than or equals to 100 million when rounded to 2 decimal places. 
  - E.g. 0.005 is rounded to 0.01 is valid but 0.004 is not.<br>

Example valid inputs: <br>
- `50` <br>
- `1500.99` <br>
- `18000` <br>
<br>

Example invalid inputs: <br>
- `$50` <br>
- `1,500` <br>
- `18 000` <br>
- `one thousand` <br>
- `-10` <br>
- `100000000.05` <br>
<br>

Expected display:
```
	Hello! So happy you took this first step of financial management.
	Let's first set your budgets!
	Please enter a daily budget you have in mind:
```
```
	____________________________________________________________
	Next, please enter a monthly budget you have in mind:
```
```
	____________________________________________________________
	Last one! Please enter a yearly budget you have in mind:
```
Expected output after successfully creating new user: <br>
```
    ____________________________________________________________
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
*Note that this is also the startup page for returning existing users.<br> 

#### Existing user:
Upon entering the correct password, user will be able to access their financials. <br>
Expected output for correct password:
```
    ____________________________________________________________
    Login Success!
    ____________________________________________________________
```

Users will be repeatedly prompted for their password if they enter the wrong password <br>
Expected output for wrong password:
```
    ____________________________________________________________
    Incorrect password! Login Failed :<
    ____________________________________________________________
    Please Enter Login Credentials:
```

### Adding an entry
#### Adding a spending:

Adds an entry into user spending list. Entry will be displayed in chronological order. <br>
Run the [`list spendings`](#listing-spendings) command to display the list with the new entry. <br>
Amount entered must be greater than 0 and less than or equals to 10 million when rounded to 2 decimal places. <br>


**Format:** `add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]`
- `{$AMOUNT}`: Numerical value of the spending, up to 2 decimal places will be taken.
  - 0 < amount <= 10 million.
  - Adding of that entry must not result to the total spending to be more than 100 million.
- `{$DESCRIPTION}`: Name of the spending.
  + Must be free of /, *, ~ and \| characters.
- `[/$DATE/]`: Date of the transaction.
  - Must be of YYYY-MM-DD format, eg.`2023-01-21`.
  - If left empty, it would be set to the date of entry.
  - The year of `$DATE` must not be more than 100 years ago.
  - Enclosed in forward slashes.
- `[*$TAG*]`: Label for the entry.
  + Must be free of /, *, ~ and \| characters.
  - Enclosed in asterisks. 
  - Case-insensitive (e.g. `Food` is treated the same as `food`)
- `[~$FREQUENCY~]`: Frequency of recurrence to automate repeated transactions.
  - Enclosed in tilde.
  - Possible options: `daily`, `monthly` and `yearly`

**Example inputs:** <br>
- `add spending 100 telegram premium` <br>
- `add spending 100 telegram premium /2024-10-20/` <br>
- `add spending 100 telegram premium *personal expense*` <br>
- `add spending 100 telegram premium /2024-10-20/ *personal expense*` <br>
- `add spending 100 telegram premium /2023-01-23/ *personal expense* ~monthly~` <br>

**Expected output:**
```
	____________________________________________________________
	Entry successfully added!
	____________________________________________________________
```
If a recurring entry is added with the date before today, user will be prompted to enter whether he/she would like to 
add all the recurring entries between the date of entry and the current date. (Y/N is case-insensitive). <br>

```
	____________________________________________________________
	Entry successfully added!
	Do you want to backlog recurrence entries from 2023-01-23 to 
	2024-11-07 if any? [Y/N]
```
If `Y` is entered, the output will be as follows:
```
	____________________________________________________________
	All entries to recur are added!
	____________________________________________________________
```

If `N` is entered, the output will be as follows:
```
	____________________________________________________________
	Ok! The entry will not be backlogged
	____________________________________________________________
```

Note: Recurring entries will only recur till current date, today, thus if an entry dated to the future is added, no 
recurring entries will be added <br>

#### Adding an income:

Adds an entry into user income list. Entry will be displayed at the latest index. <br>
Run the [`list incomes`](#listing-incomes) command to display the list with the new entry. <br>

**Format:** `add income {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]`
- `{$AMOUNT}`: Numerical value of the income, up to 2 decimal places will be taken.
  - 0 < amount <= 10 million. 
  - Adding of that entry must not result to the total income to be more than 100 million.
- `{$DESCRIPTION}`: Name of the income.
  + Must be free of /, *, ~ and \| characters.
- `[/$DATE/]`: Date of the transaction.
  - Must be of YYYY-MM-DD format, eg.`2023-01-21`.
  - If left empty, it would be set to the date of entry.
  - The year of `$DATE` must not be more than 100 years ago.
  - Enclosed in forward slashes.
- `[*$TAG*]`: Label for the entry.
  + Must be free of /, *, ~ and \| characters.
- `[~$FREQUENCY~]`: Frequency of recurrence to automate repeated transactions.
  - Enclosed in tilde.
  - Possible options: `daily`, `monthly` and `yearly`

**Example input:** <br>
- `add income 10000 commission` <br>
- `add income 10000 commission /2024-01-01/` <br>
- `add income 10000 commission *bonus*` <br>
- `add income 10000 commission /2024-01-01/ *bonus*` <br>
- `add income 10000 commission /2023-01-23/ *bonus* ~monthly~` <br>

**Expected output:**
```
	____________________________________________________________
	Entry successfully added!
	____________________________________________________________
```
If a recurring entry is added with the date before today, user will be prompted to enter whether he/she would like to
add all the recurring entries between the date of entry and the current date. (Y/N is case-insensitive). <br>

```
	____________________________________________________________
	Entry successfully added!
	Do you want to backlog recurrence entries from 2023-01-23 to 2024-11-07 if 
	any? [Y/N]
```
If `Y` is entered, the output will be as follows:
```
	____________________________________________________________
	All entries to recur are added!
	____________________________________________________________
```

If `N` is entered, the output will be as follows:
```
	____________________________________________________________
	Ok! The entry will not be backlogged
	____________________________________________________________
```

If user overspends after all spending (ie. new entry and recurring entries) are added, a alert message will be displayed.
The following is an example output, your output may differ in values and lines:
```
    !!! You have overspent your daily by: 1000 !!!
    !!! You have overspent your monthly by: 1000 !!!
    !!! You have overspent your yearly by: 1000 !!!
```


Note: Recurring entries will only recur till current date, today, thus if an entry dated to the future is added, no
recurring entries will be added. <br>

### Listing all entries:

Lists all the entries in the user's spending or income list. <br>
**Format:** `list`

**Example input:** <br>
`list`

**Example output:**
```
	____________________________________________________________
	Spendings
	1. macdonalds - 10 - 2024-10-10 - Tag: food
	2. techno - 10 - 2024-10-17 - Tag: food
	3. flights - 10 - 2024-11-01 - Tag: travel
	4. girlfriends - 10 - 2024-11-17 - Tag: personal
	Total spendings: 40
	Incomes
	1. dividends - 10 - 2024-10-17 - Tag: investment
	2. mcd - 100 - 2024-11-11 - Tag: personal
	Total incomes: 110
	____________________________________________________________
```
#### Listing spendings:

Lists entries in the user's spending list.

**Format:** `list spendings`

The user will then be prompted to select a time range from the following options:
1. All
2. This week
3. Last week and this week
4. This month

Only entries that are dated within the selected time range will be displayed. <br> 

For instance, if the command is run on 15 November 2024 (Friday),
- If the user chooses option 1 (all), all spending entries will be shown.
- If the user chooses option 2 (this week), all spending entries dated between 11 November and 17 November 2024 
(inclusive) will be shown.
- If the user chooses option 3 (last week and this week), all spending entries dated between 4 November and 17
November 2024 (inclusive) will be shown.
- If the user chooses option 4 (this month), all spending entries dated between 1 November and 30 November 2024 
(inclusive) will be shown.

If option 1 (all) is chosen, the user will then be asked if all spending statistics should be displayed 
(Y/N is case-insensitive).

**Example Input and Output**

**Input:** `list spendings`
```
	____________________________________________________________
	List spending entries for:
	[1] All
	[2] This week
	[3] Last week and this week
	[4] This month
```

- **Input:** `1`
```
    ____________________________________________________________
    List all statistics? [Y/N]:
```
- - **Input:** `y`

```
    ____________________________________________________________
    Spendings
    1. macdonalds - 10 - 2024-10-10 - food
    2. techno - 10 - 2024-10-17 - food
    3. flights - 10 - 2024-11-01 - travel
    4. girlfriends - 10 - 2024-11-17 - personal
    Total spendings: 40
        Daily spendings: 0
        Daily Budget: 1
        Daily budget left: 0
        Monthly spendings: 40
        Monthly Budget: 1
        Monthly budget left: -39
        Yearly spendings: 40
        Yearly Budget: 1
        Yearly budget left: -39
    ____________________________________________________________
```
- - **Input:** `n`
```
    ____________________________________________________________
    Spendings
    1. macdonalds - 10 - 2024-10-10 - food
    2. techno - 10 - 2024-10-17 - food
    3. flights - 10 - 2024-11-01 - travel
    4. girlfriends - 10 - 2024-11-17 - personal
    Total spendings: 40
    ____________________________________________________________
```

- **Input:** `4` (Date of command is 2024-11-18)
```
	____________________________________________________________
	Showing spending entries from 2024-11-01 to 2024-11-30
	3. flights - 10 - 2024-11-01 - Tag: travel
	4. girlfriends - 10 - 2024-11-17 - Tag: personal
	Total: 20
	____________________________________________________________
```

- **Input:** `2` (Date of command is 2024-11-18)
```
	____________________________________________________________
	Showing spending entries from 2024-11-18 to 2024-11-24
	No entries within selected time range
	Total: 0
	____________________________________________________________
```

#### Listing incomes:

Lists entries in the user's income list.

**Format:** `list incomes`

The user will then be prompted to select a time range from the following options:
1. All
2. This week
3. Last week and this week
4. This month

Only entries that are within the time range will be displayed. The time range system is the same as that of
[listing spendings](#listing-spendings).

**Example Input and Output**

**Input:** `list incomes`
```
	____________________________________________________________
	List income entries for:
	[1] All
	[2] This week
	[3] Last week and this week
	[4] This month 
```
- **Input:** `1`
  ```
      ____________________________________________________________
      Incomes
      1. dividends - 10 - 2024-10-17 - Tag: investment
      2. mcd - 100 - 2024-11-11 - Tag: personal
      Total incomes: 110
      ____________________________________________________________
  ```

- **Input:** `2` (Date of command is 2024-11-18)
  ```
      ____________________________________________________________
      Showing income entries from 2024-11-18 to 2024-11-24
      No entries within selected time range
      Total: 0
      ____________________________________________________________
  ```
  
- **Input:** `4` (Date of command is 2024-11-18)
  ```
      ____________________________________________________________
      Showing income entries from 2024-11-01 to 2024-11-30
      2. mcd - 100 - 2024-11-11 - Tag: personal
      Total: 100
      ____________________________________________________________
  ```

#### Listing all tags:

Lists all the tags in the user's list.

**Format:** `list tags`

**Example input:** <br>
`list tags`

**Example output:**
```
	____________________________________________________________
	Tags
	1. FastFood
	2. food
	3. investment
	4. personal
	5. travel
	____________________________________________________________
```

#### Listing all of specific tag:

Lists all the entries in the user's list with the specified tag.

**Format:** `list tags {$TAGNAME}`

**Example input:** <br>
`list tags food`

**Example output:**
```
	____________________________________________________________
	Tag: food
	Spendings
	1. techno - 10 - 2024-10-17 - food
	4. macdonalds - 10 - 2024-10-10 - food
	____________________________________________________________
```

### Deleting an entry
#### Deleting an income:
Deletes the specified income from the list. 
The income to delete is specified by its index.<br>
Run the [`list incomes`](#listing-incomes) command to check the index of the income.

**Format:** `delete income {$INDEX}`

**Example input:** <br>
`delete income 1`

**Example output:**
```
    ____________________________________________________________
        Successfully deleted!
    ____________________________________________________________
```

#### Deleting a spending:
Deletes the specified spending from the list.
The spending to delete is specified by its index.<br>
Run the [`list spendings`](#listing-spendings) command to check the index of the spending.

**Format:** `delete spending {$INDEX}`

**Example input:** <br>
`delete spending 1`

**Example output:**
```
    ____________________________________________________________
    Successfully deleted!
    ____________________________________________________________
```

### Getting user instructions

The `help` command allows the user to see a quick summary of the commands.

**Format:** `help`

**Example input:**<br>
`help`

**Example output**:
```
Notes about the command format:
Words in {$UPPER_CASE} are the parameters to be supplied by the user.
e.g., in add spending {$AMOUNT} {$DESCRIPTION}, {$AMOUNT} and {$DESCRIPTION}
are parameters which can be used as add spending 4 dinner.
Items in square brackets are optional.
e.g. add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] can be used as
add spending 4 lunch or add spending 4 lunch /2024-10-20/.

Adding Entries:
	add income {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]
	add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]
	e.g., add income 5000 Salary /2024-03-15/ *work* ~monthly~
	e.g., add spending 50 Lunch /2024-03-15/ *food*

Listing Entries:
	list - shows all entries
	list incomes - shows all income entries
	list spendings - shows all spending entries
	list tags {$TAG} - shows entries with specific tag

Editing Entries:
	edit {$CATEGORY} {$INDEX} {$FIELD} {$NEW_VALUE}
	-note that {$FIELD} only works for amount, description, date and tags
	e.g., edit spending 1 amount 100
	e.g., edit income 2 description Bonus

Deleting Entries:
	delete {$CATEGORY} {$INDEX}
	e.g., delete spending 1

Setting Budget:
	budget {$PERIOD} {$AMOUNT}
	e.g., budget daily 50
	e.g., budget monthly 1500

Other Commands:
	help - shows this help message
	bye - exits the application
```

### Setting a Budget

The `budget` command allows you to set a daily, monthly, or yearly budget for your spendings.
Amount entered must be greater than 0 when rounded to 2 decimal places. <br>

**Format**: `budget {$PERIOD} {$AMOUNT}`

- `{$PERIOD}`: Specifies the period for the budget. It can be `daily`, `monthly`, or `yearly`.
- `{$AMOUNT}`: The budget amount to be set.

The input criteria for the amount is the same as the amount entered for [creation of new user](#creation-of-new-user).

**Example input:**<br>
`budget daily 50`

**Example output**:
```
    ____________________________________________________________
    Successfully set daily budget of: 50
    ____________________________________________________________
```

### Editing an Entry

The `edit` command allows you to edit the amount, description, or date of an existing income or spending entry. <br>
Amount entered, if applicable, must be greater than 0 when rounded to 2 decimal places.

**Format:** `edit {$CATEGORY} {$INDEX} {$FIELD} {$NEW_VALUE}`

- `{$CATEGORY}`: Specifies the category of entry to be edited. It can be `spending` or `income`.
- `{$INDEX}`: The index of the entry to be edited (1-based index).
- `{$FIELD}`: The field to be edited. It can be `amount`, `description`, `tag` or `date`. Editing the frequency of a recurring entry is not allowed.
- `{$NEW_VALUE}`: The new value to be set for the specified field.
  - Note: There are restrictions for the new value in these fields:
    + description, tag: free of /, *, ~ and \| characters.
    - amount: 0 < amount <= 10 million, while total <= 100 million.
    - date: YYYY-MM-DD format, eg.`2023-01-21`, YYYY must not be more than 100 years ago.
  
**Example input:**<br>
`edit spending 1 amount 100` <br>
`edit spending 1 description macdonalds` <br>
`edit spending 1 tag food` <br>
`edit spending 1 date 2024-10-10` <br>

**Example output**:
```
    ____________________________________________________________
    Edit Successful!
    ____________________________________________________________
```

Note: Editing an entry with recurrence set does not trigger backlogging

### Finding an Entry

The `find` command allows you to find entries within a range of amount or date. 
It also allows you to find entries with a specific keyword in the description.

**Format:** `find {$CATEGORY} {$FIELD} {$FIND_VALUE} [to $ANOTHER_FIND_VALUE]`

- `{$CATEGORY}`: Specifies the category of entry to be edited. It can be `spending` or `income`.
- `{$FIELD}`: The field to be edited. It can be `amount`, `description`, or `date`.
- `{$FIND_VALUE}`: The value to find for the specified field.
- `[to $ANOTHER_FIND_VALUE]`: The upper limit of the search result.
  - Note: Range search is only available for the `amount` and `date` fields.
    - It will be seen as part of the description keyword for the `description` field
  - Note: There are restrictions for the find values in these fields:
    - amount: positive numerical value.
    - date: YYYY-MM-DD format, eg.`2023-01-21`.

**Example input:**<br>
- `find income amount 100` <br>
- `find spending description macdonalds` <br>
- `find spending date 2024-10-10 to 2024-12-12` <br>

**Example output**:
```
    ____________________________________________________________
    No entries found match the criteria.
    ____________________________________________________________
```
```
    ____________________________________________________________
    Here are the matching results:
    1. macdonalds - 10 - 2024-10-10
    ____________________________________________________________
```

### Exiting the program

The `bye` command allows you to exit the program safely, as it will store all changes made.

**Format:** `bye`

**Example input:**<br>
`bye`

**Example output:**
```
	____________________________________________________________
	Bye. Hope to see you again soon!
	____________________________________________________________
```

### Saving the data
All data previously inputted into the programme will be automatically saved upon the user exiting via the `bye` command.
There is no need to save manually.

### Editing the data file
> <span style="color:#f5220d">WARNING</span> <br>
> This section is dedicated to advanced users who are confident in updating the data file manually. Failure 
> to do so correctly can lead to data corruption and having possibly all previous information wiped out.
> Entry numbers detected as corrupted will be displayed to user upon start up. The user may choose to copy out the lines, 
> rectify, and manually input them back after closing the program properly.<br>

User data is stored into 3 text files, namely
- password.txt: `[JARFILE LOCATION]/password.txt`, stores the user password
- spendings.txt: `[JARFILE LOCATION]/spendings.txt`, stores all the user spending data
- incomes.txt: `[JARFILE LOCATION]/incomes.txt`, stores all the user income data

#### Format of data storage for password:
For security purposes the method of storage will not be discussed. <span style="color:#f5220d">DO NOT</span> 
alter this file, simply delete the file if you have forgotten you password and create a new password upon being 
prompt when start up. Note that this will cause a hard reset to the application and erase all data.

#### Format of data storage for income and spending:
Data are stored with `|` used as delimiter. Each line in the text file represents one entry. <br>
Format: 
`[$AMOUNT]|[$DESCRIPTION]|[$DATE_OF_ENTRY]|[TAG_NAME]|[RECURRENCE_FREQUENCY]|[LAST_RECURRENCE]|[DAY_OF_RECURRENCE]`
<br>
<br>
For spending.txt, the first line of entry stores the budgets of the user.<br>
Format: `[$DAILY_BUDGET]|[$MONTLY_BUDGET]|[$YEARLY_BUDGET]` <br>
<br>
Important data representation to note:
+ `[$DESCRIPTION]`/`[TAG_NAME]`: Must be free of /, *, ~ and \| characters.
- `[$AMOUNT]`/`[$DAILY_BUDGET]`/`[$MONTHLY_BUDGET]`/`[$YEARLY_BUDGET]`: In 2 decimal places.
- `[$DATE_OF_ENTRY]`: In the format of YYYY-MM-DD, YYYY must not be more than 100 years ago.
- `[$RECURRENCE_FREQUENCY]`: In the format of `NONE`/`DAILY`/`MONTHLY`/`YEARLY`.
- `[$DAY_OF_RECURRENCE]`: To match the day stored in `[$DATE_OF_ENTRY]`.

We recommend not to edit `[$LAST_RECURRENCE]`. Adding or editing entries with recurrence, `[$LAST_RECURRENCE]`
should match `[$DATE_OF_ENTRY]` and last possible recurred date before current date respectively, "null" otherwise.

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Simply transfer `incomes.txt`, `spendings.txt` and `password.txt` files to the folder that the program 
is at.

**Q**: Why are the indices of the entries not in consecutive order when I list all entries of a particular tag 
or when I list entries in a time range?

**A**: Each index reflects the actual index of the item in the entire list so that you can edit or delete the 
entry easily.

**Q**: What happens if I edit the date of a recurring entry to an earlier date?

**A**: The date of the entry will be changed, but no additional entries will be created between the new date and the 
current date.

**Q**: What happens if I add a recurring entry with an earlier date?

**A**: You will have the option to select whether you would like to add the additional entries between the date of entry and the current date.

**Q**: What happens if I forget my password?

**A**: Delete the `password.txt` file in the folder where the program is located. Upon starting the program, you will be prompted to create a new password.
Users have to press Ctrl+C (or Command+C for Mac users) to exit the program before they are able to delete the file.
Do note that the `spending.txt` and `incomes.txt` files will be reset and all data will be lost.
If you have a backup of the `spending.txt` and `incomes.txt` files, you can replace the new files with the backup files to restore your data.

## Command Summary
<table>
    <thead>
        <tr>
            <th colspan="2">Command</th>
            <th>Format</th>
            <th>Examples</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="2">Adding</td>
            <td>Income</td>
            <td>add income {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]</td>
            <td>add income 10000 commission /2023-01-23/ *bonus* ~monthly~</td>
        </tr>
        <tr>
            <td>Spending</td>
            <td>add spending {$AMOUNT} {$DESCRIPTION} [/$DATE/] [*$TAG*] [~$FREQUENCY~]</td>
            <td>add spending 100 telegram premium /2023-01-23/ *personal expense* ~monthly~</td>
        </tr>
        <tr>
            <td rowspan="4">Listing entries</td>
            <td>All spendings</td>
            <td>list spendings</td>
            <td>list spendings</td>
        </tr>
        <tr>
            <td>All incomes</td>
            <td>list incomes</td>
            <td>list spendings</td>
        </tr>
        <tr>
            <td>All tags</td>
            <td>list tags</td>
            <td>list tags</td>
        </tr>
        <tr>
            <td>All of a specific tag</td>
            <td>list tags {$TAGNAME}</td>
            <td>list tags food</td>
        </tr>
        <tr>
            <td rowspan="2">Deleting entries</td>
            <td>Income</td>
            <td>delete income {$INDEX}</td>
            <td>delete income 1</td>
        </tr>
        <tr>
            <td>Spending</td>
            <td>delete spending {$INDEX}</td>
            <td>delete spending 1</td>
        </tr>
        <tr>
            <td rowspan="3">Setting budget</td>
            <td>Daily</td>
            <td>budget daily {$AMOUNT}</td>
            <td>budget daily 30</td>
        </tr>
        <tr>
            <td>Monthly</td>
            <td>budget monthly {$AMOUNT}</td>
            <td>budget monthly 1000</td>
        </tr>
        <tr>
            <td>Yearly</td>
            <td>budget yearly {$AMOUNT}</td>
            <td>budget yearly 12000</td>
        </tr>
        <tr>
            <td rowspan="2">Finding entries</td>
            <td>Income</td>
            <td>find income {$FIELD} {$FIND_VALUE} [to $ANOTHER_FIND_VALUE]</td>
            <td>find income description salary</td>
        </tr>
        <tr>
            <td>Spending</td>
            <td>find spending {$FIELD} {$FIND_VALUE} [to $ANOTHER_FIND_VALUE]</td>
            <td>find spending amount 10 to 100</td>
        </tr>
        <tr>
            <td rowspan="1" colspan="2">Get all Commands</td>
            <td>help</td>
            <td>help</td>
        </tr>
        <tr>
            <td rowspan="1" colspan="2">Exit Program</td>
            <td>bye</td>
            <td>bye</td>
        </tr>
    </tbody>
</table>