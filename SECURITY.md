# Security

## Data Protection
- EncryptedSharedPreferences (AES-256) for user data
- No plaintext storage of PII
- API keys loaded from local.properties via BuildConfig

## Network Security
- HTTPS for all remote calls
- Prefer certificate pinning for production backends
- No PII sent without explicit consent

## Logging
- Avoid logging sensitive data
- Different behavior in debug vs release
