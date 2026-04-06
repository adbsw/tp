# Viony - Project Portfolio Page

## Project: InventoryBRO

InventoryBRO is a desktop application for small shop owners to manage their inventory efficiently via a Command Line Interface (CLI). It supports adding, editing, deleting, and transacting items, with features like autocompletion and typo detection.

---

## Summary of Contributions

### Code Contributed
[View my code on the tP Code Dashboard](https://nus-cs2113-ay2526s2.github.io/tp-dashboard/?search=vionyp)

---

### Enhancements Implemented

#### 1. Edit Item Feature (`editItem`)
- **What it does:** Allows the user to update an existing item's name, quantity, and price in one command:
  - `editItem INDEX d/NEW_NAME q/NEW_QUANTITY p/NEW_PRICE` — updates name, quantity, and price
- **Justification:** Store owners often need to update just one field at a time. Splitting into separate commands avoids forcing users to re-enter unchanged fields.
- **Highlights:**
  - All three sub-commands are handled within a single `EditCommand.java` file, which routes to the correct private method based on the command keyword.
  - Each sub-method validates its own input independently before updating the item in-place.
  - Added `//@@author vionyp` tags for accurate RepoSense attribution.
- **Difficulty:** Medium — required careful routing logic and independent validation for each sub-command.

#### 2. Price Field in `Item` class
- **What it does:** Added a `price` field (stored as `double`) to the `Item` class, with `setPrice()` and `getPrice()` methods, and updated `toString()` to display price in `$X.XX` format.
- **Justification:** Without price tracking, the inventory system would be incomplete for real-world shop use cases.

---

### Contributions to the User Guide

- Wrote the **Editing an Item (`editItem`)** section, including:
  - Format description with parameter table
  - Example inputs and expected outputs
  - Note explaining that all three fields must be provided

---

### Contributions to the Developer Guide

- Wrote the **Editing an Item** section under Implementation, including:
  - Step-by-step execution walkthrough
  - PlantUML sequence diagram for `EditCommand`
  - Code snippet showing the parsing logic
  - Design considerations table comparing "all fields required" vs "optional fields" approach
  - Edit Command Class Diagram reference

---

### Contributions to Team-Based Tasks

- Helped resolve merge conflicts in `Parser.java` during the v1.0 iteration.
- Updated `Parser.java` to register the `editItem` command keyword correctly.
- Added `//@@author vionyp` tags to all code I authored to ensure accurate RepoSense attribution.

---

### Review/Mentoring Contributions

- Reviewed teammates' PRs on GitHub and provided comments on code correctness and formatting.

---

### Contributions Beyond the Project Team

- Smoke-tested CATcher for the practical exam preparation.
- Reported bugs found in peer teams' products during PE dry run activities.