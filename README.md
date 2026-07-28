# School Microservices (Instructor, Course, Student) — OpenFeign + JPA + H2 + Swagger

```
school-microservices/
├── instructor-service/   (port 8083) — base service, owns Instructor data
├── course-service/       (port 8082) — owns Course data, calls instructor-service via Feign
└── student-service/      (port 8081) — owns Student data, calls course-service via Feign
```

## Chain of dependency

```
student-service --(Feign)--> course-service --(Feign)--> instructor-service
```

So `instructor-service` must start FIRST, then `course-service`, then `student-service`.

## Response format (matches your spec exactly)

Every endpoint returns this shape:
```json
{
  "success": "true",
  "status": "200 OK",
  "message": "string",
  "payload": { },
  "time": "2026-07-28T10:00:00Z"
}
```

## How to run — start in this exact order

**Terminal 1:**
```
cd instructor-service
mvn spring-boot:run
```
Wait for `Tomcat started on port 8083`.

**Terminal 2:**
```
cd course-service
mvn spring-boot:run
```
Wait for `Tomcat started on port 8082`.

**Terminal 3:**
```
cd student-service
mvn spring-boot:run
```
Wait for `Tomcat started on port 8081`.

## Swagger UI
- http://localhost:8083/swagger-ui/index.html (Instructor)
- http://localhost:8082/swagger-ui/index.html (Course)
- http://localhost:8081/swagger-ui/index.html (Student)

## H2 Console
- http://localhost:8083/h2-console → JDBC URL `jdbc:h2:mem:instructordb`
- http://localhost:8082/h2-console → JDBC URL `jdbc:h2:mem:coursedb`
- http://localhost:8081/h2-console → JDBC URL `jdbc:h2:mem:studentdb`
- Username `sa`, password blank

## Sample data preloaded automatically

**instructor-service** (ids 1–3): Dr. Sopheak, Dr. Chenda, Ms. Lina

**course-service** (ids 1–3), each linked to an instructor:
| id | courseName | instructorId |
|---|---|---|
| 1 | Introduction to Computer Science | 1 (Dr. Sopheak) |
| 2 | Calculus II | 2 (Dr. Chenda) |
| 3 | Academic English Writing | 3 (Ms. Lina) |

**student-service** (ids 1–2), each already enrolled:
| id | studentName | enrolledCourseIds |
|---|---|---|
| 1 | Sokha | [1, 2] |
| 2 | Dara | [3] |

## Endpoints

### Instructor (`/api/v1/instructors`)
| Method | URL |
|---|---|
| POST | `http://localhost:8083/api/v1/instructors` |
| GET | `http://localhost:8083/api/v1/instructors/1` |
| PUT | `http://localhost:8083/api/v1/instructors/1` |
| DELETE | `http://localhost:8083/api/v1/instructors/1` |
| GET | `http://localhost:8083/api/v1/instructors` |

### Course (`/api/v1/courses`) — enriched with instructor via Feign
| Method | URL |
|---|---|
| POST | `http://localhost:8082/api/v1/courses` (body: `{"courseName":"...","description":"...","instructorId":1}`) |
| GET | `http://localhost:8082/api/v1/courses/1` |
| PUT | `http://localhost:8082/api/v1/courses/1` |
| DELETE | `http://localhost:8082/api/v1/courses/1` |
| GET | `http://localhost:8082/api/v1/courses` |

### Student (`/api/v1/students`) — enriched with courses (and their instructor) via Feign
| Method | URL |
|---|---|
| POST | `http://localhost:8081/api/v1/students` (body: `{"studentName":"...","email":"...","phoneNumber":"...","courseIds":[1,2]}`) |
| GET | `http://localhost:8081/api/v1/students/1` |
| PUT | `http://localhost:8081/api/v1/students/1` |
| DELETE | `http://localhost:8081/api/v1/students/1` |
| GET | `http://localhost:8081/api/v1/students` |
| GET | `http://localhost:8081/api/v1/students/course/{courseId}` — **get all students enrolled in a course** |
| POST | `http://localhost:8081/api/v1/students/{studentId}/enroll/{courseId}` |

## Best test to try first

`GET http://localhost:8081/api/v1/students/1/details` is NOT a real endpoint — use:
`GET http://localhost:8081/api/v1/students/1`

Expected response (full chain: student-service → course-service → instructor-service, all via Feign):
```json
{
  "success": "true",
  "status": "200 OK",
  "message": "Student retrieved successfully",
  "payload": {
    "studentId": 1,
    "studentName": "Sokha",
    "email": "sokha@example.com",
    "phoneNumber": "012345678",
    "courses": [
      {
        "courseId": 1,
        "courseName": "Introduction to Computer Science",
        "description": "Basics of programming and computing",
        "instructor": {"instructorId": 1, "instructorName": "Dr. Sopheak", "email": "sopheak@example.com"}
      },
      {
        "courseId": 2,
        "courseName": "Calculus II",
        "description": "Advanced calculus concepts",
        "instructor": {"instructorId": 2, "instructorName": "Dr. Chenda", "email": "chenda@example.com"}
      }
    ]
  },
  "time": "..."
}
```

Also try: `GET http://localhost:8081/api/v1/students/course/1` → returns Sokha (enrolled in course 1).

## Test the fallback
Stop `instructor-service`, then call `GET http://localhost:8082/api/v1/courses/1` — instructor details will show `"Unavailable (fallback)"` instead of crashing.

## Requirements
- Java 17+, Maven, internet connection on first run (to download dependencies)

## Note
My sandbox has no internet access, so I couldn't run `mvn` here to compile and test
three live services end-to-end. I reviewed every file carefully by hand — if you
hit any error when running it, paste it here and I'll fix it right away.
