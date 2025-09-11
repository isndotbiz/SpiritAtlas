# TODO Backlog — SpiritAtlas

A prioritized, actionable backlog to continuously improve app flow and quality. Use P0 (highest) → P3 (nice to have).

## P0 — Foundations & Guardrails
- Architecture: Enforce module boundaries with build checks (no circular deps)
  - Steps: Add Gradle module dependency rules, verify via CI
  - Acceptance: Build fails on violations
- Security: Integrate OWASP Dependency-Check in CI
  - Steps: Add CI job, publish reports
  - Acceptance: CI fails for CVSS ≥7
- Privacy: Age‑gate tantric content and add explicit consent copy
  - Steps: UX copy, toggle, storage of consent state
  - Acceptance: No tantric content without age gate + consent
- Reliability: Add baseline profile and startup tracing
  - Steps: Macrobenchmark project, baseline profiles
  - Acceptance: Startup time regression detected in CI

## P1 — Features & Performance
- AI: Configurable model lists for OpenRouter and Ollama
  - Steps: Settings screen, persisted config, provider validation
  - Acceptance: User can switch models safely
- AI: Offline prompts and graceful fallback
  - Steps: Local template prompts, retry/backoff, Result typing
  - Acceptance: No crashes without network
- Performance: Cache audit for compatibility engine
  - Steps: Identify hot paths, add profilers, cache results
  - Acceptance: <50% recomputation on repeated runs
- Background: WorkManager reliability tuning
  - Steps: Constraints, backoff, idempotency
  - Acceptance: Jobs survive process death and retries

## P2 — Quality & DX
- Testing: Raise coverage of core modules to ≥80%
  - Steps: Add missing tests for astro/numerology edge cases
  - Acceptance: JaCoCo report ≥80% lines
- UI: Snapshot tests for Compose surfaces
  - Steps: Paparazzi/Compose testing
  - Acceptance: Snapshots generated and reviewed in PRs
- Tooling: Pre‑commit hooks for ktlint/detekt/lint
  - Steps: Add .pre-commit-config.yaml; docs in README
  - Acceptance: Hooks guard local commits
- Internationalization: Extract strings and add RTL checks
  - Steps: Centralize strings, enable RTL in developer options
  - Acceptance: No clipped text in RTL

## P3 — Polish & Content
- Accessibility: Large font scaling, TalkBack labels audit
  - Steps: Use semantic labels and content descriptions
  - Acceptance: Accessibility scanner passes
- UX: Dynamic color verification and design tokens
  - Steps: Audit Material 3 surfaces, add tokens
  - Acceptance: Theme parity light/dark
- Compliance: Add Privacy Policy and reinforce disclaimers
  - Steps: Link in settings, docs updates
  - Acceptance: Policy visible and versioned

## Housekeeping
- Keep a Changelog — extract README changelog into CHANGELOG.md
- Release process — semantic versioning and tagging
- Screenshots — add to docs and README badges once CI/CD is live

Assign owners per task and reference module tags (app, data, domain, core:astro, feature:compatibility, etc.).
