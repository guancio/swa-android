# Day 1 / Discplaimer
Lot of information have been copied from https://developer.android.com/training/index.html and http://www.vogella.com/tutorials/Android/article.html

# Android
Android is an operating system based on the Linux kernel.
This Android operating system can be divided into the four areas:

 * Applications - The Android Open Source Project contains several default application, like the Browser, Camera, Gallery, Music, Phone and more.
 * Application framework - An API which allows high-level interactions with the Android system from Android applications.
 * Libraries and runtime - The libraries for many common framework functions, like, graphic rendering, data storage, web browsing. It also contains the Android Runtime, as well as the core Java libraries for
   running Android applications.
 * Linux kernel - Communication layer for the underlying hardware.

An Android application developer typically works with the first two areas to create new Android applications.

Android is published in different version, each one supporting a different version of the API.
For example 
Marshmallow 6.0 supports applications that require API <= 23,
Lollipop 5.1 supports applications that require API <= 22, and
KitKat 4.4  supports applications that require API <= 19.
That is, if you application requires some functionality that has been introduced in API 22,
it will be able to run Marshmallow and Lollupop, but not on KitKat.

## How to develop Android applications
Android applications are primarily written in the Java programming language.
During development the developer creates the Android specific configuration files and writes the application logic in the Java programming language.

The Android development tooling converts these application files into an Android application. If the developer trigger the deployment, the whole Android application is compiled, packaged, deployed and potentially started.

## Android Developer Tools and Android Studio

Google provides an IDE called Android Studio as the preferred development environment for creating Android applications.
This IDE is based on the IntelliJ IDE and can be downloaded for free from https://developer.android.com/studio/index.html

The Android tools provide specialized editors for Android specific files.
Most of Android’s configuration files are based on XML (We will see later what XML is).
In this case these editors allow you to switch between the XML representation of the file and a structured user interface for entering the data.

To install Android Studio follow the guide at https://developer.android.com/studio/install.html
* Chose to install the Standard configuration (the installer will download the needed components)

# Creating an Android Project
refer to https://developer.android.com/training/basics/firstapp/creating-project.html

This lesson shows you how to create a new Android project with Android Studio and describes some of the files in the project.

1. In Android Studio, create a new project:
 * If you don't have a project opened, in the Welcome to Android Studio window, click Start a new Android Studio project.
 * If you have a project opened, select File > New Project.
2. In the New Project screen, enter the following values:
 * Application Name: "My First App"
 * Company Domain: "example.com"
3. Android Studio fills in the package name and project location for you, but you can edit these if you'd like. Click **Next**.
4. In the Target Android Devices screen, keep the default values and click Next.
 * The Minimum Required SDK is the earliest version of Android that your app supports, which is indicated by the API level. To support as many devices as possible, you should set this to the lowest version available that allows your app to provide its core feature set. If any feature of your app is possible only on newer versions of Android and it's not critical to the core feature set, enable that feature only when running on the versions that support it (see Supporting Different Platform Versions).
5. In the Add an Activity to Mobile screen, select Empty Activity and click Next.
6. In the Customize the Activity screen, keep the default values and click Finish. 

After some processing, Android Studio opens and displays a "Hello World" app with default files. You will add functionality to some of these files in the following lessons.
Now take a moment to review the most important files. First, be sure that the Project window is open (select View > Tool Windows > Project) and the Android view is selected from the drop-down list at the top.

There are usually two type of files in an Android application:
 * Java source files, used to write the source code of your application
 * XML files, used for everithing else: e.g. to specify the graphical layout of your app,
   put buttons and labels, etc etc.
Before giving a look to the files that constitute your first app we need to introduce XML files.

# Intro to XML
XML stands for eXtensible Markup Language.
An XML document is a string of characters and is mainly ised to reppresent 
a tree structure, for example
```XML
<catalog>
 <book pages="125">
  <author>Roberto Guanciale</author>	
  <title>First Android Lecture</title>	
 </book>
 <book pages"2026" year="2026">
  <author>Galileo Galilei</author>	
  <title>The Scientific Method</title>	
 </book>
 <book year="">
  <author>Newton</author>	
  <title>The Scientific Method</title>	
 </book>
</catalog>
```
is an XML document representing a catalog of three books.

XML is closely related to HTML and has been developed to
be extensible.

## Tags
A tag is a construct that begins with `<` and ends with `>`. Tags come in three flavors:
* start-tag, such as `<catalog>`, `<book>`
* end-tag, such as `</catalog>`, `</book>`

* empty-element tag, such as <line-break />. TODO

## Elements
An element is a logical document component that either begins with a start-tag and ends with a matching end-tag (or consists only of an empty-element tag).
The characters between the start-tag and end-tag, if any, are the element's content, and may contain markup, including other elements, which are called child elements.
Some examples:
* `<author>Roberto Guanciale</author>` is an element, its content is `Roberto Guanciale`
* `<title>The Scientific Method</title>` is an element, its conent is `The Scientific Method`
* `<book pages="125">
  <author>Roberto Guanciale</author>	
  <title>First Android Lecture</title>	
 </book>` is an element. it has two childs: `<author>Roberto Guanciale</author>` and `<title>First Android Lecture</title>`.
* the while XML document is an element, usually called root. It contains three childs.


## Attributes
An attribute is a construct consisting of a name–value pair that exists within a start-tag (or empty-element tag).
An example is `<book pages"2026" year="2026">`, where the name of the attributes are "pages" and "year" and their values are "2026" and "2026" respectively.
An XML attribute can only have a single value and each attribute can appear at most once on each element. In the common situation where a list of multiple values is desired, this must be done by encoding the list into a well-formed XML attribute with some format beyond what XML defines itself. Usually this is either a comma or semi-colon delimited list or, if the individual values are known not to contain spaces,a space-delimited list can be used. 

## Resources
- [w3schools.com XML Tutorial](http://www.w3schools.com/xml/)


# Content of the minimal Android App
Now take a moment to review the most important files created by Android Studio.
Be sure that the Project window is open (select View > Tool Windows > Project) and the Android view is selected from the drop-down list at the top.
You can then see the following files:

* `app > java > com.example.myfirstapp > MainActivity.java`
    This file appears in Android Studio after the New Project wizard finishes. It contains the class definition for your application.
    When you build and run the app, the MainActivity starts says "Hello World!"
* `app > res > layout > activity_main.xml`
    This XML file defines the graphical interface of your application. For now it just contains the text "Hello world!".
* `app > manifests > AndroidManifest.xml`
    THis XML file describes the fundamental characteristics of the app and is used by Android to understand how start your application.
* `Gradle Scripts > build.gradle`
    Android Studio uses Gradle to compile and build your app. There is a `build.gradle` file for each module of your project, as well as a build.gradle file for the entire project.

An Android application (short: Android app) is a single installable unit which can be started and used independently of other Android applications.
An Android application consists of Android components, Java source and resource files. For now, it is enougth to know the following Android components:

* Application: An Android application can have one Application class which is instantiated before any other Android component. It is the last component which is stopped during application shutdown.
  If not explicitly defined, Android creates a default application object for your application. (this is the case for our app)
* Activity (e.g. `MainActivity.java`): An activity is the visual representation of an Android application. An Android application can have several activities.
  Activities use views and fragments to create their user interface and to interact with the user.

Our simple application has only one activity: `MainActivity.java`.

To run the app, continue to the next lesson. 

## Running Your App
https://developer.android.com/training/basics/firstapp/running-app.html

In the previous lesson, you created an Android project that displays "Hello World."
You can now run the app on an emulator. 
Before you run your app on an emulator, you need to create an Android Virtual Device (AVD) definition.
An AVD definition defines the characteristics of an Android phone, tablet that you want to simulate in the Android Emulator.

Create an AVD Definition as follows:

1. Launch the Android Virtual Device Manager by selecting Tools > Android > AVD Manager.
2. In the Your Virtual Devices screen, click Create Virtual Device.
3. In the Select Hardware screen, select a phone device, such as Nexus 5, and then click Next.
4. In the System Image screen, choose the desired system image for the AVD and click Next.
 * Since you don't have a particular system image installed, you can get it by clicking the download link.
   Basically, you are donwloading a virtual machine able to run the selected version of Android
5. Verify the configuration settings (for your first AVD, leave all the settings as they are), and then click Finish.


Run the app from Android Studio as follows:

1. In Android Studio, select your project and click Run ![Run](images/as-run.png) from the toolbar.
2. In the Select Deployment Target window, select your emulator and click OK.
 * The fist time you execute the application (i.e. run the virtual machine), Android Studio downloads a version of Android
   compatible with your virtual machine and the API level specified.

It can take a few minutes for the emulator to start. You may have to unlock the screen. When you do, My First App appears on the emulator screen.
Notice that you are not running only your application: you are running a complete version of Android in the emulator.

1. Go on the Home-screen of your emulate phone and play around (i.e. use the Browser)
2. Re-execute you application using the Android interface
3. Close your application
4. Re-execute you application using clicking Run ![Run](images/as-run.png) from the toolbar of Android Studio.
   Notice that this time the application start faster, since the complete Android has not to be booted.
5. Close the Android Emulator
6. Re-execute the emulator using Tools > Android > AVD Manager and clicking the run button. Notice that your application is still installed.

That's how you build and run your Android app on the emulator! To start developing, continue to the next lesson.


# Modifying the Simple User Interface
Open `MainActivity.java`. This is the visual representation of the Android application and is currently the only
Activity we have. When the activity starts, it executes
```Java
setContentView(R.layout.activity_main);
```
This method, inform the Android runtime to draw the graphical interface `R.layout.activity_main`.
Where `R.layout.activity_main` is defined? 
It is not directly defined in Java. Like several components in an Android application, `R.layout.activity_main`
is defined via an XML file `res/layout/activity_main.xml`. These resources are transformed *atumatically* by
the Android development environment to Java file, that you never manipulate manually.
The reason behind this design choice is that it is easier to build tools that manipulate XML files that Java code.
Also, since XML is extensible, the same format can be used to speficy different traits of you application 

* Layout of the graphical interface, position of buttons, text areas, menus etc.
* Configurations, translation of string in different languages, etc

Java is instead used to specify the logic (behavior) of the app.

Open `res/layout/activity_main.xml`. Android Studio opens a specialized graphical editor to design Android interfaces.
This editor is able to manipulate XML files that describe activityes's layouts.
By clicking on the *design* and *text* tabs, you can switch between a graphical representation of the laout and its
original XML definition:
```XML
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.example.guancio.myapplication.MainActivity">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />
</RelativeLayout>
```
The graphical user interface for an Android app is built using a hierarchy of `View` and `ViewGroup` objects.
`View` objects are usually UI widgets such as buttons or text fields.
`ViewGroup` objects are invisible view containers that define how the child views are laid out, such as in a grid or a vertical list.

![VieGroup](images/viewgroup.png)

Layouts are subclasses of the `ViewGroup`. In this example, the interface of the application consists of `RelativeLayout` which
contains one single `View` object: a `TextView`.
The `TextView` element has the attribute `android:text` which specifies the string printed by the UI component.
Change the string to something else and re-execute (re-deploy) the application using 
![Run](images/as-run.png) from the toolbar.

Alternatively, you can change the text using the design view.

 1. Select the `TextView` from the component tree or by clicking the text
    in the graphical interface
 2. Change the `text` field in the `Properties`	panel
 3. Re-deploy the application.

We can change other properties of the `TextView`

 1. expand the textApparance section
 2. change the textSize to `22sp`
 3. switching to the text view of the editor we can notice that a new attribute as been added to the `TextView` element: `android:textSize="25sp"`

# Using resources
Writing constants (expecially strings) in the layout XML file or in a Java source file is discuraged for several reason:

* We would like to change the welcome message independently from the bheavior of the app (which is defined in Java) and
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

To let the `TextView` to print the message stored in the resource, open `main_activity.xml` and change the `android:text` attribute
of the element as `android:text="@string/hello_msg"`. The `@` symbol informs the tool that the content of the attribute must be takes from
a resource, the `string` part informs the tool that the resource is a string (needed to locate the correct resource if there are multiple resources
of different type having the same name), and the `hello_msg` part informs the tool about the name of the resource.
From now on, if we want to change the welcome message, we can simply change the resource string file, whithout affecting the application layout.

The design view of the layout editor also support resources. The same change can be done by

1. selecting the design view
2. selecting the TextView
3. clicking on `...` near the `text` attribute in the Property panel
4. selecting the proper resources and clicking ok.

# Using layout managers
A layout manager is responsible for layouting itself and its child `Views` It is a subclass of `ViewGroup`.
Android supports different default layout managers.

The most relevant layout managers in Android are: LinearLayout, FrameLayout, RelativeLayout and GridLayout.

## Layout attributes
All layout manager can be configured via attributes. Children can also define attributes which may be evaluated by their parent layout.

Children can specify their desired width and height via the following attributes.

* android:layout_width - Defines the width of the widget.
* android:layout_height - Defines the height of the widget.

Views can define their size. This can be done in units of measurement or via pre-defined layout values. For example, as 100dp.
The `match_parent` value tells the application to maximize the widget in its parent. The `wrap_content` value tells the layout to allocate the minimum amount so that the widget is rendered correctly.
The effect of these elements is demonstrated in the following graphics.

![wrap_content.png](images/wrap_content.png)

![match_parent.png](images/match_parent.png)












For example, to access a String with the `R.string.yourString` ID in your source code, you would use the getString(R.string.yourString) method defined on the Context class.

