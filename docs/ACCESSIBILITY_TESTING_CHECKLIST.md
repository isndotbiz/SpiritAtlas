# Accessibility Testing Checklist

This comprehensive checklist ensures SpiritAtlas meets WCAG 2.1 Level AAA standards.

## Quick Reference

- **WCAG AA**: Contrast 4.5:1 (normal), 3:1 (large); Touch 44dp
- **WCAG AAA**: Contrast 7:1 (normal), 4.5:1 (large); Touch 48dp
- **SpiritAtlas Target**: WCAG AAA+ with 48dp minimum touch targets

## 1. Visual Accessibility

### 1.1 Color Contrast (WCAG 2.1 Success Criterion 1.4.6 - AAA)

#### Requirements
- [ ] Normal text: minimum 7:1 contrast ratio
- [ ] Large text (18pt+): minimum 4.5:1 contrast ratio
- [ ] UI components: minimum 3:1 contrast ratio
- [ ] Icons and graphics: minimum 3:1 contrast ratio

#### Testing Steps
1. Use Android Accessibility Scanner
2. Manual verification with Color Contrast Analyzer
3. Test in High Contrast mode
4. Test with color blindness simulators

#### Common Issues to Check
- [ ] Purple text on dark backgrounds
- [ ] Gold/yellow text on light backgrounds
- [ ] Gradient text readability
- [ ] Disabled button contrast
- [ ] Placeholder text contrast
- [ ] Border and divider visibility

### 1.2 High Contrast Mode Support

- [ ] High contrast theme available in settings
- [ ] All content visible in high contrast
- [ ] No information conveyed by color alone
- [ ] Patterns/textures used alongside colors
- [ ] Borders and outlines clearly visible

### 1.3 Visual Focus Indicators (WCAG 2.1 Success Criterion 2.4.7)

- [ ] All interactive elements show focus
- [ ] Focus indicator minimum 2px wide
- [ ] Focus indicator contrast ratio 3:1
- [ ] Focus order is logical and predictable
- [ ] No keyboard traps

## 2. Touch Target Accessibility

### 2.1 Touch Target Sizes (WCAG 2.1 Success Criterion 2.5.5 - AAA)

#### Requirements
- [ ] All interactive elements minimum 48x48dp
- [ ] Spacing between targets minimum 8dp
- [ ] No overlapping touch targets
- [ ] Clear visual boundaries

#### Elements to Verify
- [ ] Buttons
- [ ] Icon buttons
- [ ] Text fields
- [ ] Checkboxes
- [ ] Radio buttons
- [ ] Switches
- [ ] Tabs
- [ ] List items
- [ ] Card actions
- [ ] Dropdown triggers

### 2.2 Touch Target Testing

1. Enable "Show layout bounds" in Developer Options
2. Measure actual touch target sizes
3. Test with accessibility scanner
4. Manual testing with touch exploration

## 3. Screen Reader Accessibility

### 3.1 TalkBack Support

#### Requirements
- [ ] All interactive elements have content descriptions
- [ ] Content descriptions are clear and concise
- [ ] State changes announced properly
- [ ] Headings marked semantically
- [ ] Reading order is logical
- [ ] No redundant announcements
- [ ] Custom TalkBack hints where helpful

#### Testing Steps
1. Enable TalkBack: Settings > Accessibility > TalkBack
2. Navigate entire app using swipe gestures
3. Test all interactive elements
4. Verify logical reading order
5. Test with eyes closed

#### Screen-by-Screen Checklist

**Home Screen**
- [ ] App title announced
- [ ] Greeting personalized and clear
- [ ] Moon phase description complete
- [ ] Daily insights readable
- [ ] Profile avatars descriptive
- [ ] Compatibility scores clear
- [ ] All buttons actionable

**Profile Screen**
- [ ] Form labels clear
- [ ] Required fields indicated
- [ ] Field errors announced
- [ ] Date/time picker accessible
- [ ] Dropdown selections clear
- [ ] Progress indicator readable
- [ ] Save button state clear

**Settings Screen**
- [ ] Settings categories clear
- [ ] Toggle states announced
- [ ] Selected options indicated
- [ ] API key field secure
- [ ] Test results announced

**Compatibility Screen**
- [ ] Profile selection clear
- [ ] Score announced with context
- [ ] Analysis sections navigable
- [ ] Chakra indicators descriptive
- [ ] Recommendations clear

### 3.2 Content Description Best Practices

#### Good Examples
```kotlin
// ✅ GOOD: Clear, concise, informative
contentDescription = "Create new profile"
contentDescription = "Compatibility score: 87 percent, excellent match"
contentDescription = "Root chakra, active"
contentDescription = "Full Moon, 100 percent illuminated"

// ✅ GOOD: State included
contentDescription = "Dark mode switch, currently on"
contentDescription = "Profile completion: 75 percent"

// ✅ GOOD: Position in collection
contentDescription = "Sarah's profile, item 1 of 3"
```

#### Bad Examples
```kotlin
// ❌ BAD: Too verbose
contentDescription = "This is a button that when pressed will create a new profile for you"

// ❌ BAD: Includes control type (TalkBack adds this)
contentDescription = "Settings button"  // TalkBack says "Settings button button"

// ❌ BAD: Missing state
contentDescription = "Dark mode"  // Is it on or off?

// ❌ BAD: No description on decorative image
// Decorative images should have contentDescription = null or be marked as decorative
```

## 4. Keyboard Navigation

### 4.1 Keyboard Support (WCAG 2.1 Success Criterion 2.1.1)

- [ ] All functionality available via keyboard
- [ ] Tab order is logical
- [ ] Shift+Tab moves backwards
- [ ] Enter activates buttons/links
- [ ] Space toggles checkboxes/switches
- [ ] Arrow keys navigate lists/grids
- [ ] Escape closes modals/dialogs
- [ ] No keyboard traps

### 4.2 Focus Management

- [ ] Focus visible at all times
- [ ] Focus not lost on state changes
- [ ] Focus restored after modal close
- [ ] Focus trapped in modals
- [ ] Skip navigation links available

## 5. Dynamic Type Support

### 5.1 Text Scaling (WCAG 2.1 Success Criterion 1.4.4)

#### Requirements
- [ ] Support up to 200% text scaling
- [ ] No text truncation at 200%
- [ ] No overlapping text
- [ ] Layout adapts to large text
- [ ] No horizontal scrolling

#### Testing Steps
1. Settings > Display > Font size > Largest
2. Settings > Display > Display size > Largest
3. Navigate all screens
4. Verify all text visible
5. Test all interactions

### 5.2 Layout Reflow

- [ ] Single column on large text
- [ ] Adequate spacing maintained
- [ ] Buttons remain accessible
- [ ] No content hidden
- [ ] Scrolling still works

## 6. Motion and Animation

### 6.1 Reduced Motion (WCAG 2.1 Success Criterion 2.3.3)

- [ ] Respect "Remove animations" system setting
- [ ] Essential motion preserved
- [ ] No automatic animations
- [ ] User-triggered animations only
- [ ] No flashing content (3Hz rule)

### 6.2 Testing Reduced Motion
1. Settings > Accessibility > Remove animations
2. Navigate all screens
3. Verify animations disabled
4. Verify essential motion preserved

## 7. Form Accessibility

### 7.1 Form Inputs

- [ ] All labels clear and descriptive
- [ ] Required fields indicated
- [ ] Error messages clear and specific
- [ ] Error messages announced
- [ ] Field instructions provided
- [ ] Autocomplete attributes set
- [ ] Input types correct

### 7.2 Error Handling

- [ ] Errors announced to screen readers
- [ ] Focus moved to first error
- [ ] Error messages persist
- [ ] Corrections suggested
- [ ] Error state visually clear

## 8. Multimedia Accessibility

### 8.1 Images

- [ ] All images have alt text
- [ ] Decorative images marked
- [ ] Complex images have extended descriptions
- [ ] Image buttons have clear labels

### 8.2 Icons

- [ ] All functional icons labeled
- [ ] Icon-only buttons descriptive
- [ ] Decorative icons hidden from screen readers

## 9. Time-Based Accessibility

### 9.1 Timing Adjustable (WCAG 2.1 Success Criterion 2.2.1)

- [ ] No time limits (or adjustable)
- [ ] Auto-refresh can be disabled
- [ ] Timeouts have warnings
- [ ] User can extend time limits

## 10. Help and Documentation

### 10.1 Context-Sensitive Help

- [ ] Help available on complex screens
- [ ] Tooltips for unfamiliar terms
- [ ] Instructions clear and concise
- [ ] Examples provided

### 10.2 Error Prevention

- [ ] Confirmation for destructive actions
- [ ] Ability to review before submit
- [ ] Ability to undo actions
- [ ] Clear labels prevent errors

## Testing Tools

### Automated Testing
- [ ] Android Accessibility Scanner
- [ ] Espresso Accessibility Checks
- [ ] Compose accessibility testing

### Manual Testing
- [ ] TalkBack navigation
- [ ] Keyboard-only navigation
- [ ] Large text testing
- [ ] High contrast testing
- [ ] Color blindness simulation

### Third-Party Testing
- [ ] WAVE browser extension
- [ ] axe DevTools
- [ ] Color Oracle (color blindness)

## Common Issues and Fixes

### Issue: Button Too Small
**Fix**: Use `Modifier.minimumTouchTarget()` or set minimum size to 48dp

### Issue: Low Contrast Text
**Fix**: Use High Contrast theme or adjust colors to meet 7:1 ratio

### Issue: Missing Content Description
**Fix**: Add descriptive `contentDescription` to all interactive elements

### Issue: Illogical Reading Order
**Fix**: Restructure layout or use `Modifier.semantics` to adjust order

### Issue: Keyboard Trap
**Fix**: Ensure modal dialogs have proper focus management

### Issue: Text Truncation at Large Sizes
**Fix**: Use `maxLines` with ellipsis or allow text to wrap

## Compliance Checklist

### WCAG 2.1 Level A
- [ ] All Level A criteria met

### WCAG 2.1 Level AA
- [ ] All Level AA criteria met
- [ ] Contrast ratios 4.5:1 (normal), 3:1 (large)
- [ ] Touch targets 44x44dp minimum

### WCAG 2.1 Level AAA (Target)
- [x] All Level AAA criteria met
- [x] Contrast ratios 7:1 (normal), 4.5:1 (large)
- [x] Touch targets 48x48dp minimum
- [x] High contrast mode available
- [x] Comprehensive keyboard support

## Sign-Off

### Tested By
- [ ] Developer
- [ ] QA Team
- [ ] Accessibility Specialist
- [ ] Users with Disabilities

### Test Date
- Date: __________
- Version: __________
- Result: Pass / Fail / Needs Work

### Notes
_Document any issues found, fixes applied, and areas needing improvement_

---

## Quick Test Scripts

### Script 1: TalkBack Basic Navigation (5 minutes)
1. Enable TalkBack
2. Navigate Home screen with swipes
3. Tap on 3 different interactive elements
4. Navigate to Profile screen
5. Fill out one form field
6. Navigate back to Home
7. Disable TalkBack

### Script 2: Keyboard Navigation (5 minutes)
1. Connect external keyboard
2. Use Tab to navigate Home screen
3. Press Enter to activate a button
4. Use Shift+Tab to go backwards
5. Navigate to Settings
6. Use arrow keys in dropdowns
7. Press Escape to close modal

### Script 3: Large Text (5 minutes)
1. Set font size to Largest
2. Navigate all main screens
3. Verify no text truncation
4. Verify all buttons accessible
5. Verify no overlapping content

### Script 4: High Contrast (5 minutes)
1. Enable High Contrast mode in app settings
2. Navigate all main screens
3. Verify all text readable
4. Verify all icons visible
5. Verify all interactive elements have clear boundaries

---

**Last Updated**: December 2025
**Next Review**: Quarterly
**Owner**: Accessibility Team
