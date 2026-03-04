# Kurly Android Pre-Assignment - Development Log

Claude Code v2.1.66 | Opus 4.6 | Claude Team

---

## 1. Project Initialization (`/init`)

CLAUDE.md 파일 생성 - 프로젝트 구조, 빌드 커맨드, 아키텍처 문서화.

- 7개 패턴 검색, 14개 파일 분석
- 빌드 커맨드 (assembleDebug, test, connectedAndroidTest, clean)
- 모듈 구조: `:app`, `:data`, `:mockserver`, `build-logic`
- 주요 패턴: Hilt DI, Orbit MVI, Kotlinx Serialization, Coil 3, Navigation Compose
- Mock Server API 엔드포인트 및 데이터 모델 문서화

**Output:** `CLAUDE.md` (58 lines)

---

## 2. Data Layer 구현

### 요구사항
- Retrofit + Kotlinx Serialization 기반 네트워킹
- MockService (Retrofit interface), MockRepository (서비스 주입)
- Hilt DI: ServiceModule, OkHttpClientModule
- CoroutineDispatcher: IO, Unconfined, Default, Main qualifier + DispatchersModule
- Repository: `withContext` + `runCatching` → `kotlin.Result` 리턴

### API 명세

**섹션 목록** (`GET /sections?page={n}`)
```json
{
  "data": [
    { "title": "함께하면 더 좋은 상품", "id": 1, "type": "grid", "url": "section_products_1" },
    { "title": "오늘 하루 특가 세일", "id": 2, "type": "horizontal", "url": "section_products_2" }
  ],
  "paging": { "next_page": 2 }
}
```

**섹션별 상품** (`GET /section/products?sectionId={id}`)
```json
{
  "data": [
    { "id": 5070319, "name": "국산 블루베리 100g (특)", "image": "...", "originalPrice": 7900, "isSoldOut": false },
    { "id": 5067331, "name": "[KF365] 삼겹살 대패 1kg", "image": "...", "originalPrice": 11900, "discountedPrice": 8400, "isSoldOut": false }
  ]
}
```

**Base URL:** `https://kurly.com/` (MockInterceptor 사용)

### 생성 파일

| 파일 | 설명 |
|------|------|
| `data/dto/SectionResponse.kt` | SectionResponse, SectionDto, PagingDto |
| `data/dto/SectionProductResponse.kt` | SectionProductResponse, ProductDto |
| `data/service/MockService.kt` | Retrofit interface (getSections, getSectionProducts) |
| `data/repository/MockRepository.kt` | Service + @IoDispatcher 주입, withContext + runCatching |
| `data/di/DispatcherQualifiers.kt` | @IoDispatcher, @DefaultDispatcher, @MainDispatcher, @UnconfinedDispatcher |
| `data/di/DispatchersModule.kt` | 4개 디스패처 Provides |
| `data/di/OkHttpClientModule.kt` | OkHttpClient + MockInterceptor + LoggingInterceptor |
| `data/di/ServiceModule.kt` | Retrofit 생성 (Kotlinx Serialization converter) |

> `data/build.gradle.kts` 수정: `android.library` → `convention.plugin.android.library` (compileSdk 누락 해결)

**Commit:** `b742ba0` - feat: implement data layer with Retrofit mock service, repository, and Hilt DI

---

## 3. Wishlist Repository 구현

### 요구사항
- Preferences DataStore 기반, DataStore/Dispatcher 주입
- `wishlist: Flow<Result<Set<String>>>` 프로퍼티
- `toggle(productId)`: contains → remove, else → add

### 생성 파일

| 파일 | 설명 |
|------|------|
| `data/repository/WishlistRepository.kt` | Flow 기반 wishlist, toggle 기능 |
| `data/di/DataStoreModule.kt` | DataStore\<Preferences\> 싱글톤 제공 |

**Commit:** `7fe1639` - feat: add wishlist repository with Preferences DataStore

---

## 4. UI 전체 구현

### 요구사항

**메인 화면**
- 여러 섹션으로 구성, pull-to-refresh 새로고침

**섹션 (3가지 타입)**
- `vertical`: 세로 스크롤 섹션
- `horizontal`: 가로 스크롤 섹션
- `grid`: 3(col) x 2(row) = 6개 상품 섹션
- 각 섹션별 title 노출

**상품 카드**
- horizontal/grid: 이미지 150dp x 200dp, 제목 2줄 말줄임
- vertical: 이미지 비율 6:4, 제목 1줄 말줄임
- 가격: 할인 시 할인율(`#FA622F`) + 할인가(bold) + 원래가격(취소선)
- horizontal/grid: 가격 2줄 (할인율+할인가 / 원래가격)
- vertical: 가격 1줄 (모든 정보)

**찜하기**
- 서버 없이 로컬 구현 (DataStore)
- 아이콘: `ic_btn_heart_on` / `ic_btn_heart_off`

### 생성 파일

| 파일 | 설명 |
|------|------|
| `ui/main/MainViewModel.kt` | Orbit MVI ViewModel, 페이지네이션, 병렬 상품 로딩, 찜하기 |
| `ui/main/MainScreen.kt` | PullToRefreshBox + LazyColumn, 3가지 섹션 레이아웃 |
| `ui/main/component/ProductCard.kt` | HorizontalGridProductCard, VerticalProductCard |
| `MainActivity.kt` | MainScreen 연결 |

**Commit:** `50dd005` - feat: implement main screen UI with sections, product cards, and wishlist

---

## 5. UI 개선 (1차)

### 변경사항
1. **Pull-to-refresh**: 새로고침 중 기존 아이템 유지 (sections 초기화 제거)
2. **원래가격 색상**: `Color.Gray` → `#FA622F` (DiscountColor)
3. **찜하기 아이콘 위치**: `BottomEnd` → `TopEnd`

**Commit:** `4096333` - fix: improve UI - keep items during refresh, fix icon position and colors

---

## 6. UI 개선 (2차)

### 변경사항
1. **ProductCard 이미지**: `fillMaxWidth + aspectRatio(3f/4f)` → 고정 `150dp x 200dp`
2. **Grid 섹션**: `weight(1f)` → `width(150.dp)`, horizontalScroll 추가
3. Vertical 카드는 기존 `aspectRatio(6f/4f)` 유지

---

## Commit History

| Hash | Message |
|------|---------|
| `54fd26f` | Initial commit |
| `b742ba0` | feat: implement data layer with Retrofit mock service, repository, and Hilt DI |
| `7fe1639` | feat: add wishlist repository with Preferences DataStore |
| `50dd005` | feat: implement main screen UI with sections, product cards, and wishlist |
| `4096333` | fix: improve UI - keep items during refresh, fix icon position and colors |
