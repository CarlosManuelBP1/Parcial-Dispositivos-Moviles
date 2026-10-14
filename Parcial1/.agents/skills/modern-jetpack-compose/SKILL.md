---
name: modern-jetpack-compose
description: >
  Guides writing, reviewing, debugging, refactoring, and reasoning about
  Android UI code built with Jetpack Compose. Covers modern APIs, state
  management, side effects, recomposition, navigation, Material 3,
  accessibility, performance, testing, and Kotlin best practices.
  Use when creating, modifying, reviewing, or debugging a Jetpack Compose
  project in Android Studio.
metadata:
  author: Carlos <carlosmanbp@gmail.com>
  version: "1.3"
---

# Modern Jetpack Compose

Review, generate, debug, and refactor Jetpack Compose code for correctness,
maintainability, accessibility, performance, and compatibility with the
existing Android project.

Prioritize genuine bugs, lifecycle problems, crashes, data-flow issues,
accessibility defects, and meaningful performance problems. Avoid nitpicking
style or recommending architectural changes without a clear benefit.

## Core Principles

1. Inspect the existing project before making assumptions.
2. Preserve working behavior, existing architecture, and established conventions
   unless a change is necessary or explicitly requested.
3. Prefer modern, stable Android APIs compatible with the project's current
   dependencies and minimum SDK.
4. Explain the reason and impact of significant changes.
5. Do not invent files, dependencies, API availability, test results, or project
   configurations.
6. Distinguish confirmed defects from potential risks and optional improvements.
7. Make the smallest coherent change that solves the problem.
8. Do not rewrite unrelated code or expand the scope of a task without approval.

## Project Inspection

Before reviewing or generating code, inspect the relevant project files when
they are available.

Check:

- `settings.gradle.kts` and the root `build.gradle.kts`.
- The app module's `build.gradle.kts`.
- `gradle/libs.versions.toml`, if present.
- Kotlin, Gradle, Android Gradle Plugin, Compose BOM, and dependency versions.
- `AndroidManifest.xml`, minimum SDK, target SDK, and relevant permissions.
- Existing package structure, navigation, theme, architecture, and coding patterns.
- Relevant tests, resources, and existing implementations.

Use the project's actual configuration as the source of truth.

### Dependency and API compatibility

- Do not upgrade Compose BOM, Kotlin, Gradle, Android Gradle Plugin, or SDK
  versions simply because newer versions exist.
- Prefer stable APIs compatible with the existing project.
- Use prerelease, experimental, or deprecated APIs only when justified by the
  requirements and the project's configuration.
- Verify API availability against the installed dependencies or authoritative
  documentation when uncertain.
- Do not assume that APIs available in the latest documentation are available
  in the current project.
- Do not modify version catalogs or build files unless required or explicitly
  requested.
- Never introduce third-party dependencies without asking the user first.
- Explain any required dependency changes before applying them.

## Review and Generation Workflow

### Reviewing existing code

Follow this sequence when relevant:

1. Deprecated APIs and dependency compatibility → `references/api.md`
2. Composable structure and composition patterns → `references/composables.md`
3. State management and data flow → `references/state.md`
4. Side effects and coroutine usage → `references/effects.md`
5. Recomposition and stability → `references/recomposition.md`
6. Navigation and type safety → `references/navigation.md`
7. Material 3, adaptive layouts, and theming → `references/design.md`
8. Accessibility and semantics → `references/accessibility.md`
9. Performance and lazy layouts → `references/performance.md`
10. Kotlin correctness and idioms → `references/kotlin.md`
11. Testing, lint, and code hygiene → `references/hygiene.md`

Review only the areas relevant to the task. For a full review, follow the
sequence above and skip checks that do not apply.

### Generating new code

1. Inspect the existing project and relevant reference files.
2. Identify the required behavior, constraints, and integration points.
3. Reuse existing architecture, dependencies, theme, and navigation where
   appropriate.
4. Generate code compatible with the project's configuration.
5. Include necessary imports, state handling, error handling, and accessibility.
6. Explain any new files, dependencies, or configuration changes.
7. Verify the result using available build, lint, and test tools when possible.

### Targeted changes

For a small bug fix or a narrowly scoped feature, inspect only the relevant
files and references. Do not conduct or apply a full architectural migration
unless requested.

## Architecture and Data Flow

Prefer unidirectional data flow (UDF) and clear separation between UI and
business logic.

- Use ViewModel-based screen state when the screen's complexity or business
  logic benefits from it.
- Prefer immutable UI state and explicit UI events.
- Use `StateFlow` for observable screen state when appropriate.
- Collect lifecycle-aware UI state with `collectAsStateWithLifecycle()` when
  the project uses Android lifecycle integration and the required dependency
  is available.
- Keep composables focused on rendering state and emitting events.
- Keep business logic out of composables when it belongs in a ViewModel,
  domain layer, or repository.
- Use repositories and domain abstractions when they provide meaningful
  separation, testability, or reuse.
- Avoid unnecessary layers, interfaces, ViewModels, or abstractions for trivial
  functionality.
- Do not introduce MVVM, MVI, Clean Architecture, or another architecture solely
  to conform to this skill.
- Preserve an existing architecture when it works and remains maintainable.

### State ownership

- Hoist state to the lowest common ancestor that needs to own or share it.
- Use `remember` for state that should survive recomposition but not necessarily
  configuration changes.
- Use `rememberSaveable` for small UI state that should survive supported
  activity or process recreation scenarios.
- Use ViewModel state for screen-level state that must survive configuration
  changes and is appropriately owned by the ViewModel.
- Avoid duplicating the same source of truth across composables and ViewModels.
- Do not place activity, view, or composable references in a ViewModel.
- Handle loading, success, empty, and error states when the feature requires
  them.

## Jetpack Compose APIs and Composable Structure

- Prefer current stable Compose APIs compatible with the project.
- Follow composable naming conventions and keep composables readable.
- Extract reusable or complex UI into dedicated composables.
- Give substantial or independently reusable components their own files when
  this improves maintainability.
- Small private helper composables may remain in the same file when appropriate.
- Keep UI rendering declarative and avoid manually modifying views or the
  composition tree.
- Use `Modifier` parameters appropriately and preserve modifier ordering when
  it affects layout, drawing, input, or semantics.
- Do not create unnecessary wrappers or composables that add no meaningful
  reuse or readability.
- Avoid excessive parameters, deeply nested composables, and oversized screen
  functions when they make code difficult to understand.

## Side Effects and Coroutines

- Keep composables free of unintended side effects.
- Use `LaunchedEffect` for suspend work tied to a composable's lifecycle and
  keyed to the values that determine when it should restart.
- Use `DisposableEffect` when a registered resource or listener requires cleanup.
- Use `SideEffect` only to publish Compose state to non-Compose code after a
  successful recomposition.
- Use `rememberCoroutineScope` for UI-triggered coroutine work that should be
  cancelled when the calling composable leaves the composition.
- Prefer `viewModelScope` for work owned by a ViewModel.
- Use `produceState` when external or asynchronous data must be converted into
  Compose state and this API fits the use case.
- Do not use effects for ordinary calculations that can be performed directly
  during composition.
- Do not launch coroutines directly from a composable body.
- Avoid unstable or overly broad effect keys.
- Ensure cancellation, cleanup, and error handling are appropriate for the task.

## Recomposition and Performance

Prioritize measurable or plausible performance problems rather than theoretical
micro-optimizations.

- Use stable, unique item keys in lazy layouts when items have persistent
  identities and stable keys are appropriate.
- Avoid expensive calculations in frequently recomposed composables when they
  can be safely moved or cached.
- Use `remember` only when caching provides a meaningful benefit or preserves
  required state.
- Use `derivedStateOf` when it reduces unnecessary updates from frequently
  changing state, not as a default wrapper for every calculation.
- Avoid unnecessary state reads in frequently recomposed layout or drawing
  scopes.
- Prefer immutable models and stable parameters where practical.
- Do not add `@Stable` or `@Immutable` unless the type actually satisfies the
  corresponding contract.
- Avoid unnecessary allocations, nested scroll containers, and duplicated
  expensive work in lazy lists.
- Do not claim that every recomposition is a performance problem.
- Do not recommend compiler or stability configuration changes without a
  concrete reason.

Example:

Before:

```kotlin
LazyColumn {
    items(books) { book ->
        BookItem(book)
    }
}
```

After, when `Book.id` is unique and stable:

```kotlin
LazyColumn {
    items(
        items = books,
        key = { book -> book.id }
    ) { book ->
        BookItem(book)
    }
}
```

The key is appropriate when it represents a stable, unique identity for each
item. Do not invent an identifier or use a non-unique property as the key.

## Navigation

- Prefer type-safe Navigation Compose routes using Kotlin Serialization when
  supported by the project's Navigation version and appropriate for the feature.
- Inspect the existing navigation implementation before proposing changes.
- Do not replace working string-based routes solely to satisfy this rule.
- Avoid unnecessary navigation dependencies or migrations.
- Pass only the information needed to identify a destination.
- Retrieve complex screen data from the appropriate state holder or repository
  instead of passing entire domain objects through routes.
- Handle back navigation, deep links, nested graphs, and saved state when
  relevant to the feature.
- Avoid retaining activity or composable references in navigation state.
- Verify that route types and navigation APIs are compatible with the installed
  Navigation Compose version.

## Material Design and Adaptive UI

- Prefer Material 3 components and theming for new UI unless the project
  explicitly requires another design system.
- Use Material 3 Expressive components only when they are available in the
  project's dependencies and appropriate for the design.
- Do not assume that every Expressive component is part of the standard stable
  Material 3 artifact.
- Preserve the existing visual identity, colors, typography, shapes, and
  spacing unless a redesign is requested.
- Use theme tokens rather than hardcoded colors when appropriate.
- Support dark mode when required by the application.
- Consider window size classes and adaptive layouts for screens that must work
  across phones, tablets, foldables, or resizable windows.
- Respect system insets, edge-to-edge requirements, and keyboard behavior.
- Avoid replacing an entire theme or design system for a small UI change.

## Accessibility

- Provide meaningful `contentDescription` values for informative images.
- Use `contentDescription = null` for genuinely decorative images.
- Give interactive controls accessible labels and appropriate semantics.
- Ensure custom controls expose their roles, state, and actions to accessibility
  services.
- Use touch targets that meet applicable Android accessibility guidance.
- Do not communicate important information through color alone.
- Respect font scaling and readable text contrast.
- Use string resources for user-facing text in production applications when
  consistent with the project's localization approach.
- Avoid redundant TalkBack announcements when a parent already provides the
  necessary semantics.
- Test accessibility behavior where tools and project setup permit.

Example:

Decorative image:

```kotlin
Image(
    painter = painterResource(R.drawable.background_decoration),
    contentDescription = null
)
```

Informative image:

```kotlin
Image(
    painter = painterResource(R.drawable.book_cover),
    contentDescription = stringResource(R.string.book_cover_description)
)
```

The description should communicate the image's purpose, not merely repeat its
filename. Use the actual resource names present in the project.

## Kotlin Best Practices

- Prefer readable, idiomatic Kotlin over clever or unnecessarily compact code.
- Use sealed classes or sealed interfaces for closed sets of UI states or events
  when they improve correctness and clarity.
- Use null safety and exhaustive `when` expressions appropriately.
- Avoid `!!` unless non-nullability is guaranteed and the assertion is justified.
- Prefer immutable values and collections where practical.
- Use structured concurrency and appropriate coroutine dispatchers.
- Do not move work to `Dispatchers.IO` or `Dispatchers.Default` without
  considering whether the operation actually requires it.
- Avoid `GlobalScope`, blocking calls on the main thread, and unnecessary
  synchronous work.
- Handle exceptions deliberately; do not silently swallow errors.
- Avoid broad catch blocks that conceal cancellation or programming errors.
- Follow existing Kotlin formatting and naming conventions.
- Do not introduce advanced language features merely because they are available.

## Testing and Verification

- Add or update tests when a change affects business logic, state transitions,
  navigation behavior, or other testable functionality.
- Prefer focused unit tests for ViewModels and business logic when appropriate.
- Use Compose UI tests for important UI behavior when the project supports them.
- Avoid adding test dependencies without approval.
- Run the narrowest relevant tests first, followed by broader verification when
  appropriate.
- Use available Gradle build, test, and lint tasks that match the project.
- Do not report a build or test as successful unless it was actually executed
  and passed.
- If verification cannot be performed, state that clearly and explain what
  remains unverified.
- Distinguish existing failures from failures introduced by the changes whenever
  evidence allows.

## Reference Files

The following reference files may be consulted when they exist and are
accessible in the skill's environment:

- `references/api.md` — deprecated APIs, API compatibility, and replacements.
- `references/composables.md` — composable structure and composition patterns.
- `references/state.md` — state ownership, ViewModel, StateFlow, and UDF.
- `references/effects.md` — side effects, lifecycle, and coroutine usage.
- `references/recomposition.md` — stability, recomposition, and state reads.
- `references/navigation.md` — Navigation Compose and type-safe routes.
- `references/design.md` — Material 3, theming, and adaptive layouts.
- `references/accessibility.md` — semantics, TalkBack, and touch targets.
- `references/performance.md` — lazy layouts and performance optimization.
- `references/kotlin.md` — Kotlin language features and idioms.
- `references/hygiene.md` — testing, lint, and code hygiene.

Important:

- Confirm that a referenced file exists before relying on its contents.
- Read the relevant reference files before reviewing or generating code when
  they are available.
- For targeted tasks, load only the references relevant to the task.
- If a reference is missing or inaccessible, use the instructions in this skill
  and available project evidence instead.
- Never claim to have read a reference file that was not available.

## Review Output Format

Organize findings by file and prioritize actionable issues.

For each finding, include:

1. File path and line number or the nearest identifiable code location.
2. Severity: Critical, High, Medium, or Low.
3. The rule or behavior involved.
4. A concise explanation of the actual problem and its impact.
5. A minimal before-and-after Kotlin snippet when a code change is useful.

Use line numbers only when they can be established from the actual source.
Otherwise, identify the relevant function, composable, or code block.

Severity guidance:

- Critical: security exposure, severe data loss, or a major failure affecting
  core functionality.
- High: crashes, broken critical flows, serious lifecycle problems, or major
  correctness defects.
- Medium: meaningful accessibility, maintainability, compatibility, or
  performance problems.
- Low: minor issues with a concrete benefit and low correction risk.

Do not report speculative issues as confirmed defects. Clearly label uncertain
findings and explain what evidence would confirm them.

Skip files with no actionable findings.

When modifying files directly, summarize the actual changes made instead of
presenting hypothetical before-and-after code as if it had been applied.

### Example finding

#### `app/src/main/java/com/example/books/HomeScreen.kt`

**Medium — Use lifecycle-aware collection for screen state when appropriate.**

Rule: lifecycle-aware state collection.

If this screen collects a `StateFlow` from a ViewModel, and the project supports
the lifecycle Compose integration, lifecycle-aware collection is generally
preferable.

Before:

```kotlin
val uiState by viewModel.uiState.collectAsState()
```

After:

```kotlin
val uiState by viewModel.uiState.collectAsStateWithLifecycle()
```

Use the necessary import and verify that the required lifecycle dependency is
already available. Do not introduce a dependency or claim a lifecycle bug
without checking the project context.

## Final Summary

End each review with a prioritized summary of the most impactful findings.

Include:

- The highest-priority problems and why they matter.
- The recommended order of fixes.
- Relevant compatibility or migration risks.
- Tests, builds, and lint checks actually performed.
- Important checks that remain unverified.

If no actionable issues are found, state that no significant issues were identified
within the scope of the review. Do not invent findings to fill the report.

## General Rule

Correctness and compatibility come first, followed by maintainability,
accessibility, and performance.

Modernize code when doing so provides a clear benefit. Preserve working code
when there is no compelling reason to change it.
