# Social Features Architecture Design
**SpiritAtlas - Spiritual Community & Connection Platform**

Version: 1.0
Date: 2025-12-10
Status: Architecture Design Document

---

## Table of Contents
1. [Executive Summary](#executive-summary)
2. [Core Capabilities](#core-capabilities)
3. [Technical Architecture](#technical-architecture)
4. [Database Schema](#database-schema)
5. [API Endpoints](#api-endpoints)
6. [UI Flows & Components](#ui-flows--components)
7. [Security & Privacy](#security--privacy)
8. [Implementation Timeline](#implementation-timeline)
9. [Integration Strategy](#integration-strategy)
10. [Testing Strategy](#testing-strategy)

---

## Executive Summary

SpiritAtlas social features will transform the app from a personal spiritual tool into a vibrant community platform where users can:
- Discover and connect with spiritually compatible individuals
- Share compatibility insights and spiritual journeys
- Build meaningful relationships through secure messaging
- Create a follower network based on spiritual resonance

**Key Differentiator**: Unlike generic social networks, SpiritAtlas connections are guided by deep spiritual compatibility analysis (numerology, astrology, human design, ayurveda, tantric energy).

**Privacy-First Approach**: All social features are opt-in, with granular privacy controls and end-to-end encryption for messages.

---

## Core Capabilities

### 1. User Social Profiles
- **Public Spiritual Identity**: Shareable spiritual profile with customizable visibility
- **Spiritual Bio**: Share your spiritual journey, practices, and intentions
- **Compatibility Badges**: Display top compatibility scores and spiritual attributes
- **Profile Privacy Levels**:
  - Private (profile library only)
  - Friends Only (followers can see full profile)
  - Public (discoverable in community)
  - Anonymous (share compatibility without identity)

### 2. Follower System
- **Follow/Unfollow Mechanics**: Build your spiritual network
- **Mutual Connections**: Identify reciprocal spiritual bonds
- **Follower Insights**: See compatibility previews for followers
- **Follower Tiers**:
  - Casual Connection (basic profile access)
  - Soul Friend (full compatibility reports)
  - Sacred Circle (private group features - Phase 2)

### 3. Compatibility Sharing
- **Share Reports**: Post compatibility insights to your feed
- **Privacy Controls**: Choose who can see shared reports
- **Anonymous Sharing**: Share insights without revealing identities
- **Compatibility Requests**: Send/receive compatibility check requests
- **Viral Sharing**: "Compare your compatibility with me" links

### 4. Messaging System
- **Direct Messages**: 1-on-1 spiritual conversations
- **Group Conversations**: Sacred circles and spiritual groups (Phase 2)
- **Message Types**:
  - Text messages with spiritual emoji support
  - Compatibility report sharing
  - Spiritual content recommendations (tantric, meditations)
  - Energy check-ins (mood, chakra state)
- **Smart Replies**: AI-suggested responses based on spiritual context
- **Message Encryption**: End-to-end encrypted conversations

### 5. Community Discovery (Phase 2)
- **Compatibility Search**: Find users with high compatibility scores
- **Spiritual Filters**: Search by zodiac, life path number, human design type
- **Location-Based Discovery**: Find spiritual connections nearby
- **Community Events**: Spiritual gatherings, workshops, moon circles

---

## Technical Architecture

### Technology Stack

#### Backend Infrastructure
**Primary Choice: Firebase (Recommended)**
- **Firebase Authentication**: User accounts with social login
- **Cloud Firestore**: Real-time NoSQL database
- **Firebase Cloud Messaging (FCM)**: Push notifications
- **Cloud Functions**: Serverless API logic
- **Firebase Storage**: Profile images and media
- **Firebase Analytics**: User engagement tracking

**Alternative: Custom Backend (More Control)**
- **Framework**: Ktor or Spring Boot (Kotlin)
- **Database**: PostgreSQL with PostGIS (location features)
- **Real-time**: WebSocket or Server-Sent Events
- **Hosting**: Google Cloud Run or AWS Fargate
- **CDN**: CloudFlare for global performance

#### Android Integration
```kotlin
// Core modules to add
dependencies {
    // Firebase BOM for version management
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))

    // Firebase core services
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-functions-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Firebase UI (optional - auth UI components)
    implementation("com.firebaseui:firebase-ui-auth:8.0.2")

    // Messaging & Real-time
    implementation("io.getstream:stream-chat-android-compose:6.0.9") // Optional: Advanced chat UI

    // Image handling
    implementation("io.coil-kt:coil-compose:2.7.0") // Already in project
}
```

#### Architecture Layers

```
┌─────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                     │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ Social Feed  │  │ Profile Card │  │ Chat Screen  │  │
│  │   Screen     │  │  Component   │  │   + ViewModel│  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│                    DOMAIN LAYER                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │ SocialProfile│  │FollowUseCase │  │MessageUseCase│  │
│  │    Model     │  │              │  │              │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
│                                                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │SocialRepo    │  │MessageRepo   │  │CompatShare   │  │
│  │ Interface    │  │  Interface   │  │   Repo       │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│                     DATA LAYER                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │FirestoreRepo │  │ FCM Service  │  │ Local Cache  │  │
│  │Implementation│  │              │  │  (Room DB)   │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│               FIREBASE CLOUD SERVICES                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Firestore   │  │Cloud Functions│ │    FCM       │  │
│  │   Database   │  │   (Node.js)  │  │ Messaging    │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

## Database Schema

### Firestore Collections Structure

#### 1. Users Collection (`users`)
```typescript
{
  userId: string,                    // Firebase Auth UID
  email: string,                     // User email
  createdAt: Timestamp,              // Account creation
  updatedAt: Timestamp,              // Last profile update

  // Social Profile
  socialProfile: {
    username: string,                // Unique @username
    displayName: string,             // Public display name
    bio: string,                     // Spiritual bio (max 500 chars)
    avatarUrl: string,               // Profile image URL
    coverImageUrl: string,           // Cover/banner image

    // Spiritual Identity
    spiritualAttributes: {
      sunSign: string,               // "Aries", "Taurus", etc.
      moonSign: string,
      risingSign: string,
      lifePathNumber: number,        // 1-9, 11, 22, 33
      humanDesignType: string,       // "Generator", "Projector", etc.
      ayurvedicDosha: string,        // "Vata", "Pitta", "Kapha"
      dominantChakra: string,        // "Heart", "Third Eye", etc.
    },

    // Profile Badges
    badges: string[],                // ["Astrology Expert", "Meditation Guide"]
    topCompatibilityTypes: string[], // ["Fire Signs", "Life Path 5"]

    // Privacy Settings
    visibility: "private" | "friends" | "public" | "anonymous",
    showCompatibilityBadges: boolean,
    allowCompatibilityRequests: boolean,
    allowMessages: "everyone" | "followers" | "mutual" | "none",
    showLocation: boolean,

    // Location (optional)
    location: {
      city: string,
      country: string,
      latitude: number,
      longitude: number,
      geohash: string,               // For proximity queries
    },

    // Stats
    stats: {
      followersCount: number,
      followingCount: number,
      compatibilityReportsShared: number,
      messagesCount: number,
      joinedAt: Timestamp,
    }
  },

  // Link to private profile data
  privateProfileId: string,          // References UserProfile in Room DB

  // Settings
  notificationSettings: {
    newFollower: boolean,
    compatibilityRequest: boolean,
    newMessage: boolean,
    weeklyInsights: boolean,
  }
}
```

#### 2. Follows Collection (`follows`)
```typescript
{
  id: string,                        // Auto-generated ID
  followerId: string,                // User who is following
  followingId: string,               // User being followed
  createdAt: Timestamp,              // When follow occurred

  // Compatibility Preview (cached)
  compatibilityPreview: {
    overallScore: number,            // 0-100
    compatibilityLevel: string,      // "Soulmate", "Excellent", etc.
    topStrength: string,             // "Emotional Harmony"
    topChallenge: string,            // "Communication Styles"
  } | null,

  // Relationship metadata
  isMutual: boolean,                 // Both users follow each other
  tier: "casual" | "soul_friend" | "sacred_circle",
  notes: string,                     // Private notes about this connection
}

// Composite Indexes:
// - followerId + createdAt (DESC)
// - followingId + createdAt (DESC)
// - followerId + followingId (unique)
// - followerId + isMutual
```

#### 3. Compatibility Shares Collection (`compatibility_shares`)
```typescript
{
  id: string,                        // Auto-generated ID
  authorId: string,                  // User who shared
  createdAt: Timestamp,              // When shared
  updatedAt: Timestamp,              // Last edit

  // Shared Report Data
  report: {
    profileAId: string,              // Can be author or anonymized
    profileBId: string,              // Can be real user or anonymized
    profileAName: string,            // Display name or "Anonymous"
    profileBName: string,

    // Summary data (not full report)
    overallScore: number,
    compatibilityLevel: string,
    topInsights: string[],           // Top 3 insights
    strengths: string[],             // Top 3 strengths
    challenges: string[],            // Top 3 challenges
  },

  // Privacy & Visibility
  visibility: "public" | "followers" | "mutual" | "private_link",
  anonymizeProfiles: boolean,        // Hide actual identities
  shareLink: string | null,          // Public share link if generated

  // Engagement
  stats: {
    viewCount: number,
    likeCount: number,
    commentCount: number,
    shareCount: number,
  },

  // Optional message
  caption: string,                   // User's thoughts about the compatibility
  tags: string[],                    // ["#soulmate", "#twinflame"]
}

// Composite Indexes:
// - authorId + createdAt (DESC)
// - visibility + createdAt (DESC)
// - shareLink (unique, if not null)
```

#### 4. Messages Collection (`conversations/:conversationId/messages`)
```typescript
// Conversations Collection
{
  conversationId: string,            // Auto-generated or composite userId1_userId2
  participants: string[],            // [userId1, userId2]
  participantsMap: {                 // For queries
    [userId1]: true,
    [userId2]: true,
  },
  createdAt: Timestamp,
  updatedAt: Timestamp,              // Last message time

  // Last Message Preview
  lastMessage: {
    text: string,
    senderId: string,
    timestamp: Timestamp,
    type: "text" | "compatibility_share" | "content_share",
  },

  // Conversation Metadata
  type: "direct" | "group",          // Group for Phase 2
  unreadCount: {
    [userId]: number,                // Unread count per participant
  },

  // Encryption
  encryptionEnabled: boolean,
  encryptionKey: string | null,      // E2E encryption key reference
}

// Messages Subcollection
{
  messageId: string,                 // Auto-generated
  conversationId: string,            // Parent conversation
  senderId: string,                  // Message sender
  timestamp: Timestamp,

  // Message Content
  type: "text" | "compatibility_share" | "content_share" | "system",

  // For text messages
  text: string | null,

  // For compatibility shares
  compatibilityShareId: string | null,

  // For content shares (tantric content, etc.)
  contentId: string | null,
  contentType: string | null,

  // For system messages ("X started following you")
  systemMessageType: string | null,

  // Message metadata
  isEdited: boolean,
  editedAt: Timestamp | null,
  isDeleted: boolean,
  deletedAt: Timestamp | null,

  // Read receipts
  readBy: {
    [userId]: Timestamp,             // When each user read the message
  },

  // Reactions (Phase 2)
  reactions: {
    [emoji]: string[],               // emoji -> array of userIds
  }
}

// Composite Indexes:
// - conversationId + timestamp (ASC/DESC)
// - senderId + timestamp (DESC)
```

#### 5. Notifications Collection (`notifications`)
```typescript
{
  id: string,                        // Auto-generated
  userId: string,                    // Recipient user
  createdAt: Timestamp,

  // Notification Content
  type: "new_follower" | "compatibility_request" | "message" | "like" | "comment",

  // Actor (who triggered the notification)
  actorId: string,
  actorName: string,
  actorAvatarUrl: string,

  // Context
  title: string,                     // "New Follower"
  message: string,                   // "Sarah started following you"

  // Navigation
  actionType: "navigate" | "open_modal" | "none",
  actionTarget: string | null,       // Deep link or screen route

  // State
  isRead: boolean,
  readAt: Timestamp | null,

  // Related entity
  relatedEntityId: string | null,    // followId, messageId, etc.
  relatedEntityType: string | null,
}

// Composite Indexes:
// - userId + isRead + createdAt (DESC)
// - userId + type + createdAt (DESC)
```

### Room Database Extensions (Local Android)

Add new tables to existing `SpiritAtlasDatabase`:

```kotlin
// New entities for local caching

@Entity(
    tableName = "social_profiles",
    indices = [Index("userId"), Index("username")]
)
data class SocialProfileEntity(
    @PrimaryKey val userId: String,
    val username: String,
    val displayName: String,
    val bio: String,
    val avatarUrl: String?,
    val followersCount: Int,
    val followingCount: Int,

    // Cached spiritual attributes
    val spiritualAttributesJson: String,

    // Sync metadata
    val lastSyncedAt: Long,
    val isCached: Boolean = true
)

@Entity(
    tableName = "message_cache",
    indices = [Index("conversationId"), Index("timestamp")]
)
data class MessageCacheEntity(
    @PrimaryKey val messageId: String,
    val conversationId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
    val type: String,
    val isRead: Boolean,

    // Sync status
    val isSynced: Boolean,
    val syncedAt: Long?
)

@Entity(
    tableName = "conversation_cache",
    indices = [Index("updatedAt")]
)
data class ConversationCacheEntity(
    @PrimaryKey val conversationId: String,
    val participantsJson: String,      // JSON array of participant IDs
    val lastMessageText: String,
    val lastMessageTimestamp: Long,
    val unreadCount: Int,
    val updatedAt: Long
)
```

---

## API Endpoints

### Cloud Functions (Firebase) or REST API Structure

#### Authentication & User Management

```typescript
// Cloud Function: createSocialProfile
POST /api/v1/users/profile/social
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  username: string,
  displayName: string,
  bio: string,
  spiritualAttributes: {...},
  visibility: string
}
Response: {
  success: boolean,
  socialProfile: SocialProfile
}

// Cloud Function: updateSocialProfile
PUT /api/v1/users/profile/social
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  displayName?: string,
  bio?: string,
  avatarUrl?: string,
  visibility?: string,
  // ... other updatable fields
}
Response: {
  success: boolean,
  socialProfile: SocialProfile
}

// Cloud Function: getSocialProfile
GET /api/v1/users/:userId/profile
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  socialProfile: SocialProfile,
  isFollowing: boolean,
  isMutual: boolean,
  compatibilityPreview: CompatibilityPreview | null
}
```

#### Follow System

```typescript
// Cloud Function: followUser
POST /api/v1/users/:userId/follow
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  tier: "casual" | "soul_friend"
}
Response: {
  success: boolean,
  followId: string,
  isMutual: boolean,
  compatibilityPreview: CompatibilityPreview
}

// Cloud Function: unfollowUser
DELETE /api/v1/users/:userId/follow
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  success: boolean
}

// Cloud Function: getFollowers
GET /api/v1/users/:userId/followers?page=1&limit=20
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  followers: Array<{
    userId: string,
    username: string,
    displayName: string,
    avatarUrl: string,
    followedAt: Timestamp,
    isMutual: boolean,
    compatibilityPreview: CompatibilityPreview
  }>,
  totalCount: number,
  hasMore: boolean
}

// Cloud Function: getFollowing
GET /api/v1/users/:userId/following?page=1&limit=20
Response: { /* similar to getFollowers */ }

// Cloud Function: getMutualFollowers
GET /api/v1/users/:userId/mutual?page=1&limit=20
Response: { /* similar to getFollowers */ }
```

#### Compatibility Sharing

```typescript
// Cloud Function: shareCompatibilityReport
POST /api/v1/compatibility/share
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  reportId: string,                  // From local Room DB
  visibility: "public" | "followers" | "mutual" | "private_link",
  anonymizeProfiles: boolean,
  caption?: string,
  tags?: string[]
}
Response: {
  success: boolean,
  shareId: string,
  shareLink?: string
}

// Cloud Function: getCompatibilityShares
GET /api/v1/compatibility/shares?visibility=public&page=1&limit=20
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  shares: Array<CompatibilityShare>,
  hasMore: boolean
}

// Cloud Function: getUserCompatibilityShares
GET /api/v1/users/:userId/compatibility/shares?page=1&limit=20
Response: { /* similar to getCompatibilityShares */ }

// Cloud Function: likeCompatibilityShare
POST /api/v1/compatibility/shares/:shareId/like
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  success: boolean,
  likeCount: number
}

// Cloud Function: requestCompatibility
POST /api/v1/compatibility/request
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  targetUserId: string,
  message?: string
}
Response: {
  success: boolean,
  requestId: string
}
```

#### Messaging

```typescript
// Cloud Function: createConversation
POST /api/v1/conversations
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  participantIds: string[],
  type: "direct" | "group"
}
Response: {
  conversationId: string,
  conversation: Conversation
}

// Cloud Function: sendMessage
POST /api/v1/conversations/:conversationId/messages
Headers: { Authorization: "Bearer <firebase_token>" }
Body: {
  type: "text" | "compatibility_share" | "content_share",
  text?: string,
  compatibilityShareId?: string,
  contentId?: string
}
Response: {
  messageId: string,
  message: Message
}

// Cloud Function: getMessages
GET /api/v1/conversations/:conversationId/messages?before=<timestamp>&limit=50
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  messages: Array<Message>,
  hasMore: boolean
}

// Cloud Function: markMessageAsRead
POST /api/v1/conversations/:conversationId/messages/:messageId/read
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  success: boolean
}

// Cloud Function: getConversations
GET /api/v1/conversations?page=1&limit=20
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  conversations: Array<Conversation>,
  hasMore: boolean
}

// Real-time listener for new messages (Firestore SDK)
onSnapshot(
  collection(db, `conversations/${conversationId}/messages`)
    .orderBy('timestamp', 'desc')
    .limit(50)
)
```

#### Discovery & Search

```typescript
// Cloud Function: searchUsers
GET /api/v1/users/search?query=sarah&filters=sunSign:Aries,lifePathNumber:5
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  users: Array<{
    userId: string,
    username: string,
    displayName: string,
    avatarUrl: string,
    spiritualAttributes: {...},
    compatibilityPreview?: CompatibilityPreview
  }>,
  hasMore: boolean
}

// Cloud Function: discoverCompatibleUsers
GET /api/v1/users/discover?minCompatibility=70&maxDistance=50&page=1
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  matches: Array<{
    user: SocialProfile,
    compatibilityPreview: CompatibilityPreview,
    matchReason: string,
    distance?: number  // km
  }>,
  hasMore: boolean
}

// Cloud Function: getNearbyUsers (location-based)
GET /api/v1/users/nearby?latitude=40.7128&longitude=-74.0060&radius=25&page=1
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  users: Array<{
    user: SocialProfile,
    distance: number,  // km
    compatibilityPreview?: CompatibilityPreview
  }>,
  hasMore: boolean
}
```

#### Notifications

```typescript
// Cloud Function: getNotifications
GET /api/v1/notifications?page=1&limit=20&unreadOnly=false
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  notifications: Array<Notification>,
  unreadCount: number,
  hasMore: boolean
}

// Cloud Function: markNotificationAsRead
POST /api/v1/notifications/:notificationId/read
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  success: boolean
}

// Cloud Function: markAllNotificationsAsRead
POST /api/v1/notifications/read-all
Headers: { Authorization: "Bearer <firebase_token>" }
Response: {
  success: boolean,
  markedCount: number
}

// FCM Push Notifications (automatic via Cloud Functions)
// Trigger: New follower, message, compatibility request, etc.
```

---

## UI Flows & Components

### 1. Social Profile Card Component

**Location**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/SocialProfileCard.kt`

```kotlin
@Composable
fun SocialProfileCard(
    socialProfile: SocialProfile,
    compatibilityPreview: CompatibilityPreview? = null,
    isFollowing: Boolean = false,
    isMutual: Boolean = false,
    onFollowClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    GlassmorphCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onProfileClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Avatar + Name + Follow Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Avatar with compatibility aura
                Box {
                    AsyncImage(
                        model = socialProfile.avatarUrl,
                        contentDescription = "Profile avatar",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(
                                2.dp,
                                compatibilityGradient(compatibilityPreview?.compatibilityLevel),
                                CircleShape
                            )
                    )

                    // Mutual connection badge
                    if (isMutual) {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Mutual connection",
                            tint = TantricRose,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.BottomEnd)
                                .background(Color.White, CircleShape)
                                .padding(2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Name + Username + Bio
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = socialProfile.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@${socialProfile.username}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (socialProfile.bio.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = socialProfile.bio,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Follow/Message buttons
                Column(horizontalAlignment = Alignment.End) {
                    SpiritualButton(
                        text = if (isFollowing) "Following" else "Follow",
                        onClick = onFollowClick,
                        style = if (isFollowing)
                            SpiritualButtonStyle.SECONDARY
                        else
                            SpiritualButtonStyle.PRIMARY,
                        modifier = Modifier.height(36.dp)
                    )

                    if (isFollowing) {
                        Spacer(modifier = Modifier.height(8.dp))
                        IconButton(
                            onClick = onMessageClick,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Message, "Send message")
                        }
                    }
                }
            }

            // Spiritual Attributes Row
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                SpiritualBadge(
                    icon = "☀️",
                    text = socialProfile.spiritualAttributes.sunSign
                )
                SpiritualBadge(
                    icon = "🌙",
                    text = socialProfile.spiritualAttributes.moonSign
                )
                SpiritualBadge(
                    icon = "#",
                    text = "Life Path ${socialProfile.spiritualAttributes.lifePathNumber}"
                )
                socialProfile.spiritualAttributes.humanDesignType?.let {
                    SpiritualBadge(icon = "⚡", text = it)
                }
            }

            // Compatibility Preview (if available)
            compatibilityPreview?.let {
                Spacer(modifier = Modifier.height(12.dp))
                SpiritualDivider()
                Spacer(modifier = Modifier.height(12.dp))

                CompatibilityScoreDisplay(
                    score = it.overallScore,
                    level = it.compatibilityLevel,
                    size = CompatibilityDisplaySize.SMALL
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Top Strength",
                            style = MaterialTheme.typography.labelSmall,
                            color = ChakraGreen
                        )
                        Text(
                            text = it.topStrength,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Growth Area",
                            style = MaterialTheme.typography.labelSmall,
                            color = ChakraOrange
                        )
                        Text(
                            text = it.topChallenge,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Stats Row
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "Followers",
                    value = socialProfile.stats.followersCount.toString()
                )
                StatItem(
                    label = "Following",
                    value = socialProfile.stats.followingCount.toString()
                )
                StatItem(
                    label = "Reports Shared",
                    value = socialProfile.stats.compatibilityReportsShared.toString()
                )
            }
        }
    }
}

@Composable
private fun SpiritualBadge(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = icon, fontSize = 12.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = SpiritualPurple
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
```

### 2. Social Feed Screen

**Location**: `/feature/social/src/main/java/com/spiritatlas/feature/social/SocialFeedScreen.kt`

```kotlin
@Composable
fun SocialFeedScreen(
    onNavigateToProfile: (String) -> Unit,
    onNavigateToChat: (String) -> Unit,
    viewModel: SocialFeedViewModel = hiltViewModel()
) {
    val feedState by viewModel.feedState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    StarfieldBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { GradientText("Spiritual Community") },
                    actions = {
                        IconButton(onClick = { /* Search */ }) {
                            Icon(Icons.Default.Search, "Search users")
                        }
                        IconButton(onClick = { /* Notifications */ }) {
                            BadgedBox(badge = {
                                if (viewModel.unreadNotifications > 0) {
                                    Badge { Text("${viewModel.unreadNotifications}") }
                                }
                            }) {
                                Icon(Icons.Default.Notifications, "Notifications")
                            }
                        }
                    }
                )
            }
        ) { padding ->
            SpiritualPullRefresh(
                isRefreshing = isRefreshing,
                onRefresh = { viewModel.refreshFeed() }
            ) {
                when (feedState) {
                    is FeedState.Loading -> LoadingState()
                    is FeedState.Success -> {
                        val items = (feedState as FeedState.Success).items
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(padding)
                        ) {
                            // Suggested Connections
                            item {
                                SuggestedConnectionsRow(
                                    suggestions = viewModel.suggestedConnections,
                                    onFollowClick = { userId -> viewModel.followUser(userId) },
                                    onProfileClick = onNavigateToProfile
                                )
                            }

                            // Feed Items
                            items(items, key = { it.id }) { item ->
                                when (item) {
                                    is FeedItem.CompatibilityShare -> {
                                        CompatibilityShareCard(
                                            share = item,
                                            onLikeClick = { viewModel.likeShare(item.id) },
                                            onCommentClick = { /* Navigate to comments */ },
                                            onProfileClick = onNavigateToProfile
                                        )
                                    }
                                    is FeedItem.NewFollower -> {
                                        NewFollowerCard(
                                            follower = item.user,
                                            onFollowBackClick = { viewModel.followUser(item.user.userId) },
                                            onProfileClick = onNavigateToProfile
                                        )
                                    }
                                    is FeedItem.CompatibilityRequest -> {
                                        CompatibilityRequestCard(
                                            request = item,
                                            onAcceptClick = { viewModel.acceptCompatibilityRequest(item.id) },
                                            onDeclineClick = { viewModel.declineCompatibilityRequest(item.id) }
                                        )
                                    }
                                }
                            }

                            // Load more trigger
                            item {
                                if (items.isNotEmpty()) {
                                    LoadMoreIndicator(
                                        onLoadMore = { viewModel.loadMoreFeedItems() }
                                    )
                                }
                            }
                        }
                    }
                    is FeedState.Error -> ErrorState(
                        message = (feedState as FeedState.Error).message,
                        onRetry = { viewModel.refreshFeed() }
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestedConnectionsRow(
    suggestions: List<SocialProfile>,
    onFollowClick: (String) -> Unit,
    onProfileClick: (String) -> Unit
) {
    Column {
        Text(
            text = "Suggested Spiritual Connections",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(suggestions, key = { it.userId }) { profile ->
                SuggestedConnectionCard(
                    profile = profile,
                    onFollowClick = { onFollowClick(profile.userId) },
                    onProfileClick = { onProfileClick(profile.userId) }
                )
            }
        }
    }
}
```

### 3. Chat Screen

**Location**: `/feature/social/src/main/java/com/spiritatlas/feature/social/ChatScreen.kt`

```kotlin
@Composable
fun ChatScreen(
    conversationId: String,
    onNavigateBack: () -> Unit,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsState()
    val conversation by viewModel.conversation.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(conversationId) {
        viewModel.loadConversation(conversationId)
    }

    // Auto-scroll to bottom on new messages
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    conversation?.let { conv ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = conv.otherParticipant.avatarUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = conv.otherParticipant.displayName,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                // Compatibility badge
                                conv.compatibilityPreview?.let {
                                    Text(
                                        text = "${it.compatibilityLevel} • ${it.overallScore.toInt()}%",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = compatibilityColor(it.compatibilityLevel)
                                    )
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Profile */ }) {
                        Icon(Icons.Default.Person, "View profile")
                    }
                    IconButton(onClick = { /* More options */ }) {
                        Icon(Icons.Default.MoreVert, "More")
                    }
                }
            )
        },
        bottomBar = {
            ChatInputBar(
                text = messageText,
                onTextChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                },
                onAttachClick = { /* Attach compatibility report or content */ }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading) {
                LoadingState()
            } else {
                // Messages list (reversed for bottom-to-top)
                LazyColumn(
                    state = listState,
                    reverseLayout = true,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(messages, key = { it.messageId }) { message ->
                        MessageBubble(
                            message = message,
                            isOwnMessage = message.senderId == viewModel.currentUserId,
                            onLongPress = { /* Show reactions */ }
                        )
                    }

                    // Load more messages trigger
                    item {
                        if (messages.size >= 50) {
                            LoadMoreIndicator(
                                onLoadMore = { viewModel.loadMoreMessages() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isOwnMessage: Boolean,
    onLongPress: () -> Unit = {}
) {
    Row(
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onLongPress() })
            }
    ) {
        when (message.type) {
            MessageType.TEXT -> {
                Box(
                    modifier = Modifier
                        .background(
                            if (isOwnMessage) SpiritualPurple else MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(12.dp)
                        .widthIn(max = 280.dp)
                ) {
                    Column {
                        Text(
                            text = message.text,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isOwnMessage) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = formatTimestamp(message.timestamp),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isOwnMessage)
                                    Color.White.copy(alpha = 0.7f)
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                            if (isOwnMessage && message.isRead) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = "Read",
                                    tint = ChakraGreen,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }
            }
            MessageType.COMPATIBILITY_SHARE -> {
                CompatibilitySharePreview(
                    shareId = message.compatibilityShareId!!,
                    modifier = Modifier.widthIn(max = 300.dp)
                )
            }
            MessageType.CONTENT_SHARE -> {
                ContentSharePreview(
                    contentId = message.contentId!!,
                    contentType = message.contentType!!,
                    modifier = Modifier.widthIn(max = 300.dp)
                )
            }
        }
    }
}

@Composable
fun ChatInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onAttachClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        IconButton(onClick = onAttachClick) {
            Icon(Icons.Default.Attachment, "Attach")
        }

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text("Type a message...") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            maxLines = 4
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onSendClick,
            enabled = text.isNotBlank()
        ) {
            Icon(
                Icons.Default.Send,
                "Send",
                tint = if (text.isNotBlank()) SpiritualPurple else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
```

### 4. Follow Button Component

**Location**: `/core/ui/src/main/java/com/spiritatlas/core/ui/components/FollowButton.kt`

```kotlin
@Composable
fun FollowButton(
    isFollowing: Boolean,
    isMutual: Boolean = false,
    onFollowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessHigh)
    )

    SpiritualButton(
        text = when {
            isMutual -> "Mutual"
            isFollowing -> "Following"
            else -> "Follow"
        },
        onClick = onFollowClick,
        style = when {
            isMutual -> SpiritualButtonStyle.GRADIENT
            isFollowing -> SpiritualButtonStyle.SECONDARY
            else -> SpiritualButtonStyle.PRIMARY
        },
        icon = when {
            isMutual -> Icons.Default.Favorite
            isFollowing -> Icons.Default.Check
            else -> Icons.Default.Add
        },
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    )
}
```

### 5. Share Compatibility Dialog

**Location**: `/feature/compatibility/src/main/java/com/spiritatlas/feature/compatibility/ShareCompatibilityDialog.kt`

```kotlin
@Composable
fun ShareCompatibilityDialog(
    compatibilityReport: CompatibilityReport,
    onDismiss: () -> Unit,
    onShareClick: (ShareOptions) -> Unit
) {
    var visibility by remember { mutableStateOf(ShareVisibility.FOLLOWERS) }
    var anonymize by remember { mutableStateOf(false) }
    var caption by remember { mutableStateOf("") }
    var selectedTags by remember { mutableStateOf(emptyList<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Share Compatibility Report") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Visibility selector
                Text("Who can see this?", fontWeight = FontWeight.Bold)
                ShareVisibilitySelector(
                    selected = visibility,
                    onSelectVisibility = { visibility = it }
                )

                // Anonymize toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Switch(
                        checked = anonymize,
                        onCheckedChange = { anonymize = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Anonymize profiles")
                }

                // Caption input
                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    label = { Text("Add a caption (optional)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                // Tag selector
                Text("Add tags", fontWeight = FontWeight.Bold)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    suggestedTags.forEach { tag ->
                        FilterChip(
                            selected = tag in selectedTags,
                            onClick = {
                                selectedTags = if (tag in selectedTags) {
                                    selectedTags - tag
                                } else {
                                    selectedTags + tag
                                }
                            },
                            label = { Text(tag) }
                        )
                    }
                }
            }
        },
        confirmButton = {
            SpiritualButton(
                text = "Share",
                onClick = {
                    onShareClick(
                        ShareOptions(
                            visibility = visibility,
                            anonymizeProfiles = anonymize,
                            caption = caption.takeIf { it.isNotBlank() },
                            tags = selectedTags
                        )
                    )
                }
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

enum class ShareVisibility {
    PUBLIC, FOLLOWERS, MUTUAL, PRIVATE_LINK
}

data class ShareOptions(
    val visibility: ShareVisibility,
    val anonymizeProfiles: Boolean,
    val caption: String?,
    val tags: List<String>
)

val suggestedTags = listOf(
    "#soulmate", "#twinflame", "#compatibility",
    "#spiritualconnection", "#astrology", "#numerology"
)
```

---

## Security & Privacy

### 1. Authentication & Authorization

**Firebase Authentication**:
- Email/password authentication
- Google Sign-In
- Optional: Apple Sign-In, Phone authentication
- JWT tokens for API authentication
- Token refresh mechanism

**Security Rules (Firestore)**:
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    // Helper functions
    function isAuthenticated() {
      return request.auth != null;
    }

    function isOwner(userId) {
      return isAuthenticated() && request.auth.uid == userId;
    }

    function isFollowing(targetUserId) {
      return exists(/databases/$(database)/documents/follows/$(request.auth.uid + '_' + targetUserId));
    }

    function isMutual(targetUserId) {
      return isFollowing(targetUserId) &&
             exists(/databases/$(database)/documents/follows/$(targetUserId + '_' + request.auth.uid));
    }

    // Users collection
    match /users/{userId} {
      // Anyone can read public profiles
      allow read: if isAuthenticated() &&
                     (resource.data.socialProfile.visibility == 'public' ||
                      isOwner(userId) ||
                      (resource.data.socialProfile.visibility == 'followers' && isFollowing(userId)) ||
                      (resource.data.socialProfile.visibility == 'friends' && isMutual(userId)));

      // Only owner can update
      allow update: if isOwner(userId);

      // Only authenticated users can create (during signup)
      allow create: if isAuthenticated() && request.auth.uid == userId;

      // Only owner can delete
      allow delete: if isOwner(userId);
    }

    // Follows collection
    match /follows/{followId} {
      // Anyone can read their own follows
      allow read: if isAuthenticated() &&
                     (resource.data.followerId == request.auth.uid ||
                      resource.data.followingId == request.auth.uid);

      // Users can create follows
      allow create: if isAuthenticated() &&
                       request.resource.data.followerId == request.auth.uid;

      // Users can delete their own follows
      allow delete: if isAuthenticated() &&
                       resource.data.followerId == request.auth.uid;
    }

    // Compatibility shares
    match /compatibility_shares/{shareId} {
      // Read based on visibility
      allow read: if isAuthenticated() &&
                     (resource.data.visibility == 'public' ||
                      isOwner(resource.data.authorId) ||
                      (resource.data.visibility == 'followers' && isFollowing(resource.data.authorId)) ||
                      (resource.data.visibility == 'mutual' && isMutual(resource.data.authorId)));

      // Only author can create/update/delete
      allow create: if isAuthenticated() && request.resource.data.authorId == request.auth.uid;
      allow update: if isOwner(resource.data.authorId);
      allow delete: if isOwner(resource.data.authorId);
    }

    // Conversations
    match /conversations/{conversationId} {
      // Only participants can read
      allow read: if isAuthenticated() &&
                     request.auth.uid in resource.data.participants;

      // Participants can update (send messages)
      allow update: if isAuthenticated() &&
                       request.auth.uid in resource.data.participants;

      // Anyone can create conversations they're part of
      allow create: if isAuthenticated() &&
                       request.auth.uid in request.resource.data.participants;

      // Messages subcollection
      match /messages/{messageId} {
        // Only conversation participants can read messages
        allow read: if isAuthenticated() &&
                       request.auth.uid in get(/databases/$(database)/documents/conversations/$(conversationId)).data.participants;

        // Only participants can create messages
        allow create: if isAuthenticated() &&
                         request.auth.uid in get(/databases/$(database)/documents/conversations/$(conversationId)).data.participants &&
                         request.resource.data.senderId == request.auth.uid;

        // Only sender can update/delete their messages
        allow update, delete: if isAuthenticated() &&
                                 resource.data.senderId == request.auth.uid;
      }
    }

    // Notifications
    match /notifications/{notificationId} {
      // Only recipient can read
      allow read: if isOwner(resource.data.userId);

      // System can create (via Cloud Functions)
      allow create: if isAuthenticated();

      // Recipient can update (mark as read)
      allow update: if isOwner(resource.data.userId);

      // Recipient can delete
      allow delete: if isOwner(resource.data.userId);
    }
  }
}
```

### 2. Data Privacy

**Encryption**:
- **End-to-End Message Encryption**: Implement E2EE using [Signal Protocol](https://signal.org/docs/) or [Matrix Olm](https://gitlab.matrix.org/matrix-org/olm)
- **Local Data Encryption**: Continue using `EncryptedSharedPreferences` for sensitive local data
- **SSL Pinning**: Maintain existing SSL certificate pinning for API calls

**Privacy Controls**:
```kotlin
data class PrivacySettings(
    // Profile visibility
    val profileVisibility: ProfileVisibility = ProfileVisibility.FRIENDS,

    // Who can send you messages
    val messagePermission: MessagePermission = MessagePermission.FOLLOWERS,

    // Who can see your compatibility reports
    val compatibilityShareVisibility: ShareVisibility = ShareVisibility.FOLLOWERS,

    // Who can request compatibility checks
    val compatibilityRequestPermission: CompatibilityRequestPermission = CompatibilityRequestPermission.FOLLOWERS,

    // Location sharing
    val showLocation: Boolean = false,
    val shareLocationPrecision: LocationPrecision = LocationPrecision.CITY,

    // Spiritual attributes visibility
    val showSunSign: Boolean = true,
    val showMoonSign: Boolean = true,
    val showLifePathNumber: Boolean = true,
    val showHumanDesignType: Boolean = false,

    // Online status
    val showOnlineStatus: Boolean = false,

    // Read receipts
    val sendReadReceipts: Boolean = true
)

enum class ProfileVisibility {
    PRIVATE,      // Only visible in your own profile library
    FRIENDS,      // Visible to mutual connections
    FOLLOWERS,    // Visible to all followers
    PUBLIC        // Visible to everyone
}

enum class MessagePermission {
    EVERYONE,
    FOLLOWERS,
    MUTUAL,
    NONE
}

enum class CompatibilityRequestPermission {
    EVERYONE,
    FOLLOWERS,
    MUTUAL,
    NONE
}

enum class LocationPrecision {
    EXACT,        // City, State, Country
    CITY,         // City, Country
    COUNTRY       // Country only
}
```

### 3. Content Moderation

**Automated Moderation**:
- **Cloud Functions**: Scan messages and captions for inappropriate content
- **ML Kit**: Use Firebase ML Kit for image moderation
- **Spam Detection**: Rate limiting and spam pattern detection

**User Moderation Tools**:
- **Block User**: Prevent all interactions
- **Report User**: Flag for admin review
- **Mute Notifications**: Mute without unfollowing

```kotlin
// Cloud Function: moderateContent
exports.moderateContent = functions.firestore
    .document('compatibility_shares/{shareId}')
    .onCreate(async (snap, context) => {
        const share = snap.data();
        const caption = share.caption || '';

        // Check for inappropriate content
        const moderationResult = await moderateText(caption);

        if (moderationResult.isInappropriate) {
            // Flag for review or auto-remove
            await snap.ref.update({
                moderationStatus: 'flagged',
                moderationReason: moderationResult.reason,
                isVisible: false
            });

            // Notify user
            await sendNotification(share.authorId, {
                title: 'Content Moderation',
                message: 'Your post has been flagged for review.'
            });
        }
    });
```

---

## Implementation Timeline

### Phase 1: Foundation (4 weeks)

#### Week 1: Backend Setup
- **Day 1-2**: Firebase project setup
  - Create Firebase project
  - Configure Firebase Authentication
  - Set up Cloud Firestore database
  - Configure security rules (basic version)
  - Set up Cloud Functions environment

- **Day 3-4**: Database schema implementation
  - Create Firestore collections
  - Set up composite indexes
  - Implement data migration scripts
  - Test write/read performance

- **Day 5**: Android Firebase integration
  - Add Firebase SDK dependencies
  - Configure `google-services.json`
  - Implement Firebase initialization
  - Test authentication flow

#### Week 2: Social Profile System
- **Day 1-2**: Domain models
  - Create `SocialProfile` data class
  - Create `SpiritualAttributes` data class
  - Implement privacy settings models
  - Add to domain layer

- **Day 3-4**: Repository implementation
  - Create `SocialProfileRepository` interface
  - Implement Firestore repository
  - Add Room database caching
  - Write repository tests

- **Day 5**: Profile UI components
  - Implement `SocialProfileCard` composable
  - Create profile edit screen
  - Add avatar upload functionality
  - Implement privacy settings UI

#### Week 3: Follow System
- **Day 1-2**: Follow logic
  - Implement follow/unfollow API endpoints
  - Create `FollowRepository`
  - Add compatibility preview caching
  - Handle mutual follow detection

- **Day 3-4**: Follow UI
  - Create followers list screen
  - Create following list screen
  - Implement `FollowButton` component
  - Add mutual connections view

- **Day 5**: Testing & optimization
  - Write follow system tests
  - Test mutual follow scenarios
  - Optimize Firestore queries
  - Performance testing

#### Week 4: Compatibility Sharing
- **Day 1-2**: Share logic
  - Implement share API endpoints
  - Create `CompatibilityShareRepository`
  - Add share link generation
  - Implement visibility controls

- **Day 3-4**: Share UI
  - Create `ShareCompatibilityDialog`
  - Implement share feed view
  - Add like/comment functionality
  - Create share preview cards

- **Day 5**: Integration testing
  - Test share workflows
  - Test privacy controls
  - Test viral share links
  - End-to-end testing

### Phase 2: Messaging (1 week)

#### Week 5: Messaging System
- **Day 1-2**: Message backend
  - Implement conversation creation
  - Create message send/receive endpoints
  - Add real-time message listeners
  - Implement read receipts

- **Day 3-4**: Chat UI
  - Create `ChatScreen` composable
  - Implement message bubbles
  - Add chat input bar
  - Create conversation list screen

- **Day 5**: Message features
  - Add typing indicators
  - Implement message notifications (FCM)
  - Add compatibility report sharing in chat
  - Test end-to-end messaging

### Phase 3: Testing & Polish (1 week)

#### Week 6: Testing
- **Day 1-2**: Unit tests
  - Repository unit tests
  - ViewModel unit tests
  - Use case tests
  - Model validation tests

- **Day 3-4**: Integration tests
  - Firebase integration tests
  - UI integration tests
  - End-to-end flow tests
  - Performance tests

- **Day 5**: Bug fixes & optimization
  - Fix identified bugs
  - Optimize Firestore queries
  - Reduce bundle size
  - Performance profiling

### Phase 4: DevOps & Launch Prep (1 week)

#### Week 7: DevOps
- **Day 1-2**: CI/CD setup
  - Configure GitHub Actions
  - Set up automated testing
  - Add Firebase deployment pipeline
  - Configure staging environment

- **Day 3-4**: Monitoring & analytics
  - Set up Firebase Analytics
  - Configure Crashlytics
  - Add performance monitoring
  - Set up alerts

- **Day 5**: Final review
  - Security audit
  - Privacy review
  - Documentation
  - Release notes

---

## Integration Strategy

### 1. Module Structure

Create new feature modules:

```
feature/
├── social/                    # New social features module
│   ├── build.gradle.kts
│   └── src/main/java/com/spiritatlas/feature/social/
│       ├── SocialFeedScreen.kt
│       ├── SocialFeedViewModel.kt
│       ├── SocialProfileScreen.kt
│       ├── FollowersScreen.kt
│       ├── FollowingScreen.kt
│       └── ShareCompatibilityScreen.kt
│
├── messaging/                 # New messaging module
│   ├── build.gradle.kts
│   └── src/main/java/com/spiritatlas/feature/messaging/
│       ├── ChatScreen.kt
│       ├── ChatViewModel.kt
│       ├── ConversationListScreen.kt
│       └── MessageNotificationService.kt
│
└── discovery/                 # User discovery module (Phase 2)
    ├── build.gradle.kts
    └── src/main/java/com/spiritatlas/feature/discovery/
        ├── DiscoverScreen.kt
        ├── UserSearchScreen.kt
        └── NearbyUsersScreen.kt
```

### 2. Navigation Integration

Update `SpiritAtlasNavGraph.kt`:

```kotlin
// Add new routes
object Screen {
    // Existing routes...
    const val SOCIAL_FEED = "social_feed"
    const val SOCIAL_PROFILE = "social_profile/{userId}"
    const val FOLLOWERS = "followers/{userId}"
    const val FOLLOWING = "following/{userId}"
    const val CHAT = "chat/{conversationId}"
    const val CONVERSATIONS = "conversations"
    const val DISCOVER = "discover"
    const val SEARCH_USERS = "search_users"
}

@Composable
fun SpiritAtlasNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController, startDestination) {
        // Existing composables...

        // Social features
        composable(Screen.SOCIAL_FEED) {
            SocialFeedScreen(
                onNavigateToProfile = { userId ->
                    navController.navigate("social_profile/$userId")
                },
                onNavigateToChat = { conversationId ->
                    navController.navigate("chat/$conversationId")
                }
            )
        }

        composable(
            route = Screen.SOCIAL_PROFILE,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: return@composable
            SocialProfileScreen(
                userId = userId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToChat = { conversationId ->
                    navController.navigate("chat/$conversationId")
                }
            )
        }

        composable(Screen.CONVERSATIONS) {
            ConversationListScreen(
                onNavigateToChat = { conversationId ->
                    navController.navigate("chat/$conversationId")
                }
            )
        }

        composable(
            route = Screen.CHAT,
            arguments = listOf(navArgument("conversationId") { type = NavType.StringType })
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getString("conversationId") ?: return@composable
            ChatScreen(
                conversationId = conversationId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Discovery features (Phase 2)
        composable(Screen.DISCOVER) {
            DiscoverScreen(
                onNavigateToProfile = { userId ->
                    navController.navigate("social_profile/$userId")
                }
            )
        }
    }
}
```

### 3. Home Screen Integration

Update `HomeScreen.kt` to add social features:

```kotlin
// Add to HomeScreen
@Composable
fun HomeScreen(...) {
    // ... existing code

    // Add Social Feed section
    item {
        HomeSectionCard(
            title = "Spiritual Community",
            icon = Icons.Default.People,
            onClick = { navController.navigate(Screen.SOCIAL_FEED) }
        ) {
            // Recent follower previews
            RecentFollowersRow(
                followers = viewModel.recentFollowers.collectAsState().value,
                onSeeAll = { navController.navigate(Screen.SOCIAL_FEED) }
            )
        }
    }

    // Add Messages section
    item {
        HomeSectionCard(
            title = "Messages",
            icon = Icons.Default.Message,
            onClick = { navController.navigate(Screen.CONVERSATIONS) },
            badge = viewModel.unreadMessageCount.collectAsState().value
        ) {
            // Recent conversations preview
            RecentConversationsRow(
                conversations = viewModel.recentConversations.collectAsState().value,
                onNavigateToChat = { conversationId ->
                    navController.navigate("chat/$conversationId")
                }
            )
        }
    }
}
```

### 4. Compatibility Screen Integration

Update `CompatibilityDetailScreen.kt`:

```kotlin
// Add share button to toolbar
TopAppBar(
    // ... existing config
    actions = {
        // Existing actions...
        IconButton(
            onClick = { showShareDialog = true }
        ) {
            Icon(Icons.Default.Share, "Share compatibility")
        }
    }
)

// Add share dialog
if (showShareDialog) {
    ShareCompatibilityDialog(
        compatibilityReport = compatibilityReport,
        onDismiss = { showShareDialog = false },
        onShareClick = { options ->
            viewModel.shareCompatibilityReport(options)
            showShareDialog = false
        }
    )
}
```

---

## Testing Strategy

### 1. Unit Tests

**Repository Tests** (`SocialProfileRepositoryTest.kt`):
```kotlin
@Test
fun `createSocialProfile saves profile to Firestore`() = runTest {
    // Arrange
    val profile = createTestSocialProfile()

    // Act
    val result = repository.createSocialProfile(profile)

    // Assert
    assertTrue(result.isSuccess)
    verify(firestoreDb).collection("users").document(profile.userId).set(any())
}

@Test
fun `followUser creates follow relationship and returns compatibility preview`() = runTest {
    // Arrange
    val followerId = "user1"
    val followingId = "user2"

    // Act
    val result = repository.followUser(followingId)

    // Assert
    assertTrue(result.isSuccess)
    val compatibilityPreview = result.getOrNull()
    assertNotNull(compatibilityPreview)
    assertEquals(85.0, compatibilityPreview?.overallScore)
}
```

**ViewModel Tests** (`SocialFeedViewModelTest.kt`):
```kotlin
@Test
fun `refreshFeed loads feed items successfully`() = runTest {
    // Arrange
    val mockFeedItems = listOf(
        createTestCompatibilityShare(),
        createTestFollowerNotification()
    )
    whenever(repository.getFeedItems()).thenReturn(flowOf(Result.success(mockFeedItems)))

    // Act
    viewModel.refreshFeed()
    advanceUntilIdle()

    // Assert
    val state = viewModel.feedState.value
    assertTrue(state is FeedState.Success)
    assertEquals(2, (state as FeedState.Success).items.size)
}

@Test
fun `followUser updates UI state and shows success message`() = runTest {
    // Arrange
    val userId = "user123"

    // Act
    viewModel.followUser(userId)
    advanceUntilIdle()

    // Assert
    verify(repository).followUser(userId)
    assertTrue(viewModel.showSuccessMessage.value)
}
```

### 2. Integration Tests

**Firebase Integration Tests** (`FirebaseSocialRepositoryTest.kt`):
```kotlin
@Test
fun `end_to_end_follow_flow_updates_firestore`() = runTest {
    // Use Firebase Emulator for testing
    val repository = FirebaseSocialRepository(firestore, auth)

    // Create two test users
    val user1 = createTestUser("user1")
    val user2 = createTestUser("user2")

    // User1 follows User2
    repository.followUser(user2.userId)

    // Verify follow relationship exists
    val followers = repository.getFollowers(user2.userId).first()
    assertEquals(1, followers.size)
    assertEquals(user1.userId, followers[0].userId)

    // User2 follows User1 back (mutual)
    repository.followUser(user1.userId)

    // Verify mutual relationship
    val follow = repository.getFollow(user1.userId, user2.userId).first()
    assertTrue(follow?.isMutual == true)
}
```

**UI Tests** (`SocialFeedScreenTest.kt`):
```kotlin
@Test
fun socialFeedScreen_displaysFollowersAndCompatibilityShares() {
    composeTestRule.setContent {
        SocialFeedScreen(
            onNavigateToProfile = {},
            onNavigateToChat = {}
        )
    }

    // Verify suggested connections row is displayed
    composeTestRule.onNodeWithText("Suggested Spiritual Connections").assertIsDisplayed()

    // Verify feed items are displayed
    composeTestRule.onNodeWithText("New Follower").assertIsDisplayed()
    composeTestRule.onNodeWithText("Compatibility Share").assertIsDisplayed()
}

@Test
fun chatScreen_sendsMessage_successfully() {
    composeTestRule.setContent {
        ChatScreen(
            conversationId = "conv123",
            onNavigateBack = {}
        )
    }

    // Type message
    composeTestRule.onNodeWithText("Type a message...").performTextInput("Hello!")

    // Click send
    composeTestRule.onNodeWithContentDescription("Send").performClick()

    // Verify message appears in chat
    composeTestRule.onNodeWithText("Hello!").assertIsDisplayed()
}
```

### 3. Performance Tests

**Firestore Query Performance**:
```kotlin
@Test
fun `getFeedItems completes within 1 second`() = runTest {
    val startTime = System.currentTimeMillis()

    repository.getFeedItems().first()

    val duration = System.currentTimeMillis() - startTime
    assertTrue(duration < 1000, "Feed load took $duration ms")
}
```

**Message Delivery Performance**:
```kotlin
@Test
fun `sendMessage delivers within 500ms`() = runTest {
    val startTime = System.currentTimeMillis()

    repository.sendMessage(conversationId, "Test message")

    val duration = System.currentTimeMillis() - startTime
    assertTrue(duration < 500, "Message send took $duration ms")
}
```

### 4. Security Tests

**Authentication Tests**:
```kotlin
@Test
fun `unauthenticated user cannot access social profile`() = runTest {
    // Arrange
    auth.signOut()

    // Act & Assert
    assertThrows<SecurityException> {
        repository.getSocialProfile("user123")
    }
}
```

**Privacy Tests**:
```kotlin
@Test
fun `private profile not visible to non-followers`() = runTest {
    // Arrange
    val privateProfile = createTestProfile(visibility = ProfileVisibility.PRIVATE)
    repository.createSocialProfile(privateProfile)

    // Act
    val result = repository.getSocialProfile(privateProfile.userId)

    // Assert
    assertTrue(result.isFailure)
    assertTrue(result.exceptionOrNull() is PermissionDeniedException)
}
```

---

## Success Metrics

### Key Performance Indicators (KPIs)

1. **User Engagement**
   - Daily Active Users (DAU) increase by 40%
   - Average session duration increase by 30%
   - User retention rate (D7, D30) increase by 25%

2. **Social Features Adoption**
   - 60% of users create social profiles within 7 days
   - 40% of users follow at least 3 people within 14 days
   - 30% of users share at least 1 compatibility report within 30 days
   - 25% of users send at least 1 message within 30 days

3. **Technical Performance**
   - Feed load time < 1 second
   - Message delivery latency < 500ms
   - 99.9% API uptime
   - Crash-free rate > 99.5%

4. **Growth Metrics**
   - Viral coefficient > 1.2 (shared compatibility links drive signups)
   - Organic referral rate > 20%
   - User-to-user connection rate > 3 connections per user

---

## Future Enhancements (Phase 2)

### 1. Community Features
- **Sacred Circles**: Private groups for spiritual practices
- **Moon Circles**: Monthly group gatherings synced with lunar phases
- **Community Events**: Virtual and in-person spiritual events
- **Spiritual Challenges**: 30-day meditation challenges, etc.

### 2. Advanced Matching
- **AI-Powered Recommendations**: ML-based compatibility suggestions
- **Advanced Filters**: Filter by specific spiritual attributes
- **Location-Based Matching**: Find spiritual connections nearby
- **Event-Based Matching**: Meet people at spiritual events

### 3. Monetization
- **Premium Subscriptions**:
  - Unlimited compatibility reports
  - Advanced analytics
  - Priority support
  - Early access to features

- **In-App Purchases**:
  - Boost profile visibility
  - Featured in discovery feed
  - Exclusive spiritual content
  - Virtual gifts for connections

### 4. Content Features
- **Spiritual Blog**: User-generated content
- **Video Stories**: Share spiritual journeys
- **Live Streaming**: Meditation sessions, workshops
- **Podcast Integration**: Spiritual podcasts and talks

---

## Conclusion

This social features architecture transforms SpiritAtlas from a personal spiritual tool into a vibrant community platform. The design prioritizes:

1. **Privacy & Security**: End-to-end encryption, granular privacy controls, and secure authentication
2. **Spiritual Authenticity**: Connections guided by deep compatibility analysis, not superficial metrics
3. **Scalability**: Firebase-based architecture supports millions of users
4. **User Experience**: Beautiful, intuitive UI aligned with SpiritAtlas' spiritual aesthetic

**Timeline Summary**:
- **Weeks 1-4**: Core social features (profiles, follows, sharing)
- **Week 5**: Messaging system
- **Week 6**: Testing & polish
- **Week 7**: DevOps & launch prep

**Total Implementation Time**: 6-7 weeks

The architecture is designed to integrate seamlessly with SpiritAtlas' existing Clean Architecture, using the same patterns and conventions already established in the codebase. All new features will maintain the app's privacy-first approach while enabling meaningful spiritual connections.
