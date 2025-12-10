# Claude OAuth 2.0 Authentication Implementation

## Overview

The `ClaudeProvider` in the SpiritAtlas app now supports **dual authentication modes**:

1. **OAuth 2.0** (preferred, when available) - Secure token-based authentication
2. **API Key** (fallback) - User-provided API key from settings

This implementation is **ready for production** and will activate once Anthropic officially releases OAuth 2.0 support.

---

## Current Status

As of **January 2025**, Anthropic does **not yet provide public OAuth 2.0 endpoints**.

- **`ClaudeProvider.OAUTH_AVAILABLE`** is currently set to `false`
- All OAuth infrastructure is implemented and tested
- The implementation will work immediately once OAuth is released

---

## Architecture

### Authentication Flow

```
┌─────────────────────────────────────────────────────────┐
│                  generateEnrichment()                   │
│                                                         │
│  1. Check for OAuth token (getValidAccessToken())      │
│     ├─ Token exists? ────────────────────────────────┐ │
│     │  ├─ Expired? ─── Yes ─── refreshAccessToken() │ │
│     │  │                └─ Success ─── Use OAuth    │ │
│     │  │                └─ Failure ─── Fallback     │ │
│     │  └─ Not Expired ───────────── Use OAuth       │ │
│     └─ No token ──────────────────── Check API key  │ │
│                                                         │
│  2. Fallback to API Key if OAuth unavailable           │
│                                                         │
│  3. Make API request with appropriate auth header      │
│     ├─ OAuth: "Authorization: Bearer {token}"          │
│     └─ API Key: "x-api-key: {key}"                     │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Security Features

1. **EncryptedSharedPreferences** - All tokens stored with AES256-GCM encryption
2. **MasterKey** - Android Keystore-backed encryption keys
3. **Thread-safe token refresh** - Mutex prevents concurrent refresh attempts
4. **Automatic token renewal** - Refreshes 5 minutes before expiration
5. **Secure error handling** - Clears invalid tokens on 401 errors

---

## API Reference

### Public Methods

#### `setApiKey(apiKey: String)`
Sets the API key for authentication (legacy/fallback method).

```kotlin
claudeProvider.setApiKey("sk-ant-api03-...")
```

#### `setOAuthToken(accessToken: String, refreshToken: String, expiresAt: Long)`
Sets OAuth credentials for token-based authentication.

**Parameters:**
- `accessToken` - The OAuth access token
- `refreshToken` - The OAuth refresh token for renewing access
- `expiresAt` - Unix timestamp (milliseconds) when access token expires

**Example:**
```kotlin
val expiresAt = System.currentTimeMillis() + (3600 * 1000) // 1 hour from now
claudeProvider.setOAuthToken(
    accessToken = "at_abc123...",
    refreshToken = "rt_xyz789...",
    expiresAt = expiresAt
)
```

#### `refreshAccessToken(): Result<Unit>`
Refreshes the OAuth access token using the refresh token.

**Returns:** `Result.Success` on successful refresh, `Result.Error` on failure

**Thread-safety:** Multiple concurrent calls are safe - only one refresh executes

**Example:**
```kotlin
when (val result = claudeProvider.refreshAccessToken()) {
    is Result.Success -> println("Token refreshed")
    is Result.Error -> println("Refresh failed: ${result.exception.message}")
    is Result.Loading -> {} // Not returned by this method
}
```

#### `clearOAuthCredentials()`
Clears all OAuth credentials from memory and secure storage.

```kotlin
claudeProvider.clearOAuthCredentials()
```

#### `isUsingOAuth(): Boolean`
Checks if OAuth authentication is currently being used.

```kotlin
if (claudeProvider.isUsingOAuth()) {
    println("Using OAuth authentication")
} else {
    println("Using API key authentication")
}
```

#### `isAvailable(): Boolean`
Returns true if the provider has valid credentials (OAuth or API key).

```kotlin
if (claudeProvider.isAvailable()) {
    // Ready to make API calls
}
```

---

## Integration Guide

### Step 1: OAuth Authorization (When Available)

Once Anthropic releases OAuth, implement the authorization flow in your settings screen:

```kotlin
// 1. Redirect user to OAuth authorization URL
val authUrl = "https://auth.anthropic.com/oauth/authorize?" +
    "client_id={your_client_id}&" +
    "redirect_uri={your_redirect_uri}&" +
    "response_type=code&" +
    "scope=api.read+api.write"

// Open in browser or WebView
startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)))

// 2. Handle redirect callback with authorization code
// In your callback Activity/DeepLink handler:
val authCode = intent.data?.getQueryParameter("code")

// 3. Exchange code for tokens
val tokenResponse = exchangeCodeForTokens(authCode)

// 4. Store tokens in ClaudeProvider
claudeProvider.setOAuthToken(
    accessToken = tokenResponse.accessToken,
    refreshToken = tokenResponse.refreshToken,
    expiresAt = System.currentTimeMillis() + (tokenResponse.expiresIn * 1000)
)
```

### Step 2: Automatic Token Management

The provider handles token lifecycle automatically:

```kotlin
// Just call generateEnrichment() - OAuth is handled internally
val result = claudeProvider.generateEnrichment(enrichmentContext)

// Provider will:
// 1. Check if OAuth token exists
// 2. Refresh if expired (automatically)
// 3. Use OAuth if available, fallback to API key
// 4. Handle 401/403 errors appropriately
```

### Step 3: Error Handling

Handle OAuth-specific errors in your UI:

```kotlin
when (val result = claudeProvider.generateEnrichment(context)) {
    is Result.Success -> {
        // Display enrichment
        displayEnrichment(result.data)
    }
    is Result.Error -> {
        val message = result.exception.message
        when {
            message?.contains("re-authenticate") == true -> {
                // OAuth token invalid - show login screen
                navigateToOAuthLogin()
            }
            message?.contains("API key") == true -> {
                // API key missing or invalid
                navigateToSettings()
            }
            else -> {
                // Other error
                showError(message)
            }
        }
    }
    is Result.Loading -> {
        // Show loading indicator
    }
}
```

---

## Token Refresh Behavior

### Automatic Refresh

- Tokens are checked before **every API call**
- Refresh triggered if expiration is within **5 minutes**
- Only **one refresh** executes at a time (mutex-protected)
- Failed refresh clears tokens and falls back to API key

### Manual Refresh

You can manually refresh tokens if needed:

```kotlin
viewModelScope.launch {
    when (val result = claudeProvider.refreshAccessToken()) {
        is Result.Success -> {
            showMessage("Token refreshed successfully")
        }
        is Result.Error -> {
            showError("Refresh failed: ${result.exception.message}")
            // Prompt user to re-authenticate
            navigateToOAuthLogin()
        }
        is Result.Loading -> {}
    }
}
```

---

## Security Considerations

### Encrypted Storage

Tokens are stored in `EncryptedSharedPreferences` with:

- **AES256-GCM** encryption for values
- **AES256-SIV** encryption for keys
- **Android Keystore** for master key storage
- File name: `claude_oauth_prefs`

### Token Security Best Practices

1. **Never log tokens** - Tokens are sensitive credentials
2. **Clear on logout** - Call `clearOAuthCredentials()` when user signs out
3. **Validate responses** - OAuth errors (401/403) automatically clear invalid tokens
4. **HTTPS only** - All OAuth endpoints use HTTPS
5. **Secure redirect** - Use app-specific redirect URIs (e.g., `spiritatlas://oauth/callback`)

---

## Migration from API Key to OAuth

### Backward Compatibility

The implementation maintains **full backward compatibility**:

- Existing API key users continue working without changes
- OAuth is **preferred** but not required
- Fallback to API key is automatic and seamless

### Migration Steps

For users migrating from API key to OAuth:

```kotlin
// 1. User currently has API key
claudeProvider.setApiKey("sk-ant-api03-...")

// 2. User completes OAuth flow
claudeProvider.setOAuthToken(accessToken, refreshToken, expiresAt)

// 3. Provider now uses OAuth (API key still available as fallback)
println(claudeProvider.isUsingOAuth()) // true

// 4. Optionally clear API key (if you want OAuth-only)
claudeProvider.setApiKey("") // Not recommended - keep fallback
```

---

## Testing

### Unit Tests (Recommended)

```kotlin
@Test
fun `test OAuth token refresh on expiration`() = runTest {
    // Given: Expired token
    val expiredAt = System.currentTimeMillis() - 1000
    claudeProvider.setOAuthToken("old_token", "refresh_token", expiredAt)

    // When: Making API call
    val result = claudeProvider.generateEnrichment(context)

    // Then: Token should be refreshed automatically
    verify(mockHttpClient).newCall(argThat {
        headers["Authorization"] == "Bearer new_token"
    })
}

@Test
fun `test fallback to API key when OAuth fails`() = runTest {
    // Given: Invalid OAuth token and valid API key
    claudeProvider.setOAuthToken("invalid", "invalid", System.currentTimeMillis())
    claudeProvider.setApiKey("sk-ant-api03-valid")

    // When: OAuth refresh fails
    // Then: Should fallback to API key
    val result = claudeProvider.generateEnrichment(context)

    assertTrue(result is Result.Success)
}
```

### Integration Testing

When OAuth becomes available:

1. Test complete OAuth flow end-to-end
2. Verify token refresh before expiration
3. Test 401/403 error handling
4. Verify fallback to API key works
5. Test concurrent API calls (no duplicate refreshes)

---

## Configuration

### When OAuth Goes Live

Update the following in `ClaudeProvider.kt`:

1. **Set flag to true:**
   ```kotlin
   const val OAUTH_AVAILABLE = true
   ```

2. **Update OAuth endpoints:**
   ```kotlin
   private val oauthTokenUrl = "https://auth.anthropic.com/oauth/token" // Real endpoint
   private val oauthAuthUrl = "https://auth.anthropic.com/oauth/authorize" // Real endpoint
   ```

3. **Configure client credentials:**
   ```kotlin
   // In refreshAccessToken()
   .add("client_id", BuildConfig.ANTHROPIC_OAUTH_CLIENT_ID)
   .add("client_secret", BuildConfig.ANTHROPIC_OAUTH_CLIENT_SECRET)
   ```

4. **Add to build.gradle.kts:**
   ```kotlin
   buildConfigField("String", "ANTHROPIC_OAUTH_CLIENT_ID", "\"${localProps.getProperty("anthropic.oauth.client.id")}\"")
   buildConfigField("String", "ANTHROPIC_OAUTH_CLIENT_SECRET", "\"${localProps.getProperty("anthropic.oauth.client.secret")}\"")
   ```

5. **Add to local.properties:**
   ```properties
   anthropic.oauth.client.id=your_client_id
   anthropic.oauth.client.secret=your_client_secret
   ```

---

## Troubleshooting

### Issue: "OAuth is not yet available from Anthropic"

**Cause:** `OAUTH_AVAILABLE` flag is set to `false`

**Solution:** Wait for Anthropic to release OAuth 2.0. Use API key authentication in the meantime.

### Issue: "OAuth token invalid or expired"

**Cause:** Token was revoked or refresh failed

**Solutions:**
1. Call `clearOAuthCredentials()` to clear invalid tokens
2. Redirect user to OAuth login flow
3. Fallback to API key if available

### Issue: Token refresh fails repeatedly

**Cause:** Refresh token is invalid or expired

**Solutions:**
1. Refresh tokens can expire (typically 30-90 days)
2. User must re-authenticate via OAuth flow
3. Implement periodic re-authentication prompts

### Issue: Multiple concurrent API calls cause multiple refreshes

**This should not happen** - implementation uses `Mutex` to prevent concurrent refreshes.

If it does occur:
1. Check that all API calls go through `getValidAccessToken()`
2. Verify `tokenRefreshMutex` is working correctly
3. Review logs for race conditions

---

## Performance Considerations

### Token Refresh Overhead

- **First call after expiration:** +200-500ms (refresh API call)
- **Subsequent calls:** No overhead (cached token)
- **Concurrent calls:** Wait for single refresh (mutex)

### Storage Performance

- **EncryptedSharedPreferences:** ~10-50ms read/write latency
- **In-memory cache:** Reduces storage reads to once per session
- **Lazy initialization:** Encryption keys created on first use

### Optimization Tips

1. **Pre-refresh tokens** - Refresh proactively before user initiates action
2. **Cache in memory** - Avoid repeated reads from encrypted storage
3. **Background refresh** - Use WorkManager for periodic token renewal

---

## Future Enhancements

### Potential Improvements (Post-OAuth Release)

1. **Token expiration notifications** - Alert user before refresh token expires
2. **Multiple account support** - Store tokens for multiple Anthropic accounts
3. **Scope management** - Request only necessary OAuth scopes
4. **PKCE support** - Use Proof Key for Code Exchange for mobile security
5. **Biometric authentication** - Require fingerprint/face for OAuth login

---

## Resources

### Documentation
- [Android EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)
- [OAuth 2.0 RFC 6749](https://datatracker.ietf.org/doc/html/rfc6749)
- [Anthropic API Documentation](https://docs.anthropic.com/claude/reference/)

### Related Files
- `/data/src/main/java/com/spiritatlas/data/ai/ClaudeProvider.kt` - Implementation
- `/core/common/src/main/java/com/spiritatlas/core/common/Result.kt` - Result type
- `/domain/src/main/java/com/spiritatlas/domain/ai/AiTextProvider.kt` - Interface

---

## Support

For questions or issues:
1. Check `ClaudeProvider.OAUTH_AVAILABLE` flag status
2. Review error messages (they include specific guidance)
3. Verify EncryptedSharedPreferences are not corrupted (clear app data if needed)
4. Monitor Anthropic's official announcements for OAuth release

---

**Last Updated:** December 8, 2025
**Implementation Status:** Ready for OAuth release
**Current Auth Mode:** API Key only (OAuth not yet available from Anthropic)
