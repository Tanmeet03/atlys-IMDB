# AtlysIMDB - Movie Database App

https://github.com/user-attachments/assets/1056b07a-39a2-49a3-86a5-9629620d618d

AtlysIMDB is an Android application built with Jetpack Compose, Hilt, Room, Retrofit, and Kotlin
Coroutines, designed to browse trending movies and search for movies using The Movie Database (TMDb)
API. The app features a clean architecture with a modular structure, offline caching, and theme
customization.

## Task Requirement

- [X]  Build a movie app that shows a list of movies on a screen.
- [X]  When a user clicks on a movie, a detail screen for that movie should open.
- [X]  The list screen should also support search functionality.
- [X]  You need to use [TMDB API](https://www.themoviedb.org/movie) to get movie information or any
  other movie API.
- [X]  Register on TMDB and generate your API key.
- [X]  Use the provided API to fetch the list of trending movies.
- [X]  Implement the movie list screen as per the design.
- [X]  Add search support on the list screen.
- [X]  Implement the movie detail screen as per the design.
- [X]  Refer to the provided documentation to fetch movie images.
- [X]  The app should display useful loading, empty, and error states where appropriate.
- [X]  Implement offline access by caching movie data locally and displaying it when the device is
  offline.
- [X]  You need to use Kotlin.
- [X]  You need to use [Jetpack Compose](https://developer.android.com/develop/ui/compose) for
  implementing UI.
- [X]  You need to
  use [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation) as your
  navigation framework.
- [X]  Code Quality: Clean, readable, and maintainable code.
- [X]  Functionality: Correct implementation of all listed tasks.
- [X]  Show us your work through your commit history.

## Extra Task

- [X]  Setting screen for toggling theme

## Project Overview

The app allows users to:

- View a list of trending movies.
- Search for movies by title.
- View detailed information about a selected movie.
- Toggle between light, dark, and system-default themes via a settings screen.

## Key Components and Their Purpose

### 1. **Application Setup**

- **`MovieApp.kt`**: The main application class
- **`MainActivity.kt`**: The entry point of the app.

### 2. **Dependency Injection**

- **`AppModule.kt`**: A Dagger Hilt module

### 3. **Data Layer**

#### Network

- **`NetworkMovie.kt`**: Data class for API responses from TMDb.
- **`MovieApiService`**: A Retrofit interface for API calls to fetch
  trending movies
- **`MovieRepository.kt`**: Repo combining network and cache operations.

#### Cache

- **`MovieEntity.kt`**: Room entity
- **`MovieDao.kt`**: Room DAO
- **`MovieMapper`**: Maps between `NetworkMovie`, `MovieEntity`,
  and domain `Movie` objects.

### 4. **Domain Layer**

- **`Movie.kt`** (in `domainData`): The domain/local model
- **`GetTrendingMoviesUseCase.kt`**: Usecase class to fetch trending movies.
- **`SearchMoviesUseCase.kt`**: Usecase to handle movie search logic

# Folder Structure

- **`bl`**: Business logic, including use cases, repositories, view model
- **`di`**: Dependency injection.
- **`ui`**: Ui presentation layer.
- To show case different ways we can create compose Ui folder i have given
  example of **`list`** and **`detail`**
    - **`list`**-> This is listing screen in which each section has its folder providing cleaner
      approach for distinguishment and defination
        - action
        - components
        - effect
        - intent
        - screen
        - state
    - **`detail`**-> This is detail screen where every thing falls in single folder for easy access

## Code Flow Summary

### Fetching Trending Movies

1. **UI**: `MovieListScreen` triggers `LoadMovies` intent on first load.
2. **ViewModel**: `MovieListViewModel` processes `FetchMovies` action, calling
   `GetTrendingMoviesUseCase`.
3. **Use Case**: Invokes `MovieRepository.getMovies()`.
4. **Repository**: Fetches from API, caches in Room, and emits domain models. Falls back to cache on
   network failure.
5. **UI**: Updates with the movie list or error.

### Searching Movies

1. **UI**: User types in `CustomSearchBar`, triggering `SearchMovies` intent after debounce.
2. **ViewModel**: `MovieListViewModel` processes `SearchMovies` action, calling
   `SearchMoviesUseCase`.
3. **Use Case**: Queries `MovieRepository.searchMovies()` with debouncing and filtering.
4. **Repository**: Searches Room database and emits results.
5. **UI**: Displays matching movies or empty state.

### Viewing Movie Details

1. **UI**: User clicks a `MovieItem`, triggering `SelectMovie` intent.
2. **ViewModel**: `MovieListViewModel` emits `NavigateToDetail` effect, navigating to
   `movie_detail/{movieId}`.
3. **UI**: `MovieDetailScreen` loads with `MovieDetailViewModel`.
4. **ViewModel**: `MovieDetailViewModel` processes `LoadMovie`, fetching the movie via
   `GetTrendingMoviesUseCase`.
5. **UI**: Displays movie details or error.

### Changing Theme

1. **UI**: User selects a theme in `SettingsScreen`.
2. **Preference**: `ThemePreference` saves the new theme.
3. **UI**: `MainActivity` reapplies the theme, updating the app’s appearance.

## Key Features

- **Offline Support**: Movies are cached in Room, allowing access during network failures.
- **Responsive UI**: Jetpack Compose with animations for smooth transitions.
- **Debounced Search**: Prevents excessive database queries.
- **Theme Customization**: Supports light, dark, and system themes.

## Dependencies

- Jetpack Compose: UI framework.
- Hilt: Dependency injection.
- Room: Local database.
- Retrofit: Network requests.
- Kotlin Coroutines: Asynchronous programming.
- Coil: Image loading.

# AtlysIMDB - Movie Database App

AtlysIMDB is an Android application built with Jetpack Compose, Hilt, Room, Retrofit, and Kotlin
Coroutines, designed to browse trending movies and search for movies using The Movie Database (TMDb)
API. The app features a clean architecture with a modular structure, offline caching, and theme
customization.

## Task Requirement

- [X]  Build a movie app that shows a list of movies on a screen.
- [X]  When a user clicks on a movie, a detail screen for that movie should open.
- [X]  The list screen should also support search functionality.
- [X]  You need to use [TMDB API](https://www.themoviedb.org/movie) to get movie information or any
  other movie API.
- [X]  Register on TMDB and generate your API key.
- [X]  Use the provided API to fetch the list of trending movies.
- [X]  Implement the movie list screen as per the design.
- [X]  Add search support on the list screen.
- [X]  Implement the movie detail screen as per the design.
- [X]  Refer to the provided documentation to fetch movie images.
- [X]  The app should display useful loading, empty, and error states where appropriate.
- [X]  Implement offline access by caching movie data locally and displaying it when the device is
  offline.
- [X]  You need to use Kotlin.
- [X]  You need to use [Jetpack Compose](https://developer.android.com/develop/ui/compose) for
  implementing UI.
- [X]  You need to
  use [Compose Navigation](https://developer.android.com/develop/ui/compose/navigation) as your
  navigation framework.
- [X]  Code Quality: Clean, readable, and maintainable code.
- [X]  Functionality: Correct implementation of all listed tasks.
- [X]  Show us your work through your commit history.

## Extra Task

- [X]  Setting screen for toggling theme

## Project Overview

The app allows users to:

- View a list of trending movies.
- Search for movies by title.
- View detailed information about a selected movie.
- Toggle between light, dark, and system-default themes via a settings screen.

## Key Components and Their Purpose

### 1. **Application Setup**

- **`MovieApp.kt`**: The main application class
- **`MainActivity.kt`**: The entry point of the app.

### 2. **Dependency Injection**

- **`AppModule.kt`**: A Dagger Hilt module

### 3. **Data Layer**

#### Network

- **`NetworkMovie.kt`**: Data class for API responses from TMDb.
- **`MovieApiService`**: A Retrofit interface for API calls to fetch
  trending movies
- **`MovieRepository.kt`**: Repo combining network and cache operations.

#### Cache

- **`MovieEntity.kt`**: Room entity
- **`MovieDao.kt`**: Room DAO
- **`MovieMapper`**: Maps between `NetworkMovie`, `MovieEntity`,
  and domain `Movie` objects.

### 4. **Domain Layer**

- **`Movie.kt`** (in `domainData`): The domain/local model
- **`GetTrendingMoviesUseCase.kt`**: Usecase class to fetch trending movies.
- **`SearchMoviesUseCase.kt`**: Usecase to handle movie search logic

# Folder Structure

- **`bl`**: Business logic, including use cases, repositories, view model
- **`di`**: Dependency injection.
- **`ui`**: Ui presentation layer.
- To show case different ways we can create compose Ui folder i have given
  example of **`list`** and **`detail`**
    - **`list`**-> This is listing screen in which each section has its folder providing cleaner
      approach for distinguishment and defination
        - action
        - components
        - effect
        - intent
        - screen
        - state
    - **`detail`**-> This is detail screen where every thing falls in single folder for easy access

## Code Flow Summary

### Fetching Trending Movies

1. **UI**: `MovieListScreen` triggers `LoadMovies` intent on first load.
2. **ViewModel**: `MovieListViewModel` processes `FetchMovies` action, calling
   `GetTrendingMoviesUseCase`.
3. **Use Case**: Invokes `MovieRepository.getMovies()`.
4. **Repository**: Fetches from API, caches in Room, and emits domain models. Falls back to cache on
   network failure.
5. **UI**: Updates with the movie list or error.

### Searching Movies

1. **UI**: User types in `CustomSearchBar`, triggering `SearchMovies` intent after debounce.
2. **ViewModel**: `MovieListViewModel` processes `SearchMovies` action, calling
   `SearchMoviesUseCase`.
3. **Use Case**: Queries `MovieRepository.searchMovies()` with debouncing and filtering.
4. **Repository**: Searches Room database and emits results.
5. **UI**: Displays matching movies or empty state.

### Viewing Movie Details

1. **UI**: User clicks a `MovieItem`, triggering `SelectMovie` intent.
2. **ViewModel**: `MovieListViewModel` emits `NavigateToDetail` effect, navigating to
   `movie_detail/{movieId}`.
3. **UI**: `MovieDetailScreen` loads with `MovieDetailViewModel`.
4. **ViewModel**: `MovieDetailViewModel` processes `LoadMovie`, fetching the movie via
   `GetTrendingMoviesUseCase`.
5. **UI**: Displays movie details or error.

### Changing Theme

1. **UI**: User selects a theme in `SettingsScreen`.
2. **Preference**: `ThemePreference` saves the new theme.
3. **UI**: `MainActivity` reapplies the theme, updating the app’s appearance.

## Key Features

- **Offline Support**: Movies are cached in Room, allowing access during network failures.
- **Responsive UI**: Jetpack Compose with animations for smooth transitions.
- **Debounced Search**: Prevents excessive database queries.
- **Theme Customization**: Supports light, dark, and system themes.

## Dependencies

- Jetpack Compose: UI framework.
- Hilt: Dependency injection.
- Room: Local database.
- Retrofit: Network requests.
- Kotlin Coroutines: Asynchronous programming.
- Coil: Image loading.


