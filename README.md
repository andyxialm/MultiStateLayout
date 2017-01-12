#MultiStateLayout
[![](https://jitpack.io/v/andyxialm/MultiStateLayout.svg)](https://jitpack.io/#andyxialm/MultiStateLayout)


A customize multiple state layout for Android.

![](https://github.com/andyxialm/MultiStateLayout/blob/master/art/screenshot.gif?raw=true)
### Usage

#### Gradle
##### Step 1. Add the JitPack repository to your build file
~~~ xml
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
~~~

##### Step 2. Add the dependency
~~~ xml
dependencies {
    compile 'com.github.andyxialm:MultiStateLayout:0.0.4'
}
~~~

#### Maven
##### Step 1. Add the JitPack repository to your build file
~~~ xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
~~~

##### Step 2. Add the dependency
~~~ xml
<dependency>
    <groupId>com.github.andyxialm</groupId>
    <artifactId>MultiStateLayout</artifactId>
    <version>0.0.4</version>
</dependency>
~~~
	
##### Edit your layout XML:

~~~ xml
<cn.refactor.multistatelayout.MultiStateLayout
    android:id="@+id/multi_state_layout"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:state="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    state:layout_network_error="@layout/layout_custom_network_error"
    state:animEnable="true"
    state:animDuration="500">
	
	<!-- content layout -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Hello World!"/>

</cn.refactor.multistatelayout.MultiStateLayout>
~~~

##### Common Configuration
```java
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiStateConfiguration.Builder builder = new MultiStateConfiguration.Builder();
        builder.setCommonEmptyLayout(R.layout.layout_empty)
               .setCommonErrorLayout(R.layout.layout_error)
               .setCommonLoadingLayout(R.layout.layout_loading);
        MultiStateLayout.setConfiguration(builder);
    }
}
```

##### How to change state?
```java

mMultiStateLayout.setState(MultiStateLayout.State.CONTENT);

mMultiStateLayout.setState(MultiStateLayout.State.EMPTY);

mMultiStateLayout.setState(MultiStateLayout.State.LOADING);

mMultiStateLayout.setState(MultiStateLayout.State.ERROR);

mMultiStateLayout.setState(MultiStateLayout.State.NETWORK_ERROR);

```

### License

    Copyright 2016 andy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.