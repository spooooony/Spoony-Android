# SPOONY
<img src="https://github.com/user-attachments/assets/e25de1b2-a2df-465b-a4ff-c6ff8d85b5b4">


**장소의 찐 리스트를 공유하며 신뢰할 수 있는 추천으로 나만의 지도를 완성하는 앱 서비스**

<br>

**‘Spoony’** 는 리뷰 작성자의 신뢰도와 영향력에 기반해, 유저가 믿을 수 있는 장소 정보를 탐색하고 공유하며 나만의 찐 리스트 지도를 만들어가는 앱 서비스입니다.

🌟Spoony와 함께 새로운 장소를 발견하고 나만의 지도를 완성해보세요!🌟

<br>


> 🥄 **Spoony 만의 특별한 사용법** <br><br>
1️⃣ **장소 등록하고 수저 획득하기**<br>
나만 알고 싶은 맛집, 아늑한 카페, 분위기 좋은 펍 등 찐 **장소를 등록하고 수저를 획득**하세요!<br><br>
2️⃣**신뢰도 높은 찐 리스트 떠먹기**<br>
획득한 수저로 다른 사람의 찐 리스트를 떠먹어 보세요! 원하는 유저를 팔로우하고 로컬 사용자와 지역별 랭킹을 통해 **신뢰도 높은 리뷰**를 확인해보세요.<br><br>
3️⃣ **나만의 지도 완성하기**<br>
떠먹은 리스트 중 **마음에 드는 장소를** 추가해 나만의 **찐 리스트 지도**를 만들어보세요!<br>

<br> 

## CONTRIBUTORS
|                                  👑박효빈<br/>([@Hyobeen-Park](https://github.com/Hyobeen-Park))                                    |                                      한민재<br/>([@angryPodo](https://github.com/angryPodo))                                       |                                  안세홍<br/>([@Roel4990](https://github.com/Roel4990))                                   |                                    박동민<br/>([@chattymin](https://github.com/chattymin))                                     |
|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|
| <img width="270px" src="https://github.com/user-attachments/assets/70668210-70cf-4cff-8c50-d8b95356ce30"/> | <img width="270px" src="https://github.com/user-attachments/assets/a373e957-978b-4f59-91b5-1a4b6f9352ad"/> | <img width="270px" src= "https://github.com/user-attachments/assets/245c76fb-2700-426d-a421-bf1f164168b2"/>     | <img width="270px" src="https://github.com/user-attachments/assets/d925f177-1c37-4426-971f-4cebbfb1e4f4"/>  |
|                                                      `메인 지도`<br/>`탐색하기`<br/>                                                      |                                                         `장소 등록하기`<br/>                                                    |                                          `장소 상세 페이지`<br/>`신고하기`<br/>                                       |                             `멘토`<br/>


<br>

## 📸 SCREENSHOTS

|       뷰       |                                                              1                                                              |                                                              2                                                              |                                                              3                                                              |
|:-------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|
| 메인 지도 <br> 탐색하기  | <img src="https://github.com/user-attachments/assets/dc0c8185-ebaf-4eb4-bc8a-f502b5c8082c" width="200"/> | <img src="https://github.com/user-attachments/assets/bd3efd23-dcf7-46ba-9444-233c1f17fe7c" width="200"/>  | <img src="https://github.com/user-attachments/assets/c4ed289e-8392-47a4-9c45-c18bf34ffbf7" width="200"/> |
| 장소 등록하기 | <img src="https://github.com/user-attachments/assets/3d59742f-5f49-429b-bcb4-a48acef84574" width="200"/> | <img src="https://github.com/user-attachments/assets/63d82360-49b7-4aa8-8d94-d0d0d8047658" width="200"/>  | <img src="https://github.com/user-attachments/assets/6156985e-6103-42bd-b2fb-acb37e468344" width="200"/> |
| 장소 상세페이지 <br> 신고하기 |<img src="https://github.com/user-attachments/assets/2c9486e6-5608-4676-b301-de487d42913d" width="200"/> | <img src="https://github.com/user-attachments/assets/134940f7-7aca-47c7-ad2d-e3f3e9c236fa" width="200"/>  | <img src="https://github.com/user-attachments/assets/cfada0cd-93c8-4c61-bded-254b3e179e15" width="200"/> |



## PACKAGE CONVENTION

```

📦spoony
 ┣ 📂core
 ┃ ┣ 📂database
 ┃ ┃ ┣ 📂di
 ┃ ┃ ┣ 📂entity
 ┃ ┣ 📂designsystem
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📂bottomsheet
 ┃ ┃ ┃ ┣ 📂button
 ┃ ┃ ┃ ┣ 📂chip
 ┃ ┃ ┃ ┣ 📂dialog
 ┃ ┃ ┃ ┣ 📂image
 ┃ ┃ ┃ ┣ 📂snackbar
 ┃ ┃ ┃ ┣ 📂tag
 ┃ ┃ ┃ ┣ 📂textfield
 ┃ ┃ ┃ ┗ 📂topappbar
 ┃ ┃ ┣ 📂event
 ┃ ┃ ┣ 📂theme
 ┃ ┃ ┗ 📂type
 ┃ ┣ 📂navigation
 ┃ ┣ 📂state
 ┃ ┗ 📂util
 ┃ ┃ ┗ 📂extension
 ┣ 📂data
 ┃ ┣ 📂datasource
 ┃ ┣ 📂datasourceimpl
 ┃ ┣ 📂di
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📂base
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┗ 📂response
 ┃ ┣ 📂mapper
 ┃ ┣ 📂repositoryimpl
 ┃ ┗ 📂service
 ┣ 📂domain
 ┃ ┣ 📂entity
 ┃ ┗ 📂repository
 ┗ 📂presentation
 ┃ ┣ 📂explore
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┗ 📂bottomsheet
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┗ 📂type
 ┃ ┣ 📂main
 ┃ ┃ ┗ 📂component
 ┃ ┣ 📂map
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┗ 📂bottomsheet
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┣ 📂navigaion
 ┃ ┃ ┗ 📂search
 ┃ ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┗ 📂navigation
 ┃ ┣ 📂placeDetail
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┗ 📂type
 ┃ ┣ 📂register
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┗ 📂navigation
 ┃ ┣ 📂report
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┗ 📂type
 ┃ ┗ 📂splash

```

<br>

## TECH STACK
| Title | Content                               |
| ------------ |---------------------------------------|
| Architecture | Clean Architecture, MVVM |
| Module | SingleModule|
| UI Framework  | Jetpack Compose                       |
| Dependency Injection | Hilt                                  |
| Network | Retrofit2, OkHttp                     |
| Asynchronous Processing | Coroutine, Flow                       |
| Third Party Library | Coil, Timber, Lottie, Naver Map       |
| Other Tools | Discode, Notion, Figma, Slack                |\
</br>

> 🛠 **Tech Stack 소개** <br><br>

1️⃣ **Architecture: Clean Architecture, MVVM**<br>
비즈니스 로직과 UI를 명확히 분리하고, 각 계층 간 의존성을 최소화했습니다. 코드의 재사용성을 높이고 유지보수가 용이하며, 특히 테스트 용이성이 크게 향상됩니다.<br><br>

2️⃣ **Dependency Injection: Hilt**<br>
구글이 공식 지원하는 Hilt로 보일러플레이트 코드를 최소화하면서도 강력한 DI 기능을 제공합니다. Android 컴포넌트들과의 완벽한 통합으로 테스트 가능한 코드베이스를 구축했습니다.<br><br>

3️⃣ **Network: Retrofit2, OkHttp**<br>
Retrofit2의 선언적 API 정의와 OkHttp의 강력한 인터셉터 기능으로 안정적이고 효율적인 네트워크 계층을 구현했습니다.<br><br>

4️⃣ **Other Tools**<br>
- GitHub: 코드 리뷰<br>
- Notion: 체계적인 프로젝트 문서화<br>
- Figma: UI/UX 디자인 협업<br>
- Slack: 빠른 팀 소통 및 개발 알림 통합<br>

## Foldering
```
📦spoony
 ┣ 📂core
 ┃ ┣ 📂database
 ┃ ┃ ┣ 📂di
 ┃ ┃ ┃ ┗ 📜DatabaseModule.kt
 ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┗ 📜SearchEntity.kt
 ┃ ┃ ┣ 📜SearchDao.kt
 ┃ ┃ ┗ 📜SearchDatabase.kt
 ┃ ┣ 📂designsystem
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📂bottomsheet
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyAdvancedBottomSheet.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyBasicBottomSheet.kt
 ┃ ┃ ┃ ┃ ┗ 📜SpoonyBasicDragHandle.kt
 ┃ ┃ ┃ ┣ 📂button
 ┃ ┃ ┃ ┃ ┗ 📜SpoonyButton.kt
 ┃ ┃ ┃ ┣ 📂chip
 ┃ ┃ ┃ ┃ ┗ 📜IconChip.kt
 ┃ ┃ ┃ ┣ 📂dialog
 ┃ ┃ ┃ ┃ ┣ 📜SingleButtonDialog.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyBasicDialog.kt
 ┃ ┃ ┃ ┃ ┗ 📜TwoButtonDialog.kt
 ┃ ┃ ┃ ┣ 📂image
 ┃ ┃ ┃ ┃ ┗ 📜SpoonyImage.kt
 ┃ ┃ ┃ ┣ 📂snackbar
 ┃ ┃ ┃ ┃ ┣ 📜BasicSnackbar.kt
 ┃ ┃ ┃ ┃ ┗ 📜TextSnackbar.kt
 ┃ ┃ ┃ ┣ 📂tag
 ┃ ┃ ┃ ┃ ┣ 📜IconTag.kt
 ┃ ┃ ┃ ┃ ┗ 📜LogoTag.kt
 ┃ ┃ ┃ ┣ 📂textfield
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyBasicTextField.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyIconButtonTextField.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyLargeTextField.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyLineTextField.kt
 ┃ ┃ ┃ ┃ ┗ 📜SpoonySearchTextField.kt
 ┃ ┃ ┃ ┗ 📂topappbar
 ┃ ┃ ┃ ┃ ┣ 📜CloseTopAppBar.kt
 ┃ ┃ ┃ ┃ ┣ 📜SpoonyBasicTopAppBar.kt
 ┃ ┃ ┃ ┃ ┣ 📜TagTopAppBar.kt
 ┃ ┃ ┃ ┃ ┗ 📜TitleTopAppBar.kt
 ┃ ┃ ┣ 📂event
 ┃ ┃ ┃ ┗ 📜SnackbarTrigger.kt
 ┃ ┃ ┣ 📂theme
 ┃ ┃ ┃ ┣ 📜Color.kt
 ┃ ┃ ┃ ┣ 📜Theme.kt
 ┃ ┃ ┃ ┗ 📜Type.kt
 ┃ ┃ ┗ 📂type
 ┃ ┃ ┃ ┣ 📜AdvancedSheetState.kt
 ┃ ┃ ┃ ┣ 📜ButtonStyles.kt
 ┃ ┃ ┃ ┣ 📜ChipColor.kt
 ┃ ┃ ┃ ┗ 📜TagSize.kt
 ┃ ┣ 📂navigation
 ┃ ┃ ┣ 📜MainTabRoute.kt
 ┃ ┃ ┗ 📜Route.kt
 ┃ ┣ 📂state
 ┃ ┃ ┗ 📜UiState.kt
 ┃ ┗ 📂util
 ┃ ┃ ┣ 📂extension
 ┃ ┃ ┃ ┣ 📜ColorExt.kt
 ┃ ┃ ┃ ┣ 📜ModifierExt.kt
 ┃ ┃ ┃ ┗ 📜StringExt.kt
 ┃ ┃ ┗ 📜UserId.kt
 ┣ 📂data
 ┃ ┣ 📂datasource
 ┃ ┃ ┣ 📜AuthRemoteDataSource.kt
 ┃ ┃ ┣ 📜CategoryDataSource.kt
 ┃ ┃ ┣ 📜ExploreRemoteDataSource.kt
 ┃ ┃ ┣ 📜MapRemoteDataSource.kt
 ┃ ┃ ┣ 📜PlaceDataSource.kt
 ┃ ┃ ┣ 📜PostRemoteDataSource.kt
 ┃ ┃ ┗ 📜ReportDataSource.kt
 ┃ ┣ 📂datasourceimpl
 ┃ ┃ ┣ 📜AuthRemoteDataSourceImpl.kt
 ┃ ┃ ┣ 📜CategoryDataSourceImpl.kt
 ┃ ┃ ┣ 📜ExploreRemoteDataSourceImpl.kt
 ┃ ┃ ┣ 📜MapRemoteDataSourceImpl.kt
 ┃ ┃ ┣ 📜PlaceDataSourceImpl.kt
 ┃ ┃ ┣ 📜PostRemoteDataSourceImpl.kt
 ┃ ┃ ┗ 📜ReportDataSourceImpl.kt
 ┃ ┣ 📂di
 ┃ ┃ ┣ 📜DataSourceModule.kt
 ┃ ┃ ┣ 📜RepositoryModule.kt
 ┃ ┃ ┣ 📜RetrofitModule.kt
 ┃ ┃ ┗ 📜ServiceModule.kt
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📂base
 ┃ ┃ ┃ ┗ 📜BaseResponse.kt
 ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┣ 📜AddMapRequestDto.kt
 ┃ ┃ ┃ ┣ 📜PlaceCheckRequestDto.kt
 ┃ ┃ ┃ ┣ 📜PostScoopRequestDto.kt
 ┃ ┃ ┃ ┣ 📜RegisterPostRequestDto.kt
 ┃ ┃ ┃ ┗ 📜ReportPostRequestDto.kt
 ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┣ 📜AddedMapListResponseDto.kt
 ┃ ┃ ┃ ┣ 📜AddedMapPostListDto.kt
 ┃ ┃ ┃ ┣ 📜CategoryResponseDto.kt
 ┃ ┃ ┃ ┣ 📜FeedsResponseDto.kt
 ┃ ┃ ┃ ┣ 📜GetPostResponseDto.kt
 ┃ ┃ ┃ ┣ 📜LocationListResponseDto.kt
 ┃ ┃ ┃ ┣ 📜PlaceCheckResponseDto.kt
 ┃ ┃ ┃ ┣ 📜PlaceResponseDto.kt
 ┃ ┃ ┃ ┣ 📜UserInfoResponseDto.kt
 ┃ ┃ ┃ ┣ 📜UserSpoonCountResponseDto.kt
 ┃ ┃ ┃ ┗ 📜ZzimLocationResponseDto.kt
 ┃ ┣ 📂mapper
 ┃ ┃ ┣ 📜AddedMapMapper.kt
 ┃ ┃ ┣ 📜AddedMapPostMapper.kt
 ┃ ┃ ┣ 📜CategoryMapper.kt
 ┃ ┃ ┣ 📜FeedsMapper.kt
 ┃ ┃ ┣ 📜GetPostResponseMapper.kt
 ┃ ┃ ┣ 📜LocationMapper.kt
 ┃ ┃ ┣ 📜PlaceMapper.kt
 ┃ ┃ ┣ 📜UserInfoMapper.kt
 ┃ ┃ ┗ 📜ZzimLocaitonMapper.kt
 ┃ ┣ 📂repositoryimpl
 ┃ ┃ ┣ 📜AuthRepositoryImpl.kt
 ┃ ┃ ┣ 📜CategoryRepositoryImpl.kt
 ┃ ┃ ┣ 📜ContentUriRequestBody.kt
 ┃ ┃ ┣ 📜ExploreRepositoryImpl.kt
 ┃ ┃ ┣ 📜MapRepositoryImpl.kt
 ┃ ┃ ┣ 📜PostRepositoryImpl.kt
 ┃ ┃ ┣ 📜RegisterRepositoryImpl.kt
 ┃ ┃ ┗ 📜ReportRepositoryImpl.kt
 ┃ ┗ 📂service
 ┃ ┃ ┣ 📜AuthService.kt
 ┃ ┃ ┣ 📜CategoryService.kt
 ┃ ┃ ┣ 📜ExploreService.kt
 ┃ ┃ ┣ 📜MapService.kt
 ┃ ┃ ┣ 📜PlaceService.kt
 ┃ ┃ ┣ 📜PostService.kt
 ┃ ┃ ┗ 📜ReportService.kt
 ┣ 📂domain
 ┃ ┣ 📂entity
 ┃ ┃ ┣ 📜AddedMapPostEntity.kt
 ┃ ┃ ┣ 📜AddedPlaceEntity.kt
 ┃ ┃ ┣ 📜CategoryEntity.kt
 ┃ ┃ ┣ 📜FeedEntity.kt
 ┃ ┃ ┣ 📜LocationEntity.kt
 ┃ ┃ ┣ 📜PlaceEntity.kt
 ┃ ┃ ┣ 📜PostEntity.kt
 ┃ ┃ ┣ 📜RegisterEntity.kt
 ┃ ┃ ┗ 📜UserEntity.kt
 ┃ ┗ 📂repository
 ┃ ┃ ┣ 📜AuthRepository.kt
 ┃ ┃ ┣ 📜CategoryRepository.kt
 ┃ ┃ ┣ 📜ExploreRepository.kt
 ┃ ┃ ┣ 📜MapRepository.kt
 ┃ ┃ ┣ 📜PostRepository.kt
 ┃ ┃ ┣ 📜RegisterRepository.kt
 ┃ ┃ ┗ 📜ReportRepository.kt
 ┣ 📂presentation
 ┃ ┣ 📂explore
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📂bottomsheet
 ┃ ┃ ┃ ┃ ┣ 📜ExploreLocationBottomSheet.kt
 ┃ ┃ ┃ ┃ ┣ 📜ExploreLocationDragHandle.kt
 ┃ ┃ ┃ ┃ ┗ 📜ExploreSortingBottomSheet.kt
 ┃ ┃ ┃ ┣ 📜ExploreEmptyScreen.kt
 ┃ ┃ ┃ ┣ 📜ExploreItem.kt
 ┃ ┃ ┃ ┗ 📜ExploreTopAppBar.kt
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┗ 📜FeedModel.kt
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┃ ┗ 📜ExploreNavigation.kt
 ┃ ┃ ┣ 📂type
 ┃ ┃ ┃ ┗ 📜SortingOption.kt
 ┃ ┃ ┣ 📜ExploreScreen.kt
 ┃ ┃ ┣ 📜ExploreState.kt
 ┃ ┃ ┗ 📜ExploreViewModel.kt
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┗ 📜MainBottomBar.kt
 ┃ ┃ ┣ 📜MainNavigator.kt
 ┃ ┃ ┣ 📜MainScreen.kt
 ┃ ┃ ┗ 📜MainTab.kt
 ┃ ┣ 📂map
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📂bottomsheet
 ┃ ┃ ┃ ┃ ┣ 📜MapBottomSheetDragHandle.kt
 ┃ ┃ ┃ ┃ ┣ 📜MapEmptyScreen.kt
 ┃ ┃ ┃ ┃ ┗ 📜MapListItem.kt
 ┃ ┃ ┃ ┗ 📜MapPlaceDetailCard.kt
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┗ 📜LocationModel.kt
 ┃ ┃ ┣ 📂navigaion
 ┃ ┃ ┃ ┗ 📜MapNavigation.kt
 ┃ ┃ ┣ 📂search
 ┃ ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┃ ┣ 📜MapSearchEmptyResultScreen.kt
 ┃ ┃ ┃ ┃ ┣ 📜MapSearchRecentEmptyScreen.kt
 ┃ ┃ ┃ ┃ ┣ 📜MapSearchRecentItem.kt
 ┃ ┃ ┃ ┃ ┣ 📜MapSearchResultItem.kt
 ┃ ┃ ┃ ┃ ┗ 📜MapSearchTopAppBar.kt
 ┃ ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┃ ┃ ┗ 📜MapSearchNavigation.kt
 ┃ ┃ ┃ ┣ 📜MapSearchScreen.kt
 ┃ ┃ ┃ ┣ 📜MapSearchState.kt
 ┃ ┃ ┃ ┗ 📜MapSearchViewModel.kt
 ┃ ┃ ┣ 📜MapScreen.kt
 ┃ ┃ ┣ 📜MapState.kt
 ┃ ┃ ┗ 📜MapViewModel.kt
 ┃ ┣ 📂placeDetail
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📜IconDropdownMenu.kt
 ┃ ┃ ┃ ┣ 📜PlaceDetailBottomBar.kt
 ┃ ┃ ┃ ┣ 📜PlaceDetailIconText.kt
 ┃ ┃ ┃ ┣ 📜PlaceDetailImageLazyRow.kt
 ┃ ┃ ┃ ┣ 📜ScoopDialog.kt
 ┃ ┃ ┃ ┣ 📜StoreInfo.kt
 ┃ ┃ ┃ ┣ 📜StoreInfoItem.kt
 ┃ ┃ ┃ ┗ 📜UserProfileInfo.kt
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┗ 📜PostModel.kt
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┃ ┗ 📜PlaceDetailNavigation.kt
 ┃ ┃ ┣ 📂type
 ┃ ┃ ┃ ┗ 📜DropdownOption.kt
 ┃ ┃ ┣ 📜PlaceDetailRoute.kt
 ┃ ┃ ┣ 📜PlaceDetailSideEffect.kt
 ┃ ┃ ┣ 📜PlaceDetailState.kt
 ┃ ┃ ┗ 📜PlaceDetailViewModel.kt
 ┃ ┣ 📂register
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📜AddMenuButtton.kt
 ┃ ┃ ┃ ┣ 📜CustomDropDownMenu.kt
 ┃ ┃ ┃ ┣ 📜NextButton.kt
 ┃ ┃ ┃ ┣ 📜PhotoPicker.kt
 ┃ ┃ ┃ ┣ 📜RegisterTooltip.kt
 ┃ ┃ ┃ ┣ 📜SearchResultItem.kt
 ┃ ┃ ┃ ┗ 📜TopLinearProgressBar.kt
 ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┣ 📜Category.kt
 ┃ ┃ ┃ ┗ 📜Place.kt
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┃ ┣ 📜RegisterNavigation.kt
 ┃ ┃ ┃ ┗ 📜RegisterRoute.kt
 ┃ ┃ ┣ 📜RegisterScreen.kt
 ┃ ┃ ┣ 📜RegisterState.kt
 ┃ ┃ ┣ 📜RegisterStepOneScreen.kt
 ┃ ┃ ┣ 📜RegisterStepTwoScreen.kt
 ┃ ┃ ┗ 📜RegisterViewModel.kt
 ┃ ┣ 📂report
 ┃ ┃ ┣ 📂component
 ┃ ┃ ┃ ┣ 📜ReportCompleteDialog.kt
 ┃ ┃ ┃ ┗ 📜ReportRadioButton.kt
 ┃ ┃ ┣ 📂navigation
 ┃ ┃ ┃ ┗ 📜ReportNavigation.kt
 ┃ ┃ ┣ 📂type
 ┃ ┃ ┃ ┗ 📜ReportOption.kt
 ┃ ┃ ┣ 📜ReportRoute.kt
 ┃ ┃ ┣ 📜ReportSideEffect.kt
 ┃ ┃ ┣ 📜ReportState.kt
 ┃ ┃ ┗ 📜ReportViewModel.kt
 ┃ ┣ 📂splash
 ┃ ┃ ┗ 📜SplashActivity.kt
 ┃ ┗ 📜MainActivity.kt
 ┗ 📜Spoony.kt
```

| 카테고리 | 문서 링크 |
|---------|-----------|
| **Convention** | • [안드푼스들의 깃 컨벤션](https://creative-suede-cad.notion.site/Git-Convention-4038dc7126e34df6aa042b400284b188?pvs=4) <br/> • [안드푼스들의 코딩 컨벤션](https://creative-suede-cad.notion.site/Android-Coding-Convention-71015e22d6a44f28b07aa756c81b2cf3?pvs=4) |
| **작업 트래킹** | • [스푸니 역할 분담](https://creative-suede-cad.notion.site/1add78d751384104b8b436f19342569d?v=667765114277498caeafdc0bab8b42d2&pvs=4) |
| **프로젝트 설계** | • [스푸니 프로젝트 설계](https://creative-suede-cad.notion.site/1add78d751384104b8b436f19342569d?v=667765114277498caeafdc0bab8b42d2&pvs=4) |
