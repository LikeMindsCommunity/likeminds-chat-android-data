# LikeMinds Chat data layer for Android

The Kotlin data layer behind the LikeMinds Chat SDK. Auth, networking, realtime sync, offline cache
and media upload.

[![Maven Central](https://img.shields.io/maven-central/v/community.likeminds/likemindschat.svg)](https://central.sonatype.com/artifact/community.likeminds/likemindschat)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)

**Docs:** https://docs.likeminds.io/

## Install

```groovy
implementation 'community.likeminds:likemindschat:2.13.0'
```

## What it gives you

`LMChatClient` exposes **88 functions across 11 sub-clients**: Chatroom, Conversation, DM,
Community, HomeFeed, Moderation, Notification, Poll, Search, User and Helper.

- **Realm-backed local database**, so chat works offline
- **WorkManager sync workers** for first-time and incremental sync
- `observeLive*` streams for realtime conversation updates

You can use this on its own if you want to build your own chat UI. If you want screens too, use the
[UI SDK](https://github.com/LikeMindsCommunity/likeminds-chat-android-community-archv1), which
depends on this.

## Built on

Retrofit · OkHttp · Dagger · RxJava · Realm

## Contributing

See the org-wide [contributing guide](https://github.com/LikeMindsCommunity/.github/blob/master/.github/CONTRIBUTING.md).
Security issues go to **hi@likeminds.community**, not the issue tracker.

## License

Apache 2.0. See [LICENSE](LICENSE).
