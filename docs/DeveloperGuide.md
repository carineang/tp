---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Notarius is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org/).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts such as `CommandBox`, `ResultDisplay`, `PersonListPanel`, 
`PersonDetailPanel`, `StatusBarFooter` and `HelpWindow`. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### List feature

The `list` command enables users to view all existing contacts from Notarius.

<p align="center">
  <img src="images/ListCommandSequenceDiagram.png" alt="Ui" />
</p>

#### Implementation

1. The user inputs the command to list all contacts.
2. A `LogicManager` object invokes the `execute` method of a `ListCommand` object.
3. The `execute` method of the `ListCommand` object invokes the `updateFilteredPersonList` and
`commitAddressBook` method of its `Model` to update and show all contacts.
4. The `execute` method of the `ListCommand` object returns a `CommandResult` object which stores the data regarding
the completion of the `list` command.

### Help feature

The `help` command allows users to view general application usage instructions or specific details about a command.

<p align="center">
  <img src="images/HelpCommandSequenceDiagram.png" alt="Ui" />
</p>

#### Implementation

1. The user inputs the command to request help, either as help for general help or help COMMAND_NAME for details on a specific command. 
2. A `HelpCommandParser` object invokes its `parse` method, which parses the user input:
   1. If no argument is provided, it returns a `HelpCommand` object for general help. 
   2. If a valid command name is provided, it returns a `HelpCommand` object with that command name. 
3. A `LogicManager` object invokes the `execute` method of the `HelpCommand` object. 
4. The execute method of the HelpCommand object checks if a command name was specified:
   1. If no command name is provided, it returns a `CommandResult` containing general help instructions. 
   2. If a valid command name is provided, it retrieves the corresponding help message from a predefined command-help mapping (`COMMAND_HELP`). 
   3. If an invalid command name is provided, it returns an error message stating that the command is unknown. 
5. The `execute` method of the `HelpCommand` object returns a `CommandResult` object that stores the help message or error message. 
6. The application displays the help message to the user, either in a popup window (for general help) or in the main interface (for specific commands).

### Clear feature

The `clear` command enables users to remove all existing contacts from Notarius.

<p align="center">
  <img src="images/ClearCommandSequenceDiagram.png" alt="Ui" />
</p>

#### Implementation

1. The user inputs the command to clear all contacts.
2. A `LogicManager` object invokes the `execute` method of a `ClearCommand` object.
3. The `execute` method of the `ClearCommand` object invokes the `setAddressBook` and `commitAddressBook` method 
of its `Model` argument with a new `AddressBook` object which contains an empty `UniquePersonList` property.
4. The `execute` method of the `ClearCommand` object returns a `CommandResult` object which stores the data regarding 
the completion of the `clear` command.

### Sort feature

The `sort` command enables users to sort contacts in Notarius by prefix in lexicographical order.

<p align="center">
  <img src="images/SortCommandSequenceDiagram.png" alt="Ui" />
</p>

#### Implementation

1. The user inputs the command to sort contacts with the specified prefix.
2. A `SortCommandParser` object invokes its `parse` method which parses the user input.
3. The `SortCommand` object is created with the parsed prefix.
4. A `LogicManager` object invokes the `execute` method of the `SortCommand` object.
5. The `execute` method of the `SortCommand` object invokes the `updateSortedPersonList`, 
`updateSortedFilteredPersonList` and `commitAddressBook` methods of its `Model` argument to update and sort 
all contacts by the target prefix.
6. The `execute` method of the `SortCommand` object returns a `CommandResult` object which stores the data regarding 
the completion of the `sort` command.

### Find feature

The `find` command allows users to search for contacts in Notarius based on specified fields: 
name, phone, email, address, and tags. The search is case-insensitive and supports multiple keywords. 
Additionally, for name, email, and address fields, the search is tolerant of minor typos, allowing matches within a Levenshtein distance of 2.

<p align="center">
  <img src="images/FindCommandSequenceDiagram.png" alt="Ui" />
</p>

#### Implementation

1. The user enters the `find` command with the desired search criteria. 
2. The `LogicManager` invokes the parseCommand method of AddressBookParser to identify the command type. 
3. If the command is recognized as `find`, the `FindCommandParser` is instantiated. 
4. The `FindCommandParser` extracts the search parameters and keywords, ensuring correct parsing of multi-word inputs enclosed in double quotes (""). 
5. A new `FindCommand` object is created using the parsed search fields and keywords. 
6. The `LogicManager` executes the `FindCommand` object, which:
   1. Calls `updateFilteredPersonList` in `Model` to filter contacts based on the search fields and keywords. 
   2. Uses a case-insensitive check for all fields. 
   3. Applies Levenshtein distance ≤ 2 matching for `name`, `email`, and `address`. 
7. The `FindCommand` returns a `CommandResult`, displaying the filtered list of contacts matching the search criteria.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commit()`, `Model#undo()` and `Model#redo()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commit()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commit()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commit()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undo()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canundo()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redo()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canredo()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commit()`, `Model#undo()` or `Model#redo()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commit()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: Lawyers

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: manage contacts faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                                | I want to …​                                                                                                     | So that I can…​                                                                            |
|----------|------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| `* * *`  | lawyer                                                                 | list all contacts with their relevant details                                                                    | easily peruse them                                                                         |
| `* * *`  | lawyer                                                                 | add a contact's name                                                                                             | identify the individual easily                                                             |
| `* * *`  | lawyer                                                                 | add a contact's phone number                                                                                     | contact them when needed                                                                   |
| `* * *`  | lawyer                                                                 | add a contact's email                                                                                            | find an alternative way to contact them                                                    |
| `* * *`  | lawyer                                                                 | add a contact's address                                                                                          | find a way to send them _case_ details                                                     |
| `* * *`  | lawyer                                                                 | save my contacts to my _local system_                                                                            | access them later after reopening the application                                          |
| `* * *`  | lawyer                                                                 | delete contacts                                                                                                  | remove contacts that I no longer need                                                      |
| `* * *`  | lawyer                                                                 | have access to my contacts even without internet                                                                 | access my contacts offline                                                                 |
| `* * *`  | lawyer who can type fast and prefers typing over clicking over a GUI   | type everything over a command line interface that minimizes my use of mouse                                     | enjoy the convenience of the application                                                   |
| `* *`    | busy lawyer who is working multiple cases and clients at the same time | add notes to contacts                                                                                            | keep track of relevant case-related details, their preferences, and their personal details |
| `* *`    | forgetful lawyer                                                       | star/pin important clients                                                                                       | look up their information faster                                                           |
| `* *`    | lawyer who can type fast                                               | re-access previously entered commands quickly                                                                    | save time by not typing them again                                                         |
| `* *`    | lawyer                                                                 | set reminders to follow up with contacts                                                                         | make sure to not miss an important check-in                                                |
| `* *`    | lawyer                                                                 | delete multiple contacts under a certain case                                                                    | clear clients of past cases faster and more conveniently                                   |
| `* *`    | lawyer                                                                 | tag contacts (e.g., "Client," "Judge," "Opposing Counsel")                                                       | remember their roles easily                                                                |
| `* *`    | lawyer                                                                 | tag multiple contacts at once                                                                                    | categorise them for my needs more efficiently and conveniently                             |
| `* *`    | lawyer                                                                 | add multiple tags to a contact                                                                                   | organise my contacts neatly and not have to keep tagging the same contact multiple time    |
| `* *`    | impatient lawyer                                                       | get the output of my commands quickly and responsively                                                           | not waste time on waiting for the output                                                   |
| `* *`    | lawyer                                                                 | have a user-friendly and not cluttered interface to navigate around                                              | focus on my legal work without getting distracted by complicated tools                     |
| `* *`    | lawyer                                                                 | edit contact details                                                                                             | update information when needed                                                             |
| `* *`    | lawyer                                                                 | be able to lookup help/guide for the application locally in case i do not know forgot how to use the application | figure out how to use it conveniently                                                      |
| `* *`    | lawyer handling _crucial client information_                           | not be able to add duplicate contacts by phone number                                                            | ensure that there is no redundant client data                                              |
| `* *`    | lawyer trying the application for the first time                       | clear all the contact data when necessary                                                                        | facilitate the population of new data                                                      |
| `*`      | lawyer                                                                 | have my notes be automatically time-stamped                                                                      | keep track of when notes were created                                                      |
| `*`      | busy lawyer                                                            | use command aliases to enter commands faster                                                                     | save time and improve efficiency                                                           |
| `*`      | lawyer                                                                 | have the application be personalised for me                                                                      | feel more connected and engaged while using it                                             |
| `*`      | lawyer                                                                 | add general notes (e.g. Post-its)                                                                                | store information that is not related to any case or contact                               |
| `*`      | lawyer                                                                 | assign contacts to specific cases                                                                                | keep track of all people involved in a legal matter                                        |
| `*`      | busy lawyer                                                            | view all contacts to specific cases                                                                              | quickly access their details when needed                                                   |
| `*`      | lawyer                                                                 | unlink contacts related from cases                                                                               | update case information when necessary                                                     |
| `*`      | lawyer                                                                 | add notes to cases                                                                                               | view case information when needed                                                          |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is `Notarius` and the **Actor** is the `User`, unless specified otherwise)

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC01 - Deleting a contact`

**Guarantees**: `If MSS reaches step 4, the requested contact will be deleted`

**MSS**:
1. User requests to list contacts.
2. Notarius displays a list of contacts.
3. User requests to delete a specific contact in the list.
4. Notarius deletes the contact and confirms that the contact has been deleted.

   Use case ends.

**Extensions**:
* 1a. Notarius is unable to find the specified contact.
  * 1a1. Notarius alerts the user about the error.
  * 1a2. User retypes the command.
  * Steps 1a-1a2 are repeated until the contact specified exists.
  * Use case resumes from step 4.

* 1b. Notarius uncovers an empty field description.
  * 1b1. Notarius alerts the user about the issue.
  * 1b2. User retypes the command with a non-empty value for the specified field.
  * Steps 1b-1b2 are repeated until the field is no longer empty.
  * Use case resumes from step 4.

* 1c. Notarius uncovers an invalid contact identifier.
  * 1c1. Notarius alerts the user about the issue.
  * 1c2. User retypes the command with a valid contact identifier format.
  * Steps 1c-1c2 are repeated until the contact identifier is valid.
  * Use case resumes from step 4.


**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC02 - Finding a particular contact by name`

**Guarantees**: `If MSS reaches step 2, the contact will return a list of contacts whose names contain the keyword the user entered`

**MSS**:
1. User enters the keyword that the person’s name contains.
2. Notarius shows the corresponding results.

   Use case ends.

**Extensions**:
* 1a. Notarius uncovers that the user wrote an empty input.
  * 1a1. Notarius alerts the user of the error.
  * 1a2. User retypes the command.
  * Steps 1a-1b are repeated until the user input is not empty.
  * Use case resumes from step 2.




**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC03 - Editing a contact`

**Guarantees**: `If MSS reaches step 2, the contact will have the specified field updated correctly to the new value`

**MSS**:
1. User requests to edit field of a contact given by their id.
2. Notarius updates that field to the new value and confirms the contact has been edited.

   Use case ends.

**Extensions**:
* 1a. Notarius is unable to find the specified contact.
  * 1a1. Notarius alerts the user about the error.
  * 1a2. User retypes the command.
  * Steps 1a-1a2 are repeated until the contact specified exists.
  * Use case resumes from step 2.

* 1b. Notarius uncovers an empty field description.
  * 1b1. Notarius alerts the user about the issue.
  * 1b2. User retypes the command with a non-empty value for the specified field.
  * Steps 1b-1b2 are repeated until the field is no longer empty.
  * Use case resumes from step 2.

* 1c. Notarius uncovers that the format of the field the user entered is invalid.
  * 1c1. Notarius alerts the user about the error.
  * 1c2. User retypes the command with a valid field format.
  * Steps 1c-1c2 are repeated until the field format is valid.
  * Use case resumes from step 2.

* 1d. Notarius uncovers an invalid contact identifier.
  * 1d1. Notarius alerts the user about the issue.
  * 1d2. User retypes the command with a valid contact identifier format.
  * Steps 1d-1d2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.



**System**: `Notarius`

**Use Case**: `UC04 - Adding a note to contact`

**Actor**: `User`

**Guarantees**: `A new note is added to the contact.`

**MSS**:

1. User requests to add a note to a contact.
2. Notarius adds the note to the contact’s list of notes and confirms the successful addition of the note.

   Use case ends.

**Extensions**:

* 1a. Notarius uncovers a missing description field in the entered input.
  * 1a1. Notarius displays the error message.
  * 1a2. User retypes the command with the description field.
  * Use case resumes from step 2.

* 1b. Notarius is unable to find the specified contact.
  * 1b1. Notarius alerts the user about the error.
  * 1b2. User retypes the command.
  * Steps 1b-1b2 are repeated until the contact specified exists.
  * Use case resumes from step 2.

* 1c. Notarius uncovers an invalid note description.
  * 1c1. Notarius alerts the user about the error.
  * 1c2. User retypes the command with a valid note description format.
  * Steps 1c-1c2 are repeated until the note description format is valid.
  * Use case resumes from step 2.

* 1d. Notarius uncovers an empty field description.
  * 1d1. Notarius alerts the user about the issue.
  * 1d2. User retypes the command with a non-empty value for the specified field.
  * Steps 1d-1d2 are repeated until the field is no longer empty.
  * Use case resumes from step 2.

* 1e. Notarius uncovers an invalid contact identifier.
  * 1e1. Notarius alerts the user about the issue.
  * 1e2. User retypes the command with a valid contact identifier format.
  * Steps 1e-1e2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

**System**: `Notarius`

**Use Case**: `UC05 - Editing a note of a contact`

**Actor**: `User`

**Guarantees**: `The specified fields of the note are updated to the correct values.`

**MSS**:

1. User requests to edit a field of a specified note of a contact.
2. Notarius updates the note and confirms the note has been successfully edited.

   Use case ends.

**Extensions**:

* 1a. Notarius uncovers an invalid note identifier.
  * 1a1. Notarius alerts the user about the issue.
  * 1a2. User retypes the command with a valid note identifier format.
  * Steps 1a-1a2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

* 1b. Notarius is unable to find the specified contact.
  * 1b1. Notarius alerts the user about the error.
  * 1b2. User retypes the command.
  * Steps 1b-1b2 are repeated until the contact specified exists.
  * Use case resumes from step 2.

* 1c. Notarius is unable to find the specified note.
  * 1c1. Notarius alerts the user of the error.
  * 1c2. User retypes the command.
  * Steps 1c-1c2 are repeated until the specified note exists.
  * Use case resumes from step 2.

* 1d. Notarius uncovers an invalid note description.
  * 1d1. Notarius alerts the user about the error.
  * 1d2. User retypes the command with a valid note description format.
  * Steps 1d-1d2 are repeated until the note description is valid.
  * Use case resumes from step 2.

* 1e. Notarius uncovers an empty field description.
  * 1e1. Notarius alerts the user about the issue.
  * 1e2. User retypes the command with a non-empty value for the specified field.
  * Steps 1e-1e2 are repeated until the field is no longer empty.
  * Use case resumes from step 2.

* 1f. Notarius uncovers an invalid contact identifier.
  * 1f1. Notarius alerts the user about the issue.
  * 1f2. User retypes the command with a valid contact identifier format.
  * Steps 1f-1f2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC06 - Deleting a note of a contact.`

**Guarantees**: `The specified note is deleted successfully.`

**MSS**:

1. User specifies the note of a contact to delete.
2. System deletes the note and confirms the successful deletion.

   Use case ends.

**Extensions**:

* 1a. Notarius is unable to find the specified note.
  * 1a1. Notarius alerts the user of the error.
  * 1a2. User retypes the command.
  * Steps 1a-1a2 are repeated until the specified note exists.
  * Use case resumes from step 2.

* 1b. Notarius uncovers an invalid note identifier.
  * 1b1. Notarius alerts the user about the issue.
  * 1b2. User retypes the command with a valid note identifier format.
  * Steps 1b-1b2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

* 1c. Notarius is unable to find the specified contact.
  * 1c1. Notarius alerts the user about the error.
  * 1c2. User retypes the command.
  * Steps 1c-1c2 are repeated until the contact specified exists.
  * Use case resumes from step 2.

* 1d. Notarius uncovers an invalid contact identifier.
  * 1d1. Notarius alerts the user about the issue.
  * 1d2. User retypes the command with a valid contact identifier format.
  * Steps 1d-1d2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC07 - Getting all notes belonging to a contact`

**Guarantees**: `All notes belonging to the specified contact are displayed.`

**MSS**:

1. User requests for the notes of a contact.
2. Notarius displays all notes belonging to the contact.

   Use case ends.

**Extensions**:

* 1a. Notarius is unable to find the specified contact.
  * 1a1. Notarius alerts the user about the error.
  * 1a2. User retypes the command.
  * Steps 1a-1a2 are repeated until the contact specified exists.
  * Use case resumes from step 2.

* 1b. Notarius uncovers an invalid contact identifier.
  * 1b1. Notarius alerts the user about the issue.
  * 1b2. User retypes the command with a valid contact identifier format.
  * Steps 1b-1b2 are repeated until the contact identifier is valid.
  * Use case resumes from step 2.

* 2a. Notarius cannot find any notes related to the contact.
  * 2a1. Notarius alerts the user with a relevant message.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC08 - Sorting the contacts list`

**Guarantees**: `If MSS reaches step 3, the user has successfully sorted the contacts list by a specified prefix.`

**MSS**:

1. User requests to sort Notarius by a specified prefix.
2. Notarius updates the contacts list in the sorted order.
3. Notarius confirms that the contacts list has been successfully sorted.

   Use case ends.

**Extensions**:

* 1a. Notarius detects a missing prefix in the entered input.
  * 1a1. Notarius displays the error message.
  * 1a2. User re-enters a new command with a specified prefix.
  * Steps 1a1 - 1a2 are repeated until a valid prefix is input by the User.
  * Use case resumes from step 2.

* 1b. Notarius detects an invalid prefix in the entered input.
  * 1b1. Notarius displays the error message.
  * 1b2. User re-enters a new command with a specified prefix.
  * Steps 1b1 - 1b2 are repeated until a valid field is input by the User.
  * Use case resumes from step 2.

* 1c. User enters extra spaces or invalid formatting in the entered input.
  * 1c1. Notarius displays an error message.
  * 1c2. User re-enters a new command with properly formatted command.
  * Steps 1c1 - 1c2 are repeated until a valid command is input by the User.
  * Use case resumes from step 2.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC09 - Clearing the contacts list`

**Guarantees**: `If MSS reaches step 3, the user has successfully cleared the contacts list.`

**MSS**:

1. User requests to clear the data in the contacts list.
2. Notarius updates the data in the contacts list.
3. Notarius confirms that the data in the contacts list has been cleared.

   Use case ends.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC10 - Listing all contacts`

**Guarantees**: `If MSS reaches step 3, the user has successfully listed all the contacts.`

**MSS**:

1. User requests to list all contacts.
2. Notarius displays all relevant contacts.
3. Notarius confirms that all relevant contacts has been successfully listed.

   Use case ends.


**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC11 - Displaying Help Information`

**Guarantees**: `If MSS reaches step 3, the requested help information will be displayed.`

**MSS**:

1. User requests help by entering the help command. 
2. Notarius displays a help window with general usage instructions.

   Use case ends.

**Extensions**:

* 1a. User requests help for a specific command. 
  * 1a1. Notarius displays detailed usage information for the specified command.<br>
    Use case ends.

* 1b.  User enters an invalid command name. 
  * 1b1. Notarius alerts the user with an error message: "Unknown command! Use help to see available commands."
  * 1b2. User retypes the command with a valid usgae.<br>
    Steps 1b1-1b2 are repeated until a valid command is entered.<br>
    Use case resumes from step 1a1.

**System**: `Notarius`

**Actor**: `User`

**Use Case**: `UC12 - Finding a Contact`

**Guarantees**: `If MSS reaches step 3, a list of matching contacts will be displayed.`

**MSS**:

1. User requests to find contacts by entering the find command with specified fields and keywords. 
2. Address Book searches for contacts whose fields contain any of the given keywords, 
allowing minor typos (up to a Levenshtein distance of 2) in the name, email, and address fields only. 
3. Address Book displays a list of matching contacts with index numbers.

   Use case ends.


**Extensions**:

* 1a. User enters an invalid search format. 
  * 1a1. Address Book alerts the user with an error message about the incorrect format. 
  * 1a2. User retypes the command following the correct format.<br>
    Steps 1a1-1a2 are repeated until the command format is valid.<br>
    Use case resumes from step 2.
* 2a. No contacts match the search criteria. 
  * 2a1. Address Book displays a message indicating that no matching contacts were found.<br>
    Use case ends.
* 2b. User enters multiple search fields. 
  * 2b1. Address Book searches for contacts that match any of the 
  specified fields (name, phone, email, address, or tags).<br>
    Use case resumes from step 3.
* 2c. User enters a keyword with minor typos in the phone or tag fields. 
  * 2c1. Address Book does not apply typo correction for phone numbers or tags. 
  * 2c2. If an exact match is not found, Address Book displays a message indicating no results were found.<br>
    Use case ends.
* 2d. User enters a keyword with minor typos in the name, email, or address fields. 
  * 2d1. Address Book applies fuzzy matching (Levenshtein distance of up to 2) for name, email, and address fields.<br>
    Use case resumes from step 3.



### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. Should be able to hold up to 1000 contacts without a noticeable lag in user requests.
3. A user with _above average typing speed_ for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Data should be stored locally as human editable file.
5. Software should work without an installer.
6. GUI should work well with standard screen resolutions 1920x1080 and higher, and for screen scales 100% and 125%.
7. GUI should be usable for resolutions 1280x720 and higher, and for screen scales 150%.
8. Application should come packaged as a single jar file.
9. The `.jar/.zip` file should not exceed 100 MB.
10. Documents packaged with the application should each not exceed 15 MB per file.
11. The application should respond within 5 seconds to any user request.

### Glossary

| **Terms**                  | **Meaning**                                                                                                                                            |
|----------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| AB-3                       | AddressBook Level 3 application, which was used as the base framework for a student-customized CLI management system to be developed.                  |
| Mainstream OS              | Windows, Linux, Unix, MacOS.                                                                                                                           |
| Above-average typing speed | ≥ 60 words per minute.                                                                                                                                 |
| Crucial client information | Important client information such as name, phone number, email, address, tags, as well as notes stored that could contain legal information for cases. |
| Local system               | The user's computer that they are using to run the application.                                                                                        |
| Cases (in law)             | The legal disputes that lawyers work on that involve many parties.                                                                                     |
| User Request               | The commands the user gives to Notarius via the command line interface.                                                                                |
| MSS                        | Main Success Scenario.                                                                                                                                 |
| API                        | Application Programming Interface.                                                                                                                     |
| GUI                        | Graphic User Interface.                                                                                                                                |
| CLI                        | Command Line Interface.                                                                                                                                |
| JAR                        | A packed file format used in Java that contains compiled Java code to enable easy distribution, portability, and execution.                            |
| JSON                       | JavaScript Object Notation, a lightweight data format widely used for storing and exchanging structured data.                                          |

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Finding a person

1. Finding a person while all persons are being shown
   1. Prerequisites: List all persons using `list` command. Multiple persons in the list.
   2. Test case: `find n/"Alice"` <br>
      Expected: Displays all contacts with names that match "Alice" (case-insensitive) or have a name with levenshtein distance <= 2 to "ALICE".
   3. Test case: `find n/"Alice" "Bob"` <br>
      Expected: Displays all contacts with names containing either "Alice" or "Bob".
   4. Test case: `find n/"Alice" e/"alice@email.com" a/"Bedok Central"` <br>
      Expected: Displays all contacts where any of the fields match or have a levenshtein distance <= 2 from each keyword.
   5. Test case: `find t/"client" p/"12345678"` <br>
      Expected: Displays contacts that exactly match either tag "client" or phone "12345678".
   6. Test case: `find n/"NonExistentName"` <br>
      Expected: "0 persons listed!" message is displayed.

### Displaying help

1. Showing help in Notarius
   1. Prerequisites: Notarius is open and running
   2. Test case: `help` <br>
      Expected: A help window appears displaying instructions for using the application. The status message confirms that the help window has been opened.
   3. Test case: `help find` <br>
      Expected: The application displays detailed instructions for the find command, including expected parameters and format. 
   4. Test case: `help delete` <br>
      Expected: The application displays detailed instructions for the delete command, including expected parameters and format.
   5. Test case: `help meeee` (invalid command) <br>
      Expected: Error message: "Unknown command! Use 'help' to see available commands."



### Sorting contacts list

Prerequisites: The contacts list should contain some contacts for testing purposes. <br>
  `add n/Alex Yeoh p/87438807 e/alexyeoh@example.com a/Blk 30 Geylang Street 29, #06-40 t/Client t/Friend`<br>
  `add n/Bernice Yu p/99272758 e/berniceyu@example.com a/Blk 30 Lorong 3 Serangoon Gardens, #07-18 t/Client t/Friend`<br>
  `add n/Charlotte Oliveiro p/93210283 e/charlotte@example.com a/Blk 11 Ang Mo Kio Street 74, #11-04 t/Lawyer`<br>
  `add n/David Li p/91031282 e/lidavid@example.com a/Blk 436 Serangoon Gardens Street 26, #16-43 t/Lawyer` <br>
  `add n/Irfan Ibrahim p/92492021 e/irfan@example.com a/Blk 47 Tampines Street 20, #17-35 t/Lawyer t/Colleagues` <br>
  `add n/Roy Balakrishnan p/92624417 e/royb@example.com a/Blk 45 Aljunied Street 85, #11-31 t/Colleagues`

1. Sorting contacts by names

   1. Test case: `sort n/`<br>
      Expected: Displays all contacts sorted by names in ascending order.

2. Sorting contacts by phone numbers

   1. Test case: `sort p/`<br>
      Expected: Displays all contacts sorted by phone numbers in ascending order.   

3. Sorting contacts by email addresses

   1. Test case: `sort e/`<br>
      Expected: Displays all contacts sorted by email addresses in ascending order.

4. Sorting contacts by addresses

   1. Test case: `sort a/`<br>
      Expected: Displays all contacts sorted by addresses in ascending order.

5. Sorting contacts by tags

   1. Test case: `sort t/`<br>
      Expected: Displays all contacts sorted by tags in ascending order.

6. Sorting contacts by tags and names

   1. Test case: `sort t/ n/`<br>
      Expected: Displays all contacts sorted by tags, followed by names in ascending order.

7. Sorting contacts by tags and phone numbers

   1. Test case: `sort t/ p/`<br>
      Expected: Displays all contacts sorted by tags, followed by phone numbers in ascending order.

7. Sorting contacts by tags and email addresses

   1. Test case: `sort t/ e/`<br>
      Expected: Displays all contacts sorted by tags, followed by email addresses in ascending order.

7. Sorting contacts by tags and addresses

   1. Test case: `sort t/ a/`<br>
      Expected: Displays all contacts sorted by tags, followed by addresses in ascending order.

### Listing all contacts

1. List all contacts

   1. Test case: `list`<br>
      Expected: Displays the whole contacts list.

### Clearing all contacts

1. Clear all contacts

  1. Test case: `clear`<br>
     Expected: Clears the whole contacts list.

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
