# LineIcons Integration Guide for Videri

This guide explains how to implement and use LineIcons SVG icons in your Compose Multiplatform project.

## 🚀 Implementation Overview

### 1. **Project Structure**
```
composeApp/src/commonMain/
├── composeResources/
│   └── drawable/
│       ├── lineicons_home.xml
│       ├── lineicons_search.xml
│       ├── lineicons_library.xml
│       ├── lineicons_user.xml
│       ├── lineicons_star.xml
│       ├── lineicons_check.xml
│       └── lineicons_bookmark.xml
└── kotlin/com/example/videri/ui/
    └── icons/
        └── LineIcons.kt
```

### 2. **Dependencies Required**
```kotlin
// In build.gradle.kts
commonMain.dependencies {
    implementation(compose.components.resources) // Already included
}
```

## 🎯 Usage Examples

### **Navigation with LineIcons**
```kotlin
NavigationBarItem(
    icon = {
        Icon(
            imageVector = LineIcons.Home,
            contentDescription = "Home"
        )
    },
    label = { Text("Home") },
    selected = selectedTab == 0,
    onClick = { /* handle click */ }
)
```

### **Status Chips with LineIcons**
```kotlin
// Watched indicator
LineIconStatusChip(
    icon = LineIcons.Check,
    backgroundColor = MaterialTheme.extendedColors.watched,
    contentColor = Color.White
)

// Watchlist indicator  
LineIconStatusChip(
    icon = LineIcons.Bookmark,
    backgroundColor = MaterialTheme.extendedColors.watchlist,
    contentColor = Color.White
)
```

### **Rating with LineIcons**
```kotlin
Row(verticalAlignment = Alignment.CenterVertically) {
    Icon(
        imageVector = LineIcons.Star,
        contentDescription = null,
        tint = MaterialTheme.extendedColors.rating,
        modifier = Modifier.size(12.dp)
    )
    Text(
        text = rating.toString().take(3),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.extendedColors.rating
    )
}
```

## 📦 Available Icons

### **Navigation Icons**
- `LineIcons.Home` - Home screen
- `LineIcons.Search` - Search functionality  
- `LineIcons.Library` - User's library/collection
- `LineIcons.User` - User profile

### **Media Icons**
- `LineIcons.Movie` - Movie content placeholder
- `LineIcons.Television` - TV show content placeholder
- `LineIcons.Star` - Rating display
- `LineIcons.StarFilled` - Filled rating star
- `LineIcons.Play` - Play button

### **Action Icons**
- `LineIcons.Check` - Completed/watched status
- `LineIcons.Bookmark` - Watchlist/saved items
- `LineIcons.BookmarkFilled` - Filled bookmark
- `LineIcons.Heart` - Favorite items
- `LineIcons.HeartFilled` - Filled heart
- `LineIcons.Plus` - Add to collection
- `LineIcons.Close` - Close/remove actions

### **Interface Icons**
- `LineIcons.Filter` - Filter options
- `LineIcons.Sort` - Sort functionality
- `LineIcons.Settings` - App settings
- `LineIcons.More` - More options menu
- `LineIcons.Back` - Navigation back
- `LineIcons.Forward` - Navigation forward

## 🛠️ Adding New Icons

### **Step 1: Download SVG from LineIcons**
Visit [LineIcons website](https://lineicons.com/) and download the SVG files you need.

### **Step 2: Convert to Android Vector Drawable**
```xml
<!-- Example: lineicons_example.xml -->
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:fillColor="@android:color/black"
        android:pathData="M12,2L13.09,8.26L19,7L14.74,11.26L21,12L14.74,12.74L19,17L13.09,15.74L12,22L10.91,15.74L5,17L9.26,12.74L3,12L9.26,11.26L5,7L10.91,8.26L12,2Z" />
</vector>
```

### **Step 3: Add to LineIcons Object**
```kotlin
val NewIcon: ImageVector
    @Composable get() = vectorResource(Res.drawable.lineicons_example)
```

## 🎨 Styling & Theming

### **Icon Sizing**
```kotlin
Icon(
    imageVector = LineIcons.Home,
    modifier = Modifier.size(24.dp), // Standard size
    tint = MaterialTheme.colorScheme.onSurface
)
```

### **Custom Colors**
```kotlin
Icon(
    imageVector = LineIcons.Star,
    tint = Color.Yellow, // Custom color
    modifier = Modifier.size(16.dp)
)
```

### **Conditional Styling**
```kotlin
Icon(
    imageVector = if (isSelected) LineIcons.HeartFilled else LineIcons.Heart,
    tint = if (isSelected) Color.Red else MaterialTheme.colorScheme.onSurface
)
```

## 🔧 Best Practices

### **1. Consistent Sizing**
- Use 24dp for navigation icons
- Use 16dp for inline icons
- Use 12dp for status indicators

### **2. Proper Content Descriptions**
```kotlin
Icon(
    imageVector = LineIcons.Search,
    contentDescription = "Search movies and TV shows", // Descriptive
    // NOT: contentDescription = "Search" // Too generic
)
```

### **3. Theme Integration**
```kotlin
// Good: Uses theme colors
Icon(
    imageVector = LineIcons.Home,
    tint = MaterialTheme.colorScheme.onSurface
)

// Avoid: Hard-coded colors
Icon(
    imageVector = LineIcons.Home,
    tint = Color.Black // Won't adapt to dark theme
)
```

### **4. Performance Optimization**
```kotlin
// Icons are loaded once and cached
val homeIcon = LineIcons.Home // Reuse this reference
```

## 🚀 Migration from Material Icons

### **Before (Material Icons)**
```kotlin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

Icon(imageVector = Icons.Default.Home)
```

### **After (LineIcons)**
```kotlin
import com.example.videri.ui.icons.LineIcons

Icon(imageVector = LineIcons.Home)
```

## 🎯 Benefits of LineIcons

1. **Consistent Design Language** - All icons follow the same design principles
2. **Lightweight** - SVG-based icons are smaller than raster images
3. **Scalable** - Vector graphics scale perfectly at any size
4. **Customizable** - Easy to modify colors and sizes
5. **Professional Look** - Clean, modern aesthetic
6. **Open Source** - Free to use in commercial projects

## 📱 Platform Support

- ✅ **Android** - Full support with vector drawables
- ✅ **iOS** - Full support with Compose Multiplatform resources
- ✅ **Desktop** - Full support with Compose Desktop
- ✅ **Web** - Full support with Compose for Web

This implementation provides a scalable, maintainable way to use consistent iconography throughout your Videri app!
