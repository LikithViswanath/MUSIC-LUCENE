# RESTful-Microservices

• Developed a Music Recommendation Service with Spring Boot and Apache Lucene for indexing and searching song metadata, including attributes like song ID, name, genre, artist, album, rating, and number of ratings.

• Implemented CRUD Operations using Lucene's IndexWriter and IndexSearcher for efficient document management and querying of song records.

• Configured FSDirectory for file-based indexing to ensure fast and reliable access to song data, integrating with MongoDB for metadata storage and MySQL for transactional data.

• Ensured Robust Error Handling by incorporating custom exception management and logging to address issues effectively and improve service reliability.

• Deployed with Docker Compose for containerization and orchestration.

## Tech Stack

- **Backend Framework:** Spring Boot
- **Search Engine:** Apache Lucene

## Installation

Build the music docker container:

```bash
docker build -t music .
```

Navigate to the directory containing your docker-compose.yml file and run the following command to start the application:

```bash
docker-compose up
```

### API Endpoints


#### add Song
```javascript
  POST http://localhost:8080/songs
```
#### Example Payload
```json 
{
  "songId": "1",
  "name": "Song Name",
  "genre": "Rock",
  "artist": "Artist Name",
  "album": "Album Name",
  "rating": 4.5,
  "ratingNum": 100
}

```

#### Example Response
```
201 Created
```

#### Get all Songs
```javascript
    GET http://localhost:8080/songs
```
#### Example Response
```json 
[
  {
    "songId": "1",
    "name": "Song Name",
    "genre": "Rock",
    "artist": "Artist Name",
    "album": "Album Name",
    "rating": 4.5,
    "ratingNum": 100
  },
  {
    "songId": "2",
    "name": "Another Song",
    "genre": "Pop",
    "artist": "Another Artist",
    "album": "Another Album",
    "rating": 4.0,
    "ratingNum": 200
  }
]

```

#### Search Songs
```javascript
    GET http://localhost:8080/songs/search?searchString=Rock
```
#### Example Response
```json 
[
  {
    "songId": "1",
    "name": "Song Name",
    "genre": "Rock",
    "artist": "Artist Name",
    "album": "Album Name",
    "rating": 4.5,
    "ratingNum": 100
  }
]
```

#### Recommend Songs (Returns Songs Based on genre_list, album_list and artist_list)

```javascript
      POST http://localhost:8080/songs/recommend
```
#### Example Payload
```json 
{
  "genre_list": ["Rock"],
  "album_list": ["Album1"],
  "artist_list": ["Artist1"]
}
```
#### Example Response 
```json 
[
  {
    "songId": "1",
    "name": "Song Name",
    "genre": "Rock",
    "artist": "Artist Name",
    "album": "Album Name",
    "rating": 4.5,
    "ratingNum": 100
  },
  {
    "songId": "2",
    "name": "Another Song",
    "genre": "Pop",
    "artist": "Another Artist",
    "album": "Another Album",
    "rating": 4.0,
    "ratingNum": 200
  }
]

```

