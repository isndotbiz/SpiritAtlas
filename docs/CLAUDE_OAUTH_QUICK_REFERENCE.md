# Claude OAuth Quick Reference

## Quick Start

### Using API Key (Current Method)
```kotlin
claudeProvider.setApiKey("sk-ant-api03-...")
```

### Using OAuth (When Available)
```kotlin
// After OAuth flow completes:
val expiresAt = System.currentTimeMillis() + (expiresInSeconds * 1000)
claudeProvider.setOAuthToken(
    accessToken = "at_...",
    refreshToken = "rt_...",
    expiresAt = expiresAt
)
```

---

## Common Operations

### Check Authentication Status
```kotlin
// Check if any auth is available
val isReady = claudeProvider.isAvailable()

// Check which auth method is active
val usingOAuth = claudeProvider.isUsingOAuth()
```

### Make API Call (Automatic Auth Selection)
```kotlin
val result = claudeProvider.generateEnrichment(enrichmentContext)
// Automatically uses OAuth if available, falls back to API key
```

### Manual Token Refresh
```kotlin
when (claudeProvider.refreshAccessToken()) {
    is Result.Success -> println("Refreshed")
    is Result.Error -> println("Failed - re-authenticate")
    is Result.Loading -> {}
}
```

### Sign Out / Clear Credentials
```kotlin
claudeProvider.clearOAuthCredentials()
// API key remains intact
```

---

## Error Handling Patterns

### Handle Authentication Errors
```kotlin
when (result) {
    is Result.Success -> { /* use data */ }
    is Result.Error -> {
        when {
            result.exception.message?.contains("re-authenticate") == true -> {
                // OAuth token invalid - show login
                navigateToOAuthLogin()
            }
            result.exception.message?.contains("API key") == true -> {
                // API key missing - show settings
                navigateToSettings()
            }
            else -> showError(result.exception.message)
        }
    }
    is Result.Loading -> { /* show loading */ }
}
```

---

## OAuth Flow (When Available)

### Step 1: Authorization
```kotlin
val authUrl = buildOAuthAuthUrl(clientId, redirectUri)
startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)))
```

### Step 2: Handle Callback
```kotlin
val authCode = intent.data?.getQueryParameter("code")
val tokens = exchangeCodeForTokens(authCode)
```

### Step 3: Store Tokens
```kotlin
claudeProvider.setOAuthToken(
    tokens.accessToken,
    tokens.refreshToken,
    System.currentTimeMillis() + (tokens.expiresIn * 1000)
)
```

---

## Configuration (When OAuth Goes Live)

### 1. Update Flag
```kotlin
// In ClaudeProvider.kt
const val OAUTH_AVAILABLE = true
```

### 2. Update Endpoints
```kotlin
private val oauthTokenUrl = "https://auth.anthropic.com/oauth/token" // Real
private val oauthAuthUrl = "https://auth.anthropic.com/oauth/authorize" // Real
```

### 3. Add Client Credentials
```properties
# local.properties
anthropic.oauth.client.id=your_client_id
anthropic.oauth.client.secret=your_client_secret
```

---

## Key Constants

```kotlin
ClaudeProvider.OAUTH_AVAILABLE  // false (as of Jan 2025)
ClaudeProvider.PROVIDER_NAME    // "Claude (Anthropic)"
```

---

## Security Notes

- Tokens encrypted with AES256-GCM
- Stored in EncryptedSharedPreferences
- Automatic refresh 5 minutes before expiration
- Thread-safe token refresh (Mutex)
- Invalid tokens automatically cleared on 401

---

## Current Status

- **OAuth Support:** Implemented, ready for activation
- **OAuth Available:** No (waiting for Anthropic release)
- **Current Auth:** API Key only
- **Fallback:** API Key remains available when OAuth is active

---

For detailed documentation, see: [CLAUDE_OAUTH_IMPLEMENTATION.md](./CLAUDE_OAUTH_IMPLEMENTATION.md)
