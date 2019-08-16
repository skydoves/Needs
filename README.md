# Needs
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) 
[![Build Status](https://travis-ci.org/skydoves/Needs.svg?branch=master)](https://travis-ci.org/skydoves/Needs)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Needs-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7569)
[![Android Weekly](https://img.shields.io/badge/Android%20Weekly-%23358-orange.svg)](https://androidweekly.net/issues/issue-358)
[![Javadoc](https://img.shields.io/badge/Javadoc-Needs-yellow.svg)](https://skydoves.github.io/libraries/needs/javadoc/needs/com.skydoves.needs/index.html)<br>
An easy way to implement modern permission instructions popup. <br>
Needs can be fully customized and showing with animations. <br>

![img0](https://user-images.githubusercontent.com/24237865/54251761-9ab78580-458b-11e9-9db0-7bd6684ce0f2.png)
![img1](https://user-images.githubusercontent.com/24237865/54251764-9ab78580-458b-11e9-86eb-794861fb9f75.png)

## Download
[![Download](https://api.bintray.com/packages/devmagician/maven/needs/images/download.svg)](https://bintray.com/devmagician/maven/needs/_latestVersion)
[![JitPack](https://jitpack.io/v/skydoves/Needs.svg)](https://jitpack.io/#skydoves/Needs)

### Gradle
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.skydoves:needs:1.0.5"
}
```

## Usage

### Basic example
This is a basic example on a screenshot. Here is how to create `Needs` using `Needs.Builder`.
```java
Needs needs = new Needs.Builder(context)
      .setTitle("Permission instructions for using this Android app.")
      .addNeedsItem(new NeedsItem(null, "· SD Card", "(Required)", "Access photos, media, and files on device."))
      .addNeedsItem(new NeedsItem(null, "· Location", "(Required)", "Access this device's location."))
      .addNeedsItem(new NeedsItem(null, "· Camera", "(Optional)", "Take pictures and record video."))
      .addNeedsItem(new NeedsItem(null, "· Contact", "(Optional)", "Access this device's contacts."))
      .addNeedsItem(new NeedsItem(null, "· SMS", "(Optional)", " end and view SMS messages."))
      .setDescription("The above accesses are used to better serve you.")
      .setConfirm("Confirm")
      .setBackgroundAlpha(0.6f)
      .setLifecycleOwner(lifecycleOwner)
      .build();
```
### Create using kotlin dsl
This is how to create `Needs`'s instance using kotlin dsl.
```kotlin
val needs = createNeeds(baseContext) {
      titleIcon = iconDrawable
      title = "Permission instructions \nfor using this Android app."
      titleTextForm = titleForm
      addNeedsItem(NeedsItem(drawable_sd, "SD Card", "(Required)", "Access photos, media, and files on device."))
      addNeedsItem(NeedsItem(drawable_location, "Location", "(Required)", "Access this device's location."))
      addNeedsItem(NeedsItem(drawable_camera, "Camera", "(Optional)", "Take pictures and record video."))
      addNeedsItem(NeedsItem(drawable_contact, "Contact", "(Optional)", "Access this device's contacts."))
      addNeedsItem(NeedsItem(drawable_sms, "SMS", "(Optional)", "Send and view SMS messages."))
      description = "The above accesses are used to better serve you."
      confirm = "Confirm"
      backgroundAlpha = 0.6f
      lifecycleOwner = lifecycle
      needsTheme = theme
      needsItemTheme = itemTheme
      needsAnimation = NeedsAnimation.CIRCULAR
    }
```

### OnConfirmListener
We can listen to the confirm button is clicked using `OnConfirmListener`.
```java
needs.setOnConfirmListener(new OnConfirmListener() {
      @Override
      public void onConfirm() {
          // confirmed
      }
    })
```

### Show and dismiss
Here is how to show needs popup and dismiss easily.
```java
needs.show(layout); // shows the popup menu to the center. 
needs.dismiss(); // dismiss the popup menu.
```

### TextForm
TextFrom is an attribute class that has some attributes about TextView for customizing popup texts.

```java
TextForm textForm = new TextForm.Builder(context)
          .setTextColor(R.color.colorPrimary)
          .setTextSize(14)
          .setTextStyle(Typeface.BOLD)
          .build();

builder.setTitleTextForm(titleTextForm);
builder.setDescriptionTextForm(descriptionTextForm);
builder.setConfirmTextForm(confirmTextForm);
```

Here is how to create `TextForm` using kotlin dsl.
```kotlin
val titleForm = textForm(baseContext) {
  textSize = 18
  textStyle = Typeface.BOLD
  textColor = ContextCompat.getColor(baseContext, R.color.black)
}
```

### NeedsTheme
NeedsTheme is an attribute class for changing `Needs` popup theme easily.

```java
NeedsTheme needsTheme = new NeedsTheme.Builder(context)
           .setBackgroundColor(ContextCompat.getColor(context, R.color.background))
           .setTitleTextForm(titleTextForm)
           .setDescriptionTextForm(descriptionTextForm)
           .setConfirmTextForm(confirmTextForm)
           .build();

builder.setNeedsTheme(needsTheme);           
```

Here is how to create `NeedsTheme` using kotlin dsl.
```kotlin
val theme = needsTheme(baseContext) {
      backgroundColor = ContextCompat.getColor(baseContext, R.color.background)
      titleTextForm = textForm(baseContext) {
        textSize = 18
        textColor = ContextCompat.getColor(baseContext, R.color.white)
      }
      descriptionTextForm = textForm(baseContext) {
        textSize = 12
        textColor = ContextCompat.getColor(baseContext, R.color.description)
      }
    }
```

### NeedsItemTheme
NeedsTheme is an attribute class for changing `Needs` popup RecyclerView's item theme easily.

```java
NeedsItemTheme needsItemTheme = new NeedsItemTheme.Builder(context)
               .setBackgroundColor(ContextCompat.getColor(context, R.color.background))
               .setTitleTextForm(titleTextForm)
               .setRequireTextForm(requireTextForm)
               .build();

builder.setNeedsItemTheme(needsItemTheme);
```

Here is how to create `NeedsItemTheme` using kotlin dsl.
```kotlin
val itemTheme = needsItemTheme(baseContext) {
      backgroundColor = ContextCompat.getColor(baseContext, R.color.background)
      titleTextForm = textForm(baseContext) {
        textColor = ContextCompat.getColor(baseContext, R.color.colorPrimaryDark)
        textSize = 16
      }
      descriptionTextForm = textForm(baseContext) {
        textColor = ContextCompat.getColor(baseContext, R.color.description)
      }
    }
```

### NeedsAnimation
NeedsAnimation implements showing and dismissing popup with animations.

ELASTIC | CIRCULAR
------------ | -------------
![elastic](https://user-images.githubusercontent.com/24237865/54488606-ffa21100-48e6-11e9-9264-59ae113cad32.gif)| ![circluar](https://user-images.githubusercontent.com/24237865/54488605-ffa21100-48e6-11e9-8c0d-aba373077905.gif)

NONE | FADE
------------ | -------------
![none](https://user-images.githubusercontent.com/24237865/54488604-ffa21100-48e6-11e9-9920-c172eafe7a8a.gif) | ![fade](https://user-images.githubusercontent.com/24237865/54488607-003aa780-48e7-11e9-8902-cbad62402822.gif)


```java
builder.setNeedsAnimation(NeedsAnimation.FADE)
builder.setNeedsAnimation(NeedsAnimation.NONE)
builder.setNeedsAnimation(NeedsAnimation.ELASTIC)
builder.setNeedsAnimation(NeedsAnimation.CIRCULAR)
```

### Kotlin Extensions
We can show and initialize Needs property more polish using extensions.

#### showNeeds
Shows the popup menu to the center. <br>
It observes the target view's inflating and after inflate finished, show up on the target view.
```
targetView.showNeeds(needs)
needs.showNeeds(targetView)
```

#### lazy needs
Initializes the Needs property using `needs` lazy delegate for Activity and Fragment.
```
private val myNeeds by needs {
  NeedsUtils.getNeedsStyle1(context = this, lifecycle = this)
}
```

### Preference
If you want to show-up the Popup only once or a specific number of times, here is how to implement it simply.
```java
.setPreferenceName("MyNeeds") // sets preference name of the Needs.
.setShowTime(3) // show-up three of times the popup. the default value is 1 If the preference name is set.
```

### Avoid Memory leak
Dialog, PopupWindow and etc.. have memory leak issue if not dismissed before activity or fragment are destroyed.<br>
But Lifecycles are now integrated with the Support Library since Architecture Components 1.0 Stable released.<br>
So we can solve the memory leak issue so easily.<br>

Just use `setLifecycleOwner` method. Then `dismiss` method will be called automatically before activity or fragment would be destroyed.
```java
.setLifecycleOwner(lifecycleOwner)
```

## Needs builder methods
```java
.setTitleIcon(@DrawableRes drawable: Drawable)
.setTitle(value: String)
.setTitleTextForm(value: TextForm)
.setDescription(value: String)
.setDescriptionTextForm(value: TextForm)
.setConfirmBackgroundColor(@ColorInt value: Int)
.setConfirm(value: String)
.setConfirmTextForm(value: TextForm)
.setConfirmVisible(value: Boolean)
.setListAdapter(value: RecyclerView.Adapter<*>)
.setListHeight(value: Int)
.setPadding(value: Int)
.addNeedsItem(value: NeedsItem)
.addNeedsItemList(value: List<NeedsItem>)
.setBackground(@DrawableRes value: Drawable)
.setBackgroundColor(@ColorInt value: Int)
.setBackgroundAlpha(value: Float)
.setDividerColor(@ColorInt value: Int)
.setDividerVisible(value: Boolean)
.setDividerHeight(value: Float)
.setOnConfirmListener(value: OnConfirmListener)
.setLifecycleOwner(value: LifecycleOwner)
.setNeedsTheme(value: NeedsTheme)
.setNeedsItemTheme(value: NeedsItemTheme)
.setNeedsAnimation(value: NeedsAnimation)
```

## Find this library useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/Needs/stargazers)__ for this repository. :star:

# License
```xml
Copyright 2019 skydoves

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
