# Advanced BugReporter [![Last Release](https://img.shields.io/badge/last%20release-v1.0.0-green.svg)](https://github.com/ImMaury/AdvancedBugReporter/releases)
Advanced BugReports is a Bukkit plugin designed to help server owners keep track of bugs in their networks using players' assistance. It's especially useful to test new gamemodes and be sure that everything works properly. 

## Features

- User-friendly reporting system
- Receive a chat message when a new report is created
- View unread bug reports at login
- Teleport to the location where a bug was reported
- JSON-encoded database

## Commands & Permissions

| Command | Permission node | Description |
| --- | --- | --- |
| /reportbug [text] | bugreporter.report | Sends a new bug report |
| /reportlist | bugreporter.list | Shows stored bug reports |
| /reportdel [selector] [value] | bugreporter.del | Deletes bug reports by selectors. Available selectors: "id", "player" |
| /reporttp [id] | bugreporter.tp | Teleports to a bug report's location |
| /reporthelp  | bugreporter.help | Shows useful information about the plugin |
| | bugreporter.receive | Allows to view sent bug reports in real time and unread reports at login |
