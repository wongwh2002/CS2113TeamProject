# Nigel Yeo Tong Wei - Project Portfolio Page

## Overview
WIAGI aims to help students who are beginning their financial independence journey and want to gain control over their 
budgeting, saving, and investing habits. We provide students with a simple-to-use application that offers a wide range 
of essential tools and features such as budgeting, saving, and investment analysis, helping them to make better 
financial decisions and be in control of their finances.

### Summary of Contributions

+ **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=NigelYeoTW&tabRepo=AY2425S1-CS2113-W14-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false) 
+ **New Feature**: Added login abilities for the user
  + What it does: Allows user to have a password for the application, be able to sign in everytime the program starts up
  + Justification: This feature allows the user to feel more secure and have a higher sense of attachment with the 
  application due to having a membership as well as feel like the app is more personalised for him/her.
+ **New Feature**: Adding recurrence ability to entries
  + What it does: Allows user to be able to set certain entries as recurring entries
  + Justification: This feature helps to automate the entries for the user for recurring entries, such as monthly
  income or daily spending, this increase the efficiency of the user when using the application as they only need to
  add one off entries.
  + Highlights: This implementation builds on the current existing add command and requires changes to it, especially
  for error checking due to changes in parameters. Since this parameter is optional, comprehensive error checking is
  also required. Due to vary days in dates (eg. February), there needs to be a check as well to ensure entries are
  recurred correctly.
+ **New feature**: Adding backlogging for new recurring events date to the past
  + What it does: It immediately adds the recurring entries for the user if an old recurring entry is added
  + Justification: The user may have forgotten to enter an old entry or did not realise a recurring spending that has
  been happening for some time. By allowing backlogging, the list of missed out entries can be added efficiently
  + Highlights: Builds on the existing methods for recurrence and required changes to the current implementation to
  allow backlogging to occur
+ **New feature**: Initialising of budgets for users
  + What it does: Initialise the budgets of the users upon program start up or when budget in saved data file is corrupted
  + Justification: The program is supposed to help the user track his/her expenses and budgets should be set from the 
  start
  + Highlights: Due to the limitations of the amounts set for the program (eg. 100 million max total in account), 
  extensive checking is required to ensure the budgets set for daily, monthly and yearly makes sense
+ **New feature**: Storing and loading entries of data files, checking of corruption of budget
  + What it does: Load and save the data of the user into text files
  + Justification: The user would want a program that can save his entries for continuous usage
  + Highlights: There is extensive checking required to check for user corrupting data for budgets since there could be
  many possible outcomes, there is also a need for logic to re-initialise budgets in the case of corruption
+ **Contribution to UG**
  + Login section
  + Saving and editing data file
  + Parts of command summary 
+ **Contribution to DG**
  + Recurrence class and Recurrence enumeration
  + Updating recurrence under program start up
  + Backlogging recurrence under add command
  + EntryType Class implementation
  + SpendingList Class implementation
  + IncomeList Class implementation
  + Parts of the appendix (NFR, user stories, manual testing, use cases, future implementation, target user, value proposition)
+ **Contribution to team based task**
  + Cleaning and refactoring of code
  + Maintaining issue tracker
  + Catching bugs in the application and adding to issue tracker
  + Help facilitate team meetings and splitting of tasks
  + Frequently reviewing pull requests
+ **Review/mentoring contributions**: 
[PR comments link](https://nus-cs2113-ay2425s1.github.io/dashboards/contents/tp-comments.html)
  + Check under @NigelYeoTW
+ **Contributions beyond the team project**
  + Reviewed PR extensively for other team during DG Review: 
  [Link](https://github.com/nus-cs2113-AY2425S1/tp/pull/6/files/c2df90cf34bd8aa55eb9bcb14a84a4ed3a485ae1)