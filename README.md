# Object Modeling Project
The **Object Modeling** project is a Java-based application developed using **Spring Boot**, designed to streamline the management of song contests. The system handles various operations related to contests, users, and questions, allowing users to participate in contests, submit answers, and generate leaderboards based on their performance.

### Project Wallthrough
[![Watch the video](https://img.youtube.com/vi/4SdfW_SgZug/0.jpg)](https://youtu.be/4SdfW_SgZug)

## Key Features
- **Contest Creation**: Create and manage contests with a variety of questions and difficulty levels.
- **User Participation**: Allow users to register and participate in contests.
- **Leaderboard Generation**: Generate leaderboards based on users' performance and scores.
- **Scalable Architecture**: Designed for scalability, ensuring smooth performance as user and contest numbers grow.
  
## Architecture Overview

This project follows the **Model-View-Controller (MVC)** architecture, ensuring a clean separation between data, logic, and user interactions.

### Model Layer
Represents key entities of the application:
- **Contest**: Stores details of a song contest, including name, difficulty, and associated questions.
- **User**: Captures user information and performance data.
- **Question**: Represents contest questions, including the correct answers.

These entities are persisted in the database using **JPA annotations**.

### View Layer
Since this is a backend-focused project, the view layer consists of **API endpoints** that allow users to interact with the system.

### Controller Layer
Various command classes act as controllers to handle specific user actions:
- **CreateContestCommand**: Initializes and saves new contests.
- **CreateUserCommand**: Manages user creation and data persistence.
- **AttendContestCommand**: Handles user participation in contests and updates their performance.
- **LeaderBoardCommand**: Generates and returns a leaderboard based on user scores.

## Repository Layer
The repository layer manages data persistence using the **Spring Data JPA** repository pattern, providing clean abstractions for CRUD operations.

### Key Repositories
- **ContestRepository**
- **UserRepository**
- **QuestionRepository**

### Optimizations
To ensure efficiency, indexed fields were used to optimize leaderboard generation and frequently queried data. **Custom query methods** were implemented for complex data retrieval.

## Challenges and Solutions
### 1. Efficient Data Management
- **Challenge**: Scaling the system for large datasets.
- **Solution**: Optimized SQL queries using joins, indexing, and caching to ensure smooth data retrieval.

### 2. Seamless Integration
- **Challenge**: Ensuring all components work together without introducing bugs.
- **Solution**: A **Test-Driven Development (TDD)** approach with comprehensive unit tests was implemented.

### 3. Scalability
- **Challenge**: Handling growing numbers of contests and users.
- **Solution**: The application is stateless where possible, and Spring Boot's auto-configuration allows for **horizontal scalability**.

## Technologies Used
- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **H2 / MySQL (for persistence)**
- **Gradle**
- **JUnit and Mockito (for testing)**

## Getting Started

### Prerequisites
- Java 11+
- Gradle
- A compatible database like H2 or MySQL

### User
    User {
      int id;
      String name;
      int score;
      List<Contest> contests;
    }
    Question {
      int id;
      String title;
      String level; // LOW, MEDIUM, HIGH
      int score;
    }
    Contest {
      int id;
      String name;
      String level; // LOW, MEDIUM, HIGH
      User creator;
      List<Question> questions;
      String contestStatus; // NOT_STARTED, IN_PROGRESS, COMPLETED
    }

### Example Data
## Users
    User [id=1, name=Ross, score=1500]
    User [id=2, name=Monica, score=1500]
    User [id=3, name=Joey, score=1500]
    User [id=4, name=Chandler, score=1500]

## Questions
    Question [id=1, title=Question1, level=LOW, score=10]
    Question [id=2, title=Question2, level=LOW, score=20]
    Question [id=10, title=Question10, level=LOW, score=100]

## Contests
    Contest [id=1, name=CRIODO1_CONTEST, level=HIGH, creator=Ross, contestStatus=NOT_STARTED, questions=[Question22, Question23, ...]]
    Contest [id=2, name=CRIODO2_CONTEST, level=LOW, creator=Monica, contestStatus=NOT_STARTED, questions=[Question1, Question2, ...]]


## Classes Overview

### User

- **Attributes**: `id`, `name`, `score`, `contests`
- **Description**: Represents a user who participates in contests.

### Contest

- **Attributes**: `id`, `name`, `level`, `creator`, `contestStatus`, `questions`
- **Description**: Represents a contest created by a user with a specific level and a list of questions.

### Question

- **Attributes**: `id`, `level`, `score`, `title`
- **Description**: Represents a question with a specific level and score that can be added to contests.

## API Endpoints

### Users

- **Get All Users**: `/users` - Returns a list of all users.
- **Get User by ID**: `/users/{id}` - Returns a user by their ID.
- **Create User**: `/users` - Creates a new user.

### Contests

- **Get All Contests**: `/contests` - Returns a list of all contests.
- **Get Contest by ID**: `/contests/{id}` - Returns a contest by its ID.
- **Create Contest**: `/contests` - Creates a new contest.

### Questions

- **Get All Questions**: `/questions` - Returns a list of all questions.
- **Get Question by ID**: `/questions/{id}` - Returns a question by its ID.
- **Create Question**: `/questions` - Creates a new question.

## Contributors

- **Ross**: Contest Creator
- **Monica**: Contest Creator
- **Chandler**: Contest Creator
- **Joey**: Participant
