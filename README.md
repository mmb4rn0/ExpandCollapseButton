# ExpandCollapseButton

It is one kind of ToggleButton with interactive animations. It can be used in various purpose. Mainly when need to Expanding or Collapsing a View or Layout.

[![Alt text for your video](https://github.com/mmb4rn0/ExpandCollapseButton/blob/master/website/expand_collapse_button.gif)]

[![](https://jitpack.io/v/mmb4rn0/ExpandCollapseButton.svg)](https://jitpack.io/#mmb4rn0/ExpandCollapseButton)

# Download
Grab via gradle-
  Step 1. Add the JitPack repository to your build file
  ```grovy
   allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```  
  Step 2. Add the dependency 
  ```grovy	
   dependencies {
	        implementation 'com.github.mmb4rn0:ExpandCollapseButton:v2.0.0'
	}
  ```
or via Maven-
  Step 1. Add the JitPack repository to your build file
  ```xml
   <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  ```
  Step 2. Add the dependency
  ```xml
   <dependency>
	    <groupId>com.github.mmb4rn0</groupId>
	    <artifactId>ExpandCollapseButton</artifactId>
	    <version>v2.0.0</version>
	</dependency>
  ```

# Sample Code
```xml
    <com.mmbarno.expandcollapsebtn.ExpandCollapseButton
        android:id="@+id/btn_y_1"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:background="@drawable/ic_expand_collapse_btn_bg"
        android:elevation="4dp"
        app:checked="false"
        app:checked_state_src="@drawable/ic_checked_x"
        app:flip_axis="x"
        app:invert_flip="false" />
   ```
   
   # License

MIT License

Copyright (c) 2018 Manzur E Mehedi Barno

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
