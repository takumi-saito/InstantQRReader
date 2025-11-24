# InstantQRReader

インストール不要で即座に使えるQRコードリーダーアプリ。Google Play Instant App対応。

## 機能

- **QRコード読み取り**: カメラでQRコードをスキャン
- **QRコード表示**: 読み取った内容をQRコードとして再表示
- **アクション機能**:
  - ブラウザで開く（URLの場合）
  - クリップボードにコピー
  - 他アプリへ共有

## 技術スタック

| カテゴリ | 技術 |
|---------|------|
| 言語 | Kotlin 1.8 |
| UI | Jetpack Compose + Material 3 |
| QRコード | ZXing Android Embedded |
| 分析 | Firebase Analytics / Crashlytics |

## ビルドバリアント

| フレーバー | 説明 |
|-----------|------|
| `instant` | Google Play Instant App版（インストール不要） |
| `installed` | 通常インストール版 |

## 動作要件

- Android 9.0 (API 28) 以上

## ビルド

```bash
# Instant App版
./gradlew assembleInstantDebug

# インストール版
./gradlew assembleInstalledDebug
```

## ライセンス

MIT License
