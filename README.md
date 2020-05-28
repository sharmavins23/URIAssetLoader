# URI Asset Loader App

Description: Vins Basics with ARCore
Screenshots and Shareables: https://cdn.discordapp.com/attachments/692419245811957822/715381673671524382/Screenshot_20200527-214848.jpg, https://cdn.discordapp.com/attachments/692419245811957822/715381674958913566/Screenshot_20200527-214858.jpg, https://cdn.discordapp.com/attachments/692419245811957822/715381675487657984/Screenshot_20200527-215058.jpg

![https://cdn.discordapp.com/attachments/692419245811957822/715381675487657984/Screenshot_20200527-215058.jpg](https://cdn.discordapp.com/attachments/692419245811957822/715381675487657984/Screenshot_20200527-215058.jpg)

All assets used were created by **[Poly by Google](https://poly.google.com/user/4aEd8rQgKu2)**. These were published under a Public/Remixable (CC-BY) license.

# Overview and Functionality

At the current stage of this project, the filesystem application is capable of taking a glTF file URI and adding it into the world. It employs Google's ARCore and Sceneform to do this.

There is no functionality yet for scaling the object in real space, adding more objects, or removing objects.

The published URI is a Poly by Google asset for a [Beagle](https://poly.google.com/view/0BnDT3T1wTE).

# Coding Process

Project development began through following closely the first reference. A Kotlin based application was designed that supports Android SDK 24 to 29. This means it supports Android versions 7.0 (Nougat) up to Android version 10.

Throughout the project I was utilizing Sceneform's beta release of built-in plugins for Android Studio. This plugin has a feature to manually convert `.obj`, `.fbx`, and `.gltf` files to Sceneform asset binaries. This would generate `.sfa` and `.sfb` files.

`.sfa` files are human-readable, and any changes in those files would be reflected in the `.sfb` files on build.

`.sfb` files are Sceneform Binary files. These binary files are compiled and contain the image data in a machine-readable format.

One of the major problems I encountered through this process was an error on program execution. The application would build properly, but crash often. This was because the ARCore version was downloaded through Sceneform, which was run on an older version of ARCore (version 1.5.0). More information about the error is documented below.

The final application utilizes ARCore version 1.12.0. This is not the latest version (1.17.0), but is tested and confirmed to be working as intended on my Oneplus 6T.

### Guides/References

- [Kotlin-based ARCore Sceneform App](https://heartbeat.fritz.ai/build-you-first-android-ar-app-with-arcore-and-sceneform-in-5-minutes-af02dc56efd6)
- [Kotlin-based Continuation (Fetching Models at Runtime)](https://proandroiddev.com/fetching-models-at-runtime-with-sceneform-and-arcore-fde1a3bad060)
- [Google's Java-based Introduction to Sceneform](https://codelabs.developers.google.com/codelabs/sceneform-intro/index.html?index=..%2F..%2Fio2018#0)
- [GitHub response to runtime loading of assets](https://github.com/google-ar/sceneform-android-sdk/issues/8#issuecomment-388074179)
- [Google's Java URI-based RenderableSource class](https://developers.google.com/sceneform/develop/create-renderables#load_3d_models_at_runtime)
- [Poorly Written Guide To ARCore Implementations (with good scalable UI design)](https://medium.com/@jose01.arteaga/kotlin-arcore-49b7a234f7cf)

---

# Errors Encountered

### [Sceneform] Gradle build failed with new import rules.

Android Studio 3.6 does not properly support Sceneform's asset simplification, and this is a known and reported issue. You can either downgrade to Android Studio 3.5, or hard-code the asset in.

[https://developers.google.com/sceneform/develop/import-assets](https://developers.google.com/sceneform/develop/import-assets)

In the app `build.gradle` file, saved asset files were put into the `\src\main\assets` folder instead.

**This is when I learned that the *app's* `build.gradle` and the *project's* `build.gradle` are two separate things.** Their only distinguishing trait (excluding the code inside) is which directory they are in.

```groovy
// Notes:
// The build.gradle APP and PROJECT files are two different things
// Despite having the same name...
// and having NO other external distinguishers.
// There are no stupid mistakes; Only developers who feel stupid after making them.

// In build.gradle APP file, save asset files into src\main\assets.
```

### [Widget Components] AAPT: Error: Resource not found.

This problem was solved by remembering to add the widget resource in the folder (`src/main/res/drawable`)

Documented here because while it's a simple mistake, the different XML drawable assets can actually be easily provided as vector graphics (and simple XML code.)

Any SVG graphic can be converted into an Android Drawable Vector graphic in Android Studio, as shown [here](https://medium.com/@iamitgupta1994/converting-svg-psd-to-xml-vector-drawable-using-android-vector-asset-studio-8e8ec23d5405).

### [Runtime] Filament Panic: Couldn't allocate 6144 KiB of memory for the command buffer.

This issue is a [well documented](https://github.com/Esri/arcgis-runtime-toolkit-android/pull/144) and known issue with ARCore 1.9.0. The solution is to upgrade ARCore's version.

In this project in specific, ARCore's version was handled by Sceneform. Thus, to upgrade ARCore's version, you need to upgrade Sceneform's defined version in the app `build.gradle`.

### [Sceneform] Binary XML File Line #23: Error inflating class com.google.ar.sceneform.*

This problem arises from a mismatch of ARCore (or Sceneform) versioning. If it continues, you can downgrade to an earlier version as well.

It might be useful in the future to store versioning in one file and reference that file instead of tail-chasing and converting versions individually throughout `build.gradle` files. (Isn't the point of a `build.gradle` to eliminate this problem entirely?)

### [URI Asset Loading] Assets do not appear/internet permissions not found.

Android apps require internet permissions before being able to parse URIs or download assets from the internet. This can be solved by adding the required permission in the `AndroidManifest.xml` file.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```