# SwipeBottomView

![SwipeBottomView](https://i.ibb.co/dg6cRNv/swipebottomview.jpg)
## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
 }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.sothree.slidinguppanel:library:3.4.0'
    implementation 'com.github.edtslib:swipebottomview:latest'
}
```
# Usage

The SwipeBottomView is very easy to use. Just add it to your layout like any other view.
##### Via XML

Here's a basic implementation.

```xml
        <id.co.edts.emart.ui.baseview.swipeview.SwipeBottomView
        android:id="@+id/racks"
        app:title="@string/rack_near_by"
        app:content="@layout/view_rack_near_by_content"
        app:bottom="@layout/view_rack_near_by_bottom"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```
### Attributes information

An example is shown below.

```xml
        app:title="@string/rack_near_by"
        app:content="@layout/view_rack_near_by_content"
        app:bottom="@layout/view_rack_near_by_bottom"
        app:titleStyle="@style/TextStyle"
```

##### _app:title_
[string]: title of bottom view

##### _app:content_
[reference]: layout content for swipe view

##### _app:bottom_
[reference]: additional layout at bottom view, like button

##### _app:titleStyle_
[reference]: style of title text

### Content and Bottom View

For access content and bottom view you can use getter SwipeBottomView
```kotlin
            val contentView = binding.racks.contentView
    val adapter = RackAdapter()
    val recyclerView = contentView?.findViewById(R.id.recyclerView) 
    
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
    
    val view = binding.emptyState.bottomView
    view?.findViewById<View>(R.id.tvPositiveButton)?.setOnClickListener {
        QrCodeScannerActivity.open(this, scanResultLauncher)
    }
```

### Method for swipe actions on the SwipeBottomView

```kotlin
    val binding = ActivityMainBinding.inflate(layoutInflater)
    binding.swipeBottomView.delegate = object : SwipeBottomDelegate {
        override fun onSwiping(offset: Float) {
        }
    
        override fun onStartSwiping() {
        }
    
        override fun onExpanded() {
        }
    
        override fun onCollapsed() {
        }
    
    }
}
```





