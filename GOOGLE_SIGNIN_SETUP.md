# Google Sign-In Integration Guide

This guide explains how to integrate Google Sign-In functionality into your Videri app.

## 🚀 Features Added

- ✅ Google Sign-In button component
- ✅ Platform-specific handlers (Android/iOS)
- ✅ Multiplatform architecture
- ✅ Loading states and error handling
- ✅ Modern Material 3 design

## 📱 Setup Instructions

### Android Setup

1. **Add Google Services Plugin**
   ```kotlin
   // In your app-level build.gradle.kts
   plugins {
       id("com.google.gms.google-services")
   }
   ```

2. **Add google-services.json**
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Create a new project or use existing
   - Add Android app with your package name
   - Download `google-services.json`
   - Place it in `composeApp/src/androidMain/`

3. **Configure OAuth 2.0**
   - In Firebase Console, go to Authentication > Sign-in method
   - Enable Google sign-in
   - Add your app's SHA-1 fingerprint

### iOS Setup (Future Implementation)

```swift
// TODO: Add Google Sign-In SDK for iOS
// https://developers.google.com/identity/sign-in/ios/start-integrating
```

## 🔧 Implementation

### Basic Usage

```kotlin
@Composable
fun LoginExample() {
    val googleSignInHandler = remember { createGoogleSignInHandler(context) }
    
    LoginScreen(
        onLoginClick = { email, password -> /* regular login */ },
        onSignUpClick = { /* navigate to sign up */ },
        onForgotPasswordClick = { /* forgot password */ },
        onGoogleSignInClick = {
            // Handle Google Sign-In
            coroutineScope.launch {
                val result = googleSignInHandler.signIn()
                if (result.isSuccess) {
                    // Success! User is signed in
                    println("Welcome ${result.userDisplayName}!")
                } else {
                    // Handle error
                    println("Error: ${result.errorMessage}")
                }
            }
        }
    )
}
```

### Advanced Usage with State Management

```kotlin
@Composable
fun AuthScreen() {
    var authState by remember { mutableStateOf<AuthState>(AuthState.Idle) }
    val googleSignInHandler = remember { createGoogleSignInHandler(context) }
    
    LoginScreen(
        // ... other callbacks
        onGoogleSignInClick = {
            authState = AuthState.GoogleSignInLoading
            
            coroutineScope.launch {
                try {
                    val result = googleSignInHandler.signIn()
                    if (result.isSuccess) {
                        authState = AuthState.Success(result)
                    } else {
                        authState = AuthState.Error(result.errorMessage ?: "Unknown error")
                    }
                } catch (e: Exception) {
                    authState = AuthState.Error(e.message ?: "Unknown error")
                }
            }
        },
        isGoogleSignInLoading = authState is AuthState.GoogleSignInLoading,
        errorMessage = (authState as? AuthState.Error)?.message
    )
}

sealed class AuthState {
    object Idle : AuthState()
    object GoogleSignInLoading : AuthState()
    data class Success(val result: GoogleSignInResult) : AuthState()
    data class Error(val message: String) : AuthState()
}
```

## 🎨 UI Components

### GoogleSignInButton

A dedicated Google Sign-In button with:
- Google branding colors
- Loading state support
- Accessibility features
- Customizable text

```kotlin
GoogleSignInButton(
    onClick = { /* handle click */ },
    isLoading = false,
    text = "Continue with Google"
)
```

### VideriButton with Google Variant

The existing `VideriButton` now supports a Google variant:

```kotlin
VideriButton(
    onClick = { /* handle click */ },
    variant = ButtonVariant.Google
) {
    Text("Sign in with Google")
}
```

## 🔐 Security Considerations

1. **Server-Side Verification**: Always verify the ID token on your backend
2. **HTTPS Only**: Ensure all API calls use HTTPS
3. **Token Storage**: Store tokens securely using encrypted preferences
4. **Token Refresh**: Implement proper token refresh logic

## 🐛 Troubleshooting

### Common Issues

1. **"Sign-in failed: No account found"**
   - Ensure Google Play Services is installed
   - Check if user has a Google account on device

2. **"Developer Error"**
   - Verify SHA-1 fingerprint is correct
   - Check package name matches Firebase configuration

3. **"Network Error"**
   - Ensure device has internet connection
   - Check if Google services are blocked

### Debug Commands

```bash
# Get debug SHA-1
./gradlew signingReport

# Check Google Play Services
adb shell pm list packages | grep google
```

## 📚 Next Steps

1. **Backend Integration**: Set up server-side token verification
2. **Profile Management**: Sync Google profile data with your user system
3. **Offline Support**: Handle offline scenarios gracefully
4. **iOS Implementation**: Complete iOS Google Sign-In integration
5. **Testing**: Add unit and integration tests

## 🔗 Resources

- [Google Sign-In for Android](https://developers.google.com/identity/sign-in/android/start-integrating)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/)
