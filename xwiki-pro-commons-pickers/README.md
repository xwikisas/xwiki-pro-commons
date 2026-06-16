# XWiki Pro Commons Pickers

This module hosts common macro parameter pickers and provides a generic way to create new macros.

## Available Pickers

The module offers 7 pickers:
com/xwiki/pickers/PagePicker.java
1. **[Page Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/PagePicker.java)** - Selects pages from the 
   XWiki based on 
   title 
   and 
   document 
   reference
2. **[Space Tree Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/SingleSpaceTree.java)** - 
   Selects a single space from a tree like view of the wiki spaces
3. **[Space Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/SuggestSpaceReference.java)** - Selects a single space based 
   on name
4. **[Spaces Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/SuggestSpacesReference.java)** - 
   Selects multiple spaces based on name
5. **[Tag Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/TagReference.java)** - Selects a single tag 
   from those available in the wiki
6. **[Tags Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/TagsReference.java)** - Selects multiple tags from those available in the wiki
7. **[Users Picker](xwiki-pro-commons-pickers-api/src/main/java/com/xwiki/pickers/UsersReference.java)** - Selects multiple users from 
   the wiki

## Helper Macros

Beside these pickers, the module offers 2 macros that make creating a picker easier:

- **`pickerImport($path)`** - Handles the import of your picker's JSX
- **`staticSelectPicker($parameters)`** - Creates a static picker from the predefined values inside `$parameters.options`

## Creating a Picker

To create a picker, you need:

1. A Java class than can be empty, this java class is used as the type of the parameter in the UI
2. A displayer, defined at `resources/templates/html_displayer/{JAVA_CLASS_NAME}`, where you define how the picker looks and import all necessary JavaScript
3. An XWiki page that uses `xwiki-selectize` to make requests to a data source
4. The data source itself, which can be an XWiki Page, a rest endpoint or anything as long as the format is correct.

For more details, see the [AutoSuggest Widget documentation](https://www.xwiki.org/xwiki/bin/view/Documentation/DevGuide/FrontendResources/AutoSuggestWidget/).