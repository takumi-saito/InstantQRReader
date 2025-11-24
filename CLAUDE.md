# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

InstantQRReaderは、Google Play Instant AppとインストールアプリをサポートするAndroid QRコードリーダーアプリ。KotlinとJetpack Composeで構築。

## ビルドコマンド

```bash
# デバッグAPKビルド
./gradlew assembleDebug

# リリースAPKビルド
./gradlew assembleRelease

# 特定フレーバーのビルド
./gradlew assembleInstantDebug    # Instant App版
./gradlew assembleInstalledDebug  # インストール版

# テスト実行
./gradlew test                    # ユニットテスト
./gradlew connectedAndroidTest    # 計装テスト

# クリーンビルド
./gradlew clean
```

## アーキテクチャ

### ビルドバリアント

`app/build.gradle.kts`で2つのプロダクトフレーバーを定義:
- **instant**: Google Play Instant App版 (versionCode: 104)
- **installed**: 通常インストール版 (versionCode: 10103)

インストール版のバージョンコード体系: `[アプリVer][instant version][revision]`

### パッケージ構成

```
com.kireaji.instantqrreader/
├── MainActivity.kt          # エントリーポイント、App Links処理
├── MyApplication.kt         # Applicationクラス、Instant App検出
└── ui/
    ├── QRCodeReaderScreen.kt    # メインUI、カメラ連携
    ├── theme/                   # Material 3テーマ
    └── widget/
        ├── HandleQRCodeActions.kt   # QR結果のアクションボタン
        └── HyperlinkText.kt         # クリック可能なリンクテキスト
```

### 主要な依存関係

- **UI**: Jetpack Compose + Material 3
- **QRコード**: ZXing Android Embedded (`com.journeyapps:zxing-android-embedded`)
- **Instant Apps**: `com.google.android.gms:play-services-instantapps`
- **分析**: Firebase Analytics + Crashlytics

### App Links

`https://instantqrreader.web.app/launch`からのディープリンクを処理。クエリパラメータ(`serial`, `param2`)対応。

## 開発環境

- **Min SDK**: 28 (Android 9)
- **Target SDK**: 34
- **Kotlin**: 1.8.10
- **Java**: 17
- **Compose Compiler**: 1.4.3

アプリは`singleInstance`起動モード、縦画面固定。
