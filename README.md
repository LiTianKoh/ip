# Bob Chatbot User Guide
> 🚀 **Version 1.0** | 📦 **Requires Java 17** | 🎯 **CLI Task Manager**

Bob is a **desktop chatbot app for managing tasks**, optimized for use via a **Command Line Interface (CLI)**.
If you can type fast, Bob can help you manage your todos, deadlines, and events faster than traditional GUI apps.

## Quick Start

1. Ensure you have **Java 17** or above installed on your computer.
   - Open a terminal and run: `java -version`
   - If not installed, download from [Oracle's website](https://www.oracle.com/java/technologies/downloads/)

2. Download the latest `Bob.jar` file from the [releases page](https://github.com/LiTianKoh/ip/releases).

3. Copy the jar file into an empty folder (this will be your home folder for Bob).

4. Open a command terminal in that folder:
   - **Windows**: Hold `Shift` + right-click in the folder → "Open PowerShell window here" or "Open command window here"
   - **Mac**: Right-click the folder → "Services" → "New Terminal at Folder"
   - **Linux**: Right-click → "Open in Terminal"

5. Run the application with the command:
   ```bash
   java -jar Bob.jar
   ```
6. You should see the following message:
   ```
   Hello from
    ______   _______   ______ 
   |  __  \ |  ___  | |  __  \
   | |__)  )| |   | | | |__)  )
   |  __  ( | |   | | |  __  (
   | |__)  )| |___| | | |__)  )
   |______/ |_______| |______/
       ___________________________
       Hello! I'm BOB
       What can I do for you?
       ___________________________
   ```
## Features
### Notes about command syntax
- Words in `UPPER_CASE` are parameters you need to supply (e.g., in todo `DESCRIPTION`)
- Parameters can be in any order (for commands with multiple parameters)
- Dates and times can be in any format (e.g., `Friday`, `2024-09-30`, `2pm`, `1600`...)

## Getting help: `help`
Displays a summary of all available commands.

Syntax: `help`
```
help
    ___________________________
    Available commands:
    - todo DESCRIPTION
    - deadline DESCRIPTION /by DATE
    - event DESCRIPTION /from START /to END
    - list
    - mark TASK_NUMBER
    - unmark TASK_NUMBER
    - delete TASK_NUMBER
    - find KEYWORD
    - bye
    ___________________________
```
## 📝 Adding a todo task: `todo`
Add a todo task without `time`/`date`\
Syntax: `todo DESCRIPTION`
```
todo do CS2113 weekly quiz
    ___________________________
    Got it. I've added this task:
    [T][ ] do CS2113 weekly quiz
    Now you have 1 tasks in the list.
    ___________________________
```
> [!WARNING]
> - Empty `DESCRIPTION`
>```
> todo
>    ___________________________
>    OOPS!! The description of a todo cannot be empty.
>    ___________________________

## ⏰ Adding a deadline task: `deadline`
Add a deadline task with `time` and/or `date`\
Syntax: `deadline DESCRIPTION /by time_andOR_date`
```
deadline do CS2113 weekly quiz /by 2359
    ___________________________
    Got it. I've added this task:
    [D][ ] do CS2113 weekly quiz (by: 2359)
    Now you have 2 tasks in the list.
    ___________________________
deadline do CS2113 weekly quiz /by Friday 6/3/26 2359
    ___________________________
    Got it. I've added this task:
    [D][ ] do CS2113 weekly quiz (by: Friday 6/3/26 2359)
    Now you have 3 tasks in the list.
    ___________________________
```
`> [!WARNING]`
> * Using `by` instead of `/by`
> ```
> deadline do CS2113 weekly quiz by 2359            
>    ___________________________
>    OOPS!! Deadline must have a description and /by time.
>    ___________________________
> ```
> * Empty `DESCRIPTION`
> ```
> deadline
>    ___________________________
>    OOPS!! Deadline must have a description and /by time.
>    ___________________________

## 📅 Adding an event task: `event`
Adds an event with `time` period and/or `date` period\
Syntax: `event DESCRIPTION /from start /to end`
```
event sleep /from 10am /to 12pm
    ___________________________
    Got it. I've added this task:
    [E][ ] sleep (from: 10am to: 12pm)
    Now you have 4 tasks in the list.
    ___________________________
event sleep /from 6/3/26 10am /to 6/3/26 12pm
    ___________________________
    Got it. I've added this task:
    [E][ ] sleep (from: 6/3/26 10am to: 6/3/26 12pm)
    Now you have 5 tasks in the list.
    ___________________________
```
> [!WARNING]
> - Using `from` and `to` instead of `/from` and `/to`
> ```
> event sleep from 10am to 12pm
>    ___________________________
>    OOPS!! Event must have a description, /from and /to times.
>    ___________________________
> ```
> * Empty `DESCRIPTION`
> ```
> event
>    ___________________________
>    OOPS!! Event must have a description, /from and /to times.
>    ___________________________
> ```

## 📋 Viewing all tasks: `list`
List out all the created `todo`, `deadline`, `event` created\
Syntax: `list`
```
list
    ___________________________
    Here are the tasks in your list:
    1.[T][ ] do CS2113 weekly quiz
    2.[D][ ] do CS2113 weekly quiz (by: 2359)
    3.[D][ ] do CS2113 weekly quiz (by: Friday 6/3/26 2359)
    4.[E][ ] sleep (from: 10am to: 12pm)
    5.[E][ ] sleep (from: 6/3/26 10am to: 6/3/26 12pm)
    ___________________________

```

## ✅ Marking a task as completed: `mark`
Syntax: `mark TASK_NUM`
```
mark 1
    ___________________________
    Nice! I've marked this task as done: 
    1.[T][X] do CS2113 weekly quiz
    ___________________________
```
> [!WARNING]
> * Wrong/Non-Existent `TASK_NUM`
> ```
> mark 6
>    ___________________________
>    Error: Task number 6 does not exist.
>    ___________________________
> ```
> * No `TASK_NUM` specified
> ```
> mark
>    ___________________________
>    Error: Please specify a task number.
>    ___________________________
> ```

## 🔄 Marking a task as incomplete: `unmark`
Syntax: `unmark TASK_NUM`
```
unmark 1
    ___________________________
    Ok, I've marked this task as not done yet: 
    1.[T][ ] do CS2113 weekly quiz
    ___________________________
```
> [!WARNING]
> * Wrong/Non-Existent `TASK_NUM`
> ```
> unmark 6
>    ___________________________
>    Error: Task number 6 does not exist.
>    ___________________________
> ```
> * No `TASK_NUM` specified
> ```
> unmark
>    ___________________________
>    Error: Please specify a task number.
>    ___________________________
> ```

## 🗑️ Deleting a task: `delete`
Syntax: `delete TASK_NUM`
```
delete 5
    ___________________________
    Noted. I've removed this task:
    [E][ ] sleep (from: 6/3/26 10am to: 6/3/26 12pm)
    Now you have 4 tasks in the list.
    ___________________________
```
> [!WARNING]
> * Wrong/Non-Existent `TASK_NUM`
> ```
> delete 6
>    ___________________________
>    Error: Task number 6 does not exist.
>    ___________________________
> ```
> * No `TASK_NUM` specified
> ```
> delete
>    ___________________________
>    Error: Please specify a task number.
>    ___________________________
> ```

## 🔍 Finding task(s): `find`
Searches for tasks or events containing `TASK_KEYWORD`\
Syntax: `find TASK_KEYWORD`
```
find CS2113
    ___________________________
    Here are the matching tasks in your list:
    1.[T][ ] do CS2113 weekly quiz
    2.[D][ ] do CS2113 weekly quiz (by: 2359)
    3.[D][ ] do CS2113 weekly quiz (by: Friday 6/3/26 2359)
    ___________________________
```
> [!WARNING]
> * Wrong/Non-Existent `TASK_KEYWORD`
> ```
> find gym
>    ___________________________
>    No matching tasks found for: gym
>    ___________________________
> ```
> * No `TASK_NUM` specified
> ```
> find
>    ___________________________
>    Error: Please specify a keyword to find.
>    ___________________________
> ```

## 🤪 Secret personality: inputs with `handsome` or `beautiful` (Case Insensitive)
Added personality\
Syntax: `... handsome` or `handsome ...` || `... beautiful` or `beautiful ...`
```
you are handsome 
    ___________________________
    Nonono, you are ;)
    ___________________________
handsome bob
    ___________________________
    Nonono, you are ;)
    ___________________________
you are beautiful 
    ___________________________
    Nonono, you are ;)
    ___________________________
beautiful bob
    ___________________________
    Nonono, you are ;)
    ___________________________
```

## 👋 Exiting the program: `bye` (Case_Insensitive)
Exits the program\
Syntax: `bye`
```
bye
    ___________________________
    Bye. Hope to see you again soon!
    ___________________________
```

## Data Storage
Bob automatically saves your tasks to the hard disk after every command that changes the data.
- Save file location: ./data/bob.txt (in the same folder as your Bob.jar)
- File format: Human-readable text (you can open it with any text editor)
- Tasks are automatically loaded when you start Bob again

## Command Summary

| Action | Format | Examples |
|--------|--------|----------|
| **Add Todo** | `todo DESCRIPTION` | `todo read book` |
| **Add Deadline** | `deadline DESCRIPTION /by DATE` | `deadline return book /by Friday` |
| **Add Event** | `event DESCRIPTION /from START /to END` | `event project meeting /from 2pm /to 4pm` |
| **List** | `list` | `list` |
| **Mark** | `mark TASK_NUMBER` | `mark 1` |
| **Unmark** | `unmark TASK_NUMBER` | `unmark 2` |
| **Delete** | `delete TASK_NUMBER` | `delete 3` |
| **Find** | `find KEYWORD` | `find book` |
| **Help** | `help` | `help` |
| **Exit** | `bye` | `bye` |

## FAQ
Q: How do I transfer my tasks to another computer?\
A: Copy both the Bob.jar file AND the entire data folder to the new computer. Run Bob from that location.

Q: What happens if I delete the data folder?\
A: Bob will create a new empty data folder and start with no tasks.

Q: Can I edit the save file manually?\
A: Yes, but be careful! If the format becomes invalid, Bob will start with an empty task list. 
Always back up the file before editing.

Q: The app shows "Error: Task number X does not exist" - why?\
A: You're trying to mark/unmark/delete a task number that doesn't exist. Use list to see valid task numbers.

## Troubleshooting

| Problem | Likely Cause | Solution |
|---------|--------------|----------|
| **`Error: Unable to access jarfile`** | Running the command from wrong directory | Navigate to the folder containing `Bob.jar` first, then run `java -jar Bob.jar` |
| **`Error: Could not find or load main class`** | Corrupted JAR file or wrong Java version | Redownload the JAR file and ensure you have Java 17 installed |
| **`Error: Todo description cannot be empty`** | Missing description after `todo` command | Use format: `todo DESCRIPTION`<br>e.g., `todo read book` |
| **`Error: Deadline must have description and /by time`** | Incorrect deadline format | Use format: `deadline DESCRIPTION /by DATE`<br>e.g., `deadline return /by Friday` |
| **`Error: Event must have description, /from and /to times`** | Incorrect event format | Use format: `event DESCRIPTION /from START /to END`<br>e.g., `event meeting /from 2pm /to 4pm` |
| **`Error: Task number X does not exist`** | Trying to access a task that doesn't exist | Run `list` first to see valid task numbers, then use a number between 1 and the total count |
| **`Error: Please specify a valid task number`** | Using non-numeric input for task number | Use numbers only: `mark 1`, `delete 3`, etc. |
| **`Error: Please specify a task number`** | Missing number after `mark`/`unmark`/`delete` | Include a task number: `mark 1`, `unmark 2`, `delete 3` |
| **`Error: Please specify a keyword to find`** | Missing keyword after `find` | Include a search term: `find book`, `find meeting` |
| **`Error: Empty command`** | Pressing Enter without typing anything | Type a valid command (try `help` to see options) |
| **App starts with no tasks** | Save file missing or corrupted | Check if `data/bob.txt` exists. If corrupted, delete it and Bob will create a fresh one |
| **Tasks not saving after exit** | Write permissions or missing data folder | Ensure the `data` folder exists in the same directory as `Bob.jar` and is writable |
| **`find` command returns no results** | No tasks match your keyword | Try a different keyword or check spelling. The search is case-insensitive and matches partial words |
| **App freezes or doesn't respond** | Unexpected error or infinite loop | Force quit the terminal/command prompt and restart. If problem persists, re-download the JAR |
| **GUI doesn't appear (if applicable)** | Running in headless environment | Bob is a CLI app - it runs in the terminal, not as a GUI window |
| **Java version error** | Wrong Java version installed | Run `java -version` to check. Install Java 17 if needed |
| **Cannot type special characters (é, ñ, etc.)** | Terminal encoding issues | Use basic ASCII characters for task descriptions to avoid encoding problems |
| **Tasks appear in wrong order after restart** | Save file might be corrupted | Check `data/bob.txt` format. If issues persist, backup and delete the file to start fresh |
| **App runs slowly with many tasks** | Large task list | Bob is optimized for normal use. With 1000+ tasks, some operations may be slower |
| **Double-clicking JAR doesn't work** | JAR files not associated with Java | Always run from terminal: `java -jar Bob.jar` |
| **Cannot find `data` folder** | Running from wrong location | The `data` folder is created in the SAME directory as `Bob.jar`. Check your JAR file location |
