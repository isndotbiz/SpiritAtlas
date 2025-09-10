# Test Plan

## Unit Tests
- Numerology: Chaldean, Pythagorean mappings and life path
- Astrology: Ayanamsa growth, sidereal conversion, aspect detection
- Energy Profiling: Type and profile mapping

## Integration
- Consent: grant/revoke flow persists and updates observers
- AI Provider: selection (Auto/Cloud/Local) respected in CombinedAiProvider
- Worker: EnrichmentWorker respects consent and queues successfully

## Manual
- First run with no consents: no network calls
- Switch provider mode in Consent screen and verify Home label updates
- Run enrichment and verify toast + logs
