## Create a new project

* Go to **Project** < **Create Project**
* **Select** the type of project that you want to create
* **Fill** the form
* Click on **Create**


You can create two types of project:

### Automator
Creates a simple project to automate tasks using Minium.

### Cucumber
Creates a base project, with a feature and his step definitions ready to execute.


After the project creation you will see the project open in the sidebar.

## Open Project
If you already have a project in your filesystem you can open it.

* Go to **Project** < **Open Project** 
* **Fill** the location of your project in the filesytem.

![minium-developer1](/assets/minium-dev-cucumber.png "Minium developer")
![minium-developer2](/assets/minium-dev-exec.png "Minium developer")
![minium-developer3](/assets/minium-dev-js.png "Minium developer")

## Launch a webdriver
You can create new webdrivers where you will execute your tests or run your code.

* Go to **Run** < **Launch Browser**
* **Select** the webdriver
* Click on the button **Create a new webdriver**

## Status Button
The floating button in the bottom right corner give you feedback when the test is being executed.

### Report
After an execution of a feature or a scenario you can see a button with a tooltip "Report".
It will open a modal with the report of your last test execution. There you can see the number of steps executed and if your test fails or some step was skipped, it give you more details about what happened.

###Cancel test
You have the possibility of cancel a test execution.

###Clear markers
Clean the markers in the present editor.

##Console tab
Minium Developer provides you a console where you can put javascript expressions that you don't want to put in your test files. It works like a helper for your javascript code, you can evaluate expressions and select elements.

##Features
###Run tests (Ctrl + enter)
Execute you the scenario from the cursor location. If you put the cursor in a scenario it and 
you can also use a shortcut (Ctrl + enter) to run a specific scenario.

###Run All
Execute all the scenario of your feature.

###Execution
When you run a scenario, minium developer will provide you a real time feedback of the step results. Each step executed will be marked on editor with a color depending on the result of the step. If the step is marked with red it means that the step doesn't pass with success.


###Selector Gadget
You can select an element or multiple elements in the webdriver. After the selection it will automatically generate a CSS expression to find the elements that you select. 
You can use this to pick elements of a web page without looking to the source code.

###Evaluate
You can evaluate your expressions in javascript. For example, if you want to evaluate an element like $("#myDiv"), var x = 10 . The expression evaluated will be stored in a scope.

###Search file (Ctrl + P)
Search and open files 

