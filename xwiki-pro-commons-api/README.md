# XWiki Pro Commons API

This module holds generic Java code that isn't substantial enough to warrant its own module.

## Available Interfaces and Their Implementations

* **MacroUtils** - Responsible for common utility methods over macro blocks, such as updating macro content, getting the macro XDom, or rendering macro content.
    * `DefaultMacroUtils` - the only implementation.
* **MacroBlockFinder** - Responsible for searching for macros inside the XDom.
    * [Iterative Macro Block Finder](com/xwiki/commons/document/MacroUtils.java) - Searches for macros iteratively.
    * [Recursive Macro Block Finder](com/xwiki/commons/document/MacroBlockFinder.java) - Searches for macros recursively.