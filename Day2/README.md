# Day 2 / Disclaimer
Lot of information have been copied from
* https://developer.android.com/training/index.html
* http://www.vogella.com/tutorials/Android/article.html
* https://developer.android.com/reference/packages.html

# Using resources
Writing constants (especially strings) in the layout XML file or in a Java source file is discouraged for several reasons:

* We would like to change the welcome message independently from the behavior of the app (which is defined in Java) and
  the graphical layout of the activity
* Possibly, we want to translate all messages to a different language


Android allows you to create static resources like images and XML configuration files. This allows you to keep these resources separate from the source code of your Android application.

Resource files must be placed in the `/res` directory of your application in a predefined sub-folder. The specific sub-folder depends on type of resource which is stored, including:

* Drawables in `/res/drawables`: Images (e.g., png, jpeg or SVG files)
* Simple Values in `/res/values`: Used to define strings, colors, dimensions, styles and static arrays of strings or integers via XML files.
  By convention each type is stored in a separate file, e.g., strings are defined in the `res/values/strings.xml` file.
* Layouts in `/res/layout`: XML files with layout descriptions are used to define the user interface for activities.

For example, in `res/values/strings.xml`  we have
```XML
<resources>
    <string name="app_name">My Application</string>
</resources>
```
which defines one string resource *app_name* whose value is "My Application".

Every relevant resource in the res folder, gets an ID assigned by the Android build system.
Android generates gen a `R.java` file which contains the generated values. These references are static integer values.
If you add a new resource file, the corresponding reference is automatically created in a `R.java` file.
Manual changes in the `R.java` file are not necessary and will be overwritten by the tooling. The Android system provides methods to access the corresponding resource files via these IDs.

To add a new resource, containing the hello message change `res/values/strings.xml` as follows
```XML
<resources>
    <string name="app_name">My Application</string>
    <string name="hello_msg">Hello I\'m Roberto Guanciale!</string>
</resources>	
```
Notice that some spacial characters (e.g. `'`) must be quoted by `\`.

To let the `TextView` print the message stored in the resource, open `main_activity.xml` and change the `android:text` attribute
of the element as `android:text="@string/hello_msg"`.
```XML
 <EditText android:id="@+id/edit_message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="@string/hello_msg" />
```
The `@` symbol informs the tool that the content of the attribute must be takes from
a resource, the `string` part informs the tool that the resource is a string (needed to locate the correct resource if there are multiple resources
of different type having the same name), and the `hello_msg` part informs the tool about the name of the resource.
From now on, if we want to change the welcome message, we can simply change the resource string file, without affecting the application layout.

The design view of the layout editor also support resources. The same change can be done by

1. selecting the design view
2. selecting the TextView
3. clicking on `...` near the `text` attribute in the Property panel
4. selecting the proper resources and clicking OK.

Similarly, we can change the text of cutton as follows
```Xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <EditText android:id="@+id/edit_message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="@string/hello_msg" />
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="sendMessage"
        android:text="@string/button_send" />
</LinearLayout>
```
Notice that, because you haven't defined the string resource `button_send`, you’ll see a compiler error at first.
Open the string resource file at `res > values > strings.xml`. Here, you add a new strings.
```XML
        <string name="button_send">Send</string>
```
For text in the user interface, always specify each string as a resource. String resources allow you to manage all UI text in a single location, which makes the text easier to find and update. Externalizing the strings also allows you to localize your app to different languages by providing alternative definitions for each string resource

Try to compile and run the application.



# Layout attributes

All layout manager can be configured via attributes. Children can also define attributes which may be evaluated by their parent layout.

Children can specify their desired width and height via the following attributes.

* android:layout_width - Defines the width of the widget.
* android:layout_height - Defines the height of the widget.

Views can define their size. This can be done in units of measurement or via predefined layout values. For example, as 100dp.
The `match_parent` value tells the application to maximize the widget in its parent. The `wrap_content` value tells the layout to allocate the minimum amount so that the widget is rendered correctly.
The effect of these elements is demonstrated in the following graphics.

![wrap_content.png](images/wrap_content.png)

![match_parent.png](images/match_parent.png)



`LinearLayout` supports assigning a weight to individual children via the `android:layout_weight` layout parameter. This value specifies how much of the extra space in the layout is allocated to the corresponding view. If, for example, you have two widgets and the first one defines a `layout_weight` of 1 and the second of 2, the first will get 1/3 of the available space and the other one 2/3. You can also set the `layout_weight` to zero to always have a certain ratio.


## Refine the user interface
It would be nice to fill the unused screen width with the text field.
You can do this inside a LinearLayout with the weight property, which you can specify using the `android:layout_weight` attribute.

The weight value is a number that specifies the amount of remaining space each view should consume, relative to the amount consumed by sibling views. This works kind of like the amount of ingredients in a drink recipe: "2 parts soda, 1 part syrup" means two-thirds of the drink is soda. For example, if you give one view a weight of 2 and another one a weight of 1, the sum is 3, so the first view fills 2/3 of the remaining space and the second view fills the rest. If you add a third view and give it a weight of 1, then the first view (with weight of 2) now gets 1/2 the remaining space, while the remaining two each get 1/4.

The default weight for all views is 0, so if you specify any weight value greater than 0 to only one view, then that view fills whatever space remains after all views are given the space they require.


In activity_main.xml, modify the <EditText> as follows:
```Xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <EditText android:id="@+id/edit_message"
        android:layout_weight="1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:hint="@string/hello_msg" />
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/button_send" />
</LinearLayout>
```

Try to compile and run the application.


# Other GUI components: Spinners
Spinners provide a quick way to select one value from a set. In the default state, a spinner shows its currently selected value. Touching the spinner displays a dropdown menu with all other available values, from which the user can select a new one.

## Adding a spinner to you view

You can add a spinner to your layout with the Spinner object. You should usually do so in your XML layout with a `<Spinner>` element. For example:

```XML
<Spinner
    android:id="@+id/my_spinner"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content" />
```

To populate the spinner with a list of choices, you then need to specify a SpinnerAdapter in your Activity or Fragment source code.
The choices you provide for the spinner can come from any source, but must be provided through an `SpinnerAdapter`, such as an `ArrayAdapter` if the choices are available in an array.

In the `onCreate` method of your activity you can set up the spinner to use the proper adapter and the proper source of choices. In the following example,
we use a list of String (`this.choices`) as source of choices:

```Java
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  choices = new Vector<CharSequence>();
  choices.add("Option 1");
  choices.add("Second option");

  Spinner spinner = (Spinner) findViewById(R.id.my_spinner);
  // Create an ArrayAdapter using the string list and a default spinner layout
  ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, this.choices);
  // Specify the layout to use when the list of choices appears
  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  // Apply the adapter to the spinner
  spinner.setAdapter(adapter);
}
```
The `ArrayAdapter` is in charge of transforming the options in a view that can be displayed by Android. The constructor  allows you to create an `ArrayAdapter` from the string list. The second argument is a layout resource that defines how the selected choice appears in the spinner control. The `simple_spinner_item` layout is provided by the platform and is the default layout you should use unless you'd like to define your own layout for the spinner's appearance.

You should then call `setDropDownViewResource(int)` to specify the layout the adapter should use to display the list of spinner choices (`simple_spinner_dropdown_item is another` standard layout defined by the platform).

Finally, call `setAdapter()` to apply the adapter to your Spinner.


If the available choices for your spinner are pre-determined, you can provide them with a string array defined in a string resource file:
```XML
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="options_array">
        <item>Option 1</item>
        <item>Option 2</item>
        <item>Option 3</item>
    </string-array>
</resources>
```

With an array such as this one, you can use the following code in your Activity to create the adapter
```Java
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_array, android.R.layout.simple_spinner_item);
```

## Modifying dynamically the list of options
Sometime it is necessary to change the options dynamically. It is not sufficient to change the `List` provided to the `ArrayAdapter`, instead the following pattern must be used

```Java
choices.add("New Option");
adapter.notifyDataSetChanged();
```
So, first the list contating the choices must be updated (e.g. by clearing it, adding elements, removing elements etc) then the adapter must be informed about the changes in the data backend using `notifyDataSetChanged`.


## Handling User Selections

When the user selects an item from the drop-down, the Spinner object receives an on-item-selected event.

To define the selection event handler for a spinner, implement the `AdapterView.OnItemSelectedListener` interface and the corresponding `onItemSelected()` callback method. For example, here's an implementation of the interface in an `Activity`:

```Java
public class SpinnerActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    ...
    public void onItemSelected(AdapterView<?> parent, View view,
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
```

The `AdapterView.OnItemSelectedListener` requires the `onItemSelected()` and `onNothingSelected()` callback methods.

Then you need to specify the interface implementation by calling `setOnItemSelectedListener()`:

```Java
Spinner spinner = (Spinner) findViewById(R.id.my_spinner);
spinner.setOnItemSelectedListener(this);
```

If you implement the `AdapterView.OnItemSelectedListener` interface with your Activity (such as in the example above), you can pass `this` as the interface instance.


# Accessing the spinner value
To access the value selected (assuming that the adapter is displayng an array of strings):
```Java
Spinner spinner = (Spinner) findViewById(R.id.my_spinner);

String value = (String) spinner.getItemAtPosition(spinner.getSelectedItemPosition());

```